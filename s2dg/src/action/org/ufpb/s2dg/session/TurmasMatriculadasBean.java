package org.ufpb.s2dg.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.AlunoTurma;
import org.ufpb.s2dg.entity.Disciplina;
import org.ufpb.s2dg.entity.Horario;
import org.ufpb.s2dg.entity.Sala;
import org.ufpb.s2dg.entity.Turma;
import org.ufpb.s2dg.entity.Aluno.FormaIngresso;
import org.ufpb.s2dg.entity.Aluno.SituacaoAcademica;
import org.ufpb.s2dg.entity.AlunoTurma.Situacao;
import org.ufpb.s2dg.entity.Disciplina.Tipo;
import org.ufpb.s2dg.session.persistence.AlunoDAO;

@Name("turmasMatriculadasBean")
@Scope(ScopeType.SESSION)
@AutoCreate
public class TurmasMatriculadasBean {

	private List<AlunoTurma> alunoTurmas;
	
	@In
	Fachada fachada;

	@In
	MatriculaBean matriculaBean;
	
	@In
	TurmasMatriculadasBean turmasMatriculadasBean;
	
	@In
	PDFAction pdfAction;
	
	@In
	AlunoDAO alunoDAO;
	
	@In
	UsuarioBean usuarioBean;
		
	public void init() {
		List<AlunoTurma> ats = fachada.getAlunoTurmaDoBanco();
		if(ats != null) {
			if (ats.size() > 0) {
				fachada.setAlunoTurma(ats.get(0));
				this.alunoTurmas = ats;
				
				for(AlunoTurma alunoTurma : alunoTurmas) {
					Turma t = alunoTurma.getTurma();
					if(t == null)
						t = fachada.getTurmaDoBanco(alunoTurma);
					Disciplina d = t.getDisciplina();
					if(d == null)
						d = fachada.getDisciplinaDoBanco(t);
					t.setDisciplina(d);
					alunoTurma.setTurma(t);
				}
				
			}
		}
	}

	public List<AlunoTurma> getAlunoTurmas() {
		return alunoTurmas;
	}

	public void setAlunoTurmas(List<AlunoTurma> alunoTurmas) {
		this.alunoTurmas = alunoTurmas;
	}
	
	public int geraCreditosIntegralizados(){
		int creditosIntegralizados = 0;
		List<AlunoTurma> ats = fachada.getAlunoTurmaDoBanco();
		if(ats != null){
			for(int i=0; i < ats.size(); i++){
				if((ats.get(i).getSituacao()==Situacao.APROVADO) ||( ats.get(i).getSituacao()==Situacao.DISPENSADO) 
						|| (ats.get(i).getSituacao()==Situacao.REPROVADO_POR_MEDIA)){
					creditosIntegralizados += ats.get(i).getTurma().getDisciplina().getCreditos();
				}
			}
		}
		return creditosIntegralizados;
	}
	public float geraCRE(){
		float somaMedias = 0;
		float somaCreditos = (float)geraCreditosIntegralizados();
		
		List<AlunoTurma> ats = fachada.getAlunoTurmaDoBanco();
		if(ats != null){
			for(int i=0; i < ats.size(); i++){
				if((ats.get(i).getSituacao()==Situacao.APROVADO) ||( ats.get(i).getSituacao()==Situacao.DISPENSADO) 
						|| (ats.get(i).getSituacao()==Situacao.REPROVADO_POR_MEDIA)){
					somaMedias += (ats.get(i).getTurma().getDisciplina().getCreditos())*(ats.get(i).getMedia());
				}	
			}
		}
		return (somaMedias/somaCreditos);
	}
	
	public int cargaHoraria(int creditos) {
		return creditos*15;
	}
	
	public int geraDisciplinasIntegralizadas(){
		int disciplinasIntegralizadas = 0;
		List<AlunoTurma> ats = fachada.getAlunoTurmaDoBanco();
		if(ats != null){
			for(int i=0; i < ats.size(); i++){
				if((ats.get(i).getSituacao()==Situacao.APROVADO) ||( ats.get(i).getSituacao()==Situacao.DISPENSADO) 
						|| (ats.get(i).getSituacao()==Situacao.REPROVADO_POR_MEDIA)){
					disciplinasIntegralizadas++;
				}
			}
			return disciplinasIntegralizadas;
		}
		return 0;
	}
	
