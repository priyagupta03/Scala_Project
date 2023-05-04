// package functions

// import breeze.linalg._
// import breeze.stats._
// import breeze.plot._

// object Plots{
//     def histogram: Unit = {
//         create histograms of each column
//         for (j <- 0 until numCols) {
//         val hist = breeze.stats.hist(data(::, j), 10)
//         val fig = Figure()
//         val plt = fig.subplot(0)
//         plt += hist
//         plt.xlabel = s"Column $j"
//         fig.saveas(f"Column $j.png")
//         fig.refresh()
//         }
// }

// // // create scatter plots of each pair of columns
// // for (j <- 0 until numCols) {
// //   for (k <- j+1 until numCols) {
// //     val fig = Figure()
// //     val plt = fig.subplot(0)
// //     plt += plot(data(::, j), data(::, k), '.')
// //     plt.xlabel = s"Column $j"
// //     plt.ylabel = s"Column $k"
// //     fig.refresh()
// //   }
// // }
