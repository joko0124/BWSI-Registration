package bwsi.registration.yearend.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_login{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("pnlareasettings").vw.setLeft((int)((78d / 100 * width)));
views.get("pnlareasettings").vw.setTop((int)((5d * scale)));
views.get("pnlareas").vw.setWidth((int)((45d * scale)));
views.get("pnlareas").vw.setHeight((int)((45d * scale)));
views.get("pnlareas").vw.setLeft((int)((views.get("pnlareasettings").vw.getWidth())/2d - (views.get("pnlareas").vw.getWidth() / 2)));
views.get("btnarea").vw.setWidth((int)((38d * scale)));
views.get("btnarea").vw.setHeight((int)((38d * scale)));
views.get("btnarea").vw.setLeft((int)((views.get("pnlareas").vw.getWidth())/2d - (views.get("btnarea").vw.getWidth() / 2)));
views.get("btnarea").vw.setTop((int)((views.get("pnlareas").vw.getHeight())/2d - (views.get("btnarea").vw.getHeight() / 2)));
views.get("lblareaicon").vw.setLeft((int)((views.get("btnarea").vw.getWidth())/2d - (views.get("lblareaicon").vw.getWidth() / 2)));
views.get("lblareaicon").vw.setTop((int)((views.get("btnarea").vw.getHeight())/2d - (views.get("lblareaicon").vw.getHeight() / 2)));
views.get("lblarea").vw.setLeft((int)((0d / 100 * width)));
views.get("lblarea").vw.setWidth((int)((views.get("pnlareasettings").vw.getWidth()) - ((0d / 100 * width))));
views.get("lblarea").vw.setTop((int)((views.get("pnlareas").vw.getTop() + views.get("pnlareas").vw.getHeight())+(2d * scale)));

}
}