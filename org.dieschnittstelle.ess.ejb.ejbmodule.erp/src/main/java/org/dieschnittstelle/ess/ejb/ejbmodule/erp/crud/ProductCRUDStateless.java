package org.dieschnittstelle.ess.ejb.ejbmodule.erp.crud;

import org.dieschnittstelle.ess.entities.erp.AbstractProduct;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProductCRUDStateless implements ProductCRUDRemote {

    @PersistenceContext(unitName = "erp_PU")
    private EntityManager em;

    @Override
    public AbstractProduct createProduct(AbstractProduct prod) {
        // persist mit Seiteneffekt - Inhalte werden in DB übertragen
        em.persist(prod);
        return prod;
    }

    @Override
    public List<AbstractProduct> readAllProducts() {
        Query qu = em.createQuery("SELECT e FROM AbstractProduct e");
        return  qu.getResultList();
    }

    @Override
    public AbstractProduct updateProduct(AbstractProduct update) {
        return em.merge(update);
    }

    @Override
    public AbstractProduct readProduct(long productID) {
        return em.find(AbstractProduct.class, productID);
    }

    @Override
    public boolean deleteProduct(long productID) {

        em.remove(em.find(AbstractProduct.class,productID));
        return true;
    }
}
