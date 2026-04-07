package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
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

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_NO_INDEX = "Index not provided.";

    public static final String MESSAGE_DUPLICATE_TAGNAME = "Duplicate tag names are not allowed!";
    public static final String MESSAGE_TAGNAME_NUMBER_AND_COLOUR_NUMBERS_DIFF = "The number of tags to add must match"
            + " the number of colours specified (Unless all tags are to be the same colour, in that case,"
            + " specify only ONE colour)";

    private static final Logger logger = LogsCenter.getLogger(TagCommandParser.class);

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * Extra internal whitespaces will be reduced to one.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = normaliseWhiteSpace(oneBasedIndex);
        if (trimmedIndex.equals("")) {
            throw new ParseException(MESSAGE_NO_INDEX);
        }
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     * Extra internal whitespaces will be reduced to one.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = normaliseWhiteSpace(name);
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading, trailing, and redundant internal whitespaces will be trimmed to single spaces.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        // 1. Trim edges and collapse all internal whitespace to single spaces
        // "+  65   123    45" --> "+ 65 123 45"
        String basicClean = phone.trim().replaceAll("\\s+", " ");

        // 2. Remove the space between '+' and the country code digits
        // "+ 65 123 45" --> "+65 123 45"
        String formattedPhone = basicClean.replaceAll("^\\+\\s+(?=\\d)", "+");

        if (!Phone.isValidPhone(formattedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(formattedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     * Extra internal whitespaces will be reduced to one.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = normaliseWhiteSpace(address);
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading, trailing, and ALL internal whitespaces will be removed.
     * e.g., " ha + 213 @ gmail . com " -> "ha+213@gmail.com"
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.replaceAll("\\s+", "");
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String salary} into an {@code Salary}.
     * Leading, trailing, and ALL internal whitespaces will be removed.
     * e.g., " 5 000 " -> "5000"
     *
     * @throws ParseException if the given {@code salary} is invalid.
     */
    public static Salary parseSalary(String salary) throws ParseException {
        requireNonNull(salary);
        String trimmedSalary = salary.replaceAll("\\s+", "");
        String noLeadingZeroes = trimmedSalary.replaceFirst("^0+(?!$)", "");
        if (!Salary.isValidSalary(noLeadingZeroes)) {
            throw new ParseException(Salary.MESSAGE_CONSTRAINTS);
        }
        return new Salary(noLeadingZeroes);
    }

    /**
     * Parses a {@code String tagName} and {@code TagColour } into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     * Extra internal whitespaces will be reduced to one.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tagName, TagColour tagColour) throws ParseException {
        requireNonNull(tagName);
        requireNonNull(tagColour);

        String trimmedTag = normaliseWhiteSpace(tagName);

        logger.finest("Attempting to create Tag: " + trimmedTag + " " + tagColour.name());
        if (!Tag.isValidTagName(trimmedTag)) {
            logger.finer("Invalid TagName " + trimmedTag);
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        if (!Tag.isValidTagLength(trimmedTag)) {
            logger.finer("Invalid Tag Length " + trimmedTag);
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS_LENGTH);
        }
        return new Tag(trimmedTag, tagColour);
    }

    /**
     * Parses a {@code String tagName} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     * Extra internal whitespaces will be reduced to one.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tagName) throws ParseException {
        requireNonNull(tagName);
        return new Tag(tagName, TagColour.DEFAULT);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(List<String> tags) throws ParseException {
        return parseTags(tags, TagColour.DEFAULT);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(List<String> tags, List<String> userInputTagColours) throws ParseException {
        requireNonNull(tags);
        requireNonNull(userInputTagColours);
        assert(!tags.isEmpty());
        assert(!userInputTagColours.isEmpty());

        if (hasDuplicateTags(tags)) {
            logger.finer("Duplicate Tags specified");
            throw new ParseException(MESSAGE_DUPLICATE_TAGNAME);
        }

        boolean isUsingOneColour = (userInputTagColours.size() == 1);
        logger.finest("Detect only one colour?: " + isUsingOneColour);

        Optional<TagColour> currentColour = TagColour.getTagColourByUserInputName(userInputTagColours.get(0));
        if (isUsingOneColour && currentColour.isPresent()) {
            logger.finest("Detected only one valid colour. " + currentColour.get());
            return parseTags(tags, currentColour.get());
        }

        if (userInputTagColours.size() != tags.size()) {
            logger.finer("Invalid number of colours. Number of colours specified: "
                    + userInputTagColours.size() + "\nNumber of tags specified: " + tags.size());
            throw new ParseException(MESSAGE_TAGNAME_NUMBER_AND_COLOUR_NUMBERS_DIFF);
        }

        String colourName;
        final Set<Tag> tagSet = new TagSet();
        for (int i = 0; i < tags.size(); i += 1) {
            colourName = userInputTagColours.get(i);
            currentColour = TagColour.getTagColourByUserInputName(colourName);

            if (currentColour.isEmpty()) {
                logger.finer("Invalid Colour Detected: " + colourName);
                throw new ParseException(TagColour.MESSAGE_INVALID_COLOUR);
            }

            tagSet.add(parseTag(tags.get(i), currentColour.get()));
        }

        logger.finer("Colours successfully parsed");
        return tagSet;
    }

    private static Set<Tag> parseTags(List<String> tags, TagColour tagColour) throws ParseException {
        requireNonNull(tags);
        assert(!tags.isEmpty());

        if (hasDuplicateTags(tags)) {
            throw new ParseException(MESSAGE_DUPLICATE_TAGNAME);
        }

        final Set<Tag> tagSet = new TagSet();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName, tagColour));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String name} into a {@code CertName}.
     * Leading and trailing whitespaces will be trimmed.
     * Extra internal whitespaces will be reduced to one.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static CertName parseCertName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = normaliseWhiteSpace(name);
        if (!CertName.isValidCertName(trimmedName)) {
            throw new ParseException(CertName.MESSAGE_CONSTRAINTS);
        }
        return new CertName(trimmedName);
    }

    /**
     * Parses a {@code String date} into a {@code CertExpiry}.
     * Leading and trailing whitespaces will be trimmed.
     * Extra internal whitespaces will be reduced to one.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static CertExpiry parseCertExpiry(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = normaliseWhiteSpace(date);
        if (!CertExpiry.isValidCertExpiry(trimmedDate)) {
            throw new ParseException(CertExpiry.MESSAGE_CONSTRAINTS);
        }
        return new CertExpiry(LocalDate.parse(trimmedDate));
    }

    /**
     * Trims leading/trailing whitespace and reduces internal whitespace
     * to a single space.
     */
    private static String normaliseWhiteSpace(String s) {
        if (s == null) {
            return null;
        }
        //standardize internal whitespace to single spaces
        String basicNormalized = s.trim().replaceAll("\\s+", " ");

        //remove space between '+' and the COUNTRY_CODE
        //"+ 65 123 33" -> "+65 123 33"
        String countryCodeFixed = basicNormalized.replaceAll("^\\+\\s+(?=\\d)", "+");

        //remove spaces around forward slashes (eg "S / O" -> "S/O")
        return basicNormalized.replaceAll("\\s*/\\s*", "/");
    }

    private static boolean hasDuplicateTags(List<String> tagNames) {
        Set<String> distinctTagNames = new HashSet<>();
        for (String tagName : tagNames) {
            if (!distinctTagNames.add(tagName)) {
                return true;
            }
        }
        return false;
    }
}
