import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleArgumentMarshaler extends AbstractArgumentMarshaler<Double> {
    private double doubleValue;

    DoubleArgumentMarshaler(){
        doubleValue = 0.0;
    }

    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException {
        String parameter = null;
        try{
            parameter = currentArgument.next();
            doubleValue = Double.parseDouble(parameter);
        } catch (NoSuchElementException e) {
            throw new ArgsException(ErrorCode.MISSING_DOUBLE, parameter);
        }
        catch (NumberFormatException e){
            throw new ArgsException(ErrorCode.INVALID_DOUBLE, parameter);
        }
    }

    public Double getValue() {
        return doubleValue;
    }

    public Double getDefaultValue() {
        return 0.0;
    }

}

