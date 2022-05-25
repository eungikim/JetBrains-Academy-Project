package coffeemachine

fun main() {
  val coffeeMachine = CoffeeMachine()
  do {
    coffeeMachine.insertInput(readln())
  } while (!coffeeMachine.isTerminate)
}

class CoffeeMachine(
  var water: Int = 400,
  var milk: Int = 540,
  var coffeeBean: Int = 120,
  var dpCup: Int = 9,
  var money: Int = 550
) {
  var state: Operation = Operation.STAY
  var fillResourcePhase = 4
  val isTerminate
    get() = state == Operation.EXIT

  fun insertInput(input: String) {
    if (input.isEmpty()) return
    state = when (state) {
      Operation.STAY -> insertOperation(input)
      Operation.BUY -> insertCoffeeMenu(input)
      Operation.FILL -> insertResource(input)
      else -> Operation.STAY
    }
  }

  private fun insertOperation(operation: String): Operation {
    return when (Operation.get(operation)) {
      Operation.BUY -> Operation.BUY
      Operation.FILL -> Operation.FILL
      Operation.TAKE -> {
        takeMoney()
        Operation.STAY
      }
      Operation.REMAINING -> {
        printState()
        Operation.STAY
      }
      Operation.EXIT -> Operation.EXIT
      else -> Operation.STAY
    }
  }

  private fun insertCoffeeMenu(menuInput: String): Operation {
    if (Operation.BACK == Operation.get(menuInput))
      return Operation.STAY

    try {
      val menu = menuInput.toInt()
      val coffee = selectCoffee(menu)
      if (coffee != null) {
        makeCoffee(coffee.water, coffee.milk, coffee.coffeeBean, coffee.money)
      }
    } catch (_: NumberFormatException) { }
    return Operation.STAY
  }

  private fun insertResource(ingredientInput: String): Operation {
    try {
      val ingredient = ingredientInput.toInt()
      fillResource(fillResourcePhase, ingredient)
      fillResourcePhase--
      if (fillResourcePhase <= 0) {
        fillResourcePhase = 4
        return Operation.STAY
      }
    } catch (_: java.lang.NumberFormatException) { }
    return Operation.FILL
  }

  private fun printState() {
    println("The coffee machine has:")
    println("$water ml of water")
    println("$milk ml of milk")
    println("$coffeeBean g of coffee beans")
    println("$dpCup disposable cups")
    println("\$$money of money")
  }

  private fun selectCoffee(menuId: Int): Coffee? = Coffee.findByMenuId(menuId)

  private fun makeCoffee(water: Int, milk: Int, coffeeBean: Int, money: Int) {
    if (this.water < water) {
      println("Sorry, not enough water!")
    } else if (this.milk < milk) {
      println("Sorry, not enough milk!")
    } else if (this.coffeeBean < coffeeBean) {
      println("Sorry, not enough disposable cups!")
    } else if (this.dpCup < 1) {
      println("Sorry, not enough disposable cups!")
    } else {
      this.water -= water
      this.milk -= milk
      this.coffeeBean -= coffeeBean
      this.dpCup--
      this.money += money
      println("I have enough resources, making you a coffee!")
    }
  }

  private fun fillResource(phase: Int, ingredient: Int) {
    when (phase) {
      4 -> water += ingredient
      3 -> milk += ingredient
      2 -> coffeeBean += ingredient
      1 -> dpCup += ingredient
    }
  }

  private fun takeMoney() {
    println("I gave you \$$money")
    money = 0
  }

  enum class Operation(val str: String) {
    STAY("stay"),
    BUY("buy"),
    FILL("fill"),
    TAKE("take"),
    REMAINING("remaining"),
    EXIT("exit"),
    BACK("back");

    companion object {
      fun get(str: String): Operation? {
        for (e in Operation.values()) {
          if (e.str == str) return e
        }
        return null
      }
    }
  }

  enum class Coffee(val menuId: Int, val water: Int, val milk: Int, val coffeeBean: Int, val money: Int) {
    ESPRESSO(1, water = 250, milk = 0, coffeeBean = 16, money = 4),
    LATTE(2, water = 350, milk = 75, coffeeBean = 20, money = 7),
    CAPPUCCINO(3, water = 200, milk = 100, coffeeBean = 12, money = 6);

    companion object {
      fun findByMenuId(id: Int): Coffee? {
        for (e in Coffee.values()) {
          if (id == e.menuId) return e
        }
        return null
      }
    }
  }

}
