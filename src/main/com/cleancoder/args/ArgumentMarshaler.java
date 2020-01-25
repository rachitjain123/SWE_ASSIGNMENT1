package main.com.cleancoder.args;

import java.util.Iterator;

public interface ArgumentMarshaler{

    void set(Iterator<String> cuurentArgument)
            throws ArgsException;
}