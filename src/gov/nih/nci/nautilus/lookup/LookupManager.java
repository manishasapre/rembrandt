package gov.nih.nci.nautilus.lookup;

import gov.nih.nci.nautilus.cache.CacheManagerDelegate;
import gov.nih.nci.nautilus.cache.ConvenientCache;
import gov.nih.nci.nautilus.data.AllGeneAlias;
import gov.nih.nci.nautilus.data.CytobandPosition;
import gov.nih.nci.nautilus.data.DiseaseTypeDim;
import gov.nih.nci.nautilus.data.ExpPlatformDim;
import gov.nih.nci.nautilus.data.PatientData;
import gov.nih.nci.nautilus.de.ChromosomeNumberDE;
import gov.nih.nci.nautilus.de.CytobandDE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;

/**
 * This class provide a single point for UI related classes to 
 * get lookup data (data that a user can select to mofify a query)
 * It uses that CacheManagerDelegate to determine if the data
 * has already been loaded.  If not it executes the query and
 * stores it in the ApplicationCache, else it retrives that data
 * from the Cache and returns it to the UI.
 * 
 * @author SahniH
 */
public class LookupManager{
    private static Logger logger = Logger.getLogger(LookupManager.class);
    private static PatientDataLookup[] patientData;
	private static CytobandLookup[] cytobands;
	private static Lookup[] pathways;
	private static ExpPlatformLookup[] expPlatforms;
	private static DiseaseTypeLookup[] diseaseTypes;
    private static GeneAliasMap aliasMap = null;
    private static Set geneSymbols = null;
    
	
	//Lookup Types
	private static final String CHROMOSOME_DE = "chromosomeDE";
	private static final String CYTOBAND_DE = "cytobandDE";
	private static final String CYTOBAND_POSITION = "cytobandPosition";
	private static final String DISEASE_TYPE = "diseaseType";
	private static final String DISEASE_TYPE_MAP = "diseaseTypeMap";
	private static final String EXP_PLATFORMS = "expPlatforms";
	private static final String GENE_SYMBOLS = "geneSymbols";
	private static final String PATIENT_DATA = "patientData";
	private static final String PATIENT_DATA_MAP = "patientDataMap";
	private static final String PATHWAYS = "pathways";
	private static final String ALLGENEALIAS = "AllGeneAlias";
	private static ConvenientCache cacheManagerDelegate;
	
	
	/**
	 * Performs the actual lookup query.  Gets the application
	 * PersistanceBroker and then passes to the 
	 * @param bean the lookup class 
	 * @param crit the criteria for the lookup
	 * @return the collection of lookup values
	 * @throws Exception
	 */
	 
	private  static Collection executeQuery(Class bean, Criteria crit, String lookupType)throws Exception{
		  
		cacheManagerDelegate = CacheManagerDelegate.getInstance();
		Collection resultsetObjs = cacheManagerDelegate.checkLookupCache(lookupType);
		if(resultsetObjs == null) {
			logger.debug("LookupType "+lookupType+" was not found in ApplicationCache");
			PersistenceBroker broker = PersistenceBrokerFactory.defaultPersistenceBroker();
			broker.clearCache();
		    resultsetObjs = createQuery(bean, crit, broker);
		    cacheManagerDelegate.addToApplicationCache(lookupType,(Serializable)resultsetObjs);
		    broker.close();
		    
		}else {
			logger.debug("LookupType "+lookupType+" found in ApplicationCache");
			
		}
	    return resultsetObjs;
	     
	}
	private static Collection createQuery(Class bean, Criteria crit, PersistenceBroker broker) throws Exception{
			//Criteria crit = new Criteria();
			Collection resultsetObjs = null;
	        Query exprQuery = QueryFactory.newQuery(bean, crit,true);
	        resultsetObjs = broker.getCollectionByQuery(exprQuery);
	        logger.debug("Got " + resultsetObjs.size() + " resultsetObjs objects.");
	        return resultsetObjs;
	}

