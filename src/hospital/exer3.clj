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
    (pprint @hospital-dois)))
;(teste-atomico)


(defn chega-em-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em-pausado-logando :espera pessoa)
  (println "apos inserir" pessoa))

(defn simula-um-dia-em-paralelo []
    (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-em-malvado! hospital "111"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "222"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "333"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "444"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "555"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000) (pprint hospital))))))

;FORÇANDO SITUAÇÕES DE RETRY
;(simula-um-dia-em-paralelo)


(defn chega-sem-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa)
  (println "apos inserir" pessoa))

(defn simula-um-dia-em-paralelo []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "111"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "222"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "333"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "444"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "555"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000) (pprint hospital))))))

;SEM FORÇAR SITUAÇÃO DE RETRY, PODE OU NÃO ACONTECER
(simula-um-dia-em-paralelo)

