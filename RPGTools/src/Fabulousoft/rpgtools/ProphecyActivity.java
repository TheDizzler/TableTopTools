package Fabulousoft.rpgtools;


import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


class Noun {
	
	String	single;
	String	plural;
	
	
	public Noun(String wordString) {
	
		String[] words = wordString.split("/");
		single = words[0];
		if (words.length > 1)
			plural = words[1];
		else
			plural = single + "s";
//		Log.w("Noun:", wordString + " -> " + single + " + " + plural);
	}
}

class Verb {
	
	String	baseForm;
	String	sForm;
	String	ingForm;
	String	pastForm;
	String	pastParticleForm;
	
	
	public Verb(String wordString) {
	
		String[] words = wordString.split("/");
		baseForm = words[0];
		if (words.length > 1) {
			sForm = words[1];
			
		} else
			sForm = wordString + "s";
		
		
	}
	
}



class Prophecy {
	
	/** Full example:
	 * 		(CAUSE)		[When] the [gray] [wizard] of [merry] [sadness] [comes],
	 * 					 conj		adj		noun		adj		noun	 verb
	 * 		(RESULT)	*/
	private String				fullProphecy;
	
	
	/* Example sentence:
	 * [Conjunction] the [primary adj][noun] of [noun] [verb]s [adverb]ally, the [adjective] [noun] will [verb].
	 */
	private String				conjunction				= "[Conjunction]";
	private String				optPrimaryAdjective		= "[adjective]";
	private Noun				primaryNoun				= new Noun("[noun]");
	private String				optSecondaryAdjective	= "[adjective]";
	private Noun				optSecondaryNoun		= new Noun("[noun]");
	private Verb				primaryVerb				= new Verb("[verb]");
	
	Random						rand					= new Random();
	
	private ArrayList<String>	conjunctionList			= new ArrayList<String>();
	private ArrayList<String>	conjunctionSpecialList	= new ArrayList<String>();
	private ArrayList<Noun>		nounCommonList			= new ArrayList<Noun>();
	private ArrayList<Noun>		nounProperList			= new ArrayList<Noun>();
	private ArrayList<Verb>		verbList				= new ArrayList<Verb>();
	private ArrayList<String>	adjectiveList			= new ArrayList<String>();
	
	
	
