import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class MapArgumentMarshaller extends AbstractArgumentMarshaller<Map> {
    private Map<String, String> map = new HashMap<>();

    public void set(Iterator<String> currentArgument) throws ArgsException {
        try {
            String[] mapEntries = currentArgument.next().split(",");
            for (String entry : mapEntries) {
                String[] entryComponents = validateEntry(entry);
                map.put(entryComponents[0], entryComponents[1]);
            }
        } catch (NoSuchElementException e) {
            throw new ArgsException(ErrorCode.MISSING_MAP);
        }
    }

    /**
     * @return entryComponents
     * @throws ArgsException MALFORMED_MAP
     */
    private String[] validateEntry(String entry) throws ArgsException {
        String[] entryComponents = entry.split(":");
        if (entryComponents.length != 2) {
            throw new ArgsException(ErrorCode.MALFORMED_MAP);
        }
        return entryComponents;
    }

    public Map<String, String> getValue() {
        return map;
    }

    /**
     * @return default value if MapArgumentMarshaller does not contain the element id
     */
    public Map<String, String> getDefaultValue() {
        return new HashMap<>();
    }

}
