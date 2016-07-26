package sjgs.utils;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Random;
import javax.swing.JOptionPane;
import sjgs.utils.data_structures.Stack;
import sjgs.utils.data_structures.shapes.Circle;
import sjgs.utils.data_structures.shapes.Line;
import sjgs.utils.data_structures.shapes.Rectangle;
import sjgs.utils.data_structures.vectors.Point2f;
import sjgs.utils.data_structures.vectors.SimplePoint;

/** @author: Mitch Weaver 2016 */

public final class Utils {

	/********************** @COMMONLY_USED_VARIABLES ********************************/
	public static final Random rand = new Random(); // No need to create more randoms for everything, can just always use this one
	public static final String OS = "Running on: " + System.getProperty("os.name");
	public static final float sqrtOf2 = sqrt(2); // used for a lot of things, saved as not to need to calc it
	public static final double second = 1_000_000_000.0d; // 1 second in nanoseconds (1 billion)
	public static final float PI = (float)Math.PI; // in float form for convenience
	public static final float E = (float)Math.E; // in float form for convenience
	public static final int _2147483647 = Integer.MAX_VALUE;
	public static final float ulp = Math.ulp(0.0f); // smallest possible floating point number
	public static final Color voidColor = __ImageManipulation.voidColor;
	public static final String userHomeDir = __SerializationUtils.userHomeDir;
	public static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
	public static final int SCREEN_WIDTH = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
	public static final int SCREEN_HEIGHT = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
	/*******************************************************************************/

	/***************************** @DEGREES_AND_RADIANS *****************************/
	public static final float radian = PI / 180f;
	public static final float _45toRadians = toRadians(45), _90toRadians = _45toRadians*2, _180toRadians = _90toRadians*2;
	public static final float  toDegrees(final float theta)    { return theta * 180 / PI; }
	public static final double toDegrees(final double theta)   { return theta * 180 / PI; }
	public static final float  toRadians(final float degrees)  { return degrees * radian; }
	public static final double toRadians(final double degrees) { return degrees * radian; }
	public static final float  sin(final float theta)  { return (float)Math.sin(theta); }
	public static final float  cos(final float theta)  { return (float)Math.cos(theta); }
	public static final float  tan(final float theta)  { return (float)Math.tan(theta); }
	public static final float  atan(final float theta) { return (float)Math.atan(theta); }
	public static final float  atan2(final float y, final float x) { return (float)Math.atan2(y, x); } // NOTE: these are in reverse order, as is standard
	public static final float  getTheta(final float x, final float y) { return atan2(y, x); } // for those that can't remember its atan2, (lol)
	/******************************************************************************/

	/***************************** @DECIMAL_FORMATS ********************************/
	public static final DecimalFormat dff = new DecimalFormat("0.##");
	public static final DecimalFormat df = new DecimalFormat("0.#");
	/******************************************************************************/

	/******************************* @CLAMPS ************************************/
	public static final int    clamp(int input, final int floor, final int ceiling){ if(input < floor) input = floor; else if(input > ceiling) input = ceiling; return input; }
	public static final float  clamp(float input, final float floor, final float ceiling){ if(input < floor) input = floor; else if(input > ceiling) input = ceiling; return input; }
	public static final double clamp(double input, final double floor, final double ceiling){ if(input < floor) input = floor; else if(input > ceiling) input = ceiling; return input; }
	public static final int    cinch(final int input, final int ceiling) { return input < ceiling ? input : ceiling; }
	public static final float  cinch(final float input, final float ceiling) { return input < ceiling ? input : ceiling; }
	public static final double cinch(final double input, final double ceiling) { return input < ceiling ? input : ceiling; }
	public static final int    restrict(final int input, final int floor) { return input > floor ? input : floor; }
	public static final float  restrict(final float input, final float floor) { return input > floor ? input : floor; }
	public static final double restrict(final double input, final double floor) { return input > floor ? input : floor; }
	/** @method converge: Steps the input towards the target. If the input as excessed the target, clamp. */
	public static final int    converge(final int input, final int target, final int step) { return isNegative(target) ? restrict(input - step, target) : cinch(input + step, target); }
	public static final float  converge(final float input, final float target, final float step) { return isNegative(target) ? restrict(input - step, target) : cinch(input + step, target); }
	public static final double converge(final double input, final double target, final double step) { return isNegative(target) ? restrict(input - step, target) : cinch(input + step, target); }
	/**************************************************************************/

