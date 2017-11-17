package gval

class SchemaBuilderException extends RuntimeException {

    SchemaBuilderException(String message) {
        super(message)
    }

    SchemaBuilderException(String message, Throwable cause) {
        super(message, cause)
    }

}
