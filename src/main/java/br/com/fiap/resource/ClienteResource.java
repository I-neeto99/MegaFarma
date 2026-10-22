package br.com.fiap.resource;

import br.com.fiap.bo.ClienteBO;
import br.com.fiap.to.ClienteTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/megafarma/clientes")
public class ClienteResource {
    private ClienteBO clienteBO = new ClienteBO();

    @GetMapping
    public ResponseEntity<List<ClienteTO>> findAll() {
        List<ClienteTO> clientes = clienteBO.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(clientes);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<?> findByIdCliente(@PathVariable Long idCliente) {
        ClienteTO cliente = clienteBO.findByIdCliente(idCliente);
        if (cliente != null) {
            return ResponseEntity.status(HttpStatus.OK).body(cliente);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid ClienteTO cliente) {
        try {
            ClienteTO resultado = clienteBO.save(cliente);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar o cliente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar o cliente");
        }
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<?> update(@PathVariable Long idCliente,
                                    @RequestBody @Valid ClienteTO cliente) {
        try {
            cliente.setIdCliente(idCliente);
            ClienteTO resultado = clienteBO.update(cliente);
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizar o cliente");
        }
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<?> delete(@PathVariable Long idCliente) {
        if (clienteBO.delete(idCliente)) {
            return ResponseEntity.status(HttpStatus.OK).body("Cliente deletado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cliente não encontrado");
        }
    }
}