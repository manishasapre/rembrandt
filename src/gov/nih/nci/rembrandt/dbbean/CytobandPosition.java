
// Generated by OJB SchemeGenerator

package gov.nih.nci.rembrandt.dbbean;

import gov.nih.nci.rembrandt.dto.lookup.CytobandLookup;


public class CytobandPosition implements CytobandLookup 
{
    public final static String CYTOBAND = "cytoband";
    public final static String CB_START= "cbStart";
    public final static String CB_ENDPOS= "cbEndPos";
    public final static String CHROMOSOME= "chromosome";
    public final static String CHR_CYTOBAND= "chrCytoband";

  private Long cbEndPos;

  private Long cbStart;

  private String chromosome;

  private String cytoband;

  private Long cytobandPositionId;

  private String organism;
  
  private String chrCytoband;

  public Long getCbEndPos()
  {
     return this.cbEndPos;
  }
  public void setCbEndPos(Long param)
  {
    this.cbEndPos = param;
  }


  public Long getCbStart()
  {
     return this.cbStart;
  }
  public void setCbStart(Long param)
  {
    this.cbStart = param;
  }


  public String getChromosome()
  {
     return this.chromosome;
  }
  public void setChromosome(String param)
  {
    this.chromosome = param;
  }


  public String getCytoband()
  {
     return this.cytoband;
  }
  public void setCytoband(String param)
  {
    this.cytoband = param;
  }


  public Long getCytobandPositionId()
  {
     return this.cytobandPositionId;
  }
  public void setCytobandPositionId(Long param)
  {
    this.cytobandPositionId = param;
  }


  public String getOrganism()
  {
     return this.organism;
  }
  public void setOrganism(String param)
  {
    this.organism = param;
  }


/**
 * @return Returns the chrCytoband.
 */
public String getChrCytoband() {
	return this.chrCytoband;
}
/**
 * @param chrCytoband The chrCytoband to set.
 */
public void setChrCytoband(String chrCytoband) {
	this.chrCytoband = chrCytoband;
}
}
