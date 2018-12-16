package biblioteka;

public class Film{
    int isfn;
    int liczbaWypozyczen;
    String tytul, gatunek, krajprodukcji;
    boolean czyWypozyczona;
    double cena;
    
    // konstruktor dla filmow wczytywanych z pliku
    
    Film(int isfn, String tytul, String gatunek, String krajprodukcji, double cena, boolean czyWypozyczona, int liczbaWypozyczen) {
        this.isfn = isfn;
        this.tytul = tytul;
        this.gatunek = gatunek;
        this.krajprodukcji = krajprodukcji;
        this.cena = cena;
        this.czyWypozyczona = czyWypozyczona;
        this.liczbaWypozyczen = liczbaWypozyczen;
    }
//konstruktor dla nowych ksiazek
   
   
    Film(String tytul, String gatunek, String krajprodukcji, double cena, int isfn) {
         this.tytul = tytul;
        this.krajprodukcji = krajprodukcji;
        this.gatunek = gatunek;
        this.cena = cena;
        this.isfn = isfn;
        liczbaWypozyczen = 0;
        czyWypozyczona = false; 
        
    }

    
    

   
 

   

    
}
