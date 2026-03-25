package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;
import seedu.address.model.tag.TagColourComparator;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private VBox cardPane;
    @FXML
    private ScrollPane innerScrollPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label salary;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane certs;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        salary.setText(person.getSalary().value);
        person.getTags().stream()
                .sorted(new TagColourComparator())
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    tagLabel.getStyleClass().add(tag.tagColour.getCssClass());
                    tags.getChildren().add(tagLabel);
                });

        person.getCertificates()
                .forEach(cert -> {
                    Label l = new Label(cert.displayCertString());
                    l.setWrapText(true);
                    l.maxWidthProperty().bind(certs.widthProperty());
                    certs.getChildren().add(l);
                });

        // prevent jumping to top if user clicks on any entry
        innerScrollPane.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> e.consume());
    }
}
