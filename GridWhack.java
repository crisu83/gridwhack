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

public class GridWhack extends CGameEngine
{
	private static int DEFAULT_FPS = 40;
	
	public static boolean DEBUG = true;
	
	private Player player;
	private GridMap map;
	private Camera camera;
	
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
		createCamera();
		
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
		//player.addListener(this);
		
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
		camera = new Camera(0, 0, w.getWidth(), w.getHeight()-100, bounds, player, true);
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
		Window w = screen.getFullScreenWindow();
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
		//camera.update(timePassed);
		
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
		
		// get the camera offset.
		int cx = (int) Math.round( Math.round(camera.getX()) );
		int cy = (int) Math.round( Math.round(camera.getY()) );
		
		g.translate(-cx, -cy);
		
		// set a black background.
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, w.getWidth(), w.getHeight());
		
		// render the map.
		map.render(g);
		
		g.translate(cx, cy);
		
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
			Graphics2D g2d = (Graphics2D)g;
			g2d.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING, 
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
	}
	*/

	/*
	@Override
	public void onUnitDeath(UnitEvent e) 
	{
		Unit source = (Unit) e.getSource();		
		
		if( source instanceof Player )
		{
			System.out.println("Player has died.");
			stopGame();
		}
	}

	@Override
	public void onUnitSpawn(UnitEvent e) {}

	@Override
	public void onUnitHealthGain(UnitEvent e) {}

	@Override
	public void onUnitHealthLoss(UnitEvent e) {}

	@Override
	public void onUnitMove(UnitEvent e) {}
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