;;; HelloVertX - Vert.X Template Project
;;
;; Copyright 2014   bwin.party digital entertainment plc
;;                  http://www.bwinparty.com
;; Developer: Lukas Prettenthaler
;;
;;; Code:
(ns net.zyclonite.vertx.ClosureVerticle
  (:require [vertx.core :as core]
            [vertx.net :as net]
            [vertx.stream :as stream]))

(let [server (net/server)]
  (-> server
      (net/on-connect #(stream/pump % %))
      (net/listen 1236 "localhost"))

  (core/on-stop
    (.close server)))