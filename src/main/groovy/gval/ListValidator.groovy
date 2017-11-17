package gval

import org.codehaus.groovy.runtime.powerassert.PowerAssertionError

class ListValidator extends Validator {

    private Integer length

    ListValidator size(int length) {
        this.length = length
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
            throw new ValidationError(e.message, e)
        }
    }
}
