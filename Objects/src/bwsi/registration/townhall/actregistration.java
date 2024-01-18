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

public class actregistration extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static actregistration mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "bwsi.registration.townhall", "bwsi.registration.townhall.actregistration");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (actregistration).");
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
		activityBA = new BA(this, layout, processBA, "bwsi.registration.townhall", "bwsi.registration.townhall.actregistration");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "bwsi.registration.townhall.actregistration", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (actregistration) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (actregistration) Resume **");
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
		return actregistration.class;
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
            BA.LogInfo("** Activity (actregistration) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (actregistration) Pause event (activity is not paused). **");
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
            actregistration mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (actregistration) Resume **");
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
public de.amberhome.objects.appcompat.ACButtonWrapper _btnregister = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnswap = null;
public anywheresoftware.b4a.objects.PanelWrapper _avatarbg = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblavatar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbranches = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldivision = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblempname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblstatus = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _pnltitle = null;
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
public anywheresoftware.b4a.objects.LabelWrapper _lblwasawardee = null;
public bwsi.registration.townhall.main _main = null;
public bwsi.registration.townhall.registration _registration = null;
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
public static class _empdata{
public boolean IsInitialized;
public int RegistrationID;
public String Avatar;
public String EmpName;
public String EmpDivision;
public int RegStatus;
public int WillAttend;
public int WasAwardee;
public String sAwards;
public void Initialize() {
IsInitialized = true;
RegistrationID = 0;
Avatar = "";
EmpName = "";
EmpDivision = "";
RegStatus = 0;
WillAttend = 0;
WasAwardee = 0;
sAwards = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _branchdata{
public boolean IsInitialized;
public int BranchID;
public String BranchName;
public void Initialize() {
IsInitialized = true;
BranchID = 0;
BranchName = "";
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
 //BA.debugLineNum = 102;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 104;BA.debugLine="Scale.SetRate(0.5)";
mostCurrent._scale._setrate /*String*/ (mostCurrent.activityBA,0.5);
 //BA.debugLineNum = 105;BA.debugLine="Activity.LoadLayout(\"Registration\")";
mostCurrent._activity.LoadLayout("Registration",mostCurrent.activityBA);
 //BA.debugLineNum = 107;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(17).Bold.Append";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (17)).Bold().Append(BA.ObjectToCharSequence(mostCurrent._globalvar._areaname /*String*/ )).PopAll();
 //BA.debugLineNum = 108;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(14).Append(G";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (14)).Append(BA.ObjectToCharSequence(mostCurrent._globalvar._areadesc /*String*/ )).PopAll();
 //BA.debugLineNum = 110;BA.debugLine="ToolBar.InitMenuListener";
mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 111;BA.debugLine="ToolBar.Title = GlobalVar.CSTitle";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 112;BA.debugLine="ToolBar.SubTitle = GlobalVar.CSSubTitle";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 114;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 115;BA.debugLine="Dim xl As XmlLayoutBuilder";
_xl = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 116;BA.debugLine="jo = ToolBar";
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._toolbar.getObject()));
 //BA.debugLineNum = 117;BA.debugLine="jo.RunMethod(\"setPopupTheme\", Array(xl.GetResourc";
_jo.RunMethod("setPopupTheme",new Object[]{(Object)(_xl.GetResourceId("style","ToolbarMenu"))});
 //BA.debugLineNum = 118;BA.debugLine="jo.RunMethod(\"setContentInsetStartWithNavigation\"";
_jo.RunMethod("setContentInsetStartWithNavigation",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))});
 //BA.debugLineNum = 119;BA.debugLine="jo.RunMethod(\"setTitleMarginStart\", Array(0dip))";
_jo.RunMethod("setTitleMarginStart",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)))});
 //BA.debugLineNum = 121;BA.debugLine="ActionBarButton.Initialize";
mostCurrent._actionbarbutton.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 122;BA.debugLine="ActionBarButton.ShowUpIndicator = True";
mostCurrent._actionbarbutton.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 124;BA.debugLine="GlobalVar.AreaID = DBFunctions.GetAreaID";
mostCurrent._globalvar._areaid /*int*/  = mostCurrent._dbfunctions._getareaid /*int*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 125;BA.debugLine="GlobalVar.AreaCode = DBFunctions.GetAreaCode(Glob";
mostCurrent._globalvar._areacode /*String*/  = mostCurrent._dbfunctions._getareacode /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 126;BA.debugLine="GlobalVar.AreaName = DBFunctions.GetAreaName(Glob";
mostCurrent._globalvar._areaname /*String*/  = mostCurrent._dbfunctions._getareaname /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 127;BA.debugLine="GlobalVar.AreaDesc = DBFunctions.GetAreaDesc(Glob";
mostCurrent._globalvar._areadesc /*String*/  = mostCurrent._dbfunctions._getareadesc /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 129;BA.debugLine="pnlTitle.Visible = False";
mostCurrent._pnltitle.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 130;BA.debugLine="pnlTitle.SetLayoutAnimated(0, 0, 0, clvEmployees.";
mostCurrent._pnltitle.SetLayoutAnimated((int) (0),(int) (0),(int) (0),mostCurrent._clvemployees._asview().getWidth(),(int) (_titleheight+_dividerheight));
 //BA.debugLineNum = 131;BA.debugLine="pnlTitle.LoadLayout(\"BranchItems\")";
mostCurrent._pnltitle.LoadLayout("BranchItems",mostCurrent.activityBA);
 //BA.debugLineNum = 132;BA.debugLine="BTAdmin.Initialize(\"Admin\")";
_btadmin.Initialize(processBA,"Admin");
 //BA.debugLineNum = 133;BA.debugLine="Serial1.Initialize(\"Printer\")";
_serial1.Initialize("Printer");
 //BA.debugLineNum = 134;BA.debugLine="AddBranches(GlobalVar.AreaID)";
_addbranches(mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 135;BA.debugLine="SetButtonColors";
_setbuttoncolors();
 //BA.debugLineNum = 136;BA.debugLine="RegID = 0";
_regid = (int) (0);
 //BA.debugLineNum = 137;BA.debugLine="CDTxtBox.Initialize2(Colors.Transparent, Colors.T";
mostCurrent._cdtxtbox.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.Transparent,anywheresoftware.b4a.keywords.Common.Colors.Transparent,(int) (0),(int) (0));
 //BA.debugLineNum = 138;BA.debugLine="txtSearch.Background = CDTxtBox";
mostCurrent._txtsearch.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdtxtbox.getObject()));
 //BA.debugLineNum = 139;BA.debugLine="blnNewReg = True";
_blnnewreg = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 142;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 143;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 144;BA.debugLine="ToolBar_NavigationItemClick";
_toolbar_navigationitemclick();
 //BA.debugLineNum = 145;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 147;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 149;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 160;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 151;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 152;BA.debugLine="pnlTitle.Visible = False";
mostCurrent._pnltitle.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 153;BA.debugLine="GlobalVar.AreaID = DBFunctions.GetAreaID";
mostCurrent._globalvar._areaid /*int*/  = mostCurrent._dbfunctions._getareaid /*int*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 154;BA.debugLine="GlobalVar.AreaCode = DBFunctions.GetAreaCode(Glob";
mostCurrent._globalvar._areacode /*String*/  = mostCurrent._dbfunctions._getareacode /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 155;BA.debugLine="GlobalVar.AreaName = DBFunctions.GetAreaName(Glob";
mostCurrent._globalvar._areaname /*String*/  = mostCurrent._dbfunctions._getareaname /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 156;BA.debugLine="GlobalVar.AreaDesc = DBFunctions.GetAreaDesc(Glob";
mostCurrent._globalvar._areadesc /*String*/  = mostCurrent._dbfunctions._getareadesc /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 157;BA.debugLine="blnNewReg = True";
_blnnewreg = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 158;BA.debugLine="End Sub";
return "";
}
public static String  _addbranch(String _title) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
bwsi.registration.townhall.actregistration._branchdata _bd = null;
 //BA.debugLineNum = 310;BA.debugLine="Sub AddBranch (Title As String)";
 //BA.debugLineNum = 311;BA.debugLine="Dim p As B4XView = xui.CreatePanel(\"\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = _xui.CreatePanel(processBA,"");
 //BA.debugLineNum = 312;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, clvEmployees.AsView.";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),mostCurrent._clvemployees._asview().getWidth(),_titleheight);
 //BA.debugLineNum = 313;BA.debugLine="p.LoadLayout(\"BranchItems\")";
_p.LoadLayout("BranchItems",mostCurrent.activityBA);
 //BA.debugLineNum = 314;BA.debugLine="lblBranches.Text = Title";
mostCurrent._lblbranches.setText(BA.ObjectToCharSequence(_title));
 //BA.debugLineNum = 315;BA.debugLine="LogColor(Title, Colors.Red)";
