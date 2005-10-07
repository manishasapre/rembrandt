/*
 *  @author: SahniH
 *  Created on Sep 24, 2004
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
package gov.nih.nci.rembrandt.dto.query;

import gov.nih.nci.caintegrator.dto.query.OperatorType;
import gov.nih.nci.caintegrator.dto.query.Queriable;
import gov.nih.nci.caintegrator.dto.query.Query;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.rembrandt.queryservice.view.ViewType;
import gov.nih.nci.rembrandt.queryservice.view.Viewable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * @author SahniH Date: Sep 24, 2004
 * 
 */
public class CompoundQuery implements Queriable, Serializable, Cloneable {
	/**
	 * IMPORTANT! This class requires a clone method! This requires that any new
	 * data field that is added to this class also be cloneable and be added to
	 * clone calls in the clone method.If you do not do this, you will not
	 * seperate the references of at least one data field when we generate a
	 * copy of this object.This means that if the data field ever changes in one
	 * copy or the other it will affect both instances... this will be hell to
	 * track down if you aren't ultra familiar with the code base, so add those
	 * methods now! (Not necesary for primitives.)
	 */
	private Logger logger = Logger.getLogger(CompoundQuery.class);

	private Queriable leftQuery = null;

	private Queriable rightQuery = null;

	private OperatorType operatorType = null;

	private Viewable associatedView = null;

	private String queryName;

	// Session that this compoundQuery is associated with
	private String sessionId = null;

	public CompoundQuery(OperatorType operator, Queriable leftQuery,
			Queriable rightQuery) throws Exception {
		setOperatorType(operator);
		setLeftQuery(leftQuery);
		setRightQuery(rightQuery);
	}

	public CompoundQuery(Queriable rightQuery) throws Exception {
		setRightQuery(rightQuery);
		setOperatorType(null);
		setLeftQuery(null);
	}

	public Viewable getAssociatedView() {
		return associatedView;
	}

	public void setAssociatedView(Viewable viewable) {
		associatedView = viewable;
	}

	/**
	 * @return Returns the operatorType.
	 */
	public OperatorType getOperatorType() {
		return this.operatorType;
	}

	/**
	 * @param operatorType
	 *            The operatorType to set.
	 */
	public void setOperatorType(OperatorType operatorType) throws Exception {
		// PROJECTS results by make sure the two views are different
		this.operatorType = operatorType;
		if (validateProjectResultsBy() == false) {// if invalide Query
			throw new Exception(
					"For ProjectResultsBy, both views need to be different");
		}

	}

	/**
	 * @return Returns the leftQuery.
	 */
	public Queriable getLeftQuery() {
		return this.leftQuery;
	}

	/**
	 * @param leftQuery
	 *            The leftQuery to set.
	 */
	public void setLeftQuery(Queriable leftQuery) throws Exception {
		this.leftQuery = leftQuery;
		if (validateProjectResultsBy() == false) {// if invalide Query
			throw new Exception(
					"For ProjectResultsBy, both views need to be different");
		}
	}

	/**
	 * @return Returns the rightQuery.
	 */
	public Queriable getRightQuery() {
		return this.rightQuery;
	}

	/**
	 * @param rightQuery
	 *            The rightQuery to set.
	 */
	public void setRightQuery(Queriable rightQuery) throws Exception {
		this.rightQuery = rightQuery;
		// The right query's AssociatedView is always the "winning view"
		if (rightQuery != null && rightQuery.getAssociatedView() != null) {
			setAssociatedView(rightQuery.getAssociatedView());
		}
		if (validateProjectResultsBy() == false) {// if invalide Query
			throw new Exception(
					"For ProjectResultsBy, both views need to be different");
		}
	}

