{:opts
 {:url "http://api.openweathermap.org/data/2.5/forecast/daily",
  :method
  :get,
  :query-params
  {:q "02215",
   :mode "json",
   :cnt 1,
   :units "metric"}},
 :body "{\"cod\":\"200\",\"message\":0.0258,\"city\":{\"id\":0,\"name\":\"Boston\",\"country\":\"US\",\"coord\":{\"lat\":42.3471,\"lon\":-71.1027}},\"cnt\":1,\"list\":[{\"dt\":1431273600,\"temp\":{\"day\":20.84,\"min\":16.4,\"max\":23.73,\"night\":19.71,\"eve\":23.68,\"morn\":16.4},\"pressure\":1034.38,\"humidity\":69,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":4.36,\"deg\":219,\"clouds\":0}]}\n",
 :headers
  {:x-source "back",
   :transfer-encoding "chunked",
   :server "nginx",
   :date "Sun, 10 May 2015 11:16:57 GMT",
   :content-type "application/json; charset=utf-8",
   :connection "keep-alive",
   :access-control-allow-origin "*",
   :access-control-allow-methods "GET, POST",
   :access-control-allow-credentials "true"},
 :status 200}
