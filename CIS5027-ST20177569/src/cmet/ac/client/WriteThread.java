package cmet.ac.client;

import java.io.*;
import java.net.*;

/**
 * This thread is responsible for reading user's input and send it to the
 * server. It runs in an infinite loop until the user types 'bye' to quit.
 */
public class WriteThread extends Thread {
	private PrintWriter writer;
	private Socket socket;
	private ChatClient client;

	public WriteThread(Socket socket, ChatClient client) {
		this.socket = socket;
		this.client = client;
		try {
			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
		} catch (IOException ex) {
			System.out.println("Error getting output stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void run() {
		Console console = System.console();
		String userName = console.readLine("\nEnter your name: ");
		client.setUserName(userName);
		writer.println(userName);
		String text;
		do {
			text = console.readLine("[" + userName + "]: ");
			writer.println(text);
			BlackjackGame mygame = new BlackjackGame();

			mygame.initializeGame();
			do {
				try {
					mygame.shuffle();
				} catch (InvalidDeckPositionException | InvalidCardSuitException | InvalidCardValueException e) {
					e.printStackTrace();
				}
				mygame.dealCards();
				mygame.printPlayerStatus();
				mygame.checkBlackjack();
				mygame.hitOrStand();
				mygame.dealerPlays();
				mygame.checkWinners();
				mygame.clearHands();
			} while (mygame.playAgain());

			try {
				FileOutputStream outObjectFile = new FileOutputStream("objOut", false);

				ObjectOutputStream outObjectStream = new ObjectOutputStream(outObjectFile);

				outObjectStream.writeObject(mygame);

				outObjectStream.flush();
				outObjectStream.close();
			} catch (FileNotFoundException fnfException) {
				System.out.println("No file");
			} catch (IOException ioException) {
				System.out.println("bad IO");
			}

			try {
				FileInputStream inObjectFile = new FileInputStream("objOut");

				ObjectInputStream inObjectStream = new ObjectInputStream(inObjectFile);

				Card myNewCard = (Card) inObjectStream.readObject();

				System.out.println(myNewCard);

			} catch (FileNotFoundException fnfException) {
				System.out.println("No File");
			} catch (IOException ioException) {
				System.out.println("IO no good");
			}

			catch (ClassNotFoundException cnfException) {
				System.out.println("This is not a Card.");
			}
		} while (!text.equals("bye"));
		try {
			socket.close();
		} catch (IOException ex) {
			System.out.println("Error writing to server: " + ex.getMessage());
		}
	}
}
