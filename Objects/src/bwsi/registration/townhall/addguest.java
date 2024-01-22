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
public static com.johan.Vibrate.Vibrate _vibration = null;
public static long[] _vibratepattern = null;
public de.amberhome.objects.appcompat.ACActionBar _actionbarbutton = null;
public de.amberhome.objects.appcompat.ACToolbarDarkWrapper _toolbar = null;
public anywheresoftware.b4a.object.XmlLayoutBuilder _xmlicon = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdtxtbox = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btncancel = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnsave = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdcancel = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdsave = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtguestname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtposition = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtcareof = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txttableno = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtremarks = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtapprovedby = null;
public anywheresoftware.b4a.objects.IME _imekeyboard = null;
public static String _esc = "";
public static String _fs = "";
public static String _gs = "";
public static String _unreverse = "";
public static String _reverse = "";
public static String _uninvert = "";
public static String _invert = "";
public static String _unrotate = "";
public static String _rotate = "";
public static String _ht = "";
public static String _uline0 = "";
public static String _uline1 = "";
public static String _uline2 = "";
public static String _bold = "";
public static String _nobold = "";
public static String _single = "";
public static String _high = "";
public static String _wide = "";
public static String _highwide = "";
public static String _leftjustify = "";
public static String _linedefault = "";
public static String _linset0 = "";
public static String _lmargin0 = "";
public static String _width0 = "";
public static String _charspacing0 = "";
public static String _charfont0 = "";
public static String _defaults = "";
public bwsi.registration.townhall.main _main = null;
public bwsi.registration.townhall.actregistration _actregistration = null;
public bwsi.registration.townhall.registration _registration = null;
public bwsi.registration.townhall.addemployee _addemployee = null;
public bwsi.registration.townhall.globalvar _globalvar = null;
public bwsi.registration.townhall.dbfunctions _dbfunctions = null;
public bwsi.registration.townhall.mainscreen _mainscreen = null;
public bwsi.registration.townhall.guestlist _guestlist = null;
public bwsi.registration.townhall.dbutils _dbutils = null;
public bwsi.registration.townhall.scale _scale = null;
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
 //BA.debugLineNum = 128;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 130;BA.debugLine="Scale.SetRate(0.5)";
mostCurrent._scale._setrate /*String*/ (mostCurrent.activityBA,0.5);
 //BA.debugLineNum = 131;BA.debugLine="Activity.LoadLayout(\"AddGuestLayout\")";
mostCurrent._activity.LoadLayout("AddGuestLayout",mostCurrent.activityBA);
 //BA.debugLineNum = 133;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(17).Bold.Append";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (17)).Bold().Append(BA.ObjectToCharSequence(("ADD GUEST"))).PopAll();
 //BA.debugLineNum = 134;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(14).Append($";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (14)).Append(BA.ObjectToCharSequence(("Add 2024 Townhall Meeting's Guest"))).PopAll();
 //BA.debugLineNum = 136;BA.debugLine="ToolBar.InitMenuListener";
mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 137;BA.debugLine="ToolBar.Title = GlobalVar.CSTitle";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 138;BA.debugLine="ToolBar.SubTitle = GlobalVar.CSSubTitle";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 140;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 141;BA.debugLine="Dim xl As XmlLayoutBuilder";
_xl = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 142;BA.debugLine="jo = ToolBar";
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._toolbar.getObject()));
 //BA.debugLineNum = 143;BA.debugLine="jo.RunMethod(\"setPopupTheme\", Array(xl.GetResourc";
_jo.RunMethod("setPopupTheme",new Object[]{(Object)(_xl.GetResourceId("style","ToolbarMenu"))});
 //BA.debugLineNum = 144;BA.debugLine="jo.RunMethod(\"setContentInsetStartWithNavigation\"";
_jo.RunMethod("setContentInsetStartWithNavigation",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))});
 //BA.debugLineNum = 145;BA.debugLine="jo.RunMethod(\"setTitleMarginStart\", Array(0dip))";
_jo.RunMethod("setTitleMarginStart",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)))});
 //BA.debugLineNum = 147;BA.debugLine="InpTyp.Initialize";
_inptyp._initialize /*String*/ (processBA);
 //BA.debugLineNum = 149;BA.debugLine="InpTyp.SetInputType(txtGuestName,Array As Int(Inp";
_inptyp._setinputtype /*String*/ (mostCurrent._txtguestname,new int[]{_inptyp._type_class_text /*int*/ (),_inptyp._type_text_flag_auto_correct /*int*/ (),_inptyp._type_text_flag_cap_characters /*int*/ ()});
 //BA.debugLineNum = 150;BA.debugLine="InpTyp.SetInputType(txtPosition,Array As Int(InpT";
_inptyp._setinputtype /*String*/ (mostCurrent._txtposition,new int[]{_inptyp._type_class_text /*int*/ (),_inptyp._type_text_flag_auto_correct /*int*/ (),_inptyp._type_text_flag_cap_characters /*int*/ ()});
 //BA.debugLineNum = 151;BA.debugLine="InpTyp.SetInputType(txtCareOf,Array As Int(InpTyp";
_inptyp._setinputtype /*String*/ (mostCurrent._txtcareof,new int[]{_inptyp._type_class_text /*int*/ (),_inptyp._type_text_flag_auto_correct /*int*/ (),_inptyp._type_text_flag_cap_words /*int*/ ()});
 //BA.debugLineNum = 152;BA.debugLine="InpTyp.SetInputType(txtTableNo,Array As Int(InpTy";
_inptyp._setinputtype /*String*/ (mostCurrent._txttableno,new int[]{_inptyp._type_class_text /*int*/ (),_inptyp._type_text_flag_auto_correct /*int*/ (),_inptyp._type_class_number /*int*/ ()});
 //BA.debugLineNum = 153;BA.debugLine="InpTyp.SetInputType(txtRemarks,Array As Int(InpTy";
_inptyp._setinputtype /*String*/ (mostCurrent._txtremarks,new int[]{_inptyp._type_class_text /*int*/ (),_inptyp._type_text_flag_auto_correct /*int*/ (),_inptyp._type_text_flag_cap_sentences /*int*/ ()});
 //BA.debugLineNum = 154;BA.debugLine="InpTyp.SetInputType(txtApprovedBy,Array As Int(In";
_inptyp._setinputtype /*String*/ (mostCurrent._txtapprovedby,new int[]{_inptyp._type_class_text /*int*/ (),_inptyp._type_text_flag_auto_correct /*int*/ (),_inptyp._type_text_flag_cap_words /*int*/ ()});
 //BA.debugLineNum = 156;BA.debugLine="ActionBarButton.Initialize";
