package Model.Values;
import Model.Types.*;

import java.util.Objects;

public class IntValue implements Value {

    private final int value;
    public IntValue(int v) {value=v;}

    public int getValue() {return value;}
    @Override
    public Type getType() {return new IntType();}
    @Override
    public String toString() {return String.valueOf(value);}

    @Override
    public boolean equals(Object other) {
        if (other instanceof IntValue)
            return Objects.equals(((IntValue)other).getValue(), value);
        return false;
    }
}
