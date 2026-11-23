import glosowanie.AlgorytmGlosujacy;
import glosowanie.GlosowanieSredniaWazona;
import glosowanie.GlosowanieWiekszosciowe;
import glosowanie.WynikGlosowania;
import sygnal.SygnalSinusoidalny;
import zaklocenia.KanalPomiarowy;

public class Main {
    public static void main(String[] args) {
        // Sygnał wzorcowy
        int okres = 20;
        SygnalSinusoidalny sygnal = new SygnalSinusoidalny(10.0, okres, 10.0);

        // Trzy niezależne kanały pomiarowe
        KanalPomiarowy kanal1 = new KanalPomiarowy(0.15);

        KanalPomiarowy kanal2 = new KanalPomiarowy(0.15);
        kanal2.ustawBledyGrube(0.05, 10.0);

        KanalPomiarowy kanal3 = new KanalPomiarowy(0.15);
        kanal3.ustawAwarie(14, -999.0);

        // Algorytmy głosowania
        AlgorytmGlosujacy wiekszosciowy = new GlosowanieWiekszosciowe(0.5);
        AlgorytmGlosujacy sredniaWazona = new GlosowanieSredniaWazona(5.0, 3.0, 2.0);

        System.out.println();
        System.out.println("Krok | Wzorzec | K1 szum | K2 błąd gruby | K3 awaria | Większościowy | Średniej ważonej");
        System.out.println("---------------------------------------------------------------------------------------");

        for (int krok = 0; krok <= okres; krok++) {
            double wzorzec = sygnal.obliczWartosc(krok);

            double odczyt1 = kanal1.zaklocSygnal(wzorzec, krok);
            double odczyt2 = kanal2.zaklocSygnal(wzorzec, krok);
            double odczyt3 = kanal3.zaklocSygnal(wzorzec, krok);

            double[] odczyty = {odczyt1, odczyt2, odczyt3};

            WynikGlosowania wynikWieksz = wiekszosciowy.glosuj(odczyty);
            WynikGlosowania wynikSrednia = sredniaWazona.glosuj(odczyty);

            // wypisane dla większościowego
            String tekstWieksz;
            if (wynikWieksz.czyZgoda()) {
                tekstWieksz = String.format("%13.2f", wynikWieksz.getWartosc());
            } else {
                tekstWieksz = "  BRAK WYNIKU";
            }

            // wypisanie dla średniej ważonej
            String tekstSrednia = String.format("%14.2f", wynikSrednia.getWartosc());

            // wypisanie całej tabelki
            System.out.printf("%4d | %7.2f | %7.2f | %13.2f | %9.2f | %s | %s%n",
                    krok, wzorzec, odczyt1, odczyt2, odczyt3, tekstWieksz, tekstSrednia);
        }
    }
}
