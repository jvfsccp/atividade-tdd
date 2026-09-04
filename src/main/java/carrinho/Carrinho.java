package carrinho;

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

    public double calcularTotal() {
        double subtotal = itens.stream()
            .mapToDouble(ItemCarrinho::calcularSubtotal)
            .sum();
        double percentualTotalDesconto = cuponsAplicados.stream()
            .mapToDouble(Cupom::getPercentualDesconto)
            .sum();
        return subtotal * (1 - percentualTotalDesconto / 100);
    }
}
