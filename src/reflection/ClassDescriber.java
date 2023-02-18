package reflection;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassDescriber {

    private Class<?> c;

    public ClassDescriber(String className) {
        try {
            this.c = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            System.err.println("Unknown className '" + className + "'");
        }
    }

    private String joinArrayToString(String[] strings, String delimiter) {
        return Arrays.stream(strings)
                .filter(s -> s.length() > 0)
                .collect(Collectors.joining(delimiter));
    }

    private String getNameAndParameters(String name, Class<?>[] params) {
        return name + "(" + joinArrayToString(new String[]{
                Arrays.toString(params)
        }, ", ") + ")";
    }

    private String getClassType() {
        return c.isInterface() ? "interface" : "class";
    }

    private String getSupperClassesNames() {
        if (c.getSuperclass() != null) {
            return "extends " + c.getSuperclass().getSimpleName();
        }
        return "";
    }

    private String getModifiers(int modifiers) {
        return modifiers == 0 ? "" : Modifier.toString(modifiers);
    }

    private String getInterfaces() {
        if (c.getInterfaces().length > 0) {
            return "implements " + Stream.of(c.getInterfaces())
                    .map(Class::getSimpleName)
                    .collect(Collectors.joining(", "));
        }
        return "";
    }

    private String getFieldInformation() {
        Field[] fields = c.getDeclaredFields();
        if (fields.length == 0) return "";

        List<String> res = new ArrayList<>(Arrays.asList("\n", "-- Fields"));
        for (Field f : fields) {
            res.add(joinArrayToString(new String[]{
                    getModifiers(f.getModifiers()),
                    String.valueOf(f.getGenericType()),
                    f.getName()}, " "));
        }
        return String.join("\n", res);
    }

    private String getConstructorInformation() {
        Constructor<?>[] constructors = c.getConstructors();
        if (constructors.length == 0) return "";

        List<String> res = new ArrayList<>(Arrays.asList("\n", "-- Constructors"));
        for (Constructor<?> constr : constructors) {
            res.add(joinArrayToString(new String[]{
                    getModifiers(constr.getModifiers()),
                    getNameAndParameters(constr.getName(), constr.getParameterTypes())
            }, " "));
        }
        return String.join("\n", res);
    }


    private String getMethodInformation() {
        Method[] methods = c.getDeclaredMethods();
        if (methods.length == 0) return "";

        List<String> res = new ArrayList<>(Arrays.asList("\n", "-- Methods"));
        for (Method m : methods) {
            res.add(joinArrayToString(new String[]{
                    getModifiers(m.getModifiers()),
                    String.valueOf(m.getGenericReturnType()),
                    getNameAndParameters(m.getName(), m.getParameterTypes())
            }, " "));
        }
        return String.join("\n", res);
    }

    @Override
    public String toString() {
        if (c == null) return "Class object doesn't exist";

        return joinArrayToString(new String[]{
                getModifiers(c.getModifiers()),
                getClassType(),
                c.getSimpleName(),
                getSupperClassesNames(),
                getInterfaces(),
                getFieldInformation(),
                getConstructorInformation(),
                getMethodInformation()
        }, "\n");
    }

}












