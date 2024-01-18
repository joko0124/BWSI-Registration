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

public class registration extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static registration mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "bwsi.registration.townhall", "bwsi.registration.townhall.registration");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (registration).");
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
		activityBA = new BA(this, layout, processBA, "bwsi.registration.townhall", "bwsi.registration.townhall.registration");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "bwsi.registration.townhall.registration", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (registration) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (registration) Resume **");
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
		return registration.class;
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
            BA.LogInfo("** Activity (registration) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (registration) Pause event (activity is not paused). **");
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
            registration mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (registration) Resume **");
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
public de.amberhome.objects.appcompat.ACActionBar _actionbarbutton = null;
public de.amberhome.objects.appcompat.ACToolbarDarkWrapper _toolbar = null;
public anywheresoftware.b4a.object.XmlLayoutBuilder _xmlicon = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsbranch = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsemployees = null;
public b4a.example3.customlistview _clvemployees = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtsearch = null;
public static int _regid = 0;
public static String _sregname = "";
public anywheresoftware.b4a.objects.collections.List _mylist = null;
public static int _titleheight = 0;
public static int _dividerheight = 0;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdtxtbox = null;
public static boolean _blnnewreg = false;
public de.amberhome.objects.appcompat.ACButtonWrapper _btncancel = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsearchtitle = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlsearchemployees = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlsearchmain = null;
public static int _iswapbranchid = 0;
public static int _iswapempid = 0;
public static String _sswapemp = "";
public bwsi.registration.townhall.searchview _sv = null;
public anywheresoftware.b4a.objects.PanelWrapper _avatarbg = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblavatar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbranch = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldivision = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblempname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblstatus = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnregister = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnswap = null;
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
public bwsi.registration.townhall.addemployee _addemployee = null;
public bwsi.registration.townhall.globalvar _globalvar = null;
public bwsi.registration.townhall.dbfunctions _dbfunctions = null;
public bwsi.registration.townhall.addguest _addguest = null;
public bwsi.registration.townhall.dbutils _dbutils = null;
public bwsi.registration.townhall.mainscreen _mainscreen = null;
public bwsi.registration.townhall.scale _scale = null;
public bwsi.registration.townhall.starter _starter = null;
public bwsi.registration.townhall.httputils2service _httputils2service = null;
public bwsi.registration.townhall.b4xcollections _b4xcollections = null;
public static class _employeeinfo{
public boolean IsInitialized;
public int RegID;
public String RegNo;
public int BranchID;
public String BranchName;
public String Avatar;
public String EmpName;
public String EmpDivision;
public int WillAttend;
public int RegStatus;
public void Initialize() {
IsInitialized = true;
RegID = 0;
RegNo = "";
BranchID = 0;
BranchName = "";
Avatar = "";
EmpName = "";
EmpDivision = "";
WillAttend = 0;
RegStatus = 0;
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
 //BA.debugLineNum = 151;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 153;BA.debugLine="Scale.SetRate(0.5)";
mostCurrent._scale._setrate /*String*/ (mostCurrent.activityBA,0.5);
 //BA.debugLineNum = 154;BA.debugLine="Activity.LoadLayout(\"Registration\")";
mostCurrent._activity.LoadLayout("Registration",mostCurrent.activityBA);
 //BA.debugLineNum = 156;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(17).Bold.Append";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (17)).Bold().Append(BA.ObjectToCharSequence(mostCurrent._globalvar._areaname /*String*/ )).PopAll();
 //BA.debugLineNum = 157;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(14).Append(G";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (14)).Append(BA.ObjectToCharSequence(mostCurrent._globalvar._areadesc /*String*/ )).PopAll();
 //BA.debugLineNum = 159;BA.debugLine="ToolBar.InitMenuListener";
mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 160;BA.debugLine="ToolBar.Title = GlobalVar.CSTitle";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 161;BA.debugLine="ToolBar.SubTitle = GlobalVar.CSSubTitle";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 163;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 164;BA.debugLine="Dim xl As XmlLayoutBuilder";
_xl = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 165;BA.debugLine="jo = ToolBar";
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._toolbar.getObject()));
 //BA.debugLineNum = 166;BA.debugLine="jo.RunMethod(\"setPopupTheme\", Array(xl.GetResourc";
_jo.RunMethod("setPopupTheme",new Object[]{(Object)(_xl.GetResourceId("style","ToolbarMenu"))});
 //BA.debugLineNum = 167;BA.debugLine="jo.RunMethod(\"setContentInsetStartWithNavigation\"";
_jo.RunMethod("setContentInsetStartWithNavigation",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))});
 //BA.debugLineNum = 168;BA.debugLine="jo.RunMethod(\"setTitleMarginStart\", Array(0dip))";
_jo.RunMethod("setTitleMarginStart",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)))});
 //BA.debugLineNum = 170;BA.debugLine="ActionBarButton.Initialize";
mostCurrent._actionbarbutton.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 171;BA.debugLine="ActionBarButton.ShowUpIndicator = True";
mostCurrent._actionbarbutton.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 173;BA.debugLine="GlobalVar.AreaID = DBFunctions.GetAreaID";
mostCurrent._globalvar._areaid /*int*/  = mostCurrent._dbfunctions._getareaid /*int*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 174;BA.debugLine="GlobalVar.AreaCode = DBFunctions.GetAreaCode(Glob";
mostCurrent._globalvar._areacode /*String*/  = mostCurrent._dbfunctions._getareacode /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 175;BA.debugLine="GlobalVar.AreaName = DBFunctions.GetAreaName(Glob";
mostCurrent._globalvar._areaname /*String*/  = mostCurrent._dbfunctions._getareaname /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 176;BA.debugLine="GlobalVar.AreaDesc = DBFunctions.GetAreaDesc(Glob";
mostCurrent._globalvar._areadesc /*String*/  = mostCurrent._dbfunctions._getareadesc /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 178;BA.debugLine="BTAdmin.Initialize(\"Admin\")";
_btadmin.Initialize(processBA,"Admin");
 //BA.debugLineNum = 179;BA.debugLine="Serial1.Initialize(\"Printer\")";
_serial1.Initialize("Printer");
 //BA.debugLineNum = 182;BA.debugLine="FillEmployess(GlobalVar.AreaID)";
_fillemployess(mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 183;BA.debugLine="RegID = 0";
_regid = (int) (0);
 //BA.debugLineNum = 184;BA.debugLine="CDTxtBox.Initialize2(Colors.Transparent, Colors.T";
mostCurrent._cdtxtbox.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.Transparent,anywheresoftware.b4a.keywords.Common.Colors.Transparent,(int) (0),(int) (0));
 //BA.debugLineNum = 185;BA.debugLine="txtSearch.Background = CDTxtBox";
mostCurrent._txtsearch.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdtxtbox.getObject()));
 //BA.debugLineNum = 187;BA.debugLine="blnNewReg = True";
_blnnewreg = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 188;BA.debugLine="IMEKeyboard.Initialize(\"\")";
mostCurrent._imekeyboard.Initialize("");
 //BA.debugLineNum = 189;BA.debugLine="End Sub";
return "";
}
public static String  _activity_createmenu(de.amberhome.objects.appcompat.ACMenuWrapper _menu) throws Exception{
de.amberhome.objects.appcompat.ACMenuItemWrapper _item = null;
 //BA.debugLineNum = 18;BA.debugLine="Sub Activity_CreateMenu(Menu As ACMenu)";
 //BA.debugLineNum = 19;BA.debugLine="Dim Item As ACMenuItem";
_item = new de.amberhome.objects.appcompat.ACMenuItemWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Menu.Clear";
_menu.Clear();
 //BA.debugLineNum = 21;BA.debugLine="Menu.Add2(1, 1, \"People\",xmlIcon.GetDrawable(\"ic_";
_menu.Add2((int) (1),(int) (1),BA.ObjectToCharSequence("People"),mostCurrent._xmlicon.GetDrawable("ic_group_add_white_36dp")).setShowAsAction(_item.SHOW_AS_ACTION_IF_ROOM);
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 191;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 192;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 193;BA.debugLine="ToolBar_NavigationItemClick";
_toolbar_navigationitemclick();
 //BA.debugLineNum = 194;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 196;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 208;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 210;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 200;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 201;BA.debugLine="GlobalVar.AreaID = DBFunctions.GetAreaID";
mostCurrent._globalvar._areaid /*int*/  = mostCurrent._dbfunctions._getareaid /*int*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 202;BA.debugLine="GlobalVar.AreaCode = DBFunctions.GetAreaCode(Glob";
mostCurrent._globalvar._areacode /*String*/  = mostCurrent._dbfunctions._getareacode /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 203;BA.debugLine="GlobalVar.AreaName = DBFunctions.GetAreaName(Glob";
mostCurrent._globalvar._areaname /*String*/  = mostCurrent._dbfunctions._getareaname /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 204;BA.debugLine="GlobalVar.AreaDesc = DBFunctions.GetAreaDesc(Glob";
mostCurrent._globalvar._areadesc /*String*/  = mostCurrent._dbfunctions._getareadesc /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 205;BA.debugLine="blnNewReg = True";
_blnnewreg = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 206;BA.debugLine="End Sub";
return "";
}
public static String  _addpeople_oncancelclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 807;BA.debugLine="Private Sub AddPeople_OnCancelClicked (View As Vie";
 //BA.debugLineNum = 808;BA.debugLine="If View<>Null Then";
if (_view!= null) { 
 //BA.debugLineNum = 809;BA.debugLine="ToastMessageShow(\"Cancelled!\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Cancelled!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 810;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 811;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 };
 //BA.debugLineNum = 813;BA.debugLine="End Sub";
return "";
}
public static String  _addpeople_onitemclick(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,String _selection,int _position,long _id) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 793;BA.debugLine="Private Sub AddPeople_OnItemClick (View As View, S";
 //BA.debugLineNum = 795;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 796;BA.debugLine="Alert.Initialize.Dismiss2";
_alert.Initialize().Dismiss2();
 //BA.debugLineNum = 798;BA.debugLine="Select Case Position";
switch (_position) {
case 0: {
 //BA.debugLineNum = 801;BA.debugLine="StartActivity(AddEmployee)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._addemployee.getObject()));
 break; }
case 1: {
 //BA.debugLineNum = 803;BA.debugLine="StartActivity(AddGuest)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._addguest.getObject()));
 break; }
}
;
 //BA.debugLineNum = 805;BA.debugLine="End Sub";
return "";
}
public static String  _btncancel_click() throws Exception{
 //BA.debugLineNum = 1249;BA.debugLine="Sub btnCancel_Click";
 //BA.debugLineNum = 1250;BA.debugLine="SearchClosed";
_searchclosed();
 //BA.debugLineNum = 1251;BA.debugLine="End Sub";
return "";
}
public static String  _btnregister_click() throws Exception{
Object _value = null;
int _index = 0;
bwsi.registration.townhall.registration._employeeinfo _employees = null;
 //BA.debugLineNum = 391;BA.debugLine="Sub btnRegister_Click";
 //BA.debugLineNum = 392;BA.debugLine="Dim Value As Object";
_value = new Object();
 //BA.debugLineNum = 393;BA.debugLine="Dim Index As Int = clvEmployees.GetItemFromView(S";
_index = mostCurrent._clvemployees._getitemfromview((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA))));
 //BA.debugLineNum = 394;BA.debugLine="Dim Employees As EmployeeInfo = clvEmployees.GetV";
_employees = (bwsi.registration.townhall.registration._employeeinfo)(mostCurrent._clvemployees._getvalue(_index));
 //BA.debugLineNum = 396;BA.debugLine="Value = Employees.RegID";
_value = (Object)(_employees.RegID /*int*/ );
 //BA.debugLineNum = 398;BA.debugLine="Log(Value)";
anywheresoftware.b4a.keywords.Common.LogImpl("47143431",BA.ObjectToString(_value),0);
 //BA.debugLineNum = 401;BA.debugLine="RegID = Value";
_regid = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 402;BA.debugLine="LogColor($\"Registration ID: \"$ & Value, Colors.Cy";
anywheresoftware.b4a.keywords.Common.LogImpl("47143435",("Registration ID: ")+BA.ObjectToString(_value),anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 403;BA.debugLine="Try";
try { //BA.debugLineNum = 404;BA.debugLine="sRegName = GetEmployeeName(Value)";
mostCurrent._sregname = _getemployeename((int)(BA.ObjectToNumber(_value)));
 } 
       catch (Exception e11) {
			processBA.setLastException(e11); //BA.debugLineNum = 406;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("47143439",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 409;BA.debugLine="ConfirmRegister(sRegName)";
_confirmregister(mostCurrent._sregname);
 //BA.debugLineNum = 410;BA.debugLine="End Sub";
return "";
}
public static String  _btnswap_click() throws Exception{
Object _value = null;
int _index = 0;
bwsi.registration.townhall.registration._employeeinfo _employees = null;
int _btnstatus = 0;
 //BA.debugLineNum = 350;BA.debugLine="Sub btnSwap_Click";
 //BA.debugLineNum = 351;BA.debugLine="Dim Value As Object";
_value = new Object();
 //BA.debugLineNum = 352;BA.debugLine="Dim Index As Int = clvEmployees.GetItemFromView(S";
_index = mostCurrent._clvemployees._getitemfromview((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA))));
 //BA.debugLineNum = 353;BA.debugLine="Dim Employees As EmployeeInfo = clvEmployees.GetV";
