package biblioteka;

import java.util.Scanner;

public class Main {
    
  static boolean dalej=true; // zmienne operujaca petla glownego menu
  static String instr,instr1; 
  // zmienna przekazujaca instrukcje w menu i podmenu
  
  // te zmienne sa potrzebne do dodawania i edycji informacji o filmach
  static int isfn;
  static String tytul, gatunek, krajprodukcji;
  static double cena;

    public static void main(String[] args)
    {
        WypozyczalniaDVD wypozyczalniadvd = new WypozyczalniaDVD();
        try{
            wypozyczalniadvd.read();
        }catch(Exception er){
            System.out.println("Zla sciezka pliku");
        }
        do
        {
         System.out.println("_______________________________________________");
         System.out.println("|     Witaj w systemie wypozyczalniDVD         |");
         System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
            Scanner sc = new Scanner(System.in);
            System.out.println("\nDostepne instrukcje:\n"
                    + ">1 Pokaz wszystkie filmy\n"
                    + ">2 Dodaj film do wypozyczalni\n"
                    + ">3 Edytuj film\n"
                    + ">4 Wypozycz film\n"
                    + ">5 Oddaj wypozyczony film\n"
                    + ">6 Szukaj filmy po kategorii\n"
                    + ">7 Inne instrukcje\n"
                    + ">8 Koniec (konczy prace programu)\n"
                    + "Jaki jest twoj wybor drogi uzytkowniku:\n");
            instr = sc.nextLine().trim();
            switch (instr)
            {
                
                    
                    
                case "1":
                    wypozyczalniadvd.showAll();
                    break;
                    
                    
                case "2":
                    
                    System.out.println("\nProsze podac tytul: ");
                    try {tytul = sc.nextLine().trim();}
                    catch(Exception e)
                    {
                        System.out.println("\nNie podano prawidlowej wartosci");
                        break;
                    }
                    System.out.println("\nProsze podac gatunek: ");
                    try {gatunek = sc.nextLine().trim();}
                    catch(Exception e)
                    {
                        System.out.println("\nNie podano prawidlowej wartosci");
                        break;
                    }
                    System.out.println("\nProsze podac kraj produkcji: ");
                    try {krajprodukcji = sc.nextLine().trim();}
                    catch(Exception e)
                    {
                        System.out.println("\nNie podano prawidlowej wartosci");
                        break;
                    }
                    System.out.println("\nProsze podac cene: ");
                    try {cena = Double.parseDouble(sc.nextLine().trim());}
                    catch(Exception e){
                         System.out.println("\nNie podano prawidlowej wartosci");
                    }
                    
                    wypozyczalniadvd.addNewFilm(tytul, krajprodukcji, gatunek, cena);
                    break;
                    
                    
                case "3":
                    System.out.println("\nPodaj identyfikator filmu: ");
                    try {isfn = sc.nextInt();}
                    catch(Exception e)
                    {
                        System.out.println("\nNie podano prawidlowej wartosci");
                        break;
                    }
                    wypozyczalniadvd.editFilm(isfn);
                    break;
                    
                    
                case "4":
                    System.out.println("\nPodaj identyfikator filmu: ");
                    try {isfn = Integer.parseInt(sc.nextLine().trim());}
                    catch(Exception e)
                    {
                        System.out.println("\nNie podano prawidlowej wartosci");
                        break;
                    }
                    wypozyczalniadvd.borrowFilm(isfn);
                    break;
                    
                    
                case "5":
                    System.out.println("\nPodaj identyfikator filmu: ");
                    try {isfn= Integer.parseInt(sc.nextLine().trim());}
                    catch(Exception e)
                    {
                        System.out.println("\nNie podano prawidlowej wartosci");
                        break;
                    }
                    wypozyczalniadvd.returnFilm(isfn);
                    break;
                    
                    
                case "6":
                    System.out.println("\nMozesz wyszukac film po:\n"
                         + ">Tytul\n"
                         + ">Gatunek\n"
                         + ">Kraj produkcji\n"
                         + "Podaj jak chcesz wyszukac film \n");
                         instr1 = sc.nextLine().toLowerCase();
                 switch(instr1)
                 {
                     case"tytul":
                         System.out.println("\nPodaj szukany tytul");
                         try
                         {
                             tytul = sc.nextLine();
                         }catch(Exception e)
                    {
                        System.out.println("\nNie podano prawidlowej wartosci");
                        break;
                    }
                         wypozyczalniadvd.showFilm1(tytul);
                         break;
                         
                         case"gatunek":
                         System.out.println("\nPodaj szukany gatunek");
                         try
                         {
                             gatunek = sc.nextLine();
                         }catch(Exception e)
                    {
                        System.out.println("\nNie podano prawidlowej wartosci");
                        break;
                    }
                         wypozyczalniadvd.showFilm2(gatunek);
                         break;
                         
                         case"kraj produkcji":
                         System.out.println("\nPodaj szukany krajprodukcji");
                         try
                         {
                             krajprodukcji = sc.nextLine();
                         }catch(Exception e)
                    {
                        System.out.println("\nNie podano prawidlowej wartosci");
                        break;
                    }
                         wypozyczalniadvd.showFilm3(krajprodukcji);
                         break;
                 }
                    break;
               
                case "7":
                    System.out.println("\nDostepne inne instrukcje: \n"
                            + ">Stan \n"
                            + ">Popularne \n"
                            + "Jaki jest twoj wybor drogi uzytkowniku\n");
                    instr1 = sc.nextLine().trim().toLowerCase();
                    switch(instr1)
                    {
                        case "stan":
                            wypozyczalniadvd.state();
                            break;
                        case "popularne":
                            wypozyczalniadvd.most();
                            break;
                    }
                    break;
                    
                    
                case "8":
                    dalej=false;
                    break;
                    
                    
                default:
                    System.out.println("\nPodano bledna instrukcje");
                    break;
            }
        }while(dalej);
    }
}