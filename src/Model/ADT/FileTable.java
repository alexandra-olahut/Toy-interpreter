package Model.ADT;

import Model.Values.StringValue;
import java.io.BufferedReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FileTable implements IFileTable {

    private final Map<StringValue, BufferedReader> files;
    public FileTable() {files = Collections.synchronizedMap(new HashMap<>());}

    @Override
    public void addFile(StringValue filename, BufferedReader fd) { files.put(filename, fd); }
    @Override
    public void removeFile(StringValue filename) { files.remove(filename); }
    @Override
    public boolean isOpen(StringValue filename) { return files.containsKey(filename); }
    @Override
    public BufferedReader getFd(StringValue filename) { return files.get(filename); }

    @Override
    public Map<StringValue, BufferedReader> getContent() {return files;}

    @Override
    public String toString() {
        String result = "{";
        int pos = 0;
        for (StringValue elem : files.keySet()) {
            if (pos == 0) result += "" + elem;
            else result += "," + elem;
            pos++;
        }
        result+="}";
        return result;
    }

    @Override
    public String toFile() {
        String result = "  FileTable:";
        for (StringValue file : files.keySet()) {
            result += "\n" + file;
        }
        return result;
    }
}
