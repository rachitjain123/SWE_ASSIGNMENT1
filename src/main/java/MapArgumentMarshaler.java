import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class MapArgumentMarshaler extends AbstractArgumentMarshaler<Map> {
    private Map<String, String> map = new HashMap<>();

    public void set(Iterator<String> cuurentArgument) throws ArgsException {
        try {
            String[] mapEntries = cuurentArgument.next().split(",");
            for (String entry : mapEntries) {
                String[] entryComponents = entry.split(":");
                if (entryComponents.length != 2)
                    throw new ArgsException(ErrorCode.MALFORMED_MAP);
                map.put(entryComponents[0], entryComponents[1]);
            }
        }
        catch (NoSuchElementException e){
            throw new ArgsException(ErrorCode.MISSING_MAP);
        }
    }
    public Map<String, String> getValue(){
        return map;
    }

    public Map<String,String> getDefaultValue(){
        return new HashMap<>();
    }

}
