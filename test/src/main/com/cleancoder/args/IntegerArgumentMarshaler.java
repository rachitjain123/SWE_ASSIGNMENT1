package main.com.cleancoder.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntegerArgumentMarshaler extends AbstractArgumentMarshaler<Integer> {

    private int intValue;

    @Override
    public void set(Iterator<String> cuurentArgument) throws ArgsException {
        String parameter = null;
        try{
            parameter = cuurentArgument.next();
            intValue = Integer.parseInt(parameter);
        }
        catch (NoSuchElementException e) {
            throw new ArgsException(ErrorCode.MISSING_INTEGER, parameter);
        }
        catch (NumberFormatException e){
            throw new ArgsException(ErrorCode.INVALID_INTEGER, parameter);
        }
    }
    public Integer getValue(){
        return intValue;
    }

    public Integer getDefaultValue(){
        return 0;
    }
}
