package coffeemachine

fun main() {
  val howManyCoffee = readln().toInt()
  println("For $howManyCoffee cups of coffee you will need:")
  println("${howManyCoffee * 200} ml of water")
  println("${howManyCoffee * 50} ml of milk")
  println("${howManyCoffee * 15} g of coffee beans")
}