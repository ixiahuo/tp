package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_EXPIRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cert.Certificate;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.tag.Tag;

/**
 * Adds a certificate to a person in the address book.
 */
public class CertAddCommand extends Command {
    public static final String COMMAND_WORD = "cert-add";

    public static final String MESSAGE_USAGE = String.format(
            "%s : adds a certificate to an existing contact according to the currently displayed list\n\n"
            + "Format : %s INDEX %sCERT_NAME %sCERT_EXPIRY\n"
            + "Example : %s 2 %sSocial Media Marketing %s2028-06-03",
            COMMAND_WORD,
            COMMAND_WORD, PREFIX_CERT_NAME, PREFIX_CERT_EXPIRY,
            COMMAND_WORD, PREFIX_CERT_NAME, PREFIX_CERT_EXPIRY);

    public static final String MESSAGE_SUCCESS = "New certificate added: %1$s";
    public static final String MESSAGE_DUPLICATE_CERT = "This person already has this certificate.";

    private final Index index;
    private final Certificate toAdd;

    /**
     * Creates a CertAddCommand to add the specified {@code Certificate}
     */
    public CertAddCommand(Index index, Certificate cert) {
        requireAllNonNull(index, cert);
        this.index = index;
        this.toAdd = cert;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        model.commitAddressBook();

        Person personToAddTo = lastShownList.get(index.getZeroBased());

        if (personToAddTo.hasCert(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CERT);
        }
        Person personAddedTo = addCertToPerson(personToAddTo, toAdd);

        model.setPerson(personToAddTo, personAddedTo);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personAddedTo)));
    }

    private static Person addCertToPerson(Person personToAddTo, Certificate cert) {
        assert personToAddTo != null;

        Name name = personToAddTo.getName();
        Phone phone = personToAddTo.getPhone();
        Email email = personToAddTo.getEmail();
        Address address = personToAddTo.getAddress();
        Set<Tag> tags = personToAddTo.getTags();
        Salary salary = personToAddTo.getSalary();
        ArrayList<Certificate> certs = new ArrayList<>(personToAddTo.getCertificates());
        certs.add(cert);

        return new Person(name, phone, email, address, tags, salary, certs);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CertAddCommand)) {
            return false;
        }

        CertAddCommand otherCertAddCommand = (CertAddCommand) other;
        return toAdd.equals(otherCertAddCommand.toAdd) && index.equals(otherCertAddCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("toAdd", toAdd)
                .toString();
    }
}
