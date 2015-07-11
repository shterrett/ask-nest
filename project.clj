(defproject ask-nest "0.1.0-SNAPSHOT"
  :description "Library for querying values of NEST thermostat"
  :url "http://github.com/shterrett/ask-nest"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [
                 [com.taoensso/timbre "3.4.0"]
                 [compojure "1.3.4"]
                 [environ "1.0.0"]
                 [http-kit "2.1.16"]
                 [http-kit.fake "0.2.1"]
                 [kerodon "0.6.1"]
                 [metosin/ring-middleware-format "0.6.0"]
                 [metosin/ring-http-response "0.6.2"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/data.xml "0.0.8"]
                 [org.clojure/data.zip "0.1.1"]
                 [org.postgresql/postgresql "9.4-1200-jdbc41"]
                 [prone "0.8.2"]
                 [ragtime "0.4.2"]
                 [ring "1.4.0-RC1"]
                 [ring/ring-anti-forgery "1.0.0"]
                 [ring/ring-defaults "0.1.5"]
                 [ring/ring-session-timeout "0.1.0"]
                 [selmer "0.8.2"]
                 [yesql "0.4.0"]
                 ]
  :plugins [[lein-environ "1.0.0"]
            [lein-ring "0.9.4"]]

  :main ask-nest.core

  :ring {:handler ask-nest.handler/app
         :init    ask-nest.handler/init
         :destroy ask-nest.handler/destroy
         :uberwar-name "ask_nest.war"}
)