	TextView					propheticText;
	Spinner						spinnerConj;
	CheckBox					checkBoxConjRand;
	ToggleButton				toggleBtnPrimaryAdj;
	ToggleButton				toggleBtnPrimaryNounPlural;
	ToggleButton				toggleBtnPrimaryNounProper;
	ToggleButton				toggleBtnSecondaryAdj;
	ToggleButton				toggleBtnSecondaryNoun;
	
	
	public Prophecy(Activity activity) {
	
		try {
			
			InputStream is = activity.getAssets().open("WordList.xml");
			Document xmlDoc = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(is);
			
			is.close();
			
			Element rootNode = xmlDoc.getDocumentElement();
			rootNode.normalize();
			
			Element conjunctionsNode = (Element) rootNode.getElementsByTagName("conjunctions").item(0);
			
			Element normalNode = (Element) conjunctionsNode.getElementsByTagName("normal").item(0);
			String newWords = normalNode.getTextContent().trim();
			for (String word : newWords.split("\\s+")) {
				conjunctionList.add(word);
			}
			
			Element specialConjNode = (Element) conjunctionsNode
				.getElementsByTagName("special").item(0);
			newWords = specialConjNode.getTextContent().trim();
			for (String word : newWords.split("\\s+")) {
				conjunctionSpecialList.add(word);
			}
			
			Element nounsNode = (Element) rootNode.getElementsByTagName("nouns").item(0);
			Element commonNounNode = (Element) nounsNode.getElementsByTagName("common").item(0);
			newWords = commonNounNode.getTextContent().trim();
			for (String word : newWords.split("\\s{2,}")) {
				nounCommonList.add(new Noun(word));
			}
			
			Element properNounNode = (Element) nounsNode.getElementsByTagName("proper").item(0);
			newWords = properNounNode.getTextContent().trim();
			for (String word : newWords.split("\\s{2,}")) {
				nounProperList.add(new Noun(word));
//				Log.e("Check", word + " WOW");
			}
			
			Element verbsNode = (Element) rootNode.getElementsByTagName("verbs").item(0);
			newWords = verbsNode.getTextContent().trim();
			for (String word : newWords.split("\\s{2,}")) {
				verbList.add(new Verb(word));
			}
			
			
			Element adjNode = (Element) rootNode.getElementsByTagName("adjectives").item(0);
			newWords = adjNode.getTextContent().trim();
			for (String word : newWords.split("\\s+")) {
				adjectiveList.add(word);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
		} catch (SAXException e) {
			e.printStackTrace();
			Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
		}
		
		initComponents(activity);
		
	}
	
	
	private void initComponents(Activity activity) {
	
		propheticText = (TextView) activity.findViewById(R.id.textview_prophetic);
		
		checkBoxConjRand = (CheckBox) activity.findViewById(R.id.checkBox_conjRand);
		spinnerConj = (Spinner) activity.findViewById(R.id.spinner_Conjunctions);
		toggleBtnPrimaryAdj = (ToggleButton) activity.findViewById(R.id.toggleBtn_primaryAdjective);
		toggleBtnPrimaryNounPlural = (ToggleButton) activity.findViewById(R.id.toggleBtn_primaryNounPlural);
		toggleBtnPrimaryNounProper = (ToggleButton) activity.findViewById(R.id.toggleBtn_primaryNounProper);
		toggleBtnSecondaryNoun = (ToggleButton) activity.findViewById(R.id.toggleBtn_secondaryNoun);
		toggleBtnSecondaryAdj = (ToggleButton) activity.findViewById(R.id.toggleBtn_secondaryAdjective);
		
		
		checkBoxConjRand.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			boolean	firstTime	= true;
			
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			
				if (!firstTime) {
					if (checkBoxConjRand.isChecked())
						conjunction = conjunctionList.get(rand.nextInt(conjunctionList.size()));
					else
						conjunction = (String) spinnerConj.getSelectedItem();
					
					reconstructProphecy();
				} else
					firstTime = false;
				
				spinnerConj.setEnabled(!checkBoxConjRand.isChecked());
			}
		});
		checkBoxConjRand.setChecked(true);
		
		ArrayAdapter<String> conjAdap = new ArrayAdapter<String>(activity,
			android.R.layout.simple_spinner_dropdown_item, conjunctionList);
		spinnerConj.setAdapter(conjAdap);
		spinnerConj.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			boolean	firstTime	= true;
			
			
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			
				if (!firstTime)
					conjunction = (String) spinnerConj.getSelectedItem();
				else
					firstTime = false;
				reconstructProphecy();
			}
			
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			
			}
		});
		
		toggleBtnPrimaryAdj.setChecked(true);
		toggleBtnPrimaryAdj.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				optPrimaryAdjective = adjectiveList.get(rand.nextInt(adjectiveList.size()));
				reconstructProphecy();
			}
		});
		
		toggleBtnPrimaryNounPlural.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				
				reconstructProphecy();
			}
		});
		
		toggleBtnPrimaryNounProper.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				if (toggleBtnPrimaryNounProper.isChecked())
					primaryNoun = nounProperList.get(rand.nextInt(nounProperList.size()));
				else
					primaryNoun = nounCommonList.get(rand.nextInt(nounCommonList.size()));
				reconstructProphecy();
			}
			
			
		});
		
		toggleBtnSecondaryAdj.setChecked(true);
		toggleBtnSecondaryAdj.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				if (toggleBtnSecondaryAdj.isChecked()) {
					optSecondaryAdjective = adjectiveList.get(rand.nextInt(adjectiveList.size()));
				}
				reconstructProphecy();
			}
			
			
		});
		
		toggleBtnSecondaryNoun.setChecked(true);
		toggleBtnSecondaryNoun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				if (toggleBtnSecondaryNoun.isChecked())
					optSecondaryNoun = nounCommonList.get(rand.nextInt(nounCommonList.size()));
				toggleBtnSecondaryAdj.setEnabled(toggleBtnSecondaryNoun.isChecked());
				
				reconstructProphecy();
			}
			
			
		});
	}
	
	
	public void generateFullProphecy() {
	
		
		if (checkBoxConjRand.isChecked())
			conjunction = conjunctionList.get(rand.nextInt(conjunctionList.size()));
		else
			conjunction = (String) spinnerConj.getSelectedItem();
		
		optPrimaryAdjective = adjectiveList.get(rand.nextInt(adjectiveList.size()));
		
		if (toggleBtnPrimaryNounProper.isChecked())
			primaryNoun = nounProperList.get(rand.nextInt(nounProperList.size()));
		else
			primaryNoun = nounCommonList.get(rand.nextInt(nounCommonList.size()));
		
		optSecondaryAdjective = adjectiveList.get(rand.nextInt(adjectiveList.size()));
		optSecondaryNoun = nounCommonList.get(rand.nextInt(nounCommonList.size()));
		
		primaryVerb = verbList.get(rand.nextInt(verbList.size()));
		
		reconstructProphecy();
	}
	
	
	public void reconstructProphecy() {
	
		reconstructCause();
		
		
		propheticText.setText(fullProphecy);
	};
	
	
	private void reconstructCause() {
		
		fullProphecy = firstCharToUpper(conjunction);
		fullProphecy += " ";
		if (toggleBtnPrimaryNounProper.isChecked())
			fullProphecy += "The ";
		else
			fullProphecy += "the ";
		
		propheticText.setText(fullProphecy);
		if (toggleBtnPrimaryAdj.isChecked()) {
			if (toggleBtnPrimaryNounProper.isChecked())
				fullProphecy += firstCharToUpper(optPrimaryAdjective) + " ";
			else
				fullProphecy += optPrimaryAdjective + " ";
		}
		
		
		if (toggleBtnPrimaryNounPlural.isChecked())
			fullProphecy += primaryNoun.plural + " ";
		else
			fullProphecy += primaryNoun.single + " ";
		
		
		
		if (toggleBtnSecondaryNoun.isChecked()) {
			fullProphecy += "of ";
			
			if (toggleBtnSecondaryAdj.isChecked())
				fullProphecy += optSecondaryAdjective + " ";
			
			fullProphecy += optSecondaryNoun.single + " ";
		}
		
		
		
		
		if (toggleBtnPrimaryNounPlural.isChecked())
			fullProphecy += primaryVerb.baseForm;
		else
			fullProphecy += primaryVerb.sForm;
	
	}


	public static String firstCharToUpper(String change) {
	
		return change.substring(0, 1).toUpperCase() + change.substring(1);
	}
	
	
}

public class ProphecyActivity extends Activity {
	
	
	Prophecy	prophecy;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prophecy);
		
		prophecy = new Prophecy(this);
		
	}
	
	
	private void generateRandomProphecy() {
	
		prophecy.generateFullProphecy();
	}
	
	
	public void generateProphecy(View v) {
	
		generateRandomProphecy();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.prophecy, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
