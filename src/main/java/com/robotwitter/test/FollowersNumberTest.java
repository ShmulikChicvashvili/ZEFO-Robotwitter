
package com.robotwitter.test;


import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.robotwitter.database.interfaces.IDatabaseFollowers;
import com.robotwitter.database.interfaces.IDatabaseHeavyHitters;
import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.primitives.DBFollower;
import com.robotwitter.database.primitives.DBFollowersNumber;
import com.robotwitter.webapp.control.account.TwitterAccountController;




public class FollowersNumberTest
{
	@Before
	public void before()
	{
		list1 = new ArrayList<DBFollowersNumber>();
		list2 = new ArrayList<DBFollowersNumber>();
		list3 = new ArrayList<DBFollowersNumber>();

		flist1 = new ArrayList<DBFollower>();
		flist2 = new ArrayList<DBFollower>();
		flist3 = new ArrayList<DBFollower>();
		flist4 = new ArrayList<DBFollower>();

		hHlist1 = new ArrayList<Long>();
		hHlist2 = new ArrayList<Long>();
		hHlist3 = new ArrayList<Long>();
		hHlist4 = new ArrayList<Long>();

		dbnumfollowers = Mockito.mock(IDatabaseNumFollowers.class);
		dbHeavyHitters = Mockito.mock(IDatabaseHeavyHitters.class);
		dbFollowers = Mockito.mock(IDatabaseFollowers.class);

		// define return values for method get(twitterID)

		Mockito.when(dbnumfollowers.get((long) 1)).thenReturn(list1);

		Mockito.when(dbnumfollowers.get((long) 2)).thenReturn(list2);

		Mockito.when(dbnumfollowers.get((long) 3)).thenReturn(list3);

		Mockito.when(dbHeavyHitters.get((long) 1)).thenReturn(hHlist1);

		Mockito.when(dbHeavyHitters.get((long) 2)).thenReturn(hHlist2);

		Mockito.when(dbHeavyHitters.get((long) 3)).thenReturn(hHlist3);

		Mockito.when(dbHeavyHitters.get((long) 4)).thenReturn(hHlist4);

		Mockito.when(dbFollowers.getFollowers(1)).thenReturn(flist1);

		Mockito.when(dbFollowers.getFollowers(2)).thenReturn(flist2);

		Mockito.when(dbFollowers.getFollowers(3)).thenReturn(flist3);

		Mockito.when(dbFollowers.getFollowers(4)).thenReturn(flist4);// empty
																		// list

		Mockito.when(dbFollowers.get(1)).thenReturn(follower1);

		Mockito.when(dbFollowers.get(2)).thenReturn(follower2);

		Mockito.when(dbFollowers.get(3)).thenReturn(follower3);

		Mockito.when(dbFollowers.get(4)).thenReturn(follower4);

		tac1 =
			new TwitterAccountController(
				1,
				"1",
				"1",
				"1",
				dbnumfollowers,
				dbHeavyHitters,
				dbFollowers);
		tac2 =
			new TwitterAccountController(
				2,
				"1",
				"1",
				"1",
				dbnumfollowers,
				dbHeavyHitters,
				dbFollowers);
		tac3 =
			new TwitterAccountController(
				3,
				"1",
				"1",
				"1",
				dbnumfollowers,
				dbHeavyHitters,
				dbFollowers);
		tac4 =
			new TwitterAccountController(
				4,
				"1",
				"1",
				"1",
				dbnumfollowers,
				dbHeavyHitters,
				dbFollowers);
		// initializing the calendar
		final Calendar calendar = Calendar.getInstance();

		// Initializing the possible dates
		from = new Date(2014, 12, 20);
		d1 = new Date(2014, 12, 30);
		d2 = new Date(2014, 12, 31);
		d3 = new Date(2015, 1, 1);
		d4 = new Date(2015, 1, 2);
		d5 = new Date(2015, 1, 3);
		d6 = new Date(2015, 1, 4);

		t0 = new Timestamp(from.getTime());
		t1 = new Timestamp(d1.getTime());
		t2 = new Timestamp(d2.getTime());
		t3 = new Timestamp(d3.getTime());
		t4 = new Timestamp(d4.getTime());
		t5 = new Timestamp(d5.getTime());
		t6 = new Timestamp(d6.getTime());

		// Initiallizing list 1 of test case 1:
		final Long num = (long) 1;
		f11 = new DBFollowersNumber(num, t1, 100, 0, 0);
		f12 = new DBFollowersNumber(num, t2, 1001, 0, 0);
		f13 = new DBFollowersNumber(num, t3, 10011, 0, 0);
		f14 = new DBFollowersNumber(num, t4, 100111, 0, 0);
		f15 = new DBFollowersNumber(num, t5, 1001111, 0, 0);

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

		// Initializing the followers DB
		follower1 =
			new DBFollower(
				1,
				"Hagai Akibayov",
				"DonAkibayov",
				"Some Description",
				20,
				40,
				"Israel",
				5,
				"France",
				true,
				Timestamp.valueOf(LocalDateTime.now()),
				"http://pbs.twimg.com/profile_images/546786848849158145/wS82lZr8_normal.jpeg");
		follower2 =
			new DBFollower(
				2,
				"Eyal Tolchisnky",
				"DonTasd",
				"Somasdasdjasde Description",
				10,
				20,
				"Haifa",
				53,
				"English",
				false,
				Timestamp.valueOf(LocalDateTime.now()),
				"http://mkalty.org/wp-content/uploads/2014/06/3602836742_6f8c876e28.jpg");

		follower3 =
			new DBFollower(
				3,
				"Doron Hogery",
				"DonTasd",
				"Somasdasdjasde Description",
				5,
				10,
				"Haifa",
				53,
				"Hebrew",
				false,
				Timestamp.valueOf(LocalDateTime.now()),
				"http://mkalty.org/wp-content/uploads/2014/06/3602836742_6f8c876e28.jpg");
		follower4 =
			new DBFollower(
				4,
				"Itaykiflitz",
				"DonTasd",
				"Somasdasdjasde Description",
				15,
				30,
				"Haifa",
				53,
				"Hebrew",
				false,
				Timestamp.valueOf(LocalDateTime.now()),
				"http://mkalty.org/wp-content/uploads/2014/06/3602836742_6f8c876e28.jpg");

		// Initializing the heavyHitterslists
		hHlist1.add((long) 1);
		hHlist1.add((long) 2);
		hHlist1.add((long) 3);
		hHlist1.add((long) 4);

		hHlist2.add((long) 1);
		hHlist2.add((long) 3);

		hHlist3.add((long) 2);

		// hHlist4 is empty

		// Initializing the followersDB lists
		flist1.add(follower1);
		flist1.add(follower2);
		flist1.add(follower3);
		flist1.add(follower4);

		flist2.add(follower1);
		flist2.add(follower3);

		flist3.add(follower2);
	}


