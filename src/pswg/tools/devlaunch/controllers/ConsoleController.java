package pswg.tools.devlaunch.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Waverunner on 6/21/2015
 */
public class ConsoleController implements Initializable {
    ExecutorService executorService = Executors.newCachedThreadPool();

    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VBox.setVgrow(tabPane, Priority.ALWAYS);
    }

	public void print(int processId, String message) {
		((ConsoleTab) tabPane.getTabs().get(processId)).addLine(message);
	}

	public void addInputStream(InputStream stream, String name) {
		final ConsoleTab tab = new ConsoleTab(name);
        tabPane.getTabs().add(tab);

        // TODO: Create tab's body
        executorService.submit(() -> {
            try {
                tabPane.getSelectionModel().select(tab);
                hookOutputStreams(tab, stream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void hookOutputStreams(ConsoleTab tab, InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        tab.addLine("Launching SwgClient_r.exe at " + System.currentTimeMillis());

        while ((line = reader.readLine()) != null) {
            tab.addLine(line);
        }

        Platform.runLater(() -> tab.setText(tab.getText() + " - DISPOSED"));
    }

    class ConsoleTab extends Tab {
        private TextArea textArea;

        public ConsoleTab(String name) {
            super(name);

            VBox parent = new VBox();

            textArea = new TextArea();
            textArea.setWrapText(true);
            textArea.setEditable(false);

	        HBox buttons = new HBox();

            parent.getChildren().add(textArea);
	        parent.getChildren().add(buttons);

            VBox.setVgrow(textArea, Priority.ALWAYS);

            setContent(parent);
        }

        public void addLine(String line) {
            textArea.appendText(line + "\n");
        }
    }
}
