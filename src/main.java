import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.IOException;
import java.util.Dictionary;


public class main {

	// an object to get input from STDIN; usually user keyboard 
	static Scanner input = new Scanner(System.in);

	// dimensions of the room
	static int toul, arz;

	// arrays start from zero, so we need a room of (toul + 1) * (arz + 1)
	static int[][] room;

	// to store names of the stuff
	static String[] names;
	static int labelOfStuff;

	public static void main(String[] args) {

		main my_main = new main(); // an object to work with in the program

		System.out.printf("Toul: ");
		toul = input.nextInt();


		System.out.printf("arz: ");
		arz = input.nextInt();

		labelOfStuff = 1;
		room = new int[toul + 1][arz + 1];
		names = new String[100];
		my_main.init();

		do { // repeat for ever until 'E' is received

			System.out.printf("Enter your choice: ");
			char nextCommand = input.next().charAt(0);

			//			my_main.clearTheScreen();

			switch (nextCommand) {
			case 'A':
				my_main.addToRoom();
				break;
			case 'S':
				my_main.resultOfDecoration();
				break;
			case 'C':
				my_main.addCabinet();
				break;
			case 'R':
				my_main.clearTheRoom();
				break;
			case 'E':
				Runtime.getRuntime().exit(0);
				break;
			default:
				break;
			}

		} while (true);
	}
	private void init() {
		for (int i = 1; i <= toul; i++) {
			for (int j = 1; j <= arz; j++) {
				room[i][j] = 0;
			}
		}
	}
	private void clearTheRoom() {
		room = new int[toul + 1][arz + 1];
		names = new String[100];

		System.out.println("Done clearing the list.");
	}
	private void addCabinet() {

		System.out.printf("toule_kabinet: ");
		int toule_kabinet = input.nextInt();

		System.out.printf("Total number of possible cabinet to be installed is %d.\n", fitCabinet(toule_kabinet));
	}
	private int fitCabinet(int toule_kabinet) {

		int c1 = 0, c2 = 0;
		int numberOfCabinet = 0;

		for (int i = 1; i <= toul; i++) {
			
			
			if (room[i][1] == 0)
				c1++;
			else
				c1 = 0;
			
			if (c1 == toule_kabinet) {
				numberOfCabinet++;
				c1 = 0;
			}			
		}

		for (int i = 1; i <= toul; i++) {


			if (room[i][arz] == 0)
				c2++;
			else
				c2 = 0;

			if (c2 == toule_kabinet) {
				numberOfCabinet++;
				c2 = 0;
			}
		}

		c1 = 0;
		c2 = 0;

		for (int i = 1; i <= arz; i++) {
			
			
			if (room[1][i] == 0)
				c1++;
			else
				c1 = 0;

			if (c1 == toule_kabinet) {
				numberOfCabinet++;
				c1 = 0;
			}
		}
		
		for (int i = 1; i <= arz; i++) {



			if (room[toul][i] == 0)
				c2++;
			else
				c2 = 0;

			if (c2 == toule_kabinet) {
				numberOfCabinet++;
				c2 = 0;
			}
		}
		return numberOfCabinet;
	}
	private void resultOfDecoration() {
		System.out.println("Here's the result of the room occupants:");
		for (int i = 1; i <= toul; i++) {
			for (int j = 1; j <= arz; j++) {
				System.out.printf("(%d,%d): ", i, j);
				if (room[i][j] == 0)
					System.out.println("EMPTY!");
				else
					System.out.printf("Occupied by %s\n", names[room[i][j]]);
			}
		}
	}
	private boolean addToRoom() {


		System.out.println("Enter the information of the stuff below.");

		System.out.printf("name: ");
		String name = input.next();


		System.out.printf("toul_vasile: ");
		int toule_vasile = input.nextInt();
		// validation test
		if (!(toule_vasile > 0 && toule_vasile <= toul)) {
			System.out.printf("Sorry, your number is not between %d-%d\n", 1, toul);
			return false;
		}


		System.out.printf("arze_vasile: ");
		int arze_vasile = input.nextInt();
		//validation test
		if (!(arze_vasile > 0 && arze_vasile <= arz)) {
			System.out.printf("Sorry, your number is not between %d-%d\n", 1, arz);
			return false;
		}


		System.out.printf("start(x): ");
		int mogheiiat_x = input.nextInt();
		// validation test
		if (!(mogheiiat_x > 0 && mogheiiat_x <= toul)) {
			System.out.printf("Sorry, your number is not between %d-%d\n", 1, toul);
			return false;
		}


		System.out.printf("start(y): ");
		int mogheiiat_y = input.nextInt();
		// validation test
		if (!(mogheiiat_y > 0 && mogheiiat_y <= arz))
			return false;


		// if numbers are out of range
		if (!(mogheiiat_x + toule_vasile - 1<= toul && mogheiiat_y + arze_vasile - 1<= arz))
		{
			System.out.println("Sorry, index out of board range.");
			return false;
		}


		// can it be placed?
		boolean fit = fitSpace(toule_vasile, arze_vasile, mogheiiat_x, mogheiiat_y);

		// place it if it's possible
		boolean fitted = fitInRoom(fit, name, toule_vasile, arze_vasile, mogheiiat_x, mogheiiat_y);

		if (fitted) {
			System.out.printf("Successfully added %s in position (%d, %d)\n", name, mogheiiat_x, mogheiiat_y);
			return true;
		}
		else {
			System.out.printf("Sorry, (%d,%d) is taken by %d.\n",
					mogheiiat_x, mogheiiat_y, names[room[mogheiiat_x][mogheiiat_y]]);
			return false;
		}

	}

	private boolean fitSpace(int toule_vasile, int arze_vasile, int mogheiiat_x, int mogheiiat_y) {
		// check if the space is available
		for (int i = mogheiiat_x; i < mogheiiat_x + toule_vasile; i++)
		{
			for (int j = mogheiiat_y; j < mogheiiat_y + arze_vasile; j++)
			{
				if (room[i][j] != 0)
				{
					return false;
				}
			}
		}
		return true;
	}

	private boolean fitInRoom(boolean fit, String name, int toule_vasile, int arze_vasile, int mogheiiat_x, int mogheiiat_y) {
		if (fit)
		{
			// occupy the spaces
			for (int i = mogheiiat_x; i < mogheiiat_x + toule_vasile; i++)
			{
				for (int j = mogheiiat_y; j < mogheiiat_y + arze_vasile; j++)
				{
					room[i][j] = labelOfStuff;
				}
			}
			// save the name
			names[labelOfStuff] = name;
			labelOfStuff++;
			return true;
		}
		return false;
	}
}