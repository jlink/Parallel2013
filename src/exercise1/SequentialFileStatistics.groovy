package exercise1

def dataDir = new File('data')

def relevantFiles = dataDir.listFiles().findAll { file ->
    file.name.endsWith '.txt'
}

def sum = 0
def count = 0

for (File file : relevantFiles) {
    def words = file.text.tokenize()
    def wordSizes = words.collect { it.size() }
    sum += wordSizes.sum()
    count += wordSizes.size()
}

def average = sum / count
println average

assert average.toDouble().round(2) == 5.47
