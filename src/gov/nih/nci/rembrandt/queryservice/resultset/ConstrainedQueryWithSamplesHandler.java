/*
 * Created on Mar 22, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.rembrandt.queryservice.resultset;

import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.query.OperatorType;
import gov.nih.nci.caintegrator.dto.query.Queriable;
import gov.nih.nci.caintegrator.dto.query.Query;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

/**
 * @author Himanso
 * 
 * This helper class to do the following:
 * 1- Cast the query to a compound query
 * 2- Creat a new SampleCriteria
 * 3- place the sampleIds into the SampleCriteria
 * 4- apply the SampleCriteria to each of the individual queries that make up the CompoundQuery
 * 
 */

public class ConstrainedQueryWithSamplesHandler {
	private static Logger logger = Logger
			.getLogger(ConstrainedQueryWithSamplesHandler.class);

	public SampleCriteria createSampleCriteria(String[] sampleIDs) {
		SampleCriteria sampleCrit = null;
		Collection sampleIDDEs = new ArrayList();
		if (sampleIDs != null) {
			for (int i = 0; i < sampleIDs.length; i++) {
				sampleIDDEs.add(new SampleIDDE(sampleIDs[i]));
			}
			sampleCrit = new SampleCriteria();
			sampleCrit.setSampleIDs(sampleIDDEs);
		}
		return sampleCrit;
	}

	public CompoundQuery constrainQuery(CompoundQuery compoundQuery,
			SampleCriteria sampleCrit) throws Exception {
		CompoundQuery newQuery = null;
		Queriable leftQuery = compoundQuery.getLeftQuery();
		Queriable rightQuery = compoundQuery.getRightQuery();
		OperatorType operator = compoundQuery.getOperatorType();

		try {
			if (leftQuery != null) {
				if (leftQuery instanceof CompoundQuery) {
					leftQuery = constrainQuery((CompoundQuery) leftQuery,
							sampleCrit);
				} else if (leftQuery instanceof Query) {
					Query query = (Query) leftQuery;
					query.setSampleIDCrit(sampleCrit);
				}
			}

			if (rightQuery != null) {
				if (rightQuery instanceof CompoundQuery) {
					rightQuery = constrainQuery((CompoundQuery) rightQuery,
							sampleCrit);
				} else if (rightQuery instanceof Query) {
					Query query = (Query) rightQuery;
					query.setSampleIDCrit(sampleCrit);
				}
			}
			if (operator != null) {
				newQuery = new CompoundQuery(operator, leftQuery, rightQuery);
			} else { //then its the right query
				newQuery = new CompoundQuery(rightQuery);
			}

		} catch (Exception ex) {
			logger.error(ex);
		}
		return newQuery;
	}
}