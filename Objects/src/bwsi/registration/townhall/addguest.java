package bwsi.registration.townhall;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class addguest extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static addguest mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "bwsi.registration.townhall", "bwsi.registration.townhall.addguest");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (addguest).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "bwsi.registration.townhall", "bwsi.registration.townhall.addguest");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "bwsi.registration.townhall.addguest", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (addguest) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (addguest) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return addguest.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        if (!dontPause)
            BA.LogInfo("** Activity (addguest) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (addguest) Pause event (activity is not paused). **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        if (!dontPause) {
            processBA.setActivityPaused(true);
            mostCurrent = null;
        }

        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            addguest mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (addguest) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public static anywheresoftware.b4a.objects.Serial.BluetoothAdmin _btadmin = null;
public static anywheresoftware.b4a.objects.collections.Map _paireddevices = null;
public static anywheresoftware.b4a.objects.collections.List _founddevices = null;
public static String _devicename = "";
public static String _devicemac = "";
public static anywheresoftware.b4a.objects.Serial _serial1 = null;
public static anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _tmprinter = null;
public static String _printbuffer = "";
public static byte[] _printlogo = null;
public static anywheresoftware.b4a.randomaccessfile.AsyncStreams _ostream = null;
public static int _res = 0;
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _logobmp = null;
public static anywheresoftware.b4j.object.JavaObject _woosimcmd = null;
public static anywheresoftware.b4j.object.JavaObject _woosimimage = null;
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _logo = null;
public static bwsi.registration.townhall.slinptypeconst _inptyp = null;
public de.amberhome.objects.appcompat.ACActionBar _actionbarbutton = null;
public de.amberhome.objects.appcompat.ACToolbarDarkWrapper _toolbar = null;
public anywheresoftware.b4a.object.XmlLayoutBuilder _xmlicon = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdtxtbox = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btncancel = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnsave = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtapprovedby = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtcareof = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtguestname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtremarks = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdcancel = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdsave = null;
public static String _registrationnum = "";
public static int _newguestid = 0;
public bwsi.registration.townhall.main _main = null;
public bwsi.registration.townhall.actregistration _actregistration = null;
public bwsi.registration.townhall.registration _registration = null;
public bwsi.registration.townhall.mainscreen _mainscreen = null;
public bwsi.registration.townhall.scale _scale = null;
public bwsi.registration.townhall.addemployee _addemployee = null;
public bwsi.registration.townhall.globalvar _globalvar = null;
public bwsi.registration.townhall.dbfunctions _dbfunctions = null;
public bwsi.registration.townhall.dbutils _dbutils = null;
public bwsi.registration.townhall.starter _starter = null;
public bwsi.registration.townhall.httputils2service _httputils2service = null;
public bwsi.registration.townhall.b4xcollections _b4xcollections = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4j.object.JavaObject _jo = null;
anywheresoftware.b4a.object.XmlLayoutBuilder _xl = null;
 //BA.debugLineNum = 77;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 79;BA.debugLine="Scale.SetRate(0.5)";
mostCurrent._scale._setrate /*String*/ (mostCurrent.activityBA,0.5);
 //BA.debugLineNum = 80;BA.debugLine="Activity.LoadLayout(\"GuestLayout\")";
mostCurrent._activity.LoadLayout("GuestLayout",mostCurrent.activityBA);
 //BA.debugLineNum = 82;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(17).Bold.Append";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (17)).Bold().Append(BA.ObjectToCharSequence(("ADD GUEST"))).PopAll();
 //BA.debugLineNum = 83;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(14).Append($";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (14)).Append(BA.ObjectToCharSequence(("Add Party Guest"))).PopAll();
 //BA.debugLineNum = 85;BA.debugLine="ToolBar.InitMenuListener";
mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 86;BA.debugLine="ToolBar.Title = GlobalVar.CSTitle";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 87;BA.debugLine="ToolBar.SubTitle = GlobalVar.CSSubTitle";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 89;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 90;BA.debugLine="Dim xl As XmlLayoutBuilder";
_xl = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 91;BA.debugLine="jo = ToolBar";
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._toolbar.getObject()));
 //BA.debugLineNum = 92;BA.debugLine="jo.RunMethod(\"setPopupTheme\", Array(xl.GetResourc";
_jo.RunMethod("setPopupTheme",new Object[]{(Object)(_xl.GetResourceId("style","ToolbarMenu"))});
 //BA.debugLineNum = 93;BA.debugLine="jo.RunMethod(\"setContentInsetStartWithNavigation\"";
_jo.RunMethod("setContentInsetStartWithNavigation",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))});
 //BA.debugLineNum = 94;BA.debugLine="jo.RunMethod(\"setTitleMarginStart\", Array(0dip))";
_jo.RunMethod("setTitleMarginStart",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)))});
 //BA.debugLineNum = 96;BA.debugLine="InpTyp.SetInputType(txtGuestName,Array As Int(Inp";
_inptyp._setinputtype /*String*/ (mostCurrent._txtguestname,new int[]{_inptyp._type_class_text /*int*/ (),_inptyp._type_text_flag_auto_correct /*int*/ (),_inptyp._type_text_flag_cap_words /*int*/ ()});
 //BA.debugLineNum = 97;BA.debugLine="InpTyp.SetInputType(txtCareOf,Array As Int(InpTyp";
