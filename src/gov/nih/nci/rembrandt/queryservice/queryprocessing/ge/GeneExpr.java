/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge;

import gov.nih.nci.rembrandt.queryservice.resultset.ClinicalResultSet;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;

import java.sql.Date;
import java.util.ArrayList;



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

abstract public class GeneExpr implements ResultSet{
    private Long cloneId;
    private Long diseaseTypeId;
    private String diseaseType;
    private Double expressionRatio;
    private String probesetName;
    private String cloneName;
    private String geneSymbol;
    private Double normalIntensity;
    private Double sampleIntensity;
    private Long probesetId;
    private Long timecourseId;
    private GeneExpr.Annotaion annotation;
    private String institutionName;
    private String specimenName;
    
    public abstract Long getID();

    public GeneExpr.Annotaion getAnnotation() {
        return annotation;
    }

    public void setAnnotation(GeneExpr.Annotaion annotation) {
        this.annotation= annotation;
    }

    public String getLocusLink() {
        return locusLink;
    }

    public void setLocusLink(String locusLink) {
        this.locusLink = locusLink;
    }

    private String locusLink;

    public Long getCloneId() {
        return cloneId;
    }

    public void setCloneId(Long cloneId) {
        this.cloneId = cloneId;
    }

    public Long getDiseaseTypeId() {
        return diseaseTypeId;
    }

