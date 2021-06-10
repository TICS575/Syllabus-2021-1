val textFile = sc.textFile("/Users/adriansotosuarez/Desktop/example.txt")
val counts = textFile.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey((a, b) => a + b)
counts.saveAsTextFile("/Users/adriansotosuarez/Desktop/example-output")

