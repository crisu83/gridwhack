package gridwhack;

import java.awt.*;

import gridwhack.entity.unit.Player;
import gridwhack.grid.Grid;
import gridwhack.gui.Gui;
import gridwhack.gui.GuiPanel;
import gridwhack.gui.message.CombatLogBox;
import gridwhack.gui.unit.HealthDisplay;
import gridwhack.map.*;
import gridwhack.map.MapFactory.MapType;

public class Game extends CGameEngine implements IEventListener
{
	public static boolean DEBUG = true;
	
	private Player player;
	private GridMap map;
	private Camera camera;
	
	/**
	 * Main method.
	 * @param args the arguments.
	 */
	public static void main(String[] args)
	{
		new Game().run();
	}
	
	/**
	 * Initializes the key test.
	 */
	public void init()
	{
		// initialize the core.
		super.init();
		
		// set window properties.
		Window w = screen.getFullScreenWindow();
		w.setFocusTraversalKeysEnabled(false);
		w.addKeyListener(new KeyboardHandler(this));
		w.setFont(new Font("Arial", Font.PLAIN, 11));
		w.setBackground(Color.black);
		w.setForeground(Color.white);
	}
	
	/**
	 * Initializes the game.
	 */
	public void gameInit()
	{
		createMap();
		createPlayer();	
		//createCamera();
		
		// initialize the user interface.
		initGui();
	}
	
	/**
	 * Creates the map.
	 */
	protected void createMap()
	{
		map = MapFactory.factory(MapType.DUNGEON);
	}
	
	/**
	 * Creates the player.
	 */
	protected void createPlayer()
	{
		Grid grid = map.getGrid();
		
		player = new Player(grid);
		player.addListener(this);
		
		// add the player onto the map.
		map.addPlayer(player);
	}
	
	/**
	 * Creates the camera to follow the player.
	 */
	protected void createCamera()
	{
		Window w = screen.getFullScreenWindow();		
		Rectangle bounds = new Rectangle(0, 0, map.getWidth(), map.getHeight());
		camera = new Camera(w.getWidth(), w.getHeight(), bounds, player);
		player.addListener(camera);
	}
	
	/**
	 * Initializes the user interface.
	 */
	protected void initGui()
	{
		createPlayerPanel();
		createCombatLog();
	}
	
	/**
	 * Creates the player panel.
	 */
	private void createPlayerPanel()
	{
		GuiPanel playerPanel = new GuiPanel(5, 5, 180, 20, null);
		playerPanel.addElement(new HealthDisplay(5, 5, player));
		Gui.addPanel(playerPanel);
	}
	
	/**
	 * Creates the combat log.
	 */
	private void createCombatLog()
	{
		Window w = screen.getFullScreenWindow();
		GuiPanel combatLog = new GuiPanel(5, w.getHeight()-95, w.getWidth()-10, 100, null);
		combatLog.addElement(new CombatLogBox(5, 15, 290, 10));
		Gui.addPanel(combatLog);
	}
	
	/**
	 * @return the player.
	 */
	public Player getPlayer()
	{
		return player;
	}
	
	/**
	 * @param timePassed the time that has passed.
	 */
	public void gameUpdate(long timePassed)
	{		
		// update the map.
		map.update(timePassed);
		
		// update the gui.
		Gui.update(timePassed);
	}

	/**
	 * Renders the game.
	 * @param g the graphics object.
	 */
	public void gameRender(Graphics2D g)
	{			
		Window w = screen.getFullScreenWindow();
		
		/*
		// get the camera offset.
		int cx = (int)Math.round(camera.getX());
		int cy = (int)Math.round(camera.getY());

		// offset the rendering according to the camera.
		g.translate(-cx, -cy);
		*/
		
		// set a black background.
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, w.getWidth(), w.getHeight());
		
		// render the map.
		map.render(g);
		
		// reverse the camera offset.
		//g.translate(cx, cy);
		
		// render the gui last.
		Gui.render(g);
	}
	
	/**
	 * Renders the application.
	 * @param g the graphics object.
	 */
	public void paint(Graphics g)
	{
		if( g instanceof Graphics2D )
		{
			Graphics2D g2d = (Graphics2D)g;
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
	}
	
	/**
	 * Handles incoming events.
	 * @param e the event.
	 */
	public void handleEvent(CEvent e) 
	{
		String type = e.getType();
		Object source = e.getSource();		
		
		// player event handling.
		if( source instanceof Player )
		{
			// actions to be taken on player death.
			if( type=="death" )
			{
				System.out.println("Player has died.");
				stopGame();
			}
		}
	}
}