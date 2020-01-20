package com.dbogucki.filerenamer;

import com.dbogucki.filerenamer.utils.FilenameResolver;
import com.dbogucki.filerenamer.utils.Helper;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Renamer {

    private static String FILE_NAME = "fileRenamer";

    private String filePath;
    private String prefix;
    private int leadingZeros;

    public Renamer() {
        this(System.getProperty("user.dir"));
    }

    public Renamer(String filePath) {
        this(filePath, "", 0);
    }

    public Renamer(String filePath, String prefix, int leadingZeros) {
        this.filePath = filePath;
        this.prefix = prefix;
        this.leadingZeros = leadingZeros;

        String[] filenames = getFilenames();

        String count = Integer.toString(filenames.length);

        if (leadingZeros > 0) {
            if (leadingZeros < count.length()) {
                this.leadingZeros = count.length();
                // TODO: Change sout to Logging library (WARN)
                System.out.println("[WARN] Wrong \"leading zeros\" value inserted. Changing to: " + count.length());
            }
        } else {
            this.leadingZeros = count.length();
            // TODO: Change sout to Logging library (INFO)
            System.out.println("[INFO] Automatic \"leading zeros\" value: " + count.length());
        }

    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getLeadingZeros() {
        return leadingZeros;
    }

    public void setLeadingZeros(int leadingZeros) {
        this.leadingZeros = leadingZeros;
    }

    public void run() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        String targetDirectoryName = FILE_NAME + "_" + now.format(formatter);

        // TODO: Change sout to Logging library (INFO)
        System.out.println("[INFO] Starting renaming files in " + filePath + " into: " + targetDirectoryName);

        String targetFilePath = Helper.combinePaths(filePath, targetDirectoryName);
        File targetFile = new File(targetFilePath);

        if (!targetFile.exists() && !targetFile.mkdirs()) {
            throw new IOException("Unable to create " + targetFile.getAbsolutePath());
        }

        String[] filenames = getFilenames();
        int iteration = 1;
        String newFilename = "";

        for (String filename : filenames) {
            StringBuilder sb = new StringBuilder();
            FilenameResolver fr = new FilenameResolver(filename);
            sb.append(prefix);
            sb.append(String.format("%0" + leadingZeros + "d", iteration));
            sb.append(fr.getExtension());
            newFilename = sb.toString();

            // TODO: Change sout to Logging library (DEBUG)
            System.out.println("[DEBUG] Copying file: " + filename + " with name: " + newFilename);

            File oldFile = new File(Helper.combinePaths(filePath, filename));
            File newFile = new File(Helper.combinePaths(targetFilePath, newFilename));
            InputStream in = new BufferedInputStream(new FileInputStream(oldFile));
            OutputStream out = new BufferedOutputStream(new FileOutputStream(newFile));

            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }

            in.close();
            out.close();

            // TODO: Implement FileChecker (Validation of copying)
            if (!newFile.exists()) {
                System.out.println("[ERROR] Couldn't copy " + filename + " to " + targetDirectoryName);
                continue;
            }

            ++iteration;
        }
        // TODO: Change sout to Logging library (INFO)
        System.out.println("[INFO] " + (iteration - 1) + " files renamed.");

    }

    //TODO: Group by extension
    private String[] getFilenames() {
        String[] filenames;
        List<String> filteredList = new ArrayList<>();
        String[] filteredFilenames;
        File mainDirectory = new File(filePath);
        filenames = mainDirectory.list();
        for (String s : filenames) {
            File file = new File(Helper.combinePaths(filePath, s));
            if (file.isFile()) filteredList.add(s);
        }

        filteredFilenames = new String[filteredList.size()];
        filteredFilenames = filteredList.toArray(filteredFilenames);

        return filteredFilenames;
    }
}
