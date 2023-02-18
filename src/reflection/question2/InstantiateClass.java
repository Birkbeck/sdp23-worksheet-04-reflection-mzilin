package reflection.question2;


import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Map;


public class InstantiateClass {
    public static void main(String[] args) {
        if (args.length < 1)
            System.out.println("Usage: You need to provide at least 1 argument - name of the class");

        else
            try {
                Object obj = args.length == 1 ?
                        instantiation(args[0], new String[]{}) :
                        instantiation(args[0], Arrays.copyOfRange(args, 1, args.length));

                if (obj != null) System.out.println(obj + " - " + obj.getClass());
                else System.out.println("Failed to create " + Arrays.toString(args));

            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
    }

    public static Object instantiation(String className, String[] args) throws ClassNotFoundException {
        for (Constructor<?> c : Class.forName(className).getConstructors()) {
            if (c.getParameterCount() == args.length) {
                try {
                    Object[] params = new Object[args.length];
                    Class<?>[] constrParamTypes = c.getParameterTypes();

                    for (int i = 0; i < args.length; i++) {
                        Class<?> p = getObjectClass(constrParamTypes[i]);
                        params[i] = p.getConstructor(String.class).newInstance(args[i]);
                    }
                    return c.newInstance(params);

                } catch (Exception ex) {
                    ex.getStackTrace();
                }
            }
        }
        return null;
    }

    private static Class<?> getObjectClass(Class<?> initialClass) {
        return PRIMITIVE_TYPES.getOrDefault(initialClass, initialClass);
    }

    private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPES = Map.of(
            int.class, Integer.class,
            long.class, Long.class,
            boolean.class, Boolean.class,
            byte.class, Byte.class,
            char.class, Character.class,
            float.class, Float.class,
            double.class, Double.class,
            short.class, Short.class,
            void.class, Void.class
    );
}










