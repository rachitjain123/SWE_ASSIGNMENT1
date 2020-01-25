package main.com.cleancoder.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StringArgumentMarshaler implements ArgumentMarshaler {
    private String stringValue;

    @Override
    public void set(Iterator<String> cuurentArgument) throws ArgsException {
        try {
            stringValue = cuurentArgument.next();
        }
        catch (NoSuchElementException e){
            throw new ArgsException(ErrorCode.MISSING_STRING);
        }
    }

    public static String getValue(ArgumentMarshaler am) {
        if(am instanceof StringArgumentMarshaler) {
            return ((StringArgumentMarshaler) am).stringValue;
        }
        return "";
    }
}
