package pswg.tools.devlaunch.resources;

import javafx.stage.Stage;
import pswg.tools.devlaunch.resources.views.ConsoleDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SwgProcessFactory {
	private final ConsoleDialog console;

	public SwgProcessFactory(Stage main) {
		console = new ConsoleDialog(main);
	}

	public void launchGame(LauncherProfile profile, String location) throws IOException {
		if (!console.isShowing())
			console.show();

		try {
			Process process = createClientProcess(getBaseArguments(profile) + " " + profile.getGameArgs(), location);
			console.watchProcess(process);
		} catch (IOException e) {
			DialogUtils.showExceptionDialog(e);
		}
	}

	private Process createClientProcess(String arguments, String location) throws IOException {
		ProcessBuilder pb = new ProcessBuilder();
		pb.directory(new File(location));
		pb.redirectErrorStream(true);
		pb.command(getCommandList(arguments));
		return pb.start();
	}

	private List<String> getCommandList(String arguments) {
		List<String> commandList = new ArrayList<>();
		Collections.addAll(commandList, arguments.split(" "));
		return commandList;
	}

	private String getBaseArguments(LauncherProfile profile) {
		return "cmd start /c SwgClient_r.exe -- -s " +
				"Station subscriptionFeatures=1 gameFeatures=65535 -s " +
				"ClientGame loginServerPort0=" + profile.getServerPort() + " loginServerAddress0=" + profile.getServerAddress();
	}
}
