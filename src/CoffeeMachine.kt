class CoffeeMachine(var water: Int, var milk: Int, var coffeeBeans: Int, var cups: Int, var money: Int) {
    enum class TypeCoffee(val water: Int, val milk: Int, val coffeeBeans: Int, val cups: Int, val cost: Int) {
        ESPRESSO(250, 0, 16, 1, 4),
        LATTE(350, 75, 20, 1, 7),
        CAPPUCCINO(200, 100, 12, 1, 6);
    }

    enum class CoffeeMachineStates() {
        CHOOSING(),
        BUY(),
        FILL();
    }

    enum class FillMachineStates() {
        FILLINGWATER(),
        FILLINGMILK(),
        FILLINGCOFFEEBEANS(),
        FILLINGCUPS();
    }

    var currentMachineState = CoffeeMachineStates.CHOOSING
    var fillMachineState = FillMachineStates.FILLINGWATER

    init {
        promtPossibleActions()
    }

    private fun promtPossibleActions() {
        println("Write action (buy, fill, take, remaining, exit):")
    }

    private fun enoughResources() {
        println("I have enough resources, making you a coffee!")
    }

    private fun notEnoughResources(resource: String) {
        println("Sorry, not enough $resource!")
    }

    private fun printCoffeeMachineStatus() {
        println("The coffee machine has:")
        println("$water of water")
        println("$milk of milk")
        println("$coffeeBeans of coffee beans")
        println("$cups of disposable cups")
        println("$$money of money")
        promtPossibleActions()
    }

    private fun prepareCoffeeWithResources(waterNeeded: Int, milkNeeded: Int, coffeeBeansNeeded: Int, cupsNeeded: Int, money: Int) {
        when {
            water < waterNeeded -> notEnoughResources("water")
            milk < milkNeeded -> notEnoughResources("milk")
            coffeeBeans < coffeeBeansNeeded -> notEnoughResources("coffee beans")
            cups < cupsNeeded -> notEnoughResources("disposable cups")
            else -> {
                enoughResources()
                water -= waterNeeded
                milk -= milkNeeded
                coffeeBeans -= coffeeBeansNeeded
                cups -= cupsNeeded
                this.money += money
            }
        }
    }

    private fun buyEspresso() {
        with(TypeCoffee.ESPRESSO) {
            prepareCoffeeWithResources(this.water, this.milk, this.coffeeBeans, this.cups, this.cost)
        }
    }

    private fun buyLatte() {
        with(TypeCoffee.LATTE) {
            prepareCoffeeWithResources(this.water, this.milk, this.coffeeBeans, this.cups, this.cost)
        }
    }

    private fun buyCappuccino() {
        with(TypeCoffee.CAPPUCCINO) {
            prepareCoffeeWithResources(this.water, this.milk, this.coffeeBeans, this.cups, this.cost)
        }
    }

    fun use(action: String) {
        when (currentMachineState) {
            CoffeeMachineStates.CHOOSING -> {
                when (action) {
                    "buy" -> {
                        println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
                        currentMachineState = CoffeeMachineStates.BUY
                    }
                    "fill" -> {
                        println("Write how many ml of water do you want to add:")
                        currentMachineState = CoffeeMachineStates.FILL
                    }
                    "take" -> {
                        println("I gave you $$money")
                        money = 0
                        promtPossibleActions()
                    }
                    "remaining" -> printCoffeeMachineStatus()
                    "exit" -> Unit
                    else -> println("Unknown Action, please use one of the supported actions (buy, fill, take)")
                }
            }
            CoffeeMachineStates.BUY -> {
                when (action) {
                    "1" -> buyEspresso()
                    "2" -> buyLatte()
                    "3" -> buyCappuccino()
                    "back" -> Unit
                    else -> println("Unknown Coffee Type, please select a supported type of coffee (1 - espresso, 2 - latte, 3 - cappuccino)")
                }
                promtPossibleActions()
                currentMachineState = CoffeeMachineStates.CHOOSING
            }
            CoffeeMachineStates.FILL -> {
                when(fillMachineState) {
                    FillMachineStates.FILLINGWATER -> {
                        water += action.toInt()
                        println("Write how many ml of milk do you want to add:")
                        fillMachineState = FillMachineStates.FILLINGMILK
                    }
                    FillMachineStates.FILLINGMILK -> {
                        println("Write how many grams of coffee beans do you want to add:")
                        milk += action.toInt()
                        fillMachineState = FillMachineStates.FILLINGCOFFEEBEANS
                    }
                    FillMachineStates.FILLINGCOFFEEBEANS -> {
                        println("Write how many disposable cups of coffee do you want to add:")
                        coffeeBeans += action.toInt()
                        fillMachineState = FillMachineStates.FILLINGCUPS
                    }
                    FillMachineStates.FILLINGCUPS -> {
                        cups += action.toInt()
                        fillMachineState = FillMachineStates.FILLINGWATER
                        promtPossibleActions()
                        currentMachineState = CoffeeMachineStates.CHOOSING
                    }
                }
            }
        }
    }
}