package pswg.tools.devlaunch.resources;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Waverunner
 */
public class DialogUtils {
    public static void showInformationDialog(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("DevLaunch Dialog");
        alert.setHeaderText("Information");
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void showExceptionDialog(Throwable throwable) {
	    throwable.printStackTrace();

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("DevLaunch Dialog");
        alert.setHeaderText("Thrown Exception");
        alert.setContentText("DevLaunch has thrown an exception.");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    public static void showErrorMessage(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("DevLaunch Dialog");
        alert.setHeaderText("An error has been encountered");
        alert.setContentText(message);

        alert.showAndWait();
    }
}
