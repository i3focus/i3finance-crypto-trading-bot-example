# I3finance Crypto Trading Bot Example

Simple example of trading bot using Cassandre (https://trading-bot.cassandre.tech/cassandre_basics/overview.html) project as a springboot starter.

This bot will have a simple strategy and the information about events will be published on a Telegram bot.

### Getting data for backtest

```shell
startDate=`date --date="3 months ago" +"%s"`
endDate=`date +"%s"`
curl -s "https://api.kucoin.com/api/v1/market/candles?type=1day&symbol=BTC-USDT&startAt=${startDate}&endAt=${endDate}" \
| jq -r -c ".data[] | @tsv" \
| tac $1 > tickers-btc-usdt.tsv
```

### Reference

* [Cassandre Documentation](https://trading-bot.cassandre.tech/cassandre_basics/overview.html)
* [Baeldung Cassandre SpringBoot Trading Bot](https://www.baeldung.com/cassandre-spring-boot-trading-bot)

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.3/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.3/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.7.3/reference/htmlsingle/#using.devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.3/reference/htmlsingle/#web)
* [Prometheus](https://docs.spring.io/spring-boot/docs/2.7.3/reference/htmlsingle/#actuator.metrics.export.prometheus)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.7.3/reference/htmlsingle/#actuator)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)