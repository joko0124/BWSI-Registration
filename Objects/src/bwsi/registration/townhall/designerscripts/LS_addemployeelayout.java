package bwsi.registration.townhall.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_addemployeelayout{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[AddEmployeeLayout/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="ToolBar.Top = 0dip"[AddEmployeeLayout/General script]
views.get("toolbar").vw.setTop((int)((0d * scale)));
//BA.debugLineNum = 5;BA.debugLine="If ActivitySize > 6.5 Then"[AddEmployeeLayout/General script]
if ((anywheresoftware.b4a.keywords.LayoutBuilder.getScreenSize()>6.5d)) { 
;
//BA.debugLineNum = 6;BA.debugLine="ToolBar.Height = 64dip"[AddEmployeeLayout/General script]
views.get("toolbar").vw.setHeight((int)((64d * scale)));
//BA.debugLineNum = 7;BA.debugLine="Else"[AddEmployeeLayout/General script]
;}else{ 
;
//BA.debugLineNum = 8;BA.debugLine="If Portrait Then"[AddEmployeeLayout/General script]
if ((BA.ObjectToBoolean( String.valueOf(anywheresoftware.b4a.keywords.LayoutBuilder.isPortrait())))) { 
;
//BA.debugLineNum = 9;BA.debugLine="ToolBar.Height = 50dip"[AddEmployeeLayout/General script]
views.get("toolbar").vw.setHeight((int)((50d * scale)));
//BA.debugLineNum = 10;BA.debugLine="Else"[AddEmployeeLayout/General script]
;}else{ 
;
//BA.debugLineNum = 11;BA.debugLine="ToolBar.Height = 42dip"[AddEmployeeLayout/General script]
views.get("toolbar").vw.setHeight((int)((42d * scale)));
//BA.debugLineNum = 12;BA.debugLine="End If"[AddEmployeeLayout/General script]
;};
//BA.debugLineNum = 13;BA.debugLine="End If"[AddEmployeeLayout/General script]
;};
//BA.debugLineNum = 14;BA.debugLine="pnlMain.SetLeftAndRight(0%x, 100%x)"[AddEmployeeLayout/General script]
views.get("pnlmain").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlmain").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
//BA.debugLineNum = 15;BA.debugLine="pnlMain.SetTopAndBottom(ToolBar.Bottom, 100%y)"[AddEmployeeLayout/General script]
views.get("pnlmain").vw.setTop((int)((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight())));
views.get("pnlmain").vw.setHeight((int)((100d / 100 * height) - ((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight()))));
//BA.debugLineNum = 17;BA.debugLine="lbl1.Top = 2%y"[AddEmployeeLayout/General script]
views.get("lbl1").vw.setTop((int)((2d / 100 * height)));
//BA.debugLineNum = 18;BA.debugLine="lbl1.Left = 3%x"[AddEmployeeLayout/General script]
views.get("lbl1").vw.setLeft((int)((3d / 100 * width)));
//BA.debugLineNum = 19;BA.debugLine="pnlFirstNameAnchor.SetLeftAndRight(5%x, pnlMain.Width - 30%x)"[AddEmployeeLayout/General script]
views.get("pnlfirstnameanchor").vw.setLeft((int)((5d / 100 * width)));
views.get("pnlfirstnameanchor").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(30d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 20;BA.debugLine="pnlFirstNameAnchor.Top = lbl1.Bottom"[AddEmployeeLayout/General script]
views.get("pnlfirstnameanchor").vw.setTop((int)((views.get("lbl1").vw.getTop() + views.get("lbl1").vw.getHeight())));
//BA.debugLineNum = 22;BA.debugLine="txtFirstName.SetLeftAndRight(1%x, pnlFirstNameAnchor.Width - 1%x)"[AddEmployeeLayout/General script]
views.get("txtfirstname").vw.setLeft((int)((1d / 100 * width)));
views.get("txtfirstname").vw.setWidth((int)((views.get("pnlfirstnameanchor").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
//BA.debugLineNum = 23;BA.debugLine="txtFirstName.SetTopAndBottom(0.5%y, pnlFirstNameAnchor.Height - 0.5%y)"[AddEmployeeLayout/General script]
views.get("txtfirstname").vw.setTop((int)((0.5d / 100 * height)));
views.get("txtfirstname").vw.setHeight((int)((views.get("pnlfirstnameanchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
//BA.debugLineNum = 25;BA.debugLine="pnlMIAnchor.SetLeftAndRight(pnlFirstNameAnchor.Right + 5dip, pnlMain.Width - 2%x)"[AddEmployeeLayout/General script]
views.get("pnlmianchor").vw.setLeft((int)((views.get("pnlfirstnameanchor").vw.getLeft() + views.get("pnlfirstnameanchor").vw.getWidth())+(5d * scale)));
views.get("pnlmianchor").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(2d / 100 * width) - ((views.get("pnlfirstnameanchor").vw.getLeft() + views.get("pnlfirstnameanchor").vw.getWidth())+(5d * scale))));
//BA.debugLineNum = 26;BA.debugLine="pnlMIAnchor.Top = lbl1.Bottom"[AddEmployeeLayout/General script]
views.get("pnlmianchor").vw.setTop((int)((views.get("lbl1").vw.getTop() + views.get("lbl1").vw.getHeight())));
//BA.debugLineNum = 27;BA.debugLine="txtMI.SetLeftAndRight(1%x, pnlMIAnchor.Width - 1%x)"[AddEmployeeLayout/General script]
views.get("txtmi").vw.setLeft((int)((1d / 100 * width)));
views.get("txtmi").vw.setWidth((int)((views.get("pnlmianchor").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
//BA.debugLineNum = 28;BA.debugLine="txtMI.SetTopAndBottom(0.5%y, pnlMIAnchor.Height - 0.5%y)"[AddEmployeeLayout/General script]
views.get("txtmi").vw.setTop((int)((0.5d / 100 * height)));
views.get("txtmi").vw.setHeight((int)((views.get("pnlmianchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
//BA.debugLineNum = 30;BA.debugLine="pnlLastNameAnchor.SetLeftAndRight(5%x, pnlMain.Width - 30%x)"[AddEmployeeLayout/General script]
views.get("pnllastnameanchor").vw.setLeft((int)((5d / 100 * width)));
views.get("pnllastnameanchor").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(30d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 31;BA.debugLine="pnlLastNameAnchor.Top = pnlFirstNameAnchor.Bottom + 5dip"[AddEmployeeLayout/General script]
views.get("pnllastnameanchor").vw.setTop((int)((views.get("pnlfirstnameanchor").vw.getTop() + views.get("pnlfirstnameanchor").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 32;BA.debugLine="txtLastName.SetLeftAndRight(1%x, pnlLastNameAnchor.Width - 1%x)"[AddEmployeeLayout/General script]
views.get("txtlastname").vw.setLeft((int)((1d / 100 * width)));
views.get("txtlastname").vw.setWidth((int)((views.get("pnllastnameanchor").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
//BA.debugLineNum = 33;BA.debugLine="txtLastName.SetTopAndBottom(0.5%y, pnlLastNameAnchor.Height - 0.5%y)"[AddEmployeeLayout/General script]
views.get("txtlastname").vw.setTop((int)((0.5d / 100 * height)));
views.get("txtlastname").vw.setHeight((int)((views.get("pnllastnameanchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
//BA.debugLineNum = 35;BA.debugLine="pnlSuffixedAnchor.SetLeftAndRight(pnlLastNameAnchor.Right + 5dip, pnlMain.Width - 2%x)"[AddEmployeeLayout/General script]
views.get("pnlsuffixedanchor").vw.setLeft((int)((views.get("pnllastnameanchor").vw.getLeft() + views.get("pnllastnameanchor").vw.getWidth())+(5d * scale)));
views.get("pnlsuffixedanchor").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(2d / 100 * width) - ((views.get("pnllastnameanchor").vw.getLeft() + views.get("pnllastnameanchor").vw.getWidth())+(5d * scale))));
//BA.debugLineNum = 36;BA.debugLine="pnlSuffixedAnchor.Top = pnlMIAnchor.Bottom + 5dip"[AddEmployeeLayout/General script]
views.get("pnlsuffixedanchor").vw.setTop((int)((views.get("pnlmianchor").vw.getTop() + views.get("pnlmianchor").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 37;BA.debugLine="txtSuffixed.SetLeftAndRight(1%x, pnlSuffixedAnchor.Width - 1%x)"[AddEmployeeLayout/General script]
views.get("txtsuffixed").vw.setLeft((int)((1d / 100 * width)));
views.get("txtsuffixed").vw.setWidth((int)((views.get("pnlsuffixedanchor").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
//BA.debugLineNum = 38;BA.debugLine="txtSuffixed.SetTopAndBottom(0.5%y, pnlSuffixedAnchor.Height - 0.5%y)"[AddEmployeeLayout/General script]
views.get("txtsuffixed").vw.setTop((int)((0.5d / 100 * height)));
views.get("txtsuffixed").vw.setHeight((int)((views.get("pnlsuffixedanchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
//BA.debugLineNum = 41;BA.debugLine="lbl2.Top = pnlLastNameAnchor.Bottom + 5dip"[AddEmployeeLayout/General script]
views.get("lbl2").vw.setTop((int)((views.get("pnllastnameanchor").vw.getTop() + views.get("pnllastnameanchor").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 42;BA.debugLine="lbl2.Left = 3%x"[AddEmployeeLayout/General script]
views.get("lbl2").vw.setLeft((int)((3d / 100 * width)));
//BA.debugLineNum = 43;BA.debugLine="pnlBranchesAnchor.SetLeftAndRight(5%x, pnlMain.Width - 3%x)"[AddEmployeeLayout/General script]
views.get("pnlbranchesanchor").vw.setLeft((int)((5d / 100 * width)));
views.get("pnlbranchesanchor").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(3d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 44;BA.debugLine="pnlBranchesAnchor.Top = lbl2.Bottom"[AddEmployeeLayout/General script]
views.get("pnlbranchesanchor").vw.setTop((int)((views.get("lbl2").vw.getTop() + views.get("lbl2").vw.getHeight())));
//BA.debugLineNum = 45;BA.debugLine="cboBranches.SetLeftAndRight(1%x, pnlBranchesAnchor.Width - 1%x)"[AddEmployeeLayout/General script]
views.get("cbobranches").vw.setLeft((int)((1d / 100 * width)));
views.get("cbobranches").vw.setWidth((int)((views.get("pnlbranchesanchor").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
//BA.debugLineNum = 46;BA.debugLine="cboBranches.SetTopAndBottom(0.5%y, pnlBranchesAnchor.Height - 0.5%y)"[AddEmployeeLayout/General script]
views.get("cbobranches").vw.setTop((int)((0.5d / 100 * height)));
views.get("cbobranches").vw.setHeight((int)((views.get("pnlbranchesanchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
//BA.debugLineNum = 48;BA.debugLine="lbl3.Top = pnlBranchesAnchor.Bottom + 5dip"[AddEmployeeLayout/General script]
views.get("lbl3").vw.setTop((int)((views.get("pnlbranchesanchor").vw.getTop() + views.get("pnlbranchesanchor").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 49;BA.debugLine="lbl3.Left = 2%x"[AddEmployeeLayout/General script]
views.get("lbl3").vw.setLeft((int)((2d / 100 * width)));
//BA.debugLineNum = 50;BA.debugLine="pnlCareOfAnchor.SetLeftAndRight(5%x, pnlMain.Width - 3%x)"[AddEmployeeLayout/General script]
views.get("pnlcareofanchor").vw.setLeft((int)((5d / 100 * width)));
views.get("pnlcareofanchor").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(3d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 51;BA.debugLine="pnlCareOfAnchor.Top = lbl3.Bottom"[AddEmployeeLayout/General script]
views.get("pnlcareofanchor").vw.setTop((int)((views.get("lbl3").vw.getTop() + views.get("lbl3").vw.getHeight())));
//BA.debugLineNum = 52;BA.debugLine="txtDivision.SetLeftAndRight(1%x, pnlCareOfAnchor.Width - 1%x)"[AddEmployeeLayout/General script]
views.get("txtdivision").vw.setLeft((int)((1d / 100 * width)));
views.get("txtdivision").vw.setWidth((int)((views.get("pnlcareofanchor").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
//BA.debugLineNum = 53;BA.debugLine="txtDivision.SetTopAndBottom(0.5%y, pnlCareOfAnchor.Height - 0.5%y)"[AddEmployeeLayout/General script]
views.get("txtdivision").vw.setTop((int)((0.5d / 100 * height)));
views.get("txtdivision").vw.setHeight((int)((views.get("pnlcareofanchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
//BA.debugLineNum = 55;BA.debugLine="lbl4.Top = pnlCareOfAnchor.Bottom + 5dip"[AddEmployeeLayout/General script]
views.get("lbl4").vw.setTop((int)((views.get("pnlcareofanchor").vw.getTop() + views.get("pnlcareofanchor").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 56;BA.debugLine="lbl4.Left = 2%x"[AddEmployeeLayout/General script]
views.get("lbl4").vw.setLeft((int)((2d / 100 * width)));
//BA.debugLineNum = 57;BA.debugLine="pnlRemarksAnchor.SetLeftAndRight(5%x, pnlMain.Width - 3%x)"[AddEmployeeLayout/General script]
views.get("pnlremarksanchor").vw.setLeft((int)((5d / 100 * width)));
views.get("pnlremarksanchor").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(3d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 58;BA.debugLine="pnlRemarksAnchor.Top = lbl4.Bottom"[AddEmployeeLayout/General script]
views.get("pnlremarksanchor").vw.setTop((int)((views.get("lbl4").vw.getTop() + views.get("lbl4").vw.getHeight())));
//BA.debugLineNum = 59;BA.debugLine="txtRemarks.SetLeftAndRight(1%x, pnlRemarksAnchor.Width - 1%x)"[AddEmployeeLayout/General script]
views.get("txtremarks").vw.setLeft((int)((1d / 100 * width)));
views.get("txtremarks").vw.setWidth((int)((views.get("pnlremarksanchor").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
//BA.debugLineNum = 60;BA.debugLine="txtRemarks.SetTopAndBottom(0.5%y, pnlRemarksAnchor.Height - 0.5%y)"[AddEmployeeLayout/General script]
views.get("txtremarks").vw.setTop((int)((0.5d / 100 * height)));
views.get("txtremarks").vw.setHeight((int)((views.get("pnlremarksanchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
//BA.debugLineNum = 62;BA.debugLine="lbl5.Top = pnlRemarksAnchor.Bottom + 5dip"[AddEmployeeLayout/General script]
views.get("lbl5").vw.setTop((int)((views.get("pnlremarksanchor").vw.getTop() + views.get("pnlremarksanchor").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 63;BA.debugLine="lbl5.Left = 2%x"[AddEmployeeLayout/General script]
views.get("lbl5").vw.setLeft((int)((2d / 100 * width)));
//BA.debugLineNum = 64;BA.debugLine="pnlAddedByAnchor.SetLeftAndRight(5%x, pnlMain.Width - 3%x)"[AddEmployeeLayout/General script]
views.get("pnladdedbyanchor").vw.setLeft((int)((5d / 100 * width)));
views.get("pnladdedbyanchor").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(3d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 65;BA.debugLine="pnlAddedByAnchor.Top = lbl5.Bottom"[AddEmployeeLayout/General script]
views.get("pnladdedbyanchor").vw.setTop((int)((views.get("lbl5").vw.getTop() + views.get("lbl5").vw.getHeight())));
//BA.debugLineNum = 66;BA.debugLine="txtAddedBy.SetLeftAndRight(1%x, pnlAddedByAnchor.Width - 1%x)"[AddEmployeeLayout/General script]
views.get("txtaddedby").vw.setLeft((int)((1d / 100 * width)));
views.get("txtaddedby").vw.setWidth((int)((views.get("pnladdedbyanchor").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
//BA.debugLineNum = 67;BA.debugLine="txtAddedBy.SetTopAndBottom(0.5%y, pnlAddedByAnchor.Height - 0.5%y)"[AddEmployeeLayout/General script]
views.get("txtaddedby").vw.setTop((int)((0.5d / 100 * height)));
views.get("txtaddedby").vw.setHeight((int)((views.get("pnladdedbyanchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
//BA.debugLineNum = 69;BA.debugLine="btnCancel.SetLeftAndRight(2%x,pnlMain.Width - 50%x)"[AddEmployeeLayout/General script]
views.get("btncancel").vw.setLeft((int)((2d / 100 * width)));
views.get("btncancel").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(50d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 70;BA.debugLine="btnCancel.SetTopAndBottom (80%y, pnlMain.Height - 3%y)"[AddEmployeeLayout/General script]
views.get("btncancel").vw.setTop((int)((80d / 100 * height)));
views.get("btncancel").vw.setHeight((int)((views.get("pnlmain").vw.getHeight())-(3d / 100 * height) - ((80d / 100 * height))));
//BA.debugLineNum = 71;BA.debugLine="btnSave.SetLeftAndRight(52%x,pnlMain.Width - 2%x)"[AddEmployeeLayout/General script]
views.get("btnsave").vw.setLeft((int)((52d / 100 * width)));
views.get("btnsave").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(2d / 100 * width) - ((52d / 100 * width))));
//BA.debugLineNum = 72;BA.debugLine="btnSave.SetTopAndBottom (80%y, pnlMain.Height - 3%y)"[AddEmployeeLayout/General script]
views.get("btnsave").vw.setTop((int)((80d / 100 * height)));
views.get("btnsave").vw.setHeight((int)((views.get("pnlmain").vw.getHeight())-(3d / 100 * height) - ((80d / 100 * height))));

}
}