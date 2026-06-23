# ShopFlow — E-commerce Backend API

Progetto didattico del **Corso AI Experis** — sviluppato con Java 17, Spring Boot 3.2 e PostgreSQL.

ShopFlow è un backend REST per la gestione di un e-commerce: clienti, prodotti, categorie e ordini.  
Il progetto è progettato per essere usato insieme agli strumenti AI (**Claude Code** e **GitHub Copilot**) durante tutte le sessioni del corso.

---

## Stack tecnologico

| Componente | Tecnologia |
|---|---|
| Linguaggio | Java 17 |
| Framework | Spring Boot 3.2 |
| Database | PostgreSQL 15 |
| Build tool | Maven 3.9 |
| Test unitari | JUnit 5 + Mockito |
| Test integrazione | Testcontainers |
| Migrazione DB | Flyway |
| Documentazione API | SpringDoc OpenAPI (Swagger) |
| Containerizzazione | Docker + Docker Compose |
| CI/CD | GitHub Actions |

---

## Struttura del progetto

```
shopflow/
├── .claude/                          # Configurazione Claude Code
│   ├── skills/
│   │   ├── generate-javadoc.md       # Skill: genera Javadoc
│   │   └── create-unit-test.md       # Skill: genera test JUnit 5
│   └── agents/
│       └── new-feature-agent.md      # Agent: crea feature completa
├── .github/
│   ├── copilot-instructions.md       # Istruzioni per GitHub Copilot Chat
│   └── workflows/
│       └── ci.yml                    # Pipeline CI GitHub Actions
├── docker/
│   ├── Dockerfile                    # Build immagine Docker
│   └── docker-compose.yml            # Avvio locale con DB
├── src/
│   ├── main/java/com/shopflow/
│   │   ├── customer/                 # Modulo clienti (riferimento completo)
│   │   ├── product/                  # Modulo prodotti (Javadoc mancante)
│   │   ├── order/                    # Modulo ordini (logica parziale)
│   │   ├── category/                 # Modulo categorie (da completare)
│   │   ├── legacy/                   # Codice legacy (da refactorare)
│   │   └── shared/                   # Eccezioni e configurazioni comuni
│   ├── main/resources/
│   │   ├── application.yml
│   │   └── db/migration/             # Script Flyway
│   └── test/
│       └── java/com/shopflow/
│           ├── customer/             # Test parziali (3/8 scritti)
│           └── product/              # Test da generare con l'AI
├── CLAUDE.md                         # Contesto progetto per Claude Code
└── pom.xml
```

---

## Setup e avvio

### Prerequisiti

- Java 17+
- Maven 3.9+
- Docker e Docker Compose

### Avvio rapido con Docker

```bash
# Clona il repository
git clone https://github.com/[org]/shopflow.git
cd shopflow

# Avvia PostgreSQL e l'applicazione
cd docker
docker-compose up -d

# Verifica che tutto sia in esecuzione
docker-compose ps
```

### Avvio locale (sviluppo)

```bash
# Avvia solo PostgreSQL con Docker
cd docker
docker-compose up -d postgres

# Torna alla root e avvia l'applicazione
cd ..
mvn spring-boot:run
```

### Verifica

- API disponibile su: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

---

## API REST disponibili

### Clienti `/api/customers`

| Metodo | Endpoint | Descrizione |
|---|---|---|
| GET | `/api/customers` | Lista tutti i clienti |
| GET | `/api/customers/{id}` | Dettaglio cliente |
| POST | `/api/customers` | Crea nuovo cliente |
| PUT | `/api/customers/{id}` | Aggiorna cliente |
| DELETE | `/api/customers/{id}` | Elimina cliente |

### Prodotti `/api/products`

| Metodo | Endpoint | Descrizione |
|---|---|---|
| GET | `/api/products` | Lista tutti i prodotti |
| GET | `/api/products/{id}` | Dettaglio prodotto |
| GET | `/api/products/category/{id}` | Prodotti per categoria |
| POST | `/api/products` | Crea nuovo prodotto |
| PUT | `/api/products/{id}` | Aggiorna prodotto |
| DELETE | `/api/products/{id}` | Elimina prodotto |

