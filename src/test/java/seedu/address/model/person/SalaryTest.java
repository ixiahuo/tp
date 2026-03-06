package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

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
    public void equals_differentTypes_returnsFalse() {
        assertNotEquals(5000, new Salary("5000"));
    }

    @Test
    public void hashcode() {
        Salary salary = new Salary("5000");
        assertEquals(salary.hashCode(), new Salary("5000").hashCode());
    }

    @Test
    public void isValidSalary() {
        // null salary
        assertThrows(NullPointerException.class, () -> Salary.isValidSalary(null));

        // invalid salary
        assertFalse(Salary.isValidSalary(" ")); // spaces only
        assertFalse(Salary.isValidSalary("two thousand")); // non-numeric
        assertFalse(Salary.isValidSalary("9011p")); // alphabets within digits

        // valid salary
        assertTrue(Salary.isValidSalary("")); // empty string for optional field
        assertTrue(Salary.isValidSalary("0"));
        assertTrue(Salary.isValidSalary("5000"));
        assertTrue(Salary.isValidSalary("123456789012345")); // long numbers
    }
}
