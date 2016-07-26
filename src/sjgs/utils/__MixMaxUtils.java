package sjgs.utils;

class __MixMaxUtils {

	static final int biggest(final int ...args) {
		if(args.length == 0) { System.err.println("ERROR: Utils.biggest --- args.length == 0"); System.exit(1); }
		else if(args.length == 1) return args[0];

		int max = args[0];
		for(int i = 1; i < args.length; i++) if(args[i] > max) max = args[i];
		return max;
	}

	static final float biggest(final float ...args) {
		if(args.length == 0) { System.err.println("ERROR: Utils.biggest --- args.length == 0"); System.exit(1); }
		else if(args.length == 1) return args[0];

		float max = args[0];
		for(int i = 1; i < args.length; i++) if(args[i] > max) max = args[i];
		return max;
	}

	static final double biggest(final double ...args) {
		if(args.length == 0) { System.err.println("ERROR: Utils.biggest --- args.length == 0"); System.exit(1); }
		else if(args.length == 1) return args[0];

		double max = args[0];
		for(int i = 1; i < args.length; i++) if(args[i] > max) max = args[i];
		return max;
	}

	static final int smallest(final int ...args) {
		if(args.length == 0) { System.err.println("ERROR: Utils.smallest --- args.length == 0"); System.exit(1); }
		else if(args.length == 1) return args[0];

		int min = args[0];
		for(int i = 1; i < args.length; i++) if(args[i] < min) min = args[i];
		return min;
	}

	static final float smallest(final float ...args) {
		if(args.length == 0) { System.err.println("ERROR: Utils.smallest --- args.length == 0"); System.exit(1); }
		else if(args.length == 1) return args[0];

		float min = args[0];
		for(int i = 1; i < args.length; i++) if(args[i] < min) min = args[i];
		return min;
	}

	static final double smallest(final double ...args) {
		if(args.length == 0) { System.err.println("ERROR: Utils.smallest --- args.length == 0"); System.exit(1); }
		else if(args.length == 1) return args[0];

		double min = args[0];
		for(int i = 1; i < args.length; i++) if(args[i] < min) min = args[i];
		return min;
	}

	static final int max(final int ...args) { return biggest(args); }
	static final float max(final float ...args) { return biggest(args); }
	static final double max(final double ...args) { return biggest(args); }
	static final int min(final int ...args) { return smallest(args); }
	static final float min(final float ...args) { return smallest(args); }
	static final double min(final double ...args) { return smallest(args); }

}
