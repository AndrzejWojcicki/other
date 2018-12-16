package biblioteka;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.EOFException;
import java.util.Scanner;

/**
 *
 * @author ANDRZEJ
 */
public class WypozyczalniaDVD{
    Film Filmy[];
    long filmPointer[];
    int ileFilmow;
    private int isfn;
    
    // konstruktor
    WypozyczalniaDVD()
    {
        Filmy = new Film[1000];
        filmPointer = new long[1000];
        ileFilmow = 0 ;
    }
    
    // metoda czytajaca filmy z pliku danych "wypozyczalnia.dat"
    void read()
    {
        try
        {
            RandomAccessFile file = new RandomAccessFile("wypozyczalnia.dat", "rw");
            try
            {
                int isfn,liczbaWyporzyczen;
                String tytul, gatunek, krajprodukcji;
                double cena;
                boolean czyWyporzyczona;
                long tempPointer;
                while(true)
                {
                    tempPointer = file.getFilePointer();
                    isfn = file.readInt();
                    tytul = file.readUTF();
                    gatunek = file.readUTF();
                    krajprodukcji = file.readUTF();
                    cena = file.readDouble();
                    czyWyporzyczona = file.readBoolean();
                    liczbaWyporzyczen = file.readInt();
                    addFromFile(isfn, tytul, gatunek, krajprodukcji, cena,  czyWyporzyczona, liczbaWyporzyczen, tempPointer);
                }
            }
            catch(EOFException ex)
            {
            }
            catch(IOException e)
            {
                System.out.println("Blad przy wczytywaniu danych z pliku");
                System.out.println("Sugeruje sie ponowne uruchomienie programu");
            }
            finally
            {
                file.close();
            }
        }
        catch(IOException e)
        {
            System.out.println("Brak dostepu do pliku danych");
            System.out.println("Sprawdz plik i uruchom program ponownie");
        }
    }
    
    // metoda wczytujaca film  pliku do objektu
    void addFromFile(int isfn,String tytul,String gatunek,String krajprodukcji,double cena,boolean  czyWyporzyczona,int liczbaWyporzyczen,long tempPointer)
    {
        Filmy[isfn] = new Film(isfn, tytul, gatunek, krajprodukcji, cena,  czyWyporzyczona, liczbaWyporzyczen);
        filmPointer[isfn] = tempPointer;
        ileFilmow++;
        isfn++;
    }
    
    // metoda dopisujaca do wypozyczalni filmow
    void addNewFilm(String tytul, String krajprodukcji,String gatunek, double cena )
    {   
        Filmy[ileFilmow] = new Film(tytul, gatunek, krajprodukcji, cena , ileFilmow);
        ileFilmow++;
        saveToFile();
    }
    
    // metoda wsywietlajaca liste filmow w formacie pelnym
    void showAll() 
    {
        for (int i = 0; i < ileFilmow; ++i)
        {
            System.out.print("|isfn: "+Filmy[i].isfn+"|| Tytul:"+Filmy[i].tytul+"|| Gatunek:"+Filmy[i].gatunek+"|| Kraj produkcji:"+Filmy[i].krajprodukcji+"|| Cena:"+Filmy[i].cena+"|| Ilość wypozyczen:"+Filmy[i].liczbaWypozyczen+"|");
            if (Filmy[i].czyWypozyczona)
                System.out.println("Wypozyczona||");
            else
                System.out.println("Dostepna||");
        }
        System.out.println();
    }
    
