package bwsi.registration.townhall.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_mainscreennew{

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
views.get("pnlstyle").vw.setLeft((int)((1d / 100 * width)));
views.get("pnlstyle").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
views.get("pnlstyle").vw.setTop((int)((1d / 100 * height)));
views.get("pnlstyle").vw.setHeight((int)((views.get("pnlmain").vw.getHeight())-(53d / 100 * height) - ((1d / 100 * height))));
views.get("lblarea").vw.setTop((int)((5d * scale)));
views.get("lblarea").vw.setLeft((int)((3d / 100 * width)));
views.get("lblarea").vw.setWidth((int)((views.get("pnlstyle").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
views.get("lvbranches").vw.setTop((int)((views.get("lblarea").vw.getTop() + views.get("lblarea").vw.getHeight())+(10d * scale)));
views.get("lvbranches").vw.setHeight((int)((views.get("pnlstyle").vw.getHeight())-(2d / 100 * height) - ((views.get("lblarea").vw.getTop() + views.get("lblarea").vw.getHeight())+(10d * scale))));
views.get("lvbranches").vw.setLeft((int)((views.get("lblarea").vw.getLeft())+(2d / 100 * width)));
views.get("lvbranches").vw.setWidth((int)((views.get("lblarea").vw.getLeft() + views.get("lblarea").vw.getWidth())-(2d / 100 * width) - ((views.get("lblarea").vw.getLeft())+(2d / 100 * width))));
views.get("pnlchart").vw.setLeft((int)((1d / 100 * width)));
views.get("pnlchart").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
views.get("pnlchart").vw.setTop((int)((views.get("pnlstyle").vw.getTop() + views.get("pnlstyle").vw.getHeight())+(5d * scale)));
views.get("pnlchart").vw.setHeight((int)((55d / 100 * height) - ((views.get("pnlstyle").vw.getTop() + views.get("pnlstyle").vw.getHeight())+(5d * scale))));
views.get("lbl1").vw.setTop((int)((2d / 100 * height)));
views.get("lbl1").vw.setWidth((int)((42d / 100 * width)));
views.get("lbl1").vw.setLeft((int)((3d * scale)));
views.get("lbltotal").vw.setTop((int)((2d / 100 * height)));
views.get("lbltotal").vw.setWidth((int)((12d / 100 * width)));
views.get("lbltotal").vw.setLeft((int)((views.get("lbl1").vw.getLeft() + views.get("lbl1").vw.getWidth())+(5d * scale)));
views.get("lbl2").vw.setTop((int)((views.get("lbltotal").vw.getTop() + views.get("lbltotal").vw.getHeight())));
views.get("lbl2").vw.setWidth((int)((42d / 100 * width)));
views.get("lbl2").vw.setLeft((int)((3d * scale)));
views.get("lblregistered").vw.setTop((int)((views.get("lbltotal").vw.getTop() + views.get("lbltotal").vw.getHeight())));
views.get("lblregistered").vw.setWidth((int)((12d / 100 * width)));
views.get("lblregistered").vw.setLeft((int)((views.get("lbl2").vw.getLeft() + views.get("lbl2").vw.getWidth())+(5d * scale)));
views.get("lbl3").vw.setTop((int)((views.get("lbl2").vw.getTop() + views.get("lbl2").vw.getHeight())));
views.get("lbl3").vw.setWidth((int)((42d / 100 * width)));
views.get("lbl3").vw.setLeft((int)((3d * scale)));
views.get("lblunregistered").vw.setTop((int)((views.get("lblregistered").vw.getTop() + views.get("lblregistered").vw.getHeight())));
views.get("lblunregistered").vw.setWidth((int)((12d / 100 * width)));
views.get("lblunregistered").vw.setLeft((int)((views.get("lbl3").vw.getLeft() + views.get("lbl3").vw.getWidth())+(5d * scale)));
views.get("btnaddemployee").vw.setLeft((int)((5d / 100 * width)));
views.get("btnaddemployee").vw.setWidth((int)((95d / 100 * width) - ((5d / 100 * width))));
views.get("btnaddemployee").vw.setTop((int)((views.get("pnlchart").vw.getTop() + views.get("pnlchart").vw.getHeight())+(10d * scale)));
views.get("btnaddemployee").vw.setHeight((int)((views.get("pnlmain").vw.getHeight())-(25d / 100 * height) - ((views.get("pnlchart").vw.getTop() + views.get("pnlchart").vw.getHeight())+(10d * scale))));
views.get("btnaddguest").vw.setLeft((int)((5d / 100 * width)));
views.get("btnaddguest").vw.setWidth((int)((95d / 100 * width) - ((5d / 100 * width))));
views.get("btnaddguest").vw.setTop((int)((views.get("btnaddemployee").vw.getTop() + views.get("btnaddemployee").vw.getHeight())+(10d * scale)));
views.get("btnaddguest").vw.setHeight((int)((views.get("pnlmain").vw.getHeight())-(14d / 100 * height) - ((views.get("btnaddemployee").vw.getTop() + views.get("btnaddemployee").vw.getHeight())+(10d * scale))));
views.get("btnstart").vw.setTop((int)((views.get("btnaddguest").vw.getTop() + views.get("btnaddguest").vw.getHeight())+(10d * scale)));
views.get("btnstart").vw.setHeight((int)((views.get("pnlmain").vw.getHeight())-(1d / 100 * height) - ((views.get("btnaddguest").vw.getTop() + views.get("btnaddguest").vw.getHeight())+(10d * scale))));
views.get("btnstart").vw.setLeft((int)((5d / 100 * width)));
views.get("btnstart").vw.setWidth((int)((95d / 100 * width) - ((5d / 100 * width))));

}
}