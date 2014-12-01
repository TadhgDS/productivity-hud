/* localtime example */
#include <stdio.h>
#include <time.h>
#include <string.h>
#include <stdlib.h>

char dayTimeTable[24][60];   //24 hours a day, 60 minutes 

struct timeObj
{
	int hour;
	int minute;
};

struct timeObj getTime(char timeString[]){
	
	struct timeObj taskStartTime;

	char temp[2];

	int x;
	for(x = 0; timeString[x] != ':'; x++){
		temp[x] = timeString[x];
	}
	taskStartTime.hour = atoi(temp);

	for( ;timeString[x] != ':'; x++){
		temp[x] = timeString[x];
	}
	taskStartTime.minute = atoi(temp);

	return taskStartTime;

}



void addTask(char task[], char timeString[], int length){
	int x;

	struct timeObj aTime = getTime(timeString);

	for(x=0;x<length;x++){
		dayTimeTable[aTime.hour][aTime.minute+x] = task;
	}

}


void getDay(int daynumber){
	
	if(daynumber==0){printf("Sunday");}
	if(daynumber==1){printf("Monday");}
	if(daynumber==2){printf("Tuesday");}
	if(daynumber==3){printf("Wednesday");}
	if(daynumber==4){printf("Thursday");}
	if(daynumber==5){printf("Friday");}
	if(daynumber==6){printf("Saturday");}
}

main()
{
	time_t rawtime;
	struct tm * timeinfo;

	time ( &rawtime );
	timeinfo = localtime ( &rawtime );

	getDay(timeinfo->tm_wday);
	printf ( ", %dth \n", timeinfo->tm_mday );
	printf ( "Time: %d:%d \n", timeinfo->tm_hour, timeinfo->tm_min );


	return 0;

}