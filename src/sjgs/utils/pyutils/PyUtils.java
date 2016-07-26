package sjgs.utils.pyutils;


import static sjgs.core.jython.Jython.pi;
import java.io.InputStream;
import org.python.core.Py;
import org.python.core.PyBoolean;
import org.python.core.PyFloat;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import sjgs.core.jython.Jython;
import sjgs.utils.Utils;

public class PyUtils {

	public static final PyBoolean True = Py.True, False = Py.False;
	public static final PyInteger One = Py.One, Zero = Py.Zero;

	public static final synchronized void initializePySystem() { PySystemState.initialize(System.getProperties(), System.getProperties()); }

	public static final void execPyScript(final String filename) {
		InputStream in = Utils.class.getResourceAsStream(filename);
		Jython.pi.execfile(filename);
		try { in.close(); in = null; } catch (final Exception e) { e.printStackTrace(); }
	}
	
	public static PyFunction createPyFunction(String funcName) { return pi.get(funcName, PyFunction.class); }

	public static PyObject java2py(final Object o)   { return Py.java2py(o);    }
	public static PyInteger int2py(final int i)      { return new PyInteger(i); }
	public static PyFloat float2py(final float f)    { return new PyFloat(f);   }
	public static PyBoolean bool2py(final boolean b) { return b ? True : False; }

	public static <T> Object tojava(final PyObject o, final Class<T> c) { return Py.tojava(o, c); }

}
