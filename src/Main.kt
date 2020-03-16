import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    val myMachine = CoffeeMachine(400, 540, 120, 9, 550)

    do {
        val action = scanner.next()
        myMachine.use(action)
    } while (action != "exit")
}