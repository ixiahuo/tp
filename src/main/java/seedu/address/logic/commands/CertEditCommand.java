package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_EDIT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_EDIT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
 * Adds a certificate to a person in the address book.
 */
public class CertEditCommand extends Command {
    public static final String COMMAND_WORD = "cert-edit";

    public static final String MESSAGE_USAGE = String.format(
            "%s : edits a certificate of an existing contact according to the currently displayed list\n\n"
            + "Format : %s INDEX %sTARGET_CERT_NAME [%sNEW_CERT_NAME] [%sNEW_EXPIRY_DATE]\n"
            + "Example : %s 2 %sSocial Media Marketing %s2028-03-05",
            COMMAND_WORD,
            COMMAND_WORD, PREFIX_CERT_NAME, PREFIX_CERT_EDIT_NAME, PREFIX_CERT_EDIT_DATE,
            COMMAND_WORD, PREFIX_CERT_NAME, PREFIX_CERT_EDIT_DATE);

    public static final String MESSAGE_SUCCESS = "Certificate edited: %1$s";
    public static final String MESSAGE_MISSING_CERT = "This person does not have this certificate.";
    public static final String MESSAGE_DUPLICATE_CERT = "This person already has this certificate.";


    private final Index index;
    private final Certificate toEdit;
    private final Optional<CertName> newName;
    private final Optional<CertExpiry> newDate;

    /**
     * Creates a CertEditCommand to edit the specified {@code Certificate} cert.
     */
    public CertEditCommand(Index index, Certificate cert,
            Optional<CertName> newName, Optional<CertExpiry> newDate) {
        this.index = index;
        this.toEdit = cert;
        this.newName = newName;
        this.newDate = newDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        model.commitAddressBook();

        Person personToEdit = lastShownList.get(index.getZeroBased());

        if (!personToEdit.hasCert(toEdit)) {
            throw new CommandException(MESSAGE_MISSING_CERT);
        }

        // get the actual certificate-to-edit
        Certificate actualToEdit = personToEdit.getCertificates()
                .get(personToEdit.getCertIndex(toEdit));

        Certificate updatedCert = this.getUpdatedCert(actualToEdit);

        if (newName.isPresent() && personToEdit.hasCert(updatedCert)) {
            throw new CommandException(MESSAGE_DUPLICATE_CERT);
        }

        Person personEdited = editCertForPerson(personToEdit, toEdit, updatedCert);

        model.setPerson(personToEdit, personEdited);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personEdited)));
    }

    /**
     * Generates the updated Certificate from the information provided.
     * If new values are not supplied, the old values will be reused.
     * @return Certificate with the edited information.
     */
    private Certificate getUpdatedCert(Certificate actualToEdit) {
        CertName updatedName = this.newName.orElse(actualToEdit.getName());
        CertExpiry updatedExpiry = this.newDate.orElse(actualToEdit.getExpiry());
        Certificate updatedCert = new Certificate(updatedName, updatedExpiry);
        return updatedCert;
    }

    private static Person editCertForPerson(Person personToEdit,
            Certificate toEdit, Certificate updatedCert) throws CommandException {
        assert personToEdit != null;
        // find the index of toEdit in the person's list of certificates
        ArrayList<Certificate> certList = new ArrayList<Certificate>(personToEdit.getCertificates());
        int index = -1;
        for (int i = 0; i < certList.size(); i++) {
            if (toEdit.isSameCert(certList.get(i))) {
                index = i;
                break;
            }
        }

        if (index < 0) {
            throw new CommandException(MESSAGE_MISSING_CERT);
        }
        // set the new cert
        certList.set(index, updatedCert);

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Set<Tag> tags = personToEdit.getTags();
        Salary salary = personToEdit.getSalary();
        return new Person(name, phone, email, address, tags, salary, certList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CertEditCommand)) {
            return false;
        }

        CertEditCommand otherCertEditCommand = (CertEditCommand) other;
        return toEdit.equals(otherCertEditCommand.toEdit)
                && index.equals(otherCertEditCommand.index)
                && newName.equals(otherCertEditCommand.newName)
                && newDate.equals(otherCertEditCommand.newDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("toEdit", toEdit)
                .add("newName", newName)
                .add("newDate", newDate)
                .toString();
    }
}
