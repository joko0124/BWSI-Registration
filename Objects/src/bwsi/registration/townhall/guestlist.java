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

public class guestlist extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static guestlist mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "bwsi.registration.townhall", "bwsi.registration.townhall.guestlist");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (guestlist).");
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
		activityBA = new BA(this, layout, processBA, "bwsi.registration.townhall", "bwsi.registration.townhall.guestlist");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "bwsi.registration.townhall.guestlist", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (guestlist) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (guestlist) Resume **");
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
		return guestlist.class;
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
            BA.LogInfo("** Activity (guestlist) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (guestlist) Pause event (activity is not paused). **");
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
            guestlist mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (guestlist) Resume **");
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
public static com.johan.Vibrate.Vibrate _vibration = null;
public static long[] _vibratepattern = null;
public de.amberhome.objects.appcompat.ACActionBar _actionbarbutton = null;
public de.amberhome.objects.appcompat.ACToolbarDarkWrapper _toolbar = null;
public anywheresoftware.b4a.object.XmlLayoutBuilder _xmlicon = null;
public b4a.example3.customlistview _clvguests = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtsearch = null;
public static int _iguestid = 0;
public static String _sguestname = "";
public anywheresoftware.b4a.objects.collections.List _mylist = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdtxtbox = null;
public anywheresoftware.b4a.objects.PanelWrapper _avatarbg = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblavatar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblguestname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblposition = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblreccount = null;
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
public bwsi.registration.townhall.addguest _addguest = null;
public bwsi.registration.townhall.mainscreen _mainscreen = null;
public bwsi.registration.townhall.dbutils _dbutils = null;
public bwsi.registration.townhall.scale _scale = null;
public bwsi.registration.townhall.starter _starter = null;
public bwsi.registration.townhall.httputils2service _httputils2service = null;
public bwsi.registration.townhall.b4xcollections _b4xcollections = null;
public static class _guestinfo{
public boolean IsInitialized;
public int GuestID;
public String StubNo;
public String GuestName;
public String GuestPos;
public String Avatar;
public void Initialize() {
IsInitialized = true;
GuestID = 0;
StubNo = "";
GuestName = "";
GuestPos = "";
Avatar = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}

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
 //BA.debugLineNum = 132;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 134;BA.debugLine="Scale.SetRate(0.5)";
mostCurrent._scale._setrate /*String*/ (mostCurrent.activityBA,0.5);
 //BA.debugLineNum = 135;BA.debugLine="Activity.LoadLayout(\"GuestListing\")";
mostCurrent._activity.LoadLayout("GuestListing",mostCurrent.activityBA);
 //BA.debugLineNum = 137;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(17).Bold.Append";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (17)).Bold().Append(BA.ObjectToCharSequence(("Townhall's Guest List"))).PopAll();
 //BA.debugLineNum = 138;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(14).Append($";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (14)).Append(BA.ObjectToCharSequence(("List of Guest"))).PopAll();
 //BA.debugLineNum = 140;BA.debugLine="ToolBar.InitMenuListener";
mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 141;BA.debugLine="ToolBar.Title = GlobalVar.CSTitle";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 142;BA.debugLine="ToolBar.SubTitle = GlobalVar.CSSubTitle";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 144;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 145;BA.debugLine="Dim xl As XmlLayoutBuilder";
_xl = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 146;BA.debugLine="jo = ToolBar";
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._toolbar.getObject()));
 //BA.debugLineNum = 147;BA.debugLine="jo.RunMethod(\"setPopupTheme\", Array(xl.GetResourc";
_jo.RunMethod("setPopupTheme",new Object[]{(Object)(_xl.GetResourceId("style","ToolbarMenu"))});
 //BA.debugLineNum = 148;BA.debugLine="jo.RunMethod(\"setContentInsetStartWithNavigation\"";
_jo.RunMethod("setContentInsetStartWithNavigation",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))});
 //BA.debugLineNum = 149;BA.debugLine="jo.RunMethod(\"setTitleMarginStart\", Array(0dip))";
_jo.RunMethod("setTitleMarginStart",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)))});
 //BA.debugLineNum = 151;BA.debugLine="ActionBarButton.Initialize";
mostCurrent._actionbarbutton.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 152;BA.debugLine="ActionBarButton.ShowUpIndicator = True";
mostCurrent._actionbarbutton.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 154;BA.debugLine="BTAdmin.Initialize(\"Admin\")";
_btadmin.Initialize(processBA,"Admin");
 //BA.debugLineNum = 155;BA.debugLine="Serial1.Initialize(\"Printer\")";
_serial1.Initialize("Printer");
 //BA.debugLineNum = 158;BA.debugLine="FillGuests";
_fillguests();
 //BA.debugLineNum = 159;BA.debugLine="CDTxtBox.Initialize2(Colors.Transparent, Colors.T";
mostCurrent._cdtxtbox.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.Transparent,anywheresoftware.b4a.keywords.Common.Colors.Transparent,(int) (0),(int) (0));
 //BA.debugLineNum = 160;BA.debugLine="txtSearch.Background = CDTxtBox";
mostCurrent._txtsearch.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdtxtbox.getObject()));
 //BA.debugLineNum = 162;BA.debugLine="IMEKeyboard.Initialize(\"\")";
mostCurrent._imekeyboard.Initialize("");
 //BA.debugLineNum = 163;BA.debugLine="vibratePattern = Array As Long(500, 500, 300, 500";
_vibratepattern = new long[]{(long) (500),(long) (500),(long) (300),(long) (500)};
 //BA.debugLineNum = 164;BA.debugLine="End Sub";
