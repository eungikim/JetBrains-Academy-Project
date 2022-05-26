package cryptography

fun main() {
  val insertOperationMsg = "Task (hide, show, exit):"

  do {
    println(insertOperationMsg)
    val operation = readln()
    try {
      println(Operation.valueOf(operation).msg)
    } catch (_: IllegalArgumentException) {
      println(Operation.wrong.msg + operation)
    }
  } while (Operation.exit.name != operation)

}

enum class Operation(val msg: String) {
  hide("Hiding message in image."),
  show("Obtaining message from image."),
  exit("Bye!"),
  wrong("Wrong task: ")
}