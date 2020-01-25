import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class StringArrayArgumentMarshaler extends AbstractArgumentMarshaler<String[]> {
    private List<String> strings = new ArrayList<>();

    public void set(Iterator<String> cuurentArgument) throws ArgsException {
        try{
            strings.add(cuurentArgument.next());
        }
        catch (NoSuchElementException e){
            throw new ArgsException(ErrorCode.MISSING_STRING);
        }
    }

    public String[] getValue(){
        return  strings.toArray(new String[0]);
    }

    public String[] getDefaultValue() {
        return new String[0];
    }
}
