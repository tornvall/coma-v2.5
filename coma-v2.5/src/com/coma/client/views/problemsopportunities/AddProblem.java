package com.coma.client.views.problemsopportunities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.internal.compiler.problem.ProblemSeverities;

import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.LoadModel2;
import com.coma.client.SaveModel2;
import com.coma.client.classes.Benefit;
import com.coma.client.classes.ProblemClass;
import com.coma.client.classes.ProblemEvolution;
import com.coma.client.classes.ProblemImpact;
import com.coma.client.classes.ProblemOccurence;
import com.coma.client.classes.ProblemSeverity;
import com.coma.client.classes.ProblemUrgency;
import com.coma.client.classes.User;
import com.coma.client.helpers.Settings;
import com.coma.client.widgets.MessageFrame;
import com.coma.client.widgets.v25.NewProblemImpactDialogBox;
import com.coma.v2.ModelInfo;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.rebind.rpc.ProblemReport.Problem;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.info.Info;

public class AddProblem {

	private MessageFrame oryxFrame = null;
	private Panel viewPanel = null;
	private CellTable<ProblemImpact> cellTable;
	private List<ProblemImpact> problemImpactList;
	private SimpleComboBox<Benefit> benefitComboBox;
	private AddProblem instance = null;
	private SelectionModel<ProblemImpact> selectionModel = null;
	private int nextImpactId = 0;
	
