import glosowanie.AlgorytmGlosujacy;
import glosowanie.GlosowanieWiekszosciowe;
import glosowanie.WynikGlosowania;
import sygnal.SygnalSinusoidalny;
import zaklocenia.KanalPomiarowy;

public class Main {
    public static void main(String[] args) {
        // Sygnał wzorcowy
        SygnalSinusoidalny sygnal = new SygnalSinusoidalny(10.0, 20.0, 50.0);

        // Trzy niezależne kanały pomiarowe
        KanalPomiarowy kanal1 = new KanalPomiarowy(0.5);

        KanalPomiarowy kanal2 = new KanalPomiarowy(0.5);
        kanal2.ustawBledyGrube(0.2, 10.0);

        KanalPomiarowy kanal3 = new KanalPomiarowy(0.5);
        kanal3.ustawAwarie(10, -999.0);

        // Algorytm głosowania
        AlgorytmGlosujacy wiekszosciowy = new GlosowanieWiekszosciowe(1.5);

        System.out.println("Krok | Wzorzec | Kanal 1 | Kanal 2 | Kanal 3 | Wiekszosciowy");
        System.out.println("------------------------------------------------------------");

        for (int krok = 0; krok <= 20; krok++) {
            double wzorzec = sygnal.obliczWartosc(krok);

            double odczyt1 = kanal1.zaklocSygnal(wzorzec, krok);
            double odczyt2 = kanal2.zaklocSygnal(wzorzec, krok);
            double odczyt3 = kanal3.zaklocSygnal(wzorzec, krok);

            double[] odczyty = {odczyt1, odczyt2, odczyt3};
            WynikGlosowania wynik = wiekszosciowy.glosuj(odczyty);

            // tekst do wyniku
            String tekstWyniku;
            if (wynik.czyZgoda()) {
                tekstWyniku = String.format("%7.2f", wynik.getWartosc());
            } else {
                tekstWyniku = "BRAK ZGODY";
            }

            System.out.printf("%4d | %7.2f | %7.2f | %7.2f | %7.2f | %s%n",
                    krok, wzorzec, odczyt1, odczyt2, odczyt3, tekstWyniku);
        }
    }
}
