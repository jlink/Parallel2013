package exercise1.solution

import groovyx.gpars.GParsPool

import java.math.RoundingMode

def dataDir = new File('data')

GParsPool.withPool {

    def relevantFiles = dataDir.listFiles().findAllParallel {file ->
        file.name.endsWith '.txt'
    }

    def sumsAndCounts = relevantFiles.collectParallel { file ->
        def words = file.text.tokenize()
        def wordSizes = words.collect { it.size() }
        [sum: wordSizes.sum(), count: wordSizes.size()]
    }

    def sums = sumsAndCounts.collectParallel {it.sum}
    def counts = sumsAndCounts.collectParallel {it.count}
    def sum   = sums.sumParallel()
    def count = counts.sumParallel()

    def average = sum / count
    println average

    assert average.setScale(2, RoundingMode.DOWN) == 5.46
}

