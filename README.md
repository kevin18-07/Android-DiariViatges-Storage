# 🌍 Android Diari de Viatges - Storage

Aplicació Android desenvolupada amb Kotlin i Jetpack Compose que permet crear, gestionar, guardar i exportar cròniques de viatges i escapades.

## 📱 Descripció del projecte

L'objectiu d'aquesta aplicació és permetre a l'usuari registrar les seves rutes i experiències de viatge mitjançant un formulari interactiu.

Les dades es poden guardar en un historial privat dins de l'aplicació i exportar com a documents de text perquè l'usuari pugui conservar-les i compartir-les.

## 🛠️ Tecnologies utilitzades

-   Kotlin
-   Android Studio
-   Jetpack Compose
-   Material Design 3
-   Arquitectura MVVM
-   StateFlow
-   Navigation Compose
-   JSON
-   Internal Storage (`filesDir`)
-   Storage Access Framework (SAF)
-   ActivityResultContracts.CreateDocument

## 🏗️ Arquitectura MVVM

El projecte utilitza l'arquitectura MVVM (Model-View-ViewModel), que separa les dades, la lògica de negoci i la interfície gràfica.

### Model

El model conté les classes que representen les dades de l'aplicació:

-   `RouteEntry`: representa una ruta amb el nom, destinació, dificultat, pressupost, data, tipus de viatge i notes.
-   `TravelDiaryUiState`: representa l'estat de la interfície i les dades del formulari.

### ViewModel

`TravelDiaryViewModel` gestiona l'estat de l'aplicació mitjançant `MutableStateFlow` i `StateFlow`.

Les dades s'actualitzen de manera immutable utilitzant `copy()`.

També s'encarrega de validar els camps del formulari i coordinar el guardat de les rutes.

### View

La interfície està desenvolupada amb Jetpack Compose i està dividida en diferents pantalles:

-   `FormScreen`: formulari per crear una nova ruta.
-   `PostalScreen`: mostra la crònica generada en format postal.
-   `HistoryScreen`: permet consultar les rutes guardades.

### Navegació

S'utilitza Navigation Compose per navegar entre les diferents pantalles i mostrar la informació de la ruta seleccionada.

## 💾 Emmagatzematge intern

Les dades de les rutes es guarden en un fitxer JSON anomenat:

`diari_viatges.json`

Aquest fitxer s'emmagatzema dins de `context.filesDir`.

L'emmagatzematge intern és privat de l'aplicació i només aquesta pot accedir directament a les seves dades.

Quan l'usuari tanca i torna a obrir l'aplicació, les rutes es poden recuperar llegint el fitxer JSON.

**Què passa si es desinstal·la l'aplicació?**

Les dades guardades a `filesDir` s'eliminen automàticament quan es desinstal·la l'aplicació.

## 📂 Emmagatzematge extern i exportació

Per exportar les cròniques s'utilitza Storage Access Framework (SAF), mitjançant:

`ActivityResultContracts.CreateDocument("text/plain")`

Aquesta funcionalitat permet obrir el selector de documents del sistema Android perquè l'usuari pugui escollir on desar la seva crònica en format TXT.

L'usuari pot seleccionar carpetes com Documents o Descàrregues.

**Què passa si es desinstal·la l'aplicació?**

Els documents exportats es guarden fora de l'espai privat de l'aplicació i normalment es conserven després de desinstal·lar-la.

## 🧩 Components apresos

| Component              | Funció                                           |
| ---------------------- | ------------------------------------------------ |
| OutlinedTextField      | Introducció de dades mitjançant camps de text.   |
| FilterChip             | Selecció del nivell de dificultat.               |
| DatePicker             | Selecció de la data de la ruta.                  |
| Switch                 | Indica si el viatge s'ha fet en solitari.        |
| StateFlow              | Gestió reactiva de l'estat de la interfície.     |
| copy()                 | Actualització immutable de les dades.            |
| Navigation Compose     | Navegació entre pantalles.                       |
| JSONObject / JSONArray | Serialització i lectura de dades JSON.           |
| CreateDocument         | Creació i exportació de documents.               |
| Intent.ACTION_SEND     | Compartició de cròniques amb altres aplicacions. |

## 📁 Estructura del projecte

text

Android-DiariViatges-Storage/  
│  
├── app/  
│   └── src/main/  
│       ├── java/com/example/diari\_viatges/  
│       │   ├── data/  
│       │   │   ├── RouteEntry.kt  
│       │   │   └── TravelDiaryRepository.kt  
│       │   │  
│       │   ├── viewmodel/  
│       │   │   └── TravelDiaryViewModel.kt  
│       │   │  
│       │   ├── ui/  
│       │   │   ├── FormScreen.kt  
│       │   │   ├── PostalScreen.kt  
│       │   │   └── HistoryScreen.kt  
│       │   │  
│       │   ├── navigation/  
│       │   │   └── AppNavigation.kt  
│       │   │  
│       │   └── MainActivity.kt  
│       │  
│       └── AndroidManifest.xml  
│  
├── gradle/  
├── build.gradle.kts  
├── settings.gradle.kts  
└── README.md

## 📸 Captures de pantalla

Quan l'aplicació estigui executada, afegiré les captures de les diferents pantalles en aquesta secció.

### Formulari de nova ruta

!FormScreen

### Postal de viatge

!PostalScreen 

### Historial de rutes

!HistoryScreen

## 🚀 Instal·lació i execució

1.  Descarregar o clonar el repositori.
2.  Obrir el projecte amb Android Studio.
3.  Esperar que Gradle sincronitzi les dependències.
4.  Executar l'aplicació en un emulador o dispositiu Android.