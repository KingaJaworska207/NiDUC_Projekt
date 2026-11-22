package glosowanie;

/**
 * Klasa implementująca działanie algorytmu średniej ważonej (Weighted Average Voter)
 */
public class GlosowanieSredniaWazona implements AlgorytmGlosujacy {

    private double w1;
    private double w2;
    private double w3;

    /**
     * Konstruktor klasy GlosowanieSredniaWazona
     * Podane wagi są automatycznie normalizowane tak, aby sumowały się do 1
     * @param w1 waga dla kanału 1
     * @param w2 waga dla kanału 2
     * @param w3 waga dla kanału 3
     */
    public GlosowanieSredniaWazona(double w1, double w2, double w3) {
        double suma = w1 + w2 + w3;

        if (w1 < 0 || w2 < 0 || w3 < 0 || suma == 0) {
            throw new IllegalArgumentException("Wagi muszą być nieujemne, a ich suma większa od zera!");
        }

        // normalizacja wag (w1 + w2 + w3 = 1)
        this.w1 = w1 / suma;
        this.w2 = w2 / suma;
        this.w3 = w3 / suma;

    }
    /**
     * Metoda pozwalająca na pobranie nazwy algorytmu głosującego i jego prarametrów
     * @return nazwa algorytmu waraz z jego parametrem
     */
    @Override
    public String pobierzNazwe() {
        return String.format("Algorytm średniej ważonej (%.2f, %.2f, %.2f)", w1, w2, w3);
    }

    /**
     * Metoda przeprowadzająca głosownaie w algorytmie większościowym
     * @param odczyty wartości uzyskane z kanałów pomiarowych
     * @return wynik głosowania
     */
    @Override
    public WynikGlosowania glosuj(double[] odczyty) {
        // sprawdzamy czy dostaliśmy 3 odczyty
        if (odczyty.length != 3) {
            return WynikGlosowania.brakZgody();
        }

        // obliczamy średnią
        double wynik = w1 * odczyty[0] + w2 * odczyty[1] + w3 * odczyty[2];

        return WynikGlosowania.sukces(wynik);

    }
}
