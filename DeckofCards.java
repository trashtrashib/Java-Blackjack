import java.security.SecureRandom;
import java.util.Scanner;

public class DeckofCards {
    
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Scanner IN = new Scanner(System.in);
    private static int numberOfCards = 52; // how many cards are shuffled into the deck. Should be a multiple of 52
    
    private static int playerCount; // Number of players. Literally players.length().
    private static Card[] deck = new Card[numberOfCards]; // Array of cards representing a deck.
    private static int currentCard = 0; // Deck progress.
    private static Player[] players; // List of players
    private static boolean end = false; // Exit game loop flag.

    public void setPlayerCount(int count) {
        playerCount = count;
    }
    public int getPlayerCount() {
        return playerCount;
    }
    public void setDeck(Card[] deckin) {
        deck = deckin;
    }
    public Card[] getDeck() {
        return deck;
    }

    // Randomize deck order by swapping each index with another random index.
    public void shuffle() {

        currentCard = 0;

        for (int i = 0; i < numberOfCards; i++) {
            int second = RANDOM.nextInt(deck.length);

            Card temp = deck[i];
            deck[i] = deck[second];
            deck[second] = temp;

        }

    }

    // Create a deck containing each card in order.
    public Card[] deckBuild() {

        Card[] newDeck = new Card[numberOfCards];
        String[] faceList = {"Ace","2","3","4","5","6","7","8","9","10","Jack","Queen","King"};
        String[] suitList = {"Spades","Diamonds","Clubs","Hearts"};

        for (int i = 0; i < numberOfCards; i++)
        {
            newDeck[i] = new Card(faceList[i % 13],suitList[(i / 13) % 4],(i % 13) + 1);
        }

        deck = newDeck;
        return deck;

    }

    // Print each card in order.
    public void showDeck() {
        for (int i = 0; i<deck.length; i++) {
            System.out.println(deck[i].asText());
        }
    }

    // Grab the current card and move to the next.
    public static Card drawCard() {
        if (currentCard < numberOfCards) {
        return deck[currentCard++];
        }
    return null;
    }

    public Player[] getPlayers() {
        return players;
    }

    // Start a hand by dealing 2 cards to each player and checking for a dealer blackjack.
    public void deal() {
        boolean blackjack = false;
                for (int i = 0; i < playerCount; i++)
                {
                    players[i].drawCard();
                    players[i].drawCard();

                    if (!players[i].getDeal()) {
                    players[i].showHand();
                    } else if (players[i].getValue() != 21) {
                        System.out.printf("Dealer has 2 cards. Including %s ",(players[i].getHand()[0].getFace() == "Ace" || players[i].getHand()[0].getValue() == 8) ? "an":"a");
                        System.out.println(players[i].getHand()[0].asText());
                    } else {
                        players[i].showHand();
                        System.out.println("Dealer has Blackjack!");
                        blackjack = true;
                    }
                }

                if (blackjack) {
                    for (int i = 0; i < playerCount-1; i++)
                    {
                        if (players[i].getValue() == 21) {System.out.println(players[i].getName() + " Tied.");} else {System.out.println(players[i].getName() + " Lost.");}
                    }
                    gameEnd();
                }
    }

    // Ask if the player would like to play another hand.
    public void gameEnd() {
        System.out.println("Would you like to play again? (y/n)");
        String read = IN.nextLine();
        while (!read.toLowerCase().equals("y") && !read.toLowerCase().equals("n")) {

        read = IN.nextLine();

        }

        if (read.toLowerCase().equals("y")) {
            shuffle();

            for (int i = 0; i < playerCount; i++) {
                players[i].setHand(new Card[10]);
            }

            deal();
        } else {end = true;}

    }

