package org.dieschnittstelle.ess.jrs;

import java.util.ArrayList;
import java.util.List;

import org.dieschnittstelle.ess.entities.GenericCRUDExecutor;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

/*
 * TODO JRS2: implementieren Sie hier die im Interface deklarierten Methoden
 */

public class ProductCRUDServiceImpl implements IProductCRUDService {

	@Context
	private ServletContext servletContext;
	private GenericCRUDExecutor<AbstractProduct> getExec() {
		return (GenericCRUDExecutor<AbstractProduct>) servletContext.getAttribute("productCRUD");
	}


	@Override
	public AbstractProduct createProduct(
			AbstractProduct prod) {

		return (AbstractProduct) getExec().createObject(prod);
	}

	@Override
	public List<AbstractProduct> readAllProducts() {
		return (List) getExec().readAllObjects();
	}

	@Override
	public AbstractProduct updateProduct(long id, AbstractProduct update) {
		// TODO Auto-generated method stub
		return (AbstractProduct) getExec().updateObject(update);
	}

	@Override
	public boolean deleteProduct(long id) {
		return getExec().deleteObject(id);
	}

	@Override
	public AbstractProduct readProduct(long id) {
		return (AbstractProduct) getExec().readObject(id);
	}
	
}
