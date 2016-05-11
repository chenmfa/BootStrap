package toolmanage;

import jp.ne.so_net.ga2.no_ji.jcom.IDispatch;
import jp.ne.so_net.ga2.no_ji.jcom.ReleaseManager;

public class Office2Pdf_JCom {       
  public void createPDF(String officePath,String pdfPath) throws Exception {
    ReleaseManager rm = null;
    IDispatch app = null;
    try {
      rm=new ReleaseManager();
      app = new IDispatch(rm, "PDFMakerAPI.PDFMakerApp");
    app.method("CreatePDF",new Object[]{officePath,pdfPath});
    } catch (Exception e) {
            throw e;
    } finally {
        try {
                app=null;
                rm.release();
                rm = null;
        } catch (Exception e) {
                throw e;
        }
    }               
  }
  
  public static void main(String[] args) throws Exception {
    Office2Pdf_JCom one=new Office2Pdf_JCom();
    one.createPDF("C:\\zms\\temp\\a.ppt","C:\\zms\\temp\\c.pdf");
  }       

}
