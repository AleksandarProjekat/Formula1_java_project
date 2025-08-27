# F1 Grand Prix Simulation (Java)

## 📌 Opis
Ovaj projekat je urađen kao školski zadatak iz Jave (školska 2022/2023).  
Cilj projekta je da se simulira **F1 Grand Prix šampionat** kroz više trka i krugova, sa vozačima i stazama učitanim iz fajlova.

Tokom svake trke uzimaju se u obzir:
- startne pozicije i vremenske kazne,
- prosečno vreme vožnje po stazi,
- specijalne veštine vozača (*braking, cornering, overtaking*),
- slučajni događaji (kvarovi, kiša, zamena pneumatika),
- ažuriranje poena i renkinga.

Na kraju šampionata određuje se **pobednik sezone**.

---

## 🏎️ Funkcionalnosti
- Učitavanje **vozača** iz fajla `vozaci.txt`
- Učitavanje **staza** iz fajla `staze.txt`
- Postavljanje startnih pozicija po renkingu
- Simulacija krugova:
  - prosečno vreme kruga
  - primena specijalnih veština
  - kvarovi i eliminacija vozača
  - kiša i uticaj pneumatika
- Rangiranje vozača i dodela poena
- Ispis pobednika svake trke
- Ispis pobednika celog šampionata

---

## 🗂️ Struktura projekta
- `Driver.java` – klasa vozača
- `Venue.java` – klasa staze
- `Championship.java` – upravljanje šampionatom
- `RNG.java` – generisanje slučajnih brojeva u zadatom opsegu
- `Simulate.java` – glavna klasa sa `main` metodom
