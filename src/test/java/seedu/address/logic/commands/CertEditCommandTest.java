package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

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
import seedu.address.testutil.PersonBuilder;

public class CertEditCommandTest {

    @Test
    public void execute_editCertNameAndCertExpirySuccessful() throws Exception {
        ArrayList<Certificate> certList = new ArrayList<Certificate>();
        certList.add(new Certificate(new CertName("cert")));
        Person personWithCert = new PersonBuilder().withName("John").withPhone("+65 88888888")
                .withEmail("john@gmail.com").withAddress("yishun").withSalary("2000")
                .withCertificates(certList).build();
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(personWithCert);
        CertEditCommand certEditCommand = new CertEditCommand(INDEX_FIRST_PERSON,
                new Certificate(new CertName("cert")),
                Optional.<CertName>of(new CertName("OSCP")),
                Optional.<CertExpiry>of(new CertExpiry(LocalDate.parse("2028-03-05"))));
        ArrayList<Certificate> editedCertList = new ArrayList<Certificate>();
        editedCertList.add(new Certificate(new CertName("OSCP"), new CertExpiry(LocalDate.parse("2028-03-05"))));
        Person personWithEditedCert = new PersonBuilder().withName("John").withPhone("+65 88888888")
                .withEmail("john@gmail.com").withAddress("yishun").withSalary("2000")
                .withCertificates(editedCertList).build();
        String expectedMessage = String.format(CertEditCommand.MESSAGE_SUCCESS, Messages.format(personWithEditedCert));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), personWithEditedCert);
        assertCommandSuccess(certEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editCertNameOnlySuccessful() throws Exception {
        ArrayList<Certificate> certList = new ArrayList<Certificate>();
        certList.add(new Certificate(new CertName("cert")));
        Person personWithCert = new PersonBuilder().withName("John").withPhone("+65 88888888")
                .withEmail("john@gmail.com").withAddress("yishun").withSalary("2000")
                .withCertificates(certList).build();
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(personWithCert);
        CertEditCommand certEditCommand = new CertEditCommand(INDEX_FIRST_PERSON,
                new Certificate(new CertName("cert")),
                Optional.<CertName>of(new CertName("OSCP")),
                Optional.<CertExpiry>empty());
        ArrayList<Certificate> editedCertList = new ArrayList<Certificate>();
        editedCertList.add(new Certificate(new CertName("OSCP")));
        Person personWithEditedCert = new PersonBuilder().withName("John").withPhone("+65 88888888")
                .withEmail("john@gmail.com").withAddress("yishun").withSalary("2000")
                .withCertificates(editedCertList).build();
        String expectedMessage = String.format(CertEditCommand.MESSAGE_SUCCESS, Messages.format(personWithEditedCert));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), personWithEditedCert);
        assertCommandSuccess(certEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editCertExpiryOnlySuccessful() throws Exception {
        ArrayList<Certificate> certList = new ArrayList<Certificate>();
        certList.add(new Certificate(new CertName("cert")));
        Person personWithCert = new PersonBuilder().withName("John").withPhone("+65 88888888")
                .withEmail("john@gmail.com").withAddress("yishun").withSalary("2000")
                .withCertificates(certList).build();
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(personWithCert);
        CertEditCommand certEditCommand = new CertEditCommand(INDEX_FIRST_PERSON,
                new Certificate(new CertName("cert")),
                Optional.<CertName>empty(),
                Optional.<CertExpiry>of(new CertExpiry(LocalDate.parse("2028-03-05"))));
        ArrayList<Certificate> editedCertList = new ArrayList<Certificate>();
        editedCertList.add(new Certificate(new CertName("cert"), new CertExpiry(LocalDate.parse("2028-03-05"))));
        Person personWithEditedCert = new PersonBuilder().withName("John").withPhone("+65 88888888")
                .withEmail("john@gmail.com").withAddress("yishun").withSalary("2000")
                .withCertificates(editedCertList).build();
        String expectedMessage = String.format(CertEditCommand.MESSAGE_SUCCESS, Messages.format(personWithEditedCert));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), personWithEditedCert);
        assertCommandSuccess(certEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonexistentCert_throwsCommandException() {
        ArrayList<Certificate> certList = new ArrayList<>();
        Person personWithoutCert = new Person(new Name("John"), new Phone("+65 88888888"),
                new Email("john@gmail.com"), new Address("yishun"), new HashSet<Tag>(),
                new Salary("2000"), certList);
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(personWithoutCert);
        CertEditCommand certEditCommand = new CertEditCommand(INDEX_FIRST_PERSON,
                new Certificate(new CertName("cert")),
                Optional.<CertName>empty(),
                Optional.<CertExpiry>empty());
        assertCommandFailure(certEditCommand, model, CertEditCommand.MESSAGE_MISSING_CERT);
    }

    @Test
    public void execute_editCertToNoExpirySuccessful() throws Exception {
        // setup Person with an existing certificate that has an exp date
        ArrayList<Certificate> certList = new ArrayList<>();
        certList.add(new Certificate(new CertName("Accounting"), new CertExpiry(LocalDate.parse("2020-01-01"))));

        Person personWithCert = new PersonBuilder()
                .withName("John")
                .withCertificates(certList)
                .build();

        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(personWithCert);

        // create cert-edit command to change that cert to "No Expiry" (Optional containing CertExpiry(null))
        CertEditCommand certEditCommand = new CertEditCommand(
                INDEX_FIRST_PERSON,
                new Certificate(new CertName("Accounting")),
                Optional.empty(),
                Optional.of(new CertExpiry(null))
        );

        ArrayList<Certificate> editedCertList = new ArrayList<>();
        editedCertList.add(new Certificate(new CertName("Accounting"), new CertExpiry(null)));

        Person personWithEditedCert = new PersonBuilder()
                .withName("John")
                .withCertificates(editedCertList)
                .build();

        String expectedMessage = String.format(CertEditCommand.MESSAGE_SUCCESS, Messages.format(personWithEditedCert));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), personWithEditedCert);

        assertCommandSuccess(certEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        CertEditCommand certEditCommand = new CertEditCommand(INDEX_FIRST_PERSON,
                new Certificate(new CertName("cert")),
                Optional.<CertName>empty(),
                Optional.<CertExpiry>empty());
        assertCommandFailure(certEditCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_editIndexSpecifiedPerson() throws Exception {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        // create persons with same name
        ArrayList<Certificate> certList = new ArrayList<Certificate>() {
            {
                add(new Certificate(new CertName("OSCP")));
            }
        };
        Person person1 = new PersonBuilder().withName("a").withCertificates(certList).build();
        Person person2 = new PersonBuilder().withName("A").withCertificates(certList).build();
        new AddCommand(person1).execute(model);
        new AddCommand(person2).execute(model);

        // edit certificate of person 2, not person 1
        new CertEditCommand(Index.fromOneBased(2),
                new Certificate(new CertName("OSCP")),
                Optional.of(new CertName("OSCP2")),
                Optional.empty())
                .execute(model);

        assertEquals(model.getFilteredPersonList().get(0), person1);
        ArrayList<Certificate> expectedCertList = new ArrayList<Certificate>() {
            {
                add(new Certificate(new CertName("OSCP2")));
            }
        };
        Person expectedPerson2 = new PersonBuilder().withName("A")
                .withCertificates(expectedCertList)
                .build();
        assertEquals(model.getFilteredPersonList().get(1), expectedPerson2);
    }

    @Test
    public void equals() {
        Certificate cert1 = new Certificate(new CertName("cert1"));
        Certificate cert2 = new Certificate(new CertName("cert2"));
        CertEditCommand certEditCommand = new CertEditCommand(Index.fromOneBased(1), cert1,
            Optional.<CertName>of(new CertName("OSCP")),
            Optional.<CertExpiry>of(new CertExpiry(LocalDate.parse("2028-03-05"))));

        // same object -> returns true
        assertTrue(certEditCommand.equals(certEditCommand));

        // same values -> returns true
        CertEditCommand certEditCommandCopy = new CertEditCommand(Index.fromOneBased(1), cert1,
            Optional.<CertName>of(new CertName("OSCP")),
            Optional.<CertExpiry>of(new CertExpiry(LocalDate.parse("2028-03-05"))));
        assertTrue(certEditCommand.equals(certEditCommandCopy));

        // different types -> returns false
        assertFalse(certEditCommand.equals(1));

        // null -> returns false
        assertFalse(certEditCommand.equals(null));

        // different person -> returns false
        assertFalse(certEditCommand.equals(new CertEditCommand(Index.fromOneBased(2), cert1,
            Optional.<CertName>of(new CertName("OSCP")),
            Optional.<CertExpiry>of(new CertExpiry(LocalDate.parse("2028-03-05"))))));

        // different cert -> returns false
        assertFalse(certEditCommand.equals(new CertEditCommand(Index.fromOneBased(1), cert2,
            Optional.<CertName>of(new CertName("OSCP")),
            Optional.<CertExpiry>of(new CertExpiry(LocalDate.parse("2028-03-05"))))));

        // different new name -> returns false
        assertFalse(certEditCommand.equals(new CertEditCommand(Index.fromOneBased(1), cert1,
            Optional.<CertName>empty(),
            Optional.<CertExpiry>of(new CertExpiry(LocalDate.parse("2028-03-05"))))));

        // different new expiry date -> returns false
        assertFalse(certEditCommand.equals(new CertEditCommand(Index.fromOneBased(1), cert2,
            Optional.<CertName>of(new CertName("OSCP")),
            Optional.<CertExpiry>of(new CertExpiry(LocalDate.parse("2027-03-05"))))));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        CertEditCommand certEditCommand = new CertEditCommand(Index.fromOneBased(1),
            new Certificate(new CertName("cert")),
            Optional.<CertName>of(new CertName("OSCP")),
            Optional.<CertExpiry>of(new CertExpiry(LocalDate.parse("2027-03-05"))));
        String expected = CertEditCommand.class.getCanonicalName() + "{index=" + index
                + ", toEdit=" + new Certificate(new CertName("cert"))
                + ", newName=" + Optional.<CertName>of(new CertName("OSCP"))
                + ", newDate=" + Optional.<CertExpiry>of(new CertExpiry(LocalDate.parse("2027-03-05"))) + "}";
        String actual = certEditCommand.toString();
        System.out.println("Expected: [" + expected + "]");
        System.out.println("Actual:   [" + actual + "]");
        System.out.println("Expected length: " + expected.length());
        System.out.println("Actual length:   " + actual.length());
        assertEquals(expected, certEditCommand.toString());
    }
}
