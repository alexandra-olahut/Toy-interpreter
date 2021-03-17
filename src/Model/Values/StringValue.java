package Model.Values;

import Model.Types.StringType;
import Model.Types.Type;

public class StringValue implements Value {

    private final String value;
    public StringValue(String v) {value=v;}

    public String getValue() {return value;}
    @Override
    public Type getType() {return new StringType();}
    @Override
    public String toString() {return "'" + value + "'";}

    @Override
    public boolean equals(Object other) {
        if (other instanceof StringValue)
            return ((StringValue)other).getValue().equals(value);
        return false;
    }
}
