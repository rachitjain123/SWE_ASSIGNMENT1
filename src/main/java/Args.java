
import java.util.*;

public class Args {

    private ListIterator<String> currentArgument;
    private Map<Character, ArgumentMarshaler> marshalers;
    private Set<Character> argsFound;



    public Args(String schema, String[] args) throws ArgsException {
        SchemaParsing parser = new SchemaParsing();
        parser.parseSchema(schema);
        parser.parseArgumentStrings(Arrays.asList(args));

        currentArgument = parser.getCurrentArgument();
        marshalers = parser.getMarshaller();
        argsFound = parser.getArgsFound();

    }

    public Args() {
    }

    boolean hasArgument(char arg){
        return argsFound.contains(arg);
    }

    int nextArgument() {
        return currentArgument.nextIndex();
    }

    boolean getBoolean(char arg) {
        ProcessArgumentMarshaler processArg = new ProcessArgumentMarshaler(marshalers);
        return processArg.process(Boolean.class, BooleanArgumentMarshaler.class, arg);
    }


    String getString(char arg) {
        ProcessArgumentMarshaler processArg = new ProcessArgumentMarshaler(marshalers);
        return processArg.process(String.class, StringArgumentMarshaler.class, arg);
    }

    int getInt(char arg) {
        ProcessArgumentMarshaler processArg = new ProcessArgumentMarshaler(marshalers);
        return processArg.process(Integer.class, IntegerArgumentMarshaler.class, arg);
    }

    double getDouble(char arg) {
        ProcessArgumentMarshaler processArg = new ProcessArgumentMarshaler(marshalers);
        return processArg.process(Double.class, DoubleArgumentMarshaler.class, arg);
    }

    String[] getStringArray(char arg) {
        ProcessArgumentMarshaler processArg = new ProcessArgumentMarshaler(marshalers);
        return processArg.process(String[].class, StringArrayArgumentMarshaler.class, arg);
    }

    Map getMap(char arg) {
        ProcessArgumentMarshaler processArg = new ProcessArgumentMarshaler(marshalers);
        return processArg.process(Map.class, MapArgumentMarshaler.class, arg);
    }


}
