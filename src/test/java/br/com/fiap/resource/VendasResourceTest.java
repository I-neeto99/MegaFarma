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
 * Testa os endpoints de /megafarma/vendas de ponta a ponta.
 */
@SpringBootTest
@AutoConfigureMockMvc
class VendasResourceTest {

    static {
        System.setProperty("megafarma.mock", "true");
    }

    private static final String URL = "/megafarma/vendas";

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    private String jsonDaVenda(String data, long codCliente) {
        return """
               { "dataDaVenda": "%s", "codCliente": %d }
               """.formatted(data, codCliente);
    }

    private Long cadastraVenda() throws Exception {
        MvcResult resultado = mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDaVenda("2026-10-22", 1)))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readTree(resultado.getResponse().getContentAsString())
                .get("codigo").asLong();
    }

    @Test
    @DisplayName("GET /vendas devolve 200 e uma lista")
    void findAllDeveDevolver200() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("POST /vendas devolve 201 e a venda com codigo")
    void saveDeveDevolver201() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDaVenda("2026-10-22", 1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigo").isNumber())
                .andExpect(jsonPath("$.codCliente").value(1));
    }

    @Test
    @DisplayName("POST /vendas sem o codCliente devolve 400")
    void saveSemClienteDeveDevolver400() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"dataDaVenda\": \"2026-10-22\" }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /vendas com data no futuro devolve 400")
    void saveComDataFuturaDeveDevolver400() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDaVenda("2099-01-01", 1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /vendas/{codigo} devolve 200 e a venda certa")
    void findByCodigoDeveDevolver200() throws Exception {
        Long codigo = cadastraVenda();

        mockMvc.perform(get(URL + "/" + codigo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value(codigo));
    }

    @Test
    @DisplayName("GET /vendas/{codigo} inexistente devolve 404")
    void findByCodigoInexistenteDeveDevolver404() throws Exception {
        mockMvc.perform(get(URL + "/999999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Venda não encontrada"));
    }

    @Test
    @DisplayName("PUT /vendas/{codigo} devolve 200 e grava a alteracao")
    void updateDeveDevolver200() throws Exception {
        Long codigo = cadastraVenda();

        mockMvc.perform(put(URL + "/" + codigo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDaVenda("2026-10-01", 2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value(codigo))
                .andExpect(jsonPath("$.codCliente").value(2));

        mockMvc.perform(get(URL + "/" + codigo))
                .andExpect(jsonPath("$.codCliente").value(2));
    }

    @Test
    @DisplayName("DELETE /vendas/{codigo} devolve 200 e a venda some")
    void deleteDeveDevolver200() throws Exception {
        Long codigo = cadastraVenda();

        mockMvc.perform(delete(URL + "/" + codigo))
                .andExpect(status().isOk())
                .andExpect(content().string("Venda deletada com sucesso"));

        mockMvc.perform(get(URL + "/" + codigo))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /vendas/{codigo} inexistente devolve 404")
    void deleteInexistenteDeveDevolver404() throws Exception {
        mockMvc.perform(delete(URL + "/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("A rota /vendas nao e capturada pelo controller de remedios")
    void rotaDeVendasNaoColideComRemedios() throws Exception {
        // /megafarma/vendas poderia cair em /megafarma/{codigo} do remedio.
        // Se caisse, viria 400 tentando converter "vendas" em numero.
        mockMvc.perform(get(URL))
                .andExpect(status().isOk());
    }
}