return "";
}
public static String  _activity_createmenu(de.amberhome.objects.appcompat.ACMenuWrapper _menu) throws Exception{
 //BA.debugLineNum = 18;BA.debugLine="Sub Activity_CreateMenu(Menu As ACMenu)";
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 166;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 167;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 168;BA.debugLine="ToolBar_NavigationItemClick";
_toolbar_navigationitemclick();
 //BA.debugLineNum = 169;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 171;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 178;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 180;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 175;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 176;BA.debugLine="End Sub";
return "";
}
public static String  _clvguests_itemclick(int _index,Object _value) throws Exception{
bwsi.registration.townhall.guestlist._guestinfo _guest = null;
 //BA.debugLineNum = 288;BA.debugLine="Sub clvGuests_ItemClick (Index As Int, Value As Ob";
 //BA.debugLineNum = 289;BA.debugLine="Dim Guest As GuestInfo = Value";
_guest = (bwsi.registration.townhall.guestlist._guestinfo)(_value);
 //BA.debugLineNum = 290;BA.debugLine="iGuestID = Guest.GuestID";
_iguestid = _guest.GuestID /*int*/ ;
 //BA.debugLineNum = 291;BA.debugLine="sGuestName = Guest.GuestName";
mostCurrent._sguestname = _guest.GuestName /*String*/ ;
 //BA.debugLineNum = 293;BA.debugLine="Log($\"Guest ID: \"$ & iGuestID)";
anywheresoftware.b4a.keywords.Common.LogImpl("118153477",("Guest ID: ")+BA.NumberToString(_iguestid),0);
 //BA.debugLineNum = 295;BA.debugLine="GuestOption";
_guestoption();
 //BA.debugLineNum = 296;BA.debugLine="End Sub";
return "";
}
public static String  _clvguests_visiblerangechanged(int _firstindex,int _lastindex) throws Exception{
int _extrasize = 0;
int _i = 0;
anywheresoftware.b4a.objects.B4XViewWrapper _pnl = null;
bwsi.registration.townhall.guestlist._guestinfo _gi = null;
 //BA.debugLineNum = 262;BA.debugLine="Sub clvGuests_VisibleRangeChanged (FirstIndex As I";
 //BA.debugLineNum = 263;BA.debugLine="Dim ExtraSize As Int = 15 'List size";
_extrasize = (int) (15);
 //BA.debugLineNum = 264;BA.debugLine="clvGuests.Refresh";
mostCurrent._clvguests._refresh();
 //BA.debugLineNum = 266;BA.debugLine="For i = Max(0, FirstIndex - ExtraSize) To Min(Las";
{
final int step3 = 1;
final int limit3 = (int) (anywheresoftware.b4a.keywords.Common.Min(_lastindex+_extrasize,mostCurrent._clvguests._getsize()-1));
_i = (int) (anywheresoftware.b4a.keywords.Common.Max(0,_firstindex-_extrasize)) ;
for (;_i <= limit3 ;_i = _i + step3 ) {
 //BA.debugLineNum = 267;BA.debugLine="Dim Pnl As B4XView = clvGuests.GetPanel(i)";
_pnl = new anywheresoftware.b4a.objects.B4XViewWrapper();
_pnl = mostCurrent._clvguests._getpanel(_i);
 //BA.debugLineNum = 268;BA.debugLine="If i > FirstIndex - ExtraSize And i < LastIndex";
if (_i>_firstindex-_extrasize && _i<_lastindex+_extrasize) { 
 //BA.debugLineNum = 269;BA.debugLine="If Pnl.NumberOfViews = 0 Then 'Add each item/la";
if (_pnl.getNumberOfViews()==0) { 
 //BA.debugLineNum = 270;BA.debugLine="Dim GI As GuestInfo = clvGuests.GetValue(i)";
_gi = (bwsi.registration.townhall.guestlist._guestinfo)(mostCurrent._clvguests._getvalue(_i));
 //BA.debugLineNum = 271;BA.debugLine="Pnl.LoadLayout(\"GuestList\")";
_pnl.LoadLayout("GuestList",mostCurrent.activityBA);
 //BA.debugLineNum = 272;BA.debugLine="lblGuestName.Text = GI.GuestName";
mostCurrent._lblguestname.setText(BA.ObjectToCharSequence(_gi.GuestName /*String*/ ));
 //BA.debugLineNum = 273;BA.debugLine="lblPosition.Text = GI.GuestPos";
mostCurrent._lblposition.setText(BA.ObjectToCharSequence(_gi.GuestPos /*String*/ ));
 //BA.debugLineNum = 274;BA.debugLine="lblAvatar.Text = GI.Avatar";
mostCurrent._lblavatar.setText(BA.ObjectToCharSequence(_gi.Avatar /*String*/ ));
 //BA.debugLineNum = 275;BA.debugLine="AvatarBG.Color = ShadeColor(Rnd(0xFF59C6CC, 0x";
mostCurrent._avatarbg.setColor(_shadecolor(anywheresoftware.b4a.keywords.Common.Rnd((int) (0xff59c6cc),(int) (0xfff8d0cd))));
 };
 }else {
 //BA.debugLineNum = 280;BA.debugLine="If Pnl.NumberOfViews > 0 Then";
if (_pnl.getNumberOfViews()>0) { 
 //BA.debugLineNum = 281;BA.debugLine="Pnl.RemoveAllViews 'Remove none visable item/l";
_pnl.RemoveAllViews();
 };
 };
 }
};
 //BA.debugLineNum = 286;BA.debugLine="End Sub";
return "";
}
public static String  _confirmreprint(String _sguest) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 447;BA.debugLine="Private Sub ConfirmReprint (sGuest As String)";
 //BA.debugLineNum = 448;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 449;BA.debugLine="Alert.Dismiss2";
_alert.Dismiss2();
 //BA.debugLineNum = 451;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(("CONFIRM REPRINT STUB")).SetMessage(("Do you want to Reprint")+anywheresoftware.b4a.keywords.Common.CRLF+_sguest+(" stub?")).SetPositiveText("YES").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetNegativeText("NO").SetNegativeColor((int) (mostCurrent._globalvar._negcolor /*double*/ )).SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"StubReprint").SetOnNegativeClicked(mostCurrent.activityBA,"StubReprint").SetOnViewBinder(mostCurrent.activityBA,"RegFontSizeBinder");
 //BA.debugLineNum = 469;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 470;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 472;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _createscaledbitmap(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _original,int _newwidth,int _newheight) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _b = null;
 //BA.debugLineNum = 650;BA.debugLine="Sub CreateScaledBitmap(Original As Bitmap, NewWidt";
 //BA.debugLineNum = 651;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 652;BA.debugLine="Dim b As Bitmap";
_b = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 653;BA.debugLine="b = r.RunStaticMethod(\"android.graphics.Bitmap\",";
_b = (anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(_r.RunStaticMethod("android.graphics.Bitmap","createScaledBitmap",new Object[]{(Object)(_original.getObject()),(Object)(_newwidth),(Object)(_newheight),(Object)(anywheresoftware.b4a.keywords.Common.True)},new String[]{"android.graphics.Bitmap","java.lang.int","java.lang.int","java.lang.boolean"})));
 //BA.debugLineNum = 654;BA.debugLine="Return b";
if (true) return _b;
 //BA.debugLineNum = 655;BA.debugLine="End Sub";
