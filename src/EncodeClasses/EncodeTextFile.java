package EncodeClasses;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import DecodeClasses.Decoder;
import org.apache.commons.io.FilenameUtils;

public class EncodeTextFile {
    private String inputPath ;

    // Define a HashMap and other variables that will be used in the program
    private  String outputpath;

    public String getOutputpath() {
        return outputpath;
    }

    public void setOutputpath(String outputpath) {
        this.outputpath = outputpath;
    }

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }
    private HashMap<String, Integer> dictionary = new HashMap<>();
    public void EncodeTextFile() throws IOException {
        BufferedReader input = null;
        BufferedWriter output = null;
         outputpath =(inputPath.split("\\."))[0] + ".lzw";
        String line = "";
        String symbol = "";
        String str = "";
        char temp[] = {};
        try {
            input = new  BufferedReader(new FileReader(inputPath));
            output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputpath), "UTF-16BE"));
            initDictionary();
            while ((line = input.readLine()) != null) {
                line += "\n";
                temp = line.toCharArray();
                int code = 255;
                for (int i = 0; i < temp.length; i++) {
                    symbol = String.valueOf(temp[i]);
                    if (dictionary.containsKey(str + symbol)) {
                        str += symbol;
                    }
                    else{
                        System.out.println(str);
                        output.write(dictionary.get(str));
                        System.out.print("Encoded data:\t" +dictionary.get(str) + "\t" + str);
                        code++;
                        dictionary.put((str + symbol),code);
                        System.out.println("\t\t\tTable updates:\t" + dictionary.get(str + symbol) + "\t" + str+symbol);
                        str = symbol;
                    }
                }
            }
            if(str.length() > 1){
                str = str.substring(0, str.length()-1);
                output.write(dictionary.get(str));
                System.out.println("Last Encoded data:\t" + dictionary.get(str) + "\t" + str);
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public void EncodeTextٍString(String string) throws IOException {
       initDictionary();
        String symbol= "";
        String str = "";
        int  out_put_code=255;
        for (int i = 0; i < string.length() ; i++) {
            symbol = String.valueOf(string.charAt(i));
            if (dictionary.containsKey(str + symbol)) {
                str += symbol;
            }
            else{
                out_put_code = dictionary.get(str);
                out_put_code++;
                dictionary.put(str+symbol,out_put_code);
                str = symbol;
                System.out.println(out_put_code);
            }
            symbol = "";
        }
    }

    public void compressImage() throws IOException {
       FileInputStream ImageStream = new FileInputStream(inputPath);
       byte[] data =  ImageStream.readAllBytes();
       String ImageString = Base64.getEncoder().encodeToString(data);
       String outputpath =(inputPath.split("\\."))[0] + ".txt";
       FileWriter fileWriter = new FileWriter(outputpath);
       fileWriter.write(ImageString);
       fileWriter.close();
       ImageStream.close();
       setInputPath(outputpath);
       EncodeTextFile();
       File file = new File(outputpath);
       file.delete();
    }

    public void decopressImage() throws IOException {
        Decoder decoder = new Decoder();
        decoder.DecodeTextFile(inputPath);
        FileInputStream fileInputStream =  new FileInputStream(decoder.getOutputFile());
        byte[] data2 = Base64.getDecoder().decode(new String(fileInputStream.readAllBytes()));
        String pathOfImageResult = (inputPath.split("\\."))[0] + "new.gif";
        FileOutputStream fileOutputStream =  new FileOutputStream(pathOfImageResult);
        fileOutputStream.write(data2);
        fileOutputStream.close();
        fileInputStream.close();
        File file = new File(decoder.getOutputFile());
        file.delete();
    }

    public void compressFolder() throws IOException {
        List<File> filesInFolder = Files.walk(Paths.get(inputPath))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList())
                ;
        String folderResultPath = (inputPath.split("\\."))[0] + "compressFolder";
        File file = new File(folderResultPath);
        file.mkdir();
        int depth = 0;
        for (int i = 0; i < filesInFolder.size(); i++) {
            String extension = FilenameUtils.getExtension(filesInFolder.get(i).getAbsolutePath());
            if(filesInFolder.get(i).isDirectory()){
                depth ++ ;
                setInputPath(filesInFolder.get(i).getPath());
                compressFolder();
                Files.move
                        (Paths.get(filesInFolder.get(i).getAbsolutePath()),
                                Paths.get(folderResultPath));
            }
            else if (extension.equals("txt")) {
                setInputPath(filesInFolder.get(i).getAbsolutePath());
                EncodeTextFile();
                if(depth==0){
                    File LzwFile = new File(getOutputpath());
                    Files.move
                            (Paths.get(getOutputpath()),
                                    Paths.get(folderResultPath+"\\"+LzwFile.getName()));
                }
            }else if(
                     extension.equals("jpg") || extension.equals("JPG") ||
                     extension.equals("PNG") || extension.equals("png") ||
                     extension.equals("gif")
                   ){
                setInputPath(filesInFolder.get(i).getAbsolutePath());
                setOutputpath(folderResultPath);
                compressImage();
                if(depth==0){
                    File LzwFile = new File(getOutputpath());
                    Files.move
                            (Paths.get(getOutputpath()),
                                    Paths.get(folderResultPath+"\\"+LzwFile.getName()));
                }
            }
        }

    }

    public <K,V> K getKeyFromDictionaryMap(Map<K , V> map, V value){
        for (Map.Entry<K, V> entry:map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void initDictionary(){
        for (int i = 0; i < 256; i++) {
            dictionary.put(String.valueOf((char) i) , i);
        }
    }



}
