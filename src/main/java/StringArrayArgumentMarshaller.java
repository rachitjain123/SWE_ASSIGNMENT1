import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class StringArrayArgumentMarshaller extends AbstractArgumentMarshaller<String[]> {
    private List<String> stringArrayList = new ArrayList<>();

    public void set(Iterator<String> currentArgument) throws ArgsException {
        try {
            stringArrayList.add(currentArgument.next());
        } catch (NoSuchElementException e) {
            throw new ArgsException(ErrorCode.MISSING_STRING);
        }
    }

    public String[] getValue() {
        return stringArrayList.toArray(new String[0]);
    }

    /**
     * @return default value if StringArrayArgumentMarshaller does not contain the element id
     */
    public String[] getDefaultValue() {
        return new String[0];
    }
}
