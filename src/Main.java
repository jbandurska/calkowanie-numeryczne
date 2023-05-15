import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            FileWriter resultWriter = new FileWriter("results.csv");
            resultWriter.write("liczbaPodzialow,funkcja,bladTrapezow,bladSimpsona,bladCSI\n");

            Funkcja funkcja1 = new Funkcja(x -> Math.sin(3*x), x -> -Math.cos(3*x)/3);
            Funkcja funkcja2 = new Funkcja(x -> Math.cos(x)/5, x -> Math.sin(x)/5);
            Funkcja funkcja3 = new Funkcja(x -> 2*Math.exp(x), x -> 2*Math.exp(x));

            ArrayList<Funkcja> funkcje = new ArrayList<>();
            funkcje.add(funkcja1);
            funkcje.add(funkcja2);
            funkcje.add(funkcja3);

            double a = 0;
            double b = 1;

            for (int liczbaPodzialow = 1; liczbaPodzialow <= 10001; liczbaPodzialow += 10) {
                System.out.println("Liczba podziałów " + liczbaPodzialow);
                for (int i = 0; i < funkcje.size(); i++) {
                    Funkcja funkcja = funkcje.get(i);

                    double prawidlowyWynik = funkcja.policzCalkeOznaczona(a, b);
                    double wynikMetodaTrapezow = MetodaTrapezow.obliczCalka(funkcja.pobierzFunkcje(), a, b, liczbaPodzialow);
                    double wynikMetodaSimpsona = MetodaSimpsona.obliczCalka(funkcja.pobierzFunkcje(), a, b, liczbaPodzialow);
                    double wynikMetodaCSI = MetodaCSI.obliczCalka(funkcja.pobierzFunkcje(), a, b, liczbaPodzialow);

                    double bladTrapezow = Math.abs(prawidlowyWynik - wynikMetodaTrapezow);
                    double bladSimpsona = Math.abs(prawidlowyWynik - wynikMetodaSimpsona);
                    double bladCSI = Math.abs(prawidlowyWynik - wynikMetodaCSI);

//                    System.out.println("\nFunkcja " + (i + 1));
//                    System.out.println("Prawidlowy wynik " + prawidlowyWynik);
//                    System.out.println("Metoda Trapezów " + wynikMetodaTrapezow + ". Błąd " + bladTrapezow);
//                    System.out.println("Metoda Simpsona " + wynikMetodaSimpsona + ". Błąd " + bladSimpsona);
//                    System.out.println("Metoda CSI " + wynikMetodaCSI + ". Błąd " + bladCSI);

                    resultWriter.write(
                            liczbaPodzialow + "," + (i + 1) + "," + bladTrapezow + "," + bladSimpsona + "," + bladCSI + "\n"
                    );
                }
            }
        } catch (IOException e) {
            System.out.println(":(");
            e.printStackTrace();
        }

    }
}