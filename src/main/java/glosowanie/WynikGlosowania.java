package glosowanie;

/**
 * Pomocnicza klasa, która zajmuje się poprawnym pokazaniem wyniku głosowania
 */
public class WynikGlosowania {

    private boolean czyZgoda;
    private double wartosc;

    /**
     * Konstruktor klasy WynikGlosowania
     * @param czyZgoda czy wyniki są ze sobą zgodne (mieszczą się w progu tolerancji)
     * @param wartosc wartość zwracana jako wynik głosowania
     */
    public WynikGlosowania(boolean czyZgoda, double wartosc) {
        this.czyZgoda = czyZgoda;
        this.wartosc = wartosc;
    }

    /**
     * Metoda pomocnicza do tworzenia poprawnego wyniku
     * @param wartosc wartość zwracana jako wynik głosowania
     * @return wynik głosowania
     */
    public static WynikGlosowania sukces(double wartosc) {
        return new WynikGlosowania(true, wartosc);
    }

    /**
     * Metoda pomocnicza, gdyby nie udało się wyłonić większości
     * @return wynik głosowania
     */
    public static WynikGlosowania brakZgody() {
        return new WynikGlosowania(false, Double.NaN); // Double.NaN oznacza brak liczby
    }

    public boolean czyZgoda() {
        return czyZgoda;
    }

    public double getWartosc() {
        return wartosc;
    }
}
