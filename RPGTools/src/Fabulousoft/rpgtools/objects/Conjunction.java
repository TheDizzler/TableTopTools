package fabulousoft.rpgtools.objects;


public class Conjunction implements Word {
	
	public String	baseForm;
	
	
	public Conjunction(String conj) {
	
		baseForm = conj;
	}
	
	
	@Override
	public String toString() {
	
		return baseForm;
	}


	@Override
	public String wordType() {
	
		return "Conjunction";
	}
	
}
