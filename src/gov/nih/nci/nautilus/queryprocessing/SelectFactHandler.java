package gov.nih.nci.nautilus.queryprocessing;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import gov.nih.nci.nautilus.data.DifferentialExpressionSfact;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Sep 29, 2004
 * Time: 6:46:04 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class SelectFactHandler implements Runnable{
     String fieldName;
     Criteria sampleCrit;
     Collection values;
     DBEvent.FactRetrieveEvent dbEvent;
     //PersistenceBroker _BROKER;
     Map geneExprObjects;
     protected String probeOrCloneIDAttr;

    public DBEvent.FactRetrieveEvent getDbEvent() {
        return dbEvent;
    }


    public SelectFactHandler(String fieldName, Criteria samplCrit, Collection values, DBEvent.FactRetrieveEvent dbEvent, Map geneExprObjects) {
        this.fieldName = fieldName;
        this.sampleCrit = samplCrit;
        this.values = values;
        //this._BROKER = BROKER;
        this.dbEvent = dbEvent;
        this.geneExprObjects = geneExprObjects;
    }

     public void run() {
        //Criteria sampleCritBasedOnProbes = new Criteria();
        sampleCrit.addOrderBy(fieldName);
        Criteria IDs = new Criteria();
        IDs.addIn(probeOrCloneIDAttr, values);
        sampleCrit.addAndCriteria(IDs);

        org.apache.ojb.broker.query.Query sampleQuery =
                QueryFactory.newQuery(DifferentialExpressionSfact.class,sampleCrit, true);

        PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
        Collection exprObjects =   _BROKER.getCollectionByQuery(sampleQuery );

        assert(exprObjects != null);
        for (Iterator iterator = exprObjects.iterator(); iterator.hasNext();) {
            DifferentialExpressionSfact exprObj = (DifferentialExpressionSfact) iterator.next();
            if (exprObj != null)
                geneExprObjects.put(exprObj.getDesId(), exprObj);
            //System.out.println("BIO ID:" + exprObj.getDesId());
        }
        getDbEvent().setCompleted(true);
        //System.out.println("DBEvent: " + getDbEvent().getThreadID());
    }

   final static class SelectFactBasedOnProbeHandler extends SelectFactHandler {
       public SelectFactBasedOnProbeHandler(String fieldName, Criteria samplCrit, Collection values, DBEvent.FactRetrieveEvent dbEvent, Map geneExprObjects) {
           super(fieldName, samplCrit, values, dbEvent, geneExprObjects);
           probeOrCloneIDAttr = DifferentialExpressionSfact.PROBESET_ID;
       }
   }
    final static class SelectFactBasedOnCloneHandler extends SelectFactHandler {
       public SelectFactBasedOnCloneHandler(String fieldName, Criteria samplCrit, Collection values, DBEvent.FactRetrieveEvent dbEvent, Map geneExprObjects) {
           super(fieldName, samplCrit, values, dbEvent, geneExprObjects);
           probeOrCloneIDAttr = DifferentialExpressionSfact.CLONE_ID;
       }
   }
}
