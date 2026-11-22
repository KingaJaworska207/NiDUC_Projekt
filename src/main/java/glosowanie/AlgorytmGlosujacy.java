package glosowanie;

/**
 * Interfejs reprezentujący schemat dla działania algorytmów głosowania.
 * Każdy algorytm musi mieć metodę pobierzNazwe.
 * Każdy algorytm musi mieć metodę glosuj przyjmującą tablicę odczytów.
 */
public interface AlgorytmGlosujacy {
    String pobierzNazwe();
    WynikGlosowania glosuj(double[] odczyty);
}
