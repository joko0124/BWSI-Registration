package bwsi.registration.townhall.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_guestlisting{

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
views.get("pnlsearchanchor").vw.setLeft((int)((1d / 100 * width)));
views.get("pnlsearchanchor").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
views.get("pnlsearchanchor").vw.setTop((int)((1d / 100 * height)));
views.get("pnlsearchanchor").vw.setHeight((int)((9d / 100 * height) - ((1d / 100 * height))));
views.get("lblsearchicon").vw.setLeft((int)((1d / 100 * width)));
views.get("lblsearchicon").vw.setTop((int)((0.5d / 100 * height)));
views.get("lblsearchicon").vw.setHeight((int)((views.get("pnlsearchanchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("txtsearch").vw.setLeft((int)((views.get("lblsearchicon").vw.getLeft() + views.get("lblsearchicon").vw.getWidth())+(5d * scale)));
views.get("txtsearch").vw.setWidth((int)((views.get("pnlsearchanchor").vw.getWidth())-(1d / 100 * width) - ((views.get("lblsearchicon").vw.getLeft() + views.get("lblsearchicon").vw.getWidth())+(5d * scale))));
views.get("txtsearch").vw.setTop((int)((0.5d / 100 * height)));
views.get("txtsearch").vw.setHeight((int)((views.get("pnlsearchanchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("clvguests").vw.setLeft((int)((1d / 100 * width)));
views.get("clvguests").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
views.get("clvguests").vw.setTop((int)((views.get("pnlsearchanchor").vw.getTop() + views.get("pnlsearchanchor").vw.getHeight())+(10d * scale)));
views.get("clvguests").vw.setHeight((int)((views.get("pnlmain").vw.getHeight())-(5d / 100 * height) - ((views.get("pnlsearchanchor").vw.getTop() + views.get("pnlsearchanchor").vw.getHeight())+(10d * scale))));
views.get("pnlstatus").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlstatus").vw.setWidth((int)((views.get("pnlmain").vw.getWidth()) - ((0d / 100 * width))));
views.get("pnlstatus").vw.setTop((int)((views.get("clvguests").vw.getTop() + views.get("clvguests").vw.getHeight())));
views.get("pnlstatus").vw.setHeight((int)((views.get("pnlmain").vw.getHeight()) - ((views.get("clvguests").vw.getTop() + views.get("clvguests").vw.getHeight()))));
views.get("lblreccount").vw.setLeft((int)((3d / 100 * width)));
views.get("lblreccount").vw.setWidth((int)((views.get("pnlstatus").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
views.get("lblreccount").vw.setTop((int)((1d / 100 * height)));
views.get("lblreccount").vw.setHeight((int)((views.get("pnlstatus").vw.getHeight())-(1d / 100 * height) - ((1d / 100 * height))));

}
}