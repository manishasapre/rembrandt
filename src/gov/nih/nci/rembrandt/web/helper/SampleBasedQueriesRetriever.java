/**
 * 
 */
package gov.nih.nci.rembrandt.web.helper;

import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.query.Queriable;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.rembrandt.cache.CacheManagerDelegate;
import gov.nih.nci.rembrandt.cache.ConvenientCache;
import gov.nih.nci.rembrandt.dto.lookup.DiseaseTypeLookup;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

/**
 * @author sahnih
 *
 */
public class SampleBasedQueriesRetriever implements Serializable {

	/**
	 * 
	 */
    private static Logger logger = Logger.getLogger(SampleBasedQueriesRetriever.class);
    private ConvenientCache cacheManager = CacheManagerDelegate.getInstance();
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unchecked")
	private Map<String,ClinicalDataQuery> predefinedQueryMap = new TreeMap();
	private List allPredefinedAndSampleSetNames;
    private LabelValueBean lvb;
	/**
	 *  Constructor creates the PredefinedQueries Disease Queries
	 */
	public SampleBasedQueriesRetriever() {
		createPredefinedDiseaseQueries();
	}
	private void createPredefinedDiseaseQueries(){		
		try {
			DiseaseTypeLookup[] myDiseaseTypes =LookupManager.getDiseaseType();
			if(myDiseaseTypes != null){
				for (DiseaseTypeLookup diseaseTypeLookup : myDiseaseTypes){
					DiseaseOrGradeCriteria diseaseCrit = new DiseaseOrGradeCriteria();
			        diseaseCrit.setDisease(new DiseaseNameDE(diseaseTypeLookup.getDiseaseType()));
			        ClinicalDataQuery clinicalDataQuery = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
			        clinicalDataQuery.setQueryName(diseaseTypeLookup.getDiseaseDesc());
			        clinicalDataQuery.setDiseaseOrGradeCrit(diseaseCrit);
			        predefinedQueryMap.put(diseaseTypeLookup.getDiseaseDesc(),clinicalDataQuery);
				}
			}
		} catch (Exception e) {
			logger.error("Error in SampleSetBag when calling createPredefinedDiseaseQueries method");
			logger.error(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	/**
	 * This is a list of defined Disease query names. Typically used in Class Comparision Analysis queries  
	 * 
	 */
	public List getAllPredefinedDiseaseQueryNames(){
		return new ArrayList( predefinedQueryMap.keySet());
	}
    
       
	/**
	 * This is a list of samples selected from a Resultset.
	 * It is used to create a SampleCriteria that is placed in a ClinicalDataQuery that
	 * has a ClinicalView.  This gives us the ability to extract or look at these
	 * "Samples of Interest" at any time, and applying them to other queries if
	 * the user desires.    
	 * 
	 * @param sessionId --identifies the sessionCache that you want a complete
	 * list of SampleSetNames stored in.
	 */
	public List getAllSampleSetNames(String sessionID){
		return  cacheManager.getSampleSetNames(sessionID);
	}
    
    /**This is the list of names of all the predefined queries and sample sets
     * that are in the session. There is a list of defined Disease query names that are
     * retrieved as well as samples the user has selected and saved. After all these 
     * names have been retieved, they are stored as LabelValueBeans in an ArrayList.
     * 
     * NOTE: may want only call predefined queries once. 
     * 
     @param sessionId --identifies the sessionCache that you want a complete
     * list of SampleSetNames stored in. -KR
     */
    public List getAllPredefinedAndSampleSetNames(String sessionID){
        
        //NEED to add sample set retrieval
        //if(CacheManagerDelegate.getInstance().getObjectFromSessionCache(sessionID,"predefinedDiseaseQueries") == null){
                   List predefined = new ArrayList(getAllPredefinedDiseaseQueryNames());
                   //List sampleSet = new ArrayList(getAllSampleSetNames(sessionID));
                   allPredefinedAndSampleSetNames = new ArrayList();
                       for(int i =0; i < predefined.size(); i++){
                           lvb = new LabelValueBean((String)predefined.get(i),(String)predefined.get(i));
                           allPredefinedAndSampleSetNames.add(lvb);
                       }
                       //cacheManager.addToSessionCache(sessionID,"predefinedDiseaseQueries",(Serializable) allPredefinedAndSampleSetNames);
        //}
        return allPredefinedAndSampleSetNames;
        }
    
	/**
	 * This method, when given a sessionId and a queryName, will check the 
	 * session cache for the stored ClinicalDataQuery.  If it is not stored or 
	 * causes an exception it will return Null.  If you are getting Null values,
	 * when you know that you have stored the query in the sessionCache, check
	 * the log files as any exceptions will be written there.
	 *   
	 * @param sessionId --the session that should have the query stored
	 * @param queryName --the query that is desired
	 * @return ClinicalDataQuery
	 */
	public ClinicalDataQuery getQuery(String sessionID, String queryName){
		if( sessionID != null &&  queryName != null){
			if(predefinedQueryMap.containsKey(queryName)){
				return predefinedQueryMap.get(queryName);
			}
			else{
				Queriable queriable = cacheManager.getQuery(sessionID, queryName);
				if(queriable instanceof ClinicalDataQuery){
					return (ClinicalDataQuery) queriable;
				}
			}
		}
		return null;
	}

	
}