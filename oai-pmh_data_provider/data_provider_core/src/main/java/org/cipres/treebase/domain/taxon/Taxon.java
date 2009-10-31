/*
 * CIPRES Copyright (c) 2005- 2006, The Regents of the University of California All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met: * Redistributions of source code must retain the
 * above copyright notice, this list of conditions and the following disclaimer. * Redistributions
 * in binary form must reproduce the above copyright notice, this list of conditions and the
 * following disclaimer in the documentation and/or other materials provided with the distribution. *
 * Neither the name of the University of California or the San Diego Supercomputer Center nor the
 * names of its contributors may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.cipres.treebase.domain.taxon;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

import org.cipres.treebase.domain.AbstractPersistedObject;
import org.cipres.treebase.domain.TBPersistable;

/**
 * Taxon.java
 * 
 * Created on Feb 14, 2006
 * 
 * @author Jin Ruan
 * 
 */
@Entity
@Table(name = "TAXON")
@AttributeOverride(name = "id", column = @Column(name = "TAXON_ID"))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "taxonCache")
@BatchSize(size = 10)
public class Taxon extends AbstractPersistedObject {
	private static final Logger LOGGER = Logger.getLogger(Taxon.class);

	private static final long serialVersionUID = 1L;

	private String mName;
	private String mDescription;
	private Integer mNcbiTaxId;
	private Integer mGroupCode;
	private Long mUBioNamebankId;
	private Integer mTB1LegacyId; // mjd 20090415

	private Collection<TaxonLink> mForeignLinks = new ArrayList<TaxonLink>();

	/**
	 * Constructor.
	 */
	public Taxon() {
		super();
	}
	
	public Taxon(String name, Long uBioId, Integer ncbiId) {
		super();
		this.setName(name);
		this.setUBioNamebankId(uBioId);
		this.setNcbiTaxId(ncbiId);
	}

	/**
	 * Return the Description field.
	 * 
	 * @return String
	 */
	@Column(name = "Description", nullable = true, length = TBPersistable.COLUMN_LENGTH_STRING_NOTES)
	public String getDescription() {
		return mDescription;
	}

	/**
	 * Set the Description field.
	 */
	public void setDescription(String pNewDescription) {
		mDescription = pNewDescription;
	}

	/**
	 * Return the Name field. It usually is the "preferred name" from NCBI if it is available.
	 * 
	 * @return String
	 */
	@Column(name = "Name", nullable = true, length = TBPersistable.COLUMN_LENGTH_STRING)
	@Index(name = "TAXON_NAME_IDX")
	public String getName() {
		return mName;
	}

	/**
	 * Set the Name field.
	 */
	public void setName(String pNewName) {
		mName = pNewName;
	}

	/**
	 * The Ncbi Taxa Id.
	 * 
	 * @return Integer
	 */
	@Column(name = "NcbiTaxId")
	public Integer getNcbiTaxId() {
		return mNcbiTaxId;
	}

	/**
	 * Set the NcbiTaxId field.
	 */
	public void setNcbiTaxId(Integer pNewNcbiTaxId) {
		mNcbiTaxId = pNewNcbiTaxId;
	}

	/**
	 * The GroupCode is not used yet. It can be used for a kingdom code.
	 * 
	 * @return Integer
	 */
	@Column(name = "GroupCode")
	public Integer getGroupCode() {
		return mGroupCode;
	}

	/**
	 * Set the GroupCode field.
	 */
	public void setGroupCode(Integer pNewGroupCode) {
		mGroupCode = pNewGroupCode;
	}

	/**
	 * Having a namebankid in the taxa table is not really needed, since it is redundant with the
	 * namebankid in TaxonVariant. But it may prove useful in future (it is a record of exactly
	 * which uBIO namebankrecord we designated as the best of all the name variants). But certainly
	 * its value should be NULL if the record's namestring does not match with the fullnamestring in
	 * TaxonVariant.
	 * 
	 * See the taxonomic intelligence logic for how this field is set.
	 * 
	 * @return Integer
	 */
	@Column(name = "UBioNamebankId")
	public Long getUBioNamebankId() {
		return mUBioNamebankId;
	}

	/**
	 * Set the UBioNamebankId field.
	 */
	public void setUBioNamebankId(Long pNewUBioNamebankId) {
		mUBioNamebankId = pNewUBioNamebankId;
	}

	@Column(name = "TB1LegacyId", nullable = true)
	public Integer getTB1LegacyId() {
		return mTB1LegacyId;
	}

	public void setTB1LegacyId(Integer legacyId) {
		mTB1LegacyId = legacyId;
	}

	//	/**
//	 * Return the TaxonVariants field.
//	 * 
//	 * @return Collection<TaxonVariant> mTaxonVariants
//	 */
//	@OneToMany(mappedBy = "taxon", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//	// @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
//	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "taxonCache")
//	protected Collection<TaxonVariant> getTaxonVariants() {
//		return mTaxonVariants;
//	}
//
//	/**
//	 * Set the TaxonVariants field.
//	 */
//	public void setTaxonVariants(Collection<TaxonVariant> pNewTaxonVariants) {
//		mTaxonVariants = pNewTaxonVariants;
//	}
//
//	/**
//	 * Append a new variant to the end of the list. Manage bi-directional relationship.
//	 * 
//	 * Creation date: Feb 22, 2006 12:06:25 PM
//	 * 
//	 * @param pVariant
//	 */
//	public void addTaxonVariant(TaxonVariant pVariant) {
//		if (pVariant != null && !getTaxonVariants().contains(pVariant)) {
//			getTaxonVariants().add(pVariant);
//			pVariant.setTaxon(this);
//		}
//	}
//
//	/**
//	 * Remove a new variant to the end of the list. Manage bi-directional relationship.
//	 * 
//	 * Creation date: Feb 22, 2006 12:06:25 PM
//	 * 
//	 * @param pVariant
//	 */
//	public void removeTaxonVariant(TaxonVariant pVariant) {
//		if (pVariant != null && getTaxonVariants().contains(pVariant)) {
//			getTaxonVariants().remove(pVariant);
//			pVariant.setTaxon(null);
//		}
//	}
//
//	/**
//	 * Return a read only list of taxon variants.
//	 * 
//	 * @return
//	 */
//	@Transient
//	public Collection<TaxonVariant> getTaxonVariantsReadOnly() {
//		return Collections.unmodifiableCollection(getTaxonVariants());
//	}
//
	/**
	 * Return the ForeignLinks field.
	 * 
	 * @return Collection<TaxonLink>
	 */
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "TAXON_ID", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "taxonCache")
	public Collection<TaxonLink> getForeignLinks() {
		return mForeignLinks;
	}

	/**
	 * Set the ForeignLinks field.
	 */
	public void setForeignLinks(Collection<TaxonLink> pNewForeignLinks) {
		mForeignLinks = pNewForeignLinks;
	}

}