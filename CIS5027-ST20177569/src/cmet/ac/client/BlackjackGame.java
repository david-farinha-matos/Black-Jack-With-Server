package cmet.ac.client;
import java.util.Scanner;


public class BlackjackGame {
	
	private Scanner theScanner = new Scanner(System.in);
	private int users; 
	private Player[] players;
	private Deck deck;
	private Dealer dealer = new Dealer();

	// Starts game and displays the rules
	public void initializeGame(){
		String names;
		System.out.println("Welcome to Blackjack!");
		System.out.println("");
		System.out.println("  The Blackjack Rules: ");
		System.out.println("	-Each player is dealt 2 cards which each player can see.");
		System.out.println("	-Each player can either deal or hold depending if they want another card or not.");
		System.out.println("	-The players cards are then added up for their total score, if the total is over 21 they are bust.");
		System.out.println("	-The dealer now is repeatedly dealt a card until their score is greater than 25.");
		System.out.println("	-The winner is either the dealer or the players depending on if they beat the dealers score or not.");
		System.out.println("");
		
		// Gets the amount of players and creates them
		do {
			System.out.print("How many people are playing (1-4)? ");
			users = theScanner.nextInt();
			

		} while (users > 4 || users < 0);

		players = new Player[users];
		deck = new Deck();

		// Asks for player names and assigns them
		for (int i = 0; i < users; i++) {
			System.out.print("What is player " + (i + 1) + "'s name? ");
			names = theScanner.next();
			players[i] = new Player();
			players[i].setName(names);
		}
	}
	
	// Shuffles the deck
	public void shuffle() throws InvalidDeckPositionException, InvalidCardSuitException, InvalidCardValueException {
		deck.shuffle();
		
	}
	
	// Deals the cards to the players and dealer
	public void dealCards(){
		for (int j = 0; j < 2; j++) {
			for (int i =0; i < users; i++) {
				//if(players[i].getBank() > 0) {
				players[i].addCard(deck.nextCard());
				//}
			}

			dealer.addCard(deck.nextCard());
		}
	}
	
	// Initial check for dealer or player Blackjack
	public void checkBlackjack(){
			for (int i =0; i < users; i++) {
				if (players[i].getTotal() == 21 ) {
					System.out.println(players[i].getName() + " has blackjack!");
				}
			}
		}		
	
	// This code takes the user commands to hit or stand
	public void hitOrStand() {
		String command;
		char c;
		for (int i = 0; i < users; i++) {
				System.out.println();
				System.out.println(players[i].getName() + " has " + players[i].getHandString());

				do {
					do {
						System.out.print(players[i].getName() + " Do you wish to deal another card? (y for yes, n for no)");
						command = theScanner.next();
						c = command.toUpperCase().charAt(0);
					} while ( ! ( c == 'Y' || c == 'N' ) );
					if ( c == 'Y' ) {
						players[i].addCard(deck.nextCard());
						System.out.println(players[i].getName() + " has " + players[i].getHandString());
					}
				} while (c != 'N' && players[i].getTotal() <= 21 );
		}
	}
	
	// Code for the dealer to play
	public void dealerPlays() {
		boolean isSomePlayerStillInTheGame = false;
		for (int i =0; i < users && isSomePlayerStillInTheGame == false; i++){
				isSomePlayerStillInTheGame = true;
		}
		if (isSomePlayerStillInTheGame) {
			dealer.dealerPlay(deck);
		}
	}
	
	// This code calculates all possible outcomes and adds or removes the player bets
	public void checkWinners() {
		System.out.println();

		for (int i = 0; i < users; i++) {
				if( players[i].getTotal() > 21 ) {
					System.out.println(players[i].getName() + " has busted");
				} else if ( players[i].getTotal() == dealer.calculateTotal() ) {
					System.out.println("The Dealer has won due to a draw");
				} else if ( players[i].getTotal() < dealer.calculateTotal() && dealer.calculateTotal() <= 21 ) {
					System.out.println(players[i].getName() + " has lost");
				} else if (players[i].getTotal() == 21) {
					System.out.println(players[i].getName() + " has won with blackjack!");
				} else {
					System.out.println(players[i].getName() + " has won");
					
				}
		}

	}

	// This prints the players hands
	public void printPlayerStatus() {
		for (int i = 0; i < users; i++) {
			System.out.println(players[i].getName() + " has " + players[i].getHandString());
		}
		System.out.println("Dealer has " + dealer.getHandString(true));
	}

	// This code resets all hands
	public void clearHands() {
		for (int i = 0; i < users; i++) {
			players[i].clearHand();
		}
		dealer.clearHand();

	}
	
	// This decides to force the game to end when all players lose or lets players choose to keep playing or not
	public boolean playAgain() {
		String command;
		char c;
		Boolean playState = true;
		if(forceEnd()) {
			playState = false;	
		}
		else {
			do {
				System.out.println("");
				System.out.print("Do you want to play again (Y)es or (N)o? ");
				command = theScanner.next();
				c = command.toUpperCase().charAt(0);
			} while ( ! ( c == 'Y' || c == 'N' ) );
			if(c == 'N')
			{
				playState = false;
			}
		}
		return playState;
	}
	
	// This says true or false to forcing the game to end
	public boolean forceEnd() {
		boolean end = false;
		int endCount = 0;
		
		if(endCount == users)
		{
			end = true;
		}
		if(end)
		{
			System.out.println("");
			System.out.println("All players have lost and the game ends.");
		}
		
		return end;
	}
	
	// This is the end game code for when all players are out of the game or players decide to stop playing
		public void endGame() {
			System.out.println("Thank you for playing!");
		}


} //End class