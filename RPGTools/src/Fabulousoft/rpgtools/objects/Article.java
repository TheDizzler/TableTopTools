package fabulousoft.rpgtools.objects;


public class Article implements Word {

	public String baseForm;
	
	public Article(String word) {
		
		baseForm = word;
	}
	
	
	@Override
	public String wordType() {
	
		return "Article";
	}
	
}
