package pswg.tools.devlaunch.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.List;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pswg.tools.devlaunch.DevLaunch;
import pswg.tools.devlaunch.models.ProfilesModel;
import pswg.tools.devlaunch.resources.LauncherProfile;
import pswg.tools.devlaunch.views.ProfilesView;

public class ProfilesController extends WindowAdapter implements ActionListener, ListSelectionListener{

	private ProfilesModel model;
	private ProfilesView view;
	
	public ProfilesController(ProfilesModel model, ProfilesView view) {
		this.model = model;
		this.view = view;
	}

	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "NEW": createNewProfile("New Profile"); break;
		case "DELETE": break;
		case "SAVE": break;
		case "CLOSE": break;
		case "BROWSE_GAME": break;
		case "BROWSE_BG": break;
		default: System.out.println("Unsure how to handle Action: " + e.getActionCommand());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void valueChanged(ListSelectionEvent event) {
		JList<LauncherProfile> selectionModel = (JList<LauncherProfile>) event.getSource();
		int selection = selectionModel.getSelectedIndex();
		if (selection == -1)
			return;
		
		view.showProfileSettings(model.getProfiles().get(selection));
	}
	
	public void createNewProfile(String name) {
		List<LauncherProfile> profiles = model.getProfiles();
		
		LauncherProfile profile = DevLaunch.getDefaultLauncherProfile();
		profile.setName(name);
		
		int duplicates = 0;
		for (LauncherProfile p : profiles) {
			if (p.getName().equals(name))
				duplicates++;
		}
		
		if (duplicates > 0)
			profile.setName(name + String.valueOf(duplicates + 1));
		profiles.add(profile);

		view.updateProfilesList(profiles);
	}
}
