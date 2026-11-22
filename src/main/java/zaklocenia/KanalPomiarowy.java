package zaklocenia;

import java.util.Random;

public class KanalPomiarowy {

    // generator liczb losowych
    private Random losuj;

    // szum pomiarowy (rozkład Gaussa)
    private double odchylenieSzumu;

    // błędy grube (chwilowe skoki)
    private double prawdopodobienstwoBleduGrubego;
    private double amplitudaBleduGrubego;

    // awaria
    private int krokAwarii;
    private double wartoscAwarii;

    /**
     * Konstruktor klasy KanalPomiarowy
     * @param odchylenieSzumu naturalny szum wynikający z działania kanału pomiarowego
     */
    public KanalPomiarowy(double odchylenieSzumu) {
        this.losuj = new Random();
        this.odchylenieSzumu = odchylenieSzumu;
        this.prawdopodobienstwoBleduGrubego = 0.0;
        this.amplitudaBleduGrubego = 0.0;
        this.krokAwarii = -1;
        this.wartoscAwarii = -999.0;
    }

    /**
     * Metoda włączająca błędy grube na tym kanale
     * @param prawdopodobienstwo prawdopodobieństwo wystąpienia błędu grubego
     * @param amplituda zakres błędu grubego, czyli o ile błąd gruby może zniekształcić wynik
     */
    public void ustawBledyGrube (double prawdopodobienstwo, double amplituda) {
        this.prawdopodobienstwoBleduGrubego = prawdopodobienstwo;
        this.amplitudaBleduGrubego = amplituda;
    }

    /**
     * Metoda włączająca trwała awarię do wybranego kroku
     * @param krokAwarii określa moment, od którego wystąpi awaria
     * @param wartoscAwarii wartość, na której blokuje się kanał pomiarowy przez wystąpienie awarii
     */
    public void ustawAwarie(int krokAwarii, double wartoscAwarii) {
        this.krokAwarii = krokAwarii;
        this.wartoscAwarii = wartoscAwarii;
    }

    /**
     * Główna metoda, która przyjmuje czysty sygnał i zwraca wartość z zakłóceniami
     * @param wartoscWzorcowa czysta wartość sygnału
     * @param krok obecny krok symulacji
     * @return wartość z zakłóceniami
     */
    public double zaklocSygnal(double wartoscWzorcowa, int krok) {
        // jeśli nastąpiła awaria, od razu zwracamy wartość błędu
        if (krokAwarii >= 0 && krok >= krokAwarii) {
            return wartoscAwarii;
        }

        // dodajemy naturalny szum pomiarowy
        double szum = losuj.nextGaussian() * odchylenieSzumu;
        double wynik = wartoscWzorcowa + szum;

        // losujemy, czy w tym kroku pojawił się błąd gruby
        if (losuj.nextDouble() < prawdopodobienstwoBleduGrubego) {
            // losowo dodajemy lub odejmujemy dużą amplitudę zkłócenia
            if (losuj.nextBoolean()) {
                wynik = wynik + amplitudaBleduGrubego;
            } else {
                wynik = wynik - amplitudaBleduGrubego;
            }
        }

        return wynik;
    }
}
