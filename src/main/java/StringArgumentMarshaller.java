
import java.util.Iterator;
import java.util.NoSuchElementException;

public class StringArgumentMarshaller extends AbstractArgumentMarshaller<String> {
    private String stringValue;

    public void set(Iterator<String> currentArgument) throws ArgsException {
        try {
            stringValue = currentArgument.next();
        }
        catch (NoSuchElementException e){
            throw new ArgsException(ErrorCode.MISSING_STRING);
        }
    }
    public String getValue(){
        return stringValue;
    }

    /**
     * @return default value if StringArgumentMarshaller does not contain the element id
     */
    public String getDefaultValue() {
        return "";
    }

}
