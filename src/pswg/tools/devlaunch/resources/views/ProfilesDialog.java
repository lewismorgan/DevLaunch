package pswg.tools.devlaunch.resources.views;

import javafx.collections.FXCollections;
import javafx.stage.Stage;
import pswg.tools.devlaunch.controllers.ProfilesController;
import pswg.tools.devlaunch.resources.LauncherProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Waverunner on 5/23/2015
 */
public class ProfilesDialog extends ApplicationDialog {

	public ProfilesDialog(Stage parent) {
		super(parent, "Profiles");
	}

	@Override
	protected String getDesignPath() {
		return "resources/design/profiles.fxml";
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProfilesController getController() {
		return super.getController();
	}

	public List<LauncherProfile> getSavedProfiles() {
		if (!getController().hasSaved())
			return null;
		return new ArrayList<>(getController().getProfileObservableList());
	}

	public void setProfileView(List<LauncherProfile> profiles) {
		List<LauncherProfile> cache = new ArrayList<>();
		profiles.forEach(profile -> cache.add(profile.clone()));
		getController().setProfileObservableList(FXCollections.observableList(cache));
	}
}
