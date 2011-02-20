package gridwhack;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import gridwhack.entity.unit.Player;
import gridwhack.grid.Grid;
import gridwhack.grid.GridUnit;
import gridwhack.gui.Gui;
import gridwhack.gui.GuiPanel;
import gridwhack.gui.item.LootBox;
import gridwhack.gui.message.CombatLogBox;
import gridwhack.gui.message.MessageLogBox;
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
	private Gui gui;
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
		gui = Gui.getInstance();
		
		Window w = screen.getFullScreenWindow();
		w.setFont(new Font("Arial", Font.PLAIN, 11));
		w.setBackground(Color.black);
		w.setForeground(Color.white);
		gui.setWindow(w);

		// Register the key hanlder.
		registerKeyHandler();

		createMap();
		createPlayer();	
		//createCamera();
		
		// initialize the user interface.
		initGui();
	}

	/**
	 * Registers the key handler.
	 */
	public void registerKeyHandler()
	{
		// Add a key listener to listen for game related key presses.
		addKeyListener(
			new KeyAdapter()
			{
				/**
				 * Actions to be taken when a key is pressed.
				 * @param e the key event.
				 */
				public void keyPressed(KeyEvent e)
				{
					if( player.isLooting() )
					{
						LootBox lb = (LootBox) gui.getPanel(Gui.PLAYER_LOOTWINDOW).getElement(Gui.PLAYER_LOOTBOX);
						int index = lb.getSelectedIndex();

						switch( e.getKeyCode() )
						{
							// Move selection up.
							case KeyEvent.VK_UP:
								lb.setSelectedIndex(index - 1);
								break;

							// Move selection down.
							case KeyEvent.VK_DOWN:
								lb.setSelectedIndex(index + 1);
								break;

							case KeyEvent.VK_ENTER:
								player.lootSelectedItem();
								break;

							// Close loot window.
							case KeyEvent.VK_ESCAPE:
								player.stopLooting();
								break;

							default:
						}
					}
					// In-game keys.
					else
					{
						switch( e.getKeyCode() )
						{
							// Move player left.
							case KeyEvent.VK_LEFT:
								player.move(GridUnit.Directions.LEFT);
								break;

							// Move player right.
							case KeyEvent.VK_RIGHT:
								player.move(GridUnit.Directions.RIGHT);
								break;

							// Move player up.
							case KeyEvent.VK_UP:
								player.move(GridUnit.Directions.UP);
								break;

							// Move player down.
							case KeyEvent.VK_DOWN:
								player.move(GridUnit.Directions.DOWN);
								break;

							// Player loot.
							case KeyEvent.VK_PERIOD:
								player.openLootWindow();
								break;

							default:
								//System.out.println("Key pressed: " + e.getKeyCode());
						}
					}

					e.consume();
				}
			}
		);
	}

	/**
	 * Stops the game.
	 */
	public void gameStop()
	{
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
		player = new Player(grid, this);
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
		createMessageLog();
	}

	/**
	 * Creates the player panel.
	 */
	private void createPlayerPanel()
	{
		GuiPanel playerPanel = new GuiPanel(5, 5, 180, 20, null);
		playerPanel.addElement(Gui.PLAYER_HEALTHDISPLAY, new HealthDisplay(5, 5, player));
		gui.addPanel(Gui.PLAYER_PANEL, playerPanel);
	}

	/**
	 * Creates the combat log.
	 */
	private void createCombatLog()
	{
		Window w = screen.getFullScreenWindow();
		GuiPanel combatLog = new GuiPanel(0, w.getHeight()-100, 300, 100, null);
		combatLog.addElement(Gui.GAME_COMBATLOGBOX, new CombatLogBox(5, 5, 290, 90));
		gui.addPanel(Gui.GAME_COMBATLOG, combatLog);
	}

	/**
	 * Creates the message log.
	 */
	private void createMessageLog()
	{
		Window w = screen.getFullScreenWindow();
		GuiPanel messageLog = new GuiPanel(300, w.getHeight()-100, 300, 100, null);
		messageLog.addElement(Gui.GAME_MESSAGELOGBOX, new MessageLogBox(5, 5, 290, 90));
		gui.addPanel(Gui.GAME_MESSAGELOG, messageLog);
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
		gui.update(timePassed);

		// Make sure that the player is not dead.
		if( player.getDead() )
		{
			System.out.println("Player has died!");
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
		gui.render(g);
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