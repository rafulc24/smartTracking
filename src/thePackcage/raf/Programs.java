package thePackcage.raf;


public class Programs {

	
	private String name;
	private long time;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long gettime() {
		return time;
	}
	public void settime(long time) {
		this.time = time;
	}
	public Programs(String name, long time) {
		
		this.name = name;
		this.time = time;
	}
	
}
