package bwsi.registration.townhall;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class bbcodeview extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "bwsi.registration.townhall.bbcodeview");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", bwsi.registration.townhall.bbcodeview.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public String _meventname = "";
public Object _mcallback = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _mbase = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.collections.List _runs = null;
public bwsi.registration.townhall.bctextengine._bcparagraphstyle _style = null;
public bwsi.registration.townhall.bctextengine _mtextengine = null;
public String _mtext = "";
public anywheresoftware.b4a.objects.B4XViewWrapper _foregroundimageview = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _backgroundimageview = null;
public bwsi.registration.townhall.bctextengine._bcparagraph _paragraph = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _touchpanel = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _sv = null;
public anywheresoftware.b4a.objects.B4XCanvas.B4XRect _padding = null;
public bwsi.registration.townhall.bbcodeparser._bbcodeparsedata _parsedata = null;
public Object _tag = null;
public boolean _lazyloading = false;
public anywheresoftware.b4a.objects.collections.List _imageviewscache = null;
public bwsi.registration.townhall.b4xorderedmap _usedimageviews = null;
public anywheresoftware.b4a.objects.collections.List _externalruns = null;
public boolean _disableautomaticdrawingsinlazymode = false;
public bwsi.registration.townhall.main _main = null;
public bwsi.registration.townhall.actregistration _actregistration = null;
public bwsi.registration.townhall.registration _registration = null;
public bwsi.registration.townhall.mainscreen _mainscreen = null;
public bwsi.registration.townhall.scale _scale = null;
public bwsi.registration.townhall.addguest _addguest = null;
public bwsi.registration.townhall.addemployee _addemployee = null;
public bwsi.registration.townhall.globalvar _globalvar = null;
public bwsi.registration.townhall.dbfunctions _dbfunctions = null;
public bwsi.registration.townhall.dbutils _dbutils = null;
public bwsi.registration.townhall.starter _starter = null;
public bwsi.registration.townhall.httputils2service _httputils2service = null;
public bwsi.registration.townhall.b4xcollections _b4xcollections = null;
public String  _base_resize(double _width,double _height) throws Exception{
 //BA.debugLineNum = 97;BA.debugLine="Public Sub Base_Resize (Width As Double, Height As";
 //BA.debugLineNum = 98;BA.debugLine="sv.SetLayoutAnimated(0, 0, 0, Width, Height)";
_sv.SetLayoutAnimated((int) (0),(int) (0),(int) (0),(int) (_width),(int) (_height));
 //BA.debugLineNum = 99;BA.debugLine="sv.ScrollViewContentWidth = Width";
_sv.setScrollViewContentWidth((int) (_width));
 //BA.debugLineNum = 100;BA.debugLine="If DisableAutomaticDrawingsInLazyMode Then Return";
if (_disableautomaticdrawingsinlazymode) { 
if (true) return "";};
 //BA.debugLineNum = 101;BA.debugLine="If Runs.IsInitialized Then";
if (_runs.IsInitialized()) { 
 //BA.debugLineNum = 102;BA.debugLine="If ParseData.NeedToReparseWhenResize Then";
if (_parsedata.NeedToReparseWhenResize /*boolean*/ ) { 
 //BA.debugLineNum = 103;BA.debugLine="ParseAndDraw";
_parseanddraw();
 }else {
 //BA.debugLineNum = 105;BA.debugLine="Redraw";
_redraw();
 };
 };
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 4;BA.debugLine="Private mEventName As String 'ignore";
_meventname = "";
 //BA.debugLineNum = 5;BA.debugLine="Private mCallBack As Object 'ignore";
_mcallback = new Object();
 //BA.debugLineNum = 6;BA.debugLine="Public mBase As B4XView 'ignore";
_mbase = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 7;BA.debugLine="Private xui As XUI 'ignore";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 8;BA.debugLine="Private Runs As List";
_runs = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 9;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 10;BA.debugLine="Public Style As BCParagraphStyle";
_style = new bwsi.registration.townhall.bctextengine._bcparagraphstyle();
 //BA.debugLineNum = 11;BA.debugLine="Private mTextEngine As BCTextEngine";
_mtextengine = new bwsi.registration.townhall.bctextengine();
 //BA.debugLineNum = 12;BA.debugLine="Private mText As String";
_mtext = "";
 //BA.debugLineNum = 13;BA.debugLine="Public ForegroundImageView As B4XView";
_foregroundimageview = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Public BackgroundImageView As B4XView";
_backgroundimageview = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Public Paragraph As BCParagraph";
_paragraph = new bwsi.registration.townhall.bctextengine._bcparagraph();
 //BA.debugLineNum = 16;BA.debugLine="Private TouchPanel As B4XView";
_touchpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Public sv As B4XView";
_sv = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Public Padding As B4XRect";
_padding = new anywheresoftware.b4a.objects.B4XCanvas.B4XRect();
 //BA.debugLineNum = 19;BA.debugLine="Public ParseData As BBCodeParseData";
_parsedata = new bwsi.registration.townhall.bbcodeparser._bbcodeparsedata();
 //BA.debugLineNum = 20;BA.debugLine="Public Tag As Object";
_tag = new Object();
 //BA.debugLineNum = 21;BA.debugLine="Public LazyLoading As Boolean";
_lazyloading = false;
 //BA.debugLineNum = 22;BA.debugLine="Private ImageViewsCache As List";
_imageviewscache = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 23;BA.debugLine="Private UsedImageViews As B4XOrderedMap";
_usedimageviews = new bwsi.registration.townhall.b4xorderedmap();
 //BA.debugLineNum = 24;BA.debugLine="Public ExternalRuns As List";
_externalruns = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 25;BA.debugLine="Public DisableAutomaticDrawingsInLazyMode As Bool";
_disableautomaticdrawingsinlazymode = false;
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public String  _cleanexistingimageviews(boolean _invisibleonly,anywheresoftware.b4a.objects.collections.List _existing,int _offset,int _height) throws Exception{
bwsi.registration.townhall.bctextengine._bctextline _line = null;
anywheresoftware.b4a.objects.B4XViewWrapper _xiv = null;
 //BA.debugLineNum = 174;BA.debugLine="Private Sub CleanExistingImageViews (InvisibleOnly";
 //BA.debugLineNum = 175;BA.debugLine="For Each Line As BCTextLine In Existing";
{
final anywheresoftware.b4a.BA.IterableList group1 = _existing;
final int groupLen1 = group1.getSize()
;int index1 = 0;
;
for (; index1 < groupLen1;index1++){
_line = (bwsi.registration.townhall.bctextengine._bctextline)(group1.Get(index1));
 //BA.debugLineNum = 176;BA.debugLine="If InvisibleOnly = False Or LineIsVisible(Line,";
if (_invisibleonly==__c.False || _lineisvisible(_line,_offset,_height)==__c.False) { 
 //BA.debugLineNum = 177;BA.debugLine="Dim xiv As B4XView = UsedImageViews.Get(Line)";
_xiv = new anywheresoftware.b4a.objects.B4XViewWrapper();
_xiv = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_usedimageviews._get /*Object*/ ((Object)(_line))));
 //BA.debugLineNum = 178;BA.debugLine="xiv.RemoveViewFromParent";
_xiv.RemoveViewFromParent();
 //BA.debugLineNum = 179;BA.debugLine="xiv.SetBitmap(Null)";
_xiv.SetBitmap((android.graphics.Bitmap)(__c.Null));
 //BA.debugLineNum = 180;BA.debugLine="ImageViewsCache.Add(xiv)";
_imageviewscache.Add((Object)(_xiv.getObject()));
 //BA.debugLineNum = 181;BA.debugLine="If InvisibleOnly = True Then UsedImageViews.Rem";
if (_invisibleonly==__c.True) { 
_usedimageviews._remove /*String*/ ((Object)(_line));};
 };
 }
};
 //BA.debugLineNum = 184;BA.debugLine="End Sub";
