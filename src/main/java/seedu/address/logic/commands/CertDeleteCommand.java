package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
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
 * Deletes a certificate from a person in the address book.
 */
public class CertDeleteCommand extends Command {
    public static final String COMMAND_WORD = "cert-del";

    public static final String MESSAGE_USAGE = String.format(
            "%s : deletes a certificate from an existing contact according to the currently displayed list\n\n"
            + "Format : %s INDEX %sCERT_NAME\n"
            + "CERT_NAME is case-sensitive\n"
            + "Example : %s 2 %sSocial Media Marketing",
            COMMAND_WORD,
            COMMAND_WORD, PREFIX_CERT_NAME,
            COMMAND_WORD, PREFIX_CERT_NAME);

    public static final String MESSAGE_SUCCESS = "Certificate deleted: %1$s";
    public static final String MESSAGE_MISSING_CERT = "This person does not have this certificate.";

    private final Index index;
    private final Certificate toDel;

    /**
     * Creates a CertDeleteCommand to deletes the specified {@code Certificate}
     */
    public CertDeleteCommand(Index index, Certificate toDel) {
        requireAllNonNull(index, toDel);
        this.index = index;
        this.toDel = toDel;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDeleteFrom = lastShownList.get(index.getZeroBased());

        if (!personToDeleteFrom.hasCert(toDel)) {
            throw new CommandException(MESSAGE_MISSING_CERT);
        }
        model.commitAddressBook();

        Person personDeletedFrom = deleteCertFromPerson(personToDeleteFrom, toDel);

        model.setPerson(personToDeleteFrom, personDeletedFrom);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personDeletedFrom)));
    }

    /**
     * Creates a new Person object with the specified certificate removed from it.
     * @param personToDeleteFrom Person to delete the Certificate from.
     * @param toDel Certificate to be deleted.
     * @return Updated Person object.
     */
    private static Person deleteCertFromPerson(Person personToDeleteFrom, Certificate toDel) {
        assert personToDeleteFrom != null;

        Name name = personToDeleteFrom.getName();
        Phone phone = personToDeleteFrom.getPhone();
        Email email = personToDeleteFrom.getEmail();
        Address address = personToDeleteFrom.getAddress();
        Set<Tag> tags = personToDeleteFrom.getTags();
        Salary salary = personToDeleteFrom.getSalary();
        ArrayList<Certificate> certs = new ArrayList<>(personToDeleteFrom.getCertificates());
        int certIndex = personToDeleteFrom.getCertIndex(toDel);
        certs.remove(certIndex);

        return new Person(name, phone, email, address, tags, salary, certs);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CertDeleteCommand)) {
            return false;
        }

        CertDeleteCommand otherCertDeleteCommand = (CertDeleteCommand) other;
        return index.equals(otherCertDeleteCommand.index)
                && toDel.equals(otherCertDeleteCommand.toDel);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("toDel", toDel)
                .toString();
    }
}
