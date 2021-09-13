package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Machine machine = new Machine();
        do {
            machine.getActionChoice();
            String action = scanner.next();
            machine.choosingAnAction(action);
        } while (!machine.getTerminate());
    }

}

class Machine {

    private int money;
    private int water;
    private int milk;
    private int coffeBeans;
    private int dispoCups;
    private boolean terminate;

    private enum states {
        ACTION, VARIANT_BUY, VARIANT_FILL
    }

    private states machineState;
    private static int counter = 0;

    public Machine() {
        this.money = 550;
        this.water = 400;
        this.milk = 540;
        this.coffeBeans = 120;
        this.dispoCups = 9;
        this.terminate = false;
        this.machineState = states.ACTION;
    }

    public void choosingAnAction(String action) {
        if (action.equals("remaining") || action.equals("buy") || action.equals("fill") || action.equals("take") || action.equals("exit")) {
            this.machineState = states.valueOf("ACTION");
        }
        switch (this.machineState) {
            case VARIANT_FILL:
                fillAction(action);
                break;
            case VARIANT_BUY:
                switch (action) {
                    case "1":
                        makeEspresso();
                        machineState = states.ACTION;
                        break;
                    case "2":
                        makeLatte();
                        machineState = states.ACTION;
                        break;
                    case "3":
                        makeCappuccino();
                        machineState = states.ACTION;
                        break;
                    case "back":
                        machineState = states.ACTION;
                        break;
                    default:
                        break;
                }
                break;
            case ACTION:
                switch (action) {
                    case "remaining":
                        remaining();
                        break;
                    case "buy":
                        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                        this.machineState = states.VARIANT_BUY;
                        break;
                    case "fill":
                        this.machineState = states.VARIANT_FILL;
                        System.out.println("Write how many ml of water you want to add:");
                        break;
                    case "take":
                        giveMoney();
                        break;
                    case "exit":
                        this.terminate = true;
                        break;
                    default:
                        break;
                }
        }
    }

    public String getMachineState() {
        return machineState.toString();
    }

    public void getActionChoice() {
        if (machineState == states.ACTION) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
        }
    }

    public boolean getTerminate() {
        return terminate;
    }

    private void giveMoney() {
        System.out.println("I gave you $" + this.money);
        this.money = 0;
    }

    private void fillAction(String action) {
        counter++;
        if (counter == 1) {
            this.water += Integer.parseInt(action);
            System.out.println("Write how many ml of milk you want to add:");
        } else if (counter == 2) {
            this.milk += Integer.parseInt(action);
            System.out.println("Write how many grams of coffee beans you want to add:");
        } else if (counter == 3) {
            this.coffeBeans += Integer.parseInt(action);
            System.out.println("Write how many disposable cups of coffee you want to add:");
        } else if (counter == 4) {
            this.dispoCups += Integer.parseInt(action);
            machineState = states.ACTION;
        }
    }

    private void makeEspresso() {
        int neededWater = 250;
        int neededBeans = 16;
        int cost = 4;
        if (isPossible(neededWater, 0, neededBeans)) {
            System.out.println("I have enough resources, making you a coffee!");
            this.water -= neededWater;
            this.coffeBeans -= neededBeans;
            this.money += cost;
            this.dispoCups--;
        }
    }

    private void makeLatte() {
        int neededWater = 350;
        int neededMilk = 75;
        int neededBeans = 20;
        int cost = 7;
        if (isPossible(neededWater, neededMilk, neededBeans)) {
            System.out.println("I have enough resources, making you a coffee!");
            this.water -= neededWater;
            this.milk -= neededMilk;
            this.coffeBeans -= neededBeans;
            this.money += cost;
            this.dispoCups--;
        }
    }

    private void makeCappuccino() {
        int neededWater = 200;
        int neededMilk = 100;
        int neededBeans = 12;
        int cost = 6;
        if (isPossible(neededWater, neededMilk, neededBeans)) {
            System.out.println("I have enough resources, making you a coffee!");
            this.water -= neededWater;
            this.milk -= neededMilk;
            this.coffeBeans -= neededBeans;
            this.money += cost;
            this.dispoCups--;
        }
    }

    private boolean isPossible(int neededWater, int neededMilk, int neededBeans) {
        boolean possible = true;
        if (water < neededWater) {
            System.out.println("Sorry, not enough water!");
            possible = false;
        } else if (milk < neededMilk) {
            System.out.println("Sorry, not enough milk!");
            possible = false;
        } else if (coffeBeans < neededBeans) {
            System.out.println("Sorry, not enough coffee beans!");
            possible = false;
        } else if (dispoCups <= 0) {
            System.out.println("Sorry, not enough disposable cups!");
            possible = false;
        }
        return possible;
    }

    private void remaining() {
        System.out.println("The coffee machine has:");
        System.out.println(this.water + " ml of water");
        System.out.println(this.milk + " ml of milk");
        System.out.println(this.coffeBeans + " g of coffee beans");
        System.out.println(this.dispoCups + " disposable cups");
        System.out.println("$" + this.money + " of money");
    }

}
