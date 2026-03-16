package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class TagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person originalPerson = model.getFilteredPersonList().get(0);

        Set<Tag> toAdd = Set.of(new Tag("Test"));
        Set<Tag> toDelete = Set.of(new Tag("friends"));

        Set<Tag> newTags = new HashSet<Tag>(originalPerson.getTags());
        newTags.addAll(toAdd);
        newTags.removeAll(toDelete);

        Person editedPerson = new Person(originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                newTags,
                originalPerson.getSalary());

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, toAdd, toDelete);

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addTagsUnfilteredList_success() {
        Person originalPerson = model.getFilteredPersonList().get(0);

        Set<Tag> toAdd = Set.of(new Tag("Test"));

        Set<Tag> newTags = new HashSet<Tag>(originalPerson.getTags());
        newTags.addAll(toAdd);

        Person editedPerson = new Person(originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                newTags,
                originalPerson.getSalary());

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, toAdd, Set.of());

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteTagsUnfilteredList_success() {
        Person originalPerson = model.getFilteredPersonList().get(0);

        Set<Tag> toDelete = new HashSet<Tag>(originalPerson.getTags());

        assert(!toDelete.isEmpty());

        Person editedPerson = new Person(originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                Set.of(),
                originalPerson.getSalary());

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, Set.of(), toDelete);

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteNonexistentTagsUnfilteredList_failure() {
        Person originalPerson = model.getFilteredPersonList().get(0);

        Set<Tag> toDelete = new HashSet<Tag>();
        toDelete.add(new Tag("Non-existent"));

        Person editedPerson = new Person(originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                Set.of(),
                originalPerson.getSalary());

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, Set.of(), toDelete);

        String expectedMessage = String.format(TagCommand.MESSAGE_NOT_EDITED,
                Messages.format(editedPerson));

        assertCommandFailure(tagCommand, model, expectedMessage);
    }

    @Test
    public void execute_addDeleteSameTagUnfilteredList_failure() {
        Person originalPerson = model.getFilteredPersonList().get(0);

        Set<Tag> tags = new HashSet<Tag>();
        tags.add(new Tag("Tag"));

        Person editedPerson = new Person(originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                Set.of(),
                originalPerson.getSalary());

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, tags, tags);

        String expectedMessage = String.format(TagCommand.MESSAGE_NOT_EDITED,
                Messages.format(editedPerson));

        assertCommandFailure(tagCommand, model, expectedMessage);
    }

    @Test
    public void execute_noFieldsSpecifiedUnfilteredList_failure() {
        Person originalPerson = model.getFilteredPersonList().get(0);

        Set<Tag> sameTags = new HashSet<Tag>(originalPerson.getTags());

        Person editedPerson = new Person(originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                sameTags,
                originalPerson.getSalary());

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, Set.of(), Set.of());

        String expectedMessage = String.format(TagCommand.MESSAGE_NOT_TAGS_PROVIDED);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandFailure(tagCommand, model, expectedMessage);
    }

    @Test
    public void execute_allFieldsFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person originalPerson = model.getFilteredPersonList().get(0);

        Set<Tag> toAdd = Set.of(new Tag("Test"));
        Set<Tag> toDelete = Set.of(new Tag("friends"));

        Set<Tag> newTags = new HashSet<Tag>(originalPerson.getTags());
        newTags.addAll(toAdd);
        newTags.removeAll(toDelete);

        Person editedPerson = new Person(originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                newTags,
                originalPerson.getSalary());

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, toAdd, toDelete);

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTagUnfilteredList_failure() {
        Person originalPerson = model.getFilteredPersonList().get(0);

        Person editedPerson = new Person(originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                originalPerson.getTags(),
                originalPerson.getSalary());

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        TagCommand tagCommand = new TagCommand(INDEX_SECOND_PERSON, originalPerson.getTags(), Set.of());

        assertCommandFailure(tagCommand, model, TagCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        TagCommand tagCommand = new TagCommand(outOfBoundIndex, Set.of(new Tag("Test")), Set.of());

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        Set<Tag> toAdd = Set.of(new Tag(VALID_TAG_FRIEND));
        Set<Tag> toDelete = Set.of(new Tag(VALID_TAG_HUSBAND));

        final TagCommand standardCommand = new TagCommand(INDEX_FIRST_PERSON, toAdd, toDelete);

        // same values -> returns true
        Set<Tag> duplicateToAdd = Set.copyOf(toAdd);
        Set<Tag> duplicateToDelete = Set.copyOf(toDelete);

        TagCommand commandWithSameValues = new TagCommand(INDEX_FIRST_PERSON,
                duplicateToAdd, duplicateToDelete);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new TagCommand(INDEX_SECOND_PERSON, toAdd, toDelete)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new TagCommand(INDEX_FIRST_PERSON, toDelete, toDelete)));
        assertFalse(standardCommand.equals(new TagCommand(INDEX_FIRST_PERSON, toAdd, toAdd)));

        Set<Tag> moreTags = Set.of(new Tag(VALID_TAG_FRIEND), new Tag("MORE"));

        final TagCommand moreTagsStandardCommand = new TagCommand(INDEX_FIRST_PERSON, toAdd, moreTags);

        assertTrue(moreTagsStandardCommand.equals(new TagCommand(INDEX_FIRST_PERSON, toAdd, moreTags)));

        // Descriptors with extra or lesser Tags
        assertFalse(moreTagsStandardCommand.equals(new TagCommand(INDEX_FIRST_PERSON, moreTags, moreTags)));
        assertFalse(moreTagsStandardCommand.equals(new TagCommand(INDEX_FIRST_PERSON, toAdd, toAdd)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        Set<Tag> toAdd = Set.of(new Tag(VALID_TAG_FRIEND), new Tag("TEST"));
        Set<Tag> toDelete = Set.of(new Tag(VALID_TAG_HUSBAND));

        TagCommand tagCommand = new TagCommand(index, toAdd, toDelete);
        String expected = TagCommand.class.getCanonicalName() + "{targetIndex=" + index
                + ", tagsToAdd=" + toAdd
                + ", tagsToDelete=" + toDelete + "}";
        assertEquals(expected, tagCommand.toString());
    }

}
