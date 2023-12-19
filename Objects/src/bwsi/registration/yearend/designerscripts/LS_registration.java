package bwsi.registration.yearend.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_registration{

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
views.get("pnltitle").vw.setLeft((int)((1d / 100 * width)));
views.get("pnltitle").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
views.get("pnltitle").vw.setTop((int)((views.get("pnlsearchanchor").vw.getTop() + views.get("pnlsearchanchor").vw.getHeight())+(10d * scale)));
views.get("pnltitle").vw.setHeight((int)((18d / 100 * height) - ((views.get("pnlsearchanchor").vw.getTop() + views.get("pnlsearchanchor").vw.getHeight())+(10d * scale))));
views.get("clvemployees").vw.setLeft((int)((1d / 100 * width)));
views.get("clvemployees").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
views.get("clvemployees").vw.setTop((int)((views.get("pnlsearchanchor").vw.getTop() + views.get("pnlsearchanchor").vw.getHeight())+(5d * scale)));
views.get("clvemployees").vw.setHeight((int)((views.get("pnlmain").vw.getHeight())-(1d / 100 * height) - ((views.get("pnlsearchanchor").vw.getTop() + views.get("pnlsearchanchor").vw.getHeight())+(5d * scale))));
views.get("pnlsearchmain").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlsearchmain").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("pnlsearchmain").vw.setTop((int)((0d / 100 * height)));
views.get("pnlsearchmain").vw.setHeight((int)((100d / 100 * height) - ((0d / 100 * height))));
views.get("pnlsearch").vw.setLeft((int)((1d / 100 * width)));
views.get("pnlsearch").vw.setWidth((int)((99d / 100 * width) - ((1d / 100 * width))));
views.get("pnlsearch").vw.setLeft((int)((50d / 100 * width) - (views.get("pnlsearch").vw.getWidth() / 2)));
views.get("pnlsearch").vw.setTop((int)((5d / 100 * height)));
views.get("pnlsearch").vw.setHeight((int)((80d / 100 * height) - ((5d / 100 * height))));
views.get("pnlsearch").vw.setTop((int)((50d / 100 * height) - (views.get("pnlsearch").vw.getHeight() / 2)));
views.get("lblsearchtitle").vw.setWidth((int)((views.get("pnlsearch").vw.getWidth())));
views.get("lblsearchtitle").vw.setHeight((int)((5d / 100 * height)));
views.get("lblsearchtitle").vw.setTop((int)((0d * scale)));
views.get("btncancel").vw.setTop((int)((68d / 100 * height)));
views.get("btncancel").vw.setHeight((int)((74d / 100 * height) - ((68d / 100 * height))));
views.get("btncancel").vw.setLeft((int)((63d / 100 * width)));
views.get("btncancel").vw.setWidth((int)((views.get("pnlsearch").vw.getLeft() + views.get("pnlsearch").vw.getWidth())-(25d * scale) - ((63d / 100 * width))));
views.get("pnlsearchemployees").vw.setTop((int)((views.get("lblsearchtitle").vw.getTop() + views.get("lblsearchtitle").vw.getHeight())+(2d * scale)));
views.get("pnlsearchemployees").vw.setHeight((int)((views.get("btncancel").vw.getTop())-(5d * scale) - ((views.get("lblsearchtitle").vw.getTop() + views.get("lblsearchtitle").vw.getHeight())+(2d * scale))));
views.get("pnlsearchemployees").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlsearchemployees").vw.setWidth((int)((97d / 100 * width) - ((0d / 100 * width))));
views.get("pnlsearchemployees").vw.setLeft((int)((49d / 100 * width) - (views.get("pnlsearchemployees").vw.getWidth() / 2)));

}
}