	// This section is for testing the function "getAmoutOfFollowers"
	/**
	 * This case checks that the main idea of the function works.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCaseAllDatesInsideInAmount()
	{
		final Map<Date, Integer> test1 = tac1.getAmountOfFollowers(from, t6);
		assertEquals(test1.size(), 5);
		assertEquals(test1.get(d1).intValue(), 100);
		assertEquals(test1.get(d2).intValue(), 1001);
		assertEquals(test1.get(d3).intValue(), 10011);
		assertEquals(test1.get(d4).intValue(), 100111);
		assertEquals(test1.get(d5).intValue(), 1001111);
	}


	// Testing the case when there is no followers in our twitter account
	@Test
	public void testCaseNoFollowersInFollowers()
	{
		final List<Integer> amounts = new ArrayList<Integer>();
		final List<Integer> separators = new ArrayList<Integer>();
		tac4.getFollowersAmountByTheirFollowersAmount(10, amounts, separators);
		assertEquals(0, amounts.size());
		assertEquals(0, separators.size());
	}


	// Testing the case when there is no followers in our twitter account
	@Test
	public void testCaseNoFollowersInFollowing()
	{
		final List<Integer> amounts = new ArrayList<Integer>();
		final List<Integer> separators = new ArrayList<Integer>();
		tac4.getFollowersAmountByTheirFollowingAmount(20, amounts, separators);
		assertEquals(0, amounts.size());
		assertEquals(0, separators.size());
	}


	// testing the case no followers on our twitter account
	@Test
	public void testCaseNoFollowersInLanguage()
	{
		final Map<String, Integer> test4 =
			tac4.getFollowersAmountByDisplayedLanguage();
		assertEquals(0, test4.size());
	}


	// testing all the normal behaviors like normal and empty databases with
	// different languages
	@Test
	public void testCaseNormalBehaviourInLanguage()
	{
		final Map<String, Integer> test1 =
			tac1.getFollowersAmountByDisplayedLanguage();
		final Map<String, Integer> test2 =
			tac2.getFollowersAmountByDisplayedLanguage();
		final Map<String, Integer> test3 =
			tac3.getFollowersAmountByDisplayedLanguage();

		assertEquals(3, test1.size());
		assertEquals((Integer) 2, test1.get("Hebrew"));
		assertEquals((Integer) 1, test1.get("France"));
		assertEquals((Integer) 1, test1.get("English"));

		assertEquals(2, test2.size());
		assertEquals((Integer) 1, test2.get("Hebrew"));
		assertEquals((Integer) 1, test2.get("France"));

		assertEquals(1, test3.size());
		assertEquals((Integer) 1, test3.get("English"));

	}


	@Test
	public void testCaseNormalBehavioursInFollowers()
	{
		final List<Integer> amounts = new ArrayList<Integer>();
		final List<Integer> separators = new ArrayList<Integer>();
		tac1.getFollowersAmountByTheirFollowersAmount(2, amounts, separators);
		assertEquals((Integer) 2, amounts.get(0));
		assertEquals((Integer) 2, amounts.get(1));
		assertEquals((Integer) 12, separators.get(0));
		assertEquals(1, separators.size());

		tac1.getFollowersAmountByTheirFollowersAmount(4, amounts, separators);
		assertEquals((Integer) 1, amounts.get(0));
		assertEquals((Integer) 1, amounts.get(1));
		assertEquals((Integer) 1, amounts.get(2));
		assertEquals((Integer) 1, amounts.get(3));

		assertEquals((Integer) 8, separators.get(0));
		assertEquals((Integer) 12, separators.get(1));
		assertEquals((Integer) 16, separators.get(2));
		assertEquals(3, separators.size());

	}


	// Section over

	// This section is for testing the function
	// "getFollowersAmountByDisplayedLanguage"

	// Section - Testing the function
	// "getFollowersAmountByTheirFollowingAmount()"
	@Test
	public void testCaseNormalBehavioursInFollowing()
	{
		final List<Integer> amounts = new ArrayList<Integer>();
		final List<Integer> separators = new ArrayList<Integer>();
		tac1.getFollowersAmountByTheirFollowingAmount(2, amounts, separators);
		assertEquals((Integer) 2, amounts.get(0));
		assertEquals((Integer) 2, amounts.get(1));
		assertEquals((Integer) 25, separators.get(0));
		assertEquals(1, separators.size());

		tac1.getFollowersAmountByTheirFollowingAmount(4, amounts, separators);
		assertEquals((Integer) 1, amounts.get(0));
		assertEquals((Integer) 1, amounts.get(1));
		assertEquals((Integer) 1, amounts.get(2));
		assertEquals((Integer) 1, amounts.get(3));

		assertEquals((Integer) 17, separators.get(0));
		assertEquals((Integer) 25, separators.get(1));
		assertEquals((Integer) 32, separators.get(2));
		assertEquals(3, separators.size());

	}


	/* This case is checking that in case of receiving in the parameters from
	 * and to the null argument, we will give all the followers in our list. */
	@Test
	public void testCaseNullInputGivesAllInAmount()
	{
		final Map<Date, Integer> test2 = tac1.getAmountOfFollowers(null, null);
		assertEquals(test2.size(), 5);
		assertEquals(test2.get(d1).intValue(), 100);
		assertEquals(test2.get(d2).intValue(), 1001);
		assertEquals(test2.get(d3).intValue(), 10011);
		assertEquals(test2.get(d4).intValue(), 100111);
		assertEquals(test2.get(d5).intValue(), 1001111);
	}


