package gval

import groovy.json.JsonSlurper

class Schema {

    def rootNode
    def jsonField

    List<Validator> validators = []

    List<Schema> childSchemas = []

    Schema parentSchema

    void validate(json) {
        if (json instanceof String) {
            json = new JsonSlurper().parseText(json)
        }
        validators.each { Validator validator ->
            assert validator.schema != null

            if (validator.jsonField != null) {
                def jsonValue = json[validator.jsonField]
                validator.validate(jsonValue)
            } else {
                def jsonValue = json[validator.schema.jsonField]
                validator.validate(jsonValue)
            }
        }
        childSchemas.each { Schema childSchema ->
            def childJson = json[childSchema.jsonField]
            childSchema.validate(childJson)
        }
    }

    def isValid(json) {
        try {
            validate(json)
            true
        } catch (ignored) {
            false
        }
    }
}
