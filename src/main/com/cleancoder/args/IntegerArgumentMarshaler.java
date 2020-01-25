package main.com.cleancoder.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntegerArgumentMarshaler implements ArgumentMarshaler {

    private int intValue;

    @Override
    public void set(Iterator<String> cuurentArgument) throws NumberFormatException, NoSuchElementException {
        String parameter = null;
        parameter = cuurentArgument.next();
        intValue = Integer.parseInt(parameter);
    }
    static int getValue(ArgumentMarshaler am) {
        if(am instanceof IntegerArgumentMarshaler) {
            return ((IntegerArgumentMarshaler) am).intValue;
        }
        return 0;
    }
}
