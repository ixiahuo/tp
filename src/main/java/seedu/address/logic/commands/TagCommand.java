package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cert.Certificate;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColour;
import seedu.address.model.tag.TagSet;


/**
 * Tag or DeTag a person identified using it's displayed index from the address book.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";
    public static final String MESSAGE_USAGE = String.format(
            "%s : adds or deletes tags from an existing contact according to the currently displayed list\n\n"
            + "Format : %s INDEX [%sTAGS_TO_ADD] [%sCOLOUR_OF_TAGS_TO_ADD] [%sTAGS_TO_DELETE] \n"
            + "Example : %s 1 %sJunior_Dev Cloud Project_1 %s%s\n\n"
            + "Multiple tags (and colours) are separated with spaces.\n%s",
            COMMAND_WORD,
            COMMAND_WORD, PREFIX_ADD_TAG, PREFIX_COLOUR_TAG, PREFIX_DELETE_TAG,
            COMMAND_WORD, PREFIX_ADD_TAG, PREFIX_COLOUR_TAG, TagColour.RED.name(),
            TagColour.MESSAGE_COLOUR_OPTIONS);

    public static final String MESSAGE_TAG_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_ALL_DUPLICATE_ADD = "All of the tags were duplicates and hence were"
            + " not added.\n Note: If you are trying to recolour tags, delete them first, then add them back";

    public static final String MESSAGE_NO_TAGS_TO_DELETE = "No tags were deleted (there were no matching tags)";

    private final Index targetIndex;
    private final Set<Tag> tagsToUpdate;
    private final boolean isAdd;

    /**
     * Adds or Deletes Tag(s) from a Person
     */
    public TagCommand(Index targetIndex, Set<Tag> tagsToUpdate, boolean isAdd) {
        requireNonNull(targetIndex);
        requireNonNull(tagsToUpdate);

        this.targetIndex = targetIndex;
        this.tagsToUpdate = tagsToUpdate;
        this.isAdd = isAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (tagsToUpdate.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());

        Set<Tag> updatedTags = new TagSet(personToEdit.getTags());
        Set<Tag> sharedTags = updatedTags.stream().filter(tagsToUpdate::contains)
                .collect(Collectors.toSet());

        Person editedPerson = modifyTagsForPerson(personToEdit, tagsToUpdate, isAdd);

        if (isAdd && !sharedTags.isEmpty()) {
            throw new CommandException(MESSAGE_ALL_DUPLICATE_ADD);
        } else if (!isAdd && sharedTags.isEmpty()) {
            throw new CommandException(MESSAGE_NO_TAGS_TO_DELETE);
        }
        model.commitAddressBook();

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_TAG_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with newly added tags from {@code tagsToAdd}
     * and newly deleted tags from {@code tagsToDelete}.
     */
    private static Person modifyTagsForPerson(Person personToEdit, Set<Tag> tagsToUpdate, boolean isAdd) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Salary updatedSalary = personToEdit.getSalary();
        ArrayList<Certificate> existingCerts = personToEdit.getCertificates();

        Set<Tag> updatedTags = new TagSet(personToEdit.getTags());

        if (isAdd) {
            updatedTags.addAll(tagsToUpdate);
        } else {
            updatedTags.removeAll(tagsToUpdate);
        }

        return new Person(name, phone, email, address, updatedTags, updatedSalary, existingCerts);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCommand otherTagCommand)) {
            return false;
        }

        return targetIndex.equals(otherTagCommand.targetIndex)
                && tagsToUpdate.equals(otherTagCommand.tagsToUpdate)
                && isAdd == otherTagCommand.isAdd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("tagsToUpdate", tagsToUpdate)
                .add("isAdd", isAdd)
                .toString();
    }
}
