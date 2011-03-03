package gridwhack.gui.character;

import gridwhack.entity.character.Character;
import gridwhack.entity.character.event.CharacterEvent;
import gridwhack.entity.character.event.ICharacterHealthListener;
import gridwhack.entity.character.player.event.IPlayerLevelListener;
import gridwhack.entity.character.player.event.PlayerEvent;

/**
 * Health status text class file.
 * Allows for displaying character health values in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class HealthText extends StatusText implements ICharacterHealthListener, IPlayerLevelListener
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
	public HealthText(int x, int y, int width, int height, Character owner)
	{
		super(x, y, width, height, owner);
		
		this.maximum = owner.getMaximumHealth();
		this.current = maximum;
	}

	/**
	 * Refreshes the values.
	 */
	private void refresh()
	{
		Character owner = getOwner();
		maximum = owner.getMaximumHealth();
		current = owner.getCurrentHealth();
	}
	
	/**
	 * @return the health text.
	 */
	public String getText()
	{
		return current + " / " + maximum;
	}

	/**
	 * Actions to be taken when the character gains health.
	 * @param e the event.
	 */
	public synchronized void onCharacterHealthGain(CharacterEvent e)
	{
		refresh();
	}

	/**
	 * Actions to be taken when the character loses health.
	 * @param e the event.
	 */
	public synchronized void onCharacterHealthLoss(CharacterEvent e)
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
	}
}
