package com.test.api.repositories;

import com.test.api.enums.Especialidade;
import com.test.api.models.Consulta;
import com.test.api.models.Medico;
import com.test.api.models.Paciente;
import com.test.api.records.consulta.DadosAgendamentoConsulta;
import com.test.api.records.endereco.DadosEndereco;
import com.test.api.records.medico.DadosCadastroMedico;
import com.test.api.records.paciente.DadosCadastroPaciente;
import com.test.api.services.ConsultaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ConsultaRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private ConsultaService consultaService;

    @Test
    @DisplayName("Deveria gravar a consulta com sucesso se for uma data futura")
    void cadastrarConsultaDiretamente() {

        //given ou arrange
        var dataFutura = LocalDateTime.now().plusDays(4);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
        var especialidade = Especialidade.CARDIOLOGIA;
        DadosAgendamentoConsulta dadosAgendamentoConsulta = new DadosAgendamentoConsulta(medico.getId(), paciente.getId(), dataFutura, especialidade);
        //when ou act
        var consultaCadastrada = consultaService.agendar(dadosAgendamentoConsulta);

        //then ou assert
        assertThat(consultaCadastrada).isNotNull();
        assertThat(consultaCadastrada.id()).isNotNull();
        assertThat(consultaCadastrada.idMedico()).isEqualTo(medico.getId());
        assertThat(consultaCadastrada.idPaciente()).isEqualTo(paciente.getId());
        assertThat(consultaCadastrada.data()).isEqualTo(dataFutura);

    }

    private Consulta cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        var consulta = em.persist(new Consulta(null, medico, paciente, data, null));
        return consulta;
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }

}