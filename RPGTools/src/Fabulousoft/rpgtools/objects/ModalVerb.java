package fabulousoft.rpgtools.objects;


public class ModalVerb implements Word {
	
	public String baseForm;
	
	public ModalVerb(String wordString) {
	
		baseForm = wordString;
	}
	
	
	@Override
	public String wordType() {
	
		return "Modal Verb";
	}
	
}