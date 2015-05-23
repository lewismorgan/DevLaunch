package pswg.tools.devlaunch.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import pswg.tools.devlaunch.models.MainModel;
import pswg.tools.devlaunch.resources.LauncherProfile;
import pswg.tools.devlaunch.resources.SwgProcessFactory;
import pswg.tools.devlaunch.views.MainView;
import pswg.tools.devlaunch.views.ProfilesView;

public class MainController {

	private MainModel model;
	private MainView view;
	
	private ProfilesView profilesDialog;

	public MainController() {}
	public MainController(MainModel model, MainView view) {
		this.model = model;
		this.view = view;
	}

	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
		case "PROFILES": showProfilesDialog(); break;
		case "OPTIONS": showOptionsDialog(); break;
		case "PLAY": launchGame(); break;
		//case "PROFILE_CHANGED": changeProfile(); break;
		default: System.out.println("Unsure how to handle Action: " + arg0.getActionCommand());
		}
	}

	/*
	public void changeProfile() {
		model.setActiveProfile(view.getActiveProfile());
		
		LauncherProfile active = model.getProfile(view.getActiveProfile());
		view.setBackground(active.getBackground());
		
	}*/
	
	public void launchGame() {
		LauncherProfile profile = model.getProfile(model.getActiveProfile());
		
		final String gameLoc = profile.getGameLoc();
		File gameExe = new File(gameLoc + "\\SwgClient_r.exe");
		
		if (!gameExe.exists()) {
			System.out.println("The game doesn't exist in the profile's directory!");
			return;
		}
		
		File loginCfg = new File(gameLoc + "\\login.cfg");
		boolean exists = loginCfg.exists();
		if (exists) renameLoginCfg(loginCfg, gameLoc, "_bak");

		try {
			System.out.println("Starting Game: " + gameExe.getAbsolutePath());
			SwgProcessFactory.launchGame(profile, gameLoc);
			if (exists) {
				Executors.newSingleThreadScheduledExecutor().schedule(() -> { renameLoginCfg(new File(gameLoc + "\\login_bak.cfg"), gameLoc, ""); }, 5, TimeUnit.SECONDS);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showOptionsDialog() {
		
	}
	
	public void showProfilesDialog() {
		if (profilesDialog == null)
			createProfilesDialog();

		profilesDialog.setVisible(true);
	}
	/*
	public void updateProfiles() {
		view.updateProfileSelections(model.getProfiles(), model.getActiveProfile());
	}*/
	
	private void createProfilesDialog() {
		profilesDialog = new ProfilesView(model);
		ProfilesController controller = new ProfilesController(model, profilesDialog, this);
		profilesDialog.addController(controller);
	}
	
	private void renameLoginCfg(File base, String directory, String name) {
		base.renameTo(new File(directory + "\\login" + name + ".cfg"));
	}
}