return null;
}
public static void  _fillguests() throws Exception{
ResumableSub_FillGuests rsub = new ResumableSub_FillGuests(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_FillGuests extends BA.ResumableSub {
public ResumableSub_FillGuests(bwsi.registration.townhall.guestlist parent) {
this.parent = parent;
}
bwsi.registration.townhall.guestlist parent;
Object _senderfilter = null;
boolean _success = false;
anywheresoftware.b4a.sql.SQL.ResultSetWrapper _rs = null;
long _starttime = 0L;
bwsi.registration.townhall.guestlist._guestinfo _ginfo = null;
String[] _name_parts = null;
anywheresoftware.b4a.objects.collections.List _initialslist = null;
String _part = "";
anywheresoftware.b4a.objects.B4XViewWrapper _pnl = null;
String[] group21;
int index21;
int groupLen21;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
try {

        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 201;BA.debugLine="Dim SenderFilter As Object";
_senderfilter = new Object();
 //BA.debugLineNum = 203;BA.debugLine="Try";
if (true) break;

case 1:
//try
this.state = 31;
this.catchState = 30;
this.state = 3;
if (true) break;

case 3:
//C
this.state = 4;
this.catchState = 30;
 //BA.debugLineNum = 204;BA.debugLine="Starter.strCriteria = \"SELECT Guests.GuestID, Gu";
parent.mostCurrent._starter._strcriteria /*String*/  = "SELECT Guests.GuestID, Guests.StubNo, "+"Guests.TableNo, Guests.GuestName, "+"Guests.Remarks_Occupation "+"FROM tblGuests AS Guests "+"ORDER BY Guests.GuestID, Guests.RegSeq ASC";
 //BA.debugLineNum = 209;BA.debugLine="LogColor(Starter.strCriteria, Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("118022409",parent.mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 211;BA.debugLine="SenderFilter = Starter.DBCon.ExecQueryAsync(\"SQL";
_senderfilter = parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQueryAsync(processBA,"SQL",parent.mostCurrent._starter._strcriteria /*String*/ ,(anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(anywheresoftware.b4a.keywords.Common.Null)));
 //BA.debugLineNum = 212;BA.debugLine="Wait For (SenderFilter) SQL_QueryComplete (Succe";
anywheresoftware.b4a.keywords.Common.WaitFor("sql_querycomplete", processBA, this, _senderfilter);
this.state = 32;
return;
case 32:
//C
this.state = 4;
_success = (Boolean) result[0];
_rs = (anywheresoftware.b4a.sql.SQL.ResultSetWrapper) result[1];
;
 //BA.debugLineNum = 213;BA.debugLine="If Success Then";
if (true) break;

case 4:
//if
this.state = 28;
if (_success) { 
this.state = 6;
}else {
this.state = 27;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 214;BA.debugLine="Dim StartTime As Long = DateTime.Now";
_starttime = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 215;BA.debugLine="clvGuests.Clear";
parent.mostCurrent._clvguests._clear();
 //BA.debugLineNum = 216;BA.debugLine="Do While RS.NextRow";
if (true) break;

case 7:
//do while
this.state = 18;
while (_rs.NextRow()) {
this.state = 9;
if (true) break;
}
if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 217;BA.debugLine="Dim GInfo As GuestInfo";
_ginfo = new bwsi.registration.townhall.guestlist._guestinfo();
 //BA.debugLineNum = 218;BA.debugLine="GInfo.Initialize";
_ginfo.Initialize();
 //BA.debugLineNum = 219;BA.debugLine="GInfo.GuestID = RS.GetInt(\"GuestID\")";
_ginfo.GuestID /*int*/  = _rs.GetInt("GuestID");
 //BA.debugLineNum = 220;BA.debugLine="GInfo.StubNo = RS.GetString(\"StubNo\")";
_ginfo.StubNo /*String*/  = _rs.GetString("StubNo");
 //BA.debugLineNum = 221;BA.debugLine="GInfo.GuestName = RS.GetString(\"GuestName\")";
_ginfo.GuestName /*String*/  = _rs.GetString("GuestName");
 //BA.debugLineNum = 222;BA.debugLine="GInfo.GuestPos = RS.GetString(\"Remarks_Occupat";
_ginfo.GuestPos /*String*/  = _rs.GetString("Remarks_Occupation");
 //BA.debugLineNum = 225;BA.debugLine="Dim name_parts() As String";
_name_parts = new String[(int) (0)];
java.util.Arrays.fill(_name_parts,"");
 //BA.debugLineNum = 226;BA.debugLine="name_parts = Regex.Split(\" \", RS.GetString(\"Gu";
_name_parts = anywheresoftware.b4a.keywords.Common.Regex.Split(" ",_rs.GetString("GuestName"));
 //BA.debugLineNum = 229;BA.debugLine="Dim initialsList As List";
_initialslist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 230;BA.debugLine="initialsList.Initialize";
_initialslist.Initialize();
 //BA.debugLineNum = 231;BA.debugLine="For Each part As String In name_parts";
if (true) break;

case 10:
//for
this.state = 17;
group21 = _name_parts;
index21 = 0;
groupLen21 = group21.length;
this.state = 33;
if (true) break;

case 33:
//C
this.state = 17;
if (index21 < groupLen21) {
this.state = 12;
_part = group21[index21];}
if (true) break;

case 34:
//C
this.state = 33;
index21++;
if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 232;BA.debugLine="If part.Length > 0 Then";
if (true) break;

case 13:
//if
this.state = 16;
if (_part.length()>0) { 
this.state = 15;
}if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 233;BA.debugLine="initialsList.Add(part.SubString2(0, 1))";
_initialslist.Add((Object)(_part.substring((int) (0),(int) (1))));
 if (true) break;

case 16:
//C
this.state = 34;
;
 if (true) break;
if (true) break;

case 17:
//C
this.state = 7;
;
 //BA.debugLineNum = 238;BA.debugLine="GInfo.Avatar = JoinStrings(initialsList, \"\")";
_ginfo.Avatar /*String*/  = _joinstrings(_initialslist,"");
 //BA.debugLineNum = 240;BA.debugLine="Dim Pnl As B4XView = xui.CreatePanel(\"\")";
_pnl = new anywheresoftware.b4a.objects.B4XViewWrapper();
_pnl = parent._xui.CreatePanel(processBA,"");
 //BA.debugLineNum = 241;BA.debugLine="Pnl.SetLayoutAnimated(0, 10dip, 20dip, clvGues";
_pnl.SetLayoutAnimated((int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),parent.mostCurrent._clvguests._asview().getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55)));
 //BA.debugLineNum = 242;BA.debugLine="clvGuests.Add(Pnl, GInfo)";
parent.mostCurrent._clvguests._add(_pnl,(Object)(_ginfo));
 if (true) break;
;
 //BA.debugLineNum = 244;BA.debugLine="If RS.RowCount <= 0 Then";

case 18:
//if
this.state = 25;
if (_rs.getRowCount()<=0) { 
this.state = 20;
}else if(_rs.getRowCount()==1) { 
this.state = 22;
}else {
this.state = 24;
}if (true) break;

case 20:
//C
this.state = 25;
 //BA.debugLineNum = 245;BA.debugLine="lblRecCount.Text = $\"No Guest Found\"$";
parent.mostCurrent._lblreccount.setText(BA.ObjectToCharSequence(("No Guest Found")));
 if (true) break;

case 22:
//C
this.state = 25;
 //BA.debugLineNum = 247;BA.debugLine="lblRecCount.Text = RS.RowCount & $\" Guest Foun";
parent.mostCurrent._lblreccount.setText(BA.ObjectToCharSequence(BA.NumberToString(_rs.getRowCount())+(" Guest Found")));
 if (true) break;

case 24:
//C
this.state = 25;
 //BA.debugLineNum = 249;BA.debugLine="lblRecCount.Text = RS.RowCount & $\" Guests Fou";
parent.mostCurrent._lblreccount.setText(BA.ObjectToCharSequence(BA.NumberToString(_rs.getRowCount())+(" Guests Found")));
 if (true) break;

case 25:
//C
this.state = 28;
;
 if (true) break;

case 27:
//C
this.state = 28;
 //BA.debugLineNum = 252;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("118022452",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;

case 28:
//C
this.state = 31;
;
 //BA.debugLineNum = 255;BA.debugLine="Log($\"List of Employees Records = ${NumberFormat";
anywheresoftware.b4a.keywords.Common.LogImpl("118022455",("List of Employees Records = "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(anywheresoftware.b4a.keywords.Common.NumberFormat2((anywheresoftware.b4a.keywords.Common.DateTime.getNow()-_starttime)/(double)1000,(int) (0),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False)))+" seconds to populate "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(parent.mostCurrent._clvguests._getsize()))+" Employees Records"),0);
 if (true) break;

case 30:
//C
this.state = 31;
this.catchState = 0;
 //BA.debugLineNum = 257;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("118022457",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 31:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 260;BA.debugLine="End Sub";
if (true) break;
}} 
       catch (Exception e0) {
			
if (catchState == 0)
    throw e0;
else {
    state = catchState;
processBA.setLastException(e0);}
            }
        }
    }
}
public static void  _sql_querycomplete(boolean _success,anywheresoftware.b4a.sql.SQL.ResultSetWrapper _rs) throws Exception{
}
public static int[]  _getargb(int _color) throws Exception{
int[] _rets = null;
 //BA.debugLineNum = 704;BA.debugLine="Sub GetARGB(Color As Int) As Int()";
 //BA.debugLineNum = 705;BA.debugLine="Private Rets(4) As Int";
_rets = new int[(int) (4)];
;
 //BA.debugLineNum = 706;BA.debugLine="Rets(0) = Bit.UnsignedShiftRight(Bit.And(Color, 0";
_rets[(int) (0)] = anywheresoftware.b4a.keywords.Common.Bit.UnsignedShiftRight(anywheresoftware.b4a.keywords.Common.Bit.And(_color,(int) (0xff000000)),(int) (24));
 //BA.debugLineNum = 707;BA.debugLine="Rets(1) = Bit.UnsignedShiftRight(Bit.And(Color, 0";
_rets[(int) (1)] = anywheresoftware.b4a.keywords.Common.Bit.UnsignedShiftRight(anywheresoftware.b4a.keywords.Common.Bit.And(_color,(int) (0xff0000)),(int) (16));
 //BA.debugLineNum = 708;BA.debugLine="Rets(2) = Bit.UnsignedShiftRight(Bit.And(Color, 0";
_rets[(int) (2)] = anywheresoftware.b4a.keywords.Common.Bit.UnsignedShiftRight(anywheresoftware.b4a.keywords.Common.Bit.And(_color,(int) (0xff00)),(int) (8));
 //BA.debugLineNum = 709;BA.debugLine="Rets(3) = Bit.And(Color, 0xff)";
_rets[(int) (3)] = anywheresoftware.b4a.keywords.Common.Bit.And(_color,(int) (0xff));
 //BA.debugLineNum = 710;BA.debugLine="Return Rets";
if (true) return _rets;
 //BA.debugLineNum = 711;BA.debugLine="End Sub";
return null;
}
public static int  _getnoofstubprint(int _iregid) throws Exception{
int _iretval = 0;
 //BA.debugLineNum = 686;BA.debugLine="Private Sub GetNoOfStubPrint(iRegID As Int) As Int";
 //BA.debugLineNum = 687;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 688;BA.debugLine="Try";
try { //BA.debugLineNum = 689;BA.debugLine="iRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_iretval = (int)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT NoOfPrint FROM tblGuests WHERE GuestID = "+BA.NumberToString(_iregid))));
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 691;BA.debugLine="iRetVal = 0";
_iretval = (int) (0);
 //BA.debugLineNum = 692;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("119333126",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 694;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 695;BA.debugLine="End Sub";
return 0;
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 55;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 58;BA.debugLine="Dim ActionBarButton As ACActionBar";
mostCurrent._actionbarbutton = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 59;BA.debugLine="Private ToolBar As ACToolBarDark";
mostCurrent._toolbar = new de.amberhome.objects.appcompat.ACToolbarDarkWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Private xmlIcon As XmlLayoutBuilder";
mostCurrent._xmlicon = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 63;BA.debugLine="Type GuestInfo (GuestID As Int, StubNo As String,";
;
 //BA.debugLineNum = 64;BA.debugLine="Private clvGuests As CustomListView";
mostCurrent._clvguests = new b4a.example3.customlistview();
 //BA.debugLineNum = 66;BA.debugLine="Private txtSearch As EditText";
mostCurrent._txtsearch = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private iGuestID As Int";
_iguestid = 0;
 //BA.debugLineNum = 69;BA.debugLine="Private sGuestName As String";
mostCurrent._sguestname = "";
 //BA.debugLineNum = 70;BA.debugLine="Private MyList As List";
mostCurrent._mylist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 74;BA.debugLine="Private CDTxtBox As ColorDrawable";
mostCurrent._cdtxtbox = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 76;BA.debugLine="Private AvatarBG As Panel";
mostCurrent._avatarbg = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 77;BA.debugLine="Private lblAvatar As Label";
mostCurrent._lblavatar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 78;BA.debugLine="Private lblGuestName As Label";
mostCurrent._lblguestname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Private lblPosition As Label";
mostCurrent._lblposition = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 81;BA.debugLine="Private lblRecCount As Label";
mostCurrent._lblreccount = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Private IMEKeyboard As IME";
mostCurrent._imekeyboard = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 85;BA.debugLine="Dim ESC As String = Chr(27)";
mostCurrent._esc = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)));
 //BA.debugLineNum = 86;BA.debugLine="Dim FS As String = Chr(28)";
