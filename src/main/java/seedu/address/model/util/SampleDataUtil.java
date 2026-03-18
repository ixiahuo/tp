package seedu.address.model.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
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

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        ArrayList<Certificate> oscpCert = new ArrayList<>();
        oscpCert.add(new Certificate(
                new CertName("OSCP Plus"),
                new CertExpiry(LocalDate.parse("2028-12-31"))));

        ArrayList<Certificate> burpCert = new ArrayList<>();
        burpCert.add(new Certificate(
                new CertName("Burp Suite Certified Practitioner"),
                new CertExpiry(LocalDate.parse("2027-06-21"))));

        return new Person[] {
            new Person(
                    new Name("John Kler"),
                    new Phone("+65 81234567"),
                    new Email("johnkler@example.co"),
                    new Address("123D Pine Road, #12-345, Singapore 123456"),
                    getTagSet("Pentester", "AD"),
                    new Salary("6500"),
                    oscpCert),
            new Person(
                    new Name("John Doe"),
                    new Phone("+65 87654321"),
                    new Email("johndoe@example.co"),
                    new Address("321D Einp Road, #54-321, Singapore 654321"),
                    getTagSet("Pentester", "Web"),
                    new Salary("6500"),
                    burpCert),
            new Person(
                    new Name("Jane Do"),
                    new Phone("+65 84321765"),
                    new Email("janedo@example.co"),
                    new Address("987A Nepi Road, #21-543, Singapore 321654"),
                    getTagSet("Intern"),
                    new Salary("1300")),
            new Person(
                    new Name("Johny Doeh"),
                    new Phone("+65 81357246"),
                    new Email("johnydoeh@example.co"),
                    new Address("654B Enpi Road, #45-123, Singapore 246135"),
                    getTagSet("Intern"),
                    new Salary("1300"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
