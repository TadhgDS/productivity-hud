import org.apache.commons.cli.*;

import java.util.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.lang.StringBuffer;

class mainHud{

    Options mainOptions = new Options();
    Options timetableOptions = new Options();
    Options radioOptions = new Options();

    void setOptions(){
        mainOptions.addOption("t", false, "display timetable");
        mainOptions.addOption("r", false, "play radio");
        mainOptions.addOption("f", false, "fitness");
        mainOptions.addOption("p", false, "poetry");


        //  timetableOptions.addOption( OptionBuilder.withDescription( "use SIZE-byte blocks" )
     //   .hasArg()
      //  .withArgName("SIZE")
      //  .create() );
        //timetableOptions.addOption()

        radioOptions.addOption("r", false, "play rnag");
        radioOptions.addOption("l", false, "play lyric");
        radioOptions.addOption("k", false, "kill radio");
    }



    void openMenu(Options options){
        try{
            Runtime.getRuntime().exec("clear");
        }catch (final Exception e){ e.printStackTrace();}

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "ops", options );

    }

    void selectOption (Options options){

        CommandLineParser parser = new BasicParser();
        Scanner scan = new Scanner(System.in);
        System.out.println("Choose an option:");


        String[] arguments;
        arguments = new String[1];
        arguments[0] = scan.nextLine();

        try{
            CommandLine cmd = parser.parse( options, arguments);

            if(cmd.hasOption("r")) {
                System.out.println("play radio");
            }
            if(cmd.hasOption("f")) {
                System.out.println("fitness");
            }
            if(cmd.hasOption("p")) {
                System.out.println("poetry");
            }
            if(cmd.hasOption("t")) {
                System.out.println("display timetable");
                //tt.printTasks(tt.taskList);
            }
            else {
                System.out.println("oops?");
            }


        }catch(ParseException e){e.printStackTrace();}


    }





	public static void main(String[] args) {

        timeTable tt = new timeTable();

        /*

         */


		try{
			Runtime.getRuntime().exec("clear");
		}catch (final Exception e){ e.printStackTrace();}

        Options mainOptions = new Options();

        mainOptions.addOption("t", false, "display timetable");
        mainOptions.addOption("r", false, "play radio");
        mainOptions.addOption("f", false, "fitness");
        mainOptions.addOption("p", false, "poetry");



        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "ops", mainOptions );



        CommandLineParser parser = new BasicParser();
        Scanner scan = new Scanner(System.in);
        System.out.println("Write something");
        String[] arguments = new String[1];
        arguments[0] = scan.nextLine();

        try{
            CommandLine cmd = parser.parse( mainOptions, arguments);

            if(cmd.hasOption("r")) {
                System.out.println("play radio");
            }
            if(cmd.hasOption("f")) {
                System.out.println("fitness");
            }
            if(cmd.hasOption("p")) {
                System.out.println("poetry");
            }
            if(cmd.hasOption("t")) {
                System.out.println("display timetable");
                tt.printTasks(tt.taskList);
            }
            else {
                System.out.println("oops?");
            }


        }catch(ParseException e){e.printStackTrace();}

	}	
}
