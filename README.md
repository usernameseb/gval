# gval

A simple library for JSON validation in Groovy.

```
def schema = SchemaBuilder.schema([
                "id": string().required().fail("Missing field 'id'"),
                "myList": list().size(1).fail(""),
                "myString": string().match('ABC').required().fail("Bad data in field 'myString'"),
                "myObject": [
                        object().required().fail("Missing object 'myObject'"),
                        SchemaBuilder.schema([
                              'nestedId': string().required().fail("Missing field 'nestedId'")
                        ])
                ]
        ])

def json = '''
  {
    "id": "a2XwqL",
    "myList": [ "item" ],
    "myString": "ABC",
    "myObject": {
      "nestedId": "ay6GsqN"
    }
  }
'''

try {
  schema.validate(json)
} catch (ValidationError e) {}

assert schema.isValid(json)

```

## Getting Started

```
./gradlew build
```

## Running the tests

```
./gradlew test
```

## Authors

* **Jamie Archibald** - https://github.com/jamie3
* **Sebastien Hiscock** - https://github.com/usernameseb

See also the list of [contributors](https://github.com/usernameseb/gval/contributors) who participated in this project.
