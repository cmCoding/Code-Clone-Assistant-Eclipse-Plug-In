package ecl_dummy_plugin.handlers;

public class Clone {
	//Attributes
	private String file;
	private int startline;
	private int endline;
	private int pcid;
	private int class_id;
	
	//Constructors
	public Clone() {
		this.file = null;
		this.startline = 0;
		this.endline = 0;
		this.pcid = 0;
		this.class_id = 0;
	}
	
	public Clone(String f, int sl, int el, int id, int classid) {
		this.file = f;
		this.startline = sl;
		this.endline = el;
		this.pcid = id;
		this.class_id = classid;
	}
	
	//Accessors and Mutators
	public String getFile() {
		return this.file;
	}
	
	public void setFile(String f) {
		this.file = f;
	}
	
	public int getStartline() {
		return this.startline;
	}
	
	public void setStartline(int sl) {
		this.startline = sl;
	}
	
	public int getEndline() {
		return this.endline;
	}
	
	public void setEndline(int el) {
		this.endline = el;
	}
	
	public int getPcid() {
		return this.pcid;
	}
	
	public void setPcid(int pid) {
		this.pcid = pid;
	}
	
	public int getClassId() {
		return this.class_id;
	}
	
	public void setClassId(int cid) {
		this.class_id = cid;
	}
	
	public String toString() {
		return "File Name: "+ this.file + "\n" +
			   "Startline: "+ this.startline + "\n" +
			   "Endline: "+ this.endline + "\n" +
			   "PCID: "+ this.pcid + "\n" +
			   "Class ID: "+ this.class_id + "\n\n";
	}
	
}
