
/* 
* * AUTHOR: Ali Hamza Noor
* ASSIGNMENT: Cryptogram
* COURSE: CSc 335 Fall 2020
* PURPOSE: This file models the cryptogram and its functions. The file
* is read for and a random quote is selected from it. The encrypted message
* of the quote is formed and the keys are assigned for decryption and encryption
* maps. It extends the Observable class which means it can be observed by a class
* which implements Observer.
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.List;
import java.util.Observable;
import java.util.Random;

class CryptogramModel extends Observable {
	// private variable(s) to store the answer, encryption key, decryption key
	private HashMap<Character, Character> encrypt = new HashMap<Character, Character>();
	private HashMap<Character, Character> decrypt = new HashMap<Character, Character>();
	private HashMap<Character, Integer> letterFreq = new HashMap<Character, Integer>();
	private Character[] charArray = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private Character[] charArraySorted = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private List<Character> list = Arrays.asList(charArray);
	private String answer = "";
	private String line = "";

	/**
	 * This is a constructor which shuffles the characters list and converts it to
	 * the array of characters. Then, the helper function is called to get a random
	 * line and the for loop is used to initialize the decryption hashmap with the
	 * characters.
	 */
	public CryptogramModel() {
		/*
		 * Read a random line from the quotes.txt file. Make the keys and set the answer
		 */
		Collections.shuffle(list);
		list.toArray(charArray);
		this.line = randomLine();
		this.answer = line;
		mapping(line);
		for (int i = 0; i < charArray.length; i++) {
			decrypt.put(charArray[i], ' ');
		}
	}

	/**
	 * This is a helper function called by the constructor to read a text file and
	 * return a random quote from that particular file. This is done by reading the
	 * quotes into a list and then using a random integer to access a random quote
	 * which is eventually returned.
	 * 
	 * @return the random string quote.
	 * @throws FileNotFoundException when a file is not found.
	 */
	private String randomLine() {
		Scanner file = null;
		try {
			file = new Scanner(new File("quotes.txt"));
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Forming a quotes list using the quotes.text file.
		List<String> quoteList = new ArrayList<String>();
		while (file.hasNext()) {
			String quote = file.nextLine();
			quoteList.add(quote);
		}
		file.close();
		Random rand = new Random();
		int index = rand.nextInt(quoteList.size());
		return quoteList.get(index).toUpperCase();
	}

	/**
	 * This is a helper function called by the contructor to map the keys for the
	 * Hashmaps of decryption and encryption to the values. This is done by looping
	 * over the string and mapping it to the characters from the character array,
	 * while at the same time assigning empty space for the mapping of the
	 * decryption hashmap.
	 * 
	 * @param the quote to be encrypted.
	 */
	private void mapping(String line) {
		HashSet<Character> setChar = new HashSet<Character>();
		for (int k = 0; k < line.length(); k++) {
			if (line.charAt(k) == ' ' || line.charAt(k) == '-' || line.charAt(k) == ',' || line.charAt(k) == '.'
					|| line.charAt(k) == ':' || line.charAt(k) == '\'' || line.charAt(k) == '?') {
				encrypt.put(line.charAt(k), line.charAt(k));
				decrypt.put(line.charAt(k), line.charAt(k));
			}

			for (int i = 0; i < line.length(); i++) {
				if (encrypt.get(line.charAt(i)) == null) {
					int index = (int) (Math.random() * charArray.length);
					int num = 0;
					while (num < 20) {
						if (line.charAt(i) == charArray[index] || setChar.contains(charArray[index])) {
							index = (int) (Math.random() * charArray.length);
						}
						num++;
					}
					if (!setChar.contains(charArray[index])) {
						encrypt.put(line.charAt(i), charArray[index]);
						setChar.add(charArray[index]);
						decrypt.put(charArray[index], ' ');
					}
				}
			}
		}
	}

	/**
	 * This method maps the characters to the number of occurences in the string.
	 * 
	 * @return the hashmap with character as jeys and integer as values.
	 */
	private HashMap<Character, Integer> freq() {
		String encryptedString = getEncryptedString();
		for (int i = 0; i < encryptedString.length(); i++) {
			char letter = encryptedString.charAt(i);
			if (letter != ' ' && letter != '-' && letter != ',' && letter != '.' && letter != ':' && letter != '\'') {
				if (!letterFreq.containsKey(letter)) {
					letterFreq.put(letter, 1);
				} else {
					letterFreq.put(letter, letterFreq.get(letter) + 1);
				}
			}
		}
		return letterFreq;
	}

	/**
	 * This method returns the frequency of characters in the encrypted string as a
	 * string.
	 * 
	 * @return the String representation of the frequency.
	 */
	public String getFrequency() {
		String frequency = "";
		letterFreq = freq();
		int count = 0;
		for (char key : letterFreq.keySet()) {
			frequency += key + ": " + letterFreq.get(key) + " ";
			count++;
			if (count % 7 == 0) {
				frequency += "\n";
			}
		}
		return frequency;
	}

	/**
	 * This method returns the frequency of characters in the encrypted string as a
	 * string.
	 * 
	 * @return the String representation of the frequency.
	 */
	public String[] getFrequencyGUI() {
		String frequency = "";
		String[] freqArr = new String[2];
		letterFreq = freq();
		int count = 0;
		for (char key : charArraySorted) {
			if (letterFreq.keySet().contains(key)) {
				frequency += key + ": " + letterFreq.get(key) + "\n";
			} else {
				frequency += key + ": " + "0" + "\n";
			}
			count++;
			if (count == 13) {
				freqArr[0] = frequency;
				frequency = "";
			}
		}
		freqArr[1] = frequency;
		return freqArr;
	}

	/**
	 * This function sets the replacement character for the decrytption hashmap by
	 * using the put() method for the hashmaps. The parameters given are the letter
	 * to be replaced and the character to replace it.
	 * 
	 * @param an encrypted character to be replaced
	 * @param a  replacement character
	 */
	public void setReplacement(char encryptedChar, char replacementChar) {
		/* add to our decryption attempt */
		if (encryptedChar != ' ' && encryptedChar != '-' && encryptedChar != ',' && encryptedChar != '.'
				&& encryptedChar != ':' && encryptedChar != '\'')
			decrypt.put(encryptedChar, replacementChar);
		setChanged();
		notifyObservers(replacementChar);
	}

	/**
	 * This method returns a string, which is the encrypted quote. The StringBuilder
	 * is used to form the string which is encrypted. The string is looped over and
	 * the encrytion hashmap is used to get the value for the key and the string
	 * formed is returned.
	 * 
	 * @return the encrypted string.
	 */
	public String getEncryptedString() {
		/* return the fully encrypted string */
		StringBuilder encrypted = new StringBuilder();
		for (int i = 0; i < line.length(); i++) {
			encrypted.append(encrypt.get(line.charAt(i)));
		}
		return encrypted.toString();
	}

	/**
	 * This method returns a string, which is the decrypted quote. The StringBuilder
	 * is used to form the string which is decrypted. The string is looped over and
	 * the decrytion hashmap is used to get the value for the key and the string
	 * formed is returned.
	 * 
	 * @return the decrypted string.
	 */
	public String getDecryptedString() {
		/*
		 * return the decrypted string with the guessed replacements or spaces
		 */
		String encryptedString = getEncryptedString();
		StringBuilder decrypted = new StringBuilder();
		for (int i = 0; i < encryptedString.length(); i++) {
			decrypted.append(decrypt.get(encryptedString.charAt(i)));
		}

		return decrypted.toString();
	}

	/**
	 * This method returns the actual quote, which is the answer to the Cryptogram.
	 * 
	 * @return the correct decrypted answer.
	 */
	public String getAnswer() {
		/* return the answer */
		return answer;
	}

	/**
	 * This method sets the correct replacement character just to give a hint.
	 */
	public void hint() {
		int hinted = 0;
		for (char s : encrypt.keySet()) {
			if (Character.isLetter(s))
				if (decrypt.get(encrypt.get(s)) == ' ' && Character.isLetter(s)) {
					hinted++;
					if (hinted == 1)
						setReplacement(encrypt.get(s), s);
				}
		}
	}

	/**
	 * This method returns the formatted string with a 80 character limit.
	 * 
	 * @return the encrypted and decrypted strings together.
	 */
	public String formattedString() {
		String encrypted = getEncryptedString();
		String decrypted = getDecryptedString();
		String finalOutput = "";
		int i = 0;
		while (i < encrypted.length()) {
			if (i + 80 < encrypted.length()) {
				finalOutput += decrypted.substring(i, i + 80) + "\n" + encrypted.substring(i, i + 80) + "\n" + "\n";

			} else {
				finalOutput += decrypted.substring(i) + "\n" + encrypted.substring(i) + "\n" + "\n";
			}
			i += 80;
		}
		return finalOutput;
	}

	public HashMap<Character, Character> decryptMap() {
		return decrypt;
	}

}
