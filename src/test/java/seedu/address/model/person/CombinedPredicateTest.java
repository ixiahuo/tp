package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.cert.CertExpiry;
import seedu.address.model.cert.CertName;
import seedu.address.model.cert.Certificate;
import seedu.address.testutil.PersonBuilder;

public class CombinedPredicateTest {

    @Test
    public void test_combinedPredicates_returnsTrueOnlyIfAllMatch() {
        CertExpiry dateToCompare = new CertExpiry(LocalDate.parse("2020-03-04"));
        CertContainsDatePredicate datePredicate = new CertContainsDatePredicate(dateToCompare);

        ArrayList<Certificate> keywords = new ArrayList<>(List.of(new Certificate(new CertName("First Aid"))));
        CertContainsKeywordsPredicate keywordPredicate = new CertContainsKeywordsPredicate(keywords);

        CombinedPredicate combinedPredicate = new CombinedPredicate(Arrays.asList(datePredicate, keywordPredicate));

        // Person has both matching certificate name and a date before the comparison date -> returns true
        Certificate matchingCert = new Certificate(new CertName("First Aid"),
            new CertExpiry(LocalDate.parse("2019-01-01")));
        assertTrue(combinedPredicate.test(new PersonBuilder().withCertificates(new ArrayList<>(
            List.of(matchingCert))).build()));
    }

    @Test
    public void test_combinedPredicates_returnsFalseIfAnyOneDoesNotMatch() {
        CertExpiry dateToCompare = new CertExpiry(LocalDate.parse("2020-03-04"));
        CertContainsDatePredicate datePredicate = new CertContainsDatePredicate(dateToCompare);

        ArrayList<Certificate> keywords = new ArrayList<>(
            List.of(new Certificate(new CertName("First Aid"), null)));
        CertContainsKeywordsPredicate keywordPredicate = new CertContainsKeywordsPredicate(keywords);

        CombinedPredicate combinedPredicate = new CombinedPredicate(Arrays.asList(datePredicate, keywordPredicate));

        // Matches keyword but date is NOT before comparison date -> returns false
        Certificate wrongDateCert = new Certificate(new CertName("First Aid"),
            new CertExpiry(LocalDate.parse("2021-01-01")));
        assertFalse(combinedPredicate.test(new PersonBuilder().withCertificates(new ArrayList<>(
            List.of(wrongDateCert))).build()));

        // Matches date but name is NOT matching keyword -> returns false
        Certificate wrongNameCert = new Certificate(new CertName("Social"),
            new CertExpiry(LocalDate.parse("2019-01-01")));
        assertFalse(combinedPredicate.test(new PersonBuilder().withCertificates(new ArrayList<>(
            List.of(wrongNameCert))).build()));
    }

    @Test
    public void equals() {
        Predicate<Person> firstPredicate = p -> true;
        Predicate<Person> secondPredicate = p -> false;

        List<Predicate<Person>> firstPredicateList = List.of(firstPredicate);
        List<Predicate<Person>> secondPredicateList = List.of(secondPredicate);

        CombinedPredicate firstCombinedPredicate = new CombinedPredicate(firstPredicateList);
        CombinedPredicate secondCombinedPredicate = new CombinedPredicate(secondPredicateList);

        // same object -> returns true
        assertTrue(firstCombinedPredicate.equals(firstCombinedPredicate));

        // same values -> returns true
        CombinedPredicate firstCombinedPredicateCopy = new CombinedPredicate(firstPredicateList);
        assertTrue(firstCombinedPredicate.equals(firstCombinedPredicateCopy));

        // different types -> returns false
        assertFalse(firstCombinedPredicate.equals(1));

        // null -> returns false
        assertFalse(firstCombinedPredicate.equals(null));

        // different predicates -> returns false
        assertFalse(firstCombinedPredicate.equals(secondCombinedPredicate));
    }

    @Test
    public void toStringMethod() {
        List<Predicate<Person>> predicates = Arrays.asList(p -> true, p -> false);
        CombinedPredicate combinedPredicate = new CombinedPredicate(predicates);
        String expected = CombinedPredicate.class.getCanonicalName() + "{keywords=" + predicates + "}";
        assertEquals(expected, combinedPredicate.toString());
    }
}
