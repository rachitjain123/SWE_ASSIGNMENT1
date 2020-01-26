
import java.util.Iterator;
import java.util.NoSuchElementException;

public class StringArgumentMarshaller extends AbstractArgumentMarshaller<String> {
    private String stringValue;

    public void set(Iterator<String> cuurentArgument) throws ArgsException {
        try {
            stringValue = cuurentArgument.next();
        }
        catch (NoSuchElementException e){
            throw new ArgsException(ErrorCode.MISSING_STRING);
        }
    }
    public String getValue(){
        return stringValue;
    }

    public String getDefaultValue() {
        return "";
    }

}
