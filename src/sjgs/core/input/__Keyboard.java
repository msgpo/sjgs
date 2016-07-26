package sjgs.core.input;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public final class __Keyboard implements Keyboard {

	// NOTE THIS IS ONLY TO BE USED FOR GAMES, TO BE USED IN REGULAR TYPING, A KEY ADAPTER WILL WORK MUCH BETTER

	static HashSet<Integer> keysDown;

	public void init(final JPanel panel) {
		keysDown = new HashSet<Integer>();
		initWASD(panel);
		initArrowKeys(panel);
		initSpace(panel);
		initEscape(panel);
		initNumbers(panel);
		initQ(panel);
		initE(panel);
		initR(panel);
		initF(panel);
		initZ(panel);
		initX(panel);
		initC(panel);
		initV(panel);
		initB(panel);
		initG(panel);
		initT(panel);
		initM(panel);
	}

	// *************** INITIALIZATION *************************************************************************************************** //


	private static void initWASD(final JPanel panel) {
		final int when = JComponent.WHEN_IN_FOCUSED_WINDOW;
		// ---- ADD WASD ------------------------------------ //
		final Action walkUp = new AbstractAction()    { @Override
			public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(W)) keysDown.add(W); } };
			final Action walkDown = new AbstractAction()  { @Override
				public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(S)) keysDown.add(S); } };
				final Action walkLeft = new AbstractAction()  { @Override
					public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(A)) keysDown.add(A); } };
					final Action walkRight = new AbstractAction() { @Override
						public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(D)) keysDown.add(D); } };
						panel.getInputMap(when).put(KeyStroke.getKeyStroke("W"), "walkUp");
						panel.getInputMap(when).put(KeyStroke.getKeyStroke("A"), "walkLeft");
						panel.getInputMap(when).put(KeyStroke.getKeyStroke("S"), "walkDown");
						panel.getInputMap(when).put(KeyStroke.getKeyStroke("D"), "walkRight");
						panel.getActionMap().put("walkUp", walkUp);
						panel.getActionMap().put("walkDown", walkDown);
						panel.getActionMap().put("walkLeft", walkLeft);
						panel.getActionMap().put("walkRight", walkRight);
						// --- END ADD WASD --------------------------------- //

						// ---- REMOVE WASD ----------------------------------//
						final Action stopWalkUp = new AbstractAction()    { @Override
							public void actionPerformed(final ActionEvent e) { if (keysDown.contains(W)) keysDown.remove(W); } };
							final Action stopWalkDown = new AbstractAction()  { @Override
								public void actionPerformed(final ActionEvent e) { if (keysDown.contains(S)) keysDown.remove(S); } };
								final Action stopWalkLeft = new AbstractAction()  { @Override
									public void actionPerformed(final ActionEvent e) { if (keysDown.contains(A)) keysDown.remove(A); } };
									final Action stopWalkRight = new AbstractAction() { @Override
										public void actionPerformed(final ActionEvent e) { if (keysDown.contains(D)) keysDown.remove(D); } };
										panel.getInputMap(when).put(KeyStroke.getKeyStroke("released W"), "stopWalkUp");
										panel.getInputMap(when).put(KeyStroke.getKeyStroke("released A"), "stopWalkLeft");
										panel.getInputMap(when).put(KeyStroke.getKeyStroke("released S"), "stopWalkDown");
										panel.getInputMap(when).put(KeyStroke.getKeyStroke("released D"), "stopWalkRight");
										panel.getActionMap().put("stopWalkUp", stopWalkUp);
										panel.getActionMap().put("stopWalkDown", stopWalkDown);
										panel.getActionMap().put("stopWalkLeft", stopWalkLeft);
										panel.getActionMap().put("stopWalkRight", stopWalkRight);
										// -- END REMOVE WASD ----------------------------------//
	}

	private static void initQ(final JPanel panel) {
		final int when = JComponent.WHEN_IN_FOCUSED_WINDOW;
		final Action addQ = new AbstractAction()    { @Override
			public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(Q)) keysDown.add(Q); } };
			panel.getInputMap(when).put(KeyStroke.getKeyStroke("Q"), "addQ");
			panel.getActionMap().put("addQ", addQ);
			final Action removeQ = new AbstractAction()    { @Override
				public void actionPerformed(final ActionEvent e) { if (keysDown.contains(Q)) keysDown.remove(Q); } };
				panel.getInputMap(when).put(KeyStroke.getKeyStroke("released Q"), "removeQ");
				panel.getActionMap().put("removeQ", removeQ);
	}

	private static void initE(final JPanel panel) {
		final int when = JComponent.WHEN_IN_FOCUSED_WINDOW;
		final Action addE = new AbstractAction()    { @Override
			public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(E)) keysDown.add(E); } };
			panel.getInputMap(when).put(KeyStroke.getKeyStroke("E"), "addE");
			panel.getActionMap().put("addE", addE);
			final Action removeE = new AbstractAction()    { @Override
				public void actionPerformed(final ActionEvent e) { if (keysDown.contains(E)) keysDown.remove(E); } };
				panel.getInputMap(when).put(KeyStroke.getKeyStroke("released E"), "removeE");
				panel.getActionMap().put("removeE", removeE);
	}

	private static void initR(final JPanel panel) {
		final int when = JComponent.WHEN_IN_FOCUSED_WINDOW;
		final Action addR = new AbstractAction()    { @Override
			public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(R)) keysDown.add(R); } };
			panel.getInputMap(when).put(KeyStroke.getKeyStroke("R"), "addR");
			panel.getActionMap().put("addR", addR);
			final Action removeR = new AbstractAction()    { @Override
				public void actionPerformed(final ActionEvent e) { if (keysDown.contains(R)) keysDown.remove(R); } };
				panel.getInputMap(when).put(KeyStroke.getKeyStroke("released R"), "removeR");
				panel.getActionMap().put("removeR", removeR);
	}

	private static void initF(final JPanel panel) {
		final int when = JComponent.WHEN_IN_FOCUSED_WINDOW;
		final Action addF = new AbstractAction()    { @Override
			public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(F)) keysDown.add(F); } };
			panel.getInputMap(when).put(KeyStroke.getKeyStroke("F"), "addF");
			panel.getActionMap().put("addF", addF);
			final Action removeF = new AbstractAction()    { @Override
				public void actionPerformed(final ActionEvent e) { if (keysDown.contains(F)) keysDown.remove(F); } };
				panel.getInputMap(when).put(KeyStroke.getKeyStroke("released F"), "removeF");
				panel.getActionMap().put("removeF", removeF);
	}

	private static void initSpace(final JPanel panel) {
		final int when = JComponent.WHEN_IN_FOCUSED_WINDOW;
		final Action space = new AbstractAction() { @Override
			public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(SPACE)) keysDown.add(SPACE); } };
			panel.getInputMap(when).put(KeyStroke.getKeyStroke("SPACE"), "space");
			panel.getActionMap().put("space", space);
			final Action releasedSpace = new AbstractAction() { @Override
				public void actionPerformed(final ActionEvent e) { if (keysDown.contains(SPACE)) keysDown.remove(SPACE); } };
				panel.getInputMap(when).put(KeyStroke.getKeyStroke("released SPACE"), "releasedSpace");
				panel.getActionMap().put("releasedSpace", releasedSpace);
	}

	private static void initEscape(final JPanel panel) {
		final int when = JComponent.WHEN_IN_FOCUSED_WINDOW;
		final Action escape = new AbstractAction() { @Override
			public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(ESCAPE)) keysDown.add(ESCAPE); } };
			panel.getInputMap(when).put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
			panel.getActionMap().put("escape", escape);
			final Action releasedEscape = new AbstractAction() { @Override
				public void actionPerformed(final ActionEvent e) { if (keysDown.contains(ESCAPE)) keysDown.remove(ESCAPE); } };
				panel.getInputMap(when).put(KeyStroke.getKeyStroke("released ESCAPE"), "releasedEscape");
				panel.getActionMap().put("releasedEscape", releasedEscape);
	}

	private static void initZ(final JPanel panel) {
		final int when = JComponent.WHEN_IN_FOCUSED_WINDOW;
		final Action addZ = new AbstractAction()    { @Override
			public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(Z)) keysDown.add(Z); } };
			panel.getInputMap(when).put(KeyStroke.getKeyStroke("Z"), "addZ");
			panel.getActionMap().put("addZ", addZ);
			final Action removeZ = new AbstractAction()    { @Override
				public void actionPerformed(final ActionEvent e) { if (keysDown.contains(Z)) keysDown.remove(Z); } };
				panel.getInputMap(when).put(KeyStroke.getKeyStroke("released Z"), "removeZ");
				panel.getActionMap().put("removeZ", removeZ);
	}

	private static void initX(final JPanel panel) {
		final int when = JComponent.WHEN_IN_FOCUSED_WINDOW;
		final Action addX = new AbstractAction()    { @Override
			public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(X)) keysDown.add(X); } };
			panel.getInputMap(when).put(KeyStroke.getKeyStroke("X"), "addX");
			panel.getActionMap().put("addX", addX);
			final Action removeX = new AbstractAction()    { @Override
				public void actionPerformed(final ActionEvent e) { if (keysDown.contains(X)) keysDown.remove(X); } };
				panel.getInputMap(when).put(KeyStroke.getKeyStroke("released X"), "removeX");
				panel.getActionMap().put("removeX", removeX);
	}

	private static void initC(final JPanel panel) {
		final int when = JComponent.WHEN_IN_FOCUSED_WINDOW;
		final Action addC = new AbstractAction()    { @Override
			public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(C)) keysDown.add(C); } };
			panel.getInputMap(when).put(KeyStroke.getKeyStroke("C"), "addC");
			panel.getActionMap().put("addC", addC);
			final Action removeC = new AbstractAction()    { @Override
				public void actionPerformed(final ActionEvent e) { if (keysDown.contains(C)) keysDown.remove(C); } };
				panel.getInputMap(when).put(KeyStroke.getKeyStroke("released C"), "removeC");
				panel.getActionMap().put("removeC", removeC);
	}

	private static void initV(final JPanel panel) {
		final int when = JComponent.WHEN_IN_FOCUSED_WINDOW;
		final Action addV = new AbstractAction()    { @Override
			public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(V)) keysDown.add(V); } };
			panel.getInputMap(when).put(KeyStroke.getKeyStroke("V"), "addV");
			panel.getActionMap().put("addV", addV);
			final Action removeV = new AbstractAction()    { @Override
				public void actionPerformed(final ActionEvent e) { if (keysDown.contains(V)) keysDown.remove(V); } };
				panel.getInputMap(when).put(KeyStroke.getKeyStroke("released V"), "removeV");
				panel.getActionMap().put("removeV", removeV);
	}

	private static void initB(final JPanel panel) {
		final int when = JComponent.WHEN_IN_FOCUSED_WINDOW;
		final Action addB = new AbstractAction()    { @Override
			public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(B)) keysDown.add(B); } };
			panel.getInputMap(when).put(KeyStroke.getKeyStroke("B"), "addB");
			panel.getActionMap().put("addB", addB);
			final Action removeB = new AbstractAction()    { @Override
				public void actionPerformed(final ActionEvent e) { if (keysDown.contains(B)) keysDown.remove(B); } };
				panel.getInputMap(when).put(KeyStroke.getKeyStroke("released B"), "removeB");
				panel.getActionMap().put("removeB", removeB);
	}

	private static void initG(final JPanel panel) {
		final int when = JComponent.WHEN_IN_FOCUSED_WINDOW;
		final Action addG = new AbstractAction()    { @Override
			public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(G)) keysDown.add(G); } };
			panel.getInputMap(when).put(KeyStroke.getKeyStroke("G"), "addG");
			panel.getActionMap().put("addG", addG);
			final Action removeG = new AbstractAction()    { @Override
				public void actionPerformed(final ActionEvent e) { if (keysDown.contains(G)) keysDown.remove(G); } };
				panel.getInputMap(when).put(KeyStroke.getKeyStroke("released G"), "removeG");
				panel.getActionMap().put("removeG", removeG);
	}

	private static void initT(final JPanel panel) {
		final int when = JComponent.WHEN_IN_FOCUSED_WINDOW;
		final Action addT = new AbstractAction()    { @Override
			public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(T)) keysDown.add(T); } };
			panel.getInputMap(when).put(KeyStroke.getKeyStroke("T"), "addT");
			panel.getActionMap().put("addT", addT);
			final Action removeT = new AbstractAction()    { @Override
				public void actionPerformed(final ActionEvent e) { if (keysDown.contains(T)) keysDown.remove(T); } };
				panel.getInputMap(when).put(KeyStroke.getKeyStroke("released T"), "removeT");
				panel.getActionMap().put("removeT", removeT);
	}

	private static void initM(final JPanel panel) {
		final int when = JComponent.WHEN_IN_FOCUSED_WINDOW;
		final Action addM = new AbstractAction()    { @Override
			public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(M)) keysDown.add(M); } };
			panel.getInputMap(when).put(KeyStroke.getKeyStroke("M"), "addM");
			panel.getActionMap().put("addM", addM);
			final Action removeM = new AbstractAction()    { @Override
				public void actionPerformed(final ActionEvent e) { if (keysDown.contains(M)) keysDown.remove(M); } };
				panel.getInputMap(when).put(KeyStroke.getKeyStroke("released M"), "removeM");
				panel.getActionMap().put("removeM", removeM);
	}

	private static void initNumbers(final JPanel panel) {
		final int when = JComponent.WHEN_IN_FOCUSED_WINDOW;
		// -------------------------------- ADDS --------------------------------------- //
		final Action add1 = new AbstractAction() { @Override
			public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(Keyboard.ONE))   keysDown.add(Keyboard.ONE); } };
			final Action add2 = new AbstractAction() { @Override
				public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(Keyboard.TWO))   keysDown.add(Keyboard.TWO); } };
				final Action add3 = new AbstractAction() { @Override
					public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(Keyboard.THREE)) keysDown.add(Keyboard.THREE); } };
					final Action add4 = new AbstractAction() { @Override
						public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(Keyboard.FOUR))  keysDown.add(Keyboard.FOUR); } };
						final Action add5 = new AbstractAction() { @Override
							public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(Keyboard.FIVE))  keysDown.add(Keyboard.FIVE); } };
							final Action add6 = new AbstractAction() { @Override
								public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(Keyboard.SIX))   keysDown.add(Keyboard.SIX); } };
								final Action add7 = new AbstractAction() { @Override
									public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(Keyboard.SEVEN)) keysDown.add(Keyboard.SEVEN); } };
									final Action add8 = new AbstractAction() { @Override
										public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(Keyboard.EIGHT)) keysDown.add(Keyboard.EIGHT); } };
										final Action add9 = new AbstractAction() { @Override
											public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(Keyboard.NINE))  keysDown.add(Keyboard.NINE); } };
											final Action add0 = new AbstractAction() { @Override
												public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(Keyboard.ZERO))  keysDown.add(Keyboard.ZERO); } };
												panel.getInputMap(when).put(KeyStroke.getKeyStroke("1"), "add1");
												panel.getInputMap(when).put(KeyStroke.getKeyStroke("2"), "add2");
												panel.getInputMap(when).put(KeyStroke.getKeyStroke("3"), "add3");
												panel.getInputMap(when).put(KeyStroke.getKeyStroke("4"), "add4");
												panel.getInputMap(when).put(KeyStroke.getKeyStroke("5"), "add5");
												panel.getInputMap(when).put(KeyStroke.getKeyStroke("6"), "add6");
												panel.getInputMap(when).put(KeyStroke.getKeyStroke("7"), "add7");
												panel.getInputMap(when).put(KeyStroke.getKeyStroke("8"), "add8");
												panel.getInputMap(when).put(KeyStroke.getKeyStroke("9"), "add9");
												panel.getInputMap(when).put(KeyStroke.getKeyStroke("0"), "add0");
												panel.getActionMap().put("add1", add1);
												panel.getActionMap().put("add2", add2);
												panel.getActionMap().put("add3", add3);
												panel.getActionMap().put("add4", add4);
												panel.getActionMap().put("add5", add5);
												panel.getActionMap().put("add6", add6);
												panel.getActionMap().put("add7", add7);
												panel.getActionMap().put("add8", add8);
												panel.getActionMap().put("add9", add9);
												panel.getActionMap().put("add0", add0);
												// ------------------------------ RELEASES ------------------------------------- //
												final Action release1 = new AbstractAction() { @Override
													public void actionPerformed(final ActionEvent e) { if (keysDown.contains(Keyboard.ONE))   keysDown.remove(Keyboard.ONE); } };
													final Action release2 = new AbstractAction() { @Override
														public void actionPerformed(final ActionEvent e) { if (keysDown.contains(Keyboard.TWO))   keysDown.remove(Keyboard.TWO); } };
														final Action release3 = new AbstractAction() { @Override
															public void actionPerformed(final ActionEvent e) { if (keysDown.contains(Keyboard.THREE)) keysDown.remove(Keyboard.THREE); } };
															final Action release4 = new AbstractAction() { @Override
																public void actionPerformed(final ActionEvent e) { if (keysDown.contains(Keyboard.FOUR))  keysDown.remove(Keyboard.FOUR); } };
																final Action release5 = new AbstractAction() { @Override
																	public void actionPerformed(final ActionEvent e) { if (keysDown.contains(Keyboard.FIVE))  keysDown.remove(Keyboard.FIVE); } };
																	final Action release6 = new AbstractAction() { @Override
																		public void actionPerformed(final ActionEvent e) { if (keysDown.contains(Keyboard.SIX))   keysDown.remove(Keyboard.SIX); } };
																		final Action release7 = new AbstractAction() { @Override
																			public void actionPerformed(final ActionEvent e) { if (keysDown.contains(Keyboard.SEVEN)) keysDown.remove(Keyboard.SEVEN); } };
																			final Action release8 = new AbstractAction() { @Override
																				public void actionPerformed(final ActionEvent e) { if (keysDown.contains(Keyboard.EIGHT)) keysDown.remove(Keyboard.EIGHT); } };
																				final Action release9 = new AbstractAction() { @Override
																					public void actionPerformed(final ActionEvent e) { if (keysDown.contains(Keyboard.NINE))  keysDown.remove(Keyboard.NINE); } };
																					final Action release0 = new AbstractAction() { @Override
																						public void actionPerformed(final ActionEvent e) { if (keysDown.contains(Keyboard.ZERO))  keysDown.remove(Keyboard.ZERO); } };

																						panel.getInputMap(when).put(KeyStroke.getKeyStroke("released 1"), "release1");
																						panel.getInputMap(when).put(KeyStroke.getKeyStroke("released 2"), "release2");
																						panel.getInputMap(when).put(KeyStroke.getKeyStroke("released 3"), "release3");
																						panel.getInputMap(when).put(KeyStroke.getKeyStroke("released 4"), "release4");
																						panel.getInputMap(when).put(KeyStroke.getKeyStroke("released 5"), "release5");
																						panel.getInputMap(when).put(KeyStroke.getKeyStroke("released 6"), "release6");
																						panel.getInputMap(when).put(KeyStroke.getKeyStroke("released 7"), "release7");
																						panel.getInputMap(when).put(KeyStroke.getKeyStroke("released 8"), "release8");
																						panel.getInputMap(when).put(KeyStroke.getKeyStroke("released 9"), "release9");
																						panel.getInputMap(when).put(KeyStroke.getKeyStroke("released 0"), "release0");
																						panel.getActionMap().put("release1", release1);
																						panel.getActionMap().put("release2", release2);
																						panel.getActionMap().put("release3", release3);
																						panel.getActionMap().put("release4", release4);
																						panel.getActionMap().put("release5", release5);
																						panel.getActionMap().put("release6", release6);
																						panel.getActionMap().put("release7", release7);
																						panel.getActionMap().put("release8", release8);
																						panel.getActionMap().put("release9", release9);
																						panel.getActionMap().put("release0", release0);


	}

	private static void initArrowKeys(final JPanel panel) {
		final int when = JComponent.WHEN_IN_FOCUSED_WINDOW;
		// ---- ADD ARROW KEYS ------------------------------------ //
		final Action ADD_UP = new AbstractAction()    { @Override
			public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(UP)) keysDown.add(UP); } };


			final Action ADD_DOWN = new AbstractAction()  { @Override
				public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(DOWN)) keysDown.add(DOWN); } };
				final Action ADD_LEFT = new AbstractAction()  { @Override
					public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(LEFT)) keysDown.add(LEFT); } };
					final Action ADD_RIGHT = new AbstractAction() { @Override
						public void actionPerformed(final ActionEvent e) { if (!keysDown.contains(RIGHT)) keysDown.add(RIGHT); } };
						panel.getInputMap(when).put(KeyStroke.getKeyStroke("UP"), "UP");
						panel.getInputMap(when).put(KeyStroke.getKeyStroke("LEFT"), "LEFT");
						panel.getInputMap(when).put(KeyStroke.getKeyStroke("DOWN"), "DOWN");
						panel.getInputMap(when).put(KeyStroke.getKeyStroke("RIGHT"), "RIGHT");
						panel.getActionMap().put("UP", ADD_UP);
						panel.getActionMap().put("LEFT", ADD_LEFT);
						panel.getActionMap().put("DOWN", ADD_DOWN);
						panel.getActionMap().put("RIGHT", ADD_RIGHT);
						// --- END ADD ARROW KEYS --------------------------------- //

						// ---- REMOVE ARROW KEYS ----------------------------------//
						final Action REMOVE_UP = new AbstractAction()    { @Override
							public void actionPerformed(final ActionEvent e) { if (keysDown.contains(UP)) keysDown.remove(UP); } };
							final Action REMOVE_DOWN = new AbstractAction()  { @Override
								public void actionPerformed(final ActionEvent e) { if (keysDown.contains(DOWN)) keysDown.remove(DOWN); } };
								final Action REMOVE_LEFT = new AbstractAction()  { @Override
									public void actionPerformed(final ActionEvent e) { if (keysDown.contains(LEFT)) keysDown.remove(LEFT); } };
									final Action REMOVE_RIGHT = new AbstractAction() { @Override
										public void actionPerformed(final ActionEvent e) { if (keysDown.contains(RIGHT)) keysDown.remove(RIGHT); } };
										panel.getInputMap(when).put(KeyStroke.getKeyStroke("released UP"), "RELEASEUP");
										panel.getInputMap(when).put(KeyStroke.getKeyStroke("released LEFT"), "RELEASELEFT");
										panel.getInputMap(when).put(KeyStroke.getKeyStroke("released DOWN"), "RELEASEDOWN");
										panel.getInputMap(when).put(KeyStroke.getKeyStroke("released RIGHT"), "RELEASERIGHT");
										panel.getActionMap().put("RELEASEUP", REMOVE_UP);
										panel.getActionMap().put("RELEASEDOWN", REMOVE_DOWN);
										panel.getActionMap().put("RELEASELEFT", REMOVE_LEFT);
										panel.getActionMap().put("RELEASERIGHT", REMOVE_RIGHT);
										// -- END REMOVE ARROW KEYS ----------------------------------//
	}
	// ********************************************************************************************************************************** //

}
