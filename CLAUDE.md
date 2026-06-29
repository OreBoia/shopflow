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
├── customer/     → gestione clienti (completo: Entity, Repository, Service, Controller, DTO)
├── product/      → gestione prodotti (completo: Entity, Repository, Service, Controller, DTO)
├── order/        → gestione ordini (completo: Entity, Repository, Service, Controller, DTO, OrderStatus, OrderItem)
├── category/     → categorie (Entity + Repository pronti, Service/Controller mancanti - esercizio)
├── storage/      → gestione inventario (completo: Entity, Repository, Service, Controller, DTO)
├── legacy/       → codice legacy con code smell - NON modificare senza approvazione
└── shared/       → eccezioni comuni e configurazioni

src/test/java/com/shopflow/
├── customer/     → CustomerServiceTest (3/8 test scritti, 5 mancanti - esercizio)
├── product/      → ProductServiceTest (vuoto - esercizio)
└── storage/      → StorageServiceTest (completo)

starter-mancanti/
├── category/     → template CategoryService e CategoryController per l'esercizio
├── context/      → contesto e linee guida del progetto
└── documentation/ → guide di implementazione
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

### Moduli

- `product/ProductService.java` → aggiungere Javadoc a tutti i metodi pubblici (skill: @generate-javadoc)
- `category/CategoryService.java` → implementare metodi CRUD (findAll, findById, save, update, delete) con Javadoc
- `category/CategoryController.java` → implementare endpoint REST (GET, GET/{id}, POST, PUT/{id}, DELETE/{id})

### Test

- `test/customer/CustomerServiceTest.java` → completare i 5 test mancanti su 8 (skill: @create-unit-test)
- `test/product/ProductServiceTest.java` → generare tutti i test da zero per ProductService (skill: @create-unit-test)
- `test/category/CategoryServiceTest.java` → generare test per i metodi CRUD

### Legacy

- `legacy/LegacyOrderProcessor.java` → analizzare e refactorare il codice legacy

## Skills disponibili

- `@generate-javadoc` → genera Javadoc per classi e metodi
- `@create-unit-test` → genera test JUnit 5 + Mockito

## Agents disponibili

- `@new-feature-agent` → crea una feature completa (entity + repo + service + controller + test)
