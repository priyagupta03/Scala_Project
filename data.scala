package functions

object DataReader{
    var data: Seq[Seq[Double]] = Seq.empty[Seq[Double]];
    var label: Seq[Seq[Double]] = Seq.empty[Seq[Double]];
    
    def read_csv(filename:String): Unit = {
        val bufferedSource = io.Source.fromFile(filename)
        for (line <- bufferedSource.getLines) {
            try{
                val cols = line.split(",").toSeq.map(_.trim).map(_.toDouble)
                val firstHalf = cols.slice(0, cols.length-1)
                val secondHalf = cols.slice(cols.length-1, cols.length)
                data = data :+ firstHalf
                label = label :+ secondHalf
            } catch{
                 case e: Exception => ""
            }
        }
        bufferedSource.close
    }

    def normalizeData: Unit = {
        val normalizeCol = data.transpose.zipWithIndex.filter { case (column, index) => column.exists(_ > 1.0)}.map(_._2)
        data = data.transpose.zipWithIndex.map { case (column, index) =>
            if (normalizeCol.contains(index)) {
            val max = column.max
            val min = column.min
            column.map(value => (value - min) / (max - min))
            } else {
            column
            }
        }.transpose
    }

    def getData: Seq[Seq[Double]] = {
        return data;
    };
    def getLabels: Seq[Seq[Double]] ={
        return label;
    };
}