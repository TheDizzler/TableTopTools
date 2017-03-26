package fabulousoft.rpgtools.objects;


public class Verb implements Word {
	

	private static final long	serialVersionUID	= -2831236665089268347L;
	public String	baseForm;
	public String	sForm;
	public String	ingForm;
	public String	pastForm;
	public String	pastParticleForm;
	
	
	public Verb(String wordString) {
	
		String[] words = wordString.split("/");
		baseForm = words[0];
		if (words.length > 1) {
			sForm = words[1];
			
		} else
			sForm = wordString + "s";
		
		
	}


	@Override
	public String wordType() {
	
		return "Verb";
	}
	
}