	/************************* @POWERS *****************************************/
	public static final int    square(final int foo)    { return foo*foo;     }
	public static final float  square(final float foo)  { return foo*foo;     }
	public static final double square(final double foo) { return foo*foo;     }
	public static final int    cube(final int foo)      { return foo*foo*foo; }
	public static final float  cube(final float foo)    { return foo*foo*foo; }
	public static final double cube(final double foo)   { return foo*foo*foo; }
	public static final int    pow(int foo, final int bar)    { for(int i = 0; i < bar; i++) foo*=foo; return foo; }
	public static final float  pow(float foo, final int bar)  { for(int i = 0; i < bar; i++) foo*=foo; return foo; }
	public static final double pow(double foo, final int bar) { for(int i = 0; i < bar; i++) foo*=foo; return foo; }
	/**************************************************************************/

	/********************** @TEXT_FILE_MANIPULATION *****************************/
	public static final int getNumLinesFromTextFile(final String path) { return __TextFileManipulation.getNumLinesFromTextFile(path); }
	public static final String[] makeStringArrayFromLinesOfTextFile(final String path) { return __TextFileManipulation.makeStringArrayFromLinesOfTextFile(path); }
	public static final StringBuilder[] makeStrinBuilderArrayFromLinesOfTextFile(final String path) { return __TextFileManipulation.makeStringBuilderArrayFromLinesOfTextFile(path); }
	public static final String readTextFileAsString(final String filepath){ return __TextFileManipulation.readTextFileAsString(filepath); }
	public static final StringBuilder readTextFileAsStringBuilder(final String path){ return new StringBuilder(__TextFileManipulation.readTextFileAsString(path)); }
	public static final char[] readTextFileAsCharArray(final String path) { return __TextFileManipulation.readTextFileAsString(path).toCharArray(); }
	/****************************************************************************/

	/********************** @IMAGE_MANIPULATION *********************************/
	/** @method removeVoidColor ~ default no color arg is (255, 0, 255) for pink void color */
	public static final BufferedImage reverseImage(final BufferedImage image) { return __ImageManipulation.reverseImage(image); }
	public static final BufferedImage invertImage(final BufferedImage image) { return __ImageManipulation.invertImage(image); }
	public static final BufferedImage rotateImageRight(final BufferedImage image) { return __ImageManipulation.rotateImageRight(image); }
	public static final BufferedImage rotateImageLeft(final BufferedImage image) { return __ImageManipulation.rotateImageLeft(image); }
	public static final BufferedImage imageToBufferedImage(final Image image) { return __ImageManipulation.imageToBufferedImage(image); }
	public static final BufferedImage loadImage(final String path) { return __ImageManipulation.loadImage(path); }
	/** @method grabSprite: Note col and rol start @ 0, this is for ease of use when using loops. */
	public static final BufferedImage grabSprite(final BufferedImage image, final int col, final int row, final int width, final int height){ return __ImageManipulation.grabSprite(image, col, row, width, height); }
	public static final BufferedImage grabSprite(final Image image, final int col, final int row, final int width, final int height){ return __ImageManipulation.grabSprite(imageToBufferedImage(image), col, row, width, height); }
	public static final BufferedImage removeVoidColor(final BufferedImage image, final Color voidColor) { return __ImageManipulation.removeVoidColor(image, voidColor); }
	public static final BufferedImage removeVoidColor(final BufferedImage image) { return removeVoidColor(image, voidColor); }
	/** @method optimizeImage: Note all the above methods call this by default. */
	public static final BufferedImage optimizeImage(final BufferedImage image) { return __ImageManipulation.optimizeImage(image); }
	/**************************************************************************************/

	/*************************** @MISC_GETTER_METHODS ***********************************/
	public static final boolean isEven(final int foo)  { return foo % 2 == 0; }   public static final boolean isEven(final long foo) { return foo % 2 == 0; }
	public static final boolean isEven(final short foo){ return foo % 2 == 0; }   public static final boolean isEven(final byte foo) { return foo % 2 == 0; }
	public static final boolean isOdd(final int foo)   { return foo % 2 != 0; }   public static final boolean isOdd(final long foo)  { return foo % 2 != 0; }
	public static final boolean isOdd(final short foo) { return foo % 2 != 0; }   public static final boolean isOdd(final byte foo)  { return foo % 2 != 0; }
	public static final boolean isNegative(final int foo) { return foo < 0; }     public static final boolean isPositive(final int foo) { return foo >= 0; }
	public static final boolean isNegative(final float foo) { return foo < 0; }   public static final boolean isPositive(final float foo) { return foo >= 0; }
	public static final boolean isNegative(final double foo) { return foo < 0; }  public static final boolean isPositive(final double foo) { return foo >= 0; }
	public static final boolean bothNegative(final int foo, final int bar) { return isNegative(foo) && isNegative(bar); }
	public static final boolean bothNegative(final float foo, final float bar) { return isNegative(foo) && isNegative(bar); }
	public static final boolean bothNegative(final double foo, final double bar) { return isNegative(foo) && isNegative(bar); }
	public static final boolean bothPositive(final int foo, final int bar) { return isPositive(foo) && isPositive(bar); }
	public static final boolean bothPositive(final float foo, final float bar) { return isPositive(foo) && isPositive(bar); }
	public static final boolean bothPositive(final double foo, final double bar) { return isPositive(foo) && isPositive(bar); }
	public static final boolean isPercent(final float foo)  { return foo >= 0 && foo <= 99.999f; }
	public static final boolean isPercent(final double foo) { return foo >= 0 && foo <= 99.999f; }
	/************************************************************************************/

