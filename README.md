    \# Android Diari de Viatges - Storage



Aplicació Android desenvolupada amb Kotlin i Jetpack Compose

que permet crear, guardar, consultar i exportar cròniques de viatges.



\## Tecnologies utilitzades



\- Kotlin

\- Android Studio

\- Jetpack Compose

\- Material Design 3

\- Arquitectura MVVM

\- StateFlow

\- Navigation Compose

\- JSON

\- Storage Access Framework



\## Arquitectura MVVM



El projecte està dividit en tres responsabilitats:



\### Model



RouteEntry representa les dades d'una ruta.



TravelDiaryUiState representa l'estat actual del formulari

i l'històric de viatges.



\### ViewModel



TravelDiaryViewModel gestiona les dades de la interfície,

les validacions i la comunicació amb el repositori.



Utilitza MutableStateFlow privat i StateFlow públic.



Les actualitzacions es realitzen mitjançant copy().



\### Vista



Les pantalles estan desenvolupades amb Jetpack Compose:



\- FormScreen

\- PostalScreen

\- HistoryScreen



\## Emmagatzematge intern



Les rutes es guarden en un fitxer JSON anomenat

diari\_viatges.json dins de context.filesDir.



Aquest emmagatzematge és privat de l'aplicació.



Quan es desinstal·la l'aplicació, les dades internes

s'eliminen.



\## Emmagatzematge extern



S'utilitza Storage Access Framework mitjançant

ActivityResultContracts.CreateDocument.



L'usuari pot seleccionar on guardar la crònica

en format TXT.



Els documents exportats normalment es conserven

quan es desinstal·la l'aplicació.



\## Components apresos



\- OutlinedTextField: camps de text.

\- FilterChip: selecció de dificultat.

\- DatePicker: selecció de dates.

\- Switch: selecció de viatge en solitari.

\- StateFlow: gestió reactiva de l'estat.

\- Navigation Compose: navegació entre pantalles.

\- JSONObject i JSONArray: gestió de dades JSON.

\- CreateDocument: exportació de fitxers.

\- Intent.ACTION\_SEND: compartir cròniques.



\## Captures de pantalla



\### Formulari



!\[FormScreen\](screenshots/form\_screen.png)



\### Postal



!\[PostalScreen\](screenshots/postal\_screen.png)



\### Històric



!\[HistoryScreen\](screenshots/history\_screen.png)



\## Autor



Projecte acadèmic Android - Diari de Viatges.