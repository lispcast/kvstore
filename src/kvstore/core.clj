(ns kvstore.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [compojure.core :refer [defroutes GET PUT POST DELETE]]
            [compojure.route :as route]
            [cheshire.core :as json]
            [clj-http.client :as http]
            [clojure.core.async :as async]
            [clojure.java.io :as io])
  (:import [java.util UUID])
  (:gen-class))

(println "recompiling")

(defn break-put [op]
  (if (= "put" (first op))
    (let [[_ id k v] op]
      (if (number? v)
        ["put" id k (inc v)]
        op))
    op))

(defn add-ops [store ops]
  (let [ids (set (map second store))]
    (into store (remove #(contains? ids (second %)) ops))))

(defn swap-bad! [atom f & args]
  (let [v @atom]
    (Thread/sleep 10)
    (reset! atom (apply f v args))))

(defonce dbs (atom {}))

(defroutes routes
  (POST "/kv" []
        (let [uuid (UUID/randomUUID)]
          (swap! dbs assoc (str uuid) (atom []))
          (json/generate-string {:kv-id (str uuid)} )))
  (POST "/kv/:kvid" {{:keys [kvid]} :params
                     body :body
                     :as req}
        (if-some [store (get @dbs kvid)]
          (let [json-body (json/parse-stream (io/reader body) keyword)
                ops (:ops json-body)
                ;;ops (map break-put ops)
                cursor (:cursor json-body)
                ops (swap! store add-ops ops)]
            (json/generate-string {:ops (drop cursor ops)}))
          {:status 404
           :body (json/generate-string {:error "KV Store not found."
                                        :params (:params req)
                                        :body (json/parse-stream (io/reader body) keyword)})}))
  (DELETE "/kv/:kvid" [kvid]
          (if-some [store (get @dbs kvid)]
            (do
              (swap! dbs dissoc kvid)
              (json/generate-string {:done true}))
            {:status 404
             :body "{\"error\":\"not found\"}"}))
  (route/not-found "{\"error\":\"not found\"}"))

(def app
  (-> routes
      wrap-keyword-params
      wrap-params
      wrap-reload))

(defn -main []
  (jetty/run-jetty #'app {:port 8989}))

