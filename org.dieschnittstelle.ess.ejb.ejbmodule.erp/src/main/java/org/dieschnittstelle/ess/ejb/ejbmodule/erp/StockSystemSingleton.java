package org.dieschnittstelle.ess.ejb.ejbmodule.erp;

import org.dieschnittstelle.ess.ejb.ejbmodule.erp.crud.PointOfSaleCRUDLocal;
import org.dieschnittstelle.ess.ejb.ejbmodule.erp.crud.StockItemCRUDLocal;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.entities.erp.PointOfSale;
import org.dieschnittstelle.ess.entities.erp.StockItem;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.dieschnittstelle.ess.utils.Utils.show;

@Singleton
@Remote(StockSystemRemote.class)
public class StockSystemSingleton implements StockSystemLocal, StockSystemRemote{

    @EJB
    private StockItemCRUDLocal siCrud;
    @EJB
    private PointOfSaleCRUDLocal posCrud;

    @Override
    public void addToStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
         System.out.println("addToStock(): siCrud: " + siCrud + " of class: " + siCrud.getClass());
         System.out.println("addToStock(): posCrud: " + posCrud + " of class: " + posCrud.getClass());

         PointOfSale pos = posCrud.readPointOfSale(pointOfSaleId);
         StockItem stockItem = siCrud.readStockItem(product,pos);

         if(stockItem == null) {
             stockItem = new StockItem(product,pos, units);
             siCrud.createStockItem(stockItem);
         } else {
             stockItem.setUnits(stockItem.getUnits() + units);
             siCrud.updateStockItem(stockItem);
         }

    }

    @Override
    public void removeFromStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
        addToStock(product, pointOfSaleId, -units);
    }

    @Override
    public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
        // klassische variante mit iteration
//        List<StockItem> silist = siCrud
//                .readStockItemsForPointOfSale(posCrud.readPointOfSale(pointOfSaleId));
//        List<IndividualisedProductItem> productItems = new ArrayList<>();
//        for (StockItem si : silist) {
//            productItems.add(si.getProduct());
//        }
//        return productItems;

        // "moderne" variante mit mapping und array func

        return siCrud
                .readStockItemsForPointOfSale(posCrud.readPointOfSale(pointOfSaleId))
                .stream()
                .map(si -> si.getProduct())
                .collect(Collectors.toList());

//        List<IndividualisedProductItem> prods = new ArrayList<>();
//        PointOfSale pos = posCrud.readPointOfSale(pointOfSaleId);
//        List<StockItem> items = siCrud.readStockItemsForPointOfSale(pos);
//        for (int i = 0; i < items.size(); i++){
//            prods.add(items.get(i).getProduct());
//        }
//        return prods;

    }

    @Override
    public List<IndividualisedProductItem> getAllProductsOnStock() {
        // methode wurde gemerget mit getProductsOnStock

        List<IndividualisedProductItem> prods = new ArrayList<>();
        List<PointOfSale> positions = posCrud.readAllPointsOfSale();

        for (PointOfSale posi: positions) {
            List<StockItem> items = siCrud.readStockItemsForPointOfSale(posi);
            // System.out.println("Alle items an der position: " + items);
            for(StockItem item: items) {
                IndividualisedProductItem prod = item.getProduct();
                // wenns nicht bereits vorhanden ist, füge es hinzu
                if(!items.contains(prod)){
                    prods.add(prod);
                }
            }
        }
        //dirty loesung - hashset eliminiert alle duplikate
        new ArrayList<>(new HashSet<>(prods));
        return prods
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public int getUnitsOnStock(IndividualisedProductItem product, long pointOfSaleId) {
        PointOfSale posi = posCrud.readPointOfSale(pointOfSaleId);
        // erst das stockitem rauslesen und dann alle Units
        return siCrud.readStockItem(product, posi).getUnits();
    }

    @Override
    public int getTotalUnitsOnStock(IndividualisedProductItem product) {
        List<StockItem> stockItems = siCrud.readStockItemsForProduct(product);
        // mit einer lambda funktion / Array funktion
        return stockItems.stream().mapToInt(si -> si.getUnits()).sum();
    }

    @Override
    public List<Long> getPointsOfSale(IndividualisedProductItem product) {
        return siCrud.readStockItemsForProduct(product)
                .stream()
                .map(si -> si.getPos().getId())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<StockItem> getCompleteStock() {
        throw new UnsupportedOperationException("getCompleteStock() is not supported");
    }
}
