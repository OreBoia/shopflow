# Skill: create-unit-test

## Obiettivo

Generare test unitari JUnit 5 + Mockito per i Service di ShopFlow.

## Regole

- Usare @ExtendWith(MockitoExtension.class)
- Usare @Mock per i repository e @InjectMocks per il service
- Usare assertThat() di AssertJ (non assertEquals di JUnit)
- Ogni metodo pubblico deve avere almeno: 1 test positivo + 1 test negativo
- Usare @DisplayName con descrizione leggibile in italiano
- Usare @BeforeEach per inizializzare oggetti di test riutilizzabili
- Verificare le interazioni con verify() di Mockito dove significativo

## Pattern da seguire

@Test
@DisplayName("[metodo()] dovrebbe [comportamento atteso] quando [condizione]")
void nomeMetodo_shouldFareCosa_whenCondizione() {
    // Given
    when(repository.metodo()).thenReturn(valore);

    // When
    TipoRitorno result = service.metodo();

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getCampo()).isEqualTo(valoreAtteso);
    verify(repository, times(1)).metodo();
}

## Esempio per eccezioni

@Test
@DisplayName("findById() dovrebbe lanciare eccezione quando non trovato")
void findById_shouldThrowException_whenNotFound() {
    when(repository.findById(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.findById(99L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("99");
}

## Struttura del file di test

1. @ExtendWith e @DisplayName sulla classe
2. @Mock per ogni dipendenza
3. @InjectMocks per il service
4. @BeforeEach con setUp() per dati di test
5. Test raggruppati per metodo con commento separatore