return "";
}
public String  _designercreateview(Object _base,anywheresoftware.b4a.objects.LabelWrapper _lbl,anywheresoftware.b4a.objects.collections.Map _props) throws Exception{
anywheresoftware.b4a.objects.ScrollViewWrapper _sp = null;
anywheresoftware.b4a.objects.B4XViewWrapper _xlbl = null;
 //BA.debugLineNum = 53;BA.debugLine="Public Sub DesignerCreateView (Base As Object, Lbl";
 //BA.debugLineNum = 54;BA.debugLine="mBase = Base";
_mbase = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_base));
 //BA.debugLineNum = 60;BA.debugLine="Dim sp As ScrollView";
_sp = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 61;BA.debugLine="sp.Initialize2(50dip, \"sv\")";
_sp.Initialize2(ba,__c.DipToCurrent((int) (50)),"sv");
 //BA.debugLineNum = 67;BA.debugLine="LazyLoading = Props.GetDefault(\"LazyLoading\", Tru";
_lazyloading = BA.ObjectToBoolean(_props.GetDefault((Object)("LazyLoading"),(Object)(__c.True)));
 //BA.debugLineNum = 68;BA.debugLine="If LazyLoading Then";
if (_lazyloading) { 
 //BA.debugLineNum = 69;BA.debugLine="ImageViewsCache.Initialize";
_imageviewscache.Initialize();
 //BA.debugLineNum = 70;BA.debugLine="UsedImageViews = B4XCollections.CreateOrderedMap";
_usedimageviews = _b4xcollections._createorderedmap /*bwsi.registration.townhall.b4xorderedmap*/ (ba);
 };
 //BA.debugLineNum = 72;BA.debugLine="sv = sp";
