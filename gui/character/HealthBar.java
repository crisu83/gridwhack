package gridwhack.gui.character;

import java.awt.*;

import gridwhack.gameobject.character.Character;
import gridwhack.gameobject.character.NPCCharacter;
import gridwhack.gameobject.character.event.CharacterEvent;
import gridwhack.gameobject.character.event.ICharacterHealthListener;
import gridwhack.gameobject.character.event.ICharacterMoveListener;
import gridwhack.gameobject.character.player.event.IPlayerLevelListener;
import gridwhack.gameobject.character.player.event.PlayerEvent;
import gridwhack.util.Vector2;

/**
 * Health bar class.
 * Allows for visualizing character health in the gui.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class HealthBar extends StatusBar implements ICharacterHealthListener, ICharacterMoveListener, IPlayerLevelListener
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
		Character owner = getOwner();
		int barWidth = calculateBarWidth(owner.getCurrentHealth(), owner.getMaximumHealth());
		
		// set the new current value.
		setBarWidth(barWidth);
	}
	
	/**
	 * Moves the bar.
	 */
	private void move()
	{
		Character owner = getOwner();

		Vector2 position = owner.getPosition();

		// calculate the new position for the bar.
		int x = (int) position.x + 1;
		int y = (int) position.y + 1;
		
		// move the bar with the character.
		super.move(x, y);
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
	 * Actions to be taken when the character moves.
	 * @param e the event.
	 */
	public synchronized void onCharacterMove(CharacterEvent e)
	{
		Character owner = getOwner();

		if( owner instanceof NPCCharacter )
		{
			move();
		}
	}

	/**
	 * Actions to be taken when the player is gains a level.
	 * @param e the event.
	 */
	public void onPlayerGainLevel(PlayerEvent e)
	{
		setBarWidth( getWidth() );
	}
}
