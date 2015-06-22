package pswg.tools.devlaunch.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import pswg.tools.devlaunch.DevLaunch;
import pswg.tools.devlaunch.resources.DialogUtils;
import pswg.tools.devlaunch.resources.LauncherProfile;
import pswg.tools.devlaunch.resources.ProfilesXmlFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfilesController implements Initializable {
	@FXML
	private ListView<LauncherProfile> profileListView;
	@FXML
	private Button removeProfileButton;
	@FXML
	private GridPane propertyGrid;

	private ObservableList<LauncherProfile> profileObservableList;
	private boolean cacheChanges = false;
	private boolean hasSaved = false;
	private int profilesCreated = 0;

	//region New field? Update this region!

	//region FXML Property Fields
	@FXML
	private TextField name;
	@FXML
	private TextField directory;
	@FXML
	private TextField arguments;
	@FXML
	private TextField server;
	@FXML
	private TextField port;
	@FXML
	private TextField display;
	@FXML
	private CheckBox console;

	//endregion
	private void setPropertyFields(LauncherProfile profile) {
		cacheChanges = false;
		name.setText(profile.getName());
		directory.setText(profile.getGameLoc());
		arguments.setText(profile.getGameArgs());
		server.setText(profile.getServerAddress());
		port.setText(profile.getServerPort());
		display.setText(profile.getBackground());
		// Misc Settings
		console.setSelected(profile.isConsoleEnabled());
		cacheChanges = true;
	}

	private void cacheProfile(LauncherProfile toCache) {
		if (!cacheChanges)
			return;
		toCache.setName(name.getText());
		toCache.setGameLoc(directory.getText());
		toCache.setGameArgs(arguments.getText());
		toCache.setServerAddress(server.getText());
		toCache.setServerPort(port.getText());
		toCache.setBackground(display.getText());
		// Misc Settings
		toCache.setConsole(console.isSelected());
	}

	private void disableInterfaceElements(boolean disable) {
		propertyGrid.setDisable(disable);
		removeProfileButton.setDisable(disable);
	}

	private void addProfileListeners() {
		// Each field just needs to have a profilesettinglistener, this will automatically setup caching when ready to apply profile changes
		ProfileSettingListener<Object> listener = new ProfileSettingListener<>();

		addSettingListener(name, listener);
		addSettingListener(directory, listener);
		addSettingListener(arguments, listener);
		addSettingListener(server, listener);
		addSettingListener(port, listener);
		addSettingListener(display, listener);
		// Misc Settings
		addSettingListener(console, listener);
	}
	//endregion

	public void createNewProfile() {
		LauncherProfile profile = DevLaunch.getDefaultLauncherProfile();

		int size = profileObservableList.size();
		profile.setName("New Profile " + String.valueOf(profilesCreated + 1));
		profileObservableList.add(profile);

		cacheChanges = false;
		selectProfile(size);
		cacheChanges = true;

		profilesCreated++;
	}

	private void saveAllProfiles() {
		try {
			ProfilesXmlFactory.save(profileObservableList);
		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}

		hasSaved = true;
	}

	private void addSettingListener(TextField field, ProfileSettingListener<Object> listener) {
		field.textProperty().addListener(listener);
	}

	private void addSettingListener(CheckBox checkBox, ProfileSettingListener<Object> listener) {
		checkBox.selectedProperty().addListener(listener);
	}

	private int getSelectedIndex() {
		return profileListView.getSelectionModel().getSelectedIndex();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addListeners();
		disableInterfaceElements(true);
	}

	private void addListeners() {
		profileListView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
			if (oldValue == newValue)
				return;

			if (oldValue != null) {
				cacheProfile(oldValue);
			}

			if (newValue != null) {
				disableInterfaceElements(false);
				setPropertyFields(newValue);
			} else {
				disableInterfaceElements(true);
				setPropertyFields(new LauncherProfile());
			}
		}));

		addProfileListeners();
	}

	public void selectProfile(int index) {
		if (index != -1) {
			disableInterfaceElements(false);
			profileListView.getSelectionModel().select(index);
		} else {
			disableInterfaceElements(true);
			profileListView.getSelectionModel().select(-1);
		}
		profileListView.setItems(profileListView.getItems());
	}

	public void onApplyChanges(ActionEvent event) {
		saveAllProfiles();
		DevLaunch.getInstance().getMainController().hideProfilesDialog(); // hackiest way of hiding self ever, 1337 codez
	}

	public ObservableList<LauncherProfile> getProfileObservableList() {
		return profileObservableList;
	}

	public void setProfileObservableList(ObservableList<LauncherProfile> profileObservableList) {
		this.profileObservableList = profileObservableList;
		this.profileListView.setItems(profileObservableList);
	}

	public boolean hasSaved() {
		return hasSaved;
	}

	public void onBrowseAction(ActionEvent event) {
		Node source = (Node) event.getSource();
		if (source.getUserData() == null)
			return;

		String type = source.getUserData().toString();
		File initialDirectory = new File("./");

		switch (type) {
			case "Game": {
				DirectoryChooser chooser = new DirectoryChooser();
				chooser.setTitle("Select Game Location");
				chooser.setInitialDirectory(initialDirectory);
				File directory = chooser.showDialog(DevLaunch.getInstance().getStage());
				this.directory.textProperty().setValue(directory.getAbsolutePath());
				break;
			}
			case "Background": {
				FileChooser chooser = new FileChooser();
				chooser.setTitle("Select Background Image");
				chooser.setInitialDirectory(initialDirectory);
				chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
				File image = chooser.showOpenDialog(DevLaunch.getInstance().getStage());
				this.display.textProperty().setValue(image.getAbsolutePath());
				break;
			}
			default:
				DialogUtils.showErrorMessage("Unable to process directory select request for type \"" + type + "\"");
				break;
		}
	}

	public void handleRemoveProfile(ActionEvent event) {
		int index = getSelectedIndex();
		if (index == -1)
			return;

		profileObservableList.remove(index);
		if (index != 0)
			selectProfile(index - 1);
	}

	public void onCancelChanges(ActionEvent event) {
		DevLaunch.getInstance().getMainController().hideProfilesDialog();
	}

	class ProfileSettingListener<T> implements ChangeListener<T> {

		@Override
		public void changed(ObservableValue observable, Object oldValue, Object newValue) {
			if (!cacheChanges || newValue == null || newValue == oldValue)
				return;

			if (oldValue instanceof String && newValue instanceof String && ((String) newValue).isEmpty())
				return;

			LauncherProfile selectedItem = profileListView.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				cacheProfile(selectedItem);
			}
		}
	}
}
