package br.com.fiap.resource;

import br.com.fiap.bo.ItensVendidosBO;
import br.com.fiap.to.ItensVendidosTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/megafarma/itensvendidos")
public class ItensVendidosResource {
    private ItensVendidosBO itensVendidosBO = new ItensVendidosBO();

    @GetMapping
    public ResponseEntity<List<ItensVendidosTO>> findAll() {
        List<ItensVendidosTO> itens = itensVendidosBO.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(itens);
    }

    @GetMapping("/venda/{codVenda}")
    public ResponseEntity<List<ItensVendidosTO>> findByVenda(@PathVariable Long codVenda) {
        List<ItensVendidosTO> itens = itensVendidosBO.findByVenda(codVenda);
        return ResponseEntity.status(HttpStatus.OK).body(itens);
    }

    @GetMapping("/{codVenda}/{codRemedio}")
    public ResponseEntity<?> findByCodigo(@PathVariable Long codVenda,
                                          @PathVariable Long codRemedio) {
        ItensVendidosTO item = itensVendidosBO.findByCodigo(codVenda, codRemedio);
        if (item != null) {
            return ResponseEntity.status(HttpStatus.OK).body(item);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item vendido não encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid ItensVendidosTO item) {
        try {
            ItensVendidosTO resultado = itensVendidosBO.save(item);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar o item vendido");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar o item vendido");
        }
    }

    @PutMapping("/{codVenda}/{codRemedio}")
    public ResponseEntity<?> update(@PathVariable Long codVenda,
                                    @PathVariable Long codRemedio,
                                    @RequestBody @Valid ItensVendidosTO item) {
        try {
            item.setCodVenda(codVenda);
            item.setCodRemedio(codRemedio);
            ItensVendidosTO resultado = itensVendidosBO.update(item);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.OK).body(resultado);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item vendido não encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizar o item vendido");
        }
    }

    @DeleteMapping("/{codVenda}/{codRemedio}")
    public ResponseEntity<?> delete(@PathVariable Long codVenda,
                                    @PathVariable Long codRemedio) {
        if (itensVendidosBO.delete(codVenda, codRemedio)) {
            return ResponseEntity.status(HttpStatus.OK).body("Item vendido deletado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Item vendido não encontrado");
        }
    }
}