_inptyp._setinputtype /*String*/ (mostCurrent._txtcareof,new int[]{_inptyp._type_class_text /*int*/ (),_inptyp._type_text_flag_auto_correct /*int*/ (),_inptyp._type_text_flag_cap_words /*int*/ ()});
 //BA.debugLineNum = 98;BA.debugLine="InpTyp.SetInputType(txtRemarks,Array As Int(InpTy";
_inptyp._setinputtype /*String*/ (mostCurrent._txtremarks,new int[]{_inptyp._type_class_text /*int*/ (),_inptyp._type_text_flag_auto_correct /*int*/ (),_inptyp._type_text_flag_cap_words /*int*/ ()});
 //BA.debugLineNum = 99;BA.debugLine="InpTyp.SetInputType(txtApprovedBy,Array As Int(In";
_inptyp._setinputtype /*String*/ (mostCurrent._txtapprovedby,new int[]{_inptyp._type_class_text /*int*/ (),_inptyp._type_text_flag_auto_correct /*int*/ (),_inptyp._type_text_flag_cap_words /*int*/ ()});
 //BA.debugLineNum = 101;BA.debugLine="ActionBarButton.Initialize";
mostCurrent._actionbarbutton.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 102;BA.debugLine="ActionBarButton.ShowUpIndicator = True";
mostCurrent._actionbarbutton.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 103;BA.debugLine="BTAdmin.Initialize(\"Admin\")";
_btadmin.Initialize(processBA,"Admin");
 //BA.debugLineNum = 104;BA.debugLine="Serial1.Initialize(\"Printer\")";
_serial1.Initialize("Printer");
 //BA.debugLineNum = 106;BA.debugLine="CDTxtBox.Initialize2(Colors.Transparent, Colors.T";
mostCurrent._cdtxtbox.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.Transparent,anywheresoftware.b4a.keywords.Common.Colors.Transparent,(int) (0),(int) (0));
 //BA.debugLineNum = 107;BA.debugLine="cdSave.Initialize2(0xFF1E4369, 20, 0,0xFF268FC2)";
mostCurrent._cdsave.Initialize2((int) (0xff1e4369),(int) (20),(int) (0),(int) (0xff268fc2));
 //BA.debugLineNum = 108;BA.debugLine="btnSave.Background = cdSave";
mostCurrent._btnsave.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdsave.getObject()));
 //BA.debugLineNum = 109;BA.debugLine="cdCancel.Initialize2(0xFFDC143C, 20, 0,0xFFDC3545";
mostCurrent._cdcancel.Initialize2((int) (0xffdc143c),(int) (20),(int) (0),(int) (0xffdc3545));
 //BA.debugLineNum = 110;BA.debugLine="btnCancel.Background = cdCancel";
mostCurrent._btncancel.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdcancel.getObject()));
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
return "";
}
public static String  _activity_createmenu(de.amberhome.objects.appcompat.ACMenuWrapper _menu) throws Exception{
 //BA.debugLineNum = 18;BA.debugLine="Sub Activity_CreateMenu(Menu As ACMenu)";
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 116;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 117;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 118;BA.debugLine="ToolBar_NavigationItemClick";
_toolbar_navigationitemclick();
 //BA.debugLineNum = 119;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 121;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 128;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 125;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 126;BA.debugLine="End Sub";
return "";
}
public static String  _addpeople_oncancelclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 330;BA.debugLine="Private Sub AddPeople_OnCancelClicked (View As Vie";
 //BA.debugLineNum = 331;BA.debugLine="If View<>Null Then";
if (_view!= null) { 
 //BA.debugLineNum = 332;BA.debugLine="ToastMessageShow(\"Cancelled!\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Cancelled!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 333;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 334;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 };
 //BA.debugLineNum = 336;BA.debugLine="End Sub";
return "";
}
public static String  _addpeople_onitemclick(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,String _selection,int _position,long _id) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 319;BA.debugLine="Private Sub AddPeople_OnItemClick (View As View, S";
 //BA.debugLineNum = 320;BA.debugLine="ToastMessageShow(Selection&\" Selected! (Position";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_selection+" Selected! (Position : "+BA.NumberToString(_position)+")"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 321;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 322;BA.debugLine="Alert.Initialize.Dismiss2";
_alert.Initialize().Dismiss2();
 //BA.debugLineNum = 324;BA.debugLine="Select Case Position";
switch (_position) {
case 0: {
 break; }
case 1: {
 break; }
}
;
 //BA.debugLineNum = 328;BA.debugLine="End Sub";
