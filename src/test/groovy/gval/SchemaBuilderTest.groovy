package gval

import org.junit.Test

import static junit.framework.TestCase.fail

class SchemaBuilderTest {

    @Test
    void testSchema_simpleValidators_shouldProduceValidSchema() {
        def schema = SchemaBuilder.schema([
                "id"       : SchemaBuilder.string().required(),
                "time"     : SchemaBuilder.string().required(),
                "eventName": SchemaBuilder.string().contains("EmailGenerated").required()
        ])
        assert schema.jsonField == null
        assert schema.childSchemas.isEmpty()
        assert schema.parentSchema == null
        assert schema.validators.size() == 3
        schema.validators.each {
            assert it instanceof StringValidator
        }
        assert schema.rootNode.get("id")
        assert schema.rootNode.get("time")
        assert schema.rootNode.get("eventName")
    }

    @Test
    void testSchema_nestedSchemas_shouldProduceValidSchema() {
        def schema = SchemaBuilder.schema([
                "id"       : SchemaBuilder.string().required(),
                "time"     : SchemaBuilder.string().required(),
                "eventName": SchemaBuilder.string().contains("EmailGenerated").required(),
                "email"    :
                        [
                                SchemaBuilder.object().required(),
                                SchemaBuilder.schema([
                                        "to"     : SchemaBuilder.string().required(),
                                        "from"   : SchemaBuilder.string().required(),
                                        "subject": SchemaBuilder.string().required(),
                                        "body"   : SchemaBuilder.string().required()
                                ])
                        ]
        ])
        assert schema.jsonField == null
        assert schema.parentSchema == null
        assert schema.validators.size() == 4
        assert schema.validators[0] instanceof StringValidator
        assert schema.validators[1] instanceof StringValidator
        assert schema.validators[2] instanceof StringValidator
        assert schema.validators[3] instanceof ObjectValidator
        assert schema.rootNode.get("id")
        assert schema.rootNode.get("time")
        assert schema.rootNode.get("eventName")
        assert schema.rootNode.get("email")
        assert schema.childSchemas.size() == 1
        def childSchema = schema.childSchemas[0]
        assert childSchema.jsonField == "email"
        assert childSchema.parentSchema == schema
        assert childSchema.validators.size() == 4
        assert childSchema.validators[0] instanceof StringValidator
        assert childSchema.validators[1] instanceof StringValidator
        assert childSchema.validators[2] instanceof StringValidator
        assert childSchema.validators[3] instanceof StringValidator
        assert childSchema.rootNode.get("to")
        assert childSchema.rootNode.get("from")
        assert childSchema.rootNode.get("subject")
        assert childSchema.rootNode.get("body")
    }

    @Test
    void testSchema_incorrectType_shouldThrowSchemaBuilderException() {
        try {
            SchemaBuilder.schema([
                    "id"       : Boolean
            ])
            fail()
        } catch (SchemaBuilderException ignored) {}
    }

}
