package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
// import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.CombinedPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate namePred = new NameContainsKeywordsPredicate(List.of("Alice"));
        TagContainsKeywordsPredicate tagPred = new TagContainsKeywordsPredicate(List.of(Set.of(new Tag("friend"))));

        CombinedPredicate firstPredicate = new CombinedPredicate(List.of(namePred));
        CombinedPredicate secondPredicate = new CombinedPredicate(List.of(tagPred));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(new CombinedPredicate(
            List.of(new NameContainsKeywordsPredicate(List.of("Alice")))));
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // different predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    // @Test
    // public void execute_zeroKeywords_noPersonFound() {
    //     String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
    //     NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(List.of(" "));
    //     CombinedPredicate combined = new CombinedPredicate(List.of(predicate));
    //     FindCommand command = new FindCommand(combined);
    //     expectedModel.updateFilteredPersonList(combined);
    //     assertCommandSuccess(command, model, expectedMessage, expectedModel);
    //     assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    // }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(List.of("Kurz", "Elle", "Kunz"));
        CombinedPredicate combined = new CombinedPredicate(List.of(predicate));
        FindCommand command = new FindCommand(combined);
        expectedModel.updateFilteredPersonList(combined);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_partialKeyword_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(List.of("Ku"));
        CombinedPredicate combined = new CombinedPredicate(List.of(predicate));
        FindCommand command = new FindCommand(combined);
        expectedModel.updateFilteredPersonList(combined);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleTag_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(
            List.of(
                Set.of(new Tag("friends"))
            )
        );
        CombinedPredicate combined = new CombinedPredicate(List.of(predicate));
        FindCommand command = new FindCommand(combined);
        expectedModel.updateFilteredPersonList(combined);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleTags_singlePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(
            List.of(
                Set.of(new Tag("friends"), new Tag("owesMoney")),
                Set.of(new Tag("IT"))
            )
        );
        CombinedPredicate combined = new CombinedPredicate(List.of(predicate));
        FindCommand command = new FindCommand(combined);
        expectedModel.updateFilteredPersonList(combined);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // Only Benson has both 'friends' and 'owesMoney'
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_partialTag_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(
            List.of(
                Set.of(new Tag("fri"))
            )
        );
        CombinedPredicate combined = new CombinedPredicate(List.of(predicate));
        FindCommand command = new FindCommand(combined);
        expectedModel.updateFilteredPersonList(combined);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

}
