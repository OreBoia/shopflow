---
description: "Use when working on ShopFlow backend tasks in Java/Spring Boot. Includes project overview, architecture structure, tech stack, and naming conventions for modules, classes, DTOs, and tests."
name: "ShopFlow Project Context"
---
# ShopFlow Project Context

## Descrizione generale
- ShopFlow e un backend REST didattico per e-commerce del corso AI Experis.
- Dominio principale: clienti, prodotti, categorie, ordini e inventario.
- Obiettivo: mantenere codice leggibile, coerente e allineato ai moduli esistenti.

## Stack tecnologico
- Java 17
- Spring Boot 3.2
- PostgreSQL 15
- Maven 3.9
- JUnit 5 + Mockito
- Testcontainers (integrazione)
- Flyway (migrazioni DB)

## Struttura generale del progetto
- `src/main/java/com/shopflow/customer`: modulo completo clienti (riferimento architetturale).
- `src/main/java/com/shopflow/product`: modulo completo prodotti.
- `src/main/java/com/shopflow/order`: modulo ordini (attenzione ai TODO didattici).
- `src/main/java/com/shopflow/category`: modulo categorie (entity/repository pronti; service/controller come esercizio).
- `src/main/java/com/shopflow/storage`: modulo inventario completo.
- `src/main/java/com/shopflow/shared`: eccezioni/configurazioni condivise.
- `src/main/java/com/shopflow/legacy`: codice legacy; non modificarlo senza richiesta esplicita.

## Naming convention
- Entita JPA: `[Entity].java`
- Repository: `[Entity]Repository.java`
- Service: `[Entity]Service.java`
- Controller: `[Entity]Controller.java`
- DTO input: `[Entity]Request.java`
- DTO output: `[Entity]Response.java`
- Test: `[Entity]ServiceTest.java`

## Regole operative di coerenza
- Usa package base `com.shopflow`.
- Mantieni iniezione dipendenze via costruttore (no field injection).
- Per entita non trovate, usa eccezioni di dominio condivise (`ResourceNotFoundException` / `BaseAppException`).
- Nei Service, evita ritorni `null`: preferisci `Optional<T>` dove appropriato.
- Quando aggiungi feature, replica stile e pattern del modulo `customer` come riferimento principale.
