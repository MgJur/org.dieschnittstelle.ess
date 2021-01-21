package org.dieschnittstelle.ess.ejb.ejbmodule.erp.crud;

import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.entities.erp.PointOfSale;
import org.dieschnittstelle.ess.entities.erp.ProductAtPosPK;
import org.dieschnittstelle.ess.entities.erp.StockItem;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class StockItemCRUDStateless implements StockItemCRUDLocal{

    @PersistenceContext(unitName = "erp_PU")
    private EntityManager em;

    @Override
    public StockItem createStockItem(StockItem item) {
       item = em.merge(item);
       return item;
    }

    @Override
    public StockItem readStockItem(IndividualisedProductItem prod, PointOfSale pos) {
        // Namen der stockitem klasse - nicht den namen der Tabelle
        // nicht namen der argumente die übergeben werden
//        Query qu = em.createQuery("SELECT si FROM StockItem si WHERE si.pos = " + pos.getId() + " AND si.product = " +prod.getId());
//        List<StockItem> stockItems = qu.getResultList();
//        if(stockItems.size() > 0) {
//            return stockItems.get(0);
//        }
//        return null;
          return em.find(StockItem.class, new ProductAtPosPK(prod,pos));
    }

    @Override
    public StockItem updateStockItem(StockItem item) {
        return em.merge(item);
    }

    @Override
    public List<StockItem> readAllStockItems() {
        Query query = em.createQuery("SELECT si FROM Stockitem");
        List<StockItem> items = query.getResultList();
        // wenn was vorhanden ist returns ansonsten null
        return items;
    }

    @Override
    public List<StockItem> readStockItemsForProduct(IndividualisedProductItem prod) {
        //kein sql sondern jpa query language
        Query query = em.createQuery("SELECT si FROM StockItem si WHERE si.product = " + prod.getId());
        List<StockItem> items = query.getResultList();
        return items;
    }

    @Override
    public List<StockItem> readStockItemsForPointOfSale(PointOfSale pos) {
        Query query = em.createQuery("SELECT si FROM StockItem si WHERE si.pos = " + pos.getId());
        List<StockItem> items = query.getResultList();
        return items;
    }
}
