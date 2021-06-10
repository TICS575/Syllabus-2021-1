// Este import nos sirve para la notación $-notation
import spark.implicits._

// Importamos los datos
val df = spark.read.json("data.json")

// Mostramos el dataset
df.show()

// Imprimimos el esquema
df.printSchema()
// root
// |-- age: long (nullable = true)
// |-- name: string (nullable = true)

// Seleccionar solamente el nombre
df.select("name").show()
// +-------+
// |   name|
// +-------+
// |Michael|
// |   Andy|
// | Justin|
// +-------+

// Seleccionar la base de datos, pero con edad aumentada en 1
df.select($"name", $"age" + 1).show()
// +-------+---------+
// |   name|(age + 1)|
// +-------+---------+
// |Michael|     null|
// |   Andy|       31|
// | Justin|       20|
// +-------+---------+

// Personas mayores que 21
df.filter($"age" > 21).show()
// +---+----+
// |age|name|
// +---+----+
// | 30|Andy|
// +---+----+

// Contar personas por edad
df.groupBy("age").count().show()
// +----+-----+
// | age|count|
// +----+-----+
// |  19|    1|
// |null|    1|
// |  30|    1|
// +----+-----+

// Registramos el DataFrame como una vista SQL
df.createOrReplaceTempView("people")

// Consultamos el DF con SQL
val sqlDF = spark.sql("SELECT * FROM people")
sqlDF.show()
// +----+-------+
// | age|   name|
// +----+-------+
// |null|Michael|
// |  30|   Andy|
// |  19| Justin|
// +----+-------+
