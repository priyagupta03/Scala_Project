import functions._
import scala.util.Random

@main def hello: Unit = {
  DataReader.read_csv("heart_disease.csv");
  DataReader.normalizeData;
  var data = DataReader.getData;
  var labels = DataReader.getLabels;
  // data = data.slice(0, 10)
  // println(f"Data: $data")
  // labels = labels.slice(0, 10)
  // println(f"Labels: $labels")

  Split.train_test_split(data, labels, train_split = 0.9)
  val train_data = Split.get_training_data
  val train_labels = Split.get_training_labels
  val test_data = Split.get_testing_data
  val test_labels = Split.get_testing_labels

  // Logistic Regression Algorithm
  val lr = LogisticRegression()
  lr.train(train_data, train_labels)
  println(f"Predicted Output: ${lr.predict(test_data)}")
  println(f"Real Output: $test_labels")
}