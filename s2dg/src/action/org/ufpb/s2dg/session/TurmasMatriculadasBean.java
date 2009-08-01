package org.ufpb.s2dg.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import org.ufpb.s2dg.entity.AlunoTurma.Situacao;

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
	PDFAction pdfAction;
		
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
	

}
