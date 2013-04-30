package examples.collections

import static YahooService.getYearEndClosing
import static groovyx.gpars.GParsPool.withPool

def tickers = "AAPL IBM ORCL MSFT".tokenize()

withPool {
    def top = tickers.parallel
        .map { [ticker: it, price: getYearEndClosing(it, 2012)] }
        .max { it.price }
    println top
}