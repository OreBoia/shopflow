# ShopFlow - Mappa esercizi e file

## Legenda stato

| Simbolo | Significato |
|---|---|
| ✅ Presente | File presente e pronto all'uso |
| ⚠️ Parziale | File presente ma incompleto (con TODO / stub) |
| 📁 In starter-mancanti | File aggiunto nella cartella separata `starter-mancanti/` |
| ❌ Mancante | File assente — da creare prima di erogare l'esercizio |

---

## Modulo 1 — Setup e Strumenti

| Es. | Titolo | File necessari | Stato |
|---|---|---|---|
| 1.1 | Setup e primo contatto | `README.md`, `docker/docker-compose.yml`, `pom.xml` | ✅ Presente |
| 1.2 | Creare il file `CLAUDE.md` | `CLAUDE.md` (root) | ❌ Mancante |
| 1.3 | Copilot vs Claude Code a confronto | Qualsiasi file Java (es. `order/OrderService.java`) | ✅ Presente |

---

## Modulo 2 — Generazione Codice

| Es. | Titolo | File necessari | Stato |
|---|---|---|---|
| 2.1 | Generare un CRUD completo (Category) | `category/Category.java`, `category/CategoryRepository.java` (base) + `starter-mancanti/category/CategoryService.java`, `CategoryController.java` (template da completare) | ⚠️ Parziale / 📁 In starter-mancanti |
| 2.2 | Scaffolding con Copilot | `starter-mancanti/category/CategoryService.java` (file di service vuoto con commento) | 📁 In starter-mancanti |
| 2.3 | Prompt Engineering per la generazione | Nessun file specifico (esercizio concettuale) | ✅ Presente |

---

## Modulo 3 — Comprensione e Refactoring

| Es. | Titolo | File necessari | Stato |
|---|---|---|---|
| 3.1 | Analisi di un modulo legacy | `src/main/java/com/shopflow/legacy/LegacyOrderProcessor.java` | ✅ Presente |
| 3.2 | Refactoring guidato | `src/main/java/com/shopflow/order/OrderService.java` | ⚠️ Presente (con TODO) |
| 3.3 | Caccia al technical debt | Intero package `src/main/java/com/shopflow/order/` | ✅ Presente |

---

## Modulo 4 — Qualità e Workflow

| Es. | Titolo | File necessari | Stato |
|---|---|---|---|
| 4.1 | Migliorare un prompt e confrontare output | `src/main/java/com/shopflow/order/OrderService.java` | ⚠️ Presente (con TODO) |
| 4.2 | Validazione sistematica del codice AI | `src/main/java/com/shopflow/product/ProductService.java` | ⚠️ Presente (con TODO) |
| 4.3 | Git workflow con codice AI (PaymentService) | Nessun file di partenza (da generare in branch durante l'esercizio) | ✅ Presente |

---

## Modulo 5 — Contesto e Istruzioni

| Es. | Titolo | File necessari | Stato |
|---|---|---|---|
| 5.1 | Creare il `CLAUDE.md` del progetto | `CLAUDE.md` (root) | ❌ Mancante |
| 5.2 | Configurare `copilot-instructions.md` | `.github/copilot-instructions.md` | ✅ Presente |
| 5.3 | File di contesto per task specifico | `starter-mancanti/context/modulo-context-template.md` | 📁 In starter-mancanti |

---

## Modulo 6 — Automazione e Skill

| Es. | Titolo | File necessari | Stato |
|---|---|---|---|
| 6.1 | Creare una skill Claude Code | `.claude/skills/create-unit-test.md`, `.claude/skills/generate-javadoc.md` | ✅ Presente |
| 6.2 | Configurare un agent Claude Code | `.claude/agents/new-feature-agent.md` | ✅ Presente |
| 6.3 | Slash commands e hook Git pre-commit | `src/main/java/com/shopflow/order/OrderService.java` + `.git/hooks/pre-commit` (script shell) | ❌ Mancante (hook) |

---

## Riepilogo file mancanti da creare

| File | Esercizi impattati | Azione richiesta |
|---|---|---|
| `CLAUDE.md` (root) | Es. 1.2, Es. 5.1 | Creare file con: nome progetto, stack, struttura cartelle, convenzioni di naming |
| `.git/hooks/pre-commit` | Es. 6.3 | Creare script shell che analizza i file Java staged prima del commit |

---

## File aggiuntivi presenti in `starter-mancanti/`

```text
starter-mancanti/
├── category/
│   ├── CategoryService.java     ← template vuoto per Es. 2.1 e 2.2
│   └── CategoryController.java  ← template vuoto per Es. 2.1
├── context/
│   └── modulo-context-template.md  ← template per Es. 5.3
└── documentation/
    └── ARCHITECTURE.md          ← riferimento architetturale
```

> **Nota:** I file in `starter-mancanti/category/` sono template volutamente incompleti, pronti per essere usati come punto di partenza dagli studenti. Non modificare il codice sorgente in `src/main` e `src/test` — i file con TODO sono il punto di partenza ufficiale degli esercizi guidati.
