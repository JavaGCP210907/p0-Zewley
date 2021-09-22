package com.revature;

/******************************************************************************
ONE MONTH TO ESCAPE THE SPACE PORT

Welcome to "ONE MONTH TO ESCAPE THE SPACE PORT", a text adventure game in the 
vein of classics such as Zork, Hitchhikers Guide to the Galaxy,
Legend of the Red Dragon, and unpaid bills.

The goal of the game is to purchase space ship parts and flee the space port 
by the end of the month. 

You're prompted to select a class, and each day you have a limited number of
choices to make to earn enough credits to purchase ship parts and fuel.


Notes:

Log at:
character creation
character load
purchase
deposit
loss
end of day
game completion






*******************************************************************************/

//1 thief
//2 soldier
//3 engineer
//4 assassin

import java.util.Scanner;

public class Main {

// Making the player a static inner class allows it to be instantiated outside
// the mainclass
	static class Player {
		String[] roles = { "Invalid", "Thief", "Fighter", "Engineer", "Assassin" };
// Gamestate
		int saveID;
		int endReached;
		boolean isDay = true;
		int day;

//Player inventory
		String name;
		int role;
		int credits;
		boolean hasFrame;
		boolean hasEngine;
		boolean hasFuel;

//Store inventory
		int storeCredits;
		boolean storeFrame;
		boolean storeEngine;
		boolean storeFuel;

		public Player(String inName, int choice) {
			day = 0;
			credits = 0;
			role = choice;
			name = inName;
			hasFrame = false;
			hasEngine = false;
			hasFuel = false;
			endReached = 0;
			isDay = true;
			storeCredits = 10;
		}

		public Player() {
		}

//Work action, advances to night
		public void work() {
			if (role == 1) {
				System.out.println("You made one whole credit Mr Big Bucks");
				credits++;
			}
			isDay = false;
		}

//Role specific actions
		public void overTime() {
//Thief action
			if (this.role == 1) {
				System.out.println("Your sneakthiefing landed you 5 credits.");
				System.out.println("To be honest, I'm proud of you.");
				this.credits += 5;
			}

		}

