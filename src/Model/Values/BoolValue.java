package Model.Values;
import Model.Types.*;

import java.util.Objects;

public class BoolValue implements Value {

    private final boolean value;
    public BoolValue(boolean v) {value=v;}

    public boolean getValue() {return value;}
    @Override
    public Type getType() {return new BoolType();}
    @Override
    public String toString() {return String.valueOf(value);}

    @Override
    public boolean equals(Object other) {
        if (other instanceof BoolValue)
            return Objects.equals(((BoolValue)other).getValue(), value);
        return false;
    }
}
