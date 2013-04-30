package exercise1.solution

def dataDir = new File('data')

def relevantFiles = dataDir.listFiles().findAll {file ->
    file.name.endsWith '.txt'
}

def sumsAndCounts = relevantFiles.collect { file ->
    def words = file.text.tokenize()
    def wordSizes = words.collect { it.size() }
    [sum: wordSizes.sum(), count: wordSizes.size()]
}

def sums = sumsAndCounts.collect {it.sum}
def counts = sumsAndCounts.collect {it.count}
def sum   = sums.sum()
def count = counts.sum()

def average = sum / count
println average
assert average == 9.45