mostCurrent._actionbarbutton.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 157;BA.debugLine="ActionBarButton.ShowUpIndicator = True";
mostCurrent._actionbarbutton.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 158;BA.debugLine="BTAdmin.Initialize(\"Admin\")";
_btadmin.Initialize(processBA,"Admin");
 //BA.debugLineNum = 159;BA.debugLine="Serial1.Initialize(\"Printer\")";
_serial1.Initialize("Printer");
 //BA.debugLineNum = 161;BA.debugLine="cdTxtBox.Initialize2(Colors.Transparent, Colors.T";
mostCurrent._cdtxtbox.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.Transparent,anywheresoftware.b4a.keywords.Common.Colors.Transparent,(int) (0),(int) (0));
 //BA.debugLineNum = 162;BA.debugLine="txtGuestName.Background = cdTxtBox";
mostCurrent._txtguestname.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdtxtbox.getObject()));
 //BA.debugLineNum = 163;BA.debugLine="txtPosition.Background = cdTxtBox";
mostCurrent._txtposition.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdtxtbox.getObject()));
 //BA.debugLineNum = 164;BA.debugLine="txtCareOf.Background = cdTxtBox";
mostCurrent._txtcareof.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdtxtbox.getObject()));
 //BA.debugLineNum = 165;BA.debugLine="txtTableNo.Background = cdTxtBox";
mostCurrent._txttableno.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdtxtbox.getObject()));
 //BA.debugLineNum = 166;BA.debugLine="txtRemarks.Background = cdTxtBox";
mostCurrent._txtremarks.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdtxtbox.getObject()));
 //BA.debugLineNum = 167;BA.debugLine="txtApprovedBy.Background = cdTxtBox";
mostCurrent._txtapprovedby.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdtxtbox.getObject()));
 //BA.debugLineNum = 169;BA.debugLine="IMEKeyboard.Initialize(\"IME\")";
mostCurrent._imekeyboard.Initialize("IME");
 //BA.debugLineNum = 170;BA.debugLine="txtGuestName.RequestFocus";
mostCurrent._txtguestname.RequestFocus();
 //BA.debugLineNum = 171;BA.debugLine="IMEKeyboard.ShowKeyboard(txtGuestName)";
mostCurrent._imekeyboard.ShowKeyboard((android.view.View)(mostCurrent._txtguestname.getObject()));
 //BA.debugLineNum = 173;BA.debugLine="cdSave.Initialize2(0xFF1E4369, 20, 0,0xFF268FC2)";
mostCurrent._cdsave.Initialize2((int) (0xff1e4369),(int) (20),(int) (0),(int) (0xff268fc2));
 //BA.debugLineNum = 174;BA.debugLine="btnSave.Background = cdSave";
mostCurrent._btnsave.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdsave.getObject()));
 //BA.debugLineNum = 175;BA.debugLine="cdCancel.Initialize2(0xFFDC143C, 20, 0,0xFFDC3545";
mostCurrent._cdcancel.Initialize2((int) (0xffdc143c),(int) (20),(int) (0),(int) (0xffdc3545));
 //BA.debugLineNum = 176;BA.debugLine="btnCancel.Background = cdCancel";
mostCurrent._btncancel.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdcancel.getObject()));
 //BA.debugLineNum = 178;BA.debugLine="txtGuestName.Text = \"\"";
mostCurrent._txtguestname.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 179;BA.debugLine="txtPosition.Text = \"\"";
mostCurrent._txtposition.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 180;BA.debugLine="txtCareOf.Text = \"\"";
mostCurrent._txtcareof.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 181;BA.debugLine="txtTableNo.Text = \"\"";
mostCurrent._txttableno.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 182;BA.debugLine="txtRemarks.Text = \"\"";
mostCurrent._txtremarks.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 183;BA.debugLine="txtApprovedBy.Text = \"\"";
mostCurrent._txtapprovedby.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 184;BA.debugLine="vibratePattern = Array As Long(500, 500, 300, 500";
_vibratepattern = new long[]{(long) (500),(long) (500),(long) (300),(long) (500)};
 //BA.debugLineNum = 186;BA.debugLine="End Sub";
return "";
}
public static String  _activity_createmenu(de.amberhome.objects.appcompat.ACMenuWrapper _menu) throws Exception{
de.amberhome.objects.appcompat.ACMenuItemWrapper _item = null;
 //BA.debugLineNum = 18;BA.debugLine="Sub Activity_CreateMenu(Menu As ACMenu)";
 //BA.debugLineNum = 19;BA.debugLine="Dim Item As ACMenuItem";
_item = new de.amberhome.objects.appcompat.ACMenuItemWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Menu.Clear";
_menu.Clear();
 //BA.debugLineNum = 21;BA.debugLine="Menu.Add2(1, 1, \"List\",xmlIcon.GetDrawable(\"ic_vi";
_menu.Add2((int) (1),(int) (1),BA.ObjectToCharSequence("List"),mostCurrent._xmlicon.GetDrawable("ic_view_list_white_36dp")).setShowAsAction(_item.SHOW_AS_ACTION_IF_ROOM);
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 188;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 189;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 190;BA.debugLine="ToolBar_NavigationItemClick";
_toolbar_navigationitemclick();
 //BA.debugLineNum = 191;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 193;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 195;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 200;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 202;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 197;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return "";
}
public static String  _addpeople_oncancelclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 413;BA.debugLine="Private Sub AddPeople_OnCancelClicked (View As Vie";
 //BA.debugLineNum = 414;BA.debugLine="If View<>Null Then";
if (_view!= null) { 
 //BA.debugLineNum = 415;BA.debugLine="ToastMessageShow(\"Cancelled!\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Cancelled!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 416;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 417;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 };
 //BA.debugLineNum = 419;BA.debugLine="End Sub";
return "";
}
public static String  _addpeople_onitemclick(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,String _selection,int _position,long _id) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 402;BA.debugLine="Private Sub AddPeople_OnItemClick (View As View, S";
 //BA.debugLineNum = 403;BA.debugLine="ToastMessageShow(Selection&\" Selected! (Position";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_selection+" Selected! (Position : "+BA.NumberToString(_position)+")"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 404;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 405;BA.debugLine="Alert.Initialize.Dismiss2";
_alert.Initialize().Dismiss2();
 //BA.debugLineNum = 407;BA.debugLine="Select Case Position";
switch (_position) {
case 0: {
 break; }
case 1: {
 break; }
}
;
 //BA.debugLineNum = 411;BA.debugLine="End Sub";
