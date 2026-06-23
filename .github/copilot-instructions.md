# Istruzioni per GitHub Copilot Chat — ShopFlow

## Stack e linguaggio
- Java 17, Spring Boot 3.2, Maven
- Non suggerire Kotlin, Lombok o Jakarta EE standalone
- Framework test: JUnit 5 + Mockito

## Stile del codice
- Naming in camelCase per metodi e variabili
- PascalCase per classi e interfacce
- UPPER_SNAKE_CASE per costanti
- Massimo 100 caratteri per riga
- Iniezione dipendenze sempre via costruttore, mai @Autowired su campo

## Regole di sicurezza
- Non concatenare stringhe in query SQL → usare parametri con ?
- Validare sempre l'input con @Valid e Jakarta Bean Validation
- Non restituire mai null → usare Optional<T>
- Non esporre stacktrace nelle API REST

## Testing
- Usare @ExtendWith(MockitoExtension.class) per test unitari
- Usare assertThat() di AssertJ, non assertEquals() di JUnit
- Ogni metodo pubblico del Service deve avere almeno un test positivo e uno negativo
- Usare @DisplayName con descrizione leggibile in italiano

## Logging
- Usare SLF4J: private static final Logger log = LoggerFactory.getLogger(ClassName.class)
- Non usare System.out.println in nessun caso

## Cosa evitare
- Non generare metodi statici di utilità dove un Service è più appropriato
- Non usare field injection (@Autowired su campo)
- Non lanciare eccezioni generiche (Exception, RuntimeException) → usare BaseAppException
