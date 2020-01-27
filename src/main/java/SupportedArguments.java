import java.util.HashMap;
import java.util.Map;

class SupportedArguments {
    /**
     * @return All the  arguments that schema supports
     * More Scalable approach
     */
    Map<String, AbstractArgumentMarshaller> getSupportedArguments() {
        Map<String, AbstractArgumentMarshaller> supportedArguments = new HashMap<>();
        supportedArguments.put("*", new StringArgumentMarshaller());
        supportedArguments.put("#", new IntegerArgumentMarshaller());
        supportedArguments.put("##", new DoubleArgumentMarshaller());
        supportedArguments.put("&", new MapArgumentMarshaller());
        supportedArguments.put("[*]", new StringArrayArgumentMarshaller());
        return supportedArguments;
    }
}
