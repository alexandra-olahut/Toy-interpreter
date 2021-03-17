package Model.Types;

import Model.Values.IntValue;
import Model.Values.Value;

public class IntType implements Type {

    @Override
    public boolean equals(Object other) {
        return other instanceof IntType;
    }

    @Override
    public String toString() {return "int";}

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }
}
