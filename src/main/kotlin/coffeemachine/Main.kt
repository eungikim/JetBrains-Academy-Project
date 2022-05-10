package coffeemachine

fun main() {
  val haveWater = readln().toInt()
  val haveMilk = readln().toInt()
  val haveCoffeeBean = readln().toInt()
  val needCoffee = readln().toInt()
  val canMakeCoffee = Math.min(Math.min(haveWater / 200, haveMilk / 50), haveCoffeeBean / 15)
  println(
    if (canMakeCoffee > needCoffee) {
      "Yes, I can make that amount of coffee (and even ${canMakeCoffee - needCoffee} more than that)"
    } else if (canMakeCoffee == needCoffee) {
      "Yes, I can make that amount of coffee"
    } else {
      "No, I can make only $canMakeCoffee cups of coffee"
    }
  )
}