    // Loop over each player and see if they beat the dealer or not.
    public void tally() {
        System.out.println("\nThe hand has ended.");
        players[playerCount-1].showHand();

        for (int i = 0; i<playerCount-1; i++) {
            if ((players[i].getValue() > players[playerCount-1].getValue() ||
            (players[playerCount-1].getValue() == 21 && players[i].getValue() == 21 && players[i].getHand()[2] == null) || players[playerCount-1].getValue() > 21) &&
            players[i].getValue() <= 21)
            {System.out.println(players[i].getName() + " has won!");} else 
                if (players[i].getValue() < 21 && players[i].getValue() == players[playerCount-1].getValue()) {System.out.println(players[i].getName() + " tied with the dealer.");} else
                {System.out.println(players[i].getName() + " has lost.");}
        }

        gameEnd();

    }

    public void round() {

        boolean allStay = true;
        int lowest = 999; // used for dealer ai.
        int highest = 0; // used for dealer ai.
        System.out.println();
        // Loop through each player and either hit or stay.
        for (int i = 0; i<playerCount-1; i++) {
            if (players[i].getValue() > 21) {continue;}
            players[i].showHand();
            System.out.println("Should " + players[i].getName() + " hit or stay? (h/s)");

        String read = IN.nextLine();
        while (!read.toLowerCase().equals("h") && !read.toLowerCase().equals("s")) {

        read = IN.nextLine();

        }

        if (read.toLowerCase().equals("h")) {
            allStay = false;
            players[i].drawCard();
            System.out.println(players[i].getName() + " hits.");
            players[i].showHand();
            if (players[i].getValue() > 21) {System.out.println(players[i].getName() + " has bust."); lowest = -1;}
            else if (players[i].getValue() > highest) {highest = players[i].getValue();}
            if (players[i].getValue() < lowest) {lowest = players[i].getValue();}
            IN.nextLine();
        } 
            

        }

        // Dealer ai
        if ((players[playerCount-1].getValue() < 17 || players[playerCount-1].getValue() < lowest) && players[playerCount-1].getValue() <= highest) {
            players[playerCount-1].drawCard();
            System.out.printf("The dealer hits. The dealer has " + players[playerCount-1].getHandSize() + " cards in hand including %s ",(players[playerCount-1].getHand()[0].getFace() == "Ace" || players[playerCount-1].getHand()[0].getValue() == 8) ? "an":"a");
                        System.out.println(players[playerCount-1].getHand()[0].asText());
            if (players[playerCount-1].getValue() > 21) {System.out.println("The dealer has bust!"); tally();}} else {
                System.out.println("The dealer stays.");
                if (allStay) {tally();}
            }

    }

    public void intro() {
        System.out.println("Welcome to BlackJack!\n\nIn blackjack, players compete with a dealer to get the closest number to 21 without going over.\nEach player starts with a two card hand, the dealer starts with a two card hand aswell, but the dealer's hand is hidden except for one card.\nPlayers then take turns choosing to either 'hit' (Drawing an additional card to try for a better hand.) or 'stay' keeping their current hand.\nIf the dealer and all players stay, the dealer's hand is revealed and each player with a better hand wins.\nThe value of each number card is equal to its number, face cards (King, Queen, Jack) have a value of 10, and aces have a value of either 1 or 11 (Whichever is best for your hand.)\nA 'blackjack' refers to a two card hand worth exactly 21 points. (An Ace and a 10 or face card.) blackjack is the strongest hand and beats all other hands even if they equal 21.\nIf the dealer gets blackjack, the round ends immediately and any player without a blackjack themselves loses.");
        IN.nextLine();
        System.out.print("Set number of players: ");
        playerCount = IN.nextInt() + 1;
        players = new Player[playerCount];

        for (int i = 0; i<playerCount; i++) {
            players[i] = new Player("Player " + (i+1), 10, (i+1 == playerCount)); // Could use array lists but I don't care enough to bother.
        }
        IN.nextLine();
        System.out.print("Set number of decks: ");
        numberOfCards = IN.nextInt() * 52;
        IN.nextLine();
    }

    public boolean getEnd() {return end;}


}