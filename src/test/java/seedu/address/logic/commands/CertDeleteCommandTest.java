package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cert.CertExpiry;
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

public class CertDeleteCommandTest {

    @Test
    public void execute_delCertSuccessful() throws Exception {
        ArrayList<Certificate> certList = new ArrayList<Certificate>();
        certList.add(new Certificate(new CertName("cert")));
        Person personWithCert = new PersonBuilder().withName("John").withPhone("+65 88888888")
                .withEmail("john@gmail.com").withAddress("yishun").withSalary("2000")
                .withCertificates(certList).build();
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(personWithCert);
        CertDeleteCommand certDeleteCommand = new CertDeleteCommand(INDEX_FIRST_PERSON,
                new Certificate(new CertName("cert")));
        Person personWithoutCert = new PersonBuilder().withName("John").withPhone("+65 88888888")
                .withEmail("john@gmail.com").withAddress("yishun").withSalary("2000")
                .withCertificates(new ArrayList<Certificate>()).build();
        String expectedMessage = String.format(CertDeleteCommand.MESSAGE_SUCCESS, Messages.format(personWithoutCert));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), personWithoutCert);
        assertCommandSuccess(certDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_delCertDifferentExpiryDateSuccessful() throws Exception {
        ArrayList<Certificate> certList = new ArrayList<Certificate>();
        certList.add(new Certificate(new CertName("cert"), new CertExpiry(LocalDate.parse("2028-03-05"))));
        Person personWithCert = new PersonBuilder().withName("John").withPhone("+65 88888888")
                .withEmail("john@gmail.com").withAddress("yishun").withSalary("2000")
                .withCertificates(certList).build();
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(personWithCert);
        CertDeleteCommand certDeleteCommand = new CertDeleteCommand(INDEX_FIRST_PERSON,
                new Certificate(new CertName("cert")));
        Person personWithoutCert = new PersonBuilder().withName("John").withPhone("+65 88888888")
                .withEmail("john@gmail.com").withAddress("yishun").withSalary("2000")
                .withCertificates(new ArrayList<Certificate>()).build();
        String expectedMessage = String.format(CertDeleteCommand.MESSAGE_SUCCESS, Messages.format(personWithoutCert));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), personWithoutCert);
        assertCommandSuccess(certDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonexistentCert_throwsCommandException() {
        ArrayList<Certificate> certList = new ArrayList<>();
        Person personWithoutCert = new Person(new Name("John"), new Phone("+65 88888888"),
                new Email("john@gmail.com"), new Address("yishun"), new TreeSet<Tag>(new TagNameComparator()),
                new Salary("2000"), certList);
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(personWithoutCert);
        CertDeleteCommand certDeleteCommand = new CertDeleteCommand(INDEX_FIRST_PERSON,
                new Certificate(new CertName("cert")));
        assertCommandFailure(certDeleteCommand, model, CertDeleteCommand.MESSAGE_MISSING_CERT);
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        CertDeleteCommand certDeleteCommand = new CertDeleteCommand(INDEX_FIRST_PERSON,
                new Certificate(new CertName("cert")));
        assertCommandFailure(certDeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Certificate cert1 = new Certificate(new CertName("cert1"));
        Certificate cert2 = new Certificate(new CertName("cert2"));
        CertDeleteCommand certDeleteCommand = new CertDeleteCommand(Index.fromOneBased(1), cert1);

        // same object -> returns true
        assertTrue(certDeleteCommand.equals(certDeleteCommand));

        // same values -> returns true
        CertDeleteCommand certDeleteCommandCopy = new CertDeleteCommand(Index.fromOneBased(1), cert1);
        assertTrue(certDeleteCommand.equals(certDeleteCommandCopy));

        // different types -> returns false
        assertFalse(certDeleteCommand.equals(1));

        // null -> returns false
        assertFalse(certDeleteCommand.equals(null));

        // different person -> returns false
        assertFalse(certDeleteCommand.equals(new CertDeleteCommand(Index.fromOneBased(2), cert1)));

        // different cert -> returns false
        assertFalse(certDeleteCommand.equals(new CertDeleteCommand(Index.fromOneBased(1), cert2)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        CertDeleteCommand certDeleteCommand = new CertDeleteCommand(index,
                new Certificate(new CertName("cert")));
        String expected = CertDeleteCommand.class.getCanonicalName() + "{index=" + index
                + ", toDel=" + new Certificate(new CertName("cert")) + "}";
        String actual = certDeleteCommand.toString();
        System.out.println("Expected: [" + expected + "]");
        System.out.println("Actual:   [" + actual + "]");
        System.out.println("Expected length: " + expected.length());
        System.out.println("Actual length:   " + actual.length());
        assertEquals(expected, certDeleteCommand.toString());
    }
}
