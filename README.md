# Referência para testes unitários

---

#### Boas práticas e padrões

- Nomenclatura de um teste
  - <AÇÃO> should <EFEITO> [when <CENÁRIO>]

* Padrão AAA

  - Arrange: instancie os objetos necessários
  - Act: execute as ações necessárias
  - Assert: declare o que deveria acontecer (resultado esperado)

* Independência / isolamento

  - Um teste não pode depender de outros testes, nem da ordem de execução

# Referência para testes

---

- Annotations usadas nas classes de teste

| Annotation                            | Descrição                                                                                                                                        |
| ------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------ |
| @SpringBootTest                       | Carrega o contexto da aplicação (teste de integração)                                                                                            |
| @SpringBootTest @AutoConfigureMockMvc | Carrega o contexto da aplicação (teste de integração & web). Trata as requisições sem subir o servidor                                           |
| @WebMvcTest(Classe.class)             | Carrega o contexto, porém somente da camada web (teste de unidade: controlador)                                                                  |
| @ExtendWith(SpringExtension.class)    | Não carrega o contexto, mas permite usar os recursos do Spring com JUnit (teste de unidade: service/component)                                   |
| @DataJpaTest                          | Carrega somente os componentes relacionados ao Spring Data JPA. Cada teste é transacional e dá rollback ao final. (teste de unidade: repository) |

- Fixtures
  - É uma forma de organizar melhor o código dos testes e evitar repetições.

## JUnit Annotations

| JUnit 5 Annotation | JUnit 4 Annotation | Objetivo                                                      |
| ------------------ | ------------------ | ------------------------------------------------------------- |
| @BeforeAll         | @BeforeClass       | Preparação antes de todos testes da classe (método estático)  |
| @AfterAll          | @AfterClass        | Preparação depois de todos testes da classe (método estático) |
| @BeforeEach        | @Before            | Preparação antes de cada teste da classe                      |
| @AfterEach         | @After             | Preparação depois de cada teste da classe                     |

- Mockito vs @MockBean
  - https://stackoverflow.com/questions/44200720/difference-between-mock-mockbean-and-mockito-mock

## Mockito vs @MockBean

| Código                                                                        | Descrição                                                                                             |
| ----------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- |
| @Mock<br>private MyComp myComp;<br>ou<br>myComp = Mockito.mock(MyComp.class); | Usar quando a classe de teste não carrega o contexto da aplicação. É mais rápido e enxuto.            |
| @ExtendWith<br>@MockBean<br>private MyComp myComp;                            | Usar quando a classe de teste carrega o contexto da aplicação e precisa mockar algum bean do sistema. |
| @WebMvcTest<br>@SpringBootTest                                                |                                                                                                       |
