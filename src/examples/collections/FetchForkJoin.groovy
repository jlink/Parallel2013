package examples.collections

import static groovyx.gpars.GParsPool.withPool
import static YahooService.getYearEndClosing

def tickers = "AAPL IBM ORCL MSFT".tokenize()

withPool {
    def top = tickers
        .collectParallel { [ticker: it, price: getYearEndClosing(it, 2012)] }
        .maxParallel { it.price }
    println top
}