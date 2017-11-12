package dm.otus.l5_annotation;

public class MathUtil {
    static boolean doubleEquality(double a, double b){
        final double EPSILON = 0.00001;
        return (a >= b-EPSILON) && (a <= b+EPSILON);
    }
}
