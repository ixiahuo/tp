package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.tag.Tag;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = String.format(
            "%s : adds or deletes tags from an existing contact according to the currently displayed list\n\n"
            + "Format : %s INDEX [%sTAGS_TO_ADD] [%sTAGS_TO_DELETE]\n"
            + "Example : %s 1 %sJunior_Dev Cloud Project_1 %sIntern\n\n"
            + "Multiple tags are separated with spaces.",
            COMMAND_WORD,
            COMMAND_WORD, PREFIX_ADD_TAG, PREFIX_DELETE_TAG,
            COMMAND_WORD, PREFIX_ADD_TAG, PREFIX_DELETE_TAG);

    public static final String MESSAGE_TAG_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_TAGS_PROVIDED = "At least one field to edit must be provided.";
    public static final String MESSAGE_NOT_EDITED = "No tags were changed.";

    private final Index targetIndex;
    private final Set<Tag> tagsToAdd;
    private final Set<Tag> tagsToDelete;

    /**
     * Adds or Deletes Tag(s) from a Person
     */
    public TagCommand(Index targetIndex, Set<Tag> tagsToAdd, Set<Tag> tagsToDelete) {
        requireNonNull(targetIndex);
        requireNonNull(tagsToAdd);
        requireNonNull(tagsToDelete);

        this.targetIndex = targetIndex;
        this.tagsToAdd = tagsToAdd;
        this.tagsToDelete = tagsToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (tagsToAdd.isEmpty() && tagsToDelete.isEmpty()) {
            throw new CommandException(MESSAGE_NOT_TAGS_PROVIDED);
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        Person editedPerson = modifyTagsForPerson(personToEdit, tagsToAdd, tagsToDelete);
        if (personToEdit.getTags().equals(editedPerson.getTags())) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_TAG_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with newly added tags from {@code tagsToAdd}
     * and newly deleted tags from {@code tagsToDelete}.
     */
    private static Person modifyTagsForPerson(Person personToEdit,
                                              Set<Tag> tagsToAdd, Set<Tag> tagsToDelete) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Salary updatedSalary = personToEdit.getSalary();

        Set<Tag> updatedTags = new HashSet<>(tagsToAdd);
        updatedTags.addAll(personToEdit.getTags());
        updatedTags.removeAll(tagsToDelete);

        return new Person(name, phone, email, address, updatedTags, updatedSalary);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand otherTagCommand = (TagCommand) other;
        return targetIndex.equals(otherTagCommand.targetIndex)
                && tagsToAdd.equals(otherTagCommand.tagsToAdd)
                && tagsToDelete.equals(otherTagCommand.tagsToDelete);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("tagsToAdd", tagsToAdd)
                .add("tagsToDelete", tagsToDelete)
                .toString();
    }
}