_employees = (bwsi.registration.townhall.registration._employeeinfo)(mostCurrent._clvemployees._getvalue(_index));
 //BA.debugLineNum = 355;BA.debugLine="Value = Employees.RegID";
_value = (Object)(_employees.RegID /*int*/ );
 //BA.debugLineNum = 356;BA.debugLine="iSwapBranchID = Employees.BranchID";
_iswapbranchid = _employees.BranchID /*int*/ ;
 //BA.debugLineNum = 357;BA.debugLine="Dim btnStatus As Int";
_btnstatus = 0;
 //BA.debugLineNum = 359;BA.debugLine="LogColor(Value, Colors.Red)";
anywheresoftware.b4a.keywords.Common.LogImpl("47077897",BA.ObjectToString(_value),anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 362;BA.debugLine="btnStatus = Employees.RegStatus";
_btnstatus = _employees.RegStatus /*int*/ ;
 //BA.debugLineNum = 364;BA.debugLine="LogColor($\"Register Status: \"$ & btnStatus, Color";
anywheresoftware.b4a.keywords.Common.LogImpl("47077902",("Register Status: ")+BA.NumberToString(_btnstatus),anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 365;BA.debugLine="LogColor($\"Branch ID: \"$ & iSwapBranchID, Colors.";
anywheresoftware.b4a.keywords.Common.LogImpl("47077903",("Branch ID: ")+BA.NumberToString(_iswapbranchid),anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 367;BA.debugLine="Try";
try { //BA.debugLineNum = 368;BA.debugLine="Select Case btnStatus";
switch (_btnstatus) {
case 0: {
 //BA.debugLineNum = 370;BA.debugLine="If pnlSearchMain.Visible = True Then Return";
if (mostCurrent._pnlsearchmain.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
if (true) return "";};
 //BA.debugLineNum = 371;BA.debugLine="pnlSearchMain.Visible = True";
mostCurrent._pnlsearchmain.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 372;BA.debugLine="lblSearchTitle.Text = $\"Search Employee to Swa";
mostCurrent._lblsearchtitle.setText(BA.ObjectToCharSequence(("Search Employee to Swap Duty")));
 //BA.debugLineNum = 373;BA.debugLine="SV.Initialize(Me,\"SV\")";
mostCurrent._sv._initialize /*String*/ (mostCurrent.activityBA,registration.getObject(),"SV");
 //BA.debugLineNum = 374;BA.debugLine="SV.AddToParent(pnlSearchEmployees, 3, 3, pnlSe";
mostCurrent._sv._addtoparent /*String*/ (mostCurrent._pnlsearchemployees,(int) (3),(int) (3),(int) (mostCurrent._pnlsearchemployees.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),mostCurrent._pnlsearchemployees.getHeight());
 //BA.debugLineNum = 375;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 376;BA.debugLine="SV.lv.Clear";
mostCurrent._sv._lv /*anywheresoftware.b4a.objects.ListViewWrapper*/ .Clear();
 //BA.debugLineNum = 377;BA.debugLine="btnCancel.Enabled = True";
mostCurrent._btncancel.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 379;BA.debugLine="SearchSwapEmployees(iSwapBranchID)";
_searchswapemployees(_iswapbranchid);
 break; }
case 1: {
 //BA.debugLineNum = 382;BA.debugLine="sRegName = GetEmployeeName(Value)";
mostCurrent._sregname = _getemployeename((int)(BA.ObjectToNumber(_value)));
 //BA.debugLineNum = 383;BA.debugLine="RegID = Value";
_regid = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 384;BA.debugLine="ConfirmReprint(sRegName)";
_confirmreprint(mostCurrent._sregname);
 break; }
}
;
 } 
       catch (Exception e29) {
			processBA.setLastException(e29); //BA.debugLineNum = 387;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("47077925",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 389;BA.debugLine="End Sub";
return "";
}
public static String  _clvemployees_itemclick(int _index,Object _value) throws Exception{
 //BA.debugLineNum = 347;BA.debugLine="Sub clvEmployees_ItemClick (Index As Int, Value As";
 //BA.debugLineNum = 348;BA.debugLine="End Sub";
return "";
}
public static String  _clvemployees_visiblerangechanged(int _firstindex,int _lastindex) throws Exception{
int _extrasize = 0;
int _i = 0;
anywheresoftware.b4a.objects.B4XViewWrapper _pnl = null;
bwsi.registration.townhall.registration._employeeinfo _ei = null;
 //BA.debugLineNum = 288;BA.debugLine="Sub clvEmployees_VisibleRangeChanged (FirstIndex A";
 //BA.debugLineNum = 289;BA.debugLine="Dim ExtraSize As Int = 15 'List size";
_extrasize = (int) (15);
 //BA.debugLineNum = 290;BA.debugLine="clvEmployees.Refresh";
mostCurrent._clvemployees._refresh();
 //BA.debugLineNum = 292;BA.debugLine="For i = Max(0, FirstIndex - ExtraSize) To Min(Las";
{
final int step3 = 1;
final int limit3 = (int) (anywheresoftware.b4a.keywords.Common.Min(_lastindex+_extrasize,mostCurrent._clvemployees._getsize()-1));
_i = (int) (anywheresoftware.b4a.keywords.Common.Max(0,_firstindex-_extrasize)) ;
for (;_i <= limit3 ;_i = _i + step3 ) {
 //BA.debugLineNum = 293;BA.debugLine="Dim Pnl As B4XView = clvEmployees.GetPanel(i)";
_pnl = new anywheresoftware.b4a.objects.B4XViewWrapper();
_pnl = mostCurrent._clvemployees._getpanel(_i);
 //BA.debugLineNum = 294;BA.debugLine="If i > FirstIndex - ExtraSize And i < LastIndex";
if (_i>_firstindex-_extrasize && _i<_lastindex+_extrasize) { 
 //BA.debugLineNum = 295;BA.debugLine="If Pnl.NumberOfViews = 0 Then 'Add each item/la";
if (_pnl.getNumberOfViews()==0) { 
 //BA.debugLineNum = 296;BA.debugLine="Dim EI As EmployeeInfo = clvEmployees.GetValue";
_ei = (bwsi.registration.townhall.registration._employeeinfo)(mostCurrent._clvemployees._getvalue(_i));
 //BA.debugLineNum = 297;BA.debugLine="Pnl.LoadLayout(\"EmployeeList\")";
_pnl.LoadLayout("EmployeeList",mostCurrent.activityBA);
 //BA.debugLineNum = 299;BA.debugLine="lblBranch.Text = EI.BranchName";
mostCurrent._lblbranch.setText(BA.ObjectToCharSequence(_ei.BranchName /*String*/ ));
 //BA.debugLineNum = 300;BA.debugLine="lblAvatar.Text = EI.Avatar";
mostCurrent._lblavatar.setText(BA.ObjectToCharSequence(_ei.Avatar /*String*/ ));
 //BA.debugLineNum = 301;BA.debugLine="lblEmpName.Text = EI.EmpName";
mostCurrent._lblempname.setText(BA.ObjectToCharSequence(_ei.EmpName /*String*/ ));
 //BA.debugLineNum = 302;BA.debugLine="lblDivision.Text = EI.EmpDivision";
mostCurrent._lbldivision.setText(BA.ObjectToCharSequence(_ei.EmpDivision /*String*/ ));
 //BA.debugLineNum = 303;BA.debugLine="SetButtonColors";
_setbuttoncolors();
 //BA.debugLineNum = 305;BA.debugLine="AvatarBG.Color = ShadeColor(Rnd(0xFF59C6CC, 0x";
mostCurrent._avatarbg.setColor(_shadecolor(anywheresoftware.b4a.keywords.Common.Rnd((int) (0xff59c6cc),(int) (0xfff8d0cd))));
 //BA.debugLineNum = 307;BA.debugLine="If EI.WillAttend = 1 Then";
if (_ei.WillAttend /*int*/ ==1) { 
 //BA.debugLineNum = 308;BA.debugLine="If EI.RegStatus = 0 Then";
if (_ei.RegStatus /*int*/ ==0) { 
 //BA.debugLineNum = 309;BA.debugLine="lblStatus.Text = \"UNREGISTERED\"";
mostCurrent._lblstatus.setText(BA.ObjectToCharSequence("UNREGISTERED"));
 //BA.debugLineNum = 310;BA.debugLine="lblStatus.TextColor = GlobalVar.NegColor";
mostCurrent._lblstatus.setTextColor((int) (mostCurrent._globalvar._negcolor /*double*/ ));
 //BA.debugLineNum = 311;BA.debugLine="btnRegister.Enabled = True";
mostCurrent._btnregister.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 312;BA.debugLine="btnSwap.Enabled = False";
mostCurrent._btnswap.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 313;BA.debugLine="btnSwap.Text = \"PRINT STUB\"";
mostCurrent._btnswap.setText(BA.ObjectToCharSequence("PRINT STUB"));
 }else {
 //BA.debugLineNum = 315;BA.debugLine="lblStatus.Text = \"REGISTERED\"";
mostCurrent._lblstatus.setText(BA.ObjectToCharSequence("REGISTERED"));
 //BA.debugLineNum = 316;BA.debugLine="lblStatus.TextColor = GlobalVar.PriColor";
mostCurrent._lblstatus.setTextColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 317;BA.debugLine="btnRegister.Enabled = False";
mostCurrent._btnregister.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 318;BA.debugLine="btnSwap.Enabled = True";
mostCurrent._btnswap.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 319;BA.debugLine="btnSwap.Text = \"REPRINT STUB\"";
mostCurrent._btnswap.setText(BA.ObjectToCharSequence("REPRINT STUB"));
 };
 }else {
 //BA.debugLineNum = 322;BA.debugLine="If EI.RegStatus = 0 Then";
if (_ei.RegStatus /*int*/ ==0) { 
 //BA.debugLineNum = 323;BA.debugLine="lblStatus.Text = \"UNREGISTERED\"";
mostCurrent._lblstatus.setText(BA.ObjectToCharSequence("UNREGISTERED"));
 //BA.debugLineNum = 324;BA.debugLine="lblStatus.TextColor = GlobalVar.NegColor";
mostCurrent._lblstatus.setTextColor((int) (mostCurrent._globalvar._negcolor /*double*/ ));
 //BA.debugLineNum = 325;BA.debugLine="btnRegister.Enabled = True";
mostCurrent._btnregister.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 326;BA.debugLine="btnSwap.Enabled = True";
mostCurrent._btnswap.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 327;BA.debugLine="btnSwap.Text = \"SWAP DUTY\"";
mostCurrent._btnswap.setText(BA.ObjectToCharSequence("SWAP DUTY"));
 }else {
 //BA.debugLineNum = 329;BA.debugLine="lblStatus.Text = \"REGISTERED\"";
mostCurrent._lblstatus.setText(BA.ObjectToCharSequence("REGISTERED"));
 //BA.debugLineNum = 330;BA.debugLine="lblStatus.TextColor = GlobalVar.PriColor";
mostCurrent._lblstatus.setTextColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 331;BA.debugLine="btnRegister.Enabled = False";
mostCurrent._btnregister.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 332;BA.debugLine="btnSwap.Enabled = True";
mostCurrent._btnswap.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 333;BA.debugLine="btnSwap.Text = \"REPRINT STUB\"";
mostCurrent._btnswap.setText(BA.ObjectToCharSequence("REPRINT STUB"));
 };
 };
 };
 }else {
 //BA.debugLineNum = 339;BA.debugLine="If Pnl.NumberOfViews > 0 Then";
if (_pnl.getNumberOfViews()>0) { 
 //BA.debugLineNum = 340;BA.debugLine="Pnl.RemoveAllViews 'Remove none visable item/l";
_pnl.RemoveAllViews();
 };
 };
 }
};
 //BA.debugLineNum = 345;BA.debugLine="End Sub";
return "";
}
public static String  _confirmregister(String _sempname) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 523;BA.debugLine="Private Sub ConfirmRegister (sEmpName As String)";
 //BA.debugLineNum = 524;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 526;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(("CONFIRM REGISTRATION")).SetMessage(("Do you want to Register")+anywheresoftware.b4a.keywords.Common.CRLF+_sempname+("?")).SetPositiveText("YES").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetNegativeText("NO").SetNegativeColor((int) (mostCurrent._globalvar._negcolor /*double*/ )).SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"RegisterEmp").SetOnNegativeClicked(mostCurrent.activityBA,"RegisterEmp").SetOnViewBinder(mostCurrent.activityBA,"RegFontSizeBinder");
 //BA.debugLineNum = 544;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 545;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 547;BA.debugLine="End Sub";
return "";
}
public static String  _confirmreprint(String _sempname) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 567;BA.debugLine="Private Sub ConfirmReprint (sEmpName As String)";
 //BA.debugLineNum = 568;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 570;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(("CONFIRM REPRINT STUB")).SetMessage(("Do you want to Reprint")+anywheresoftware.b4a.keywords.Common.CRLF+_sempname+(" stub?")).SetPositiveText("YES").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetNegativeText("NO").SetNegativeColor((int) (mostCurrent._globalvar._negcolor /*double*/ )).SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"StubReprint").SetOnNegativeClicked(mostCurrent.activityBA,"StubReprint").SetOnViewBinder(mostCurrent.activityBA,"RegFontSizeBinder");
 //BA.debugLineNum = 588;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 589;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 591;BA.debugLine="End Sub";
return "";
}
public static String  _confirmswapemp(String _sempname) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 611;BA.debugLine="Private Sub ConfirmSwapEmp (sEmpName As String)";
 //BA.debugLineNum = 612;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 614;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(("CONFIRM SWAP DUTY")).SetMessage(("Confirm Swap Duty with")+anywheresoftware.b4a.keywords.Common.CRLF+_sempname+("?")).SetPositiveText("YES").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetNegativeText("NO").SetNegativeColor((int) (mostCurrent._globalvar._negcolor /*double*/ )).SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"SwapEmp").SetOnNegativeClicked(mostCurrent.activityBA,"SwapEmp").SetOnViewBinder(mostCurrent.activityBA,"RegFontSizeBinder");
 //BA.debugLineNum = 632;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 633;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 635;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.StateListDrawable  _createbuttoncolor(int _focusedcolor,int _enabledcolor,int _disabledcolor,int _pressedcolor) throws Exception{
anywheresoftware.b4a.objects.drawable.StateListDrawable _retcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwfocusedcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwenabledcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwdisabledcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwpressedcolor = null;
 //BA.debugLineNum = 432;BA.debugLine="Private Sub CreateButtonColor(FocusedColor As Int,";
 //BA.debugLineNum = 434;BA.debugLine="Dim RetColor As StateListDrawable";
_retcolor = new anywheresoftware.b4a.objects.drawable.StateListDrawable();
 //BA.debugLineNum = 435;BA.debugLine="RetColor.Initialize";
_retcolor.Initialize();
 //BA.debugLineNum = 436;BA.debugLine="Dim drwFocusedColor, drwEnabledColor, drwDisabled";
_drwfocusedcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_drwenabledcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_drwdisabledcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_drwpressedcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 438;BA.debugLine="drwFocusedColor.Initialize2(FocusedColor, 25, 0,";
_drwfocusedcolor.Initialize2(_focusedcolor,(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 439;BA.debugLine="drwEnabledColor.Initialize2(EnabledColor, 25, 0,";
_drwenabledcolor.Initialize2(_enabledcolor,(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 440;BA.debugLine="drwDisabledColor.Initialize2(DisabledColor, 25, 2";
_drwdisabledcolor.Initialize2(_disabledcolor,(int) (25),(int) (2),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 441;BA.debugLine="drwPressedColor.Initialize2(PressedColor, 25, 0,";
_drwpressedcolor.Initialize2(_pressedcolor,(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 443;BA.debugLine="RetColor.AddState(RetColor.State_Focused, drwFocu";
_retcolor.AddState(_retcolor.State_Focused,(android.graphics.drawable.Drawable)(_drwfocusedcolor.getObject()));
 //BA.debugLineNum = 444;BA.debugLine="RetColor.AddState(RetColor.State_Pressed, drwPres";
_retcolor.AddState(_retcolor.State_Pressed,(android.graphics.drawable.Drawable)(_drwpressedcolor.getObject()));
 //BA.debugLineNum = 445;BA.debugLine="RetColor.AddState(RetColor.State_Enabled, drwEnab";
_retcolor.AddState(_retcolor.State_Enabled,(android.graphics.drawable.Drawable)(_drwenabledcolor.getObject()));
 //BA.debugLineNum = 446;BA.debugLine="RetColor.AddState(RetColor.State_Disabled, drwDis";
_retcolor.AddState(_retcolor.State_Disabled,(android.graphics.drawable.Drawable)(_drwdisabledcolor.getObject()));
 //BA.debugLineNum = 447;BA.debugLine="RetColor.AddCatchAllState(drwFocusedColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwfocusedcolor.getObject()));
 //BA.debugLineNum = 448;BA.debugLine="RetColor.AddCatchAllState(drwEnabledColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwenabledcolor.getObject()));
 //BA.debugLineNum = 449;BA.debugLine="RetColor.AddCatchAllState(drwDisabledColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwdisabledcolor.getObject()));
 //BA.debugLineNum = 450;BA.debugLine="RetColor.AddCatchAllState(drwPressedColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwpressedcolor.getObject()));
 //BA.debugLineNum = 451;BA.debugLine="Return RetColor";
if (true) return _retcolor;
 //BA.debugLineNum = 453;BA.debugLine="End Sub";
return null;
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _createscaledbitmap(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _original,int _newwidth,int _newheight) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _b = null;
 //BA.debugLineNum = 1024;BA.debugLine="Sub CreateScaledBitmap(Original As Bitmap, NewWidt";
 //BA.debugLineNum = 1025;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 1026;BA.debugLine="Dim b As Bitmap";
_b = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 1027;BA.debugLine="b = r.RunStaticMethod(\"android.graphics.Bitmap\",";
_b = (anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(_r.RunStaticMethod("android.graphics.Bitmap","createScaledBitmap",new Object[]{(Object)(_original.getObject()),(Object)(_newwidth),(Object)(_newheight),(Object)(anywheresoftware.b4a.keywords.Common.True)},new String[]{"android.graphics.Bitmap","java.lang.int","java.lang.int","java.lang.boolean"})));
 //BA.debugLineNum = 1028;BA.debugLine="Return b";
if (true) return _b;
 //BA.debugLineNum = 1029;BA.debugLine="End Sub";
return null;
}
public static void  _fillemployess(int _iareaid) throws Exception{
ResumableSub_FillEmployess rsub = new ResumableSub_FillEmployess(null,_iareaid);
rsub.resume(processBA, null);
}
public static class ResumableSub_FillEmployess extends BA.ResumableSub {
public ResumableSub_FillEmployess(bwsi.registration.townhall.registration parent,int _iareaid) {
this.parent = parent;
this._iareaid = _iareaid;
}
bwsi.registration.townhall.registration parent;
int _iareaid;
Object _senderfilter = null;
boolean _success = false;
anywheresoftware.b4a.sql.SQL.ResultSetWrapper _rs = null;
long _starttime = 0L;
bwsi.registration.townhall.registration._employeeinfo _empinfo = null;
anywheresoftware.b4a.objects.B4XViewWrapper _pnl = null;

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
 //BA.debugLineNum = 231;BA.debugLine="Dim SenderFilter As Object";
_senderfilter = new Object();
 //BA.debugLineNum = 233;BA.debugLine="Try";
if (true) break;

case 1:
//try
this.state = 23;
this.catchState = 22;
this.state = 3;
if (true) break;

case 3:
//C
this.state = 4;
this.catchState = 22;
 //BA.debugLineNum = 234;BA.debugLine="Starter.strCriteria = \"SELECT Employees.BranchID";
parent.mostCurrent._starter._strcriteria /*String*/  = "SELECT Employees.BranchID, Branches.AreaID, Branches.BranchName, "+"Employees.RegID, Employees.RegNo, "+"Employees.LastName, Employees.FirstName, Employees.MiddleName, Employees.Suffixed, "+"Employees.FullName, Divisions.DivisionName, "+"Employees.WillAttend, Employees.WasRegistered "+"FROM tblRegistration AS Employees "+"INNER JOIN tblBranches AS Branches ON Employees.BranchID = Branches.BranchID "+"INNER JOIN tblDivisions AS Divisions ON Employees.DivisionID = Divisions.DivisionID "+"WHERE Branches.AreaID = "+BA.NumberToString(_iareaid)+" "+"AND Employees.WillAttend <> 0 "+"ORDER BY Branches.BranchID ASC, Divisions.DivisionID ASC, Employees.FullName ASC, Employees.RegID ASC";
 //BA.debugLineNum = 246;BA.debugLine="LogColor(Starter.strCriteria, Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("46881296",parent.mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 248;BA.debugLine="SenderFilter = Starter.DBCon.ExecQueryAsync(\"SQL";
_senderfilter = parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQueryAsync(processBA,"SQL",parent.mostCurrent._starter._strcriteria /*String*/ ,(anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(anywheresoftware.b4a.keywords.Common.Null)));
 //BA.debugLineNum = 249;BA.debugLine="Wait For (SenderFilter) SQL_QueryComplete (Succe";
anywheresoftware.b4a.keywords.Common.WaitFor("sql_querycomplete", processBA, this, _senderfilter);
this.state = 24;
return;
case 24:
//C
this.state = 4;
_success = (Boolean) result[0];
_rs = (anywheresoftware.b4a.sql.SQL.ResultSetWrapper) result[1];
;
 //BA.debugLineNum = 250;BA.debugLine="If Success Then";
if (true) break;

case 4:
//if
this.state = 20;
if (_success) { 
this.state = 6;
}else {
this.state = 19;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 251;BA.debugLine="Dim StartTime As Long = DateTime.Now";
_starttime = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 252;BA.debugLine="clvEmployees.Clear";
parent.mostCurrent._clvemployees._clear();
 //BA.debugLineNum = 253;BA.debugLine="Do While RS.NextRow";
if (true) break;

case 7:
//do while
this.state = 10;
while (_rs.NextRow()) {
this.state = 9;
if (true) break;
}
if (true) break;

case 9:
//C
this.state = 7;
 //BA.debugLineNum = 254;BA.debugLine="Dim EmpInfo As EmployeeInfo";
_empinfo = new bwsi.registration.townhall.registration._employeeinfo();
 //BA.debugLineNum = 255;BA.debugLine="EmpInfo.Initialize";
_empinfo.Initialize();
 //BA.debugLineNum = 256;BA.debugLine="EmpInfo.RegID = RS.GetInt(\"RegID\")";
_empinfo.RegID /*int*/  = _rs.GetInt("RegID");
 //BA.debugLineNum = 257;BA.debugLine="EmpInfo.RegNo = RS.GetString(\"RegNo\")";
_empinfo.RegNo /*String*/  = _rs.GetString("RegNo");
 //BA.debugLineNum = 258;BA.debugLine="EmpInfo.BranchID = RS.GetInt(\"BranchID\")";
_empinfo.BranchID /*int*/  = _rs.GetInt("BranchID");
 //BA.debugLineNum = 259;BA.debugLine="EmpInfo.BranchName = RS.GetString(\"BranchName\"";
_empinfo.BranchName /*String*/  = _rs.GetString("BranchName");
 //BA.debugLineNum = 260;BA.debugLine="EmpInfo.EmpName = RS.GetString(\"FullName\")";
_empinfo.EmpName /*String*/  = _rs.GetString("FullName");
 //BA.debugLineNum = 261;BA.debugLine="EmpInfo.EmpDivision = RS.GetString(\"DivisionNa";
_empinfo.EmpDivision /*String*/  = _rs.GetString("DivisionName");
 //BA.debugLineNum = 262;BA.debugLine="EmpInfo.Avatar = GlobalVar.SF.Upper(GlobalVar.";
_empinfo.Avatar /*String*/  = parent.mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(parent.mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv6(_rs.GetString("FirstName"),(long) (1)))+parent.mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(parent.mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv6(_rs.GetString("LastName"),(long) (1)));
 //BA.debugLineNum = 263;BA.debugLine="EmpInfo.WillAttend = RS.GetInt(\"WillAttend\")";
_empinfo.WillAttend /*int*/  = _rs.GetInt("WillAttend");
 //BA.debugLineNum = 264;BA.debugLine="EmpInfo.RegStatus = RS.GetInt(\"WasRegistered\")";
_empinfo.RegStatus /*int*/  = _rs.GetInt("WasRegistered");
 //BA.debugLineNum = 266;BA.debugLine="Dim Pnl As B4XView = xui.CreatePanel(\"\")";
_pnl = new anywheresoftware.b4a.objects.B4XViewWrapper();
_pnl = parent._xui.CreatePanel(processBA,"");
 //BA.debugLineNum = 267;BA.debugLine="Pnl.SetLayoutAnimated(0, 10dip, 0, clvEmployee";
_pnl.SetLayoutAnimated((int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (0),parent.mostCurrent._clvemployees._asview().getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170)));
 //BA.debugLineNum = 268;BA.debugLine="clvEmployees.Add(Pnl, EmpInfo)";
parent.mostCurrent._clvemployees._add(_pnl,(Object)(_empinfo));
 if (true) break;
;
 //BA.debugLineNum = 270;BA.debugLine="If RS.RowCount <= 0 Then";

case 10:
//if
this.state = 17;
if (_rs.getRowCount()<=0) { 
this.state = 12;
}else if(_rs.getRowCount()==1) { 
this.state = 14;
}else {
this.state = 16;
}if (true) break;

case 12:
//C
this.state = 17;
 //BA.debugLineNum = 271;BA.debugLine="lblRecCount.Text = $\"No Employee Found\"$";
parent.mostCurrent._lblreccount.setText(BA.ObjectToCharSequence(("No Employee Found")));
 if (true) break;

case 14:
//C
this.state = 17;
 //BA.debugLineNum = 273;BA.debugLine="lblRecCount.Text = RS.RowCount & $\" Employee F";
parent.mostCurrent._lblreccount.setText(BA.ObjectToCharSequence(BA.NumberToString(_rs.getRowCount())+(" Employee Found")));
 if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 275;BA.debugLine="lblRecCount.Text = RS.RowCount & $\" Employees";
parent.mostCurrent._lblreccount.setText(BA.ObjectToCharSequence(BA.NumberToString(_rs.getRowCount())+(" Employees Found")));
 if (true) break;

case 17:
//C
this.state = 20;
;
 if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 278;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("46881328",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;

case 20:
//C
this.state = 23;
;
 //BA.debugLineNum = 281;BA.debugLine="Log($\"List of Employees Records = ${NumberFormat";
anywheresoftware.b4a.keywords.Common.LogImpl("46881331",("List of Employees Records = "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(anywheresoftware.b4a.keywords.Common.NumberFormat2((anywheresoftware.b4a.keywords.Common.DateTime.getNow()-_starttime)/(double)1000,(int) (0),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False)))+" seconds to populate "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(parent.mostCurrent._clvemployees._getsize()))+" Employees Records"),0);
 if (true) break;

case 22:
//C
this.state = 23;
this.catchState = 0;
 //BA.debugLineNum = 283;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("46881333",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 23:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 286;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 418;BA.debugLine="Sub GetARGB(Color As Int) As Int()";
 //BA.debugLineNum = 419;BA.debugLine="Private Rets(4) As Int";
_rets = new int[(int) (4)];
;
 //BA.debugLineNum = 420;BA.debugLine="Rets(0) = Bit.UnsignedShiftRight(Bit.And(Color, 0";
_rets[(int) (0)] = anywheresoftware.b4a.keywords.Common.Bit.UnsignedShiftRight(anywheresoftware.b4a.keywords.Common.Bit.And(_color,(int) (0xff000000)),(int) (24));
 //BA.debugLineNum = 421;BA.debugLine="Rets(1) = Bit.UnsignedShiftRight(Bit.And(Color, 0";
_rets[(int) (1)] = anywheresoftware.b4a.keywords.Common.Bit.UnsignedShiftRight(anywheresoftware.b4a.keywords.Common.Bit.And(_color,(int) (0xff0000)),(int) (16));
 //BA.debugLineNum = 422;BA.debugLine="Rets(2) = Bit.UnsignedShiftRight(Bit.And(Color, 0";
_rets[(int) (2)] = anywheresoftware.b4a.keywords.Common.Bit.UnsignedShiftRight(anywheresoftware.b4a.keywords.Common.Bit.And(_color,(int) (0xff00)),(int) (8));
 //BA.debugLineNum = 423;BA.debugLine="Rets(3) = Bit.And(Color, 0xff)";
_rets[(int) (3)] = anywheresoftware.b4a.keywords.Common.Bit.And(_color,(int) (0xff));
 //BA.debugLineNum = 424;BA.debugLine="Return Rets";
if (true) return _rets;
 //BA.debugLineNum = 425;BA.debugLine="End Sub";
return null;
}
public static String  _getemployeename(int _iempid) throws Exception{
String _sretval = "";
 //BA.debugLineNum = 1033;BA.debugLine="Private Sub GetEmployeeName(iEmpID As Int) As Stri";
 //BA.debugLineNum = 1034;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 1035;BA.debugLine="Try";
try { //BA.debugLineNum = 1036;BA.debugLine="sRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_sretval = mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT FullName FROM tblRegistration WHERE RegID = "+BA.NumberToString(_iempid));
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 1038;BA.debugLine="sRetVal = \"\"";
_sretval = "";
 //BA.debugLineNum = 1039;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("49043974",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1041;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 1042;BA.debugLine="End Sub";
return "";
}
public static int  _getnoofstubprint(int _iregid) throws Exception{
int _iretval = 0;
 //BA.debugLineNum = 1115;BA.debugLine="Private Sub GetNoOfStubPrint(iRegID As Int) As Int";
 //BA.debugLineNum = 1116;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 1117;BA.debugLine="Try";
try { //BA.debugLineNum = 1118;BA.debugLine="iRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_iretval = (int)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT NoPrintStub FROM tblRegistration WHERE RegID = "+BA.NumberToString(_iregid))));
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 1120;BA.debugLine="iRetVal = 0";
_iretval = (int) (0);
 //BA.debugLineNum = 1121;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("49240582",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1123;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 1124;BA.debugLine="End Sub";
return 0;
}
public static int  _getregstatus(int _iregid) throws Exception{
int _iretval = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsstatus = null;
 //BA.debugLineNum = 1126;BA.debugLine="Private Sub GetRegStatus(iRegID As Int) As Int";
 //BA.debugLineNum = 1127;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 1128;BA.debugLine="Dim rsStatus As Cursor";
_rsstatus = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 1129;BA.debugLine="Try";
try { //BA.debugLineNum = 1130;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblRegistra";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblRegistration WHERE RegID = "+BA.NumberToString(_iregid);
 //BA.debugLineNum = 1131;BA.debugLine="rsStatus = Starter.DBCon.ExecQuery(Starter.strCr";
_rsstatus = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 1132;BA.debugLine="If rsStatus.RowCount > 0 Then";
if (_rsstatus.getRowCount()>0) { 
 //BA.debugLineNum = 1133;BA.debugLine="rsStatus.Position = 0";
_rsstatus.setPosition((int) (0));
 //BA.debugLineNum = 1134;BA.debugLine="If rsStatus.GetInt(\"WasRegistered\") = 1 Then 'R";
if (_rsstatus.GetInt("WasRegistered")==1) { 
 //BA.debugLineNum = 1135;BA.debugLine="iRetVal = 1 'Reprint Stub";
_iretval = (int) (1);
 }else {
 //BA.debugLineNum = 1137;BA.debugLine="iRetVal = 2 'Do Nothing";
_iretval = (int) (2);
 };
 };
 } 
       catch (Exception e15) {
			processBA.setLastException(e15); //BA.debugLineNum = 1141;BA.debugLine="iRetVal = 0";
_iretval = (int) (0);
 //BA.debugLineNum = 1142;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("49306128",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1144;BA.debugLine="rsStatus.Close";
_rsstatus.Close();
 //BA.debugLineNum = 1145;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 1146;BA.debugLine="End Sub";
return 0;
}
public static String  _getswapemployeename(int _iempid) throws Exception{
String _sretval = "";
 //BA.debugLineNum = 1193;BA.debugLine="Private Sub GetSwapEmployeeName(iEmpID As Int) As";
 //BA.debugLineNum = 1194;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 1195;BA.debugLine="Try";
try { //BA.debugLineNum = 1196;BA.debugLine="sRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_sretval = mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT FullName FROM tblRegistration WHERE RegID = "+BA.NumberToString(_iempid));
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 1198;BA.debugLine="sRetVal = \"\"";
_sretval = "";
 //BA.debugLineNum = 1199;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("49437190",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1201;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 1202;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 56;BA.debugLine="Dim ActionBarButton As ACActionBar";
mostCurrent._actionbarbutton = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 57;BA.debugLine="Private ToolBar As ACToolBarDark";
mostCurrent._toolbar = new de.amberhome.objects.appcompat.ACToolbarDarkWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private xmlIcon As XmlLayoutBuilder";
mostCurrent._xmlicon = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 60;BA.debugLine="Private rsBranch As Cursor";
mostCurrent._rsbranch = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private rsEmployees As Cursor";
mostCurrent._rsemployees = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Type EmployeeInfo (RegID As Int, RegNo As String,";
;
 //BA.debugLineNum = 64;BA.debugLine="Private clvEmployees As CustomListView";
mostCurrent._clvemployees = new b4a.example3.customlistview();
 //BA.debugLineNum = 66;BA.debugLine="Private txtSearch As EditText";
mostCurrent._txtsearch = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private RegID As Int";
_regid = 0;
 //BA.debugLineNum = 69;BA.debugLine="Private sRegName As String";
mostCurrent._sregname = "";
 //BA.debugLineNum = 70;BA.debugLine="Private MyList As List";
mostCurrent._mylist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 74;BA.debugLine="Private TitleHeight As Int = 30dip";
_titleheight = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30));
 //BA.debugLineNum = 75;BA.debugLine="Private DividerHeight As Int = 5dip";
_dividerheight = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5));
 //BA.debugLineNum = 77;BA.debugLine="Private CDTxtBox As ColorDrawable";
mostCurrent._cdtxtbox = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 79;BA.debugLine="Private blnNewReg As Boolean";
_blnnewreg = false;
 //BA.debugLineNum = 81;BA.debugLine="Private btnCancel As ACButton";
mostCurrent._btncancel = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Private lblSearchTitle As Label";
mostCurrent._lblsearchtitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 83;BA.debugLine="Private pnlSearchEmployees As Panel";
mostCurrent._pnlsearchemployees = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 84;BA.debugLine="Private pnlSearchMain As Panel";
mostCurrent._pnlsearchmain = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 85;BA.debugLine="Dim iSwapBranchID As Int";
_iswapbranchid = 0;
 //BA.debugLineNum = 86;BA.debugLine="Dim iSwapEmpID As Int";
_iswapempid = 0;
 //BA.debugLineNum = 87;BA.debugLine="Dim sSwapEmp As String";
mostCurrent._sswapemp = "";
 //BA.debugLineNum = 88;BA.debugLine="Private SV As SearchView";
mostCurrent._sv = new bwsi.registration.townhall.searchview();
 //BA.debugLineNum = 90;BA.debugLine="Private AvatarBG As Panel";
mostCurrent._avatarbg = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 91;BA.debugLine="Private lblAvatar As Label";
mostCurrent._lblavatar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 92;BA.debugLine="Private lblBranch As Label";
mostCurrent._lblbranch = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 93;BA.debugLine="Private lblDivision As Label";
mostCurrent._lbldivision = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 94;BA.debugLine="Private lblEmpName As Label";
mostCurrent._lblempname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 95;BA.debugLine="Private lblStatus As Label";
mostCurrent._lblstatus = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 97;BA.debugLine="Private btnRegister As ACButton";
mostCurrent._btnregister = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 98;BA.debugLine="Private btnSwap As ACButton";
mostCurrent._btnswap = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 100;BA.debugLine="Private lblRecCount As Label";
mostCurrent._lblreccount = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 101;BA.debugLine="Private IMEKeyboard As IME";
mostCurrent._imekeyboard = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 104;BA.debugLine="Dim ESC As String = Chr(27)";
mostCurrent._esc = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)));
 //BA.debugLineNum = 105;BA.debugLine="Dim FS As String = Chr(28)";
mostCurrent._fs = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (28)));
 //BA.debugLineNum = 106;BA.debugLine="Dim GS As String = Chr(29)";
mostCurrent._gs = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (29)));
 //BA.debugLineNum = 109;BA.debugLine="Dim UNREVERSE As String  = GS & \"B\" & Chr(0)";
mostCurrent._unreverse = mostCurrent._gs+"B"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)));
 //BA.debugLineNum = 110;BA.debugLine="Dim REVERSE As String = GS & \"B\" & Chr(1)";
