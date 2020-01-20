package com.dbogucki.filerenamer.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilenameResolver {
    private String name;
    private String extension = "";

    public FilenameResolver(String fileName) {
        String regex = ".*\\.";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(fileName);
        if (m.find()) {
            name = fileName.substring(m.start(), m.end() - 1);
            extension = fileName.substring(m.end() - 1);
        } else name = fileName;

    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }

}
