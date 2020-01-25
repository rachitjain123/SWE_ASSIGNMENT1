package main.com.cleancoder.args;

import java.util.Iterator;

public class BooleanArgumentMarshaler extends AbstractArgumentMarshaler<Boolean> {
    private boolean booleanValue;

    BooleanArgumentMarshaler() {
        booleanValue = false;
    }

    @Override
    public void set(Iterator<String> cuurentArgument) throws ArgsException {
        booleanValue = true;
    }

    public Boolean getValue() {
        return booleanValue;
    }

    @Override
    public Boolean getDefaultValue() {
        return false;
    }
}
