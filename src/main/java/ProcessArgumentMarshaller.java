import java.util.Map;

class ProcessArgumentMarshaller {
    private Map<Character, ArgumentMarshaller> marshalers;

    ProcessArgumentMarshaller(Map<Character, ArgumentMarshaller> marshalers){
        this.marshalers = marshalers;
    }

    <S, T extends ArgumentMarshaller<S>> S process(Class<T> marshallerClass, char arg) {
        ArgumentMarshaller m = marshalers.get(arg);

        if (m != null) {
            try {
                marshallerClass.cast(m);
                return marshallerClass.cast(m).getValue();
            }
            catch (ClassCastException e) { }
        }

        try {
            return marshallerClass.newInstance().getDefaultValue();
        }
        catch (InstantiationException e1) {}
        catch (IllegalAccessException e1) {}
        return null;
    }
}