_sv = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_sp.getObject()));
 //BA.debugLineNum = 73;BA.debugLine="sv.Color = mBase.Color";
_sv.setColor(_mbase.getColor());
 //BA.debugLineNum = 74;BA.debugLine="sv.ScrollViewInnerPanel.Color = mBase.Color";
_sv.getScrollViewInnerPanel().setColor(_mbase.getColor());
 //BA.debugLineNum = 75;BA.debugLine="mBase.Tag = Me";
_mbase.setTag(this);
 //BA.debugLineNum = 76;BA.debugLine="mBase.AddView(sv, 0, 0, mBase.Width, mBase.Height";
_mbase.AddView((android.view.View)(_sv.getObject()),(int) (0),(int) (0),_mbase.getWidth(),_mbase.getHeight());
 //BA.debugLineNum = 77;BA.debugLine="Dim xlbl As B4XView = Lbl";
_xlbl = new anywheresoftware.b4a.objects.B4XViewWrapper();
_xlbl = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_lbl.getObject()));
 //BA.debugLineNum = 78;BA.debugLine="mText = xlbl.Text";
_mtext = _xlbl.getText();
 //BA.debugLineNum = 79;BA.debugLine="ParseData.DefaultColor = xlbl.TextColor";
_parsedata.DefaultColor /*int*/  = _xlbl.getTextColor();
 //BA.debugLineNum = 80;BA.debugLine="ParseData.DefaultFont = xlbl.Font";
_parsedata.DefaultFont /*anywheresoftware.b4a.objects.B4XViewWrapper.B4XFont*/  = _xlbl.getFont();
 //BA.debugLineNum = 81;BA.debugLine="ParseData.ViewsPanel = sv.ScrollViewInnerPanel";
_parsedata.ViewsPanel /*anywheresoftware.b4a.objects.B4XViewWrapper*/  = _sv.getScrollViewInnerPanel();
 //BA.debugLineNum = 82;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_linkcl";
if (_xui.SubExists(ba,_mcallback,_meventname+"_linkclicked",(int) (1))) { 
 //BA.debugLineNum = 83;BA.debugLine="TouchPanel = xui.CreatePanel(\"TouchPanel\")";
_touchpanel = _xui.CreatePanel(ba,"TouchPanel");
 };
 //BA.debugLineNum = 90;BA.debugLine="ParseData.DefaultBoldFont = xui.CreateFont(Typefa";
