package coffeemachine


var haveWater = 400
var haveMilk = 540
var haveCoffeeBean = 120
var haveDpCup = 9
var haveMoney = 550

const val OPERATION_BUY = "buy"
const val OPERATION_FILL = "fill"
const val OPERATION_TAKE = "take"
const val OPERATION_REMAINING = "remaining"
const val OPERATION_EXIT = "exit"
const val OPERATION_BACK = "back"
fun main() {

  while (true) {
    val operation = readLine()!!
    when (operation) {
      OPERATION_BUY -> makeCoffee()
      OPERATION_FILL -> fillResource()
      OPERATION_TAKE -> takeMoney()
      OPERATION_REMAINING -> printState()
      OPERATION_EXIT -> break
    }
  }

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
  val selectedMenu = readLine()!!
  if (OPERATION_BACK == selectedMenu) return

  when (selectedMenu.toInt()) {
    1 -> {
      if (haveWater < 250) {
        println("Sorry, not enough water!")
      } else if (haveCoffeeBean < 16) {
        println("Sorry, not enough coffee beans!")
      } else if (haveDpCup < 1) {
        println("Sorry, not enough disposable cups!")
      } else {
        haveWater -= 250
        haveCoffeeBean -= 16
        haveDpCup--
        haveMoney += 4
        println("I have enough resources, making you a coffee!")
      }
    }
    2 -> {
      if (haveWater < 350) {
        println("Sorry, not enough water!")
      } else if (haveMilk < 75) {
        println("Sorry, not enough milk!")
      } else if (haveCoffeeBean < 20) {
        println("Sorry, not enough coffee beans!")
      } else if (haveDpCup < 1) {
        println("Sorry, not enough disposable cups!")
      } else {
        haveWater -= 350
        haveMilk -= 75
        haveCoffeeBean -= 20
        haveDpCup--
        haveMoney += 7
        println("I have enough resources, making you a coffee!")
      }
    }
    3 -> {
      if (haveWater < 200) {
        println("Sorry, not enough water!")
      } else if (haveMilk < 100) {
        println("Sorry, not enough milk!")
      } else if (haveCoffeeBean < 12) {
        println("Sorry, not enough coffee beans!")
      } else if (haveDpCup < 1) {
        println("Sorry, not enough disposable cups!")
      } else {
        haveWater -= 200
        haveMilk -= 100
        haveCoffeeBean -= 12
        haveDpCup--
        haveMoney += 6
        println("I have enough resources, making you a coffee!")
      }
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