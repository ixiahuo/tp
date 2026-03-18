package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String ODD_ROW_COLOUR = "-fx-background-color: #514654;";
    private static final String EVEN_ROW_COLOUR = "-fx-background-color: #383c50;";

    private static final String FXML = "PersonListPanel.fxml";
    private static final int MIN_TILE_WIDTH = 340;
    private static final int PAD_AND_SCROLL_BAR_WIDTH = 55;

    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private final ObservableList<Person> personList;
    private int numColumns;

    @FXML
    private ScrollPane personScrollPane;

    @FXML
    private TilePane personTilePane;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     *
     * Code rewritten with help from ChatGPT.
     */
    public PersonListPanel(ObservableList<Person> personList, double primaryStageWidth) {
        super(FXML);
        this.personList = personList;
        this.numColumns = 1;

        int usableWidth = (int) primaryStageWidth - PAD_AND_SCROLL_BAR_WIDTH;
        updateTileWidth(usableWidth);
        updateTilePane();
    }

    private void updateTileWidth(int usableWidth) {
        this.numColumns = usableWidth / MIN_TILE_WIDTH;
        this.numColumns = (numColumns <= 0) ? 1 : this.numColumns;
        assert this.numColumns > 0 : "Num columns <= 0";

        int extraTileWidth = usableWidth % MIN_TILE_WIDTH / this.numColumns;
        int updatedTileWidth = MIN_TILE_WIDTH + extraTileWidth;

        this.personTilePane.setPrefTileWidth(updatedTileWidth);
    }

    private void updateTilePane() {
        this.personTilePane.getChildren().clear();

        int row = 1;
        int col = 1;
        int index = 1;

        for (Person person : this.personList) {
            PersonCard p = new PersonCard(person, index++);
            String colour = (row % 2 == 1) ? ODD_ROW_COLOUR : EVEN_ROW_COLOUR;
            p.getRoot().setStyle(colour);
            this.personTilePane.getChildren().add(p.getRoot());

            if (col == this.numColumns) {
                row++;
                col = 1;
            } else {
                col++;
            }
        }

        this.personScrollPane.setVvalue(0); // reset scroll to top
    }

    /**
     * Sets up listeners so {@code TilePane} updates with personList and window resizing.
     */
    public void setUpListeners() {
        // Sets up a listener that updates the TilePane upon change of the personList.
        this.personList.addListener((ListChangeListener<Person>) change -> {
            updateTilePane();
        });

        // Sets up a listener that updates the TilePane upon resizing of the ScrollPane.
        this.personScrollPane.viewportBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            int scrollPaneWidth = (int) newBounds.getWidth();
            updateTileWidth(scrollPaneWidth);
            updateTilePane();
        });
    }
}
