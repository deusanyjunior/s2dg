package org.ufpb.s2dg.session;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.AlunoTurma;
import org.ufpb.s2dg.entity.Avaliacao;
import org.ufpb.s2dg.entity.Disciplina.Tipo;
import org.ufpb.s2dg.session.persistence.AlunoDAO;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Name("pdfAction")
@AutoCreate
@Scope(ScopeType.PAGE)
public class PDFAction {
	
	@In(create=true)
	AlunoTurmaAvaliacoesBean alunoTurmaAvaliacoesBean;
	
	@In
    FacesContext facesContext;
	

	@In(create=true)
	ReportGenerator reportGenerator;
	
	@In
	AlunoDAO alunoDAO;
	
	@In
	UsuarioBean usuarioBean;
	
	@In
	TurmasMatriculadasBean turmasMatriculadasBean;
	
	private Document doc;
	
	public PDFAction(){
		
	}

	public void geraPdfDiario(String nome, List<AlunoTurma> list, List<Avaliacao> avaliacoes){		
		Rectangle pageSize = new Rectangle(PageSize.A4);
 
		pageSize.setBackgroundColor(Color.WHITE);
		
		doc = new Document(pageSize);
		
		try {
			PdfWriter.getInstance(doc, new FileOutputStream(nome));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		this.doc.open();
				
		geraTabelaRelatorioDeNotas(list, avaliacoes);
        
		//geraCabecalho();
		
        this.doc.close(); 
        
        try {
        	reportGenerator.generate(nome);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Diário de Notas impresso com sucesso!",null));
		} catch (IOException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Não foi possível imprimir o documento!",null));
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	public void geraPdf(String nome, ArrayList<HashMap<String, String>> informacoes){
		Rectangle pageSize = new Rectangle(PageSize.A4);
		 
		pageSize.setBackgroundColor(Color.WHITE);
		
		doc = new Document(pageSize);
		
		try {
			PdfWriter.getInstance(doc, new FileOutputStream(nome));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		this.doc.open();
		
		geraTabelaHoratio(informacoes);
        
		//geraCabecalho();

        this.doc.close(); 

        try {
        	reportGenerator.generate(nome);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Horário Individual impresso com sucesso!",null));
		} catch (IOException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Não foi possível imprimir o documento!",null));
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
        
	}
	
	public void geraPdfHistorico(String nome, ArrayList<HashMap<String, String>> informacoes){
		System.out.println("¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬SIZE"+informacoes.size());
		
		Rectangle pageSize = new Rectangle(PageSize.A4);
		 
		pageSize.setBackgroundColor(Color.WHITE);
		
		doc = new Document(pageSize);
		
		try {
			PdfWriter.getInstance(doc, new FileOutputStream(nome));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		this.doc.open();
		
		geraTabelaHistorico(informacoes);
		
        
		//geraCabecalho();

        this.doc.close(); 
        
        try {
        	reportGenerator.generate(nome);
        	facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Horário Individual impresso com sucesso!",null));
		} catch (IOException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Não foi possível imprimir o documento!",null));
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}
	
	public void addParagrafo(Paragraph p){
		try
        {
            this.doc.add(p);
        }
        catch(DocumentException e)
        {
            e.printStackTrace();
        }
	}
	
	public void addParagrafoCentralizado(Paragraph p){
		try
        {
			p.setAlignment(PdfPCell.ALIGN_CENTER);
            this.doc.add(p);
        }
        catch(DocumentException e)
        {
            e.printStackTrace();
        }
	}
	
	public void addParagrafo(String text){
        try
        {
            this.doc.add(new Paragraph(text));
        }
        catch(DocumentException e)
        {
            e.printStackTrace();
        }
    }
	
	public void addImage(String name){
        Image image = null;
        try 
        {        
            image = Image.getInstance(name);
                           
            this.doc.add(image);
        }
        catch(MalformedURLException e) 
        {
            e.printStackTrace();
        } 
        catch(BadElementException e) 
        {
            e.printStackTrace();
        } 
        catch(IOException e) 
        {
            e.printStackTrace();
        }
        catch(DocumentException e)
        {
            e.printStackTrace();
        }
    }
	
	public void geraCabecalho(){
		//ServletContext servletContext = (ServletContext)facesContext.getExternalContext().getContext();
		//servletContext.getRealPath("/img");
		
		Image image = null;
		try {
			image = Image.getInstance("C:\\ambiente\\workspace\\s2dg\\WebContent\\img\\logo_ufpb2.png");
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		PdfPTable table = new PdfPTable(2);
		table.addCell(image);
		Paragraph cabecalho = new Paragraph("Formulario de Cadastramento Simples\n",FontFactory.getFont("ARIAL",12));
		
		table.addCell(cabecalho);
		
		try 
        {
            this.doc.add(table);
        } 
        catch (DocumentException ex) {ex.printStackTrace();}
	}
	
	public void geraTabelaHoratio(ArrayList<HashMap<String, String>> informacoes){				
		Font f1 = new Font(); f1.setStyle(Font.BOLD); f1.setSize(12);
        Font f2 = new Font(); f2.setStyle(Font.ITALIC); f2.setSize(8);
		
        PdfPTable table = new PdfPTable(7);
        PdfPCell cell0 = new PdfPCell(new Paragraph("Horário Individual", f1));
        PdfPCell cell1 = new PdfPCell(new Paragraph("Horário", f2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Nome", f2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Turma", f2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Código", f2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Sala", f2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Créditos", f2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("Carga Horária", f2));

        cell0.setBackgroundColor(Color.BLUE);
        cell0.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell0.setColspan(7);
        cell0.setBorderWidthBottom(1);
        cell0.setBorderWidthTop(1);
        
        cell1.setBackgroundColor(Color.LIGHT_GRAY);
        cell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        cell2.setBackgroundColor(Color.LIGHT_GRAY);
        cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        cell3.setBackgroundColor(Color.LIGHT_GRAY);
        cell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        cell4.setBackgroundColor(Color.LIGHT_GRAY);
        cell4.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        cell5.setBackgroundColor(Color.LIGHT_GRAY);
        cell5.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        cell6.setBackgroundColor(Color.LIGHT_GRAY);
        cell6.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        cell7.setBackgroundColor(Color.LIGHT_GRAY);
        cell7.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        table.addCell(cell0);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        
        for(HashMap<String, String> hash : informacoes){
        	
        	cell1 = new PdfPCell(new Paragraph(hash.get("Horarios"), f2));
            cell2 = new PdfPCell(new Paragraph(hash.get("Nome"), f2));
            cell3 = new PdfPCell(new Paragraph(hash.get("Turma"), f2));
            cell4 = new PdfPCell(new Paragraph(hash.get("Codigo"), f2));
            cell5 = new PdfPCell(new Paragraph(hash.get("Sala"), f2));
            cell6 = new PdfPCell(new Paragraph(hash.get("Creditos"), f2));
            cell7 = new PdfPCell(new Paragraph(hash.get("Carga Horaria"), f2));
            
            cell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

            cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            
            cell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            
            cell4.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            
            cell5.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            
            cell6.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            
            cell7.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            
        	table.addCell(cell1);
        	table.addCell(cell2);
        	table.addCell(cell3);
        	table.addCell(cell4);
        	table.addCell(cell5);
        	table.addCell(cell6);
        	table.addCell(cell7);
        }
        
        try 
        {
            this.doc.add(table);
        } 
        catch (DocumentException ex) {ex.printStackTrace();}
	}
	
	public void geraTabelaRelatorioDeNotas(List<AlunoTurma> list, List<Avaliacao> avaliacoes){
		
        Font f1 = new Font(); f1.setStyle(Font.BOLD); f1.setSize(12);
        Font f2 = new Font(); f2.setStyle(Font.ITALIC); f2.setSize(8);
        
        int size = 3;
        if (avaliacoes != null)
         size = 3 + avaliacoes.size();
        
        System.out.println("***********************************"+size);

        PdfPTable titulo = new PdfPTable(1);
        PdfPTable table = new PdfPTable(size);
        
        PdfPCell [] cell = new PdfPCell[size+1];
        
        cell[0] = new PdfPCell(new Paragraph("Relatorio de Notas", f1));
        cell[1] = new PdfPCell(new Paragraph("Aluno", f2));
        cell[2] = new PdfPCell(new Paragraph("Faltas", f2));
        
        cell[0].setBackgroundColor(Color.BLUE);
        cell[0].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell[0].setColspan(7);
        cell[0].setBorderWidthBottom(1);
        cell[0].setBorderWidthTop(1);    
        
        cell[1].setBackgroundColor(Color.LIGHT_GRAY);
        cell[1].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        cell[2].setBackgroundColor(Color.LIGHT_GRAY);
        cell[2].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        
        for(int i = 0; i < size - 3; i++){
        	Avaliacao avaliacao = avaliacoes.get(i);
        	
        	cell[i+3] = new PdfPCell(new Paragraph(avaliacao.getNome(), f2));
        	cell[i+3].setBackgroundColor(Color.LIGHT_GRAY);
            cell[i+3].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            
            System.out.println("¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬*****i: "+i);
        }
        
        cell[cell.length - 1] = new PdfPCell(new Paragraph("Media", f2));

        cell[cell.length - 1].setBackgroundColor(Color.LIGHT_GRAY);
        cell[cell.length - 1].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        titulo.addCell(cell[0]);
        for(int i = 1; i < cell.length; i++){
        	System.out.println("¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬"+i);
        	table.addCell(cell[i]);
        }

        
        for(AlunoTurma aluno : list){
        	cell[1] = new PdfPCell(new Paragraph(aluno.getAluno().getUsuario().getNome(), f2));
        	cell[2] = new PdfPCell(new Paragraph(aluno.getFaltas()+"", f2));
        	
        	cell[1].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        	cell[2].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        	
            table.addCell(cell[1]);
            table.addCell(cell[2]);
            
            if (avaliacoes != null) {
            
            for(Avaliacao avaliacao : avaliacoes){
            	PdfPCell cell0 = new PdfPCell(new Paragraph(alunoTurmaAvaliacoesBean.getAlunoTurmaAvaliacao(aluno, avaliacao).getNota()+"", f2));
            	cell0.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            	table.addCell(cell0);
            }
            
            }
            
            cell[3] = new PdfPCell(new Paragraph(aluno.getMedia()+"", f2));
            cell[3].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            
            table.addCell(cell[3]);
        }
        
        try 
        {
        	this.doc.add(titulo);
            this.doc.add(table);
        } 
        catch (DocumentException ex) {ex.printStackTrace();}
        
    }
	
	public void geraTabelaHistorico(ArrayList<HashMap<String, String>> informacoes){
		Font f1 = new Font(); f1.setStyle(Font.TIMES_ROMAN); f1.setSize(8);
        Font f2 = new Font(); f2.setStyle(Font.TIMES_ROMAN); f2.setSize(8);
		
        addParagrafoCentralizado(new Paragraph("H I S T Ó R I C O   E S C O L A R\n\n", f2));
        
        addParagrafo(new Paragraph("ALUNO: " + informacoes.get(0).get("Matricula") + "--" + informacoes.get(0).get("Nome"), f2));
        addParagrafo(new Paragraph("CURSO: "+ informacoes.get(0).get("Curso") + "--" + informacoes.get(0).get("NomeCurso"), f2));
        addParagrafo(new Paragraph("CURRÍCULO: "+ informacoes.get(0).get("Curriculo"), f2));
        addParagrafo(new Paragraph("RECONHECIMENTO: "+ informacoes.get(0).get("Reconhecimento"), f2));
        addParagrafo(new Paragraph("RG: "+ informacoes.get(0).get("RG"), f2));
        addParagrafo("\n");
        
        List<AlunoTurma> aluno = turmasMatriculadasBean.getDisciplinasOrdenadas(alunoDAO.getAlunos(usuarioBean.getUsuario().getAluno().getMatricula()));
                
        int obrigatorias = 0;
        int complementar = 0;
        int optativa = 0;
        
        PdfPTable table = new PdfPTable(7);
        PdfPCell cell0 = new PdfPCell(new Paragraph("Disciplicas Obrigatorias", f1));
        PdfPCell cell = new PdfPCell(new Paragraph("Codigo", f2));
        PdfPCell cell1 = new PdfPCell(new Paragraph("Disciplina", f2));
        PdfPCell cell2 = new PdfPCell(new Paragraph("CR", f2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("CH", f2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Período", f2));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Média", f2));
        PdfPCell cell6 = new PdfPCell(new Paragraph("Situação", f2));
        
        cell0.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell0.setColspan(7);
        
        cell0.setBorderColor(Color.WHITE);
        cell.setBorderColor(Color.WHITE);
        cell1.setBorderColor(Color.WHITE);
        cell2.setBorderColor(Color.WHITE);
        cell3.setBorderColor(Color.WHITE);
        cell4.setBorderColor(Color.WHITE);
        cell5.setBorderColor(Color.WHITE);
        cell6.setBorderColor(Color.WHITE);        
        
        table.addCell(cell0);
        table.addCell(cell);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        
        for(AlunoTurma at : aluno){
        	if(at.getTurma().getDisciplina().getTipo() == Tipo.OBRIGATORIA){
        		obrigatorias++;
        	}
        	else if(at.getTurma().getDisciplina().getTipo() == Tipo.COMPLEMENTAR){
        		complementar++;
        	}
        	else{
        		optativa++;
        	}
        }
        
        for(int i = 0; i < obrigatorias; i++){        
    		cell = new PdfPCell(new Paragraph(informacoes.get(i+1).get("Codigo"), f2));
    		cell1 = new PdfPCell(new Paragraph(informacoes.get(i+1).get("Disciplina"), f2));
            cell2 = new PdfPCell(new Paragraph(informacoes.get(i+1).get("Creditos"), f2));
            cell3 = new PdfPCell(new Paragraph(informacoes.get(i+1).get("Carga Horaria"), f2));
            cell4 = new PdfPCell(new Paragraph(informacoes.get(i+1).get("Periodo"), f2));
            cell5 = new PdfPCell(new Paragraph(informacoes.get(i+1).get("Media"), f2));
            cell6 = new PdfPCell(new Paragraph(informacoes.get(i+1).get("Situacao"), f2));
            
            cell.setBorderColor(Color.WHITE);
            cell1.setBorderColor(Color.WHITE);
            cell2.setBorderColor(Color.WHITE);
            cell3.setBorderColor(Color.WHITE);
            cell4.setBorderColor(Color.WHITE);
            cell5.setBorderColor(Color.WHITE);
            cell6.setBorderColor(Color.WHITE);       
            
            table.addCell(cell);
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
        	
        }                
                
        PdfPTable table1 = new PdfPTable(7);
        cell0 = new PdfPCell(new Paragraph("Disciplicas Optativas", f1));  
        cell = new PdfPCell(new Paragraph("Codigo", f2));
        cell1 = new PdfPCell(new Paragraph("Disciplina", f2));
        cell2 = new PdfPCell(new Paragraph("CR", f2));
        cell3 = new PdfPCell(new Paragraph("CH", f2));
        cell4 = new PdfPCell(new Paragraph("Período", f2));
        cell5 = new PdfPCell(new Paragraph("Média", f2));
        cell6 = new PdfPCell(new Paragraph("Situação", f2));
        
        cell0.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell0.setColspan(7);
        
        cell0.setBorderColor(Color.WHITE);
        cell.setBorderColor(Color.WHITE);
        cell1.setBorderColor(Color.WHITE);
        cell2.setBorderColor(Color.WHITE);
        cell3.setBorderColor(Color.WHITE);
        cell4.setBorderColor(Color.WHITE);
        cell5.setBorderColor(Color.WHITE);
        cell6.setBorderColor(Color.WHITE);  
                
        table1.addCell(cell0);
        table1.addCell(cell);
        table1.addCell(cell1);
        table1.addCell(cell2);
        table1.addCell(cell3);
        table1.addCell(cell4);
        table1.addCell(cell5);
        table1.addCell(cell6);
   
        for(int i = 0; i < optativa; i++){        
    		cell = new PdfPCell(new Paragraph(informacoes.get(i+1+obrigatorias).get("Codigo"), f2));
    		cell1 = new PdfPCell(new Paragraph(informacoes.get(i+1+obrigatorias).get("Disciplina"), f2));
            cell2 = new PdfPCell(new Paragraph(informacoes.get(i+1+obrigatorias).get("Creditos"), f2));
            cell3 = new PdfPCell(new Paragraph(informacoes.get(i+1+obrigatorias).get("Carga Horaria"), f2));
            cell4 = new PdfPCell(new Paragraph(informacoes.get(i+1+obrigatorias).get("Periodo"), f2));
            cell5 = new PdfPCell(new Paragraph(informacoes.get(i+1+obrigatorias).get("Media"), f2));
            cell6 = new PdfPCell(new Paragraph(informacoes.get(i+1+obrigatorias).get("Situacao"), f2));
            
            cell.setBorderColor(Color.WHITE);
            cell1.setBorderColor(Color.WHITE);
            cell2.setBorderColor(Color.WHITE);
            cell3.setBorderColor(Color.WHITE);
            cell4.setBorderColor(Color.WHITE);
            cell5.setBorderColor(Color.WHITE);
            cell6.setBorderColor(Color.WHITE);       
            
            table1.addCell(cell);
            table1.addCell(cell1);
            table1.addCell(cell2);
            table1.addCell(cell3);
            table1.addCell(cell4);
            table1.addCell(cell5);
            table1.addCell(cell6);
        }      
        
        
        PdfPTable table2 = new PdfPTable(7);
        cell0 = new PdfPCell(new Paragraph("Disciplicas Complementares", f1));   
        cell = new PdfPCell(new Paragraph("Codigo", f2));
        cell1 = new PdfPCell(new Paragraph("Disciplina", f2));
        cell2 = new PdfPCell(new Paragraph("CR", f2));
        cell3 = new PdfPCell(new Paragraph("CH", f2));
        cell4 = new PdfPCell(new Paragraph("Período", f2));
        cell5 = new PdfPCell(new Paragraph("Média", f2));
        cell6 = new PdfPCell(new Paragraph("Situação", f2));
        
        cell0.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell0.setColspan(7);
        
        cell0.setBorderColor(Color.WHITE);
        cell.setBorderColor(Color.WHITE);
        cell1.setBorderColor(Color.WHITE);
        cell2.setBorderColor(Color.WHITE);
        cell3.setBorderColor(Color.WHITE);
        cell4.setBorderColor(Color.WHITE);
        cell5.setBorderColor(Color.WHITE);
        cell6.setBorderColor(Color.WHITE);  
                
        table2.addCell(cell0);
        table2.addCell(cell);
        table2.addCell(cell1);
        table2.addCell(cell2);
        table2.addCell(cell3);
        table2.addCell(cell4);
        table2.addCell(cell5);
        table2.addCell(cell6);        
        
        for(int i = 0; i < complementar; i++){        
    		cell = new PdfPCell(new Paragraph(informacoes.get(i+1+obrigatorias+optativa).get("Codigo"), f2));
    		cell1 = new PdfPCell(new Paragraph(informacoes.get(i+1+obrigatorias+optativa).get("Disciplina"), f2));
            cell2 = new PdfPCell(new Paragraph(informacoes.get(i+1+obrigatorias+optativa).get("Creditos"), f2));
            cell3 = new PdfPCell(new Paragraph(informacoes.get(i+1+obrigatorias+optativa).get("Carga Horaria"), f2));
            cell4 = new PdfPCell(new Paragraph(informacoes.get(i+1+obrigatorias+optativa).get("Periodo"), f2));
            cell5 = new PdfPCell(new Paragraph(informacoes.get(i+1+obrigatorias+optativa).get("Media"), f2));
            cell6 = new PdfPCell(new Paragraph(informacoes.get(i+1+obrigatorias+optativa).get("Situacao"), f2));
            
            cell.setBorderColor(Color.WHITE);
            cell1.setBorderColor(Color.WHITE);
            cell2.setBorderColor(Color.WHITE);
            cell3.setBorderColor(Color.WHITE);
            cell4.setBorderColor(Color.WHITE);
            cell5.setBorderColor(Color.WHITE);
            cell6.setBorderColor(Color.WHITE);       
            
            table2.addCell(cell);
            table2.addCell(cell1);
            table2.addCell(cell2);
            table2.addCell(cell3);
            table2.addCell(cell4);
            table2.addCell(cell5);
            table2.addCell(cell6);
        }   
                
        PdfPTable table3 = new PdfPTable(6);
       
        cell0 = new PdfPCell(new Paragraph("Dados inerentes a integralizacao curricular", f1));   
        cell1 = new PdfPCell(new Paragraph("Integralização Curricular", f2));
        cell2 = new PdfPCell(new Paragraph("Mínimo", f2));
        cell3 = new PdfPCell(new Paragraph("Integ.", f2));
        cell4 = new PdfPCell(new Paragraph("Mínimo", f2));
        cell5 = new PdfPCell(new Paragraph("Integr.", f2));
        cell6 = new PdfPCell(new Paragraph("Mínimo", f2));
        PdfPCell cell7 = new PdfPCell(new Paragraph("Integr.", f2));
        
        cell0.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell0.setColspan(7);
        
        cell0.setBorderColor(Color.WHITE);
        cell1.setBorderColor(Color.WHITE);
        cell2.setBorderColor(Color.WHITE);
        cell3.setBorderColor(Color.WHITE);
        cell4.setBorderColor(Color.WHITE);
        cell5.setBorderColor(Color.WHITE);
        cell6.setBorderColor(Color.WHITE);
        cell7.setBorderColor(Color.WHITE);
                
        table3.addCell(cell0);
        table3.addCell(cell1);
        table3.addCell(cell2);
        table3.addCell(cell3);
        table3.addCell(cell4);
        table3.addCell(cell5);
        table3.addCell(cell6);
        table3.addCell(cell7);
          
        
        try 
        {
        	addParagrafo(new Paragraph("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", f2));
            this.doc.add(table);
            addParagrafo(new Paragraph("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", f2));
            addParagrafo("\n");
            this.doc.add(table1);
            addParagrafo(new Paragraph("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", f2));
            addParagrafo("\n");
            this.doc.add(table2);
            addParagrafo(new Paragraph("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", f2));
            addParagrafo("\n");
            addParagrafo(new Paragraph("==============================================================================================================\n", f2));
            this.doc.add(table3);
        } 
        catch (DocumentException ex) {ex.printStackTrace();}
        
        addParagrafo(new Paragraph("Disciplinas Obrigatorias...... " + informacoes.get(1+obrigatorias+optativa+complementar).get("Carga Horaria Minima") + "   "
        		+ informacoes.get(1+obrigatorias+optativa+complementar).get("Integralizada") + "   "
        		+ informacoes.get(1+obrigatorias+optativa+complementar).get("Creditos Minimo") + "   "
        		+ informacoes.get(1+obrigatorias+optativa+complementar).get("IntegralizadoCredito") + "   "
        		+ informacoes.get(1+obrigatorias+optativa+complementar).get("Disciplinas Minimo") + "   "
        		+ informacoes.get(1+obrigatorias+optativa+complementar).get("IntegralizadoDisciplina") + "   ", f2));
        addParagrafo(new Paragraph("Disciplinas Optativas......... " + informacoes.get(2+obrigatorias+optativa+complementar).get("Carga Horaria Minima"), f2));
        addParagrafo(new Paragraph("Disciplinas Complementares.... " + informacoes.get(2+obrigatorias+optativa+complementar).get("Carga Horaria Minima"), f2));
        addParagrafo(new Paragraph("TOTAIS DO CURRICULO =========> " + informacoes.get(2+obrigatorias+optativa+complementar).get("Carga Horaria Minima"), f2));
        
        addParagrafo(new Paragraph("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", f2));
        
        addParagrafo(new Paragraph("Número de semestres cursados..", f2));
        addParagrafo(new Paragraph("Trancamentos Totais efetuados.", f2));
        addParagrafo(new Paragraph("Matrículas Institucionais ....", f2));
        addParagrafo(new Paragraph("Trancamentos Parciais efetuad.", f2));
        addParagrafo(new Paragraph("Matriculado atualmente em ....", f2));
        
        addParagrafo(new Paragraph("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", f2));
        
        addParagrafo(new Paragraph("Situacao acadêmica............", f2));
        addParagrafo(new Paragraph("Forma de ingresso.............", f2));
        
        addParagrafo(new Paragraph("----------------------------------------------------------------------- PROVAS E NOTAS DO VESTIBULAR -----------------------------------------------------------------------", f2));
        
        addParagrafo(new Paragraph("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", f2));
        
        addParagrafo("\n");
        addParagrafoCentralizado(new Paragraph("A MATRICULA É OBRIGATÓRIA EM TODO PERÍODO LETIVO EVITE SITUAÇÃO DE ABANDONO", f2));
        addParagrafo("\n");
        addParagrafoCentralizado(new Paragraph("HISTÓRICO EMITIDO PARA SIMPLES CONFERÊNCIA. NAO VALE COMO DOCUMENTO OFICIAL", f2));
	}
	
	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

}