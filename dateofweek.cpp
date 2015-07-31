#include <iostream>
#include <cstring>

using namespace std;

bool is_leap_year(int);
int days_in_month(int, int);
string date_to_dayofweek(int, int, int);
string num_to_dayofweek(int);

int main(){
	int month, day, year;
	
	while(true){
		cin >> month;
		cin >> day;
		cin >> year;
		if(month == -1)
			break;
		else if(month >= 1 && month <= 12)
			cout<<date_to_dayofweek(month - 1, day, year)<<endl;
	}

	return 0;
}

bool is_leap_year(int year){
	if(year % 400 == 0)
		return true;
	else if(year % 100 == 0)
		return false;
	else if(year % 4 == 0)
		return true;
	return false;
}

int days_in_month(int month, int year){
	switch(month){
		case 0:
			return 31;
		case 1:
			return is_leap_year(year) ? 29 : 28;
		case 2:
			return 31;
		case 3:
			return 30;
		case 4:
			return 31;
		case 5:
			return 30;
		case 6:
			return 31;
		case 7:
			return 31;
		case 8:
			return 30;
		case 9:
			return 31;
		case 10:
			return 30;
		case 11:
			return 31;
	}
}

string date_to_dayofweek(int month, int day, int year){
	//known sunday is July 26, 2015
	int days_difference = 0;
	int in_between_month;
	if(month < 6){
		in_between_month = month + 1;
		while(in_between_month < 6){//add full months in between
			days_difference -= days_in_month(in_between_month++, year);
			//cout<<"dim: "<<days_in_month(in_between_month-1, year)<<endl;
		}
	}else if(month > 6){
		in_between_month = month - 1;
		while(in_between_month > 6)//add full months in between
			days_difference += days_in_month(in_between_month--, year);
	}
	//cout<<"days difference month: "<<days_difference<<endl;
	//add portions of months
	if(month < 6){
		days_difference -= days_in_month(month, year) - day + 26;
		cout<<"dd: "<<days_in_month(month, year) - day + 26<<endl;
	}
	else if(month > 6)
		days_difference += days_in_month(6, year) - 26 + day;
	else//same month
		days_difference += day - 26;
	//cout<<"days difference day: "<<days_difference<<endl;
	//add years
	while(year < 2015){
		days_difference -= is_leap_year(year + 1) ? 366 : 365;
		year++;
	}
	while(year > 2015){
		days_difference += is_leap_year(year) ? 366 : 365;
		year--;
	}
	//cout<<"days difference total: "<<days_difference<<endl;
	while(days_difference < 0)
		days_difference += 7;
	
	return num_to_dayofweek(days_difference % 7);
}

// date_to_dayofweek2(int month, int day, int year){
//}

string num_to_dayofweek(int num){
	switch(num){
		case 0:
			return "Sunday";
		case 1:
			return "Monday";
		case 2:
			return "Tuesday";
		case 3:
			return "Wednesday";
		case 4:
			return "Thursday";
		case 5:
			return "Friday";
		case 6:
			return "Saturday";
	}
}
