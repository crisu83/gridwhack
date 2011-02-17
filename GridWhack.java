package gridwhack;

import java.awt.*;

import gridwhack.entity.unit.Player;
import gridwhack.grid.Grid;
import gridwhack.gui.Gui;
import gridwhack.gui.GuiPanel;
import gridwhack.gui.message.CombatLogBox;
import gridwhack.gui.unit.HealthDisplay;
//import gridwhack.map.Camera;
import gridwhack.map.GridMap;
import gridwhack.map.MapFactory;
import gridwhack.map.MapFactory.MapType;

/**
 * GridWhack class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class GridWhack extends CGameEngine
{
	private static final int DEFAULT_FPS = 80;
	
	public static final boolean DEBUG = true;
	
	private Player player;
	private GridMap map;
	//private Camera camera;

	/**
	 * Creates the game.
	 * @param period the update period.
	 */
	public GridWhack(long period)
	{
		super("GridWhack", period);
	}
	
	/**
	 * Initializes the game.
	 */
	public void gameInit()
	{
		Window w = screen.getFullScreenWindow();
		w.addKeyListener(new KeyboardHandler(this));
		w.setFont(new Font("Arial", Font.PLAIN, 11));
		w.setBackground(Color.black);
		w.setForeground(Color.white);
		
		createMap();
		createPlayer();	
		//createCamera();
		
		// initialize the user interface.
		initGui();
	}

	public void gameStop()
	{
		System.out.println("Player has died!");

		super.gameStop();
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
		
		map.setPlayer(player);

		// TODO: Think of a better way to call this the first time.
		player.updateFov();
	}
	
	/**
	 * Creates the camera to follow the player.
	 */
	/*
	protected void createCamera()
	{
		Window w = screen.getFullScreenWindow();		
		Rectangle bounds = new Rectangle(0, 0, map.getWidth(), map.getHeight());
		camera = new Camera(0, 0, w.getWidth(), w.getHeight()-100, bounds, player, true);
		player.addListener(camera);
	}
	*/
	
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
		GuiPanel combatLog = new GuiPanel(0, w.getHeight()-100, w.getWidth(), 100, null);
		Font font = new Font("Arial", Font.PLAIN, 11);
		combatLog.addElement(new CombatLogBox(5, 15, 290, 10, font, Color.white));
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
		//camera.update(timePassed);
		
		// update the map.
		map.update(timePassed);
		
		// update the gui.
		Gui.update(timePassed);

		// Make sure that the player is not dead.
		if( player.getDead() )
		{
			gameStop();
		}
	}

	/**
	 * Renders the game.
	 * @param g the graphics object.
	 */
	public void gameRender(Graphics2D g)
	{			
		Window w = screen.getFullScreenWindow();
		
		// get the camera offset.
		//int cx = (int) Math.round( Math.round(camera.getX()) );
		//int cy = (int) Math.round( Math.round(camera.getY()) );
		
		//g.translate(-cx, -cy);
		
		// set a black background.
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, w.getWidth(), w.getHeight());
		
		// render the map.
		map.render(g);
		
		//g.translate(cx, cy);
		
		// render the gui last.
		Gui.render(g);
	}
	
	/**
	 * Renders the application.
	 * @param g the graphics object.
	 */
	/*
	public void paint(Graphics g)
	{
		if( g instanceof Graphics2D )
		{
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING, 
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
	}
	*/
	
	/**
	 * Main method.
	 * @param args the application arguments.
	 */
	public static void main(String[] args)
	{
		int fps = DEFAULT_FPS;
		
		if( args.length!=0 )
		{
			fps = Integer.parseInt(args[0]);
		}
		
		long period = (long) 1000.0/fps;
		
		System.out.println("fps: " + fps + "; period: " + period + " ms");
		
		new GridWhack(period*1000000L); // ms -> ns
	}
}