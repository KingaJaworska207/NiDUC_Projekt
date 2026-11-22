package glosowanie;

public class GlosowanieWiekszosciowe implements AlgorytmGlosujacy {

    // Próg tolerancji - maksymalna dopuszczalna różnica między dwoma zgodnymi wartościami
    private double progTolerancji;

    public GlosowanieWiekszosciowe(double progTolerancji) {
        this.progTolerancji = progTolerancji;
    }

    @Override
    public String pobierzNazwe() {
        return "Algorytm wiekszosciowy (prog = " + progTolerancji + ")";
    }

    @Override
    public WynikGlosowania glosuj(double[] odczyty) {
        // sprawdzamy czy dostaliśmy 3 odczyty
        if (odczyty.length != 3) {
            return WynikGlosowania.brakZgody();
        }

        double a = odczyty[0];
        double b = odczyty[1];
        double c = odczyty[2];

        // obliczamy bezwzględne różnice między każdą parą kanałów
        double roznicaAB = Math.abs(a - b);
        double roznicaAC = Math.abs(a - c);
        double roznicaBC = Math.abs(b - c);

        // sprawdzamy, które pary mieszczą się w progu tolerancji
        boolean zgodneAB = roznicaAB <= progTolerancji;
        boolean zgodneAC = roznicaAC <= progTolerancji;
        boolean zgodneBC = roznicaBC <= progTolerancji;

        // przypadek A: wszystkie 3 kanały są ze sobą zgodne -> obliczamy średnią wartość
        if (zgodneAB && zgodneAC && zgodneBC) {
            double srednia = (a + b + c) / 3.0;
            return WynikGlosowania.sukces(srednia);
        }

        // przypadek B: szukamy zgodnej pary (jeśli zgodne są dwie pary, wybieramy tą z mniejszą róznicą)
        double najmniejszaRoznica = Double.MAX_VALUE;
        double wybranaSrednia = 0.0;
        boolean znalezionoPare = false;

        if(zgodneAB && roznicaAB < najmniejszaRoznica) {
            najmniejszaRoznica = roznicaAB;
            wybranaSrednia = (a + b) / 2.0;
            znalezionoPare = true;
        }
        if (zgodneAC && roznicaAC < najmniejszaRoznica) {
            najmniejszaRoznica = roznicaAC;
            wybranaSrednia = (a + b) / 2.0;
            znalezionoPare = true;
        }
        if (zgodneBC && roznicaBC < najmniejszaRoznica) {
            najmniejszaRoznica = roznicaBC;
            wybranaSrednia = (a + b) / 2.0;
            znalezionoPare = true;
        }

        if (znalezionoPare) {
            return WynikGlosowania.sukces(wybranaSrednia);
        }

        // przypadek C: żaden kanał nie zgadza się z innymi
        return WynikGlosowania.brakZgody();
    }
}
