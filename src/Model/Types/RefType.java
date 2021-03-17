package Model.Types;

import Model.Values.RefValue;
import Model.Values.Value;

public class RefType implements Type {

    private final Type inner;
    public RefType(Type in) {inner=in;}

    public Type getInner() {return inner;}

    @Override
    public boolean equals(Object other){
        if (other instanceof RefType)
            return inner.equals(((RefType) other).getInner());
        return false;
    }

    @Override
    public String toString() {return  inner.toString()+"*";}

    @Override
    public Value defaultValue() { return new RefValue(0,inner);}
}
