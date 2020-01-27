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
- *ILLEGAL_ACCESS* *INVALID_CAST* *INVALID_INSTANTIATION* for *ProcessArgumentMarshaler.java*

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

### Documentation
- Proper documentation with JavaDocs wherever required.
- Comments are not added everywhere, tried to explain through code only

### Usage of Maven
- Maven is better for managing dependencies than ANT and is better for controlling of build processes
- Look at *pom.xml* for information about the project and the dependencies

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
- Proper Naming of methods and classes
- Followed OOPS principles

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