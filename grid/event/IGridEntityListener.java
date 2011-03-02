package gridwhack.grid.event;

import gridwhack.event.IEventListener;

public interface IGridEntityListener extends IEventListener
{
	//public void onGridEntityAdd(GridEntityEvent e);

	public void onGridEntityMove(GridEntityEvent e);

	//public void onGridEntityRemove(GridEntityEvent e);
}
