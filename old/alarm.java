import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Collections;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;	
import java.text.SimpleDateFormat;


class alarm {
    

	class timeTable{
		List<List<taskObj>> dayTimeTable = new ArrayList<List<taskObj>>(24);
	
		//void readTimeTable


	};


	
	class taskObj	{
		String taskDescription;
		//Start time
		int hour;
		int minute;
		int duration;

		int endingHour;
		int endingMin;
		
		boolean isRecurring;
	};


	void printTimetable(int daynumber, List<taskObj> timetable){

		for(taskObj task : timetable){
			System.out.println(task.taskDescription);
		}


    taskObj getTime(String timeString){
		
		taskObj taskStartTime = new taskObj();
		String timeStr = new String(timeString);

		taskStartTime.hour = Integer.parseInt(timeStr.substring(0,timeStr.indexOf(':')));
		taskStartTime.minute = Integer.parseInt(timeStr.substring(timeStr.indexOf(':')+1));

		return taskStartTime;
	}

	String getDay(int daynumber){
	
		if(daynumber==1){return "Sunday";}
		if(daynumber==2){return "Monday";}
		if(daynumber==3){return "Tuesday";}
		if(daynumber==4){return "Wednesday";}
		if(daynumber==5){return "Thursday";}
		if(daynumber==6){return "Friday";}
		if(daynumber==7){return "Saturday";}
		else{return "ERROR";}
	}

/*
	void addTask(List<taskObj> timetable,String taskDescr, 
		String timeString, int taskDuration){

		int x;

		taskObj newTask = getTime(timeString);
		newTask.taskDescription = taskDescr;
		newTask.duration = taskDuration;
		
		timetable.add(newTask);
	}
*/





    public static void main(String[] args) {


    	alarm anAlarm = new alarm();
    	List<List<taskObj>> dayTimeTable = new ArrayList<List<taskObj>>(24);




/*			/////// USEFUL

    	taskObj asd = anAlarm.getTime("23:15");
    	System.out.println(asd.hour + " " + asd.minute);

        System.out.println("Hello World! " + anAlarm.test); // Display the string.



		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		System.out.println(anAlarm.getDay(dayOfWeek));

*/


    }
}
}













