package gov.nih.nci.nautilus.query;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 12, 2004
 * Time: 6:39:40 PM
 */
class QueryFactory {
    public static Query newQuery(QueryType queryType) {
          if (queryType instanceof QueryType.GeneExprQueryType) {
              return new GeneExpressionQuery();
          }
          else if (queryType instanceof QueryType.CGHQueryType) {              
              return new ComparativeGenomicQuery();
          }
		  else if (queryType instanceof QueryType.ClinicalQueryType) {              
              return new ClinicalDataQuery();
          }
        return null;
    }


}
