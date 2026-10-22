package br.com.fiap.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testa os endpoints de /megafarma/clientes de ponta a ponta:
 * sobe a aplicação, manda a requisição HTTP e confere status e JSON.
 *
 * É o mesmo que o Postman faz, só que automático.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ClienteResourceTest {

    // Liga o mock antes do Spring subir, para o teste não precisar do Oracle.
    static {
        System.setProperty("megafarma.mock", "true");
    }

    private static final String URL = "/megafarma/clientes";

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    private String jsonDoCliente(String nome, String email) {
        return """
               {
                 "nome": "%s",
                 "cpf": "11122233344",
                 "email": "%s",
                 "dataDeNascimento": "1999-07-10"
               }
               """.formatted(nome, email);
    }

    /** Cadastra um cliente e devolve o id gerado, para os testes que precisam de um id real. */
    private Long cadastraCliente(String nome) throws Exception {
        MvcResult resultado = mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDoCliente(nome, "teste@email.com")))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readTree(resultado.getResponse().getContentAsString())
                .get("idCliente").asLong();
    }

    // ---------- GET /clientes ----------

    @Test
    @DisplayName("GET /clientes devolve 200 e uma lista")
    void findAllDeveDevolver200() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    // ---------- POST /clientes ----------

    @Test
    @DisplayName("POST /clientes devolve 201 e o cliente com id")
    void saveDeveDevolver201() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDoCliente("Italo Oliveira", "italo@email.com")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCliente").isNumber())
                .andExpect(jsonPath("$.nome").value("Italo Oliveira"));
    }

    @Test
    @DisplayName("POST /clientes sem o nome devolve 400")
    void saveSemNomeDeveDevolver400() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 { "cpf": "11122233344", "email": "x@email.com" }
                                 """))
                .andExpect(status().isBadRequest());
    }

    // ---------- GET /clientes/{id} ----------

    @Test
    @DisplayName("GET /clientes/{id} devolve 200 e o cliente certo")
    void findByIdDeveDevolver200() throws Exception {
        Long id = cadastraCliente("Cliente do GET por id");

        mockMvc.perform(get(URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCliente").value(id))
                .andExpect(jsonPath("$.nome").value("Cliente do GET por id"));
    }

    @Test
    @DisplayName("GET /clientes/{id} inexistente devolve 404")
    void findByIdInexistenteDeveDevolver404() throws Exception {
        mockMvc.perform(get(URL + "/999999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente não encontrado"));
    }

    // ---------- PUT /clientes/{id} ----------

    @Test
    @DisplayName("PUT /clientes/{id} devolve 200 e os dados atualizados")
    void updateDeveDevolver200() throws Exception {
        Long id = cadastraCliente("Nome antigo");

        mockMvc.perform(put(URL + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDoCliente("Nome novo", "novo@email.com")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCliente").value(id))
                .andExpect(jsonPath("$.nome").value("Nome novo"));

        // confere que a alteração ficou gravada
        mockMvc.perform(get(URL + "/" + id))
                .andExpect(jsonPath("$.nome").value("Nome novo"));
    }

    // ---------- DELETE /clientes/{id} ----------

    @Test
    @DisplayName("DELETE /clientes/{id} devolve 200 e o cliente some")
    void deleteDeveDevolver200() throws Exception {
        Long id = cadastraCliente("Cliente do DELETE");

        mockMvc.perform(delete(URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente deletado com sucesso"));

        mockMvc.perform(get(URL + "/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /clientes/{id} inexistente devolve 404")
    void deleteInexistenteDeveDevolver404() throws Exception {
        mockMvc.perform(delete(URL + "/999999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente não encontrado"));
    }
}
