# Guia de Contribuição

Obrigado por seu interesse em contribuir com este projeto!  
Contribuições são essenciais para o crescimento e qualidade do software livre.  
Este documento explica como você pode participar de forma eficiente e organizada.

---

## 🧭 Sumário
1. [Como começar](#como-começar)
2. [Requisitos do ambiente](#requisitos-do-ambiente)
3. [Padrões de código](#padrões-de-código)
4. [Fluxo de contribuição](#fluxo-de-contribuição)
5. [Commits e mensagens](#commits-e-mensagens)
6. [Testes](#testes)
7. [Relatando problemas (issues)](#relatando-problemas-issues)
8. [Abrindo Pull Requests](#abrindo-pull-requests)
9. [Código de Conduta](#código-de-conduta)

---

## 🚀 Como começar

1. Faça um **fork** deste repositório.
2. **Clone** seu fork para sua máquina local:
```bash
   git clone git@github.com:JohnneSouza/pirullometro.git
```
4. Crie uma **branch** para sua contribuição:
   git checkout -b feature/nome-da-sua-feature
5. Instale as dependências e rode o projeto localmente.

---

## 🧰 Requisitos do ambiente

- **JDK 21+**
- **Maven** ou **Gradle**
- **Docker**
- **Git**
- **IDE recomendada:** IntelliJ IDEA

Para rodar a aplicação localmente:
./mvnw spring-boot:run
# ou
./gradlew bootRun

---

## 🧹 Padrões de código

Siga estas boas práticas:

- Use **nomes claros e descritivos** para classes, métodos e variáveis.
- Mantenha os **métodos curtos** e com **única responsabilidade**.
- Evite duplicação de código (princípio **DRY**).
- Utilize o estilo de código definido pelo projeto (.editorconfig, checkstyle.xml, etc).
- Siga os princípios **SOLID** e **Clean Code** (Robert C. Martin).
- Toda nova funcionalidade deve conter **testes automatizados**.

---

## 🔄 Fluxo de contribuição

1. **Abra uma issue** antes de começar uma mudança significativa.  
   Assim discutimos a necessidade e a abordagem ideal.
2. **Implemente sua alteração** em uma branch separada.
3. **Adicione ou atualize testes** relacionados.
4. **Atualize a documentação** (README, docs/, etc).
5. **Rode os testes localmente** e garanta que todos passam.
6. **Envie um Pull Request (PR)** para a branch principal (main ou develop).

---

## ✍️ Commits e mensagens

Use mensagens curtas e padronizadas.  
Recomendado seguir o formato **Conventional Commits**:

<tipo>(<escopo>): <mensagem>

**Tipos comuns:**
- feat: nova funcionalidade  
- fix: correção de bug  
- docs: atualização de documentação  
- test: adição ou ajuste de testes  
- refactor: melhoria interna de código sem alterar comportamento  
- chore: mudanças de configuração ou dependências

**Exemplo:**
feat(user): adicionar endpoint de cadastro de usuário

---

## 🧪 Testes

Antes de enviar um PR:
./mvnw clean test

- Todo novo código deve ser acompanhado de **testes unitários e/ou de integração**.
- Verifique cobertura mínima de testes (geralmente ≥ 80%).
- Testes devem ser legíveis e autoexplicativos.

---

## 🐞 Relatando problemas (issues)

Para reportar um bug ou sugerir uma melhoria:

1. Verifique se já existe uma issue semelhante.
2. Caso não exista, abra uma nova com:
   - Passos para reproduzir o problema.
   - Comportamento esperado vs. observado.
   - Logs e prints (se possível).
   - Versão do projeto usada.

Use rótulos como bug, enhancement, question, help wanted, etc.

---

## 🔀 Abrindo Pull Requests

1. **Atualize sua branch** com as últimas mudanças da main:
   git fetch origin
   git rebase origin/main
2. **Garanta que os testes passam.**
3. **Abra o PR** e descreva:
   - O propósito da mudança.
   - O problema resolvido.
   - Quais arquivos foram modificados.
   - Como testar localmente.

Adicione no título o formato [tipo]: descrição.

**Exemplo:**
fix: corrigir erro de serialização no endpoint /users

Após a revisão, mantenedores poderão sugerir alterações antes de aceitar o PR.

---

## 🤝 Código de Conduta

Ao participar deste projeto, você concorda em seguir o Código de Conduta (./CODE_OF_CONDUCT.md).

Respeite a comunidade e ajude a manter um ambiente acolhedor para todos.


**Obrigado por contribuir! 💪**  
Seu envolvimento ajuda a fortalecer o ecossistema de software livre.
