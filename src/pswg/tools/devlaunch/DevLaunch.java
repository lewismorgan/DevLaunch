package pswg.tools.devlaunch;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import pswg.tools.devlaunch.controllers.MainController;
import pswg.tools.devlaunch.models.MainModel;
import pswg.tools.devlaunch.resources.LauncherProfile;
import pswg.tools.devlaunch.views.MainView;


public class DevLaunch {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		MainModel model = new MainModel();
		MainView view = new MainView(model);
		MainController controller = new MainController(model, view);
		view.addController(controller);
		view.show();
	}

	public static LauncherProfile getDefaultLauncherProfile() {
		LauncherProfile profile = new LauncherProfile("Default Profile");
		profile.setGameArgs("");
		profile.setGameLoc("C:\\Program Files (x86)\\Star Wars Galaxies");
		profile.setServerAddress("127.0.0.1");
		profile.setServerPort("44453");
		return profile;
	}
}
