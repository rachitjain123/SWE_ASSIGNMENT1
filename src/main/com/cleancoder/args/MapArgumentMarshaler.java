package main.com.cleancoder.args;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class MapArgumentMarshaler implements ArgumentMarshaler {
    private Map<String, String> map = new HashMap<>();

    @Override
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

    static Map<String, String> getValue(ArgumentMarshaler am) {
        if(am instanceof MapArgumentMarshaler) {
            return ((MapArgumentMarshaler) am).map;
        }
        return new HashMap<>();
    }
}
