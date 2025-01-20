(ns hospital.exer4
  (:use [clojure.pprint])
  (:require [hospital.model :as h.model]
            [hospital.logic :as h.logic]))


(defn chega-sem-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa)
  (println "apos inserir" pessoa))

(defn simula-um-dia-em-paralelo-com-mapv []
  "Simulação utilizando mapv para forçar imperativamente a execução do que era lazy"
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]]

    (mapv #(.start (Thread. (fn [] (chega-sem-malvado! hospital %)))) pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000) (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-mapv)


(defn simula-um-dia-em-paralelo-com-mapv-refatorada []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]
        starta-thread-de-chegada #(.start (Thread. (fn [] (chega-sem-malvado! hospital %))))]

    (mapv starta-thread-de-chegada pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000) (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-mapv-refatorada)



(defn starta-thread-de-chegada [hospital pessoa]
  (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa)))))

(defn preparada [hospital]
  (fn [pessoa] (starta-thread-de-chegada hospital pessoa)))

(defn simula-um-dia-em-paralelo-com-mapv-extraida []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]]

    (mapv (preparada hospital) pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000) (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-mapv-extraida)


(defn starta-thread-de-chegada
  ([hospital]
   (fn [pessoa] (starta-thread-de-chegada hospital pessoa)))
  ([hospital pessoa]
   (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa))))))

(defn simula-um-dia-em-paralelo-com-mapv-extraida []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]]

    (mapv (starta-thread-de-chegada hospital) pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000) (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-mapv-extraida)



(defn starta-thread-de-chegada
  ([hospital pessoa]
   (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa))))))

(defn simula-um-dia-em-paralelo-com-partial []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]
        starta (partial starta-thread-de-chegada hospital)]

    (mapv starta pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000) (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-partial)



(defn starta-thread-de-chegada
  ([hospital pessoa]
   (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa))))))

(defn simula-um-dia-em-paralelo-com-doseq []
  "Aqui estou preocupado em executar para os elementos da sequência"
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]]

    (doseq [pessoa pessoas]
      (starta-thread-de-chegada hospital pessoa))
    (.start (Thread. (fn [] (Thread/sleep 8000) (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-doseq)



(defn simula-um-dia-em-paralelo-com-doseq []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas (range 6)]

    (doseq [pessoa pessoas]
      (starta-thread-de-chegada hospital pessoa))
    (.start (Thread. (fn [] (Thread/sleep 8000) (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-doseq)



(defn simula-um-dia-em-paralelo-com-dotimes []
  "Estou preocupado que execute N vezes"
  (let [hospital (atom (h.model/novo-hospital))]

    (dotimes [pessoa 6]
      (starta-thread-de-chegada hospital pessoa))
    (.start (Thread. (fn [] (Thread/sleep 8000) (pprint hospital))))))

(simula-um-dia-em-paralelo-com-dotimes)



