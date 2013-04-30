package examples.collections

import static YahooService.getYearEndClosing

def tickers = "AAPL IBM ORCL MSFT".tokenize()

def top = tickers
    .collect { [ticker: it, price:getYearEndClosing(it, 2012)] }
    .max     { it.price }

println top
