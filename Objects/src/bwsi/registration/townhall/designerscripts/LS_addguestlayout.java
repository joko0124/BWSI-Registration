package bwsi.registration.townhall.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_addguestlayout{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("toolbar").vw.setTop((int)((0d * scale)));
if ((anywheresoftware.b4a.keywords.LayoutBuilder.getScreenSize()>6.5d)) { 
;
views.get("toolbar").vw.setHeight((int)((64d * scale)));
;}else{ 
;
if ((BA.ObjectToBoolean( String.valueOf(anywheresoftware.b4a.keywords.LayoutBuilder.isPortrait())))) { 
;
views.get("toolbar").vw.setHeight((int)((50d * scale)));
;}else{ 
;
views.get("toolbar").vw.setHeight((int)((42d * scale)));
;};
;};
views.get("pnlmain").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlmain").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("pnlmain").vw.setTop((int)((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight())));
views.get("pnlmain").vw.setHeight((int)((100d / 100 * height) - ((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight()))));
views.get("lbl1").vw.setTop((int)((2d / 100 * height)));
views.get("lbl1").vw.setLeft((int)((2d / 100 * width)));
views.get("lbl1").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("pnlguestnameanchor").vw.setLeft((int)((5d / 100 * width)));
views.get("pnlguestnameanchor").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(3d / 100 * width) - ((5d / 100 * width))));
views.get("pnlguestnameanchor").vw.setTop((int)((views.get("lbl1").vw.getTop() + views.get("lbl1").vw.getHeight())));
views.get("txtguestname").vw.setLeft((int)((1d / 100 * width)));
views.get("txtguestname").vw.setWidth((int)((views.get("pnlguestnameanchor").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
views.get("txtguestname").vw.setTop((int)((0.5d / 100 * height)));
views.get("txtguestname").vw.setHeight((int)((views.get("pnlguestnameanchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("lbl2").vw.setTop((int)((views.get("pnlguestnameanchor").vw.getTop() + views.get("pnlguestnameanchor").vw.getHeight())+(5d * scale)));
views.get("lbl2").vw.setLeft((int)((2d / 100 * width)));
views.get("lbl2").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("pnlpositionanchor").vw.setLeft((int)((5d / 100 * width)));
views.get("pnlpositionanchor").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(3d / 100 * width) - ((5d / 100 * width))));
views.get("pnlpositionanchor").vw.setTop((int)((views.get("lbl2").vw.getTop() + views.get("lbl2").vw.getHeight())));
views.get("txtposition").vw.setLeft((int)((1d / 100 * width)));
views.get("txtposition").vw.setWidth((int)((views.get("pnlpositionanchor").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
views.get("txtposition").vw.setTop((int)((0.5d / 100 * height)));
views.get("txtposition").vw.setHeight((int)((views.get("pnlpositionanchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("lbl3").vw.setTop((int)((views.get("pnlpositionanchor").vw.getTop() + views.get("pnlpositionanchor").vw.getHeight())+(5d * scale)));
views.get("lbl3").vw.setLeft((int)((2d / 100 * width)));
views.get("lbl3").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("pnlcareofanchor").vw.setLeft((int)((5d / 100 * width)));
views.get("pnlcareofanchor").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(35d / 100 * width) - ((5d / 100 * width))));
views.get("pnlcareofanchor").vw.setTop((int)((views.get("lbl3").vw.getTop() + views.get("lbl3").vw.getHeight())));
views.get("txtcareof").vw.setLeft((int)((1d / 100 * width)));
views.get("txtcareof").vw.setWidth((int)((views.get("pnlcareofanchor").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
views.get("txtcareof").vw.setTop((int)((0.5d / 100 * height)));
views.get("txtcareof").vw.setHeight((int)((views.get("pnlcareofanchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("lbl4").vw.setTop((int)((views.get("lbl3").vw.getTop())));
views.get("lbl4").vw.setLeft((int)((views.get("pnlcareofanchor").vw.getLeft() + views.get("pnlcareofanchor").vw.getWidth())));
views.get("pnltablenoanchor").vw.setLeft((int)((views.get("pnlcareofanchor").vw.getLeft() + views.get("pnlcareofanchor").vw.getWidth())+(5d * scale)));
views.get("pnltablenoanchor").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(3d / 100 * width) - ((views.get("pnlcareofanchor").vw.getLeft() + views.get("pnlcareofanchor").vw.getWidth())+(5d * scale))));
views.get("pnltablenoanchor").vw.setTop((int)((views.get("lbl4").vw.getTop() + views.get("lbl4").vw.getHeight())));
views.get("txttableno").vw.setLeft((int)((1d / 100 * width)));
views.get("txttableno").vw.setWidth((int)((views.get("pnltablenoanchor").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
views.get("txttableno").vw.setTop((int)((0.5d / 100 * height)));
views.get("txttableno").vw.setHeight((int)((views.get("pnltablenoanchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("lbl5").vw.setTop((int)((views.get("pnltablenoanchor").vw.getTop() + views.get("pnltablenoanchor").vw.getHeight())+(5d * scale)));
views.get("lbl5").vw.setLeft((int)((2d / 100 * width)));
views.get("lbl5").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("pnlremarksanchor").vw.setLeft((int)((5d / 100 * width)));
views.get("pnlremarksanchor").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(3d / 100 * width) - ((5d / 100 * width))));
views.get("pnlremarksanchor").vw.setTop((int)((views.get("lbl5").vw.getTop() + views.get("lbl5").vw.getHeight())));
views.get("txtremarks").vw.setLeft((int)((1d / 100 * width)));
views.get("txtremarks").vw.setWidth((int)((views.get("pnlremarksanchor").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
views.get("txtremarks").vw.setTop((int)((0.5d / 100 * height)));
views.get("txtremarks").vw.setHeight((int)((views.get("pnlremarksanchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("lbl6").vw.setTop((int)((views.get("pnlremarksanchor").vw.getTop() + views.get("pnlremarksanchor").vw.getHeight())+(5d * scale)));
views.get("lbl6").vw.setLeft((int)((2d / 100 * width)));
views.get("lbl6").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("pnlapprovedbyanchor").vw.setLeft((int)((5d / 100 * width)));
views.get("pnlapprovedbyanchor").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(3d / 100 * width) - ((5d / 100 * width))));
views.get("pnlapprovedbyanchor").vw.setTop((int)((views.get("lbl6").vw.getTop() + views.get("lbl6").vw.getHeight())));
views.get("txtapprovedby").vw.setLeft((int)((1d / 100 * width)));
views.get("txtapprovedby").vw.setWidth((int)((views.get("pnlapprovedbyanchor").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
views.get("txtapprovedby").vw.setTop((int)((0.5d / 100 * height)));
views.get("txtapprovedby").vw.setHeight((int)((views.get("pnlapprovedbyanchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("btncancel").vw.setLeft((int)((2d / 100 * width)));
views.get("btncancel").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(50d / 100 * width) - ((2d / 100 * width))));
views.get("btncancel").vw.setTop((int)((80d / 100 * height)));
views.get("btncancel").vw.setHeight((int)((views.get("pnlmain").vw.getHeight())-(3d / 100 * height) - ((80d / 100 * height))));
views.get("btnsave").vw.setLeft((int)((52d / 100 * width)));
views.get("btnsave").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(2d / 100 * width) - ((52d / 100 * width))));
views.get("btnsave").vw.setTop((int)((80d / 100 * height)));
views.get("btnsave").vw.setHeight((int)((views.get("pnlmain").vw.getHeight())-(3d / 100 * height) - ((80d / 100 * height))));

}
}