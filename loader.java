import java.io.*;
import java.lang.reflect.*;
import java.util.*;


public class loader {
final private static String wdir = System.getProperty("user.dir");

	public static void main(String[] arg) {
		
		try {
			load();
		}
		catch (IOException I) {
			System.out.println(I.toString());
		}
	}

	public static void load() throws IOException {

		load(wdir, Comparator.<String>naturalOrder());
	}

	public static void loadFrom(String subdir, Comparator ord) throws IOException {
		String dir = wdir + File.separator + subdir;
		load(subdir, Comparator.<String>naturalOrder());
	}

	public static void load(String dir, Comparator ord) throws IOException {
		class KlassFilter implements FilenameFilter {

			public boolean accept(File d, String f) {
				boolean accepted = f.endsWith(".class");
					
				if (f.equals("loader.class")) {
					accepted = false;
				}
				if (f.contains("$")) {
					accepted = false;
				}
				
				return accepted;
			}

		}
		File fdir = new File(dir);
		if (fdir.isDirectory() == false) throw new IOException(fdir.toString() + " is not a directory!");
		String[] files = fdir.list(new KlassFilter());
		Arrays.sort(files, ord);
		for (String filename : files) {
			try {
				String[] klassname = filename.split("[.]", 2);

				Class<?> klass = ClassLoader.getSystemClassLoader().loadClass(klassname[0]);
					try {
						Runnable rable = (Runnable)klass.newInstance();
						if (rable instanceof Runnable) {
						rable.run();
						}
					}
					catch (InstantiationException I) {
						System.out.println(I.toString());
					}
					catch (IllegalAccessException I) {
						System.out.println(I.toString());
					}
					catch (ClassCastException C) {
						System.out.println(klassname[0]);
						System.out.println(C.toString());
					}


			}
			catch (ClassNotFoundException C) {
				System.out.println(C.toString());
			}
		}
	}

}
