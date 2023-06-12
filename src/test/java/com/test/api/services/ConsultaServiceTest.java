package com.test.api.services;

import com.test.api.configs.exceptions.ValidacaoException;
import com.test.api.enums.Especialidade;
import com.test.api.models.Consulta;
import com.test.api.models.Endereco;
import com.test.api.models.Medico;
import com.test.api.models.Paciente;
import com.test.api.records.consulta.DadosAgendamentoConsulta;
import com.test.api.records.consulta.DadosDetalhamentoConsulta;
import com.test.api.records.endereco.DadosEndereco;
import com.test.api.records.medico.DadosCadastroMedico;
import com.test.api.records.paciente.DadosCadastroPaciente;
import com.test.api.repositories.ConsultaRepository;
import com.test.api.repositories.MedicoRepository;
import com.test.api.repositories.PacienteRepository;
import com.test.api.validations.agendamento.ValidadorAgendamentoDeConsulta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultaServiceTest {

    @InjectMocks
    private ConsultaService consultaService;
    @Mock
    private MedicoRepository medicoRepositoryMock;
    @Mock
    private PacienteRepository pacienteRepositoryMock;
    @Mock
    private ConsultaRepository consultaRepositoryMock;

    @Mock
    private ValidadorAgendamentoDeConsulta validadorMock;

    @Mock
    private List<ValidadorAgendamentoDeConsulta> validadores;

    @Captor
    private ArgumentCaptor<Medico> medicoCaptor;

    @Captor
    private ArgumentCaptor<Consulta> consultaCaptor;



    @Test
    @DisplayName("Deve agendar uma consulta com sucesso para uma data futura")
    void agendarComSucessoParaDataFutura() {

        // arrange
        var dataFutura = LocalDateTime.now().plusDays(1);
        var medicoMock = cadastrarMedico();
        var pacienteMock = cadastrarPaciente();
        var especialidade = Especialidade.CARDIOLOGIA;

//        Medico medicoMock = Mockito.mock(Medico.class);
//        Paciente pacienteMock = Mockito.mock(Paciente.class);
//        Consulta consultaMock = Mockito.mock(Consulta.class);

        when(medicoRepositoryMock.existsById(medicoMock.getId())).thenReturn(true);
        when(medicoRepositoryMock.getReferenceById(medicoMock.getId())).thenReturn(medicoMock);

        when(pacienteRepositoryMock.existsById(pacienteMock.getId())).thenReturn(true);
        when(pacienteRepositoryMock.getReferenceById(pacienteMock.getId())).thenReturn(pacienteMock);

        var dadosAgendamento = new DadosAgendamentoConsulta( medicoMock.getId(), pacienteMock.getId(), dataFutura, especialidade);
        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, medicoMock.getId(), pacienteMock.getId(), dataFutura);

        //action
        var resultado = consultaService.agendar(dadosAgendamento);

        // assertions
        Mockito.verify(consultaRepositoryMock).save(consultaCaptor.capture());
        var consultaSalva = consultaCaptor.getValue();

        assertNotNull(consultaSalva);
        assertThat(consultaSalva.getData()).isEqualTo(dadosAgendamento.data());
        assertThat(consultaSalva.getPaciente().getId()).isEqualTo(dadosAgendamento.idPaciente());

        assertThat(consultaSalva.getMedico()).isNotNull();
        assertThat(consultaSalva.getPaciente()).isNotNull();

        assertThat(resultado).isEqualTo(dadosDetalhamento);

    }

    @Test
    @DisplayName("Deve apresentar erro ao agendar uma consulta sem paciente")
    void agendarSemSucessoParaConsultaSemPaciente() {

        var dadosAgendamento = new DadosAgendamentoConsulta(null, null, null, null);
        assertThrows(ValidacaoException.class, () -> consultaService.agendar(dadosAgendamento));
    }

    private Medico cadastrarMedico() {
        return new Medico(1L, "Medico", "medico@com.med", "11111111111", "123456", Especialidade.CARDIOLOGIA, dadosEndereco(), true);
    }

    private Paciente cadastrarPaciente() {
        return new Paciente(1L, "Paciente", "paciente@email.com", "00000000000", "11111111111", dadosEndereco(), true);
    }

    private Endereco dadosEndereco() {
        return new Endereco(
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