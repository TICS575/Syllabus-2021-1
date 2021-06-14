import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD

val vertexArray = Array(
  (1L, ("Alicia", 52)),
  (2L, ("Bob", 17)),
  (3L, ("Carlos", 65)),
  (4L, ("David", 24)),
  (5L, ("Eduardo", 42)),
  (6L, ("Facundo", 60))
)

val edgeArray = Array(
  Edge(1L, 2L, "follows"),
  Edge(2L, 1L, "follows"),
  Edge(1L, 4L, "follows"),
  Edge(2L, 4L, "follows"),
  Edge(5L, 4L, "follows"),
  Edge(3L, 2L, "follows"),
  Edge(2L, 3L, "follows"),
  Edge(1L, 6L, "follows"),
  Edge(6L, 3L, "follows")
)

val vertexRDD: RDD[(Long, (String, Int))] = sc.parallelize(vertexArray)
val edgeRDD: RDD[Edge[String]] = sc.parallelize(edgeArray)

val graph: Graph[(String, Int), String] = Graph(vertexRDD, edgeRDD)

// Continua el programa bajo esta línea
