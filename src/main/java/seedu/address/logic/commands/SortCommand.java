package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Sorts employee profiles in the address book.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = String.format(
            COMMAND_WORD + " : sorts employee profiles with the specified priority.\n"
            + "Sorts by names in alphabetical order by default.");

    public static final String MESSAGE_SUCCESS = "Profiles have been sorted.";
    private final Comparator<Person> sortingComparator;

    /**
     * Constructor for SortCommand when a comparator is provided.
     * @param sortingComparator Comparator to be used for sorting.
     */
    public SortCommand(Comparator<Person> sortingComparator) {
        requireNonNull(sortingComparator);
        this.sortingComparator = sortingComparator;
    }

    /**
     * Overloaded constructor for SortCommand. Implements the default sort.
     */
    public SortCommand() {
        this.sortingComparator = new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                Name name1 = p1.getName();
                Name name2 = p2.getName();
                return name1.compareTo(name2);
            }
        };
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.commitAddressBook();

        model.sort(this.sortingComparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof SortCommand otherSortCommand) {
            return sortingComparator.equals(otherSortCommand.sortingComparator);
        }
        return false;
    }

    @Override
    public String toString() {
        return SortCommand.class.getCanonicalName();
    }
}
