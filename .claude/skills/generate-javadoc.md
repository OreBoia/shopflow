# Skill: generate-javadoc

## Obiettivo

Generare documentazione Javadoc completa per classi e metodi Java.

## Regole

- Ogni classe deve avere una descrizione generale di 2-3 righe
- Ogni metodo pubblico deve avere: descrizione, @param, @return, @throws (se lancia eccezioni)
- I metodi privati NON vanno documentati
- Usare italiano per tutte le descrizioni
- Non modificare la logica del codice, aggiungere solo commenti Javadoc
- Per i DTO (Request/Response), documentare solo la classe, non i getter/setter

## Formato atteso

/**

- Descrizione della classe in 2-3 righe.
- Seconda riga se necessario.
 */

/**

- Descrizione breve del metodo.
-
- @param nomeParam descrizione del parametro
- @return descrizione del valore restituito
- @throws NomeEccezione quando viene lanciata e perché
 */

## Esempio di output corretto

/**

- Service per la gestione dei prodotti di ShopFlow.
- Fornisce operazioni CRUD e ricerca per categoria.
 */
@Service
public class ProductService {

    /**
  - Restituisce tutti i prodotti disponibili nel catalogo.
  -
  - @return lista di tutti i prodotti
     */
    public List<ProductResponse> findAll() { ... }

    /**
  - Cerca un prodotto per identificativo univoco.
  -
  - @param id l'identificativo del prodotto
  - @return il prodotto trovato
  - @throws ResourceNotFoundException se il prodotto non esiste
     */
    public ProductResponse findById(Long id) { ... }
}
