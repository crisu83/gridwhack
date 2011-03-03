package gridwhack.gui.character.player;

import gridwhack.entity.character.Character;
import gridwhack.entity.character.player.Player;
import gridwhack.entity.character.player.event.IPlayerExperienceListener;
import gridwhack.entity.character.player.event.PlayerEvent;
import gridwhack.gui.character.StatusBar;

import java.awt.*;

/**
 * Experience bar class file.
 * Allows for visualizing player experience in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class ExperienceBar extends StatusBar implements IPlayerExperienceListener
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
		Player player = getOwner();
		int level = player.getLevel();
		int previousExperience = player.getLevelMaximumExperience(level - 1);
		int experience = player.getExperience() - previousExperience;
		int maximumExperience = player.getLevelMaximumExperience(level) - previousExperience;
		int barWidth = calculateBarWidth(experience, maximumExperience);

		// Set the new current value.
		setBarWidth(barWidth);
	}

	/**
	 * Returns the owner of this element.
	 * @return the owner.
	 */
	public Player getOwner()
	{
		return (Player) super.getOwner();
	}

	/**
	 * Actions to be taken when the player gains experience.
	 * @param e the event.
	 */
	public synchronized void onPlayerGainExperience(PlayerEvent e)
	{
		refresh();
	}
}
