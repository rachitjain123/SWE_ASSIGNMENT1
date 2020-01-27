# SWE ASSIGNMENT 1 (Rachit Jain, 20161005)

## Explanation of Implemented features

### Usage of Java Generics
- I have added a process argument marshaler and abstract argument marshaler which takes a class and cast to another class or else returns a default value
- Advantage of this approach is it reduces code duplicacy in every boolean, int, string, strinarray, etc. marshaller and replaces them with simple function
```
<S, T extends ArgumentMarshaller<S>> S process(Class<T> marshallerClass, char arg) throws ArgsException {
        if (marshaller.containsKey(arg)) {
            try {
                marshallerClass.cast(marshaller.get(arg));
                return marshallerClass.cast(marshaller.get(arg)).getValue();
            } catch (ClassCastException e) {
                throw new ArgsException(ErrorCode.INVALID_CAST);
            }
        }
        ....
        See ProcessArgumentMarshaler.java for more details
```
```
New IntegerArgumentMarshaler Class
    public Integer getValue() {
        return intValue;
    }

    /**
     * @return default value if IntegerArgumentMarshaller does not contain the element id
     */
    public Integer getDefaultValue() {
        return 0;
    }
```

### New ErrorCode class
- Its bad habit to import static classes
- Previously, ```import static com.cleancoder.args.ArgsException.ErrorCode.*; ``` was there, I made a new class ErrorCode.
- Better maintenance of code
```
public enum ErrorCode {

    ILLEGAL_ACCESS, INVALID_ARGUMENT_FORMAT, INVALID_ARGUMENT_NAME, INVALID_CAST,
    INVALID_DOUBLE,
    INVALID_INSTANTIATION, INVALID_INTEGER,
    MALFORMED_MAP, MISSING_DOUBLE, MISSING_INTEGER, MISSING_MAP, MISSING_STRING,
    OK, UNEXPECTED_ARGUMENT
}

```

### Used map instead of switch cases and too many if else
- Earlier for parsing schema, to check all the supported arguments("&", "#", etc.) , if else was used which was very hard to scale
- Original Code
```
 private void parseSchemaElement(String element) throws ArgsException {
        if (elementTail.length() == 0) {
            marshalers.put(elementId, new BooleanArgumentMarshaler());
        } else if (elementTail.equals("*")) {
            marshalers.put(elementId, new StringArgumentMarshaler());
            ...
  	switch (errorCode) {
            case OK:
                return "TILT: Should not get here.";
            case UNEXPECTED_ARGUMENT:
                return String.format("Argument -%c unexpected.", errorArgumentId);
                ...
```
- New Code
```
    Map<String, AbstractArgumentMarshaller> getSupportedArguments() {
        Map<String, AbstractArgumentMarshaller> supportedArguments = new HashMap<>();
        supportedArguments.put("*", new StringArgumentMarshaller());


       Map<ErrorCode, String> errorCodeMessageMap = new HashMap<>();
        errorCodeMessageMap.put(ErrorCode.OK, "TILT: Should not get here.");

             ...
   See ErrorMessage.java and SupportedArgument.java
```
- A more scalable Approach

### Refactoring of Args Class

- Args Class was doing lot of things, violating Solid Design Principles
- Refactored it into *SchemaParser.java*, *ArgumentParser.java* and *SupportedArguments.java*
- See the above classes for more details

### Refactoring of ArgsException Class

- Refactored ArgsException into *ErrorMesage.java* where *ErrorMessage.java* has all the error messages and from *ArgsException.java* we get the error message

### Three more ErrorCodes
- *INVALID_CAST*, *ILLEGAL_ACCESS* and *INVALID_INSTANTIATION* for *ProcessArgumentMarshaler.java*
- Tests are written for same.
```
    @Test
    public void testInvalidCast() {
        try {
            Args args = new Args("x#", new String[]{"-x", "42"});
            args.getBoolean('x');
            fail();
        } catch (ArgsException e) {
            assertEquals(ErrorCode.INVALID_CAST, e.getErrorCode());
        }
    }
    Test for InvalidCast
```

### Arguments are Immutable
- All methods arguments are immutables
- There was one place in *Args.java* where list are passed directly and was not immutable
```
   /* Converting argList to ImmutableList */
        ImmutableList<String> argList = ImmutableList.<String>builder()
                .addAll(Arrays.asList(args))
                .build();
   Used Google Library guava, see Args.java for more details
```