	/**
	 * @return validationStatus as true or false
	 * 
	 */
	public boolean validateProjectResultsBy() {
		// PROJECTS results by make sure the two views are different
		if ((operatorType != null && operatorType
				.equals(OperatorType.PROJECT_RESULTS_BY))
				&& (getRightQuery() != null && getLeftQuery() != null)) {
			if ((getRightQuery().getAssociatedView() != null && getLeftQuery()
					.getAssociatedView() != null)) {
				if ((getRightQuery().getAssociatedView().equals(
						ViewType.GENE_GROUP_SAMPLE_VIEW) || getRightQuery()
						.getAssociatedView().equals(
								ViewType.GENE_SINGLE_SAMPLE_VIEW))
						&& (!getLeftQuery().getAssociatedView().equals(
								ViewType.CLINICAL_VIEW))
						|| ((getLeftQuery().getAssociatedView().equals(
								ViewType.GENE_GROUP_SAMPLE_VIEW) || getLeftQuery()
								.getAssociatedView().equals(
										ViewType.GENE_SINGLE_SAMPLE_VIEW)) && (!getRightQuery()
								.getAssociatedView().equals(
										ViewType.CLINICAL_VIEW)))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * toString() method to generate Compound query for display - pcs
	 */
	public String toString() {
		Queriable leftQuery = this.getLeftQuery();
		Queriable rightQuery = this.getRightQuery();
		OperatorType operator = this.getOperatorType();
		String leftString = "";
		String rightString = "";
		String outString = "";

		try {
			if (leftQuery != null) {
				if (leftQuery instanceof CompoundQuery) {
					leftString += ((CompoundQuery) leftQuery).toString();
				} else if (leftQuery instanceof Query) {
					leftString += ((Query) leftQuery).getQueryName();
				}
			}

			if (rightQuery != null) {
				if (rightQuery instanceof CompoundQuery) {
					rightString += ((CompoundQuery) rightQuery).toString();
				} else if (rightQuery instanceof Query) {
					rightString += ((Query) rightQuery).getQueryName();
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		}

		if (operator != null) {
			outString += "( " + leftString + " " + operator.getOperatorType()
					+ " " + rightString + " )";
		} else {
			outString = rightString;
		}
		return outString;
	}

	public ViewType[] getValidViews() {
		ViewType[] validViewTypes = null;
		ArrayList queryTypesCollection = null;
		boolean isGEQuery = false;
		boolean isCGHQuery = false;
		boolean isClinical = false;

		queryTypesCollection = getQueryTypes(this);

		for (Iterator iter = queryTypesCollection.iterator(); iter.hasNext();) {
			QueryType thisQuery = (QueryType) iter.next();
			if (thisQuery instanceof QueryType.GeneExprQueryType)
				isGEQuery = true;
			if (thisQuery instanceof QueryType.CGHQueryType)
				isCGHQuery = true;
			if (thisQuery instanceof QueryType.ClinicalQueryType)
				isClinical = true;
		}
		// Gene Expression Only
		if (isGEQuery && !isCGHQuery && !isClinical) {
			validViewTypes = new ViewType[] { ViewType.CLINICAL_VIEW,
					ViewType.GENE_SINGLE_SAMPLE_VIEW,
					ViewType.GENE_GROUP_SAMPLE_VIEW };
		}
		// Genomic Only
		else if (!isGEQuery && isCGHQuery && !isClinical) {
			validViewTypes = new ViewType[] { ViewType.CLINICAL_VIEW,
					ViewType.COPYNUMBER_GROUP_SAMPLE_VIEW };
		}
		// Clinical Only
		else if (!isGEQuery && !isCGHQuery && isClinical) {
			validViewTypes = new ViewType[] { ViewType.CLINICAL_VIEW };
		}
		// Gene Expression and Clinical Only
		else if (isGEQuery && !isCGHQuery && isClinical) {
			validViewTypes = new ViewType[] { ViewType.CLINICAL_VIEW,
					ViewType.GENE_SINGLE_SAMPLE_VIEW };
		}
		// Genomic and Clinical Only
		else if (!isGEQuery && isCGHQuery && isClinical) {
			validViewTypes = new ViewType[] { ViewType.CLINICAL_VIEW,
					ViewType.COPYNUMBER_GROUP_SAMPLE_VIEW };
		}
		// The rest compound queries
		else {
			validViewTypes = new ViewType[] { ViewType.CLINICAL_VIEW,
					ViewType.GENE_SINGLE_SAMPLE_VIEW,
					ViewType.COPYNUMBER_GROUP_SAMPLE_VIEW };
		}

		return validViewTypes;
	}

	public ArrayList getQueryTypes(CompoundQuery cQuery) {
		ArrayList queryType = new ArrayList();
		Queriable lQuery;
		Queriable rQuery;
		lQuery = cQuery.getLeftQuery();
		rQuery = cQuery.getRightQuery();

		try {
			if (lQuery != null) {
				if (lQuery instanceof CompoundQuery)
					queryType.addAll(getQueryTypes((CompoundQuery) lQuery));
				else if (lQuery instanceof Query) {
					queryType.add(((Query) lQuery).getQueryType());
				}
			}

			if (rQuery != null) {
				if (rQuery instanceof CompoundQuery)
					queryType.addAll(getQueryTypes((CompoundQuery) rQuery));
				else if (rQuery instanceof Query) {
					queryType.add(((Query) rQuery).getQueryType());
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return queryType;
	}

	public Query[] getAssociatiedQueries() {
		Queriable leftQuery = this.getLeftQuery();
		Queriable rightQuery = this.getRightQuery();
		Collection queries = new Vector();

		try {
			if (leftQuery != null) {
				queries.addAll(Arrays.asList((Query[]) leftQuery
						.getAssociatiedQueries()));
			}
			if (rightQuery != null) {
				queries.addAll(Arrays.asList((Query[]) rightQuery
						.getAssociatiedQueries()));
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		return (Query[]) queries.toArray(new Query[queries.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.nautilus.query.Queriable#getQueryName()
	 */
	public String getQueryName() {
		return queryName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.nautilus.query.Queriable#setQueryName(java.lang.String)
	 */

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	/**
	 * @return Returns the sessionId.
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            The sessionId to set.
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * Overrides the protected Object.clone() method exposing it as public. It
	 * performs a 2 tier copy, that is, it does a memcopy of the instance and
	 * then sets all the non-primitive data fields to clones of themselves.
	 * 
	 * @return -A minimum 2 deep copy of this object.
	 */
	public Object clone() {
		CompoundQuery myClone = null;
		try {
			myClone = (CompoundQuery) super.clone();
		} catch (CloneNotSupportedException e) {
			// This will never happen...
		}
		if(associatedView!=null) {
			myClone.associatedView = (Viewable) associatedView.clone();
		}
		if(leftQuery!=null) {
			myClone.leftQuery = (Queriable) leftQuery.clone();
		}
		if(rightQuery!=null) {		
			myClone.rightQuery = (Queriable) rightQuery.clone();
		
		}
		if(operatorType!=null) {
			myClone.operatorType = (OperatorType) operatorType.clone();
		}
		return myClone;
	}
	/**
	 * This method will return the if the compoundQuery contains an all all genes querries.
	 * It iterates through the current list of queries and checks for
	 * isAllGenesQuery(). There is no
	 * setter for this property as it is only a subset of the current queries
	 *  in the compound query.
	 * 
	 * @return -- a boolean ifl the All Genes Query is detected
	 */
	public boolean isAllGenesQuery() {
		Query[] queries = getAssociatiedQueries();
		if(queries != null){
			for(int i = 0; i < queries.length; i++) {
				
				Query query = (Query)queries[i];
				if(query instanceof ComparativeGenomicQuery) {
					ComparativeGenomicQuery cgQuery = (ComparativeGenomicQuery)query;
					if(cgQuery.isAllGenesQuery()) {
						return true;
					}
				}else if(query instanceof GeneExpressionQuery) {
					GeneExpressionQuery geQuery = (GeneExpressionQuery)query;
					if(geQuery.isAllGenesQuery()) {
						return true;
					}
				}
			}
		}
		return false;
	}
}