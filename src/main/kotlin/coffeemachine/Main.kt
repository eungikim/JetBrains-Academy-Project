package coffeemachine

fun main() {
  val coffeeMachine = CoffeeMachine()
  do {
    coffeeMachine.listenInput()
  } while (!coffeeMachine.isTerminate)
}

class CoffeeMachine(
  private var water: Int = 400,
  private var milk: Int = 540,
  private var coffeeBean: Int = 120,
  private var dpCup: Int = 9,
  private var money: Int = 550
) {
  var state: StateMode = StateMode.STANDBY
  val isTerminate
    get() = state == StateMode.EXIT

  fun listenInput() {
    print(state.msg)
    val input = readln()
    if (input.isEmpty()) return
    state = when (state) {
      StateMode.STANDBY -> insertOperation(input)
      StateMode.ORDER_COFFEE -> insertCoffeeMenu(input)
      StateMode.FILL_WATER,
      StateMode.FILL_MILK,
      StateMode.FILL_COFFEE_BEAN,
      StateMode.FILL_CUP -> insertResource(input, state)
      else -> StateMode.STANDBY
    }
    println()
  }

  private fun insertOperation(operation: String): StateMode {
    return when (Operation.valueOf(operation)) {
      Operation.buy -> StateMode.ORDER_COFFEE
      Operation.fill -> StateMode.FILL_WATER
      Operation.take -> {
        takeMoney()
        StateMode.STANDBY
      }
      Operation.remaining -> {
        printState()
        StateMode.STANDBY
      }
      Operation.exit -> StateMode.EXIT
      else -> StateMode.STANDBY
    }
  }

  private fun insertCoffeeMenu(menuInput: String): StateMode {
    if (Operation.back.name == menuInput)
      return StateMode.STANDBY

    try {
      val menu = menuInput.toInt()
      val coffee = selectCoffee(menu)
      if (coffee != null) {
        makeCoffee(coffee.water, coffee.milk, coffee.coffeeBean, coffee.money)
      }
    } catch (_: NumberFormatException) { }
    return StateMode.STANDBY
  }

  private fun insertResource(ingredientInput: String, phase: StateMode): StateMode {
    try {
      val ingredient = ingredientInput.toInt()
      fillResource(phase, ingredient)
      when (phase) {
        StateMode.FILL_WATER -> return StateMode.FILL_MILK
        StateMode.FILL_MILK -> return StateMode.FILL_COFFEE_BEAN
        StateMode.FILL_COFFEE_BEAN -> return StateMode.FILL_CUP
        StateMode.FILL_CUP -> return StateMode.STANDBY
        else -> {}
      }
    } catch (_: java.lang.NumberFormatException) { }
    return StateMode.STANDBY
  }

  private fun printState() {
    println()
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
      println("Sorry, not enough coffee beans!")
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

  private fun fillResource(phase: StateMode, ingredient: Int) {
    when (phase) {
      StateMode.FILL_WATER -> water += ingredient
      StateMode.FILL_MILK -> milk += ingredient
      StateMode.FILL_COFFEE_BEAN -> coffeeBean += ingredient
      StateMode.FILL_CUP -> dpCup += ingredient
      else -> {}
    }
  }

  private fun takeMoney() {
    println("I gave you \$$money")
    money = 0
  }

  enum class Operation {
    buy, fill, take, remaining, exit, back;
  }

  enum class StateMode(val msg: String) {
    STANDBY("Write action (buy, fill, take, remaining, exit): "),
    ORDER_COFFEE("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: "),
    FILL_WATER("Write how many ml of water do you want to add: "),
    FILL_MILK("Write how many ml of milk do you want to add: "),
    FILL_COFFEE_BEAN("Write how many grams of coffee beans do you want to add: "),
    FILL_CUP("Write how many disposable cups of coffee do you want to add: "),
    EXIT(""),
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
