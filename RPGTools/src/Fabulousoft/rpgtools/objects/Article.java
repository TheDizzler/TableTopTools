package fabulousoft.rpgtools.objects;

import android.util.Log;


public class Article implements Word {

	public String baseForm;
	
	public Article(String word) {
		
		baseForm = word;
//		Log.w("Article", "|" + baseForm + "|" );
	}
	
	
	@Override
	public String wordType() {
	
		return "Article";
	}
	
}
