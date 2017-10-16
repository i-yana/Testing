package ru.nsu.fit.endpoint.service.manager;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import ru.nsu.fit.endpoint.service.database.DBService;
import ru.nsu.fit.endpoint.service.database.data.Customer;

import java.util.List;
import java.util.UUID;

public class CustomerManager extends ParentManager {
    public CustomerManager(DBService dbService, Logger flowLog) {
        super(dbService, flowLog);
    }

    /**
     * Метод создает новый объект типа Customer. Ограничения:
     * Аргумент 'customerData' - не null;
     * firstName - нет пробелов, длина от 2 до 12 символов включительно, начинается с заглавной буквы, остальные символы строчные, нет цифр и других символов;
     * lastName - нет пробелов, длина от 2 до 12 символов включительно, начинается с заглавной буквы, остальные символы строчные, нет цифр и других символов;
     * login - указывается в виде email, проверить email на корректность, проверить что нет customer с таким же email;
     * pass - длина от 6 до 12 символов включительно, не должен быть простым (123qwe или 1q2w3e), не должен содержать части login, firstName, lastName
     * money - должно быть равно 0.
     */
    public Customer createCustomer(Customer customer) {

        Validate.notNull(customer, "Argument 'customerData' is null.");

        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        String login = customer.getLogin();
        String pass = customer.getPass();

        Validate.notEmpty(customer.getPass(), "Password is empty");
        Validate.notEmpty(customer.getFirstName(), "FirstName is empty");
        Validate.notEmpty(customer.getLastName(), "LastName is empty");

        Validate.isTrue(firstName.length() >= 2 && firstName.length() <= 12,
                        "FirstName's length should be more or equal 2 symbols and less or equal 12 symbols.");
        Validate.isTrue(firstName.equals(firstName.replace(" ",""))
                        && firstName.matches("[a-zA-Z]+$")
                        && firstName.equals(toNameCase(firstName)),
                        "FirstName should contain symbols of latin alphabet without spaces");

        Validate.isTrue(lastName.length() >= 2 && lastName.length() <= 12,
                        "LastName's length should be more or equal 2 symbols and less or equal 12 symbols.");
        Validate.isTrue(lastName.equals(lastName.replace(" ",""))
                        && lastName.matches("[a-zA-Z]+$")
                        && lastName.equals(toNameCase(lastName)),
                        "LastName should contain symbols of latin alphabet without spaces");

        Validate.isTrue(login.contains("@"));
        Validate.isTrue(getCustomers().stream().noneMatch(c -> c.getLogin().equals(login)), "Customer with login already exist");

        Validate.isTrue(pass.length() >= 6 && pass.length() < 13, "Password's length should be more or equal 6 symbols and less or equal 12 symbols.");
        Validate.isTrue(!pass.equalsIgnoreCase("123qwe"), "Password is easy.");

        Validate.isTrue(customer.getBalance() == 0);

        return dbService.createCustomer(customer);
    }

    /**
     * Метод возвращает список объектов типа customer.
     */
    public List<Customer> getCustomers() {
        return dbService.getCustomers();
    }


    /**
     * Метод обновляет объект типа Customer.
     * Можно обновить только firstName и lastName.
     */
    public Customer updateCustomer(Customer customer) {
        throw new NotImplementedException("Please implement the method.");
    }

    public void removeCustomer(UUID id) {
        throw new NotImplementedException("Please implement the method.");
    }

    /**
     * Метод добавляет к текущему баласу amount.
     * amount - должен быть строго больше нуля.
     */
    public Customer topUpBalance(UUID customerId, int amount) {
        throw new NotImplementedException("Please implement the method.");
    }

    private String toNameCase(String name) {
        return name.substring(0, 1).toUpperCase().concat(name.substring(1).toLowerCase());
    }

}
