package pswg.tools.devlaunch;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DevLaunch extends Application {

	private static DevLaunch instance;
	private static List<String> randomBgs;

	private Stage stage;
	private MainController mainController;

	public static void main(String[] args) {
		if (instance != null) {
			DialogUtils.showInformationDialog("Only one instance of DevLaunch can be active at a time. Please close the existing instance to open a new one.");
			return;
		}
		Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
			throwable.printStackTrace();
			DialogUtils.showExceptionDialog(throwable);
		});

		File backgrounds = new File("./backgrounds");
		if (backgrounds.exists()) {
			randomBgs = new ArrayList<>();
			for (String f : backgrounds.list()) {
				if (f.endsWith(".png")) randomBgs.add("backgrounds/" + f);
			}
		}

		Font.loadFont(DevLaunch.class.getResource("resources/design/Starjedi.ttf").toExternalForm(), 14);

		launch(args);
	}

	public static LauncherProfile getDefaultLauncherProfile() {
		LauncherProfile profile = new LauncherProfile("Default Profile");
		profile.setGameArgs("");
		profile.setGameLoc("C:/Program Files (x86)/Star Wars Galaxies");
		profile.setServerAddress("127.0.0.1");
		profile.setServerPort("44453");
		profile.setBackground("");
		profile.setConsole(true);
		return profile;
	}

	public static List<LauncherProfile> loadSavedProfiles() {
		File fSavedProfiles = new File(ProfilesXmlFactory.FILE);

		List<LauncherProfile> profiles = null;

		if (fSavedProfiles.exists()) {
			try {
				profiles = ProfilesXmlFactory.open(fSavedProfiles);
			} catch (ParserConfigurationException | SAXException | IOException ignored) {

			}
		} else {
			Platform.runLater(() -> DialogUtils.showInformationDialog("This seems to be your first time using DevLaunch. A default profile has been generated. You can modify it by selecting the Profiles" +
					" button on the bottom left side of the launcher window."));
		}
		if (profiles == null) {
			profiles = new ArrayList<>();
			profiles.add(getDefaultLauncherProfile());
		}

		return profiles;
	}

	public static DevLaunch getInstance() {
		return instance;
	}

	public static String getRandomBackground() {
		Random random = new Random();
		return randomBgs.get(random.nextInt(randomBgs.size()));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		instance = this;

		URL fxml = getClass().getResource("resources/design/main.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(fxml);

		Parent root = fxmlLoader.load();
		mainController = fxmlLoader.getController();

		primaryStage.setTitle("ProjectSWG DevLaunch");
		primaryStage.setScene(new Scene(root));
		primaryStage.setResizable(false);

		primaryStage.show();

		this.stage = primaryStage;
	}

	public Stage getStage() {
		return stage;
	}

	public MainController getMainController() {
		return mainController;
	}
}