    //metoda edytujaca informacje  danego filmu
    void editFilm(int isfn)
    {
        String pole=""; // nazwa pola, ktorego wartosc ma zostac zmieniona
        String sWartosc=""; // nowa wartosc dla pola tytul lub gatunek lub kraj produkcji
        double iWartosc=0; // nowa wartosc dla pola cena
        int    nWartosc=0;
        Scanner sc = new Scanner(System.in);
        boolean powtorz1=true, powtorz2=true; // zmienna potrzebna do sprawdzenia, czy powtorzyc edycje (1 - bledne wpisanie pola, 2 - bledne wpisanie wartosci)
        do
        {
            System.out.println("\nTryb edycji informacji o ksiazce\nAktualne informacje:\n");
            System.out.print("||"+Filmy[isfn].isfn+"| "+Filmy[isfn].tytul+"| "+Filmy[isfn].gatunek+"| "+Filmy[isfn].krajprodukcji+"| "+Filmy[isfn].cena+"| "+Filmy[isfn].liczbaWypozyczen+"|  ");
            if (Filmy[isfn].czyWypozyczona)
                System.out.println("Wypozyczona||");
            else
                System.out.println("Dostepna||");
            if(powtorz1)
            {
                System.out.println("\nWpisz i zatwierdz [Enter] jaka informacje chcesz zmienic\nMozliwe pola: tytul, gatunek, krajprodukcji, cena\n");
                pole = sc.nextLine();
                pole.toLowerCase();
            }
            switch (pole)
            {
                case "tytul":
                case "gatunek":
                case "krajprodukcji":
 
                    powtorz1 = false;
                    System.out.println("Wpisz i zatwierdz [Enter] nowa wartosc: ");
                    try { sWartosc = sc.nextLine(); powtorz2 = false;}
                    catch(NullPointerException e)
                    {
                        System.out.println("\nNie podano prawidlowej wartosci");
                    }
                    break;
                case "cena":
                    powtorz1 = false;
                    System.out.println("Wpisz i zatwierdz [Enter] nowa wartosc: ");
                    try { iWartosc =  Double.parseDouble(sc.nextLine()); 
                    powtorz2 = false;}
                    catch(NumberFormatException e)
                    {
                        System.out.println("\nNie podano prawidlowej wartosci");
                    }
                    break;
                default:
                    System.out.println("\nNie podano prawidlowego pola\n");
                    break;
            }
        }while(powtorz2);
        try{
            RandomAccessFile file = new RandomAccessFile("wypozyczalnia.dat", "rw");
            file.seek(filmPointer[isfn]);
            file.writeInt(Filmy[isfn].isfn);
            
            if(pole.equals("tytul"))
                Filmy[isfn].tytul = sWartosc;
            file.writeUTF(Filmy[isfn].tytul);
            
            if(pole.equals("gatunek"))
                Filmy[isfn].gatunek = sWartosc;
            file.writeUTF(Filmy[isfn].gatunek);
            
            if(pole.equals("krajprodukcji"))
                Filmy[isfn].krajprodukcji = sWartosc;
            file.writeUTF(Filmy[isfn].krajprodukcji);
            
            if(pole.equals("cena"))
                Filmy[isfn].cena = iWartosc;
            file.writeDouble(Filmy[isfn].cena);

            file.writeBoolean(Filmy[isfn].czyWypozyczona);
            
            if(pole.equals("liczbaW"))
                Filmy[isfn].liczbaWypozyczen = nWartosc;
            file.writeInt(Filmy[isfn].liczbaWypozyczen);
            
            file.close();
        }
        catch(IOException e)
        {
            System.out.println("Blad dostepu do pliku");
        }
    }
    
    // metoda wypozyczajaca film z wypozyczalni
    void borrowFilm(int isfn)
    {
        if(Filmy[isfn].czyWypozyczona)
            System.out.println("\nNie mozna wypozyczyc. Ten film jest już wypożyczony \n");
        else
        {
            Filmy[isfn].czyWypozyczona = true;
            System.out.println("Wypozyczono\n");
        }
        saveToFile();
    }
    
    // metoda zwracajaca film do wypozyczalni
    void returnFilm(int isfn)
    {
        if(Filmy[isfn].czyWypozyczona)
        {
            Filmy[isfn].czyWypozyczona = false;
            System.out.println("Zwrocono\n");
            ++Filmy[isfn].liczbaWypozyczen;
        }
        else
            System.out.println("Nie mozna zwrocic. Ten film nie zostala wypozyczona\n");
        saveToFile();
    }
    
    // metody wyswietlajace informacje o filmachh na podstawie: 1 - tytulu, 2 - gatunku, 3 - krajuprodukcji
    void showFilm1(String tytul)
    {
        for(int i = 0; i < ileFilmow; ++i)
        {
            if(Filmy[i].tytul.contains(tytul))
            {
              System.out.print("||"+Filmy[i].isfn+"| "+Filmy[i].tytul+"| "+Filmy[i].gatunek+"| "+Filmy[i].krajprodukcji+"| "+Filmy[i].cena+"| "+Filmy[i].liczbaWypozyczen+"|  ");
            if (Filmy[i].czyWypozyczona)
                System.out.println("Wypozyczona||");
            else
                System.out.println("Dostepna||");
            }
        }
    }
    void showFilm2(String gatunek)
    {
        for(int i = 0; i < ileFilmow; ++i)
        {
            if(Filmy[i].gatunek.contains(gatunek))
            {
              System.out.print("||"+Filmy[i].isfn+"| "+Filmy[i].tytul+"| "+Filmy[i].gatunek+"| "+Filmy[i].krajprodukcji+"| "+Filmy[i].cena+"| "+Filmy[i].liczbaWypozyczen+"|  ");
            if (Filmy[i].czyWypozyczona)
                System.out.println("Wypozyczona||");
            else
                System.out.println("Dostepna||");
            }
        }
    }
    void showFilm3(String krajprodukcji)
    {
        for(int i = 0; i < ileFilmow; ++i)
        {
            if(Filmy[i].krajprodukcji.contains(krajprodukcji))
            {
                System.out.print("||"+Filmy[i].isfn+"| "+Filmy[i].tytul+"| "+Filmy[i].gatunek+"| "+Filmy[i].krajprodukcji+"| "+Filmy[i].cena+"| "+Filmy[i].liczbaWypozyczen+"|  ");
            if (Filmy[i].czyWypozyczona)
                System.out.println("Wypozyczna||");
            else
                System.out.println("Dostepna||");
            }
        }
    }
    
