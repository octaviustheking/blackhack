import java.util.*;

public class Blackhack {
    public static int money = 500;

    public static int minMoney = 1500;

    public static boolean hasInventory = false;
    public static boolean gunOwned = false;
    public static int fakeFace = 0;
    public static int fakeAce = 0;
    public static int cardIncinerator = 0;

    static List <Integer> cards = new LinkedList<>();

    public static void main(String[] args) {
        System.out.println("Welcome to Blackhack!");

        game();
    }

    static void game() {
        if (money == 0) {
            System.out.println("Sorry, you don't have any more money. ");
            System.out.println("Thanks for playing Blackhack, though!");
            System.exit(0);
        }

        for (int i = 1; i <= 4; i++) {
            cards.add(2);
            cards.add(3);
            cards.add(4);
            cards.add(5);
            cards.add(6);
            cards.add(7);
            cards.add(8);
            cards.add(9);
            cards.add(10);
            cards.add(11);
            cards.add(12);
            cards.add(13);
            cards.add(14);
        }

        boolean bettingDone = false;

        Scanner input = new Scanner(System.in);

        int bet = 0;

        boolean decidingVisit = true;

        System.out.println("You notice there is a shady man at the back of the casino. ");

        while (decidingVisit) {
            System.out.print("Do you choose to visit him? (yes/no) > ");

            String visit = input.next();

            if (visit.equalsIgnoreCase("yes")) {
                shop();
                decidingVisit = false;
            }
            else if (visit.equalsIgnoreCase("no")) {
                decidingVisit = false;
            }
            else {
                System.out.println("Please enter yes or no. ");
            }
        }

        while (!bettingDone) {
            System.out.println("You have $" + money + ".");
            System.out.print("How much would you like to bet? > ");

            while (!input.hasNextInt())
            {
                input.next();
                System.out.print("Please enter an integer. > ");
            }

            bet = input.nextInt();

            if (bet < 0) {
                System.out.println("Please enter an amount greater than 0.");
            }
            else if (bet <= money) {
                bettingDone = true;
            } else {
                System.out.println("You don't have that much money!");
            }
        }

        int card1 = newCard();
        int card2 = newCard();

        List<Integer> playerCards = new LinkedList<>();

        playerCards.add(card1);
        playerCards.add(card2);

        boolean playing = true;

        while (playing) {
            playing = gameplay(playerCards, input, bet);
        }
    }

    static SequencedCollection<String> numToCard(List<Integer> cards) {
        List <String> values = new LinkedList<>();
        for (int i : cards) {
            if (i > 10) {
                if (i == 11) {
                    values.add("Jack");
                }
                else if (i == 12) {
                    values.add("Queen");
                }
                else if (i == 13) {
                    values.add("King");
                }
                else if (i == 14) {
                    values.add("Ace");
                }
            }
            else {
                values.add(String.valueOf(i));
            }
        }

        return values;
    }

    static int cardValue(List <Integer> cards) {
        int value = 0;
        int numAces = 0;

        for (int i : cards) {
            if (i <= 10) {
                value += i;
            }
            else if (i < 14) {
                value += 10;
            }
            else {
                numAces ++;
                value ++;
            }
        }

        if (21 - value >= 11) {
            if (numAces >= 1) {
                value += 10;
            }
        }

        return value;
    }

    static int newCard() {
        Random random = new Random();
        int randomIndex = random.nextInt(cards.size());
        int card = cards.get(randomIndex);
        cards.remove(randomIndex);

        return card;
    }

