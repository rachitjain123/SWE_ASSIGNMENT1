
import java.util.*;

public class Args {
    private Map<Character, ArgumentMarshaler> marshalers;
    private Set<Character> argsFound;
    private ListIterator<String> currentArgument;

    private Map<String, AbstractArgumentMarshaler> getSupportedArguments(){
        Map<String, AbstractArgumentMarshaler> supportedArguments = new HashMap<>();
        supportedArguments.put("*", new StringArgumentMarshaler());
        supportedArguments.put("#", new IntegerArgumentMarshaler());
        supportedArguments.put("##", new DoubleArgumentMarshaler());
        supportedArguments.put("&", new MapArgumentMarshaler());
        supportedArguments.put("[*]", new StringArrayArgumentMarshaler());
        return supportedArguments;
    }

    public Args(String schema, String[] args) throws ArgsException {
        marshalers = new HashMap<>();
        argsFound = new HashSet<>();
        parseSchema(schema);
        parseArgumentStrings(Arrays.asList(args));
    }

    private void parseSchema(String schema) throws ArgsException {
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
            marshalers.put(elementId, new BooleanArgumentMarshaler());
        else if(supportedArguments.containsKey(elementTail))
            marshalers.put(elementId, supportedArguments.get(elementTail));
        else
            throw new ArgsException(ErrorCode.INVALID_ARGUMENT_FORMAT, elementId, elementTail);
    }

    private void validateSchemaElementId(char elementId) throws ArgsException {
        if (!Character.isLetter(elementId))
            throw new ArgsException(ErrorCode.INVALID_ARGUMENT_NAME, elementId,null);
    }

    private void parseArgumentStrings(List<String> argsList) throws ArgsException {
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
        ArgumentMarshaler m = marshalers.get(argChar);
        if (m == null) {
            throw new ArgsException(ErrorCode.UNEXPECTED_ARGUMENT, argChar,null);
        } else {
            argsFound.add(argChar);
            m.set(currentArgument);
        }

    }

    public boolean has(char arg){
        return argsFound.contains(arg);
    }

    public int nextArgument() {
        return currentArgument.nextIndex();
    }


    private <S, T extends ArgumentMarshaler<S>> S process(Class<S> type, Class<T> marshalerClass, char arg) {
        ArgumentMarshaler m = marshalers.get(arg);

        if (m != null) {
            try {
                marshalerClass.cast(m);
                return marshalerClass.cast(m).getValue();
            }
            catch (ClassCastException e) { }
        }

        try {
            return marshalerClass.newInstance().getDefaultValue();
        }
        catch (InstantiationException e1) {}
        catch (IllegalAccessException e1) {}
        return null;
    }

    public boolean getBoolean(char arg) {
        return process(Boolean.class, BooleanArgumentMarshaler.class, arg);
    }


    public String getString(char arg) {
        return process(String.class, StringArgumentMarshaler.class, arg);
    }

    public int getInt(char arg) {
        return process(Integer.class, IntegerArgumentMarshaler.class, arg);
    }

    public double getDouble(char arg) {
        return process(Double.class, DoubleArgumentMarshaler.class, arg);
    }

    public String[] getStringArray(char arg) {
        return process(String[].class, StringArrayArgumentMarshaler.class, arg);
    }

    public Map getMap(char arg) {
        return process(Map.class, MapArgumentMarshaler.class, arg);
    }


}
