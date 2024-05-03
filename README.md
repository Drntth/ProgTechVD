# Libra

Ez a Java alkalmazás egy könyvtárkezelő rendszert valósít meg, amely felhasználóbarát felhasználói felülettel rendelkezik, adatokat tárol adatbázisban, és számos tervezési mintát alkalmaz, miközben betartja az OCP (Open-Closed Principle) és SRP (Single Responsibility Principle) elveket. Az alkalmazás ablakos környezetben fut.

## Funkciók

- **Bejelentkezés és jogosultságok**: Az alkalmazás lehetővé teszi a felhasználók bejelentkezését. Az adminisztrátoroknak kiterjesztett jogosultságaik vannak a könyvek kezelésére, míg a felhasználóknak csak olvasási joguk van.

- **Könyvek böngészése**: A felhasználók böngészhetik a rendelkezésre álló könyveket listázva.

- **Könyv hozzáadása**: Az adminisztrátorok új könyveket adhatnak hozzá a rendszerhez cím, szerző, ár, kiadó és egyéb információk megadásával.

- **Könyv szerkesztése és törlése**: Az adminisztrátorok módosíthatják a könyvek adatait vagy törölhetik azokat a rendszerből.

## Technológiák és elvek

- **Java nyelv**: Az alkalmazás teljes mértékben Java nyelven íródott.

- **Ablakos alkalmazás**: Az alkalmazás ablakos felhasználói felülettel rendelkezik.

- **Adatok tárolása adatbázisban**: Az alkalmazás adatbázisrétege JDBC-t használ a könyvek tárolására és kezelésére.

- **Log**: A naplózás segíti az alkalmazás működését monitorozását és hibakeresését.

- **OCP, SRP szem előtt tartása**: Az alkalmazás kódszerkezete és tervezése betartja az OCP és SRP elveket, hogy a kód könnyen bővíthető és karbantartható legyen.

- **Unit tesztek**: Az alkalmazás részegység-teszteket használ a kritikus komponensek tesztelésére.

- **Feladatkövetés**: A projekt követi a fejlesztési folyamatot, beleértve a feladatok felosztását és megvalósítását.

- **Repository használata**: A projekt verziókezelést használ a forráskód változásainak nyomon követésére és verziókezelésre.

- **Nagyvonalú rendszerterv**: A projekt magában foglal egy nagyvonalú rendszertervet, amely lefedi a felhasználói igényeket és a technikai megvalósítást.

- **Tervezési minták**: Az alkalmazás legalább két tervezési mintát alkalmaz, például Observer minta az események kezelésére, valamint Factory minta a létrehozás folyamatok kiszervezésére.

## Közreműködők

- Sipos Valentin Dominik (JPCM1W)
- Tóth Dorina Ildikó (FYA26Y)