return "";
}
public static String  _btncancel_click() throws Exception{
 //BA.debugLineNum = 350;BA.debugLine="Sub btnCancel_Click";
 //BA.debugLineNum = 351;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 352;BA.debugLine="End Sub";
return "";
}
public static String  _btnsave_click() throws Exception{
 //BA.debugLineNum = 340;BA.debugLine="Sub btnSave_Click";
 //BA.debugLineNum = 341;BA.debugLine="If Not(ValidEntries) Then Return";
if (anywheresoftware.b4a.keywords.Common.Not(_validentries())) { 
if (true) return "";};
 //BA.debugLineNum = 342;BA.debugLine="If Not(SaveGuestData) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_saveguestdata())) { 
 //BA.debugLineNum = 343;BA.debugLine="ShowEntryError($\"ERROR\"$, $\"Unable to Save Guest";
_showentryerror(("ERROR"),("Unable to Save Guest data due to ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 };
 //BA.debugLineNum = 346;BA.debugLine="ProgressDialogShow2($\"Saving Guest Data...\"$, Fal";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Saving Guest Data...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 347;BA.debugLine="PrintStub(RegistrationNum)";
_printstub(mostCurrent._registrationnum);
 //BA.debugLineNum = 348;BA.debugLine="End Sub";
return "";
}
public static String  _confirmregister(String _sempname) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 143;BA.debugLine="Private Sub ConfirmRegister (sEmpName As String)";
 //BA.debugLineNum = 144;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 146;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(("CONFIRM REGISTRATION")).SetMessage(("Do you want to Register")+anywheresoftware.b4a.keywords.Common.CRLF+_sempname+("?")).SetPositiveText("YES").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetNegativeText("NO").SetNegativeColor((int) (mostCurrent._globalvar._negcolor /*double*/ )).SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"RegisterEmp").SetOnNegativeClicked(mostCurrent.activityBA,"RegisterEmp").SetOnViewBinder(mostCurrent.activityBA,"RegFontSizeBinder");
 //BA.debugLineNum = 164;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 165;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 167;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _createscaledbitmap(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _original,int _newwidth,int _newheight) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _b = null;
 //BA.debugLineNum = 631;BA.debugLine="Sub CreateScaledBitmap(Original As Bitmap, NewWidt";
 //BA.debugLineNum = 632;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 633;BA.debugLine="Dim b As Bitmap";
_b = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 634;BA.debugLine="b = r.RunStaticMethod(\"android.graphics.Bitmap\",";
_b = (anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(_r.RunStaticMethod("android.graphics.Bitmap","createScaledBitmap",new Object[]{(Object)(_original.getObject()),(Object)(_newwidth),(Object)(_newheight),(Object)(anywheresoftware.b4a.keywords.Common.True)},new String[]{"android.graphics.Bitmap","java.lang.int","java.lang.int","java.lang.boolean"})));
 //BA.debugLineNum = 635;BA.debugLine="Return b";
if (true) return _b;
 //BA.debugLineNum = 636;BA.debugLine="End Sub";
return null;
}
public static String  _entryerror_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 406;BA.debugLine="Private Sub EntryError_OnPositiveClicked (View As";
 //BA.debugLineNum = 407;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 408;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 409;BA.debugLine="End Sub";
return "";
}
public static String  _entryerrorbinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 411;BA.debugLine="Private Sub EntryErrorBinder_OnBindView (View As V";
 //BA.debugLineNum = 412;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 413;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 414;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 415;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 416;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 417;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf071)))+"  "));
 //BA.debugLineNum = 418;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 420;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 423;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 424;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 425;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 426;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 428;BA.debugLine="End Sub";
