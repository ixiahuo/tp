package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String HELP_MESSAGE =
            "Available commands:\n"
            + " * 'add' : adds a new contact\n"
            + " * 'edit' : edits an existing contact\n"
            + " * 'delete' : deletes an existing contact\n"
            + "    'clear' : deletes all existing contacts\n"
            + "    'undo' : restores contact list to immediate previous state\n"
            + " * 'cert-add' : adds a certificate to an existing contact\n"
            + " * 'cert-edit' : edits a certificate of an existing contact\n"
            + " * 'cert-del' : deletes a certificate from an existing contact\n"
            + " * 'tag' : adds or deletes tags from an existing contact\n"
            + "    'sort' : sorts the contact list by alphabetical order of names\n"
            + " * 'find' : finds existing contacts based on given criteria\n"
            + "    'list' : lists all existing contacts\n"
            + "    'exit' : exits the application\n"
            + "    'help': shows this help menu\n\n"
            + "TIP: commands marked with * have detailed usage explanations.\n"
            + "Run such commands with no extra input to see their usage explanations (e.g. 'cert-add')";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button closeButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Sets the help message to be displayed.
     */
    public void setHelpMessage(String helpMessage) {
        this.helpMessage.setText(helpMessage);
        getRoot().sizeToScene();
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().centerOnScreen();
        getRoot().requestFocus();
    }

    /**
     * Closes the help window.
     */
    @FXML
    private void close() {
        getRoot().close();
    }
}