_parsedata.DefaultBoldFont /*anywheresoftware.b4a.objects.B4XViewWrapper.B4XFont*/  = _xui.CreateFont(__c.Typeface.CreateNew(_lbl.getTypeface(),__c.Typeface.STYLE_BOLD),_xlbl.getTextSize());
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return "";
}
public String  _drawvisibleregion() throws Exception{
 //BA.debugLineNum = 133;BA.debugLine="Private Sub DrawVisibleRegion";
 //BA.debugLineNum = 134;BA.debugLine="If DisableAutomaticDrawingsInLazyMode Then Return";
if (_disableautomaticdrawingsinlazymode) { 
if (true) return "";};
 //BA.debugLineNum = 135;BA.debugLine="UpdateVisibleRegion(sv.ScrollViewOffsetY * mTextE";
_updatevisibleregion((int) (_sv.getScrollViewOffsetY()*_mtextengine._mscale /*float*/ ),(int) (_sv.getHeight()*_mtextengine._mscale /*float*/ ));
 //BA.debugLineNum = 136;BA.debugLine="End Sub";
return "";
}
public String  _gettext() throws Exception{
 //BA.debugLineNum = 129;BA.debugLine="Public Sub getText As String";
 //BA.debugLineNum = 130;BA.debugLine="Return mText";
if (true) return _mtext;
 //BA.debugLineNum = 131;BA.debugLine="End Sub";
return "";
}
public bwsi.registration.townhall.bctextengine  _gettextengine() throws Exception{
 //BA.debugLineNum = 120;BA.debugLine="Public Sub getTextEngine As BCTextEngine";
 //BA.debugLineNum = 121;BA.debugLine="Return mTextEngine";
if (true) return _mtextengine;
 //BA.debugLineNum = 122;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.Map  _getviews() throws Exception{
 //BA.debugLineNum = 45;BA.debugLine="Public Sub getViews As Map";
 //BA.debugLineNum = 46;BA.debugLine="Return ParseData.Views";
if (true) return _parsedata.Views /*anywheresoftware.b4a.objects.collections.Map*/ ;
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return null;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,Object _callback,String _eventname) throws Exception{
innerInitialize(_ba);
anywheresoftware.b4a.objects.ImageViewWrapper _iv = null;
 //BA.debugLineNum = 28;BA.debugLine="Public Sub Initialize (Callback As Object, EventNa";
 //BA.debugLineNum = 29;BA.debugLine="mEventName = EventName";
_meventname = _eventname;
 //BA.debugLineNum = 30;BA.debugLine="mCallBack = Callback";
_mcallback = _callback;
 //BA.debugLineNum = 31;BA.debugLine="Dim iv As ImageView";
_iv = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 32;BA.debugLine="iv.Initialize(\"\")";
_iv.Initialize(ba,"");
 //BA.debugLineNum = 33;BA.debugLine="ForegroundImageView = iv";
_foregroundimageview = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_iv.getObject()));
 //BA.debugLineNum = 34;BA.debugLine="ParseData.Initialize";
_parsedata.Initialize();
 //BA.debugLineNum = 35;BA.debugLine="ParseData.Views.Initialize";
_parsedata.Views /*anywheresoftware.b4a.objects.collections.Map*/ .Initialize();
 //BA.debugLineNum = 36;BA.debugLine="ParseData.URLs.Initialize";
_parsedata.URLs /*anywheresoftware.b4a.objects.collections.Map*/ .Initialize();
 //BA.debugLineNum = 37;BA.debugLine="If xui.IsB4J Then";
if (_xui.getIsB4J()) { 
 //BA.debugLineNum = 38;BA.debugLine="Padding.Initialize(5dip, 5dip, 20dip, 5dip)";
_padding.Initialize((float) (__c.DipToCurrent((int) (5))),(float) (__c.DipToCurrent((int) (5))),(float) (__c.DipToCurrent((int) (20))),(float) (__c.DipToCurrent((int) (5))));
 }else {
 //BA.debugLineNum = 40;BA.debugLine="Padding.Initialize(5dip, 5dip, 5dip, 5dip)";
_padding.Initialize((float) (__c.DipToCurrent((int) (5))),(float) (__c.DipToCurrent((int) (5))),(float) (__c.DipToCurrent((int) (5))),(float) (__c.DipToCurrent((int) (5))));
 };
 //BA.debugLineNum = 42;BA.debugLine="ParseData.ImageCache.Initialize";
