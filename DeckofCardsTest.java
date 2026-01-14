public class DeckofCardsTest {
    public static void main(String[] args) {
        
        DeckofCards manager = new DeckofCards();

        //
        manager.intro();
        

        // Set up the deck.
        manager.deckBuild();
        manager.shuffle();

        // Deal 2 cards to each player. Read out hands and detect blackjacks.
        manager.deal();

        while (!manager.getEnd()) {
            
            manager.round();

        }


    }
}