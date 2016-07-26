package sjgs.core;

import static sjgs.graphics.Colors.black;
import static sjgs.graphics.Colors.pastelRed;
import static sjgs.graphics.Colors.white;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.LinkedList;

public abstract class DeveloperConsole extends KeyAdapter {

	// Remember to add this to your engine.frame!

	private static StringBuilder sb;
	private static LinkedList<String> history;
	private static int index;
	public static boolean CONSOLE_OPEN;

	private static Font ponderosa_20;
	private static Font gregorian48Bold;

	public DeveloperConsole(final Engine engine) {
		sb = new StringBuilder();
		history = new LinkedList<String>();

		try {   InputStream is1 = DeveloperConsole.class.getResourceAsStream("/gregorian.ttf");
		InputStream is2 = DeveloperConsole.class.getResourceAsStream("/ponderosa.ttf");
		ponderosa_20 = Font.createFont(Font.TRUETYPE_FONT, is2).deriveFont(Font.BOLD, 10.0f);
		gregorian48Bold = Font.createFont(Font.TRUETYPE_FONT, is1).deriveFont(Font.BOLD, 48f);
		is1.close(); is1 = null; is2.close(); is2 = null;
		final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(gregorian48Bold);
		ge.registerFont(ponderosa_20);
		} catch (final Exception e) { e.printStackTrace(); }
		engine.frame.addKeyListener(this);
	}

	protected abstract void commands(String action, String item, String value);

	private synchronized void execute(final String command) {
		if(command.length() <= 0) { exit(); return; }

		String action, item, value;

		// GET THE ACTION, IF THE ACTION IS A ONE-WORD PHRASE, ACTION = THE WHOLE COMMAND
		try { action = command.substring(0, command.indexOf(" ")); } catch (final StringIndexOutOfBoundsException e) { action = command.toString(); }

		// GET THE ITEM, IF THERE IS NO AMOUNT, ITEM = JUST THE ITEM, IF THERE IS NO ITEM, ITEM = ""
		try { final String tempItem = command.substring(command.indexOf(" ") + 1); item = tempItem.substring(0, tempItem.indexOf(" ")); } catch (final StringIndexOutOfBoundsException e) {
			try { item = command.substring(command.indexOf(" ") + 1); } catch (final StringIndexOutOfBoundsException ee) { item = ""; }
		}

		//  GET THE VALUE ----
		try { final String temp = command.substring(command.indexOf(" ") + 1)  ;
		value = temp.substring(temp.indexOf(" ") + 1);
		} catch (final StringIndexOutOfBoundsException ee) { value = ""; }

		item.replace(" ", ""); action.replace(" ", ""); value.replace(" ", "");
		//------------------------------------------------//
		baseCommands(action, item, value);
		commands(action, item, value);
		//------------------------------------------------//
		exit();
	}

	private static void baseCommands(final String action, @SuppressWarnings("unused") final String item, @SuppressWarnings("unused") final String value) {
		switch(action) {
		case "quit": exit(); break;
		case "exit": exit(); break;
		}
	}

	@Override
	public void keyPressed(final KeyEvent e) {

		if(e.getKeyChar() == '~') { if(!CONSOLE_OPEN) open();  else exit(); return; }
		else if(!CONSOLE_OPEN) return;
		else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) { exit(); return; }


		else if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(history.size() <= 0) return;
			final String temp = sb.toString();
			sb = new StringBuilder();
			try { sb.append(history.get(index--)); } catch (final Exception ex) {
				index++;
				sb.append(temp);
			}
		}

		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(history.size() <= 0) return;
			final String temp = sb.toString();
			sb = new StringBuilder();
			try { sb.append(history.get(++index)); } catch (final Exception ex) {
				index--;
				sb.append(temp);
			}
		}

		else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			history.add(sb.toString());
			execute(sb.toString());
			sb = new StringBuilder();
			return;
		}

		else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) { if(sb.length() > 0) sb.deleteCharAt(sb.length()-1); return; }

		else if(sb.length() < 67) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_BACK_SPACE: return;
			case KeyEvent.VK_TAB: return;
			case KeyEvent.VK_SHIFT: return;
			case KeyEvent.VK_UP: return;
			case KeyEvent.VK_DOWN: return;
			case KeyEvent.VK_LEFT: return;
			case KeyEvent.VK_RIGHT: return;
			case KeyEvent.VK_CONTROL: return;
			case KeyEvent.VK_WINDOWS: return;
			case KeyEvent.VK_UNDEFINED: return;
			case KeyEvent.VK_NUM_LOCK: return;
			default: sb.append(e.getKeyChar());
			}
		}
	}

	public static void render(final Graphics g, final int width, final int height) {
		if(!CONSOLE_OPEN) return;

		g.setColor(black);
		g.fillRect(width/2 - width/4, height/2 - height/4, width/2, height/20);

		g.setColor(white);
		g.setFont(ponderosa_20);

		g.drawString(sb.toString(), width/2 - width/4 + 10, height/2 - height/4 + 20);

		g.setColor(pastelRed);
		g.setFont(gregorian48Bold);
		g.drawString("sjgs console:", width/2 - width/4 + 2, height/2 - height/4 - 10);
	}

	private static synchronized void open() {
		sb = new StringBuilder();
		CONSOLE_OPEN = true;
	}

	private static synchronized void exit() {
		index = history.size() - 1;
		CONSOLE_OPEN = false;
	}
}
