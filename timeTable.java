//learn words
//poems
//shopping list
///diet considerations
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
import java.util.TimeZone;




class timeTable{

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    /*
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    */



    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }


    //List of tasks for the day
    List<taskObj> taskList = new ArrayList<taskObj>();


    public timeTable() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        date = calendar.getTime();
        System.out.println(date.toString());

        DateFormat dateFormat = new SimpleDateFormat("EEE");
        String today = dateFormat.format(date);



        dateFormat = new SimpleDateFormat("yyyy-MM-dd-EEE");
        String tlistTimestamp = dateFormat.format(date);

        System.out.println(tlistTimestamp);


        String filepath = "/home/tadhg/ProductivityHud/productivity-hud/timetables/" + today + ".txt";
        System.out.println(filepath);
        System.out.println(today);
        taskList = readTimeTable(filepath);
        try{
            System.out.println(readFile(filepath, Charset.defaultCharset()));

        }catch(IOException e){e.printStackTrace();}
        System.out.println("Today");
        printTasks(taskList);
        saveTasks(taskList,tlistTimestamp);
    }


	class taskObj{

		String taskDescription;

		Date startTime;
		Date endTime;

		taskObj(String description){
			this.taskDescription = description;
		}
	}

	void saveTasks(List<taskObj> listOfTasks,String dateFileName){
		if(listOfTasks != null){


            ///// TRYING TO SORT THIS GOD DAMN LIST OF TASKS BY START TIME
            System.out.println(listOfTasks.toString());
            Collections.sort(listOfTasks, new CustomComparator());
            System.out.println(listOfTasks.toString());

			StringBuffer aStringBuffer = new StringBuffer();
            SimpleDateFormat printFormat = new SimpleDateFormat("HH:mm");
			for(taskObj aTask : listOfTasks){
				aStringBuffer.append(aTask.taskDescription + ",");
				aStringBuffer.append(printFormat.format(aTask.startTime) + ",");
				aStringBuffer.append(printFormat.format(aTask.endTime) + ",");
				aStringBuffer.append("\n");
			}

			try {
                String filepath = "/home/tadhg/ProductivityHud/productivity-hud/timetables/" + dateFileName + ".txt";
				File file = new File(filepath);
	 
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

	List<taskObj> readTimeTable(String filepath){
		
		List<taskObj> tt = new ArrayList<taskObj>();

		FileReader fr = null;

		try {
			fr = new FileReader(filepath);
		} catch (IOException e){e.printStackTrace();}

		BufferedReader br = new BufferedReader(fr);

  		try {
	        String line = br.readLine();

	        while (line != null && line.length() > 1) {

				String[] parts = line.split(",");
				
	        	taskObj aTask = new taskObj(parts[0]);
	        	//aTask.taskDescription = parts[0];
		        try{
                    try{
                        //this may not be necessary anymore

                        Date st = new SimpleDateFormat("EEE MMM dd HH:mm zzz yyyy").parse(parts[1]);
                        aTask.startTime = st;
                        Date et = new SimpleDateFormat("EEE MMM dd HH:mm zzz yyyy").parse(parts[2]);
                        aTask.endTime = et;
                    }catch (ParseException e) {
                        Date st = new SimpleDateFormat("HH:mm").parse(parts[1]);
                        aTask.startTime = st;
                        Date et = new SimpleDateFormat("HH:mm").parse(parts[2]);
                        aTask.endTime = et;
                    }

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

				System.out.println(ANSI_RED + "Description: " + ANSI_RESET + " " + aTask.taskDescription);
				System.out.println(ANSI_RED + "Start Time: " + ANSI_RESET  + " " + printFormat.format(aTask.startTime));
				System.out.println(ANSI_RED + "End Time: " + ANSI_RESET  + " " + printFormat.format(aTask.endTime));
				System.out.println("\n");

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

            File file = new File("/home/tadhg/ProductivityHud/productivity-hud/timetables/" + day + ".txt");

            String contents = null;
            try{
                contents = readFile(file.getAbsolutePath(),Charset.defaultCharset());
            }catch (IOException e){e.printStackTrace();}

            if(contents.contains(newTask.taskDescription) == false){

                StringBuffer aStringBuffer = new StringBuffer();

                aStringBuffer.append(newTask.taskDescription + ",");
                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                String startTimeFiltered = dateFormat.format(newTask.startTime);
                String endTimeFiltered = dateFormat.format(newTask.endTime);

                aStringBuffer.append(startTimeFiltered + ",");
                aStringBuffer.append(endTimeFiltered + ",");

                try {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true)));
                    out.println(aStringBuffer);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
	tt.saveTasks(tt.taskList,"THISISATEST");

	//tt.printTasks(tt.readTimeTable("/home/tadhg/ProductivityHud/productivity-hud/timetables/test.txt"));
    //tt.readTimeTable("/home/tadhg/ProductivityHud/timetables/test.txt");

    /*
    tt.addRecurringTask("test task", "02:19", 44, "Mon,Wed,Sun");
    tt.addRecurringTask("notherk","14:28", 555, "Mon,Wed,Sun");
    tt.addRecurringTask("anoooooooooother ask","05:49", 200, "Mon,Wed,Sun");
    tt.addRecurringTask("tes232222222","21:12", 20, "Mon,Wed,Sun");
    */
    tt.addRecurringTask("this is a test","22:12", 20, "Thu");

    tt.printTasks(tt.readTimeTable("/home/tadhg/ProductivityHud/productivity-hud/timetables/Mon.txt"));
 



}

}


