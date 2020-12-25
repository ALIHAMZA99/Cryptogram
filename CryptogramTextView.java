import java.util.Scanner;

public class CryptogramTextView {

	public static void main(String[] args) {

		// Scanner object is initialized to get the input.

		// Creating an instance of the class CryptogramModel.
		CryptogramModel model = new CryptogramModel();
		// Creating an instance of the class CryptogramController.
		CryptogramController controller = new CryptogramController(model);
		System.out.println(controller.getOutput());
		Scanner input = new Scanner(System.in);
		while (!controller.isGameOver()) {

			System.out.println("Enter a command (type help to see commands) :");
			String userInput = input.nextLine();
			String[] userCommand = userInput.split(" ");
			commands(controller, userCommand);
		}
		input.close();
	}

	/**
	 * The while is run till the game is over, which is when the
	 * controller.isGameOver() returns true, which is basically checking if the
	 * Users progress so far equals the answer then the while loop stops. Inside the
	 * while loop there are replacements happening for the characters of the
	 * encrypted string and the users tries to get the correct decrypted string.
	 * 
	 * @param CryptogramController object
	 * @param string               array of user command.
	 * @return This is void function and does not return anything.
	 */

	public static void commands(CryptogramController controller, String[] userCommand) {
		if (userCommand.length == 3) {
			char letterToReplace = userCommand[0].charAt(0);
			char replacementLetter = userCommand[2].charAt(0);
			gameControl(controller, letterToReplace, replacementLetter);

		} else {
			switch (userCommand[0]) {
			case "replace":
				char letterToReplace = userCommand[1].charAt(0);
				char replacementLetter = userCommand[3].charAt(0);
				gameControl(controller, letterToReplace, replacementLetter);
				break;
			case "freq":
				freq(controller);
				break;
			case "hint":
				hint(controller);
				break;
			case "exit":
				exit(controller);
				break;
			case "help":
				helpCommand();
				break;
			}
		}
	}

	/**
	 * This is a helper method for the users to make replacement of a character in
	 * the encrypted string with the character input from the user.
	 * 
	 * @param CryptogramController object
	 * @param character            of the letter to be replacement
	 * @param character            of the replacement letter,
	 */
	public static void gameControl(CryptogramController controller, char letterToReplace, char replacementLetter) {
		controller.makeReplacement(letterToReplace, replacementLetter);
		System.out.println();
		System.out.println(controller.getOutput());

		// If the user gets the correct answer this statement is run.
		if (controller.isGameOver()) {
			System.out.println("You got it!");
		}
	}

	/**
	 * This method returns the frequency of the letters in the encrypted string.
	 * 
	 * @param CryptogramController object
	 */
	public static void freq(CryptogramController controller) {
		System.out.println(controller.getLetterFrequency());
	}

	/**
	 * This is a helper method to give a hint to the users. It calls the hint method
	 * from the CryptogramController class. It prints a message if the game is over.
	 * 
	 * @param CryptogramController object
	 */
	public static void hint(CryptogramController controller) {
		System.out.println(controller.hint());
		if (controller.isGameOver()) {
			System.out.println("You got it!");
		}
	}

	/**
	 * This is a helper method to exit the cryptogram without finishing the game.
	 * 
	 * @param CryptogramController object
	 */
	public static void exit(CryptogramController controller) {
		if (!controller.isGameOver())
			System.out.println("Sorry to see you quit so early.");
		System.exit(0);
	}

	/**
	 * This is a helper method to print the commands possible for user to input.
	 */
	public static void helpCommand() {
		System.out.println("a. replace X by Y – replace letter X by letter Y in our attempted solution\n");
		System.out.println("   X = Y – a shortcut forth is same command");
		System.out.println("b. freq – Display the letter frequencies in the encrypted quotation (i.e., how many of \n"
				+ "   letter X appear) like:\n\n "
				+ "  A:3 B:8 C:4 D:0 E:12F:4G:6\n   (and so on, 7 per line for 4 lines)");

		System.out.println("c. hint – display one correct mapping that has not yet been guessed");
		System.out.println("d. exit – Ends the game early");
		System.out.println("e. help – List these commands");
	}
}