    static boolean gameplay(List <Integer> playerCards, Scanner input, int bet) {
        boolean decidingAction = true;

        String action = null;

        int total = 0;

        while (decidingAction) {
            List<String> values = new LinkedList<>();
            values = (List<String>) numToCard(playerCards);
            String lastValue = "";
            StringBuilder value = new StringBuilder();

            if (values.size() == 1) {
                value = new StringBuilder(values.getFirst());
            }
            else if (values.size() == 2) {
                value = new StringBuilder(values.get(0) + " and a " + values.get(1));
            } else {
                lastValue = values.getLast();
                values.removeLast();

                for (String i : values) {
                    value.append(i).append(", ");
                }

                value = new StringBuilder(value + "and a " + lastValue);
            }

            System.out.println("You got a " + value + "!");
            total = cardValue(playerCards);
            System.out.println("That's a total of " + total + "!");
            if (total <= 21) {
                System.out.print("Would you like to hit, stand, or check your inventory? (hit/stand/inventory) > ");
                action = input.next();

                if (!action.equalsIgnoreCase("hit") && !action.equalsIgnoreCase("stand") && !action.equalsIgnoreCase("inventory")) {
                    System.out.println("That is not a valid action!");
                } else {
                    decidingAction = false;
                }
            }
            else {
                gameOver(bet);
            }
        }

        if (action.equals("stand")) {
            dealerTurn(total, bet);
            return false;
        }
        else if (action.equals("inventory")) {
            System.out.println("You have the following: ");
            if (hasInventory) {
                if (gunOwned) {
                    System.out.println("Gun");
                }
                if (fakeFace > 0) {
                    System.out.println("Face");
                }
                if (fakeAce > 0) {
                    System.out.println("Ace");
                }
                if (cardIncinerator > 0) {
                    System.out.println("Incinerator");
                }

                boolean decidingItem = true;
                String item = null;

                while (decidingItem) {
                    System.out.print("What would you like to use? (name of item/nothing) > ");
                    item = input.next();

                    if (!item.equalsIgnoreCase("gun") && !item.equalsIgnoreCase("face") && !item.equalsIgnoreCase("nothing") && !item.equalsIgnoreCase("ace") && !item.equalsIgnoreCase("incinerator")) {
                        System.out.println("Please enter the name of an item or nothing.");
                    }
                    else {
                        decidingItem = false;
                    }
                }

                if (item.equalsIgnoreCase("gun")) {
                    if (gunOwned) {
                        System.out.println("You shoot the dealer dead and pocket the $1000 in his wallet. ");
                        System.out.println("You now have $" + (money + 1000) + "!");
                        System.out.println("As you stand to leave, however, the police arrive. ");
                        System.out.println("You are placed in handcuffs are dragged outside. ");
                        System.out.println("Thanks for playing Blackhack!");
                        return false;
                    }
                    else {
                        System.out.println("You do not own a gun!");
                        return true;
                    }
                }
                else if (item.equalsIgnoreCase("face")) {
                    if (fakeFace > 0) {
                        System.out.println("You secretly add a face card to your hand.");
                        Random randomCard = new Random();
                        playerCards.add((randomCard.nextInt(13 - 11 + 1)) + 11);
                        fakeFace -= 1;
                    }
                    else {
                        System.out.println("You do not own a face card!");
                    }
                    return true;
                }
                else if (item.equalsIgnoreCase("ace")) {
                    if (fakeAce > 0) {
                        System.out.println("You secretly add an ace to your hand.");
                        playerCards.add(14);
                        fakeAce -= 1;
                    }
                    else {
                        System.out.println("You do not own a face card!");
                    }
                    return true;
                }
                else if (item.equalsIgnoreCase("incinerator")) {
                    if (cardIncinerator > 0) {
                        List<String> value = new LinkedList<>();
                        value = (List<String>) numToCard(playerCards);
                        System.out.println("You grab your " + value.getLast() + " and drop it into your portable incinerator.");
                        System.out.println("However, while that card does burn, so does your incinerator. ");
                        playerCards.removeLast();
                        cardIncinerator -= 1;
                    }
                    else {
                        System.out.println("You do not own a portable incinerator!");
                    }
                    return true;
                }
                else {
                    return true;
                }
            }
            else {
                System.out.println("Nothing!");
                return true;
            }
        }
        else {
            int newCard = newCard();
            playerCards.add(newCard);
            return true;
        }
    }

    static void dealerTurn(int playerTotal, int bet) {
        int card1 = newCard();
        int card2 = newCard();

        List <Integer> dealerCards = new LinkedList<>();
        dealerCards.add(card1);
        dealerCards.add(card2);

        int dealerTotal = cardValue(dealerCards);

        List<String> values = new LinkedList<>();
        values = (List<String>) numToCard(dealerCards);
        String lastValue = "";
        StringBuilder value = new StringBuilder();

        if (values.size() == 2) {
            value = new StringBuilder(values.get(0) + " and a " + values.get(1));
        } else {
            lastValue = values.getLast();
            values.removeLast();

            for (String i : values) {
                value.append(i).append(", ");
            }

            value = new StringBuilder(value + "and a " + lastValue);
        }

        System.out.println("The dealer has a " + value + "!");
        System.out.println("That's a total of " + dealerTotal + "!");

        while (dealerTotal <= 16) {
            dealerTotal = dealerAction(dealerCards, dealerTotal);
        }

        if (dealerTotal > 21) {
            System.out.println("You won!");
            System.out.println("The dealer went over 21!");
            System.out.println("You earned $" + bet + "!");
            money += bet;
            boolean deciding = true;

            while (deciding) {
                System.out.print("Play Again? (yes/no) > ");

                Scanner input = new Scanner(System.in);

                String action = input.next();

                if (!action.equals("yes") && !action.equals("no")) {
                    System.out.println("Invalid action! Please try again!");
                }
                else {
                    deciding = false;
                    if (action.equals("no")) {
                        System.out.println("You ended up with $" + money + ".");
                        System.out.println("Thanks for playing Blackhack!");
                        System.exit(0);
                    }
                    else {
                        game();
                    }
                }
            }
        }
        else if (dealerTotal > playerTotal) {
            gameOver(bet);
        }
        else if (dealerTotal == playerTotal) {
            System.out.println("You tied! The dealer wins!");
            gameOver(bet);
        }
        else {
            System.out.println("You won!");
            System.out.println("You earned $" + bet + "!");
            money += bet;
            boolean deciding = true;

            while (deciding) {
                System.out.print("Play Again? (yes/no) > ");

                Scanner input = new Scanner(System.in);

                String action = input.next();

                if (!action.equals("yes") && !action.equals("no")) {
                    System.out.println("Invalid action! Please try again!");
                }
                else {
                    deciding = false;
                    if (action.equals("no")) {
                        System.out.println("You ended up with $" + money + ".");
                        System.out.println("Thanks for playing Blackhack!");
                        System.exit(0);
                    }
                    else {
                        game();
                    }
                }
            }
        }

    }

