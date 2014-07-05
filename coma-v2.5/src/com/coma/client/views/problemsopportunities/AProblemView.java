package com.coma.client.views.problemsopportunities;

import com.coma.client.models.ProblemImpact;

public abstract class AProblemView {
	public abstract void addImpact(ProblemImpact newImpact);	
	public abstract void addSelection(ProblemImpact newImpact);
	public abstract void refreshProblemImpactList();
}
