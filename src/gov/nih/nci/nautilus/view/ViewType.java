package gov.nih.nci.nautilus.view;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 12, 2004
 * Time: 7:29:49 PM
 * To change this template use Options | File Templates.
 */
abstract public class ViewType {
    abstract ViewType getViewType();
    public final static GeneSingleSampleView GENE_SINGLE_SAMPLE_VIEW = new GeneSingleSampleView();
    public final static GeneGroupSampleView GENE_GROUP_SAMPLE_VIEW = new GeneGroupSampleView();
    public final static CopyNumberSampleView COPYNUMBER_GROUP_SAMPLE_VIEW = new CopyNumberSampleView();
    public final static ClinicalView CLINICAL_VIEW = new ClinicalView();

    public static class GeneSingleSampleView extends ViewType {
       public ViewType getViewType() {
           return GENE_SINGLE_SAMPLE_VIEW;
       }
    }
    public static class GeneGroupSampleView extends ViewType {
       public ViewType getViewType() {
           return GENE_GROUP_SAMPLE_VIEW;
       }
    }
    public static class ClinicalView extends ViewType {
       public ViewType getViewType() {
           return CLINICAL_VIEW ;
       }
    }
    public static class CopyNumberSampleView extends ViewType {
        public ViewType getViewType() {
            return COPYNUMBER_GROUP_SAMPLE_VIEW;
        }
}
