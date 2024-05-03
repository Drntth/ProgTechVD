# Rendszerterv

## 1. Cél

A rendszerterv célja egy JavaFX alapú könyvtárkezelő alkalmazás tervezése, amely felhasználóbarát felhasználói felülettel rendelkezik és adatokat tárol egy adatbázisban. A tervezés célja a hatékony és strukturált fejlesztés elősegítése, valamint az alkalmazás szükségleteinek és működésének részletes leírása.

## 2. Projektterv

### 2.1 Szerepek

- **Scrum Master:** A fejlesztési csapat agilis munkamódszerekkel történő koordinálása és támogatása.
- **Termék tulajdonos (Product Owner):** Az üzleti igények és követelmények képviselete, valamint a végtermék funkcionalitásának felügyelete.
- **Üzleti érdekelt (Business Stakeholder):** A projekthez kapcsolódó üzleti szempontok és elvárások megfogalmazása.

### 2.2 Feladatkörök

- **Frontend:** A felhasználói felület megtervezése és kialakítása JavaFX segítségével.
- **Backend:** Az alkalmazás logikájának és adatkezelésének megvalósítása Java nyelven.
- **Tesztelés:** Az alkalmazás funkcionalitásának és teljesítményének tesztelése.

### 2.3 Ütemezés

A projekt ütemezése az alábbi fő feladatok mentén történik:

| Funkció                | Feladat                       | Prioritás | Becslés |
|------------------------|-------------------------------|-----------|---------|
| Követelmények          | Dokumentáció írása             | 1         | 1 hét    |
| Funkcionális specifikáció | Dokumentáció írása             | 1         | 1 hét    |
| Rendszerterv           | Dokumentáció írása             | 1         | 1 hét    |
| Képernyőtervek készítése | Felhasználói felület tervezése | 2         | 2 hét    |
| Prototípus készítése    | Felhasználói felület kialakítása| 3         | 4 hét    |
| Alapfunkciók megvalósítása | Backend logika fejlesztése    | 3         | 6 hét    |
| Tesztelés               | Funkcionalitás ellenőrzése     | 4         | 2 hét    |

## 3. Üzleti folyamatmodell

### 3.1 Szereplők

- **Felhasználók:** Az alkalmazás végfelhasználói, akik regisztrálhatnak, könyveket böngészhetnek, kölcsönözhetnek és visszahozhatnak.

### 3.2 Folyamatok

- **Regisztráció folyamata:** Felhasználói fiók létrehozása az alkalmazásba való belépéshez.
- **Könyvek böngészése:** A felhasználók által böngészhető könyvek listájának megjelenítése.
- **Kölcsönzés folyamata:** Felhasználók által kiválasztott könyv kölcsönzése.
- **Visszahozatal folyamata:** Kölcsönzött könyv visszahozatalának folyamata.

## 4. Követelmények

### 4.1 Funkcionális követelmények

- **Regisztráció:** Felhasználók képesek legyenek regisztrálni az alkalmazásba.
- **Könyv böngészés:** Felhasználók listázhatják és böngészhetik a rendelkezésre álló könyveket.
- **Kölcsönzés:** Felhasználók könyveket kölcsönözhetnek.
- **Visszahozatal:** Felhasználók visszahozhatják a korábban kölcsönzött könyveket.

### 4.2 Nem funkcionális követelmények

- **Felhasználóbarát felület:** Az alkalmazásnak intuitív és könnyen kezelhető felhasználói felülettel kell rendelkeznie.
- **Teljesítmény:** Az alkalmazásnak gyorsnak és megbízhatónak kell lennie még nagy adatmennyiség esetén is.

## 5. Funkcionális tervezés

### 5.1 Szereplők

- **Felhasználó:** Regisztrált felhasználók, akik könyveket böngészhetnek, kölcsönözhetnek és visszahozhatnak.

### 5.2 Menük

Az alkalmazás menürendszere tartalmazza a következő funkciókat:

- **Könyvek böngészése:** Az elérhető könyvek listájának megjelenítése.
- **Keresés:** Könyvek keresése cím, szerző vagy kategória alapján.
- **Kölcsönzés:** Könyvek kölcsönzése.
- **Fiók kezelése:** Felhasználói fiók adatainak és kölcsönzéseinek kezelése.
