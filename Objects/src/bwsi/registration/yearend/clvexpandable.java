package bwsi.registration.yearend;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class clvexpandable extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "bwsi.registration.yearend.clvexpandable");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", bwsi.registration.yearend.clvexpandable.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public b4a.example3.customlistview _mclv = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public bwsi.registration.yearend.main _main = null;
public bwsi.registration.yearend.mainscreen _mainscreen = null;
public bwsi.registration.yearend.scale _scale = null;
public bwsi.registration.yearend.globalvar _globalvar = null;
public bwsi.registration.yearend.dbfunctions _dbfunctions = null;
public bwsi.registration.yearend.actregistration _actregistration = null;
public bwsi.registration.yearend.dbutils _dbutils = null;
public bwsi.registration.yearend.starter _starter = null;
public bwsi.registration.yearend.addguest _addguest = null;
public bwsi.registration.yearend.httputils2service _httputils2service = null;
public bwsi.registration.yearend.b4xcollections _b4xcollections = null;
public static class _expandableitemdata{
public boolean IsInitialized;
public int CollapsedHeight;
public int ExpandedHeight;
public Object Value;
public boolean Expanded;
public void Initialize() {
IsInitialized = true;
CollapsedHeight = 0;
ExpandedHeight = 0;
Value = new Object();
Expanded = false;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public String  _animatedarrow(int _from,int _todegree,anywheresoftware.b4a.objects.B4XViewWrapper _pnl) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _title = null;
anywheresoftware.b4a.objects.B4XViewWrapper _iv = null;
 //BA.debugLineNum = 71;BA.debugLine="Private Sub AnimatedArrow(From As Int, ToDegree As";
 //BA.debugLineNum = 72;BA.debugLine="Dim title As B4XView = Pnl.GetView(0) 'pnlTitle i";
_title = new anywheresoftware.b4a.objects.B4XViewWrapper();
_title = _pnl.GetView((int) (0));
 //BA.debugLineNum = 73;BA.debugLine="Dim iv As B4XView = title.GetView(1) 'ImageView1";
_iv = new anywheresoftware.b4a.objects.B4XViewWrapper();
_iv = _title.GetView((int) (1));
 //BA.debugLineNum = 74;BA.debugLine="iv.SetRotationAnimated(0, From)";
_iv.SetRotationAnimated((int) (0),(float) (_from));
 //BA.debugLineNum = 75;BA.debugLine="iv.SetRotationAnimated(mCLV.AnimationDuration, To";
_iv.SetRotationAnimated(_mclv._animationduration,(float) (_todegree));
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 3;BA.debugLine="Type ExpandableItemData (CollapsedHeight As Int,";
;
 //BA.debugLineNum = 4;BA.debugLine="Private mCLV As CustomListView";
_mclv = new b4a.example3.customlistview();
 //BA.debugLineNum = 5;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 6;BA.debugLine="End Sub";
return "";
}
public String  _collapseitem(int _index) throws Exception{
 //BA.debugLineNum = 34;BA.debugLine="Sub CollapseItem(index As Int)";
 //BA.debugLineNum = 35;BA.debugLine="ResizeItem(index, True)";
_resizeitem(_index,__c.True);
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public bwsi.registration.yearend.clvexpandable._expandableitemdata  _createvalue(anywheresoftware.b4a.objects.B4XViewWrapper _pnl,Object _value) throws Exception{
bwsi.registration.yearend.clvexpandable._expandableitemdata _e = null;
 //BA.debugLineNum = 12;BA.debugLine="Public Sub CreateValue(pnl As B4XView, Value As Ob";
 //BA.debugLineNum = 13;BA.debugLine="Dim e As ExpandableItemData";
_e = new bwsi.registration.yearend.clvexpandable._expandableitemdata();
 //BA.debugLineNum = 14;BA.debugLine="e.Initialize";
_e.Initialize();
 //BA.debugLineNum = 15;BA.debugLine="e.CollapsedHeight = pnl.GetView(0).Height";
_e.CollapsedHeight /*int*/  = _pnl.GetView((int) (0)).getHeight();
 //BA.debugLineNum = 16;BA.debugLine="e.ExpandedHeight = pnl.GetView(0).Height + pnl.Ge";
_e.ExpandedHeight /*int*/  = (int) (_pnl.GetView((int) (0)).getHeight()+_pnl.GetView((int) (1)).getHeight());
 //BA.debugLineNum = 17;BA.debugLine="e.Value = Value";
_e.Value /*Object*/  = _value;
 //BA.debugLineNum = 18;BA.debugLine="Return e";
if (true) return _e;
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return null;
}
public String  _expanditem(int _index) throws Exception{
 //BA.debugLineNum = 29;BA.debugLine="Public Sub ExpandItem (index As Int)";
 //BA.debugLineNum = 30;BA.debugLine="ResizeItem(index, False)";
_resizeitem(_index,__c.False);
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public Object  _getvalue(int _index) throws Exception{
bwsi.registration.yearend.clvexpandable._expandableitemdata _e = null;
 //BA.debugLineNum = 21;BA.debugLine="Public Sub GetValue (Index As Int) As Object";
 //BA.debugLineNum = 22;BA.debugLine="If mCLV.GetValue(Index) Is ExpandableItemData The";
if (_mclv._getvalue(_index) instanceof bwsi.registration.yearend.clvexpandable._expandableitemdata) { 
 //BA.debugLineNum = 23;BA.debugLine="Dim e As ExpandableItemData = mCLV.GetValue(Inde";
_e = (bwsi.registration.yearend.clvexpandable._expandableitemdata)(_mclv._getvalue(_index));
 //BA.debugLineNum = 24;BA.debugLine="Return e.Value";
if (true) return _e.Value /*Object*/ ;
 };
 //BA.debugLineNum = 26;BA.debugLine="Return mCLV.GetValue(Index)";
if (true) return _mclv._getvalue(_index);
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return null;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,b4a.example3.customlistview _clv) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 8;BA.debugLine="Public Sub Initialize (CLV As CustomListView)";
 //BA.debugLineNum = 9;BA.debugLine="mCLV = CLV";
_mclv = _clv;
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public String  _moveitembetweenpanels(anywheresoftware.b4a.objects.B4XViewWrapper _src,anywheresoftware.b4a.objects.B4XViewWrapper _target) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _v = null;
 //BA.debugLineNum = 63;BA.debugLine="Private Sub MoveItemBetweenPanels (Src As B4XView,";
 //BA.debugLineNum = 64;BA.debugLine="Do While Src.NumberOfViews > 0";
while (_src.getNumberOfViews()>0) {
 //BA.debugLineNum = 65;BA.debugLine="Dim v As B4XView = Src.GetView(0)";
_v = new anywheresoftware.b4a.objects.B4XViewWrapper();
_v = _src.GetView((int) (0));
 //BA.debugLineNum = 66;BA.debugLine="v.RemoveViewFromParent";
_v.RemoveViewFromParent();
 //BA.debugLineNum = 67;BA.debugLine="Target.AddView(v, v.Left, v.Top, v.Width, v.Heig";
_target.AddView((android.view.View)(_v.getObject()),_v.getLeft(),_v.getTop(),_v.getWidth(),_v.getHeight());
 }
;
 //BA.debugLineNum = 69;BA.debugLine="End Sub";
return "";
}
public void  _resizeitem(int _index,boolean _collapse) throws Exception{
ResumableSub_ResizeItem rsub = new ResumableSub_ResizeItem(this,_index,_collapse);
rsub.resume(ba, null);
}
public static class ResumableSub_ResizeItem extends BA.ResumableSub {
public ResumableSub_ResizeItem(bwsi.registration.yearend.clvexpandable parent,int _index,boolean _collapse) {
this.parent = parent;
this._index = _index;
this._collapse = _collapse;
}
bwsi.registration.yearend.clvexpandable parent;
int _index;
boolean _collapse;
b4a.example3.customlistview._clvitem _item = null;
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
anywheresoftware.b4a.objects.B4XViewWrapper _newpanel = null;
bwsi.registration.yearend.clvexpandable._expandableitemdata _id = null;
int _newsize = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 39;BA.debugLine="Dim item As CLVItem = mCLV.GetRawListItem(Index)";
_item = parent._mclv._getrawlistitem(_index);
 //BA.debugLineNum = 40;BA.debugLine="Dim p As B4XView = item.Panel.GetView(0)";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = _item.Panel.GetView((int) (0));
 //BA.debugLineNum = 41;BA.debugLine="If p.NumberOfViews = 0 Or (item.Value Is Expandab";
if (true) break;

case 1:
//if
this.state = 6;
if (_p.getNumberOfViews()==0 || (_item.Value instanceof bwsi.registration.yearend.clvexpandable._expandableitemdata)==parent.__c.False) { 
this.state = 3;
;}if (true) break;

case 3:
//C
this.state = 6;
if (true) return ;
if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 42;BA.debugLine="Dim NewPanel As B4XView = xui.CreatePanel(\"\")";
_newpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
_newpanel = parent._xui.CreatePanel(ba,"");
 //BA.debugLineNum = 43;BA.debugLine="MoveItemBetweenPanels(p, NewPanel)";
parent._moveitembetweenpanels(_p,_newpanel);
 //BA.debugLineNum = 44;BA.debugLine="Dim id As ExpandableItemData = item.Value";
_id = (bwsi.registration.yearend.clvexpandable._expandableitemdata)(_item.Value);
 //BA.debugLineNum = 45;BA.debugLine="id.Expanded = Not(Collapse)";
_id.Expanded /*boolean*/  = parent.__c.Not(_collapse);
 //BA.debugLineNum = 46;BA.debugLine="mCLV.sv.ScrollViewInnerPanel.AddView(NewPanel, 0,";
parent._mclv._sv.getScrollViewInnerPanel().AddView((android.view.View)(_newpanel.getObject()),(int) (0),_item.Offset,_p.getWidth(),_id.ExpandedHeight /*int*/ );
 //BA.debugLineNum = 47;BA.debugLine="If Collapse Then";
if (true) break;

case 7:
//if
this.state = 12;
if (_collapse) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 48;BA.debugLine="AnimatedArrow(180, 0, NewPanel)";
parent._animatedarrow((int) (180),(int) (0),_newpanel);
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 50;BA.debugLine="AnimatedArrow(0, 180, NewPanel)";
parent._animatedarrow((int) (0),(int) (180),_newpanel);
 if (true) break;

case 12:
//C
this.state = 13;
;
 //BA.debugLineNum = 52;BA.debugLine="Dim NewSize As Int";
_newsize = 0;
 //BA.debugLineNum = 53;BA.debugLine="If Collapse Then NewSize = id.CollapsedHeight Els";
if (true) break;

case 13:
//if
this.state = 20;
if (_collapse) { 
this.state = 15;
;}
else {
this.state = 17;
;}if (true) break;

case 15:
//C
this.state = 20;
_newsize = _id.CollapsedHeight /*int*/ ;
if (true) break;

case 17:
//C
this.state = 20;
_newsize = _id.ExpandedHeight /*int*/ ;
if (true) break;

case 20:
//C
this.state = 21;
;
 //BA.debugLineNum = 54;BA.debugLine="mCLV.ResizeItem(Index, NewSize)";
parent._mclv._resizeitem(_index,_newsize);
 //BA.debugLineNum = 55;BA.debugLine="NewPanel.SendToBack";
_newpanel.SendToBack();
 //BA.debugLineNum = 56;BA.debugLine="Sleep(mCLV.AnimationDuration)";
parent.__c.Sleep(ba,this,parent._mclv._animationduration);
this.state = 25;
return;
case 25:
//C
this.state = 21;
;
 //BA.debugLineNum = 57;BA.debugLine="If p.Parent.IsInitialized Then";
if (true) break;

case 21:
//if
this.state = 24;
if (_p.getParent().IsInitialized()) { 
this.state = 23;
}if (true) break;

case 23:
//C
this.state = 24;
 //BA.debugLineNum = 58;BA.debugLine="MoveItemBetweenPanels(NewPanel, p)";
parent._moveitembetweenpanels(_newpanel,_p);
 if (true) break;

case 24:
//C
this.state = -1;
;
 //BA.debugLineNum = 60;BA.debugLine="NewPanel.RemoveViewFromParent";
_newpanel.RemoveViewFromParent();
 //BA.debugLineNum = 61;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public String  _toggleitem(int _index) throws Exception{
bwsi.registration.yearend.clvexpandable._expandableitemdata _i = null;
 //BA.debugLineNum = 78;BA.debugLine="Public Sub ToggleItem (Index As Int)";
 //BA.debugLineNum = 79;BA.debugLine="Dim i As ExpandableItemData = mCLV.GetValue(Index";
_i = (bwsi.registration.yearend.clvexpandable._expandableitemdata)(_mclv._getvalue(_index));
 //BA.debugLineNum = 80;BA.debugLine="If i.Expanded = True Then";
if (_i.Expanded /*boolean*/ ==__c.True) { 
 //BA.debugLineNum = 81;BA.debugLine="CollapseItem(Index)";
_collapseitem(_index);
 }else {
 //BA.debugLineNum = 83;BA.debugLine="ExpandItem(Index)";
_expanditem(_index);
 };
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
