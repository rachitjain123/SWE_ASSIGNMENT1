import java.util.*;

class SchemaArgumentParser extends Args {

    private ListIterator<String> currentArgument;
    private Map<Character, ArgumentMarshaller> marshaller;
    private Set<Character> argsFound;

    SchemaArgumentParser() {
        marshaller = new HashMap<>();
        argsFound = new HashSet<>();
    }

    private void validateSchemaElementId(char elementId) throws ArgsException {
        if (!Character.isLetter(elementId))
            throw new ArgsException(ErrorCode.INVALID_ARGUMENT_NAME, elementId, null);
    }
    private void parseSchemaElement(String element) throws ArgsException {
        SupportedArguments supportedArguments = new SupportedArguments();
        Map<String, AbstractArgumentMarshaller> allSupportedArguments = supportedArguments.getSupportedArguments();
        char elementId = element.charAt(0);
        String elementTail = element.substring(1);
        validateSchemaElementId(elementId);
        if (elementTail.isEmpty())
            marshaller.put(elementId, allSupportedArguments.get("!"));
        else if (allSupportedArguments.containsKey(elementTail))
            marshaller.put(elementId, allSupportedArguments.get(elementTail));
        else
            throw new ArgsException(ErrorCode.INVALID_ARGUMENT_FORMAT, elementId, elementTail);
    }
    void parseSchema(String schema) throws ArgsException {
        for (String element : schema.split(",")) {
            if (!element.isEmpty()) {
                parseSchemaElement(element.trim());
            }
        }
    }

    private void parseArgumentCharacter(char argChar) throws ArgsException {
        if (!marshaller.containsKey(argChar)) {
            throw new ArgsException(ErrorCode.UNEXPECTED_ARGUMENT, argChar, null);
        } else {
            ArgumentMarshaller argumentMarshaller = marshaller.get(argChar);
            argsFound.add(argChar);
            argumentMarshaller.set(currentArgument);
        }
    }
    private void parseArgumentString(String argChars) throws ArgsException {
        for (int i = 0; i < argChars.length(); i++)
            parseArgumentCharacter(argChars.charAt(i));
    }
    void parseArgumentStringArray(List<String> argsList) throws ArgsException {
        currentArgument = argsList.listIterator();
        while (currentArgument.hasNext()) {
            String argString = currentArgument.next();
            if (!argString.startsWith("-")) {
                currentArgument.previous();
                break;
            } else {
                parseArgumentString(argString.substring(1));
            }
        }
    }

    ListIterator<String> getCurrentArgument() {
        return currentArgument;
    }
    Map<Character, ArgumentMarshaller> getMarshaller() {
        return marshaller;
    }
    Set<Character> getArgsFound() {
        return argsFound;
    }

}
