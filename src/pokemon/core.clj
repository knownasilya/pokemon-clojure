(ns pokemon.core
  (:require [malli.generator :as gen]
            [clojure.string :as string]
            [clj-http.client :as client]
            [babashka.cli :as cli]))

;;; https://pokeapi.co/api/v2/pokemon/{name}

(def Pokemon [:map
              [:name [:string {:min 3 :max 100}]]
              [:id :uuid]
              [:height [:int {:min 1}]]
              [:order [:int {:min 0}]]
              [:type [:enum
                      "normal" "fire" "flying"]]])

(def pokemon-names ["ditto" "pikachu" "charmander" "bulbasaur" "squirtle"])

(def pokemon (gen/sample Pokemon))

(defn search [name pokemon]
  (->> pokemon
       (filter #(string/includes? % name))
       (first)))

(defn find-pokemon
  ([name]
   (if (empty? name)
     (throw (ex-info "empty pokemon" {}))
     (client/get (str "https://pokeapi.co/api/v2/pokemon/" name) {:accept :json :as :json})))
  ([] (find-pokemon (:name (gen/generate Pokemon)))))

(defn run [options]
  (try (-> (search (:name options) pokemon-names)
           (find-pokemon)
           (:body)
           (select-keys [:name :height :order :id])
           (println))
       (catch Exception _ (println "pokemon not found"))))

(defn -main [& args]
  (let [a (cli/parse-args args)]
    (run {:name (first (:args a))})))