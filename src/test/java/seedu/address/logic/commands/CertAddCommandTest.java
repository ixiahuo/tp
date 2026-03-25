package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cert.CertName;
import seedu.address.model.cert.Certificate;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagNameComparator;
import seedu.address.testutil.PersonBuilder;

public class CertAddCommandTest {

    @Test
    public void constructor_nullCert_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CertAddCommand(null, null));
    }

    @Test
    public void execute_addCertSuccessful() throws Exception {
        Person personWithoutCert = new PersonBuilder().withName("John").withPhone("+65 88888888")
                .withEmail("john@gmail.com").withAddress("yishun").withSalary("2000").build();
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(personWithoutCert);
        Certificate certToAdd = new Certificate(new CertName("cert"));
        CertAddCommand certAddCommand = new CertAddCommand(INDEX_FIRST_PERSON, certToAdd);
        ArrayList<Certificate> updatedCertList = new ArrayList<>();
        updatedCertList.add(certToAdd);
        Person personWithCert = new PersonBuilder().withName("John").withPhone("+65 88888888")
                .withEmail("john@gmail.com").withAddress("yishun").withSalary("2000")
                .withCertificates(updatedCertList).build();
        String expectedMessage = String.format(CertAddCommand.MESSAGE_SUCCESS, Messages.format(personWithCert));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), personWithCert);
        assertCommandSuccess(certAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCert_throwsCommandException() {
        ArrayList<Certificate> certList = new ArrayList<>();
        certList.add(new Certificate(new CertName("cert")));
        Person personWithCert = new Person(new Name("John"), new Phone("+65 88888888"),
                new Email("john@gmail.com"), new Address("yishun"), new TreeSet<Tag>(new TagNameComparator()),
                new Salary("2000"), certList);
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(personWithCert);
        CertAddCommand certAddCommand = new CertAddCommand(INDEX_FIRST_PERSON,
                new Certificate(new CertName("cert")));
        assertCommandFailure(certAddCommand, model, CertAddCommand.MESSAGE_DUPLICATE_CERT);
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        CertAddCommand certAddCommand = new CertAddCommand(INDEX_FIRST_PERSON,
                new Certificate(new CertName("cert")));
        assertCommandFailure(certAddCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Certificate cert1 = new Certificate(new CertName("cert1"));
        Certificate cert2 = new Certificate(new CertName("cert2"));
        CertAddCommand certAddCommand = new CertAddCommand(Index.fromOneBased(1), cert1);

        // same object -> returns true
        assertTrue(certAddCommand.equals(certAddCommand));

        // same values -> returns true
        CertAddCommand certAddCommandCopy = new CertAddCommand(Index.fromOneBased(1), cert1);
        assertTrue(certAddCommand.equals(certAddCommandCopy));

        // different types -> returns false
        assertFalse(certAddCommand.equals(1));

        // null -> returns false
        assertFalse(certAddCommand.equals(null));

        // different person -> returns false
        assertFalse(certAddCommand.equals(new CertAddCommand(Index.fromOneBased(2), cert1)));

        // different cert -> returns false
        assertFalse(certAddCommand.equals(new CertAddCommand(Index.fromOneBased(1), cert2)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        CertAddCommand certAddCommand = new CertAddCommand(index,
                new Certificate(new CertName("cert")));
        String expected = CertAddCommand.class.getCanonicalName() + "{index=" + index
                + ", toAdd=" + new Certificate(new CertName("cert")) + "}";
        String actual = certAddCommand.toString();
        System.out.println("Expected: [" + expected + "]");
        System.out.println("Actual:   [" + actual + "]");
        System.out.println("Expected length: " + expected.length());
        System.out.println("Actual length:   " + actual.length());
        assertEquals(expected, certAddCommand.toString());
    }
}
