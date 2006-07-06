package gov.nih.nci.rembrandt.web.helper;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.util.LabelValueBean;

public class ClinicalGroupRetriever {
    private static List<LabelValueBean> clinicalGroupsCollection = new ArrayList<LabelValueBean>();
    
    /**
     * retrieves all current clinical groups after cycling through all the clinical
     * group types.
     * @return collection of LabelValueBeans to populate an ActionForm 
     * -KR
     */
    public static List<LabelValueBean> getClinicalGroupsCollection(HttpSession session){
        UserListBeanHelper helper = new UserListBeanHelper(session);
        List<UserList> patientLists = helper.getLists(ListType.PatientDID);
        
        for(UserList patientList: patientLists){
            clinicalGroupsCollection.add(new LabelValueBean(patientList.getName(),patientList.getClass().getCanonicalName() + "#" + patientList.getName()));
        }
        return clinicalGroupsCollection;
    
    }
}
