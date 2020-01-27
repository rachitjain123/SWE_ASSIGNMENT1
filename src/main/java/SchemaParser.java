import java.util.*;

class SchemaParser {

    private Map<Character, ArgumentMarshaller> marshaller;
    private Map<String, AbstractArgumentMarshaller> allSupportedArguments;

    SchemaParser() {
        marshaller = new HashMap<>();
    }

    Map<Character, ArgumentMarshaller> getMarshaller() {
        return marshaller;
    }

    private void validateSchemaElementId(char elementId) throws ArgsException {
        if (!Character.isLetter(elementId))
            throw new ArgsException(ErrorCode.INVALID_ARGUMENT_NAME, elementId);
    }

    private void putIntoMarshaller(String elementTail, char elementId) throws ArgsException {
        if (elementTail.isEmpty())
            marshaller.put(elementId, new BooleanArgumentMarshaller());
        else if (allSupportedArguments.containsKey(elementTail))
            marshaller.put(elementId, allSupportedArguments.get(elementTail));
        else
            throw new ArgsException(ErrorCode.INVALID_ARGUMENT_FORMAT, elementId, elementTail);
    }

    private void parseSchemaElement(String element) throws ArgsException {
        SupportedArguments supportedArguments = new SupportedArguments();
        allSupportedArguments = supportedArguments.getSupportedArguments();
        char elementId = element.charAt(0);
        String elementTail = element.substring(1);
        validateSchemaElementId(elementId);
        putIntoMarshaller(elementTail, elementId);
    }

    void parseSchema(String schema) throws ArgsException {
        for (String element : schema.split(",")) {
            if (!element.isEmpty()) {
                parseSchemaElement(element.trim());
            }
        }
    }


}