anywheresoftware.b4a.keywords.Common.LogImpl("43604485",_title,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 316;BA.debugLine="Dim BD As BranchData";
_bd = new bwsi.registration.townhall.actregistration._branchdata();
 //BA.debugLineNum = 317;BA.debugLine="BD.BranchName = Title";
_bd.BranchName /*String*/  = _title;
 //BA.debugLineNum = 318;BA.debugLine="clvEmployees.AnimationDuration = 0";
mostCurrent._clvemployees._animationduration = (int) (0);
 //BA.debugLineNum = 319;BA.debugLine="clvEmployees.Add(p, BD)";
mostCurrent._clvemployees._add(_p,(Object)(_bd));
 //BA.debugLineNum = 320;BA.debugLine="End Sub";
return "";
}
public static String  _addbranches(int _iareaid) throws Exception{
int _ibranchid = 0;
String _sbranchname = "";
long _starttime = 0L;
int _x = 0;
int _i = 0;
bwsi.registration.townhall.actregistration._empdata _ed = null;
 //BA.debugLineNum = 242;BA.debugLine="Private Sub AddBranches(iAreaID As Int)";
 //BA.debugLineNum = 243;BA.debugLine="Dim iBranchID As Int";
_ibranchid = 0;
 //BA.debugLineNum = 244;BA.debugLine="Dim sBranchName As String";
_sbranchname = "";
 //BA.debugLineNum = 246;BA.debugLine="clvEmployees.Clear";
mostCurrent._clvemployees._clear();
 //BA.debugLineNum = 247;BA.debugLine="Try";
try { //BA.debugLineNum = 248;BA.debugLine="Starter.strCriteria = \"SELECT DISTINCT Employees";
mostCurrent._starter._strcriteria /*String*/  = "SELECT DISTINCT Employees.BranchID, Branches.BranchName "+"FROM tblBranches AS Branches "+"INNER JOIN tblRegistration AS Employees ON Branches.BranchID = Employees.BranchID "+"WHERE Branches.AreaID = "+BA.NumberToString(_iareaid)+" "+"ORDER BY Branches.BranchID ASC";
 //BA.debugLineNum = 254;BA.debugLine="rsBranch = Starter.DBCon.ExecQuery(Starter.strCr";
mostCurrent._rsbranch = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 256;BA.debugLine="If rsBranch.RowCount > 0 Then";
if (mostCurrent._rsbranch.getRowCount()>0) { 
 //BA.debugLineNum = 257;BA.debugLine="Dim StartTime As Long = DateTime.Now";
_starttime = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 258;BA.debugLine="For x = 0 To rsBranch.RowCount - 1";
{
final int step9 = 1;
final int limit9 = (int) (mostCurrent._rsbranch.getRowCount()-1);
_x = (int) (0) ;
for (;_x <= limit9 ;_x = _x + step9 ) {
 //BA.debugLineNum = 259;BA.debugLine="rsBranch.Position = x";
mostCurrent._rsbranch.setPosition(_x);
 //BA.debugLineNum = 260;BA.debugLine="iBranchID = rsBranch.GetInt(\"BranchID\")";
_ibranchid = mostCurrent._rsbranch.GetInt("BranchID");
 //BA.debugLineNum = 261;BA.debugLine="sBranchName = rsBranch.GetString(\"BranchName\")";
_sbranchname = mostCurrent._rsbranch.GetString("BranchName");
 //BA.debugLineNum = 263;BA.debugLine="AddBranch(sBranchName)";
_addbranch(_sbranchname);
 //BA.debugLineNum = 265;BA.debugLine="Starter.strCriteria = \"SELECT Registration.Reg";
mostCurrent._starter._strcriteria /*String*/  = "SELECT Registration.RegID, Registration.FirstName, Registration.LastName, Registration.FullName, Branches.BranchName, "+"Registration.Division, Registration.WillAttend, Registration.WasRegistered, Registration.WasAwardee, Registration.Awards "+"FROM tblRegistration AS Registration "+"INNER JOIN tblBranches AS Branches ON Registration.BranchID = Branches.BranchID "+"WHERE Registration.BranchID = "+BA.NumberToString(_ibranchid)+" "+"AND Registration.WillAttend <> 0 "+"ORDER BY Registration.Division, Registration.FullName ASC	";
 //BA.debugLineNum = 273;BA.debugLine="LogColor(Starter.strCriteria, Colors.Cyan)";
anywheresoftware.b4a.keywords.Common.LogImpl("43538975",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 274;BA.debugLine="rsEmployees = Starter.DBCon.ExecQuery(Starter.";
mostCurrent._rsemployees = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 276;BA.debugLine="If rsEmployees.RowCount > 0 Then";
if (mostCurrent._rsemployees.getRowCount()>0) { 
 //BA.debugLineNum = 277;BA.debugLine="For i = 0 To rsEmployees.RowCount - 1";
{
final int step18 = 1;
final int limit18 = (int) (mostCurrent._rsemployees.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit18 ;_i = _i + step18 ) {
 //BA.debugLineNum = 278;BA.debugLine="rsEmployees.Position = i";
mostCurrent._rsemployees.setPosition(_i);
 //BA.debugLineNum = 279;BA.debugLine="Dim ED As EmpData";
_ed = new bwsi.registration.townhall.actregistration._empdata();
 //BA.debugLineNum = 280;BA.debugLine="ED.RegistrationID = rsEmployees.GetInt(\"RegI";
_ed.RegistrationID /*int*/  = mostCurrent._rsemployees.GetInt("RegID");
 //BA.debugLineNum = 281;BA.debugLine="ED.Avatar = GlobalVar.SF.Upper(GlobalVar.SF.";
_ed.Avatar /*String*/  = mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv6(mostCurrent._rsemployees.GetString("FirstName"),(long) (1)))+mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv6(mostCurrent._rsemployees.GetString("LastName"),(long) (1)));
 //BA.debugLineNum = 282;BA.debugLine="ED.EmpName = rsEmployees.GetString(\"FullName";
_ed.EmpName /*String*/  = mostCurrent._rsemployees.GetString("FullName");
 //BA.debugLineNum = 283;BA.debugLine="ED.EmpDivision = rsEmployees.GetString(\"Divi";
_ed.EmpDivision /*String*/  = mostCurrent._rsemployees.GetString("Division");
 //BA.debugLineNum = 284;BA.debugLine="ED.WillAttend = rsEmployees.GetInt(\"WillAtte";
_ed.WillAttend /*int*/  = mostCurrent._rsemployees.GetInt("WillAttend");
 //BA.debugLineNum = 285;BA.debugLine="ED.RegStatus = rsEmployees.GetInt(\"WasRegist";
_ed.RegStatus /*int*/  = mostCurrent._rsemployees.GetInt("WasRegistered");
 //BA.debugLineNum = 286;BA.debugLine="ED.WasAwardee = rsEmployees.GetInt(\"WasAward";
_ed.WasAwardee /*int*/  = mostCurrent._rsemployees.GetInt("WasAwardee");
 //BA.debugLineNum = 287;BA.debugLine="ED.sAwards = rsEmployees.GetString(\"Awards\")";
_ed.sAwards /*String*/  = mostCurrent._rsemployees.GetString("Awards");
 //BA.debugLineNum = 288;BA.debugLine="clvEmployees.Add(AddEmployees(clvEmployees.A";
mostCurrent._clvemployees._add((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_addemployees(mostCurrent._clvemployees._asview().getWidth(),_ed.EmpName /*String*/ ,_ed.EmpDivision /*String*/ ,_ed.RegStatus /*int*/ ,_ed.WillAttend /*int*/ ,_ed.Avatar /*String*/ ,_ed.WasAwardee /*int*/ ,_ed.sAwards /*String*/ ).getObject())),(Object)(mostCurrent._rsemployees.GetInt("RegID")));
 }
};
 }else {
 //BA.debugLineNum = 291;BA.debugLine="Log(rsEmployees.RowCount)";
anywheresoftware.b4a.keywords.Common.LogImpl("43538993",BA.NumberToString(mostCurrent._rsemployees.getRowCount()),0);
 };
 }
};
 }else {
 //BA.debugLineNum = 296;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("43538998",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 299;BA.debugLine="Log($\"List of Time Records = ${NumberFormat2((Da";
anywheresoftware.b4a.keywords.Common.LogImpl("43539001",("List of Time Records = "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(anywheresoftware.b4a.keywords.Common.NumberFormat2((anywheresoftware.b4a.keywords.Common.DateTime.getNow()-_starttime)/(double)1000,(int) (0),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False)))+" seconds to populate "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._clvemployees._getsize()))+" Time Records"),0);
 } 
       catch (Exception e40) {
			processBA.setLastException(e40); //BA.debugLineNum = 302;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("43539004",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 308;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.PanelWrapper  _addemployees(int _iwidth,String _strempname,String _strbranchname,int _iregstatus,int _intwillattend,String _savatar,int _intwasawardee,String _strawards) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
int _iheight = 0;
anywheresoftware.b4a.objects.CSBuilder _csstatus = null;
 //BA.debugLineNum = 322;BA.debugLine="Sub AddEmployees(iWidth As Int, strEmpName As Stri";
 //BA.debugLineNum = 324;BA.debugLine="Dim p As B4XView = xui.CreatePanel(\"\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = _xui.CreatePanel(processBA,"");
 //BA.debugLineNum = 325;BA.debugLine="Dim iHeight As Int = 125dip";
_iheight = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (125));
 //BA.debugLineNum = 326;BA.debugLine="Dim csStatus As CSBuilder";
_csstatus = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 328;BA.debugLine="If GetDeviceLayoutValues.ApproximateScreenSize <";
if (anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(mostCurrent.activityBA).getApproximateScreenSize()<4.5) { 
_iheight = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310));};
 //BA.debugLineNum = 329;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, iWidth, iHeight)";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),_iwidth,_iheight);
 //BA.debugLineNum = 330;BA.debugLine="p.LoadLayout(\"EmployeeList\")";
_p.LoadLayout("EmployeeList",mostCurrent.activityBA);
 //BA.debugLineNum = 331;BA.debugLine="clvEmployees.AnimationDuration = 0";
mostCurrent._clvemployees._animationduration = (int) (0);
 //BA.debugLineNum = 332;BA.debugLine="If intWillAttend = 1 Then";
if (_intwillattend==1) { 
 //BA.debugLineNum = 333;BA.debugLine="If iRegStatus = 0 Then";
if (_iregstatus==0) { 
 //BA.debugLineNum = 334;BA.debugLine="csStatus.Initialize.Bold.Color(Colors.Red).Appe";
_csstatus.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence("UNREGISTERED")).PopAll();
 //BA.debugLineNum = 335;BA.debugLine="btnSwap.Enabled = False";
mostCurrent._btnswap.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 336;BA.debugLine="btnSwap.Visible = True";
mostCurrent._btnswap.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 337;BA.debugLine="btnSwap.Text = \"Reprint Stub\"";
mostCurrent._btnswap.setText(BA.ObjectToCharSequence("Reprint Stub"));
 //BA.debugLineNum = 338;BA.debugLine="btnRegister.Enabled = True";
mostCurrent._btnregister.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 339;BA.debugLine="btnRegister.Text = \"Register\"";
mostCurrent._btnregister.setText(BA.ObjectToCharSequence("Register"));
 }else {
 //BA.debugLineNum = 341;BA.debugLine="csStatus.Initialize.Bold.Color(0xFF1976D2).Appe";
_csstatus.Initialize().Bold().Color((int) (0xff1976d2)).Append(BA.ObjectToCharSequence("REGISTERED")).PopAll();
 //BA.debugLineNum = 342;BA.debugLine="btnSwap.Enabled = True";
mostCurrent._btnswap.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 343;BA.debugLine="btnSwap.Visible = True";
mostCurrent._btnswap.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 344;BA.debugLine="btnSwap.Text = \"Reprint Stub\"";
mostCurrent._btnswap.setText(BA.ObjectToCharSequence("Reprint Stub"));
 //BA.debugLineNum = 345;BA.debugLine="btnRegister.Enabled = False";
mostCurrent._btnregister.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 346;BA.debugLine="btnRegister.Text = \"Register\"";
mostCurrent._btnregister.setText(BA.ObjectToCharSequence("Register"));
 };
 }else {
 //BA.debugLineNum = 349;BA.debugLine="If iRegStatus = 0 Then";
if (_iregstatus==0) { 
 //BA.debugLineNum = 350;BA.debugLine="csStatus.Initialize.Bold.Color(Colors.Red).Appe";
_csstatus.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence("UNREGISTERED")).PopAll();
 //BA.debugLineNum = 351;BA.debugLine="btnSwap.Text = $\"Swap Duty\"$";
mostCurrent._btnswap.setText(BA.ObjectToCharSequence(("Swap Duty")));
 //BA.debugLineNum = 352;BA.debugLine="btnSwap.Visible = True";
mostCurrent._btnswap.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 353;BA.debugLine="btnSwap.Enabled = True";
mostCurrent._btnswap.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 354;BA.debugLine="btnRegister.Text = \"Register\"";
mostCurrent._btnregister.setText(BA.ObjectToCharSequence("Register"));
 }else {
 //BA.debugLineNum = 356;BA.debugLine="csStatus.Initialize.Bold.Color(0xFF1976D2).Appe";
_csstatus.Initialize().Bold().Color((int) (0xff1976d2)).Append(BA.ObjectToCharSequence("REGISTERED")).PopAll();
 //BA.debugLineNum = 357;BA.debugLine="btnSwap.Visible = True";
mostCurrent._btnswap.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 358;BA.debugLine="btnSwap.Enabled = True";
mostCurrent._btnswap.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 359;BA.debugLine="btnSwap.Text = \"Reprint Stub\"";
mostCurrent._btnswap.setText(BA.ObjectToCharSequence("Reprint Stub"));
 //BA.debugLineNum = 360;BA.debugLine="btnRegister.Enabled = False";
mostCurrent._btnregister.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 363;BA.debugLine="lblAvatar.Text = sAvatar";
mostCurrent._lblavatar.setText(BA.ObjectToCharSequence(_savatar));
 //BA.debugLineNum = 364;BA.debugLine="lblEmpName.Text = GlobalVar.SF.Upper(strEmpName)";
mostCurrent._lblempname.setText(BA.ObjectToCharSequence(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(_strempname)));
 //BA.debugLineNum = 365;BA.debugLine="lblDivision.Text = strBranchName";
mostCurrent._lbldivision.setText(BA.ObjectToCharSequence(_strbranchname));
 //BA.debugLineNum = 366;BA.debugLine="lblStatus.Text = csStatus";
mostCurrent._lblstatus.setText(BA.ObjectToCharSequence(_csstatus.getObject()));
 //BA.debugLineNum = 367;BA.debugLine="If intWasAwardee = 1 Then";
if (_intwasawardee==1) { 
 //BA.debugLineNum = 368;BA.debugLine="lblWasAwardee.Visible = True";
mostCurrent._lblwasawardee.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 369;BA.debugLine="lblWasAwardee.Text = $\"LOYALTY AWARDEE (\"$ & str";
mostCurrent._lblwasawardee.setText(BA.ObjectToCharSequence(("LOYALTY AWARDEE (")+_strawards+(")")));
 }else {
 //BA.debugLineNum = 371;BA.debugLine="lblWasAwardee.Visible = False";
mostCurrent._lblwasawardee.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 373;BA.debugLine="AvatarBG.Color = ShadeColor(Rnd(0xFF000050, 0xFFF";
mostCurrent._avatarbg.setColor(_shadecolor(anywheresoftware.b4a.keywords.Common.Rnd((int) (0xff000050),(int) (0xffffffff))));
 //BA.debugLineNum = 374;BA.debugLine="SetButtonColors";
_setbuttoncolors();
 //BA.debugLineNum = 375;BA.debugLine="Return p";
if (true) return (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_p.getObject()));
 //BA.debugLineNum = 376;BA.debugLine="End Sub";
return null;
}
public static String  _addpeople_oncancelclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 768;BA.debugLine="Private Sub AddPeople_OnCancelClicked (View As Vie";
 //BA.debugLineNum = 769;BA.debugLine="If View<>Null Then";
