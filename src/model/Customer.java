package model;

public record Customer(String firstName, String lastName, String email) {
    public Customer(String firstName, String lastName, String email) {
        firstName = firstName.trim();
        lastName = lastName.trim();
        email = email.trim();
        email = email.toLowerCase();
        Validator validator = new Validator();
        validator.validateCustomerDetails(firstName, lastName, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "First Name: " + firstName + "\n"
                + "Last Name: " + lastName + "\n"
                + "Email Address: " + email;
    }
}
