package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_SALARY = new Prefix("s/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_CERT = new Prefix("c/"); // Used in find

    public static final Prefix PREFIX_ADD_TAG = new Prefix("a/");
    public static final Prefix PREFIX_DELETE_TAG = new Prefix("d/");

    /* Prefix definitions for cert command */
    public static final Prefix PREFIX_CERT_NAME = new Prefix("n/");
    public static final Prefix PREFIX_CERT_EXPIRY = new Prefix("e/");
    public static final Prefix PREFIX_CERT_EDIT_NAME = new Prefix("ne/");
    public static final Prefix PREFIX_CERT_EDIT_DATE = new Prefix("ee/");
}
