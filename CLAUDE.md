# ShopFlow — Contesto progetto per Claude Code

## Descrizione

Backend REST per la gestione di un e-commerce.
Progetto didattico del corso AI Experis.

## Stack tecnologico

- Java 17
- Spring Boot 3.2
- PostgreSQL 15
- Maven 3.9
- JUnit 5 + Mockito
- Testcontainers (test integrazione)
- Flyway (migrazione DB)

## Struttura del progetto

```
src/main/java/com/shopflow/
├── customer/     → gestione clienti (modello completo di riferimento)
├── product/      → gestione prodotti (documentazione mancante)
├── order/        → gestione ordini (logica parziale da completare)
├── category/     → categorie (service e controller mancanti - esercizio)
├── legacy/       → codice legacy con code smell - NON modificare senza approvazione
└── shared/       → eccezioni comuni e configurazioni
```

## Convenzioni di naming

- Entità JPA:        `[Entity].java`
- Repository:        `[Entity]Repository.java`
- Service:           `[Entity]Service.java`
- Controller:        `[Entity]Controller.java`
- DTO Input:         `[Entity]Request.java`
- DTO Output:        `[Entity]Response.java`
- Test:              `[Entity]ServiceTest.java`

## Regole obbligatorie

- Tutte le eccezioni custom estendono `BaseAppException`
- Non usare `null` come valore di ritorno → usare `Optional<T>`
- Non usare `@Autowired` su campo → iniezione sempre via costruttore
- Logger SLF4J: `private static final Logger log = LoggerFactory.getLogger(ClassName.class)`
- Non concatenare stringhe in query SQL → usare parametri `?` o Spring Data
- Non esporre stacktrace nelle risposte REST → gestire tutto in `GlobalExceptionHandler`
- Tutti i metodi pubblici nei Service devono avere Javadoc

## Package base

`com.shopflow`

## Aree con TODO (esercizi del corso)

- `order/OrderService.java` → aggiungere verifica stock e calcolo totale
- `product/ProductService.java` → aggiungere Javadoc completo
- `category/` → creare CategoryService e CategoryController
- `test/.../CustomerServiceTest.java` → completare i 5 test mancanti
- `test/.../ProductServiceTest.java` → generare tutti i test da zero
- `legacy/LegacyOrderProcessor.java` → analizzare e refactorare

## Skills disponibili

- `@generate-javadoc` → genera Javadoc per classi e metodi
- `@create-unit-test` → genera test JUnit 5 + Mockito

## Agents disponibili

- `@new-feature-agent` → crea una feature completa (entity + repo + service + controller + test)
