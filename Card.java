public class Card {
    
    private String face;
    private String suit;
    private int value;

    public int getValue() {return value;}

    public String getFace() {
        return face;
    }
    public void setFace(String face) {
        this.face = face;
    }
    public String getSuit() {
        return suit;
    }
    public void setSuit(String suit) {
        this.suit = suit;
    }

    // Print cards name.
    public String asText() {
        return this.face + " of " + this.suit;
    }

    public Card(String face,String suit,int value) {
        this.face = face;
        this.suit = suit;
        this.value = value;
    }

}