mostCurrent._fs = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (28)));
 //BA.debugLineNum = 87;BA.debugLine="Dim GS As String = Chr(29)";
mostCurrent._gs = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (29)));
 //BA.debugLineNum = 90;BA.debugLine="Dim UNREVERSE As String  = GS & \"B\" & Chr(0)";
mostCurrent._unreverse = mostCurrent._gs+"B"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)));
 //BA.debugLineNum = 91;BA.debugLine="Dim REVERSE As String = GS & \"B\" & Chr(1)";
mostCurrent._reverse = mostCurrent._gs+"B"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)));
 //BA.debugLineNum = 94;BA.debugLine="Dim UNINVERT As String = ESC & \"{0\"";
mostCurrent._uninvert = mostCurrent._esc+"{0";
 //BA.debugLineNum = 95;BA.debugLine="Dim INVERT As String = ESC & \"{1\"";
mostCurrent._invert = mostCurrent._esc+"{1";
 //BA.debugLineNum = 98;BA.debugLine="Dim UNROTATE As String = ESC & \"V0\"";
mostCurrent._unrotate = mostCurrent._esc+"V0";
 //BA.debugLineNum = 99;BA.debugLine="Dim ROTATE As String = ESC & \"V1\"";
mostCurrent._rotate = mostCurrent._esc+"V1";
 //BA.debugLineNum = 102;BA.debugLine="Dim HT As String = Chr(9)";
mostCurrent._ht = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (9)));
 //BA.debugLineNum = 105;BA.debugLine="Dim ULINE0 As String = ESC & \"-0\"";
mostCurrent._uline0 = mostCurrent._esc+"-0";
 //BA.debugLineNum = 106;BA.debugLine="Dim ULINE1 As String = ESC & \"-1\"";
mostCurrent._uline1 = mostCurrent._esc+"-1";
 //BA.debugLineNum = 107;BA.debugLine="Dim ULINE2 As String = ESC & \"-2\"";
mostCurrent._uline2 = mostCurrent._esc+"-2";
 //BA.debugLineNum = 110;BA.debugLine="Dim BOLD As String = ESC & \"E1\"";
mostCurrent._bold = mostCurrent._esc+"E1";
 //BA.debugLineNum = 111;BA.debugLine="Dim NOBOLD As String = ESC & \"E0\"";
mostCurrent._nobold = mostCurrent._esc+"E0";
 //BA.debugLineNum = 114;BA.debugLine="Dim SINGLE As String = GS & \"!\" & Chr(0x00)";
