# ExchangeRateService
Exchange Rate Service

ServerPort: `8080`


an Issue with gradle version and test, compatibility
run `gradle  build -x test`

# Start run docker-compose up -d
# Run com.mova.currencyexchange.ExchangeRateServiceApplication class

GET http://localhost:8080/api/exchange-rates/{CODE}

GET http://localhost:8080/api/currencies/list

PUT http://localhost:8080/api/currencies
{
"code":"USD",
"name": "Dollar"
}

# http://data.fixer.io/api/latest?access_key=052a4123126083f9c812cf977d9af655&base=USD
`
{
"success": false,
"error": {
"code": 105,
"type": "base_currency_access_restricted"
}
}
`


