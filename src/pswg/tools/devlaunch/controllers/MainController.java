package pswg.tools.devlaunch.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pswg.tools.devlaunch.DevLaunch;
import pswg.tools.devlaunch.models.MainModel;
import pswg.tools.devlaunch.resources.DialogUtils;
import pswg.tools.devlaunch.resources.LauncherProfile;
import pswg.tools.devlaunch.resources.SwgProcessFactory;
import pswg.tools.devlaunch.resources.views.ProfilesDialog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	private SwgProcessFactory processFactory;

	private MainModel model;

	@FXML
	private ImageView imageView;
	@FXML
	private ComboBox<LauncherProfile> profileBox;

	private ObservableList<LauncherProfile> profileObservableList;

	private ProfilesDialog profilesDialog;
	private LauncherProfile currentProfile;

	@FXML
	private void handleLaunchGame() {
		if (!handleCheckCurrentProfile())
			return;

		final String gameLoc = currentProfile.getGameLoc();
		File gameExe = new File(gameLoc + "\\SwgClient_r.exe");
		
		if (!gameExe.exists()) {
			DialogUtils.showErrorMessage("Could not find game client at " + gameExe.getAbsolutePath());
			return;
		}
		
		File loginCfg = new File(gameLoc + "\\login.cfg");
		boolean exists = loginCfg.exists();
		if (exists) renameLoginCfg(loginCfg, gameLoc, "_bak");

		try {
			processFactory.launchGame(currentProfile, gameLoc);
		} catch (IOException e) {
			DialogUtils.showExceptionDialog(e);
		}
	}

	@FXML
	private void handleOpenProfilesDialog() {
		if (profilesDialog == null) {
			profilesDialog = new ProfilesDialog(DevLaunch.getInstance().getStage());
		}

		profilesDialog.display();
	}

	private void renameLoginCfg(File base, String directory, String name) {
		base.renameTo(new File(directory + "\\login" + name + ".cfg"));
	}

	private void handleChangeCurrentProfile(LauncherProfile profile) {
		imageView.setImage(new Image("file:" + profile.getBackground()));
	}

	private boolean handleCheckCurrentProfile() {
		if (currentProfile == null) {
			DialogUtils.showInformationDialog("You must first select a profile before you can launch the game.");
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		processFactory = new SwgProcessFactory(DevLaunch.getInstance().getStage());
		model = new MainModel(DevLaunch.loadSavedProfiles());
		profileObservableList = FXCollections.observableList(model.getProfiles());

		profileBox.setItems(profileObservableList);
		createListeners();
	}

	private void createListeners() {
		profileBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue == newValue)
				return;

			setCurrentProfile(newValue);
		});
	}

	public LauncherProfile getCurrentProfile() {
		return currentProfile;
	}

	public void setCurrentProfile(LauncherProfile currentProfile) {
		this.currentProfile = currentProfile;

		int index = model.getProfiles().indexOf(currentProfile);
		if (index != -1)
			model.setActiveProfile(model.getProfiles().indexOf(currentProfile));

		handleChangeCurrentProfile(currentProfile);
	}
}
