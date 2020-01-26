import java.util.Map;

class ProcessArgumentMarshaler {
    private Map<Character, ArgumentMarshaler> marshalers;

    ProcessArgumentMarshaler(Map<Character, ArgumentMarshaler> marshalers){
        this.marshalers = marshalers;
    }

    <S, T extends ArgumentMarshaler<S>> S process(Class<S> type, Class<T> marshalerClass, char arg) {
        ArgumentMarshaler m = marshalers.get(arg);

        if (m != null) {
            try {
                marshalerClass.cast(m);
                return marshalerClass.cast(m).getValue();
            }
            catch (ClassCastException e) { }
        }

        try {
            return marshalerClass.newInstance().getDefaultValue();
        }
        catch (InstantiationException e1) {}
        catch (IllegalAccessException e1) {}
        return null;
    }
}
