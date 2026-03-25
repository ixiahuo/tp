package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String ODD_ROW_COLOUR = "-fx-background-color: #514654;";
    private static final String EVEN_ROW_COLOUR = "-fx-background-color: #383c50;";

    private static final String FXML = "PersonListPanel.fxml";

    private static final int ENTRY_HEIGHT = 270;
    private static final int MIN_ENTRY_WIDTH = 340;
    private static final int SCROLL_BAR_WIDTH = 15;

    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private final ObservableList<Person> personList;
    private int numColumns;
    private int entryWidth;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane personGridPane;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     *
     * Code rewritten with help from ChatGPT.
     */
    public PersonListPanel(ObservableList<Person> personList, double primaryStageWidth) {
        super(FXML);
        this.personList = personList;
        this.numColumns = 1;
        this.entryWidth = MIN_ENTRY_WIDTH;

        int usableWidth = (int) primaryStageWidth - SCROLL_BAR_WIDTH;
        updateNumColumnsAndEntryWidth(usableWidth);
        updateDisplay();
    }

    private void updateNumColumnsAndEntryWidth(int usableWidth) {
        this.numColumns = usableWidth / MIN_ENTRY_WIDTH;
        this.numColumns = (this.numColumns <= 0) ? 1 : this.numColumns;
        assert this.numColumns > 0 : "Number of columns cannot be less than 1!";

        int extraWidth = usableWidth % MIN_ENTRY_WIDTH / this.numColumns;
        this.entryWidth = MIN_ENTRY_WIDTH + extraWidth;
        assert this.entryWidth >= MIN_ENTRY_WIDTH : "Calculated entry width is less than the minimum!";

        logger.info("----------------[PersonListPanel][Number of columns: " + this.numColumns + "]");
    }

    private void updateDisplay() {
        this.personGridPane.getChildren().clear();

        int row = 0;
        int col = 0;
        int id = 1;

        for (Person p : this.personList) {
            PersonCard pc = new PersonCard(p, id++);
            String colour = (row % 2 == 0) ? ODD_ROW_COLOUR : EVEN_ROW_COLOUR;

            pc.getRoot().setStyle(colour);
            pc.getRoot().setPrefWidth(entryWidth);
            pc.getRoot().setMinHeight(ENTRY_HEIGHT);
            pc.getRoot().setPrefHeight(ENTRY_HEIGHT);
            pc.getRoot().setMaxHeight(ENTRY_HEIGHT);

            this.personGridPane.add(pc.getRoot(), col++, row);
            if (col == numColumns) {
                col = 0;
                row++;
            }
        }

        this.scrollPane.setVvalue(0); // reset scroll to top
        logger.info("----------------[PersonListPanel][Display updated]");
    }

    /**
     * Sets up listeners so the display updates when personList changes or window resizes.
     */
    public void setUpListeners() {
        this.personList.addListener((ListChangeListener<Person>) change -> {
            updateDisplay();
        });

        this.scrollPane.viewportBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            int scrollPaneWidth = (int) newBounds.getWidth();
            updateNumColumnsAndEntryWidth(scrollPaneWidth);
            updateDisplay();
        });
    }
}
