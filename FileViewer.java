
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
public class FileViewer
{

    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {
        if (args.length == 0) {
            //f no parameters are passed, then the program should default to
            //displaying information on the current directory 
            displayDirectory(".");
        } else if (args.length == 1 && args[0].equals("-l")) {
            //“-l” (for (l)isting) with an optional directory as a 2nd parameter. If
            //no 2nd parameter is passed, default to the current directory “.”
            displayDirectory(".");
        } else if (args.length == 2 && args[0].equals("-l")) {
            //“-l” (for (l)isting) with an optional directory as a 2nd parameter
            displayDirectory(args[1]);
        } else if (args.length == 2 && args[0].equals("-v")) {
            //-v” (for (v)iew) with a file to view as the 2nd parameter
            displayFile(args[1]);
        } else if (args.length == 3 && args[0].equals("-c")) {
            copyFile(args[1],args[2]);
        } else if (args.length == 3 && args[0].equals("-d")) {
            compareFiles(args[1],args[2]);
        } else {
            System.out.println("Usage: java -jar hw4.jar [-l [<directory>]|-v <file>|-c <sourceFile> <destFile>|-d <file1> <file2>]");
        }
    }

    /**
     *
     * @param origin
     * @param destination
     */
    public static void copyFile(String origin, String destination)
    {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {            
            inputStream = new FileInputStream(new File(origin));
            File file = new File(destination);
            if (file.exists()) {
                System.out.println("file " + destination + " already exists");
                return;
            } else {
                outputStream = new FileOutputStream(destination);
            }
            int r;
            while ((r = inputStream.read()) != -1) {
                outputStream.write((byte) r);
            }
            System.out.println("File "+origin+" was copied to file "+destination+" successfully.");
        } catch (FileNotFoundException ex) {
            System.out.println("Invalid filename, please try again" );
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                // Close files
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    /**
     *
     * @param firstFile
     * @param secondFile
     */
    public static void compareFiles(String firstFile, String secondFile)
    {
        FileInputStream fileStream1 = null;
        FileInputStream fileStream2 = null;
        try {            
            fileStream1 = new FileInputStream(new File(firstFile));            
            fileStream2 = new FileInputStream(new File(secondFile));            
            int r1;
            do
            {
                r1=fileStream1.read();
                int r2=fileStream2.read();
                if(r1!=r2)
                {
                    System.out.println("The two files "+firstFile+" and "+secondFile+" are not the same." );
                    return;                    
                }                
            }while(r1!=-1);
            System.out.println("The two files "+firstFile+" and "+secondFile+" are the same." );
        } catch (FileNotFoundException ex) {
            System.out.println("Invalid filename, please try again" );
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                // Close files
                if (fileStream1 != null) {
                    fileStream1.close();
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    /**
     *
     * @param path
     */
    public static void displayFile(String path)
    {
        Scanner input = null;
        try {
            input = new Scanner(new File(path));
            while (input.hasNextLine()) {
                System.out.println(input.nextLine());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + path);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    /**
     *
     * @param path
     */
    public static void displayDirectory(String path)
    {
        try {
            File file = new File(path);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                System.out.println("Size Filename");
                for (File current : files) {
                    if (current.isDirectory()) {
                        System.out.printf("%-5s%s\n", "*", current.getName());
                    } else {
                        System.out.printf("%-5d%s\n", current.length(), current.getName());
                    }
                }
            } else {
                System.out.println("Error: Invalid Directory");
            }
        } finally {
        }
    }
}
