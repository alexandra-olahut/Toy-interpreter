package Model.Values;

import Model.Types.RefType;
import Model.Types.Type;

import java.util.Objects;

public class RefValue implements Value {

    private final int address;
    private final Type locationType;
    public RefValue(int a, Type l) {address=a; locationType=l;}

    public int getAddress() {return address;}
    public Type getLocationType() {return locationType;}

    @Override
    public Type getType() {return new RefType(locationType);}

    @Override
    public String toString() {
        if (address == 0) {
            return "null";
        } else {
            return "(" + address + "," + locationType + ")";
        }
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof RefValue)
            return (Objects.equals(((RefValue) other).getAddress(),address)
                    && locationType.equals(((RefValue) other).getLocationType()));
        return false;
    }
}
