package machine;

import java.util.Scanner;

class Cup {
    int water;
    int beans;
    int milk;
    int price;

    public Cup(int type){
        switch (type) {
            case 1:
                water = 250;
                beans = 16;
                price = 4;
                milk = 0;
                break;
            case 2:
                water = 350;
                beans = 20;
                price = 7;
                milk = 75;
                break;
            case 3:
                water = 200;
                beans = 12;
                price = 6;
                milk = 100;
                break;
        }
    }

    public Cup(int w, int b, int m){
        water = w;
        beans = b;
        milk = m;
    }

    void printAmountIngredients(int ncups) {
        System.out.println("For " + ncups + " cups of coffee you will need:");
        System.out.println(water * ncups + " ml of water");
        System.out.println(milk * ncups + " ml of milk");
        System.out.println(beans * ncups + " g of coffee beans");
    }
}

class Machine{
    int water;
    int beans;
    int milk;
    int money;
    int dcups;
    String state = "";
    int substate = 0;

    public Machine(int water, int beans, int milk, int money, int dcups){
        this.beans = beans;
        this.milk = milk;
        this.water = water;
        this.money = money;
        this.dcups = dcups;
    }

        void buy(String action) {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        System.out.print("> ");
            if (substate == 0) {
                substate++;
                return;
            }
            if (action.equals("back")) {
                    substate = 0;
                    state = "";
                    return;
        }
        Cup cup = new Cup(Integer.parseInt(action));
        buyCup(cup);
        substate = 0;
        state = "";
    }


    void fill(String action) {
        if (substate == 0) {
            System.out.println("Write how many ml of water do you want to add:");
            System.out.print("> ");
            substate++;
            return;
        }
        if (substate == 1) {
            water += Integer.parseInt(action);
            System.out.println("Write how many ml of milk do you want to add:");
            System.out.print("> ");
            substate++;
            return;
        }
        if (substate == 2) {
            milk += Integer.parseInt(action);
            System.out.println("Write how many grams of coffee beans do you want to add:");
            System.out.print("> ");
            substate++;
            return;
        }
        if (substate == 3) {
            beans += Integer.parseInt(action);
            System.out.println("Write how many disposable cups of coffee do you want to add:");
            System.out.print("> ");
            substate++;
            return;
        }
        if (substate == 4) {
                dcups += Integer.parseInt(action);
                substate = 0;
                state = "";
        }
    }
    void take() {
        System.out.println("I gave you $" + money);
        money = 0;
    }
    public void setState(String action) {
        if (state.equals("")) {
            switch (action) {
                case "buy":
                    state = "buy";
                    buy(action);
                    if (substate == 0 && state.isEmpty() ) {
                        System.out.println("Write action (buy, fill, take, remaining, exit):");
                        System.out.print("> ");
                    }
                    break;
                case "fill":
                    state = "fill";
                    fill(action);
                    if (substate == 0 && state.isEmpty() ) {
                        System.out.println("Write action (buy, fill, take, remaining, exit):");
                        System.out.print("> ");
                    }
                    break;
                case "take":
                    take();
                    System.out.println("Write action (buy, fill, take, remaining, exit):");
                    System.out.print("> ");
                    break;
                case "remaining":
                    printState();
                    System.out.println("Write action (buy, fill, take, remaining, exit):");
                    System.out.print("> ");
                    break;
                default :
                    System.out.println("Write action (buy, fill, take, remaining, exit):");
                    System.out.print("> ");
            }
        } else if (state.equals("buy")) {
            buy(action);
            if (substate == 0 && state.isEmpty() ) {
                System.out.println("Write action (buy, fill, take, remaining, exit):");
                System.out.print("> ");
            }
        } else if (state.equals("fill")) {
            fill(action);
            if (substate == 0 && state.isEmpty() ) {
                System.out.println("Write action (buy, fill, take, remaining, exit):");
                System.out.print("> ");
            }
        }
    }

    public void printState() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(beans + " of coffee beans");
        System.out.println(dcups + " of disposable cups");
        System.out.println("$" + money + " of money");
    }

    public void buyCup(Cup cup) {
        if (cup.beans > beans) {
            System.out.println("Sorry, not enough coffee beans!");
            return;
        } else if (cup.water > water) {
            System.out.println("Sorry, not enough water!");
            return;
        } else if (cup.milk > milk) {
            System.out.println("Sorry, not enough milk!");
            return;
        } else if (dcups == 0) {
            System.out.println("Sorry, not enough disposable cups!");
            return;
        }
        System.out.println("I have enough resources, making you a coffee!");
        beans -= cup.beans;
        milk -= cup.milk;
        water -= cup.water;
        money += cup.price;
        dcups--;

    }

    static void printAbilityToMake(Cup cup, int w, int m, int b, int ncups) {
        int res = w / cup.water;
        if (m / cup.milk < res) {
            res = m / cup.milk;
        } else if (b / cup.beans < res) {
            res = b / cup.beans;
        }
        if (res == ncups) {
            System.out.println("Yes, I can make that amount of coffee");
        } else if (res < ncups) {
            System.out.println("No, I can make only " + res + " cup(s) of coffee");
        } else {
            System.out.println("Yes, I can make that amount of coffee (and even " + (res - ncups) + " more than that)");
        }
    }
}

public class CoffeeMachine {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Machine m = new Machine(400,120,540,550,9);
        //m.printState();
        String action = " ";
        while (!action.equals("exit")) {
            m.setState(action);
            action = scanner.nextLine();
        }
        //m.printState();
        /*System.out.println("Write how many ml of water the coffee machine has:");
        System.out.print("> ");
        int water = scanner.nextInt();
        System.out.println("Write how many ml of milk the coffee machine has:");
        System.out.print("> ");
        int milk = scanner.nextInt();
        System.out.println("Write how many grams of coffee beans the coffee machine has:");
        System.out.print("> ");
        int beans = scanner.nextInt();
        System.out.println("Write how many cups of coffee you will need:");
        System.out.print("> ");
        int ncups = scanner.nextInt();
        Cup cup = new Cup();
        Machine.printAbilityToMake(cup, water, milk, beans, ncups);*/
        /*System.out.println("Write how many cups of coffee you will need:");
        System.out.print("> ");
        int ncups = scanner.nextInt();
        Cup cup = new Cup();
        cup.printAmountIngredients(ncups);*/
        /*System.out.println("Starting to make a coffee");
        System.out.println("Grinding coffee beans");
        System.out.println("Boiling water");
        System.out.println("Mixing boiled water with crushed coffee beans");
        System.out.println("Pouring coffee into the cup");
        System.out.println("Pouring some milk into the cup");
        System.out.println("Coffee is ready!");*/
    }
}
