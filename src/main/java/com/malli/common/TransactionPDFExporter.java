package com.malli.common;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.malli.model.Transactions;
 
 
public class TransactionPDFExporter {
    private List<Transactions> transactionsList;
     
    public TransactionPDFExporter(List<Transactions> transactionsList) {
        this.transactionsList = transactionsList;
    }
 
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
         
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("S NO", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("TRANSACTION ID", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("DATE", font));
        table.addCell(cell); 
         
        cell.setPhrase(new Phrase("MODE", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("DEPOSITS", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("WITHDRAWALS", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("BALANCE", font));
        table.addCell(cell);
         
          
        
         
    }
     
    private void writeTableData(PdfPTable table) {
    	int i = 1;
        for (Transactions transaction : transactionsList) {
        	if(StringUtils.isNotBlank(transaction.getType())) {
	            table.addCell(String.valueOf(i));
	            table.addCell(String.valueOf(transaction.getId()));
	            table.addCell(transaction.getCreatedOn().toString());
	            table.addCell(transaction.getMethod());
	            if(transaction.getType().equalsIgnoreCase("Deposite")) {
	            	table.addCell(transaction.getAmount().toString());
	            	table.addCell("");
	            } else {
	            	table.addCell("");
	            	table.addCell(transaction.getAmount().toString());
	            }
	            table.addCell(String.valueOf(transaction.getBalance()));
	
	            i = i+1;
	        }
        }
    }
     
    public void export(HttpServletResponse response,String title) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
         
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
         
        Paragraph p = new Paragraph(title, font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
         
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1f, 3f, 2.5f, 2.0f, 2f,2f,2f});
        table.setSpacingBefore(10);
         
        writeTableHeader(table);
        writeTableData(table);
         
        document.add(table);
         
        document.close();
         
    }
}