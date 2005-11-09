package gov.nih.nci.rembrandt.web.struts.form;





import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.de.DomainElement;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;


public class UploadGeneSetForm extends BaseForm {
    
    private static Logger logger = Logger.getLogger(BaseForm.class);
    
    private PresentationTierCache cacheManager = ApplicationFactory.getPresentationTierCache();
    private FormFile geneSetFile;
    private String geneSetName;    
    private String geneType;
    private Collection<DomainElement> domainElementCollection;
    private HttpServletRequest thisRequest;
    
	


	public UploadGeneSetForm(){

	}

   
    /**
     * @return Returns the geneSetName.
     */
    public String getGeneSetName() {
        return geneSetName;
    }




    /**
     * @param geneSetName The geneSetName to set.
     */
    public void setGeneSetName(String geneSetName) {
        this.geneSetName = geneSetName;
    }

    /**
     * @return Returns the geneType.
     */
    public String getGeneType() {
        return geneType;
    }

    /**
     * @param geneType The geneType to set.
     */
    public void setGeneType(String geneType) {
        this.geneType = geneType;
    }
    /**
     * @return Returns the geneSetFile.
     */
    public FormFile getGeneSetFile() {
        return geneSetFile;
    }




    /**
     * @param geneSetFile The geneSetFile to set.
     */
    public void setGeneSetFile(FormFile geneSetFile) {
        this.geneSetFile = geneSetFile;
        if (thisRequest != null) {
            String thisGeneType = this.thisRequest.getParameter("geneType");
            
            // retrieve the file name & size
            String fileName = geneSetFile.getFileName();
            int fileSize = geneSetFile.getFileSize();

            if ( (thisGeneType.length() > 0) && (this.geneSetFile != null)
                    && (this.geneSetFile.getFileName().endsWith(".txt")|| this.geneSetFile.getFileName().endsWith(".TXT"))
                    && (this.geneSetFile.getContentType().equals("text/plain"))) {
                try {
                    InputStream stream = geneSetFile.getInputStream();
                    String inputLine = null;
                    BufferedReader inFile = new BufferedReader(
                            new InputStreamReader(stream));

                    int count = 0;
                    
                    GeneIdentifierDE geneIdentifierDE = null;
                    while ((inputLine = inFile.readLine()) != null
                            && count < RembrandtConstants.MAX_FILEFORM_COUNT) {
                        if (UIFormValidator.isAscii(inputLine)) { // make sure
                                                                    // all data
                                                                    // is ASCII
                            inputLine = inputLine.trim();
                            count++;
                            if (thisGeneType.equalsIgnoreCase("genesymbol")) {
                                geneIdentifierDE = new GeneIdentifierDE.GeneSymbol(inputLine);
                            } else if (thisGeneType.equalsIgnoreCase("genelocus")) {
                                geneIdentifierDE = new GeneIdentifierDE.LocusLink(inputLine);
                            } else if (thisGeneType.equalsIgnoreCase("genbankno")) {
                                geneIdentifierDE = new GeneIdentifierDE.GenBankAccessionNumber(inputLine);

                            }
                            
                        }
                    }// end of while

                    inFile.close();
                } catch (IOException ex) {
                    logger.error("Errors when uploading gene file:"
                            + ex.getMessage());
                }
            }
        }
            
        }
    

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        geneSetFile = null;
        geneType = "";
        geneSetName = "";
         //Set the Request Object
        this.thisRequest = request;
    }
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        
        errors = UIFormValidator.validateTextFileType(geneSetFile,
            "geneGroup", errors);
        
        return errors;
    }
}