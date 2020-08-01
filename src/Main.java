import DecodeClasses.Decoder;
import EncodeClasses.EncodeTextFile;
import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {
        String input_path=null;
    Scanner input = new Scanner(System.in);
        String optionValue = null;
        System.out.println("for dell with Image file click 1");
        System.out.println("for dell with Text file click 2");
        System.out.println("for dell with Folder click 3");
        optionValue = input.nextLine();
        switch (optionValue){
            case "1":
                System.out.println("*****LZW Image Compression and DCompression*****");
                System.out.println("Enter 1 for Encode Image File according the entered path");
                System.out.println("Enter 2 for Decode Image File according the entered path");
                System.out.println("your choose is : ");
                optionValue = input.nextLine();
                switch (optionValue){
                    case "1":
                        System.out.println("Enter image to compress with extension: ");
                        input_path = input.nextLine();
                        EncodeTextFile encoder = new EncodeTextFile();
                        encoder.setInputPath(input_path);
                        encoder.compressImage();
                        break;
                    case "2":
                        System.out.println("Enter image to compress with extension: ");
                        EncodeTextFile encoder2 = new EncodeTextFile();
                        input_path = input.nextLine();
                        encoder2.setInputPath(input_path);
                        encoder2.decopressImage();
                        break;
                    default:
                        System.out.println("Invalid value please retry Enter valid value");
                        System.out.println("Enter 1 for Encode Image File according the entered path");
                        System.out.println("Enter 2 for Decode Image File according the entered path");
                        System.out.println("your choose is : ");
                        optionValue = input.nextLine();
                }
                break;
            case "2":
                System.out.println("*****LZW Text Compression and DCompression*****");
                System.out.println("Enter 1 for Encode Text File according the entered path");
                System.out.println("Enter 2 for Decode Text File according the entered path");
                System.out.println("your choose is : ");
                optionValue = input.nextLine();
                switch (optionValue){
                    case "1":
                        System.out.println("Enter the name of your (input.txt) file.");
                         input_path = input.nextLine();
                        EncodeTextFile encoder = new EncodeTextFile();
                        encoder.setInputPath(input_path);
                        encoder.EncodeTextFile();
                        break;
                    case "2":
                        System.out.println("Enter the name of your (input.txt) file.");
                         input_path = input.nextLine();
                        Decoder decoder = new Decoder();
                        decoder.DecodeTextFile(input_path);
                        break;
                    default:
                        System.out.println("Invalid value please retry Enter valid value");
                        System.out.println("Enter 1 for Encode Text File according the entered path");
                        System.out.println("Enter 2 for Decode Text File according the entered path");
                        System.out.println("your choose is : ");
                        optionValue = input.nextLine();
                }
                break;
                    case "3":
                        System.out.println("*****LZW Folder Compression and DCompression*****");
                        System.out.println("Enter the name of your Folder.");
                        input_path = input.nextLine();
                        EncodeTextFile encoder = new EncodeTextFile();
                        encoder.setInputPath(input_path);
                        encoder.compressFolder();
                        break;
            default:
                System.out.println("Invalid value please retry Enter valid value");
                System.out.println("for dell with Image file click 1");
                System.out.println("for dell with Text file click 2");
                System.out.println("your choose is : ");
                optionValue = input.nextLine();
        }
    }
}
