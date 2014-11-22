import java.util.ArrayList;
import java.util.List;
import java.io.*;

import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;	
import java.text.SimpleDateFormat;

class alarm {
    



	
	class taskObj
	{
		String taskDescription;
		int hour;
		int minute;
		int duration;
	};

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


	void addTask(List<taskObj> timetable,String taskDescr, String timeString, int taskDuration){
		int x;

		taskObj newTask = getTime(timeString);
		newTask.taskDescription = taskDescr;
		newTask.duration = taskDuration;
		
		timetable.add(newTask);
	}





    public static void main(String[] args) {

    	List<List<taskObj>> dayTimeTable = new ArrayList<List<taskObj>>(24);

    	alarm anAlarm = new alarm();

    	taskObj asd = anAlarm.getTime("23:15");
    	System.out.println(asd.hour + " " + asd.minute);

        System.out.println("Hello World!"); // Display the string.



		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		System.out.println(anAlarm.getDay(dayOfWeek));


    }
}












