package com.dbogucki.filerenamer.console;

public class ArgumentResolver {
    private String filePath;
    private String prefix;
    private int leadingZeros;

    public ArgumentResolver(String[] args) {
        filePath = args[0];
        leadingZeros = args.length < 2 || args[1].isEmpty() ? 0 : Integer.parseInt(args[1]);
        prefix = args.length < 3 || args[2].isEmpty() ? "" : args[2];
    }

    public String getFilePath() {
        return filePath;
    }

    public int getLeadingZeros() {
        return leadingZeros;
    }

    public String getPrefix() {
        return prefix;
    }

}
