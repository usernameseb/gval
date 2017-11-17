package gval

import org.junit.Test

class StringValidatorTest {

    @Test
    void test_string_required_pass() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.string().required()
        ])

        schema.validate('''
            {
                "id": "ABC"
            }
        ''')
    }

    @Test(expected = ValidationError.class)
    void test_string_required_fail() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.string().required()
        ])

        schema.validate('''
            {
                "id2": "ABC"
            }
        '''
        )
    }

    @Test(expected = ValidationError.class)
    void test_string_as_boolean_fail() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.string()
        ])

        schema.validate('''
            {
                "id": 123
            }
        ''')
    }


    @Test
    void test_string_not_required() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.string()
        ])

        schema.validate('''
            {
                
            }
        ''')
    }

    @Test
    void test_string_min_fail() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.string().min(3).required()
        ])

        schema.validate('''
            {
                "id": "123"
            }
        ''')
    }

    @Test
    void test_string_min_pass() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.string().min(3).required()
        ])

        schema.validate('''
            {
                "id": "123"
            }
        ''')
    }

    @Test
    void test_string_max_pass() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.string().max(3).required()
        ])

        schema.validate('''
            {
                "id": "123"
            }
        ''')
    }

    @Test
    void test_string_max_fail() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.string().max(3).required()
        ])

        schema.validate('''
            {
                "id": "1"
            }
        ''')
    }

    @Test(expected = ValidationError)
    void test_string_required_object_fail() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.string().required()
        ])

        schema.validate('''
            {
                "id": []
            }
        ''')
    }

    @Test
    void test_string_nested_pass() {

        def schema = SchemaBuilder.schema([
                "car": [
                        SchemaBuilder.schema([
                                "model": SchemaBuilder.string().required()
                        ])
                ]
        ])

        schema.validate('''
            {
                "car": {
                    "model": "ford"
                }
            }
        ''')
    }

    @Test
    void testValidate_jsonFieldContainsString_shouldNotThrowException() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.string().contains("test")
        ])

        schema.validate('''
            {
                "id": "test"
            }
        ''')
    }

    @Test(expected = ValidationError.class)
    void testValidate_jsonFieldDoesNotContainString_shouldThrowException() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.string().contains("test")
        ])

        schema.validate('''
            {
                "id": "ABC"
            }
        '''
        )
    }

    @Test
    void testValidate_jsonFieldContainsMatch_shouldNotThrowException() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.string().match("test")
        ])

        schema.validate('''
            {
                "id": "test"
            }
        ''')
    }

    @Test(expected = ValidationError.class)
    void testValidate_jsonFieldDoesNotContainMatch_shouldThrowException() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.string().match("test")
        ])

        schema.validate('''
            {
                "id": "ABC"
            }
        '''
        )
    }

    @Test
    void testValidate_jsonFieldContainsCorrectLength_shouldNotThrowException() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.string().length(4)
        ])

        schema.validate('''
            {
                "id": "test"
            }
        ''')
    }

    @Test(expected = ValidationError.class)
    void testValidate_jsonFieldDoesNotCorrectLength_shouldThrowException() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.string().length(4)
        ])

        schema.validate('''
            {
                "id": "ABC"
            }
        '''
        )
    }

    @Test
    void testValidate_failureMessage_shouldThrowExceptionWithCustomMessage() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.string().contains("test").fail("oops")
        ])

        try {
            schema.validate('''
            {
                "id": "ABC"
            }
        '''
            )
        } catch (ValidationError e) {
            assert e.message == "oops"
        }
    }

}
