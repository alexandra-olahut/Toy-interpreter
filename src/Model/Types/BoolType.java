package Model.Types;

import Model.Values.BoolValue;
import Model.Values.Value;

public class BoolType implements Type {

    @Override
    public boolean equals(Object other) {
        return other instanceof BoolType;
    }

    @Override
    public String toString() {return "bool";}

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }
}
