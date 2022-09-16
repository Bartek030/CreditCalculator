package service;

class MortgageException extends RuntimeException {

    MortgageException() {
        super("Case not handled");
    }
}
