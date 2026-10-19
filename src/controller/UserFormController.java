package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

import model.User;

/**
 * Controller for the user form (the "C" in MVC).
 *
 * It wires the View (UserFormView.fxml) to the Model (User). On submit it
 * validates every field, shows inline error messages for anything invalid,
 * and - when everything is valid - builds a {@link User} and pops up a dialog
 * showing the details that were entered.
 */
public class UserFormController implements Initializable {

    // --- Input fields injected from the FXML view ---
    @FXML private TextField nameField;
    @FXML private Spinner<Integer> ageSpinner;   // constrained to 1-120 in the view
    @FXML private TextField emailField;

    // --- Inline error labels injected from the FXML view ---
    @FXML private Label nameError;
    @FXML private Label emailError;

    // Basic, practical email pattern: something@something.tld
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");

    // Name: letters, spaces, apostrophes and hyphens, 2-50 chars.
    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[A-Za-z][A-Za-z .'-]{1,49}$");

    /**
     * Called automatically by the FXMLLoader after the view is built.
     * Sets up the age spinner so it only allows whole numbers from 1 to 120,
     * starting at 18.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ageSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 120, 18));
    }

    /**
     * Handles the Submit button. Runs all validations first; if any fail the
     * user sees inline messages (and an error dialog). Only when the whole
     * form is valid do we build the model and show the confirmation pop-up.
     */
    @FXML
    private void handleSubmit() {
        clearErrors();

        boolean valid = true;
        StringBuilder problems = new StringBuilder();

        // --- Name validation ---
        String name = safeText(nameField);
        if (name.isEmpty()) {
            nameError.setText("Name is required.");
            problems.append("• Name is required.\n");
            valid = false;
        } else if (!NAME_PATTERN.matcher(name).matches()) {
            nameError.setText("Use 2-50 letters (spaces, ' and - allowed).");
            problems.append("• Name format is invalid.\n");
            valid = false;
        }

        // --- Age ---
        // The Spinner can only hold a whole number from 1 to 120, so it is
        // always valid; we just read the chosen value.
        int age = ageSpinner.getValue();

        // --- Email validation ---
        String email = safeText(emailField);
        if (email.isEmpty()) {
            emailError.setText("Email is required.");
            problems.append("• Email is required.\n");
            valid = false;
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            emailError.setText("Enter a valid email (e.g. you@example.com).");
            problems.append("• Email is invalid.\n");
            valid = false;
        }

        if (!valid) {
            showAlert(AlertType.ERROR, "Please fix the following",
                    problems.toString().trim());
            return;
        }

        // All good: build the model and show the details pop-up.
        User user = new User(name, age, email);
        showAlert(AlertType.INFORMATION, "Submitted Details", user.toString());
    }

    /**
     * Clears every field and any error messages.
     */
    @FXML
    private void handleClear() {
        nameField.clear();
        ageSpinner.getValueFactory().setValue(18);
        emailField.clear();
        clearErrors();
    }

    private void clearErrors() {
        nameError.setText("");
        emailError.setText("");
    }

    private String safeText(TextField field) {
        return field.getText() == null ? "" : field.getText().trim();
    }

    private void showAlert(AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(header);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
