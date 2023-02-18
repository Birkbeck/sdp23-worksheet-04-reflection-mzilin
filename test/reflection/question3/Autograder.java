package reflection.question3;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class Autograder {

//    static final String CLASS_NAME = "reflection.question3.GoodStudentAssignment";
    static final String CLASS_NAME = "reflection.question3.BadStudentAssignment";
    static Class<?> c;

    @BeforeAll
    static void getTestClass() throws ClassNotFoundException {
        c = Class.forName(CLASS_NAME);
    }

    @Test
    public void testMoreThanFourFields() {
        assertTrue(c.getDeclaredFields().length < 5,
                "Has more than 4 fields");
    }

    @Test
    public void testNonPrivateFields() {
        for (Field f : c.getDeclaredFields()) {
            assertEquals(f.getModifiers(), f.getModifiers() | Modifier.PRIVATE,
                    "Non-private field found.");
        }
    }

    @Test
    public void testNonArrayListTypeFields() {
        for (Field f : c.getDeclaredFields()) {
            assertNotEquals(ArrayList.class, f.getType(),
                    "ArrayList type field found.");
        }
    }

    @Test
    public void testFewerThanTwoHelperMethods() {
        int helpers = 0;
        for (Method m : c.getDeclaredMethods()) {
            if ((m.getModifiers() | Modifier.STATIC) == m.getModifiers() &&
                    !m.getName().equals("main")) {
                helpers ++;
            }
        }
        assertTrue(helpers >= 2, "Has fewer than 2 helper methods");
    }

    @Test
    public void testMethodHasThrowClause() {
        for (Method m : c.getDeclaredMethods()) {
            assertEquals(0, m.getExceptionTypes().length,
                    "Method with throw clause found");
        }
    }

    @Test
    public void testMethodReturnsInt() {
        for (Method m : c.getDeclaredMethods()) {
            assertNotEquals(int.class, m.getReturnType(),
                    "Method that returns int found");
        }
    }

    @Test
    public void testHasZeroArgsConstructor() {
        for (Constructor<?> constr : c.getDeclaredConstructors()) {
            if (constr.getParameterCount() == 0) return;
        }
        fail("Class has no zero args constructor");
    }
}
