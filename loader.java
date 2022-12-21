import java.io.*;
import java.lang.reflect.*;


public class loader {


	public static void main(String[] arg) {

	load(".");
	}

	public static void load() {
	
	load(/*whatever is the working directory variable*/);
	}

	public static void load(String dir) {
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
		try {
			File fdir = new File(dir);
			File[] patches = fdir.listFiles(new KlassFilter());
			for (File patch : patches) {
				try {
					String filename = patch.getName();
					String[] klassname = filename.split("[.]", 2);

					Class<?> klass = ClassLoader.getSystemClassLoader().loadClass(klassname[0]);
						try {
							Runnable rable = (Runnable)klass.newInstance();
							if (rable instanceof Runnable) {
							rable.run();
							}
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
		catch (Exception I) {
			System.out.println(I.toString());
		
		}
	}

}
