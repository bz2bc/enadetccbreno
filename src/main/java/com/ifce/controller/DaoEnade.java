package com.ifce.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import db.DB;

public class DaoEnade {
	public static List<String> get(Connection conn) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
						
			st = conn.prepareStatement("SELECT * FROM INSTITUTO_IES WHERE "
						+ "MUNICIPIO = 'Fortaleza'");

			rs = st.executeQuery();
			List<String> list = new ArrayList<String>();
			if(rs.next()) {
				do {
					System.out.print(rs.toString());
					list.add(rs.getString("CODIGO_IES") + " - " +
							rs.getString("NOME_IES"));
				}while(rs.next());
			}
			
			rs.close();
			return list;
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			System.out.println("\nFim requisição Get");
			//DB.closeConnection();
		}
	}

	public static List<String> consultarTodasAreas(Connection conn) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
						
			st = conn.prepareStatement("SELECT DISTINCT NOME_AREA, CODIGO_AREA FROM AREA_AVALIACAO "
					+ "INNER JOIN EXAME_ENADE ON CODIGO_AREA = FK_CODIGO_AREA ORDER BY NOME_AREA;");

			rs = st.executeQuery();
			List<String> list = new ArrayList<String>();
			if(rs.next()) {
				do {
					System.out.print(rs.toString());
					String arrayValores = rs.getString("NOME_AREA");
					list.add(arrayValores);
				}while(rs.next());
			}
			
			rs.close();
			return list;
			
		}catch(Exception e) {
			throw e;
		}finally {
			System.out.println("\n Fim requisição | consultar Areas");
		}
	}

	public static List<String> consultaEdicoesArea(Connection conn,String area) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
						
			List<String> list = new ArrayList<String>();
			
			if(area==null || area.trim() == "")
				return list;
			
			st = conn.prepareStatement("SELECT EXAME_ENADE.ANO, AREA_AVALIACAO.NOME_AREA, AREA_AVALIACAO.CODIGO_AREA "
					+ "FROM AREA_AVALIACAO "
					+ "INNER JOIN EXAME_ENADE ON AREA_AVALIACAO.CODIGO_AREA = EXAME_ENADE.FK_CODIGO_AREA "
					+ "WHERE TRIM(AREA_AVALIACAO.NOME_AREA) = '"+area.trim()+"' "
					+ "GROUP BY EXAME_ENADE.ANO, AREA_AVALIACAO.NOME_AREA, AREA_AVALIACAO.CODIGO_AREA "
					+ "ORDER BY EXAME_ENADE.ANO DESC;");

			rs = st.executeQuery();
			if(rs.next()) {
				do {
					System.out.print(rs.toString());
					String arrayValores = rs.getString("ANO");
					list.add(arrayValores);
				}while(rs.next());
			}
			
			rs.close();
			return list;
			
		}catch(Exception e) {
			throw e;
		}finally {
			System.out.println("\n Fim requisição | consultar Areas");
		}		
	}

	public static List<String> consultarTodosMunicipios(Connection conn) throws Exception{
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
						
			st = conn.prepareStatement("SELECT DISTINCT MUNICIPIO FROM instituto_ies ORDER BY MUNICIPIO;");

			rs = st.executeQuery();
			List<String> list = new ArrayList<String>();
			if(rs.next()) {
				do {
					//System.out.print(rs.toString());
					list.add(rs.getString("MUNICIPIO"));
				}while(rs.next());
			}
			
			rs.close();
			return list;
			
		}catch(Exception e) {
			throw e;
		}finally {
			System.out.println("\n Fim requisição | consultar Areas");
		}
	}

	public static List<String> consultarTodosIES(Connection conn,String area) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
						
			st = conn.prepareStatement("SELECT DISTINCT NOME_IES FROM EXAME_ENADE "+
					"INNER JOIN AREA_AVALIACAO ON CODIGO_AREA = FK_CODIGO_AREA "+
					"INNER JOIN INSTITUTO_IES ON CODIGO_IES = FK_CODIGO_IES " +
					"WHERE TRIM(NOME_AREA) = '"+area.trim()+"' ORDER BY NOME_IES;");

			rs = st.executeQuery();
			List<String> list = new ArrayList<String>();
			if(rs.next()) {
				do {
					//System.out.print(rs.toString());
					list.add(rs.getString("NOME_IES"));
				}while(rs.next());
			}
			
			rs.close();
			return list;
			
		}catch(Exception e) {
			throw e;
		}finally {
			System.out.println("\n Fim requisição | consultar Areas");
		}
	}

	public static List<String> consultarEdicoesAnoMunicipio(Connection conn, String ano, String municipio) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
						
			List<String> list = new ArrayList<String>();
			
			if(ano==null || ano.trim() == "")
				return list;
			if(municipio==null || municipio.trim() == "")
				return list;
			
			st = conn.prepareStatement("SELECT FK_CODIGO_IES, SIGLA_IES, NOME_IES, ANO,"
					+ " NOME_AREA, FK_MUNICIPIO, MODALIDADE_ENSINO, "
					+ "CONCEITO_ENADE_CONTINUO, NUMERO_CONCLUINTES_PARTICIPANTES,"
					+ " NUMERO_CONCLUINTES_INSCRITOS "
					+ "FROM EXAME_ENADE "
					+ "INNER JOIN AREA_AVALIACAO ON CODIGO_AREA = FK_CODIGO_AREA "
					+ "INNER JOIN INSTITUTO_IES ON CODIGO_IES = FK_CODIGO_IES "
					+ "WHERE ANO = "+ano.trim()+" AND MUNICIPIO = '"+municipio+"' AND FK_MUNICIPIO = '"+municipio+"'");

			rs = st.executeQuery();
			if(rs.next()) {
				do {
					System.out.print(rs.toString());
					String arrayValores = "["
					+rs.getString("FK_CODIGO_IES") + ","+ rs.getString("SIGLA_IES").trim()+","+
					rs.getString("NOME_IES") + ","+ rs.getString("ANO").trim()+","+
					rs.getString("NOME_AREA") + ","+ rs.getString("FK_MUNICIPIO").trim()+","+
					rs.getString("MODALIDADE_ENSINO") + ","+ rs.getString("CONCEITO_ENADE_CONTINUO").trim()+","+
					rs.getString("NUMERO_CONCLUINTES_PARTICIPANTES") + ","+
					rs.getString("NUMERO_CONCLUINTES_INSCRITOS").trim()+
					"]";
					list.add(arrayValores);
				}while(rs.next());
			}
			
			rs.close();
			return list;
			
		}catch(Exception e) {
			throw e;
		}finally {
			System.out.println("\n Fim requisição | consultar Areas");
		}
	}

	public static List<String[]> consultarIndicesPorAnoMunicipioAreaNomeIES(Connection conn, String anoInicial, String anoFinal, String municipio,
			String area, String nomeIes) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
						
			List<String[]> list = new ArrayList<String[]>();
			
			if(anoInicial==null || anoInicial.trim() == "")
				return list;
			if(anoFinal==null || anoFinal.trim() == "")
				return list;
			if(municipio==null || municipio.trim() == "")
				return list;
			if(area==null || area.trim() == "")
				return list;
			if(nomeIes==null || nomeIes.trim() == "")
				return list;
			
			st = conn.prepareStatement("SELECT DISTINCT EDICAO AS Edição, NOME_IES AS InstitutoEnsinoSuperior, "
					+ "NOME_AREA AS ÁreaDeAvaliação, CONCEITO_ENADE_CONTINUO AS Nota FROM EXAME_ENADE "
					+ "INNER JOIN AREA_AVALIACAO ON CODIGO_AREA = FK_CODIGO_AREA INNER JOIN INSTITUTO_IES ON CODIGO_IES = FK_CODIGO_IES "
					+ "WHERE ANO BETWEEN +'"+anoInicial+"' AND '"+anoFinal+"'  AND MUNICIPIO = '"+municipio+"' AND FK_MUNICIPIO = '"+municipio+"' "
					+ "AND TRIM(NOME_AREA) = '"+area+"' AND TRIM(NOME_IES) = '"+nomeIes+"';");

			rs = st.executeQuery();
			if(rs.next()) {
				do {
					System.out.print(rs.toString());
					String[] arrayValores = {rs.getString("Edição"),rs.getString("Nota")};
					list.add(arrayValores);
				}while(rs.next());
			}
			
			rs.close();
			return list;
			
		}catch(Exception e) {
			throw e;
		}finally {
			System.out.println("\n Fim requisição | consultar Areas");
		}
	}
	
	public static List<String[]> consultarIndicesPorAnoMunicipioAreaNomeIES2(Connection conn, String anoInicial, String anoFinal, String municipio,
			String area, String nomeIes) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
						
			List<String[]> list = new ArrayList<String[]>();
			
			if(anoInicial==null || anoInicial.trim() == "")
				return list;
			if(anoFinal==null || anoFinal.trim() == "")
				return list;
			if(municipio==null || municipio.trim() == "")
				return list;
			if(area==null || area.trim() == "")
				return list;
			if(nomeIes==null || nomeIes.trim() == "")
				return list;
			
			st = conn.prepareStatement("SELECT DISTINCT EDICAO AS Edição, NOME_IES AS InstitutoEnsinoSuperior, "
					+ "NOME_AREA AS ÁreaDeAvaliação, NUMERO_CONCLUINTES_INSCRITOS, NUMERO_CONCLUINTES_PARTICIPANTES, CONCEITO_ENADE_CONTINUO AS Nota FROM EXAME_ENADE "
					+ "INNER JOIN AREA_AVALIACAO ON CODIGO_AREA = FK_CODIGO_AREA INNER JOIN INSTITUTO_IES ON CODIGO_IES = FK_CODIGO_IES "
					+ "WHERE ANO BETWEEN +'"+anoInicial+"' AND '"+anoFinal+"'  AND MUNICIPIO = '"+municipio+"' AND FK_MUNICIPIO = '"+municipio+"' "
					+ "AND TRIM(NOME_AREA) = '"+area+"' AND TRIM(NOME_IES) = '"+nomeIes+"';");

			rs = st.executeQuery();
			if(rs.next()) {
				do {
					System.out.print(rs.toString());
					String[] arrayValores = {rs.getString("Edição"),rs.getString("Nota"), 
							rs.getString("NUMERO_CONCLUINTES_INSCRITOS"), 
							rs.getString("NUMERO_CONCLUINTES_PARTICIPANTES")};
					list.add(arrayValores);
				}while(rs.next());
			}
			
			rs.close();
			return list;
			
		}catch(Exception e) {
			throw e;
		}finally {
			System.out.println("\n Fim requisição | consultar Areas");
		}
	}

	public static List<String> consultaMunicipios(Connection conn, String nomeArea, String nomeIes) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<String> list = null;
		try {
			conn = DB.getConnection();
			list = new ArrayList<String>();
			
			st = conn.prepareStatement("SELECT DISTINCT MUNICIPIO FROM AREA_AVALIACAO "
					+ "INNER JOIN EXAME_ENADE ON EXAME_ENADE.FK_CODIGO_AREA = AREA_AVALIACAO.CODIGO_AREA "
					+ "INNER JOIN INSTITUTO_IES ON INSTITUTO_IES.CODIGO_IES = EXAME_ENADE.FK_CODIGO_IES "
					+ "WHERE TRIM(NOME_IES) = ? "
					+ "AND TRIM(NOME_AREA) = ?  AND MUNICIPIO = FK_MUNICIPIO ORDER BY MUNICIPIO;"); 
			
			st.setString(1, nomeIes);
			st.setString(2, nomeArea);
			
			rs = st.executeQuery();
			if(rs.next()) {
				do {
					list.add(rs.getString("MUNICIPIO"));
				}while(rs.next());
			}
			return list;
		}catch(Exception e) {
			throw e;
		}finally {	
			if(rs!=null) {
				rs.close();				
			}
		}
	}
}
