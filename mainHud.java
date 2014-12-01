import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Collections;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;	
import java.text.SimpleDateFormat;
import java.lang.StringBuffer;

class mainHud{

	public static void main(String[] args) {
		try{
			Runtime.getRuntime().exec("clear");
		}catch (final Exception e){ System.out.println("he");}
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date)); 

	}	
}
