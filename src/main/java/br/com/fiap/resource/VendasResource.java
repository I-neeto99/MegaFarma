package br.com.fiap.resource;

import br.com.fiap.bo.VendasBO;
import br.com.fiap.to.VendasTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/megafarma/vendas")
public class VendasResource {
    private VendasBO vendasBO = new VendasBO();

    @GetMapping
    public ResponseEntity<List<VendasTO>> findAll() {
        List<VendasTO> vendas = vendasBO.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(vendas);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findByCodigo(@PathVariable Long codigo) {
        VendasTO venda = vendasBO.findByCodigo(codigo);
        if (venda != null) {
            return ResponseEntity.status(HttpStatus.OK).body(venda);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venda não encontrada");
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid VendasTO venda) {
        try {
            VendasTO resultado = vendasBO.save(venda);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar a venda");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar a venda");
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> update(@PathVariable Long codigo,
                                    @RequestBody @Valid VendasTO venda) {
        try {
            venda.setCodigo(codigo);
            VendasTO resultado = vendasBO.update(venda);
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizar a venda");
        }
    }
    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> delete(@PathVariable Long codigo) {
        if (vendasBO.delete(codigo)) {
            return ResponseEntity.status(HttpStatus.OK).body("Venda deletada com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Venda não encontrada");
        }
    }
}