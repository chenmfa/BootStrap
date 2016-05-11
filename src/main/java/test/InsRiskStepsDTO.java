package test;

public class InsRiskStepsDTO{

  private String group_caption;
  private Integer company_id;
  private Integer risk_serial_no;
  private Integer workgroup_id;
  private Double approve_upper;
  private String approve_upper_label;
  private Double approve_lower;
  private Integer approve_order;

  public String getGroup_caption() {
    return group_caption;
  }

  public void setGroup_caption(String group_caption) {
    this.group_caption = group_caption;
  }

  public Integer getCompany_id() {
    return company_id;
  }

  public void setCompany_id(Integer company_id) {
    this.company_id = company_id;
  }

  public Integer getRisk_serial_no() {
    return risk_serial_no;
  }

  public void setRisk_serial_no(Integer risk_serial_no) {
    this.risk_serial_no = risk_serial_no;
  }

  public Integer getWorkgroup_id() {
    return workgroup_id;
  }

  public void setWorkgroup_id(Integer workgroup_id) {
    this.workgroup_id = workgroup_id;
  }

  public Double getApprove_upper() {
    return approve_upper;
  }

  public void setApprove_upper(Double approve_upper) {
    this.approve_upper = approve_upper;
  }

  public Double getApprove_lower() {
    return approve_lower;
  }

  public void setApprove_lower(Double approve_lower) {
    this.approve_lower = approve_lower;
  }

  public Integer getApprove_order() {
    return approve_order;
  }

  public void setApprove_order(Integer approve_order) {
    this.approve_order = approve_order;
  }

  public String getApprove_upper_label() {
    return approve_upper_label;
  }

  public void setApprove_upper_label(String approve_upper_label) {
    this.approve_upper_label = approve_upper_label;
  }

}
