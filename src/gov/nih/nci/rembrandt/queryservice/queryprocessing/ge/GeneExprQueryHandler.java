/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge;

import gov.nih.nci.caintegrator.dto.critieria.AllGenesCriteria;
import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Constants;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.view.ClinicalSampleView;
import gov.nih.nci.caintegrator.dto.view.CopyNumberSampleView;
import gov.nih.nci.caintegrator.dto.view.CopyNumberSegmentView;
import gov.nih.nci.caintegrator.dto.view.GeneExprDiseaseView;
import gov.nih.nci.caintegrator.dto.view.GeneExprSampleView;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.dto.query.Query;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ThreadController;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * @author BhattarR
 */


/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
*/

final public class GeneExprQueryHandler extends QueryHandler {
    GEFactHandler factHandler = null;
    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
    boolean includeClones;
    boolean includeProbes;
    private Collection allProbeIDS = Collections.synchronizedCollection(new HashSet());
    private Collection allCloneIDS = Collections.synchronizedCollection(new HashSet());
    private List eventList = Collections.synchronizedList(new ArrayList());

    public ResultSet[] handle(gov.nih.nci.rembrandt.dto.query.Query query) throws Exception {
    	GeneExpressionQuery geQuery = (GeneExpressionQuery) query;
    	
    	handleView(geQuery);
    	
        AllGenesCriteria allGenesCrit = geQuery.getAllGenesCrit();
        if (allGenesCrit!=null && allGenesCrit.isAllGenes() ) {

            if (! (factHandler instanceof GEFactHandler))
            throw new Exception("AllGenes criteria is not allowed for Disease view");

            if (null == geQuery.getSampleIDCrit())
                throw new Exception("Sample IDs are required when All Genes is specified");

            return factHandler.executeSampleQueryForAllGenes(geQuery);
        }
    	
    	handleCriteria(geQuery);
    	
        ThreadController.sleepOnEvents(eventList);

        return factHandler.executeSampleQuery(allProbeIDS, allCloneIDS, geQuery);
   }
    private void handleView(GeneExpressionQuery geQuery) throws Exception {
    	if (geQuery.getAssociatedView() instanceof GeneExprSampleView ||
        		geQuery.getAssociatedView()instanceof GeneExprDiseaseView ||
        		geQuery.getAssociatedView()instanceof ClinicalSampleView ||
        		geQuery.getAssociatedView()instanceof CopyNumberSegmentView ||
        		geQuery.getAssociatedView()instanceof CopyNumberSampleView)
                factHandler = new GEFactHandler();
        else throw new Exception("Illegal View.  This view is not supported in this Query:");

        
    }
    private void handleCriteria(GeneExpressionQuery geQuery) throws Exception {
    	//    	 make sure that platform (for the resulting smaples) is specified
        ArrayPlatformCriteria platObj = geQuery.getArrayPlatformCriteria();
        assert(platObj != null);
        populateProbeAndCloneIncludeFlags(platObj);                                             
          
        ThreadGroup tg = new ThreadGroup("childGroup");

        if (geQuery.getCloneOrProbeIDCriteria() != null) {
            GEReporterIDCriteria porbeClonePlatformCrit = CloneProbePlatfromHandler.buildCloneProbePlatformCriteria(geQuery.getCloneOrProbeIDCriteria(), platObj);
            assert(porbeClonePlatformCrit != null);
            SelectHandler handler = new SelectHandler.ProbeCloneIDSelectHandler(porbeClonePlatformCrit, allProbeIDS, allCloneIDS);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

        if (geQuery.getGeneIDCrit() != null && geQuery.getGeneIDCrit().getGeneIdentifiers().size() > 0) {
            GEReporterIDCriteria geneIDCrit = GeneIDCriteriaHandler.buildReporterIDCritForGEQuery(geQuery.getGeneIDCrit(), includeClones, includeProbes);
            assert(geneIDCrit != null);
            SelectHandler handler = new SelectHandler.GeneIDSelectHandler(geneIDCrit, allProbeIDS, allCloneIDS);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
         
        }

        if (geQuery.getRegionCrit() != null) {
            GEReporterIDCriteria regionCrit = gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.ChrRegionCriteriaHandler.buildGERegionCriteria(geQuery.getRegionCrit(), includeClones, includeProbes);
            assert(regionCrit != null);
            SelectHandler handler = new SelectHandler.RegionSelectHandler(regionCrit, allProbeIDS, allCloneIDS);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

        if (geQuery.getGeneOntologyCriteria() != null) {
            GEReporterIDCriteria ontologyCrit  = GeneOntologyHandler.buildGeneOntologyIDCriteria(geQuery.getGeneOntologyCriteria(), includeClones, includeProbes);
            assert(ontologyCrit != null);
            SelectHandler handler = new SelectHandler.OntologySelectHandler(ontologyCrit, allProbeIDS, allCloneIDS);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

        if (geQuery.getPathwayCriteria() != null) {
            GEReporterIDCriteria pathwayCrit = GenePathwayHandler.buildPathwayCriteria(geQuery.getPathwayCriteria(), includeClones, includeProbes);
            assert(pathwayCrit != null);
            SelectHandler handler = new SelectHandler.PathwaySelectHandler(pathwayCrit, allProbeIDS, allCloneIDS);
            eventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

        //_BROKER.close();

        ThreadController.sleepOnEvents(eventList);
   }

    private void populateProbeAndCloneIncludeFlags(ArrayPlatformCriteria platObj) throws Exception {
        if ((platObj != null) && platObj.getPlatform() != null) {
            ArrayPlatformDE platDE = platObj.getPlatform();
            if (platDE != null) {
                if (platDE.getValueObject().equalsIgnoreCase(Constants.ALL_PLATFROM) ) {
                    includeProbes = true;
                    includeClones = true;
                }
                else if (platDE.getValueObject().equalsIgnoreCase(Constants.AFFY_OLIGO_PLATFORM)) {
                    includeProbes = true;
                }
                else if (platDE.getValueObject().equalsIgnoreCase(Constants.CDNA_ARRAY_PLATFORM)) {
                    includeClones = true;
                }
            }
        }
        else throw new Exception("Array Platform can not be null");
    }
	public Integer getCount(Query query) throws Exception {
		//unimplemented for this release
			ResultSet[] resultset = handle(query);
			if(resultset != null){
				return resultset.length;
			}
			return null;
	}
}
