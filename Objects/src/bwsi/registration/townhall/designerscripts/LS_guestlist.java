package bwsi.registration.townhall.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_guestlist{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("pnldetails").vw.setLeft((int)((0d / 100 * width)));
views.get("pnldetails").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("pnldetails").vw.setTop((int)((0d / 100 * width)));
views.get("pnldetails").vw.setHeight((int)((100d / 100 * height) - ((0d / 100 * width))));
views.get("avatarbg").vw.setLeft((int)((3d / 100 * width)));
views.get("avatarbg").vw.setTop((int)((3d / 100 * height)));
views.get("lblguestname").vw.setTop((int)((views.get("avatarbg").vw.getTop() + views.get("avatarbg").vw.getHeight()/2)-(8d * scale) - (views.get("lblguestname").vw.getHeight() / 2)));
views.get("lblguestname").vw.setLeft((int)((views.get("avatarbg").vw.getLeft() + views.get("avatarbg").vw.getWidth())+(5d * scale)));
views.get("lblguestname").vw.setWidth((int)((views.get("pnldetails").vw.getWidth())-(2d / 100 * width) - ((views.get("avatarbg").vw.getLeft() + views.get("avatarbg").vw.getWidth())+(5d * scale))));
views.get("lblposition").vw.setLeft((int)((views.get("avatarbg").vw.getLeft() + views.get("avatarbg").vw.getWidth())+(5d * scale)));
views.get("lblposition").vw.setWidth((int)((views.get("pnldetails").vw.getWidth())-(2d / 100 * width) - ((views.get("avatarbg").vw.getLeft() + views.get("avatarbg").vw.getWidth())+(5d * scale))));
views.get("lblposition").vw.setTop((int)((views.get("lblguestname").vw.getTop() + views.get("lblguestname").vw.getHeight())-(5d * scale)));

}
}