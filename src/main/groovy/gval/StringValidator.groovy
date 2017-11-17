package gval

import org.codehaus.groovy.runtime.powerassert.PowerAssertionError

class StringValidator extends Validator {

    private Integer min
    private Integer max
    private Integer length
    private String match
    private String contains
    private String message
    private boolean required = false

    StringValidator required() {
        required = true
        this
    }

    StringValidator min(int i) {
        min = i
        this
    }

    StringValidator max(int i) {
        max = i
        this
    }

    StringValidator length(int i) {
        length = i
        this
    }

    StringValidator match(String s) {
        match = s
        this
    }

    StringValidator contains(String s) {
        contains = s
        this
    }

    StringValidator fail(String message) {
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
                assert obj instanceof String || obj instanceof char[]
            }
            String string = obj as String
            if (min != null) {
                assert string.length() >= min
            }
            if (max != null) {
                assert string.length() <= max
            }
            if (match != null) {
                assert string == match
            }
            if (contains != null) {
                assert string.contains(contains)
            }
            if (length != null) {
                assert string?.length() == length
            }
        } catch (PowerAssertionError e) {
            throw new ValidationError(message ? message : e.message, e)
        }
    }
}