return "";
}
public static String  _btncancel_click() throws Exception{
 //BA.debugLineNum = 430;BA.debugLine="Sub btnCancel_Click";
 //BA.debugLineNum = 431;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 432;BA.debugLine="End Sub";
return "";
}
public static String  _btnsave_click() throws Exception{
 //BA.debugLineNum = 423;BA.debugLine="Sub btnSave_Click";
 //BA.debugLineNum = 424;BA.debugLine="If Not(ValidEntries) Then Return";
if (anywheresoftware.b4a.keywords.Common.Not(_validentries())) { 
if (true) return "";};
 //BA.debugLineNum = 425;BA.debugLine="vibration.vibratePattern(vibratePattern, 0)";
_vibration.vibratePattern(processBA,_vibratepattern,(int) (0));
 //BA.debugLineNum = 426;BA.debugLine="ConfirmRegister(txtGuestName.Text)";
_confirmregister(mostCurrent._txtguestname.getText());
 //BA.debugLineNum = 428;BA.debugLine="End Sub";
return "";
}
public static String  _confirmregister(String _sguestname) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 217;BA.debugLine="Private Sub ConfirmRegister (sGuestName As String)";
 //BA.debugLineNum = 218;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 220;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(("CONFIRM REGISTRATION")).SetMessage(("Do you want to Add and Register ")+anywheresoftware.b4a.keywords.Common.CRLF+_sguestname+("?")).SetPositiveText("YES").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetNegativeText("NO").SetNegativeColor((int) (mostCurrent._globalvar._negcolor /*double*/ )).SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"RegisterEmp").SetOnNegativeClicked(mostCurrent.activityBA,"RegisterEmp").SetOnViewBinder(mostCurrent.activityBA,"RegFontSizeBinder");
 //BA.debugLineNum = 238;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 239;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 241;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _createscaledbitmap(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _original,int _newwidth,int _newheight) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _b = null;
 //BA.debugLineNum = 728;BA.debugLine="Sub CreateScaledBitmap(Original As Bitmap, NewWidt";
 //BA.debugLineNum = 729;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 730;BA.debugLine="Dim b As Bitmap";
_b = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 731;BA.debugLine="b = r.RunStaticMethod(\"android.graphics.Bitmap\",";
_b = (anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(_r.RunStaticMethod("android.graphics.Bitmap","createScaledBitmap",new Object[]{(Object)(_original.getObject()),(Object)(_newwidth),(Object)(_newheight),(Object)(anywheresoftware.b4a.keywords.Common.True)},new String[]{"android.graphics.Bitmap","java.lang.int","java.lang.int","java.lang.boolean"})));
 //BA.debugLineNum = 732;BA.debugLine="Return b";
if (true) return _b;
 //BA.debugLineNum = 733;BA.debugLine="End Sub";
return null;
}
public static String  _entryerror_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 502;BA.debugLine="Private Sub EntryError_OnPositiveClicked (View As";
 //BA.debugLineNum = 503;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 504;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 505;BA.debugLine="End Sub";
return "";
}
public static String  _entryerrorbinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 507;BA.debugLine="Private Sub EntryErrorBinder_OnBindView (View As V";
 //BA.debugLineNum = 508;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 509;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 510;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 511;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 512;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 513;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf071)))+"  "));
 //BA.debugLineNum = 514;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 516;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 519;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 520;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 521;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 522;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 524;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 58;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 62;BA.debugLine="Dim ActionBarButton As ACActionBar";
mostCurrent._actionbarbutton = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 63;BA.debugLine="Private ToolBar As ACToolBarDark";
mostCurrent._toolbar = new de.amberhome.objects.appcompat.ACToolbarDarkWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private xmlIcon As XmlLayoutBuilder";
mostCurrent._xmlicon = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 66;BA.debugLine="Private cdTxtBox As ColorDrawable";
mostCurrent._cdtxtbox = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 67;BA.debugLine="Private btnCancel As ACButton";
mostCurrent._btncancel = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private btnSave As ACButton";
mostCurrent._btnsave = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Private cdCancel, cdSave As ColorDrawable";
mostCurrent._cdcancel = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
mostCurrent._cdsave = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 72;BA.debugLine="Private txtGuestName As EditText";
mostCurrent._txtguestname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 73;BA.debugLine="Private txtPosition As EditText";
mostCurrent._txtposition = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 74;BA.debugLine="Private txtCareOf As EditText";
mostCurrent._txtcareof = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Private txtTableNo As EditText";
mostCurrent._txttableno = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 76;BA.debugLine="Private txtRemarks As EditText";
mostCurrent._txtremarks = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 77;BA.debugLine="Private txtApprovedBy As EditText";
mostCurrent._txtapprovedby = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Private IMEKeyboard As IME";
mostCurrent._imekeyboard = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 82;BA.debugLine="Dim ESC As String = Chr(27)";
mostCurrent._esc = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)));
 //BA.debugLineNum = 83;BA.debugLine="Dim FS As String = Chr(28)";
mostCurrent._fs = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (28)));
 //BA.debugLineNum = 84;BA.debugLine="Dim GS As String = Chr(29)";
mostCurrent._gs = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (29)));
 //BA.debugLineNum = 87;BA.debugLine="Dim UNREVERSE As String  = GS & \"B\" & Chr(0)";
mostCurrent._unreverse = mostCurrent._gs+"B"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)));
 //BA.debugLineNum = 88;BA.debugLine="Dim REVERSE As String = GS & \"B\" & Chr(1)";
mostCurrent._reverse = mostCurrent._gs+"B"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)));
 //BA.debugLineNum = 91;BA.debugLine="Dim UNINVERT As String = ESC & \"{0\"";
mostCurrent._uninvert = mostCurrent._esc+"{0";
 //BA.debugLineNum = 92;BA.debugLine="Dim INVERT As String = ESC & \"{1\"";
mostCurrent._invert = mostCurrent._esc+"{1";
 //BA.debugLineNum = 95;BA.debugLine="Dim UNROTATE As String = ESC & \"V0\"";
mostCurrent._unrotate = mostCurrent._esc+"V0";
 //BA.debugLineNum = 96;BA.debugLine="Dim ROTATE As String = ESC & \"V1\"";
mostCurrent._rotate = mostCurrent._esc+"V1";
 //BA.debugLineNum = 99;BA.debugLine="Dim HT As String = Chr(9)";
mostCurrent._ht = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (9)));
 //BA.debugLineNum = 102;BA.debugLine="Dim ULINE0 As String = ESC & \"-0\"";