	/************************** @ROTATION ************************************************/
	public static final Point   rotatePoint(final Point p, final double theta)  { return new Point((int)(p.x * Math.cos(theta) - p.y * Math.sin(theta)), (int)(p.x * Math.sin(theta) + p.y * Math.cos(theta)));}
	public static final Point2f rotatePoint(final Point2f p, final double theta){ return new Point2f(p.x * Math.cos(theta) - p.y * Math.sin(theta), p.x * Math.sin(theta) + p.y * Math.cos(theta));}
	public static final float   rotateX(final int    x, final int    y, final double theta) { return (float)(x * Math.cos(theta) - y * Math.sin(theta)); }
	public static final float   rotateY(final int    x, final int    y, final double theta) { return (float)(x * Math.sin(theta) + y * Math.cos(theta)); }
	public static final float   rotateX(final float  x, final float  y, final double theta) { return (float)(x * Math.cos(theta) - y * Math.sin(theta)); }
	public static final float   rotateY(final float  x, final float  y, final double theta) { return (float)(x * Math.sin(theta) + y * Math.cos(theta)); }
	public static final double  rotateX(final double x, final double y, final double theta) { return 		  x * Math.cos(theta) - y * Math.sin(theta);  }
	public static final double  rotateY(final double x, final double y, final double theta) { return 		  x * Math.sin(theta) + y * Math.cos(theta);	}
	/************************************************************************************/

	/************************** @ABSOLUTE_VALUE ******************************************/
	public static final int    abs(final int value)    { return value >= 0 ? value : value * -1;  }
	public static final float  abs(final float value)  { return value >= 0 ? value : value * -1f; }
	public static final double abs(final double value) { return value >= 0 ? value : value * -1d; }
	public static final long   abs(final long value)   { return value >= 0 ? value : value * -1;  }
	/************************************************************************************/

	/*************************** @FAST_SQUARE_ROOT *************************************/
	// evil floating point bit level hacking
	// what the fuck?
	public static final float  sqrt(final float foo) { return (float)sqrt((double)foo); }
	public static final double sqrt(final double foo) { final double sqrt = Double.longBitsToDouble( ( Double.doubleToLongBits( foo )-(1l<<52)>>1 ) + ( 1l<<61 ) ); return (sqrt + foo/sqrt)/2.0d; }
	/**********************************************************************************/

	/************************************** @PYTHAGORAS **********************************/
	public static final float pythagoras(final int foo, final int bar){ return sqrt(square(foo) + square(bar)); }
	public static final float pythagoras(final float foo, final float bar){ return sqrt(square(foo) + square(bar)); }
	public static final double pythagoras(final double foo, final double bar){ return sqrt(square(foo) + square(bar)); }
	/************************************************************************************/

	/**************************************** @POINT_DISTANCE *************************************************************************/
	public static final float distance(final Point2f foo, final Point2f bar){ return Utils.sqrt(Utils.square(foo.y - bar.y) + Utils.square(foo.x - bar.x)); }
	public static final float distance(final Point foo, final Point bar){ return Utils.sqrt(Utils.square(foo.y - bar.y) + Utils.square(foo.x - bar.x)); }
	public static final float distance(final float x1, final float y1, final float x2, final float y2){ return Utils.sqrt(Utils.square(y1 - y2) + Utils.square(x1 - x2)); }
	public static final float distance(final int x1, final int y1, final int x2, final int y2){ return Utils.sqrt(Utils.square(y1 - y2) + Utils.square(x1 - x2)); }
	/******************************************************************************************************************************************/

