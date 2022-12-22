import java.io.*;
import java.lang.reflect.*;
import java.util.*;


public class loader {
final private static String wdir = System.getProperty("user.dir") + File.separator;

	public static void main(String[] arg) {
		
		try {
			load();
		}
		catch (IOException I) {
			System.out.println(I.toString());
		}
	}

	public static void load() throws IOException {
		load("", Comparator.<File>naturalOrder());
	}

	public static void load(final String dir, Comparator ord) throws IOException {
		final class KlassFilter implements FilenameFilter {

			public boolean accept(File parent, String filename) {
				boolean accepted = filename.endsWith(".class");
					
				if (filename.equals("loader.class")) {
					accepted = false;
				}
				if (filename.contains("$")) {
					accepted = false;
				}
				return accepted;
			}

		}
		File fdir = new File(wdir, dir);
		if (fdir.isDirectory() == false) throw new IOException(fdir.toString() + " is not a directory!");
		File[] files = fdir.listFiles(new KlassFilter());
		Arrays.sort(files, ord);
		for (File file : files) {
			String filename = file.getCanonicalPath().replace(wdir, "");
			String klassname = filename.replace(File.separator, ".").replace(".class", "");
			loadClass(klassname);
			
		}
	}

	private static void loadClass(String klassname) {
		try {

			Class<?> klass = Class.forName(klassname);
				try {
					Runnable rable = (Runnable)klass.newInstance();
					if (rable instanceof Runnable) {
					rable.run();
					}
				}
				catch (InstantiationException I) {
					System.err.println(klassname);
					System.err.println(I.toString());
				}
				catch (IllegalAccessException I) {
					System.err.println(klassname);
					System.err.println(I.toString());
				}
				catch (ClassCastException C) {
					System.err.println(klassname);
					System.err.println(C.toString());
				}


		}
		catch (ClassNotFoundException C) {
			System.err.println(klassname);
			System.err.println(C.toString());
		}
	}


}
