# Agent: New Feature

## Obiettivo
Creare una nuova feature completa seguendo l'architettura del progetto.

## Step da eseguire in ordine

### Step 1 — Analisi del contesto
Leggi CLAUDE.md e identifica i pattern già presenti nel progetto
per il tipo di feature richiesta.

### Step 2 — Generazione del codice
Crea nell'ordine:
1. Entità JPA con annotazioni (`@Entity`, `@Table`, `@Id`)
2. Repository (`JpaRepository`)
3. DTO: [Entity]Request e [Entity]Response
4. Service con metodi: findById, findAll, save, delete
5. Controller REST su `/api/[entity]`

### Step 3 — Test unitari
Per ogni classe generata, crea il corrispondente file di test
in `src/test/java/` usando JUnit 5 e Mockito.

### Step 4 — Documentazione
Aggiungi Javadoc a tutte le classi e i metodi pubblici
usando la skill @generate-javadoc.

### Step 5 — Riepilogo
Elenca i file creati, i test generati e
le eventuali dipendenze da aggiungere al pom.xml.