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
        // TODO(human): escreva o corpo deste método.
        //
        // Siga a mesma ideia do findByIdCliente:
        //   1. chame clienteBO.delete(idCliente) -> ele devolve um boolean
        //   2. se for true  -> HttpStatus.OK    + "Cliente deletado com sucesso"
        //   3. se for false -> HttpStatus.NOT_FOUND + "Cliente não encontrado"
        //
        // Dica: NÃO use HttpStatus.NO_CONTENT (204) junto com .body(...).
        // O 204 significa "sem conteúdo" e o Spring joga a mensagem fora.
        // Ou manda 200 com mensagem, ou 204 com .build() e sem mensagem.
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
