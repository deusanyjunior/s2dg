package org.ufpb.s2dg.session;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.ufpb.s2dg.entity.AlunoTurma;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
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
		
		geraTabelaRelatorioDeNotas(list);
        
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
	
	public void geraTabelaRelatorioDeNotas(List<AlunoTurma> list){
		
        Font f1 = new Font(); f1.setStyle(Font.BOLD); f1.setSize(15);
        Font f2 = new Font(); f2.setStyle(Font.ITALIC); f2.setSize(13);

        PdfPTable table = new PdfPTable(3);
        PdfPCell cell1 = new PdfPCell(new Paragraph("Relatorio de Notas", f1));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Nome", f2));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Faltas", f2));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Media", f2));

        cell1.setBackgroundColor(Color.ORANGE);
        cell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell1.setColspan(7);
        cell1.setBorderWidthBottom(1);
        cell1.setBorderWidthTop(1);

        cell2.setBackgroundColor(Color.LIGHT_GRAY);
        cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        cell3.setBackgroundColor(Color.LIGHT_GRAY);
        cell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        cell4.setBackgroundColor(Color.LIGHT_GRAY);
        cell4.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);

        for(AlunoTurma aluno : list){
            table.addCell(aluno.getAluno().getUsuario().getNome());
            table.addCell(aluno.getFaltas()+"");
            table.addCell(aluno.getMedia()+"");
        }
        
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
