package com.example.pidevcocomarket.services;

import com.example.pidevcocomarket.entities.Boutique;
import com.example.pidevcocomarket.entities.Catalog;
import com.example.pidevcocomarket.entities.Produit;
import com.example.pidevcocomarket.interfaces.ICatalogService;
import com.example.pidevcocomarket.repositories.BoutiqueRepository;
import com.example.pidevcocomarket.repositories.CatalogRepository;
import com.example.pidevcocomarket.repositories.ProduitRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ICatalogServiceImpl implements ICatalogService {
    @Autowired
    CatalogRepository cataloguerepo;
    @Autowired
    ProduitRepository produitRepository;
    @Autowired
    BoutiqueRepository boutiqueRepository ;

    public List<Catalog> GetAllCatalogue(){
        return cataloguerepo.findAll();
    }
    //Generation PDF
    private void addTableHeader(PdfPTable table) {
        Stream.of("Product Name", "Description", "Price" , "Boutique Name" , "Status")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    public Catalog GenerationCatalogueAll (){
        Set<Produit> produits = produitRepository.findAll().stream().collect(Collectors.toSet());
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(LocalDate.now()+"AllProduct.pdf"));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        document.open();
        PdfPTable table = new PdfPTable(5);
        addTableHeader(table);
        for (Produit produit : produits){
            table.addCell(produit.getName());
            table.addCell(produit.getDescription());
            table.addCell(String.valueOf(produit.getPrice()));
            table.addCell(produit.getBoutique().getNom());
            table.addCell(String.valueOf(produit.getStatus()) );
        }
        try {
            document.add(table);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        document.close();
        Catalog catalog = new Catalog();
        catalog.setDateCreation(LocalDate.now());
        catalog.setProduits(produitRepository.findAll().stream().collect(Collectors.toSet()));
        catalog.setName("AllCatalogue");
        return cataloguerepo.save(catalog) ;
    }

    public Catalog GenerateCatalogueBoutiquePromotion(Integer idBoutique){
        Catalog catalog = new Catalog();
        Boutique boutique = boutiqueRepository.findById(idBoutique).orElse(null) ;
        Set<Produit> Produits = produitRepository.findAll().stream().filter(produit -> produit.getBoutique().getId()==idBoutique && produit.isPromotion()==true).collect(Collectors.toSet());
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(LocalDate.now()+boutique.getNom()+"Promotion.pdf"));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        document.open();
        PdfPTable table = new PdfPTable(5);
        addTableHeader(table);
        for (Produit produit : Produits){
            table.addCell(produit.getName());
            table.addCell(produit.getDescription());
            table.addCell(String.valueOf(produit.getPrice()));
            table.addCell(produit.getBoutique().getNom());
            table.addCell(String.valueOf(produit.getStatus()) );
        }
        try {
            document.add(table);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        document.close();
        if(boutique != null ) {
            catalog.setDateCreation(LocalDate.now());
            catalog.setProduits( Produits);
            catalog.setName(boutique.getNom());
        }
        return  cataloguerepo.save(catalog) ;
    }

    public Catalog GenerateCatalogueBoutiqueSansPromotion(Integer idBoutique){
        Catalog catalog = new Catalog();
        Boutique boutique = boutiqueRepository.findById(idBoutique).orElse(null) ;
        Set<Produit> Produits = produitRepository.findAll().stream().filter(produit -> produit.getBoutique().getId()==idBoutique && produit.isPromotion()==false).collect(Collectors.toSet()); ;
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(LocalDate.now()+boutique.getNom()+"SansPromotion.pdf"));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        document.open();
        Paragraph p = new Paragraph("Catalog de tous les produits" + LocalDate.now(), new Font());
        p.setAlignment(Paragraph.ALIGN_CENTER);
        PdfPTable table = new PdfPTable(5);
        addTableHeader(table);
        for (Produit produit : Produits){
            table.addCell(produit.getName());
            table.addCell(produit.getDescription());
            table.addCell(String.valueOf(produit.getPrice()));
            table.addCell(produit.getBoutique().getNom());
            table.addCell(String.valueOf(produit.getStatus()) );
        }
        try {document.add(p);} catch (DocumentException e) {throw new RuntimeException(e);}
        try {document.add(table);} catch (DocumentException e) {throw new RuntimeException(e);}
        document.close();
        if(boutique != null ) {
            catalog.setDateCreation(LocalDate.now());
            catalog.setProduits( Produits);
            catalog.setName(boutique.getNom());
        }
        return  cataloguerepo.save(catalog) ;
    }

    public Catalog GenerateCataloguePromotion (){
        Catalog catalog = new Catalog() ;
        catalog.setDateCreation(LocalDate.now());
        Set<Produit> Produits = produitRepository.findAll().stream().filter(pr-> pr.isPromotion()==true).collect(Collectors.toSet());
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(LocalDate.now()+"PromotionCatalogue.pdf"));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        document.open();
        PdfPTable table = new PdfPTable(5);
        addTableHeader(table);
        for (Produit produit : Produits){
            table.addCell(produit.getName());
            table.addCell(produit.getDescription());
            table.addCell(String.valueOf(produit.getPrice()));
            table.addCell(produit.getBoutique().getNom());
            table.addCell(String.valueOf(produit.getStatus()) );
        }
        try {document.add(table);} catch (DocumentException e) {throw new RuntimeException(e);}
        document.close();
        catalog.setProduits(produitRepository.findAll().stream().filter(pr-> pr.isPromotion()==true).collect(Collectors.toSet()));
        catalog.setName("Catalog Promotion");
        return  cataloguerepo.save(catalog) ;
    }

    public Set<Produit> GetProduitCatalogueById(Integer Id){
        Catalog catalog = cataloguerepo.findById(Id).orElse(null) ;
        return catalog.getProduits();
    }

    public Integer DeleteCatalogue (Integer Id){
        cataloguerepo.deleteById(Id);
        return Id;
    }

    public Catalog UpdateCatalogue (Catalog catalog, Integer Id){
        Catalog oldCatalog = cataloguerepo.findById(Id).orElse(null) ;
        oldCatalog.setName(catalog.getName());
        return  cataloguerepo.save(oldCatalog) ;
    }
}
