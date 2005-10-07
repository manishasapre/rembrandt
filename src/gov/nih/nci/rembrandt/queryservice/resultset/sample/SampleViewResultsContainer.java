/*
 *  @author: SahniH
 *  Created on Oct 21, 2004
 *  @version $ Revision: 1.0 $
 * 
 *	The caBIO Software License, Version 1.0
 *
 *	Copyright 2004 SAIC. This software was developed in conjunction with the National Cancer 
 *	Institute, and so to the extent government employees are co-authors, any rights in such works 
 *	shall be subject to Title 17 of the United States Code, section 105.
 * 
 *	Redistribution and use in source and binary forms, with or without modification, are permitted 
 *	provided that the following conditions are met:
 *	 
 *	1. Redistributions of source code must retain the above copyright notice, this list of conditions 
 *	and the disclaimer of Article 3, below.  Redistributions in binary form must reproduce the above 
 *	copyright notice, this list of conditions and the following disclaimer in the documentation and/or 
 *	other materials provided with the distribution.
 * 
 *	2.  The end-user documentation included with the redistribution, if any, must include the 
 *	following acknowledgment:
 *	
 *	"This product includes software developed by the SAIC and the National Cancer 
 *	Institute."
 *	
 *	If no such end-user documentation is to be included, this acknowledgment shall appear in the 
 *	software itself, wherever such third-party acknowledgments normally appear.
 *	 
 *	3. The names "The National Cancer Institute", "NCI" and "SAIC" must not be used to endorse or 
 *	promote products derived from this software.
 *	 
 *	4. This license does not authorize the incorporation of this software into any proprietary 
 *	programs.  This license does not authorize the recipient to use any trademarks owned by either 
 *	NCI or SAIC-Frederick.
 *	 
 *	
 *	5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED 
 *	WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 *	MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE 
 *	DISCLAIMED.  IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR 
 *	THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 *	EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 *	PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 *	PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY 
 *	OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 *	NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 *	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *	
 */
package gov.nih.nci.rembrandt.queryservice.resultset.sample;

import gov.nih.nci.caintegrator.dto.de.DomainElement;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author SahniH
 * Date: Oct 21, 2004
 * 
 */
public class SampleViewResultsContainer implements ResultsContainer{
	protected SortedMap samples = new TreeMap();
	/**
	* the object type, it repesents generic typpe for data element
    */
    protected DomainElement type;
	/**
	 * @param bioSpecimenResultset Adds bioSpecimenResultset to this DiseaseTypeResultset object.
	 */
	public void addBioSpecimenResultset(BioSpecimenResultset bioSpecimenResultset){
		if(bioSpecimenResultset != null && bioSpecimenResultset.getBiospecimen() != null){
			samples.put(bioSpecimenResultset.getBiospecimen().getValue().toString(), bioSpecimenResultset);
		}
	}
	/**
	 * @param bioSpecimenResultset Removes bioSpecimenResultset to this DiseaseTypeResultset object.
	 */
	public void removeBioSpecimenResultset(BioSpecimenResultset bioSpecimenResultset){
		if(bioSpecimenResultset != null && bioSpecimenResultset.getBiospecimen() != null){
			samples.remove(bioSpecimenResultset.getBiospecimen());
		}
	}
	/**
	 * @return bioSpecimenResultset Returns bioSpecimenResultset to this DiseaseTypeResultset object.
	 */
    public Collection getBioSpecimenResultsets(){
    		return samples.values();
    }
    /**
     * @param samplId
	 * @return bioSpecimenResultset Returns reporterResultset for this GeneResultset.
	 */
    public BioSpecimenResultset getBioSpecimenResultset(String samplId){
    	if(samplId != null){
			return (BioSpecimenResultset) samples.get(samplId);
		}
    		return null;
    }
	/**
	 * @param none Removes all bioSpecimenResultset in this DiseaseTypeResultset object.
	 */
    public void removeAllBioSpecimenResultset(){
    	samples.clear();	
    }
}