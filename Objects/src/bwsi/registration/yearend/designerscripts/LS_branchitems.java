package bwsi.registration.yearend.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_branchitems{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 3;BA.debugLine="lblBranches.SetLeftAndRight(0%X, 100%X)"[BranchItems/General script]
views.get("lblbranches").vw.setLeft((int)((0d / 100 * width)));
views.get("lblbranches").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
//BA.debugLineNum = 4;BA.debugLine="lblBranches.HorizontalCenter = 50%x"[BranchItems/General script]
views.get("lblbranches").vw.setLeft((int)((50d / 100 * width) - (views.get("lblbranches").vw.getWidth() / 2)));
//BA.debugLineNum = 5;BA.debugLine="lblBranches.SetTopAndBottom(0%Y,100%Y)"[BranchItems/General script]
views.get("lblbranches").vw.setTop((int)((0d / 100 * height)));
views.get("lblbranches").vw.setHeight((int)((100d / 100 * height) - ((0d / 100 * height))));

}
}