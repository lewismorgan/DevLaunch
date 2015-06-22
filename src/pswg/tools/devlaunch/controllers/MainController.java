package pswg.tools.devlaunch.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	private SwgProcessFactory processFactory;

	private MainModel model;

	@FXML
	private ImageView imageView;
	@FXML
	private ComboBox<LauncherProfile> profileBox;

	private ProfilesDialog dialog;

	@FXML
	public void handleLaunchGame() {
		if (!handleCheckCurrentProfile())
			return;

		LauncherProfile currentProfile = getSelectedProfile();
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
	public void handleOpenProfilesDialog() {
		ProfilesDialog profilesDialog = new ProfilesDialog(DevLaunch.getInstance().getStage());
		profilesDialog.setProfileView(model.getProfiles());
		dialog = profilesDialog;
		profilesDialog.showAndWait();

		List<LauncherProfile> updatedProfiles = profilesDialog.getSavedProfiles();
		if (updatedProfiles != null && updatedProfiles.size() != 0) {
			model.setProfiles(updatedProfiles);
			profileBox.setItems(FXCollections.observableList(updatedProfiles));
			selectProfile(0);
		}

		dialog = null;
	}

	public void hideProfilesDialog() {
		if (dialog == null)
			return;

		dialog.hide();
	}

	private void renameLoginCfg(File base, String directory, String name) {
		base.renameTo(new File(directory + "\\login" + name + ".cfg"));
	}

	private void handleChangeCurrentProfile(LauncherProfile profile) {
		String background = profile.getBackground();
		if (background == null || background.isEmpty())
			background = DevLaunch.getRandomBackground();
		imageView.setImage(new Image("file:" + background));
	}

	private boolean handleCheckCurrentProfile() {
		if (getSelectedProfile() == null) {
			DialogUtils.showInformationDialog("You must first select a profile before you can launch the game.");
			return false;
		} else {
			return true;
		}
	}

	public LauncherProfile getSelectedProfile() {
		return profileBox.getSelectionModel().getSelectedItem();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		processFactory = new SwgProcessFactory(DevLaunch.getInstance().getStage());
		model = new MainModel(DevLaunch.loadSavedProfiles());
		profileBox.setItems(FXCollections.observableList(model.getProfiles()));

		createListeners();
		selectProfile(model.getActiveProfile());
	}

	private void createListeners() {
		profileBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null || oldValue == newValue)
				return;

			setCurrentProfile(newValue);
		});
	}

	public void selectProfile(int index) {
		profileBox.getSelectionModel().select(index);
	}

	public void setCurrentProfile(LauncherProfile currentProfile) {
		handleChangeCurrentProfile(currentProfile);
		model.setActiveProfile(profileBox.getSelectionModel().getSelectedIndex());
	}

	public void handleOpenGameSettings(ActionEvent event) {
		if (!handleCheckCurrentProfile())
			return;

		LauncherProfile currentProfile = getSelectedProfile();
		final String gameLoc = currentProfile.getGameLoc();

		File setupExe = new File(gameLoc + "\\SwgClientSetup_r.exe");
		if (!setupExe.exists()) {
			DialogUtils.showErrorMessage("Could not find SwgClientSetup_r.exe at " + gameLoc);
			return;
		}

		processFactory.launchGameSettings(gameLoc);
	}

	public void handleClearCrashLogs(ActionEvent event) {
		if (!handleCheckCurrentProfile())
			return;

		LauncherProfile currentProfile = getSelectedProfile();

		File root = new File(currentProfile.getGameLoc());

	}
}
