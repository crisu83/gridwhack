package gridwhack.gui.character.player;

import gridwhack.entity.character.Character;
import gridwhack.entity.character.player.Player;
import gridwhack.entity.character.player.event.IPlayerListener;
import gridwhack.entity.character.player.event.PlayerEvent;
import gridwhack.gui.character.StatusBar;

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
	 * @param owner the character this bar belongs to.
	 */
	public ExperienceBar(int x, int y, int width, int height, Character owner)
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