### Ordini `/api/orders`

| Metodo | Endpoint | Descrizione |
|---|---|---|
| GET | `/api/orders` | Lista tutti gli ordini |
| GET | `/api/orders/{id}` | Dettaglio ordine |
| GET | `/api/orders/customer/{id}` | Ordini per cliente |
| POST | `/api/orders` | Crea nuovo ordine |
| PATCH | `/api/orders/{id}/status` | Aggiorna stato ordine |

---

## Esercizi per modulo

### Modulo Coding

| Livello | Esercizio | File |
|---|---|---|
| Junior | Generare CategoryService e CategoryController con l'AI | `category/` |
| Mid | Completare i TODO in OrderService (stock + totale) | `order/OrderService.java` |
| Senior | Analizzare e refactorare LegacyOrderProcessor con Claude Code | `legacy/LegacyOrderProcessor.java` |

### Modulo Testing & Quality

| Livello | Esercizio | File |
|---|---|---|
| Junior | Completare i 5 test mancanti in CustomerServiceTest | `test/.../CustomerServiceTest.java` |
| Mid | Generare tutti i test di ProductServiceTest con Claude Code | `test/.../ProductServiceTest.java` |
| Senior | Integrare SonarQube e portare il quality gate a verde | `pom.xml` + configurazione |

### Modulo Documentazione

| Livello | Esercizio | File |
|---|---|---|
| Junior | Aggiungere Javadoc a ProductService con la skill @generate-javadoc | `product/ProductService.java` |
| Mid | Documentare l'intero package `order/` con Claude Code | `order/*.java` |
| Senior | Generare documentazione architetturale del progetto | Nuovo file `ARCHITECTURE.md` |

### Modulo Code Review & PR

| Livello | Esercizio | File |
|---|---|---|
| Tutti | Aprire una PR con la feature Category e fare review con Copilot | Branch `feature/category` |

### Modulo CI/CD & DevOps

| Livello | Esercizio | File |
|---|---|---|
| Mid | Completare la pipeline CI aggiungendo analisi SonarQube | `.github/workflows/ci.yml` |
| Senior | Aggiungere job di build e push Docker Hub nella pipeline | `.github/workflows/ci.yml` |

---

## Strumenti AI configurati

### Claude Code

Il file `CLAUDE.md` nella root contiene il contesto completo del progetto.  
Claude Code lo legge automaticamente ad ogni sessione.

**Skills disponibili:**

```
@generate-javadoc   → genera Javadoc per classi e metodi
@create-unit-test   → genera test JUnit 5 + Mockito
```

**Agent disponibile:**

```
@new-feature-agent  → crea una feature completa in 8 step automatici
```

### GitHub Copilot Chat

Il file `.github/copilot-instructions.md` definisce le regole di comportamento per Copilot.

**Slash commands utili:**

```
/explain   → spiega il codice selezionato
/fix       → corregge bug o problemi
/tests     → genera test per il codice selezionato
/doc       → genera Javadoc per il codice selezionato
```

---

## Esecuzione dei test

```bash
# Test unitari
mvn test

# Test unitari + integrazione
mvn verify

# Test con report coverage
mvn verify -P coverage
```

---

## Branch strategy

```
main          → codice stabile, pronto per produzione
develop       → integrazione feature in corso
feature/[x]   → nuove funzionalità
ai/[x]-draft  → bozze generate dall'AI da revisionare
fix/[x]       → correzione bug
```

---

## Credenziali di default (sviluppo)

| Servizio | Valore |
|---|---|
| DB Host | `localhost:5432` |
| DB Name | `shopflow` |
| DB User | `shopflow` |
| DB Password | `shopflow` |

> **Attenzione:** queste credenziali sono solo per l'ambiente di sviluppo locale.

---

## Licenza

Progetto didattico — uso interno corso AI Experis.
