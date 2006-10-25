package gov.nih.nci.rembrandt.web.ajax;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import gov.nih.nci.caintegrator.application.lists.ListManager;
import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBean;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.application.lists.ajax.CommonListFunctions;
//import gov.nih.nci.ispy.util.ispyConstants;
import gov.nih.nci.rembrandt.queryservice.validation.DataValidator;
import gov.nih.nci.rembrandt.web.helper.RembrandtListValidator;
import gov.nih.nci.rembrandt.dto.lookup.AllGeneAliasLookup;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import uk.ltd.getahead.dwr.ExecutionContext;

public class DynamicListHelper {
/**
 * Basically a wrapper for app-commons/application/lists/ajax/CommonListFunctions
 * except this specifically sets the ListValidator for this context and passes it off
 * to the CommonListFunctions
 *
 */
	
	public DynamicListHelper() {}
	/*
    public static String getPatientListAsList()	{
		return CommonListFunctions.getListAsList(ListType.PatientDID);
	}
	
	public static String getGeneListAsList()	{
		return CommonListFunctions.getListAsList(ListType.GeneSymbol);
	}
	*/
	public static String getGenericListAsList(String listType)	{
		return CommonListFunctions.getListAsList(ListType.valueOf(listType));
	}
	
	public static String createGenericList(String listType, List<String> list, String name) throws OperationNotSupportedException	{
		try	{
			String[] tps = CommonListFunctions.parseListType(listType);
			//tps[0] = ListType
			//tps[1] = ListSubType (if not null)
			ListType lt = ListType.valueOf(tps[0]);
			if(tps.length > 1 && tps[1] != null){
				//create a list out of [1]
				ArrayList<ListSubType> lst = new ArrayList();
				lst.add(ListSubType.valueOf(tps[1]));
				return CommonListFunctions.createGenericList(lt, lst, list, name, new RembrandtListValidator(ListSubType.valueOf(tps[1]), ListType.valueOf(tps[0]), list));
			}
			else if(tps.length >0 && tps[0] != null)	{
				//no subtype, only a primary type - typically a PatientDID then
				return CommonListFunctions.createGenericList(lt, list, name, new RembrandtListValidator(ListSubType.Custom, ListType.valueOf(tps[0]), list));
			}
			else	{
				//no type or subtype, not good, force to clinical in catch
				//return CommonListFunctions.createGenericList(lt, list, name, new RembrandtListValidator());
				throw new Exception();
			}
		}
		catch(Exception e)	{
			//try as a patient list as default, will fail validation if its not accepted
			return CommonListFunctions.createGenericList(ListType.PatientDID, list, name, new RembrandtListValidator(ListType.PatientDID, list));
		}
	}
/*
	public static String createPatientList(String[] list, String name){
		return CommonListFunctions.createGenericList(ListType.PatientDID, list, name, new RembrandtListValidator());
	}
*/
/*	
	public static String createGeneList(String[] list, String name){
		return CommonListFunctions.createGenericList(ListType.Gene, list, name, new RembrandtListValidator());
	}
*/
	public static String exportListasTxt(String name, HttpSession session){
		return CommonListFunctions.exportListasTxt(name, session);
	}
	
	public static String getAllLists()	{
		//create a list of allowable types
		ArrayList listTypesList = new ArrayList();
		for(ListType l  : ListType.values())	{
			listTypesList.add(l.toString());
		}
		//call CommonListFunctions.getAllLists(listTypesList);
		return "(" + CommonListFunctions.getAllLists(listTypesList) + ")";
	}
	public static String getGenericList(String listType)	{
		//just want one type
		ArrayList<String> listTypesList = new ArrayList();
		listTypesList.add(listType);
		return CommonListFunctions.getAllLists(listTypesList);
	}

	public static String getPathwayGeneSymbols(String pathwayName)	{
        List<String> geneSymbols = LookupManager.getPathwayGeneSymbols(pathwayName);
        JSONArray symbols = new JSONArray();
        for(String symbol : geneSymbols)	{
	        symbols.add(symbol);
        }

		return "(" + symbols.toString() + ")";
	}
	/*
	public static String getPathwayGeneSymbols()	{
        List<String> geneSymbols = LookupManager.getPathwayGeneSymbols();
        JSONArray symbols = new JSONArray();
        for(String symbol : geneSymbols)	{
	        symbols.add(symbol);
        }
		//call CommonListFunctions.getAllLists(listTypesList);
		return "(" + symbols.toString() + ")";
	}
	*/
	/*
	public static String getAllPatientLists()	{
		return CommonListFunctions.getAllLists(ListType.PatientDID.toString());
	}
	*/

	/*
	public static String getAllGeneLists()	{
		return CommonListFunctions.getAllLists(ListType.GeneSymbol.toString());
	}
	*/
	
	public static String uniteLists(String[] sLists, String groupName, String groupType, String action)	{	
		return CommonListFunctions.uniteLists(sLists, groupName, groupType, action);
	}
	
	public static String getRBTFeatures()	{
		String jfeats = "";
		//get the features from the external props
		String feats = System.getProperty("rembrandt.feedback.features");
		List<String> f = Arrays.asList(feats.split(","));
		JSONArray fs = new JSONArray();
		for(String s : f)	{
			s = s.trim();
			fs.add(s);
		}
		if(fs.size()>0){
			jfeats = fs.toString();
		}
		return jfeats;
	}
	
	public static String getGeneAliases(String gene)	{
		JSONArray aliases = new JSONArray();
	    try {
			if(!DataValidator.isGeneSymbolFound(gene)){
				AllGeneAliasLookup[] allGeneAlias = DataValidator.searchGeneKeyWord(gene);
				// if there are aliases , set the array to be displayed in the form and return the showAlias warning
				if(allGeneAlias != null){
					for(int i =0; i < allGeneAlias.length ; i++){
						JSONObject a = new JSONObject();
						
						AllGeneAliasLookup alias = allGeneAlias[i];
						a.put("alias", alias.getAlias());
						a.put("symbol", alias.getApprovedSymbol());
						a.put("name", alias.getApprovedName());
						//alias.getAlias()+"\t"+alias.getApprovedSymbol()+"\t"+alias.getApprovedName()
						aliases.add(a);
					}
				}
				else	{
					aliases.add("invalid");
				}
			}
			
		} 
	    catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return aliases.toString();
	}
}
