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
import org.ufpb.s2dg.entity.Sala;
import org.ufpb.s2dg.entity.Turma;

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
	
	public int cargaHoraria(int creditos) {
		return creditos*15;
	}
	
	public List<Sala> getSalasDoBanco(long id) {
		return fachada.getSalaDoBanco(id);
	}
	
	public void exportarPDF() {
		ArrayList<HashMap<String, String>> mapas = new ArrayList<HashMap<String, String>>();
		//Numero - Codigo - Nome da disciplina - Creditos - CargaHoraria - Horarios - Sala
		for (AlunoTurma at : alunoTurmas) {
			HashMap<String, String> mapa = new HashMap<String, String>();
			mapa.put("Numero", at.getTurma().getNumero());
			mapa.put("Codigo", at.getTurma().getDisciplina().getCodigo());
			mapa.put("Nome", at.getTurma().getDisciplina().getNome());
			mapa.put("Horarios", matriculaBean.getHorariosOrdenados(at.getTurma().getHorarios()).toString());
			mapa.put("Sala", getSalasDoBanco(at.getTurma().getId()).toString());
			int creditos = at.getTurma().getDisciplina().getCreditos();
			mapa.put("Creditos", String.valueOf(creditos));
			mapa.put("Carga Horaria", String.valueOf(creditos*15));
			
			mapas.add(mapa);
		}
		
		pdfAction.geraPdf("Horario Individual", mapas);
	}
}
