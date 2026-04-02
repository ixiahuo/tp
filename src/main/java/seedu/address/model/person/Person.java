package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.cert.Certificate;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagNameComparator;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags;
    private final Salary salary;
    private ArrayList<Certificate> certs;

    /**
     * Every field except certificates must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Salary salary) {
        requireAllNonNull(name, phone, email, address, tags, salary);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags = tags;
        this.salary = salary;
        this.certs = new ArrayList<Certificate>();
    }

    /**
     * Minimalist constructor: Only Name is strictly required.
     * All other fields are initialized as empty.
     */
    public Person(Name name) {
        requireAllNonNull(name);
        this.name = name;
        this.phone = new Phone("");
        this.email = new Email("");
        this.address = new Address("");
        this.tags = Set.of();
        this.salary = new Salary("");
        this.certs = new ArrayList<Certificate>();
    }

    /**
     * Overloaded constructor to create a Person without using tags
     * Chains to the main constructor with an empty HashSet.
     */
    public Person(Name name, Phone phone, Email email, Address address, Salary salary) {
        this(name, phone, email, address, new TreeSet<>(new TagNameComparator()), salary);
    }

    /**
     * Overloaded constructor to create a Person with existing certificates.
     */
    public Person(Name name, Phone phone,
                Email email, Address address, Set<Tag> tags,
                Salary salary, ArrayList<Certificate> certs) {
        requireAllNonNull(name, phone, email, address, tags, salary);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags = tags;
        this.salary = salary;
        this.certs = certs;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Salary getSalary() {
        return salary;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public ArrayList<Certificate> getCertificates() {
        return certs;
    }

    /**
     * Checks if this person already has this certificate.
     * @param cert Certificate to be checked against.
     * @return true if the certificate exists in this person's list of certificates, false otherwise.
     */
    public boolean hasCert(Certificate cert) {
        return (this.certs.stream()
                .filter(x -> x.isSameCert(cert))
                .map(x -> 1)
                .reduce(0, (x, y) -> x + y) >= 1);
    }

    /**
     * Gets the index of a Certificate within this.certs
     * @param cert Certificate to find index of
     * @return index of cert within this.certs
     */
    public int getCertIndex(Certificate cert) {
        for (int i = 0; i < this.certs.size(); i++) {
            if (certs.get(i).isSameCert(cert)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns true if both persons have the same phone and email.
     * This defines a stronger notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        if (otherPerson == null) {
            return false;
        }

        boolean p1HasPhone = !getPhone().value.isEmpty();
        boolean p1HasEmail = !getEmail().value.isEmpty();
        boolean p2HasPhone = !otherPerson.getPhone().value.isEmpty();
        boolean p2HasEmail = !otherPerson.getEmail().value.isEmpty();

        // both persons have no email and no phone, check name
        if (!p1HasPhone && !p2HasPhone) {
            if (!p1HasEmail && !p2HasEmail) {
                return getName().equals(otherPerson.getName());
            }
        }

        return otherPerson.getName().equals(getName())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && salary.equals(otherPerson.salary)
                && certs.equals(otherPerson.certs);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, salary, certs);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("salary", salary)
                .add("certificates", certs)
                .toString();
    }

}
