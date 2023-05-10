import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Function;

public class MetodaCSI {

    public static double obliczCalka(Function<Double, Double> funkcja, double a, double b, int liczbaPodzialow) {
        double h = (b - a) / liczbaPodzialow;

        Double[] x = new Double[liczbaPodzialow + 1];
        Double[] y = new Double[liczbaPodzialow + 1];

        for (int i = 0; i <= liczbaPodzialow; i++) {
            x[i] = a + i * h;
            y[i] = funkcja.apply(x[i]);
        }

        double u = 0.5;
        double l = 0.5;
        double dPrefix = 3.0 / h*h;

        MySparseMatrix macierz = new MySparseMatrix(liczbaPodzialow + 1, liczbaPodzialow + 1);
        macierz.setItem(0, 0, 2);
        macierz.setItem(liczbaPodzialow, liczbaPodzialow-1, u);
        macierz.setItem(liczbaPodzialow, liczbaPodzialow, 2);
        double[] wyrazyWolne = new double[liczbaPodzialow + 1];
        wyrazyWolne[0] = 0;
        for (int i = 1; i < liczbaPodzialow; i++) {
            macierz.setItem(i, i - 1, u);
            macierz.setItem(i, i, 2);
            macierz.setItem(i, i + 1, l);

            double d = dPrefix * (y[i+1] - 2*y[i] + y[i-1]);
            wyrazyWolne[i] = d;
        }
        macierz.absoluteTerms = wyrazyWolne;
        double[] M = macierz.solveGauss();

        double wynik = 0;
        for (int i = 0; i < liczbaPodzialow; i++) {
            double alfa = ((y[i+1] - y[i])/h)-((2*M[i]+M[i+1])/6)*h;
            double beta = M[i] / 2;
            double gamma = (M[i+1]-M[i])/(6*h);
            // s(x) = y + alfa(x - xi) + beta(x - xi)^2 + gamma(x - xi)^3
            // I teraz całka oznaczona z tego...
            double dolnaGranica = a + i * h;
            double gornaGranica = dolnaGranica + h;
            int j = i;
            Function<Double, Double> calka =
                    X -> y[j]*X + alfa*((Math.pow(X, 2)/2)-x[j]*X) + (beta*Math.pow(X-x[j], 3)/3) + (gamma*Math.pow(X-x[j], 4)/4);
            double calkaOznaczona = calka.apply(gornaGranica) - calka.apply(dolnaGranica);
            wynik += calkaOznaczona;
        }

        return wynik;
    }
}
