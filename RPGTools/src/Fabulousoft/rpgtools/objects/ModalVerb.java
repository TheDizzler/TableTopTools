package fabulousoft.rpgtools.objects;


public class ModalVerb implements Word {
	

	private static final long	serialVersionUID	= -6446633978812065207L;
	public String baseForm;
	
	public ModalVerb(String wordString) {
	
		baseForm = wordString;
	}
	
	
	@Override
	public String wordType() {
	
		return "Modal Verb";
	}
	
}