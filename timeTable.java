//learn words
//poems
//shopping list
//diet considerations
//email
//latex
//news items
//upcoming events
//financial info

//include templates for mon-sunday




import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.util.Collections;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;	
import java.text.SimpleDateFormat;
import java.lang.StringBuffer;
import java.text.ParseException;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.file.Files;




class timeTable{

public static final String ANSI_RESET = "\u001B[0m";
public static final String ANSI_BLACK = "\u001B[30m";
public static final String ANSI_RED = "\u001B[31m";
public static final String ANSI_GREEN = "\u001B[32m";
public static final String ANSI_YELLOW = "\u001B[33m";
public static final String ANSI_BLUE = "\u001B[34m";
public static final String ANSI_PURPLE = "\u001B[35m";
public static final String ANSI_CYAN = "\u001B[36m";
public static final String ANSI_WHITE = "\u001B[37m";

static String readFile(String path, Charset encoding)
        throws IOException
{
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, encoding);
}

	class taskObj{

		String taskDescription;

		Date startTime;
		Date endTime;

		taskObj(String description){
			this.taskDescription = description;
		}
	}

	public List<taskObj> taskList = new ArrayList<taskObj>();

	void saveTasks(List<taskObj> listOfTasks){
		if(listOfTasks != null){
			StringBuffer aStringBuffer = new StringBuffer();
			for(taskObj aTask : listOfTasks){
				aStringBuffer.append(aTask.taskDescription + ",");
				aStringBuffer.append(aTask.startTime.toString() + ",");
				aStringBuffer.append(aTask.endTime.toString() + ",");
				aStringBuffer.append("\n");
			}

			try {
				File file = new File("/home/tadhg/ProductivityHud/timetables/sample.txt");
	 
				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}
	 
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(aStringBuffer.toString());
				bw.close();
	 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	List<taskObj> readTimeTable(String filename){
		
		List<taskObj> tt = new ArrayList<taskObj>();

		FileReader fr = null;

		try {
			fr = new FileReader(filename);
		} catch (IOException e){}

		BufferedReader br = new BufferedReader(fr);

  		try {
	        String line = br.readLine();

	        while (line != null && line.length() > 1) {

				String[] parts = line.split(",");
				
	        	taskObj aTask = new taskObj(parts[0]);
	        	//aTask.taskDescription = parts[0];
		        try{
                    Date st = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(parts[1]);
                    aTask.startTime = st;
                    Date et = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(parts[2]);
                    aTask.endTime = et;

		        } catch (ParseException e) {
		            e.printStackTrace();
				}

	        	tt.add(aTask);

	            line = br.readLine();
	        }
	        
	    } catch (IOException e) {
				e.printStackTrace();
		}finally {
			try{
		        br.close();
		    } catch (IOException e) {}
	    }

	    return tt;
	}


	void printTasks(List<taskObj> listOfTasks){
		if(listOfTasks != null){
			for(taskObj aTask : listOfTasks){

				SimpleDateFormat printFormat = new SimpleDateFormat("HH:mm");
				/*
				System.out.println(ANSI_RED + "Description: " + ANSI_RESET + aTask.taskDescription);
				System.out.println(ANSI_RED + "Start Time: " + ANSI_RESET + printFormat.format(aTask.startTime));
				System.out.println(ANSI_RED + "End Time: " + ANSI_RESET + printFormat.format(aTask.endTime));
				System.out.println("\n");
				*/
			}
		}
	}

	Date getTime(String timeString){
		Date taskStartTime = null;
		if(timeString != null){

			int hour = Integer.parseInt(timeString.substring(0,timeString.indexOf(':')));
			int minute = Integer.parseInt(timeString.substring(timeString.indexOf(':')+1));


			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY,hour);
			cal.set(Calendar.MINUTE,minute);
			cal.set(Calendar.SECOND,0);
			cal.set(Calendar.MILLISECOND,0);

			taskStartTime = cal.getTime();
		}
		return taskStartTime;
	}

	void addTask(String taskDescr, String timeString, Integer taskDuration){
		if((taskDescr != null) && (timeString != null) && (taskDuration != null)){
		    Calendar cal = Calendar.getInstance(); // creates calendar
		    cal.setTime(new Date()); // sets calendar time/date
		    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		    cal.getTime(); // returns new date object, one hour in the future

			//gets to here successfully
			taskObj newTask = new taskObj(taskDescr);
			//newTask.taskDescription = taskDescr;

			Date taskStartTime = getTime(timeString);
			newTask.startTime = taskStartTime;


			Calendar endCal = Calendar.getInstance();
			endCal.setTime(taskStartTime);
			endCal.add(Calendar.HOUR_OF_DAY, taskDuration/60);
			endCal.add(Calendar.MINUTE, taskDuration%60);
			
			newTask.endTime = endCal.getTime();


			taskList.add(newTask);
		}
	}






	void addRecurringTask(String taskDescr, String timeString, Integer taskDuration, String days){

        //building new task object
        taskObj newTask = new taskObj(taskDescr);
        Date taskStartTime = getTime(timeString);
        newTask.startTime = taskStartTime;

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(taskStartTime);
        endCal.add(Calendar.HOUR_OF_DAY, taskDuration/60);
        endCal.add(Calendar.MINUTE, taskDuration%60);
        newTask.endTime = endCal.getTime();


        String[] daysSplit = days.split(",");

        for(String day : daysSplit){

            //System.out.println("/home/tadhg/ProductivityHud/timetables/" + day + ".txt");
            File file = new File("/home/tadhg/ProductivityHud/timetables/" + day + ".txt");

            String contents = null;
            try{
                contents = readFile(file.getAbsolutePath(),Charset.defaultCharset());
            }catch (IOException e){e.printStackTrace();}

            if(contents.contains(newTask.taskDescription) == false){

                StringBuffer aStringBuffer = new StringBuffer();

                aStringBuffer.append(newTask.taskDescription + ",");
                aStringBuffer.append(newTask.startTime.toString() + ",");
                aStringBuffer.append(newTask.endTime.toString() + ",");

                try {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true)));
                    out.println(aStringBuffer);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /*
            try{
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(aStringBuffer.toString());
            bw.close();
            }
            catch (IOException e){}
            System.out.println("Done");
            */

    /*
            try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsoluteFile())))) {
                out.println(aStringBuffer.toString());
            }catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
    */
        }
	}










public static void main(String[] args) {

	timeTable tt = new timeTable();
	
	tt.addTask("first task", "04:04", 90);
	tt.addTask("second task", "01:36", 90);
	tt.addTask("third task", "13:12", 200);
	tt.addTask("fourth task", "14:36", 15);
	tt.addTask("fifth task", "22:36", 300);
	tt.addTask("6th task", "19:31", 5);

//	tt.printTasks(tt.taskList);
	tt.saveTasks(tt.taskList);

	//tt.printTasks(tt.readTimeTable("/home/tadhg/ProductivityHud/timetables/test.txt"));
    tt.readTimeTable("/home/tadhg/ProductivityHud/timetables/test.txt");

    tt.addRecurringTask("test task","02:19", 44, "mon,wed,sun");
    tt.addRecurringTask("notherk","14:28", 555, "mon,wed,sun");
    tt.addRecurringTask("anoooooooooother ask","05:49", 200, "mon,wed,sun");
    tt.addRecurringTask("tes232222222","21:12", 20, "mon,wed,sun");

    tt.readTimeTable("/home/tadhg/ProductivityHud/timetables/mon.txt");

}

}


