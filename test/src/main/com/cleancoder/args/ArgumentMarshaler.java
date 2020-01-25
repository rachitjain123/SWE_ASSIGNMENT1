package main.com.cleancoder.args;

import java.util.Iterator;

public interface ArgumentMarshaler<T> {

    void set(Iterator<String> currentArgument) throws ArgsException;
    public T getValue();
    public T getDefaultValue();
}