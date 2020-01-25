package main.com.cleancoder.args;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class StringArrayArgumentMarshaler implements ArgumentMarshaler {
    private List<String> strings = new ArrayList<>();

    @Override
    public void set(Iterator<String> cuurentArgument) throws ArgsException {
        try{
            strings.add(cuurentArgument.next());
        }
        catch (NoSuchElementException e){
            throw new ArgsException(ErrorCode.MISSING_STRING);
        }
    }

    public static String[] getValue(ArgumentMarshaler am) {
        if(am instanceof StringArrayArgumentMarshaler) {
            return ((StringArrayArgumentMarshaler) am).strings.toArray(new String[0]);
        }
        return new String[0];
    }
}