mostCurrent._reverse = mostCurrent._gs+"B"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)));
 //BA.debugLineNum = 113;BA.debugLine="Dim UNINVERT As String = ESC & \"{0\"";
mostCurrent._uninvert = mostCurrent._esc+"{0";
 //BA.debugLineNum = 114;BA.debugLine="Dim INVERT As String = ESC & \"{1\"";
mostCurrent._invert = mostCurrent._esc+"{1";
 //BA.debugLineNum = 117;BA.debugLine="Dim UNROTATE As String = ESC & \"V0\"";
mostCurrent._unrotate = mostCurrent._esc+"V0";
 //BA.debugLineNum = 118;BA.debugLine="Dim ROTATE As String = ESC & \"V1\"";
mostCurrent._rotate = mostCurrent._esc+"V1";
 //BA.debugLineNum = 121;BA.debugLine="Dim HT As String = Chr(9)";
mostCurrent._ht = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (9)));
 //BA.debugLineNum = 124;BA.debugLine="Dim ULINE0 As String = ESC & \"-0\"";
mostCurrent._uline0 = mostCurrent._esc+"-0";
 //BA.debugLineNum = 125;BA.debugLine="Dim ULINE1 As String = ESC & \"-1\"";
mostCurrent._uline1 = mostCurrent._esc+"-1";
 //BA.debugLineNum = 126;BA.debugLine="Dim ULINE2 As String = ESC & \"-2\"";
