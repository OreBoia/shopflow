# Skill: refactor-legacy

## Obiettivo

Analizzare codice legacy Java con code smell e produrre una versione refactored
che rispetta le convenzioni ShopFlow, senza alterare il comportamento osservabile.
Usare principalmente su `LegacyOrderProcessor.java` e su qualsiasi codice segnalato
come legacy nel progetto.

## Come richiamare

"Esegui @refactor-legacy su [file]"
Esempi: "refactora LegacyOrderProcessor", "esegui @refactor-legacy su legacy/"

## Code smell da individuare e correggere

### Struttura e leggibilità
- **Magic numbers**: costanti numeriche inline senza nome (es. `* 0.20`, `> 1000`)
  → estrarre come `private static final` con nome descrittivo
- **Metodi lunghi**: metodi con più di 20-25 righe che fanno troppe cose
  → estrarre metodi privati con nomi che esprimono l'intenzione
- **Nomi non descrittivi**: variabili `d`, `x`, `tmp`, `res`, metodi `calc()`, `proc()`
  → rinominare con nomi che descrivono il contenuto o l'azione
- **Commenti che spiegano il "cosa"**: un commento `// moltiplica per 0.9` su `price * 0.9`
  → il codice deve essere auto-documentante; rimuovere o trasformare in Javadoc

### Gestione null e Optional
- **Ritorno di null**: `return null;` nei metodi pubblici
  → sostituire con `Optional.empty()` o lanciare eccezione appropriata
- **Null check espliciti**: `if (x == null)` nei metodi pubblici
  → usare `Optional` o validare all'ingresso con eccezione descrittiva

### Logging
- **System.out / System.err**: `System.out.println(...)`, `System.err.println(...)`
  → sostituire con `log.info(...)` / `log.error(...)` via SLF4J
- **printStackTrace**: `e.printStackTrace()`
  → sostituire con `log.error("Messaggio", e)`

### Eccezioni
- **Catch generico silente**: `catch (Exception e) { }` o `catch (Exception e) { return null; }`
  → loggare e rilancire, oppure convertire in eccezione custom che estende `BaseAppException`
- **throw new RuntimeException(...)** nei Service
  → usare eccezioni custom del dominio

### Duplicazione
- **Logica ripetuta**: blocchi identici o quasi identici in più punti
  → estrarre in metodo privato riutilizzabile

### Stile Java moderno (Java 17)
- **Switch con if-else a cascata** per discriminanti semplici
  → usare switch expression (`return switch (x) { case A -> ...; }`)
- **Cicli for classici** per trasformazioni su collezioni
  → usare Stream API (`list.stream().map(...).toList()`)

## Step da eseguire

1. **Leggere** il file o i file da refactorare
2. **Elencare** tutti i code smell trovati, con numero di riga e categoria
3. **Proporre** la versione refactored completa del file
4. **Verificare** che il comportamento sia invariato: i metodi pubblici devono
   produrre lo stesso output con gli stessi input
5. **Controllare** che la versione refactored superi @validate-conventions

## Vincoli importanti

- NON modificare la firma dei metodi pubblici (nome, parametri, tipo di ritorno)
  senza esplicita approvazione — potrebbe rompere chiamanti esistenti
- NON introdurre nuove dipendenze esterne
- NON modificare file in `legacy/` senza approvazione esplicita dell'utente:
  presentare prima l'analisi e la proposta, applicare solo dopo conferma
- La logica di business deve rimanere identica: stessi calcoli, stesse regole

## Formato output atteso

```
=== Analisi code smell: [NomeFile] ===

SMELL [1] Magic number - Riga 15: 0.20 → estrarre come VIP_DISCOUNT
SMELL [2] System.out   - Riga 34: System.out.println → log.info
SMELL [3] Null return  - Riga 58: return null → Optional.empty()
SMELL [4] Catch vuoto  - Riga 72: catch (Exception e) {} → loggare e rilanciare

--- Totale smell trovati: 4 ---

=== Versione refactored proposta ===

[codice completo del file refactored]

=== Riepilogo modifiche ===
- Estratte N costanti named
- Sostituiti N System.out con SLF4J
- Eliminati N ritorni null
- Aggiunti/corretti N Javadoc
- Comportamento invariato: sì
```
