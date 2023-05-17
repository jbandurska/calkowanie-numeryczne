import java.util.function.Function;

public class MetodaTrapezow {

    public static double obliczCalka(Function<Double, Double> funkcja, double a, double b, int liczbaPodzialow) {
        double h = (b - a) / liczbaPodzialow;
        double suma = 0.5 * (funkcja.apply(a) + funkcja.apply(b));

        for (int i = 1; i < liczbaPodzialow; i++) {
            double x = a + i * h;
            suma += funkcja.apply(x);
        }

        return h * suma;
    }
}
