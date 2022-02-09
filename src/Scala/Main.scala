package Scala
import Factory.{AssetManager, CSVLoader, JsonLoader}

import scala.collection.mutable.ListBuffer


object Main extends App{
  val assets = new AssetManager
  assets.addLoader(new JsonLoader, "json")
  assets.addLoader(new CSVLoader, "csv")
  assets.addLoader(new CSVLoader, "txt")

  val list1 = new Composite(null,null)
  val list2 = new Composite(null,list1)
  var list = new ListBuffer[String]
  //Da fallo y no se como arreglar
  //list = assets.load("cities.csv")
  val list3 = new Composite(assets.load("cities.csv"), list2)
  //val list4 = new Composite(assets.load("cities.json"), list2)

  //val list5 = new Composite(assets.load("cities.csv"), list4)
  //val list6 = new Composite(assets.load("cities.txt"), list4)

  list3.size
  //list2.size
}
