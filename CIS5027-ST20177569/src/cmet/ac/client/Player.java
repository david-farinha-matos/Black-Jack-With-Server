package cmet.ac.client;

import java.io.Serializable;

public class Player implements Serializable {
	private String name;
	private Hand hand;

	// Creates a player object
	public Player() {
		hand = new Hand();

	}

	// Sets a player's name
	public void setName(String name1) {
		name = name1;
	}

	// Gets a player's name
	public String getName() {
		return name;
	}

	// Gets a player's hand total
	public int getTotal() {
		return hand.calculateTotal();
	}

	// Adds a card to a player's hand
	public void addCard(Card card) {
		hand.addCard(card);
	}

	// Gets the player's cards to print as a string
	public String getHandString() {
		String str = "Cards:" + hand.toString();
		return str;
	}

	// Clears a player's hand
	public void clearHand() {
		hand.clearHand();
	}

} // End class