mostCurrent._uline0 = mostCurrent._esc+"-0";
 //BA.debugLineNum = 103;BA.debugLine="Dim ULINE1 As String = ESC & \"-1\"";
mostCurrent._uline1 = mostCurrent._esc+"-1";
 //BA.debugLineNum = 104;BA.debugLine="Dim ULINE2 As String = ESC & \"-2\"";
mostCurrent._uline2 = mostCurrent._esc+"-2";
 //BA.debugLineNum = 107;BA.debugLine="Dim BOLD As String = ESC & \"E1\"";
mostCurrent._bold = mostCurrent._esc+"E1";
 //BA.debugLineNum = 108;BA.debugLine="Dim NOBOLD As String = ESC & \"E0\"";
mostCurrent._nobold = mostCurrent._esc+"E0";
 //BA.debugLineNum = 111;BA.debugLine="Dim SINGLE As String = GS & \"!\" & Chr(0x00)";
mostCurrent._single = mostCurrent._gs+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x00)));
 //BA.debugLineNum = 112;BA.debugLine="Dim HIGH As String = GS & \"!\" & Chr(0x01)";
mostCurrent._high = mostCurrent._gs+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x01)));
 //BA.debugLineNum = 113;BA.debugLine="Dim WIDE As String = GS & \"!\" & Chr(0x10)";
mostCurrent._wide = mostCurrent._gs+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x10)));
 //BA.debugLineNum = 114;BA.debugLine="Dim HIGHWIDE As String = GS & \"!\" & Chr(0x11)";
mostCurrent._highwide = mostCurrent._gs+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x11)));
 //BA.debugLineNum = 117;BA.debugLine="Private LEFTJUSTIFY As String = ESC & \"a0\"";
mostCurrent._leftjustify = mostCurrent._esc+"a0";
 //BA.debugLineNum = 118;BA.debugLine="Private LINEDEFAULT As String = ESC & \"2\"";
mostCurrent._linedefault = mostCurrent._esc+"2";
 //BA.debugLineNum = 119;BA.debugLine="Private LINSET0 As String = ESC & \"$\" & Chr(0x0)";
mostCurrent._linset0 = mostCurrent._esc+"$"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x0)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x0)));
 //BA.debugLineNum = 120;BA.debugLine="Private LMARGIN0 As String = GS & \"L\" & Chr(0x0)";
mostCurrent._lmargin0 = mostCurrent._gs+"L"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x0)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x0)));
 //BA.debugLineNum = 121;BA.debugLine="Private WIDTH0 As String = GS & \"W\" & Chr(0xff) &";
mostCurrent._width0 = mostCurrent._gs+"W"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xff)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xff)));
 //BA.debugLineNum = 122;BA.debugLine="Private CHARSPACING0 As String = ESC & \" \" & Chr(";
mostCurrent._charspacing0 = mostCurrent._esc+" "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)));
 //BA.debugLineNum = 123;BA.debugLine="Private CHARFONT0 As String = ESC & \"M\" & Chr(0)";
mostCurrent._charfont0 = mostCurrent._esc+"M"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)));
 //BA.debugLineNum = 124;BA.debugLine="Dim DEFAULTS As String =  CHARSPACING0 & CHARFONT";
mostCurrent._defaults = mostCurrent._charspacing0+mostCurrent._charfont0+mostCurrent._lmargin0+mostCurrent._width0+mostCurrent._linset0+mostCurrent._linedefault+mostCurrent._leftjustify+mostCurrent._uninvert+mostCurrent._unrotate+mostCurrent._unreverse+mostCurrent._nobold+mostCurrent._uline0;
 //BA.debugLineNum = 126;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 686;BA.debugLine="Log(\"Connected: \" & Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("115532033","Connected: "+BA.ObjectToString(_success),0);
 //BA.debugLineNum = 688;BA.debugLine="If Success = False Then";
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
 //BA.debugLineNum = 689;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 693;BA.debugLine="StartPrinter";
_startprinter();
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 696;BA.debugLine="Dim initPrinter() As Byte";
_initprinter = new byte[(int) (0)];
;
 //BA.debugLineNum = 698;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 699;BA.debugLine="TMPrinter.Initialize2(Serial1.OutputStream, \"win";
parent._tmprinter.Initialize2(parent._serial1.getOutputStream(),"windows-1252");
 //BA.debugLineNum = 700;BA.debugLine="oStream.Initialize(Serial1.InputStream, Serial1.";
parent._ostream.Initialize(processBA,parent._serial1.getInputStream(),parent._serial1.getOutputStream(),"LogoPrint");
 //BA.debugLineNum = 701;BA.debugLine="Logo.Initialize(File.DirAssets, \"Stub-Header-Tow";
parent._logo.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Stub-Header-Townhall.png");
 //BA.debugLineNum = 702;BA.debugLine="LogoBMP = CreateScaledBitmap(Logo, Logo.Width, L";
parent._logobmp = _createscaledbitmap(parent._logo,parent._logo.getWidth(),parent._logo.getHeight());
 //BA.debugLineNum = 703;BA.debugLine="Log(DeviceName)";
anywheresoftware.b4a.keywords.Common.LogImpl("115532050",parent._devicename,0);
 //BA.debugLineNum = 705;BA.debugLine="WoosimCMD.InitializeStatic(\"com.woosim.printer.W";
parent._woosimcmd.InitializeStatic("com.woosim.printer.WoosimCmd");
 //BA.debugLineNum = 706;BA.debugLine="WoosimImage.InitializeStatic(\"com.woosim.printer";
parent._woosimimage.InitializeStatic("com.woosim.printer.WoosimImage");
 //BA.debugLineNum = 708;BA.debugLine="initPrinter = WoosimCMD.RunMethod(\"initPrinter\",";
_initprinter = (byte[])(parent._woosimcmd.RunMethod("initPrinter",(Object[])(anywheresoftware.b4a.keywords.Common.Null)));
 //BA.debugLineNum = 709;BA.debugLine="PrintLogo = WoosimImage.RunMethod(\"printBitmap\",";
parent._printlogo = (byte[])(parent._woosimimage.RunMethod("printBitmap",new Object[]{(Object)(0),(Object)(0),(Object)(420),(Object)(220),(Object)(parent._logobmp.getObject())}));
 //BA.debugLineNum = 711;BA.debugLine="oStream.Write(initPrinter)";
parent._ostream.Write(_initprinter);
 //BA.debugLineNum = 712;BA.debugLine="oStream.Write(WoosimCMD.RunMethod(\"setPageMode\",";
