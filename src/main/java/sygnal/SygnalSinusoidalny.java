package sygnal;

/**
 * Klasa reprezentująca sinusoidalny sygnał wejściowy.
 */
public class SygnalSinusoidalny {

    //y(t) = A * sin( (2 * pi * t)/T )

    // pola klasy (parametry sygnału)
    private double amplituda;
    private double okres;
    private double przesuniecie;


    /**
     * Konstruktor klasy SygnalSinusoidalny
     * @param amplituda amplituda, czyli jak mocno sygnał odchyla się w górę i w dół od środka
     * @param okres okres, czyli liczba kroków potrzebna na wykonanie jednej pełnej fali
     * @param przesuniecie przesunięcie pionowe, czyli wartość bazowa, wokół której oscyluje sygnał
     */
    public SygnalSinusoidalny(double amplituda, double okres, double przesuniecie) {
        this.amplituda = amplituda;
        this.okres = okres;
        this.przesuniecie = przesuniecie;
    }

    /**
     * Metoda obliczjąca wartość sygnału dla konkretnego kroku t
     * @param krok aktualny krok symulacji
     * @return wartość sygnału dla konkretnego kroku t
     */
    public double obliczWartosc(int krok) {
        double kat = (2.0 * Math.PI * krok) / okres;
        double wartosc = amplituda * Math.sin(kat) + przesuniecie;
        return wartosc;
    }
}
