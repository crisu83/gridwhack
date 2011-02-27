package gridwhack.gui.item;

import gridwhack.entity.item.Item;
import gridwhack.entity.character.player.Player;
import gridwhack.grid.GridLoot;
import gridwhack.gui.GuiElement;

import java.awt.*;
import java.util.ArrayList;

/**
 * Loot box class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class LootBox extends GuiElement
{
	private GridLoot loot;
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
	public LootBox(int x, int y, GridLoot loot, Player owner)
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
		ArrayList<Item> items = loot.getItems();

		// Make sure that we have items.
		if( !items.isEmpty() )
		{
			Item item = items.get(selectedIndex);

			item.loot(owner);
			loot.removeItem(item);

			// We need to remove the loot and stop looting
			// when the last item is looted.
			if( items.isEmpty() )
			{
				loot.markRemoved();
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
	 * Renders the loot box.
	 * @param g the graphics context.
	 */
	public void render(Graphics2D g)
	{
		Color textColor = getTextColor();
		int lineHeight = getLineHeight();

		g.setFont(getFont());
		g.setColor(textColor);

		ArrayList<Item> items = loot.getItems();

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

				g.drawString(items.get(i).getName(), getX() + 5, getY() + 15 + (i * lineHeight));
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
