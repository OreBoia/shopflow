# ShopFlow - Struttura file per esercizi

## Esito verifica

La codebase contiene quasi tutti i file di partenza per gli esercizi del PDF.
I file di partenza mancanti sono stati aggiunti nella cartella separata `starter-mancanti/`.

## Esercizi e file collegati

| Area esercizio (PDF) | File/script collegati in codebase | Stato |
|---|---|---|
| Setup e primo contatto | `README.md`, `docker/docker-compose.yml`, `pom.xml` | Presente |
| Creare `CLAUDE.md` del progetto | `CLAUDE.md` | Presente |
| Configurare `copilot-instructions.md` | `.github/copilot-instructions.md` | Presente |
| CRUD completo su nuovo modulo (Category) | `src/main/java/com/shopflow/category/Category.java`, `src/main/java/com/shopflow/category/CategoryRepository.java` | Parziale |
| Completare logica ordini (stock + totale) | `src/main/java/com/shopflow/order/OrderService.java` | Presente (con TODO) |
| Documentazione ProductService | `src/main/java/com/shopflow/product/ProductService.java` | Presente (con TODO) |
| Test CustomerService da completare | `src/test/java/com/shopflow/customer/CustomerServiceTest.java` | Presente (con TODO) |
| Test ProductService da generare | `src/test/java/com/shopflow/product/ProductServiceTest.java` | Presente (stub) |
| Analisi/refactor modulo legacy | `src/main/java/com/shopflow/legacy/LegacyOrderProcessor.java` | Presente |
| Skill Claude Code | `.claude/skills/generate-javadoc.md`, `.claude/skills/create-unit-test.md` | Presente |
| Agent Claude Code | `.claude/agents/new-feature-agent.md` | Presente |
| CI/CD con Sonar e quality gate | `.github/workflows/ci.yml` | Presente (con TODO) |
| File contesto task-specifico modulo | `starter-mancanti/context/modulo-context-template.md` | Aggiunto |
| Documentazione architetturale (`ARCHITECTURE.md`) | `starter-mancanti/documentation/ARCHITECTURE.md` | Aggiunto |

## File mancanti aggiunti in cartella separata

```text
starter-mancanti/
├── category/
│   ├── CategoryService.java
│   └── CategoryController.java
├── context/
│   └── modulo-context-template.md
└── documentation/
    └── ARCHITECTURE.md
```

## Note operative

- I file in `starter-mancanti/category/` sono template volutamente incompleti, pronti per l'esercizio.
- Non e stato modificato il codice sorgente applicativo in `src/main` e `src/test`.
- I file gia presenti con TODO restano il punto di partenza ufficiale per gli esercizi guidati.
