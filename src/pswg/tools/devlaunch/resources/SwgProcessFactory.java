package pswg.tools.devlaunch.resources;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;

public class SwgProcessFactory {
	
	public static Process createProcess(LauncherProfile profile, String location) throws IOException {
		long id = System.currentTimeMillis();
		ProcessBuilder pb = new ProcessBuilder();
		pb.redirectErrorStream(true);
		pb.redirectOutput(Redirect.to(new File("./logs/console_output_" + String.valueOf(id) + ".txt")));
		pb.redirectError(Redirect.to(new File("./logs/error_output_" + String.valueOf(id) + ".txt")));
		pb.directory(new File(location));
		pb.command(getCommandList(getBaseArguments(profile) + " " + profile.getGameArgs()));
		System.out.println("Command: " + pb.command());
		return pb.start();
	}
	
	private static List<String> getCommandList(String arguments) {
		List<String> commandList = new ArrayList<String>();
		
		String[] args = arguments.split(" ");
		for (String arg : args) {
			commandList.add(arg);
		}
		return commandList;
	}
	
	private static String getBaseArguments(LauncherProfile profile) {
		return "cmd /c start SwgClient_r.exe -- -s " +
				"Station subscriptionFeatures=1 gameFeatures=65535 -s " +
				"ClientGame loginServerPort0=" + profile.getServerPort() + " loginServerAddress0=" + profile.getServerAddress();
	}
}
