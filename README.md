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
