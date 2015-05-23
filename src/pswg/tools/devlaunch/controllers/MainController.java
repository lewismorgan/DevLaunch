package pswg.tools.devlaunch.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pswg.tools.devlaunch.DevLaunch;
import pswg.tools.devlaunch.models.MainModel;
import pswg.tools.devlaunch.resources.LauncherProfile;
import pswg.tools.devlaunch.resources.SwgProcessFactory;
import pswg.tools.devlaunch.resources.views.ProfilesDialog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainController implements Initializable {

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
			System.out.println("The game doesn't exist in the profile's directory!");
			return;
		}
		
		File loginCfg = new File(gameLoc + "\\login.cfg");
		boolean exists = loginCfg.exists();
		if (exists) renameLoginCfg(loginCfg, gameLoc, "_bak");

		try {
			System.out.println("Starting Game: " + gameExe.getAbsolutePath());
			SwgProcessFactory.launchGame(currentProfile, gameLoc);
			if (exists) {
				Executors.newSingleThreadScheduledExecutor().schedule(() -> { renameLoginCfg(new File(gameLoc + "\\login_bak.cfg"), gameLoc, ""); }, 5, TimeUnit.SECONDS);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleOpenProfilesDialog() {
		if (profilesDialog == null) {
			profilesDialog = new ProfilesDialog(DevLaunch.getInstance().getStage());
		}

		profilesDialog.showAndWait();
	}

	private void renameLoginCfg(File base, String directory, String name) {
		base.renameTo(new File(directory + "\\login" + name + ".cfg"));
	}

	private void handleChangeCurrentProfile(LauncherProfile profile) {
		imageView.setImage(new Image("file:" + profile.getBackground()));
	}

	private boolean handleCheckCurrentProfile() {
		if (currentProfile == null) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("DevLaunch");
			alert.setHeaderText("Launch Game");
			alert.setContentText("You must specify a profile to use from the Current Profiles selection box.");
			alert.showAndWait();
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
