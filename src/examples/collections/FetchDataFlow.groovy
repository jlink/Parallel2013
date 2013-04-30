package examples.collections

import groovyx.gpars.dataflow.Dataflows
import static groovyx.gpars.dataflow.Dataflow.task

import static YahooService.getYearEndClosing

def tickers = "AAPL IBM ORCL MSFT".tokenize()

def prices = new Dataflows()

tickers.each { ticker ->
    task {
        prices[ticker] = getYearEndClosing(ticker, 2012)
    }
}
def top = tickers.max { symbol -> prices[symbol] }
println "$top ${prices[top]}"

