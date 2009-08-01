package org.ufpb.s2dg.session;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;



import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesContext;
import org.ufpb.s2dg.entity.AlunoTurma;
import org.ufpb.s2dg.entity.Avaliacao;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@SuppressWarnings("unused")
@Name("pdfAction")
@AutoCreate
@Scope(ScopeType.PAGE)
public class PDFAction {
	
	//@In(create=true)
	//AlunoTurmaAvaliacoesBean alunoTurmaAvaliacao;
	
	//@In
	//AvaliacoesBean avaliacoesBean;
	
	private Document doc;
	
	public PDFAction(){
		
	}

	public void geraPdf(String nome, List<AlunoTurma> list){
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
		
		addParagrafo("Teste");
		
		//geraTabelaRelatorioDeNotas(list, avaliacoesBean.getAvaliacoes());
        
		//geraCabecalho();
		
		addParagrafo("Teste");
		
        this.doc.close(); 
	}
	
	public void geraPdf(String nome, ArrayList<HashMap<String, String>> informacoes){
		System.out.println("***********************************geraPdf");
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
		
        Font f1 = new Font(); f1.setStyle(Font.BOLD); f1.setSize(15);
        Font f2 = new Font(); f2.setStyle(Font.ITALIC); f2.setSize(13);
        
        int size = 3 ;
        
        System.out.println("***********************************"+size);

        PdfPTable table = new PdfPTable(size);
        
        PdfPCell [] cell = new PdfPCell[size];
        
        cell[0] = new PdfPCell(new Paragraph("Relatorio de Notas", f1));
        cell[1] = new PdfPCell(new Paragraph("Nome", f2));
        cell[2] = new PdfPCell(new Paragraph("Faltas", f2));
        
        cell[0].setBackgroundColor(Color.ORANGE);
        cell[0].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell[0].setColspan(7);
        cell[0].setBorderWidthBottom(1);
        cell[0].setBorderWidthTop(1);      
        
        cell[1].setBackgroundColor(Color.LIGHT_GRAY);
        cell[1].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        cell[2].setBackgroundColor(Color.LIGHT_GRAY);
        cell[2].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        /*
        for(int i = 3; i < avaliacoes.size(); i++){
        	Avaliacao avaliacao = avaliacoes.get(i-3);
        	
        	cell[i] = new PdfPCell(new Paragraph(avaliacao.getNome(), f2));
        	cell[i].setBackgroundColor(Color.LIGHT_GRAY);
            cell[i].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        }*/
        /*
        cell[size - 1] = new PdfPCell(new Paragraph("Media", f2));

        cell[size - 1].setBackgroundColor(Color.LIGHT_GRAY);
        cell[size - 1].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        */
        
        for(int i = 0; i < cell.length; i++){
        	System.out.println("***********************************"+i);
        	table.addCell(cell[i]);
        }

        /*
        for(AlunoTurma aluno : list){
            table.addCell(aluno.getAluno().getUsuario().getNome());
            table.addCell(aluno.getFaltas()+"");
            
            for(Avaliacao avaliacao : avaliacoes){
            	//table.addCell(alunoTurmaAvaliacao.getAlunoTurmaAvaliacao(aluno, avaliacao).getNota()+"");
            }
            
            table.addCell(aluno.getMedia()+"");
        }
        */
        try 
        {
            this.doc.add(table);
        } 
        catch (DocumentException ex) {ex.printStackTrace();}
        
    }
	
	public void geraTabela(){
		
	}
	
	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

}
