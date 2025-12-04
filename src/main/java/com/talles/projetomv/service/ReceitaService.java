package com.talles.projetomv.service;

import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class ReceitaService {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521/freepdb1";
    private static final String USER = "TALLES_APP";
    private static final String PASSWORD = "24221915";

    public double calcularTaxa(int qtdMovimentacoes) {
        double resultado = 0.0;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            CallableStatement stmt = conn.prepareCall("{ ? = call CALCULAR_VALOR_MOV(?) }");
            stmt.registerOutParameter(1, Types.NUMERIC);
            stmt.setInt(2, qtdMovimentacoes);
            stmt.execute();

            resultado = stmt.getDouble(1);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }
}
