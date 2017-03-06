package com.stt.MyDemo;

import org.junit.Test;

public class URLDecoderTest {

	// 结果：
	/*
	 * <?xml version="1.0" encoding="GBK"?> <Transaction> <SystemHead>
	 * <Language>zh_CN</Language> <Encodeing></Encodeing> <Version></Version>
	 * <ServiceName></ServiceName> <CifNo>2400154072</CifNo>
	 * <UserID>001</UserID> <SyMacFlag></SyMacFlag> <MAC></MAC>
	 * <SyPinFlag></SyPinFlag> <PinSeed></PinSeed> <LicenseId></LicenseId>
	 * <Flag></Flag> <Note></Note> </SystemHead> <TransHead>
	 * <TransCode>b2e004001</TransCode>
	 * <BatchID>LEFU2017030216342800000814333</BatchID>
	 * <JnlDate>20170302</JnlDate> <JnlTime>163431</JnlTime> </TransHead>
	 * <TransContent> <ReqData>
	 * <ClientPatchID>LEFU2017030216342800000814333</ClientPatchID>
	 * <transferType>2120</transferType>
	 * <accountNo>35500188067555043</accountNo>
	 * <toAccountName>test</toAccountName>
	 * <toAccountNo>10001676807</toAccountNo>
	 * <toBank>REMIT_SINGEL_CEB_310001</toBank> <amount>1000.00</amount>
	 * <toLocation></toLocation> <clientSignature></clientSignature>
	 * <checkNo></checkNo> <checkPassword></checkPassword> <note>锟斤拷锟矫凤拷</note>
	 * <isUrgent>1</isUrgent> <cellphone></cellphone> <perOrEnt>1</perOrEnt>
	 * <IsAudit></IsAudit> <matchRule></matchRule> </ReqData> </TransContent>
	 * </Transaction>
	 * 
	 * 
	 */
	@Test
	public void test01() {
		try {
			String msg = "%3C%3Fxml+version=%221.0%22+encoding%3D%22GBK%22%3F%3E%0A%3CTransaction%3E%0A++++%3CSystemHead%3E%0A++++++++%3CLanguage%3Ezh_CN%3C%2FLanguage%3E%0A++++++++%3CEncodeing%3E%3C%2FEncodeing%3E%0A++++++++%3CVersion%3E%3C%2FVersion%3E%0A++++++++%3CServiceName%3E%3C%2FServiceName%3E%0A++++++++%3CCifNo%3E2400154072%3C%2FCifNo%3E%0A++++++++%3CUserID%3E001%3C%2FUserID%3E%0A++++++++%3CSyMacFlag%3E%3C%2FSyMacFlag%3E%0A++++++++%3CMAC%3E%3C%2FMAC%3E%0A++++++++%3CSyPinFlag%3E%3C%2FSyPinFlag%3E%0A++++++++%3CPinSeed%3E%3C%2FPinSeed%3E%0A++++++++%3CLicenseId%3E%3C%2FLicenseId%3E%0A++++++++%3CFlag%3E%3C%2FFlag%3E%0A++++++++%3CNote%3E%3C%2FNote%3E%0A++++%3C%2FSystemHead%3E%0A++++%3CTransHead%3E%0A++++++++%3CTransCode%3Eb2e004001%3C%2FTransCode%3E%0A++++++++%3CBatchID%3ELEFU2017030216342800000814333%3C%2FBatchID%3E%0A++++++++%3CJnlDate%3E20170302%3C%2FJnlDate%3E%0A++++++++%3CJnlTime%3E163431%3C%2FJnlTime%3E%0A++++%3C%2FTransHead%3E%0A++++%3CTransContent%3E%0A++++++++%3CReqData%3E%0A++++++++++++%3CClientPatchID%3ELEFU2017030216342800000814333%3C%2FClientPatchID%3E%0A++++++++++++%3CtransferType%3E2120%3C%2FtransferType%3E%0A++++++++++++%3CaccountNo%3E35500188067555043%3C%2FaccountNo%3E%0A++++++++++++%3CtoAccountName%3Etest%3C%2FtoAccountName%3E%0A++++++++++++%3CtoAccountNo%3E10001676807%3C%2FtoAccountNo%3E%0A++++++++++++%3CtoBank%3EREMIT_SINGEL_CEB_310001%3C%2FtoBank%3E%0A++++++++++++%3Camount%3E1000.00%3C%2Famount%3E%0A++++++++++++%3CtoLocation%3E%3C%2FtoLocation%3E%0A++++++++++++%3CclientSignature%3E%3C%2FclientSignature%3E%0A++++++++++++%3CcheckNo%3E%3C%2FcheckNo%3E%0A++++++++++++%3CcheckPassword%3E%3C%2FcheckPassword%3E%0A++++++++++++%3Cnote%3E%EF%BF%BD%EF%BF%BD%EF%BF%BD%C3%B7%EF%BF%BD%3C%2Fnote%3E%0A++++++++++++%3CisUrgent%3E1%3C%2FisUrgent%3E%0A++++++++++++%3Ccellphone%3E%3C%2Fcellphone%3E%0A++++++++++++%3CperOrEnt%3E1%3C%2FperOrEnt%3E%0A++++++++++++%3CIsAudit%3E%3C%2FIsAudit%3E%0A++++++++++++%3CmatchRule%3E%3C%2FmatchRule%3E%0A++++++++%3C%2FReqData%3E%0A++++%3C%2FTransContent%3E%0A%3C%2FTransaction%3E%0A";
			msg = new String(msg.getBytes("ISO8859-1"));
			msg = java.net.URLDecoder.decode(msg, "GBK");
			System.out.println(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
