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
        ArrayList<Certificate> certs = new ArrayList<>();
        certs.add(new Certificate(
                new CertName("OSCP"),
                new CertExpiry(LocalDate.parse("2028-12-31"))));

        return new Person[] {
            new Person(
                    new Name("John Kler"),
                    new Phone("+65 81234567"),
                    new Email("johnkler@example.co"),
                    new Address("123D Pine Road, #12-345, Singpore 123456"),
                    getTagSet("Pentester", "AD"),
                    new Salary("6500"),
                    certs)
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
