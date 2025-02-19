## 🧪 **Referência para Testes Unitários**

### **Tipos de Testes**

1. **Unitário**
   - Teste realizado pelo desenvolvedor para validar o comportamento de unidades funcionais de código (por exemplo, métodos de uma classe). Não pode acessar componentes ou recursos externos como arquivos, banco de dados, redes, etc.

2. **Integração**
   - Teste que verifica a comunicação entre diferentes componentes/módulos da aplicação, além de interagir com recursos externos para garantir a integração correta.

3. **Funcional**
   - Teste do ponto de vista do usuário para validar se uma funcionalidade está funcionando conforme esperado, ou seja, produzindo o comportamento ou resultado desejado.

---

### **Boas Práticas e Padrões**

- **Nomenclatura de Testes**
   - Utilize o padrão: `<AÇÃO> should <EFEITO> [when <CENÁRIO>]`
   - Exemplo: `public void findById_Should_throwResourceNotFoundException_When_idNotExists()`

- **Padrão AAA (Arrange, Act, Assert)**
   - **Arrange**: Instanciar objetos necessários.
   - **Act**: Executar as ações necessárias.
   - **Assert**: Declarar o que deveria acontecer (resultado esperado).

- **Independência/Isolamento**
   - Testes não devem depender de outros testes ou da ordem de execução. Cada teste deve ser autônomo.

---

### **Anotações Usadas nas Classes de Teste**

| **Annotation**                        | **Descrição**                                                                                      |
|---------------------------------------|----------------------------------------------------------------------------------------------------|
| `@SpringBootTest`                     | Carrega o contexto da aplicação (teste de integração).                                             |
| `@SpringBootTest @AutoConfigureMockMvc`| Carrega o contexto da aplicação e trata as requisições sem subir o servidor (teste web de integração). |
| `@WebMvcTest(Classe.class)`           | Carrega o contexto da camada web (teste de unidade de controlador).                               |
| `@ExtendWith(SpringExtension.class)`  | Não carrega o contexto, mas permite usar os recursos do Spring com JUnit (teste de unidade de service/component). |
| `@DataJpaTest`                        | Carrega apenas os componentes relacionados ao Spring Data JPA, com rollback após cada teste (teste de unidade de repository). |

---

### **JUnit Annotations**

| **JUnit 5 Annotation** | **JUnit 4 Annotation** | **Objetivo**                                                        |
|------------------------|------------------------|---------------------------------------------------------------------|
| `@BeforeAll`           | `@BeforeClass`         | Preparação antes de todos os testes da classe (método estático).    |
| `@AfterAll`            | `@AfterClass`          | Preparação depois de todos os testes da classe (método estático).   |
| `@BeforeEach`          | `@Before`              | Preparação antes de cada teste da classe.                           |
| `@AfterEach`           | `@After`               | Preparação depois de cada teste da classe.                          |

---

### **Mockito vs @MockBean**

| **Código**                                                               | **Descrição**                                                                                             |
|--------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------|
| `@Mock<br>private MyComp myComp;<br>ou<br>myComp = Mockito.mock(MyComp.class);` | Usar quando a classe de teste não carrega o contexto da aplicação. Mais rápido e enxuto.                 |
| `@ExtendWith<br>@MockBean<br>private MyComp myComp;`                       | Usar quando a classe de teste carrega o contexto da aplicação e precisa mockar algum bean do sistema.    |

---

### **Classe para Gerar um Token Dinamicamente (Para Testes de Integração)**

* Esta classe é útil para obter um token de autenticação dinamicamente durante os testes de integração, simulando uma autenticação via OAuth2.
* Permite realizar o processo de obtenção do token de acesso ao enviar uma requisição de autenticação via `POST` para o endpoint de token `/oauth2/token`.

```java
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class TokenUtil {

	@Value("${security.client-id}")
	private String clientId;

	@Value("${security.client-secret}")
	private String clientSecret;

	public String obtainAccessToken(MockMvc mockMvc, String username, String password) throws Exception {

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("client_id", clientId);
		params.add("username", username);
		params.add("password", password);

		ResultActions result = mockMvc
				.perform(post("/oauth2/token")
						.params(params)
						.with(httpBasic(clientId, clientSecret))
						.accept("application/json;charset=UTF-8"))
						.andExpect(status().isOk())
						.andExpect(content().contentType("application/json;charset=UTF-8"));

		String resultString = result.andReturn().getResponse().getContentAsString();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("access_token").toString();
	}
}
```

---

### Exemplos:

* **Usando @MockBean para Testes de Integração**
  * O Spring cria um mock do bean e o substitui no contexto de teste.
  * Útil quando você não quer que um bean real interaja com componentes externos.
  * Em outras palavras, cria uma ação simulada e não real.

```java
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void whenFindByEmail_thenReturnUser() {
        User user = new User("john@example.com", "password");
        Mockito.when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        
        User result = userService.findByEmail("john@example.com");
        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());
    }
}
```

* **MockMvc e jsonPath()**

```java
@Test
public void testGetUser() throws Exception {
    mockMvc.perform(get("/api/users/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.username").value("john"))
           .andExpect(jsonPath("$.email").value("john@example.com"));
}
```

* **Verificando Status HTTP com MockMvc**

```java
@Test
public void testPostUser() throws Exception {
    UserDTO newUser = new UserDTO("john", "john@example.com");
    
    mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newUser)))
           .andExpect(status().isCreated());
}
```

* **Testando Requisições e Respostas com @SpringBootTest**

```java
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateUser() throws Exception {
        UserDTO newUser = new UserDTO("john", "john@example.com");

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.username").value("john"));
    }
}
```

* **Usando @DataJpaTest para Testar Repositórios**

```java
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail() {
        User user = new User("john@example.com", "password");
        userRepository.save(user);
        
        Optional<User> found = userRepository.findByEmail("john@example.com");
        assertTrue(found.isPresent());
        assertEquals("john@example.com", found.get().getEmail());
    }
}
```

* **Isolando Testes com @Mock**

```java
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void whenFindById_thenReturnUser() {
        User user = new User("john@example.com", "password");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        
        User result = userService.findById(1L);
        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());
    }
}
```

---
