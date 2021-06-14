import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import scala.math._

// Programa en GraphX para calcular el máximo número en un grafo con Pregel

// Definimoos los verticas como un id (tipo long) y un número
val vertices: RDD[(VertexId, Int)] =
    sc.parallelize(Array(
      (1L, 3),
      (2L, 6),
      (3L, 2),
      (4L, 1),
      (5L, 10)
    )
)

// Definimos las aristas
val relationships: RDD[Edge[Boolean]] =
  sc.parallelize(Array(
    Edge(1L, 2L, true),
    Edge(2L, 1L, true),
    Edge(2L, 4L, true),
    Edge(4L, 2L, true),
    Edge(3L, 2L, true),
    Edge(2L, 3L, true),
    Edge(3L, 4L, true),
    Edge(4L, 3L, true),
    Edge(1L, 5L, true),
    Edge(5L, 1L, true)
  )
)

// Creamos el grafo y lo preprocesamos para cambiar el número por una tupla
// La tupla representara (valor actual, valor iteración anterior)
val graph = Graph(vertices, relationships)
val initial_graph = graph.mapVertices((id, attr) => (attr, -1))

// Imprimimos los vértices
initial_graph.vertices.collect.foreach(println)

// Mensaje inicial
val initialMsg = 0

// Vertex Program: recibe un id de nodo, un nodo que es un par (int, int),
// recibe un mensaje que es un int, y actualiza los nodos a un par (int, int)
def vprog(vertexId: VertexId, value: (Int, Int), message: Int): (Int, Int) = {
  // Si el mensaje es el inicial no hacemos nada
  if (message == initialMsg) {
    value
  }
  // Movemos el valor actual al valor de la iteración anterior
  // Nuestro valor actual es el más grande entre el mensaje recibido y mi valor actual
  else {
    (max(message, value._1), value._1)
  }
}

// SendMessage: recibe un triplet en donde los nodos son (int, int) y las aristas son
// de tipo boolean, y emitimos un mensaje con un id de nodo y el mensaje que es un int
def sendMsg(triplet: EdgeTriplet[(Int, Int), Boolean]): Iterator[(VertexId, Int)] = {
  // Tomamos el sourceVertex del triplet
  val sourceVertex = triplet.srcAttr

  // Si nuestro número en la iteración pasada es el mismo que en esta iteración no hacemos nada
  if (sourceVertex._1 == sourceVertex._2) {
    Iterator.empty
  }
  // En otro caso, emitimos nuestro valor actual hacia el nodo de destino
  else {
    Iterator((triplet.dstId, sourceVertex._1))
  }
}

// mergeMessage: cuando recibimos más de un mensaje, hacemos reduce con la función max
def mergeMsg(msg1: Int, msg2: Int): Int = max(msg1, msg2)

// Ejecutamos la función pregel y guardamos el valor
// Los argumentos son el mensaje inicial, el número de iteraciones, la dirección a considerar
// y las tres funciones definidas más arriba
val maxGraph = initial_graph.pregel(initialMsg,
  Int.MaxValue,
  EdgeDirection.Out)(
  vprog,
  sendMsg,
  mergeMsg
)

// Imprimimos el grafo resultante
maxGraph.vertices.collect.foreach(v => println(s"${v}"))
