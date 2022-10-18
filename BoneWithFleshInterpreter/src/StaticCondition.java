/**
 * StaticCondition always evaluate to a predefined value on initialization
 * it can be used to create while (true) loops or turn an ifelse to else by
 * passing true to if e.g. ifelse(true) == else.
 */
public class StaticCondition extends Condition{

    private final boolean staticValue;

    /**
     * Creates a new static content with predefined value.
     * @param staticValue Values that this condition will always evaluate to.
     */
    public StaticCondition(boolean staticValue){
        this.staticValue = staticValue;
    }

    /**
     * Returns a predefined value.
     * @return Returns value that was specified on initialization.
     */
    @Override
    public boolean evaluate() {
        return staticValue;
    }
}
