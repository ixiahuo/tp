package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UndoCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_undoWithoutCommit_throwsCommandException() {
        assertCommandFailure(new UndoCommand(), model, UndoCommand.UNDO_MESSAGE_FAILURE);
    }

    @Test
    public void execute_undoAfterCommit_success() {
        model.commitAddressBook();
        model.deletePerson(model.getFilteredPersonList().get(0));
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.UNDO_MESSAGE_SUCCESS, expectedModel);
    }
}
