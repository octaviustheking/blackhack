import java.util.*;

public class Blackhack {
    public static int money = 500;

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

        while (!bettingDone) {
                System.out.println("You have $" + money + ".");
                System.out.print("How much would you like to bet? > ");

                bet = input.nextInt();

                if (bet <= money) {
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

            System.out.println("You got a " + value + "!");
            total = cardValue(playerCards);
            System.out.println("That's a total of " + total + "!");
            if (total <= 21) {
                System.out.print("Would you like to hit or stand? > ");
                action = input.next();

                if (!action.equals("hit") && !action.equals("stand")) {
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
            System.out.println("The dealer has a total of " + dealerTotal + "!");
            System.out.println("You only had a total of " + playerTotal + "!");
            gameOver(bet);
        }
        else if (dealerTotal == playerTotal) {
            System.out.println("You tied! The dealer wins!");
            gameOver(bet);
        }
        else {
                System.out.println("You won!");
                System.out.println("The dealer only had a total of " + dealerTotal + "!");
                System.out.println("You had a total of " + playerTotal + "!");
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
}

