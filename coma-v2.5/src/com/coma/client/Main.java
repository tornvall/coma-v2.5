package com.coma.client;

import com.google.gwt.core.client.EntryPoint;

public class Main implements EntryPoint{

	@Override
	public void onModuleLoad() {		
		Comav25.GetInstance().initialize();		
	}

}
