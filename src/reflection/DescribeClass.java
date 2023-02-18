package reflection;

/**
 * Question 1
 */
public class DescribeClass {

    public static void main(String[] args) {
        if (args.length != 1)
            System.out.println("Usage: Provide a single parameter");
        else System.out.println(new ClassDescriber(args[0]));
    }
}
