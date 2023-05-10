import java.util.ArrayList;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Funkcja funkcja1 = new Funkcja(x -> Math.sin(3*x), x -> -Math.cos(3*x)/3);
        Funkcja funkcja2 = new Funkcja(x -> Math.cos(x)/5, x -> Math.sin(x)/5);
        Funkcja funkcja3 = new Funkcja(x -> 2*Math.exp(x), x -> 2*Math.exp(x));

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