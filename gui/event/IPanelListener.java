package gridwhack.gui.event;

import gridwhack.event.IEventListener;

/**
 * Panel listener interface file.
 * All panel listeners must implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface IPanelListener extends IEventListener
{
	/**
	 * Actions to be taken when a panel is removed.
	 * @param e the event.
	 */
	public void onPanelRemove(PanelEvent e);
}