	/************************************** @POINT_DIRECTION **********************************************************************/
	/** @direction: returns the angle in radians that point is from another */
	public static final float direction(final Point2f foo, final Point2f bar){ return foo.direction(bar); }
	public static final int intDirection(final Point2f foo, final Point2f bar) {
		double angle = toDegrees(atan2(bar.y - foo.y, bar.x - foo.x)); if(angle < 0) angle+=360;
		if(angle > 247.5 && angle < 292.5) return 1; if(angle > 67.5  && angle < 112.5)  return 5;
		if(angle > 292.5 && angle < 337.5) return 2; if(angle > 112.5 && angle < 157.5) return 6;
		if(angle > 337.5 || angle < 22.5)  return 3; if(angle > 157.5 && angle < 202.5) return 7;
		if(angle > 22.5  && angle < 67.5)  return 4; if(angle > 202.5 && angle < 247.5) return 8; return 1;
	}
	/*****************************************************************************************************************************************/

	/************************************** @MISC ***********************************************************/
	public static final synchronized int getNumProcessors() { return Runtime.getRuntime().availableProcessors(); }
	public static final float  nextUlp(final float input)   { return input + ulp; }
	public static final double nextUlp(final double input)  { return input + ulp; }
	public static final <E> void print(final E e) { System.out.println(e); }
	public static final <E> void error(final E e) { System.err.println(e); }
	public static final <E> void print(final E[] arr) { for(final E e : arr) System.out.println(e); }
	public static final <E> void dialog(final E e) { JOptionPane.showMessageDialog(null, e); }
	public static final void exit() { System.exit(0); }
	public static final void quit() { exit(); }
	/********************************************************************************************************/

	/************************************* @SERIALIZATION ****************************************************/
	public static final synchronized void   saveObject(final Object obj, final String path){ __SerializationUtils.saveObject(obj, path); }
	public static final synchronized Object readObject(final String path){ return __SerializationUtils.readObject(path); }
	/** NOTE: Unlike the other methods that load things as resource streams,
	 * 		  @writeStringToFile must use a file writer, which doesnt use a stream.
	 * 		  Do not put the '/' in front of your path!  						 */
	public static final synchronized void writeStringToFile(final String string, final String path) { __SerializationUtils.writeStringToFile(string, path); }
	/*********************************************************************************************************/

	/************************************ @IMAGE_RGB_MANIPULATION ********************************************/
	public static final int unpackRed(final int pixel)   { return pixel >> 16 & 0xff; }
	public static final int unpackGreen(final int pixel) { return pixel >> 8 & 0xff;  }
	public static final int unpackBlue(final int pixel)  { return pixel & 0xff;       }
	public static final boolean isTransparent(final int pixel)  { return pixel == voidColor.getRGB() || pixel>>24 == 0x00;  }
	public static final int getTransparentPixel() { return 0x00; }
	/********************************************************************************************************/

	/************************* @RANDOM_CHANCES **************************************************************/
	public static final boolean COIN_FLIP()              { return rand.nextBoolean();           }
	public static final boolean ONE_PERCENT()            { return rand.nextInt(100) == 0;       }
	public static final boolean ONE_IN_A_THOUSAND()      { return rand.nextInt(1_000) == 0;     }
	public static final boolean ONE_IN_A_MILLION()       { return rand.nextInt(1_000_000) == 0; }
	public static final boolean roll(final int bound)          { return rand.nextInt(bound) == 0;     }
	public static final int     nextInt(final int bound)	     { return rand.nextInt(bound);          }
	public static final float   nextFloat()				 { return rand.nextFloat();				}
	public static final double  nextDouble()			 { return rand.nextDouble();			}
	public static final int nextInt(final int floor, final int ceiling) { return restrict(nextInt(ceiling+1), floor);  }
	public static final byte nextByte() { return (byte)nextInt(-128, 128); }
	/********************************************************************************************************/

	/******************************** @MAX_AND_MIN ******************************************************/
	public static final int    max(final int ...args)         { return __MixMaxUtils.biggest(args);  }
	public static final float  max(final float ...args)       { return __MixMaxUtils.biggest(args);  }
	public static final double max(final double ...args)      { return __MixMaxUtils.biggest(args);  }
	public static final int    biggest(final int ...args)     { return __MixMaxUtils.biggest(args);  }
	public static final float  biggest(final float ...args)   { return __MixMaxUtils.biggest(args);  }
	public static final double biggest(final double ...args)  { return __MixMaxUtils.biggest(args);  }
	public static final int    min(final int ...args)         { return __MixMaxUtils.smallest(args); }
	public static final float  min(final float ...args)       { return __MixMaxUtils.smallest(args); }
	public static final double min(final double ...args)      { return __MixMaxUtils.smallest(args); }
	public static final int    smallest(final int ...args)    { return __MixMaxUtils.smallest(args); }
	public static final float  smallest(final float ...args)  { return __MixMaxUtils.smallest(args); }
	public static final double smallest(final double ...args) { return __MixMaxUtils.smallest(args); }
	/********************************************************************************************************/

