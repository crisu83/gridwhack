package gridwhack.gui.unit.player;

import gridwhack.entity.character.Character;
import gridwhack.entity.character.player.Player;
import gridwhack.entity.character.player.event.IPlayerListener;
import gridwhack.entity.character.player.event.PlayerEvent;
import gridwhack.gui.unit.StatusText;

/**
 * Experience status text class.
 * Allows for displaying player experience values in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class ExperienceText extends StatusText implements IPlayerListener
{
	protected int current;
	protected int maximum;

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

		Player player = (Player) owner;
		this.current = 0;
		this.maximum = player.getLevelMaximumExperience( player.getLevel() );
	}

	/**
	 * Refreshes the values.
	 */
	private void refresh()
	{
		Player player = (Player) owner;
		current = player.getLevelCurrentExperience( player.getLevel() );
	}
	
	/**
	 * @return the health text.
	 */
	public String getText()
	{
		return current + " / " + maximum;
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
		refresh();

		Player player = (Player) owner;
		this.maximum = player.getLevelMaximumExperience( player.getLevel() );
	}
}
