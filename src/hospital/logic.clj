(ns hospital.logic)

(defn cabe-na-fila? [hospital departamento]
(-> hospital
    (get ,,, departamento)
    count ,,,
    (< ,,, 5)))

(defn chega-em
  [hospital departamento pessoa]
    (if (cabe-na-fila? hospital departamento)
      (update hospital departamento conj pessoa)
      (throw (ex-info "Fila esta cheia" {:tentando-adicionar pessoa}))))

;FUNÇÃO QUE PARECE PURA MAIS USA RANDOM
(defn chega-em-pausado
  [hospital departamento pessoa]
  (Thread/sleep (*(rand) 2000))
  (if (cabe-na-fila? hospital departamento)
    (do                                                     ;
       ;(Thread/sleep (*(rand) 2000))
      (update hospital departamento conj pessoa))
    (throw (ex-info "Fila esta cheia" {:tentando-adicionar pessoa}))))

;FUNÇÃO QUE PARECE PURA MAIS USA RANDOM E ALTERA ESTADO DO RANDOM E LOGA
(defn chega-em-pausado-logando
  [hospital departamento pessoa]
  (println "Tentando adicionar a pessoa" pessoa)
  (Thread/sleep (*(rand) 2000))
  (if (cabe-na-fila? hospital departamento)
    (do                                                     ;
      ;(Thread/sleep (*(rand) 2000))
      (println "Acontece o update" pessoa)
      (update hospital departamento conj pessoa))
    (throw (ex-info "Fila esta cheia" {:tentando-adicionar pessoa}))))

(defn atende
  [hospital departamento]
  (update hospital departamento pop))

