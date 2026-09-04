package carrinho;

import carrinho.exception.CarrinhoVazioException;
import carrinho.exception.CupomJaAplicadoException;
import carrinho.exception.EstoqueInsuficienteException;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private final List<ItemCarrinho> itens = new ArrayList<>();
    private final List<Cupom> cuponsAplicados = new ArrayList<>();

    public void adicionarItem(Produto produto, int quantidade) throws EstoqueInsuficienteException {
        if (quantidade > produto.getEstoque()) {
            throw new EstoqueInsuficienteException(
                "Estoque insuficiente para o produto " + produto.getNome() + ".");
        }
        itens.add(new ItemCarrinho(produto, quantidade));
    }

    public void removerItem(Produto produto) {
        itens.removeIf(item -> item.getProduto() == produto);
    }

    public void aplicarCupom(Cupom cupom) throws CupomJaAplicadoException {
        if (cuponsAplicados.contains(cupom)) {
            throw new CupomJaAplicadoException(
                "O cupom " + cupom.getCodigo() + " ja foi aplicado a este carrinho.");
        }
        cuponsAplicados.add(cupom);
    }

    public void finalizarCompra() throws CarrinhoVazioException {
        if (itens.isEmpty()) {
            throw new CarrinhoVazioException("Nao e possivel finalizar a compra de um carrinho vazio.");
        }
    }

    public double calcularTotal() {
        return calcularSubtotalItens() * (1 - calcularPercentualDesconto() / 100);
    }

    private double calcularSubtotalItens() {
        return itens.stream()
            .mapToDouble(ItemCarrinho::calcularSubtotal)
            .sum();
    }

    private double calcularPercentualDesconto() {
        return cuponsAplicados.stream()
            .mapToDouble(Cupom::getPercentualDesconto)
            .sum();
    }
}
