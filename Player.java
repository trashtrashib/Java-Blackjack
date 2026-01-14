public class Player {
    
    private String name;
    private Card[] hand;
    private boolean is_dealer;

    // Constructor to initialize the needed handsize.
    public Player(String name, int handSize, boolean deals) {
        this.name = name;
        this.hand = new Card[handSize];
        this.is_dealer = deals;
        if (deals) {this.name = "Dealer";}
    }

    public String getName() {
        return name;
    }
    public void setName(String newName) {
        this.name = newName;
    }

    public Card[] getHand() {
        return hand;
    }
    public void setHand(Card[] newHand) {
        this.hand = newHand;
    }

    public int getHandSize() {
        for (int i = 0; i<hand.length; i++) {
            if (hand[i] == null) {return i;}
        }
        return hand.length;
    }
    // Set the first empty index in hand array to the top card of the deck;
    public void drawCard() {
        int i = 0;
        while (this.hand[i] != null) {i++;}
        this.hand[i] = DeckofCards.drawCard();
    }

    // Print the cards in hand.
    public void showHand() {
        System.out.println(name + "'s Hand:");
        for (int i = 0; i<hand.length; i++) {
            if (hand[i] != null) {
            System.out.println(hand[i].asText());}
        }
        System.out.println();
    }

    public boolean getDeal() {return is_dealer;}

    public int getValue() {
        int total = 0;
        int aces = 0;

        // Add values for each card, setting each face card value to 10 and noting each ace we encounter.
        for (int i = 0; i<this.hand.length;i++)
        {
            if (this.hand[i] != null) {

                if (this.hand[i].getValue() > 10) {total += 10;} else if (this.hand[i].getValue() == 1) {total++; aces++;} else {total += this.hand[i].getValue();}
            
            }
        }

        // Handle special ace rule.
        while (aces > 0 && total+10 <= 21) {
            total += 10;
            aces--;
        }

        return total;
    }


}