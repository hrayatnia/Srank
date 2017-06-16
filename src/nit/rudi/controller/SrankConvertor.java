package nit.rudi.controller;

import java.io.*;

/**
 * Created by rayatnia on 2017-05-31.
 */
public class SrankConvertor {
    private File srankCoreInputFile;
    private BufferedWriter inputWriter;


    public SrankConvertor(){
        this.srankCoreInputFile = new File("./Core/input.txt");
        this.inputWriter = createInputFile();

    }

    public void clearFile(){
        BufferedWriter out=null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(this.srankCoreInputFile), "UTF8"));
            PrintWriter pr = new PrintWriter(out);
            pr.write("");
            pr.close();
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }



    private BufferedWriter createInputFile(){
        BufferedWriter out=null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(this.srankCoreInputFile,true), "UTF8"));
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return out;
    }



    public void wirteStringInputFile(String val){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(this.inputWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer.append(val);
        writer.close();
    }

}
