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


(defn chega-em-pausado
  "FUNÇÃO QUE PARECE PURA MAIS USA RANDOM"
  [hospital departamento pessoa]
  (Thread/sleep (*(rand) 2000))
  (if (cabe-na-fila? hospital departamento)
    (do                                                     ;
       ;(Thread/sleep (*(rand) 2000))
      (update hospital departamento conj pessoa))
    (throw (ex-info "Fila esta cheia" {:tentando-adicionar pessoa}))))


(defn chega-em-pausado-logando
  "FUNÇÃO QUE PARECE PURA MAIS USA RANDOM E ALTERA ESTADO DO RANDOM E LOGA"
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

(defn proxima
  "Retorna o próximo paciente da fila"
  [hospital departamento]
  (-> hospital
      departamento
      peek))

(defn transfere
  "Transfere o próximo paciente da fila de espera para a fila do laboratorio"
  [hospital de para]
  (let [pessoa (proxima hospital de)]
    (-> hospital
        (atende de)
        (chega-em para pessoa))))