	public List<Sala> getSalasDoBanco(long id) {
		return fachada.getSalaDoBanco(id);}
	
	public float geraMedia(AlunoTurma alunoTurma){
		if((alunoTurma.getSituacao()==Situacao.APROVADO) ||( alunoTurma.getSituacao()==Situacao.DISPENSADO) 
			|| (alunoTurma.getSituacao()==Situacao.REPROVADO_POR_MEDIA)){
			return alunoTurma.getMedia();
		}
		return 0;
	}
	
	public int geraTrancamentosParciais(){
		int trancamentosParciais = 0;
		List<AlunoTurma> ats = fachada.getAlunoTurmaDoBanco();
		if(ats != null){
			for(int i=0; i < ats.size(); i++){
				if(ats.get(i).getSituacao()==Situacao.TRANCADO){
					trancamentosParciais++;
				}
			}
			return trancamentosParciais;
		}
		return trancamentosParciais;
	}
	public List<AlunoTurma> getDisciplinasOrdenadas(List<AlunoTurma> alunoTurma){
		ArrayList<AlunoTurma> lista  = new ArrayList<AlunoTurma>(alunoTurma);
		LinkedList<AlunoTurma> ordenada = new LinkedList<AlunoTurma>();
		
		for (AlunoTurma at : lista) {
			if (ordenada.size() == 0) {
				ordenada.add(at);
			}
			else {
				int anoNaoOrdenado = Integer.parseInt(at.getTurma().getPeriodo().getAno()), 
				periodoNaoOrdenado = (int) at.getTurma().getPeriodo().getSemestre(), 
				anoOrdenado, periodoOrdenado;
				boolean entrou = false;
				
				int anoAtual = Integer.parseInt(ordenada.get(0).getTurma().getPeriodo().getAno());
				for (int i = 0; i < ordenada.size(); i++) {
					anoOrdenado = Integer.parseInt(ordenada.get(i).getTurma().getPeriodo().getAno()); 
					periodoOrdenado = (int) ordenada.get(i).getTurma().getPeriodo().getSemestre(); 
					if(anoAtual < anoOrdenado){
						anoAtual = anoOrdenado;
					}
					if (anoNaoOrdenado < anoOrdenado) {
						ordenada.add(i, at);
						entrou = true;
						break;
					}
					else if (anoNaoOrdenado == anoOrdenado) {
						if (periodoNaoOrdenado == periodoOrdenado) {
							if (anoAtual == anoOrdenado) {
								ordenada.add(i, at);
								entrou = true;
								break;
							}
						}
						else {
							if (periodoNaoOrdenado > periodoOrdenado) {
								ordenada.add(i + 1, at);
								entrou = true;
								break;
							}
							else {
								ordenada.add(i, at);
								entrou = true;
								break;
							}
						}
					}
					
				}
				if (!entrou) {
					ordenada.add(at);
				}
			}
		}
		return ordenada;
	}
	