mostCurrent._uline2 = mostCurrent._esc+"-2";
 //BA.debugLineNum = 129;BA.debugLine="Dim BOLD As String = ESC & \"E1\"";
mostCurrent._bold = mostCurrent._esc+"E1";
 //BA.debugLineNum = 130;BA.debugLine="Dim NOBOLD As String = ESC & \"E0\"";
mostCurrent._nobold = mostCurrent._esc+"E0";
 //BA.debugLineNum = 133;BA.debugLine="Dim SINGLE As String = GS & \"!\" & Chr(0x00)";
mostCurrent._single = mostCurrent._gs+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x00)));
 //BA.debugLineNum = 134;BA.debugLine="Dim HIGH As String = GS & \"!\" & Chr(0x01)";
mostCurrent._high = mostCurrent._gs+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x01)));
 //BA.debugLineNum = 135;BA.debugLine="Dim WIDE As String = GS & \"!\" & Chr(0x10)";
mostCurrent._wide = mostCurrent._gs+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x10)));
 //BA.debugLineNum = 136;BA.debugLine="Dim HIGHWIDE As String = GS & \"!\" & Chr(0x11)";
mostCurrent._highwide = mostCurrent._gs+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x11)));
 //BA.debugLineNum = 139;BA.debugLine="Private LEFTJUSTIFY As String = ESC & \"a0\"";
