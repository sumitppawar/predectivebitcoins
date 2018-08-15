Bitcoin Stat
--
### Tech Stack
```
1. scala, scalatest, scalacheck, Java8
2. PlayFramework
```
### How to run test
```
> cd predectivebitcoins
> sbt test 
```

### How to start
```
> cd predectivebitcoins
> sbt run 

//sbt run task start http server at 9000 port
```

### Prices history api 
```
http://localhost:9000/api/v1/bitcoin/usd/price/history?period=all
http://localhost:9000/api/v1/bitcoin/usd/price/history?period=week
http://localhost:9000/api/v1/bitcoin/usd/price/history?period=year
http://localhost:9000/api/v1/bitcoin/usd/price/history?startDate=2018-01-01&endDate=2018-08-15
```

### Average
```
http://localhost:9000/api/v1/bitcoin/usd/price/average?startDate=2018-01-01&endDate=2018-08-15
```

### Predict
```
http://localhost:9000/api/v1/bitcoin/usd/predict?startDate=2018-01-01&endDate=2018-08-15
```

### Assumptions 
```
1. No authentication provide
2. Must provide input in specified format
3. endDate shouldBe greater than start date
```