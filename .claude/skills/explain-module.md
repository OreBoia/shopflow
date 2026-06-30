# Skill: explain-module

## Obiettivo

Leggere un modulo di ShopFlow e produrre una spiegazione pedagogica completa:
architettura interna, relazioni JPA, endpoint REST, flusso di una richiesta
dall'ingresso al database. Pensata per studenti che vogliono capire il codice
prima di implementare qualcosa di simile.

## Come richiamare

"Esegui @explain-module su [modulo]"
Esempi: "spiega il modulo order", "esegui @explain-module su payment", "spiega storage"

## Moduli disponibili nel progetto

- `customer` — gestione clienti
- `product` — catalogo prodotti
- `order` — ordini con righe e stati
- `category` — categorie prodotto (parzialmente implementato)
- `storage` — inventario e giacenze
- `invoice` — fatture
- `payment` — pagamenti e rimborsi (context in `.claude/custom/payment-context.md`)
- `legacy` — codice legacy con `LegacyOrderProcessor`

## Step da eseguire

1. Leggere tutti i file del modulo richiesto (Entity, Repository, Service, Controller, Request, Response)
2. Se esiste un file di contesto in `.claude/custom/`, leggerlo prima
3. Produrre la spiegazione nel formato seguente

## Formato output atteso

### Sezione 1 — Panoramica
Descrizione in 3-5 righe: cosa gestisce il modulo, perché esiste, qual è il suo
ruolo nell'e-commerce ShopFlow.

### Sezione 2 — Entità JPA
- Tabella nel database, campi principali con tipo e vincoli
- Relazioni con altre entità (es. `@ManyToOne` verso `Customer`, `@OneToMany` verso `OrderItem`)
- Campi particolari: enum, `@PrePersist`, indici

### Sezione 3 — Repository
- Metodi custom oltre quelli ereditati da `JpaRepository`
- Spiegare cosa fa ogni metodo custom e quando viene usato

### Sezione 4 — DTO (Request / Response)
- Campi del Request e relative validazioni Jakarta Bean Validation
- Campi del Response e perché differiscono dall'Entity
- Il metodo statico `from(Entity)` nel Response: scopo del pattern

### Sezione 5 — Service (logica di business)
Per ogni metodo pubblico:
- Cosa fa in parole semplici
- Casi di errore gestiti (quali eccezioni può lanciare e quando)
- Interazione con il Repository

### Sezione 6 — Controller (endpoint REST)
Tabella degli endpoint:

| Metodo HTTP | Path | Cosa fa | Risposta |
|-------------|------|---------|---------|
| GET | /api/... | ... | 200 + lista |
| ...

### Sezione 7 — Flusso di una richiesta (esempio end-to-end)
Tracciare il percorso di una richiesta rappresentativa (es. "POST /api/orders")
passo per passo: Controller → Service → Repository → DB → risposta.
Includere cosa succede in caso di errore (entità non trovata, validazione fallita).

### Sezione 8 — Collegamento con altri moduli
- Quali moduli dipendono da questo (lo usano)
- Da quali moduli questo dipende
- Perché queste dipendenze esistono

### Sezione 9 — Cosa imparare da questo modulo
2-3 pattern o tecniche che questo modulo esemplifica bene e che lo studente
dovrebbe riconoscere e riusare quando implementa un nuovo modulo.

## Tono e stile

- Italiano, diretto, senza gergo inutile
- Spiegare i "perché", non solo i "cosa" (es. perché si usa `Optional`, perché
  la logica sta nel Service e non nel Controller)
- Fare riferimento al codice reale con nomi di classi e metodi specifici,
  non descrizioni astratte
- Lunghezza: completa ma senza ripetizioni; preferire esempi concreti a liste vuote
