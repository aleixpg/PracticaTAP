package Scala

trait DataFrameInterfaceScala {
  def at(row: Int, column: String): String

  def columns: Int

  def size: Int
}
