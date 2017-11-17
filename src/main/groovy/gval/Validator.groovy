package gval

abstract class Validator {

    Schema schema
    String jsonField

    abstract void validate(json)
}
