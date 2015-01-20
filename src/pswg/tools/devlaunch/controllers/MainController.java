package pswg.tools.devlaunch.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pswg.tools.devlaunch.models.MainModel;
import pswg.tools.devlaunch.views.MainView;
import pswg.tools.devlaunch.views.ProfilesView;

public class MainController implements ActionListener {

	private MainModel model;
	private MainView view;
	
	private ProfilesView profilesDialog;
	
	public MainController(MainModel model, MainView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
		case "PROFILES": showProfilesDialog(); break;
		case "OPTIONS": showOptionsDialog(); break;
		case "PLAY": launchGame(); break;
		default: System.out.println("Unsure how to handle Action: " + arg0.getActionCommand());
		}
	}
	
	public void launchGame() {
		
	}
	
	public void showOptionsDialog() {
		
	}
	
	public void showProfilesDialog() {
		if (profilesDialog == null)
			createProfilesDialog();

		profilesDialog.setVisible(true);
	}
	
	public void updateProfiles() {
		view.updateProfileSelections(model.getProfiles(), model.getActiveProfile());
	}
	
	private void createProfilesDialog() {
		profilesDialog = new ProfilesView(model);
		ProfilesController controller = new ProfilesController(model, profilesDialog, this);
		profilesDialog.addController(controller);
	}
}
