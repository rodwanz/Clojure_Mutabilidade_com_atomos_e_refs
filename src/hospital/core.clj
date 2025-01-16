(ns hospital.core
  (:use [clojure.pprint])
  (:require [hospital.model :as h.model]))

(let [hospital-um (h.model/novo-hospital)]
  (pprint hospital-um))
(pprint h.model/fila-vazia)


