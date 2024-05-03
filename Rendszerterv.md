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
- **Megvásárlás folyamata:** Felhasználók által kiválasztott könyv megvásárlása.

## 4. Követelmények

### 4.1 Funkcionális követelmények

- **Regisztráció:** Felhasználók képesek legyenek regisztrálni az alkalmazásba.
- **Könyv böngészés:** Felhasználók listázhatják és böngészhetik a rendelkezésre álló könyveket.
- **Vásárlás:** Felhasználók könyveket vásárolhatnak.

### 4.2 Nem funkcionális követelmények

- **Felhasználóbarát felület:** Az alkalmazásnak intuitív és könnyen kezelhető felhasználói felülettel kell rendelkeznie.
- **Teljesítmény:** Az alkalmazásnak gyorsnak és megbízhatónak kell lennie még nagy adatmennyiség esetén is.

## 5. Funkcionális tervezés

### 5.1 Szereplők

- **Felhasználó:** Regisztrált felhasználók, akik könyveket böngészhetnek, kölcsönözhetnek és visszahozhatnak.
- **Admin:**: Új könyvek felvétele az adatbázisba, vásárlási folyamatok kezelése.

### 5.2 Menük

Az alkalmazás menürendszere tartalmazza a következő funkciókat:

- **Könyvek böngészése:** Az elérhető könyvek listájának megjelenítése.
- **Keresés:** Könyvek keresése cím, szerző vagy kategória alapján.
- **Vásárlás:** Könyvek vásárlása.
- **Fiók kezelése:** Felhasználói fiók adatainak és kölcsönzéseinek kezelése.

## 6. Fizikai Környezet

### Szoftver Komponensek
- Java Runtime Environment (JRE) 8 vagy újabb verzió
- JavaFX 8 vagy újabb verzió (ha JavaFX-t használunk)

### Hardver
- Minimum 2 GB RAM
- Legalább 1 GHz-es processzor
- Legalább 100 MB szabad tárhely a futtatható állományoknak és az adatbázisnak

### Alrendszerek
- Operációs rendszer: Windows 7 vagy újabb, macOS, vagy Linux

### Fejlesztő Eszközök
- Java fejlesztőkörnyezet (pl. IntelliJ IDEA, Eclipse, NetBeans)

## 8. Architekturális Terv

### Web Szerver
- Nem alkalmazunk web szervert ebben a rendszerben.

### Adatbázis
- SQLite adatbázisrendszer

### Program Hozzáférés
- JDBC (Java Database Connectivity) API az adatbázishoz való hozzáféréshez

## 9. Adatbázis Terv
- **Táblák:**
  - Felhasználók tábla (users): az alkalmazás felhasználóinak adatait tárolja
  - Könyvek tábla (books): a rendszerben található könyvek adatait tárolja
  - Rendelések tábla (orders): a felhasználók által indított vásárlások kezeléséhez szükséges adatokat tárolja
  - Jogosultságok tábla (roles): tartalmazza, hogy egy felhasználó admin vagy regisztrált vásárló

## 10. Megvalósítási Terv
- **Backend fejlesztés:**
  - Felhasználókezelés megvalósítása
  - Könyvkezelés megvalósítása
  - Vásárlás megvalósítása
- **Frontend fejlesztés:**
  - Felhasználói felület kialakítása és megvalósítása JavaFX segítségével

## 11. Teszt Terv
- **Egységtesztek:**
  - Backend egységtesztelése JUnit keretrendszerrel
  - Felhasználói felület tesztelése manuális és automatizált tesztekkel

## 12. Telepítési Terv

### Fizikai Telepítés:
- A rendszer telepítése a szerver hardverére vagy a felhasználók számítógépeire

### Szoftver Telepítés:
- SQLite adatbázisrendszer telepítése
- SQLite JDBC driver telepítése
- A könyvtárkezelő alkalmazás futtatható fájljainak telepítése

## 13. Karbantartási Terv
- **Rendszerfrissítések:**
  - A Java és a JavaFX verzióinak frissítése, ha szükséges
- **Adatbázis karbantartás:**
  - Adatbázis mentési rutinok kialakítása és futtatása rendszeresen