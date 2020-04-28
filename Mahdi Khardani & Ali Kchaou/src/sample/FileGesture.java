package sample;

import java.io.*;

public class FileGesture {

    private BufferedReader bufferedReader;

    private BufferedWriter bufferedWriter;

    private File file;

    private FileWriter fileWriter;

    private FileReader fileReader;

    public FileGesture(File file){
        this.file=file;
    }

    public boolean open() throws IOException{
        file.setWritable(true);
        fileWriter=new FileWriter(file.getAbsoluteFile(), true);
        bufferedWriter = new BufferedWriter(fileWriter);
        fileReader=new FileReader(file.getAbsoluteFile());
        bufferedReader=new BufferedReader(fileReader);
        return true;
    }

    public void write(String chaine) throws IOException {
        if(!chaine.equals("")){
            bufferedWriter.write(chaine, 0, chaine.length());
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(chaine);
            bufferedWriter.newLine();
        }
        else
        {
            PrintWriter printWriter=new PrintWriter(file);
            printWriter.print("");
            printWriter.close();
        }
        close();
    }

    public String read() throws IOException {

        String finalResult="";
        String currentValue;
            while((currentValue=bufferedReader.readLine())!=null)
                finalResult+=currentValue+'\n';
        return finalResult;
    }

    public void close() throws IOException {
        bufferedReader.close();
        fileReader.close();
        bufferedWriter.close();
    }

    public void setFile(File file) {
        this.file = file;
    }
}