    public void setDiseaseTypeId(Long diseaseTypeId) {
        this.diseaseTypeId = diseaseTypeId;
    }

    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }
    public Double getExpressionRatio() {
        return expressionRatio;
    }

    public void setExpressionRatio(Double expressionRatio) {
        this.expressionRatio = expressionRatio;
    }
    public String getProbesetName() {
        return probesetName;
    }

    public void setProbesetName(String probesetName) {
        this.probesetName = probesetName;
    }

    public String getCloneName() {
        return cloneName;
    }

    public void setCloneName(String cloneName) {
        this.cloneName = cloneName;
    }

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    /*

    public Long getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Long institutionId) {
        this.institutionId = institutionId;
    }
    */
    public Double getNormalIntensity() {
        return normalIntensity;
    }

    public void setNormalIntensity(Double normalIntensity) {
        this.normalIntensity = normalIntensity;
    }

    public Double getSampleIntensity() {
        return sampleIntensity;
    }

    public void setSampleIntensity(Double sampleIntensity) {
        this.sampleIntensity = sampleIntensity;
    }

    public Long getProbesetId() {
        return probesetId;
    }

    public void setProbesetId(Long probesetId) {
        this.probesetId = probesetId;
    }

    public Long getTimecourseId() {
        return timecourseId;
    }

    public void setTimecourseId(Long timecourseId) {
        this.timecourseId = timecourseId;
    }

    final public static class GeneExprSingle extends GeneExpr implements ClinicalResultSet, GeneExprSingleInterface{
        private Long 	 survivalLength;
        private Long age;
        private String 	 censor;
        private Long agentId;
        private String ageGroup;
        private Long biospecimenId;
        private Long desId;
        private Long diseaseHistoryId;
        private String survivalLengthRange;
        private String sampleId;
        private Long treatmentHistoryId;
        private String cytoband;
        private String genderCode;
        private String race;  
        private String whoGrade;
        private String timePoint;  
        private String timePoints;  

        private Date followupDate;  
        private String followupDates;   
        

        private Long followupMonth;
        private String followupMonths;

        private Date neuroEvaluationDate;   
        private String neuroEvaluationDates;
        
        private Long karnofskyScore;
        private String karnofskyScores;
        
        private Long lanskyScore;
        private String lanskyScores;
        
        private Long neuroExam;  
        private String neuroExams;  
        
        private Long mriCtScore;
        private String mriCtScores;
        
        private String steroidDoseStatus;
        private String steroidDoseStatuses;
        
        private String antiConvulsantStatus;
        private String antiConvulsantStatuses;
        
        private String priorRadiationTimePoints;
        private String priorRadiationRadiationSites;
        private String priorRadiationDoseStartDates;
        private String priorRadiationDoseStopDates;
        private String priorRadiationFractionDoses;
        private String priorRadiationFractionNumbers;
        private String priorRadiationRadiationTypes;
        
        
        private String priorChemoTimePoints;
        private String priorChemoagentIds ;
        private String priorChemoAgentNames ;							
        private String priorChemoCourseCounts ;
        private String priorChemoDoseStartDates;	
        private String priorChemoDoseStopDates ;
        private String priorChemoStudySources ;
        private String priorChemoProtocolNumbers ;
        
        private String priorSurgeryTimePoints ;
        private String priorSurgeryProcedureTitles ;
        private String priorSurgeryTumorHistologys ;							
        private String priorSurgerySurgeryDates ;
        private String priorSurgerySurgeryOutcomes ;	
        
        
        private String onStudyRadiationTimePoints;
        private String onStudyRadiationRadiationSites;
        private String onStudyRadiationDoseStartDates;
        private String onStudyRadiationDoseStopDates;
        private String onStudyRadiationFractionDoses;
        private String onStudyRadiationFractionNumbers;
        private String onStudyRadiationRadiationTypes;
        private String onStudyRadiationNeurosisStatuses;
        
        
        private String onStudyChemoTimePoints;
        private String onStudyChemoagentIds ;
        private String onStudyChemoAgentNames ;							
        private String onStudyChemoCourseCounts ;
        private String onStudyChemoDoseStartDates;	
        private String onStudyChemoDoseStopDates ;
        private String onStudyChemoStudySources ;
        private String onStudyChemoProtocolNumbers ;
        private String onStudyChemoRegimenNumbers;
        
        private String onStudySurgeryTimePoints ;
        private String onStudySurgeryProcedureTitles ;
        private String onStudySurgeryHistoDiagnoses ;							
        private String onStudySurgerySurgeryDates ;
        private String onStudySurgerySurgeryOutcomes ;
        private String onStudySurgeryIndications ;      
        
        
        
        
        
        /**
		 * @return Returns the onStudyChemoagentIds.
		 */
		public String getOnStudyChemoagentIds() {
			return onStudyChemoagentIds;
		}
		/**
		 * @param onStudyChemoagentIds The onStudyChemoagentIds to set.
		 */
		public void setOnStudyChemoagentIds(String onStudyChemoagentIds) {
			this.onStudyChemoagentIds = onStudyChemoagentIds;
		}
		/**
		 * @return Returns the onStudyChemoAgentNames.
		 */
		public String getOnStudyChemoAgentNames() {
			return onStudyChemoAgentNames;
		}
		/**
		 * @param onStudyChemoAgentNames The onStudyChemoAgentNames to set.
		 */
		public void setOnStudyChemoAgentNames(String onStudyChemoAgentNames) {
			this.onStudyChemoAgentNames = onStudyChemoAgentNames;
		}
		/**
		 * @return Returns the onStudyChemoCourseCounts.
		 */
		public String getOnStudyChemoCourseCounts() {
			return onStudyChemoCourseCounts;
		}
		/**
		 * @param onStudyChemoCourseCounts The onStudyChemoCourseCounts to set.
		 */
		public void setOnStudyChemoCourseCounts(String onStudyChemoCourseCounts) {
			this.onStudyChemoCourseCounts = onStudyChemoCourseCounts;
		}
		/**
		 * @return Returns the onStudyChemoDoseStartDates.
		 */
		public String getOnStudyChemoDoseStartDates() {
			return onStudyChemoDoseStartDates;
		}
		/**
		 * @param onStudyChemoDoseStartDates The onStudyChemoDoseStartDates to set.
		 */
		public void setOnStudyChemoDoseStartDates(String onStudyChemoDoseStartDates) {
			this.onStudyChemoDoseStartDates = onStudyChemoDoseStartDates;
		}
		/**
		 * @return Returns the onStudyChemoDoseStopDates.
		 */
		public String getOnStudyChemoDoseStopDates() {
			return onStudyChemoDoseStopDates;
		}
		/**
		 * @param onStudyChemoDoseStopDates The onStudyChemoDoseStopDates to set.
		 */
		public void setOnStudyChemoDoseStopDates(String onStudyChemoDoseStopDates) {
			this.onStudyChemoDoseStopDates = onStudyChemoDoseStopDates;
		}
		/**
		 * @return Returns the onStudyChemoProtocolNumbers.
		 */
		public String getOnStudyChemoProtocolNumbers() {
			return onStudyChemoProtocolNumbers;
		}
		/**
		 * @param onStudyChemoProtocolNumbers The onStudyChemoProtocolNumbers to set.
		 */
		public void setOnStudyChemoProtocolNumbers(String onStudyChemoProtocolNumbers) {
			this.onStudyChemoProtocolNumbers = onStudyChemoProtocolNumbers;
		}
		/**
		 * @return Returns the onStudyChemoRegimenNumbers.
		 */
		public String getOnStudyChemoRegimenNumbers() {
			return onStudyChemoRegimenNumbers;
		}
		/**
		 * @param onStudyChemoRegimenNumbers The onStudyChemoRegimenNumbers to set.
		 */
		public void setOnStudyChemoRegimenNumbers(String onStudyChemoRegimenNumbers) {
			this.onStudyChemoRegimenNumbers = onStudyChemoRegimenNumbers;
		}
		/**
		 * @return Returns the onStudyChemoStudySources.
		 */
		public String getOnStudyChemoStudySources() {
			return onStudyChemoStudySources;
		}
		/**
		 * @param onStudyChemoStudySources The onStudyChemoStudySources to set.
		 */
		public void setOnStudyChemoStudySources(String onStudyChemoStudySources) {
			this.onStudyChemoStudySources = onStudyChemoStudySources;
		}
		/**
		 * @return Returns the onStudyChemoTimePoints.
		 */
		public String getOnStudyChemoTimePoints() {
			return onStudyChemoTimePoints;
		}
		/**
		 * @param onStudyChemoTimePoints The onStudyChemoTimePoints to set.
		 */
		public void setOnStudyChemoTimePoints(String onStudyChemoTimePoints) {
			this.onStudyChemoTimePoints = onStudyChemoTimePoints;
		}
		/**
		 * @return Returns the onStudyRadiationDoseStartDates.
		 */
		public String getOnStudyRadiationDoseStartDates() {
			return onStudyRadiationDoseStartDates;
		}
		/**
		 * @param onStudyRadiationDoseStartDates The onStudyRadiationDoseStartDates to set.
		 */
		public void setOnStudyRadiationDoseStartDates(
				String onStudyRadiationDoseStartDates) {
			this.onStudyRadiationDoseStartDates = onStudyRadiationDoseStartDates;
		}
		/**
		 * @return Returns the onStudyRadiationDoseStopDates.
		 */
		public String getOnStudyRadiationDoseStopDates() {
			return onStudyRadiationDoseStopDates;
		}
		/**
		 * @param onStudyRadiationDoseStopDates The onStudyRadiationDoseStopDates to set.
		 */
		public void setOnStudyRadiationDoseStopDates(
				String onStudyRadiationDoseStopDates) {
			this.onStudyRadiationDoseStopDates = onStudyRadiationDoseStopDates;
		}
		/**
		 * @return Returns the onStudyRadiationFractionDoses.
		 */
		public String getOnStudyRadiationFractionDoses() {
			return onStudyRadiationFractionDoses;
		}
		/**
		 * @param onStudyRadiationFractionDoses The onStudyRadiationFractionDoses to set.
		 */
		public void setOnStudyRadiationFractionDoses(
				String onStudyRadiationFractionDoses) {
			this.onStudyRadiationFractionDoses = onStudyRadiationFractionDoses;
		}
		/**
		 * @return Returns the onStudyRadiationFractionNumbers.
		 */
		public String getOnStudyRadiationFractionNumbers() {
			return onStudyRadiationFractionNumbers;
		}
		/**
		 * @param onStudyRadiationFractionNumbers The onStudyRadiationFractionNumbers to set.
		 */
		public void setOnStudyRadiationFractionNumbers(
				String onStudyRadiationFractionNumbers) {
			this.onStudyRadiationFractionNumbers = onStudyRadiationFractionNumbers;
		}
		/**
		 * @return Returns the onStudyRadiationNeurosisStatuses.
		 */
		public String getOnStudyRadiationNeurosisStatuses() {
			return onStudyRadiationNeurosisStatuses;
		}
		/**
		 * @param onStudyRadiationNeurosisStatuses The onStudyRadiationNeurosisStatuses to set.
		 */
		public void setOnStudyRadiationNeurosisStatuses(
				String onStudyRadiationNeurosisStatuses) {
			this.onStudyRadiationNeurosisStatuses = onStudyRadiationNeurosisStatuses;
		}
		/**
		 * @return Returns the onStudyRadiationRadiationSites.
		 */
		public String getOnStudyRadiationRadiationSites() {
			return onStudyRadiationRadiationSites;
		}
		/**
		 * @param onStudyRadiationRadiationSites The onStudyRadiationRadiationSites to set.
		 */
		public void setOnStudyRadiationRadiationSites(
				String onStudyRadiationRadiationSites) {
			this.onStudyRadiationRadiationSites = onStudyRadiationRadiationSites;
		}
		/**
		 * @return Returns the onStudyRadiationRadiationTypes.
		 */
		public String getOnStudyRadiationRadiationTypes() {
			return onStudyRadiationRadiationTypes;
		}
		/**
		 * @param onStudyRadiationRadiationTypes The onStudyRadiationRadiationTypes to set.
		 */
		public void setOnStudyRadiationRadiationTypes(
				String onStudyRadiationRadiationTypes) {
			this.onStudyRadiationRadiationTypes = onStudyRadiationRadiationTypes;
		}
		/**
		 * @return Returns the onStudyRadiationTimePoints.
		 */
		public String getOnStudyRadiationTimePoints() {
			return onStudyRadiationTimePoints;
		}
		/**
		 * @param onStudyRadiationTimePoints The onStudyRadiationTimePoints to set.
		 */
		public void setOnStudyRadiationTimePoints(String onStudyRadiationTimePoints) {
			this.onStudyRadiationTimePoints = onStudyRadiationTimePoints;
		}
		/**
		 * @return Returns the onStudySurgeryHistoDiagnoses.
		 */
		public String getOnStudySurgeryHistoDiagnoses() {
			return onStudySurgeryHistoDiagnoses;
		}
		/**
		 * @param onStudySurgeryHistoDiagnoses The onStudySurgeryHistoDiagnoses to set.
		 */
		public void setOnStudySurgeryHistoDiagnoses(String onStudySurgeryHistoDiagnoses) {
			this.onStudySurgeryHistoDiagnoses = onStudySurgeryHistoDiagnoses;
		}
		/**
		 * @return Returns the onStudySurgeryIndications.
		 */
		public String getOnStudySurgeryIndications() {
			return onStudySurgeryIndications;
		}
		/**
		 * @param onStudySurgeryIndications The onStudySurgeryIndications to set.
		 */
		public void setOnStudySurgeryIndications(String onStudySurgeryIndications) {
			this.onStudySurgeryIndications = onStudySurgeryIndications;
		}
		/**
		 * @return Returns the onStudySurgeryProcedureTitles.
		 */
		public String getOnStudySurgeryProcedureTitles() {
			return onStudySurgeryProcedureTitles;
		}
		/**
		 * @param onStudySurgeryProcedureTitles The onStudySurgeryProcedureTitles to set.
		 */
		public void setOnStudySurgeryProcedureTitles(
				String onStudySurgeryProcedureTitles) {
			this.onStudySurgeryProcedureTitles = onStudySurgeryProcedureTitles;
		}
		/**
		 * @return Returns the onStudySurgerySurgeryDates.
		 */
		public String getOnStudySurgerySurgeryDates() {
			return onStudySurgerySurgeryDates;
		}
		/**
		 * @param onStudySurgerySurgeryDates The onStudySurgerySurgeryDates to set.
		 */
		public void setOnStudySurgerySurgeryDates(String onStudySurgerySurgeryDates) {
			this.onStudySurgerySurgeryDates = onStudySurgerySurgeryDates;
		}
		/**
		 * @return Returns the onStudySurgerySurgeryOutcomes.
		 */
		public String getOnStudySurgerySurgeryOutcomes() {
			return onStudySurgerySurgeryOutcomes;
		}
		/**
		 * @param onStudySurgerySurgeryOutcomes The onStudySurgerySurgeryOutcomes to set.
		 */
		public void setOnStudySurgerySurgeryOutcomes(
				String onStudySurgerySurgeryOutcomes) {
			this.onStudySurgerySurgeryOutcomes = onStudySurgerySurgeryOutcomes;
		}
		/**
		 * @return Returns the onStudySurgeryTimePoints.
		 */
		public String getOnStudySurgeryTimePoints() {
			return onStudySurgeryTimePoints;
		}
		/**
		 * @param onStudySurgeryTimePoints The onStudySurgeryTimePoints to set.
		 */
		public void setOnStudySurgeryTimePoints(String onStudySurgeryTimePoints) {
			this.onStudySurgeryTimePoints = onStudySurgeryTimePoints;
		}
		public  String getPriorRadiationTimePoints() {
        	return priorRadiationTimePoints;
        }
    	public  String getPriorRadiationRadiationSites() {
    		return  priorRadiationRadiationSites;
    	}
    	public  String getPriorRadiationDoseStartDates(){
    		return priorRadiationDoseStartDates;
    	}
    	public  String getPriorRadiationDoseStopDates(){
    		return priorRadiationDoseStopDates;
    	}
    	public  String getPriorRadiationFractionDoses(){
    		return priorRadiationFractionDoses;
    	}
    	public  String getPriorRadiationFractionNumbers(){
    		return priorRadiationFractionNumbers;
    	}
    	public  String getPriorRadiationRadiationTypes(){
    		return priorRadiationRadiationTypes;
    	}
    	
    	public String getPriorChemoTimePoints(){
    		return priorChemoTimePoints;
    	}
    	public String getPriorChemoagentIds(){
    		return priorChemoagentIds;
    	}
    	public String getPriorChemoAgentNames(){
    		return priorChemoAgentNames;
    	}
    	public String getPriorChemoCourseCounts() {
    		return priorChemoCourseCounts;
    	}
    	public String getPriorChemoDoseStartDates() {
    		return priorChemoDoseStartDates;
    	}
    	public String getPriorChemoDoseStopDates(){
    		return priorChemoDoseStopDates;
    	}
    	public String getPriorChemoStudySources() {
    		return priorChemoStudySources;
    	}
    	public String getPriorChemoProtocolNumbers(){
    		return priorChemoProtocolNumbers;
    	}
    	
    	public String getPriorSurgeryTimePoints(){
    		return priorSurgeryTimePoints;
    	}
    	public String getPriorSurgeryProcedureTitles(){
    		return priorSurgeryProcedureTitles;
    	}
    	public String getPriorSurgeryTumorHistologys() {
    		return priorSurgeryTumorHistologys;
    	}
    	public String getPriorSurgerySurgeryDates(){
    		return priorSurgerySurgeryDates;
    	}
    	public String getPriorSurgerySurgeryOutcomes(){
    		return priorSurgerySurgeryOutcomes;
    	}
        
        
        
        
        

        
        
        
        

        /**
		 * @param priorChemoagentIds The priorChemoagentIds to set.
		 */
		public void setPriorChemoagentIds(String priorChemoagentIds) {
			this.priorChemoagentIds = priorChemoagentIds;
		}
		/**
		 * @param priorChemoAgentNames The priorChemoAgentNames to set.
		 */
		public void setPriorChemoAgentNames(String priorChemoAgentNames) {
			this.priorChemoAgentNames = priorChemoAgentNames;
		}
		/**
		 * @param priorChemoCourseCounts The priorChemoCourseCounts to set.
		 */
		public void setPriorChemoCourseCounts(String priorChemoCourseCounts) {
			this.priorChemoCourseCounts = priorChemoCourseCounts;
		}
		/**
		 * @param priorChemoDoseStartDates The priorChemoDoseStartDates to set.
		 */
		public void setPriorChemoDoseStartDates(String priorChemoDoseStartDates) {
			this.priorChemoDoseStartDates = priorChemoDoseStartDates;
		}
		/**
		 * @param priorChemoDoseStopDates The priorChemoDoseStopDates to set.
		 */
		public void setPriorChemoDoseStopDates(String priorChemoDoseStopDates) {
			this.priorChemoDoseStopDates = priorChemoDoseStopDates;
		}
		/**
		 * @param priorChemoProtocolNumbers The priorChemoProtocolNumbers to set.
		 */
		public void setPriorChemoProtocolNumbers(String priorChemoProtocolNumbers) {
			this.priorChemoProtocolNumbers = priorChemoProtocolNumbers;
		}
		/**
		 * @param priorChemoStudySources The priorChemoStudySources to set.
		 */
		public void setPriorChemoStudySources(String priorChemoStudySources) {
			this.priorChemoStudySources = priorChemoStudySources;
		}
		/**
		 * @param priorChemoTimePoints The priorChemoTimePoints to set.
		 */
		public void setPriorChemoTimePoints(String priorChemoTimePoints) {
			this.priorChemoTimePoints = priorChemoTimePoints;
		}
		/**
		 * @param priorRadiationDoseStartDates The priorRadiationDoseStartDates to set.
		 */
		public void setPriorRadiationDoseStartDates(String priorRadiationDoseStartDates) {
			this.priorRadiationDoseStartDates = priorRadiationDoseStartDates;
		}
		/**
		 * @param priorRadiationDoseStopDates The priorRadiationDoseStopDates to set.
		 */
		public void setPriorRadiationDoseStopDates(String priorRadiationDoseStopDates) {
			this.priorRadiationDoseStopDates = priorRadiationDoseStopDates;
		}
		/**
		 * @param priorRadiationFractionDoses The priorRadiationFractionDoses to set.
		 */
		public void setPriorRadiationFractionDoses(String priorRadiationFractionDoses) {
			this.priorRadiationFractionDoses = priorRadiationFractionDoses;
		}
		/**
		 * @param priorRadiationFractionNumbers The priorRadiationFractionNumbers to set.
		 */
		public void setPriorRadiationFractionNumbers(
				String priorRadiationFractionNumbers) {
			this.priorRadiationFractionNumbers = priorRadiationFractionNumbers;
		}
		/**
		 * @param priorRadiationRadiationSites The priorRadiationRadiationSites to set.
		 */
		public void setPriorRadiationRadiationSites(String priorRadiationRadiationSites) {
			this.priorRadiationRadiationSites = priorRadiationRadiationSites;
		}
		/**
		 * @param priorRadiationRadiationTypes The priorRadiationRadiationTypes to set.
		 */
		public void setPriorRadiationRadiationTypes(String priorRadiationRadiationTypes) {
			this.priorRadiationRadiationTypes = priorRadiationRadiationTypes;
		}
		/**
		 * @param priorRadiationTimePoints The priorRadiationTimePoints to set.
		 */
		public void setPriorRadiationTimePoints(String priorRadiationTimePoints) {
			this.priorRadiationTimePoints = priorRadiationTimePoints;
		}
		/**
		 * @param priorSurgeryProcedureTitles The priorSurgeryProcedureTitles to set.
		 */
		public void setPriorSurgeryProcedureTitles(String priorSurgeryProcedureTitles) {
			this.priorSurgeryProcedureTitles = priorSurgeryProcedureTitles;
		}
		/**
		 * @param priorSurgerySurgeryDates The priorSurgerySurgeryDates to set.
		 */
		public void setPriorSurgerySurgeryDates(String priorSurgerySurgeryDates) {
			this.priorSurgerySurgeryDates = priorSurgerySurgeryDates;
		}
		/**
		 * @param priorSurgerySurgeryOutcomes The priorSurgerySurgeryOutcomes to set.
		 */
		public void setPriorSurgerySurgeryOutcomes(String priorSurgerySurgeryOutcomes) {
			this.priorSurgerySurgeryOutcomes = priorSurgerySurgeryOutcomes;
		}
		/**
		 * @param priorSurgeryTimePoints The priorSurgeryTimePoints to set.
		 */
		public void setPriorSurgeryTimePoints(String priorSurgeryTimePoints) {
			this.priorSurgeryTimePoints = priorSurgeryTimePoints;
		}
		/**
		 * @param priorSurgeryTumorHistologys The priorSurgeryTumorHistologys to set.
		 */
		public void setPriorSurgeryTumorHistologys(String priorSurgeryTumorHistologys) {
			this.priorSurgeryTumorHistologys = priorSurgeryTumorHistologys;
		}
		/**
		 * @return Returns the antiConvulsantStatuses.
		 */
		public String getAntiConvulsantStatuses() {
			return antiConvulsantStatuses;
		}
		/**
		 * @param antiConvulsantStatuses The antiConvulsantStatuses to set.
		 */
		public void setAntiConvulsantStatuses(String antiConvulsantStatuses) {
			this.antiConvulsantStatuses = antiConvulsantStatuses;
		}
		/**
		 * @return Returns the followupMonths.
		 */
		public String getFollowupMonths() {
			return followupMonths;
		}
		/**
		 * @param followupMonths The followupMonths to set.
		 */
		public void setFollowupMonths(String followupMonths) {
			this.followupMonths = followupMonths;
		}
		/**
		 * @return Returns the karnofskyScores.
		 */
		public String getKarnofskyScores() {
			return karnofskyScores;
		}
		/**
		 * @param karnofskyScores The karnofskyScores to set.
		 */
		public void setKarnofskyScores(String karnofskyScores) {
			this.karnofskyScores = karnofskyScores;
		}
		/**
		 * @return Returns the lanskyScores.
		 */
		public String getLanskyScores() {
			return lanskyScores;
		}
		/**
		 * @param lanskyScores The lanskyScores to set.
		 */
		public void setLanskyScores(String lanskyScores) {
			this.lanskyScores = lanskyScores;
		}
		/**
		 * @return Returns the mriCtScores.
		 */
		public String getMriCtScores() {
			return mriCtScores;
		}
		/**
		 * @param mriCtScores The mriCtScores to set.
		 */
		public void setMriCtScores(String mriCtScores) {
			this.mriCtScores = mriCtScores;
		}
		/**
		 * @return Returns the neuroEvaluationDates.
		 */
		public String getNeuroEvaluationDates() {
			return neuroEvaluationDates;
		}
		/**
		 * @param neuroEvaluationDates The neuroEvaluationDates to set.
		 */
		public void setNeuroEvaluationDates(String neuroEvaluationDates) {
			this.neuroEvaluationDates = neuroEvaluationDates;
		}
		/**
		 * @return Returns the neuroExams.
		 */
		public String getNeuroExams() {
			return neuroExams;
		}
		/**
		 * @param neuroExams The neuroExams to set.
		 */
		public void setNeuroExams(String neuroExams) {
			this.neuroExams = neuroExams;
		}
		/**
		 * @return Returns the steroidDoseStatuses.
		 */
		public String getSteroidDoseStatuses() {
			return steroidDoseStatuses;
		}
		/**
		 * @param steroidDoseStatuses The steroidDoseStatuses to set.
		 */
		public void setSteroidDoseStatuses(String steroidDoseStatuses) {
			this.steroidDoseStatuses = steroidDoseStatuses;
		}
		/**
		 * @return Returns the timePoints.
		 */
		public String getTimePoints() {
			return timePoints;
		}
		/**
		 * @param timePoints The timePoints to set.
		 */
		public void setTimePoints(String timePoints) {
			this.timePoints = timePoints;
		}
		/**
		 * @return Returns the followupDates.
		 */
		public String getFollowupDates() {
			return followupDates;
		}
		/**
		 * @param followupDates The followupDates to set.
		 */
		public void setFollowupDates(String followupDates) {
			this.followupDates = followupDates;
		}
		/**
		 * @return Returns the followupDate.
		 */
		public Date getFollowupDate() {
			return followupDate;
		}
		/**
		 * @param followupDate The followupDate to set.
		 */
		public void setFollowupDate(Date followupDate) {
			this.followupDate = followupDate;
		}
		/**
		 * @return Returns the timePoint.
		 */
		public String getTimePoint() {
			return timePoint;
		}
		/**
		 * @param timePoint The timePoint to set.
		 */
		public void setTimePoint(String timePoint) {
			this.timePoint = timePoint;
		}
		/**
		 * @return Returns the mriCtScore.
		 */
		public Long getMriCtScore() {
			return mriCtScore;
		}
		/**
		 * @param mriCtScore The mriCtScore to set.
		 */
		public void setMriCtScore(Long mriCtScore) {
			this.mriCtScore = mriCtScore;
		}
		/**
		 * @return Returns the karnofskyScore.
		 */
		public Long getKarnofskyScore() {
			return karnofskyScore;
		}
		/**
		 * @param karnofskyScore The karnofskyScore to set.
		 */
		public void setKarnofskyScore(Long karnofskyScore) {
			this.karnofskyScore = karnofskyScore;
		}
		/**
		 * @return Returns the lanskyScore.
		 */
		public Long getLanskyScore() {
			return lanskyScore;
		}
		/**
		 * @param lanskyScore The lanskyScore to set.
		 */
		public void setLanskyScore(Long lanskyScore) {
			this.lanskyScore = lanskyScore;
		}
		/**
		 * @return Returns the neuroExam.
		 */
		public Long getNeuroExam() {
			return neuroExam;
		}
		/**
		 * @param neuroExam The neuroExam to set.
		 */
		public void setNeuroExam(Long neuroExam) {
			this.neuroExam = neuroExam;
		}
		/**
		 * @return Returns the race.
		 */
		public String getRace() {
			return race;
		}
		/**
		 * @param race The race to set.
		 */
		public void setRace(String race) {
			this.race = race;
		}
		public String getSampleId() {
            return sampleId;
        }
        public void setSampleId(String sampleId) {
            this.sampleId = sampleId;
        }
        public Long getID() {
            return getDesId();
        }
        
        /**
		 * @return Returns the whoGrade.
		 */
		public String getWhoGrade() {
			return whoGrade;
		}
		/**
		 * @param whoGrade The whoGrade to set.
		 */
		public void setWhoGrade(String whoGrade) {
			this.whoGrade = whoGrade;
		}
		public Long getAgentId() {
            return agentId;
        }
        public void setAgentId(Long agentId) {
            this.agentId = agentId;
        }

        public String getAgeGroup() {
            return ageGroup;
        }

        public void setAgeGroup(String ageGroup) {
            this.ageGroup = ageGroup;
        }

        public Long getBiospecimenId() {
            return biospecimenId;
        }

        public void setBiospecimenId(Long biospecimenId) {
            this.biospecimenId = biospecimenId;
        }

        public Long getDesId() {
            return desId;
        }

        public void setDesId(Long desId) {
            this.desId = desId;
        }

        public Long getDiseaseHistoryId() {
            return diseaseHistoryId;
        }

        public void setDiseaseHistoryId(Long diseaseHistoryId) {
            this.diseaseHistoryId = diseaseHistoryId;
        }

        public String getSurvivalLengthRange() {
            return survivalLengthRange;
        }

        public void setSurvivalLengthRange(String survivalLengthRange) {
            this.survivalLengthRange = survivalLengthRange;
        }

        public Long getTreatmentHistoryId() {
            return treatmentHistoryId;
        }

        public void setTreatmentHistoryId(Long treatmentHistoryId) {
            this.treatmentHistoryId = treatmentHistoryId;
        }

        public String getCytoband() {
            return cytoband;
        }

        public void setCytoband(String cytoband) {
            this.cytoband = cytoband;
        }

        public String getGenderCode() {
            return genderCode;
        }

        public void setGenderCode(String genderCode) {
            this.genderCode = genderCode;
        }
		/**
		 * @return Returns the antiConvulsantStatus.
		 */
		public String getAntiConvulsantStatus() {
			return antiConvulsantStatus;
		}
		/**
		 * @param antiConvulsantStatus The antiConvulsantStatus to set.
		 */
		public void setAntiConvulsantStatus(String antiConvulsantStatus) {
			this.antiConvulsantStatus = antiConvulsantStatus;
		}
		/**
		 * @return Returns the followupMonth.
		 */
		public Long getFollowupMonth() {
			return followupMonth;
		}
		/**
		 * @param followupMonth The followupMonth to set.
		 */
		public void setFollowupMonth(Long followupMonth) {
			this.followupMonth = followupMonth;
		}
		/**
		 * @return Returns the neuroEvaluationDate.
		 */
		public Date getNeuroEvaluationDate() {
			return neuroEvaluationDate;
		}
		/**
		 * @param neuroEvaluationDate The neuroEvaluationDate to set.
		 */
		public void setNeuroEvaluationDate(Date neuroEvaluationDate) {
			this.neuroEvaluationDate = neuroEvaluationDate;
		}
		/**
		 * @return Returns the steroidDoseStatus.
		 */
		public String getSteroidDoseStatus() {
			return steroidDoseStatus;
		}
		/**
		 * @param steroidDoseStatus The steroidDoseStatus to set.
		 */
		public void setSteroidDoseStatus(String steroidDoseStatus) {
			this.steroidDoseStatus = steroidDoseStatus;
		}
		public String getCensoringStatus() {
			return censor;
		}
		public void setCensoringStatus(String censor) {
			this.censor = censor;
			
		}
		/**
		 * @return Returns the survivalLength.
		 */
		public Long getSurvivalLength() {
			return survivalLength;
		}
		/**
		 * @param survivalLength The survivalLength to set.
		 */
		public void setSurvivalLength(Long survivalLength) {
			this.survivalLength = survivalLength;
		}
		/**
		 * @return Returns the age.
		 */
		public Long getAge() {
			return age;
		}
		/**
		 * @param age The age to set.
		 */
		public void setAge(Long age) {
			this.age = age;
		}
    }

    final public static class GeneExprGroup extends GeneExpr {
        private Double ratioPval;
        private Long degId;
        private Double standardDeviationRatio;

        public Double getStandardDeviationRatio() {
            return standardDeviationRatio;
        }

        public void setStandardDeviationRatio(Double standardDeviationRatio) {
            this.standardDeviationRatio = standardDeviationRatio;
        }

        public Long getID() {
            return getDegId();
        }

        public Long getDegId() {
            return degId;
        }
        public void setDegId(Long degId) {
            this.degId = degId;
        }
        public Double getRatioPval() {
            return ratioPval;
        }
        public void setRatioPval(Double ratioPval) {
            this.ratioPval = ratioPval;
        }
    }
    public abstract static class Annotaion {
        ArrayList locusLinks;
        ArrayList accessions;
        GeneAnnotation geneAnnotation;
        Long ID;

        public StringBuffer print() {
            StringBuffer toString = null;
            toString = new StringBuffer();
            if (geneAnnotation != null) {
                String geneSymbol = geneAnnotation.getGeneSymbol();
                toString.append("Gene Symbol: " + geneSymbol.toUpperCase());
                toString.append("     Pathways:        ");
                toString.append("                      " + geneAnnotation.getPathwayNames().toString());
                toString.append("     Gene Ontologies: ");
                toString.append("                      " + geneAnnotation.getGoIDs().toString());
            }
            return toString;
        }

        public String toString() {
            return print().toString();
        }

        protected Annotaion() { }
        public Annotaion(ArrayList locusLinks, ArrayList accessions, Long ID) {
            this.locusLinks = locusLinks;
            this.accessions = accessions;
            this.ID = ID;
        }
        public GeneAnnotation getGeneAnnotation() {
             return geneAnnotation;
        }
        public void setGeneAnnotation(GeneAnnotation geneAnnotation) {
            this.geneAnnotation = geneAnnotation;
        }

        /**
		 * @return Returns the accessions.
		 */
		public ArrayList getAccessions() {
			return accessions;
		}
		/**
		 * @param accessions The accessions to set.
		 */
		public void setAccessions(ArrayList accessions) {
			this.accessions = accessions;
		}
		/**
		 * @return Returns the iD.
		 */
		public Long getID() {
			return ID;
		}
		/**
		 * @param id The iD to set.
		 */
		public void setID(Long id) {
			ID = id;
		}
		/**
		 * @return Returns the locusLinks.
		 */
		public ArrayList getLocusLinks() {
			return locusLinks;
		}
		/**
		 * @param locusLinks The locusLinks to set.
		 */
		public void setLocusLinks(ArrayList locusLinks) {
			this.locusLinks = locusLinks;
		}
    }

    public static class ProbeAnnotaion extends Annotaion{
        public ProbeAnnotaion(ArrayList locusLinks, ArrayList accessions, Long ID) {
            super(locusLinks, accessions, ID);
        }

        public StringBuffer print() {
            StringBuffer toString = super.print();
            toString.append("     Accessions:      ");
            toString.append("                      " + accessions);
            return toString;
        }
    }
    public static class CloneAnnotaion extends Annotaion{
        public CloneAnnotaion(ArrayList locusLinks, ArrayList accessions, Long ID) {
            super(locusLinks, accessions, ID);
        }
        public StringBuffer print() {
            StringBuffer toString = super.print();
            toString.append("     Locus Links:     ");
            toString.append("                      " + locusLinks);
            return toString;
        }
    }
    public static class GeneAnnotation {
        ArrayList pathwayNames;
        ArrayList goIDs;
        String geneSymbol;

        public GeneAnnotation(ArrayList pathwayNames, ArrayList goIDs, String geneSymbol) {
            this.pathwayNames = pathwayNames;
            this.goIDs = goIDs;
            this.geneSymbol = geneSymbol;
        }

        public ArrayList getPathwayNames() {
            return pathwayNames;
        }

        public void setPathwayNames(ArrayList pathwayNames) {
            this.pathwayNames = pathwayNames;
        }

        public ArrayList getGoIDs() {
            return goIDs;
        }

        public void setGoIDs(ArrayList goIDs) {
            this.goIDs = goIDs;
        }

        public String getGeneSymbol() {
            return geneSymbol;
        }

        public void setGeneSymbol(String geneSymbol) {
            this.geneSymbol = geneSymbol;
        }
    }
	/**
	 * @return Returns the institutionName.
	 */
	public String getInstitutionName() {
		return institutionName;
	}

	/**
	 * @param institutionName The institutionName to set.
	 */
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}


	/**
	 * @return Returns the specimenName.
	 */
	public String getSpecimenName() {
		return specimenName;
	}

	/**
	 * @param specimenName The specimenName to set.
	 */
	public void setSpecimenName(String specimenName) {
		this.specimenName = specimenName;
	}



}
