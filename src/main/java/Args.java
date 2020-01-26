
import java.util.*;

import com.google.common.collect.ImmutableList;

public class Args {


    private ListIterator<String> currentArgument;
    private Map<Character, ArgumentMarshaller> marshaller;
    private Set<Character> argsFound;


    public Args() {
    }


    public Args(String schema, String[] args) throws ArgsException {

        /* Converting argList to ImmutableList */
        ImmutableList<String> argList = ImmutableList.<String>builder()
                .addAll(Arrays.asList(args))
                .build();

        SchemaParser schemaParser = new SchemaParser();
        schemaParser.parseSchema(schema);
        marshaller = schemaParser.getMarshaller();

        ArgumentParser argumentParser = new ArgumentParser(marshaller);
        argumentParser.parseArgumentStringArray(argList);
        currentArgument = argumentParser.getCurrentArgument();
        argsFound = argumentParser.getArgsFound();

    }

    boolean hasArgument(char arg){
        return argsFound.contains(arg);
    }

    int nextArgument() {
        return currentArgument.nextIndex();
    }

    boolean getBoolean(char arg) throws ArgsException {
        ProcessArgumentMarshaller processArg = new ProcessArgumentMarshaller(marshaller);
        return processArg.process(BooleanArgumentMarshaller.class, arg);
    }


    String getString(char arg) throws ArgsException {
        ProcessArgumentMarshaller processArg = new ProcessArgumentMarshaller(marshaller);
        return processArg.process(StringArgumentMarshaller.class, arg);
    }

    int getInt(char arg) throws ArgsException {
        ProcessArgumentMarshaller processArg = new ProcessArgumentMarshaller(marshaller);
        return processArg.process(IntegerArgumentMarshaller.class, arg);
    }

    double getDouble(char arg) throws ArgsException {
        ProcessArgumentMarshaller processArg = new ProcessArgumentMarshaller(marshaller);
        return processArg.process(DoubleArgumentMarshaller.class, arg);
    }

    String[] getStringArray(char arg) throws ArgsException {
        ProcessArgumentMarshaller processArg = new ProcessArgumentMarshaller(marshaller);
        return processArg.process(StringArrayArgumentMarshaller.class, arg);
    }

    Map getMap(char arg) throws ArgsException {
        ProcessArgumentMarshaller processArg = new ProcessArgumentMarshaller(marshaller);
        return processArg.process(MapArgumentMarshaller.class, arg);
    }


}
