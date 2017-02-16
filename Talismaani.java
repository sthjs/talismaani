/* 
 * Talismaani-lautapelin inspiroima peli.
 * Versio 0.001 20.10.2012
 * Versio 0.002 10.11.2012-11.11.2012
 * Versio 0.003 25.12.2012
 *   - hahmolomake-taulukko
 *   - kylän toiminnot
 *   - niittysilmukka ja niityn toiminnot
 *   - niityn sijainnit ja tutkiNiitty-metodi
 *
 * Kirjoittanut Juho Karvinen
 *
 * Koodi ja muu sisältö on GPL-lisenssin alaista.
 * 
 */

/*
 * Huomioita:
 * Tässä vaiheessa ei tehdä valmista vaan kehitellään ideoita.
 * Koodia on rakenneltu pitkin ohjelmoinnin peruskurssia, eli ratkaisut eivät aina ole
 * fiksuimpia mahdollisia.
 *
 */

public class Talismaani { 
   public static void main(String[] args) {
      /*
       * Esitellään ja alustetaan vakiot.
       *
       */

      // Viholliset.
      final char HIISI = 'h';

      /*
       * Esitellään ja alustetaan muuttujat.
       *
       */

      // Alueiden lippumuuttujat. Peli alkaa kylästä.
      boolean kylassa = true;
      boolean niitylla = false;
      boolean louhoksessa = false;
      boolean metsassa = false;
      boolean aavikolla = false;

      // Sijainti alueella.
      int sijainti = 1;

      // Tapahtumat alueilla.
      char niitynTapahtuma;
      char louhoksenTapahtuma;
      char metsanTapahtuma;
      char aavikonTapahtuma;

      // Vihollismuuttuja
      char vihollinen;

      // Valintamuuttuja kyllä-ei-kysymyksiin.
      char valinta;

      // Lippumuuttuja, joka kertoo, ollaanko elossa.
      boolean elossa = true;

      // Pääsilmukan lippumuuttuja.
      boolean lopetetaan = false;

      /*
       * Luodaan hahmo.
       */
      int[] omin = luoHahmo();

      /*
       *  Peli alkaa!
       */

      // Kerrotaan taustatarina.
      System.out.println("Saavut kylään.");

      // Pelin pääsilmukka. Peli loppuu, kun silmukasta poistutaan.
      do {
         // Kyläsilmukka
         while (kylassa) {
            System.out.println(" a = Jatka matkaasi kohti niittyjä.");
            System.out.println(" b = Puhu kylän vanhimmalle.");
            System.out.println(" c = Lepää majatalossa.");
            valinta = In.readChar();

            if (valinta == 'a') {
               kylassa = false;
               niitylla = true;
               sijainti = 1;
               System.out.println("Saavut niitylle.");
               jatketaan();
            }
            if (valinta == 'b') {
               System.out.println(" Sinun tehtäväsi on voittaa luolan mörkö.");
               System.out.println(" Luolaan päästäksesi sinun on kuljettava niityn läpi louhokseen.");
               System.out.println(" Niityt ja louhos ovat vaarallisia, mutta muuta tietä ei ole.");
               jatketaan();
            }
            if (valinta == 'c') {
               omin[2] = omin[1];
               System.out.println("Voimasi palautuivat.");
               jatketaan();
            }
         } // Kyläsilmukka.

         /* Niityt. Täällä kohdataan vihollinen!
          */

         while (niitylla) {
            System.out.println(" a = Liiku etelään.");
            System.out.println(" b = Liiku pohjoiseen.");
            System.out.println(" c = Katso karttaa.");
            if (sijainti == 0)
               System.out.println(" d = Siirry kylään");
            if (sijainti == 16)
               System.out.println(" e = Siirry louhokseen.");
            if (sijainti == 26)
               System.out.println(" f = Siirry metsään.");
            if (sijainti == 37)
               System.out.println(" g = Siirry aavikolle.");
            valinta = In.readChar();

            if (valinta == 'a') {
               if (sijainti < 37) {
                  sijainti++;
                  elossa = tutkiNiitty(omin);
               }
               else
                  System.out.println("Olet niityn eteläpäädyssä.");
            }
            if (valinta == 'b') {
               if (sijainti > 0) {
                  sijainti--;
                  elossa = tutkiNiitty(omin);
               }
               else
                  System.out.println("Olet niityn pohjoispäädyssä.");
            }
            if (valinta == 'c') {
               System.out.println(" Sijaintisi niityllä on " + sijainti + ".");
               System.out.println(" Kylän sijainti on 0.");
               System.out.println(" Louhoksen sijainti on 16.");
               System.out.println(" Metsän sijainti on 26.");
               System.out.println(" Aavikon sijainti on 37.");
               jatketaan();
            }
            if (sijainti == 0 && valinta == 'd') {
               niitylla = false;
               kylassa = true;
               sijainti = 1;
            }
            if (sijainti == 16 && valinta == 'e') {
               niitylla = false;
               louhoksessa = true;
               sijainti = 1;
            }
            if (sijainti == 26 && valinta == 'f') {
               niitylla = false;
               metsassa = true;
               sijainti = 1;
            }
            if (sijainti == 37 && valinta == 'g') {
               niitylla = false;
               aavikolla = true;
               sijainti = 1;
            }

            // Jos ollaan kuolleita, lopetetaan.
            if (!elossa)
               lopetetaan = true;

         } // Niittysilmukka.

         // Louhossilmukka
         while () {
            System.out.println("Saavuit louhokseen. Onneksi olkoon!");
            System.out.println("Peli päättyi.");
            lopetetaan = true;
         } // Louhossilmukka.
      }
      while (!lopetetaan); // Pääsilmukka.
   }

