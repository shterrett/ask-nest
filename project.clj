(defproject ask-nest "0.1.0-SNAPSHOT"
  :description "Library for querying values of NEST thermostat"
  :url "http://github.com/shterrett/ask-nest"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [http-kit "2.1.16"]
                 [http-kit.fake "0.2.1"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/data.xml "0.0.8"]
                 [org.clojure/data.zip "0.1.1"]
                 [org.postgresql/postgresql "9.4-1200-jdbc41"]
                 [yesql "0.4.0"]
                 [ragtime "0.3.8"]
                 [ragtime/ragtime.sql.files "0.3.7"]
                 [environ "1.0.0"]]
  :plugins [[lein-environ "1.0.0"]
            [ragtime/ragtime.lein "0.3.8"]]
)
