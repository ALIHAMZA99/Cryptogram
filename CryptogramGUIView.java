import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.HashMap;

/* 
* AUTHOR: Ali Hamza Noor
* ASSIGNMENT: Cryptogram
* COURSE: CSc 335 Fall 2020
* PURPOSE: This file implements the GUI of the cryptogram using the 
* javafx. It also implements the Observer to observe the changes in the
* model and reflect the changes in the view. 
*/

public class CryptogramGUIView extends Application implements Observer {

	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 *	This method organizes the layout of the gui uses the controller to
	 *	call the appropriate methods in order to implement the buttons and
	 *	update them when needed. In the end, it shows the output on the window.
	 *	@param stage It takes the Stage object as the parameter.
	 */
	@Override
	public void start(Stage stage) {
		CryptogramModel model = new CryptogramModel();
		CryptogramController controller = new CryptogramController(model);
		model.addObserver(this);
		String encrypted = controller.getEncryptedQuote();
		String[] freqArr = model.getFrequencyGUI();
		HashMap<Character, Character> decrypt = model.decryptMap();

		BorderPane window = new BorderPane();
		updateButtons(window, encrypted, decrypt, controller);
		setButtons(window, freqArr, encrypted, decrypt, controller);
		Scene scene = new Scene(window, 900, 400);
		stage.setTitle("Cryptogram");
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * This method updates the textfields in the gui. It makes sure that the 
	 * textFields and Labels are properly formatted.
	 * @param window This is a BorderPane object.
	 * @param encrypted This is the encrypted string to be shown.
	 * @param decrypt This is the HashMap of decrypted letters.
	 * @param controller The is the CryptogramController object.
	 * @return It returns the BorderPane object.
	 */
	public BorderPane updateButtons(BorderPane window, String encrypted, HashMap<Character, Character> decrypt,
			CryptogramController controller) {

		GridPane gridpane = new GridPane();
		window.setCenter(gridpane);
		int k = 0;
		ArrayList<TextField> listText = new ArrayList<TextField>();
		ArrayList<Label> listLabel = new ArrayList<Label>();
		for (int j = 0; j < Math.ceil(encrypted.length() / 30) + 1; j++) {
			for (int i = 0; i < 30; i++) {
				VBox fields = new VBox();
				if (k == encrypted.length()) {
					break;
				}
				int check = k + 1;
				int count = 0;
				if (check < encrypted.length())
					while (encrypted.charAt(check) != ' ' && check < encrypted.length()) {
						check++;
						count++;
						if (check == encrypted.length())
							break;
					}
				if (i + count >= 30) {
					break;
				}
				char letter = encrypted.charAt(k);
				if ((i == 29 && letter == ' ')) {
					k++;
				} else if ((i == 0 && letter == ' ')) {
					k++;
					i--;
				} else {
					TextField txt = new TextField();
					if (letter == ' ' || letter == '-' || letter == ',' || letter == '.' || letter == ':'
							|| letter == '\'' || letter == '?') {
						txt.setText(Character.toString(decrypt.get(letter)).trim());
						txt.setPrefWidth(28);
						txt.setDisable(true);
					} else {
						txt.setText(Character.toString(decrypt.get(letter)).trim());
						txt.setPrefWidth(28);
					}
					listText.add(txt);
					txt.setOnAction((event) -> {
						String guess = ((TextField) (fields.getChildren().get(0))).getText();
						char label = ((Label) (fields.getChildren().get(1))).getText().charAt(0);
						controller.makeReplacement(label, guess.charAt(0));
						for (int rows = 0; rows < listText.size(); rows++) {
							if (listLabel.get(rows).getText().charAt(0) == label)
								listText.get(rows).setText(Character.toString(guess.charAt(0)));
							((TextField) (fields.getChildren().get(0))).setText(Character.toString(guess.charAt(0)));
						}
						event.consume();
					});

					fields.getChildren().add(txt);
					fields.setAlignment(Pos.CENTER);
					Label label = new Label(Character.toString(letter));
					fields.getChildren().add(label);
					listLabel.add(label);
					gridpane.add(fields, i, j);
					k++;
				}
			}
		}
		if(controller.isGameOver()) {
			for (int rows = 0; rows < listText.size(); rows++) {
				listText.get(rows).setDisable(true);
			}
		}
		return window;
	}

	/**
	 * This method sets the position of the buttons and gives the 
	 * functionality to the buttons like the hint button, show hints
	 * box and the new puzzle. The window gets updated and shown.
	 * @param window The BorderPane object
	 * @param freqArr The frequency of the letters.
	 * @param encrypted The encrypted string to be shown as label
	 * @param decrypt The HashMap of the decrypted string.
	 * @param controller The CryptogramController Object.
	 */
	public void setButtons(BorderPane window, String[] freqArr, String encrypted, HashMap<Character, Character> decrypt,
			CryptogramController controller) {
		VBox buttons = new VBox();
		Button puzzle = new Button("New Puzzle");
		Button hint = new Button("Hint");
		CheckBox show = new CheckBox("Show Hints");

		Label label = new Label();
		Label label2 = new Label();

		show.setOnAction((event) -> {
			if (show.isSelected()) {

				label.setText(freqArr[0]);
				label2.setText(freqArr[1]);
			} else {
				label.setText("");
				label2.setText("");
			}
		});
		puzzle.setOnAction((event) -> {
			Stage stage = (Stage) puzzle.getScene().getWindow();
			stage.close();
			start(stage);
		});

		hint.setOnAction((event) -> {
			controller.hint();
			updateButtons(window, encrypted, decrypt, controller);
		});

		FlowPane flow = new FlowPane(Orientation.VERTICAL, 20, 20);
		flow.getChildren().addAll(label, label2);
		buttons.getChildren().addAll(puzzle, hint, show, flow);
		window.setRight(buttons);

	}

	/**
	 * This method updates the view whenever there is a 
	 * change in the controller, which changes the model 
	 * and since model is observable by this class, the view
	 * gets updated.
	 * @param observable The Observable object
	 * @param arg an argument which can change the controller.
	 */
	@Override
	public void update(Observable observable, Object arg) {
		CryptogramModel cryptogram = (CryptogramModel) observable;
		CryptogramController controller = new CryptogramController(cryptogram);
		if (controller.isGameOver()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Message");
			alert.setHeaderText("Message");
			alert.setContentText("You Won");
			alert.showAndWait();
		}

	}
}
