(ns hospital.exer3
  (:use [clojure.pprint])
  (:require [hospital.model :as h.model]
            [hospital.logic :as h.logic]))

(defn teste-atomico []
  (let [hospital-dois (atom {:espera hospital.model/fila-vazia})]
    (println hospital-dois)
    (pprint hospital-dois)
    (pprint @hospital-dois)


    ;NÃO É ASSIM QUE EU ALTEROO CONTEÚDO DENTRO DE UM ÁTOMO
    (pprint( assoc @hospital-dois :laboratorio1 hospital.model/fila-vazia))
    (pprint @hospital-dois)


    ;ESSA É UMA DAS MANEIRAS DE ALTERAR CONTEÚDO DENTRO DE UM ÁTOMO
    (swap! hospital-dois assoc :laboratorio1 hospital.model/fila-vazia)
    (pprint @hospital-dois)

    (swap! hospital-dois assoc :laboratorio2 hospital.model/fila-vazia)
    (pprint @hospital-dois)


    ;UPDATE TRADICIONAL IMUTÁVEL, COM DERREFENCIA, QUE NÃO TRARÁ EFEITO
    (update @hospital-dois :laboratorio1 conj "111")

    ;INDO PRO SWAP
    (swap! hospital-dois update :laboratorio1 conj "111")
    (pprint @hospital-dois)




    ))
(teste-atomico)

