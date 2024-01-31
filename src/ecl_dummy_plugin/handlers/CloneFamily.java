package ecl_dummy_plugin.handlers;

public class CloneFamily {
	//Attributes
	private int id;
	private int population;
	private int length;
	private float similarity;
	
	
	
	//Constructors
	public CloneFamily() {
		this.id = 0;
		this.population = 0;
		this.length = 0;
		this.similarity = (float) 0.0;
	}
	
	public CloneFamily(int i, int pop, int l, float simil) {
		this.id = i;
		this.population = pop;
		this.length = l;
		this.similarity = simil;
	}
	
	//Accessors and Mutators
	public int getID() {
		return this.id;
	}
	
	public void setID(int i) {
		this.id = i;
	}
	
	public int getPopulation() {
		return this.population;
	}
	
	public void setPopulation(int pop) {
		this.population = pop;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public void setLength(int l) {
		this.length = l;
	}
	
	public float getSimilarity() {
		return this.similarity;
	}
	
	public void setSimilarity(float simil) {
		this.similarity = simil;
	}
	
	
	public String toString() {
		return "Class ID: "+this.id+"\n"+
			   "Population: "+this.population+"\n"+
			   "Length: "+this.length+"\n"+
			   "Similarity: "+String.valueOf(this.similarity)+"\n";
	}
	
	
}