_parsedata.ImageCache /*anywheresoftware.b4a.objects.collections.Map*/ .Initialize();
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return "";
}
public boolean  _lineisvisible(bwsi.registration.townhall.bctextengine._bctextline _line,int _offset,int _height) throws Exception{
 //BA.debugLineNum = 170;BA.debugLine="Private Sub LineIsVisible(line As BCTextLine, offs";
 //BA.debugLineNum = 171;BA.debugLine="Return line.BaselineY + line.MaxHeightBelowBaseLi";
if (true) return _line.BaselineY /*int*/ +_line.MaxHeightBelowBaseLine /*int*/ >=_offset && _line.BaselineY /*int*/ -_line.MaxHeightAboveBaseLine /*int*/ <=_offset+_height;
 //BA.debugLineNum = 172;BA.debugLine="End Sub";
return false;
}
public String  _parseanddraw() throws Exception{
anywheresoftware.b4a.objects.collections.List _pe = null;
 //BA.debugLineNum = 187;BA.debugLine="Public Sub ParseAndDraw";
 //BA.debugLineNum = 188;BA.debugLine="ParseData.NeedToReparseWhenResize = False";
_parsedata.NeedToReparseWhenResize /*boolean*/  = __c.False;
 //BA.debugLineNum = 189;BA.debugLine="ParseData.Text = mText";
_parsedata.Text /*String*/  = _mtext;
 //BA.debugLineNum = 190;BA.debugLine="ParseData.URLs.Clear";
_parsedata.URLs /*anywheresoftware.b4a.objects.collections.Map*/ .Clear();
 //BA.debugLineNum = 191;BA.debugLine="ParseData.Width = (mBase.Width - Padding.Left - P";
_parsedata.Width /*int*/  = (int) ((_mbase.getWidth()-_padding.getLeft()-_padding.getRight()));
 //BA.debugLineNum = 192;BA.debugLine="Dim pe As List = mTextEngine.TagParser.Parse(Pars";
_pe = new anywheresoftware.b4a.objects.collections.List();
_pe = _mtextengine._tagparser /*bwsi.registration.townhall.bbcodeparser*/ ._parse /*anywheresoftware.b4a.objects.collections.List*/ (_parsedata);
 //BA.debugLineNum = 193;BA.debugLine="sv.ScrollViewInnerPanel.RemoveAllViews";
_sv.getScrollViewInnerPanel().RemoveAllViews();
 //BA.debugLineNum = 194;BA.debugLine="If TouchPanel.IsInitialized Then";
if (_touchpanel.IsInitialized()) { 
 //BA.debugLineNum = 195;BA.debugLine="sv.ScrollViewInnerPanel.AddView(TouchPanel, 0, 0";
_sv.getScrollViewInnerPanel().AddView((android.view.View)(_touchpanel.getObject()),(int) (0),(int) (0),(int) (0),(int) (0));
 };
 //BA.debugLineNum = 197;BA.debugLine="sv.ScrollViewInnerPanel.AddView(ForegroundImageVi";
_sv.getScrollViewInnerPanel().AddView((android.view.View)(_foregroundimageview.getObject()),(int) (0),(int) (0),__c.DipToCurrent((int) (2)),__c.DipToCurrent((int) (2)));
 //BA.debugLineNum = 198;BA.debugLine="If ExternalRuns.IsInitialized And ExternalRuns.Si";
if (_externalruns.IsInitialized() && _externalruns.getSize()>0) { 
 //BA.debugLineNum = 199;BA.debugLine="Runs = ExternalRuns";
_runs = _externalruns;
 }else {
 //BA.debugLineNum = 201;BA.debugLine="Runs = mTextEngine.TagParser.CreateRuns(pe, Pars";
_runs = _mtextengine._tagparser /*bwsi.registration.townhall.bbcodeparser*/ ._createruns /*anywheresoftware.b4a.objects.collections.List*/ (_pe,_parsedata);
 };
 //BA.debugLineNum = 203;BA.debugLine="Redraw";
_redraw();
 //BA.debugLineNum = 204;BA.debugLine="End Sub";
return "";
}
public String  _redraw() throws Exception{
 //BA.debugLineNum = 206;BA.debugLine="Public Sub Redraw";
 //BA.debugLineNum = 207;BA.debugLine="Dim Style As BCParagraphStyle = mTextEngine.Creat";
_style = _mtextengine._createstyle /*bwsi.registration.townhall.bctextengine._bcparagraphstyle*/ ();
 //BA.debugLineNum = 208;BA.debugLine="Style.Padding = Padding";
_style.Padding /*anywheresoftware.b4a.objects.B4XCanvas.B4XRect*/  = _padding;
 //BA.debugLineNum = 209;BA.debugLine="Style.MaxWidth = mBase.Width";
_style.MaxWidth /*int*/  = _mbase.getWidth();
 //BA.debugLineNum = 210;BA.debugLine="Style.ResizeHeightAutomatically = True";
_style.ResizeHeightAutomatically /*boolean*/  = __c.True;
 //BA.debugLineNum = 211;BA.debugLine="If LazyLoading Then";
if (_lazyloading) { 
 //BA.debugLineNum = 212;BA.debugLine="CleanExistingImageViews(False, UsedImageViews.Ke";
_cleanexistingimageviews(__c.False,_usedimageviews._getkeys /*anywheresoftware.b4a.objects.collections.List*/ (),(int) (0),(int) (0));
 //BA.debugLineNum = 213;BA.debugLine="UsedImageViews.Clear";
_usedimageviews._clear /*String*/ ();
 //BA.debugLineNum = 214;BA.debugLine="Paragraph = mTextEngine.PrepareForLazyDrawing(Ru";
_paragraph = _mtextengine._prepareforlazydrawing /*bwsi.registration.townhall.bctextengine._bcparagraph*/ (_runs,_style,_sv);
 //BA.debugLineNum = 215;BA.debugLine="ForegroundImageView.SetLayoutAnimated(0, Style.P";
_foregroundimageview.SetLayoutAnimated((int) (0),(int) (_style.Padding /*anywheresoftware.b4a.objects.B4XCanvas.B4XRect*/ .getLeft()),(int) (_style.Padding /*anywheresoftware.b4a.objects.B4XCanvas.B4XRect*/ .getTop()),(int) (_sv.getScrollViewContentWidth()-_style.Padding /*anywheresoftware.b4a.objects.B4XCanvas.B4XRect*/ .getWidth()),(int) (_sv.getScrollViewContentHeight()-_style.Padding /*anywheresoftware.b4a.objects.B4XCanvas.B4XRect*/ .getHeight()));
 //BA.debugLineNum = 216;BA.debugLine="DrawVisibleRegion";
_drawvisibleregion();
 }else {
 //BA.debugLineNum = 218;BA.debugLine="Paragraph = mTextEngine.DrawText(Runs, Style, Fo";
_paragraph = _mtextengine._drawtext /*bwsi.registration.townhall.bctextengine._bcparagraph*/ (_runs,_style,_foregroundimageview,_sv);
 };
 //BA.debugLineNum = 220;BA.debugLine="If TouchPanel.IsInitialized Then";
if (_touchpanel.IsInitialized()) { 
 //BA.debugLineNum = 221;BA.debugLine="TouchPanel.SetLayoutAnimated(0, ForegroundImageV";
_touchpanel.SetLayoutAnimated((int) (0),_foregroundimageview.getLeft(),_foregroundimageview.getTop(),_foregroundimageview.getWidth(),_foregroundimageview.getHeight());
 };
 //BA.debugLineNum = 223;BA.debugLine="End Sub";
return "";
}
public String  _settext(String _t) throws Exception{
 //BA.debugLineNum = 124;BA.debugLine="Public Sub setText(t As String)";
 //BA.debugLineNum = 125;BA.debugLine="mText = t";
_mtext = _t;
 //BA.debugLineNum = 126;BA.debugLine="ParseAndDraw";
_parseanddraw();
 //BA.debugLineNum = 127;BA.debugLine="End Sub";
return "";
}
public String  _settextengine(bwsi.registration.townhall.bctextengine _b) throws Exception{
 //BA.debugLineNum = 110;BA.debugLine="Public Sub setTextEngine (b As BCTextEngine)";
 //BA.debugLineNum = 111;BA.debugLine="mTextEngine = b";
_mtextengine = _b;
 //BA.debugLineNum = 115;BA.debugLine="If mText <> \"\" Then";
if ((_mtext).equals("") == false) { 
 //BA.debugLineNum = 116;BA.debugLine="setText(mText)";
_settext(_mtext);
 };
 //BA.debugLineNum = 118;BA.debugLine="End Sub";
return "";
}
public String  _setviews(anywheresoftware.b4a.objects.collections.Map _m) throws Exception{
 //BA.debugLineNum = 49;BA.debugLine="Public Sub setViews (m As Map)";
 //BA.debugLineNum = 50;BA.debugLine="ParseData.Views = m";
_parsedata.Views /*anywheresoftware.b4a.objects.collections.Map*/  = _m;
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public String  _sv_scrollchanged(int _position) throws Exception{
 //BA.debugLineNum = 244;BA.debugLine="Private Sub sv_ScrollChanged(Position As Int)";
 //BA.debugLineNum = 245;BA.debugLine="If LazyLoading Then DrawVisibleRegion";
if (_lazyloading) { 
_drawvisibleregion();};
 //BA.debugLineNum = 246;BA.debugLine="End Sub";
return "";
}
public String  _touchpanel_touch(int _action,float _x,float _y) throws Exception{
bwsi.registration.townhall.bctextengine._bcsinglestylesection _single = null;
String _url = "";
 //BA.debugLineNum = 225;BA.debugLine="Private Sub TouchPanel_Touch (Action As Int, X As";
 //BA.debugLineNum = 226;BA.debugLine="If Action = TouchPanel.TOUCH_ACTION_UP Then";
if (_action==_touchpanel.TOUCH_ACTION_UP) { 
 //BA.debugLineNum = 227;BA.debugLine="Dim single As BCSingleStyleSection = mTextEngine";
_single = _mtextengine._findsinglestylesection /*bwsi.registration.townhall.bctextengine._bcsinglestylesection*/ (_paragraph,(int) (_x),(int) (_y));
 //BA.debugLineNum = 228;BA.debugLine="If single <> Null Then";
if (_single!= null) { 
 //BA.debugLineNum = 229;BA.debugLine="If ParseData.URLs.ContainsKey(single.Run) Then";
if (_parsedata.URLs /*anywheresoftware.b4a.objects.collections.Map*/ .ContainsKey((Object)(_single.Run /*bwsi.registration.townhall.bctextengine._bctextrun*/ ))) { 
 //BA.debugLineNum = 230;BA.debugLine="Dim url As String = ParseData.Urls.Get(single.";
_url = BA.ObjectToString(_parsedata.URLs /*anywheresoftware.b4a.objects.collections.Map*/ .Get((Object)(_single.Run /*bwsi.registration.townhall.bctextengine._bctextrun*/ )));
 //BA.debugLineNum = 231;BA.debugLine="CallSubDelayed2(mCallBack, mEventName & \"_Link";
__c.CallSubDelayed2(ba,_mcallback,_meventname+"_LinkClicked",(Object)(_url));
 };
 };
 };
 //BA.debugLineNum = 235;BA.debugLine="End Sub";
return "";
}
public String  _updatevisibleregion(int _offsety,int _height) throws Exception{
boolean _foundfirst = false;
anywheresoftware.b4a.objects.collections.List _existing = null;
bwsi.registration.townhall.bctextengine._bctextline _line = null;
anywheresoftware.b4a.objects.B4XViewWrapper _xiv = null;
anywheresoftware.b4a.objects.ImageViewWrapper _iv = null;
 //BA.debugLineNum = 139;BA.debugLine="Public Sub UpdateVisibleRegion (OffsetY As Int, He";
 //BA.debugLineNum = 140;BA.debugLine="Dim foundFirst As Boolean";
_foundfirst = false;
 //BA.debugLineNum = 141;BA.debugLine="Dim Existing As List";
_existing = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 142;BA.debugLine="Existing.Initialize";
_existing.Initialize();
 //BA.debugLineNum = 143;BA.debugLine="Existing.AddAll(UsedImageViews.Keys)";
_existing.AddAll(_usedimageviews._getkeys /*anywheresoftware.b4a.objects.collections.List*/ ());
 //BA.debugLineNum = 144;BA.debugLine="CleanExistingImageViews(True, Existing, OffsetY,";
_cleanexistingimageviews(__c.True,_existing,_offsety,_height);
 //BA.debugLineNum = 145;BA.debugLine="For Each Line As BCTextLine In Paragraph.TextLine";
{
final anywheresoftware.b4a.BA.IterableList group6 = _paragraph.TextLines /*anywheresoftware.b4a.objects.collections.List*/ ;
final int groupLen6 = group6.getSize()
;int index6 = 0;
;
for (; index6 < groupLen6;index6++){
_line = (bwsi.registration.townhall.bctextengine._bctextline)(group6.Get(index6));
 //BA.debugLineNum = 146;BA.debugLine="If LineIsVisible (Line, OffsetY, Height) Then";
if (_lineisvisible(_line,_offsety,_height)) { 
 //BA.debugLineNum = 147;BA.debugLine="foundFirst = True";
_foundfirst = __c.True;
 //BA.debugLineNum = 148;BA.debugLine="If UsedImageViews.ContainsKey(Line) Then";
if (_usedimageviews._containskey /*boolean*/ ((Object)(_line))) { 
 //BA.debugLineNum = 149;BA.debugLine="Continue";
if (true) continue;
 };
 //BA.debugLineNum = 151;BA.debugLine="Dim xiv As B4XView";
_xiv = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 152;BA.debugLine="If ImageViewsCache.Size = 0 Then";
if (_imageviewscache.getSize()==0) { 
 //BA.debugLineNum = 153;BA.debugLine="Dim iv As ImageView";
_iv = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 154;BA.debugLine="iv.Initialize(\"\")";
_iv.Initialize(ba,"");
 //BA.debugLineNum = 155;BA.debugLine="xiv = iv";
_xiv = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_iv.getObject()));
 }else {
 //BA.debugLineNum = 157;BA.debugLine="xiv = ImageViewsCache.Get(ImageViewsCache.Size";
_xiv = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_imageviewscache.Get((int) (_imageviewscache.getSize()-1))));
 //BA.debugLineNum = 158;BA.debugLine="ImageViewsCache.RemoveAt(ImageViewsCache.Size";
_imageviewscache.RemoveAt((int) (_imageviewscache.getSize()-1));
 };
 //BA.debugLineNum = 160;BA.debugLine="sv.ScrollViewInnerPanel.AddView(xiv, 0, 0, 0, 0";
_sv.getScrollViewInnerPanel().AddView((android.view.View)(_xiv.getObject()),(int) (0),(int) (0),(int) (0),(int) (0));
 //BA.debugLineNum = 161;BA.debugLine="xiv.SendToBack";
_xiv.SendToBack();
 //BA.debugLineNum = 162;BA.debugLine="mTextEngine.DrawSingleLine(Line, xiv, Paragraph";
_mtextengine._drawsingleline /*String*/ (_line,_xiv,_paragraph);
 //BA.debugLineNum = 163;BA.debugLine="UsedImageViews.Put(Line, xiv)";
_usedimageviews._put /*String*/ ((Object)(_line),(Object)(_xiv.getObject()));
 }else {
 //BA.debugLineNum = 165;BA.debugLine="If foundFirst Then Exit";
if (_foundfirst) { 
if (true) break;};
 };
 }
};
 //BA.debugLineNum = 168;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
if (BA.fastSubCompare(sub, "SETTEXTENGINE"))
	return _settextengine((bwsi.registration.townhall.bctextengine) args[0]);
return BA.SubDelegator.SubNotFound;
}
}
