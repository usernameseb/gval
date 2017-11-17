package gval

import org.junit.Test

class ListValidatorTest {

    @Test
    void test_list_required_pass() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.list()
        ])

        def json = '''
            {
                "id": [
                ]
            }
        '''

        assert schema.isValid(json)
        assert !schema.isValid('''{}''')
        schema.validate(json)
    }

    @Test(expected = ValidationError)
    void test_list_required_fail() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.list()
        ])

        schema.validate('''
            {
                "id": null
            }
        ''')
    }

    @Test(expected = ValidationError)
    void test_list_required_missing_key_fail() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.list()
        ])

        schema.validate('''
            {
                
            }
        ''')
    }

    @Test
    void test_list_required_nested_pass() {

        def schema = SchemaBuilder.schema([
                "id": SchemaBuilder.list().size(1)
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
    void test_list_required_nested_list_pass() {

        def schema = SchemaBuilder.schema([
                "id": [
                        SchemaBuilder.list().size(1),
                        SchemaBuilder.schema([
                                "hello": SchemaBuilder.string().required()
                        ])
                ]
        ])

        assert schema.childSchemas.size() == 1
        assert schema.childSchemas[0].validators.size() == 1
        assert schema.childSchemas[0].validators[0] instanceof StringValidator

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
}
