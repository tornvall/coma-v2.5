package com.coma.client.asisEvaluation;

import com.coma.client.Comav200;
import com.google.gwt.core.client.EntryPoint;

public class Main implements EntryPoint{

	@Override
	public void onModuleLoad() {		
		Comav200.GetInstance().initialize();		
	}

}