	//Input
	private TextBox problemNameTextBox;
	private TextArea problemDescTextBox;
	private ListBox problemSeverityListBox;
	private ListBox problemEvolutionListBox;
	private ListBox problemUrgencyListBox;
	private ListBox problemOccurenceListBox;
	private TextArea problemExplanationTextBox;
	
	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);	
	
	public AddProblem(){		
	}	
	
	public Panel getNewView(){				
		this.viewPanel = initAddProblemView();
		
		return this.viewPanel;
	}		
	
	public void addImpact(ProblemImpact impact){
		impact.setUniqueId(nextImpactId);
		nextImpactId++;
		
		this.problemImpactList.add(impact);
	}
	
	public void addSelection(ProblemImpact impact){
		selectionModel.setSelected(impact, true);
	}
	
	private Panel initAddProblemView(){				
		instance = this;
		VerticalPanel panel = new VerticalPanel();
		HorizontalPanel headerPanel = new HorizontalPanel();
		HorizontalPanel mainPanel = new HorizontalPanel();				

		headerPanel.add(new Label("Enter problem"));		
				
		mainPanel.add(problemSettings());		
		this.initializeOryxFrame();
		mainPanel.add(oryxFrame);	
		oryxFrame.setVisible(true);
		//Loads latest activegroupmodel
		//new LoadModel2().getActiveGroupModelFromDatabase(oryxFrame);
		//Loads original
		new LoadModel2().getModelFromDatabase(Settings.groupModelId, oryxFrame);
		
		panel.add(headerPanel);
		panel.add(mainPanel);
		
		return panel;
	}
	
	private Panel problemSettings(){
		VerticalPanel panel = new VerticalPanel();
		panel.setWidth("300px");
		
		//Name
		HorizontalPanel problemNameHorizontalPanel = new HorizontalPanel();
		Label problemNameLabel = new Label("Problem name*:");
		problemNameTextBox = new TextBox();
		problemNameHorizontalPanel.add(problemNameLabel);
		problemNameHorizontalPanel.add(problemNameTextBox);
		
		//Description
		HorizontalPanel problemDescHorizontalPanel = new HorizontalPanel();
		Label problemDescLabel = new Label("Description*:");
		problemDescTextBox = new TextArea();
		problemDescHorizontalPanel.add(problemDescLabel);
		problemDescHorizontalPanel.add(problemDescTextBox);
		
		//Explain how to select problem area

		//Severity
		HorizontalPanel problemSeverityHorizontalPanel = new HorizontalPanel();
		Label problemSeverityLabel = new Label("Severity:");
		problemSeverityListBox = new ListBox();
		for(ProblemSeverity severity: ProblemSeverity.values()){
			problemSeverityListBox.addItem(severity.toString());
		}
		problemSeverityHorizontalPanel.add(problemSeverityLabel);
		problemSeverityHorizontalPanel.add(problemSeverityListBox);
		
		//Evolution
		HorizontalPanel problemEvolutionHorizontalPanel = new HorizontalPanel();
		Label problemEvolutionLabel = new Label("Evolution:");
		problemEvolutionListBox = new ListBox();
		for(ProblemEvolution evolution: ProblemEvolution.values()){
			problemEvolutionListBox.addItem(evolution.toString());
		}
		problemEvolutionHorizontalPanel.add(problemEvolutionLabel);
		problemEvolutionHorizontalPanel.add(problemEvolutionListBox);
		
		//Urgency
		HorizontalPanel problemUrgencyHorizontalPanel = new HorizontalPanel();
		Label problemUrgencyLabel = new Label("Urgency:");
		problemUrgencyListBox = new ListBox();
		for(ProblemUrgency urgency: ProblemUrgency.values()){
			problemUrgencyListBox.addItem(urgency.toString());
		}
		problemUrgencyHorizontalPanel.add(problemUrgencyLabel);
		problemUrgencyHorizontalPanel.add(problemUrgencyListBox);
		
		//Occurence
		HorizontalPanel problemOccurenceHorizontalPanel = new HorizontalPanel();
		Label problemOccurenceLabel = new Label("Occurence:");
		problemOccurenceListBox = new ListBox();
		for(ProblemOccurence occurence: ProblemOccurence.values()){
			problemOccurenceListBox.addItem(occurence.toString());
		}
		problemOccurenceHorizontalPanel.add(problemOccurenceLabel);
		problemOccurenceHorizontalPanel.add(problemOccurenceListBox);
		
		//Explanation
		HorizontalPanel problemExplanationHorizontalPanel = new HorizontalPanel();
		Label problemExplanationLabel = new Label("Explanation:");
		problemExplanationTextBox = new TextArea();		
		problemExplanationHorizontalPanel.add(problemExplanationLabel);
		problemExplanationHorizontalPanel.add(problemExplanationTextBox);
		
		//Impact		
		VerticalPanel problemImpactPanel = this.createImpactPanel();
		
		//Add Impact
		HorizontalPanel addProblemImpactPanel = this.createAddImpactPanel();
		
		//Save
		TextButton saveProblemButton = new TextButton("Save problem");	
		saveProblemButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				new SaveModel2().saveProblem(oryxFrame, createProblem());
			}
		});	
		
		//Add all panels
		panel.add(problemNameHorizontalPanel);
		panel.add(problemDescHorizontalPanel);
		panel.add(problemSeverityHorizontalPanel);
		panel.add(problemEvolutionHorizontalPanel);
		panel.add(problemUrgencyHorizontalPanel);
		panel.add(problemOccurenceHorizontalPanel);
		panel.add(problemExplanationHorizontalPanel);		
		panel.add(problemImpactPanel);
		panel.add(addProblemImpactPanel);
		panel.add(saveProblemButton);
		
		return panel;
	}
	
	private VerticalPanel createImpactPanel(){
		VerticalPanel impactPanel = new VerticalPanel();
		Label impactLabel = new Label("Impacts of problem on organization"); 		

		cellTable = new CellTable<ProblemImpact>();
		cellTable.setWidth("100%", true);
		cellTable.setAutoHeaderRefreshDisabled(true);
		
		problemImpactList = new ArrayList<ProblemImpact>();
		
		//Selection model
		selectionModel = new MultiSelectionModel<ProblemImpact>(new ProvidesKey<ProblemImpact>() {
		    @Override
		    public Object getKey(ProblemImpact item) {		    	
				return item.getUniqueId();
		    }
		});		
		cellTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<ProblemImpact> createCheckboxManager());			
		
		//Create Benefit column
		TextColumn<ProblemImpact> benefitColumn = new TextColumn<ProblemImpact>() {
		      @Override
		      public String getValue(ProblemImpact object) {
		        return object.getBenefitName();
		      }
		    };
		cellTable.addColumn(benefitColumn, "Benefit");
		
		//Create Impact description column
		TextColumn<ProblemImpact> impactColumn = new TextColumn<ProblemImpact>() {
		      @Override
		      public String getValue(ProblemImpact object) {
		        return object.getImpact();
		      }
		    };
		cellTable.addColumn(impactColumn, "Impact on benefit");			
		
		//Check column
		Column<ProblemImpact, Boolean> checkColumn = new Column<ProblemImpact, Boolean>(new CheckboxCell(true, false)) {
		      @Override
		      public Boolean getValue(ProblemImpact object) {
		        // Get the value from the selection model.
		    	  return selectionModel.isSelected(object);
		      }
		};		    		
		cellTable.addColumn(checkColumn);
		cellTable.setColumnWidth(checkColumn, 40, Unit.PX);
		
		//Fetch data
		this.fetchProblemImpactList();
		
		//Add all elements
		impactPanel.add(impactLabel);
		impactPanel.add(cellTable);
		
		return impactPanel;
	}	
	
	private HorizontalPanel createAddImpactPanel() {
		HorizontalPanel addImpactPanel = new HorizontalPanel();

		Label addImpactLabel = new Label("Add Impact:");
		
		final LabelProvider<Benefit> lb = new LabelProvider<Benefit>() {
            @Override
            public String getLabel(Benefit item) {
                return item.getDescription();
            }
        };
		
		benefitComboBox = new SimpleComboBox<Benefit>(lb);
		benefitComboBox.setForceSelection(true);
		benefitComboBox.setEmptyText("Select a benefit");
		
		//Fetch data
		this.fetchBenefits();		
		
		benefitComboBox.addSelectionHandler(new SelectionHandler<Benefit>() {
		      @Override
		      public void onSelection(SelectionEvent<Benefit> event) {
		        NewProblemImpactDialogBox npidb = new NewProblemImpactDialogBox(
		        		event.getSelectedItem().getID(),
		        		event.getSelectedItem().getDescription(),
		        		instance);		        
		      }
		});		
		
		addImpactPanel.add(addImpactLabel);
		addImpactPanel.add(benefitComboBox);
		
		return addImpactPanel;
	}
	
	public void fetchProblemImpactList(){
		//Get impacts for problem
		databaseConnection.getProblemImpacts(Settings.problemId, new AsyncCallback<List<ProblemImpact>>() {		
			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<ProblemImpact> result) {
				//Add impacts and generate uniqueId for selection
				for (ProblemImpact impact:result) {
					addImpact(impact);
				}
				
				//Refresh list and push data
				refreshProblemImpactList();
				
				//Set selected (requires uniqueId)
				for (ProblemImpact impact:result) {
					if(impact.getIsActive().equals(true)){
						selectionModel.setSelected(impact, true);
					}
				}
			}
		});	
	}
	
	public void refreshProblemImpactList(){
		//For paging calculations
	    cellTable.setRowCount(problemImpactList.size(), true);

	    //Push data to widget
	    cellTable.setRowData(0, problemImpactList);
	}
	
	private void fetchBenefits(){
		databaseConnection.getAllBenefits(new AsyncCallback<List<Benefit>>() {		
			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<Benefit> result) {				
				for (Benefit benefit : result) {
					benefitComboBox.add(benefit);
				}
			}
		});	
	}
	
	private ProblemClass createProblem(){
		ProblemClass problem = new ProblemClass(
				this.problemNameTextBox.getText(),
				this.problemDescTextBox.getText(),
				ProblemSeverity.fromString(problemSeverityListBox.getItemText(problemSeverityListBox.getSelectedIndex())),
				ProblemEvolution.fromString(problemEvolutionListBox.getItemText(problemEvolutionListBox.getSelectedIndex())),
				ProblemUrgency.fromString(problemUrgencyListBox.getItemText(problemUrgencyListBox.getSelectedIndex())),
				ProblemOccurence.fromString(problemOccurenceListBox.getItemText(problemOccurenceListBox.getSelectedIndex())),
				this.problemExplanationTextBox.getText(),
				problemImpactList);
		
		return problem;		
	}
	
	private void initializeOryxFrame() {
		oryxFrame = new MessageFrame("oryxFrame");
		oryxFrame.init();
		oryxFrame.setUrl(Settings.oryxFrameURL);
		oryxFrame.setHeight(Settings.oryxFrameHeight);
		oryxFrame.setWidth(Settings.oryxFrameWidth);
	}
}
