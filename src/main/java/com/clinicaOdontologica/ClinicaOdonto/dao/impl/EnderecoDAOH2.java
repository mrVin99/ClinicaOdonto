package com.clinicaOdontologica.ClinicaOdonto.dao.impl;

import com.clinicaOdontologica.ClinicaOdonto.dao.ConfigurationJDBC;
import com.clinicaOdontologica.ClinicaOdonto.dao.IDao;
import com.clinicaOdontologica.ClinicaOdonto.model.Endereco;
import com.clinicaOdontologica.ClinicaOdonto.model.Paciente;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
@Configuration
public class EnderecoDAOH2 implements IDao<Endereco> {
    private static final Logger logger = LogManager.getLogger(PacienteDAOH2.class);
    ConfigurationJDBC configurationJDBC = new ConfigurationJDBC("org.h2.Driver", "jdbc:h2:~/ClinicaOdonto;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "");
    Connection connection = null;
    @Override
    public Endereco salvar(Endereco endereco) throws SQLException {
        logger.info("Abrindo conexão");
        String SQLInsert = String.format("INSERT INTO enderecos (estado, cidade, cep, rua, numero)" +
                " VALUES ('%s','%s','%s','%s','%s')", endereco.getEstado(), endereco.getCidade(),endereco.getCep(),endereco.getRua(),endereco.getNumero());
        Connection connection = null;
        try {
            logger.info("Salvando novo endereço");
            configurationJDBC = new ConfigurationJDBC("org.h2.Driver","jdbc:h2:~/ClinicaOdonto;INIT=RUNSCRIPT FROM 'create.sql'","sa","");
            connection = configurationJDBC.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(SQLInsert, Statement.RETURN_GENERATED_KEYS);

            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.next()){
                endereco.setId(resultSet.getInt(1));
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Erro ao cadastrar endereço");
        }finally {
            logger.info("Fechando conexão");
            connection.close();
        }
        return endereco;
    }

    @Override
    public List<Endereco> buscarTodos() throws SQLException {
        logger.debug("Abrindo uma conexão no banco");
        Connection connection = null;
        Statement stmt = null;
        String SQLQUERY = "SELECT * FROM ENDERECOS";
        List<Endereco> enderecos = new ArrayList<>();
        try {
            configurationJDBC = new ConfigurationJDBC("org.h2.Driver","jdbc:h2:~/ClinicaOdonto;INIT=RUNSCRIPT FROM 'create.sql'","sa","");
            connection = configurationJDBC.getConnection();
             stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQLQUERY);

            logger.debug("Buscando todos os endereços do banco");

            while (rs.next()){
                enderecos.add(criarObjetoEndereco(rs));
            }

        } catch (SQLException throwables) {
           throwables.printStackTrace();
        } finally {
            connection.close();
        }

        return enderecos;
    }

    @Override
    public void alterar(Endereco endereco) throws SQLException {

    }

    @Override
    public void excluir(int id) throws SQLException {

    }
    private Endereco criarObjetoEndereco(ResultSet rs) throws SQLException {

        Integer id = rs.getInt("idEndereco");
        String estado = rs.getString("estado");
        String cidade = rs.getString("cidade");
        String cep = rs.getString("cep");
        String rua = rs.getString("rua");
        String numero = rs.getString("numero");
        return new Endereco(id,estado, cidade, cep,rua, numero);

    }
}