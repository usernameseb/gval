package gval

import org.codehaus.groovy.runtime.powerassert.PowerAssertionError

class ObjectValidator extends Validator {

    private boolean required = false
    private String message

    ObjectValidator required() {
        required = true
        this
    }

    ObjectValidator fail(message) {
        this.message = message
        this
    }

    @Override
    void validate(obj) {
        try {
            if (required) {
                assert obj != null
            }
            if (obj != null) {
                // json object should be of Map or List type when deserialized to Groovy/Java
                assert obj instanceof Map || obj instanceof List
            }
        } catch (PowerAssertionError e) {
            throw new ValidationError(message ? message : e.message, e)
        }
    }

}
