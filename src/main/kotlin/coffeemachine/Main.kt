package coffeemachine


var haveWater = 400
var haveMilk = 540
var haveCoffeeBean = 120
var haveDpCup = 9
var haveMoney = 550

fun main() {
  printState()

  val operation = readLine()!!
  when(operation) {
    "buy" -> makeCoffee()
    "fill" -> fillResource()
    "take" -> takeMoney()
  }

  printState()
}

fun printState() {
  println("The coffee machine has:")
  println("$haveWater ml of water")
  println("$haveMilk ml of milk")
  println("$haveCoffeeBean g of coffee beans")
  println("$haveDpCup disposable cups")
  println("\$$haveMoney of money")
}

fun makeCoffee() {
  val selectedMenu = readLine()!!.toInt()
  when (selectedMenu) {
    1 -> {
      haveWater -= 250
      haveCoffeeBean -= 16
      haveDpCup--
      haveMoney += 4
    }
    2 -> {
      haveWater -= 350
      haveMilk -= 75
      haveCoffeeBean -= 20
      haveDpCup--
      haveMoney += 7
    }
    3 -> {
      haveWater -= 200
      haveMilk -= 100
      haveCoffeeBean -= 12
      haveDpCup--
      haveMoney += 6
    }
  }
}

fun fillResource() {
  haveWater += readLine()!!.toInt()
  haveMilk += readLine()!!.toInt()
  haveCoffeeBean += readLine()!!.toInt()
  haveDpCup += readLine()!!.toInt()
}

fun takeMoney() {
  println("I gave you \$$haveMoney")
  haveMoney = 0
}