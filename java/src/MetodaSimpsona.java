import java.util.function.Function;

public class MetodaSimpsona {

    public static double obliczCalka(Function<Double, Double> funkcja, double a, double b, int liczbaPodzialow) {
        double h = (b - a) / (2 * liczbaPodzialow);
        double suma = funkcja.apply(a) + funkcja.apply(b);

        for (int i = 1; i < 2 * liczbaPodzialow; i++) {
            double x = a + i * h;
            suma += funkcja.apply(x) * (i % 2 == 0 ? 2 : 4);
        }

        return h * suma / 3;
    }
}
