package sjgs.utils;

import sjgs.utils.data_structures.Stack;
import sjgs.utils.data_structures.shapes.Circle;
import sjgs.utils.data_structures.shapes.Rectangle;
import sjgs.utils.data_structures.vectors.Point2f;

class __IntersectionUtils {

	static Rectangle calcMostIntersecting(final Rectangle rect, final Rectangle ...args) {
		if(args.length == 0) return null;  if(args.length == 1) return args[0];
		final Stack<Rectangle> rects = new Stack<Rectangle>();
		for(final Rectangle r : args) if(r != null && rect.intersects(r)) rects.push(r);
		if(rects.size() == 0) return null;
		final float[] areas = new float[rects.size()];
		for(int i = 0; i < rects.size(); i++) areas[i] = rect.getIntersectingArea(rects.get(i));
		final float largest_area = Utils.max(areas);
		final Stack<Rectangle> answers = new Stack<Rectangle>();
		for(int i = 0; i < areas.length; i++)  if(areas[i] == largest_area) answers.push(rects.get(i));
		if(answers.size() == 0) return null;
		return answers.get(Utils.rand.nextInt(answers.size()));
	}

	static Rectangle calcMostIntersecting(final Rectangle rect, final Stack<Rectangle> args) {
		if(args.size() == 0) return null;  if(args.size() == 1) return args.getFirst();
		final Stack<Rectangle> rects = new Stack<Rectangle>();
		for(final Rectangle r : args) if(r != null && rect.intersects(r)) rects.push(r);
		if(rects.size() == 0) return null;
		final float[] areas = new float[rects.size()];
		for(int i = 0; i < rects.size(); i++) areas[i] = rect.getIntersectingArea(rects.get(i));
		final float largest_area = Utils.max(areas);
		final Stack<Rectangle> answers = new Stack<Rectangle>();
		for(int i = 0; i < areas.length; i++)  if(areas[i] == largest_area) answers.push(rects.get(i));
		if(answers.size() == 0) return null;
		return answers.get(Utils.rand.nextInt(answers.size()));
	}

	static boolean fudgeRectIntersect(final Rectangle r1, final Rectangle r2, final float fudge_amount) {
		return !(r1.pos.x + r1.width + fudge_amount < r2.pos.x ||   // if this is LEFT of r
				r1.pos.x - fudge_amount > r2.pos.x + r2.width ||   // if this is RIGHT of r
				r1.pos.y + r1.height + fudge_amount < r2.pos.y ||  // if this is BELOW r
				r1.pos.y - fudge_amount > r2.pos.y + r2.height);   // if this is ABOVE r
	}

	// CIRCLES AND RECTS
	static boolean intersects(final Circle circle, final Rectangle rect) {
		return intersects(circle.center, circle.radius, rect);
	}
	static boolean intersects(final Point2f center, final float radius, final Rectangle rect){
		return Utils.square(center.x - Utils.clamp(center.x, rect.pos.x, rect.pos.x + rect.width)) +
				Utils.square(center.y  - Utils.clamp(center.y, rect.pos.y, rect.pos.y + rect.height))
		< Utils.square(radius);
	}

	// Un-made circles and Point2fs
	static boolean contains(final Point2f center, final float radius, final Point2f p){ return Utils.square(p.x - center.x) + Utils.square(p.y - center.y) < Utils.square(radius); }
	static boolean contains(final Point2f center, final float radius, final float x, final float y){ return Utils.square(x - center.x) + Utils.square(y - center.y) < Utils.square(radius); }

}