   /*
    * METODIT:
    * luoHahmo()
    * jatketaan()
    * taistelu()
    * noppa(int sivuja)
    *
    */

   public static int[] luoHahmo() {
      /* Hahmoluokat numerokoodattuna.
         v = velho (taito+)
         n = ninja (taito)
         s = samooja (tasapainoinen)
         m = soturimunkki (voima)
         b = barbaari (voima+)
      */
      final int VELHO = 1;
      final int NINJA = 2;
      final int SAMOOJA = 3;
      final int MUNKKI = 4;
      final int BARBAARI = 5;

      // Hahmoluokkien taidon ja voiman pohja-arvot.
      final int VELHOTAITO = 11;
      final int VELHOVOIMA = 3;
      final int NINJATAITO = 9;
      final int NINJAVOIMA = 5;
      final int SAMOOJATAITO = 7;
      final int SAMOOJAVOIMA = 7;
      final int MUNKKITAITO = 5;
      final int MUNKKIVOIMA = 9;
      final int BARBAARITAITO = 3;
      final int BARBAARIVOIMA = 11;

      /* Taulukko: hahmolomake eli hahmon ominaisuudet.
       * omin[0] = hahmoluokka
       * omin[1] = maksimikuntopisteet
       * omin[2] = kuntopisteet
       * omin[3] = voimapisteet
       * omin[4] = taitopisteet
       * omin[5] = kokemuspisteet
       */
      int[] omin = new int[6];

      // Valitaan hahmoluokka.
      System.out.println("Valitse hahmoluokkasi:");
      System.out.println(" v = velho");
      System.out.println(" n = ninja");
      System.out.println(" s = samooja");
      System.out.println(" m = munkki");
      System.out.println(" b = barbaari");
      char valinta = In.readChar();

      // Alustetaan hahmolomake.

      // Maksimikunto, kunto ja kokemus.
      omin[1] = 4;
      omin[2] = omin[1];
      omin[5] = 0;

      // Hahmoluokka, voima, taito, taustatarina.
      // Voima ja taito saavat arvon pohja-arvo +/- 2.
      if (valinta == 'v') {
         omin[0] = VELHO;
         omin[3] = VELHOVOIMA - 3 + noppa(5);
         omin[4] = VELHOTAITO - 3 + noppa(5);
         System.out.println("Olet valmistunut velhokoulusta.");
      }
      if (valinta == 'n') {
         omin[0] = NINJA;
         omin[3] = NINJAVOIMA - 3 + noppa(5);
         omin[4] = NINJATAITO - 3 + noppa(5);
         System.out.println("Olet valmistunut ninjakoulusta.");
      }
      if (valinta == 's') {
         omin[0] = SAMOOJA;
         omin[3] = SAMOOJAVOIMA - 3 + noppa(5);
         omin[4] = SAMOOJATAITO - 3 + noppa(5);
         System.out.println("Olet harjaantunut samooja.");
      }
      if (valinta == 'm') {
         omin[0] = MUNKKI;
         omin[3] = MUNKKIVOIMA - 3 + noppa(5);
         omin[4] = MUNKKITAITO - 3 + noppa(5);
         System.out.println("Olet viettänyt monta vuotta munkkiluostarissa.");
      }
      if (valinta == 'b') {
         omin[0] = BARBAARI;
         // Barbaarilla on ylimääräinen kuntopiste.
         omin[1]++;
         omin[2] = omin[1];
         omin[3] = BARBAARIVOIMA - 3 + noppa(5);
         omin[4] = BARBAARITAITO - 3 + noppa(5);
         System.out.println("Olet karaistunut pohjolan ylängöillä.");
      }
      jatketaan();

      // Tulostetaan hahmon ominaisuudet.
      System.out.println("Kuntosi on " + omin[1] + ".");
      System.out.println("Voimasi on " + omin[3] + ".");
      System.out.println("Taitosi on " + omin[4] + ".");
      jatketaan();

      // Palautetaan hahmolomake.
      return omin;
   }