mostCurrent._leftjustify = mostCurrent._esc+"a0";
 //BA.debugLineNum = 140;BA.debugLine="Private LINEDEFAULT As String = ESC & \"2\"";
mostCurrent._linedefault = mostCurrent._esc+"2";
 //BA.debugLineNum = 141;BA.debugLine="Private LINSET0 As String = ESC & \"$\" & Chr(0x0)";
mostCurrent._linset0 = mostCurrent._esc+"$"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x0)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x0)));
 //BA.debugLineNum = 142;BA.debugLine="Private LMARGIN0 As String = GS & \"L\" & Chr(0x0)";
mostCurrent._lmargin0 = mostCurrent._gs+"L"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x0)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0x0)));
 //BA.debugLineNum = 143;BA.debugLine="Private WIDTH0 As String = GS & \"W\" & Chr(0xff) &";
mostCurrent._width0 = mostCurrent._gs+"W"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xff)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xff)));
 //BA.debugLineNum = 144;BA.debugLine="Private CHARSPACING0 As String = ESC & \" \" & Chr(";
mostCurrent._charspacing0 = mostCurrent._esc+" "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)));
 //BA.debugLineNum = 145;BA.debugLine="Private CHARFONT0 As String = ESC & \"M\" & Chr(0)";
mostCurrent._charfont0 = mostCurrent._esc+"M"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)));
 //BA.debugLineNum = 146;BA.debugLine="Dim DEFAULTS As String =  CHARSPACING0 & CHARFONT";
mostCurrent._defaults = mostCurrent._charspacing0+mostCurrent._charfont0+mostCurrent._lmargin0+mostCurrent._width0+mostCurrent._linset0+mostCurrent._linedefault+mostCurrent._leftjustify+mostCurrent._uninvert+mostCurrent._unrotate+mostCurrent._unreverse+mostCurrent._nobold+mostCurrent._uline0;
 //BA.debugLineNum = 149;BA.debugLine="End Sub";
return "";
}
public static String  _pnlsearchmain_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 1245;BA.debugLine="Sub pnlSearchMain_Touch (Action As Int, X As Float";
 //BA.debugLineNum = 1247;BA.debugLine="End Sub";
