
/* 
* * AUTHOR: Ali Hamza Noor
* ASSIGNMENT: Cryptogram
* COURSE: CSc 335 Fall 2020
* PURPOSE: This file is the main file which uses the command line
* arguments to run the cryptogram as a textview or graphical user
* interface depending on the argument given.
*/

import javafx.application.Application;

public class Cryptograms {
	public static void main(String[] args) {


		if(args[0].equals("-text")) {
			CryptogramTextView.main(args);
		}
		
		else if(args[0].equals("-window")) {
			Application.launch(CryptogramGUIView.class, args);
		}
		
		else {
			Application.launch(CryptogramGUIView.class, "-window");
		}
	}
}