	public int geraSemestresCursados (List<AlunoTurma> alunoTurma){
		
		if(alunoTurma.size()==0){
			return 0;
		}
		
		int anoAtual = Integer.parseInt(alunoTurma.get(0).getTurma().getPeriodo().getAno());
		int semestreAtual = (int)(alunoTurma.get(0).getTurma().getPeriodo().getSemestre());
		int semestresCursados = 1;
		
		for(int i = 0; i < alunoTurma.size(); i++) {
			int ano = Integer.parseInt(alunoTurma.get(i).getTurma().getPeriodo().getAno()), 
			semestre = (int) alunoTurma.get(i).getTurma().getPeriodo().getSemestre();
			
			if(ano != anoAtual || semestre != semestreAtual){
				semestresCursados++;
				anoAtual = ano;
				semestreAtual = semestre;

			}
		}
		return semestresCursados;
	}
	
	
	public int geraTrancamentosTotais(List<AlunoTurma> alunoTurma){
		
		ArrayList<AlunoTurma> lista  = new ArrayList<AlunoTurma>(alunoTurma);
		LinkedList<AlunoTurma> listaPeriodo = new LinkedList<AlunoTurma>();
		int trancamentosParciais = 0;
		int trancamentosTotais = 0;
		int anoAtual = Integer.parseInt(alunoTurma.get(0).getTurma().getPeriodo().getAno());
		int semestreAtual = (int)(alunoTurma.get(0).getTurma().getPeriodo().getSemestre());
		
		for (int i = 0; i < lista.size(); i++) {
			int anoLista = Integer.parseInt(lista.get(i).getTurma().getPeriodo().getAno()), 
			semestreLista = (int) lista.get(i).getTurma().getPeriodo().getSemestre();
			if(anoLista == anoAtual && semestreLista == semestreAtual){
				listaPeriodo.add(lista.get(i));
				if(lista.get(i).getSituacao()==Situacao.TRANCADO){
					trancamentosParciais++; 
				}
			}
			else {
				anoAtual = anoLista;
				semestreAtual = semestreLista;
				if(trancamentosParciais == listaPeriodo.size()){
					trancamentosTotais++;
					trancamentosParciais = 0;
					listaPeriodo.clear();
					i--;
				}
			}
		}
		return trancamentosTotais;
	}
	public Boolean isDisciplinaObrigatoria(Disciplina disciplina){
		if(disciplina.getTipo()==Tipo.OBRIGATORIA){
			return true;
		}
		else 
			return false;
	}
	
	public Boolean isDisciplinaOptativa(Disciplina disciplina){
		if(disciplina.getTipo()==Tipo.OPTATIVA){
			return true;
		}
		else 
			return false;
	}
	
	public Boolean isDisciplinaComplementar(Disciplina disciplina){
		if(disciplina.getTipo()==Tipo.COMPLEMENTAR){
			return true;
		}
		else 
			return false;
	}
	
	public void exportarPDF() {
		System.out.println("***********************************geraTabelaHoratio");
		ArrayList<HashMap<String, String>> mapas = new ArrayList<HashMap<String, String>>();
		//Numero - Codigo - Nome da disciplina - Creditos - CargaHoraria - Horarios - Sala
		for (AlunoTurma at : alunoTurmas) {
			HashMap<String, String> mapa = new HashMap<String, String>();
			mapa.put("Numero", at.getTurma().getNumero());
			
			mapa.put("Codigo", at.getTurma().getDisciplina().getCodigo());
			
			mapa.put("Nome", at.getTurma().getDisciplina().getNome());

			String horarios = "";
			for (Horario h : matriculaBean.getHorariosOrdenados(at.getTurma().getHorarios())) {
				horarios += h.toString()+ "\n";
			}
			
			mapa.put("Horarios", horarios);
			
			String salas = "";
			
			for (Sala s : getSalasDoBanco(at.getTurma().getId())) {
				salas += s.getSala() + "\n";
			}
			
			mapa.put("Sala", salas);
			mapa.put("Turma", at.getTurma().getNumero());

			int creditos = at.getTurma().getDisciplina().getCreditos();
			mapa.put("Creditos", String.valueOf(creditos));
			System.out.println(at.getTurma().getNumero());
			mapa.put("Carga Horaria", String.valueOf(creditos*15));
			System.out.println(at.getTurma().getNumero());
			
			mapas.add(mapa);
		}
		
		pdfAction.geraPdf("Horario_Individual.pdf", mapas);
	}
	