parent._ostream.Write((byte[])(parent._woosimcmd.RunMethod("setPageMode",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 713;BA.debugLine="oStream.Write(PrintLogo)";
parent._ostream.Write(parent._printlogo);
 //BA.debugLineNum = 714;BA.debugLine="oStream.Write(WoosimCMD.RunMethod(\"PM_setStdMode";
parent._ostream.Write((byte[])(parent._woosimcmd.RunMethod("PM_setStdMode",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 715;BA.debugLine="oStream.Write(PrintLogo)";
parent._ostream.Write(parent._printlogo);
 //BA.debugLineNum = 717;BA.debugLine="Sleep(500)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (500));
this.state = 7;
return;
case 7:
//C
this.state = 6;
;
 //BA.debugLineNum = 718;BA.debugLine="TMPrinter.WriteLine(PrintBuffer)";
parent._tmprinter.WriteLine(parent._printbuffer);
 //BA.debugLineNum = 719;BA.debugLine="Log(PrintBuffer)";
anywheresoftware.b4a.keywords.Common.LogImpl("115532066",parent._printbuffer,0);
 //BA.debugLineNum = 720;BA.debugLine="TMPrinter.Flush";
parent._tmprinter.Flush();
 //BA.debugLineNum = 721;BA.debugLine="Sleep(600)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (600));
this.state = 8;
return;
case 8:
//C
this.state = 6;
;
 //BA.debugLineNum = 722;BA.debugLine="ShowSuccessMsg($\"SUCCESS\"$, $\"Stub was successfu";
_showsuccessmsg(("SUCCESS"),("Stub was successfully printed.")+anywheresoftware.b4a.keywords.Common.CRLF+("Tap OK to Continue..."));
 //BA.debugLineNum = 723;BA.debugLine="TMPrinter.Close";
parent._tmprinter.Close();
 //BA.debugLineNum = 724;BA.debugLine="Serial1.Disconnect";
parent._serial1.Disconnect();
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 726;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _printerbinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 309;BA.debugLine="Private Sub PrinterBinder_OnBindView (View As View";
 //BA.debugLineNum = 310;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 311;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 312;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 313;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 314;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 315;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf071)))+"  "));
 //BA.debugLineNum = 316;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 318;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 321;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 322;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 323;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 324;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 326;BA.debugLine="End Sub";
return "";
}
public static String  _printererror_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 304;BA.debugLine="Private Sub PrinterError_OnPositiveClicked (View A";
 //BA.debugLineNum = 305;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 306;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 307;BA.debugLine="End Sub";
return "";
}
public static String  _printstub(int _iguestid) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsdata = null;
String _stubno = "";
String _regfullname = "";
String _regposition = "";
int _regtableno = 0;
 //BA.debugLineNum = 573;BA.debugLine="Private Sub PrintStub(iGuestID As Int)";
 //BA.debugLineNum = 574;BA.debugLine="Dim rsData As Cursor";
_rsdata = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 575;BA.debugLine="Dim StubNo As String";
_stubno = "";
 //BA.debugLineNum = 576;BA.debugLine="Dim RegFullName As String";
_regfullname = "";
 //BA.debugLineNum = 577;BA.debugLine="Dim RegPosition As String";
_regposition = "";
 //BA.debugLineNum = 578;BA.debugLine="Dim RegTableNo As Int";
_regtableno = 0;
 //BA.debugLineNum = 580;BA.debugLine="ProgressDialogShow2($\"Stub Printing.  Please Wait";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Stub Printing.  Please Wait...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 582;BA.debugLine="Try";
try { //BA.debugLineNum = 583;BA.debugLine="Starter.strCriteria = \"SELECT StubNo, TableNo, G";
mostCurrent._starter._strcriteria /*String*/  = "SELECT StubNo, TableNo, GuestName, Remarks_Occupation "+"FROM tblGuests "+"WHERE GuestID = "+BA.NumberToString(_iguestid);
 //BA.debugLineNum = 587;BA.debugLine="rsData = Starter.DBCon.ExecQuery(Starter.strCrit";
_rsdata = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 588;BA.debugLine="LogColor(Starter.strCriteria, Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("115400975",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 590;BA.debugLine="If rsData.RowCount > 0 Then";
if (_rsdata.getRowCount()>0) { 
 //BA.debugLineNum = 591;BA.debugLine="rsData.Position = 0";
_rsdata.setPosition((int) (0));
 //BA.debugLineNum = 592;BA.debugLine="StubNo = rsData.GetString(\"StubNo\")";
_stubno = _rsdata.GetString("StubNo");
 //BA.debugLineNum = 593;BA.debugLine="RegFullName = rsData.GetString(\"GuestName\")";
_regfullname = _rsdata.GetString("GuestName");
 //BA.debugLineNum = 594;BA.debugLine="RegPosition = rsData.GetString(\"Remarks_Occupat";
_regposition = _rsdata.GetString("Remarks_Occupation");
 //BA.debugLineNum = 595;BA.debugLine="RegTableNo = rsData.GetInt(\"TableNo\")";
_regtableno = _rsdata.GetInt("TableNo");
 }else {
 //BA.debugLineNum = 597;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 600;BA.debugLine="PrintBuffer =  ESC & \"@\" _ 					& ESC & Chr(97)";
_printbuffer = mostCurrent._esc+"@"+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("STUB NO.: ")+mostCurrent._bold+_stubno+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._esc+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regposition+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._high+mostCurrent._reverse+("                  ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._highwide+mostCurrent._unreverse+("TABLE NO.: ")+BA.NumberToString(_regtableno)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+("Welcome to BWSI Townhall!")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("DINNER STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._esc+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regposition+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._high+mostCurrent._reverse+("                  ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._highwide+mostCurrent._unreverse+("TABLE NO.: ")+BA.NumberToString(_regtableno)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("LUNCH STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._esc+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regposition+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._high+mostCurrent._reverse+("                  ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._highwide+mostCurrent._unreverse+("TABLE NO.: ")+BA.NumberToString(_regtableno)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (73)));
 //BA.debugLineNum = 627;BA.debugLine="StartPrinter";
_startprinter();
 } 
       catch (Exception e23) {
			processBA.setLastException(e23); //BA.debugLineNum = 629;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 630;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("115401017",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 632;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 53;BA.debugLine="Private vibration As B4Avibrate";
_vibration = new com.johan.Vibrate.Vibrate();
 //BA.debugLineNum = 54;BA.debugLine="Private vibratePattern() As Long";
_vibratepattern = new long[(int) (0)];
;
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}
public static String  _regfontsizebinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 263;BA.debugLine="Private Sub RegFontSizeBinder_OnBindView (View As";
 //BA.debugLineNum = 264;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 265;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 266;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 267;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 268;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 269;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf059)))+"  "));
 //BA.debugLineNum = 270;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 272;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 275;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 276;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 277;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 278;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 280;BA.debugLine="End Sub";
return "";
}
public static String  _registeremp_onnegativeclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 243;BA.debugLine="Private Sub RegisterEmp_OnNegativeClicked (View As";
 //BA.debugLineNum = 244;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 245;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 246;BA.debugLine="vibration.vibrateCancel";
_vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 247;BA.debugLine="ToastMessageShow($\"Canceled!\"$, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Canceled!")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 248;BA.debugLine="End Sub";
return "";
}
public static String  _registeremp_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 250;BA.debugLine="Private Sub RegisterEmp_OnPositiveClicked (View As";
 //BA.debugLineNum = 251;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 252;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 253;BA.debugLine="vibration.vibrateCancel";
_vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 255;BA.debugLine="If Not(SaveGuestRegistration) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_saveguestregistration())) { 
 //BA.debugLineNum = 256;BA.debugLine="ShowEntryError($\"ERROR\"$, $\"Unable to Add Guest";
_showentryerror(("ERROR"),("Unable to Add Guest data due to ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 };
 //BA.debugLineNum = 258;BA.debugLine="GlobalVar.NewRegID = DBFunctions.GetIDByCode(\"Gue";
mostCurrent._globalvar._newregid /*int*/  = mostCurrent._dbfunctions._getidbycode /*int*/ (mostCurrent.activityBA,"GuestID","tblGuests","StubNo",mostCurrent._globalvar._newregno /*String*/ );
 //BA.debugLineNum = 259;BA.debugLine="PrintStub(GlobalVar.NewRegID)";
_printstub(mostCurrent._globalvar._newregid /*int*/ );
 //BA.debugLineNum = 261;BA.debugLine="End Sub";
return "";
}
public static boolean  _saveguestregistration() throws Exception{
boolean _bretval = false;
long _lngdatetime = 0L;
int _regseq = 0;
int _noprint = 0;
String _sguestname = "";
String _sposition = "";
String _scareof = "";
String _sapprover = "";
String _sremarks = "";
String _divisionid = "";
String _stableno = "";
String _addedby = "";
String _timereg = "";
 //BA.debugLineNum = 526;BA.debugLine="Private Sub SaveGuestRegistration() As Boolean";
 //BA.debugLineNum = 527;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 528;BA.debugLine="Dim lngDateTime As Long";
_lngdatetime = 0L;
 //BA.debugLineNum = 529;BA.debugLine="Dim RegSeq, NoPrint As Int";
_regseq = 0;
_noprint = 0;
 //BA.debugLineNum = 532;BA.debugLine="Dim sGuestName, sPosition, sCareof, sApprover As";
_sguestname = "";
_sposition = "";
_scareof = "";
_sapprover = "";
 //BA.debugLineNum = 533;BA.debugLine="Dim sRemarks, DivisionID, sTableNo, AddedBy As St";
_sremarks = "";
_divisionid = "";
_stableno = "";
_addedby = "";
 //BA.debugLineNum = 534;BA.debugLine="Dim TimeReg As String";
_timereg = "";
 //BA.debugLineNum = 536;BA.debugLine="ProgressDialogShow2($\"Saving Employee Data...\"$,";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Saving Employee Data...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 538;BA.debugLine="sGuestName = txtGuestName.Text";
_sguestname = mostCurrent._txtguestname.getText();
 //BA.debugLineNum = 539;BA.debugLine="sPosition = txtPosition.Text";
_sposition = mostCurrent._txtposition.getText();
 //BA.debugLineNum = 540;BA.debugLine="sCareof = txtCareOf.Text";
_scareof = mostCurrent._txtcareof.getText();
 //BA.debugLineNum = 541;BA.debugLine="sTableNo = txtTableNo.Text";
_stableno = mostCurrent._txttableno.getText();
 //BA.debugLineNum = 542;BA.debugLine="sRemarks = txtRemarks.Text";
_sremarks = mostCurrent._txtremarks.getText();
 //BA.debugLineNum = 543;BA.debugLine="sApprover = txtApprovedBy.Text";
_sapprover = mostCurrent._txtapprovedby.getText();
 //BA.debugLineNum = 545;BA.debugLine="AddedBy = GlobalVar.AssignedEmp";
_addedby = mostCurrent._globalvar._assignedemp /*String*/ ;
 //BA.debugLineNum = 547;BA.debugLine="lngDateTime = DateTime.Now";
_lngdatetime = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 548;BA.debugLine="DateTime.TimeFormat = \"HH:mm:ss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("HH:mm:ss");
 //BA.debugLineNum = 549;BA.debugLine="TimeReg = DateTime.Time(lngDateTime)";
_timereg = anywheresoftware.b4a.keywords.Common.DateTime.Time(_lngdatetime);
 //BA.debugLineNum = 551;BA.debugLine="RegSeq = DBFunctions.GetSeqNo";
_regseq = mostCurrent._dbfunctions._getseqno /*int*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 553;BA.debugLine="NoPrint = 1";
_noprint = (int) (1);
 //BA.debugLineNum = 555;BA.debugLine="GlobalVar.NewRegNo = DBFunctions.GetNewStubNo";
mostCurrent._globalvar._newregno /*String*/  = mostCurrent._dbfunctions._getnewstubno /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 557;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 558;BA.debugLine="Try";
try { //BA.debugLineNum = 559;BA.debugLine="Starter.strCriteria=\"INSERT INTO tblGuests VALUE";
mostCurrent._starter._strcriteria /*String*/  = "INSERT INTO tblGuests VALUES ("+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null)+", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
 //BA.debugLineNum = 560;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._globalvar._newregno /*String*/ ),(Object)(_stableno),(Object)(_sguestname),(Object)(_sposition),(Object)(_scareof),(Object)(_sapprover),(Object)(_sremarks),(Object)(mostCurrent._globalvar._assignedemp /*String*/ ),(Object)(_timereg),(Object)(_regseq),(Object)(("1")),(Object)(("1"))}));
 //BA.debugLineNum = 561;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 562;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e28) {
			processBA.setLastException(e28); //BA.debugLineNum = 564;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 565;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("115335463",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 567;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 569;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 570;BA.debugLine="End Sub";
return false;
}
public static String  _showaddperson() throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.collections.List _items = null;
 //BA.debugLineNum = 375;BA.debugLine="Private Sub ShowAddPerson";
 //BA.debugLineNum = 376;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 378;BA.debugLine="Dim items As List";
_items = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 379;BA.debugLine="items.Initialize";
_items.Initialize();
 //BA.debugLineNum = 380;BA.debugLine="items.Add(\"New Employee\")";
_items.Add((Object)("New Employee"));
 //BA.debugLineNum = 381;BA.debugLine="items.Add(\"Guest\")";
_items.Add((Object)("Guest"));
 //BA.debugLineNum = 383;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialog").SetStyle(_alert.getSTYLE_ACTIONSHEET()).SetTitle("Select an Option").SetTitleColor(anywheresoftware.b4a.keywords.Common.Colors.Black).SetCancelText("Cancel").SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOthers((java.util.ArrayList)(_items.getObject())).SetActionsheetTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnCancelClicked(mostCurrent.activityBA,"AddPeople").SetOnItemClickListener(mostCurrent.activityBA,"AddPeople");
 //BA.debugLineNum = 397;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD).SetCanc";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject())).SetCancelBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 398;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 399;BA.debugLine="End Sub";
return "";
}
public static String  _showentryerror(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 480;BA.debugLine="Private Sub ShowEntryError(sTitle As String, sMsg";
 //BA.debugLineNum = 481;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 483;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetMessage(_smsg).SetPositiveText("OK").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"EntryError").SetOnViewBinder(mostCurrent.activityBA,"EntryErrorBinder");
 //BA.debugLineNum = 497;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 498;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 500;BA.debugLine="End Sub";
return "";
}
public static String  _showprintererror(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 282;BA.debugLine="Private Sub ShowPrinterError(sTitle As String, sMs";
 //BA.debugLineNum = 283;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 285;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetMessage(_smsg).SetPositiveText("OK").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"PrinterError").SetOnViewBinder(mostCurrent.activityBA,"PrinterBinder");
 //BA.debugLineNum = 299;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 300;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 302;BA.debugLine="End Sub";
return "";
}
public static String  _showsuccessmsg(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 328;BA.debugLine="Private Sub ShowSuccessMsg(sTitle As String, sMsg";
 //BA.debugLineNum = 329;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 331;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetMessage(_smsg).SetPositiveText("OK").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"SuccessMsg").SetOnViewBinder(mostCurrent.activityBA,"SuccessBinder");
 //BA.debugLineNum = 345;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 346;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 348;BA.debugLine="End Sub";
return "";
}
public static String  _startprinter() throws Exception{
int _i = 0;
 //BA.debugLineNum = 634;BA.debugLine="Sub StartPrinter";
 //BA.debugLineNum = 636;BA.debugLine="PairedDevices.Initialize";
_paireddevices.Initialize();
 //BA.debugLineNum = 638;BA.debugLine="Try";
try { //BA.debugLineNum = 639;BA.debugLine="PairedDevices = Serial1.GetPairedDevices";
_paireddevices = _serial1.GetPairedDevices();
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 641;BA.debugLine="ShowPrinterError($\"PRINTER ERROR\"$, $\"Getting Pa";
_showprintererror(("PRINTER ERROR"),("Getting Paired Devices..."));
 //BA.debugLineNum = 642;BA.debugLine="TMPrinter.Close";
_tmprinter.Close();
 //BA.debugLineNum = 643;BA.debugLine="Serial1.Disconnect";
_serial1.Disconnect();
 };
 //BA.debugLineNum = 646;BA.debugLine="If PairedDevices.Size = 0 Then";
if (_paireddevices.getSize()==0) { 
 //BA.debugLineNum = 647;BA.debugLine="ShowPrinterError($\"PRINTER ERROR\"$, $\"Error Conn";
_showprintererror(("PRINTER ERROR"),("Error Connecting to Printer - Either No Paired Bluetooth Printer or Printer Not Found!"));
 //BA.debugLineNum = 648;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 651;BA.debugLine="If PairedDevices.Size = 1 Then";
if (_paireddevices.getSize()==1) { 
 //BA.debugLineNum = 652;BA.debugLine="Try";
try { //BA.debugLineNum = 653;BA.debugLine="DeviceName=PairedDevices.Getkeyat(0)";
_devicename = BA.ObjectToString(_paireddevices.GetKeyAt((int) (0)));
 //BA.debugLineNum = 654;BA.debugLine="DeviceMac=PairedDevices.GetValueAt(0)";
_devicemac = BA.ObjectToString(_paireddevices.GetValueAt((int) (0)));
 //BA.debugLineNum = 655;BA.debugLine="Log(DeviceName & \" -> \" & DeviceMac)";
anywheresoftware.b4a.keywords.Common.LogImpl("115466517",_devicename+" -> "+_devicemac,0);
 //BA.debugLineNum = 657;BA.debugLine="Serial1.ConnectInsecure(BTAdmin, DeviceMac,1)";
_serial1.ConnectInsecure(processBA,_btadmin,_devicemac,(int) (1));
 //BA.debugLineNum = 658;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 } 
       catch (Exception e21) {
			processBA.setLastException(e21); //BA.debugLineNum = 660;BA.debugLine="ShowPrinterError($\"PRINTER ERROR\"$, $\"Printer C";
_showprintererror(("PRINTER ERROR"),("Printer Connection Error..."));
 //BA.debugLineNum = 661;BA.debugLine="TMPrinter.Close";
_tmprinter.Close();
 //BA.debugLineNum = 662;BA.debugLine="Serial1.Disconnect";
_serial1.Disconnect();
 };
 }else {
 //BA.debugLineNum = 665;BA.debugLine="FoundDevices.Initialize";
_founddevices.Initialize();
 //BA.debugLineNum = 667;BA.debugLine="For i = 0 To PairedDevices.Size - 1";
{
final int step27 = 1;
final int limit27 = (int) (_paireddevices.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit27 ;_i = _i + step27 ) {
 //BA.debugLineNum = 668;BA.debugLine="FoundDevices.Add(PairedDevices.GetKeyAt(i))";
_founddevices.Add(_paireddevices.GetKeyAt(_i));
 //BA.debugLineNum = 669;BA.debugLine="DeviceName=PairedDevices.Getkeyat(i)";
_devicename = BA.ObjectToString(_paireddevices.GetKeyAt(_i));
 //BA.debugLineNum = 670;BA.debugLine="DeviceMac=PairedDevices.GetValueAt(i)";
_devicemac = BA.ObjectToString(_paireddevices.GetValueAt(_i));
 //BA.debugLineNum = 671;BA.debugLine="Log(DeviceName & \" -> \" & DeviceMac)";
anywheresoftware.b4a.keywords.Common.LogImpl("115466533",_devicename+" -> "+_devicemac,0);
 //BA.debugLineNum = 672;BA.debugLine="Serial1.ConnectInsecure(BTAdmin, DeviceMac,1)";
_serial1.ConnectInsecure(processBA,_btadmin,_devicemac,(int) (1));
 //BA.debugLineNum = 673;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 674;BA.debugLine="Exit";
if (true) break;
 }
};
 //BA.debugLineNum = 677;BA.debugLine="Res = InputList(FoundDevices, \"Choose Device\", -";
_res = anywheresoftware.b4a.keywords.Common.InputList(_founddevices,BA.ObjectToCharSequence("Choose Device"),(int) (-1),mostCurrent.activityBA);
 //BA.debugLineNum = 679;BA.debugLine="If Res <> DialogResponse.CANCEL Then";
if (_res!=anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 680;BA.debugLine="Serial1.Connect(PairedDevices.Get(FoundDevices.";
_serial1.Connect(processBA,BA.ObjectToString(_paireddevices.Get(_founddevices.Get(_res))));
 };
 };
 //BA.debugLineNum = 683;BA.debugLine="End Sub";
return "";
}
public static String  _successbinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 356;BA.debugLine="Private Sub SuccessBinder_OnBindView (View As View";
 //BA.debugLineNum = 357;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 358;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 359;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 360;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 361;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 362;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color((int) (mostCurrent._globalvar._poscolor /*double*/ )).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf164)))+"  "));
 //BA.debugLineNum = 363;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 365;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 368;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 369;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 370;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 371;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 373;BA.debugLine="End Sub";
return "";
}
public static String  _successmsg_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 350;BA.debugLine="Private Sub SuccessMsg_OnPositiveClicked (View As";
 //BA.debugLineNum = 351;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 352;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 353;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 354;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_menuitemclick(de.amberhome.objects.appcompat.ACMenuItemWrapper _item) throws Exception{
 //BA.debugLineNum = 208;BA.debugLine="Sub ToolBar_MenuItemClick (Item As ACMenuItem)";
 //BA.debugLineNum = 209;BA.debugLine="Select Case Item.Id";
switch (BA.switchObjectToInt(_item.getId(),(int) (1))) {
case 0: {
 //BA.debugLineNum = 211;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 212;BA.debugLine="StartActivity(GuestList)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._guestlist.getObject()));
 break; }
}
;
 //BA.debugLineNum = 214;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 204;BA.debugLine="Sub ToolBar_NavigationItemClick";
 //BA.debugLineNum = 205;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 206;BA.debugLine="End Sub";
return "";
}
public static String  _txtguestname_enterpressed() throws Exception{
 //BA.debugLineNum = 737;BA.debugLine="Sub txtGuestName_EnterPressed";
 //BA.debugLineNum = 738;BA.debugLine="txtPosition.RequestFocus";
mostCurrent._txtposition.RequestFocus();
 //BA.debugLineNum = 739;BA.debugLine="End Sub";
return "";
}
public static boolean  _validentries() throws Exception{
 //BA.debugLineNum = 434;BA.debugLine="Private Sub ValidEntries() As Boolean";
 //BA.debugLineNum = 436;BA.debugLine="Try";
try { //BA.debugLineNum = 437;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtGuestNa";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtguestname.getText()))<=0) { 
 //BA.debugLineNum = 438;BA.debugLine="vibration.vibrateOnce(1500)";
_vibration.vibrateOnce(processBA,(long) (1500));
 //BA.debugLineNum = 439;BA.debugLine="ShowEntryError($\"ERROR\"$, $\"Guest Name cannot b";
_showentryerror(("ERROR"),("Guest Name cannot be blank!"));
 //BA.debugLineNum = 440;BA.debugLine="txtGuestName.RequestFocus";
mostCurrent._txtguestname.RequestFocus();
 //BA.debugLineNum = 441;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 445;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtPositio";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtposition.getText()))<=0) { 
 //BA.debugLineNum = 446;BA.debugLine="vibration.vibrateOnce(1500)";
_vibration.vibrateOnce(processBA,(long) (1500));
 //BA.debugLineNum = 447;BA.debugLine="ShowEntryError($\"ERROR\"$, $\"Guest Position/Occu";
_showentryerror(("ERROR"),("Guest Position/Occupation cannot be blank!"));
 //BA.debugLineNum = 448;BA.debugLine="txtPosition.RequestFocus";
mostCurrent._txtposition.RequestFocus();
 //BA.debugLineNum = 449;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 452;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtCareOf.";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtcareof.getText()))<=0) { 
 //BA.debugLineNum = 453;BA.debugLine="vibration.vibrateOnce(1500)";
_vibration.vibrateOnce(processBA,(long) (1500));
 //BA.debugLineNum = 454;BA.debugLine="ShowEntryError($\"ERROR\"$, $\"Accompanied Employe";
_showentryerror(("ERROR"),("Accompanied Employee Name cannot be blank!"));
 //BA.debugLineNum = 455;BA.debugLine="txtCareOf.RequestFocus";
mostCurrent._txtcareof.RequestFocus();
 //BA.debugLineNum = 456;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 459;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtTableNo";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txttableno.getText()))<=0) { 
 //BA.debugLineNum = 460;BA.debugLine="vibration.vibrateOnce(1500)";
_vibration.vibrateOnce(processBA,(long) (1500));
 //BA.debugLineNum = 461;BA.debugLine="ShowEntryError($\"ERROR\"$,$\"Table Number cannot";
_showentryerror(("ERROR"),("Table Number cannot be blank!"));
 //BA.debugLineNum = 462;BA.debugLine="txtTableNo.RequestFocus";
mostCurrent._txttableno.RequestFocus();
 //BA.debugLineNum = 463;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 466;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtApprove";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtapprovedby.getText()))<=0) { 
 //BA.debugLineNum = 467;BA.debugLine="vibration.vibrateOnce(1500)";
_vibration.vibrateOnce(processBA,(long) (1500));
 //BA.debugLineNum = 468;BA.debugLine="ShowEntryError($\"ERROR\"$, $\"Approver cannot be";
_showentryerror(("ERROR"),("Approver cannot be blank!"));
 //BA.debugLineNum = 469;BA.debugLine="txtApprovedBy.RequestFocus";
mostCurrent._txtapprovedby.RequestFocus();
 //BA.debugLineNum = 470;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 473;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e34) {
			processBA.setLastException(e34); //BA.debugLineNum = 475;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 476;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("115073322",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 478;BA.debugLine="End Sub";
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