		public void shipStore() {
			int choice = 0;
			System.out.println("---Welcome to the ship store," + " the store for ships----");
			System.out.println(this.credits + " credits on hand.");

			if (!this.hasFrame) {
				System.out.println("Press 1 to purchase the EX66 frame " + "for 30 credits");
			}
			if (!this.hasEngine) {
				System.out.println("Press 2 to purchase the EX66 engine for" + " 20 credits");
			}
			if (!this.hasFuel) {
				System.out.println("Press 3 to purchase the EX66 fuel cylinder" + " for 10 credits");
				System.out.println("Or... press 4 to swipe the fuel cylinder " + "from the shop");
				if (this.role == 1) {
					System.out.println("You're totally fast enough to" + " swipe the fuel cylinder");
				}
			}
			System.out.println("Or you can press 5 to leave");

			if (this.role == 1) {
				System.out.println("You could even... press 6 steal credits from the store");
				System.out.println("The developer does not endorse the cool crime of stealing");
			}

			Scanner scan = new Scanner(System.in);
			while (choice < 1 || choice > 6) {
				choice = scan.nextInt();
			}

			if (choice == 1) {
				if (this.credits >= 30) {
					this.credits -= 30;
					this.hasFrame = true;
					System.out.println("You got the ship frame!");
				} else
					System.out.println("Not enough credits");
			}
			if (choice == 2) {
				if (this.credits >= 20) {
					this.credits -= 20;
					this.hasEngine = true;
					System.out.println("You got the ship engine!");
				} else
					System.out.println("Not enough credits");
			}
			if (choice == 3) {
				if (this.credits >= 10) {
					this.credits -= 10;
					this.hasFuel = true;
					System.out.println("You got the ship fuel!");
				} else
					System.out.println("Not enough credits");
			}
			if (choice == 4) {
				if (this.role == 1) {
					this.hasFuel = true;
					System.out.println("You were quick enough to steal the fuel!");
					System.out.println("...and that's even cooler than buying it");
				}
			}
			if (choice == 6) {
				if (this.role == 1) {
					this.credits += this.storeCredits;
					System.out.println("You stole " + this.storeCredits + " credits you sly dog you");
					this.storeCredits = 0;
				}
			}
		}

// Start of day
		public int dayChoice() {
			int choice = 0;
			this.storeCredits += 10;
			System.out.println("----------------Day: " + this.day + " out of 30----------------");
			System.out.println("Wake up and smell the artificial atmosphere.");

			System.out.println("You have " + this.credits + " credits.");
			if (!this.hasFrame) {
				System.out.println("You need the ship frame");
			}
			if (!this.hasEngine) {
				System.out.println("You need the ship engine");
			}
			if (!this.hasFuel) {
				System.out.println("You need fuel");
			}

			if (this.hasFrame && this.hasEngine && this.hasFuel) {
				System.out.println("-----VICTORY--------");
				System.exit(0);
			}

			System.out.println("What are you gonna do this morning?");
			System.out.println("1: Go to work");
			System.out.println("2: Go to the ship store");

			Scanner scan = new Scanner(System.in);
			while (choice < 1 || choice > 2) {
				choice = scan.nextInt();
			}

			return choice;
		}

// Day time action, advances to night
		public void dayTime(int dayChoice) {
			if (dayChoice == 1) {
				this.work();
			}
			if (dayChoice == 2) {
				this.shipStore();
			}
		}

// Night time action choice
		public int nightChoice() {
			int choice = 0;
			System.out.println("--------------Now its night time.------------");
			System.out.println("Indulge your hobby or go to sleep.");
			System.out.println("What's it gonna be?");
			System.out.println("1: Go to sleep");
			System.out.println("2: " + this.roles[this.role] + " stuff");
			System.out.println("3: Advance to end of Month");

			Scanner scan = new Scanner(System.in); // Create a Scanner object
			while (choice < 1 || choice > 3) {
				choice = scan.nextInt(); // Read user input
			}
			System.out.println("Good choice");
			return choice;
		}

//Night time action,
		public void nightTime(int nightChoice) {
			if (nightChoice == 2) {
				this.overTime();
			}
			if (nightChoice == 3) {
				this.day = 30;
			}
			this.goToBed();
		}

//Ends day, increments day counter, updates player table, advances day
		public void goToBed() {
			this.day++;
			this.isDay = true;
//Update player table
//log
		}
	}

// creates a new a character and intro text
	static void newCharacter(Player player) {
		int choice = 0;
		String nameInput;

		System.out.println(
				"You look up at the artifical sky of the spaceport and" + " hope things will be different this time.");
		System.out.println("Turning your attention to your equipment case you" + " find your choice of: ");
		System.out.println("1: A thief's lockpicks, for an honest days work.");
		System.out.println("2: A fighter's pair of cyber boxing gloves, " + "for cyber boxing. ");
		System.out.println("3: An engineer's multimeter.");
		System.out.println("4: An assassin's gun, for humanitarian purposes.");
		System.out.println("Which do you pickup?");

		Scanner scan = new Scanner(System.in);
		while (choice < 1 || choice > 4) {
			choice = scan.nextInt();
		}
		System.out.println("Good choice");
		System.out.println("What was your name again?");
		nameInput = scan.next();
		player.name = nameInput;
		player.role = choice;
		System.out.println(player.name + " the " + player.roles[player.role] + " eh?");
		System.out.println("Well, you're in some trouble");

//System.out.println(player.name + " is a "+ player.roles[player.role]);

	}

//load character
	static void loadCharacter(Player player) {
// read table
// print character ID#
// continueGame(player)
		System.out.println("To be implemented");
	}

// Prompts user to create a character or load a character
	static Player startUp() {
		int choice = 0;
		String nameInput;
		Player player = new Player();
		System.out.println("1 to create a new character");
		System.out.println("2 to load a character");

		Scanner scan = new Scanner(System.in); // Create a Scanner object

		while (choice < 1 || choice > 2) {
			choice = scan.nextInt(); // Read user input
		}
		if (choice == 1) {
			newCharacter(player);
		} else {
			loadCharacter(player);
		}

		return player;
	}

	public static void main(String[] args) {
		int dayChoice;
		int nightChoice;

// System.out.println(player.name + " is WORKING as a " +
// player.roles[player.role]);
// System.out.println(" You chose to.... " + dayAction);

//Declare player object and instantiate a new game or load previous game
		Player player = startUp();

//System.out.println("This is day: " + player.day + " out of 30 days");
		while (player.day < 30) {
//Prompts player for daytime choice and runs day logic
			dayChoice = player.dayChoice();
			player.dayTime(dayChoice);

//prompts player for night time choice and runs night logic (tm)
			nightChoice = player.nightChoice();
			player.nightTime(nightChoice);
		}

		System.out.println("Considering time is up, I hope you got the parts");
	}
}