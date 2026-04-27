package com.pegasus.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.pegasus.models.Commande;
import com.pegasus.models.LignePanier;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class TicketPdfService {

    public static String genererTicket(Commande commande, List<LignePanier> lignes, String clientNom) {
        String path = System.getProperty("user.home") + "/ticket_commande_" + commande.getId() + ".pdf";

        try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            // ── Fonts ──────────────────────────────────────
            Font fontTitre    = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD,   new BaseColor(26, 26, 46));
            Font fontSousTitre= new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL, new BaseColor(100, 100, 100));
            Font fontBold     = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD,   BaseColor.BLACK);
            Font fontNormal   = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
            Font fontOrange   = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD,   new BaseColor(240, 165, 0));
            Font fontVert     = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD,   new BaseColor(39, 174, 96));
            Font fontBlanc    = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD,   BaseColor.WHITE);

            // ── En-tête ────────────────────────────────────
            PdfPTable header = new PdfPTable(1);
            header.setWidthPercentage(100);
            PdfPCell headerCell = new PdfPCell();
            headerCell.setBackgroundColor(new BaseColor(26, 26, 46));
            headerCell.setPadding(20);
            headerCell.setBorder(Rectangle.NO_BORDER);

            Paragraph titre = new Paragraph("PEGASUS", fontTitre);
            titre.getFont().setColor(BaseColor.WHITE);
            titre.getFont().setSize(26);
            titre.setAlignment(Element.ALIGN_CENTER);
            headerCell.addElement(titre);

            Paragraph sousTitre = new Paragraph("Ticket de Commande", fontSousTitre);
            sousTitre.getFont().setColor(new BaseColor(200, 200, 200));
            sousTitre.setAlignment(Element.ALIGN_CENTER);
            headerCell.addElement(sousTitre);

            header.addCell(headerCell);
            document.add(header);
            document.add(Chunk.NEWLINE);

            // ── Infos commande ─────────────────────────────
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingBefore(10);

            addInfoRow(infoTable, "N° Commande :", "#" + commande.getId(), fontBold, fontOrange);
            addInfoRow(infoTable, "Client :", clientNom, fontBold, fontNormal);
            addInfoRow(infoTable, "Date :", commande.getDateCommande()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontBold, fontNormal);
            addInfoRow(infoTable, "Statut :", commande.getStatut().toUpperCase(), fontBold, fontVert);

            document.add(infoTable);
            document.add(Chunk.NEWLINE);

            // ── Séparateur ─────────────────────────────────
            LineSeparator line = new LineSeparator();
            line.setLineColor(new BaseColor(240, 165, 0));
            document.add(new Chunk(line));
            document.add(Chunk.NEWLINE);

            // ── Tableau produits ───────────────────────────
            Paragraph titreArticles = new Paragraph("Détail des articles", fontBold);
            titreArticles.setSpacingAfter(8);
            document.add(titreArticles);

            PdfPTable prodTable = new PdfPTable(4);
            prodTable.setWidthPercentage(100);
            prodTable.setWidths(new float[]{4f, 1.5f, 2f, 2f});

            // En-têtes colonnes
            String[] headers = {"Produit", "Qté", "Prix unitaire", "Sous-total"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, fontBlanc));
                cell.setBackgroundColor(new BaseColor(26, 26, 46));
                cell.setPadding(8);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                prodTable.addCell(cell);
            }

            // Lignes produits
            boolean pair = false;
            for (LignePanier lp : lignes) {
                BaseColor bg = pair ? new BaseColor(245, 245, 245) : BaseColor.WHITE;
                String nomProduit = lp.getProduit() != null ? lp.getProduit().getNom() : "Produit";
                float sousTotal = lp.getPrixUnitaire() * lp.getQuantite();

                addProdRow(prodTable, nomProduit,
                        String.valueOf(lp.getQuantite()),
                        String.format("%.2f EUR", lp.getPrixUnitaire()),
                        String.format("%.2f EUR", sousTotal),
                        fontNormal, bg);
                pair = !pair;
            }

            document.add(prodTable);
            document.add(Chunk.NEWLINE);

            // ── Total ──────────────────────────────────────
            PdfPTable totalTable = new PdfPTable(2);
            totalTable.setWidthPercentage(50);
            totalTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

            PdfPCell labelTotal = new PdfPCell(new Phrase("TOTAL", fontBold));
            labelTotal.setBorder(Rectangle.NO_BORDER);
            labelTotal.setPadding(10);
            labelTotal.setBackgroundColor(new BaseColor(26, 26, 46));
            labelTotal.getPhrase().getFont().setColor(BaseColor.WHITE);

            PdfPCell valTotal = new PdfPCell(
                    new Phrase(String.format("%.2f EUR", commande.getTotal()), fontOrange));
            valTotal.setBorder(Rectangle.NO_BORDER);
            valTotal.setPadding(10);
            valTotal.setBackgroundColor(new BaseColor(26, 26, 46));
            valTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);

            totalTable.addCell(labelTotal);
            totalTable.addCell(valTotal);
            document.add(totalTable);

            // ── Pied de page ───────────────────────────────
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            Paragraph footer = new Paragraph(
                    "Merci pour votre commande ! — Pegasus © 2024", fontSousTitre);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            return path;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void addInfoRow(PdfPTable table, String label, String value,
                                   Font fontLabel, Font fontValue) {
        PdfPCell c1 = new PdfPCell(new Phrase(label, fontLabel));
        c1.setBorder(Rectangle.NO_BORDER);
        c1.setPaddingBottom(6);

        PdfPCell c2 = new PdfPCell(new Phrase(value, fontValue));
        c2.setBorder(Rectangle.NO_BORDER);
        c2.setPaddingBottom(6);

        table.addCell(c1);
        table.addCell(c2);
    }

    private static void addProdRow(PdfPTable table, String nom, String qte,
                                   String prix, String total, Font font, BaseColor bg) {
        String[] vals = {nom, qte, prix, total};
        for (String v : vals) {
            PdfPCell cell = new PdfPCell(new Phrase(v, font));
            cell.setBackgroundColor(bg);
            cell.setPadding(7);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        }
    }
}