package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.cert.CertExpiry;
import seedu.address.model.cert.CertName;
import seedu.address.model.cert.Certificate;
import seedu.address.testutil.PersonBuilder;

/**
 * Tests that a {@code Person}'s list of {@code Certificate}s matches the date given.
 */
public class CertContainsDatePredicateTest {
    @Test
    public void test_compareExpiryDatesAfter_returnsFalse() {
        CertExpiry dateToCompare = new CertExpiry(LocalDate.parse("2020-03-04"));
        Predicate<Person> predicate = new CertContainsDatePredicate(dateToCompare);

        Certificate futureDay = new Certificate(new CertName("A"), new CertExpiry(LocalDate.parse("2020-03-05")));
        Certificate futureMonth = new Certificate(new CertName("A"), new CertExpiry(LocalDate.parse("2020-04-02")));
        Certificate futureYear = new Certificate(new CertName("A"), new CertExpiry(LocalDate.parse("2021-04-02")));

        // Test on dates after
        assertFalse(predicate.test(new PersonBuilder().withCertificates(new ArrayList<Certificate>(
                List.of(futureDay, futureMonth, futureYear))).build()));
    }

    @Test
    public void test_compareDatesAfter_returnsTrue() {
        CertExpiry dateToCompare = new CertExpiry(LocalDate.parse("2020-03-04"));
        Predicate<Person> predicate = new CertContainsDatePredicate(dateToCompare);

        Certificate pastDay = new Certificate(new CertName("A"), new CertExpiry(LocalDate.parse("2020-03-02")));
        Certificate pastMonth = new Certificate(new CertName("A"), new CertExpiry(LocalDate.parse("2020-02-02")));
        Certificate pastYear = new Certificate(new CertName("A"), new CertExpiry(LocalDate.parse("2019-03-02")));
        Certificate futureDate = new Certificate(new CertName("A"), new CertExpiry(LocalDate.parse("2020-06-01")));


        assertTrue(predicate.test(new PersonBuilder().withCertificates(new ArrayList<Certificate>(
                List.of(pastDay))).build()));

        assertTrue(predicate.test(new PersonBuilder().withCertificates(new ArrayList<Certificate>(
                List.of(pastMonth))).build()));

        assertTrue(predicate.test(new PersonBuilder().withCertificates(new ArrayList<Certificate>(
                List.of(pastYear))).build()));

        assertTrue(predicate.test(new PersonBuilder().withCertificates(new ArrayList<Certificate>(
                List.of(pastYear, futureDate))).build()));

        assertTrue(predicate.test(new PersonBuilder().withCertificates(new ArrayList<Certificate>(
                List.of(futureDate, pastYear))).build()));
    }

    @Test
    public void test_personNoCertificate_returnsFalse() {
        CertExpiry dateToCompare = new CertExpiry(LocalDate.parse("2020-03-04"));
        Predicate<Person> predicate = new CertContainsDatePredicate(dateToCompare);

        assertFalse(predicate.test(new PersonBuilder().withCertificates(new ArrayList<Certificate>(
                List.of())).build()));
    }

    @Test
    public void test_compareSameDate_returnsFalse() {
        CertExpiry dateToCompare = new CertExpiry(LocalDate.parse("2020-03-04"));
        Predicate<Person> predicate = new CertContainsDatePredicate(dateToCompare);
        Certificate sameDate = new Certificate(new CertName("A"), dateToCompare);

        assertFalse(predicate.test(new PersonBuilder().withCertificates(new ArrayList<Certificate>(
                List.of(sameDate))).build()));
    }

    @Test
    public void test_noExpiryCert_returnsFalse() {
        // Searching for certificates expired before 2026-01-01
        CertContainsDatePredicate predicate =
                new CertContainsDatePredicate(new CertExpiry(LocalDate.parse("2026-01-01")));

        // Person has a certificate with NO expiry date
        Certificate permanentCert = new Certificate(new CertName("Safety 101"), new CertExpiry(null));
        Person person = new PersonBuilder().withName("Alice")
                .withCertificates(new ArrayList<Certificate>(List.of(permanentCert))).build();

        // return false since a permanent cert never expires
        assertFalse(predicate.test(person));
    }

    @Test
    public void equals() {
        CertExpiry firstDate = new CertExpiry(LocalDate.parse("2020-03-04"));
        CertExpiry secondDate = new CertExpiry(LocalDate.parse("2020-05-05"));

        CertContainsDatePredicate firstPredicate = new CertContainsDatePredicate(firstDate);
        CertContainsDatePredicate secondPredicate = new CertContainsDatePredicate(secondDate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CertContainsDatePredicate firstPredicateCopy = new CertContainsDatePredicate(firstDate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different date -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void toStringMethod() {
        CertExpiry dateToCompare = new CertExpiry(LocalDate.parse("2020-03-04"));
        CertContainsDatePredicate predicate = new CertContainsDatePredicate(dateToCompare);
        String expected = CertContainsDatePredicate.class.getCanonicalName() + "{expiry date=" + dateToCompare + "}";
        assertEquals(expected, predicate.toString());
    }
}
