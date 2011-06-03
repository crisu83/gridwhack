package gridwhack.gui.item;

import gridwhack.base.BaseObject;
import gridwhack.gameobject.character.player.Player;
import gridwhack.gameobject.item.Item;
import gridwhack.gameobject.loot.Loot;
import gridwhack.gui.GuiElement;
import gridwhack.util.SortedArrayList;

import java.awt.*;

/**
 * Loot box class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class LootBox extends GuiElement
{
	private Loot loot;
	private Player owner;
	private Color selectionColor;
	private int selectedIndex;

	/**
	 * Creates the loot box.
	 * @param x the x-coordinates.
	 * @param y the y-coordinates.
	 * @param loot the loot.
	 * @param owner the owner.
	 */
	public LootBox(int x, int y, Loot loot, Player owner)
	{
		super(x, y, 110, 90);

		this.loot = loot;
		this.owner = owner;
		this.selectionColor = Color.darkGray;
		this.selectedIndex = 0;

		setLineHeight( (int) Math.round(getFontSize() * 1.8) );
	}

	/**
	 * Loots the selected item.
	 */
	public void lootSelectedItem()
	{
		SortedArrayList<BaseObject> items = loot.getItems();

		// Make sure that we have items.
		if( !items.isEmpty() )
		{
			Item item = (Item) items.get(selectedIndex);

			item.loot(owner);
			loot.removeItem(item);
			loot.update(null); // TODO: Remove this hack

			// We need to remove the loot and stop looting
			// when the last item is looted.
			if( items.isEmpty() )
			{
				loot.setRemoved(true);
				owner.stopLooting();
			}
		}
	}

	/**
	 * Updates this element.
	 * @param timePassed the time that has passed.
	 */
	public void update(long timePassed) {}

	/**
	 * Draws this object.
	 * @param g The graphics context.
	 */
	public void draw(Graphics2D g)
	{
		Color textColor = getTextColor();
		int lineHeight = getLineHeight();

		g.setFont(getFont());
		g.setColor(textColor);

		SortedArrayList<BaseObject> items = loot.getItems();

		// Make sure that we have items.
		if( !items.isEmpty() )
		{
			// Loop through the items and add them to the loot box.
			for( int i=0, length=items.size(); i<length; i++ )
			{
				// Mark the selected index with a gray background.
				if( i==selectedIndex )
				{
					g.setColor(selectionColor);
					g.fillRect(getX(), getY() + (i * lineHeight), getWidth(), 20);
					g.setColor(textColor);
				}

				Item item = (Item) items.get(i);

				g.drawString(item.getName(), getX() + 5, getY() + 15 + (i * lineHeight));
			}
		}
	}

	/**
	 * Sets the selected index.
	 * @param index the index.
	 */
	public void setSelectedIndex(int index)
	{
		int itemCount = loot.getItemCount();

		if( index<0 )
		{
			index = 0;
		}
		else if( index>=itemCount )
		{
			index = itemCount - 1;
		}

		this.selectedIndex = index;
	}

	/**
	 * Returns the selected index.
	 * @return the index.
	 */
	public int getSelectedIndex()
	{
		return selectedIndex;
	}
}
