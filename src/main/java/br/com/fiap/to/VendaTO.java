package br.com.fiap.to;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class VendaTO {
    // atributos
    private Long codigo;
    @PastOrPresent private LocalDate dataDaVenda;
    @NotNull @Positive private Long codCliente;
    // construtores
    public VendaTO() {
    }
    public VendaTO(Long codigo, LocalDate dataDaVenda, Long codCliente) {
        this.codigo = codigo;
        this.dataDaVenda = dataDaVenda;
        this.codCliente = codCliente;
    }
    // métodos getters/setters
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public LocalDate getDataDaVenda() {
        return dataDaVenda;
    }

    public void setDataDaVenda(LocalDate dataDaVenda) {
        this.dataDaVenda = dataDaVenda;
    }

    public Long getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(Long codCliente) {
        this.codCliente = codCliente;
    }
}
