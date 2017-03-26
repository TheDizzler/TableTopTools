package fabulousoft.rpgtools.objects;


public class Article implements Word {

	private static final long	serialVersionUID	= -1394021111966603772L;
	public String baseForm;
	
	public Article(String word) {
		
		baseForm = word;
	}
	
	
	@Override
	public String wordType() {
	
		return "Article";
	}
	
}
