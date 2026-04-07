package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.cert.CertName;
import seedu.address.model.cert.Certificate;
import seedu.address.testutil.PersonBuilder;

/**
 * Tests that a {@code Person}'s list of {@code Certificate}s matches any of the keywords given.
 */
public class CertContainsKeywordsPredicateTest {

    @Test
    public void test_certNameContainsKeywords_returnsTrue() {
        // One keyword
        CertContainsKeywordsPredicate predicate = new CertContainsKeywordsPredicate(new ArrayList<>(
            List.of(new Certificate(new CertName("First Aid")))));
        assertTrue(predicate.test(new PersonBuilder().withCertificates(
            new ArrayList<>(List.of(new Certificate(new CertName("First Aid"))))).build()));

        // Case-insensitive matching
        predicate = new CertContainsKeywordsPredicate(new ArrayList<>(
            List.of(new Certificate(new CertName("first aid")))));
        assertTrue(predicate.test(new PersonBuilder().withCertificates(
            new ArrayList<>(List.of(new Certificate(new CertName("FIRST AID"))))).build()));

        // Multiple keywords, only one matches
        predicate = new CertContainsKeywordsPredicate(new ArrayList<>(Arrays.asList(
            new Certificate(new CertName("First Aid")),
            new Certificate(new CertName("Accounting")))));
        assertTrue(predicate.test(new PersonBuilder().withCertificates(
            new ArrayList<>(List.of(new Certificate(new CertName("Accounting"))))).build()));
    }

    @Test
    public void test_certNameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        CertContainsKeywordsPredicate predicate = new CertContainsKeywordsPredicate(new ArrayList<>(
                List.of(new Certificate(new CertName("First Aid"), null))));
        assertFalse(predicate.test(new PersonBuilder().withCertificates(
            new ArrayList<>(List.of(new Certificate(new CertName("Accounting"), null)))).build()));

        // Person with no certificates
        predicate = new CertContainsKeywordsPredicate(new ArrayList<>(
            List.of(new Certificate(new CertName("First Aid"), null))));
        assertFalse(predicate.test(new PersonBuilder().withCertificates(new ArrayList<>()).build()));
    }

    @Test
    public void equals() {
        ArrayList<Certificate> firstPredicateKeywordList = new ArrayList<>(
            List.of(new Certificate(new CertName("first"), null)));
        ArrayList<Certificate> secondPredicateKeywordList = new ArrayList<>(
            List.of(new Certificate(new CertName("second"), null)));

        CertContainsKeywordsPredicate firstPredicate = new CertContainsKeywordsPredicate(firstPredicateKeywordList);
        CertContainsKeywordsPredicate secondPredicate = new CertContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CertContainsKeywordsPredicate firstPredicateCopy = new CertContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void toStringMethod() {
        ArrayList<Certificate> keywords = new ArrayList<>(List.of(new Certificate(new CertName("keyword"), null)));
        CertContainsKeywordsPredicate predicate = new CertContainsKeywordsPredicate(keywords);
        String expected = CertContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
