import java.util.function.Function;

public class Funkcja {
    private final Function<Double, Double> funkcja;
    private final Function<Double, Double> wzorNaCalke;

    public Funkcja(Function<Double, Double> funkcja, Function<Double, Double> wzorNaCalke) {
        this.funkcja = funkcja;
        this.wzorNaCalke = wzorNaCalke;
    }

    public Function<Double, Double> pobierzFunkcje() {
        return funkcja;
    }
    public Double policzCalkeOznaczona(double a, double b) {
        return wzorNaCalke.apply(b) - wzorNaCalke.apply(a);
    }
}
