import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class Args {


    private ListIterator<String> currentArgument;
    private Set<Character> argsFound;
    private ImmutableMap<Character, ArgumentMarshaller> immutableMarshaller;

    public Args() {
    }

    public Args(String schema, String[] args) throws ArgsException {

        /* Converting argList to ImmutableList */
        ImmutableList<String> argList = ImmutableList.<String>builder()
                .addAll(Arrays.asList(args))
                .build();

        SchemaParser schemaParser = new SchemaParser();
        schemaParser.parseSchema(schema);
        Map<Character, ArgumentMarshaller> marshaller = schemaParser.getMarshaller();

        /* Converting Immutable Map */
        immutableMarshaller = ImmutableMap.<Character, ArgumentMarshaller>builder().putAll(marshaller).build();

        ArgumentParser argumentParser = new ArgumentParser(immutableMarshaller);
        argumentParser.parseArgumentStringArray(argList);
        currentArgument = argumentParser.getCurrentArgument();
        argsFound = argumentParser.getArgsFound();

    }

    boolean hasArgument(char arg) {
        return argsFound.contains(arg);
    }

    int nextArgument() {
        return currentArgument.nextIndex();
    }

    boolean getBoolean(char arg) throws ArgsException {
        ProcessArgumentMarshaller processArg = new ProcessArgumentMarshaller(immutableMarshaller);
        return processArg.process(BooleanArgumentMarshaller.class, arg);
    }

    String getString(char arg) throws ArgsException {
        ProcessArgumentMarshaller processArg = new ProcessArgumentMarshaller(immutableMarshaller);
        return processArg.process(StringArgumentMarshaller.class, arg);
    }

    int getInt(char arg) throws ArgsException {
        ProcessArgumentMarshaller processArg = new ProcessArgumentMarshaller(immutableMarshaller);
        return processArg.process(IntegerArgumentMarshaller.class, arg);
    }

    double getDouble(char arg) throws ArgsException {
        ProcessArgumentMarshaller processArg = new ProcessArgumentMarshaller(immutableMarshaller);
        return processArg.process(DoubleArgumentMarshaller.class, arg);
    }

    String[] getStringArray(char arg) throws ArgsException {
        ProcessArgumentMarshaller processArg = new ProcessArgumentMarshaller(immutableMarshaller);
        return processArg.process(StringArrayArgumentMarshaller.class, arg);
    }

    Map getMap(char arg) throws ArgsException {
        ProcessArgumentMarshaller processArg = new ProcessArgumentMarshaller(immutableMarshaller);
        return processArg.process(MapArgumentMarshaller.class, arg);
    }


}
