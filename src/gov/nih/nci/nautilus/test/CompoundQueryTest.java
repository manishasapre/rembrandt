/*
 * Created on Oct 18, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.test;

import gov.nih.nci.nautilus.criteria.ArrayPlatformCriteria;
import gov.nih.nci.nautilus.criteria.CloneOrProbeIDCriteria;
import gov.nih.nci.nautilus.criteria.Constants;
import gov.nih.nci.nautilus.criteria.FoldChangeCriteria;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.de.ArrayPlatformDE;
import gov.nih.nci.nautilus.de.CloneIdentifierDE;
import gov.nih.nci.nautilus.de.ExprFoldChangeDE;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.query.GeneExpressionQuery;
import gov.nih.nci.nautilus.query.OperatorType;
import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.query.QueryManager;
import gov.nih.nci.nautilus.query.QueryType;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.view.ViewFactory;
import gov.nih.nci.nautilus.view.ViewType;
import gov.nih.nci.nautilus.view.Viewable;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CompoundQueryTest extends TestCase {
    ArrayPlatformCriteria allPlatformCrit;
    ArrayPlatformCriteria affyOligoPlatformCrit;
    ArrayPlatformCriteria cdnaPlatformCrit;
    CloneOrProbeIDCriteria cloneCrit;
    CloneOrProbeIDCriteria probeCrit;
    GeneIDCriteria geneCrit;
    FoldChangeCriteria foldCrit;
    GeneExpressionQuery probeQuery;
    GeneExpressionQuery cloneQuery;
    GeneExpressionQuery geneQuery;
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
        buildPlatformCrit();
        buildFoldChangeCrit();
        buildCloneCrit();
        buildProbeCrit();
        buildGeneIDCrit();
        buildGeneExprCloneSingleViewQuery();
        buildGeneExprProbeSetSingleViewQuery();
        buildGeneExprGeneSingleViewQuery();        
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSingleQueryInCompoundQueryProcessor() {
		try {
			//test Single Query
			System.out.println("Testing Single Gene Query>>>>>>>>>>>>>>>>>>>>>>>");
			CompoundQuery myCompoundQuery = new CompoundQuery(geneQuery);
			ResultSet[] geneExprObjects = QueryManager.executeQuery(myCompoundQuery);
			System.out.println("SingleQuery:\n"+ myCompoundQuery.toString());
			print(geneExprObjects);
		} catch (Exception e) {
			e.printStackTrace();
			}
		}
	public void testCompoundQueryANDProcessor() {
		try {
			//test CompoundQuery Query
			System.out.println("Testing CompoundQuery GeneQuery AND ProbeQuery>>>>>>>>>>>>>>>>>>>>>>>");
			CompoundQuery myCompoundQuery = new CompoundQuery(OperatorType.AND,geneQuery,probeQuery);
			ResultSet[] geneExprObjects = QueryManager.executeQuery(myCompoundQuery);
			System.out.println("CompoundQuery:\n"+ myCompoundQuery.toString());
			print(geneExprObjects);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	public void testCompoundQueryNOTProcessor() {
		try {
			//test CompoundQuery Query
			System.out.println("Testing CompoundQuery GeneQuery NOT ProbeQuery>>>>>>>>>>>>>>>>>>>>>>>");
			CompoundQuery myCompoundQuery = new CompoundQuery(OperatorType.NOT,geneQuery,probeQuery);
			ResultSet[] geneExprObjects = QueryManager.executeQuery(myCompoundQuery);
			System.out.println("CompoundQuery:\n"+ myCompoundQuery.toString());
			print(geneExprObjects);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	public void testCompoundQueryORProcessor() {
		try {
			//test CompoundQuery Query
			System.out.println("Testing CompoundQuery CloneQuery OR ProbeQuery>>>>>>>>>>>>>>>>>>>>>>>");
			CompoundQuery myCompoundQuery = new CompoundQuery(OperatorType.OR,cloneQuery,probeQuery);
			ResultSet[] geneExprObjects = QueryManager.executeQuery(myCompoundQuery);
			System.out.println("CompoundQuery:\n"+ myCompoundQuery.toString());
			print(geneExprObjects);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
    /**
	 * @param geneExprObjects
	 */
	private void print(ResultSet[] geneExprObjects) {
		if(geneExprObjects != null){
			System.out.println("Number of Records:"+ geneExprObjects.length);
	        for (int i =0; i < geneExprObjects.length; i++) {
	        	GeneExpr.GeneExprSingle expObj = (GeneExpr.GeneExprSingle) geneExprObjects[i];
	        	if(expObj != null){
	            System.out.println( "uID: " + expObj.getDesId() + "|geneSymbol: " + expObj.getGeneSymbol() +"|clone: " + expObj.getCloneName()+"|probeSet: "+expObj.getProbesetName()+"|biospecimenID: " + expObj.getBiospecimenId() );
	        	}
	        }
		}
		
	}

	public static Test suite() {
		TestSuite suit =  new TestSuite();
        suit.addTest(new TestSuite(CompoundQueryTest.class));
        return suit;
	}

	public static void main (String[] args) {
		junit.textui.TestRunner.run(suite());

    }
	public void testExecute() {
	}
	private void buildProbeCrit() {
        probeCrit = new CloneOrProbeIDCriteria();
        //1555146_at is a probeSet for ATF2
        probeCrit.setCloneIdentifier(new CloneIdentifierDE.ProbesetID("1555146_at"));
    }

    private void buildCloneCrit() {
        cloneCrit = new CloneOrProbeIDCriteria();
        //IMAGE:2014733 is a CloneID for AFT2
        cloneCrit.setCloneIdentifier(new CloneIdentifierDE.IMAGEClone("IMAGE:2014733"));

    }
    private void buildGeneIDCrit() {
        geneCrit = new GeneIDCriteria();
        //Both IMAGE:2014733 and 1555146_at should be subsets of ATF2
        geneCrit.setGeneIdentifier(new GeneIdentifierDE.GeneSymbol("ATF2"));

    }
    private void buildFoldChangeCrit() {
        Float upRegExpected = new Float(2.0);
        Float downRegExpected = new Float(1.0);
        ExprFoldChangeDE.UpRegulation upRegObj = new ExprFoldChangeDE.UpRegulation(upRegExpected );
        ExprFoldChangeDE.DownRegulation downRegObj = new ExprFoldChangeDE.DownRegulation(downRegExpected );
        //ExprFoldChangeDE.UnChangedRegulationUpperLimit upUnChangedObj = new ExprFoldChangeDE.UnChangedRegulationUpperLimit(upperUnchangedExpected );
        //ExprFoldChangeDE.UnChangedRegulationDownLimit downUnChangedRegObj = new ExprFoldChangeDE.UnChangedRegulationDownLimit(downUnChangedExpected );

        foldCrit = new FoldChangeCriteria();
        Collection objs = new ArrayList(4);
        objs.add(upRegObj);
        objs.add(downRegObj);
        //objs.add(upUnChangedObj); objs.add(downUnChangedRegObj);
        foldCrit.setFoldChangeObjects(objs);
    }
    private void buildGeneExprProbeSetSingleViewQuery(){
        probeQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
        probeQuery.setQueryName("ProbeSetQuery");
        probeQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
        probeQuery.setCloneOrProbeIDCrit(probeCrit);
        probeQuery.setArrayPlatformCrit(affyOligoPlatformCrit);
        probeQuery.setFoldChgCrit(foldCrit);
    }
    private void buildGeneExprCloneSingleViewQuery(){
        cloneQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
        cloneQuery.setQueryName("CloneQuery");
        cloneQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
        cloneQuery.setCloneOrProbeIDCrit(cloneCrit);
        cloneQuery.setArrayPlatformCrit(cdnaPlatformCrit);
        cloneQuery.setFoldChgCrit(foldCrit);
    }
    private void buildGeneExprGeneSingleViewQuery(){
        geneQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
        geneQuery.setQueryName("GeneQuery");
        geneQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
        geneQuery.setGeneIDCrit(geneCrit);
        geneQuery.setArrayPlatformCrit(allPlatformCrit);
        geneQuery.setFoldChgCrit(foldCrit);
    }
    private void buildPlatformCrit() {
        allPlatformCrit = new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.ALL_PLATFROM));
        affyOligoPlatformCrit = new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.AFFY_OLIGO_PLATFORM));
        cdnaPlatformCrit = new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.CDNA_ARRAY_PLATFORM));
    }
    private void changeQueryView(Query query,Viewable view){
    	if(query !=null){
    		query.setAssociatedView(view);
    	}
    }
}
