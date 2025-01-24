(ns hospital.exer6
  (:use [clojure.pprint])
  (:require [hospital.model :as h.model]))


(defn cabe-na-fila? [fila]
  (-> fila
      count
      (<  5)))

(defn chega-em [fila pessoa]
  (if (cabe-na-fila? fila)
    (conj fila pessoa)
    (throw (ex-info "Fila esta cheia" {:tentando-adicionar pessoa}))))

(defn chega-em! [hospital pessoa]
  "Troca de rreferência via ref-set"
  (let [fila (get hospital :espera)]
    (ref-set fila (chega-em @fila pessoa))))

(defn chega-em! [hospital pessoa]
  "Troca de referência via alter"
  (let [fila (get hospital :espera)]
    (alter fila chega-em pessoa)))

(defn simula-um-dia []
  (let [hospital {:espera       (ref hospital.model/fila-vazia)
                  :laboratorio1 (ref hospital.model/fila-vazia)
                  :laboratorio2 (ref hospital.model/fila-vazia)
                  :laboratorio3 (ref hospital.model/fila-vazia)}]
    (dosync (chega-em! hospital "Pedro")
            (chega-em! hospital "André")
            (chega-em! hospital "Tiago")
            (chega-em! hospital "João")
            (chega-em! hospital "Filipe")
            ;(chega-em! hospital "Mateus")
            )
    (pprint hospital)))


;(simula-um-dia)


(defn async-chega-em! [hospital pessoa]
  (future (Thread/sleep (rand 5000))
          (dosync (println "Tentando o código sincronizado" pessoa)
                  (chega-em! hospital pessoa))))

(defn simula-um-dia-async []
  (let [hospital {:espera       (ref hospital.model/fila-vazia)
                  :laboratorio1 (ref hospital.model/fila-vazia)
                  :laboratorio2 (ref hospital.model/fila-vazia)
                  :laboratorio3 (ref hospital.model/fila-vazia)}]
    (def futures (mapv #(async-chega-em! hospital %) (range 10)))
    (future
      (dotimes [n 4]
        (Thread/sleep 2000)
        (pprint hospital)
        (pprint futures)))))

(simula-um-dia-async)





