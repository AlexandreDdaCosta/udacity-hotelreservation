package services;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import model.Customer;

public final class CustomerService {
    private static CustomerService INSTANCE;

    private final Map<String, Customer> customers = new HashMap<>();

    private CustomerService() {
    }

    public static CustomerService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomerService();
        }
        return INSTANCE;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        email = email.replaceAll("\\s", "");
        email = email.toLowerCase();
        customers.put(email, new Customer(firstName, lastName, email));
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

    public Customer getCustomer(String email) {
        email = email.replaceAll("\\s", "");
        email = email.toLowerCase();
        Customer customer = customers.get(email);
        if (customer == null) {
            throw new InvalidParameterException("No customer account associated with email address "
                                                + "\"" + email + "\"");
        }
        return customer;
    }
}