    static int dealerAction(List <Integer> dealerCards, int dealerTotal) {
        int card = newCard();
        dealerCards.add(card);

        List<String> values;
        values = (List<String>) numToCard(dealerCards);
        String lastValue = "";
        StringBuilder value = new StringBuilder();

        if (values.size() == 2) {
            value = new StringBuilder(values.get(0) + " and a " + values.get(1));
        } else {
            lastValue = values.getLast();
            values.removeLast();

            for (String i : values) {
                value.append(i).append(", ");
            }

            value = new StringBuilder(value + "and a " + lastValue);
        }

        dealerTotal = cardValue(dealerCards);
        System.out.println("The dealer has a " + value + "!");
        System.out.println("That's a total of " + dealerTotal + "!");

        return dealerTotal;
    }

    static void gameOver(int bet) {
        System.out.println("You lost!");
        System.out.println("That means you lost $" + bet + "!");
        money -= bet;
        System.out.println("99% of people quit before they hit it big!");

        boolean deciding = true;

        while (deciding) {
            System.out.print("Play Again? (yes/no) > ");

            Scanner input = new Scanner(System.in);

            String action = input.next();

            if (!action.equals("yes") && !action.equals("no")) {
                System.out.println("Invalid action! Please try again!");
            }
            else {
                deciding = false;
                if (action.equals("no")) {
                    System.out.println("You ended up with $" + money + ".");
                    System.out.println("Thanks for playing Blackhack!");
                    System.exit(0);
                }
                else {
                    game();
                }
            }
        }

    }

    static void shop() {
        System.out.println("You decide to visit the shady man in the back of the casino.");

        boolean done = false;

        if (money < minMoney) {
            System.out.println("He tells you he doesn't deal with small fry who have less than $" + minMoney + ".");
            System.out.println("You return to the dealer.");
        }
        else {
            while (!done) {
                System.out.println("He shows you his wares.");
                System.out.println("You have $" + money + ".");
                System.out.println("What would you like to buy?");
                System.out.println("0. Leave");
                System.out.println("1. A gun ($300)");
                System.out.println("2. A face card ($200)");
                System.out.println("3. An ace ($400)");
                System.out.println("4. A portable incinerator ($500)");
                System.out.print("Type the number of the item you would like to purchase. > ");

                Scanner input = new Scanner(System.in);

                while (!input.hasNextInt())
                {
                    input.next();
                    System.out.print("Please enter an integer. > ");
                }

                int option = input.nextInt();

                if (option == 0) {
                    System.out.println("You return to the dealer. ");
                    done = true;
                }
                else {
                    hasInventory = true;
                    if (option == 1) {
                        gunOwned = true;
                        money -= 300;
                        System.out.println("You purchased a gun for $300.");
                        System.out.println("The shady man waves you away.");
                        done = true;
                    }
                    else if (option == 2) {
                        fakeFace += 1;
                        money -= 200;
                        System.out.println("You purchased a face card for $200.");
                        System.out.println("The shady man waves you away.");
                        done = true;
                    }
                    else if (option == 3) {
                        fakeAce += 1;
                        money -= 400;
                        System.out.println("You purchased a ace card for $400.");
                        System.out.println("The shady man waves you away.");
                        done = true;
                    }
                    else if (option == 4) {
                        cardIncinerator += 1;
                        money -= 500;
                        System.out.println("You purchased a portable incinerator for $500.");
                        System.out.println("The shady man waves you away.");
                        done = true;
                    }
                }
            }
        }
    }
}
