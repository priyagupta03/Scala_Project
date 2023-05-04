package functions

object Split{

    var train_data: Seq[Seq[Double]] = Seq.empty[Seq[Double]];
    var train_labels: Seq[Seq[Double]] = Seq.empty[Seq[Double]];
    var test_data: Seq[Seq[Double]] = Seq.empty[Seq[Double]];
    var test_labels: Seq[Seq[Double]] = Seq.empty[Seq[Double]];

    def train_test_split(data: Seq[Seq[Double]], labels: Seq[Seq[Double]], train_split: Float = 0.8): Unit = {
        val split_index = (train_split*data.length).toInt
        println(f"Training Samples: $split_index \n Testing Samples: ${data.length - split_index}")
        train_data = data.slice(0, split_index)
        test_data = data.slice(split_index, data.length)
        train_labels = labels.slice(0, split_index)
        test_labels = labels.slice(split_index, data.length)
    }

    def get_training_data: Seq[Seq[Double]] = {
        return train_data
    }

    def get_testing_data: Seq[Seq[Double]] = {
        return test_data
    }

    def get_training_labels: Seq[Seq[Double]] = {
        return train_labels
    }

    def get_testing_labels: Seq[Seq[Double]] = {
        return test_labels
    }


}