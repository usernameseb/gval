package gval

import org.codehaus.groovy.runtime.powerassert.PowerAssertionError

class ListValidator extends Validator {

    private Integer length
    private String message

    ListValidator size(int length) {
        this.length = length
        this
    }

    ListValidator fail(String message) {
        this.message = message
        this
    }

    @Override
    void validate(obj) {
        try {
            // json object should be List type when deserialized to Groovy/Java
            assert obj instanceof List

            List list = obj

            if (length != null) {
                assert list.size() == length
            }
        } catch (PowerAssertionError e) {
            throw new ValidationError(message ? message : e.message, e)
        }
    }
}
