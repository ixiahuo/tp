package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
import seedu.address.model.tag.TagColour;
import seedu.address.model.tag.TagSet;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class TagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute_addTagsFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person originalPerson = model.getFilteredPersonList().get(0);

        Set<Tag> toAdd = Set.of(new Tag("Test"));

        Set<Tag> newTags = new TagSet(originalPerson.getTags());
        newTags.addAll(toAdd);

        Person editedPerson = new Person(originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                newTags,
                originalPerson.getSalary());

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, toAdd, true);

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

        Set<Tag> newTags = new TagSet();
        newTags.addAll(originalPerson.getTags());
        newTags.addAll(toAdd);

        Person editedPerson = new Person(originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                newTags,
                originalPerson.getSalary());

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, toAdd, true);

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteTagsUnfilteredList_success() {
        Person originalPerson = model.getFilteredPersonList().get(0);

        Set<Tag> toDelete = new TagSet();
        toDelete.addAll(originalPerson.getTags());

        assert(!toDelete.isEmpty());

        Person editedPerson = new Person(originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                Set.of(),
                originalPerson.getSalary());

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, toDelete, false);

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteAllNonexistentTagsUnfilteredList_failure() {

        Set<Tag> toDelete = new TagSet();
        toDelete.add(new Tag("Non-existent"));

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, toDelete, false);

        assertCommandFailure(tagCommand, model, TagCommand.MESSAGE_NO_TAGS_TO_DELETE);
    }

    @Test
    public void execute_deleteSomeNonexistentTagsUnfilteredList_success() {
        Person originalPerson = model.getFilteredPersonList().get(1);

        Tag nonExistentTag = new Tag("TEST", TagColour.RED);
        Tag toDeleteTag = new Tag(originalPerson.getTags().stream().toList().get(0).tagName);

        TagSet tagsToDelete = new TagSet();
        tagsToDelete.add(toDeleteTag);
        tagsToDelete.add(nonExistentTag);
        TagCommand tagCommand = new TagCommand(INDEX_SECOND_PERSON, tagsToDelete, false);

        TagSet expectedTags = new TagSet();
        expectedTags.addAll(originalPerson.getTags());
        expectedTags.remove(toDeleteTag);

        Person editedPerson = new Person(originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                expectedTags,
                originalPerson.getSalary(),
                originalPerson.getCertificates());

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(originalPerson, editedPerson);
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptySetSpecifiedUnfilteredList_failure() {
        Person originalPerson = model.getFilteredPersonList().get(0);

        Set<Tag> sameTags = new TagSet();
        sameTags.addAll(originalPerson.getTags());

        Person editedPerson = new Person(originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                sameTags,
                originalPerson.getSalary());

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, Set.of(), true);

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(originalPerson, editedPerson);

        assertCommandFailure(tagCommand, model, expectedMessage);
    }

    @Test
    public void execute_addAllDuplicateTagsUnfilteredList_failure() {
        Person originalPerson = model.getFilteredPersonList().get(1);

        TagCommand tagCommand = new TagCommand(INDEX_SECOND_PERSON, originalPerson.getTags(), true);

        assertCommandFailure(tagCommand, model, TagCommand.MESSAGE_ALL_DUPLICATE_ADD);
    }

    @Test
    public void execute_addSomeDuplicateTagsUnfilteredList_success() {
        Person originalPerson = model.getFilteredPersonList().get(1);

        Tag tagToAdd = new Tag("TEST", TagColour.RED);

        TagSet tagsToAdd = new TagSet();
        tagsToAdd.add(new Tag(originalPerson.getTags().stream().toList().get(0).tagName, TagColour.PURPLE));
        tagsToAdd.add(tagToAdd);
        TagCommand tagCommand = new TagCommand(INDEX_SECOND_PERSON, tagsToAdd, true);

        TagSet expectedTags = new TagSet();
        expectedTags.addAll(originalPerson.getTags());
        expectedTags.add(tagToAdd);

        Person editedPerson = new Person(originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                expectedTags,
                originalPerson.getSalary(),
                originalPerson.getCertificates());

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(originalPerson, editedPerson);
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        TagCommand tagCommand = new TagCommand(outOfBoundIndex, Set.of(new Tag("Test")), true);

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }

    @Test
    public void execute_tagIndexSpecifiedPerson() throws Exception {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Person person1 = new PersonBuilder().withName("a").build();
        Person person2 = new PersonBuilder().withName("A").build();
        new AddCommand(person1).execute(model);
        new AddCommand(person2).execute(model);

        new TagCommand(Index.fromOneBased(2), Set.of(new Tag("test")), true)
                .execute(model);

        Person expectedPerson2 = new PersonBuilder().withName("A").withTags("test").build();
        assertEquals(model.getFilteredPersonList().get(0), person1);
        assertEquals(model.getFilteredPersonList().get(1), expectedPerson2);
    }

    @Test
    public void equals() {
        Set<Tag> toUpdate = Set.of(new Tag(VALID_TAG_FRIEND));
        Set<Tag> toUpdateAlt = Set.of(new Tag(VALID_TAG_HUSBAND));

        final TagCommand standardCommand = new TagCommand(INDEX_FIRST_PERSON, toUpdate, true);

        // same values -> returns true
        Set<Tag> duplicateToAdd = Set.copyOf(toUpdate);

        TagCommand commandWithSameValues = new TagCommand(INDEX_FIRST_PERSON, duplicateToAdd, true);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new TagCommand(INDEX_SECOND_PERSON, toUpdate, true)));

        // empty set -> returns false
        assertFalse(standardCommand.equals(new TagCommand(INDEX_SECOND_PERSON, Set.of(), true)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new TagCommand(INDEX_FIRST_PERSON, toUpdateAlt, true)));
        assertFalse(standardCommand.equals(new TagCommand(INDEX_FIRST_PERSON, toUpdate, false)));

        TagSet moreTags = new TagSet();
        moreTags.add(new Tag("MORE"));
        moreTags.addAll(toUpdate);
        final TagCommand moreTagsStandardCommand = new TagCommand(INDEX_FIRST_PERSON, moreTags, true);

        assertTrue(moreTagsStandardCommand.equals(new TagCommand(INDEX_FIRST_PERSON, moreTags, true)));

        // Descriptors with extra or lesser Tags
        assertFalse(moreTagsStandardCommand.equals(new TagCommand(INDEX_FIRST_PERSON, toUpdate, true)));
        assertFalse(standardCommand.equals(moreTagsStandardCommand));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        Set<Tag> toAdd = Set.of(new Tag(VALID_TAG_FRIEND), new Tag("TEST"));

        TagCommand tagCommand = new TagCommand(index, toAdd, true);
        String expected = TagCommand.class.getCanonicalName() + "{targetIndex=" + index
                + ", tagsToUpdate=" + toAdd
                + ", isAdd=" + true + "}";
        assertEquals(expected, tagCommand.toString());
    }

}
