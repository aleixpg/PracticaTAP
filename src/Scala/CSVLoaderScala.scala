package Scala

import com.opencsv.CSVReader

import java.io.{File, FileReader}
import java.util
import java.util.{ArrayList, List}

class CSVLoaderScala {
  def readCSVFile(file: File): Array[Array[String]] = {

    val rows = Array.ofDim[String](64, 256)
    val bufferedSource = io.Source.fromFile(file)
    var count = 0
    for (line <- bufferedSource.getLines) {
      rows(count) = line.split(",").map(_.trim)
      count += 1
    }
    bufferedSource.close

    rows
  }
}
