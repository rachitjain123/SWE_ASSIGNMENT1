import java.util.*;

class SchemaParser {

    private Map<Character, ArgumentMarshaller> marshaller;

    SchemaParser() {
        marshaller = new HashMap<>();
    }

    Map<Character, ArgumentMarshaller> getMarshaller() {
        return marshaller;
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


}
