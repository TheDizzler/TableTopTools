package fabulousoft.rpgtools.objects;


public class Adjective implements Word {
	

	private static final long	serialVersionUID	= -5491296066292046960L;
	public String	baseForm;
	
	
	public Adjective(String adj) {
	
		baseForm = adj;
	}
	
	
	@Override
	public String toString() {
	
		return baseForm;
	}


	@Override
	public String wordType() {
	
		return "Adjective";
	}
}
