(ns tatara.repository-contract-test
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is]]))

(def root (-> *file* io/file .getParentFile .getParentFile .getParentFile))

(deftest canonical-edn-contract
  (doseq [path ["identity.edn" "manifest.edn" "schema/schema.edn"
                "data/seed-plant-graph.kotoba.edn" "kotoba.app.edn"]]
    (is (some? (edn/read-string (slurp (io/file root path)))) path)))

(deftest wire-formats-are-contained
  (is (.exists (io/file root "wire/manifest.jsonld")))
  (is (not (.exists (io/file root "manifest.jsonld")))))
