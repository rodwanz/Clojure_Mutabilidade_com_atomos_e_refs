(ns hospital.exer6
  (:use [clojure.pprint])
  (:require [hospital.model :as h.model]))


(defn chega-em [fila pessoa]
  (conj fila pessoa))

(defn chega-em! [hospital pessoa]
  "Troca de rreferência via ref-set"
  (let [fila (get hospital :espera)]
    (ref-set fila (chega-em @fila pessoa))))


(defn chega-em! [hospital pessoa]
  "Troca de referência via alter"
  (let [fila (get hospital :espera)]
    (alter fila chega-em pessoa)))


(defn simula-um-dia []
  (let [hospital {:espera (ref hospital.model/fila-vazia)
                  :laboratorio1 (ref hospital.model/fila-vazia)
                  :laboratorio2 (ref hospital.model/fila-vazia)
                  :laboratorio3 (ref hospital.model/fila-vazia)}]
    (dosync (chega-em! hospital "Pedro"))
    (pprint hospital)))


(simula-um-dia)












