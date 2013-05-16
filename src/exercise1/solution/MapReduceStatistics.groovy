package exercise1.solution

import groovyx.gpars.GParsPool

def dataDir = new File('data')

GParsPool.withPool {
    def sumsAndCounts = dataDir.listFiles().parallel
        .filter {file -> file.name.endsWith '.txt' }
        .map { file ->
            def words = file.text.tokenize()
            def wordSizes = words.collect { it.size() }
            [sum: wordSizes.sum(), count: wordSizes.size()]
        }
        .reduce { left, right ->
            [sum: left.sum + right.sum, count: left.count + right.count]
        }

    BigDecimal average = sumsAndCounts.sum / sumsAndCounts.count
    println average

    assert average.toDouble().round(2) == 5.47
}