   /*
    * Metodi: Jatketaan käyttäjän käskystä.
    */
   public static void jatketaan() {
      boolean jatketaan = false;
      char valinta = 'x';
      while (!jatketaan) {
         System.out.println("Jatkaaksesi paina j.");
         valinta = In.readChar();
         if (valinta == 'j')
            jatketaan = true;
      }
      jatketaan = false;
   }

   /*
    * Metodi: Tutki niitty.
    * Parametrinä hahmolomake.
    * Palauttaa elossa-muuttujan arvon.
    */
   public static boolean tutkiNiitty(int[] omin) {
      boolean elossa = true;
      char vihollinen;
      int noppa = noppa(100);

      // Taistelu: Hiisi 20 %
      if (noppa <= 20) {
         System.out.println("Hiisi hyökkää kimppuusi.");
         vihollinen = 'h';
         elossa = taistelu(omin, vihollinen);
      }

      // Rauhallista 75 %
      if (20 < noppa && noppa <= 95)
         System.out.println("Kirmaat niityllä.");

      // Aarre 5 %
      if (95 < noppa && noppa <= 100)
         System.out.println("Löysit aarteen! Et tiedä mitä sillä tekisi, joten jätät sen.");
      return elossa;
   }

   /*
    * Metodi: Taistelu.
    * Parametrina saadaan hahmolomake ja vihollinen.
    * Palauttaa true, jos taistelu voitetaan.
    */
   public static boolean taistelu(int[] omin, char vihollinen) {
      // Hiiden ominaisuudet.
      int HIISITAITO = 2;
      int HIISIVOIMA = 2;
      int HIISIKUNTO = 2;

      // Vihollisen ominaisuudet.
      int taitoVih = 0;
      int voimaVih = 0;
      int kuntoVih = 0;

      // Sijoitetaan goblinin ominaisuudet.
      if (vihollinen == 'h') {
         taitoVih = HIISITAITO;
         voimaVih = HIISIVOIMA;
         kuntoVih = HIISIKUNTO;
      }
      // Vihollinen kuollut, jos kunto tippu nollaan.
      boolean kuollutVih = false;

      // Voimataistelun vaihtuvat arvot.
      int voimaTaistelu = 0;
      int voimaTaisteluVih = 0;

      // Komentomuuttuja: [h]yökkää, [p]akene
      char komento = 'x';

      // Taistelu loppuu, kun jompi kumpi kuolee tai hahmo pakenee.
      boolean taistelussa = true;
      while (taistelussa) {
         System.out.println("Haluatko hyökätä (h) vai paeta (p)?");
         komento = In.readChar();
         if (komento == 'h') {
            // Hahmo hyökkää.
            voimaTaistelu = omin[3] + noppa(6);
            System.out.println("Hyökkäsit voimalla " + voimaTaistelu + ".");
            // Vihollinen puolustaa.
            voimaTaisteluVih = voimaVih + noppa(6);
            System.out.println("Vihollinen puolusti voimalla " + voimaTaisteluVih + ".");
            if (voimaTaistelu > voimaTaisteluVih) {
               kuntoVih--;
               System.out.println("Osuit.");
            }
            else
               System.out.println("Et osunut.");
            // Vihollinen kuolee, jos sen kunto tippuu nollaan.
            // Kokemus kasvaa vihollisen voiman mukaisesti.
            if (kuntoVih == 0) {
               kuollutVih = true;
               taistelussa = false;
               omin[5] = omin[5] + voimaVih;
               System.out.println("Vihollinen kuoli. Onneksi olkoon!");
               System.out.println("Sait " + voimaVih + " kokemuspistettä.");
            }
            jatketaan();

            // Vihollinen hyökkää, jos on elossa.
            if (kuollutVih == false) {
               voimaTaisteluVih = voimaVih + noppa(6);
               System.out.println("Vihollinen hyökkäsi voimalla " + voimaTaisteluVih + ".");
               // Hahmo puolustaa.
               voimaTaistelu = omin[3] + noppa(6);
               System.out.println("Puolustit voimalla " + voimaTaistelu + ".");
               if (voimaTaistelu > voimaTaisteluVih) {
                  System.out.println("Torjuit.");
               }
               else {
                  omin[2]--;
                  System.out.println("Vihollinen osui. Kuntosi on " + omin[2] + ".");
               }
               // Hahmo kuolee, jos kunto tippuu nollaan. Peli päättyy!
               if (omin[2] == 0) {
                  System.out.println("Kuolit. Peli päättyi!");
                  taistelussa = false;
               }
            }
         } // Komento: hyökkää.
         if (komento == 'p') {
            System.out.println("Pakenit kylään.");
            taistelussa = false;
         } // Komento: pakene.
      } // Taistelussa.
      if (kuollutVih)
         return true;
      else
         return false;
   }

   /*
    * Metodi: Noppa.
    * Nopan sivujen lukumäärä (eli nopan suurin arvo) parametrina.
    * Palauttaa nopanheiton arvon.
    */
   public static int noppa(int sivuja) {
      int noppa = 1 + (int)(sivuja * Math.random());
      return noppa;
   }

}
