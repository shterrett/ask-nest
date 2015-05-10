(ns ask-nest.weather-codes)

(def category-codes
  {200 "thunderstorm"
   300 "drizzle"
   500 "rain"
   600 "snow"
   700 "atmosphere"
   800 "clouds"
   900 "extreme"
   950 "additional"
   960 "additional"})

(def detail-codes
  {200 ["thunderstorm with light rain" "11d.png"]
   201 ["thunderstorm with rain" "11d.png"]
   202 ["thunderstorm with heavy rain" "11d.png"]
   210 ["light thunderstorm" "11d.png"]
   211 ["thunderstorm" "11d.png"]
   212 ["heavy thunderstorm" "11d.png"]
   221 ["ragged thunderstorm" "11d.png"]
   230 ["thunderstorm with light drizzle" "11d.png"]
   231 ["thunderstorm with drizzle" "11d.png"]
   232 ["thunderstorm with heavy drizzle" "11d.png"]
   300 ["light intensity drizzle" "09d.png"]
   301 ["drizzle" "09d.png"]
   302 ["heavy intensity drizzle" "09d.png"]
   310 ["light intensity drizzle rain" "09d.png"]
   311 ["drizzle rain" "09d.png"]
   312 ["heavy intensity drizzle rain" "09d.png"]
   313 ["shower rain and drizzle" "09d.png"]
   314 ["heavy shower rain and drizzle" "09d.png"]
   321 ["shower drizzle" "09d.png"]
   500 ["light rain" "10d.png"]
   501 ["moderate rain" "10d.png"]
   502 ["heavy intensity rain" "10d.png"]
   503 ["very heavy rain" "10d.png"]
   504 ["extreme rain" "10d.png"]
   511 ["freezing rain" "13d.png"]
   520 ["light intensity shower rain" "09d.png"]
   521 ["shower rain" "09d.png"]
   522 ["heavy intensity shower rain" "09d.png"]
   531 ["ragged shower rain" "09d.png"]
   600 ["light snow" "13d.png"]
   601 ["snow" "13d.png"]
   602 ["heavy snow" "13d.png"]
   611 ["sleet" "13d.png"]
   612 ["shower sleet" "13d.png"]
   615 ["light rain and snow" "13d.png"]
   616 ["rain and snow" "13d.png"]
   620 ["light shower snow" "13d.png"]
   621 ["shower snow" "13d.png"]
   622 ["heavy shower snow" "13d.png"]
   701 ["mist" "50d.png"]
   711 ["smoke" "50d.png"]
   721 ["haze" "50d.png"]
   731 ["sand, dust whirls" "50d.png"]
   741 ["fog" "50d.png"]
   751 ["sand" "50d.png"]
   761 ["dust" "50d.png"]
   762 ["volcanic ash" "50d.png"]
   771 ["squalls" "50d.png"]
   781 ["tornado" "50d.png"]
   800 ["clear sky" "01d.png" "01n.png"]
   801 ["few clouds" "02d.png" "02n.png"]
   802 ["scattered clouds" "03d.png" "03d.png"]
   803 ["broken clouds" "04d.png" "03d.png"]
   804 ["overcast clouds" "04d.png" "04d.png"]
   900 ["tornado"]
   901 ["tropical storm"]
   902 ["hurricane"]
   903 ["cold"]
   904 ["hot"]
   905 ["windy"]
   906 ["hail"]
   951 ["calm"]
   952 ["light breeze"]
   953 ["gentle breeze"]
   954 ["moderate breeze"]
   955 ["fresh breeze"]
   956 ["strong breeze"]
   957 ["high wind, near gale"]
   958 ["gale"]
   959 ["severe gale"]
   960 ["storm"]
   961 ["violent storm"]
   962 ["hurricane"]})

(def image-url "http://openweathermap.org/img/w/")

(defn detail-description [code]
  (first (get detail-codes code)))

(defn images [code]
  (map #(str image-url %)
       (rest (get detail-codes code))))

(defn category [code]
  (let [base-code (* 100 (quot code 100))]
    (if (< base-code 900)
      (get category-codes base-code)
      (get category-codes (* 10 (quot code 10))))))
