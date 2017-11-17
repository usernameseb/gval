package gval

class ValidationError extends RuntimeException {

    ValidationError(message, cause) {
        super(message, cause)
    }
}
