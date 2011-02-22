package gridwhack.gui.item;

import gridwhack.entity.item.Item;
import gridwhack.entity.unit.player.Player;
import gridwhack.grid.GridLoot;
import gridwhack.gui.Gui;
import gridwhack.gui.GuiElement;

import java.awt.*;
import java.util.ArrayList;

/**
 * Loot box class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class LootBox extends GuiElement
{
	protected GridLoot loot;
	protected Player owner;
	protected int lineHeight;
	protected int selectedIndex;

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

		setFont( Gui.getInstance().getWindow().getFont() );
		setTextColor(Color.white);

		this.loot = loot;
		this.owner = owner;
		this.lineHeight = (int) Math.round(font.getSize() * 1.8);
		this.selectedIndex = 0;
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
		g.setFont(font);
		g.setColor(textColor);

		ArrayList<Item> items = loot.getItems();

		// Make sure that we have items.
		if( !items.isEmpty() )
		{
			// Loop through the items and add them to the loot box.
			for( int i=0, length=items.size(); i<length; i++ )
			{
				// Mark the selected index with a gray background.
				if( i==selectedIndex)
				{
					g.setColor(Color.darkGray);
					g.fillRect(getX(), getY()+(i*lineHeight), width, 20);
					g.setColor(textColor);
				}

				g.drawString(items.get(i).getName(), getX()+5, getY()+15+(i*lineHeight));
			}
		}
	}

	/**
	 * @return the line height.
	 */
	public int getLineHeight()
	{
		return lineHeight;
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
