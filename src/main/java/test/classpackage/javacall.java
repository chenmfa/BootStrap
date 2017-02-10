/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
package test.classpackage;

public class javacall
{
        static
        {
         System.loadLibrary("umf.dll");
        }


    public native static int fw_init ( int port,long baud);
    public native static short fw_exit(int lDevice);
    public native static short fw_card(int lDevice,short iMode,int[] pSnr);
    public native static short fw_card_hex(int lDevice,short iMode,char[] pSnr);
    public native static short fw_authentication(int lDevice,short iMode,short iSecNr);
    public native static short fw_halt(int lDevice);
    public native static short fw_read(int lDevice,short iAdr,char[] pData);
    public native static short fw_write(int lDevice,short iAdr,char[] pData);
    public native static short fw_increment(int lDevice,short iAdr,int lValue);
    public native static short fw_decrement(int lDevice,short iAdr,int lValue);
    public native static short fw_initval(int lDevice,short iAdr,int lValue);
    public native static short fw_readval(int lDevice,short iAdr,int[] pValue);
    public native static short fw_transfer(int lDevice,short iAdr);
    public native static short fw_restore(int lDevice,short iAdr);
    public native static short fw_load_key(int lDevice,short iMode,short iSecNr,char[] pKey);
    public native static short fw_beep(int lDevice,short iMsec);
    public native static short fw_request(int lDevice,short iMode,int[] pTagType);
    public native static short fw_anticoll(int lDevice,short iBcnt,int[] pSnr);
    public native static short fw_select(int lDevice,int lSnr,short[] pSize);
    public native static short fw_swr_eeprom(int lDevice,int lOffset,int lLen,char[] pBuffer);
    public native static short fw_srd_eeprom(int lDevice,int lOffset,int lLen,char[] pBuffer);
    
    public native static short fw_des(char[] key,char[] sour,char[] dest,int m);
    public native static short fw_changeb3(int lDevice,short secNr,char[] keyA,
    	char[] ctrlW,short bk,char[] keyB);
    public native static short fw_getver(int lDevice,char[] rBuffer);
    public native static short fw_reset(int lDevice,int mSec);
    
    public native static short fw_cpureset(int lDevice,short[] pLen,char[] pData);
    public native static short fw_setcpu(int lDevice,short idSam);
    public native static short fw_cpuapdu(int lDevice,short iLen,char[] pSData,short[] pLen,char[] pRData);

   
    public native static short fw_read_4442(int lDevice,short iAdr,char[] pData,int length);
    public native static short fw_write_4442(int lDevice,short iAdr,char[] rData,int length);
    public native static short fw_getProtectData_4442(int lDevice,short iAdr,char[] rData,int length);
    public native static short fw_setProtectData_4442(int lDevice,short iAdr,char[] pData,int length);
    public native static short fw_authentikey_4442(int lDevice,short iAdr,int rlen,char[] pKey);
    public native static short fw_changkey_4442(int lDevice,short iAdr,int rlen,char[] pKey);
    public native static short fw_cntReadError_4442(int lDevice,char[] cntReadError);
    
    public native static void  hex_a(char[] cHex,char[] a,int length);
    public native static short a_hex(char[] a,char[] cHex,int length);
    
    //FCPU Card
    public native static short fw_pro_reset(int IDevice,char[] rlen,char[] rbuf);
    public native static short fw_pro_commandlink(int IDevice,short slen,char[] sbuff,char[] rlen,char[] rbuf,short tt,short fg);
    




}
