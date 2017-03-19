package fabulousoft.rpgtools;


import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

//import fabulousoft.rpgtools.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;


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
	 * 					 conj		adj		noun	  2nd adj  2nd noun	 verb
	 * 		(RESULT)	the [gelatinous] [princess] shall [mistakenly] [venture].
	 * 						  3rd adj	  3rd noun			adverb		 2nd verb
	 * */
	private String				fullProphecy;
	
	
	/* Example sentence:
	 * [Conjunction] the [primary adj][noun] of [noun] [verb]s [adverb]ally, the [adjective] [noun] will [verb].
	 */
	private String				conjunction				= "[Conjunction]";
	private String				causePrimaryAdjective	= "[adjective]";
	private Noun				causePrimaryNoun		= new Noun("[noun]");
	private String				causeSecondaryAdjective	= "[adjective]";
	private Noun				causeSecondaryNoun		= new Noun("[noun]");
	private Verb				causeVerb				= new Verb("[verb]");
	
	private String				resultAdjective			= "[adjective]";
	private Noun				resultNoun				= new Noun("[noun]");
	private String				adverb					= "[adverb]ally";
	private Verb				resultVerb				= new Verb("[verb]");
	
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
			
				causePrimaryAdjective = adjectiveList.get(rand.nextInt(adjectiveList.size()));
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
					causePrimaryNoun = nounProperList.get(rand.nextInt(nounProperList.size()));
				else
					causePrimaryNoun = nounCommonList.get(rand.nextInt(nounCommonList.size()));
				reconstructProphecy();
			}
			
			
		});
		
		toggleBtnSecondaryAdj.setChecked(true);
		toggleBtnSecondaryAdj.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				if (toggleBtnSecondaryAdj.isChecked()) {
					causeSecondaryAdjective = adjectiveList.get(rand.nextInt(adjectiveList.size()));
				}
				reconstructProphecy();
			}
			
			
		});
		
		toggleBtnSecondaryNoun.setChecked(true);
		toggleBtnSecondaryNoun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				if (toggleBtnSecondaryNoun.isChecked())
					causeSecondaryNoun = nounCommonList.get(rand.nextInt(nounCommonList.size()));
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
		
		causePrimaryAdjective = adjectiveList.get(rand.nextInt(adjectiveList.size()));
		
		if (toggleBtnPrimaryNounProper.isChecked())
			causePrimaryNoun = nounProperList.get(rand.nextInt(nounProperList.size()));
		else
			causePrimaryNoun = nounCommonList.get(rand.nextInt(nounCommonList.size()));
		
		causeSecondaryAdjective = adjectiveList.get(rand.nextInt(adjectiveList.size()));
		causeSecondaryNoun = nounCommonList.get(rand.nextInt(nounCommonList.size()));
		
		causeVerb = verbList.get(rand.nextInt(verbList.size()));
		
		reconstructProphecy();
	}
	
	
	public void reconstructProphecy() {
	
		reconstructCause();
		reconstrucResult();
		
		propheticText.setText(fullProphecy);
	};
	
	
	private void reconstrucResult() {
	
		fullProphecy += "the ";
		
		fullProphecy += resultAdjective + " ";
		fullProphecy += resultNoun.single + " ";
		fullProphecy += "shall ";
		fullProphecy += resultVerb.baseForm + " ";
		fullProphecy += adverb;
		fullProphecy += ".";
		
	}
	
	
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
				fullProphecy += firstCharToUpper(causePrimaryAdjective) + " ";
			else
				fullProphecy += causePrimaryAdjective + " ";
		}
		
		
		if (toggleBtnPrimaryNounPlural.isChecked())
			fullProphecy += causePrimaryNoun.plural + " ";
		else
			fullProphecy += causePrimaryNoun.single + " ";
		
		
		
		if (toggleBtnSecondaryNoun.isChecked()) {
			fullProphecy += "of ";
			
			if (toggleBtnSecondaryAdj.isChecked())
				fullProphecy += causeSecondaryAdjective + " ";
			
			fullProphecy += causeSecondaryNoun.single + " ";
		}
		
		
		
		
		if (toggleBtnPrimaryNounPlural.isChecked())
			fullProphecy += causeVerb.baseForm;
		else
			fullProphecy += causeVerb.sForm;
		
		fullProphecy += ", ";
	}
	
	
	public static String firstCharToUpper(String change) {
	
		return change.substring(0, 1).toUpperCase() + change.substring(1);
	}
	
	
}



