package bwsi.registration.townhall.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_itemlayout{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("pnltitle").vw.setLeft((int)((0d / 100 * width)));
views.get("pnltitle").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("lblareas").vw.setTop((int)((1d / 100 * height)));
views.get("lblareas").vw.setHeight((int)((views.get("pnltitle").vw.getHeight())-(1d / 100 * height) - ((1d / 100 * height))));
views.get("lblareas").vw.setLeft((int)((2d / 100 * width)));
views.get("lblareas").vw.setWidth((int)((views.get("pnltitle").vw.getWidth())-(12d / 100 * width) - ((2d / 100 * width))));
views.get("arrow").vw.setLeft((int)((views.get("pnltitle").vw.getWidth())-(2d / 100 * width) - (views.get("arrow").vw.getWidth())));
views.get("arrow").vw.setTop((int)((views.get("lblareas").vw.getTop() + views.get("lblareas").vw.getHeight()/2) - (views.get("arrow").vw.getHeight() / 2)));
views.get("pnldetails").vw.setLeft((int)((0d / 100 * width)));
views.get("pnldetails").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("pnldetails").vw.setTop((int)((views.get("pnltitle").vw.getTop() + views.get("pnltitle").vw.getHeight())+(5d * scale)));
views.get("pnldetails").vw.setHeight((int)((30d / 100 * height) - ((views.get("pnltitle").vw.getTop() + views.get("pnltitle").vw.getHeight())+(5d * scale))));

}
}