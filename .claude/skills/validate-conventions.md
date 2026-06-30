# Skill: validate-conventions

## Obiettivo

Verificare che un file o modulo Java rispetti tutte le convenzioni obbligatorie
definite nel CLAUDE.md di ShopFlow. Usare come checklist finale prima di consegnare
un esercizio o aprire una PR.

## Come richiamare

"Esegui @validate-conventions su [file o modulo]"
Esempi: "valida CategoryService", "esegui @validate-conventions su tutto il modulo order"

## Checklist da verificare

### 1. Iniezione dipendenze
- VIETATO: `@Autowired` su campo (es. `@Autowired private CustomerRepository repo;`)
- CORRETTO: costruttore con tutti i parametri richiesti, senza `@Autowired` sul costruttore
  se c'è un solo costruttore (Spring lo riconosce automaticamente)

### 2. Valori di ritorno
- VIETATO: restituire `null` da metodi pubblici
- CORRETTO: usare `Optional<T>` quando il valore può essere assente

### 3. Logger SLF4J
- Ogni classe che fa logging deve avere:
  `private static final Logger log = LoggerFactory.getLogger(NomeClasse.class);`
- VIETATO: `System.out.println`, `System.err.println`, `e.printStackTrace()`

### 4. Eccezioni custom
- Tutte le eccezioni custom devono estendere `BaseAppException`
  (package: `com.shopflow.shared.exception`)
- VIETATO: lanciare `RuntimeException` o `Exception` direttamente nei Service
- Eccezione accettata: `IllegalArgumentException` per validazioni di input

### 5. Query sicure
- VIETATO: concatenazione di stringhe in query (`"SELECT * FROM " + tableName`)
- CORRETTO: Spring Data (metodi del Repository) oppure `@Query` con parametri nominali

### 6. Gestione errori REST
- VIETATO: stacktrace nelle risposte JSON (niente `e.getMessage()` esposto direttamente)
- CORRETTO: tutte le eccezioni mappate in `GlobalExceptionHandler`

### 7. Javadoc nei Service
- Tutti i metodi `public` in classi annotate `@Service` devono avere Javadoc
- Minimo: descrizione + `@param` (se ha parametri) + `@return` (se non void) + `@throws` (se lancia)
- Metodi privati: Javadoc non richiesto

### 8. Convenzioni di naming
- Entity: `[Nome].java` (es. `Category.java`)
- Repository: `[Nome]Repository.java`
- Service: `[Nome]Service.java`
- Controller: `[Nome]Controller.java`
- DTO input: `[Nome]Request.java`
- DTO output: `[Nome]Response.java`
- Test: `[Nome]ServiceTest.java`

## Come eseguire la verifica

1. Leggere il file o i file del modulo indicato
2. Per ogni voce della checklist, indicare: OK / VIOLAZIONE / N/A
3. Per ogni VIOLAZIONE: indicare riga, regola violata, e correzione suggerita
4. Produrre un riepilogo finale con esito complessivo

## Formato output atteso

```
=== Validazione convenzioni: [NomeFile/Modulo] ===

[1] Iniezione dipendenze        → OK
[2] Valori di ritorno           → OK
[3] Logger SLF4J                → VIOLAZIONE (riga 12: usa System.out.println)
[4] Eccezioni custom            → OK
[5] Query sicure                → N/A (nessuna query custom)
[6] Gestione errori REST        → OK
[7] Javadoc nei Service         → VIOLAZIONE (metodo update() senza @param)
[8] Naming conventions          → OK

--- Violazioni trovate: 2 ---

VIOLAZIONE [3] - Riga 12:
  Attuale:   System.out.println("Elaborazione...");
  Corretto:  log.info("Elaborazione...");

VIOLAZIONE [7] - Metodo update():
  Manca @param id e @param request nel Javadoc

--- Esito: NON CONFORME (2 violazioni) ---
```

Se non ci sono violazioni:
```
--- Esito: CONFORME ✓ (nessuna violazione trovata) ---
```
