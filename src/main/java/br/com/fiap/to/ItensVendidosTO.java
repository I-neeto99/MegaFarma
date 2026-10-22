package br.com.fiap.to;

import jakarta.validation.constraints.*;

public class ItensVendidosTO {
    // atributos
    @NotNull @Positive private Long codVenda;
    @NotNull @Positive private Long codRemedio;
    @NotNull @Positive private Integer quantidade;
    // construtores
    public ItensVendidosTO() {
    }
    public ItensVendidosTO(Long codVenda, Long codRemedio, Integer quantidade) {
        this.codVenda = codVenda;
        this.codRemedio = codRemedio;
        this.quantidade = quantidade;
    }
    // métodos getters/setters
    public Long getCodVenda() {
        return codVenda;
    }
    public void setCodVenda(Long codVenda) {
        this.codVenda = codVenda;
    }
    public Long getCodRemedio() {
        return codRemedio;
    }
    public void setCodRemedio(Long codRemedio) {
        this.codRemedio = codRemedio;
    }
    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}