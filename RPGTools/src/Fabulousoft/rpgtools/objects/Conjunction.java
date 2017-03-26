package fabulousoft.rpgtools.objects;


public class Conjunction implements Word {
	
	private static final long	serialVersionUID	= 8527122176001147270L;
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
