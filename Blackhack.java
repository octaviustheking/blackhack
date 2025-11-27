import java.util.*;

public class Blackhack {
    public static int money = 500;

    static List <Integer> cards = new LinkedList<>();

    public static void main(String[] args) {
        System.out.println("Welcome to Blackhack!");
        game();
    }

    static void game() {
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

        while (!bettingDone) {
            System.out.println("You have $" + money + ".");
            System.out.print("How much would you like to bet? > ");

            int bet = input.nextInt();

            if (bet <= money) {
                bettingDone = true;
            }
            else {
                System.out.println("You don't have that much money!");
            }
        }

        Random random = new Random();
        int randomIndex = random.nextInt(cards.size());
        int card1 = cards.get(randomIndex);
        cards.remove(randomIndex);
        randomIndex = random.nextInt(cards.size());
        int card2 = cards.get(randomIndex);
        cards.remove(randomIndex);

        List <Integer> playerCards = new LinkedList<>();

        playerCards.add(card1);
        playerCards.add(card2);

        boolean decidingAction = true;

        while (decidingAction) {
            List <String> values = new LinkedList<>();
            values = (List<String>) numToCard(playerCards);
            String lastValue = "";
            StringBuilder value = new StringBuilder();

            if (values.size() == 2) {
                value = new StringBuilder(values.get(0) + " and a " + values.get(1));
            }
            else {
                lastValue = values.getLast();
                values.removeLast();

                for (String i : values) {
                    value.append(i).append(", ");
                }

                value = new StringBuilder(value + ", and a " + lastValue);
            }

            System.out.println("You got a " + value + "!");
            int total = cardValue(playerCards);
            System.out.println("That's a total of " + total + "!");
            System.out.print("Would you like to hit or stand? > ");
            String action = input.next();

            if (!action.equals("hit") && !action.equals("stand")) {
                System.out.println("That is not a valid action!");
            }
            else {
                decidingAction = false;
            }
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
            if (i != 14) {
                value += i;
            }
            if (i > 10 && i < 14) {
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
}