return "";
}
public static int  _getguestid(String _sregno) throws Exception{
int _iretval = 0;
 //BA.debugLineNum = 469;BA.debugLine="Private Sub GetGuestID(sRegNo As String) As Int";
 //BA.debugLineNum = 470;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 471;BA.debugLine="Try";
try { //BA.debugLineNum = 472;BA.debugLine="iRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_iretval = (int)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT GuestID FROM tblGuests WHERE StubNo = "+_sregno)));
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 474;BA.debugLine="ToastMessageShow($\"Unable to fetch Guest ID due";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Guest ID due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 475;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("814548998",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 477;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 479;BA.debugLine="End Sub";
return 0;
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 56;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 59;BA.debugLine="Dim ActionBarButton As ACActionBar";
mostCurrent._actionbarbutton = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 60;BA.debugLine="Private ToolBar As ACToolBarDark";
mostCurrent._toolbar = new de.amberhome.objects.appcompat.ACToolbarDarkWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private xmlIcon As XmlLayoutBuilder";
mostCurrent._xmlicon = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 63;BA.debugLine="Private CDTxtBox As ColorDrawable";
mostCurrent._cdtxtbox = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 64;BA.debugLine="Private btnCancel As ACButton";
mostCurrent._btncancel = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Private btnSave As ACButton";
mostCurrent._btnsave = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Private txtApprovedBy As EditText";
mostCurrent._txtapprovedby = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Private txtCareOf As EditText";
mostCurrent._txtcareof = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private txtGuestName As EditText";
mostCurrent._txtguestname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Private txtRemarks As EditText";
mostCurrent._txtremarks = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Private cdCancel, cdSave As ColorDrawable";
mostCurrent._cdcancel = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
mostCurrent._cdsave = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 72;BA.debugLine="Private RegistrationNum As String";
mostCurrent._registrationnum = "";
 //BA.debugLineNum = 73;BA.debugLine="Private NewGuestID As Int";
_newguestid = 0;
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return "";
}
public static void  _printer_connected(boolean _success) throws Exception{
ResumableSub_Printer_Connected rsub = new ResumableSub_Printer_Connected(null,_success);
rsub.resume(processBA, null);
}
public static class ResumableSub_Printer_Connected extends BA.ResumableSub {
public ResumableSub_Printer_Connected(bwsi.registration.townhall.addguest parent,boolean _success) {
this.parent = parent;
this._success = _success;
}
bwsi.registration.townhall.addguest parent;
boolean _success;
byte[] _initprinter = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 588;BA.debugLine="Log(\"Connected: \" & Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("814745601","Connected: "+BA.ObjectToString(_success),0);
 //BA.debugLineNum = 590;BA.debugLine="If Success = False Then";
if (true) break;

case 1:
//if
this.state = 6;
if (_success==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 591;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 595;BA.debugLine="StartPrinter";
_startprinter();
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 598;BA.debugLine="Dim initPrinter() As Byte";
_initprinter = new byte[(int) (0)];
;
 //BA.debugLineNum = 600;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 601;BA.debugLine="TMPrinter.Initialize2(Serial1.OutputStream, \"win";
parent._tmprinter.Initialize2(parent._serial1.getOutputStream(),"windows-1252");
 //BA.debugLineNum = 602;BA.debugLine="oStream.Initialize(Serial1.InputStream, Serial1.";
parent._ostream.Initialize(processBA,parent._serial1.getInputStream(),parent._serial1.getOutputStream(),"LogoPrint");
 //BA.debugLineNum = 603;BA.debugLine="Logo.Initialize(File.DirAssets, \"Stub-Header.png";
parent._logo.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Stub-Header.png");
 //BA.debugLineNum = 604;BA.debugLine="LogoBMP = CreateScaledBitmap(Logo, Logo.Width, L";
parent._logobmp = _createscaledbitmap(parent._logo,parent._logo.getWidth(),parent._logo.getHeight());
 //BA.debugLineNum = 605;BA.debugLine="Log(DeviceName)";
anywheresoftware.b4a.keywords.Common.LogImpl("814745618",parent._devicename,0);
 //BA.debugLineNum = 607;BA.debugLine="WoosimCMD.InitializeStatic(\"com.woosim.printer.W";
parent._woosimcmd.InitializeStatic("com.woosim.printer.WoosimCmd");
 //BA.debugLineNum = 608;BA.debugLine="WoosimImage.InitializeStatic(\"com.woosim.printer";
parent._woosimimage.InitializeStatic("com.woosim.printer.WoosimImage");
 //BA.debugLineNum = 610;BA.debugLine="initPrinter = WoosimCMD.RunMethod(\"initPrinter\",";
_initprinter = (byte[])(parent._woosimcmd.RunMethod("initPrinter",(Object[])(anywheresoftware.b4a.keywords.Common.Null)));
 //BA.debugLineNum = 611;BA.debugLine="PrintLogo = WoosimImage.RunMethod(\"printBitmap\",";
parent._printlogo = (byte[])(parent._woosimimage.RunMethod("printBitmap",new Object[]{(Object)(0),(Object)(0),(Object)(420),(Object)(205),(Object)(parent._logobmp.getObject())}));
 //BA.debugLineNum = 613;BA.debugLine="oStream.Write(initPrinter)";
parent._ostream.Write(_initprinter);
 //BA.debugLineNum = 614;BA.debugLine="oStream.Write(WoosimCMD.RunMethod(\"setPageMode\",";
parent._ostream.Write((byte[])(parent._woosimcmd.RunMethod("setPageMode",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 615;BA.debugLine="oStream.Write(PrintLogo)";
parent._ostream.Write(parent._printlogo);
 //BA.debugLineNum = 616;BA.debugLine="oStream.Write(WoosimCMD.RunMethod(\"PM_setStdMode";
parent._ostream.Write((byte[])(parent._woosimcmd.RunMethod("PM_setStdMode",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 617;BA.debugLine="oStream.Write(PrintLogo)";
parent._ostream.Write(parent._printlogo);
 //BA.debugLineNum = 619;BA.debugLine="Sleep(500)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (500));
this.state = 7;
return;
case 7:
//C
this.state = 6;
;
 //BA.debugLineNum = 620;BA.debugLine="TMPrinter.WriteLine(PrintBuffer)";
parent._tmprinter.WriteLine(parent._printbuffer);
 //BA.debugLineNum = 621;BA.debugLine="Log(PrintBuffer)";
anywheresoftware.b4a.keywords.Common.LogImpl("814745634",parent._printbuffer,0);
 //BA.debugLineNum = 622;BA.debugLine="TMPrinter.Flush";
parent._tmprinter.Flush();
 //BA.debugLineNum = 623;BA.debugLine="Sleep(600)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (600));
this.state = 8;
return;
case 8:
//C
this.state = 6;
;
 //BA.debugLineNum = 624;BA.debugLine="ShowSuccessMsg($\"SUCCESS\"$, $\"Stub was successf";
_showsuccessmsg(("SUCCESS"),("Stub was successfully printed.")+anywheresoftware.b4a.keywords.Common.CRLF+("Tap OK to Continue..."));
 //BA.debugLineNum = 626;BA.debugLine="TMPrinter.Close";
parent._tmprinter.Close();
 //BA.debugLineNum = 627;BA.debugLine="Serial1.Disconnect";
parent._serial1.Disconnect();
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 629;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _printerbinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 226;BA.debugLine="Private Sub PrinterBinder_OnBindView (View As View";
 //BA.debugLineNum = 227;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 228;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 229;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 230;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 231;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 232;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf071)))+"  "));
 //BA.debugLineNum = 233;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 235;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 238;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 239;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 240;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 241;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 243;BA.debugLine="End Sub";
return "";
}
public static String  _printererror_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 221;BA.debugLine="Private Sub PrinterError_OnPositiveClicked (View A";
 //BA.debugLineNum = 222;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 223;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 224;BA.debugLine="End Sub";
return "";
}
public static String  _printstub(String _sregno) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsdata = null;
String _stubno = "";
String _regguestname = "";
String _regremarks = "";
 //BA.debugLineNum = 482;BA.debugLine="Private Sub PrintStub(sRegNo As String)";
 //BA.debugLineNum = 483;BA.debugLine="Dim rsData As Cursor";
_rsdata = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 484;BA.debugLine="Dim StubNo As String";
_stubno = "";
 //BA.debugLineNum = 485;BA.debugLine="Dim RegGuestName As String";
_regguestname = "";
 //BA.debugLineNum = 486;BA.debugLine="Dim RegRemarks As String";
_regremarks = "";
 //BA.debugLineNum = 488;BA.debugLine="ProgressDialogShow2($\"Stub Printing.  Please Wait";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Stub Printing.  Please Wait...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 490;BA.debugLine="Try";
try { //BA.debugLineNum = 491;BA.debugLine="Starter.strCriteria = \"SELECT tblGuests.GuestID,";
mostCurrent._starter._strcriteria /*String*/  = "SELECT tblGuests.GuestID,tblGuests.StubNo, "+"tblGuests.GuestName, tblGuests.Remarks_Occupation, "+"tblGuests.CareOf, tblGuests.ApprovedBy, tblGuests.TimeReg, "+"tblGuests.WasStubPrinted, tblGuests.NoOfPrint "+"FROM tblGuests "+"WHERE tblGuests.StubNo = '"+_sregno+"'";
 //BA.debugLineNum = 498;BA.debugLine="rsData = Starter.DBCon.ExecQuery(Starter.strCrit";
_rsdata = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 499;BA.debugLine="LogColor(Starter.strCriteria, Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("814614545",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 501;BA.debugLine="If rsData.RowCount > 0 Then";
if (_rsdata.getRowCount()>0) { 
 //BA.debugLineNum = 502;BA.debugLine="rsData.Position = 0";
_rsdata.setPosition((int) (0));
 //BA.debugLineNum = 503;BA.debugLine="StubNo = rsData.GetString(\"StubNo\")";
_stubno = _rsdata.GetString("StubNo");
 //BA.debugLineNum = 504;BA.debugLine="RegGuestName = rsData.GetString(\"GuestName\")";
_regguestname = _rsdata.GetString("GuestName");
 //BA.debugLineNum = 505;BA.debugLine="RegRemarks = rsData.GetString(\"Remarks_Occupati";
_regremarks = _rsdata.GetString("Remarks_Occupation");
 }else {
 //BA.debugLineNum = 507;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 510;BA.debugLine="PrintBuffer = Chr(27) & \"@\" _ 						& Chr(27) &";
_printbuffer = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"@"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+("LAUSGROUP EVENT CENTRE")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+("December 19, 2023")+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("STUB NO.: ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regguestname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regremarks+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+("Have a Blessed Holiday Season!")+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("DINNER STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regguestname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 //BA.debugLineNum = 528;BA.debugLine="StartPrinter";
_startprinter();
 } 
       catch (Exception e21) {
			processBA.setLastException(e21); //BA.debugLineNum = 530;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 531;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("814614577",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 533;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 25;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 29;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 31;BA.debugLine="Private BTAdmin As BluetoothAdmin";
_btadmin = new anywheresoftware.b4a.objects.Serial.BluetoothAdmin();
 //BA.debugLineNum = 32;BA.debugLine="Private PairedDevices As Map";
_paireddevices = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 34;BA.debugLine="Private FoundDevices As List";
_founddevices = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 35;BA.debugLine="Private DeviceName, DeviceMac As String";
_devicename = "";
_devicemac = "";
 //BA.debugLineNum = 37;BA.debugLine="Private Serial1 As Serial";
_serial1 = new anywheresoftware.b4a.objects.Serial();
 //BA.debugLineNum = 38;BA.debugLine="Dim TMPrinter As TextWriter";
_tmprinter = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private PrintBuffer As String";
_printbuffer = "";
 //BA.debugLineNum = 41;BA.debugLine="Private PrintLogo() As Byte";
_printlogo = new byte[(int) (0)];
;
 //BA.debugLineNum = 43;BA.debugLine="Private oStream As AsyncStreams";
_ostream = new anywheresoftware.b4a.randomaccessfile.AsyncStreams();
 //BA.debugLineNum = 44;BA.debugLine="Private Res As Int";
_res = 0;
 //BA.debugLineNum = 46;BA.debugLine="Private LogoBMP As Bitmap";
_logobmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private WoosimCMD As JavaObject";
_woosimcmd = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 48;BA.debugLine="Private WoosimImage As JavaObject";
_woosimimage = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 50;BA.debugLine="Private Logo As Bitmap";
_logo = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private InpTyp As SLInpTypeConst";
_inptyp = new bwsi.registration.townhall.slinptypeconst();
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static String  _regfontsizebinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 180;BA.debugLine="Private Sub RegFontSizeBinder_OnBindView (View As";
 //BA.debugLineNum = 181;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 182;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 183;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 184;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 185;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 186;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf059)))+"  "));
 //BA.debugLineNum = 187;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 189;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 192;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 193;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 194;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 195;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 197;BA.debugLine="End Sub";
return "";
}
public static String  _registeremp_onnegativeclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 169;BA.debugLine="Private Sub RegisterEmp_OnNegativeClicked (View As";
 //BA.debugLineNum = 170;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 171;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 172;BA.debugLine="ToastMessageShow($\"Canceled!\"$, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Canceled!")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
return "";
}
public static String  _registeremp_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 175;BA.debugLine="Private Sub RegisterEmp_OnPositiveClicked (View As";
 //BA.debugLineNum = 176;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 177;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 178;BA.debugLine="End Sub";
return "";
}
public static boolean  _saveguestdata() throws Exception{
boolean _bretval = false;
long _lngdatetime = 0L;
int _regseq = 0;
int _noprint = 0;
String _newregno = "";
String _guestname = "";
String _remarks = "";
String _careof = "";
String _approver = "";
String _timereg = "";
 //BA.debugLineNum = 430;BA.debugLine="Private Sub SaveGuestData() As Boolean";
 //BA.debugLineNum = 431;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 432;BA.debugLine="Dim lngDateTime As Long";
_lngdatetime = 0L;
 //BA.debugLineNum = 433;BA.debugLine="Dim RegSeq, NoPrint As Int";
_regseq = 0;
_noprint = 0;
 //BA.debugLineNum = 434;BA.debugLine="Dim NewRegNo As String";
_newregno = "";
 //BA.debugLineNum = 435;BA.debugLine="Dim GuestName, Remarks, CareOf, Approver As Strin";
_guestname = "";
_remarks = "";
_careof = "";
_approver = "";
 //BA.debugLineNum = 436;BA.debugLine="Dim TimeReg As String";
_timereg = "";
 //BA.debugLineNum = 439;BA.debugLine="GuestName = txtGuestName.Text";
_guestname = mostCurrent._txtguestname.getText();
 //BA.debugLineNum = 440;BA.debugLine="Remarks = txtRemarks.Text";
_remarks = mostCurrent._txtremarks.getText();
 //BA.debugLineNum = 441;BA.debugLine="CareOf = txtCareOf.Text";
_careof = mostCurrent._txtcareof.getText();
 //BA.debugLineNum = 442;BA.debugLine="Approver = txtApprovedBy.Text";
_approver = mostCurrent._txtapprovedby.getText();
 //BA.debugLineNum = 444;BA.debugLine="lngDateTime = DateTime.Now";
_lngdatetime = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 445;BA.debugLine="DateTime.TimeFormat = \"HH:mm:ss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("HH:mm:ss");
 //BA.debugLineNum = 446;BA.debugLine="TimeReg = DateTime.Time(lngDateTime)";
_timereg = anywheresoftware.b4a.keywords.Common.DateTime.Time(_lngdatetime);
 //BA.debugLineNum = 447;BA.debugLine="RegSeq = DBFunctions.GetSeqNo";
_regseq = mostCurrent._dbfunctions._getseqno /*int*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 449;BA.debugLine="NoPrint = 1";
_noprint = (int) (1);
 //BA.debugLineNum = 451;BA.debugLine="NewRegNo = DBFunctions.GetNewStubNo";
_newregno = mostCurrent._dbfunctions._getnewstubno /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 453;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 454;BA.debugLine="Try";
try { //BA.debugLineNum = 455;BA.debugLine="Starter.strCriteria=\"INSERT INTO tblGuests VALUE";
mostCurrent._starter._strcriteria /*String*/  = "INSERT INTO tblGuests VALUES ("+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null)+", ?, ?, ?, ?, ?, ?, ?, ?)";
 //BA.debugLineNum = 456;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(_newregno),(Object)(_guestname),(Object)(_remarks),(Object)(_careof),(Object)(_approver),(Object)(_timereg),(Object)(("1")),(Object)(_noprint)}));
 //BA.debugLineNum = 457;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 458;BA.debugLine="RegistrationNum = NewRegNo";
mostCurrent._registrationnum = _newregno;
 //BA.debugLineNum = 459;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e25) {
			processBA.setLastException(e25); //BA.debugLineNum = 461;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 462;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("814483488",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 464;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 466;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 467;BA.debugLine="End Sub";
return false;
}
public static String  _showaddperson() throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.collections.List _items = null;
 //BA.debugLineNum = 292;BA.debugLine="Private Sub ShowAddPerson";
 //BA.debugLineNum = 293;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 295;BA.debugLine="Dim items As List";
_items = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 296;BA.debugLine="items.Initialize";
_items.Initialize();
 //BA.debugLineNum = 297;BA.debugLine="items.Add(\"New Employee\")";
_items.Add((Object)("New Employee"));
 //BA.debugLineNum = 298;BA.debugLine="items.Add(\"Guest\")";
_items.Add((Object)("Guest"));
 //BA.debugLineNum = 300;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialog").SetStyle(_alert.getSTYLE_ACTIONSHEET()).SetTitle("Select an Option").SetTitleColor(anywheresoftware.b4a.keywords.Common.Colors.Black).SetCancelText("Cancel").SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOthers((java.util.ArrayList)(_items.getObject())).SetActionsheetTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnCancelClicked(mostCurrent.activityBA,"AddPeople").SetOnItemClickListener(mostCurrent.activityBA,"AddPeople");
 //BA.debugLineNum = 314;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD).SetCanc";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject())).SetCancelBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 315;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 316;BA.debugLine="End Sub";
return "";
}
public static String  _showentryerror(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 384;BA.debugLine="Private Sub ShowEntryError(sTitle As String, sMsg";
 //BA.debugLineNum = 385;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 387;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetMessage(_smsg).SetPositiveText("OK").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"EntryError").SetOnViewBinder(mostCurrent.activityBA,"EntryErrorBinder");
 //BA.debugLineNum = 401;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 402;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 404;BA.debugLine="End Sub";
return "";
}
public static String  _showprintererror(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 199;BA.debugLine="Private Sub ShowPrinterError(sTitle As String, sMs";
 //BA.debugLineNum = 200;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 202;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetMessage(_smsg).SetPositiveText("OK").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"PrinterError").SetOnViewBinder(mostCurrent.activityBA,"PrinterBinder");
 //BA.debugLineNum = 216;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 217;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 219;BA.debugLine="End Sub";
return "";
}
public static String  _showsuccessmsg(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 245;BA.debugLine="Private Sub ShowSuccessMsg(sTitle As String, sMsg";
 //BA.debugLineNum = 246;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 248;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetMessage(_smsg).SetPositiveText("OK").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"SuccessMsg").SetOnViewBinder(mostCurrent.activityBA,"SuccessBinder");
 //BA.debugLineNum = 262;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 263;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 265;BA.debugLine="End Sub";
return "";
}
public static String  _startprinter() throws Exception{
int _i = 0;
 //BA.debugLineNum = 535;BA.debugLine="Sub StartPrinter";
 //BA.debugLineNum = 537;BA.debugLine="PairedDevices.Initialize";
_paireddevices.Initialize();
 //BA.debugLineNum = 540;BA.debugLine="Try";
try { //BA.debugLineNum = 541;BA.debugLine="PairedDevices = Serial1.GetPairedDevices";
_paireddevices = _serial1.GetPairedDevices();
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 543;BA.debugLine="ShowPrinterError($\"PRINTER ERROR\"$, $\"Getting Pa";
_showprintererror(("PRINTER ERROR"),("Getting Paired Devices..."));
 //BA.debugLineNum = 544;BA.debugLine="TMPrinter.Close";
_tmprinter.Close();
 //BA.debugLineNum = 545;BA.debugLine="Serial1.Disconnect";
_serial1.Disconnect();
 };
 //BA.debugLineNum = 548;BA.debugLine="If PairedDevices.Size = 0 Then";
if (_paireddevices.getSize()==0) { 
 //BA.debugLineNum = 549;BA.debugLine="ShowPrinterError($\"PRINTER ERROR\"$, $\"Error Conn";
_showprintererror(("PRINTER ERROR"),("Error Connecting to Printer - Either No Paired Bluetooth Printer or Printer Not Found!"));
 //BA.debugLineNum = 550;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 553;BA.debugLine="If PairedDevices.Size = 1 Then";
if (_paireddevices.getSize()==1) { 
 //BA.debugLineNum = 554;BA.debugLine="Try";
try { //BA.debugLineNum = 555;BA.debugLine="DeviceName=PairedDevices.Getkeyat(0)";
_devicename = BA.ObjectToString(_paireddevices.GetKeyAt((int) (0)));
 //BA.debugLineNum = 556;BA.debugLine="DeviceMac=PairedDevices.GetValueAt(0)";
_devicemac = BA.ObjectToString(_paireddevices.GetValueAt((int) (0)));
 //BA.debugLineNum = 557;BA.debugLine="Log(DeviceName & \" -> \" & DeviceMac)";
anywheresoftware.b4a.keywords.Common.LogImpl("814680086",_devicename+" -> "+_devicemac,0);
 //BA.debugLineNum = 559;BA.debugLine="Serial1.ConnectInsecure(BTAdmin, DeviceMac,1)";
_serial1.ConnectInsecure(processBA,_btadmin,_devicemac,(int) (1));
 //BA.debugLineNum = 560;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 } 
       catch (Exception e21) {
			processBA.setLastException(e21); //BA.debugLineNum = 562;BA.debugLine="ShowPrinterError($\"PRINTER ERROR\"$, $\"Printer C";
_showprintererror(("PRINTER ERROR"),("Printer Connection Error..."));
 //BA.debugLineNum = 563;BA.debugLine="TMPrinter.Close";
_tmprinter.Close();
 //BA.debugLineNum = 564;BA.debugLine="Serial1.Disconnect";
_serial1.Disconnect();
 };
 }else {
 //BA.debugLineNum = 567;BA.debugLine="FoundDevices.Initialize";
_founddevices.Initialize();
 //BA.debugLineNum = 569;BA.debugLine="For i = 0 To PairedDevices.Size - 1";
{
final int step27 = 1;
final int limit27 = (int) (_paireddevices.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit27 ;_i = _i + step27 ) {
 //BA.debugLineNum = 570;BA.debugLine="FoundDevices.Add(PairedDevices.GetKeyAt(i))";
_founddevices.Add(_paireddevices.GetKeyAt(_i));
 //BA.debugLineNum = 571;BA.debugLine="DeviceName=PairedDevices.Getkeyat(i)";
_devicename = BA.ObjectToString(_paireddevices.GetKeyAt(_i));
 //BA.debugLineNum = 572;BA.debugLine="DeviceMac=PairedDevices.GetValueAt(i)";
_devicemac = BA.ObjectToString(_paireddevices.GetValueAt(_i));
 //BA.debugLineNum = 573;BA.debugLine="Log(DeviceName & \" -> \" & DeviceMac)";
anywheresoftware.b4a.keywords.Common.LogImpl("814680102",_devicename+" -> "+_devicemac,0);
 //BA.debugLineNum = 574;BA.debugLine="Serial1.ConnectInsecure(BTAdmin, DeviceMac,1)";
_serial1.ConnectInsecure(processBA,_btadmin,_devicemac,(int) (1));
 //BA.debugLineNum = 575;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 576;BA.debugLine="Exit";
if (true) break;
 }
};
 //BA.debugLineNum = 579;BA.debugLine="Res = InputList(FoundDevices, \"Choose Device\", -";
_res = anywheresoftware.b4a.keywords.Common.InputList(_founddevices,BA.ObjectToCharSequence("Choose Device"),(int) (-1),mostCurrent.activityBA);
 //BA.debugLineNum = 581;BA.debugLine="If Res <> DialogResponse.CANCEL Then";
if (_res!=anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 582;BA.debugLine="Serial1.Connect(PairedDevices.Get(FoundDevices.";
_serial1.Connect(processBA,BA.ObjectToString(_paireddevices.Get(_founddevices.Get(_res))));
 };
 };
 //BA.debugLineNum = 585;BA.debugLine="End Sub";
return "";
}
public static String  _successbinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 273;BA.debugLine="Private Sub SuccessBinder_OnBindView (View As View";
 //BA.debugLineNum = 274;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 275;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 276;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 277;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 278;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 279;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color((int) (mostCurrent._globalvar._poscolor /*double*/ )).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf164)))+"  "));
 //BA.debugLineNum = 280;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 282;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 285;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 286;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 287;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 288;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 290;BA.debugLine="End Sub";
return "";
}
public static String  _successmsg_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 267;BA.debugLine="Private Sub SuccessMsg_OnPositiveClicked (View As";
 //BA.debugLineNum = 268;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 269;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 270;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 271;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_menuitemclick(de.amberhome.objects.appcompat.ACMenuItemWrapper _item) throws Exception{
 //BA.debugLineNum = 136;BA.debugLine="Sub ToolBar_MenuItemClick (Item As ACMenuItem)";
 //BA.debugLineNum = 137;BA.debugLine="Select Case Item.Id";
switch (BA.switchObjectToInt(_item.getId(),(int) (1))) {
case 0: {
 break; }
}
;
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 132;BA.debugLine="Sub ToolBar_NavigationItemClick";
 //BA.debugLineNum = 133;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 134;BA.debugLine="End Sub";
return "";
}
public static boolean  _validentries() throws Exception{
 //BA.debugLineNum = 354;BA.debugLine="Private Sub ValidEntries() As Boolean";
 //BA.debugLineNum = 356;BA.debugLine="Try";
try { //BA.debugLineNum = 357;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtGuestNa";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtguestname.getText()))<=0) { 
 //BA.debugLineNum = 358;BA.debugLine="ShowEntryError($\"ERROR\"$, $\"Guest Name cannot b";
_showentryerror(("ERROR"),("Guest Name cannot be blank!"));
 //BA.debugLineNum = 359;BA.debugLine="txtGuestName.RequestFocus";
mostCurrent._txtguestname.RequestFocus();
 //BA.debugLineNum = 360;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 362;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtRemarks";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtremarks.getText()))<=0) { 
 //BA.debugLineNum = 363;BA.debugLine="ShowEntryError($\"ERROR\"$, $\"Remarks cannot be b";
_showentryerror(("ERROR"),("Remarks cannot be blank!"));
 //BA.debugLineNum = 364;BA.debugLine="txtRemarks.RequestFocus";
mostCurrent._txtremarks.RequestFocus();
 //BA.debugLineNum = 365;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 367;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtCareOf.";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtcareof.getText()))<=0) { 
 //BA.debugLineNum = 368;BA.debugLine="ShowEntryError($\"ERROR\"$,$\"Care of Employee can";
_showentryerror(("ERROR"),("Care of Employee cannot be blank!"));
 //BA.debugLineNum = 369;BA.debugLine="txtCareOf.RequestFocus";
mostCurrent._txtcareof.RequestFocus();
 //BA.debugLineNum = 370;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 372;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtApprove";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtapprovedby.getText()))<=0) { 
 //BA.debugLineNum = 373;BA.debugLine="ShowEntryError($\"ERROR\"$, $\"Approved By cannot";
_showentryerror(("ERROR"),("Approved By cannot be blank!"));
 //BA.debugLineNum = 374;BA.debugLine="txtApprovedBy.RequestFocus";
mostCurrent._txtapprovedby.RequestFocus();
 //BA.debugLineNum = 375;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 377;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e24) {
			processBA.setLastException(e24); //BA.debugLineNum = 379;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 380;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("814221338",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 382;BA.debugLine="End Sub";
return false;
}

public boolean _onCreateOptionsMenu(android.view.Menu menu) {
	if (processBA.subExists("activity_createmenu")) {
		processBA.raiseEvent2(null, true, "activity_createmenu", false, new de.amberhome.objects.appcompat.ACMenuWrapper(menu));
		return true;
	}
	else
		return false;
}
}
