(ns foo.core
  (:require [replicant.dom :as d]))

(defonce st (atom false))

(defn handler [{:replicant/keys [life-cycle]} [k]]
  (case k
    :event/toggle (swap! st not)
    :event/on-render (println "LIFE CYCLE:" life-cycle)))

(defn inner []
  [:div {:replicant/on-render [:event/on-render]}
   "INNER"])

(defn outer [st]
  [:div
   [:button {:on {:click [:event/toggle]}} "TOGGLE"]
   (when st
     [:div
      [:ui/inner]])])

(defn render []
  (d/render
    (js/document.getElementById "app")
    (outer @st)
    {:aliases {:ui/inner inner}}))

(defn main []
  (d/set-dispatch! handler)
  (render)
  (add-watch st :update render))
