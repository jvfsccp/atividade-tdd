package carrinho;

import carrinho.exception.CarrinhoVazioException;
import carrinho.exception.CupomJaAplicadoException;
import carrinho.exception.EstoqueInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CarrinhoTest {

    private Carrinho carrinho;

    @BeforeEach
    void setUp() {
        carrinho = new Carrinho();
    }

    @Test
    void carrinhoVazioTemTotalZero() {
        assertEquals(0.0, carrinho.calcularTotal());
    }

    @Test
    void adicionarItemAumentaTotalPeloValorDePrecoVezesQuantidade() throws EstoqueInsuficienteException {
        Produto produto = new Produto("Caneta", 2.50, 10);

        carrinho.adicionarItem(produto, 3);

        assertEquals(7.50, carrinho.calcularTotal(), 0.0001);
    }

    @Test
    void adicionarItemComQuantidadeMaiorQueEstoqueLancaExcecao() {
        Produto produto = new Produto("Caneta", 2.50, 5);

        assertThrows(EstoqueInsuficienteException.class,
            () -> carrinho.adicionarItem(produto, 10));
    }

    @Test
    void removerItemReduzTotalCorretamente() throws EstoqueInsuficienteException {
        Produto produto = new Produto("Caneta", 2.50, 5);
        carrinho.adicionarItem(produto, 2);

        carrinho.removerItem(produto);

        assertEquals(0.0, carrinho.calcularTotal(), 0.0001);
    }

    @Test
    void aplicarCupomValidoReduzTotalPeloPercentual() throws EstoqueInsuficienteException, CupomJaAplicadoException {
        Produto produto = new Produto("Caneta", 10.0, 5);
        carrinho.adicionarItem(produto, 2);
        Cupom cupom = new Cupom("DESC10", 10);

        carrinho.aplicarCupom(cupom);

        assertEquals(18.0, carrinho.calcularTotal(), 0.0001);
    }

    @Test
    void aplicarMesmoCupomDuasVezesLancaExcecao() throws EstoqueInsuficienteException, CupomJaAplicadoException {
        Produto produto = new Produto("Caneta", 10.0, 5);
        carrinho.adicionarItem(produto, 2);
        Cupom cupom = new Cupom("DESC10", 10);
        carrinho.aplicarCupom(cupom);

        assertThrows(CupomJaAplicadoException.class, () -> carrinho.aplicarCupom(cupom));
    }

    @Test
    void finalizarCompraComCarrinhoVazioLancaExcecao() {
        assertThrows(CarrinhoVazioException.class, () -> carrinho.finalizarCompra());
    }
}
