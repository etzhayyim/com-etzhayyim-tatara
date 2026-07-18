#!/usr/bin/env bb
;; tatara 鑪 — bb-native test runner (Clojure / babashka; no shell).
;;
;;   bb run_tests.clj      ; run from anywhere
;;
;; The standalone repository's src and test roots are derived from THIS file.
(require '[babashka.classpath :as cp]
         '[babashka.fs :as fs]
         '[clojure.test :as t])

(def repo-root (fs/parent (fs/absolutize *file*)))
(cp/add-classpath (str (fs/path repo-root "src")))
(cp/add-classpath (str (fs/path repo-root "test")))

(def suites
  '[tatara.methods.test-analyze
    tatara.methods.test-kotoba
    tatara.methods.test-autorun
    tatara.methods.test-crosscheck
    tatara.methods.test-maturity
    tatara.methods.test-viz
    tatara.methods.test-robustness
    tatara.repository-contract-test])

(apply require suites)

(let [{:keys [fail error]} (apply t/run-tests suites)]
  (if (zero? (+ fail error))
    (println "── tatara: ALL suites green ──")
    (do (println "── tatara: FAILURES above ──")
        (System/exit 1))))
