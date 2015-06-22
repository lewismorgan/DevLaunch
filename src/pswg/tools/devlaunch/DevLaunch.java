package pswg.tools.devlaunch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import pswg.tools.devlaunch.controllers.MainController;
import pswg.tools.devlaunch.resources.DialogUtils;
import pswg.tools.devlaunch.resources.LauncherProfile;
import pswg.tools.devlaunch.resources.ProfilesXmlFactory;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;


public class DevLaunch extends Application {

	private static DevLaunch instance;

	private Stage stage;
	private MainController mainController;

	public static void main(String[] args) {
		if (instance != null) {
			DialogUtils.showInformationDialog("Only one instance of DevLaunch can be active at a time. Please close the existing instance to open a new one.");
			return;
		}
		Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
			throwable.printStackTrace();
			//DialogUtils.showExceptionDialog(throwable);
		});
		launch(args);
	}

	public static LauncherProfile getDefaultLauncherProfile() {
		LauncherProfile profile = new LauncherProfile("Default Profile");
		profile.setGameArgs("");
		profile.setGameLoc("C:\\Program Files (x86)\\Star Wars Galaxies");
		profile.setServerAddress("127.0.0.1");
		profile.setServerPort("44453");
		profile.setBackground("");
		return profile;
	}

	public static List<LauncherProfile> loadSavedProfiles() {
		File fSavedProfiles = new File(ProfilesXmlFactory.FILE);

		if (!fSavedProfiles.exists())
			return null;

		List<LauncherProfile> profiles = null;

		try {
			profiles = ProfilesXmlFactory.open(fSavedProfiles);
			if (profiles == null) {
				DialogUtils.showErrorMessage("DevLaunch was not able to open a valid profiles file. This can be fixed by creating a new profile.");
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			DialogUtils.showExceptionDialog(e);
		}
		return profiles;
	}

	public static DevLaunch getInstance() {
		return instance;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		instance = this;

		URL fxml = getClass().getResource("resources/design/main.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(fxml);

		Parent root = fxmlLoader.load();
		mainController = fxmlLoader.getController();

		primaryStage.setTitle("DevLaunch");
		primaryStage.setScene(new Scene(root));
		primaryStage.setResizable(false);

		primaryStage.show();

		//new ConsoleDialog(primaryStage).show();
		this.stage = primaryStage;
	}

	public Scene getScene() {
		return stage.getScene();
	}

	public Stage getStage() {
		return stage;
	}

	public MainController getMainController() {
		return mainController;
	}
}
