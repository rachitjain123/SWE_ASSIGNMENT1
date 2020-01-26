import java.util.*;

class SchemaParsing extends Args {

    private ListIterator<String> currentArgument;
    private Map<Character, ArgumentMarshaler> marshaller;
    private Set<Character> argsFound;

    SchemaParsing() throws ArgsException {
        marshaller = new HashMap<>();
        argsFound = new HashSet<>();
    }

    /**
     *
     * @return All the  arguments that schema supports
     *         Scalable, can add more
     */
    private Map<String, AbstractArgumentMarshaler> getSupportedArguments(){
        Map<String, AbstractArgumentMarshaler> supportedArguments = new HashMap<>();
        supportedArguments.put("*", new StringArgumentMarshaler());
        supportedArguments.put("#", new IntegerArgumentMarshaler());
        supportedArguments.put("##", new DoubleArgumentMarshaler());
        supportedArguments.put("&", new MapArgumentMarshaler());
        supportedArguments.put("[*]", new StringArrayArgumentMarshaler());
        supportedArguments.put("!", new BooleanArgumentMarshaler());
        return supportedArguments;
    }

    void parseSchema(String schema) throws ArgsException {
        for (String element : schema.split(",")) {
            if (element.length() > 0) {
                parseSchemaElement(element.trim());
            }
        }
    }

    private void parseSchemaElement(String element) throws ArgsException {
        Map<String, AbstractArgumentMarshaler> supportedArguments = getSupportedArguments();
        char elementId = element.charAt(0);
        String elementTail = element.substring(1);
        validateSchemaElementId(elementId);
        if(elementTail.isEmpty())
            marshaller.put(elementId, supportedArguments.get("!"));
        else if(supportedArguments.containsKey(elementTail))
            marshaller.put(elementId, supportedArguments.get(elementTail));
        else
            throw new ArgsException(ErrorCode.INVALID_ARGUMENT_FORMAT, elementId, elementTail);
    }

    private void validateSchemaElementId(char elementId) throws ArgsException {
        if (!Character.isLetter(elementId))
            throw new ArgsException(ErrorCode.INVALID_ARGUMENT_NAME, elementId,null);
    }

    void parseArgumentStrings(List<String> argsList) throws ArgsException {
        currentArgument = argsList.listIterator();
        while (currentArgument.hasNext()) {
            String argString = currentArgument.next();
            if (!argString.startsWith("-")) {
                currentArgument.previous();
                break;
            } else {
                parseArgumentCharacters(argString.substring(1));
            }
        }
    }

    private void parseArgumentCharacters(String argChars) throws ArgsException {
        for (int i = 0; i < argChars.length(); i++)
            parseArgumentCharacter(argChars.charAt(i));
    }

    private void parseArgumentCharacter(char argChar) throws ArgsException {
        ArgumentMarshaler m = marshaller.get(argChar);
        if (m == null) {
            throw new ArgsException(ErrorCode.UNEXPECTED_ARGUMENT, argChar,null);
        } else {
            argsFound.add(argChar);
            m.set(currentArgument);
        }
    }

    ListIterator<String> getCurrentArgument() {
        return currentArgument;
    }

    Map<Character, ArgumentMarshaler> getMarshaller() {
        return marshaller;
    }

    Set<Character> getArgsFound() {
        return argsFound;
    }

}
