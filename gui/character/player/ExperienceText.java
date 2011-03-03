package gridwhack.gui.character.player;

import gridwhack.entity.character.Character;
import gridwhack.entity.character.player.Player;
import gridwhack.entity.character.player.event.IPlayerExperienceListener;
import gridwhack.entity.character.player.event.IPlayerLevelListener;
import gridwhack.entity.character.player.event.PlayerEvent;
import gridwhack.gui.character.StatusText;

/**
 * Experience status text class.
 * Allows for displaying player experience values in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class ExperienceText extends StatusText implements IPlayerExperienceListener, IPlayerLevelListener
{
	private int current;
	private int maximum;

	/**
	 * Creates the status text.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param width the width.
	 * @param height the height.
	 * @param owner the character this text belongs to.
	 */
	public ExperienceText(int x, int y, int width, int height, Character owner)
	{
		super(x, y, width, height, owner);

		Player player = getOwner();
		current = 0;
		maximum = player.getLevelMaximumExperience( player.getLevel() );
	}

	/**
	 * Refreshes the values.
	 */
	private void refresh()
	{
		current = getOwner().getExperience();
	}
	
	/**
	 * @return the health text.
	 */
	public String getText()
	{
		return current + " / " + maximum;
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
	public void onPlayerGainExperience(PlayerEvent e)
	{
		refresh();
	}

	/**
	 * Actions to be taken when the player is gains a level.
	 * @param e the event.
	 */
	public void onPlayerGainLevel(PlayerEvent e)
	{
		Player player = getOwner();
		maximum = player.getLevelMaximumExperience( player.getLevel() );

		refresh();
	}
}