	/**
	 * @return Returns the cytobands.
	 */
	public static CytobandLookup[] getCytobandPositions() throws Exception{
		
			Criteria crit = new Criteria();
			crit.addOrderByAscending("chrCytoband");
			cytobands = (CytobandLookup[]) executeQuery(CytobandPosition.class, crit,LookupManager.CYTOBAND_POSITION).toArray(new CytobandLookup[1]);
		
		return cytobands;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public static ChromosomeNumberDE[] getChromosomeDEs() throws Exception {
		Collection chromosomeDEs = new ArrayList();
		ChromosomeNumberDE chromosomeDE ;
		CytobandLookup[] cytobandLookups = getCytobandPositions();
		if(cytobandLookups != null){
			for (int i = 0;i < cytobandLookups.length;i++){
				chromosomeDE = new ChromosomeNumberDE(cytobandLookups[i].getChromosome());
				chromosomeDEs.add(chromosomeDE);			
			}
		}
		return (ChromosomeNumberDE[]) chromosomeDEs.toArray(new ChromosomeNumberDE[1]);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public static CytobandDE[] getCytobandDEs(ChromosomeNumberDE chromosomeDE) throws Exception {
		Collection cytobandDEs = new ArrayList();
		CytobandDE cytobandDE ;
		CytobandLookup[] cytobandLookups = getCytobandPositions();
		if(cytobandLookups != null){
			for (int i = 0;i < cytobandLookups.length;i++){
				if(chromosomeDE.getValue().toString().equals(cytobandLookups[i].getChromosome())){
					cytobandDE = new CytobandDE(cytobandLookups[i].getCytoband());
					cytobandDEs.add(cytobandDE);	
				}
			}
		}
		return (CytobandDE[]) cytobandDEs.toArray(new CytobandDE[1]);
	}
	/**
	 * @return Returns the pathways.
	 */
	public Lookup[] getPathways() {
		return pathways;
	}
	/**
	 * @return Returns the patientData.
	 * @throws Exception
	 */
	public static PatientDataLookup[] getPatientData() throws Exception {
		if(patientData == null){
			Criteria crit = new Criteria();
			patientData = (PatientDataLookup[])(executeQuery(PatientData.class,crit,LookupManager.PATIENT_DATA).toArray(new PatientDataLookup[1]));
		}
		return patientData;
	}
	/**
	 * @return Returns the patientDataMap.
	 * @throws Exception
	 * BiospecimenId is the key & PatientDataLookup is the returned object
	 */
	public static Map getPatientDataMap() throws Exception{
		PatientDataLookup[] patients = getPatientData();
		Map patientDataMap = new HashMap();
		if(patients != null){
			for (int i = 0;i < patients.length;i++){
				String key = patients[i].getBiospecimenId().toString();
				PatientDataLookup patient = patients[i];				
				patientDataMap.put(key,patient);				
			}
		}
		return patientDataMap;
		
	}
	/**
	 * @return Returns the diseaseTypes.
	 * @throws Exception
	 */
	public static DiseaseTypeLookup[] getDiseaseType() throws Exception {
		if(diseaseTypes == null){
			Criteria crit = new Criteria();
			crit.addOrderByAscending("diseaseTypeId");
			diseaseTypes = (DiseaseTypeLookup[])(executeQuery(DiseaseTypeDim.class,crit,LookupManager.DISEASE_TYPE).toArray(new DiseaseTypeLookup[1]));
		}
		return diseaseTypes;
	}
	/**
	 * @return Returns the patientDataMap.
	 * @throws Exception
	 * BiospecimenId is the key & PatientDataLookup is the returned object
	 */
	public static Map getDiseaseTypeMap() throws Exception{
		DiseaseTypeLookup[] diseases = getDiseaseType();
		Map patientDataMap = new HashMap();
		if(diseases != null){
			for (int i = 0;i < diseases.length;i++){
				String key = diseases[i].getDiseaseType().toString();
				DiseaseTypeLookup disease = diseases[i];				
				patientDataMap.put(key,disease);				
			}
		}
		return patientDataMap;
		
	}
	/**
	 * @return Returns the expPlatforms.
	 * @throws Exception
	 */
	public static ExpPlatformLookup[] getExpPlatforms() throws Exception {
		if(expPlatforms == null){
			Criteria crit = new Criteria();
			expPlatforms = (ExpPlatformLookup[]) executeQuery(ExpPlatformDim.class,crit,LookupManager.EXP_PLATFORMS).toArray(new ExpPlatformLookup[1]);
		}
		return expPlatforms;
	}
   
    private static void getAllGeneAlias() throws Exception{
    	if(aliasMap == null){
	        Criteria crit = new Criteria();
	        Collection allGeneAlias = executeQuery(AllGeneAlias.class, (Criteria)crit,LookupManager.ALLGENEALIAS);
	        aliasMap = new GeneAliasMap();
	        geneSymbols =  new HashSet();
	        for (Iterator iterator = allGeneAlias.iterator(); iterator.hasNext();) {
	        	AllGeneAlias geneAlias = (AllGeneAlias) iterator.next();
	        	aliasMap.addGenes(geneAlias);
	        	geneSymbols.add(geneAlias.getApprovedSymbol().trim());
	         }
    	}
    }
    public static boolean isGeneSymbolFound(String geneSymbol) throws Exception{
    	if(geneSymbols == null){
    		getAllGeneAlias();    		
    	}
    	if(geneSymbol != null){
	    	geneSymbol = geneSymbol.trim();
	    	if(geneSymbols.contains(geneSymbol) || geneSymbols.contains(geneSymbol.toUpperCase())){
	    		return true;
	    	}
    	}
    	return false;
    }
    public static AllGeneAliasLookup[] getGenesForAlias(String geneSymbol) throws Exception{
    	if(aliasMap == null){
    		getAllGeneAlias();
    	}
    	if(geneSymbol != null){
	    	geneSymbol = geneSymbol.trim();
	    	AllGeneAliasLookup[] geneAlias = aliasMap.getGenes(geneSymbol);
	    	if(geneAlias == null ){
	    	    geneAlias = aliasMap.getGenes(geneSymbol.toUpperCase());
	    	}
	    	return geneAlias;
    	}
    	return null;
    }
}
