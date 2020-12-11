package org.dieschnittstelle.ess.mip.client.demos;

import org.dieschnittstelle.ess.mip.client.Constants;
import org.dieschnittstelle.ess.mip.client.ejbclients.EJBProxyFactory;
import org.dieschnittstelle.ess.mip.components.crm.crud.TouchpointCRUD;

/* demonstrate direct access to the CRUD layer */
public class CreateTouchpointsAccessingCRUD {

	public static void main(String[] args) {
		EJBProxyFactory.initialise();

		try {
			TouchpointCRUD tpCRUD = EJBProxyFactory.getInstance().getProxy(TouchpointCRUD.class,"ejb:org.dieschnittstelle.ess.ejb/org.dieschnittstelle.ess.ejb.ejbmodule.crm/TouchpointCRUDStateless!org.dieschnittstelle.ess.ejb.ejbmodule.crm.crud.TouchpointCRUDRemote");
			tpCRUD.createTouchpoint(Constants.TOUCHPOINT_1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}