	/********************************** @ROUNDING ***********************************************************/
	public static final float  round(final float i) { return isPositive(i) ? truncate(i + 0.5f) : truncate(i - 0.5f); }
	public static final double round(final double i) { return isPositive(i) ? truncate(i + 0.5f) : truncate(i - 0.5f); }
	public static final float  truncate(final float input) { return (int)input; }
	public static final double truncate(final double input) { return (int)input; }
	public static final float  floor(final float input) { return truncate(input); }
	public static final double floor(final double input) { return truncate(input); }
	public static final float  ceil(final float input) { return truncate(input + 0.5f); }
	public static final double ceil(final double input) { return truncate(input + 0.5f); }
	/********************************************************************************************************/


	/****************************** @INTERSECTION_UTILS ******************************************************/
	/** @calcMostIntersecting: Takes in a rectangle to check, and any number of other rectangles.
	 * 						   Returns the rect from args that intersects with rect the most.
	 * 						   If there is a tie, it will return a random rectangle from the answers. */
	public static final Rectangle calcMostIntersecting(final Rectangle rect, final Rectangle ...args) { return __IntersectionUtils.calcMostIntersecting(rect, args); }
	public static final Rectangle calcMostIntersecting(final Rectangle rect, final Stack<Rectangle> args) { return __IntersectionUtils.calcMostIntersecting(rect, args); }
	/** @fudgeRectangleIntersection: returns if the rectangles collide given the tolerance amount     */
	public static final boolean fudgeRectIntersection(final Rectangle r1, final Rectangle r2, final float fudge_amount) { return __IntersectionUtils.fudgeRectIntersect(r1, r2, fudge_amount); }
	public static final float   calcArea(final float x1, final float y1, final float x2, final float y2) { return (x2 - x1) * (y2 - y1); }
	public static final boolean intersects(final Point2f center, final float radius, final Rectangle rect)  { return __IntersectionUtils.intersects(center, radius, rect); }
	public static final boolean intersects(final Circle circle, final Rectangle rect) { return __IntersectionUtils.intersects(circle, rect); }
	public static final boolean intersects(final Rectangle rect, final Circle circle) { return __IntersectionUtils.intersects(circle, rect); }
	public static final boolean intersects(final Circle c1, final Circle c2){ return c1.intersects(c2); }
	public static final boolean intersects(final Circle c1, final Point2f center2, final float radius2){ return c1.intersects(center2, radius2); }
	public static final boolean intersects(final Point2f c1, final float r1, final Point2f c2, final float r2){ return c1.distance(c2) <= r1 + r2; }
	public static final boolean intersects(final Line l1, final Line l2) { return l1.intersects(l2); }
	public static final boolean intersects(final Point2f p1, final Point2f p2, final Point2f q1, final Point2f q2) { return new Line(p1, p2).intersects(new Line(q1, q2)); }
	public static final boolean contains(final Rectangle r, final Point2f p) { return r.contains(p); }
	public static final boolean contains(final Rectangle r, final SimplePoint p) { return r.contains(p); }
	public static final boolean contains(final Point2f center, final float radius, final Point2f p){ return __IntersectionUtils.contains(center, radius, p); }
	public static final boolean contains(final Point2f center, final float radius, final float x, final float y){ return __IntersectionUtils.contains(center, radius, x, y); }
	/********************************************************************************************************/

	/************************************* @ALGORITHMS ******************************************************/
	public static final SimplePoint[] brensenham(final SimplePoint p1, final SimplePoint p2) { return __Algorithms.brensenham(p1, p2); }
	public static final SimplePoint[] brensenham(final Point2f p1, final Point2f p2) { return __Algorithms.brensenham(p1, p2); }
	public static final SimplePoint[] brensenham(final int x1, final int y1, final int x2, final int y2) { return __Algorithms.brensenham(x1, y1, x2, y2); }
	public static int greatestCommonDivisor(final int p, final int q) { return q == 0 ? p : greatestCommonDivisor(q, p % q); }
	/********************************************************************************************************/



}