package reflection.question4;


import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ToString {

    public static void main(String[] args) {
        String s = "Hello";
        File f = new File("test.txt");
        System.out.println(ToString.toString(s));
        System.out.println(ToString.toString(f));
    }

    public static String toString(Object obj) {
        if (obj == null) return "null";

        Class<?> c = obj.getClass();
        List<String> fields = new ArrayList<>();

        for (Field f : c.getDeclaredFields()) {
            try {
                if(!Modifier.isPublic(f.getModifiers()) && !f.trySetAccessible()) {
                    return "Cannot Access";
                }
                Class<?> fieldTypeClass = f.getType();
                Object fieldValue = f.get(obj);

                if (fieldTypeClass.isPrimitive()) {
                    fields.add(f.getName() + "=" +
                            (fieldTypeClass == char.class ? "'" + fieldValue + "'" : fieldValue));
                } else {
                    try {
                        Method m = fieldTypeClass.getDeclaredMethod("toString");
                        fields.add(f.getName() + "=" +
                                (fieldTypeClass == String.class && fieldValue != null
                                ? "\"" + fieldValue +"\"" : fieldValue));

                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }

                fields.add(f.getName());

            } catch (IllegalAccessException ex) {
                fields.add(f.getName() + "=IllegalAccess");
            }
        }

        return c.getSimpleName() + " {" + String.join(", ", fields) + "}";
    }
}
