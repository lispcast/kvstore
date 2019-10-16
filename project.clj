(defproject kvstore "0.1.0-SNAPSHOT"
  :description "Part of PurelyFunctional.tv"
  :url "https://purelyfunctional.tv/"
  :license {:name "CC0 1.0 Universal (CC0 1.0) Public Domain Dedication"
            :url "http://creativecommons.org/publicdomain/zero/1.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/core.async "0.4.500"]
                 [javax.servlet/servlet-api "2.5"]
                 [ring "1.7.1"]
                 [compojure "1.6.1"]
                 [cheshire "5.9.0"]
                 [clj-http "3.10.0"]]
  :main kvstore.core)
