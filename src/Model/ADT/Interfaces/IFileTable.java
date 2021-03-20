package Model.ADT.Interfaces;

import Model.Values.StringValue;

import java.io.BufferedReader;
import java.util.Map;

public interface IFileTable {

    void addFile(StringValue filename, BufferedReader fd);
    void removeFile(StringValue filename);
    boolean isOpen(StringValue filename);
    BufferedReader getFd(StringValue filename);

    Map<StringValue, BufferedReader> getContent();

    String toFile();
}
