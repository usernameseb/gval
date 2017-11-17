package gval

class SchemaBuilder {

    static StringValidator string() {
        new StringValidator()
    }

    static def object() {
        new ObjectValidator()
    }

    static def list() {
        new ListValidator()
    }

    static Schema schema(def json) {

        def schema = new Schema()
        schema.rootNode = json

        json.each { node ->
            def key = node.key
            def value = node.value

            if (value instanceof Validator) {
                value.schema = schema
                value.jsonField = key
                schema.validators.add(value)
            } else if (value instanceof List) {
                value.each { nestedNode ->
                    if (nestedNode instanceof Validator) {
                        Validator validator = nestedNode
                        validator.schema = schema
                        validator.jsonField = key
                        schema.validators.add(validator)
                    } else if (nestedNode instanceof Schema) {
                        nestedNode.parentSchema = schema
                        nestedNode.jsonField = key
                        schema.childSchemas.add(nestedNode)
                    }
                }
            } else {
                throw new SchemaBuilderException("Invalid type for JSON node. Must be Map, List or Validator instance.")

            }
        }

        schema
    }

}