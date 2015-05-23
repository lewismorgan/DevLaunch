package pswg.tools.devlaunch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.ParserConfigurationException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import pswg.tools.devlaunch.controllers.MainController;
import pswg.tools.devlaunch.models.MainModel;
import pswg.tools.devlaunch.resources.LauncherProfile;
import pswg.tools.devlaunch.resources.ProfilesXmlFactory;
import pswg.tools.devlaunch.views.MainView;


public class DevLaunch extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("resources/design/sample.fxml"));
		primaryStage.setTitle("Hello World");
		primaryStage.setScene(new Scene(root, 300, 275));
		primaryStage.show();
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
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return profiles;
	}
}
