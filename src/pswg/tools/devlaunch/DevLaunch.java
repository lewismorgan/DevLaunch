package pswg.tools.devlaunch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import pswg.tools.devlaunch.controllers.MainController;
import pswg.tools.devlaunch.models.MainModel;
import pswg.tools.devlaunch.resources.LauncherProfile;
import pswg.tools.devlaunch.resources.ProfilesXmlFactory;
import pswg.tools.devlaunch.views.MainView;


public class DevLaunch {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				List<LauncherProfile> profiles = loadSavedProfiles();
				if (profiles == null) {
					profiles = new ArrayList<LauncherProfile>();
					profiles.add(getDefaultLauncherProfile());
				}
				
				MainModel model = new MainModel(profiles);
				MainView view = new MainView(model);
				MainController controller = new MainController(model, view);
				view.addController(controller);
				view.show();
			}
			
		});
	}

	public static LauncherProfile getDefaultLauncherProfile() {
		LauncherProfile profile = new LauncherProfile("Default Profile");
		profile.setGameArgs("");
		profile.setGameLoc("C:\\Program Files (x86)\\Star Wars Galaxies");
		profile.setServerAddress("127.0.0.1");
		profile.setServerPort("44453");
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
