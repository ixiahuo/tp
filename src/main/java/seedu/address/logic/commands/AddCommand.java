package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = String.format(
            "%s : adds a new contact\n\n"
            + "Format : %s %sNAME %sPHONE %sEMAIL %sADDRESS %sSALARY\n"
            + "Example : %s "
                    + "%sJohn Doe "
                    + "%s+65 81234567 "
                    + "%sj.doe@xyz.co "
                    + "%s123D Pine Road, #12-345, Singapore 123456 "
                    + "%s5000\n\n"
            + "Phone, email, address and salary can be blank, "
            + "but the prefixes (%s %s %s %s) must still be entered.",
            COMMAND_WORD,
            COMMAND_WORD, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_SALARY,
            COMMAND_WORD, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_SALARY,
            PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_SALARY);

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
