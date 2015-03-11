package pswg.tools.devlaunch.resources;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.DefaultCaret;

public class SwgProcessFactory {
	
	public static void launchGame(LauncherProfile profile, String location) throws IOException {
		createConsoleOutput(getBaseArguments(profile) + " " + profile.getGameArgs(), location);
	}
	
	private static Process createClient(String arguments, String location) throws IOException {
		ProcessBuilder pb = new ProcessBuilder();
		pb.directory(new File(location));
		pb.command(getCommandList(arguments));
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
		return "cmd start /c SwgClient_r.exe -- -s " +
				"Station subscriptionFeatures=1 gameFeatures=65535 -s " +
				"ClientGame loginServerPort0=" + profile.getServerPort() + " loginServerAddress0=" + profile.getServerAddress();
	}
	
	private static void createConsoleOutput(String args, String location) throws IOException {
		JTextPane output = new JTextPane();
		JScrollPane outputContainer = new JScrollPane(output);
		output.setEditable(false);
		((DefaultCaret)output.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		final JFrame frame = new JFrame("SwgClient_r");
		frame.setSize(400, 400);
		frame.setContentPane(outputContainer);
		frame.setVisible(true);
		final Process process = createClient(args, location);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) { cleanup(frame, process); }
			public void windowClosing(WindowEvent e) { cleanup(frame, process); }
		});
		final InputStream programOutput = process.getInputStream();
		final InputStream errorOutput = process.getErrorStream();
		while (process.isAlive()) {
			if (programOutput.available() > 0)
				append(output, outputContainer, read(programOutput));
			else if (errorOutput.available() > 0)
				append(output, outputContainer, read(errorOutput));
		}
	}
	
	private static final void append(JTextPane output, JScrollPane scroll, String text) {
		output.setText(output.getText() + text);
	}
	
	private static final String read(InputStream stream) throws IOException {
		if (stream.available() == 0)
			return "";
		byte [] data = new byte[stream.available()];
		stream.read(data);
		String str = new String(data);
		System.out.print(str);
		return str;
	}
	
	private static final void cleanup(JFrame frame, Process process) {
		frame.dispose();
		process.destroy();
		System.exit(0);
	}
	
	private static final Process startClient(String command) throws IOException {
		return Runtime.getRuntime().exec(command);
	}
	
}
