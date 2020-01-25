package main.com.cleancoder.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleArgumentMarshaler implements ArgumentMarshaler {
    private double doubleValue;

    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException, NumberFormatException {
        String parameter = null;
        try{
            parameter = currentArgument.next();
            doubleValue = Double.parseDouble(parameter);
        } catch (NoSuchElementException e) {
            throw new ArgsException(ErrorCode.MISSING_DOUBLE);
        }
    }

    public static double getValue(ArgumentMarshaler am) {
        if(am instanceof DoubleArgumentMarshaler) {
            return ((DoubleArgumentMarshaler) am).doubleValue;
        }
        return 0.0;
    }
}

