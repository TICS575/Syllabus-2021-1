// scalastyle:off println

import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.max

import spark.implicits._

// Cargar datos
val training = spark.read.format("libsvm").load("sample_libsvm_data.txt")

val lr = new LogisticRegression()
  .setMaxIter(10)
  .setRegParam(0.3)
  .setElasticNetParam(0.8)

// Entrenando el modelo
val lrModel = lr.fit(training)

// Pesos aprendidos
val weights = lrModel.weights

// Resumen del modelo
val trainingSummary = lrModel.binarySummary

// Obtener la el objetivo por iteración
val objectiveHistory = trainingSummary.objectiveHistory
println("objectiveHistory:")
objectiveHistory.foreach(loss => println(loss))

// ROC
val roc = trainingSummary.roc
roc.show()
println(s"areaUnderROC: ${trainingSummary.areaUnderROC}")

// Buscar el threshold que maximiza el F score
val fMeasure = trainingSummary.fMeasureByThreshold
val maxFMeasure = fMeasure.select(max("F-Measure")).head().getDouble(0)
val bestThreshold = fMeasure.where($"F-Measure" === maxFMeasure)
  .select("threshold").head().getDouble(0)
