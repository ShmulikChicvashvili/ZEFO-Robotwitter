package com.robotwitter.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.robotwitter.webapp.control.account.TwitterAccountController;
import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.primitives.DBFollowersNumber;


public class FollowersNumberTest {
		@Before
		public void before()  {
			list1=new ArrayList<DBFollowersNumber>();
			list2=new ArrayList<DBFollowersNumber>();
			list3=new ArrayList<DBFollowersNumber>();
			list4=new ArrayList<DBFollowersNumber>();
			list5=new ArrayList<DBFollowersNumber>();
			
			dbnumfollowers = Mockito.mock(IDatabaseNumFollowers.class);
			
			// define return values for method get(twitterID)
			
			Mockito.when(dbnumfollowers.get((long) 1)).thenReturn(list1);

			Mockito.when(dbnumfollowers.get((long) 2)).thenReturn(list2);

			Mockito.when(dbnumfollowers.get((long) 3)).thenReturn(list3);

			tac1=new TwitterAccountController(1,"1","1","1",dbnumfollowers);
			tac2=new TwitterAccountController(2,"1","1","1",dbnumfollowers);
			tac3=new TwitterAccountController(3,"1","1","1",dbnumfollowers);
			//initializing the calendar
			Calendar calendar = Calendar.getInstance();

			// Initializing the possible dates
			from=new Date(2014,12,20);
			d1=new Date(2014,12,30);
			d2=new Date(2014,12,31);
			d3=new Date(2015,1,1);
			d4=new Date(2015,1,2);;
			d5=new Date(2015,1,3);
			d6=new Date(2015,1,4);
			
			t0=new Timestamp(from.getTime());
			t1=new Timestamp(d1.getTime());
			t2=new Timestamp(d2.getTime());
			t3=new Timestamp(d3.getTime());
			t4=new Timestamp(d4.getTime());
			t5=new Timestamp(d5.getTime());
			t6=new Timestamp(d6.getTime());

			
			// Initiallizing list 1 of test case 1:
			Long num=(long) 1;
			f11=new DBFollowersNumber(num, t1, 100,0,0);
			f12=new DBFollowersNumber(num, t2, 1001,0,0);
			f13=new DBFollowersNumber(num, t3, 10011,0,0);
			f14=new DBFollowersNumber(num, t4, 100111,0,0);
			f15=new DBFollowersNumber(num, t5, 1001111,0,0);

			list1.add(f11);
			list1.add(f12);
			list1.add(f13);
			list1.add(f14);
			list1.add(f15);
			
			// Initiallizing list 2 of test case 2:
			list2.add(f11);
			list2.add(f13);
			list2.add(f14);
			
			// list 3 is an empty list for test cases		
		}
		/**
		 * This case checks that the main idea of the function works.
		 */
		@SuppressWarnings("nls")
		@Test
		public void testAllDatesInside() 
		{
			Map<Date,Integer> test1=tac1.getAmountOfFollowers(from,t6);
			assertEquals(test1.size(),5);
			assertEquals(test1.get(d1).intValue(),100);
			assertEquals(test1.get(d2).intValue(),1001);
			assertEquals(test1.get(d3).intValue(),10011);
			assertEquals(test1.get(d4).intValue(),100111);
			assertEquals(test1.get(d5).intValue(),1001111);
		}
		/*
		 * This case is checking that in case of receiving in the parameters 
		 * from and to the null argument, we will give all the followers in our list. 
		 */
		@Test
		public void testNullInputGivesAll(){
			Map<Date,Integer> test2=tac1.getAmountOfFollowers(null,null);
			assertEquals(test2.size(),5);
			assertEquals(test2.get(d1).intValue(),100);
			assertEquals(test2.get(d2).intValue(),1001);
			assertEquals(test2.get(d3).intValue(),10011);
			assertEquals(test2.get(d4).intValue(),100111);
			assertEquals(test2.get(d5).intValue(),1001111);
		}
		/*
		 * Checking to see that in case of an empty database list everything works
		 * fine without any exceptions.
		 */
		@Test
		public void testNullList(){
			Map<Date,Integer> test1=tac3.getAmountOfFollowers(null,null);
			assertEquals(test1.size(),0);
		}
		/*
		 * Testing that in case of receiving only part of the list,
		 *  it will return the right output
		 */
		@Test
		public void testOnlyPartOfList(){
			Map<Date,Integer> test1=tac1.getAmountOfFollowers(d2,d4);
			assertEquals(test1.size(),3);
			assertEquals(test1.get(d2).intValue(),1001);
			assertEquals(test1.get(d3).intValue(),10011);
			assertEquals(test1.get(d4).intValue(),100111);
		}
		
		/*
		 * In this case we are checking that in case of receiving only one 
		 * null in our input we will return that this null will be the first or the latest
		 * date (accordingly) in our list.
		 */
		@Test
		public void testReceiveOneNull(){
			Map<Date,Integer> test1=tac1.getAmountOfFollowers(d2,null);
			Map<Date,Integer> test2=tac1.getAmountOfFollowers(null,d3);
			assertEquals(test1.size(),4);		
			assertEquals(test1.get(d2).intValue(),1001);
			assertEquals(test1.get(d3).intValue(),10011);
			assertEquals(test1.get(d4).intValue(),100111);
			assertEquals(test1.get(d5).intValue(),1001111);
			assertEquals(test2.size(),3);
			assertEquals(test2.get(d1).intValue(),100);
			assertEquals(test2.get(d2).intValue(),1001);
			assertEquals(test2.get(d3).intValue(),10011);
		}
		/*
		 * testing to see if we give as an input the same date as an existing date
		 * we will receive that information in our output - as we should.
		 */
		@Test
		public void testTheSameDate(){
			Map<Date,Integer> test2=tac1.getAmountOfFollowers(d1,d5);
			assertEquals(test2.size(),5);
			assertEquals(test2.get(d1).intValue(),100);
			assertEquals(test2.get(d2).intValue(),1001);
			assertEquals(test2.get(d3).intValue(),10011);
			assertEquals(test2.get(d4).intValue(),100111);
			assertEquals(test2.get(d5).intValue(),1001111);
		}
		List<DBFollowersNumber> list1;
		List<DBFollowersNumber> list2;
		List<DBFollowersNumber> list3;
		List<DBFollowersNumber> list4;
		List<DBFollowersNumber> list5;
		
		Date from;
		Date d1;
		Date d2;
		Date d3;
		Date d4;
		Date d5;
		Date d6;
		
		Timestamp t0;
		Timestamp t1;
		Timestamp t2;
		Timestamp t3;
		Timestamp t4;
		
		Timestamp t5;
		Timestamp t6;
		DBFollowersNumber f11;
		
		DBFollowersNumber f12;

	DBFollowersNumber f13; 
	


	DBFollowersNumber f14;
	
	DBFollowersNumber f15;
	
	TwitterAccountController tac1;
	
	TwitterAccountController tac2;
	
	TwitterAccountController tac3;
	
	IDatabaseNumFollowers dbnumfollowers;
}
