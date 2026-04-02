package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;

public class SalaryTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Salary(null));
    }

    @Test
    public void constructor_invalidSalary_throwsIllegalArgumentException() {
        String invalidSalary = "two thousand";
        assertThrows(IllegalArgumentException.class, () -> new Salary(invalidSalary));
    }

    @Test
    public void equals_nullAndOtherTypes() {
        Salary salary = new Salary("5000");
        assertFalse(salary.equals(null)); // Tests null path
        assertFalse(salary.equals("5000")); // Tests different type path
    }
    @Test
    public void hashcode() {
        Salary salary = new Salary("5000");
        assertEquals(salary.hashCode(), new Salary("5000").hashCode());
    }

    @Test
    public void isValidSalary() {
        // invalid salary
        assertFalse(Salary.isValidSalary("two thousand")); // non-numeric
        assertFalse(Salary.isValidSalary("9011p")); // alphabets within digits
        assertFalse(Salary.isValidSalary("5 000")); //although ParserUtil will eventually trim internal spaces

        // valid salary
        assertTrue(Salary.isValidSalary(null));
        assertTrue(Salary.isValidSalary(" ")); // spaces only
        assertTrue(Salary.isValidSalary("")); // empty string for optional field
        assertTrue(Salary.isValidSalary("0"));
        assertTrue(Salary.isValidSalary("5000"));
        assertTrue(Salary.isValidSalary("123456789012345")); // long numbers
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
