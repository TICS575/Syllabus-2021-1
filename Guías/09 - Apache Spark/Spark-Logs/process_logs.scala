val textFile = sc.textFile("logs.txt")

// Creamos un DataFrame de una columna
val df = textFile.toDF("line")
val errors = df.filter(col("line").like("%Error%"))

// Contamos los errores
errors.count()

// Contando errores del servidor
errors.filter(col("line").like("%servidor%")).count()

// Entregando los errores del servidor
errors.filter(col("line").like("%servidor%")).collect()
