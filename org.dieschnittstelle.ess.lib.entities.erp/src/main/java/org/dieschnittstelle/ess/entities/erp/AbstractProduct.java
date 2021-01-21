package org.dieschnittstelle.ess.entities.erp;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.GenericCRUDEntity;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/*
 * TODO JRS3: entfernen Sie die Auskommentierung der Annotation
 */
@Entity
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@XmlType(namespace = "http://dieschnittstelle.org/ess/entities/erp/ws")
@XmlSeeAlso({IndividualisedProductItem.class, ProductType.class})
@SequenceGenerator(name = "abstract_product_sequence", sequenceName = "abstract_product_id_sequence")

public abstract class AbstractProduct implements Serializable, GenericCRUDEntity {

	protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(AbstractProduct.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 6940403029597060153L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "abstract_product_sequence")
	private long id;

	private String name;
	
	private int price;

	public AbstractProduct() {
		logger.info("<constructor>");
	}

	public AbstractProduct(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
