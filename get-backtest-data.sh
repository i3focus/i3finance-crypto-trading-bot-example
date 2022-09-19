#!/bin/bash

# shellcheck disable=SC2006
startDate=`date --date="3 months ago" +"%s"`
endDate=`date +"%s"`
curl -s "https://api.kucoin.com/api/v1/market/candles?type=1day&symbol=BTC-USDT&startAt=${startDate}&endAt=${endDate}" \
| jq -r -c ".data[] | @tsv" \
| tac "$1" > tickers-btc-usdt.tsv