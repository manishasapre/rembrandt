package gov.nih.nci.rembrandt.queryservice.resultset.kaplanMeierPlot;

import gov.nih.nci.caintegrator.dto.de.BasePairPositionDE;
import gov.nih.nci.caintegrator.dto.de.CytobandDE;
import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.dto.lookup.PatientDataLookup;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.cgh.CopyNumber;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.UnifiedGeneExpr;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExpr.GeneExprSingle;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.UnifiedGeneExpr.UnifiedGeneExprSingle;
import gov.nih.nci.rembrandt.queryservice.resultset.ClinicalResultSet;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
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

public class KaplanMeierPlotHandler {
	/**
	 * @param geneExpr
	 * @return KaplanMeierPlotContainer
	 * @throws Exception
	 */
	public static KaplanMeierPlotContainer handleKMGeneExprPlotContainer(GeneExpr.GeneExprSingle[] geneExprObjects) throws Exception{
 		KaplanMeierPlotContainer kaplanMeierPlotContainer = new KaplanMeierPlotContainer();
 		if(geneExprObjects != null  ){
 			for (int i = 0; i < geneExprObjects.length; i++) {
 				if(geneExprObjects[i] != null) {
 					ResultSet obj = geneExprObjects[i];
 					if (obj instanceof GeneExpr.GeneExprSingle)  {
 						//Propulate the KaplanMeierPlotContainer
 						GeneExprSingle  exprObj = (GeneExpr.GeneExprSingle) obj;
 						kaplanMeierPlotContainer = handleKMGeneExpPlotContainer(kaplanMeierPlotContainer,exprObj);
 					}
 				}
          	}//for
 			if(geneExprObjects.length > 1){
	 	 		kaplanMeierPlotContainer.setGeneSymbol(new GeneIdentifierDE.GeneSymbol(geneExprObjects[0].getGeneSymbol()));
	 			Collection samples = kaplanMeierPlotContainer.getSampleResultsets();
	 			Map paitentDataLookup = LookupManager.getPatientDataMap();
		    	for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
		    		SampleKaplanMeierPlotResultset sample = (SampleKaplanMeierPlotResultset)sampleIterator.next();
		    		PatientDataLookup patient = (PatientDataLookup) paitentDataLookup.get(sample.getSampleIDDE().getValue().toString());
		    		if(patient != null  && patient.getSurvivalLength() != null && patient.getCensoringStatus()!=null){
	
			    		sample.setSurvivalLength(patient.getSurvivalLength());
			    		sample.setCensor(new DatumDE(DatumDE.CENSOR,patient.getCensoringStatus()));
			    		kaplanMeierPlotContainer.addSampleResultset(sample);  //update sample resultset
		    		}
		    	}	
 			}

 		}
        return kaplanMeierPlotContainer;

	}
	public static KaplanMeierPlotContainer handleKMCopyNumberPlotContainer(CopyNumber[] copyNumberObjects) throws Exception{
 		KaplanMeierPlotContainer kaplanMeierPlotContainer = new KaplanMeierPlotContainer();
 		if(copyNumberObjects != null){
 			for (int i = 0; i < copyNumberObjects.length; i++) {
 				if(copyNumberObjects[i] != null) {
 					ResultSet obj = copyNumberObjects[i];
 					if (obj instanceof CopyNumber)  {
 						//Propulate the KaplanMeierPlotContainer
 						CopyNumber  copyNumberObj = (CopyNumber) obj;
 						kaplanMeierPlotContainer = handleKMCopyNumberPlotContainer(kaplanMeierPlotContainer,copyNumberObj);
 					}
 				}
          	}//for
 			if(copyNumberObjects.length > 0){
	 	 		kaplanMeierPlotContainer.setCytobandDE(new CytobandDE(copyNumberObjects[0].getCytoband()));//TODO NEED GeneSymbol in CopyNumber
	 			Collection samples = kaplanMeierPlotContainer.getSampleResultsets();
	 			Map paitentDataLookup = LookupManager.getPatientDataMap();
		    	for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
		    		SampleKaplanMeierPlotResultset sample = (SampleKaplanMeierPlotResultset)sampleIterator.next();
		    		PatientDataLookup patient = (PatientDataLookup) paitentDataLookup.get(sample.getSampleIDDE().getValue().toString());
		    		if(patient != null){
			    		sample.setSurvivalLength(patient.getSurvivalLength());
			    		sample.setCensor(new DatumDE(DatumDE.CENSOR,patient.getCensoringStatus()));
			    		kaplanMeierPlotContainer.addSampleResultset(sample);  //update sample resultset
		    		}
		    	}
 			}

 		}
 		
        return kaplanMeierPlotContainer;

	}
	/**
	 * @param kaplanMeierPlotContainer
	 * @param copyNumberObj
	 * @return
	 */
	private static KaplanMeierPlotContainer handleKMCopyNumberPlotContainer(KaplanMeierPlotContainer kaplanMeierPlotContainer, CopyNumber copyNumberObj) {
		SampleKaplanMeierPlotResultset sampleResultset = null;
		ReporterResultset reporterResultset = null;
      	if (kaplanMeierPlotContainer != null && copyNumberObj != null){
      		kaplanMeierPlotContainer.setCytobandDE(new CytobandDE (copyNumberObj.getCytoband()));
      		sampleResultset = handleSampleKaplanMeierPlotResultset(kaplanMeierPlotContainer, copyNumberObj);
      		reporterResultset = handleCopyNumberReporterResultset(sampleResultset,copyNumberObj);
      		sampleResultset.addReporterResultset(reporterResultset);
      		kaplanMeierPlotContainer.addSampleResultset(sampleResultset); 
      	}
		return kaplanMeierPlotContainer;
	}
	private static ReporterResultset handleCopyNumberReporterResultset(SampleKaplanMeierPlotResultset sampleResultset, CopyNumber copyNumberObj) {
        // find out if it has a probeset or a clone associated with it
        //populate ReporterResultset with the approciate one
        ReporterResultset reporterResultset = null;
        if(sampleResultset != null && copyNumberObj != null){
            if(copyNumberObj.getSnpProbesetName() != null ){
                DatumDE reporter = new DatumDE(DatumDE.PROBESET_ID,copyNumberObj.getSnpProbesetName());
                reporterResultset = sampleResultset.getReporterResultset(copyNumberObj.getSnpProbesetName().toString());
                if(reporterResultset == null){
                    reporterResultset = new ReporterResultset(reporter);                    
                    }   
            }
            reporterResultset.setValue(new DatumDE(DatumDE.COPY_NUMBER,copyNumberObj.getCopyNumber()));
            reporterResultset.setStartPhysicalLocation(new BasePairPositionDE.StartPosition(copyNumberObj.getPhysicalPosition()));
            if(copyNumberObj.getAnnotations() != null){
                CopyNumber.SNPAnnotation annotation = copyNumberObj.getAnnotations();
                if(annotation.getAccessionNumbers()!= null){
                    reporterResultset.setAssiciatedGenBankAccessionNos(annotation.getAccessionNumbers());
                }
                if(annotation.getLocusLinkIDs()!=null){
                    reporterResultset.setAssiciatedLocusLinkIDs(copyNumberObj.getAnnotations().getLocusLinkIDs());
                }
                if(annotation.getGeneSymbols()!=null){
                    reporterResultset.setAssiciatedGeneSymbols(copyNumberObj.getAnnotations().getGeneSymbols());
                }
                
            }
            
        }
        return reporterResultset;
    }
    /**
	 * @param sampleResultset
	 * @param copyNumberObj
	 * @return
	 */

	/**
	 * @param kaplanMeierPlotContainer
	 * @param exprObj
	 * @return KaplanMeierPlotContainer
	 */
	private static KaplanMeierPlotContainer handleKMGeneExpPlotContainer(KaplanMeierPlotContainer kaplanMeierPlotContainer,GeneExpr.GeneExprSingle exprObj){
		SampleKaplanMeierPlotResultset sampleResultset = null;
		ReporterResultset reporterResultset = null;
      	if (kaplanMeierPlotContainer != null && exprObj != null){
      		kaplanMeierPlotContainer.setGeneSymbol(new GeneIdentifierDE.GeneSymbol (exprObj.getGeneSymbol()));
      		sampleResultset = handleSampleKaplanMeierPlotResultset(kaplanMeierPlotContainer, exprObj);
      		reporterResultset = handleReporterFoldChangeValuesResultset(sampleResultset,exprObj);
      		sampleResultset.addReporterResultset(reporterResultset);
      		kaplanMeierPlotContainer.addSampleResultset(sampleResultset); 
      	}
		return kaplanMeierPlotContainer;
	}
	/**
	 * @param sampleResultset
	 * @param exprObj
	 * @return
	 */
	private static ReporterResultset handleReporterFoldChangeValuesResultset(SampleKaplanMeierPlotResultset sampleResultset, GeneExprSingle exprObj) {
  		// find out if it has a probeset or a clone associated with it
  		//populate ReporterResultset with the approciate one
		ReporterResultset reporterResultset = null;
		if(sampleResultset != null && exprObj != null){
	    	if(exprObj.getProbesetName() != null){
	  			DatumDE reporter = new DatumDE(DatumDE.PROBESET_ID,exprObj.getProbesetName());
	       		reporterResultset = sampleResultset.getReporterResultset(reporter.getValue().toString());
	      		if(reporterResultset == null){
	      		 	reporterResultset = new ReporterResultset(reporter);
	      			}
	      		reporterResultset.setValue(new DatumDE(DatumDE.FOLD_CHANGE_RATIO,exprObj.getExpressionRatio()));
	    		}	  		
		}
        return reporterResultset;
	}

	/**
	 * @param kaplanMeierPlotContainer
	 * @param exprObj
	 * @return
	 */
	private static SampleKaplanMeierPlotResultset handleSampleKaplanMeierPlotResultset(KaplanMeierPlotContainer kaplanMeierPlotContainer, ClinicalResultSet clinicalObj) {
//		find out the sample associated with the exprObj
  		//populate the SampleKaplanMeierPlotResultset
		SampleKaplanMeierPlotResultset sampleResultset = null;
  		if(kaplanMeierPlotContainer != null && clinicalObj != null &&  clinicalObj.getSampleId() != null){
  			SampleIDDE sampleID = new SampleIDDE(clinicalObj.getSampleId());
  			sampleResultset = (SampleKaplanMeierPlotResultset) kaplanMeierPlotContainer.getSampleResultset(clinicalObj.getSampleId().toString());
  		    if (sampleResultset == null){
  		    	sampleResultset= new SampleKaplanMeierPlotResultset(sampleID);
  		    }
      	}
  		return sampleResultset;
	}
	public static ResultsContainer handleKMUnifiedGeneExprPlotContainer(UnifiedGeneExprSingle[] unifiedGeneExprObjects) throws Exception {
	 		KaplanMeierPlotContainer kaplanMeierPlotContainer = new KaplanMeierPlotContainer();
	 		if(unifiedGeneExprObjects != null  ){
	 			for (int i = 0; i < unifiedGeneExprObjects.length; i++) {
	 				if(unifiedGeneExprObjects[i] != null) {
	 					ResultSet obj = unifiedGeneExprObjects[i];
	 					if (obj instanceof UnifiedGeneExpr.UnifiedGeneExprSingle)  {
	 						//Propulate the KaplanMeierPlotContainer
	 						UnifiedGeneExpr.UnifiedGeneExprSingle  exprObj = (UnifiedGeneExpr.UnifiedGeneExprSingle) obj;
	 						kaplanMeierPlotContainer = handleKMUnifiedGeneExpPlotContainer(kaplanMeierPlotContainer,exprObj);
	 					}
	 				}
	          	}//for
	 			if(unifiedGeneExprObjects.length > 1){
		 	 		kaplanMeierPlotContainer.setGeneSymbol(new GeneIdentifierDE.GeneSymbol(unifiedGeneExprObjects[0].getGeneSymbol()));
		 			Collection samples = kaplanMeierPlotContainer.getSampleResultsets();
		 			Map paitentDataLookup = LookupManager.getPatientDataMap();
			    	for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
			    		SampleKaplanMeierPlotResultset sample = (SampleKaplanMeierPlotResultset)sampleIterator.next();
			    		PatientDataLookup patient = (PatientDataLookup) paitentDataLookup.get(sample.getSampleIDDE().getValue().toString());
			    		if(patient != null  && patient.getSurvivalLength() != null && patient.getCensoringStatus()!=null){
		
				    		sample.setSurvivalLength(patient.getSurvivalLength());
				    		sample.setCensor(new DatumDE(DatumDE.CENSOR,patient.getCensoringStatus()));
				    		kaplanMeierPlotContainer.addSampleResultset(sample);  //update sample resultset
			    		}
			    	}	
	 			}

	 		}
	        return kaplanMeierPlotContainer;

		}
	private static KaplanMeierPlotContainer handleKMUnifiedGeneExpPlotContainer(KaplanMeierPlotContainer kaplanMeierPlotContainer, UnifiedGeneExprSingle exprObj) {
		SampleKaplanMeierPlotResultset sampleResultset = null;
		ReporterResultset reporterResultset = null;
      	if (kaplanMeierPlotContainer != null && exprObj != null){
      		kaplanMeierPlotContainer.setGeneSymbol(new GeneIdentifierDE.GeneSymbol (exprObj.getGeneSymbol()));
      		sampleResultset = handleSampleKaplanMeierPlotResultset(kaplanMeierPlotContainer, exprObj);
      		reporterResultset = handleUnifiedReporterFoldChangeValuesResultset(sampleResultset,exprObj);
      		sampleResultset.addReporterResultset(reporterResultset);
      		kaplanMeierPlotContainer.addSampleResultset(sampleResultset); 
      	}
		return kaplanMeierPlotContainer;

	}
	private static ReporterResultset handleUnifiedReporterFoldChangeValuesResultset(SampleKaplanMeierPlotResultset sampleResultset, UnifiedGeneExprSingle exprObj) {
 		// find out if it has a probeset or a clone associated with it
  		//populate ReporterResultset with the approciate one
		ReporterResultset reporterResultset = null;
		if(sampleResultset != null && exprObj != null){
	    	if(exprObj.getUnifiedGeneID() != null){
	  			DatumDE reporter = new DatumDE(DatumDE.UNIFIED_GENE_ID,exprObj.getUnifiedGeneID());
	       		reporterResultset = sampleResultset.getReporterResultset(reporter.getValue().toString());
	      		if(reporterResultset == null){
	      		 	reporterResultset = new ReporterResultset(reporter);
	      			}
	      		reporterResultset.setValue(new DatumDE(DatumDE.FOLD_CHANGE_RATIO,exprObj.getExpressionRatio()));
	    		}	  		
		}
        return reporterResultset;
	}
	/**
	 * @param clinicalResultObjects
	 * @return
	 * @throws Exception
	 */
	public static KaplanMeierPlotContainer handleKMSamplePlotContainer(ClinicalResultSet[] clinicalObjects) throws Exception{
		KaplanMeierPlotContainer kaplanMeierPlotContainer = new KaplanMeierPlotContainer();
 		if(clinicalObjects != null){
 			for (int i = 0; i < clinicalObjects.length; i++) {
 				if(clinicalObjects[i] != null) {
 					ResultSet obj = (ResultSet) clinicalObjects[i];
 					if (obj != null && obj instanceof ClinicalResultSet)  {
 						//Propulate the KaplanMeierPlotContainer
 						ClinicalResultSet  clinicalObj = (ClinicalResultSet) obj;
 						SampleKaplanMeierPlotResultset sampleResultset = null;
			       		sampleResultset = handleSampleKaplanMeierPlotResultset(kaplanMeierPlotContainer, clinicalObj);
			      		kaplanMeierPlotContainer.addSampleResultset(sampleResultset); 
 				    }
 				}
          	}//for
 			if(clinicalObjects.length > 0){
	 			Collection samples = kaplanMeierPlotContainer.getSampleResultsets();
	 			Map paitentDataLookup = LookupManager.getPatientDataMap();
		    	for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
		    		SampleKaplanMeierPlotResultset sample = (SampleKaplanMeierPlotResultset)sampleIterator.next();
		    		PatientDataLookup patient = (PatientDataLookup) paitentDataLookup.get(sample.getSampleIDDE().getValue().toString());
		    		if(patient != null){
			    		sample.setSurvivalLength(patient.getSurvivalLength());
			    		sample.setCensor(new DatumDE(DatumDE.CENSOR,patient.getCensoringStatus()));
			    		kaplanMeierPlotContainer.addSampleResultset(sample);  //update sample resultset
		    		}
		    	}
 			}

 		}
 		
        return kaplanMeierPlotContainer;

	}
}
