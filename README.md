# ExchangeRateService
Exchange Rate Service

ServerPort: `8080`

run `./gradlew clean  build`

# Start run docker-compose up -d
# Run com.mova.currencyexchange.ExchangeRateServiceApplication class

GET http://localhost:8080/api/exchange-rates/{CODE}

GET http://localhost:8080/api/currencies

PUT http://localhost:8080/api/currencies
{
"code":"USD",
"name": "Dollar"
}
