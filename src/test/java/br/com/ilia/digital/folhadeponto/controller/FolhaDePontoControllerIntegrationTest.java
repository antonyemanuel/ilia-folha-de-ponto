package br.com.ilia.digital.folhadeponto.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ilia.digital.folhadeponto.FolhaDePontoApplication;
import br.com.ilia.digital.folhadeponto.controller.form.AlocacaoForm;
import br.com.ilia.digital.folhadeponto.controller.form.Momento;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FolhaDePontoApplication.class)
@TestPropertySource(value = "classpath:application-test.properties")
@AutoConfigureMockMvc
public class FolhaDePontoControllerIntegrationTest {
 
    @Autowired
    private MockMvc mockMvc;
 
	@Autowired
	private ObjectMapper objectMapper;
 
	@Test
	void registroComSucesso() throws Exception {
		Momento m = new Momento();
		m.setDataHora("2020-11-16T08:00:10");
		String content = this.objectMapper.writeValueAsString(m);
		this.mockMvc.perform(post("/batidas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isCreated())
		.andExpect(content().string("{\"dia\":\"2020-11-16\",\"horarios\":[\"08:00:10\"]}"));
	}
	
	@Test
	void registroComSucesso_segundaBatida() throws Exception {
		Momento m = new Momento();
		m.setDataHora("2020-11-16T08:00:10");
		
		String content = this.objectMapper.writeValueAsString(m);
		this.mockMvc.perform(post("/batidas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isCreated())
		.andExpect(content().string("{\"dia\":\"2020-11-16\",\"horarios\":[\"08:00:10\"]}"));
		
		m.setDataHora("2020-11-16T09:00:10");
		content = this.objectMapper.writeValueAsString(m);
		this.mockMvc.perform(post("/batidas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isCreated())
		.andExpect(content().string("{\"dia\":\"2020-11-16\",\"horarios\":[\"08:00:10\",\"09:00:10\"]}"));		
	}	
	
	@Test
	void registroAcimaLimiteDiario() throws Exception {
		Momento m = new Momento();
		m.setDataHora("2020-11-16T08:00:00");
		String content = this.objectMapper.writeValueAsString(m);
		this.mockMvc.perform(post("/batidas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isCreated())
		.andExpect(content().string("{\"dia\":\"2020-11-16\",\"horarios\":[\"08:00:00\"]}"));

		m.setDataHora("2020-11-16T12:00:00");
		content = this.objectMapper.writeValueAsString(m);
		this.mockMvc.perform(post("/batidas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isCreated())
		.andExpect(content().string("{\"dia\":\"2020-11-16\",\"horarios\":[\"08:00:00\",\"12:00:00\"]}"));
		
		m.setDataHora("2020-11-16T13:10:00");
		content = this.objectMapper.writeValueAsString(m);
		this.mockMvc.perform(post("/batidas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isCreated())
		.andExpect(content().string("{\"dia\":\"2020-11-16\",\"horarios\":[\"08:00:00\",\"12:00:00\",\"13:10:00\"]}"));
		
		m.setDataHora("2020-11-16T16:30:00");
		content = this.objectMapper.writeValueAsString(m);
		this.mockMvc.perform(post("/batidas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isCreated())
		.andExpect(content().string("{\"dia\":\"2020-11-16\",\"horarios\":[\"08:00:00\",\"12:00:00\",\"13:10:00\",\"16:30:00\"]}"));
		
		m.setDataHora("2020-11-16T18:00:00");
		content = this.objectMapper.writeValueAsString(m);
		this.mockMvc.perform(post("/batidas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isBadRequest())
		.andExpect(content().string("O limite diário de batidas foi atingido: 4"));
	}
	
	@Test
	void registroDiaNaoUtil() throws Exception {
		Momento m = new Momento();
		//domingo
		m.setDataHora("2020-11-15T08:00:00");
		String content = this.objectMapper.writeValueAsString(m);
		this.mockMvc.perform(post("/batidas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isBadRequest())
		.andExpect(content().string("{\"Não é permitido registrar o ponto Sábados e Domingos\"}"));
	}	
	
	@Test
	void registroIntervaloAlmoco() throws Exception {
		Momento m = new Momento();
		m.setDataHora("2020-11-16T08:00:00");
		String content = this.objectMapper.writeValueAsString(m);
		this.mockMvc.perform(post("/batidas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isCreated())
		.andExpect(content().string("{\"dia\":\"2020-11-16\",\"horarios\":[\"08:00:00\"]}"));

		m.setDataHora("2020-11-16T12:00:00");
		content = this.objectMapper.writeValueAsString(m);
		this.mockMvc.perform(post("/batidas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isCreated())
		.andExpect(content().string("{\"dia\":\"2020-11-16\",\"horarios\":[\"08:00:00\",\"12:00:00\"]}"));
		
		m.setDataHora("2020-11-16T12:10:00");
		content = this.objectMapper.writeValueAsString(m);
		this.mockMvc.perform(post("/batidas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isBadRequest())
		.andExpect(content().string("O intervalo de almoço deve ser no mínimo de 1h"));
		
	}


	@Test
	void realizarAlocacaoComSucesso() throws Exception {
		Momento m = new Momento();
		m.setDataHora("2020-11-16T08:00:00");
		String content = this.objectMapper.writeValueAsString(m);
		this.mockMvc.perform(post("/batidas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isCreated())
		.andExpect(content().string("{\"dia\":\"2020-11-16\",\"horarios\":[\"08:00:00\"]}"));

		m.setDataHora("2020-11-16T12:00:00");
		content = this.objectMapper.writeValueAsString(m);
		this.mockMvc.perform(post("/batidas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isCreated())
		.andExpect(content().string("{\"dia\":\"2020-11-16\",\"horarios\":[\"08:00:00\",\"12:00:00\"]}"));
		
		AlocacaoForm al = new AlocacaoForm();
		al.setDia("2020-11-16");
		al.setTempo("PT2H30M10S");
		al.setNomeProjeto("Folha");
		content = this.objectMapper.writeValueAsString(al);
		this.mockMvc.perform(post("/alocacoes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isCreated())
		.andExpect(content().string("{\"dia\":\"2020-11-16\",\"nomeProjeto\":\"Folha\",\"tempo\":\"PT2H30M10S\"}"));
	}
	
	@Test
	void realizarAlocacaoSemRegistroPonto() throws Exception {
		AlocacaoForm al = new AlocacaoForm();
		al.setDia("2020-11-16");
		al.setTempo("PT2H30M10S");
		al.setNomeProjeto("Folha");
		String content = this.objectMapper.writeValueAsString(al);
		this.mockMvc.perform(post("/alocacoes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isBadRequest())
		.andExpect(content().string("Não existem batidas de ponto para esse dia. É necessário registra-las primeiro."));
	}
	
	@Test
	void realizarAlocacaoComTempoMaiorQueRegistrado() throws Exception {
		Momento m = new Momento();
		m.setDataHora("2020-11-16T08:00:00");
		String content = this.objectMapper.writeValueAsString(m);
		this.mockMvc.perform(post("/batidas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isCreated())
		.andExpect(content().string("{\"dia\":\"2020-11-16\",\"horarios\":[\"08:00:00\"]}"));

		m.setDataHora("2020-11-16T12:00:00");
		content = this.objectMapper.writeValueAsString(m);
		this.mockMvc.perform(post("/batidas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isCreated())
		.andExpect(content().string("{\"dia\":\"2020-11-16\",\"horarios\":[\"08:00:00\",\"12:00:00\"]}"));
		
		AlocacaoForm al = new AlocacaoForm();
		al.setDia("2020-11-16");
		al.setTempo("PT5H30M10S");
		al.setNomeProjeto("Folha");
		content = this.objectMapper.writeValueAsString(al);
		this.mockMvc.perform(post("/alocacoes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isCreated())
		.andExpect(content().string("A Alocação não pode ser realizada porque ultrapassou o tempo trabalhado do dia"));
	}

}