import java.util.ArrayList;
import static java.lang.Math.*;

public class Main {
    public static void main(String[] args) {
        // Te komentarze z całkami można wklepać do Wolfram Alpha
        // i policzyć sobie z tego pochodną w ramach sprawdzenia

        // Całka: cos^2(cos(x))*sin(3*x^2)
        Funkcja funkcja1 = new Funkcja(
                x -> 2.0 * cos(cos(x)) *
                        (3.0 * x * cos(3.0 * pow(x, 2.0)) *
                                cos(cos(x)) +
                                sin(x) * sin(3.0 * pow(x, 2.0)) *
                                        sin(cos(x))
                        ),
                x -> pow(cos(cos(x)), 2.0) *
                        sin(3.0 * pow(x, 2.0))
        );

        // Całka: sqrt(cos^2(x))
        Funkcja funkcja2 = new Funkcja(
                x -> -sqrt(pow(cos(x), 2.0))*tan(x),
                x -> sqrt(pow(cos(x), 2))
        );

        // Całka: (sin(x)*cos(x))/e^x^2
        Funkcja funkcja3 = new Funkcja(
                x -> exp(-pow(x, 2.0)) *
                        (-pow(sin(x), 2.0) +
                        pow(cos(x), 2.0) -
                        2 * x * sin(x) * cos(x)),
                x -> (sin(x) * cos(x)) / exp(pow(x, 2.0))
        );

        ArrayList<Funkcja> funkcje = new ArrayList<>();
        funkcje.add(funkcja1);
        funkcje.add(funkcja2);
        funkcje.add(funkcja3);

        double a = 0;
        double b = 1;
        int liczbaPodzialow = 100;

        for (int i = 0; i < funkcje.size(); i++) {
            Funkcja funkcja = funkcje.get(i);

            double wynikMetodaTrapezow = MetodaTrapezow.obliczCalka(funkcja.pobierzFunkcje(), a, b, liczbaPodzialow);
            double wynikMetodaSimpsona = MetodaSimpsona.obliczCalka(funkcja.pobierzFunkcje(), a, b, liczbaPodzialow);
            double wynikMetodaCSI = MetodaCSI.obliczCalka(funkcja.pobierzFunkcje(), a, b, liczbaPodzialow);

            System.out.println("\nFunkcja " + (i + 1));
            System.out.println("Prawidlowy wynik " + funkcja.policzCalkeOznaczona(a, b));
            System.out.println("Metoda Trapezów " + wynikMetodaTrapezow);
            System.out.println("Metoda Simpsona " + wynikMetodaSimpsona);
            System.out.println("Metoda CSI " + wynikMetodaCSI);
        }
    }
}