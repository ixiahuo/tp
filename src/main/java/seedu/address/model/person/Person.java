package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.cert.Certificate;
import seedu.address.model.tag.Tag;

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
    private final Set<Tag> tags = new HashSet<>();
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
        this.tags.addAll(tags);
        this.salary = salary;
        this.certs = new ArrayList<Certificate>();
    }

    /**
     * Overloaded constructor to create a Person without using tags
     * Chains to the main constructor with an empty HashSet.
     */
    public Person(Name name, Phone phone, Email email, Address address, Salary salary) {
        this(name, phone, email, address, new HashSet<>(), salary);
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
        this.tags.addAll(tags);
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
                return otherPerson.getName().equals(getName());
            }
        }

        return otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getName().equals(getName());
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
