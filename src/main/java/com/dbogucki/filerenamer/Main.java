package com.dbogucki.filerenamer;

import com.dbogucki.filerenamer.console.ArgumentResolver;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please provide a path to directory: ");
            args = new String[1];
            args[0] = sc.nextLine();
        }
        ArgumentResolver resolver = new ArgumentResolver(args);

        //  TODO: Change sout to Logging library (INFO)
        System.out.println("Path: " + resolver.getFilePath());
        System.out.println("Prefix: " + resolver.getPrefix());
        System.out.println("Leading Zeros: " + resolver.getLeadingZeros());

        Renamer renamer = new Renamer(resolver.getFilePath(), resolver.getPrefix(), resolver.getLeadingZeros());

        try {
            renamer.run();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
