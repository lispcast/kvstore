# kvstore

Code to accompany the [course][course], part of
[PurelyFunctional.tv][mentoring] Clojure training.

[course]: https://purelyfunctional.tv/courses/property-based-testing-with-test-check/
[mentoring]: https://purelyfunctional.tv/

This project is a key-value store server. The client is in
the accompanying code repository
(https://github.com/lispcast/test-check-playground).

## Usage

To run this server, you will need
[Leiningen](https://leiningen.org). You can find install
guides for the major platforms
[here](https://purelyfunctional.tv/guide/how-to-install-clojure/).

With Leiningen set up, just run:

```bash
$CMD lein run
```

in the directory. The server will start on port 8989. There
are no options for changing that. Feel free to change the
code (and don't forget to change the client).

## License

[![CC0](http://i.creativecommons.org/p/zero/1.0/88x31.png)](http://creativecommons.org/publicdomain/zero/1.0/)

To the extent possible under law, the person who associated CC0 with
this work has waived all copyright and related or neighboring rights
to the code in this repository.

See the `LICENSE` file for more information.
