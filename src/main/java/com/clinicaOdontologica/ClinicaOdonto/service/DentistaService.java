package com.clinicaOdontologica.ClinicaOdonto.service;

import com.clinicaOdontologica.ClinicaOdonto.dao.IDao;
import com.clinicaOdontologica.ClinicaOdonto.model.Dentista;
import com.clinicaOdontologica.ClinicaOdonto.model.Paciente;
import com.clinicaOdontologica.ClinicaOdonto.model.dto.DentistaDTO;
import com.clinicaOdontologica.ClinicaOdonto.model.dto.PacienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DentistaService{

    @Autowired
    IDao<Dentista> dentistaDAOH2;

    public Dentista salvar(Dentista dentista) throws SQLException {
        return dentistaDAOH2.salvar(dentista);
    }

    public List<DentistaDTO> listarDentistas() throws SQLException {
        List<Dentista> dentistaList = dentistaDAOH2.buscarTodos();
        List<DentistaDTO> dentistaDTOList = new ArrayList<>();

        for(Dentista e : dentistaList){
            dentistaDTOList.add(new DentistaDTO(e));
        }

        return dentistaDTOList;
    }

    public void alterar (Dentista dentista) throws SQLException{
        dentistaDAOH2.alterar(dentista);
    }

    public void excluir(int id) throws SQLException {
        dentistaDAOH2.excluir(id);
    }

    public Optional<Dentista> buscarDentistaId(int id) throws SQLException{
        return dentistaDAOH2.buscarPorId(id);
    }

}
