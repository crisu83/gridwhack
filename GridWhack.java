package gridwhack;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;

import gridwhack.core.Game;
import gridwhack.gameobject.character.Character.CharacterType;
import gridwhack.gameobject.character.CharacterFactory;
import gridwhack.gameobject.character.player.Player;
import gridwhack.gameobject.exception.InvalidGameObjectException;
import gridwhack.gameobject.grid.Grid;
import gridwhack.gameobject.map.Map;
import gridwhack.gameobject.unit.Unit;
import gridwhack.gameobject.map.MapFactory;
import gridwhack.gui.Gui;
import gridwhack.gui.GuiElement;
import gridwhack.gui.GuiPanel;
import gridwhack.gui.item.LootBox;
import gridwhack.gui.message.CombatLogBox;
import gridwhack.gui.message.MessageLogBox;
import gridwhack.gui.character.player.ExperienceDisplay;
import gridwhack.gui.character.HealthDisplay;
//import gridwhack.gameobject.Camera;
import gridwhack.gui.character.player.PlayerDetails;
import gridwhack.gameobject.map.Map.MapType;

/**
 * GridWhack game class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class GridWhack extends Game
{
	private static final int DEFAULT_FPS = 80;

	//public static final boolean DEBUG = true;
	
	private Player player;
	private Map map;
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
	public void init()
	{
		gui = Gui.getInstance();

		Window w = getGameWindow();
		w.setFont(new Font("Arial", Font.PLAIN, 12));
		w.setBackground(Color.black);
		w.setForeground(Color.white);
		gui.setWindow(w);

		createMap();
		createPlayer();
		//createCamera();

		// Register the key hanlder.
		registerKeyHandler();

		// initialize the user interface.
		initGui();

		/*
		try
		{
			sqlLiteTest();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		*/
	}

	/**
	 * Registers the key handler.
	 */
	public void registerKeyHandler()
	{
		if (player != null)
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
						// Looting keys.
						if( player.isLooting() )
						{
							LootBox lb = (LootBox) gui.getPanel(
									GuiPanel.GuiPanelType.WINDOW_PLAYER_LOOT).getChild(GuiElement.GuiElementType.PLAYER_LOOTBOX);
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
									player.move(Unit.Directions.LEFT);
									break;

								// Move player right.
								case KeyEvent.VK_RIGHT:
									player.move(Unit.Directions.RIGHT);
									break;

								// Move player up.
								case KeyEvent.VK_UP:
									player.move(Unit.Directions.UP);
									break;

								// Move player down.
								case KeyEvent.VK_DOWN:
									player.move(Unit.Directions.DOWN);
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
		try
		{
			map = MapFactory.getInstance().create(MapType.DUNGEON, 60, 30);
		}
		catch (InvalidGameObjectException e)
		{
			System.out.print(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Creates the player.
	 */
	protected void createPlayer()
	{
		Grid grid = map.getGrid();
		player = grid.getPlayer();
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
		createMessageLog();
		createCombatLog();
	}

	/**
	 * Creates the player panel.
	 */
	private void createPlayerPanel()
	{
		GuiPanel playerPanel = new GuiPanel(0, 0, 200, 70);
		playerPanel.addChild(GuiElement.GuiElementType.PLAYER_DETAILS, new PlayerDetails(10, 5, player));
		playerPanel.addChild(GuiElement.GuiElementType.PLAYER_HEALTHDISPLAY, new HealthDisplay(5, 15, player));
		playerPanel.addChild(GuiElement.GuiElementType.PLAYER_EXPERIENCEDISPLAY, new ExperienceDisplay(5, 25, player));
		gui.addPanel(GuiPanel.GuiPanelType.PANEL_PLAYER_INFO, playerPanel);
	}

	/**
	 * Creates the message log.
	 */
	private void createMessageLog()
	{
		Window w = getGameWindow();
		GuiPanel messageLog = new GuiPanel(0, w.getHeight()-100, 500, 100);
		messageLog.addChild(GuiElement.GuiElementType.GAME_MESSAGELOGBOX, new MessageLogBox(5, 5, 290, 90));
		gui.addPanel(GuiPanel.GuiPanelType.PANEL_MESSAGELOG, messageLog);
	}

	/**
	 * Creates the combat log.
	 */
	private void createCombatLog()
	{
		Window w = getGameWindow();
		GuiPanel combatLog = new GuiPanel(w.getWidth()-500, w.getHeight()-100, 500, 100);
		combatLog.addChild(GuiElement.GuiElementType.GAME_COMBATLOGBOX, new CombatLogBox(5, 5, 290, 90));
		gui.addPanel(GuiPanel.GuiPanelType.PANEL_COMBATLOG, combatLog);
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Updates the game logic.
	 */
	@Override
	public void updateLogic()
	{
		map.update(null);
		gui.update(null);
	}

	/**
	 * Draws a single frame of the game.
	 *
	 * @param g The graphics context.
	 */
	@Override
	public void drawFrame(Graphics2D g)
	{
		map.draw(g);
		gui.draw(g);
	}

	public void sqlLiteTest() throws Exception
	{
		Class.forName("org.sqlite.JDBC");

		Connection conn = DriverManager.getConnection("jdbc:sqlite:gridwhack/db/dungeon.sqlite");

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from Hostiles;");
		while (rs.next())
		{
			System.out.println("Name: " + rs.getString("name"));
			System.out.println("Minimum damage: " + rs.getInt("damageMin"));
			System.out.println("Maximum damage: " + rs.getInt("damageMax"));
			System.out.println("Health: " + rs.getInt("health"));
			System.out.println("Attack cooldown: " + rs.getInt("attackCooldown"));
			System.out.println("Movement cooldown: " + rs.getInt("movementCooldown"));
			System.out.println("View range: " + rs.getInt("viewRange"));
			System.out.println("Experience: " + rs.getInt("experience"));
			System.out.println("Image: " + rs.getString("image"));
		}

		conn.close();
	}

	// -----------
	// Main method
	// -----------

	/**
	 * Main method.
	 * @param args The application arguments.
	 */
	public static void main(String[] args)
	{
		int fps = DEFAULT_FPS;
		long period = (long) 1000.0/fps;
		
		System.out.println("fps: " + fps + "; period: " + period + " ms");
		
		new GridWhack(period*1000000L); // ms -> ns
	}
}