/**
 *
 */
package com.robotwitter.twitter;

// TODO: Auto-generated Javadoc
/**
 * The Class GainedLost.
 *
 * @author Eyal
 */
public class GainedLostNumFollowers
{

	/**
	 * Instantiates a new gained lost.
	 *
	 * @param total the total
	 * @param gained the gained
	 * @param lost the lost
	 */
	public GainedLostNumFollowers(int total, int gained, int lost)
	{
		super();
		this.total = total;
		this.gained = gained;
		this.lost = lost;
	}

	/**
	 * Gets the gained.
	 *
	 * @return the gained
	 */
	public int getGained()
	{
		return gained;
	}

	/**
	 * Gets the lost.
	 *
	 * @return the lost
	 */
	public int getLost()
	{
		return lost;
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public int getTotal()
	{
		return total;
	}

	/**
	 * Sets the gained.
	 *
	 * @param gained the gained to set
	 */
	public void setGained(int gained)
	{
		this.gained = gained;
	}

	/**
	 * Sets the lost.
	 *
	 * @param lost the lost to set
	 */
	public void setLost(int lost)
	{
		this.lost = lost;
	}

	/**
	 * Sets the total.
	 *
	 * @param total the total to set
	 */
	public void setTotal(int total)
	{
		this.total = total;
	}

	/** The total number of followers. */
	int total;

	/** The number of gained followers. */
	int gained;

	/** The number of lost followers. */
	int lost;
}
