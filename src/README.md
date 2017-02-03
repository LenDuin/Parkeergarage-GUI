# Project Parkeergarage

Tijdens dit project zullen jullie in projectgroepen van 3 of 4
personen gaan werken aan een simulatieprogramma. Het doel van deze
applicatie is om aan een parkeergarage informatie te verstrekken
die gebruikt kan worden om het parkeerbeleid, met name de indeling
van de garage met gereserveerde plekken voor abonnementen en
reserveringen te optimaliseren. In dit project zullen alle
onderwerpen die de revue hebben gepasseerd tijdens dit thema
(Object georiënteerd programmeren, configuration managment en human
computer interaction) aan bod komen.

## Case beschrijving "Parkeergarage Cityparking Groningen".
(dit is een verzonnen case; het bedrijf bestaat niet echt in
Groningen).

### 1. De Organisatie
De parkeergarage "Cityparking Groningen" bevindt zich in het centrum
van Groningen, een grote stad in het noordoosten van Nederland. De
garage heeft 500 parkeerplekken verdeeld over drie verdiepingen.
Het bedrijf heeft 15 werknemers en is 24 uur per dag, zeven dagen
per week open.

Bezoekers kunnen hun auto in de garage parkeren door een kaartje te
nemen bij het inrijden, en bij vertrek te betalen bij de in de garage
geplaatste betaalautomaten. Een andere mogelijkheid is om een
abonoment te nemen en te parkeren op een van de voor abonnementhouders
gereserveerde plekken. Als extra service wil het bedrijf het mogelijk
maken om via de website van de parkeergarage van tevoren een plek te
reserveren. Hierbij moet de verwachte aankomsttijd ingevuld worden.
Er wordt dan een kwartier van tevoren tot een half uur na de aangegeven
tijd een plek gereserveerd.

De garage heeft twee ingangen en een uitgang. Een van de twee ingangen
wordt gebruik door abonnementhouders en zal ook door de klanten die
een plek hebben gereserveerd kunnen worden gebruikt.

De garage maakt bij het in en uitrijden van de garage gebruik van
nummerbordherkenning. Abonnementhouders hebben dus geen kaartje
nodig en kunnen zonder langs de betaalautomaat te gaan uit de garage
rijden. Betaling van de reserveringen gebeurt wel bij de
betaalautomaten. Dit gebeurt op basis van het nummerbord. De klant
voert de gegeven van zijn nummerbord in, betaalt voor de periode die
hij in de garage heeft gestaan en kan vervolgens uitrijden.

Er is een maximum aan het aantal abonnementhouders omdat maar een
beperkt deel van de garage gereserveerd wordt voor deze klanten.
Er is dus ook een wachtlijst voor abonnementen.

Naast de parkeergarage staat het beroemde theater "het Concertgebouw".
Dit theater biedt plaats aan 1000 bezoekers en elk weekend zijn er
voorstellingen op vrijdag en zaterdagavond en zondagmiddag die altijd
uitverkocht zijn. Dit zorgt voor extra drukte in de parkeergarage.
Verder zijn er op donderdagavond koopavonden die ook voor extra
klanten zorgen.

Op sommige momenten is de rij voor de garage zo lang dat potentiele
klanten doorrijden en op zoek gaan naar een andere parkeerplek.

### 2. De Simulator.
"Cityparking Groningen" gebruikt het programma project-parkeer-garage.
Dit is een simulatie van het gebruik van de pareergarage. De organisatie
wil de simulatie gebruiken om te onderzoeken wat het effect is van
het veranderen van het maximumaantal abonnementhouders en reserveringen
op de bezettingsgraad en de omzet van de garage en ook op het aantal
gemiste klanten. In de huidige applicatie is de volgende
functionaliteit al geimplementeerd:
* Een simulatie van het aankomen en vertrekken van de klanten.
    * Queues
    * Aankomst van gewone klanten en abonnementhouders.
* Betaling bij vertrek.
    * Reguliere betaling
    * Gebruik van abonnement
* Een grafische weergave van de bezetting van de garage.

Het is nog niet mogelijk om een deel van de garage te reserveren
voor klanten met een abonnement.