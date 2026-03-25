package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

public class SortCommandTest {

    @Test
    public void constructor_nullComparator_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortCommand(null));
    }

    @Test
    public void execute_defaultSortSuccessful() throws Exception {
        ModelStubWithPeople modelStub = new ModelStubWithPeople();
        CommandResult commandResult = new SortCommand().execute(modelStub);
        ArrayList<Person> expectedList = new ArrayList<Person>() {
            {
                add(ALICE);
                add(BENSON);
                add(CARL);
                add(DANIEL);
            }
        };
        assertEquals(SortCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertEquals(expectedList, modelStub.personsAdded);
    }

    @Test
    public void equals() {
        // trivial equality test
        SortCommand sortCommand = new SortCommand();
        assertTrue(sortCommand.equals(sortCommand));

        // different comparators -> false
        SortCommand otherSortCommand = new SortCommand(new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                Name name1 = p1.getName();
                Name name2 = p2.getName();
                return name2.compareTo(name1);
            }
        });
        assertFalse(sortCommand.equals(otherSortCommand));

        // different types -> false
        assertFalse(sortCommand.equals(1));
    }

    @Test
    public void toStringMethod() {
        SortCommand sortCommand = new SortCommand();
        String expectedString = SortCommand.class.getCanonicalName();
        assertEquals(expectedString, sortCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sort(Comparator<Person> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndo() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubWithPeople extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>() {
            {
                add(DANIEL);
                add(CARL);
                add(BENSON);
                add(ALICE);
            }
        };

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public void commitAddressBook() {
        }

        @Override
        public void sort(Comparator<Person> comparator) {
            requireNonNull(comparator);
            personsAdded.sort(comparator);
        }
    }
}
