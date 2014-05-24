package com.coma.client.widgets;


import com.coma.v2.ActiveInvite;
import com.coma.v2.HandleGroups;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class HandleGroupInvitesDialogBox {

	Dialog dialog = new Dialog();
	
	public Dialog createDialogBox(CellList<String> cellList){
		// Create the popup dialog box
              
        dialog.setHeadingText("My group invites");
        dialog.setPixelSize(400, 400);
        dialog.setPredefinedButtons();
        
        //VerticalPanel dialogVPanel = new VerticalPanel();
        VerticalLayoutContainer dialogVPanel = new VerticalLayoutContainer();
        
        dialogVPanel.addStyleName("dialogVPanel");
       // dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
       
        ScrollPanel scrollPanel = new ScrollPanel(cellList);
		scrollPanel.setHeight("600px");
		scrollPanel.setWidth("600px");
		dialogVPanel.add(scrollPanel);
		dialog.setWidget(dialogVPanel);
        
        dialog.addButton(new TextButton("Accept invite", new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				new HandleGroups().acceptGroupInvite(ActiveInvite.getInstance().getGroupID(), ActiveInvite.getInstance().getInviteID());
                dialog.hide();
				
			}
			
		}));
        
		dialog.addButton(new TextButton("Decline invite", new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				new HandleGroups().declineGroupInvite(ActiveInvite.getInstance().getInviteID());
				dialog.hide();
			}
			
		}));
		
		dialog.addButton(new TextButton("Cancel", new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				dialog.hide();
				
			}
			
		}));
		
		
		
       
       
        return dialog;
}
}
