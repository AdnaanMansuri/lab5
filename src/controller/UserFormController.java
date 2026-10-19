package controller;

import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
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
public class UserFormController {

    // --- Input fields injected from the FXML view ---
    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private TextField emailField;

    // --- Inline error labels injected from the FXML view ---
    @FXML private Label nameError;
    @FXML private Label ageError;
    @FXML private Label emailError;

    // Basic, practical email pattern: something@something.tld
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");

    // Name: letters, spaces, apostrophes and hyphens, 2-50 chars.
    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[A-Za-z][A-Za-z .'-]{1,49}$");

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

        // --- Age validation ---
        String ageText = safeText(ageField);
        int age = -1;
        if (ageText.isEmpty()) {
            ageError.setText("Age is required.");
            problems.append("• Age is required.\n");
            valid = false;
        } else if (!ageText.matches("\\d{1,3}")) {
            ageError.setText("Age must be a whole number.");
            problems.append("• Age must be a whole number.\n");
            valid = false;
        } else {
            age = Integer.parseInt(ageText);
            if (age < 1 || age > 120) {
                ageError.setText("Age must be between 1 and 120.");
                problems.append("• Age must be between 1 and 120.\n");
                valid = false;
            }
        }

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
        ageField.clear();
        emailField.clear();
        clearErrors();
    }

    private void clearErrors() {
        nameError.setText("");
        ageError.setText("");
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
