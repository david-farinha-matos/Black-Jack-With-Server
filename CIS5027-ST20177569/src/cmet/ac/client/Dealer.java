package cmet.ac.client;
import java.io.Serializable;

public class Dealer implements Serializable
{

	private Hand hand = new Hand();

	// Determines if dealer has a blackjack
	public boolean isBlackjack(){
		if (hand.calculateTotal() == 21){
			return true;
		} else {
			return false;
		}
	}
	
	// This automates the dealer's play
	public void dealerPlay(Deck deck){
		System.out.println();
		while (hand.calculateTotal() <= 15) {
			System.out.println("Dealer has " + hand.calculateTotal()+ " and hits");
			hand.addCard(deck.nextCard());
		} 
		if ( hand.calculateTotal() > 21) {
			System.out.println("Dealer busts. " + this.getHandString(true));
		} else {
			System.out.println("Dealer stands. " + this.getHandString(true));
		}
	}
	
	// Adds a card o the dealer's hand
	public void addCard(Card card) {
		hand.addCard(card);

	}
	
	// Gets the dealer's hand as a string
	public String getHandString(boolean isDealer) {
		String str = "Cards:" + hand.toString(isDealer);

		return str;
	}
	
	// Calculates the dealer's hand total
	public int calculateTotal() {
		return hand.calculateTotal();
	}
	
	// Clears the dealer's hand
	public void clearHand() {
		hand.clearHand();
	}
	
} //End class