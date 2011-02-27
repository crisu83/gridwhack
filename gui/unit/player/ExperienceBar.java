package gridwhack.gui.unit.player;

import gridwhack.entity.unit.player.Player;
import gridwhack.entity.unit.player.event.IPlayerListener;
import gridwhack.entity.unit.player.event.PlayerEvent;
import gridwhack.grid.GridUnit;
import gridwhack.gui.unit.StatusBar;

import java.awt.*;

/**
 * Experience bar class file.
 * Allows for visualizing player experience in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class ExperienceBar extends StatusBar implements IPlayerListener
{
	/**
	 * Creates the bar.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param width the width of the bar.
	 * @param height the height of the bar.
	 * @param owner the unit this bar belongs to.
	 */
	public ExperienceBar(int x, int y, int width, int height, GridUnit owner)
	{
		super(x, y, width, height, true, owner);

		// Experience bars should be purple.
		setBarColor( new Color(168, 0, 168) );
	}
	
	/**
	 * Refreshes the bar.
	 */
	private void refresh()
	{
		Player player = (Player) owner;
		int currentExperience = player.getLevelCurrentExperience( player.getLevel() );
		int maximumExperience = player.getLevelMaximumExperience( player.getLevel() );
		int barWidth = calculateBarWidth(currentExperience, maximumExperience);

		// Set the new current value.
		setBarWidth(barWidth);
	}

	/**
	 * Actions to be taken when the player gains experience.
	 * @param e the event.
	 */
	public synchronized void onPlayerGainExperience(PlayerEvent e)
	{
		refresh();
	}

	/**
	 * Actions to be taken when the player is gains a level.
	 * @param e the event.
	 */
	public synchronized void onPlayerGainLevel(PlayerEvent e)
	{
		refresh();
	}
}
