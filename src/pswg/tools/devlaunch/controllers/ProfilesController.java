package pswg.tools.devlaunch.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import pswg.tools.devlaunch.DevLaunch;
import pswg.tools.devlaunch.models.MainModel;
import pswg.tools.devlaunch.resources.LauncherProfile;
import pswg.tools.devlaunch.resources.ProfilesXmlFactory;
import pswg.tools.devlaunch.views.ProfilesView;

public class ProfilesController extends WindowAdapter implements ActionListener, ListSelectionListener{

	private MainModel model;
	private ProfilesView view;
	
	private int profilesCreated;
	private MainController mainController;
	
	private int selectedProfile;
	
	public ProfilesController(MainModel model, ProfilesView view, MainController mainController) {
		this.model = model;
		this.view = view;
		this.profilesCreated = 0;
		this.mainController = mainController;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "NEW": createNewProfile("New Profile"); break;
		case "DELETE": break;
		case "SAVE": saveAllProfiles(); closeView(); break;
		case "CLOSE": closeView(); break;
		case "BROWSE_GAME": break;
		case "BROWSE_BG": break;
		default: System.out.println("Unsure how to handle Action: " + e.getActionCommand());
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		@SuppressWarnings("unchecked")
		JList<LauncherProfile> selectionModel = (JList<LauncherProfile>) event.getSource();
		int selection = selectionModel.getSelectedIndex();
		if (selection == -1)
			return;
		
		selectedProfile = selection;
		view.showProfileSettings(model.getProfile(selection));
	}
	
	public void createNewProfile(String name) {
		saveAllProfiles();
		
		LauncherProfile profile = DevLaunch.getDefaultLauncherProfile();
		profile.setName((profilesCreated == 0) ? name : name +" "+String.valueOf(profilesCreated));
		model.addProfile(profile);

		view.updateProfilesList(model.getProfiles());
		
		profilesCreated++;
	}
	
	
	public void saveAllProfiles() {
		for (int i = 0; i < model.getProfiles().size(); i++) {
			if (i == selectedProfile) {
				LauncherProfile p = model.getProfile(i);
				p.setName(view.getValueName());
				p.setGameLoc(view.getValueGameDir());
				p.setGameArgs(view.getValueGameArgs());
				p.setServerAddress(view.getValueAddress());
				p.setServerPort(view.getValuePort());
				break;
			}
		}

		try {
			ProfilesXmlFactory.save(model.getProfiles());
		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}
		
		mainController.updateProfiles();
	}
	
	public void closeView() {
		view.setVisible(false);
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
		view.updateProfilesList(model.getProfiles());
	}
	
	@Override
	public void windowDeactivated(WindowEvent e) {
		//saveAllProfiles();
	}
}
