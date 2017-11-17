package gval

import org.junit.Test

class ObjectValidatorTest {

    @Test
    void test_object_required_pass() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.object().required()
        ])

        schema.validate('''
            {
                "id": [
                ]
            }
        ''')
    }

    @Test(expected = ValidationError)
    void test_object_required_fail() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.object().required()
        ])

        schema.validate('''
            {
                "id": null
            }
        ''')
    }

    @Test(expected = ValidationError)
    void test_object_required_missing_key_fail() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.object().required()
        ])

        schema.validate('''
            {
                
            }
        ''')
    }

    @Test
    void test_object_required_nested_pass() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.object().required()
        ])

        schema.validate('''
            {
                "id": [
                    {
                        "hello": "world"
                    }
                ]
            }
        ''')
    }

    @Test(expected = ValidationError)
    void test_object_required_nested_list_pass() {

        def schema = SchemaBuilder.schema([
                "id": [
                        SchemaBuilder.list().size(1),
                        SchemaBuilder.schema([
                                "hello": SchemaBuilder.string().required()
                        ])
                ]
        ])

        schema.validate('''
            {
                "id": [
                    {
                        "hello": null
                    }
                ]
            }
        ''')
    }

    @Test
    void testValidate_failureMessage_shouldThrowExceptionWithCustomMessage() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.object().required().fail("oops")
        ])

        try {
            schema.validate('''
            {
                "other": "ABC"
            }
        '''
            )
        } catch (ValidationError e) {
            assert e.message == "oops"
        }
    }
}
