package gridwhack.gui.character;

import java.awt.*;

import gridwhack.entity.character.Character;
import gridwhack.entity.character.NPCCharacter;
import gridwhack.entity.character.event.CharacterEvent;
import gridwhack.entity.character.event.ICharacterListener;
import gridwhack.entity.character.player.event.IPlayerListener;
import gridwhack.entity.character.player.event.PlayerEvent;

/**
 * Health bar class.
 * Allows for visualizing character health in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class HealthBar extends StatusBar implements ICharacterListener, IPlayerListener
{
	/**
	 * Creates the bar.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @param width the width of the bar.
	 * @param height the height of the bar.
	 * @param owner the character this bar belongs to.
	 */
	public HealthBar(int x, int y, int width, int height, Character owner)
	{
		super(x, y, width, height, false, owner);

		// Health bars should be red.
		setBarColor( new Color(205, 0, 0) );
	}
	
	/**
	 * Refreshes the bar.
	 */
	private void refresh()
	{
		int barWidth = calculateBarWidth(owner.getCurrentHealth(), owner.getMaximumHealth());
		
		// set the new current value.
		setBarWidth(barWidth);
	}
	
	/**
	 * Moves the bar.
	 */
	private void move()
	{
		// calculate the new position for the bar.
		int x = (int) Math.round(owner.getX()+1);
		int y = (int) Math.round(owner.getY()+1);
		
		// move the bar with the character.
		super.move(x, y);
	}

	/**
	 * Actions to be taken when the character dies.
	 * @param e the event.
	 */
	public synchronized void onCharacterDeath(CharacterEvent e) {}

	/**
	 * Actions to be taken when the character is spawned.
	 * @param e the event.
	 */
	public synchronized void onCharacterSpawn(CharacterEvent e) {}

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
	 * Actions to be taken when the character moves.
	 * @param e the event.
	 */
	public synchronized void onCharacterMove(CharacterEvent e)
	{		
		if( owner instanceof NPCCharacter)
		{
			move();
		}
	}

	/**
	 * Actions to be taken when the player gains experience.
	 * @param e the event.
	 */
	public void onPlayerGainExperience(PlayerEvent e) {}

	/**
	 * Actions to be taken when the player is gains a level.
	 * @param e the event.
	 */
	public void onPlayerGainLevel(PlayerEvent e)
	{
		setBarWidth(getWidth());
	}
}