### One assert per test, one concept per test
- All the unit tests are changed such that they have only one assertion per test and testing only one concept
- Refactored schema related tests into new file *ArgsSchemaTest.java*
```

    @Test
    public void testNonLetterSchemaErrorArgument() {
        try {
            new Args("*", new String[]{});
            fail("Args constructor should have thrown exception");
        } catch (ArgsException e) {
            assertEquals('*', e.getErrorArgumentId());
        }
    }
```

### New Idea: EqualsBuilder.java
- This class tells the reason of assertion even when the asserions are anded. It tells based on the message parameter that is passed 
- For example: expected "Field2" but we got "Field1" in argument x
```
    EqualsBuilder and(final Object expected, final Object actual, final String msg) {
        result = (result && actual != null && expected != null) && expected.equals(actual);
        if (!result && text.length() < 1) {
            text = MessageFormat.format("expected:<[{0}]> but was <[{1}]> failed at {2}", expected, actual, msg);
        }
        return this;
    }

    boolean result() {
        return result;
    }
    See EqualsBuilder.java for more details

```
```
EqualsBuilder equalsBuilder = new EqualsBuilder()
                .and(args.hasArgument('y'), true, "Has argument")
                .and(42, args.getInt('y'),"Argument Parameter y");
        assertTrue(equalsBuilder.getMessage(), equalsBuilder.result());

If the above fails, it displays expected value, actual value and the message
```

### Limiting the size of methods
- All methods are made shorter and cleaner by adding new functions or classes

### Code Style
- Proper indentation, brackets, line space was maintained uniformly across all files
- All null parameters are avoided

### Documentation
- Proper documentation with JavaDocs wherever required.
- Comments are not added everywhere, tried to explain through code only
- Sample Documentation of *ProcessArgumentMarshaller.java*
```
    /**
     * @param marshallerClass Type of class ArgumentMarsheller
     * @param arg             argument id
     * @param <S>             type parameter
     * @param <T>             type parameter
     * @return casted output of marshallerClass to the type parameter if arg present
     * else returns default value of marshaller class
     */
    <S, T extends ArgumentMarshaller<S>> S process(Class<T> marshallerClass, char arg) throws ArgsException {
        if (marshaller.containsKey(arg)) {
            try {
                marshallerClass.cast(marshaller.get(arg));
                return marshallerClass.cast(marshaller.get(arg)).getValue();
            } catch (ClassCastException e) {
                throw new ArgsException(ErrorCode.INVALID_CAST);
            }
        }

```

### Single Level Abstraction
- Tried to follow SLA principle in all methods
```
    private void parseSchemaElement(String element) throws ArgsException {
        SupportedArguments supportedArguments = new SupportedArguments();
        allSupportedArguments = supportedArguments.getSupportedArguments();
        char elementId = element.charAt(0);
        String elementTail = element.substring(1);
        validateSchemaElementId(elementId);
        putIntoMarshaller(elementTail, elementId);
    }
    Code from SchemaParser.java
```

### Usage of Maven
- Maven is better for managing dependencies than ANT and is better for controlling of build processes
- Look at *pom.xml* for information about the project and the dependencies
```
    <groupId>com.cleancoder</groupId>
    <artifactId>args</artifactId>
    <version>1.0-SNAPSHOT</version>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>8</source>
                    <target>8</target>
                </configuration>
            </plugin>
        </plugins>
    </build>

    Sample code part of Pom file 
```

## Code Base Characterstics
- Modularity
- Clean Code
- One assert per test
- Methods have less than two arguments everywhere
- Immutable Arguments in methods
- One assert per test
- One concept per test
- Single responsibility of every class
- No duplicacy of code
- Documentation of code
- Smaller Functions
- Proper Naming of methods and classes
- Followed OOPS principles
- Avoided Passing and Returning Null Values

## Requirements
- Maven 3.6.1 or later
- Java 1.8+

## Execution and Unit Testing
- Clone the repository and go the directory *SWE_ASSIGNMENT1*
- Use IntelliJ or Eclipse and open the pom.xml to see all dependencies
- Compile the project
```
mvn compile
```
- Build the project and run unit tests
```
mvn clean install
```
- Run tests only
```
mvn test
```