
// Generated by OJB SchemeGenerator

package gov.nih.nci.nautilus.data;

import gov.nih.nci.nautilus.lookup.PatientDataLookup;

public class PatientData implements PatientDataLookup
{
  public final static String BIOSPECIMEN_ID = "biospecimenId";
  public final static String SURVIVAL_LENGTH = "survivalLength";
  public final static String AGE = "age";
  public final static String GENDER = "gender";    

  private Long age;

  private Long biospecimenId;

  private String censoringStatus;

  private Long patientDid;

  private Long populationTypeId;

  private String sampleId;

  private Long survivalLength;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String gender;

  public Long getAge()
  {
     return this.age;
  }
  public void setAge(Long param)
  {
    this.age = param;
  }


  public Long getBiospecimenId()
  {
     return this.biospecimenId;
  }
  public void setBiospecimenId(Long param)
  {
    this.biospecimenId = param;
  }


  public String getCensoringStatus()
  {
     return this.censoringStatus;
  }
  public void setCensoringStatus(String param)
  {
    this.censoringStatus = param;
  }


  public Long getPatientDid()
  {
     return this.patientDid;
  }
  public void setPatientDid(Long param)
  {
    this.patientDid = param;
  }


  public Long getPopulationTypeId()
  {
     return this.populationTypeId;
  }
  public void setPopulationTypeId(Long param)
  {
    this.populationTypeId = param;
  }


  public String getSampleId()
  {
     return this.sampleId;
  }
  public void setSampleId(String param)
  {
    this.sampleId = param;
  }


  public Long getSurvivalLength()
  {
     return this.survivalLength;
  }
  public void setSurvivalLength(Long param)
  {
    this.survivalLength = param;
  }


}

