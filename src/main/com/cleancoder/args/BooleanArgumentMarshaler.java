package main.com.cleancoder.args;

import java.util.Iterator;

public class BooleanArgumentMarshaler implements ArgumentMarshaler {
    private boolean booleanValue;

    BooleanArgumentMarshaler() {
        booleanValue = false;
    }

    @Override
    public void set(Iterator<String> cuurentArgument) throws ArgsException {
        booleanValue = true;
    }

    static boolean getValue(ArgumentMarshaler am) {
        if(am instanceof BooleanArgumentMarshaler) {
            return ((BooleanArgumentMarshaler) am).booleanValue;
        }
        return false;
    }
}
