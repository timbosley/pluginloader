package patches.morepatches;



public class message implements Runnable {
final private String space = " ";

	public void run() {
		System.out.print(this.getClass().getName().toUpperCase() + space);
	}



}
