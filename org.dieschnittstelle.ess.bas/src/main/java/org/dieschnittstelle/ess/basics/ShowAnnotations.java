package org.dieschnittstelle.ess.basics;


import org.dieschnittstelle.ess.basics.annotations.AnnotatedStockItemBuilder;
import org.dieschnittstelle.ess.basics.annotations.DisplayAs;
import org.dieschnittstelle.ess.basics.annotations.StockItemProxyImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import static org.dieschnittstelle.ess.utils.Utils.*;

public class ShowAnnotations {

	public static void main(String[] args) {
		// we initialise the collection
		StockItemCollection collection = new StockItemCollection(
				"stockitems_annotations.xml", new AnnotatedStockItemBuilder());
		// we load the contents into the collection
		collection.load();

		for (IStockItem consumable : collection.getStockItems()) {
			showAttributes(((StockItemProxyImpl)consumable).getProxiedObject());
		}

		// we initialise a consumer
		Consumer consumer = new Consumer();
		// ... and let them consume
		consumer.doShopping(collection.getStockItems());
	}

	/*
	 * TODO BAS2
	 */
	private static void showAttributes(Object consumable) {
		// TODO BAS2: create a string representation of consumable by iterating
		//  over the object's attributes / fields as provided by its class
		//  and reading out the attribute values. The string representation
		//  will then be built from the field names and field values.
		//  Note that only read-access to fields via getters or direct access
		//  is required here.
		try{
			Class klass = consumable.getClass();
			//show( "Klasse: %s", klass.getSimpleName());
			String asOneString = "{ " + klass.getSimpleName();
			for (Field field : klass.getDeclaredFields()){
				field.setAccessible(true);
				Annotation[] annotations = field.getDeclaredAnnotations();

				boolean hasDisplayAs = false;
				for(Annotation annotation :annotations){
					if( annotation != null && annotation instanceof DisplayAs ){
						DisplayAs dis = (DisplayAs) annotation;
						hasDisplayAs = true;
						asOneString += " , " +dis.name() + " : " + field.get(consumable);
					}
				}
				if(!hasDisplayAs){
					asOneString += " , " +field.getName() + " : " + field.get(consumable);
				}
				//show("Attribut %s : %s, Wert von Attribut %s: %s", i, attr.getName(),i, attr.get(consumable));
			}
			asOneString += " }";
			show(asOneString);
		}
		catch(Exception e) {
			show(e);
		}


		// TODO BAS3: if the new @DisplayAs annotation is present on a field,
		//  the string representation will not use the field's name, but the name
		//  specified in the the annotation. Regardless of @DisplayAs being present
		//  or not, the field's value will be included in the string representation.
	}

}
