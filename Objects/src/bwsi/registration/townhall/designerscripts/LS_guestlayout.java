package bwsi.registration.townhall.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_guestlayout{

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
views.get("txtguestname").vw.setLeft((int)((views.get("lbl1").vw.getLeft())));
views.get("txtguestname").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(2d / 100 * width) - ((views.get("lbl1").vw.getLeft()))));
views.get("txtguestname").vw.setTop((int)((views.get("lbl1").vw.getTop() + views.get("lbl1").vw.getHeight())));
views.get("lbl2").vw.setTop((int)((views.get("txtguestname").vw.getTop() + views.get("txtguestname").vw.getHeight())+(5d * scale)));
views.get("lbl2").vw.setLeft((int)((2d / 100 * width)));
views.get("txtremarks").vw.setLeft((int)((views.get("lbl2").vw.getLeft())));
views.get("txtremarks").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(2d / 100 * width) - ((views.get("lbl2").vw.getLeft()))));
views.get("txtremarks").vw.setTop((int)((views.get("lbl2").vw.getTop() + views.get("lbl2").vw.getHeight())));
views.get("lbl3").vw.setTop((int)((views.get("txtremarks").vw.getTop() + views.get("txtremarks").vw.getHeight())+(5d * scale)));
views.get("lbl3").vw.setLeft((int)((2d / 100 * width)));
views.get("txtcareof").vw.setLeft((int)((views.get("lbl3").vw.getLeft())));
views.get("txtcareof").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(2d / 100 * width) - ((views.get("lbl3").vw.getLeft()))));
views.get("txtcareof").vw.setTop((int)((views.get("lbl3").vw.getTop() + views.get("lbl3").vw.getHeight())));
views.get("lbl4").vw.setTop((int)((views.get("txtcareof").vw.getTop() + views.get("txtcareof").vw.getHeight())+(5d * scale)));
views.get("lbl4").vw.setLeft((int)((2d / 100 * width)));
views.get("txtapprovedby").vw.setLeft((int)((views.get("lbl4").vw.getLeft())));
views.get("txtapprovedby").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(2d / 100 * width) - ((views.get("lbl4").vw.getLeft()))));
views.get("txtapprovedby").vw.setTop((int)((views.get("lbl4").vw.getTop() + views.get("lbl4").vw.getHeight())));
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