package bwsi.registration.townhall.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_login{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("pnlsettings").vw.setLeft((int)(0d));
views.get("pnlsettings").vw.setWidth((int)((100d / 100 * width) - (0d)));
views.get("pnlsettings").vw.setTop((int)(0d));
views.get("pnlsettings").vw.setHeight((int)((100d / 100 * height) - (0d)));
views.get("lbltitle").vw.setTop((int)((23d / 100 * height)));
views.get("lbltitle").vw.setLeft((int)((2d / 100 * width)));
views.get("lbltitle").vw.setWidth((int)((views.get("pnlsettings").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("pnlassignment").vw.setLeft((int)((2d / 100 * width)));
views.get("pnlassignment").vw.setWidth((int)((views.get("pnlsettings").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("pnlassignment").vw.setTop((int)((30d / 100 * height)));
views.get("pnlassignment").vw.setHeight((int)((views.get("pnlsettings").vw.getHeight())-(40d / 100 * height) - ((30d / 100 * height))));
views.get("lbl1").vw.setTop((int)((2d / 100 * height)));
views.get("lbl1").vw.setLeft((int)((3d / 100 * width)));
views.get("lbl1").vw.setWidth((int)((views.get("pnlassignment").vw.getWidth())-(1d / 100 * width) - ((3d / 100 * width))));
views.get("pnlempnameanchor").vw.setLeft((int)((4d / 100 * width)));
views.get("pnlempnameanchor").vw.setWidth((int)((views.get("pnlassignment").vw.getWidth())-(4d / 100 * width) - ((4d / 100 * width))));
views.get("pnlempnameanchor").vw.setTop((int)((views.get("lbl1").vw.getTop() + views.get("lbl1").vw.getHeight())+(5d * scale)));
views.get("pnlempnameanchor").vw.setHeight((int)((views.get("pnlassignment").vw.getHeight())-(13d / 100 * height) - ((views.get("lbl1").vw.getTop() + views.get("lbl1").vw.getHeight())+(5d * scale))));
views.get("txtempname").vw.setLeft((int)((1d / 100 * width)));
views.get("txtempname").vw.setWidth((int)((views.get("pnlempnameanchor").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
views.get("txtempname").vw.setTop((int)((1d / 100 * height)));
views.get("txtempname").vw.setHeight((int)((views.get("pnlempnameanchor").vw.getHeight())-(1d / 100 * height) - ((1d / 100 * height))));
views.get("btnsave").vw.setTop((int)((views.get("pnlempnameanchor").vw.getTop() + views.get("pnlempnameanchor").vw.getHeight())+(20d * scale)));
views.get("btnsave").vw.setHeight((int)((views.get("pnlassignment").vw.getHeight())-(2d / 100 * height) - ((views.get("pnlempnameanchor").vw.getTop() + views.get("pnlempnameanchor").vw.getHeight())+(20d * scale))));
views.get("btnsave").vw.setLeft((int)((62d / 100 * width)));
views.get("btnsave").vw.setWidth((int)((views.get("pnlassignment").vw.getWidth())-(4d / 100 * width) - ((62d / 100 * width))));
views.get("btncancel").vw.setTop((int)((views.get("pnlempnameanchor").vw.getTop() + views.get("pnlempnameanchor").vw.getHeight())+(20d * scale)));
views.get("btncancel").vw.setHeight((int)((views.get("pnlassignment").vw.getHeight())-(2d / 100 * height) - ((views.get("pnlempnameanchor").vw.getTop() + views.get("pnlempnameanchor").vw.getHeight())+(20d * scale))));
views.get("btncancel").vw.setLeft((int)((30d / 100 * width)));
views.get("btncancel").vw.setWidth((int)((views.get("pnlassignment").vw.getWidth())-(36d / 100 * width) - ((30d / 100 * width))));
views.get("pnlareasettings").vw.setLeft((int)((80d / 100 * width)));
views.get("pnlareasettings").vw.setTop((int)(0d));
views.get("pnlareas").vw.setWidth((int)((40d * scale)));
views.get("pnlareas").vw.setHeight((int)((40d * scale)));
views.get("pnlareas").vw.setLeft((int)((views.get("pnlareasettings").vw.getWidth())/2d - (views.get("pnlareas").vw.getWidth() / 2)));
views.get("btnarea").vw.setWidth((int)((35d * scale)));
views.get("btnarea").vw.setHeight((int)((35d * scale)));
views.get("btnarea").vw.setLeft((int)((views.get("pnlareas").vw.getWidth())/2d - (views.get("btnarea").vw.getWidth() / 2)));
views.get("btnarea").vw.setTop((int)((views.get("pnlareas").vw.getHeight())/2d - (views.get("btnarea").vw.getHeight() / 2)));
views.get("lblareaicon").vw.setLeft((int)((views.get("btnarea").vw.getWidth())/2d - (views.get("lblareaicon").vw.getWidth() / 2)));
views.get("lblareaicon").vw.setTop((int)((views.get("btnarea").vw.getHeight())/2d - (views.get("lblareaicon").vw.getHeight() / 2)));
views.get("lblarea").vw.setLeft((int)((0d / 100 * width)));
views.get("lblarea").vw.setWidth((int)((views.get("pnlareasettings").vw.getWidth()) - ((0d / 100 * width))));
views.get("lblarea").vw.setTop((int)((views.get("pnlareas").vw.getTop() + views.get("pnlareas").vw.getHeight())+(2d * scale)));

}
}