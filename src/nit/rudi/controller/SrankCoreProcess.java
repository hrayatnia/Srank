package nit.rudi.controller;
import java.io.*;

/**
 * Created by rayatnia on 2017-06-07.
 */
public class SrankCoreProcess {
    private String lines="";
    public  SrankCoreProcess(String command) throws Exception{
        //ProcessBuilder builder = new ProcessBuilder(command);
        //builder.redirectErrorStream(true);
        //Process p = builder.start();
        Process p =Runtime.getRuntime().exec(command);
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;

        while ((line = r.readLine()) != null)
            this.lines +=line+"\n";
    }

    public String getLines() {
        return this.lines;
    }
}
