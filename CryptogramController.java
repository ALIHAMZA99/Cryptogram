import java.util.HashMap;

/* 
* AUTHOR: Ali Hamza Noor
* ASSIGNMENT: Cryptogram
* COURSE: CSc 335 Fall 2020
* PURPOSE: This file as a controller for the cryptogram model.
* It uses the methods from the cryptogramModel.java to determine 
* when the game is over, to get the encrypted quote, decrypted quote
* and the users progress.
*/
class CryptogramController{
	CryptogramModel model = null;
	/*
	 * This is a constructor which takes the CryptogramModel object as a 
	 * parameter and sets it to the model for this class.
	 * @param CryptogramModel object
	 */
	public CryptogramController(CryptogramModel model) {
		this.model = model;
	}

	/**
	 * This method checks if the game is over yet or not. It
	 * uses the method getDecryptedString() and getAnswer() method
	 * from the CryptogramModel.java to compare the users version
	 * of the decrypted String and the actual answer. Compares it using
	 * the .equals() method and returns the truth value of it.
	 * @return a boolean indicating if the users answer is equal to actual answer.
	 */
	public boolean isGameOver() {
		return getUsersProgress().equals(model.getAnswer());
	}
	
	/**
	 * This method replaces the letters in the decryoted String by calling the
	 * setReplacement method from the CryptogramModel.java.
	 * @param character of the letter to be replacement
	 * @param character of the replacement letter,
	 */
	public void makeReplacement(char letterToReplace, char replacementLetter) {
		model.setReplacement(letterToReplace, replacementLetter);
	}

	/**
	 * This method returns the string representation of the encrypted string
	 * by calling the getEncryptedString() from the CryptogramModel.java, which 
	 * returns the string to this method. 
	 * @return the Encrypted string.
	 */
	public String getEncryptedQuote() {
		/* for the view to display */
		return model.getEncryptedString();
	}

	/**
	 * This method returns the string representation of the decrypted string
	 * so far by calling the getDecryptedString() method from the 
	 * CryptogramModel.java which returns the string. 
	 * @return the Decrypted string.
	 */
	public String getUsersProgress() {
		/* for the view to display */
		return model.getDecryptedString();
	}
	
	/**
	 * This method returns the frequency of characters in the decrypted string.
	 * It calls the getFrequency method which counts the number of characters
	 * in the encrypted string.
	 * @return the frequency of characters in the encrypted string.
	 */
	public String getLetterFrequency() {
		return model.getFrequency();
	}
	
	/**
	 * This method returns the string representation of the encrypted and 
	 * decrypted quotes formatted accordingly to limit the 80 characters
	 * @return the formatted encrypted and decrypted lines.
	 */
	public String getOutput() {
		return model.formattedString();
	}
	
	/**
	 * This method returns the decrypted string with one hint at a time. It 
	 * calls the hint method and traversing over the string to to replace 
	 * the correct letter.
	 * @return the decrypted string with a hint for the user.
	 */
	public String hint() {
		model.hint();
		return getOutput();
	}
	
	public HashMap<Character, Character> decryptMap() {
		return model.decryptMap();
	}
}