	// Section Over

	// Section - Testing the function
	// "getFollowersAmountByTheirFollowersAmount()"

	/* Checking to see that in case of an empty database list everything works
	 * fine without any exceptions. */
	@Test
	public void testCaseNullListInAmount()
	{
		final Map<Date, Integer> test1 = tac3.getAmountOfFollowers(null, null);
		assertEquals(test1.size(), 0);
	}


	// Testing the case when there is only one follower
	@Test
	public void testCaseOnlyOneFollowerInFollowers()
	{
		final List<Integer> amounts = new ArrayList<Integer>();
		final List<Integer> separators = new ArrayList<Integer>();
		tac3.getFollowersAmountByTheirFollowersAmount(2, amounts, separators);
		assertEquals(1, separators.size());
		assertEquals((Integer) 10, separators.get(0));
		assertEquals(1, amounts.size());
		assertEquals((Integer) 1, amounts.get(0));
	}


	// Testing the case when there is only one follower
	@Test
	public void testCaseOnlyOneFollowerInFollowing()
	{
		final List<Integer> amounts = new ArrayList<Integer>();
		final List<Integer> separators = new ArrayList<Integer>();
		tac3.getFollowersAmountByTheirFollowingAmount(2, amounts, separators);
		assertEquals(1, separators.size());
		assertEquals((Integer) 20, separators.get(0));
		assertEquals(1, amounts.size());
		assertEquals((Integer) 1, amounts.get(0));
	}


