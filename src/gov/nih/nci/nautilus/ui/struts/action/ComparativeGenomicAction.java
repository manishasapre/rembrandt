package gov.nih.nci.nautilus.ui.struts.action;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.criteria.AlleleFrequencyCriteria;
import gov.nih.nci.nautilus.criteria.AssayPlatformCriteria;
import gov.nih.nci.nautilus.criteria.CloneOrProbeIDCriteria;
import gov.nih.nci.nautilus.criteria.CopyNumberCriteria;
import gov.nih.nci.nautilus.criteria.DiseaseOrGradeCriteria;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.criteria.RegionCriteria;
import gov.nih.nci.nautilus.criteria.SNPCriteria;
import gov.nih.nci.nautilus.query.ComparativeGenomicQuery;
import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.query.QueryManager;
import gov.nih.nci.nautilus.query.QueryType;
import gov.nih.nci.nautilus.ui.helper.SessionQueryBag;
import gov.nih.nci.nautilus.ui.struts.form.ComparativeGenomicForm;
import gov.nih.nci.nautilus.view.ViewFactory;
import gov.nih.nci.nautilus.view.ViewType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ComparativeGenomicAction extends Action {
    private static Logger logger = Logger.getLogger(NautilusConstants.LOGGER);
    
    /**
     * Method execute
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
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        request.getSession().setAttribute("currentPage", "0");
        request.getSession().removeAttribute("currentPage2");
        ComparativeGenomicForm comparativeGenomicForm = (ComparativeGenomicForm) form;
        ComparativeGenomicQuery cghQuery = createCGHQuery(comparativeGenomicForm);
        //This is required as struts resets the form.  It is later added back to the request
        request.setAttribute("previewForm", comparativeGenomicForm.cloneMe());
        if (comparativeGenomicForm.getMethod().equals("run report")) {
            CompoundQuery compoundQuery = new CompoundQuery(cghQuery);
            compoundQuery.setAssociatedView(ViewFactory
                    .newView(ViewType.COPYNUMBER_GROUP_SAMPLE_VIEW));
            SessionQueryBag collection = new SessionQueryBag();
            collection.setCompoundQuery(compoundQuery);
            request.setAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY, collection);
            return mapping.findForward("previewReport");
        } else {
            try {
                //Set query in Session.
                if (!cghQuery.isEmpty()) {
                    // Get Hashmap from session if available
                    SessionQueryBag queryCollection = (SessionQueryBag) request
                            .getSession().getAttribute(
                                    NautilusConstants.SESSION_QUERY_BAG_KEY);
                    if (queryCollection == null) {
                        queryCollection = new SessionQueryBag();
                    }
                    queryCollection.putQuery(cghQuery);
                    request.getSession().setAttribute(
                            NautilusConstants.SESSION_QUERY_BAG_KEY, queryCollection);

                } else {

                    ActionErrors errors = new ActionErrors();
                    errors
                            .add(
                                    ActionErrors.GLOBAL_ERROR,
                                    new ActionError(
                                            "gov.nih.nci.nautilus.ui.struts.form.query.cgh.error"));
                    this.saveErrors(request, errors);
                    return mapping.findForward("backToCGHExp");
                }
            }
            catch (Exception e) {
               logger.error(e);
            }
            return mapping.findForward("advanceSearchMenu");
        }

    } 
    
    private ComparativeGenomicQuery createCGHQuery(ComparativeGenomicForm comparativeGenomicForm){
        //Create Query Objects
        ComparativeGenomicQuery cghQuery = (ComparativeGenomicQuery) QueryManager
                .createQuery(QueryType.CGH_QUERY_TYPE);
        cghQuery.setQueryName(comparativeGenomicForm.getQueryName());
        String thisView = comparativeGenomicForm.getResultView();
        // Change this code later to get view type directly from Form !!
        if (thisView.equalsIgnoreCase("sample")) {
            cghQuery.setAssociatedView(ViewFactory
                    .newView(ViewType.CLINICAL_VIEW));
        } else if (thisView.equalsIgnoreCase("gene")) {
            cghQuery.setAssociatedView(ViewFactory
                    .newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
        }

        // set Disease criteria
        DiseaseOrGradeCriteria diseaseOrGradeCriteria = comparativeGenomicForm
                .getDiseaseOrGradeCriteria();
        if (!diseaseOrGradeCriteria.isEmpty()) {
            cghQuery.setDiseaseOrGradeCrit(diseaseOrGradeCriteria);
        }

        // Set gene criteria
        GeneIDCriteria geneIDCrit = comparativeGenomicForm.getGeneIDCriteria();
        if (!geneIDCrit.isEmpty()) {
            cghQuery.setGeneIDCrit(geneIDCrit);
        }

        // set copy number criteria
        CopyNumberCriteria CopyNumberCrit = comparativeGenomicForm
                .getCopyNumberCriteria();
        if (!CopyNumberCrit.isEmpty()) {
            cghQuery.setCopyNumberCrit(CopyNumberCrit);
        }

        // set region criteria
        RegionCriteria regionCrit = comparativeGenomicForm.getRegionCriteria();
        if (!regionCrit.isEmpty()) {
            cghQuery.setRegionCrit(regionCrit);
        }

        // set clone/probe criteria
        CloneOrProbeIDCriteria cloneOrProbeIDCriteria = comparativeGenomicForm
                .getCloneOrProbeIDCriteria();
        if (!cloneOrProbeIDCriteria.isEmpty()) {
            cghQuery.setCloneOrProbeIDCrit(cloneOrProbeIDCriteria);
        }

        // set snp criteria
        SNPCriteria snpCrit = comparativeGenomicForm.getSNPCriteria();
        if (!snpCrit.isEmpty()) {
            cghQuery.setSNPCrit(snpCrit);
        }

        // set allele criteria
        AlleleFrequencyCriteria alleleFrequencyCriteria = comparativeGenomicForm
                .getAlleleFrequencyCriteria();
        if (!alleleFrequencyCriteria.isEmpty()) {
            cghQuery.setAlleleFrequencyCrit(alleleFrequencyCriteria);
        }

        AssayPlatformCriteria assayPlatformCriteria = comparativeGenomicForm
                .getAssayPlatformCriteria();
        if (!assayPlatformCriteria.isEmpty()) {
            cghQuery.setAssayPlatformCrit(assayPlatformCriteria);
        }
        return cghQuery;
    }
}