	public void exportarHistoricoPDF() {
		ArrayList<HashMap<String, String>> mapas = new ArrayList<HashMap<String, String>>();
		
		// Matricula - Nome do Aluno - Codigo do Curso - Nome do Curso - Numero do Curriculo - Reconhecimento - RG
		HashMap<String, String> mapaCabecalho = new HashMap<String, String>();
		mapaCabecalho.put("Matricula", fachada.getAluno().getMatricula());
		mapaCabecalho.put("Nome", fachada.getUsuario().getNome());
		mapaCabecalho.put("Curso", fachada.getAluno().getCurriculo().getCurso().getCodigo());
		mapaCabecalho.put("Nome", fachada.getAluno().getCurriculo().getCurso().getNome());
		mapaCabecalho.put("Curriculo", fachada.getAluno().getCurriculo().getNumero());
		mapaCabecalho.put("Reconhecimento", fachada.getAluno().getCurriculo().getCurso().getCur_ato_criacao());
		mapaCabecalho.put("RG", fachada.getUsuario().getRg());
		mapas.add(mapaCabecalho);
		
		//CODIGO - NOME DA DISCIPLINA - CR - CH.- PERIODO - MEDIA -SITUACAO 
		//Disciplinas Obrigatorias
		for (AlunoTurma at : alunoTurmas) {
			if(at.getTurma().getDisciplina().getTipo()==Tipo.OBRIGATORIA){
				HashMap<String, String> mapaDisciplinasObrigatorias = new HashMap<String, String>();
				mapaDisciplinasObrigatorias.put("Codigo", at.getTurma().getNumero());
				mapaDisciplinasObrigatorias.put("Disciplina", at.getTurma().getDisciplina().getCodigo());
				int creditos = at.getTurma().getDisciplina().getCreditos();
				mapaDisciplinasObrigatorias.put("Creditos", String.valueOf(creditos));
				mapaDisciplinasObrigatorias.put("Carga Horaria", String.valueOf(creditos*15));			
				mapaDisciplinasObrigatorias.put("Periodo", at.getTurma().getPeriodo().getAno().concat(" "+ at.getTurma().getPeriodo().getSemestre()));
				mapaDisciplinasObrigatorias.put("Media",turmasMatriculadasBean.geraMedia(at)+"");
				mapaDisciplinasObrigatorias.put("Situacao", at.getSituacao()+"");
						
				mapas.add(mapaDisciplinasObrigatorias);
			}
		}
		
		//Disciplinas Optativas
		for (AlunoTurma at : alunoTurmas) {
			if(at.getTurma().getDisciplina().getTipo()==Tipo.OPTATIVA){
				HashMap<String, String> mapaDisciplinasOptativas = new HashMap<String, String>();
				mapaDisciplinasOptativas.put("Codigo", at.getTurma().getNumero());
				mapaDisciplinasOptativas.put("Disciplina", at.getTurma().getDisciplina().getCodigo());
				int creditos = at.getTurma().getDisciplina().getCreditos();
				mapaDisciplinasOptativas.put("Creditos", String.valueOf(creditos));
				mapaDisciplinasOptativas.put("Carga Horaria", String.valueOf(creditos*15));			
				mapaDisciplinasOptativas.put("Periodo", at.getTurma().getPeriodo().getAno().concat(" "+ at.getTurma().getPeriodo().getSemestre()));
				mapaDisciplinasOptativas.put("Media",turmasMatriculadasBean.geraMedia(at)+"");
				mapaDisciplinasOptativas.put("Situacao", at.getSituacao()+"");
						
				mapas.add(mapaDisciplinasOptativas);
			}
		}
		
		//Disciplinas Complementares
		for (AlunoTurma at : alunoTurmas) {
			if(at.getTurma().getDisciplina().getTipo()==Tipo.COMPLEMENTAR){
				HashMap<String, String> mapaDisciplinasComplementares = new HashMap<String, String>();
				mapaDisciplinasComplementares.put("Codigo", at.getTurma().getNumero());
				mapaDisciplinasComplementares.put("Disciplina", at.getTurma().getDisciplina().getCodigo());
				int creditos = at.getTurma().getDisciplina().getCreditos();
				mapaDisciplinasComplementares.put("Creditos", String.valueOf(creditos));
				mapaDisciplinasComplementares.put("Carga Horaria", String.valueOf(creditos*15));			
				mapaDisciplinasComplementares.put("Periodo", at.getTurma().getPeriodo().getAno().concat(" "+ at.getTurma().getPeriodo().getSemestre()));
				mapaDisciplinasComplementares.put("Media",turmasMatriculadasBean.geraMedia(at)+"");
				mapaDisciplinasComplementares.put("Situacao", at.getSituacao()+"");
						
				mapas.add(mapaDisciplinasComplementares);
			}
		}
		//Integralizacoes Disciplina Obrigatoria
		HashMap<String, String> mapaIntegralizacaoObrigatoria = new HashMap<String, String>();
		mapaIntegralizacaoObrigatoria.put("Carga Horaria Minima", cargaHoraria(fachada.getAluno().getCurriculo().getMinimoCreditosCurriculo())+"");
		mapaIntegralizacaoObrigatoria.put("Integralizada", cargaHoraria(geraCreditosIntegralizados())+"");
		mapaIntegralizacaoObrigatoria.put("Creditos Minimo", fachada.getAluno().getCurriculo().getMinimoCreditosCurriculo()+"");
		mapaIntegralizacaoObrigatoria.put("Integralizado", fachada.getAluno().getCurriculo().getMinimoCreditosCurriculo()+"");
		mapaIntegralizacaoObrigatoria.put("Disciplinas Minimo", fachada.getAluno().getCurriculo().getMinimoDisciplinas()+"");
		mapaIntegralizacaoObrigatoria.put("Integralizado", alunoTurmas.size()+"");
		mapas.add(mapaIntegralizacaoObrigatoria);
		
		//Integralizacoes Disciplina Optativa
		HashMap<String, String> mapaIntegralizacaoOptativa = new HashMap<String, String>();
		mapaIntegralizacaoOptativa.put("Carga Horaria Minima", cargaHoraria(fachada.getAluno().getCurriculo().getMinimoCreditosCurriculo())+"");
		mapaIntegralizacaoOptativa.put("Integralizada", cargaHoraria(geraCreditosIntegralizados())+"");
		mapaIntegralizacaoOptativa.put("Creditos Minimo", fachada.getAluno().getCurriculo().getMinimoCreditosCurriculo()+"");
		mapaIntegralizacaoOptativa.put("Integralizado", fachada.getAluno().getCurriculo().getMinimoCreditosCurriculo()+"");
		mapaIntegralizacaoOptativa.put("Disciplinas Minimo", fachada.getAluno().getCurriculo().getMinimoDisciplinas()+"");
		mapaIntegralizacaoOptativa.put("Integralizado", alunoTurmas.size()+"");
		mapas.add(mapaIntegralizacaoOptativa);
		
		//Integralizacoes Disciplina Complementar
		HashMap<String, String> mapaIntegralizacaoComplementar = new HashMap<String, String>();
		mapaIntegralizacaoComplementar.put("Carga Horaria Minima", cargaHoraria(fachada.getAluno().getCurriculo().getMinimoCreditosCurriculo())+"");
		mapaIntegralizacaoComplementar.put("Integralizada", cargaHoraria(geraCreditosIntegralizados())+"");
		mapaIntegralizacaoComplementar.put("Creditos Minimo", fachada.getAluno().getCurriculo().getMinimoCreditosCurriculo()+"");
		mapaIntegralizacaoComplementar.put("Integralizado", fachada.getAluno().getCurriculo().getMinimoCreditosCurriculo()+"");
		mapaIntegralizacaoComplementar.put("Disciplinas Minimo", fachada.getAluno().getCurriculo().getMinimoDisciplinas()+"");
		mapaIntegralizacaoComplementar.put("Integralizado", alunoTurmas.size()+"");
		mapas.add(mapaIntegralizacaoComplementar);
		
		//Integralizacao Total
		HashMap<String, String> mapaIntegralizacaoTotal = new HashMap<String, String>();
		mapaIntegralizacaoTotal.put("Carga Horaria Minima", cargaHoraria(fachada.getAluno().getCurriculo().getMinimoCreditosCurriculo())+"");
		mapaIntegralizacaoTotal.put("Integralizada", cargaHoraria(geraCreditosIntegralizados())+"");
		mapaIntegralizacaoTotal.put("Creditos Minimo", fachada.getAluno().getCurriculo().getMinimoCreditosCurriculo()+"");
		mapaIntegralizacaoTotal.put("Integralizado", fachada.getAluno().getCurriculo().getMinimoCreditosCurriculo()+"");
		mapaIntegralizacaoTotal.put("Disciplinas Minimo", fachada.getAluno().getCurriculo().getMinimoDisciplinas()+"");
		mapaIntegralizacaoTotal.put("Integralizado", alunoTurmas.size()+"");
		mapas.add(mapaIntegralizacaoTotal);

		//Semestres cursados
		HashMap<String, String> mapaSemestresCursados = new HashMap<String, String>();
		mapaSemestresCursados.put("Cursados", geraSemestresCursados(getDisciplinasOrdenadas(alunoDAO.getAlunos(usuarioBean.getUsuario().getAluno().getMatricula())))+"");
		mapaSemestresCursados.put("Minimo", fachada.getAluno().getCurriculo().getMinimoSemestres()+"");
		mapaSemestresCursados.put("Maximo", fachada.getAluno().getCurriculo().getMaximoSemestres()+"");
		mapaSemestresCursados.put("Ativos", geraSemestresCursados(getDisciplinasOrdenadas(alunoDAO.getAlunos(usuarioBean.getUsuario().getAluno().getMatricula())))+"");
		mapas.add(mapaSemestresCursados);
		
		//Trancamentos Totais
		HashMap<String, String> mapaTrancamentosTotais = new HashMap<String, String>();
		mapaTrancamentosTotais.put("Trancamentos", geraTrancamentosTotais(getDisciplinasOrdenadas(alunoDAO.getAlunos(usuarioBean.getUsuario().getAluno().getMatricula())))+"");
		mapaTrancamentosTotais.put("Maximo", fachada.getAluno().getCurriculo().getTrancamentosTotais()+"");
		mapas.add(mapaTrancamentosTotais);
		
		//Matriculas Institucionais
		HashMap<String, String> mapaMatriculasInstitucionais = new HashMap<String, String>();
		mapaMatriculasInstitucionais.put("Matriculas", geraTrancamentosTotais(getDisciplinasOrdenadas(alunoDAO.getAlunos(usuarioBean.getUsuario().getAluno().getMatricula())))+"");
		mapaMatriculasInstitucionais.put("Maximo", fachada.getAluno().getCurriculo().getMatriculaInstitucional()+"");
		mapas.add(mapaMatriculasInstitucionais);

		//Trancamentos Parciais
		HashMap<String, String> mapaTrancamentosParciais = new HashMap<String, String>();
		mapaTrancamentosParciais.put("Trancamentos", geraTrancamentosParciais()+"");
		mapaTrancamentosParciais.put("Maximo", fachada.getAluno().getCurriculo().getMinimoCreditos()+"");
		mapas.add(mapaTrancamentosParciais);
		
		//Creditos Matriculados
		HashMap<String, String> mapaCreditosMatriculados = new HashMap<String, String>();
		mapaCreditosMatriculados.put("Matriculados", geraCreditosIntegralizados()+"");
		mapaCreditosMatriculados.put("Minimo", fachada.getAluno().getCurriculo().getMinimoCreditos()+"");
		mapaCreditosMatriculados.put("Maximo", fachada.getAluno().getCurriculo().getMaximoCreditos()+"");
		mapas.add(mapaCreditosMatriculados);
		
		//Situacao Academica
		HashMap<String, String> mapaSituacaoAcademica = new HashMap<String, String>();
		mapaSituacaoAcademica.put("Situacao", fachada.getAluno().getSituacaoAcademica()+"");
		mapaSituacaoAcademica.put("CRE", geraCRE()+"");
		if(fachada.getAluno().getSituacaoAcademica()==SituacaoAcademica.GRADUADO){
			mapaSituacaoAcademica.put("Ano de Conclusao", getDisciplinasOrdenadas(alunoDAO.getAlunos(usuarioBean.getUsuario().getAluno().getMatricula())).get(0).getTurma().getPeriodo().getAno().
					  concat(" "+getDisciplinasOrdenadas(alunoDAO.getAlunos(usuarioBean.getUsuario().getAluno().getMatricula())).get(0).getTurma().getPeriodo().getSemestre()));
		}
		mapaSituacaoAcademica.put("Forma de Ingresso", fachada.getAluno().getFormaIngresso()+"");
		mapaSituacaoAcademica.put("Ano Ingresso", getDisciplinasOrdenadas(alunoDAO.getAlunos(usuarioBean.getUsuario().getAluno().getMatricula())).get(0).getTurma().getPeriodo().getAno().
								  concat(" "+getDisciplinasOrdenadas(alunoDAO.getAlunos(usuarioBean.getUsuario().getAluno().getMatricula())).get(0).getTurma().getPeriodo().getSemestre()));
		mapas.add(mapaSituacaoAcademica);
		
		//Notas do Vestibular
		if(fachada.getAluno().getFormaIngresso()==FormaIngresso.VESTIBULAR){
			
		}
		
		//pdfAction.geraPdfHistorico("Historico Escolar", mapas);
	}

}
