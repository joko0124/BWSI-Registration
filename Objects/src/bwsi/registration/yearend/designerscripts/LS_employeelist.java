package bwsi.registration.yearend.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_employeelist{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("pnldetails").vw.setLeft((int)((0d / 100 * width)));
views.get("pnldetails").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("pnldetails").vw.setTop((int)((0d / 100 * width)));
views.get("pnldetails").vw.setHeight((int)((100d / 100 * height) - ((0d / 100 * width))));
//BA.debugLineNum = 6;BA.debugLine="AvatarBG.Left = 3%x"[EmployeeList/General script]
views.get("avatarbg").vw.setLeft((int)((3d / 100 * width)));
//BA.debugLineNum = 7;BA.debugLine="AvatarBG.Top = 3%y"[EmployeeList/General script]
views.get("avatarbg").vw.setTop((int)((3d / 100 * height)));
//BA.debugLineNum = 8;BA.debugLine="lblEmpName.VerticalCenter = AvatarBG.VerticalCenter - 8dip"[EmployeeList/General script]
views.get("lblempname").vw.setTop((int)((views.get("avatarbg").vw.getTop() + views.get("avatarbg").vw.getHeight()/2)-(8d * scale) - (views.get("lblempname").vw.getHeight() / 2)));
//BA.debugLineNum = 9;BA.debugLine="lblEmpName.SetLeftAndRight(AvatarBG.Right + 5dip, pnlDetails.Width-2%x)"[EmployeeList/General script]
views.get("lblempname").vw.setLeft((int)((views.get("avatarbg").vw.getLeft() + views.get("avatarbg").vw.getWidth())+(5d * scale)));
views.get("lblempname").vw.setWidth((int)((views.get("pnldetails").vw.getWidth())-(2d / 100 * width) - ((views.get("avatarbg").vw.getLeft() + views.get("avatarbg").vw.getWidth())+(5d * scale))));
//BA.debugLineNum = 11;BA.debugLine="lblDivision.SetLeftAndRight(AvatarBG.Right + 5dip, pnlDetails.Width-2%x)"[EmployeeList/General script]
views.get("lbldivision").vw.setLeft((int)((views.get("avatarbg").vw.getLeft() + views.get("avatarbg").vw.getWidth())+(5d * scale)));
views.get("lbldivision").vw.setWidth((int)((views.get("pnldetails").vw.getWidth())-(2d / 100 * width) - ((views.get("avatarbg").vw.getLeft() + views.get("avatarbg").vw.getWidth())+(5d * scale))));
//BA.debugLineNum = 12;BA.debugLine="lblDivision.Top = lblEmpName.Bottom - 5dip"[EmployeeList/General script]
views.get("lbldivision").vw.setTop((int)((views.get("lblempname").vw.getTop() + views.get("lblempname").vw.getHeight())-(5d * scale)));
//BA.debugLineNum = 14;BA.debugLine="lblStatus.Top = AvatarBG.Bottom + 5dip"[EmployeeList/General script]
views.get("lblstatus").vw.setTop((int)((views.get("avatarbg").vw.getTop() + views.get("avatarbg").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 16;BA.debugLine="btnSwap.Top = lblStatus.Bottom + 5dip"[EmployeeList/General script]
views.get("btnswap").vw.setTop((int)((views.get("lblstatus").vw.getTop() + views.get("lblstatus").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 17;BA.debugLine="btnSwap.SetLeftAndRight(20%x, pnlDetails.Width - 40%x)"[EmployeeList/General script]
views.get("btnswap").vw.setLeft((int)((20d / 100 * width)));
views.get("btnswap").vw.setWidth((int)((views.get("pnldetails").vw.getWidth())-(40d / 100 * width) - ((20d / 100 * width))));
//BA.debugLineNum = 18;BA.debugLine="btnRegister.Top = lblStatus.Bottom + 5dip"[EmployeeList/General script]
views.get("btnregister").vw.setTop((int)((views.get("lblstatus").vw.getTop() + views.get("lblstatus").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 19;BA.debugLine="btnRegister.SetLeftAndRight(btnSwap.Right + 7dip, pnlDetails.Width - 1%y)"[EmployeeList/General script]
views.get("btnregister").vw.setLeft((int)((views.get("btnswap").vw.getLeft() + views.get("btnswap").vw.getWidth())+(7d * scale)));
views.get("btnregister").vw.setWidth((int)((views.get("pnldetails").vw.getWidth())-(1d / 100 * height) - ((views.get("btnswap").vw.getLeft() + views.get("btnswap").vw.getWidth())+(7d * scale))));
//BA.debugLineNum = 21;BA.debugLine="lblWasAwardee.SetLeftAndRight(lblStatus.Right + 7dip, pnlDetails.Width - 1%y)"[EmployeeList/General script]
views.get("lblwasawardee").vw.setLeft((int)((views.get("lblstatus").vw.getLeft() + views.get("lblstatus").vw.getWidth())+(7d * scale)));
views.get("lblwasawardee").vw.setWidth((int)((views.get("pnldetails").vw.getWidth())-(1d / 100 * height) - ((views.get("lblstatus").vw.getLeft() + views.get("lblstatus").vw.getWidth())+(7d * scale))));
//BA.debugLineNum = 22;BA.debugLine="lblWasAwardee.VerticalCenter = lblStatus.VerticalCenter"[EmployeeList/General script]
views.get("lblwasawardee").vw.setTop((int)((views.get("lblstatus").vw.getTop() + views.get("lblstatus").vw.getHeight()/2) - (views.get("lblwasawardee").vw.getHeight() / 2)));

}
}