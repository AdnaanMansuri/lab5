package model;

/**
 * Model class for the JavaFX MVC lab.
 *
 * Holds the data captured by the form (name, age, email). It is a plain data
 * object with no knowledge of the UI or of how the values were validated,
 * which keeps the Model layer independent of the View and Controller.
 */
public class User {

    private String name;
    private int age;
    private String email;

    public User(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name +
               "\nAge: " + age +
               "\nEmail: " + email;
    }
}
