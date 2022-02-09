package Scala


import java.util.{Scanner}
import scala.collection.mutable.ListBuffer

class Composite(val lista: ListBuffer[ListBuffer[ListBuffer[String]]], val nodex: Composite){

  var list: ListBuffer[ListBuffer[ListBuffer[String]]] = new ListBuffer[ListBuffer[ListBuffer[String]]]
  var name: String = null
  val nextnode: ListBuffer[Composite] = new ListBuffer[Composite]()
  var prevnode: Composite = null
  val teclat: Scanner = new Scanner(System.in)
  var childs: Int = 0
  var numchild: Int = 0


  if (lista != null) list = lista
  if (nodex == null) {
    System.out.println("Introduzca el nombre de este primer Dataframe: ")
    val frase: String = teclat.nextLine
    name = frase
  }
  else {
    prevnode = nodex
    System.out.println("Introduzca el nombre de este dataframe que su padre es: " + prevnode.name)
    val frase: String = teclat.nextLine
    name = prevnode.name + "/" + frase

    var node: Composite = this
    prevnode.nextnode+=this

    var childPrev = prevnode.childs
    childPrev+=1
    prevnode.childs = childPrev

    var listaux: ListBuffer[ListBuffer[ListBuffer[String]]] = null
    while ({node.prevnode != null}) {
      if (node.prevnode.list.size == 0) node.prevnode.list = this.list
      else {
        listaux = node.prevnode.list
        var y: Int = 0
        for (i <- list(1)) {
          listaux(1)+=node.list(1)(y)
          y += 1
        }
        node.prevnode.list = listaux
      }
      node = node.prevnode
    }
  }

  def at(row: Int, column: String): String = {
    list(1)(row)(posColumn(list.head.head, column))
  }

  def columns: Int = {
    list.head.head.length
  }

  def size: Int = {
    this.list(1).size
  }

  private def posColumn(lista: ListBuffer[String], column: String): Int = {
    var i = 0
    var pos = 0
    for (elem <- lista) {
      //pos = if (elem.equals(column)) pos = i
      if (elem.equals(column)) pos = i
      i += 1
    }
    pos
  }
}