import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.StringBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.cli.*;

/**
 * Created by tadhg on 06/12/14.
 */
public class LiftingLog {


    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    void getPlotData(String exercise){

        String readPath = "/home/tadhg/ProductivityHud/productivity-hud/gym/" + exercise + ".txt";
        FileReader fr = null;
        try {
            fr = new FileReader(readPath);
        } catch (IOException e){e.printStackTrace();}

        BufferedReader br = new BufferedReader(fr);
        StringBuffer aStringBuffer = new StringBuffer();

        try {
            String line = br.readLine();

            while (line != null && line.length() > 1) {

                String[] parts = line.split(",");
                String date = parts[0];
                String weight = parts[1];

                aStringBuffer.append(date + " ");
                aStringBuffer.append(weight + "\n");

                line = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                br.close();
            } catch (IOException e) {}
        }


        String writePath = "/home/tadhg/ProductivityHud/productivity-hud/gym/datfiles/" + exercise + ".dat";
        File file = new File(writePath);

        try{
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(aStringBuffer.toString());
            bw.close();
        } catch (IOException e){e.printStackTrace();}
    }




    void addExercise(String exercise){
        try {
            String filepath = "/home/tadhg/ProductivityHud/productivity-hud/gym/" + exercise + ".txt";
            File file = new File(filepath);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    void setCompleted(String exercise, double weight, int reps){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(date);

        String filepath = "/home/tadhg/ProductivityHud/productivity-hud/gym/" + exercise + ".txt";
        File file = new File(filepath);

        String contents = null;
        try{
            contents = readFile(file.getAbsolutePath(),Charset.defaultCharset());
        }catch (IOException e){e.printStackTrace();}


        StringBuffer aStringBuffer = new StringBuffer();

        aStringBuffer.append(today + ",");
        aStringBuffer.append(Double.toString(weight) + ",");
        aStringBuffer.append(Integer.toString(reps) + ",");

        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true)));
            out.println(aStringBuffer);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void plotExercise(String exercise){



        /*
            Formatting
         */

        String datPath = "/home/tadhg/ProductivityHud/productivity-hud/gym/datfiles/" + exercise + ".dat";
        try{
            String datfile = readFile(datPath, Charset.defaultCharset());
            String startDate = datfile.substring(0,datfile.indexOf(","));
        }catch (IOException e){e.printStackTrace();}

        String templatePath = "/home/tadhg/ProductivityHud/productivity-hud/gym/plotTemplate.txt";
        String gnuplotCommands = null;
        try{
            gnuplotCommands = readFile(templatePath, Charset.defaultCharset());
        }catch (IOException e){e.printStackTrace();}

        gnuplotCommands = gnuplotCommands.replace("{TITLE}",exercise);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        gnuplotCommands = gnuplotCommands.replace("{START-DATE}",startDate);

        gnuplotCommands = gnuplotCommands.replace("{END-DATE}",dateFormat.format(date));
        gnuplotCommands = gnuplotCommands.replace("{OUTPUT-FILE-NAME}",exercise);


        Runtime rt = Runtime.getRuntime();
        try{
            Process pr = rt.exec(gnuplotCommands);
        } catch (IOException e){e.printStackTrace();}

    }


    public static void main(String args[]){
        System.out.println("Wat");


        LiftingLog ll = new LiftingLog();
        /*
        ll.setCompleted("bp",50.5,8);
        ll.setCompleted("bp",55.5,8);
        ll.setCompleted("bp",60.0,8);
        */
/*
            for(int day =60;day <150; day++){
                System.out.println(day + ".0, 8");
            }
*/

        ll.getPlotData("bp");


    }
}





