package sjgs.world_generation;

import static sjgs.graphics.Colors.voidColor;
import static sjgs.utils.Utils.loadImage;
import static sjgs.utils.Utils.print;
import static sjgs.utils.Utils.unpackBlue;
import static sjgs.utils.Utils.unpackGreen;
import static sjgs.utils.Utils.unpackRed;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class TileMap {

	private final HashMap<Color, Action> table;
	private final BufferedImage map;
	private final int TILE_SIZE;

	public TileMap(final BufferedImage image, final int TILE_SIZE) {
		map = image;
		this.TILE_SIZE = TILE_SIZE;
		table = new HashMap<Color, Action>();
	}
	/** @constructor: can also take a param as a string to load the image for you! */
	public TileMap(final String image_path, final int TILE_SIZE) {
		map = loadImage(image_path);
		this.TILE_SIZE = TILE_SIZE;
		table = new HashMap<Color, Action>();
	}

	public void setColorAction(final Color color, final Action action) { table.put(color, action); }

	public void generateWorld() {
		try {

			for (int i = 0; i < map.getHeight(); i++)
				for (int j = 0; j < map.getWidth(); j++) {
					final int pixel = map.getRGB(i, j);

					final Color color = new Color(unpackRed(pixel), unpackGreen(pixel), unpackBlue(pixel));

					//  grab the Action from the HashMap, make the i, j be the x,y of the Action
					if(color != voidColor) table.get(color).run(i, j);
				}

		} catch (final ArrayIndexOutOfBoundsException e) {
			print("The image must be square to be used as a TileMap." + "\n" +
					"Hint: Use the void color to fill the rest of the square.");
		}
	}

}