    // metoda zliczajaca i wyswietlajaca ilosc filmow, ilosc wypozyczonych filmow i laczna ilosc wypozyczen
    void state()
    {
        int ile1=0, ile2=0;
        System.out.println("Ilosc filmow: "+ileFilmow+'\n');
        for(int i = 0; i < ileFilmow; ++i)
        {
            if (Filmy[i].czyWypozyczona) ++ile1;
            ile2 += Filmy[i].liczbaWypozyczen;
        }
        System.out.println("Ilosc aktualnie wypozyczonych filmow: "+ile1+'\n');
        System.out.println("Ilosc wszystkich wypozyczen: "+ile2+'\n');
    }
    
    // metoda wyswietlajaca 5 najczesciej wyporzyczanych egzemplarzy ksiazek
    void most()
    {
        Film most[] = new Film[ileFilmow+1];
        // dodatkowe miejsca na ewentualne takie same liczby wypozyczen
        // 1 miejsce na zamiane miejscami podczas sortowania
        
        // napelniamy tabele
        for(int i = 0; i < ileFilmow; ++i)
        {
            most[i]=Filmy[i];
        }
        
        // sortujemy nowa tablice
        int n = ileFilmow;
        boolean swapped=false;
        
        do
        {
            for(int i = 1; i < n; ++i)
            {
                swapped = false;
                if ((most[i-1].liczbaWypozyczen) < (most[i].liczbaWypozyczen))
                {
                    most[ileFilmow] = most[i-1];
                    most[i-1] = most[i];
                    most[i] = most[ileFilmow];
                    swapped = true;
                }
            }
            --n;
        }while(swapped);
        // jezeli zostana zamieniona chociaz jedna para egzemplarzy, to znaczy, ze jeszcze nie posortowano tablicy
        int place = 0, amount;
        for(amount = 1; amount<ileFilmow && place<6; ++amount)
        {
            if(most[amount].liczbaWypozyczen<most[amount-1].liczbaWypozyczen)
            {
                ++place;
            }
            
        }
        
        // wyswietlamy
        for(int i = 0; i < amount; ++i)
        {
            System.out.print("||"+most[i].isfn+" |"+most[i].tytul+" |"+most[i].gatunek+" |" + most[i].krajprodukcji + " |");
                if (Filmy[i].czyWypozyczona)
                    System.out.println("Wypozyczona||");
                else
                    System.out.println("Dostepna||");
        }
    }
    
    // metoda zapisujaca ksiazki do pliku danych "wypozyczalnia.dat"
    void saveToFile()
    {
        try
        {
            RandomAccessFile file = new RandomAccessFile("wypozyczalnia.dat", "rw");
            System.out.println("Zapisywanie danych do pliku, prosze czekac");
            try
            {
                for(int i = 0; i < ileFilmow; ++i)
                {
                    filmPointer[i] = file.getFilePointer();
                    file.writeInt    (Filmy[i].isfn);
                    file.writeUTF    (Filmy[i].tytul);
                    file.writeUTF    (Filmy[i].gatunek);
                    file.writeUTF    (Filmy[i].krajprodukcji);
                    file.writeDouble    (Filmy[i].cena);
                    file.writeBoolean(Filmy[i].czyWypozyczona);
                    file.writeInt    (Filmy[i].liczbaWypozyczen);
                }
                System.out.println("Zakonczono zapisywanie.\n");
            }
            catch(IOException e)
            {
                System.out.println("Blad przy zapisywaniu danych z pliku");
            }
            finally
            {
                file.close();
            }
        }
        catch(IOException e)
        {
            System.out.println("Brak dostepu do pliku danych");
            System.out.println("Sprawdz plik i uruchom program ponownie");
        }
    }


}