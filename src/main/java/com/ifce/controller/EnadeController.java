package com.ifce.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analiseenade")
public class EnadeController {
	static Connection conn = null;
	
	/*@GetMapping("/getTeste") 
	public List<String> get() {
		try {
			return DaoEnade.get(conn);
		}catch(Exception e) {
			throw e;
		}
	}*/
	@GetMapping("/consulta-todas-areas")
	public List<String> consultaAreas() throws Exception{
		try {
			return DaoEnade.consultarTodasAreas(conn);
		}catch(Exception e) {
			throw e;
		}
	}
	
	@GetMapping("/consulta-edicoes-por-area")
	public List<String> consultaEdicoesPorArea(@RequestParam(name = "nomeArea") String nomeArea) throws Exception{
		try {
			return DaoEnade.consultaEdicoesArea(conn, nomeArea);
		}catch(Exception e) {
			throw e;
		}
	}
	
	@GetMapping("/consulta-todos-municipios")
	public List<String> consultaMunicipios() throws Exception{
		try {
			return DaoEnade.consultarTodosMunicipios(conn);
		}catch(Exception e) {
			throw e;
		}
	}
	
	@GetMapping("/consulta-todos-ies")
	public List<String> consultaTodosIES(@RequestParam(name = "nomeArea") String nomeArea) throws Exception{
		try {
			return DaoEnade.consultarTodosIES(conn,nomeArea);
		}catch(Exception e) {
			throw e;
		}
	}
	@GetMapping("/consulta-dados-por-ano-municipio-area-nomeies-individual")
	public List<String[]> consultaEdicoesPorAnoMunicipio(
			@RequestParam(name = "anoInicial") String anoInicial,
			@RequestParam(name = "anoFinal") String anoFinal,
			@RequestParam(name="municipio") String municipio,
			@RequestParam(name="area") String area,
			@RequestParam(name="nomeies") String nomeIes) throws Exception{
		try {
			
			
			List<String[]> ies1 = DaoEnade.consultarIndicesPorAnoMunicipioAreaNomeIES(conn, anoInicial, anoFinal, municipio, area, nomeIes);
			return ies1;
		}catch(Exception e) {
			throw e;
		}
	}
	
	@GetMapping("/consulta-dados-por-ano-municipio-area-nomeies")
	public List<Object> consultaEdicoesPorAnoMunicipio(
			@RequestParam(name = "anoInicial") String anoInicial,
			@RequestParam(name = "anoFinal") String anoFinal,
			@RequestParam(name="municipio1") String municipio1,
			@RequestParam(name="municipio2") String municipio2,
			@RequestParam(name="area") String area,
			@RequestParam(name="nomeies1") String nomeIes1,
			@RequestParam(name="nomeies2") String nomeIes2) throws Exception{
		try {
			List<String[]> ies1 = DaoEnade.consultarIndicesPorAnoMunicipioAreaNomeIES(conn, anoInicial, anoFinal, municipio1, area, nomeIes1);
			List<String[]> ies2 = DaoEnade.consultarIndicesPorAnoMunicipioAreaNomeIES(conn, anoInicial, anoFinal, municipio2, area, nomeIes2);
			
			Set<String> anosSet = new HashSet<String>();
			for (String[] item : ies1) {
				anosSet.add(item[0]);
			}
			for (String[] item : ies2) {
				anosSet.add(item[0]);
			}
			
			List<String> anos = new ArrayList<String>(anosSet);
			Collections.sort(anos);
			
			List<List<String>> notas = new ArrayList<List<String>>();
			for (int i = 0; i < 2; i++) {
				List<String[]> ies = (i == 0) ? ies1 : ies2;
				List<String> notasIes = new ArrayList<String>();
				for (String ano : anos) {
					boolean encontrado = false;
					for (String[] item : ies) {
						if (item[0].equals(ano)) {
							notasIes.add(item[1]);
							encontrado = true;
							break;
						}
					}
					if (!encontrado) {
						notasIes.add("undefined");
					}
				}
				notas.add(notasIes);
			}
			
			List<Object> l = new ArrayList<Object>();
			l.add(anos);
			l.add(notas.get(0));
			l.add(notas.get(1));
			return l;
		}catch(Exception e) {
			throw e;
		}
	}
	
