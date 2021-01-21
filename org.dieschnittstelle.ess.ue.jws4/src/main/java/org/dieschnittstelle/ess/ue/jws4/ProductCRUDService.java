package org.dieschnittstelle.ess.ue.jws4;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.dieschnittstelle.ess.entities.GenericCRUDExecutor;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import org.dieschnittstelle.ess.entities.erp.Campaign;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.entities.erp.ProductType;

/*
 * TODO JWS4: machen Sie die Funktionalitaet dieser Klasse als Web Service verfuegbar und verwenden Sie fuer
 *  die Umetzung der Methoden die Instanz von GenericCRUDExecutor<AbstractProduct>,
 *  die Sie aus dem ServletContext auslesen koennen
 */

@WebService(targetNamespace = "http://dieschnittstelle.org/ess/jws",serviceName = "ProductCRUDWebService", name = "IProductCRUDService", portName = "ProductCRUDPort")
@SOAPBinding
public class ProductCRUDService {

	@Resource
	private WebServiceContext wsContext;

	private ServletContext initServletContext() {
		MessageContext msgContext = wsContext.getMessageContext();
		return (ServletContext) msgContext.get(MessageContext.SERVLET_CONTEXT);
	}

	private GenericCRUDExecutor<AbstractProduct> getExec() {
		ServletContext servletContext = initServletContext();
		return (GenericCRUDExecutor<AbstractProduct>) servletContext.getAttribute("productCRUD");
	}


	public List<AbstractProduct> readAllProducts() {
		return (List) getExec().readAllObjects();
	}


	public AbstractProduct createProduct(AbstractProduct product) {
		return (AbstractProduct) getExec().createObject(product);
	}


	public AbstractProduct updateProduct(AbstractProduct update) {
		return (AbstractProduct) getExec().updateObject(update);
	}


	public boolean deleteProduct(long id) {
		return getExec().deleteObject(id);
	}

	public AbstractProduct readProduct(long id) {
		return (AbstractProduct) getExec().readObject(id);
	}

}
