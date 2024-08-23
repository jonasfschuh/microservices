package io.github.kenzor1979.mscreditassessor.application.ex;

public class CustomerDataNotFoundException extends Exception {

    public CustomerDataNotFoundException() {
        super("Customer data not found for the CPF provided");
    }
}
