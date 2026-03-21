package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

// Code written with help from ChatGPT
@ExtendWith({ApplicationExtension.class})
class PersonListPanelTest {
    private static final double DEFAULT_HEIGHT = 700;
    private static final double DEFAULT_WIDTH = 700;

    private PersonListPanel personListPanel;
    private ObservableList<Person> personList = FXCollections.observableArrayList();

    @Start
    void start(Stage stage) {
        StackPane container = new StackPane();
        this.personListPanel = new PersonListPanel(this.personList, DEFAULT_WIDTH);
        this.personListPanel.setUpListeners();
        container.getChildren().add(this.personListPanel.getRoot());
        stage.setScene(new Scene(container, DEFAULT_WIDTH, DEFAULT_HEIGHT));
        stage.show();
    }

    @Test
    void updatePersonList_addThreePerson_success(FxRobot robot) throws TimeoutException {
        FxToolkit.setupFixture(() -> {
            Person p = new PersonBuilder().build();
            this.personList.add(p);
            this.personList.add(p);
            this.personList.add(p);

            GridPane personGridPane =
                    robot.lookup("#personGridPane").queryAs(GridPane.class);
            assertEquals(3, personGridPane.getChildren().size());

            Node node = personGridPane.getChildren().get(0);
            assertEquals(0, personGridPane.getRowIndex(node));
            assertEquals(0, personGridPane.getColumnIndex(node));

            node = personGridPane.getChildren().get(1);
            assertEquals(0, personGridPane.getRowIndex(node));
            assertEquals(1, personGridPane.getColumnIndex(node));

            node = personGridPane.getChildren().get(2);
            assertEquals(1, personGridPane.getRowIndex(node));
            assertEquals(0, personGridPane.getColumnIndex(node));
        });
    }
}