	@GetMapping("/consulta-dados-por-ano-municipio-area-nomeies2")
	public List<Object> consultaEdicoesPorAnoMunicipio2(
			@RequestParam(name = "anoInicial") String anoInicial,
			@RequestParam(name = "anoFinal") String anoFinal,
			@RequestParam(name="municipio1") String municipio1,
			@RequestParam(name="municipio2") String municipio2,
			@RequestParam(name="area") String area,
			@RequestParam(name="nomeies1") String nomeIes1,
			@RequestParam(name="nomeies2") String nomeIes2) throws Exception{
		try {
			List<String[]> ies1 = DaoEnade.consultarIndicesPorAnoMunicipioAreaNomeIES2(conn, anoInicial, anoFinal, municipio1, area, nomeIes1);
			List<String[]> ies2 = DaoEnade.consultarIndicesPorAnoMunicipioAreaNomeIES2(conn, anoInicial, anoFinal, municipio2, area, nomeIes2);
			
			Set<String> anosSet = new HashSet<String>();
			for (String[] item : ies1) {
				anosSet.add(item[0]);
			}
			for (String[] item : ies2) {
				anosSet.add(item[0]);
			}
			
			List<String> anos = new ArrayList<String>(anosSet);
			Collections.sort(anos);
			
			List<List<Object>> notas = new ArrayList<List<Object>>();
			for (int i = 0; i < 2; i++) {
				List<String[]> ies = (i == 0) ? ies1 : ies2;
				List<Object> notasIes = new ArrayList<Object>();
				for (String ano : anos) {
					boolean encontrado = false;
					for (String[] item : ies) {
						
						System.out.println(item.length);
						if (item[0].equals(ano)) {
							ArrayList<Object> list = new ArrayList<>();
							//list.add(item[1]);
							System.out.println("\nitem1: "+item[0]);//ano
							System.out.println("\nitem2: "+item[1]);//nota
							System.out.println("\nitem3: "+item[2]);//ano
							System.out.println("\nitem4: "+item[3]);//nota
							list.add(item[2]);//nr par
							//list.add(item[3]);//nr par conclu
							notasIes.add(list);
							encontrado = true;
							break;
						}
					}
					if (!encontrado) {
						notasIes.add("undefined");
					}
				}
				notas.add(notasIes);
			}
			
			List<Object> l = new ArrayList<Object>();
			l.add(anos);
			l.add(notas.get(0));
			l.add(notas.get(1));
			//l.add(notas.get(2));
			return l;
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@GetMapping("/consulta-dados-por-ano-municipio-area-nomeies3")
	public List<Object> consultaEdicoesPorAnoMunicipio3(
			@RequestParam(name = "anoInicial") String anoInicial,
			@RequestParam(name = "anoFinal") String anoFinal,
			@RequestParam(name="municipio1") String municipio1,
			@RequestParam(name="municipio2") String municipio2,
			@RequestParam(name="area") String area,
			@RequestParam(name="nomeies1") String nomeIes1,
			@RequestParam(name="nomeies2") String nomeIes2) throws Exception{
		try {
			List<String[]> ies1 = DaoEnade.consultarIndicesPorAnoMunicipioAreaNomeIES2(conn, anoInicial, anoFinal, municipio1, area, nomeIes1);
			List<String[]> ies2 = DaoEnade.consultarIndicesPorAnoMunicipioAreaNomeIES2(conn, anoInicial, anoFinal, municipio2, area, nomeIes2);
			
			Set<String> anosSet = new HashSet<String>();
			for (String[] item : ies1) {
				anosSet.add(item[0]);
			}
			for (String[] item : ies2) {
				anosSet.add(item[0]);
			}
			
			List<String> anos = new ArrayList<String>(anosSet);
			Collections.sort(anos);
			
			List<List<Object>> notas = new ArrayList<List<Object>>();
			for (int i = 0; i < 2; i++) {
				List<String[]> ies = (i == 0) ? ies1 : ies2;
				List<Object> notasIes = new ArrayList<Object>();
				for (String ano : anos) {
					boolean encontrado = false;
					for (String[] item : ies) {
						
						System.out.println(item.length);
						if (item[0].equals(ano)) {
							ArrayList<Object> list = new ArrayList<>();
							//list.add(item[1]);
							System.out.println("\nitem1: "+item[0]);//ano
							System.out.println("\nitem2: "+item[1]);//nota
							System.out.println("\nitem3: "+item[2]);//ano
							System.out.println("\nitem4: "+item[3]);//nota
							list.add(item[3]);//nr par
							//list.add(item[3]);//nr par conclu
							notasIes.add(list);
							encontrado = true;
							break;
						}
					}
					if (!encontrado) {
						notasIes.add("undefined");
					}
				}
				notas.add(notasIes);
			}
			
			List<Object> l = new ArrayList<Object>();
			l.add(anos);
			l.add(notas.get(0));
			l.add(notas.get(1));
			//l.add(notas.get(2));
			return l;
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@GetMapping("/consulta-dados-por-ano-municipio")
	public List<String> consultaEdicoesPorAnoMunicipio(@RequestParam(name = "ano") String ano,@RequestParam(name="municipio") String municipio) throws Exception{
		try {
			return DaoEnade.consultarEdicoesAnoMunicipio(conn, ano, municipio);
		}catch(Exception e) {
			throw e;
		}
	}
	
	@GetMapping("/consulta-municipios-por-nomearea-nomeies")
	public List<String> consultaMunicipiosPorNomeAreaNomeIes(
			@RequestParam(name = "nomeArea") String nomeArea,
			@RequestParam(name="nomeIes") String nomeIes) throws Exception{
		try {
			return DaoEnade.consultaMunicipios(conn, nomeArea, nomeIes);
		}catch(Exception e) {
			throw e;
		}
	}
	
	
	

}