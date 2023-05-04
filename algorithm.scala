package functions

import scala.util.Random

class LogisticRegression {

  var weights: Seq[Double] = Seq.empty[Double];
  var bias: Seq[Double] = Seq.empty[Double];

  def sigmoid(x: Double): Seq[Double] = {
    return Seq(1.0 / (1.0 + Math.exp(-x)))
  }

  def loss_fn(y:Seq[Seq[Double]], y_hat:Seq[Seq[Double]]): Seq[Double] = {
    y.zip(y_hat).map{ case (val1, val2) => val1(0) - val2(0)}.toSeq
  }

  def gradient(x: Seq[Seq[Double]], y: Seq[Seq[Double]], y_hat: Seq[Seq[Double]]): Tuple = {
    // Loss Calculation
    val loss_val = loss_fn(y, y_hat)
    val loss = loss_val.sum / y.length
    println(f"Loss: $loss")
    val grad_w = x.transpose.map(row => dotProduct(row, loss_val)/y.length)
    // println(f"Weight Gradient: $grad_w")
    return (grad_w, loss)
  }

  def dotProduct(seq1: Seq[Double], seq2: Seq[Double]): Double = {
    seq1.zip(seq2).map { case (x, y) => x * y }.sum
    }

  def train(x: Seq[Seq[Double]], y_hat: Seq[Seq[Double]], num_iter: Int  = 10, lr: Double = 0.01): Unit = {
    val random = new Random()
    val col = x(0).length
    weights = Seq.fill(col)(random.nextDouble())
    println(f"Weights: $weights")
    bias = Seq.fill(1)(random.nextDouble())
    println(f"Bias: $bias")

    for (i <- 0 until num_iter){
      print(f"Epoch: ${i+1}/$num_iter\t=>\t")
      // Forward calculation
      var y = x.map(row => dotProduct(row, weights))
      y = y.map(val1 => val1 - bias(0))
      val y_output = y.map(row => sigmoid(row))
      // println(f"Output: $y_output")

      // gradient calculation
      val (grad_w, loss) = gradient(x, y_output, y_hat)
      val grad_w1 = grad_w.asInstanceOf[Seq[Double]]
      val loss1 = loss.asInstanceOf[Double]
      weights = weights.zip(grad_w1).map{case (w, grad) => w - lr*grad}
      bias = bias.map(b => b - lr*loss1)
    }  
  }

  def predict(x: Seq[Seq[Double]]): Seq[Seq[Double]] = {
    var y = x.map(row => dotProduct(row, weights))
    y = y.map(val1 => val1 - bias(0))
    return y.map(row => sigmoid(row))
  }
}
