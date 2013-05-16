package exercise1

import java.math.RoundingMode

def dataDir = new File('data')

def relevantFiles = dataDir.listFiles().findAll {file ->
    file.name.endsWith '.txt'
}

def sum   = 0
def count = 0

for(File file : relevantFiles) {
    def words = file.text.tokenize()
    def wordSizes = words.collect { it.size() }
    sum += wordSizes.sum()
    count += wordSizes.size()
}

def average = sum / count
println average

assert average.setScale(2, RoundingMode.DOWN) == 5.46
