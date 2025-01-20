(ns hospital.exer5
  (:use [clojure.pprint])
  (:require [hospital.model :as h.model]
            [hospital.logic :as h.logic]))



(defn chega-em! [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa))

(defn transfere! [hospital de para]
  (swap! hospital h.logic/transfere de para))

(defn simula-um-dia []
  (let [hospital (atom (h.model/novo-hospital))]
    (chega-em! hospital "João")
    (chega-em! hospital "Tiago")
    (chega-em! hospital "André")
    (chega-em! hospital "Pedro")
    (transfere! hospital :espera :laboratorio1)
    (pprint hospital)))

(simula-um-dia)



