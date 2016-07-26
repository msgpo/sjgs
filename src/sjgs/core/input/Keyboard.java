package sjgs.core.input;

import java.util.HashSet;

public interface Keyboard {

	static final HashSet<Integer> keysDown = __Keyboard.keysDown;

	// --------------------- KEY CODES ---------------------------------- //
	static final int W=0, A=1, S=2, D=3, SPACE=4, TAB=5, E=11, Q=12, ESCAPE=13, F=14,
			MINUS=15, EQUALS = 16, CTRL = 17, PLUS=18, ONE=6, TWO=7, THREE=8,
			FOUR=9, FIVE=10, SIX=19, SEVEN=20, EIGHT=21, NINE=22, ZERO=23, R=24,
			Z=25, X=26, C=27, V=28, B=29, G=30, T=31, M=32, UP=33, DOWN=34, RIGHT=35, LEFT=36;
	// -------------------- END KEY CODES ------------------------------- //

	public static boolean W() 	 	{ return keysDown.contains(W); 		}
	public static boolean A() 	 	{ return keysDown.contains(A); 		}
	public static boolean S() 	 	{ return keysDown.contains(S); 		}
	public static boolean D() 	 	{ return keysDown.contains(D); 		}
	public static boolean SPACE()  	{ return keysDown.contains(SPACE);	}
	public static boolean TAB() 	{ return keysDown.contains(TAB); 	}
	public static boolean E() 	 	{ return keysDown.contains(E); 		}
	public static boolean Q() 	 	{ return keysDown.contains(Q); 		}
	public static boolean ESCAPE() 	{ return keysDown.contains(ESCAPE); }
	public static boolean F() 		{ return keysDown.contains(F); 		}
	public static boolean MINUS() 	{ return keysDown.contains(MINUS); 	}
	public static boolean EQUALS()	{ return keysDown.contains(EQUALS); }
	public static boolean CTRL()	{ return keysDown.contains(CTRL); 	}
	public static boolean PLUS() 	{ return keysDown.contains(PLUS); 	}
	public static boolean ONE() 	{ return keysDown.contains(ONE); 	}
	public static boolean TWO() 	{ return keysDown.contains(TWO); 	}
	public static boolean THREE() 	{ return keysDown.contains(THREE); 	}
	public static boolean FOUR() 	{ return keysDown.contains(FOUR); 	}
	public static boolean FIVE() 	{ return keysDown.contains(FIVE); 	}
	public static boolean SIX() 	{ return keysDown.contains(SIX); 	}
	public static boolean SEVEN() 	{ return keysDown.contains(SEVEN); 	}
	public static boolean EIGHT() 	{ return keysDown.contains(EIGHT); 	}
	public static boolean NINE() 	{ return keysDown.contains(NINE); 	}
	public static boolean ZERO() 	{ return keysDown.contains(ZERO); 	}
	public static boolean R() 		{ return keysDown.contains(R); 		}
	public static boolean Z() 		{ return keysDown.contains(Z); 		}
	public static boolean X() 		{ return keysDown.contains(X); 		}
	public static boolean C() 		{ return keysDown.contains(C);	 	}
	public static boolean V() 		{ return keysDown.contains(V);		}
	public static boolean B() 		{ return keysDown.contains(B);		}
	public static boolean G() 		{ return keysDown.contains(G); 		}
	public static boolean T() 		{ return keysDown.contains(T); 		}
	public static boolean M() 		{ return keysDown.contains(M); 		}
	public static boolean UP() 		{ return keysDown.contains(UP); 	}
	public static boolean DOWN() 	{ return keysDown.contains(DOWN); 	}
	public static boolean RIGHT() 	{ return keysDown.contains(RIGHT); 	}
	public static boolean LEFT() 	{ return keysDown.contains(LEFT); 	}


}
