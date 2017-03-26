package fabulousoft.rpgtools.objects;


public class Noun implements Word {
	
	private static final long	serialVersionUID	= 8356817319034746799L;
	public String	single;
	public String	plural;
	
	
	public Noun(String wordString) {
	
		String[] words = wordString.split("/");
		single = words[0];
		if (words.length > 1)
			plural = words[1];
		else
			plural = single + "s";
	}


	@Override
	public String wordType() {
	
		return "Noun";
	}
}