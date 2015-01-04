package com.robotwitter.test;

import static org.junit.Assert.assertEquals;

import com.robotwitter.*;

import org.bouncycastle.asn1.x509.Time;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.validation.constraints.AssertTrue;

import com.robotwitter.database.MySqlDatabaseTwitterAccounts;
import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBUser;
import com.robotwitter.webapp.control.account.TwitterAccountController;
import com.robotwitter.webapp.control.registration.IRegistrationController.Status;
import com.robotwitter.webapp.control.registration.RegistrationController;
import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.primitives.DBFollowersNumber;


public class FollowersNumberTest {

	@Before
	public void before()  {
		List<DBFollowersNumber> list1=new ArrayList<DBFollowersNumber>();
		List<DBFollowersNumber> list2=new ArrayList<DBFollowersNumber>();
		List<DBFollowersNumber> list3=new ArrayList<DBFollowersNumber>();
		List<DBFollowersNumber> list4=new ArrayList<DBFollowersNumber>();
		List<DBFollowersNumber> list5=new ArrayList<DBFollowersNumber>();
		
		IDatabaseNumFollowers dbnumfollowers = Mockito.mock(IDatabaseNumFollowers.class);
		
		// define return values for method get(twitterID)
		
		Mockito.when(dbnumfollowers.get((long) 1)).thenReturn(list1);

		Mockito.when(dbnumfollowers.get((long) 2)).thenReturn(list2);

		Mockito.when(dbnumfollowers.get((long) 3)).thenReturn(list3);

		Mockito.when(dbnumfollowers.get((long) 4)).thenReturn(list4);

		Mockito.when(dbnumfollowers.get((long) 5)).thenReturn(list5);

		TwitterAccountController tac1=new TwitterAccountController(1,"1","1","1",dbnumfollowers);

		//initializing the calendar
		Calendar calendar = Calendar.getInstance();

		// Initializing the possible dates
		Date from=new Date(2014,12,20);
		Date d1=new Date(2014,12,30);
		Date d2=new Date(2014,12,31);
		Date d3=new Date(2015,1,1);
		Date d4=new Date(2015,1,2);;
		Date d5=new Date(2015,1,3);
		Date d6=new Date(2015,1,4);
		
		Timestamp t0=new Timestamp(from.getTime());
		Timestamp t1=new Timestamp(d1.getTime());
		Timestamp t2=new Timestamp(d2.getTime());
		Timestamp t3=new Timestamp(d3.getTime());
		Timestamp t4=new Timestamp(d4.getTime());
		Timestamp t5=new Timestamp(d5.getTime());
		Timestamp t6=new Timestamp(d6.getTime());

		
		// Initiallizing list 1 of test case 1:
		Long num=(long) 1;
		DBFollowersNumber f11=new DBFollowersNumber(num, t1, 100);
		DBFollowersNumber f12=new DBFollowersNumber(num, t2, 1001);
		DBFollowersNumber f13=new DBFollowersNumber(num, t3, 10011);
		DBFollowersNumber f14=new DBFollowersNumber(num, t4, 100111);
		DBFollowersNumber f15=new DBFollowersNumber(num, t5, 1001111);

		list1.add(f11);
		list1.add(f12);
		list1.add(f13);
		list1.add(f14);
		list1.add(f15);
		
		// Initiallizing list 2 of test case 2:
		list2.add(f11);
		list2.add(f13);
		list2.add(f14);
		
		// Initiallizing list 3 of test case 3:

		
		// Initiallizing list 4 of test case 4:

		
		
		// Initiallizing list 5 of test case 5:

	  // TODO use mock in test.... 
		

		Map<Date,Integer> test1=tac1.getAmountOfFollowers(from,t6);
		assertEquals(test1.size(),5);
	} 
	


	/**
	 * Testing followersNumberTest
	 */
	@SuppressWarnings("nls")
	@Test
	public void test() 
	{
	}
}
/*
Calendar calendar = Calendar.getInstance();

// For example, two dates, the first being:
calendar.set(Calendar.YEAR, 1993);
calendar.set(Calendar.MONTH, Calendar.JUNE);
calendar.set(Calendar.DATE, 24);
calendar.set(Calendar.HOUR_OF_DAY, 13);
calendar.set(Calendar.MINUTE, 49);
calendar.set(Calendar.SECOND, 35);
// Obviously, you don't need to set everything. Just what you can.
// (If database has only hour precision now, don't set minutes, and
// seconds)
Date date1 = calendar.getTime();
int followers1 = 9000;
followers.put(date1, Integer.valueOf(followers1));

// For example, the second date can be
calendar.clear(); // First clear the previous date
calendar.set(1993, 6, 25);	// Then set 25 June, 1993 (for example)
calendar.set(Calendar.HOUR_OF_DAY, 13); // I can then set hour too
// See that I can set year, month, and date in one method
Date date2 = calendar.getTime();
int followers2 = 9001;
followers.put(date2, Integer.valueOf(followers2));
*/