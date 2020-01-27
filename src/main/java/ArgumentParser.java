import java.util.*;

class ArgumentParser {

    private Set<Character> argsFound;
    private ListIterator<String> currentArgument;
    private Map<Character, ArgumentMarshaller> marshaller;

    ArgumentParser(Map<Character, ArgumentMarshaller> marshaller) {
        this.marshaller = marshaller;
        argsFound = new HashSet<>();

    }

    private void parseArgumentCharacter(char argChar) throws ArgsException {
        if (!marshaller.containsKey(argChar)) {
            throw new ArgsException(ErrorCode.UNEXPECTED_ARGUMENT, argChar, null);
        } else {
            ArgumentMarshaller argumentMarshaller = marshaller.get(argChar);
            argsFound.add(argChar);
            argumentMarshaller.setValue(currentArgument);
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

    Set<Character> getArgsFound() {
        return argsFound;
    }

}
