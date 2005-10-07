package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.caintegrator.dto.critieria.AgeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.ChemoAgentCriteria;
import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GenderCriteria;
import gov.nih.nci.caintegrator.dto.critieria.OccurrenceCriteria;
import gov.nih.nci.caintegrator.dto.critieria.RadiationTherapyCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SurgeryTypeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SurvivalCriteria;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.rembrandt.cache.CacheManagerDelegate;
import gov.nih.nci.rembrandt.cache.ConvenientCache;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.queryservice.view.ViewFactory;
import gov.nih.nci.rembrandt.queryservice.view.ViewType;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;
import gov.nih.nci.rembrandt.web.helper.ReportGeneratorHelper;
import gov.nih.nci.rembrandt.web.struts.form.ClinicalDataForm;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

public class ClinicalDataAction extends LookupDispatchAction {
    private Logger logger = Logger.getLogger(ClinicalDataAction.class);
    private ConvenientCache cacheManager = CacheManagerDelegate.getInstance();
    
   //if multiUse button clicked (with styles de-activated) forward back to page
    public ActionForward multiUse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		return mapping.findForward("backToClinical");
    }
    
    /**
     * Method submittal
     * 
     * @param ActionMapping
     *            mapping
     * @param ActionForm
     *            form
     * @param HttpServletRequest
     *            request
     * @param HttpServletResponse
     *            response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward submittal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        request.getSession().setAttribute("currentPage", "0");
        request.getSession().removeAttribute("currentPage2");
        String sessionId = request.getSession().getId();
        ClinicalDataForm clinicalDataForm = (ClinicalDataForm) form;
        logger.debug("This is a Clinical Data Submittal");
        //Create Query Objects
        ClinicalDataQuery clinicalDataQuery = createClinicalDataQuery(clinicalDataForm);
        if (!clinicalDataQuery.isEmpty()) {
        	SessionQueryBag queryBag = cacheManager.getSessionQueryBag(sessionId);
            queryBag.putQuery(clinicalDataQuery, clinicalDataForm);
            cacheManager.putSessionQueryBag(sessionId, queryBag);
        }else{
            ActionErrors errors = new ActionErrors();
            ActionError error = new ActionError("gov.nih.nci.nautilus.ui.struts.form.query.cgh.error");
            errors.add(ActionErrors.GLOBAL_ERROR,error);
            this.saveErrors(request, errors);
            return mapping.findForward("backToClinical");
        }
        return mapping.findForward("advanceSearchMenu");
    }

    
    public ActionForward preview(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        request.getSession().setAttribute("currentPage", "0");
        request.getSession().removeAttribute("currentPage2");
                
        ClinicalDataForm clinicalDataForm = (ClinicalDataForm) form;
        
        logger.debug("This is a Clinical Data Preview");
        //Create Query Objects
        ClinicalDataQuery clinicalDataQuery = createClinicalDataQuery(clinicalDataForm);
        request.setAttribute("previewForm",clinicalDataForm.cloneMe());
        CompoundQuery compoundQuery = new CompoundQuery(clinicalDataQuery);
        compoundQuery.setQueryName(RembrandtConstants.PREVIEW_RESULTS);
        logger.debug("Setting query name to:"+compoundQuery.getQueryName());
        compoundQuery.setAssociatedView(ViewFactory
                .newView(ViewType.CLINICAL_VIEW));
        logger.debug("Associated View for the Preview:"+compoundQuery.getAssociatedView().getClass());
	    //Save the sessionId that this preview query is associated with
        compoundQuery.setSessionId(request.getSession().getId());
        //Generate the reportXML for the preview.  It will be stored in the session
	    //cache for later retrieval
        ReportGeneratorHelper reportHelper = new ReportGeneratorHelper(compoundQuery,new HashMap());
        return mapping.findForward("previewReport");
	}
        
    private ClinicalDataQuery createClinicalDataQuery(ClinicalDataForm clinicalDataForm){

        String thisView = clinicalDataForm.getResultView();
        // Create Query Objects
        ClinicalDataQuery clinicalDataQuery = (ClinicalDataQuery) QueryManager
                .createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
        clinicalDataQuery.setQueryName(clinicalDataForm.getQueryName());

        // Change this code later to get view type directly from Form !!
        if (thisView.equalsIgnoreCase("sample")) {
            clinicalDataQuery.setAssociatedView(ViewFactory
                    .newView(ViewType.CLINICAL_VIEW));
          //clinicalDataQuery.setAssociatedView(ViewFactory.newView(ViewType.SAMPLE_VIEW_TYPE));
        } else if (thisView.equalsIgnoreCase("gene")) {
            clinicalDataQuery.setAssociatedView(ViewFactory
                    .newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
            //clinicalDataQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_VIEW_TYPE));
        }
        
        // Set sample Criteria
        SampleCriteria sampleIDCrit = clinicalDataForm.getSampleCriteria();
		if (!sampleIDCrit.isEmpty())
		    clinicalDataQuery.setSampleIDCrit(sampleIDCrit);

        // Set disease criteria
        DiseaseOrGradeCriteria diseaseOrGradeCrit = clinicalDataForm
                .getDiseaseOrGradeCriteria();
        if (!diseaseOrGradeCrit.isEmpty()) {
            clinicalDataQuery.setDiseaseOrGradeCrit(diseaseOrGradeCrit);
        }

        // Set Occurrence criteria
        OccurrenceCriteria occurrenceCrit = clinicalDataForm
                .getOccurrenceCriteria();
        if (!occurrenceCrit.isEmpty()) {
            clinicalDataQuery.setOccurrenceCrit(occurrenceCrit);
        }

        // Set RadiationTherapy criteria
        RadiationTherapyCriteria radiationTherapyCrit = clinicalDataForm
                .getRadiationTherapyCriteria();
        if (!radiationTherapyCrit.isEmpty()) {
            clinicalDataQuery.setRadiationTherapyCrit(radiationTherapyCrit);
        }

        //Set ChemoAgent Criteria
        ChemoAgentCriteria chemoAgentCrit = clinicalDataForm
                .getChemoAgentCriteria();
        if (!chemoAgentCrit.isEmpty()) {
            clinicalDataQuery.setChemoAgentCrit(chemoAgentCrit);
        }

        // Set SurgeryType Criteria
        SurgeryTypeCriteria surgeryTypeCrit = clinicalDataForm
                .getSurgeryTypeCriteria();
        if (!surgeryTypeCrit.isEmpty()) {
            clinicalDataQuery.setSurgeryTypeCrit(surgeryTypeCrit);
        }

        // Set Survival Criteria
        SurvivalCriteria survivalCrit = clinicalDataForm.getSurvivalCriteria();
        if (!survivalCrit.isEmpty()) {
            clinicalDataQuery.setSurvivalCrit(survivalCrit);
        }

        // Set Age Criteria
        AgeCriteria ageCrit = clinicalDataForm.getAgeCriteria();
        if (!ageCrit.isEmpty()) {
            clinicalDataQuery.setAgeCrit(ageCrit);
        }

        // Set gender Criteria
        GenderCriteria genderCrit = clinicalDataForm.getGenderCriteria();
        if (!genderCrit.isEmpty()) {
            clinicalDataQuery.setGenderCrit(genderCrit);
        }
        return clinicalDataQuery;
    }
    
    protected Map getKeyMethodMap() {
		 
     HashMap map = new HashMap();
     
     //Submit Query Button using comparative genomic submittal method
     map.put("buttons_tile.submittalButton", "submittal");
     
     //Preview Query Button using comparative genomic preview method
     map.put("buttons_tile.previewButton", "preview");
     
     //Submit nothing if multiuse button entered if css turned off
     map.put("buttons_tile.multiUseButton", "multiUse");
     
     return map;
     
     }

}