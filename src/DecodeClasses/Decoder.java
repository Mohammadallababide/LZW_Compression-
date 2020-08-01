package DecodeClasses;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
public class Decoder {
    // Define a HashMap and other variables that will be used in the program
    private ArrayList<String> Table = new ArrayList<>();
    private String outputFile;

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public void DecodeTextFile(String path){
        BufferedReader input = null;
        File file = null;
        FileWriter fw = null;
        BufferedWriter output = null;
//        _decoded
         outputFile = (path.split("\\."))[0] + "new.txt";
        String line = "";
        String newLine = "";
        String code = "";
        String str = "";
        String newStr = "";
        char temp[] = {};
        try {
            file = new File(outputFile);
            fw = new FileWriter(file.getAbsoluteFile());
            input = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_16BE));
            output = new BufferedWriter(fw);
            initTable();
            boolean firstTime = true;
            line = input.readLine();
            while (line != null) {
                if((newLine = input.readLine()) != null){
                    line += "\n";
                }
                temp = line.toCharArray();
                for (int i = 0; i < temp.length; i++) {
                    code = String.valueOf(temp[i]);
                    if (firstTime) {
                        firstTime = false;
                        str = Table.get((int) code.charAt(0)); /* get the 'string' from table at index 'code' */
                        output.write(str); /* write the first 'string' of the first 'code' to output buffer */
                        System.out.print("Decoded data:\t" + Table.indexOf(str) + "\t" + str);
                        System.out.println("\t\t\tcodes:\t" + (int) code.charAt(0) + "\t" + code);
                        continue;
                    }
                    if (Table.size() <= (int) code.charAt(0)) {
                        newStr = str + str.toCharArray()[0]; /* if the 'code' is NOT DEFINED in the table then generate the 'new string' from 'string' */
                    }
                    else {
                        newStr = Table.get((int) code.charAt(0)); /* else get the 'new string' from the table at index 'code' */
                    }

                    output.write(newStr); /* write the 'new string' for the existing 'code' to output buffer */
                    Table.add(str + newStr.toCharArray()[0]);
                    System.out.print("\t\t\tcodes:\t" + (int) code.charAt(0) + "\t" + code);
                    System.out.println("\t\t\tTable updates:\t" + Table.indexOf(str + newStr.toCharArray()[0]) + "\t" + str + newStr.toCharArray()[0]);
                    str = newStr;

                }

                line = newLine;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally { // 'finally' block is always executed; cleanup code is included in most cases.
            if (input != null) {
                try {
                    input.close(); /* close the input buffer */
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (output != null) {
                try {
                    output.close(); /* close the output buffer */
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public  void initTable() {
        System.out.println("Initializing Table...\tTable size: " + Table.size());

        for (int i = 0; i < 256; i++) {
            Table.add(String.valueOf((char) i));
            // System.out.println(i + "\t" + Table.get(i));
        }

        System.out.println("Initialization completed...\tTable size: " + Table.size());
    }

}
