package gridwhack.gui.unit.player;

import gridwhack.entity.unit.player.Player;
import gridwhack.entity.unit.player.event.IPlayerListener;
import gridwhack.entity.unit.player.event.PlayerEvent;
import gridwhack.grid.GridUnit;
import gridwhack.gui.unit.StatusText;

import java.awt.*;

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
	 * Constructs the health text.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param font the font to use.
	 * @param owner the unit the health belongs to.
	 */
	public ExperienceText(int x, int y, Font font, GridUnit owner)
	{
		super(x, y, font, owner);

		Player player = (Player) owner;
		this.maximum = player.getLevelMaximumExperience( player.getLevel() );
		this.current = 0;
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
