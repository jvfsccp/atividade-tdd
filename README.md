# Carrinho de Compras — Atividade de TDD

Projeto desenvolvido para a atividade prática de laboratório da aula especial
de **Test-Driven Development (TDD)** da disciplina *ADS1253 – Programação
Orientada a Objetos com Banco de Dados* (PUC Goiás).

O objetivo foi implementar, em Java, um carrinho de compras aplicando o ciclo
**Red → Green → Refactor**, escrevendo cada teste antes do código de produção
que ele exige — nenhuma classe de domínio foi criada sem antes existir um
teste que a motivasse.

## Regras de negócio implementadas

Lista de testes (*test list*) definida no enunciado, do caso mais simples para
o mais complexo:

1. Um carrinho recém-criado tem total igual a R$ 0,00.
2. Adicionar um item ao carrinho aumenta o total pelo valor de preço × quantidade.
3. Não é possível adicionar um item em quantidade maior que o estoque
   disponível do produto (`EstoqueInsuficienteException`).
4. Remover um item do carrinho reduz o total corretamente.
5. Aplicar um cupom de desconto válido reduz o total pelo percentual do cupom.
6. Aplicar o mesmo cupom duas vezes lança `CupomJaAplicadoException`.
7. Finalizar a compra com o carrinho vazio lança `CarrinhoVazioException`.

**Desafio opcional** (item extra, proposto no próprio roteiro da atividade):

8. Um cupom que levaria o total a um valor negativo é rejeitado
   (`DescontoInvalidoException`).

## Estrutura do projeto

```
src/main/java/carrinho/
├── Carrinho.java                  # agregado principal: itens, cupons e total
├── ItemCarrinho.java              # produto + quantidade dentro do carrinho
├── Produto.java                   # nome, preço e estoque
├── Cupom.java                     # código e percentual de desconto
└── exception/
    ├── EstoqueInsuficienteException.java
    ├── CupomJaAplicadoException.java
    ├── CarrinhoVazioException.java
    └── DescontoInvalidoException.java

src/test/java/carrinho/
└── CarrinhoTest.java               # suíte com os 8 cenários acima
```

## Como rodar os testes

Pré-requisitos: JDK 17+ e Maven.

```bash
mvn test
```

Saída esperada: `Tests run: 8, Failures: 0, Errors: 0, Skipped: 0`.

## Histórico de commits

Cada commit corresponde a uma única fase do ciclo TDD, identificada no
prefixo da mensagem:

- `red: ...` — teste escrito e falhando (por assert ou por erro de compilação).
- `green: ...` — código mínimo de produção que faz esse teste passar.
- `refactor: ...` — melhoria de código/testes mantendo a suíte toda verde.

A suíte completa foi executada a cada passo, não apenas o teste novo, antes de
cada commit — garantindo que nenhum ciclo quebrou o que já estava funcionando.
Dois momentos de refactor foram reservados: um após o item 4 e outro após o
item 7, revisando duplicação e nomes nas classes de domínio e nos testes.

Veja o histórico completo com:

```bash
git log --oneline
```
