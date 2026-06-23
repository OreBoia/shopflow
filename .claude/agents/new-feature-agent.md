# Agent: new-feature-agent

## Obiettivo
Creare una nuova feature completa seguendo l'architettura di ShopFlow.
Da usare quando si vuole aggiungere un nuovo modulo (entità + CRUD + test).

## Come richiamare questo agent
"Esegui @new-feature-agent per l'entità [NomeEntità]"

## Step da eseguire in ordine

### Step 1 — Analisi del contesto
- Leggere CLAUDE.md per stack e convenzioni
- Identificare le relazioni con entità esistenti (Customer, Product, Order, Category)
- Verificare se esiste già qualcosa di simile nel progetto

### Step 2 — Entità JPA
Creare `[Entity].java` in `src/main/java/com/shopflow/[entity]/`:
- Annotazioni @Entity, @Table(name = "[entities]")
- @Id con @GeneratedValue(strategy = GenerationType.IDENTITY)
- @PrePersist per createdAt se presente
- Getters e Setters per tutti i campi
- Relazioni JPA coerenti con il modello esistente

### Step 3 — Repository
Creare `[Entity]Repository.java`:
- Estendere JpaRepository<[Entity], Long>
- Aggiungere metodi di ricerca custom con Javadoc

### Step 4 — DTO
Creare `[Entity]Request.java`:
- Validazioni Jakarta Bean Validation (@NotBlank, @NotNull, @Email, ecc.)

Creare `[Entity]Response.java`:
- Metodo statico `from([Entity] entity)`
- Solo i campi necessari alla risposta

### Step 5 — Service
Creare `[Entity]Service.java`:
- @Service e @Transactional
- Logger SLF4J
- Iniezione dipendenze via costruttore
- Metodi: findAll(), findById(Long id), save(Request), update(Long id, Request), delete(Long id)
- Javadoc su tutti i metodi pubblici
- Usare ResourceNotFoundException per entità non trovate

### Step 6 — Controller
Creare `[Entity]Controller.java`:
- @RestController e @RequestMapping("/api/[entities]")
- GET /api/[entities]
- GET /api/[entities]/{id}
- POST /api/[entities]
- PUT /api/[entities]/{id}
- DELETE /api/[entities]/{id}

### Step 7 — Test unitari
Creare `[Entity]ServiceTest.java` in `src/test/java/com/shopflow/[entity]/`:
- Usare la skill @create-unit-test
- Coprire tutti i metodi pubblici del Service
- Almeno 1 test positivo + 1 negativo per metodo

### Step 8 — Riepilogo finale
Elencare:
- File creati con path completo
- Relazioni JPA aggiunte ad altre entità
- Dipendenze nel pom.xml da aggiungere (se necessario)
- Endpoint REST disponibili
- Test generati