mostCurrent._single = mostCurrent._gs+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x00)));
 //BA.debugLineNum = 115;BA.debugLine="Dim HIGH As String = GS & \"!\" & Chr(0x01)";
mostCurrent._high = mostCurrent._gs+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x01)));
 //BA.debugLineNum = 116;BA.debugLine="Dim WIDE As String = GS & \"!\" & Chr(0x10)";
mostCurrent._wide = mostCurrent._gs+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x10)));
 //BA.debugLineNum = 117;BA.debugLine="Dim HIGHWIDE As String = GS & \"!\" & Chr(0x11)";
mostCurrent._highwide = mostCurrent._gs+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x11)));
 //BA.debugLineNum = 120;BA.debugLine="Private LEFTJUSTIFY As String = ESC & \"a0\"";
mostCurrent._leftjustify = mostCurrent._esc+"a0";
 //BA.debugLineNum = 121;BA.debugLine="Private LINEDEFAULT As String = ESC & \"2\"";
mostCurrent._linedefault = mostCurrent._esc+"2";
 //BA.debugLineNum = 122;BA.debugLine="Private LINSET0 As String = ESC & \"$\" & Chr(0x0)";
mostCurrent._linset0 = mostCurrent._esc+"$"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x0)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x0)));
 //BA.debugLineNum = 123;BA.debugLine="Private LMARGIN0 As String = GS & \"L\" & Chr(0x0)";
mostCurrent._lmargin0 = mostCurrent._gs+"L"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x0)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x0)));
 //BA.debugLineNum = 124;BA.debugLine="Private WIDTH0 As String = GS & \"W\" & Chr(0xff) &";
mostCurrent._width0 = mostCurrent._gs+"W"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xff)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xff)));
 //BA.debugLineNum = 125;BA.debugLine="Private CHARSPACING0 As String = ESC & \" \" & Chr(";
mostCurrent._charspacing0 = mostCurrent._esc+" "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)));
 //BA.debugLineNum = 126;BA.debugLine="Private CHARFONT0 As String = ESC & \"M\" & Chr(0)";
mostCurrent._charfont0 = mostCurrent._esc+"M"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)));
 //BA.debugLineNum = 127;BA.debugLine="Dim DEFAULTS As String =  CHARSPACING0 & CHARFONT";
mostCurrent._defaults = mostCurrent._charspacing0+mostCurrent._charfont0+mostCurrent._lmargin0+mostCurrent._width0+mostCurrent._linset0+mostCurrent._linedefault+mostCurrent._leftjustify+mostCurrent._uninvert+mostCurrent._unrotate+mostCurrent._unreverse+mostCurrent._nobold+mostCurrent._uline0;
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public static String  _guestoption() throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.collections.List _items = null;
 //BA.debugLineNum = 395;BA.debugLine="Private Sub GuestOption";
 //BA.debugLineNum = 396;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 398;BA.debugLine="Dim items As List";
_items = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 399;BA.debugLine="items.Initialize";
_items.Initialize();
 //BA.debugLineNum = 400;BA.debugLine="items.Add(\"Reprint Stub\")";
_items.Add((Object)("Reprint Stub"));
 //BA.debugLineNum = 401;BA.debugLine="items.Add(\"Edit\")";
_items.Add((Object)("Edit"));
 //BA.debugLineNum = 403;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialog").SetStyle(_alert.getSTYLE_ACTIONSHEET()).SetTitle("Select an Option").SetTitleColor(anywheresoftware.b4a.keywords.Common.Colors.Black).SetCancelColor((int) (mostCurrent._globalvar._negcolor /*double*/ )).SetCancelText("Cancel").SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOthers((java.util.ArrayList)(_items.getObject())).SetActionsheetTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnCancelClicked(mostCurrent.activityBA,"GuestOptions").SetOnItemClickListener(mostCurrent.activityBA,"GuestOptions");
 //BA.debugLineNum = 418;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD).SetCanc";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject())).SetCancelBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 419;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 420;BA.debugLine="End Sub";
return "";
}
public static String  _guestoptions_oncancelclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 439;BA.debugLine="Private Sub GuestOptions_OnCancelClicked (View As";
 //BA.debugLineNum = 440;BA.debugLine="If View<>Null Then";
