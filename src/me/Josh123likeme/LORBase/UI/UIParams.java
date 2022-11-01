package me.Josh123likeme.LORBase.UI;

import java.util.ArrayList;
import java.util.List;

import me.Josh123likeme.LORBase.Types.Pair;

public class UIParams {

	@SuppressWarnings("rawtypes")
	List<Pair> params = new ArrayList<Pair>();
	
	public <T> void addParameter(String parameter, T value) {
		
		Pair<String, T> param = new Pair<String, T>(parameter, value);
		
		params.add(param);
		
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getParameter(String parameter) {
		
		for (int i = 0; i < params.size(); i++) {
			
			if (params.get(i).getFirst().equals(parameter)) {
				
				return (T) params.get(i).getLast();
				
			}
			
		}
		
		throw new IllegalArgumentException("that parameter doesnt exist");
		
	}
	
}
