package bwsi.registration.townhall.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_employeelist{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("pnldetails").vw.setLeft((int)((0d / 100 * width)));
views.get("pnldetails").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("pnldetails").vw.setTop((int)((0d / 100 * width)));
views.get("pnldetails").vw.setHeight((int)((100d / 100 * height) - ((0d / 100 * width))));
views.get("lblbranch").vw.setTop((int)((1d / 100 * height)));
views.get("lblbranch").vw.setLeft((int)((1d / 100 * width)));
views.get("lblbranch").vw.setWidth((int)((views.get("pnldetails").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
views.get("avatarbg").vw.setLeft((int)((3d / 100 * width)));
views.get("avatarbg").vw.setTop((int)((views.get("lblbranch").vw.getTop() + views.get("lblbranch").vw.getHeight())+(10d * scale)));
views.get("lblempname").vw.setTop((int)((views.get("avatarbg").vw.getTop() + views.get("avatarbg").vw.getHeight()/2)-(8d * scale) - (views.get("lblempname").vw.getHeight() / 2)));
views.get("lblempname").vw.setLeft((int)((views.get("avatarbg").vw.getLeft() + views.get("avatarbg").vw.getWidth())+(5d * scale)));
views.get("lblempname").vw.setWidth((int)((views.get("pnldetails").vw.getWidth())-(2d / 100 * width) - ((views.get("avatarbg").vw.getLeft() + views.get("avatarbg").vw.getWidth())+(5d * scale))));
views.get("lbldivision").vw.setLeft((int)((views.get("avatarbg").vw.getLeft() + views.get("avatarbg").vw.getWidth())+(5d * scale)));
views.get("lbldivision").vw.setWidth((int)((views.get("pnldetails").vw.getWidth())-(2d / 100 * width) - ((views.get("avatarbg").vw.getLeft() + views.get("avatarbg").vw.getWidth())+(5d * scale))));
views.get("lbldivision").vw.setTop((int)((views.get("lblempname").vw.getTop() + views.get("lblempname").vw.getHeight())-(5d * scale)));
views.get("lblstatus").vw.setTop((int)((views.get("avatarbg").vw.getTop() + views.get("avatarbg").vw.getHeight())+(5d * scale)));
views.get("btnswap").vw.setTop((int)((views.get("lblstatus").vw.getTop() + views.get("lblstatus").vw.getHeight())+(5d * scale)));
views.get("btnswap").vw.setLeft((int)((20d / 100 * width)));
views.get("btnswap").vw.setWidth((int)((views.get("pnldetails").vw.getWidth())-(40d / 100 * width) - ((20d / 100 * width))));
views.get("btnregister").vw.setTop((int)((views.get("lblstatus").vw.getTop() + views.get("lblstatus").vw.getHeight())+(5d * scale)));
views.get("btnregister").vw.setLeft((int)((views.get("btnswap").vw.getLeft() + views.get("btnswap").vw.getWidth())+(7d * scale)));
views.get("btnregister").vw.setWidth((int)((views.get("pnldetails").vw.getWidth())-(1d / 100 * height) - ((views.get("btnswap").vw.getLeft() + views.get("btnswap").vw.getWidth())+(7d * scale))));

}
}