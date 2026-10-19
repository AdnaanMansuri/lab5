# Lab 5 — JavaFX & MVC Pattern

A small JavaFX desktop app that collects a user's **name, age and email**,
validates the input, and shows the entered details in a pop-up on submit.
The project is organised with the **Model–View–Controller (MVC)** pattern.

**Author:** Mohammed Mansuri — NetBeans (Ant), JDK 21, JavaFX 21.0.6

## MVC layout

| Layer          | File(s)                               | Responsibility                                   |
|----------------|---------------------------------------|--------------------------------------------------|
| **Model**      | `src/model/User.java`                 | Plain data holder (name, age, email).            |
| **View**       | `src/view/UserFormView.fxml` + `app.css` | The UI, editable in **Scene Builder**.        |
| **Controller** | `src/controller/UserFormController.java` | Validation, building the model, showing the pop-up. |
| **App**        | `src/app/MainApp.java`                | JavaFX entry point; loads the view.              |

The View knows nothing about validation, the Model knows nothing about the UI,
and the Controller is the only place the two meet — the MVC separation the lab
asks for.

## Run

Open the project in **NetBeans** and press **F6** (main class: `app.MainApp`).

JavaFX is bundled in `lib/` (the official modular jars), so there is nothing
extra to install. The project is configured to put those jars on the module
path and run with:

```
--add-modules javafx.controls,javafx.fxml
```

To run from the command line without the IDE:

```
java -p "lib/javafx-base-21.0.6-win.jar;lib/javafx-graphics-21.0.6-win.jar;lib/javafx-controls-21.0.6-win.jar;lib/javafx-fxml-21.0.6-win.jar" ^
     --add-modules javafx.controls,javafx.fxml ^
     -cp dist/lab5.jar app.MainApp
```

## Validations

- **Name** — required; 2–50 letters (spaces, `'` and `-` allowed).
- **Age** — required; whole number between 1 and 120.
- **Email** — required; must match `something@something.tld`.

Invalid fields show a red message inline **and** an error dialog listing every
problem. When everything is valid, an information pop-up displays the submitted
details.

## Screenshots

See the [`Screenshots/`](Screenshots) folder:

1. `1-empty-form.png` — the initial form.
2. `2-filled-form.png` — valid data entered.
3. `3-validation-errors.png` — inline validation messages.
4. `4-submit-popup.png` — the confirmation pop-up with the entered details.
