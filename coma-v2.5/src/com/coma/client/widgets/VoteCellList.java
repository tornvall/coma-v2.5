package com.coma.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.coma.v2.Comav200;
import com.coma.v2.ModelInfo;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class VoteCellList {

	private static class MyDataProvider extends AsyncDataProvider<String> {

		@Override
		protected void onRangeChanged(HasData<String> display) {
			// Get the new range.
			final Range range = display.getVisibleRange();

			/*
			 * Query the data asynchronously. If you are using a database, you can
			 * make an RPC call here. We'll use a Timer to simulate a delay.
			 */
			int start = range.getStart();
			List<String> newData = new ArrayList<String>();

			for (ModelInfo modelInfo : getModelInfoList())
			{
				int modelID = modelInfo.getModelID();
				int modelCreator = modelInfo.getModelCreator();
				String modelName = modelInfo.getModelName();
				String modelCreationDate = modelInfo.getModelCreationDate();
				newData.add(modelID + "/" + "Creator: " + modelCreator + " Diagram name: " + modelName + " Diagram creation date " + modelCreationDate);
			}

			// Push the data to the displays. AsyncDataProvider will only update
			// displays that are within range of the data.
			updateRowData(start, newData);
		}
	}

	public static List<ModelInfo> modelInfoList;

	public CellList<String> votingPanel()
	{
		// Create a CellList.
		CellList<String> cellList = new CellList<String>(new TextCell());

		// Create a data provider.
		MyDataProvider dataProvider = new MyDataProvider();

        // Add the cellList to the dataProvider.
        dataProvider.addDataDisplay(cellList);

        // Create paging controls.
        SimplePager pager = new SimplePager();
        pager.setDisplay(cellList);

     // Add a selection model to handle user selection.
        final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
        cellList.setSelectionModel(selectionModel);

        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
          public void onSelectionChange(SelectionChangeEvent event) {
            String selected = selectionModel.getSelectedObject();
            if (selected != null) {
              String[] modelID = selected.split("/");
              //Window.alert("You selected: " + modelID[1] + "ModelID: " + modelID[0]); 
              Comav200.GetInstance().loadModelFromCellList(Integer.parseInt(modelID[0]));
              Comav200.GetInstance().setActiveModelID((Integer.parseInt(modelID[0])));
              }
          }
        });
        cellList.setWidth("100%");

		return cellList;
	}

	public static List<ModelInfo> getModelInfoList() {
		return modelInfoList;
	}

	public static void setModelInfoList(List<ModelInfo> modelInfoList) {
		
		VoteCellList.modelInfoList = modelInfoList;
	}
}


