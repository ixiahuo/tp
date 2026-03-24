package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Restores the address book to its previous state.
 * This command allows users to undo a single modifying operation.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = String.format(
            "%s : Restores the contact list to the previous state.\n"
                    + "Example : %s", COMMAND_WORD, COMMAND_WORD);

    public static final String UNDO_MESSAGE_SUCCESS = "Undid the previous command.";
    public static final String UNDO_MESSAGE_FAILURE = "No previous commands to undo.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canUndo()) {
            throw new CommandException(UNDO_MESSAGE_FAILURE);
        }
        model.undoAddressBook();
        return new CommandResult(UNDO_MESSAGE_SUCCESS);
    }
}