public class ProphecyActivity extends Activity {
	
	
	Prophecy	prophecy;
	TabHost		tabHost;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prophecy);
		
		prophecy = new Prophecy(this);
		
		
		tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			View	currentView;
			View	previousView;
			int		previousTab;
			
			
			@Override
			public void onTabChanged(String arg0) {
			
				if (previousView == null)
					previousView = tabHost.getCurrentView();
				
				currentView = tabHost.getCurrentView();
				
				if (tabHost.getCurrentTab() > previousTab) {
					previousView.startAnimation(slideToLeftAnimation());
					currentView.startAnimation(slideFromRightAnimation());
					Log.w("Halp", "GO UP");
				} else {
					previousView.startAnimation(slideToRightAnimation());
					currentView.startAnimation(slideFromLeftAnimation());
					Log.w("Halp", "GO DOWN");
				}
				previousView = currentView;
				previousTab = tabHost.getCurrentTab();
				
			}
			
			
		});
		
		TabHost.TabSpec tabSpec = tabHost.newTabSpec("Tab One");
		tabSpec.setContent(R.id.tab1);
		tabSpec.setIndicator("Cause");
		tabHost.addTab(tabSpec);
		
		tabSpec = tabHost.newTabSpec("Result");
		tabSpec.setContent(R.id.tab2);
		tabSpec.setIndicator("Result");
		tabHost.addTab(tabSpec);
		
//		currentTab = tabHost.getCurrentTab();
	}
	
	
	private Animation slideToLeftAnimation() {
	
		Animation slideOutRight = new TranslateAnimation(
			Animation.RELATIVE_TO_PARENT, 0,
			Animation.RELATIVE_TO_PARENT, -1,
			Animation.RELATIVE_TO_PARENT, 0,
			Animation.RELATIVE_TO_PARENT, 0);
		slideOutRight.setDuration(250);
		slideOutRight.setInterpolator(new AccelerateInterpolator());
		
		return slideOutRight;
	}
	
	
	private Animation slideFromLeftAnimation() {
	
		Animation slideFromRight = new TranslateAnimation(
			Animation.RELATIVE_TO_PARENT, -1,
			Animation.RELATIVE_TO_PARENT, 0,
			Animation.RELATIVE_TO_PARENT, 0,
			Animation.RELATIVE_TO_PARENT, 0);
		slideFromRight.setDuration(250);
		slideFromRight.setInterpolator(new AccelerateInterpolator());
		
		
		return slideFromRight;
	}
	
	
	private Animation slideToRightAnimation() {
	
		Animation slideOutRight = new TranslateAnimation(
			Animation.RELATIVE_TO_PARENT, 0,
			Animation.RELATIVE_TO_PARENT, 1,
			Animation.RELATIVE_TO_PARENT, 0,
			Animation.RELATIVE_TO_PARENT, 0);
		slideOutRight.setDuration(250);
		slideOutRight.setInterpolator(new AccelerateInterpolator());
		
		return slideOutRight;
	}
	
	
	private Animation slideFromRightAnimation() {
	
		Animation slideFromRight = new TranslateAnimation(
			Animation.RELATIVE_TO_PARENT, 1,
			Animation.RELATIVE_TO_PARENT, 0,
			Animation.RELATIVE_TO_PARENT, 0,
			Animation.RELATIVE_TO_PARENT, 0);
		slideFromRight.setDuration(250);
		slideFromRight.setInterpolator(new AccelerateInterpolator());
		
		
		return slideFromRight;
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