if (_view!= null) { 
 //BA.debugLineNum = 441;BA.debugLine="ToastMessageShow(\"Cancelled!\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Cancelled!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 442;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 443;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 };
 //BA.debugLineNum = 445;BA.debugLine="End Sub";
return "";
}
public static void  _guestoptions_onitemclick(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,String _selection,int _position,long _id) throws Exception{
ResumableSub_GuestOptions_OnItemClick rsub = new ResumableSub_GuestOptions_OnItemClick(null,_view,_selection,_position,_id);
rsub.resume(processBA, null);
}
public static class ResumableSub_GuestOptions_OnItemClick extends BA.ResumableSub {
public ResumableSub_GuestOptions_OnItemClick(bwsi.registration.townhall.guestlist parent,anywheresoftware.b4a.objects.ConcreteViewWrapper _view,String _selection,int _position,long _id) {
this.parent = parent;
this._view = _view;
this._selection = _selection;
this._position = _position;
this._id = _id;
}
bwsi.registration.townhall.guestlist parent;
anywheresoftware.b4a.objects.ConcreteViewWrapper _view;
String _selection;
int _position;
long _id;
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 425;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 426;BA.debugLine="Alert.Initialize.Dismiss2";
_alert.Initialize().Dismiss2();
 //BA.debugLineNum = 428;BA.debugLine="Select Case Position";
if (true) break;

case 1:
//select
this.state = 6;
switch (_position) {
case 0: {
this.state = 3;
if (true) break;
}
case 1: {
this.state = 5;
if (true) break;
}
}
if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 430;BA.debugLine="Alert.Dismiss2";
_alert.Dismiss2();
 //BA.debugLineNum = 431;BA.debugLine="Sleep(100)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (100));
this.state = 7;
return;
case 7:
//C
this.state = 6;
;
 //BA.debugLineNum = 433;BA.debugLine="vibration.vibratePattern(vibratePattern,0)";
parent._vibration.vibratePattern(processBA,parent._vibratepattern,(int) (0));
 //BA.debugLineNum = 434;BA.debugLine="ConfirmReprint(sGuestName)";
_confirmreprint(parent.mostCurrent._sguestname);
 if (true) break;

case 5:
//C
this.state = 6;
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 437;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _joinstrings(anywheresoftware.b4a.objects.collections.List _list,String _separator) throws Exception{
anywheresoftware.b4a.keywords.StringBuilderWrapper _result = null;
int _i = 0;
 //BA.debugLineNum = 713;BA.debugLine="Sub JoinStrings(list As List, separator As String)";
 //BA.debugLineNum = 715;BA.debugLine="Dim result As StringBuilder";
_result = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 716;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 717;BA.debugLine="For i = 0 To list.Size - 1";
{
final int step3 = 1;
final int limit3 = (int) (_list.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit3 ;_i = _i + step3 ) {
 //BA.debugLineNum = 718;BA.debugLine="If i > 0 Then result.Append(separator)";
if (_i>0) { 
_result.Append(_separator);};
 //BA.debugLineNum = 719;BA.debugLine="result.Append(list.Get(i))";
_result.Append(BA.ObjectToString(_list.Get(_i)));
 }
};
 //BA.debugLineNum = 721;BA.debugLine="Return result.ToString";
if (true) return _result.ToString();
 //BA.debugLineNum = 722;BA.debugLine="End Sub";
return "";
}
public static void  _printer_connected(boolean _success) throws Exception{
ResumableSub_Printer_Connected rsub = new ResumableSub_Printer_Connected(null,_success);
rsub.resume(processBA, null);
}
public static class ResumableSub_Printer_Connected extends BA.ResumableSub {
public ResumableSub_Printer_Connected(bwsi.registration.townhall.guestlist parent,boolean _success) {
this.parent = parent;
this._success = _success;
}
bwsi.registration.townhall.guestlist parent;
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
 //BA.debugLineNum = 607;BA.debugLine="Log(\"Connected: \" & Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("119136513","Connected: "+BA.ObjectToString(_success),0);
 //BA.debugLineNum = 609;BA.debugLine="If Success = False Then";
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
 //BA.debugLineNum = 610;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 614;BA.debugLine="StartPrinter";
_startprinter();
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 617;BA.debugLine="Dim initPrinter() As Byte";
_initprinter = new byte[(int) (0)];
;
 //BA.debugLineNum = 619;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 620;BA.debugLine="TMPrinter.Initialize2(Serial1.OutputStream, \"win";
parent._tmprinter.Initialize2(parent._serial1.getOutputStream(),"windows-1252");
 //BA.debugLineNum = 621;BA.debugLine="oStream.Initialize(Serial1.InputStream, Serial1.";
parent._ostream.Initialize(processBA,parent._serial1.getInputStream(),parent._serial1.getOutputStream(),"LogoPrint");
 //BA.debugLineNum = 622;BA.debugLine="Logo.Initialize(File.DirAssets, \"Stub-Header-Tow";
parent._logo.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Stub-Header-Townhall.png");
 //BA.debugLineNum = 623;BA.debugLine="LogoBMP = CreateScaledBitmap(Logo, Logo.Width, L";
parent._logobmp = _createscaledbitmap(parent._logo,parent._logo.getWidth(),parent._logo.getHeight());
 //BA.debugLineNum = 624;BA.debugLine="Log(DeviceName)";
anywheresoftware.b4a.keywords.Common.LogImpl("119136530",parent._devicename,0);
 //BA.debugLineNum = 626;BA.debugLine="WoosimCMD.InitializeStatic(\"com.woosim.printer.W";
parent._woosimcmd.InitializeStatic("com.woosim.printer.WoosimCmd");
 //BA.debugLineNum = 627;BA.debugLine="WoosimImage.InitializeStatic(\"com.woosim.printer";
parent._woosimimage.InitializeStatic("com.woosim.printer.WoosimImage");
 //BA.debugLineNum = 629;BA.debugLine="initPrinter = WoosimCMD.RunMethod(\"initPrinter\",";
_initprinter = (byte[])(parent._woosimcmd.RunMethod("initPrinter",(Object[])(anywheresoftware.b4a.keywords.Common.Null)));
 //BA.debugLineNum = 630;BA.debugLine="PrintLogo = WoosimImage.RunMethod(\"printBitmap\",";
parent._printlogo = (byte[])(parent._woosimimage.RunMethod("printBitmap",new Object[]{(Object)(0),(Object)(0),(Object)(420),(Object)(220),(Object)(parent._logobmp.getObject())}));
 //BA.debugLineNum = 632;BA.debugLine="oStream.Write(initPrinter)";
parent._ostream.Write(_initprinter);
 //BA.debugLineNum = 633;BA.debugLine="oStream.Write(WoosimCMD.RunMethod(\"setPageMode\",";
parent._ostream.Write((byte[])(parent._woosimcmd.RunMethod("setPageMode",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 634;BA.debugLine="oStream.Write(PrintLogo)";
parent._ostream.Write(parent._printlogo);
 //BA.debugLineNum = 635;BA.debugLine="oStream.Write(WoosimCMD.RunMethod(\"PM_setStdMode";
parent._ostream.Write((byte[])(parent._woosimcmd.RunMethod("PM_setStdMode",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 636;BA.debugLine="oStream.Write(PrintLogo)";
parent._ostream.Write(parent._printlogo);
 //BA.debugLineNum = 638;BA.debugLine="Sleep(500)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (500));
this.state = 7;
return;
case 7:
//C
this.state = 6;
;
 //BA.debugLineNum = 639;BA.debugLine="TMPrinter.WriteLine(PrintBuffer)";
parent._tmprinter.WriteLine(parent._printbuffer);
 //BA.debugLineNum = 640;BA.debugLine="Log(PrintBuffer)";
anywheresoftware.b4a.keywords.Common.LogImpl("119136546",parent._printbuffer,0);
 //BA.debugLineNum = 641;BA.debugLine="TMPrinter.Flush";
parent._tmprinter.Flush();
 //BA.debugLineNum = 642;BA.debugLine="Sleep(600)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (600));
this.state = 8;
return;
case 8:
//C
this.state = 6;
;
 //BA.debugLineNum = 643;BA.debugLine="ShowSuccessMsg($\"SUCCESS\"$, $\"Stub was successf";
_showsuccessmsg(("SUCCESS"),("Stub was successfully printed.")+anywheresoftware.b4a.keywords.Common.CRLF+("Tap OK to Continue..."));
 //BA.debugLineNum = 645;BA.debugLine="TMPrinter.Close";
parent._tmprinter.Close();
 //BA.debugLineNum = 646;BA.debugLine="Serial1.Disconnect";
parent._serial1.Disconnect();
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 648;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _printerbinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 327;BA.debugLine="Private Sub PrinterBinder_OnBindView (View As View";
 //BA.debugLineNum = 328;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 329;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 330;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 331;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 332;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 333;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf071)))+"  "));
 //BA.debugLineNum = 334;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 336;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 339;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 340;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 341;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 342;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 344;BA.debugLine="End Sub";
return "";
}
public static String  _printererror_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 322;BA.debugLine="Private Sub PrinterError_OnPositiveClicked (View A";
 //BA.debugLineNum = 323;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 324;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 325;BA.debugLine="End Sub";
return "";
}
public static String  _printstub(int _intguestid) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsdata = null;
String _stubno = "";
String _regfullname = "";
String _regposition = "";
int _regtableno = 0;
 //BA.debugLineNum = 494;BA.debugLine="Private Sub PrintStub(intGuestID As Int)";
 //BA.debugLineNum = 495;BA.debugLine="Dim rsData As Cursor";
_rsdata = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 496;BA.debugLine="Dim StubNo As String";
_stubno = "";
 //BA.debugLineNum = 497;BA.debugLine="Dim RegFullName As String";
_regfullname = "";
 //BA.debugLineNum = 498;BA.debugLine="Dim RegPosition As String";
_regposition = "";
 //BA.debugLineNum = 499;BA.debugLine="Dim RegTableNo As Int";
_regtableno = 0;
 //BA.debugLineNum = 501;BA.debugLine="ProgressDialogShow2($\"Stub Printing.  Please Wait";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Stub Printing.  Please Wait...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 503;BA.debugLine="Try";
try { //BA.debugLineNum = 504;BA.debugLine="Starter.strCriteria = \"SELECT StubNo, TableNo, G";
mostCurrent._starter._strcriteria /*String*/  = "SELECT StubNo, TableNo, GuestName, Remarks_Occupation "+"FROM tblGuests "+"WHERE GuestID = "+BA.NumberToString(_intguestid);
 //BA.debugLineNum = 508;BA.debugLine="rsData = Starter.DBCon.ExecQuery(Starter.strCrit";
_rsdata = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 509;BA.debugLine="LogColor(Starter.strCriteria, Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("119005455",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 511;BA.debugLine="If rsData.RowCount > 0 Then";
if (_rsdata.getRowCount()>0) { 
 //BA.debugLineNum = 512;BA.debugLine="rsData.Position = 0";
_rsdata.setPosition((int) (0));
 //BA.debugLineNum = 513;BA.debugLine="StubNo = rsData.GetString(\"StubNo\")";
_stubno = _rsdata.GetString("StubNo");
 //BA.debugLineNum = 514;BA.debugLine="RegFullName = rsData.GetString(\"GuestName\")";
_regfullname = _rsdata.GetString("GuestName");
 //BA.debugLineNum = 515;BA.debugLine="RegPosition = rsData.GetString(\"Remarks_Occupat";
_regposition = _rsdata.GetString("Remarks_Occupation");
 //BA.debugLineNum = 516;BA.debugLine="RegTableNo = rsData.GetInt(\"TableNo\")";
_regtableno = _rsdata.GetInt("TableNo");
 }else {
 //BA.debugLineNum = 518;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 521;BA.debugLine="PrintBuffer =  ESC & \"@\" _ 					& ESC & Chr(97)";
_printbuffer = mostCurrent._esc+"@"+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("STUB NO.: ")+mostCurrent._bold+_stubno+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._esc+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regposition+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._high+mostCurrent._reverse+("                  ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._highwide+mostCurrent._unreverse+("TABLE NO.: ")+BA.NumberToString(_regtableno)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+("Welcome to BWSI Townhall!")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("DINNER STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._esc+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regposition+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._high+mostCurrent._reverse+("                  ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._highwide+mostCurrent._unreverse+("TABLE NO.: ")+BA.NumberToString(_regtableno)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("LUNCH STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._esc+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regposition+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._high+mostCurrent._reverse+("                  ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._highwide+mostCurrent._unreverse+("TABLE NO.: ")+BA.NumberToString(_regtableno)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (73)));
 //BA.debugLineNum = 548;BA.debugLine="StartPrinter";
_startprinter();
 } 
       catch (Exception e23) {
			processBA.setLastException(e23); //BA.debugLineNum = 550;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 551;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("119005497",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 553;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 51;BA.debugLine="Private vibration As B4Avibrate";
_vibration = new com.johan.Vibrate.Vibrate();
 //BA.debugLineNum = 52;BA.debugLine="Private vibratePattern() As Long";
_vibratepattern = new long[(int) (0)];
;
 //BA.debugLineNum = 53;BA.debugLine="End Sub";
return "";
}
public static int  _shadecolor(int _clr) throws Exception{
int[] _argb = null;
float _factor = 0f;
 //BA.debugLineNum = 698;BA.debugLine="Sub ShadeColor(clr As Int) As Int";
 //BA.debugLineNum = 699;BA.debugLine="Dim argb() As Int = GetARGB(clr)";
_argb = _getargb(_clr);
 //BA.debugLineNum = 700;BA.debugLine="Dim factor As Float = 3";
_factor = (float) (3);
 //BA.debugLineNum = 701;BA.debugLine="Return xui.Color_RGB(argb(1) * factor, argb(2) *";
if (true) return _xui.Color_RGB((int) (_argb[(int) (1)]*_factor),(int) (_argb[(int) (2)]*_factor),(int) (_argb[(int) (3)]*_factor));
 //BA.debugLineNum = 702;BA.debugLine="End Sub";
return 0;
}
public static String  _showprintererror(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 300;BA.debugLine="Private Sub ShowPrinterError(sTitle As String, sMs";
 //BA.debugLineNum = 301;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 303;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetMessage(_smsg).SetPositiveText("OK").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"PrinterError").SetOnViewBinder(mostCurrent.activityBA,"PrinterBinder");
 //BA.debugLineNum = 317;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 318;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 320;BA.debugLine="End Sub";
return "";
}
public static String  _showsuccessmsg(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 346;BA.debugLine="Private Sub ShowSuccessMsg(sTitle As String, sMsg";
 //BA.debugLineNum = 347;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 349;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetMessage(_smsg).SetPositiveText("OK").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"SuccessMsg").SetOnViewBinder(mostCurrent.activityBA,"SuccessBinder");
 //BA.debugLineNum = 363;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 364;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 366;BA.debugLine="End Sub";
return "";
}
public static String  _startprinter() throws Exception{
int _i = 0;
 //BA.debugLineNum = 555;BA.debugLine="Sub StartPrinter";
 //BA.debugLineNum = 557;BA.debugLine="PairedDevices.Initialize";
_paireddevices.Initialize();
 //BA.debugLineNum = 559;BA.debugLine="Try";
try { //BA.debugLineNum = 560;BA.debugLine="PairedDevices = Serial1.GetPairedDevices";
_paireddevices = _serial1.GetPairedDevices();
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 562;BA.debugLine="ShowPrinterError($\"PRINTER ERROR\"$, $\"Getting Pa";
_showprintererror(("PRINTER ERROR"),("Getting Paired Devices..."));
 //BA.debugLineNum = 563;BA.debugLine="TMPrinter.Close";
_tmprinter.Close();
 //BA.debugLineNum = 564;BA.debugLine="Serial1.Disconnect";
_serial1.Disconnect();
 };
 //BA.debugLineNum = 567;BA.debugLine="If PairedDevices.Size = 0 Then";
if (_paireddevices.getSize()==0) { 
 //BA.debugLineNum = 568;BA.debugLine="ShowPrinterError($\"PRINTER ERROR\"$, $\"Error Conn";
_showprintererror(("PRINTER ERROR"),("Error Connecting to Printer - Either No Paired Bluetooth Printer or Printer Not Found!"));
 //BA.debugLineNum = 569;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 572;BA.debugLine="If PairedDevices.Size = 1 Then";
if (_paireddevices.getSize()==1) { 
 //BA.debugLineNum = 573;BA.debugLine="Try";
try { //BA.debugLineNum = 574;BA.debugLine="DeviceName=PairedDevices.Getkeyat(0)";
_devicename = BA.ObjectToString(_paireddevices.GetKeyAt((int) (0)));
 //BA.debugLineNum = 575;BA.debugLine="DeviceMac=PairedDevices.GetValueAt(0)";
_devicemac = BA.ObjectToString(_paireddevices.GetValueAt((int) (0)));
 //BA.debugLineNum = 576;BA.debugLine="Log(DeviceName & \" -> \" & DeviceMac)";
anywheresoftware.b4a.keywords.Common.LogImpl("119070997",_devicename+" -> "+_devicemac,0);
 //BA.debugLineNum = 578;BA.debugLine="Serial1.ConnectInsecure(BTAdmin, DeviceMac,1)";
_serial1.ConnectInsecure(processBA,_btadmin,_devicemac,(int) (1));
 //BA.debugLineNum = 579;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 } 
       catch (Exception e21) {
			processBA.setLastException(e21); //BA.debugLineNum = 581;BA.debugLine="ShowPrinterError($\"PRINTER ERROR\"$, $\"Printer C";
_showprintererror(("PRINTER ERROR"),("Printer Connection Error..."));
 //BA.debugLineNum = 582;BA.debugLine="TMPrinter.Close";
_tmprinter.Close();
 //BA.debugLineNum = 583;BA.debugLine="Serial1.Disconnect";
_serial1.Disconnect();
 };
 }else {
 //BA.debugLineNum = 586;BA.debugLine="FoundDevices.Initialize";
_founddevices.Initialize();
 //BA.debugLineNum = 588;BA.debugLine="For i = 0 To PairedDevices.Size - 1";
{
final int step27 = 1;
final int limit27 = (int) (_paireddevices.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit27 ;_i = _i + step27 ) {
 //BA.debugLineNum = 589;BA.debugLine="FoundDevices.Add(PairedDevices.GetKeyAt(i))";
_founddevices.Add(_paireddevices.GetKeyAt(_i));
 //BA.debugLineNum = 590;BA.debugLine="DeviceName=PairedDevices.Getkeyat(i)";
_devicename = BA.ObjectToString(_paireddevices.GetKeyAt(_i));
 //BA.debugLineNum = 591;BA.debugLine="DeviceMac=PairedDevices.GetValueAt(i)";
_devicemac = BA.ObjectToString(_paireddevices.GetValueAt(_i));
 //BA.debugLineNum = 592;BA.debugLine="Log(DeviceName & \" -> \" & DeviceMac)";
anywheresoftware.b4a.keywords.Common.LogImpl("119071013",_devicename+" -> "+_devicemac,0);
 //BA.debugLineNum = 593;BA.debugLine="Serial1.ConnectInsecure(BTAdmin, DeviceMac,1)";
_serial1.ConnectInsecure(processBA,_btadmin,_devicemac,(int) (1));
 //BA.debugLineNum = 594;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 595;BA.debugLine="Exit";
if (true) break;
 }
};
 //BA.debugLineNum = 598;BA.debugLine="Res = InputList(FoundDevices, \"Choose Device\", -";
_res = anywheresoftware.b4a.keywords.Common.InputList(_founddevices,BA.ObjectToCharSequence("Choose Device"),(int) (-1),mostCurrent.activityBA);
 //BA.debugLineNum = 600;BA.debugLine="If Res <> DialogResponse.CANCEL Then";
if (_res!=anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 601;BA.debugLine="Serial1.Connect(PairedDevices.Get(FoundDevices.";
_serial1.Connect(processBA,BA.ObjectToString(_paireddevices.Get(_founddevices.Get(_res))));
 };
 };
 //BA.debugLineNum = 604;BA.debugLine="End Sub";
return "";
}
public static String  _stubreprint_onnegativeclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 474;BA.debugLine="Private Sub StubReprint_OnNegativeClicked (View As";
 //BA.debugLineNum = 475;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 476;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 477;BA.debugLine="vibration.vibrateCancel";
_vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 478;BA.debugLine="ToastMessageShow($\"Reprinting Cancelled!\"$, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Reprinting Cancelled!")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 479;BA.debugLine="End Sub";
return "";
}
public static String  _stubreprint_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 481;BA.debugLine="Private Sub StubReprint_OnPositiveClicked (View As";
 //BA.debugLineNum = 482;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 483;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 484;BA.debugLine="vibration.vibrateCancel";
_vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 486;BA.debugLine="If Not(UpdateReprintStub(iGuestID)) Then Return";
if (anywheresoftware.b4a.keywords.Common.Not(_updatereprintstub(_iguestid))) { 
if (true) return "";};
 //BA.debugLineNum = 487;BA.debugLine="PrintStub(iGuestID)";
_printstub(_iguestid);
 //BA.debugLineNum = 488;BA.debugLine="ToastMessageShow($\"Reprinted!\"$, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Reprinted!")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 489;BA.debugLine="End Sub";
return "";
}
public static String  _successbinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 376;BA.debugLine="Private Sub SuccessBinder_OnBindView (View As View";
 //BA.debugLineNum = 377;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 378;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 379;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 380;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 381;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 382;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color((int) (mostCurrent._globalvar._poscolor /*double*/ )).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf164)))+"  "));
 //BA.debugLineNum = 383;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 385;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 388;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 389;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 390;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 391;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 393;BA.debugLine="End Sub";
return "";
}
public static String  _successmsg_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 368;BA.debugLine="Private Sub SuccessMsg_OnPositiveClicked (View As";
 //BA.debugLineNum = 369;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 370;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 371;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtSearch.T";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtsearch.getText()))>0) { 
mostCurrent._txtsearch.setText(BA.ObjectToCharSequence(""));};
 //BA.debugLineNum = 372;BA.debugLine="FillGuests";
_fillguests();
 //BA.debugLineNum = 374;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_menuitemclick(de.amberhome.objects.appcompat.ACMenuItemWrapper _item) throws Exception{
 //BA.debugLineNum = 192;BA.debugLine="Sub ToolBar_MenuItemClick (Item As ACMenuItem)";
 //BA.debugLineNum = 193;BA.debugLine="Select Case Item.Id";
switch (BA.switchObjectToInt(_item.getId(),(int) (1))) {
case 0: {
 //BA.debugLineNum = 195;BA.debugLine="GuestOption";
_guestoption();
 break; }
}
;
 //BA.debugLineNum = 197;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 182;BA.debugLine="Sub ToolBar_NavigationItemClick";
 //BA.debugLineNum = 183;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtSearch.T";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtsearch.getText()))<=0) { 
 //BA.debugLineNum = 184;BA.debugLine="IMEKeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 185;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else {
 //BA.debugLineNum = 187;BA.debugLine="txtSearch.Text = \"\"";
mostCurrent._txtsearch.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 188;BA.debugLine="IMEKeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 };
 //BA.debugLineNum = 190;BA.debugLine="End Sub";
return "";
}
public static boolean  _updatereprintstub(int _iregid) throws Exception{
boolean _bretval = false;
int _noprint = 0;
 //BA.debugLineNum = 659;BA.debugLine="Private Sub UpdateReprintStub(iRegID As Int) As Bo";
 //BA.debugLineNum = 660;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 661;BA.debugLine="Dim NoPrint As Int";
_noprint = 0;
 //BA.debugLineNum = 663;BA.debugLine="NoPrint = GetNoOfStubPrint(iRegID)";
_noprint = _getnoofstubprint(_iregid);
 //BA.debugLineNum = 664;BA.debugLine="NoPrint = NoPrint + 1";
_noprint = (int) (_noprint+1);
 //BA.debugLineNum = 666;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 667;BA.debugLine="Try";
try { //BA.debugLineNum = 668;BA.debugLine="Starter.strCriteria = \"UPDATE tblGuests \" & _";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblGuests "+"SET NoOfPrint = ?  "+"WHERE GuestID = "+BA.NumberToString(_iregid);
 //BA.debugLineNum = 672;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{BA.NumberToString(_noprint)}));
 //BA.debugLineNum = 674;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 675;BA.debugLine="ProgressDialogShow2($\"Preparing Stub Printing\"$,";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Preparing Stub Printing")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 676;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e13) {
			processBA.setLastException(e13); //BA.debugLineNum = 678;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 679;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 680;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("119267605",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 682;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 683;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 684;BA.debugLine="End Sub";
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