return "";
}
public static void  _printer_connected(boolean _success) throws Exception{
ResumableSub_Printer_Connected rsub = new ResumableSub_Printer_Connected(null,_success);
rsub.resume(processBA, null);
}
public static class ResumableSub_Printer_Connected extends BA.ResumableSub {
public ResumableSub_Printer_Connected(bwsi.registration.townhall.registration parent,boolean _success) {
this.parent = parent;
this._success = _success;
}
bwsi.registration.townhall.registration parent;
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
 //BA.debugLineNum = 977;BA.debugLine="Log(\"Connected: \" & Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("48912897","Connected: "+BA.ObjectToString(_success),0);
 //BA.debugLineNum = 979;BA.debugLine="If Success = False Then";
if (true) break;

case 1:
//if
this.state = 12;
if (_success==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 12;
 //BA.debugLineNum = 980;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 984;BA.debugLine="StartPrinter";
_startprinter();
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 987;BA.debugLine="Dim initPrinter() As Byte";
_initprinter = new byte[(int) (0)];
;
 //BA.debugLineNum = 989;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 990;BA.debugLine="TMPrinter.Initialize2(Serial1.OutputStream, \"win";
parent._tmprinter.Initialize2(parent._serial1.getOutputStream(),"windows-1252");
 //BA.debugLineNum = 991;BA.debugLine="oStream.Initialize(Serial1.InputStream, Serial1.";
parent._ostream.Initialize(processBA,parent._serial1.getInputStream(),parent._serial1.getOutputStream(),"LogoPrint");
 //BA.debugLineNum = 992;BA.debugLine="Logo.Initialize(File.DirAssets, \"Stub-Header-Tow";
parent._logo.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Stub-Header-Townhall.png");
 //BA.debugLineNum = 993;BA.debugLine="LogoBMP = CreateScaledBitmap(Logo, Logo.Width, L";
parent._logobmp = _createscaledbitmap(parent._logo,parent._logo.getWidth(),parent._logo.getHeight());
 //BA.debugLineNum = 994;BA.debugLine="Log(DeviceName)";
anywheresoftware.b4a.keywords.Common.LogImpl("48912914",parent._devicename,0);
 //BA.debugLineNum = 996;BA.debugLine="WoosimCMD.InitializeStatic(\"com.woosim.printer.W";
parent._woosimcmd.InitializeStatic("com.woosim.printer.WoosimCmd");
 //BA.debugLineNum = 997;BA.debugLine="WoosimImage.InitializeStatic(\"com.woosim.printer";
parent._woosimimage.InitializeStatic("com.woosim.printer.WoosimImage");
 //BA.debugLineNum = 999;BA.debugLine="initPrinter = WoosimCMD.RunMethod(\"initPrinter\",";
_initprinter = (byte[])(parent._woosimcmd.RunMethod("initPrinter",(Object[])(anywheresoftware.b4a.keywords.Common.Null)));
 //BA.debugLineNum = 1000;BA.debugLine="PrintLogo = WoosimImage.RunMethod(\"printBitmap\",";
parent._printlogo = (byte[])(parent._woosimimage.RunMethod("printBitmap",new Object[]{(Object)(0),(Object)(0),(Object)(420),(Object)(220),(Object)(parent._logobmp.getObject())}));
 //BA.debugLineNum = 1002;BA.debugLine="oStream.Write(initPrinter)";
parent._ostream.Write(_initprinter);
 //BA.debugLineNum = 1003;BA.debugLine="oStream.Write(WoosimCMD.RunMethod(\"setPageMode\",";
parent._ostream.Write((byte[])(parent._woosimcmd.RunMethod("setPageMode",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 1004;BA.debugLine="oStream.Write(PrintLogo)";
parent._ostream.Write(parent._printlogo);
 //BA.debugLineNum = 1005;BA.debugLine="oStream.Write(WoosimCMD.RunMethod(\"PM_setStdMode";
parent._ostream.Write((byte[])(parent._woosimcmd.RunMethod("PM_setStdMode",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 1006;BA.debugLine="oStream.Write(PrintLogo)";
parent._ostream.Write(parent._printlogo);
 //BA.debugLineNum = 1008;BA.debugLine="Sleep(500)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (500));
this.state = 13;
return;
case 13:
//C
this.state = 6;
;
 //BA.debugLineNum = 1009;BA.debugLine="TMPrinter.WriteLine(PrintBuffer)";
parent._tmprinter.WriteLine(parent._printbuffer);
 //BA.debugLineNum = 1010;BA.debugLine="Log(PrintBuffer)";
anywheresoftware.b4a.keywords.Common.LogImpl("48912930",parent._printbuffer,0);
 //BA.debugLineNum = 1011;BA.debugLine="TMPrinter.Flush";
parent._tmprinter.Flush();
 //BA.debugLineNum = 1012;BA.debugLine="Sleep(600)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (600));
this.state = 14;
return;
case 14:
//C
this.state = 6;
;
 //BA.debugLineNum = 1013;BA.debugLine="If blnNewReg = True Then";
if (true) break;

case 6:
//if
this.state = 11;
if (parent._blnnewreg==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 8;
}else {
this.state = 10;
}if (true) break;

case 8:
//C
this.state = 11;
 //BA.debugLineNum = 1014;BA.debugLine="ShowSuccessMsg($\"SUCCESS\"$, $\"Stub was successf";
_showsuccessmsg(("SUCCESS"),("Stub was successfully printed.")+anywheresoftware.b4a.keywords.Common.CRLF+("Tap OK to Continue..."));
 if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 1016;BA.debugLine="ShowSuccessMsg($\"REPRINT SUCCESS\"$, $\"Stub was";
_showsuccessmsg(("REPRINT SUCCESS"),("Stub was successfully reprinted.")+anywheresoftware.b4a.keywords.Common.CRLF+("Tap OK to Continue..."));
 if (true) break;

case 11:
//C
this.state = 12;
;
 //BA.debugLineNum = 1019;BA.debugLine="TMPrinter.Close";
parent._tmprinter.Close();
 //BA.debugLineNum = 1020;BA.debugLine="Serial1.Disconnect";
parent._serial1.Disconnect();
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 1022;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _printerbinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 698;BA.debugLine="Private Sub PrinterBinder_OnBindView (View As View";
 //BA.debugLineNum = 699;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 700;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 701;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 702;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 703;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 704;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf071)))+"  "));
 //BA.debugLineNum = 705;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 707;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 710;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 711;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 712;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 713;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 715;BA.debugLine="End Sub";
return "";
}
public static String  _printererror_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 693;BA.debugLine="Private Sub PrinterError_OnPositiveClicked (View A";
 //BA.debugLineNum = 694;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 695;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 696;BA.debugLine="End Sub";
return "";
}
public static String  _printstub(int _iregid) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsdata = null;
String _stubno = "";
String _regfullname = "";
String _regbranchname = "";
String _regdivision = "";
int _regtableno = 0;
int _attendstatus = 0;
 //BA.debugLineNum = 817;BA.debugLine="Private Sub PrintStub(iRegID As Int)";
 //BA.debugLineNum = 818;BA.debugLine="Dim rsData As Cursor";
_rsdata = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 819;BA.debugLine="Dim StubNo As String";
_stubno = "";
 //BA.debugLineNum = 820;BA.debugLine="Dim RegFullName As String";
_regfullname = "";
 //BA.debugLineNum = 821;BA.debugLine="Dim RegBranchName As String";
_regbranchname = "";
 //BA.debugLineNum = 822;BA.debugLine="Dim RegDivision As String";
_regdivision = "";
 //BA.debugLineNum = 823;BA.debugLine="Dim RegTableNo As Int";
_regtableno = 0;
 //BA.debugLineNum = 824;BA.debugLine="Dim AttendStatus As Int";
_attendstatus = 0;
 //BA.debugLineNum = 826;BA.debugLine="ProgressDialogShow2($\"Stub Printing.  Please Wait";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Stub Printing.  Please Wait...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 828;BA.debugLine="Try";
try { //BA.debugLineNum = 829;BA.debugLine="Starter.strCriteria = \"SELECT Employees.RegNo, E";
mostCurrent._starter._strcriteria /*String*/  = "SELECT Employees.RegNo, Employees.FullName, "+"Branches.BranchName, Divisions.DivisionName, Employees.TableNo, "+"Employees.WillAttend AS AttendStatus "+"FROM tblRegistration AS Employees "+"INNER JOIN tblBranches AS Branches ON Employees.BranchID = Branches.BranchID "+"INNER JOIN tblDivisions AS Divisions ON Employees.DivisionID = Divisions.DivisionID "+"WHERE Employees.RegID = "+BA.NumberToString(_iregid)+" "+"AND Employees.WasRegistered = 1";
 //BA.debugLineNum = 838;BA.debugLine="rsData = Starter.DBCon.ExecQuery(Starter.strCrit";
_rsdata = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 839;BA.debugLine="LogColor(Starter.strCriteria, Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("48781846",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 841;BA.debugLine="If rsData.RowCount > 0 Then";
if (_rsdata.getRowCount()>0) { 
 //BA.debugLineNum = 842;BA.debugLine="rsData.Position = 0";
_rsdata.setPosition((int) (0));
 //BA.debugLineNum = 843;BA.debugLine="StubNo = rsData.GetString(\"RegNo\")";
_stubno = _rsdata.GetString("RegNo");
 //BA.debugLineNum = 844;BA.debugLine="RegFullName = rsData.GetString(\"FullName\")";
_regfullname = _rsdata.GetString("FullName");
 //BA.debugLineNum = 845;BA.debugLine="RegBranchName = rsData.GetString(\"BranchName\")";
_regbranchname = _rsdata.GetString("BranchName");
 //BA.debugLineNum = 846;BA.debugLine="RegDivision = rsData.GetString(\"DivisionName\")";
_regdivision = _rsdata.GetString("DivisionName");
 //BA.debugLineNum = 847;BA.debugLine="RegTableNo = rsData.GetInt(\"TableNo\")";
_regtableno = _rsdata.GetInt("TableNo");
 //BA.debugLineNum = 848;BA.debugLine="AttendStatus = rsData.GetInt(\"AttendStatus\")";
_attendstatus = _rsdata.GetInt("AttendStatus");
 }else {
 //BA.debugLineNum = 850;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 853;BA.debugLine="If AttendStatus = 1 Then 'Attendees";
if (_attendstatus==1) { 
 //BA.debugLineNum = 854;BA.debugLine="PrintBuffer =  ESC & \"@\" _ 						& ESC & Chr(97";
_printbuffer = mostCurrent._esc+"@"+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("STUB NO.: ")+mostCurrent._bold+_stubno+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._esc+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._esc+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regdivision+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._high+mostCurrent._reverse+("                  ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._highwide+mostCurrent._unreverse+("TABLE NO.: ")+BA.NumberToString(_regtableno)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+("Sit back, Listen & Learn!")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("DINNER STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._esc+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._esc+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regdivision+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._high+mostCurrent._reverse+("                  ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._highwide+mostCurrent._unreverse+("TABLE NO.: ")+BA.NumberToString(_regtableno)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("LUNCH STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._esc+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._esc+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regdivision+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._high+mostCurrent._reverse+("                  ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._highwide+mostCurrent._unreverse+("TABLE NO.: ")+BA.NumberToString(_regtableno)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("RAFFLE STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._esc+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._esc+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regdivision+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._high+mostCurrent._reverse+("                  ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._highwide+mostCurrent._unreverse+("TABLE NO.: ")+BA.NumberToString(_regtableno)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (73)));
 }else if(_attendstatus==2) { 
 //BA.debugLineNum = 898;BA.debugLine="PrintBuffer =  ESC & \"@\" _ 						& ESC & Chr(97";
_printbuffer = mostCurrent._esc+"@"+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("STUB NO.: ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._esc+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._esc+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regdivision+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+("See you on the next Townhall!")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("GIVE AWAY STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._esc+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._esc+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regdivision+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+mostCurrent._esc+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (73)));
 };
 //BA.debugLineNum = 918;BA.debugLine="StartPrinter";
_startprinter();
 } 
       catch (Exception e31) {
			processBA.setLastException(e31); //BA.debugLineNum = 920;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 921;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("48781928",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 923;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public static String  _regfontsizebinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 652;BA.debugLine="Private Sub RegFontSizeBinder_OnBindView (View As";
 //BA.debugLineNum = 653;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 654;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 655;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 656;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 657;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 658;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf059)))+"  "));
 //BA.debugLineNum = 659;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 661;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 664;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 665;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 666;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 667;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 669;BA.debugLine="End Sub";
return "";
}
public static boolean  _registeremp(int _iregid) throws Exception{
boolean _bretval = false;
long _lngdatetime = 0L;
String _timereg = "";
int _regseq = 0;
int _noprint = 0;
 //BA.debugLineNum = 1044;BA.debugLine="Private Sub RegisterEmp(iRegID As Int) As Boolean";
 //BA.debugLineNum = 1045;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 1046;BA.debugLine="Dim lngDateTime As Long";
_lngdatetime = 0L;
 //BA.debugLineNum = 1047;BA.debugLine="Dim TimeReg As String";
_timereg = "";
 //BA.debugLineNum = 1048;BA.debugLine="Dim RegSeq, NoPrint As Int";
_regseq = 0;
_noprint = 0;
 //BA.debugLineNum = 1050;BA.debugLine="lngDateTime = DateTime.Now";
_lngdatetime = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 1051;BA.debugLine="DateTime.TimeFormat = \"HH:mm:ss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("HH:mm:ss");
 //BA.debugLineNum = 1052;BA.debugLine="TimeReg = DateTime.Time(lngDateTime)";
_timereg = anywheresoftware.b4a.keywords.Common.DateTime.Time(_lngdatetime);
 //BA.debugLineNum = 1053;BA.debugLine="RegSeq = DBFunctions.GetSeqNo";
_regseq = mostCurrent._dbfunctions._getseqno /*int*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 1055;BA.debugLine="NoPrint = GetNoOfStubPrint(iRegID)";
_noprint = _getnoofstubprint(_iregid);
 //BA.debugLineNum = 1056;BA.debugLine="NoPrint = NoPrint + 1";
_noprint = (int) (_noprint+1);
 //BA.debugLineNum = 1057;BA.debugLine="GlobalVar.AssignedEmp = DBFunctions.GetAssignedEm";
mostCurrent._globalvar._assignedemp /*String*/  = mostCurrent._dbfunctions._getassignedemp /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 1059;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 1060;BA.debugLine="Try";
try { //BA.debugLineNum = 1061;BA.debugLine="Starter.strCriteria = \"UPDATE tblRegistration \"";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblRegistration "+"SET WasRegistered = ?, TimeRegistered = ?, "+"RegSeq = ?, WasStubPrint = ?, NoPrintStub = ?, RegisteredBy = ? "+"WHERE RegID = "+BA.NumberToString(_iregid);
 //BA.debugLineNum = 1066;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{("1"),_timereg,BA.NumberToString(_regseq),("1"),BA.NumberToString(_noprint),mostCurrent._globalvar._assignedemp /*String*/ }));
 //BA.debugLineNum = 1069;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 1070;BA.debugLine="ProgressDialogShow2($\"Preparing Stub Printing\"$,";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Preparing Stub Printing")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1071;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e20) {
			processBA.setLastException(e20); //BA.debugLineNum = 1073;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1074;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1075;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("49109535",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1077;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 1078;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 1079;BA.debugLine="End Sub";
return false;
}
public static String  _registeremp_onnegativeclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 550;BA.debugLine="Private Sub RegisterEmp_OnNegativeClicked (View As";
 //BA.debugLineNum = 551;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 552;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 553;BA.debugLine="ToastMessageShow($\"Canceled!\"$, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Canceled!")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 554;BA.debugLine="End Sub";
return "";
}
public static String  _registeremp_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 556;BA.debugLine="Private Sub RegisterEmp_OnPositiveClicked (View As";
 //BA.debugLineNum = 557;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 558;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 559;BA.debugLine="blnNewReg = True";
_blnnewreg = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 561;BA.debugLine="If Not(RegisterEmp(RegID)) Then Return";
if (anywheresoftware.b4a.keywords.Common.Not(_registeremp(_regid))) { 
if (true) return "";};
 //BA.debugLineNum = 562;BA.debugLine="PrintStub(RegID)";
_printstub(_regid);
 //BA.debugLineNum = 563;BA.debugLine="ToastMessageShow($\"Registered!\"$, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Registered!")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 564;BA.debugLine="End Sub";
return "";
}
public static String  _searchclosed() throws Exception{
 //BA.debugLineNum = 1253;BA.debugLine="Sub SearchClosed";
 //BA.debugLineNum = 1254;BA.debugLine="SV.ClearSearchBox";
mostCurrent._sv._clearsearchbox /*String*/ ();
 //BA.debugLineNum = 1255;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 1256;BA.debugLine="pnlSearchMain.Visible = False";
mostCurrent._pnlsearchmain.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1257;BA.debugLine="End Sub";
return "";
}
public static String  _searchswapemployees(int _ibranchid) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsswapemployees = null;
anywheresoftware.b4a.objects.collections.List _searchlist = null;
int _i = 0;
bwsi.registration.townhall.searchview._item _it = null;
 //BA.debugLineNum = 1148;BA.debugLine="Private Sub SearchSwapEmployees(iBranchID As Int)";
 //BA.debugLineNum = 1149;BA.debugLine="Dim rsSwapEmployees As Cursor";
_rsswapemployees = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 1150;BA.debugLine="Dim SearchList As List";
_searchlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1152;BA.debugLine="If SV.IsInitialized=False Then";
if (mostCurrent._sv.IsInitialized /*boolean*/ ()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1153;BA.debugLine="SV.Initialize(Me,\"SV\")";
mostCurrent._sv._initialize /*String*/ (mostCurrent.activityBA,registration.getObject(),"SV");
 };
 //BA.debugLineNum = 1156;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 1157;BA.debugLine="SV.ClearSearchBox";
mostCurrent._sv._clearsearchbox /*String*/ ();
 //BA.debugLineNum = 1158;BA.debugLine="SV.lv.Clear";
mostCurrent._sv._lv /*anywheresoftware.b4a.objects.ListViewWrapper*/ .Clear();
 //BA.debugLineNum = 1160;BA.debugLine="SearchList.Initialize";
_searchlist.Initialize();
 //BA.debugLineNum = 1161;BA.debugLine="SearchList.Clear";
_searchlist.Clear();
 //BA.debugLineNum = 1162;BA.debugLine="Try";
try { //BA.debugLineNum = 1163;BA.debugLine="Starter.strCriteria = \"SELECT tblRegistration.Re";
mostCurrent._starter._strcriteria /*String*/  = "SELECT tblRegistration.RegID, tblRegistration.FullName, tblBranches.BranchName "+"FROM tblRegistration INNER JOIN tblBranches ON tblRegistration.BranchID = tblBranches.BranchID "+"WHERE tblRegistration.WillAttend = 1 "+"AND tblRegistration.WasRegistered = 0 "+"AND tblRegistration.BranchID = "+BA.NumberToString(_ibranchid);
 //BA.debugLineNum = 1169;BA.debugLine="LogColor(Starter.strCriteria, Colors.Cyan)";
anywheresoftware.b4a.keywords.Common.LogImpl("49371669",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 1171;BA.debugLine="rsSwapEmployees = Starter.DBCon.ExecQuery(Starte";
_rsswapemployees = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 1173;BA.debugLine="If rsSwapEmployees.RowCount > 0 Then";
if (_rsswapemployees.getRowCount()>0) { 
 //BA.debugLineNum = 1174;BA.debugLine="For i = 0 To rsSwapEmployees.RowCount - 1";
{
final int step16 = 1;
final int limit16 = (int) (_rsswapemployees.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit16 ;_i = _i + step16 ) {
 //BA.debugLineNum = 1175;BA.debugLine="rsSwapEmployees.Position = i";
_rsswapemployees.setPosition(_i);
 //BA.debugLineNum = 1176;BA.debugLine="Dim it As Item";
_it = new bwsi.registration.townhall.searchview._item();
 //BA.debugLineNum = 1177;BA.debugLine="it.Title=rsSwapEmployees.GetString(\"FullName\")";
_it.Title /*String*/  = _rsswapemployees.GetString("FullName");
 //BA.debugLineNum = 1178;BA.debugLine="it.Text=rsSwapEmployees.GetString(\"BranchName\"";
_it.Text /*String*/  = _rsswapemployees.GetString("BranchName");
 //BA.debugLineNum = 1179;BA.debugLine="it.SearchText=rsSwapEmployees.GetString(\"FullN";
_it.SearchText /*String*/  = _rsswapemployees.GetString("FullName").toLowerCase();
 //BA.debugLineNum = 1180;BA.debugLine="it.Value=rsSwapEmployees.GetInt(\"RegID\")";
_it.Value /*Object*/  = (Object)(_rsswapemployees.GetInt("RegID"));
 //BA.debugLineNum = 1181;BA.debugLine="SearchList.Add(it)";
_searchlist.Add((Object)(_it));
 }
};
 }else {
 //BA.debugLineNum = 1184;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1186;BA.debugLine="SV.SetItems(SearchList)";
mostCurrent._sv._setitems /*Object*/ (_searchlist);
 //BA.debugLineNum = 1187;BA.debugLine="SearchList.Clear";
_searchlist.Clear();
 } 
       catch (Exception e31) {
			processBA.setLastException(e31); //BA.debugLineNum = 1189;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("49371689",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1191;BA.debugLine="End Sub";
return "";
}
public static String  _setbuttoncolors() throws Exception{
 //BA.debugLineNum = 427;BA.debugLine="Private Sub SetButtonColors()";
 //BA.debugLineNum = 428;BA.debugLine="btnSwap.Background = CreateButtonColor(0xFF0D47A1";
mostCurrent._btnswap.setBackground((android.graphics.drawable.Drawable)(_createbuttoncolor((int) (0xff0d47a1),(int) (0xff0d47a1),(int) (0xff1e88e5),(int) (0xff0d47a1)).getObject()));
 //BA.debugLineNum = 429;BA.debugLine="btnRegister.Background = CreateButtonColor(0xFF0D";
mostCurrent._btnregister.setBackground((android.graphics.drawable.Drawable)(_createbuttoncolor((int) (0xff0d47a1),(int) (0xff0d47a1),(int) (0xff1e88e5),(int) (0xff0d47a1)).getObject()));
 //BA.debugLineNum = 430;BA.debugLine="End Sub";
return "";
}
public static int  _shadecolor(int _clr) throws Exception{
int[] _argb = null;
float _factor = 0f;
 //BA.debugLineNum = 412;BA.debugLine="Sub ShadeColor(clr As Int) As Int";
 //BA.debugLineNum = 413;BA.debugLine="Dim argb() As Int = GetARGB(clr)";
_argb = _getargb(_clr);
 //BA.debugLineNum = 414;BA.debugLine="Dim factor As Float = 3";
_factor = (float) (3);
 //BA.debugLineNum = 415;BA.debugLine="Return xui.Color_RGB(argb(1) * factor, argb(2) *";
if (true) return _xui.Color_RGB((int) (_argb[(int) (1)]*_factor),(int) (_argb[(int) (2)]*_factor),(int) (_argb[(int) (3)]*_factor));
 //BA.debugLineNum = 416;BA.debugLine="End Sub";
return 0;
}
public static String  _showaddperson() throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.collections.List _items = null;
 //BA.debugLineNum = 766;BA.debugLine="Private Sub ShowAddPerson";
 //BA.debugLineNum = 767;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 769;BA.debugLine="Dim items As List";
_items = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 770;BA.debugLine="items.Initialize";
_items.Initialize();
 //BA.debugLineNum = 771;BA.debugLine="items.Add(\"New Employee\")";
_items.Add((Object)("New Employee"));
 //BA.debugLineNum = 772;BA.debugLine="items.Add(\"Guest\")";
_items.Add((Object)("Guest"));
 //BA.debugLineNum = 774;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialog").SetStyle(_alert.getSTYLE_ACTIONSHEET()).SetTitle("Select an Option").SetTitleColor(anywheresoftware.b4a.keywords.Common.Colors.Black).SetCancelText("Cancel").SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOthers((java.util.ArrayList)(_items.getObject())).SetActionsheetTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnCancelClicked(mostCurrent.activityBA,"AddPeople").SetOnItemClickListener(mostCurrent.activityBA,"AddPeople");
 //BA.debugLineNum = 788;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD).SetCanc";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject())).SetCancelBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 789;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 790;BA.debugLine="End Sub";
return "";
}
public static String  _showprintererror(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 671;BA.debugLine="Private Sub ShowPrinterError(sTitle As String, sMs";
 //BA.debugLineNum = 672;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 674;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetMessage(_smsg).SetPositiveText("OK").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"PrinterError").SetOnViewBinder(mostCurrent.activityBA,"PrinterBinder");
 //BA.debugLineNum = 688;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 689;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 691;BA.debugLine="End Sub";
return "";
}
public static String  _showsuccessmsg(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 717;BA.debugLine="Private Sub ShowSuccessMsg(sTitle As String, sMsg";
 //BA.debugLineNum = 718;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 720;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetMessage(_smsg).SetPositiveText("OK").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"SuccessMsg").SetOnViewBinder(mostCurrent.activityBA,"SuccessBinder");
 //BA.debugLineNum = 734;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 735;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 737;BA.debugLine="End Sub";
return "";
}
public static String  _startprinter() throws Exception{
int _i = 0;
 //BA.debugLineNum = 925;BA.debugLine="Sub StartPrinter";
 //BA.debugLineNum = 927;BA.debugLine="PairedDevices.Initialize";
_paireddevices.Initialize();
 //BA.debugLineNum = 929;BA.debugLine="Try";
try { //BA.debugLineNum = 930;BA.debugLine="PairedDevices = Serial1.GetPairedDevices";
_paireddevices = _serial1.GetPairedDevices();
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 932;BA.debugLine="ShowPrinterError($\"PRINTER ERROR\"$, $\"Getting Pa";
_showprintererror(("PRINTER ERROR"),("Getting Paired Devices..."));
 //BA.debugLineNum = 933;BA.debugLine="TMPrinter.Close";
_tmprinter.Close();
 //BA.debugLineNum = 934;BA.debugLine="Serial1.Disconnect";
_serial1.Disconnect();
 };
 //BA.debugLineNum = 937;BA.debugLine="If PairedDevices.Size = 0 Then";
if (_paireddevices.getSize()==0) { 
 //BA.debugLineNum = 938;BA.debugLine="ShowPrinterError($\"PRINTER ERROR\"$, $\"Error Conn";
_showprintererror(("PRINTER ERROR"),("Error Connecting to Printer - Either No Paired Bluetooth Printer or Printer Not Found!"));
 //BA.debugLineNum = 939;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 942;BA.debugLine="If PairedDevices.Size = 1 Then";
if (_paireddevices.getSize()==1) { 
 //BA.debugLineNum = 943;BA.debugLine="Try";
try { //BA.debugLineNum = 944;BA.debugLine="DeviceName=PairedDevices.Getkeyat(0)";
_devicename = BA.ObjectToString(_paireddevices.GetKeyAt((int) (0)));
 //BA.debugLineNum = 945;BA.debugLine="DeviceMac=PairedDevices.GetValueAt(0)";
_devicemac = BA.ObjectToString(_paireddevices.GetValueAt((int) (0)));
 //BA.debugLineNum = 946;BA.debugLine="Log(DeviceName & \" -> \" & DeviceMac)";
anywheresoftware.b4a.keywords.Common.LogImpl("48847381",_devicename+" -> "+_devicemac,0);
 //BA.debugLineNum = 948;BA.debugLine="Serial1.ConnectInsecure(BTAdmin, DeviceMac,1)";
_serial1.ConnectInsecure(processBA,_btadmin,_devicemac,(int) (1));
 //BA.debugLineNum = 949;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 } 
       catch (Exception e21) {
			processBA.setLastException(e21); //BA.debugLineNum = 951;BA.debugLine="ShowPrinterError($\"PRINTER ERROR\"$, $\"Printer C";
_showprintererror(("PRINTER ERROR"),("Printer Connection Error..."));
 //BA.debugLineNum = 952;BA.debugLine="TMPrinter.Close";
_tmprinter.Close();
 //BA.debugLineNum = 953;BA.debugLine="Serial1.Disconnect";
_serial1.Disconnect();
 };
 }else {
 //BA.debugLineNum = 956;BA.debugLine="FoundDevices.Initialize";
_founddevices.Initialize();
 //BA.debugLineNum = 958;BA.debugLine="For i = 0 To PairedDevices.Size - 1";
{
final int step27 = 1;
final int limit27 = (int) (_paireddevices.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit27 ;_i = _i + step27 ) {
 //BA.debugLineNum = 959;BA.debugLine="FoundDevices.Add(PairedDevices.GetKeyAt(i))";
_founddevices.Add(_paireddevices.GetKeyAt(_i));
 //BA.debugLineNum = 960;BA.debugLine="DeviceName=PairedDevices.Getkeyat(i)";
_devicename = BA.ObjectToString(_paireddevices.GetKeyAt(_i));
 //BA.debugLineNum = 961;BA.debugLine="DeviceMac=PairedDevices.GetValueAt(i)";
_devicemac = BA.ObjectToString(_paireddevices.GetValueAt(_i));
 //BA.debugLineNum = 962;BA.debugLine="Log(DeviceName & \" -> \" & DeviceMac)";
anywheresoftware.b4a.keywords.Common.LogImpl("48847397",_devicename+" -> "+_devicemac,0);
 //BA.debugLineNum = 963;BA.debugLine="Serial1.ConnectInsecure(BTAdmin, DeviceMac,1)";
_serial1.ConnectInsecure(processBA,_btadmin,_devicemac,(int) (1));
 //BA.debugLineNum = 964;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 965;BA.debugLine="Exit";
if (true) break;
 }
};
 //BA.debugLineNum = 968;BA.debugLine="Res = InputList(FoundDevices, \"Choose Device\", -";
_res = anywheresoftware.b4a.keywords.Common.InputList(_founddevices,BA.ObjectToCharSequence("Choose Device"),(int) (-1),mostCurrent.activityBA);
 //BA.debugLineNum = 970;BA.debugLine="If Res <> DialogResponse.CANCEL Then";
if (_res!=anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 971;BA.debugLine="Serial1.Connect(PairedDevices.Get(FoundDevices.";
_serial1.Connect(processBA,BA.ObjectToString(_paireddevices.Get(_founddevices.Get(_res))));
 };
 };
 //BA.debugLineNum = 974;BA.debugLine="End Sub";
return "";
}
public static String  _stubreprint_onnegativeclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 593;BA.debugLine="Private Sub StubReprint_OnNegativeClicked (View As";
 //BA.debugLineNum = 594;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 595;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 596;BA.debugLine="ToastMessageShow($\"Reprinting Cancelled!\"$, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Reprinting Cancelled!")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 597;BA.debugLine="End Sub";
return "";
}
public static String  _stubreprint_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 599;BA.debugLine="Private Sub StubReprint_OnPositiveClicked (View As";
 //BA.debugLineNum = 600;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 601;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 603;BA.debugLine="blnNewReg = False";
_blnnewreg = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 605;BA.debugLine="If Not(UpdateReprintStub(RegID)) Then Return";
if (anywheresoftware.b4a.keywords.Common.Not(_updatereprintstub(_regid))) { 
if (true) return "";};
 //BA.debugLineNum = 606;BA.debugLine="PrintStub(RegID)";
_printstub(_regid);
 //BA.debugLineNum = 607;BA.debugLine="ToastMessageShow($\"Reprinted!\"$, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Reprinted!")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 608;BA.debugLine="End Sub";
return "";
}
public static String  _successbinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 747;BA.debugLine="Private Sub SuccessBinder_OnBindView (View As View";
 //BA.debugLineNum = 748;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 749;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 750;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 751;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 752;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 753;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color((int) (mostCurrent._globalvar._poscolor /*double*/ )).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf164)))+"  "));
 //BA.debugLineNum = 754;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 756;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 759;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 760;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 761;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 762;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 764;BA.debugLine="End Sub";
return "";
}
public static String  _successmsg_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 739;BA.debugLine="Private Sub SuccessMsg_OnPositiveClicked (View As";
 //BA.debugLineNum = 740;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 741;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 742;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtSearch.T";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtsearch.getText()))>0) { 
mostCurrent._txtsearch.setText(BA.ObjectToCharSequence(""));};
 //BA.debugLineNum = 743;BA.debugLine="FillEmployess(GlobalVar.AreaID)";
_fillemployess(mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 745;BA.debugLine="End Sub";
return "";
}
public static String  _sv_itemclick(int _value) throws Exception{
 //BA.debugLineNum = 1206;BA.debugLine="Sub SV_ItemClick(Value As Int)";
 //BA.debugLineNum = 1207;BA.debugLine="Log(Value)";
anywheresoftware.b4a.keywords.Common.LogImpl("49502721",BA.NumberToString(_value),0);
 //BA.debugLineNum = 1208;BA.debugLine="iSwapEmpID = Value";
_iswapempid = _value;
 //BA.debugLineNum = 1209;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 1210;BA.debugLine="SearchClosed";
_searchclosed();
 //BA.debugLineNum = 1211;BA.debugLine="sSwapEmp = GetSwapEmployeeName(iSwapEmpID)";
mostCurrent._sswapemp = _getswapemployeename(_iswapempid);
 //BA.debugLineNum = 1212;BA.debugLine="If sSwapEmp = \"\" Then Return";
if ((mostCurrent._sswapemp).equals("")) { 
if (true) return "";};
 //BA.debugLineNum = 1213;BA.debugLine="ConfirmSwapEmp(sSwapEmp)";
_confirmswapemp(mostCurrent._sswapemp);
 //BA.debugLineNum = 1214;BA.debugLine="End Sub";
return "";
}
public static String  _swapemp_onnegativeclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 637;BA.debugLine="Private Sub SwapEmp_OnNegativeClicked (View As Vie";
 //BA.debugLineNum = 638;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 639;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 640;BA.debugLine="ToastMessageShow($\"Canceled!\"$, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Canceled!")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 641;BA.debugLine="End Sub";
return "";
}
public static String  _swapemp_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 643;BA.debugLine="Private Sub SwapEmp_OnPositiveClicked (View As Vie";
 //BA.debugLineNum = 644;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 645;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 646;BA.debugLine="If UpdateSwappedEmp(iSwapEmpID, RegID) = False Th";
if (_updateswappedemp(_iswapempid,_regid)==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 647;BA.debugLine="If RegisterEmp(RegID) = False Then Return";
if (_registeremp(_regid)==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 648;BA.debugLine="PrintStub(RegID)";
_printstub(_regid);
 //BA.debugLineNum = 649;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_menuitemclick(de.amberhome.objects.appcompat.ACMenuItemWrapper _item) throws Exception{
 //BA.debugLineNum = 222;BA.debugLine="Sub ToolBar_MenuItemClick (Item As ACMenuItem)";
 //BA.debugLineNum = 223;BA.debugLine="Select Case Item.Id";
switch (BA.switchObjectToInt(_item.getId(),(int) (1))) {
case 0: {
 //BA.debugLineNum = 225;BA.debugLine="ShowAddPerson";
_showaddperson();
 break; }
}
;
 //BA.debugLineNum = 227;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 212;BA.debugLine="Sub ToolBar_NavigationItemClick";
 //BA.debugLineNum = 213;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtSearch.T";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtsearch.getText()))<=0) { 
 //BA.debugLineNum = 214;BA.debugLine="IMEKeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 215;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else {
 //BA.debugLineNum = 217;BA.debugLine="txtSearch.Text = \"\"";
mostCurrent._txtsearch.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 218;BA.debugLine="IMEKeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 };
 //BA.debugLineNum = 220;BA.debugLine="End Sub";
return "";
}
public static void  _txtsearch_textchanged(String _old,String _new) throws Exception{
ResumableSub_txtSearch_TextChanged rsub = new ResumableSub_txtSearch_TextChanged(null,_old,_new);
rsub.resume(processBA, null);
}
public static class ResumableSub_txtSearch_TextChanged extends BA.ResumableSub {
public ResumableSub_txtSearch_TextChanged(bwsi.registration.townhall.registration parent,String _old,String _new) {
this.parent = parent;
this._old = _old;
this._new = _new;
}
bwsi.registration.townhall.registration parent;
String _old;
String _new;
Object _senderfilter = null;
boolean _success = false;
anywheresoftware.b4a.sql.SQL.ResultSetWrapper _rs = null;
long _starttime = 0L;
bwsi.registration.townhall.registration._employeeinfo _empinfo = null;
anywheresoftware.b4a.objects.B4XViewWrapper _pnl = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 457;BA.debugLine="If New.Length = 1 Or txtSearch.Text.Length = 2 Th";
if (true) break;

case 1:
//if
this.state = 6;
if (_new.length()==1 || parent.mostCurrent._txtsearch.getText().length()==2) { 
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
 //BA.debugLineNum = 459;BA.debugLine="clvEmployees.Clear";
parent.mostCurrent._clvemployees._clear();
 //BA.debugLineNum = 460;BA.debugLine="Sleep(0)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (0));
this.state = 30;
return;
case 30:
//C
this.state = 7;
;
 //BA.debugLineNum = 462;BA.debugLine="Dim SenderFilter As Object";
_senderfilter = new Object();
 //BA.debugLineNum = 464;BA.debugLine="If New.Length = 0  Then";
if (true) break;

case 7:
//if
this.state = 12;
if (_new.length()==0) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 465;BA.debugLine="FillEmployess(GlobalVar.AreaID)";
_fillemployess(parent.mostCurrent._globalvar._areaid /*int*/ );
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 467;BA.debugLine="Starter.strCriteria = \"SELECT Employees.BranchID";
parent.mostCurrent._starter._strcriteria /*String*/  = "SELECT Employees.BranchID, Branches.AreaID, Branches.BranchName, "+"Employees.RegID, Employees.RegNo, "+"Employees.LastName, Employees.FirstName, Employees.MiddleName, Employees.Suffixed, "+"Employees.FullName, Divisions.DivisionName, "+"Employees.WillAttend, Employees.WasRegistered "+"FROM tblRegistration AS Employees "+"INNER JOIN tblBranches AS Branches ON Employees.BranchID = Branches.BranchID "+"INNER JOIN tblDivisions AS Divisions ON Employees.DivisionID = Divisions.DivisionID "+"WHERE Branches.AreaID = "+BA.NumberToString(parent.mostCurrent._globalvar._areaid /*int*/ )+" "+"AND Employees.WillAttend <> 0 "+"AND (Employees.FullName LIKE '%"+_new+"%' "+"OR Branches.BranchName LIKE '%"+_new+"%') "+"ORDER BY Branches.BranchID ASC, Divisions.DivisionID ASC, Employees.FullName ASC, Employees.RegID ASC";
 if (true) break;

case 12:
//C
this.state = 13;
;
 //BA.debugLineNum = 483;BA.debugLine="LogColor(Starter.strCriteria, Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("47471131",parent.mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 484;BA.debugLine="SenderFilter = Starter.DBCon.ExecQueryAsync(\"SQL\"";
_senderfilter = parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQueryAsync(processBA,"SQL",parent.mostCurrent._starter._strcriteria /*String*/ ,(anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(anywheresoftware.b4a.keywords.Common.Null)));
 //BA.debugLineNum = 486;BA.debugLine="Wait For (SenderFilter) SQL_QueryComplete (Succes";
anywheresoftware.b4a.keywords.Common.WaitFor("sql_querycomplete", processBA, this, _senderfilter);
this.state = 31;
return;
case 31:
//C
this.state = 13;
_success = (Boolean) result[0];
_rs = (anywheresoftware.b4a.sql.SQL.ResultSetWrapper) result[1];
;
 //BA.debugLineNum = 487;BA.debugLine="If Success Then";
if (true) break;

case 13:
//if
this.state = 29;
if (_success) { 
this.state = 15;
}else {
this.state = 28;
}if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 488;BA.debugLine="Dim StartTime As Long = DateTime.Now";
_starttime = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 489;BA.debugLine="clvEmployees.Clear";
parent.mostCurrent._clvemployees._clear();
 //BA.debugLineNum = 490;BA.debugLine="Do While RS.NextRow";
if (true) break;

case 16:
//do while
this.state = 19;
while (_rs.NextRow()) {
this.state = 18;
if (true) break;
}
if (true) break;

case 18:
//C
this.state = 16;
 //BA.debugLineNum = 491;BA.debugLine="Dim EmpInfo As EmployeeInfo";
_empinfo = new bwsi.registration.townhall.registration._employeeinfo();
 //BA.debugLineNum = 492;BA.debugLine="EmpInfo.Initialize";
_empinfo.Initialize();
 //BA.debugLineNum = 493;BA.debugLine="EmpInfo.RegID = RS.GetInt(\"RegID\")";
_empinfo.RegID /*int*/  = _rs.GetInt("RegID");
 //BA.debugLineNum = 494;BA.debugLine="EmpInfo.RegNo = RS.GetString(\"RegNo\")";
_empinfo.RegNo /*String*/  = _rs.GetString("RegNo");
 //BA.debugLineNum = 495;BA.debugLine="EmpInfo.BranchID = RS.GetInt(\"BranchID\")";
_empinfo.BranchID /*int*/  = _rs.GetInt("BranchID");
 //BA.debugLineNum = 496;BA.debugLine="EmpInfo.BranchName = RS.GetString(\"BranchName\")";
_empinfo.BranchName /*String*/  = _rs.GetString("BranchName");
 //BA.debugLineNum = 497;BA.debugLine="EmpInfo.EmpName = RS.GetString(\"FullName\")";
_empinfo.EmpName /*String*/  = _rs.GetString("FullName");
 //BA.debugLineNum = 498;BA.debugLine="EmpInfo.EmpDivision = RS.GetString(\"DivisionNam";
_empinfo.EmpDivision /*String*/  = _rs.GetString("DivisionName");
 //BA.debugLineNum = 499;BA.debugLine="EmpInfo.Avatar = GlobalVar.SF.Upper(GlobalVar.S";
_empinfo.Avatar /*String*/  = parent.mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(parent.mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv6(_rs.GetString("FirstName"),(long) (1)))+parent.mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(parent.mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv6(_rs.GetString("LastName"),(long) (1)));
 //BA.debugLineNum = 500;BA.debugLine="EmpInfo.WillAttend = RS.GetInt(\"WillAttend\")";
_empinfo.WillAttend /*int*/  = _rs.GetInt("WillAttend");
 //BA.debugLineNum = 501;BA.debugLine="EmpInfo.RegStatus = RS.GetInt(\"WasRegistered\")";
_empinfo.RegStatus /*int*/  = _rs.GetInt("WasRegistered");
 //BA.debugLineNum = 503;BA.debugLine="Dim Pnl As B4XView = xui.CreatePanel(\"\")";
_pnl = new anywheresoftware.b4a.objects.B4XViewWrapper();
_pnl = parent._xui.CreatePanel(processBA,"");
 //BA.debugLineNum = 504;BA.debugLine="Pnl.SetLayoutAnimated(0, 10dip, 0, clvEmployees";
_pnl.SetLayoutAnimated((int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (0),parent.mostCurrent._clvemployees._asview().getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (180)));
 //BA.debugLineNum = 505;BA.debugLine="clvEmployees.Add(Pnl, EmpInfo)";
parent.mostCurrent._clvemployees._add(_pnl,(Object)(_empinfo));
 if (true) break;
;
 //BA.debugLineNum = 507;BA.debugLine="If RS.RowCount <= 0 Then";

case 19:
//if
this.state = 26;
if (_rs.getRowCount()<=0) { 
this.state = 21;
}else if(_rs.getRowCount()==1) { 
this.state = 23;
}else {
this.state = 25;
}if (true) break;

case 21:
//C
this.state = 26;
 //BA.debugLineNum = 508;BA.debugLine="lblRecCount.Text = $\"No Employee Found\"$";
parent.mostCurrent._lblreccount.setText(BA.ObjectToCharSequence(("No Employee Found")));
 if (true) break;

case 23:
//C
this.state = 26;
 //BA.debugLineNum = 510;BA.debugLine="lblRecCount.Text = RS.RowCount & $\" Employee Fo";
parent.mostCurrent._lblreccount.setText(BA.ObjectToCharSequence(BA.NumberToString(_rs.getRowCount())+(" Employee Found")));
 if (true) break;

case 25:
//C
this.state = 26;
 //BA.debugLineNum = 512;BA.debugLine="lblRecCount.Text = RS.RowCount & $\" Employees F";
parent.mostCurrent._lblreccount.setText(BA.ObjectToCharSequence(BA.NumberToString(_rs.getRowCount())+(" Employees Found")));
 if (true) break;

case 26:
//C
this.state = 29;
;
 if (true) break;

case 28:
//C
this.state = 29;
 //BA.debugLineNum = 515;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("47471163",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;

case 29:
//C
this.state = -1;
;
 //BA.debugLineNum = 518;BA.debugLine="Log($\"List of Employees Records = ${NumberFormat2";
anywheresoftware.b4a.keywords.Common.LogImpl("47471166",("List of Employees Records = "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(anywheresoftware.b4a.keywords.Common.NumberFormat2((anywheresoftware.b4a.keywords.Common.DateTime.getNow()-_starttime)/(double)1000,(int) (0),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False)))+" seconds to populate "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(parent.mostCurrent._clvemployees._getsize()))+" Employees Records"),0);
 //BA.debugLineNum = 520;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static boolean  _updatereprintstub(int _iregid) throws Exception{
boolean _bretval = false;
int _noprint = 0;
String _reprinttime = "";
long _lngdatetime = 0L;
 //BA.debugLineNum = 1081;BA.debugLine="Private Sub UpdateReprintStub(iRegID As Int) As Bo";
 //BA.debugLineNum = 1082;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 1083;BA.debugLine="Dim NoPrint As Int";
_noprint = 0;
 //BA.debugLineNum = 1084;BA.debugLine="Dim ReprintTime As String";
_reprinttime = "";
 //BA.debugLineNum = 1085;BA.debugLine="Dim lngDateTime As Long";
_lngdatetime = 0L;
 //BA.debugLineNum = 1087;BA.debugLine="lngDateTime = DateTime.Now";
_lngdatetime = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 1088;BA.debugLine="DateTime.TimeFormat = \"HH:mm:ss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("HH:mm:ss");
 //BA.debugLineNum = 1089;BA.debugLine="ReprintTime = DateTime.Time(lngDateTime)";
_reprinttime = anywheresoftware.b4a.keywords.Common.DateTime.Time(_lngdatetime);
 //BA.debugLineNum = 1092;BA.debugLine="NoPrint = GetNoOfStubPrint(iRegID)";
_noprint = _getnoofstubprint(_iregid);
 //BA.debugLineNum = 1093;BA.debugLine="NoPrint = NoPrint + 1";
_noprint = (int) (_noprint+1);
 //BA.debugLineNum = 1095;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 1096;BA.debugLine="Try";
try { //BA.debugLineNum = 1097;BA.debugLine="Starter.strCriteria = \"UPDATE tblRegistration \"";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblRegistration "+"SET NoPrintStub = ?, LastReprintedTime = ?  "+"WHERE RegID = "+BA.NumberToString(_iregid);
 //BA.debugLineNum = 1101;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{BA.NumberToString(_noprint),_reprinttime}));
 //BA.debugLineNum = 1103;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 1104;BA.debugLine="ProgressDialogShow2($\"Preparing Stub Printing\"$,";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Preparing Stub Printing")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1105;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e18) {
			processBA.setLastException(e18); //BA.debugLineNum = 1107;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1108;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1109;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("49175068",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1111;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 1112;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 1113;BA.debugLine="End Sub";
return false;
}
public static boolean  _updateswappedemp(int _iswapemp,int _iregid) throws Exception{
boolean _bretval = false;
 //BA.debugLineNum = 1217;BA.debugLine="Private Sub UpdateSwappedEmp(iSwapEmp As Int, iReg";
 //BA.debugLineNum = 1218;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 1220;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1221;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 1222;BA.debugLine="Try";
try { //BA.debugLineNum = 1224;BA.debugLine="Starter.strCriteria = \"UPDATE tblRegistration \"";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblRegistration "+"SET WasSwapped = ?, SwappedTo = ?, WillAttend = ?"+"WHERE RegID = "+BA.NumberToString(_iregid);
 //BA.debugLineNum = 1227;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{("1"),mostCurrent._sswapemp,("1")}));
 //BA.debugLineNum = 1229;BA.debugLine="Starter.strCriteria = \"UPDATE tblRegistration \"";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblRegistration "+"SET WillAttend = ?"+"WHERE RegID = "+BA.NumberToString(_iswapemp);
 //BA.debugLineNum = 1232;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{("2")}));
 //BA.debugLineNum = 1233;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 1234;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e12) {
			processBA.setLastException(e12); //BA.debugLineNum = 1236;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1237;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("49568276",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1239;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 1240;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 1241;BA.debugLine="End Sub";
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