	/* Testing that in case of receiving only part of the list, it will return
	 * the right output */
	@Test
	public void testCaseOnlyPartOfListInAmount()
	{
		final Map<Date, Integer> test1 = tac1.getAmountOfFollowers(d2, d4);
		assertEquals(test1.size(), 3);
		assertEquals(test1.get(d2).intValue(), 1001);
		assertEquals(test1.get(d3).intValue(), 10011);
		assertEquals(test1.get(d4).intValue(), 100111);
	}


	// Section Over

	/* In this case we are checking that in case of receiving only one null in
	 * our input we will return that this null will be the first or the latest
	 * date (accordingly) in our list. */
	@Test
	public void testCaseReceiveOneNullInAmount()
	{
		final Map<Date, Integer> test1 = tac1.getAmountOfFollowers(d2, null);
		final Map<Date, Integer> test2 = tac1.getAmountOfFollowers(null, d3);
		assertEquals(test1.size(), 4);
		assertEquals(test1.get(d2).intValue(), 1001);
		assertEquals(test1.get(d3).intValue(), 10011);
		assertEquals(test1.get(d4).intValue(), 100111);
		assertEquals(test1.get(d5).intValue(), 1001111);
		assertEquals(test2.size(), 3);
		assertEquals(test2.get(d1).intValue(), 100);
		assertEquals(test2.get(d2).intValue(), 1001);
		assertEquals(test2.get(d3).intValue(), 10011);
	}


	/* testing to see if we give as an input the same date as an existing date
	 * we will receive that information in our output - as we should. */
	@Test
	public void testCaseTheSameDateInAmount()
	{
		final Map<Date, Integer> test2 = tac1.getAmountOfFollowers(d1, d5);
		assertEquals(test2.size(), 5);
		assertEquals(test2.get(d1).intValue(), 100);
		assertEquals(test2.get(d2).intValue(), 1001);
		assertEquals(test2.get(d3).intValue(), 10011);
		assertEquals(test2.get(d4).intValue(), 100111);
		assertEquals(test2.get(d5).intValue(), 1001111);
	}


	// Testing the case when there is too much subdivisions and that the list
	// still right
	@Test
	public void testCaseTooManySubDivInFollowers()
	{
		final List<Integer> amounts = new ArrayList<Integer>();
		final List<Integer> separators = new ArrayList<Integer>();
		tac1.getFollowersAmountByTheirFollowersAmount(50, amounts, separators);
		assertEquals(15, separators.size());
	}


	// Testing the case when there is too much subdivisions and that the list
	// still right
	@Test
	public void testCaseTooManySubDivInFollowing()
	{
		final List<Integer> amounts = new ArrayList<Integer>();
		final List<Integer> separators = new ArrayList<Integer>();
		tac1.getFollowersAmountByTheirFollowingAmount(50, amounts, separators);
		assertEquals(30, separators.size());
	}



	// End of section

	// Section - testing the function "getLastKnownAmountOfFollowers"
	// @Test
	// public void testCaseNormalInLastAmountOfFollowers(){
	// assertEquals(20,tac1.getLastKnownAmountOfFollowers());
	//
	// }

	List<DBFollowersNumber> list1;

	List<DBFollowersNumber> list2;

	List<DBFollowersNumber> list3;

	ArrayList<DBFollower> flist1;

	ArrayList<DBFollower> flist2;

	ArrayList<DBFollower> flist3;

	ArrayList<DBFollower> flist4;

	ArrayList<Long> hHlist1;

	ArrayList<Long> hHlist2;

	ArrayList<Long> hHlist3;

	ArrayList<Long> hHlist4;

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

	DBFollower follower1;

	DBFollower follower2;

	DBFollower follower3;

	DBFollower follower4;

	DBFollowersNumber f14;

	DBFollowersNumber f15;

	TwitterAccountController tac1;

	TwitterAccountController tac2;

	TwitterAccountController tac3;

	TwitterAccountController tac4;

	IDatabaseNumFollowers dbnumfollowers;

	IDatabaseHeavyHitters dbHeavyHitters;

	IDatabaseFollowers dbFollowers;
}
