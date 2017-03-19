package fabulousoft.rpgtools.fragments;

import fabulousoft.rpgtools.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ResultControlFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
								Bundle savedInstanceState) {
	
		View view;
		view = inflater.inflate(R.layout.fragment_result_controls, container, false);
		
		
		return view;
	}
	
}
