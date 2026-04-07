package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cert.CertExpiry;
import seedu.address.model.cert.CertName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColour;
import seedu.address.model.tag.TagSet;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel"; // contains special char @
    private static final String INVALID_PHONE = "+651234"; // no space between country code and phone
    private static final String INVALID_ADDRESS = "9*1 Tah Ching Road"; // contains invalid special char *
    private static final String INVALID_EMAIL = "example.com"; // missing @ char
    private static final String INVALID_TAG = "[friend]";
    private static final String INVALID_TAG_BY_LENGTH = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";


    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "+65 123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "Department";
    private static final String VALID_TAG_2 = "IT";
    private static final String VALID_TAG_3 = "Security";


    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseName_slashWithExtraWhitespace_returnsNormalizedName() throws Exception {
        String nameWithMessySlash = "  John    S    /    O   ";
        Name expectedName = new Name("John S/O");
        assertEquals(expectedName, ParserUtil.parseName(nameWithMessySlash));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullNamePointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null, TagColour.DEFAULT));
    }

    @Test
    public void parseTag_null_throwsNullColourPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag("TEST", null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG, TagColour.DEFAULT));
    }

    @Test
    public void parseTag_invalidLength_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG_BY_LENGTH, TagColour.DEFAULT));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1, TagColour.DEFAULT));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace, TagColour.DEFAULT));
    }

    @Test
    public void parseTags_listWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyList_throwsAssertionError() throws Exception {
        assertThrows(AssertionError.class, () -> ParserUtil.parseTags(Collections.emptyList()));
        assertThrows(AssertionError.class, () -> ParserUtil.parseTags(Collections.emptyList(), List.of("red")));
        assertThrows(AssertionError.class, () -> ParserUtil.parseTags(List.of("tagName"), Collections.emptyList()));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null, List.of("red")));
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(List.of("tagName"), null));
    }

    @Test
    public void parseTags_listWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new TagSet();
        expectedTagSet.add(new Tag(VALID_TAG_1));
        expectedTagSet.add(new Tag(VALID_TAG_2));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseTags_listWithDuplicateTagNames_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_1)));
    }

    @Test
    public void parseTags_listWithValidMultiColour_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2, VALID_TAG_3),
                Arrays.asList("red", "GREEN", "pUrplE"));
        Set<Tag> expectedTagSet = new TagSet();
        expectedTagSet.add(new Tag(VALID_TAG_1, TagColour.RED));
        expectedTagSet.add(new Tag(VALID_TAG_2, TagColour.GREEN));
        expectedTagSet.add(new Tag(VALID_TAG_3, TagColour.PURPLE));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseTags_listWithWrongNumberOfColour_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(
                Arrays.asList(VALID_TAG_1, VALID_TAG_2, VALID_TAG_3), Arrays.asList("red", "green")));

        assertThrows(ParseException.class, () -> ParserUtil.parseTags(
                Arrays.asList(VALID_TAG_1, VALID_TAG_2, VALID_TAG_3), Arrays.asList("red", "green", "purple", "red")));
    }

    @Test
    public void parseTags_listWithInvalidColour_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(
                Arrays.asList(VALID_TAG_1, VALID_TAG_2, VALID_TAG_3), Arrays.asList("red", "green", "Puzle")));
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(
                Arrays.asList(VALID_TAG_1, VALID_TAG_2, VALID_TAG_3), Arrays.asList("red", "grren", "Purple")));
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(
                Arrays.asList(VALID_TAG_1, VALID_TAG_2, VALID_TAG_3), Arrays.asList("redi", "green", "Purple")));

    }

    @Test
    public void parseTags_listWithOneColour_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2, VALID_TAG_3),
                List.of("red"));
        Set<Tag> expectedTagSet = new TagSet();
        expectedTagSet.add(new Tag(VALID_TAG_1, TagColour.RED));
        expectedTagSet.add(new Tag(VALID_TAG_2, TagColour.RED));
        expectedTagSet.add(new Tag(VALID_TAG_3, TagColour.RED));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseTags_listWithDuplicateColour_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2, VALID_TAG_3),
                List.of("green", "green", "green"));
        Set<Tag> expectedTagSet = new TagSet();
        expectedTagSet.add(new Tag(VALID_TAG_1, TagColour.GREEN));
        expectedTagSet.add(new Tag(VALID_TAG_2, TagColour.GREEN));
        expectedTagSet.add(new Tag(VALID_TAG_3, TagColour.GREEN));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseSalary_invalidValue_throwsParseException() {
        // Non-numeric salary
        assertThrows(ParseException.class, () -> ParserUtil.parseSalary("two thousand"));

        // Negative salary
        assertThrows(ParseException.class, () -> ParserUtil.parseSalary("-100"));
    }

    @Test
    public void parseCertName_invalidValue_throwsParseException() {
        String invalidCertName = "OSCP@2026";
        assertThrows(ParseException.class, () -> ParserUtil.parseCertName(invalidCertName));
    }

    @Test
    public void parseCertExpiry_invalidValue_throwsParseException() {
        String invalidDate = "2026-13-45";
        assertThrows(ParseException.class, () -> ParserUtil.parseCertExpiry(invalidDate));
    }

    @Test
    public void normaliseWhiteSpace_nullInput_returnsNull() throws Exception {
        java.lang.reflect.Method method = ParserUtil.class.getDeclaredMethod("normaliseWhiteSpace", String.class);
        method.setAccessible(true);
        Object result = method.invoke(null, (String) null);
        assertNull(result);
    }

    @Test
    public void parseName_extraWhitespace_returnsNormalizedName() throws Exception {
        String nameWithSpaces = "  Mary    Lee  ";
        Name expectedName = new Name("Mary Lee");
        assertEquals(expectedName, ParserUtil.parseName(nameWithSpaces));
    }

    @Test
    public void parsePhone_extraWhitespace_returnsNormalizedPhone() throws Exception {
        String phoneWithSpaces = "   +65    91093749  ";
        Phone expectedPhone = new Phone("+65 91093749");
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithSpaces));
    }

    @Test
    public void parseEmail_extraWhitespace_returnsNormalizedEmail() throws Exception {
        String emailWithSpaces = "  riceMedia@gmail.com  ";
        Email expectedEmail = new Email("riceMedia@gmail.com");
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithSpaces));
    }

    @Test
    public void parseAddress_extraWhitespace_returnsNormalizedAddress() throws Exception {
        String addressWithSpaces = "  Blk   8  #08-109    Tah Ching   Road   S642928   ";
        Address expectedAddress = new Address("Blk 8 #08-109 Tah Ching Road S642928");
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithSpaces));
    }

    @Test
    public void parseSalary_extraWhitespace_returnsNormalizedSalary() throws Exception {
        String salaryWithSpaces = "  8000   ";
        Salary expectedSalary = new Salary("8000");
        assertEquals(expectedSalary, ParserUtil.parseSalary(salaryWithSpaces));
    }

    @Test
    public void parseTags_extraWhitespace_returnsNormalizedTags() throws Exception {
        Set<Tag> expectedTagSet = new TagSet();
        expectedTagSet.add(new Tag("HR"));
        expectedTagSet.add(new Tag("hr"));
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList("  HR     ", "hr  "));
        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseCertName_extraWhitespace_returnsNormalizedCertName() throws Exception {
        String certNameWithSpaces = "  Accounting    Essentials   ";
        CertName expectedCertName = new CertName("Accounting Essentials");
        assertEquals(expectedCertName, ParserUtil.parseCertName(certNameWithSpaces));
    }

    @Test
    public void parseCertExpiry_extraWhitespace_returnsNormalizedCertExpiry() throws Exception {
        String certExpiryWithSpaces = "  2026-02-08   ";
        CertExpiry expectedCertExpiry = new CertExpiry(LocalDate.parse("2026-02-08"));
        assertEquals(expectedCertExpiry, ParserUtil.parseCertExpiry(certExpiryWithSpaces));
    }

    @Test
    public void parsePhone_messyInput_cleansCorrectly() throws Exception {
        String messyPhone = "   +    65  123      45     ";
        String expectedPhone = "+65 123 45";
        assertEquals(new Phone(expectedPhone), ParserUtil.parsePhone(messyPhone));

        // Test no space between + and digits remains same
        assertEquals(new Phone("+65 123"), ParserUtil.parsePhone("+65 123"));
    }

    @Test
    public void parseEmail_internalSpaces_stripsCorrectly() throws Exception {
        String messyEmail = "  ha + 213 @ gmail . com  ";
        String expectedEmail = "ha+213@gmail.com";
        assertEquals(new Email(expectedEmail), ParserUtil.parseEmail(messyEmail));
    }

    @Test
    public void parseSalary_internalSpaces_stripsCorrectly() throws Exception {
        String messySalary = "  5 000  ";
        String expectedSalary = "5000";
        assertEquals(new Salary(expectedSalary), ParserUtil.parseSalary(messySalary));
    }

    @Test
    public void parseSalary_leadingZeros_returnsNormalizedSalary() throws Exception {
        String salaryWithLeadingZeros = "0000004000";
        Salary expectedSalary = new Salary("4000");
        assertEquals(expectedSalary, ParserUtil.parseSalary(salaryWithLeadingZeros));
    }

    @Test
    public void parseSalary_onlyLeadingZeros_returnsNormalizedSalary() throws Exception {
        String salaryWithLeadingZeros = "0000000000";
        Salary expectedSalary = new Salary("0");
        assertEquals(expectedSalary, ParserUtil.parseSalary(salaryWithLeadingZeros));
    }
}