if (_view!= null) { 
 //BA.debugLineNum = 770;BA.debugLine="ToastMessageShow(\"Cancelled!\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Cancelled!"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 771;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 772;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 };
 //BA.debugLineNum = 774;BA.debugLine="End Sub";
return "";
}
public static String  _addpeople_onitemclick(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,String _selection,int _position,long _id) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 755;BA.debugLine="Private Sub AddPeople_OnItemClick (View As View, S";
 //BA.debugLineNum = 756;BA.debugLine="ToastMessageShow(Selection&\" Selected! (Position";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_selection+" Selected! (Position : "+BA.NumberToString(_position)+")"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 757;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 758;BA.debugLine="Alert.Initialize.Dismiss2";
_alert.Initialize().Dismiss2();
 //BA.debugLineNum = 760;BA.debugLine="Select Case Position";
switch (_position) {
case 0: {
 //BA.debugLineNum = 762;BA.debugLine="ToastMessageShow($\"Sorry, Module not available.";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Sorry, Module not available...")),anywheresoftware.b4a.keywords.Common.True);
 break; }
case 1: {
 //BA.debugLineNum = 764;BA.debugLine="StartActivity(AddGuest)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._addguest.getObject()));
 break; }
}
;
 //BA.debugLineNum = 766;BA.debugLine="End Sub";
return "";
}
public static String  _btncancel_click() throws Exception{
 //BA.debugLineNum = 1317;BA.debugLine="Sub btnCancel_Click";
 //BA.debugLineNum = 1318;BA.debugLine="SearchClosed";
_searchclosed();
 //BA.debugLineNum = 1319;BA.debugLine="AddBranches(GlobalVar.AreaID)";
_addbranches(mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 1320;BA.debugLine="End Sub";
return "";
}
public static String  _btnregister_click() throws Exception{
int _index = 0;
 //BA.debugLineNum = 214;BA.debugLine="Sub btnRegister_Click";
 //BA.debugLineNum = 215;BA.debugLine="Dim index As Int =clvEmployees.GetValue(clvEmploy";
_index = (int)(BA.ObjectToNumber(mostCurrent._clvemployees._getvalue(mostCurrent._clvemployees._getitemfromview((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)))))));
 //BA.debugLineNum = 216;BA.debugLine="RegID = index";
_regid = _index;
 //BA.debugLineNum = 217;BA.debugLine="LogColor($\"Registration ID: \"$ & RegID, Colors.Cy";
anywheresoftware.b4a.keywords.Common.LogImpl("43342339",("Registration ID: ")+BA.NumberToString(_regid),anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 218;BA.debugLine="Try";
try { //BA.debugLineNum = 219;BA.debugLine="sRegName = GetEmployeeName(RegID)";
mostCurrent._sregname = _getemployeename(_regid);
 } 
       catch (Exception e7) {
			processBA.setLastException(e7); //BA.debugLineNum = 221;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("43342343",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 224;BA.debugLine="ConfirmRegister(sRegName)";
_confirmregister(mostCurrent._sregname);
 //BA.debugLineNum = 225;BA.debugLine="End Sub";
return "";
}
public static String  _btnswap_click() throws Exception{
int _index = 0;
int _btnstatus = 0;
 //BA.debugLineNum = 180;BA.debugLine="Sub btnSwap_Click";
 //BA.debugLineNum = 181;BA.debugLine="Dim index As Int = clvEmployees.GetValue(clvEmplo";
_index = (int)(BA.ObjectToNumber(mostCurrent._clvemployees._getvalue(mostCurrent._clvemployees._getitemfromview((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)))))));
 //BA.debugLineNum = 183;BA.debugLine="RegID = index";
_regid = _index;
 //BA.debugLineNum = 184;BA.debugLine="Dim btnStatus As Int";
_btnstatus = 0;
 //BA.debugLineNum = 186;BA.debugLine="LogColor(index, Colors.Red)";
anywheresoftware.b4a.keywords.Common.LogImpl("43276806",BA.NumberToString(_index),anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 188;BA.debugLine="btnStatus = GetRegStatus(RegID)";
_btnstatus = _getregstatus(_regid);
 //BA.debugLineNum = 189;BA.debugLine="LogColor($\"Register Status: \"$ & btnStatus, Color";
anywheresoftware.b4a.keywords.Common.LogImpl("43276809",("Register Status: ")+BA.NumberToString(_btnstatus),anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 191;BA.debugLine="Try";
try { //BA.debugLineNum = 192;BA.debugLine="Select Case btnStatus";
switch (_btnstatus) {
case 1: {
 //BA.debugLineNum = 194;BA.debugLine="sRegName = GetEmployeeName(RegID)";
mostCurrent._sregname = _getemployeename(_regid);
 //BA.debugLineNum = 195;BA.debugLine="ConfirmReprint(sRegName)";
_confirmreprint(mostCurrent._sregname);
 break; }
case 2: {
 //BA.debugLineNum = 197;BA.debugLine="If pnlSearchMain.Visible = True Then Return";
if (mostCurrent._pnlsearchmain.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
if (true) return "";};
 //BA.debugLineNum = 198;BA.debugLine="pnlSearchMain.Visible = True";
mostCurrent._pnlsearchmain.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 199;BA.debugLine="lblSearchTitle.Text = $\"Search Employee to Swa";
mostCurrent._lblsearchtitle.setText(BA.ObjectToCharSequence(("Search Employee to Swap Duty")));
 //BA.debugLineNum = 200;BA.debugLine="SV.Initialize(Me,\"SV\")";
mostCurrent._sv._initialize /*String*/ (mostCurrent.activityBA,actregistration.getObject(),"SV");
 //BA.debugLineNum = 201;BA.debugLine="SV.AddToParent(pnlSearchEmployees,3,3,pnlSearc";
mostCurrent._sv._addtoparent /*String*/ (mostCurrent._pnlsearchemployees,(int) (3),(int) (3),(int) (mostCurrent._pnlsearchemployees.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),mostCurrent._pnlsearchemployees.getHeight());
 //BA.debugLineNum = 202;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 203;BA.debugLine="SV.lv.Clear";
mostCurrent._sv._lv /*anywheresoftware.b4a.objects.ListViewWrapper*/ .Clear();
 //BA.debugLineNum = 204;BA.debugLine="btnCancel.Enabled = True";
mostCurrent._btncancel.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 205;BA.debugLine="iSwapBranchID = DBFunctions.GetBranchIDByEmpID";
_iswapbranchid = mostCurrent._dbfunctions._getbranchidbyempid /*int*/ (mostCurrent.activityBA,_regid);
 //BA.debugLineNum = 207;BA.debugLine="SearchSwapEmployees(iSwapBranchID)";
_searchswapemployees(_iswapbranchid);
 break; }
}
;
 } 
       catch (Exception e25) {
			processBA.setLastException(e25); //BA.debugLineNum = 210;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("43276830",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 212;BA.debugLine="End Sub";
return "";
}
public static String  _clvemployees_itemclick(int _index,Object _value) throws Exception{
 //BA.debugLineNum = 176;BA.debugLine="Sub clvEmployees_ItemClick (Index As Int, Value As";
 //BA.debugLineNum = 178;BA.debugLine="End Sub";
return "";
}
public static String  _confirmregister(String _sempname) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 485;BA.debugLine="Private Sub ConfirmRegister (sEmpName As String)";
 //BA.debugLineNum = 486;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 488;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(("CONFIRM REGISTRATION")).SetMessage(("Do you want to Register")+anywheresoftware.b4a.keywords.Common.CRLF+_sempname+("?")).SetPositiveText("YES").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetNegativeText("NO").SetNegativeColor((int) (mostCurrent._globalvar._negcolor /*double*/ )).SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"RegisterEmp").SetOnNegativeClicked(mostCurrent.activityBA,"RegisterEmp").SetOnViewBinder(mostCurrent.activityBA,"RegFontSizeBinder");
 //BA.debugLineNum = 506;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 507;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 509;BA.debugLine="End Sub";
return "";
}
public static String  _confirmreprint(String _sempname) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 529;BA.debugLine="Private Sub ConfirmReprint (sEmpName As String)";
 //BA.debugLineNum = 530;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 532;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(("CONFIRM REPRINT STUB")).SetMessage(("Do you want to Reprint")+anywheresoftware.b4a.keywords.Common.CRLF+_sempname+(" stub?")).SetPositiveText("YES").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetNegativeText("NO").SetNegativeColor((int) (mostCurrent._globalvar._negcolor /*double*/ )).SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"StubReprint").SetOnNegativeClicked(mostCurrent.activityBA,"StubReprint").SetOnViewBinder(mostCurrent.activityBA,"RegFontSizeBinder");
 //BA.debugLineNum = 550;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 551;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 553;BA.debugLine="End Sub";
return "";
}
public static String  _confirmswapemp(String _sempname) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 573;BA.debugLine="Private Sub ConfirmSwapEmp (sEmpName As String)";
 //BA.debugLineNum = 574;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 576;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(("CONFIRM SWAP DUTY")).SetMessage(("Confirm Swap Duty with")+anywheresoftware.b4a.keywords.Common.CRLF+_sempname+("?")).SetPositiveText("YES").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetNegativeText("NO").SetNegativeColor((int) (mostCurrent._globalvar._negcolor /*double*/ )).SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"SwapEmp").SetOnNegativeClicked(mostCurrent.activityBA,"SwapEmp").SetOnViewBinder(mostCurrent.activityBA,"RegFontSizeBinder");
 //BA.debugLineNum = 594;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 595;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 597;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.StateListDrawable  _createbuttoncolor(int _focusedcolor,int _enabledcolor,int _disabledcolor,int _pressedcolor) throws Exception{
anywheresoftware.b4a.objects.drawable.StateListDrawable _retcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwfocusedcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwenabledcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwdisabledcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwpressedcolor = null;
 //BA.debugLineNum = 383;BA.debugLine="Private Sub CreateButtonColor(FocusedColor As Int,";
 //BA.debugLineNum = 385;BA.debugLine="Dim RetColor As StateListDrawable";
_retcolor = new anywheresoftware.b4a.objects.drawable.StateListDrawable();
 //BA.debugLineNum = 386;BA.debugLine="RetColor.Initialize";
_retcolor.Initialize();
 //BA.debugLineNum = 387;BA.debugLine="Dim drwFocusedColor, drwEnabledColor, drwDisabled";
_drwfocusedcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_drwenabledcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_drwdisabledcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_drwpressedcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 389;BA.debugLine="drwFocusedColor.Initialize2(FocusedColor, 25, 0,";
_drwfocusedcolor.Initialize2(_focusedcolor,(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 390;BA.debugLine="drwEnabledColor.Initialize2(EnabledColor, 25, 0,";
_drwenabledcolor.Initialize2(_enabledcolor,(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 391;BA.debugLine="drwDisabledColor.Initialize2(DisabledColor, 25, 2";
_drwdisabledcolor.Initialize2(_disabledcolor,(int) (25),(int) (2),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 392;BA.debugLine="drwPressedColor.Initialize2(PressedColor, 25, 0,";
_drwpressedcolor.Initialize2(_pressedcolor,(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 394;BA.debugLine="RetColor.AddState(RetColor.State_Focused, drwFocu";
_retcolor.AddState(_retcolor.State_Focused,(android.graphics.drawable.Drawable)(_drwfocusedcolor.getObject()));
 //BA.debugLineNum = 395;BA.debugLine="RetColor.AddState(RetColor.State_Pressed, drwPres";
_retcolor.AddState(_retcolor.State_Pressed,(android.graphics.drawable.Drawable)(_drwpressedcolor.getObject()));
 //BA.debugLineNum = 396;BA.debugLine="RetColor.AddState(RetColor.State_Enabled, drwEnab";
_retcolor.AddState(_retcolor.State_Enabled,(android.graphics.drawable.Drawable)(_drwenabledcolor.getObject()));
 //BA.debugLineNum = 397;BA.debugLine="RetColor.AddState(RetColor.State_Disabled, drwDis";
_retcolor.AddState(_retcolor.State_Disabled,(android.graphics.drawable.Drawable)(_drwdisabledcolor.getObject()));
 //BA.debugLineNum = 398;BA.debugLine="RetColor.AddCatchAllState(drwFocusedColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwfocusedcolor.getObject()));
 //BA.debugLineNum = 399;BA.debugLine="RetColor.AddCatchAllState(drwEnabledColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwenabledcolor.getObject()));
 //BA.debugLineNum = 400;BA.debugLine="RetColor.AddCatchAllState(drwDisabledColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwdisabledcolor.getObject()));
 //BA.debugLineNum = 401;BA.debugLine="RetColor.AddCatchAllState(drwPressedColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwpressedcolor.getObject()));
 //BA.debugLineNum = 402;BA.debugLine="Return RetColor";
if (true) return _retcolor;
 //BA.debugLineNum = 404;BA.debugLine="End Sub";
return null;
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _createscaledbitmap(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _original,int _newwidth,int _newheight) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _b = null;
 //BA.debugLineNum = 1100;BA.debugLine="Sub CreateScaledBitmap(Original As Bitmap, NewWidt";
 //BA.debugLineNum = 1101;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 1102;BA.debugLine="Dim b As Bitmap";
_b = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 1103;BA.debugLine="b = r.RunStaticMethod(\"android.graphics.Bitmap\",";
_b = (anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(_r.RunStaticMethod("android.graphics.Bitmap","createScaledBitmap",new Object[]{(Object)(_original.getObject()),(Object)(_newwidth),(Object)(_newheight),(Object)(anywheresoftware.b4a.keywords.Common.True)},new String[]{"android.graphics.Bitmap","java.lang.int","java.lang.int","java.lang.boolean"})));
 //BA.debugLineNum = 1104;BA.debugLine="Return b";
if (true) return _b;
 //BA.debugLineNum = 1105;BA.debugLine="End Sub";
return null;
}
public static int[]  _getargb(int _color) throws Exception{
int[] _rets = null;
 //BA.debugLineNum = 233;BA.debugLine="Sub GetARGB(Color As Int) As Int()";
 //BA.debugLineNum = 234;BA.debugLine="Private Rets(4) As Int";
_rets = new int[(int) (4)];
;
 //BA.debugLineNum = 235;BA.debugLine="Rets(0) = Bit.UnsignedShiftRight(Bit.And(Color, 0";
_rets[(int) (0)] = anywheresoftware.b4a.keywords.Common.Bit.UnsignedShiftRight(anywheresoftware.b4a.keywords.Common.Bit.And(_color,(int) (0xff000000)),(int) (24));
 //BA.debugLineNum = 236;BA.debugLine="Rets(1) = Bit.UnsignedShiftRight(Bit.And(Color, 0";
_rets[(int) (1)] = anywheresoftware.b4a.keywords.Common.Bit.UnsignedShiftRight(anywheresoftware.b4a.keywords.Common.Bit.And(_color,(int) (0xff0000)),(int) (16));
 //BA.debugLineNum = 237;BA.debugLine="Rets(2) = Bit.UnsignedShiftRight(Bit.And(Color, 0";
_rets[(int) (2)] = anywheresoftware.b4a.keywords.Common.Bit.UnsignedShiftRight(anywheresoftware.b4a.keywords.Common.Bit.And(_color,(int) (0xff00)),(int) (8));
 //BA.debugLineNum = 238;BA.debugLine="Rets(3) = Bit.And(Color, 0xff)";
_rets[(int) (3)] = anywheresoftware.b4a.keywords.Common.Bit.And(_color,(int) (0xff));
 //BA.debugLineNum = 239;BA.debugLine="Return Rets";
if (true) return _rets;
 //BA.debugLineNum = 240;BA.debugLine="End Sub";
return null;
}
public static String  _getemployeename(int _iempid) throws Exception{
String _sretval = "";
 //BA.debugLineNum = 1109;BA.debugLine="Private Sub GetEmployeeName(iEmpID As Int) As Stri";
 //BA.debugLineNum = 1110;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 1111;BA.debugLine="Try";
try { //BA.debugLineNum = 1112;BA.debugLine="sRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_sretval = mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT FullName FROM tblRegistration WHERE RegID = "+BA.NumberToString(_iempid));
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 1114;BA.debugLine="sRetVal = \"\"";
_sretval = "";
 //BA.debugLineNum = 1115;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("45505030",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1117;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 1118;BA.debugLine="End Sub";
return "";
}
public static int  _getnoofstubprint(int _iregid) throws Exception{
int _iretval = 0;
 //BA.debugLineNum = 1183;BA.debugLine="Private Sub GetNoOfStubPrint(iRegID As Int) As Int";
 //BA.debugLineNum = 1184;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 1185;BA.debugLine="Try";
try { //BA.debugLineNum = 1186;BA.debugLine="iRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_iretval = (int)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT NoPrintStub FROM tblRegistration WHERE RegID = "+BA.NumberToString(_iregid))));
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 1188;BA.debugLine="iRetVal = 0";
_iretval = (int) (0);
 //BA.debugLineNum = 1189;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("45701638",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1191;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 1192;BA.debugLine="End Sub";
return 0;
}
public static int  _getregstatus(int _iregid) throws Exception{
int _iretval = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsstatus = null;
 //BA.debugLineNum = 1194;BA.debugLine="Private Sub GetRegStatus(iRegID As Int) As Int";
 //BA.debugLineNum = 1195;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 1196;BA.debugLine="Dim rsStatus As Cursor";
_rsstatus = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 1197;BA.debugLine="Try";
try { //BA.debugLineNum = 1198;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblRegistra";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblRegistration WHERE RegID = "+BA.NumberToString(_iregid);
 //BA.debugLineNum = 1199;BA.debugLine="rsStatus = Starter.DBCon.ExecQuery(Starter.strCr";
_rsstatus = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 1200;BA.debugLine="If rsStatus.RowCount > 0 Then";
if (_rsstatus.getRowCount()>0) { 
 //BA.debugLineNum = 1201;BA.debugLine="rsStatus.Position = 0";
_rsstatus.setPosition((int) (0));
 //BA.debugLineNum = 1202;BA.debugLine="If rsStatus.GetInt(\"WasRegistered\") = 1 Then 'R";
if (_rsstatus.GetInt("WasRegistered")==1) { 
 //BA.debugLineNum = 1203;BA.debugLine="iRetVal = 1 'Reprint Stub";
_iretval = (int) (1);
 }else {
 //BA.debugLineNum = 1205;BA.debugLine="iRetVal = 2 'Do Nothing";
_iretval = (int) (2);
 };
 };
 } 
       catch (Exception e15) {
			processBA.setLastException(e15); //BA.debugLineNum = 1209;BA.debugLine="iRetVal = 0";
_iretval = (int) (0);
 //BA.debugLineNum = 1210;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("45767184",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1212;BA.debugLine="rsStatus.Close";
_rsstatus.Close();
 //BA.debugLineNum = 1213;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 1214;BA.debugLine="End Sub";
return 0;
}
public static String  _getswapemployeename(int _iempid) throws Exception{
String _sretval = "";
 //BA.debugLineNum = 1261;BA.debugLine="Private Sub GetSwapEmployeeName(iEmpID As Int) As";
 //BA.debugLineNum = 1262;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 1263;BA.debugLine="Try";
try { //BA.debugLineNum = 1264;BA.debugLine="sRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_sretval = mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT FullName FROM tblRegistration WHERE RegID = "+BA.NumberToString(_iempid));
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 1266;BA.debugLine="sRetVal = \"\"";
_sretval = "";
 //BA.debugLineNum = 1267;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("45898246",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1269;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 1270;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 63;BA.debugLine="Private clvEmployees As CustomListView";
mostCurrent._clvemployees = new b4a.example3.customlistview();
 //BA.debugLineNum = 65;BA.debugLine="Private txtSearch As EditText";
mostCurrent._txtsearch = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Type EmpData (RegistrationID As Int, Avatar As St";
;
 //BA.debugLineNum = 67;BA.debugLine="Type BranchData (BranchID As Int, BranchName As S";
;
 //BA.debugLineNum = 69;BA.debugLine="Private RegID As Int";
_regid = 0;
 //BA.debugLineNum = 70;BA.debugLine="Private sRegName As String";
mostCurrent._sregname = "";
 //BA.debugLineNum = 71;BA.debugLine="Private MyList As List";
mostCurrent._mylist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 74;BA.debugLine="Private btnRegister As ACButton";
mostCurrent._btnregister = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Private btnSwap As ACButton";
mostCurrent._btnswap = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 77;BA.debugLine="Private AvatarBG As Panel";
mostCurrent._avatarbg = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 78;BA.debugLine="Private lblAvatar As Label";
mostCurrent._lblavatar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Private lblBranches As Label";
mostCurrent._lblbranches = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 80;BA.debugLine="Private lblDivision As Label";
mostCurrent._lbldivision = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 81;BA.debugLine="Private lblEmpName As Label";
mostCurrent._lblempname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Private lblStatus As Label";
mostCurrent._lblstatus = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 83;BA.debugLine="Private pnlTitle As B4XView";
mostCurrent._pnltitle = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 84;BA.debugLine="Private TitleHeight As Int = 30dip";
_titleheight = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30));
 //BA.debugLineNum = 85;BA.debugLine="Private DividerHeight As Int = 5dip";
_dividerheight = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5));
 //BA.debugLineNum = 87;BA.debugLine="Private CDTxtBox As ColorDrawable";
mostCurrent._cdtxtbox = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 89;BA.debugLine="Private blnNewReg As Boolean";
_blnnewreg = false;
 //BA.debugLineNum = 90;BA.debugLine="Private btnCancel As ACButton";
mostCurrent._btncancel = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 91;BA.debugLine="Private lblSearchTitle As Label";
mostCurrent._lblsearchtitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 92;BA.debugLine="Private pnlSearchEmployees As Panel";
mostCurrent._pnlsearchemployees = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 93;BA.debugLine="Private pnlSearchMain As Panel";
mostCurrent._pnlsearchmain = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 94;BA.debugLine="Dim iSwapBranchID As Int";
_iswapbranchid = 0;
 //BA.debugLineNum = 95;BA.debugLine="Dim iSwapEmpID As Int";
_iswapempid = 0;
 //BA.debugLineNum = 96;BA.debugLine="Dim sSwapEmp As String";
mostCurrent._sswapemp = "";
 //BA.debugLineNum = 97;BA.debugLine="Private SV As SearchView";
mostCurrent._sv = new bwsi.registration.townhall.searchview();
 //BA.debugLineNum = 99;BA.debugLine="Private lblWasAwardee As Label";
mostCurrent._lblwasawardee = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return "";
}
public static String  _pnlsearchmain_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 1313;BA.debugLine="Sub pnlSearchMain_Touch (Action As Int, X As Float";
 //BA.debugLineNum = 1315;BA.debugLine="End Sub";
return "";
}
public static void  _printer_connected(boolean _success) throws Exception{
ResumableSub_Printer_Connected rsub = new ResumableSub_Printer_Connected(null,_success);
rsub.resume(processBA, null);
}
public static class ResumableSub_Printer_Connected extends BA.ResumableSub {
public ResumableSub_Printer_Connected(bwsi.registration.townhall.actregistration parent,boolean _success) {
this.parent = parent;
this._success = _success;
}
bwsi.registration.townhall.actregistration parent;
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
 //BA.debugLineNum = 1053;BA.debugLine="Log(\"Connected: \" & Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("45373953","Connected: "+BA.ObjectToString(_success),0);
 //BA.debugLineNum = 1055;BA.debugLine="If Success = False Then";
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
 //BA.debugLineNum = 1056;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1060;BA.debugLine="StartPrinter";
_startprinter();
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 1063;BA.debugLine="Dim initPrinter() As Byte";
_initprinter = new byte[(int) (0)];
;
 //BA.debugLineNum = 1065;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1066;BA.debugLine="TMPrinter.Initialize2(Serial1.OutputStream, \"win";
parent._tmprinter.Initialize2(parent._serial1.getOutputStream(),"windows-1252");
 //BA.debugLineNum = 1067;BA.debugLine="oStream.Initialize(Serial1.InputStream, Serial1.";
parent._ostream.Initialize(processBA,parent._serial1.getInputStream(),parent._serial1.getOutputStream(),"LogoPrint");
 //BA.debugLineNum = 1068;BA.debugLine="Logo.Initialize(File.DirAssets, \"Stub-Header.png";
parent._logo.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Stub-Header.png");
 //BA.debugLineNum = 1069;BA.debugLine="LogoBMP = CreateScaledBitmap(Logo, Logo.Width, L";
parent._logobmp = _createscaledbitmap(parent._logo,parent._logo.getWidth(),parent._logo.getHeight());
 //BA.debugLineNum = 1070;BA.debugLine="Log(DeviceName)";
anywheresoftware.b4a.keywords.Common.LogImpl("45373970",parent._devicename,0);
 //BA.debugLineNum = 1072;BA.debugLine="WoosimCMD.InitializeStatic(\"com.woosim.printer.W";
parent._woosimcmd.InitializeStatic("com.woosim.printer.WoosimCmd");
 //BA.debugLineNum = 1073;BA.debugLine="WoosimImage.InitializeStatic(\"com.woosim.printer";
parent._woosimimage.InitializeStatic("com.woosim.printer.WoosimImage");
 //BA.debugLineNum = 1075;BA.debugLine="initPrinter = WoosimCMD.RunMethod(\"initPrinter\",";
_initprinter = (byte[])(parent._woosimcmd.RunMethod("initPrinter",(Object[])(anywheresoftware.b4a.keywords.Common.Null)));
 //BA.debugLineNum = 1076;BA.debugLine="PrintLogo = WoosimImage.RunMethod(\"printBitmap\",";
parent._printlogo = (byte[])(parent._woosimimage.RunMethod("printBitmap",new Object[]{(Object)(0),(Object)(0),(Object)(420),(Object)(205),(Object)(parent._logobmp.getObject())}));
 //BA.debugLineNum = 1078;BA.debugLine="oStream.Write(initPrinter)";
parent._ostream.Write(_initprinter);
 //BA.debugLineNum = 1079;BA.debugLine="oStream.Write(WoosimCMD.RunMethod(\"setPageMode\",";
parent._ostream.Write((byte[])(parent._woosimcmd.RunMethod("setPageMode",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 1080;BA.debugLine="oStream.Write(PrintLogo)";
parent._ostream.Write(parent._printlogo);
 //BA.debugLineNum = 1081;BA.debugLine="oStream.Write(WoosimCMD.RunMethod(\"PM_setStdMode";
parent._ostream.Write((byte[])(parent._woosimcmd.RunMethod("PM_setStdMode",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 1082;BA.debugLine="oStream.Write(PrintLogo)";
parent._ostream.Write(parent._printlogo);
 //BA.debugLineNum = 1084;BA.debugLine="Sleep(500)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (500));
this.state = 13;
return;
case 13:
//C
this.state = 6;
;
 //BA.debugLineNum = 1085;BA.debugLine="TMPrinter.WriteLine(PrintBuffer)";
parent._tmprinter.WriteLine(parent._printbuffer);
 //BA.debugLineNum = 1086;BA.debugLine="Log(PrintBuffer)";
anywheresoftware.b4a.keywords.Common.LogImpl("45373986",parent._printbuffer,0);
 //BA.debugLineNum = 1087;BA.debugLine="TMPrinter.Flush";
parent._tmprinter.Flush();
 //BA.debugLineNum = 1088;BA.debugLine="Sleep(600)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (600));
this.state = 14;
return;
case 14:
//C
this.state = 6;
;
 //BA.debugLineNum = 1089;BA.debugLine="If blnNewReg = True Then";
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
 //BA.debugLineNum = 1090;BA.debugLine="ShowSuccessMsg($\"SUCCESS\"$, $\"Stub was successf";
_showsuccessmsg(("SUCCESS"),("Stub was successfully printed.")+anywheresoftware.b4a.keywords.Common.CRLF+("Tap OK to Continue..."));
 if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 1092;BA.debugLine="ShowSuccessMsg($\"REPRINT SUCCESS\"$, $\"Stub was";
_showsuccessmsg(("REPRINT SUCCESS"),("Stub was successfully reprinted.")+anywheresoftware.b4a.keywords.Common.CRLF+("Tap OK to Continue..."));
 if (true) break;

case 11:
//C
this.state = 12;
;
 //BA.debugLineNum = 1095;BA.debugLine="TMPrinter.Close";
parent._tmprinter.Close();
 //BA.debugLineNum = 1096;BA.debugLine="Serial1.Disconnect";
parent._serial1.Disconnect();
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 1098;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _printerbinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 661;BA.debugLine="Private Sub PrinterBinder_OnBindView (View As View";
 //BA.debugLineNum = 662;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 663;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 664;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 665;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 666;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 667;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf071)))+"  "));
 //BA.debugLineNum = 668;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 670;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 673;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 674;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 675;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 676;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 678;BA.debugLine="End Sub";
return "";
}
public static String  _printererror_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 656;BA.debugLine="Private Sub PrinterError_OnPositiveClicked (View A";
 //BA.debugLineNum = 657;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 658;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 659;BA.debugLine="End Sub";
return "";
}
public static String  _printstub(int _iregid) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsdata = null;
String _stubno = "";
String _regfullname = "";
String _regbranchname = "";
String _regdivision = "";
int _attendstatus = 0;
int _wasawardee = 0;
String _sawards = "";
String _sawardsmsg = "";
int _wasguest = 0;
 //BA.debugLineNum = 778;BA.debugLine="Private Sub PrintStub(iRegID As Int)";
 //BA.debugLineNum = 779;BA.debugLine="Dim rsData As Cursor";
_rsdata = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 780;BA.debugLine="Dim StubNo As String";
_stubno = "";
 //BA.debugLineNum = 781;BA.debugLine="Dim RegFullName As String";
_regfullname = "";
 //BA.debugLineNum = 782;BA.debugLine="Dim RegBranchName As String";
_regbranchname = "";
 //BA.debugLineNum = 783;BA.debugLine="Dim RegDivision As String";
_regdivision = "";
 //BA.debugLineNum = 784;BA.debugLine="Dim AttendStatus As Int";
_attendstatus = 0;
 //BA.debugLineNum = 785;BA.debugLine="Dim WasAwardee As Int";
_wasawardee = 0;
 //BA.debugLineNum = 786;BA.debugLine="Dim sAwards As String";
_sawards = "";
 //BA.debugLineNum = 787;BA.debugLine="Dim sAwardsMsg As String";
_sawardsmsg = "";
 //BA.debugLineNum = 789;BA.debugLine="Dim WasGuest As Int";
_wasguest = 0;
 //BA.debugLineNum = 791;BA.debugLine="ProgressDialogShow2($\"Stub Printing.  Please Wait";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Stub Printing.  Please Wait...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 793;BA.debugLine="Try";
try { //BA.debugLineNum = 794;BA.debugLine="Starter.strCriteria = \"SELECT Employees.RegNo, E";
mostCurrent._starter._strcriteria /*String*/  = "SELECT Employees.RegNo, Employees.FullName, "+"Branches.BranchName, Employees.Division, "+"Employees.WillAttend AS AttendStatus, Employees.WasAwardee, Employees.Awards, Employees.WasGuest "+"FROM tblRegistration AS Employees "+"INNER JOIN tblBranches AS Branches ON Employees.BranchID = Branches.BranchID "+"WHERE Employees.RegID = "+BA.NumberToString(_iregid)+" "+"AND Employees.WasRegistered = 1";
 //BA.debugLineNum = 801;BA.debugLine="rsData = Starter.DBCon.ExecQuery(Starter.strCrit";
_rsdata = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 802;BA.debugLine="LogColor(Starter.strCriteria, Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("45242904",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 804;BA.debugLine="If rsData.RowCount > 0 Then";
if (_rsdata.getRowCount()>0) { 
 //BA.debugLineNum = 805;BA.debugLine="rsData.Position = 0";
_rsdata.setPosition((int) (0));
 //BA.debugLineNum = 806;BA.debugLine="StubNo = rsData.GetString(\"RegNo\")";
_stubno = _rsdata.GetString("RegNo");
 //BA.debugLineNum = 807;BA.debugLine="RegFullName = rsData.GetString(\"FullName\")";
_regfullname = _rsdata.GetString("FullName");
 //BA.debugLineNum = 808;BA.debugLine="RegBranchName = rsData.GetString(\"BranchName\")";
_regbranchname = _rsdata.GetString("BranchName");
 //BA.debugLineNum = 809;BA.debugLine="RegDivision = rsData.GetString(\"Division\")";
_regdivision = _rsdata.GetString("Division");
 //BA.debugLineNum = 810;BA.debugLine="AttendStatus = rsData.GetInt(\"AttendStatus\")";
_attendstatus = _rsdata.GetInt("AttendStatus");
 //BA.debugLineNum = 811;BA.debugLine="WasAwardee = rsData.GetInt(\"WasAwardee\")";
_wasawardee = _rsdata.GetInt("WasAwardee");
 //BA.debugLineNum = 812;BA.debugLine="sAwards = rsData.GetString(\"Awards\")";
_sawards = _rsdata.GetString("Awards");
 //BA.debugLineNum = 813;BA.debugLine="WasGuest = rsData.GetInt(\"WasGuest\")";
_wasguest = _rsdata.GetInt("WasGuest");
 }else {
 //BA.debugLineNum = 815;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 818;BA.debugLine="If WasAwardee = 1 Then";
if (_wasawardee==1) { 
 //BA.debugLineNum = 819;BA.debugLine="If sAwards = \"10 YEARS\" Then";
if ((_sawards).equals("10 YEARS")) { 
 //BA.debugLineNum = 820;BA.debugLine="sAwardsMsg = \"Thank you for Amazing DECADE of";
_sawardsmsg = "Thank you for Amazing DECADE of service!";
 }else if((_sawards).equals("15 YEARS")) { 
 //BA.debugLineNum = 822;BA.debugLine="sAwardsMsg = \"Thank you for your 15 Years of S";
_sawardsmsg = "Thank you for your 15 Years of Service!";
 }else if((_sawards).equals("20 YEARS")) { 
 //BA.debugLineNum = 824;BA.debugLine="sAwardsMsg = \"Thank you for your 20 Years of S";
_sawardsmsg = "Thank you for your 20 Years of Service!";
 }else if((_sawards).equals("25 YEARS")) { 
 //BA.debugLineNum = 826;BA.debugLine="sAwardsMsg = \"Thank you for your 25 Years of S";
_sawardsmsg = "Thank you for your 25 Years of Service!";
 };
 };
 //BA.debugLineNum = 830;BA.debugLine="If WasGuest = 1 Then 'Guest Only";
if (_wasguest==1) { 
 //BA.debugLineNum = 831;BA.debugLine="PrintBuffer = Chr(27) & \"@\" _ 					& Chr(27) &";
_printbuffer = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"@"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+("LAUSGROUP EVENT CENTRE")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+("December 19, 2023")+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("STUB NO.: ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+("Have a Blessed Holiday Season!")+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("DINNER STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 }else if(_attendstatus==1) { 
 //BA.debugLineNum = 849;BA.debugLine="If WasAwardee = 1 Then";
if (_wasawardee==1) { 
 //BA.debugLineNum = 850;BA.debugLine="PrintBuffer = Chr(27) & \"@\" _ 					& Chr(27) &";
_printbuffer = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"@"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+("LAUSGROUP EVENT CENTRE")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+("December 19, 2023")+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("STUB NO.: ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regdivision+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (32)))+_sawardsmsg+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+("Have a Blessed Holiday Season!")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("DINNER STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("RAFFLE STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regdivision+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_sawards+(" LOYALTY AWARDEE")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("TOKEN STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regdivision+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_sawards+(" LOYALTY AWARDEE")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 }else {
 //BA.debugLineNum = 890;BA.debugLine="PrintBuffer = Chr(27) & \"@\" _ 					& Chr(27) &";
_printbuffer = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"@"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+("LAUSGROUP EVENT CENTRE")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+("December 19, 2023")+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("STUB NO.: ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regdivision+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+("Have a Blessed Holiday Season!")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("DINNER STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("RAFFLE STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regdivision+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("TOKEN STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regdivision+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 }else if(_attendstatus==2) { 
 //BA.debugLineNum = 929;BA.debugLine="If WasAwardee = 1 Then";
if (_wasawardee==1) { 
 //BA.debugLineNum = 930;BA.debugLine="PrintBuffer = Chr(27) & \"@\" _ 					& Chr(27) &";
_printbuffer = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"@"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+("LAUSGROUP EVENT CENTRE")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+("December 19, 2023")+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("STUB NO.: ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regdivision+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (32)))+_sawardsmsg+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Have a Blessed Holiday Season!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("RAFFLE STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regdivision+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_sawards+" LOYALTY AWARDEE"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("TOKEN STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regdivision+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_sawards+" LOYALTY AWARDEE"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 }else {
 //BA.debugLineNum = 963;BA.debugLine="PrintBuffer = Chr(27) & \"@\" _ 					& Chr(27) &";
_printbuffer = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"@"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+("LAUSGROUP EVENT CENTRE")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+("December 19, 2023")+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("STUB NO.: ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regdivision+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+("Have a Blessed Holiday Season!")+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("RAFFLE STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regdivision+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+("TOKEN STUB")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (112)))+_stubno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+_regfullname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+_regdivision+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 };
 //BA.debugLineNum = 994;BA.debugLine="StartPrinter";
_startprinter();
 } 
       catch (Exception e57) {
			processBA.setLastException(e57); //BA.debugLineNum = 996;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 997;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("45243099",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 999;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 615;BA.debugLine="Private Sub RegFontSizeBinder_OnBindView (View As";
 //BA.debugLineNum = 616;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 617;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 618;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 619;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 620;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 621;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf059)))+"  "));
 //BA.debugLineNum = 622;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 624;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 627;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 628;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 629;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 630;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 632;BA.debugLine="End Sub";
return "";
}
public static boolean  _registeremp(int _iregid) throws Exception{
boolean _bretval = false;
long _lngdatetime = 0L;
String _timereg = "";
int _regseq = 0;
int _noprint = 0;
 //BA.debugLineNum = 1120;BA.debugLine="Private Sub RegisterEmp(iRegID As Int) As Boolean";
 //BA.debugLineNum = 1121;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 1122;BA.debugLine="Dim lngDateTime As Long";
_lngdatetime = 0L;
 //BA.debugLineNum = 1123;BA.debugLine="Dim TimeReg As String";
_timereg = "";
 //BA.debugLineNum = 1124;BA.debugLine="Dim RegSeq, NoPrint As Int";
_regseq = 0;
_noprint = 0;
 //BA.debugLineNum = 1126;BA.debugLine="lngDateTime = DateTime.Now";
_lngdatetime = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 1127;BA.debugLine="DateTime.TimeFormat = \"HH:mm:ss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("HH:mm:ss");
 //BA.debugLineNum = 1128;BA.debugLine="TimeReg = DateTime.Time(lngDateTime)";
_timereg = anywheresoftware.b4a.keywords.Common.DateTime.Time(_lngdatetime);
 //BA.debugLineNum = 1129;BA.debugLine="RegSeq = DBFunctions.GetSeqNo";
_regseq = mostCurrent._dbfunctions._getseqno /*int*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 1131;BA.debugLine="NoPrint = GetNoOfStubPrint(iRegID)";
_noprint = _getnoofstubprint(_iregid);
 //BA.debugLineNum = 1132;BA.debugLine="NoPrint = NoPrint + 1";
_noprint = (int) (_noprint+1);
 //BA.debugLineNum = 1134;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 1135;BA.debugLine="Try";
try { //BA.debugLineNum = 1136;BA.debugLine="Starter.strCriteria = \"UPDATE tblRegistration \"";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblRegistration "+"SET WasRegistered = ?, TimeRegistered = ?, "+"RegSeq = ?, WasStubPrint = ?, NoPrintStub = ? "+"WHERE RegID = "+BA.NumberToString(_iregid);
 //BA.debugLineNum = 1141;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{("1"),_timereg,BA.NumberToString(_regseq),("1"),BA.NumberToString(_noprint)}));
 //BA.debugLineNum = 1144;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 1145;BA.debugLine="ProgressDialogShow2($\"Preparing Stub Printing\"$,";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Preparing Stub Printing")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1146;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e19) {
			processBA.setLastException(e19); //BA.debugLineNum = 1148;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1149;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1150;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("45570590",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1152;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 1153;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 1154;BA.debugLine="End Sub";
return false;
}
public static String  _registeremp_onnegativeclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 511;BA.debugLine="Private Sub RegisterEmp_OnNegativeClicked (View As";
 //BA.debugLineNum = 512;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 513;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 514;BA.debugLine="ToastMessageShow($\"Canceled!\"$, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Canceled!")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 515;BA.debugLine="End Sub";
return "";
}
public static String  _registeremp_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 517;BA.debugLine="Private Sub RegisterEmp_OnPositiveClicked (View As";
 //BA.debugLineNum = 518;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 519;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 520;BA.debugLine="blnNewReg = True";
_blnnewreg = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 522;BA.debugLine="If Not(RegisterEmp(RegID)) Then Return";
if (anywheresoftware.b4a.keywords.Common.Not(_registeremp(_regid))) { 
if (true) return "";};
 //BA.debugLineNum = 523;BA.debugLine="PrintStub(RegID)";
_printstub(_regid);
 //BA.debugLineNum = 524;BA.debugLine="ToastMessageShow($\"Registered!\"$, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Registered!")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 525;BA.debugLine="AddBranches(GlobalVar.AreaID)";
_addbranches(mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 526;BA.debugLine="End Sub";
return "";
}
public static String  _searchclosed() throws Exception{
 //BA.debugLineNum = 1321;BA.debugLine="Sub SearchClosed";
 //BA.debugLineNum = 1322;BA.debugLine="SV.ClearSearchBox";
mostCurrent._sv._clearsearchbox /*String*/ ();
 //BA.debugLineNum = 1323;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 1324;BA.debugLine="pnlSearchMain.Visible = False";
mostCurrent._pnlsearchmain.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1325;BA.debugLine="End Sub";
return "";
}
public static String  _searchemp(int _iareaid,String _ssearch) throws Exception{
int _ibranchid = 0;
String _sbranchname = "";
long _starttime = 0L;
int _x = 0;
int _i = 0;
bwsi.registration.townhall.actregistration._empdata _ed = null;
 //BA.debugLineNum = 407;BA.debugLine="Private Sub SearchEmp(iAreaID As Int, sSearch As S";
 //BA.debugLineNum = 408;BA.debugLine="Dim iBranchID As Int";
_ibranchid = 0;
 //BA.debugLineNum = 409;BA.debugLine="Dim sBranchName As String";
_sbranchname = "";
 //BA.debugLineNum = 411;BA.debugLine="clvEmployees.Clear";
mostCurrent._clvemployees._clear();
 //BA.debugLineNum = 412;BA.debugLine="Try";
try { //BA.debugLineNum = 413;BA.debugLine="Starter.strCriteria = \"SELECT DISTINCT Employees";
mostCurrent._starter._strcriteria /*String*/  = "SELECT DISTINCT Employees.BranchID, Branches.BranchName "+"FROM tblBranches AS Branches "+"INNER JOIN tblRegistration AS Employees ON Branches.BranchID = Employees.BranchID "+"WHERE Branches.AreaID = "+BA.NumberToString(_iareaid)+" "+"AND Employees.FullName LIKE '%"+_ssearch+"%' "+"ORDER BY Branches.BranchID ASC";
 //BA.debugLineNum = 420;BA.debugLine="LogColor(Starter.strCriteria, Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("43866637",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 421;BA.debugLine="rsBranch = Starter.DBCon.ExecQuery(Starter.strCr";
mostCurrent._rsbranch = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 423;BA.debugLine="If rsBranch.RowCount > 0 Then";
if (mostCurrent._rsbranch.getRowCount()>0) { 
 //BA.debugLineNum = 424;BA.debugLine="Dim StartTime As Long = DateTime.Now";
_starttime = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 425;BA.debugLine="For x = 0 To rsBranch.RowCount - 1";
{
final int step10 = 1;
final int limit10 = (int) (mostCurrent._rsbranch.getRowCount()-1);
_x = (int) (0) ;
for (;_x <= limit10 ;_x = _x + step10 ) {
 //BA.debugLineNum = 426;BA.debugLine="rsBranch.Position = x";
mostCurrent._rsbranch.setPosition(_x);
 //BA.debugLineNum = 427;BA.debugLine="iBranchID = rsBranch.GetInt(\"BranchID\")";
_ibranchid = mostCurrent._rsbranch.GetInt("BranchID");
 //BA.debugLineNum = 428;BA.debugLine="sBranchName = rsBranch.GetString(\"BranchName\")";
_sbranchname = mostCurrent._rsbranch.GetString("BranchName");
 //BA.debugLineNum = 430;BA.debugLine="AddBranch(sBranchName)";
_addbranch(_sbranchname);
 //BA.debugLineNum = 432;BA.debugLine="Starter.strCriteria = \"SELECT Registration.Reg";
mostCurrent._starter._strcriteria /*String*/  = "SELECT Registration.RegID, Registration.FirstName, Registration.LastName, Registration.FullName, Branches.BranchName, "+"Registration.Division, Registration.WillAttend, Registration.WasRegistered, Registration.WasAwardee, Registration.Awards "+"FROM tblRegistration AS Registration "+"INNER JOIN tblBranches AS Branches ON Registration.BranchID = Branches.BranchID "+"WHERE Registration.BranchID = "+BA.NumberToString(_ibranchid)+" "+"AND Registration.WillAttend <> 0 "+"AND Registration.FullName LIKE '%"+_ssearch+"%' "+"ORDER BY Registration.Division, Registration.FullName ASC";
 //BA.debugLineNum = 441;BA.debugLine="LogColor(Starter.strCriteria, Colors.Cyan)";
anywheresoftware.b4a.keywords.Common.LogImpl("43866658",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 442;BA.debugLine="rsEmployees = Starter.DBCon.ExecQuery(Starter.";
mostCurrent._rsemployees = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 444;BA.debugLine="If rsEmployees.RowCount > 0 Then";
if (mostCurrent._rsemployees.getRowCount()>0) { 
 //BA.debugLineNum = 445;BA.debugLine="For i = 0 To rsEmployees.RowCount - 1";
{
final int step19 = 1;
final int limit19 = (int) (mostCurrent._rsemployees.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit19 ;_i = _i + step19 ) {
 //BA.debugLineNum = 446;BA.debugLine="rsEmployees.Position = i";
mostCurrent._rsemployees.setPosition(_i);
 //BA.debugLineNum = 447;BA.debugLine="Dim ED As EmpData";
_ed = new bwsi.registration.townhall.actregistration._empdata();
 //BA.debugLineNum = 448;BA.debugLine="ED.RegistrationID = rsEmployees.GetInt(\"RegI";
_ed.RegistrationID /*int*/  = mostCurrent._rsemployees.GetInt("RegID");
 //BA.debugLineNum = 449;BA.debugLine="ED.Avatar = GlobalVar.SF.Upper(GlobalVar.SF.";
_ed.Avatar /*String*/  = mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv6(mostCurrent._rsemployees.GetString("FirstName"),(long) (1)))+mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv6(mostCurrent._rsemployees.GetString("LastName"),(long) (1)));
 //BA.debugLineNum = 450;BA.debugLine="ED.EmpName = rsEmployees.GetString(\"FullName";
_ed.EmpName /*String*/  = mostCurrent._rsemployees.GetString("FullName");
 //BA.debugLineNum = 451;BA.debugLine="ED.EmpDivision = rsEmployees.GetString(\"Divi";
_ed.EmpDivision /*String*/  = mostCurrent._rsemployees.GetString("Division");
 //BA.debugLineNum = 452;BA.debugLine="ED.WillAttend = rsEmployees.GetInt(\"WillAtte";
_ed.WillAttend /*int*/  = mostCurrent._rsemployees.GetInt("WillAttend");
 //BA.debugLineNum = 453;BA.debugLine="ED.RegStatus = rsEmployees.GetInt(\"WasRegist";
_ed.RegStatus /*int*/  = mostCurrent._rsemployees.GetInt("WasRegistered");
 //BA.debugLineNum = 454;BA.debugLine="ED.WasAwardee = rsEmployees.GetInt(\"WasAward";
_ed.WasAwardee /*int*/  = mostCurrent._rsemployees.GetInt("WasAwardee");
 //BA.debugLineNum = 455;BA.debugLine="ED.sAwards = rsEmployees.GetString(\"Awards\")";
_ed.sAwards /*String*/  = mostCurrent._rsemployees.GetString("Awards");
 //BA.debugLineNum = 456;BA.debugLine="clvEmployees.Add(AddEmployees(clvEmployees.A";
mostCurrent._clvemployees._add((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_addemployees(mostCurrent._clvemployees._asview().getWidth(),_ed.EmpName /*String*/ ,_ed.EmpDivision /*String*/ ,_ed.RegStatus /*int*/ ,_ed.WillAttend /*int*/ ,_ed.Avatar /*String*/ ,_ed.WasAwardee /*int*/ ,_ed.sAwards /*String*/ ).getObject())),(Object)(mostCurrent._rsemployees.GetInt("RegID")));
 }
};
 }else {
 //BA.debugLineNum = 459;BA.debugLine="Log(rsEmployees.RowCount)";
anywheresoftware.b4a.keywords.Common.LogImpl("43866676",BA.NumberToString(mostCurrent._rsemployees.getRowCount()),0);
 };
 }
};
 }else {
 //BA.debugLineNum = 464;BA.debugLine="clvEmployees.Clear";
mostCurrent._clvemployees._clear();
 };
 //BA.debugLineNum = 467;BA.debugLine="Log($\"List of Time Records = ${NumberFormat2((Da";
anywheresoftware.b4a.keywords.Common.LogImpl("43866684",("List of Time Records = "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(anywheresoftware.b4a.keywords.Common.NumberFormat2((anywheresoftware.b4a.keywords.Common.DateTime.getNow()-_starttime)/(double)1000,(int) (0),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False)))+" seconds to populate "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(mostCurrent._clvemployees._getsize()))+" Time Records"),0);
 } 
       catch (Exception e41) {
			processBA.setLastException(e41); //BA.debugLineNum = 470;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("43866687",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 472;BA.debugLine="End Sub";
return "";
}
public static String  _searchswapemployees(int _ibranchid) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsswapemployees = null;
anywheresoftware.b4a.objects.collections.List _searchlist = null;
int _i = 0;
bwsi.registration.townhall.searchview._item _it = null;
 //BA.debugLineNum = 1216;BA.debugLine="Private Sub SearchSwapEmployees(iBranchID As Int)";
 //BA.debugLineNum = 1217;BA.debugLine="Dim rsSwapEmployees As Cursor";
_rsswapemployees = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 1218;BA.debugLine="Dim SearchList As List";
_searchlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1220;BA.debugLine="If SV.IsInitialized=False Then";
if (mostCurrent._sv.IsInitialized /*boolean*/ ()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1221;BA.debugLine="SV.Initialize(Me,\"SV\")";
mostCurrent._sv._initialize /*String*/ (mostCurrent.activityBA,actregistration.getObject(),"SV");
 };
 //BA.debugLineNum = 1224;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 1225;BA.debugLine="SV.ClearSearchBox";
mostCurrent._sv._clearsearchbox /*String*/ ();
 //BA.debugLineNum = 1226;BA.debugLine="SV.lv.Clear";
mostCurrent._sv._lv /*anywheresoftware.b4a.objects.ListViewWrapper*/ .Clear();
 //BA.debugLineNum = 1228;BA.debugLine="SearchList.Initialize";
_searchlist.Initialize();
 //BA.debugLineNum = 1229;BA.debugLine="SearchList.Clear";
_searchlist.Clear();
 //BA.debugLineNum = 1230;BA.debugLine="Try";
try { //BA.debugLineNum = 1231;BA.debugLine="Starter.strCriteria = \"SELECT tblRegistration.Re";
mostCurrent._starter._strcriteria /*String*/  = "SELECT tblRegistration.RegID, tblRegistration.FullName, tblBranches.BranchName "+"FROM tblRegistration INNER JOIN tblBranches ON tblRegistration.BranchID = tblBranches.BranchID "+"WHERE tblRegistration.WillAttend = 1 "+"AND tblRegistration.WasRegistered = 0 "+"AND tblRegistration.BranchID = "+BA.NumberToString(_ibranchid);
 //BA.debugLineNum = 1237;BA.debugLine="LogColor(Starter.strCriteria, Colors.Cyan)";
anywheresoftware.b4a.keywords.Common.LogImpl("45832725",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 1239;BA.debugLine="rsSwapEmployees = Starter.DBCon.ExecQuery(Starte";
_rsswapemployees = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 1241;BA.debugLine="If rsSwapEmployees.RowCount > 0 Then";
if (_rsswapemployees.getRowCount()>0) { 
 //BA.debugLineNum = 1242;BA.debugLine="For i = 0 To rsSwapEmployees.RowCount - 1";
{
final int step16 = 1;
final int limit16 = (int) (_rsswapemployees.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit16 ;_i = _i + step16 ) {
 //BA.debugLineNum = 1243;BA.debugLine="rsSwapEmployees.Position = i";
_rsswapemployees.setPosition(_i);
 //BA.debugLineNum = 1244;BA.debugLine="Dim it As Item";
_it = new bwsi.registration.townhall.searchview._item();
 //BA.debugLineNum = 1245;BA.debugLine="it.Title=rsSwapEmployees.GetString(\"FullName\")";
_it.Title /*String*/  = _rsswapemployees.GetString("FullName");
 //BA.debugLineNum = 1246;BA.debugLine="it.Text=rsSwapEmployees.GetString(\"BranchName\"";
_it.Text /*String*/  = _rsswapemployees.GetString("BranchName");
 //BA.debugLineNum = 1247;BA.debugLine="it.SearchText=rsSwapEmployees.GetString(\"FullN";
_it.SearchText /*String*/  = _rsswapemployees.GetString("FullName").toLowerCase();
 //BA.debugLineNum = 1248;BA.debugLine="it.Value=rsSwapEmployees.GetInt(\"RegID\")";
_it.Value /*Object*/  = (Object)(_rsswapemployees.GetInt("RegID"));
 //BA.debugLineNum = 1249;BA.debugLine="SearchList.Add(it)";
_searchlist.Add((Object)(_it));
 }
};
 }else {
 //BA.debugLineNum = 1252;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1254;BA.debugLine="SV.SetItems(SearchList)";
mostCurrent._sv._setitems /*Object*/ (_searchlist);
 //BA.debugLineNum = 1255;BA.debugLine="SearchList.Clear";
_searchlist.Clear();
 } 
       catch (Exception e31) {
			processBA.setLastException(e31); //BA.debugLineNum = 1257;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("45832745",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1259;BA.debugLine="End Sub";
return "";
}
public static String  _setbuttoncolors() throws Exception{
 //BA.debugLineNum = 378;BA.debugLine="Private Sub SetButtonColors()";
 //BA.debugLineNum = 379;BA.debugLine="btnSwap.Background = CreateButtonColor(0xFF0D47A1";
mostCurrent._btnswap.setBackground((android.graphics.drawable.Drawable)(_createbuttoncolor((int) (0xff0d47a1),(int) (0xff0d47a1),(int) (0xff1e88e5),(int) (0xff0d47a1)).getObject()));
 //BA.debugLineNum = 380;BA.debugLine="btnRegister.Background = CreateButtonColor(0xFF0D";
mostCurrent._btnregister.setBackground((android.graphics.drawable.Drawable)(_createbuttoncolor((int) (0xff0d47a1),(int) (0xff0d47a1),(int) (0xff1e88e5),(int) (0xff0d47a1)).getObject()));
 //BA.debugLineNum = 381;BA.debugLine="End Sub";
return "";
}
public static int  _shadecolor(int _clr) throws Exception{
int[] _argb = null;
float _factor = 0f;
 //BA.debugLineNum = 227;BA.debugLine="Sub ShadeColor(clr As Int) As Int";
 //BA.debugLineNum = 228;BA.debugLine="Dim argb() As Int = GetARGB(clr)";
_argb = _getargb(_clr);
 //BA.debugLineNum = 229;BA.debugLine="Dim factor As Float = 0.75";
_factor = (float) (0.75);
 //BA.debugLineNum = 230;BA.debugLine="Return xui.Color_RGB(argb(1) * factor, argb(2) *";
if (true) return _xui.Color_RGB((int) (_argb[(int) (1)]*_factor),(int) (_argb[(int) (2)]*_factor),(int) (_argb[(int) (3)]*_factor));
 //BA.debugLineNum = 231;BA.debugLine="End Sub";
return 0;
}
public static String  _showaddperson() throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.collections.List _items = null;
 //BA.debugLineNum = 728;BA.debugLine="Private Sub ShowAddPerson";
 //BA.debugLineNum = 729;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 731;BA.debugLine="Dim items As List";
_items = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 732;BA.debugLine="items.Initialize";
_items.Initialize();
 //BA.debugLineNum = 733;BA.debugLine="items.Add(\"New Employee\")";
_items.Add((Object)("New Employee"));
 //BA.debugLineNum = 734;BA.debugLine="items.Add(\"Guest\")";
_items.Add((Object)("Guest"));
 //BA.debugLineNum = 736;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialog").SetStyle(_alert.getSTYLE_ACTIONSHEET()).SetTitle("Select an Option").SetTitleColor(anywheresoftware.b4a.keywords.Common.Colors.Black).SetCancelText("Cancel").SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOthers((java.util.ArrayList)(_items.getObject())).SetActionsheetTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnCancelClicked(mostCurrent.activityBA,"AddPeople").SetOnItemClickListener(mostCurrent.activityBA,"AddPeople");
 //BA.debugLineNum = 750;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD).SetCanc";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject())).SetCancelBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 751;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 752;BA.debugLine="End Sub";
return "";
}
public static String  _showprintererror(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 634;BA.debugLine="Private Sub ShowPrinterError(sTitle As String, sMs";
 //BA.debugLineNum = 635;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 637;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetMessage(_smsg).SetPositiveText("OK").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"PrinterError").SetOnViewBinder(mostCurrent.activityBA,"PrinterBinder");
 //BA.debugLineNum = 651;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 652;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 654;BA.debugLine="End Sub";
return "";
}
public static String  _showsuccessmsg(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 680;BA.debugLine="Private Sub ShowSuccessMsg(sTitle As String, sMsg";
 //BA.debugLineNum = 681;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 683;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetMessage(_smsg).SetPositiveText("OK").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"SuccessMsg").SetOnViewBinder(mostCurrent.activityBA,"SuccessBinder");
 //BA.debugLineNum = 697;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 698;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 700;BA.debugLine="End Sub";
return "";
}
public static String  _startprinter() throws Exception{
int _i = 0;
 //BA.debugLineNum = 1001;BA.debugLine="Sub StartPrinter";
 //BA.debugLineNum = 1003;BA.debugLine="PairedDevices.Initialize";
_paireddevices.Initialize();
 //BA.debugLineNum = 1005;BA.debugLine="Try";
try { //BA.debugLineNum = 1006;BA.debugLine="PairedDevices = Serial1.GetPairedDevices";
_paireddevices = _serial1.GetPairedDevices();
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 1008;BA.debugLine="ShowPrinterError($\"PRINTER ERROR\"$, $\"Getting Pa";
_showprintererror(("PRINTER ERROR"),("Getting Paired Devices..."));
 //BA.debugLineNum = 1009;BA.debugLine="TMPrinter.Close";
_tmprinter.Close();
 //BA.debugLineNum = 1010;BA.debugLine="Serial1.Disconnect";
_serial1.Disconnect();
 };
 //BA.debugLineNum = 1013;BA.debugLine="If PairedDevices.Size = 0 Then";
if (_paireddevices.getSize()==0) { 
 //BA.debugLineNum = 1014;BA.debugLine="ShowPrinterError($\"PRINTER ERROR\"$, $\"Error Conn";
_showprintererror(("PRINTER ERROR"),("Error Connecting to Printer - Either No Paired Bluetooth Printer or Printer Not Found!"));
 //BA.debugLineNum = 1015;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1018;BA.debugLine="If PairedDevices.Size = 1 Then";
if (_paireddevices.getSize()==1) { 
 //BA.debugLineNum = 1019;BA.debugLine="Try";
try { //BA.debugLineNum = 1020;BA.debugLine="DeviceName=PairedDevices.Getkeyat(0)";
_devicename = BA.ObjectToString(_paireddevices.GetKeyAt((int) (0)));
 //BA.debugLineNum = 1021;BA.debugLine="DeviceMac=PairedDevices.GetValueAt(0)";
_devicemac = BA.ObjectToString(_paireddevices.GetValueAt((int) (0)));
 //BA.debugLineNum = 1022;BA.debugLine="Log(DeviceName & \" -> \" & DeviceMac)";
anywheresoftware.b4a.keywords.Common.LogImpl("45308437",_devicename+" -> "+_devicemac,0);
 //BA.debugLineNum = 1024;BA.debugLine="Serial1.ConnectInsecure(BTAdmin, DeviceMac,1)";
_serial1.ConnectInsecure(processBA,_btadmin,_devicemac,(int) (1));
 //BA.debugLineNum = 1025;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 } 
       catch (Exception e21) {
			processBA.setLastException(e21); //BA.debugLineNum = 1027;BA.debugLine="ShowPrinterError($\"PRINTER ERROR\"$, $\"Printer C";
_showprintererror(("PRINTER ERROR"),("Printer Connection Error..."));
 //BA.debugLineNum = 1028;BA.debugLine="TMPrinter.Close";
_tmprinter.Close();
 //BA.debugLineNum = 1029;BA.debugLine="Serial1.Disconnect";
_serial1.Disconnect();
 };
 }else {
 //BA.debugLineNum = 1032;BA.debugLine="FoundDevices.Initialize";
_founddevices.Initialize();
 //BA.debugLineNum = 1034;BA.debugLine="For i = 0 To PairedDevices.Size - 1";
{
final int step27 = 1;
final int limit27 = (int) (_paireddevices.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit27 ;_i = _i + step27 ) {
 //BA.debugLineNum = 1035;BA.debugLine="FoundDevices.Add(PairedDevices.GetKeyAt(i))";
_founddevices.Add(_paireddevices.GetKeyAt(_i));
 //BA.debugLineNum = 1036;BA.debugLine="DeviceName=PairedDevices.Getkeyat(i)";
_devicename = BA.ObjectToString(_paireddevices.GetKeyAt(_i));
 //BA.debugLineNum = 1037;BA.debugLine="DeviceMac=PairedDevices.GetValueAt(i)";
_devicemac = BA.ObjectToString(_paireddevices.GetValueAt(_i));
 //BA.debugLineNum = 1038;BA.debugLine="Log(DeviceName & \" -> \" & DeviceMac)";
anywheresoftware.b4a.keywords.Common.LogImpl("45308453",_devicename+" -> "+_devicemac,0);
 //BA.debugLineNum = 1039;BA.debugLine="Serial1.ConnectInsecure(BTAdmin, DeviceMac,1)";
_serial1.ConnectInsecure(processBA,_btadmin,_devicemac,(int) (1));
 //BA.debugLineNum = 1040;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1041;BA.debugLine="Exit";
if (true) break;
 }
};
 //BA.debugLineNum = 1044;BA.debugLine="Res = InputList(FoundDevices, \"Choose Device\", -";
_res = anywheresoftware.b4a.keywords.Common.InputList(_founddevices,BA.ObjectToCharSequence("Choose Device"),(int) (-1),mostCurrent.activityBA);
 //BA.debugLineNum = 1046;BA.debugLine="If Res <> DialogResponse.CANCEL Then";
if (_res!=anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 1047;BA.debugLine="Serial1.Connect(PairedDevices.Get(FoundDevices.";
_serial1.Connect(processBA,BA.ObjectToString(_paireddevices.Get(_founddevices.Get(_res))));
 };
 };
 //BA.debugLineNum = 1050;BA.debugLine="End Sub";
return "";
}
public static String  _stubreprint_onnegativeclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 555;BA.debugLine="Private Sub StubReprint_OnNegativeClicked (View As";
 //BA.debugLineNum = 556;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 557;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 558;BA.debugLine="ToastMessageShow($\"Reprinting Cancelled!\"$, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Reprinting Cancelled!")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 559;BA.debugLine="End Sub";
return "";
}
public static String  _stubreprint_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 561;BA.debugLine="Private Sub StubReprint_OnPositiveClicked (View As";
 //BA.debugLineNum = 562;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 563;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 565;BA.debugLine="blnNewReg = False";
_blnnewreg = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 567;BA.debugLine="If Not(UpdateReprintStub(RegID)) Then Return";
if (anywheresoftware.b4a.keywords.Common.Not(_updatereprintstub(_regid))) { 
if (true) return "";};
 //BA.debugLineNum = 568;BA.debugLine="PrintStub(RegID)";
_printstub(_regid);
 //BA.debugLineNum = 569;BA.debugLine="ToastMessageShow($\"Reprinted!\"$, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Reprinted!")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 570;BA.debugLine="AddBranches(GlobalVar.AreaID)";
_addbranches(mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 571;BA.debugLine="End Sub";
return "";
}
public static String  _successbinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 709;BA.debugLine="Private Sub SuccessBinder_OnBindView (View As View";
 //BA.debugLineNum = 710;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 711;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 712;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 713;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 714;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 715;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color((int) (mostCurrent._globalvar._poscolor /*double*/ )).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf164)))+"  "));
 //BA.debugLineNum = 716;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 718;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 721;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 722;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 723;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 724;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 726;BA.debugLine="End Sub";
return "";
}
public static String  _successmsg_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 702;BA.debugLine="Private Sub SuccessMsg_OnPositiveClicked (View As";
 //BA.debugLineNum = 703;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 704;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 705;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtSearch.T";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtsearch.getText()))>0) { 
mostCurrent._txtsearch.setText(BA.ObjectToCharSequence(""));};
 //BA.debugLineNum = 707;BA.debugLine="End Sub";
return "";
}
public static String  _sv_itemclick(int _value) throws Exception{
 //BA.debugLineNum = 1274;BA.debugLine="Sub SV_ItemClick(Value As Int)";
 //BA.debugLineNum = 1275;BA.debugLine="Log(Value)";
anywheresoftware.b4a.keywords.Common.LogImpl("45963777",BA.NumberToString(_value),0);
 //BA.debugLineNum = 1276;BA.debugLine="iSwapEmpID = Value";
_iswapempid = _value;
 //BA.debugLineNum = 1277;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 1278;BA.debugLine="SearchClosed";
_searchclosed();
 //BA.debugLineNum = 1279;BA.debugLine="sSwapEmp = GetSwapEmployeeName(iSwapEmpID)";
mostCurrent._sswapemp = _getswapemployeename(_iswapempid);
 //BA.debugLineNum = 1280;BA.debugLine="If sSwapEmp = \"\" Then Return";
if ((mostCurrent._sswapemp).equals("")) { 
if (true) return "";};
 //BA.debugLineNum = 1281;BA.debugLine="ConfirmSwapEmp(sSwapEmp)";
_confirmswapemp(mostCurrent._sswapemp);
 //BA.debugLineNum = 1282;BA.debugLine="End Sub";
return "";
}
public static String  _swapemp_onnegativeclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 599;BA.debugLine="Private Sub SwapEmp_OnNegativeClicked (View As Vie";
 //BA.debugLineNum = 600;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 601;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 602;BA.debugLine="ToastMessageShow($\"Canceled!\"$, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Canceled!")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 603;BA.debugLine="End Sub";
return "";
}
public static String  _swapemp_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 605;BA.debugLine="Private Sub SwapEmp_OnPositiveClicked (View As Vie";
 //BA.debugLineNum = 606;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 607;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 608;BA.debugLine="If UpdateSwappedEmp(iSwapEmpID, RegID) = False Th";
if (_updateswappedemp(_iswapempid,_regid)==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 609;BA.debugLine="If RegisterEmp(RegID) = False Then Return";
if (_registeremp(_regid)==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 610;BA.debugLine="PrintStub(RegID)";
_printstub(_regid);
 //BA.debugLineNum = 611;BA.debugLine="AddBranches(GlobalVar.AreaID)";
_addbranches(mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 612;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_menuitemclick(de.amberhome.objects.appcompat.ACMenuItemWrapper _item) throws Exception{
 //BA.debugLineNum = 168;BA.debugLine="Sub ToolBar_MenuItemClick (Item As ACMenuItem)";
 //BA.debugLineNum = 169;BA.debugLine="Select Case Item.Id";
switch (BA.switchObjectToInt(_item.getId(),(int) (1))) {
case 0: {
 //BA.debugLineNum = 171;BA.debugLine="ShowAddPerson";
_showaddperson();
 break; }
}
;
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 164;BA.debugLine="Sub ToolBar_NavigationItemClick";
 //BA.debugLineNum = 165;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 166;BA.debugLine="End Sub";
return "";
}
public static String  _txtsearch_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 475;BA.debugLine="Sub txtSearch_TextChanged (Old As String, New As S";
 //BA.debugLineNum = 476;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtSearch.T";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtsearch.getText()))<=0) { 
 //BA.debugLineNum = 477;BA.debugLine="AddBranches(GlobalVar.AreaID)";
_addbranches(mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 478;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 481;BA.debugLine="SearchEmp(GlobalVar.AreaID, txtSearch.Text)";
_searchemp(mostCurrent._globalvar._areaid /*int*/ ,mostCurrent._txtsearch.getText());
 //BA.debugLineNum = 482;BA.debugLine="End Sub";
return "";
}
public static boolean  _updatereprintstub(int _iregid) throws Exception{
boolean _bretval = false;
int _noprint = 0;
 //BA.debugLineNum = 1156;BA.debugLine="Private Sub UpdateReprintStub(iRegID As Int) As Bo";
 //BA.debugLineNum = 1157;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 1158;BA.debugLine="Dim NoPrint As Int";
_noprint = 0;
 //BA.debugLineNum = 1160;BA.debugLine="NoPrint = GetNoOfStubPrint(iRegID)";
_noprint = _getnoofstubprint(_iregid);
 //BA.debugLineNum = 1161;BA.debugLine="NoPrint = NoPrint + 1";
_noprint = (int) (_noprint+1);
 //BA.debugLineNum = 1163;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 1164;BA.debugLine="Try";
try { //BA.debugLineNum = 1165;BA.debugLine="Starter.strCriteria = \"UPDATE tblRegistration \"";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblRegistration "+"SET NoPrintStub = ? "+"WHERE RegID = "+BA.NumberToString(_iregid);
 //BA.debugLineNum = 1169;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{BA.NumberToString(_noprint)}));
 //BA.debugLineNum = 1171;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 1172;BA.debugLine="ProgressDialogShow2($\"Preparing Stub Printing\"$,";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Preparing Stub Printing")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1173;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e13) {
			processBA.setLastException(e13); //BA.debugLineNum = 1175;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1176;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1177;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("45636117",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1179;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 1180;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 1181;BA.debugLine="End Sub";
return false;
}
public static boolean  _updateswappedemp(int _iswapemp,int _iregid) throws Exception{
boolean _bretval = false;
 //BA.debugLineNum = 1285;BA.debugLine="Private Sub UpdateSwappedEmp(iSwapEmp As Int, iReg";
 //BA.debugLineNum = 1286;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 1288;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1289;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 1290;BA.debugLine="Try";
try { //BA.debugLineNum = 1292;BA.debugLine="Starter.strCriteria = \"UPDATE tblRegistration \"";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblRegistration "+"SET WasSwapped = ?, SwappedTo = ?, WillAttend = ?"+"WHERE RegID = "+BA.NumberToString(_iregid);
 //BA.debugLineNum = 1295;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{("1"),mostCurrent._sswapemp,("1")}));
 //BA.debugLineNum = 1297;BA.debugLine="Starter.strCriteria = \"UPDATE tblRegistration \"";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblRegistration "+"SET WillAttend = ?"+"WHERE RegID = "+BA.NumberToString(_iswapemp);
 //BA.debugLineNum = 1300;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{("2")}));
 //BA.debugLineNum = 1301;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 1302;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e12) {
			processBA.setLastException(e12); //BA.debugLineNum = 1304;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1305;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("46029332",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1307;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 1308;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 1309;BA.debugLine="End Sub";
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
