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

public class mainscreen extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static mainscreen mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "bwsi.registration.townhall", "bwsi.registration.townhall.mainscreen");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (mainscreen).");
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
		activityBA = new BA(this, layout, processBA, "bwsi.registration.townhall", "bwsi.registration.townhall.mainscreen");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "bwsi.registration.townhall.mainscreen", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (mainscreen) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (mainscreen) Resume **");
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
		return mainscreen.class;
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
            BA.LogInfo("** Activity (mainscreen) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (mainscreen) Pause event (activity is not paused). **");
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
            mainscreen mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (mainscreen) Resume **");
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
public de.amberhome.objects.appcompat.ACActionBar _actionbarbutton = null;
public de.amberhome.objects.appcompat.ACToolbarDarkWrapper _toolbar = null;
public anywheresoftware.b4a.object.XmlLayoutBuilder _xmlicon = null;
public mpandroidchartwrapper.pieChartWrapper _piechart1 = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnstart = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdreg = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdemp = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdguest = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblarea = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbranches = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblregistered = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltotal = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblunregistered = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lvbranches = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnaddguest = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnaddemployee = null;
public bwsi.registration.townhall.main _main = null;
public bwsi.registration.townhall.actregistration _actregistration = null;
public bwsi.registration.townhall.registration _registration = null;
public bwsi.registration.townhall.addemployee _addemployee = null;
public bwsi.registration.townhall.globalvar _globalvar = null;
public bwsi.registration.townhall.dbfunctions _dbfunctions = null;
public bwsi.registration.townhall.addguest _addguest = null;
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
 //BA.debugLineNum = 51;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 52;BA.debugLine="Scale.SetRate(0.5)";
mostCurrent._scale._setrate /*String*/ (mostCurrent.activityBA,0.5);
 //BA.debugLineNum = 53;BA.debugLine="Activity.LoadLayout(\"MainScreenNew\")";
mostCurrent._activity.LoadLayout("MainScreenNew",mostCurrent.activityBA);
 //BA.debugLineNum = 55;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(18).Bold.Append";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (18)).Bold().Append(BA.ObjectToCharSequence(("2024 TOWNHALL MEETING"))).PopAll();
 //BA.debugLineNum = 56;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(15).Append($";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (15)).Append(BA.ObjectToCharSequence(("Registration App ")+anywheresoftware.b4a.keywords.Common.Application.getVersionName())).PopAll();
 //BA.debugLineNum = 58;BA.debugLine="ToolBar.InitMenuListener";
mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 59;BA.debugLine="ToolBar.Title = GlobalVar.CSTitle";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 60;BA.debugLine="ToolBar.SubTitle = GlobalVar.CSSubTitle";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 62;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 63;BA.debugLine="Dim xl As XmlLayoutBuilder";
_xl = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 64;BA.debugLine="jo = ToolBar";
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._toolbar.getObject()));
 //BA.debugLineNum = 65;BA.debugLine="jo.RunMethod(\"setPopupTheme\", Array(xl.GetResourc";
_jo.RunMethod("setPopupTheme",new Object[]{(Object)(_xl.GetResourceId("style","ToolbarMenu"))});
 //BA.debugLineNum = 66;BA.debugLine="jo.RunMethod(\"setContentInsetStartWithNavigation\"";
_jo.RunMethod("setContentInsetStartWithNavigation",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))});
 //BA.debugLineNum = 67;BA.debugLine="jo.RunMethod(\"setTitleMarginStart\", Array(0dip))";
_jo.RunMethod("setTitleMarginStart",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)))});
 //BA.debugLineNum = 69;BA.debugLine="ActionBarButton.Initialize";
mostCurrent._actionbarbutton.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 70;BA.debugLine="ActionBarButton.ShowUpIndicator = True";
mostCurrent._actionbarbutton.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 73;BA.debugLine="If FirstTime = True Then";
if (_firsttime==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 74;BA.debugLine="lvBranches.SingleLineLayout.ItemHeight = 25dip";
mostCurrent._lvbranches.getSingleLineLayout().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 75;BA.debugLine="lvBranches.SingleLineLayout.Label.TextColor = Gl";
mostCurrent._lvbranches.getSingleLineLayout().Label.setTextColor((int) (mostCurrent._globalvar._seccolor /*double*/ ));
 //BA.debugLineNum = 76;BA.debugLine="lvBranches.SingleLineLayout.Label.Color = Colors";
mostCurrent._lvbranches.getSingleLineLayout().Label.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 77;BA.debugLine="lvBranches.SingleLineLayout.Label.TextSize = 12";
mostCurrent._lvbranches.getSingleLineLayout().Label.setTextSize((float) (12));
 //BA.debugLineNum = 79;BA.debugLine="GlobalVar.AreaID = DBFunctions.GetAreaID";
mostCurrent._globalvar._areaid /*int*/  = mostCurrent._dbfunctions._getareaid /*int*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 80;BA.debugLine="GlobalVar.AreaName = DBFunctions.GetAreaName(Glo";
mostCurrent._globalvar._areaname /*String*/  = mostCurrent._dbfunctions._getareaname /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 81;BA.debugLine="GlobalVar.AreaDesc = DBFunctions.GetAreaDesc(Glo";
mostCurrent._globalvar._areadesc /*String*/  = mostCurrent._dbfunctions._getareadesc /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 82;BA.debugLine="GlobalVar.AreaCode = DBFunctions.GetAreaCode(Glo";
mostCurrent._globalvar._areacode /*String*/  = mostCurrent._dbfunctions._getareacode /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 83;BA.debugLine="GlobalVar.TotEmployees = DBFunctions.GetTotalAtt";
mostCurrent._globalvar._totemployees /*int*/  = (int) (mostCurrent._dbfunctions._gettotalattendees /*long*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ ));
 //BA.debugLineNum = 84;BA.debugLine="GlobalVar.TotReg = DBFunctions.GetTotRegistered(";
mostCurrent._globalvar._totreg /*int*/  = (int) (mostCurrent._dbfunctions._gettotregistered /*long*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ ));
 //BA.debugLineNum = 85;BA.debugLine="GlobalVar.TotUnreg = DBFunctions.GetTotUnRegiste";
mostCurrent._globalvar._totunreg /*int*/  = (int) (mostCurrent._dbfunctions._gettotunregister /*long*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ ));
 };
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return "";
}
public static String  _activity_createmenu(de.amberhome.objects.appcompat.ACMenuWrapper _menu) throws Exception{
 //BA.debugLineNum = 18;BA.debugLine="Sub Activity_CreateMenu(Menu As ACMenu)";
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 90;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 91;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 92;BA.debugLine="ToolBar_NavigationItemClick";
_toolbar_navigationitemclick();
 //BA.debugLineNum = 93;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 95;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 97;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 170;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 171;BA.debugLine="If UserClosed Then ExitApplication";
if (_userclosed) { 
anywheresoftware.b4a.keywords.Common.ExitApplication();};
 //BA.debugLineNum = 172;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 99;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 101;BA.debugLine="GlobalVar.AreaID = DBFunctions.GetAreaID";
mostCurrent._globalvar._areaid /*int*/  = mostCurrent._dbfunctions._getareaid /*int*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 102;BA.debugLine="GlobalVar.AreaName = DBFunctions.GetAreaName(Glob";
mostCurrent._globalvar._areaname /*String*/  = mostCurrent._dbfunctions._getareaname /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 103;BA.debugLine="GlobalVar.AreaDesc = DBFunctions.GetAreaDesc(Glob";
mostCurrent._globalvar._areadesc /*String*/  = mostCurrent._dbfunctions._getareadesc /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 104;BA.debugLine="GlobalVar.AreaCode = DBFunctions.GetAreaCode(Glob";
mostCurrent._globalvar._areacode /*String*/  = mostCurrent._dbfunctions._getareacode /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 105;BA.debugLine="GlobalVar.TotEmployees = DBFunctions.GetTotalAtte";
mostCurrent._globalvar._totemployees /*int*/  = (int) (mostCurrent._dbfunctions._gettotalattendees /*long*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ ));
 //BA.debugLineNum = 106;BA.debugLine="GlobalVar.TotReg = DBFunctions.GetTotRegistered(G";
mostCurrent._globalvar._totreg /*int*/  = (int) (mostCurrent._dbfunctions._gettotregistered /*long*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ ));
 //BA.debugLineNum = 107;BA.debugLine="GlobalVar.TotUnreg = DBFunctions.GetTotUnRegister";
mostCurrent._globalvar._totunreg /*int*/  = (int) (mostCurrent._dbfunctions._gettotunregister /*long*/ (mostCurrent.activityBA,mostCurrent._globalvar._areaid /*int*/ ));
 //BA.debugLineNum = 109;BA.debugLine="GenerateBranchList(GlobalVar.AreaID)";
_generatebranchlist(mostCurrent._globalvar._areaid /*int*/ );
 //BA.debugLineNum = 110;BA.debugLine="lvBranches.SingleLineLayout.ItemHeight = 25dip";
mostCurrent._lvbranches.getSingleLineLayout().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 111;BA.debugLine="lvBranches.SingleLineLayout.Label.TextColor = 0xF";
mostCurrent._lvbranches.getSingleLineLayout().Label.setTextColor((int) (0xff01baef));
 //BA.debugLineNum = 112;BA.debugLine="lvBranches.SingleLineLayout.Label.Color = Colors.";
mostCurrent._lvbranches.getSingleLineLayout().Label.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 113;BA.debugLine="lvBranches.SingleLineLayout.Label.TextSize = 15";
mostCurrent._lvbranches.getSingleLineLayout().Label.setTextSize((float) (15));
 //BA.debugLineNum = 115;BA.debugLine="lblArea.Text = GlobalVar.AreaName";
mostCurrent._lblarea.setText(BA.ObjectToCharSequence(mostCurrent._globalvar._areaname /*String*/ ));
 //BA.debugLineNum = 116;BA.debugLine="lblTotal.Text = GlobalVar.TotEmployees";
mostCurrent._lbltotal.setText(BA.ObjectToCharSequence(mostCurrent._globalvar._totemployees /*int*/ ));
 //BA.debugLineNum = 117;BA.debugLine="lblRegistered.Text = GlobalVar.TotReg";
mostCurrent._lblregistered.setText(BA.ObjectToCharSequence(mostCurrent._globalvar._totreg /*int*/ ));
 //BA.debugLineNum = 118;BA.debugLine="lblUnregistered.Text = GlobalVar.TotUnreg";
mostCurrent._lblunregistered.setText(BA.ObjectToCharSequence(mostCurrent._globalvar._totunreg /*int*/ ));
 //BA.debugLineNum = 160;BA.debugLine="cdEmp.Initialize2(0xFF1E4369, 25, 0,0xFF188731)";
mostCurrent._cdemp.Initialize2((int) (0xff1e4369),(int) (25),(int) (0),(int) (0xff188731));
 //BA.debugLineNum = 161;BA.debugLine="btnAddEmployee.Background = cdEmp";
mostCurrent._btnaddemployee.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdemp.getObject()));
 //BA.debugLineNum = 163;BA.debugLine="cdGuest.Initialize2(0xFF1E4369, 25, 0,0xFFFFC107)";
mostCurrent._cdguest.Initialize2((int) (0xff1e4369),(int) (25),(int) (0),(int) (0xffffc107));
 //BA.debugLineNum = 164;BA.debugLine="btnAddGuest.Background = cdGuest";
mostCurrent._btnaddguest.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdguest.getObject()));
 //BA.debugLineNum = 166;BA.debugLine="cdReg.Initialize2(0xFF1E4369, 25, 0,0xFF268FC2)";
mostCurrent._cdreg.Initialize2((int) (0xff1e4369),(int) (25),(int) (0),(int) (0xff268fc2));
 //BA.debugLineNum = 167;BA.debugLine="btnStart.Background = cdReg";
mostCurrent._btnstart.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdreg.getObject()));
 //BA.debugLineNum = 168;BA.debugLine="End Sub";
return "";
}
public static String  _btnaddemployee_click() throws Exception{
 //BA.debugLineNum = 254;BA.debugLine="Sub btnAddEmployee_Click";
 //BA.debugLineNum = 255;BA.debugLine="StartActivity(AddEmployee)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._addemployee.getObject()));
 //BA.debugLineNum = 256;BA.debugLine="End Sub";
return "";
}
public static String  _btnaddguest_click() throws Exception{
 //BA.debugLineNum = 250;BA.debugLine="Sub btnAddGuest_Click";
 //BA.debugLineNum = 251;BA.debugLine="StartActivity(AddGuest)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._addguest.getObject()));
 //BA.debugLineNum = 252;BA.debugLine="End Sub";
return "";
}
public static String  _btnstart_click() throws Exception{
 //BA.debugLineNum = 227;BA.debugLine="Sub btnStart_Click";
 //BA.debugLineNum = 228;BA.debugLine="StartActivity(Registration)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._registration.getObject()));
 //BA.debugLineNum = 229;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _fontbit(String _icon,float _font_size,int _color,boolean _awesome) throws Exception{
anywheresoftware.b4a.keywords.constants.TypefaceWrapper _typ = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _cvs = null;
double _h = 0;
 //BA.debugLineNum = 208;BA.debugLine="Sub FontBit (icon As String, font_size As Float, c";
 //BA.debugLineNum = 209;BA.debugLine="If color = 0 Then color = Colors.White";
if (_color==0) { 
_color = anywheresoftware.b4a.keywords.Common.Colors.White;};
 //BA.debugLineNum = 210;BA.debugLine="Dim typ As Typeface = Typeface.MATERIALICONS";
_typ = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
_typ = (anywheresoftware.b4a.keywords.constants.TypefaceWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.constants.TypefaceWrapper(), (android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.getMATERIALICONS()));
 //BA.debugLineNum = 211;BA.debugLine="If awesome Then typ = Typeface.FONTAWESOME";
if (_awesome) { 
_typ = (anywheresoftware.b4a.keywords.constants.TypefaceWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.constants.TypefaceWrapper(), (android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()));};
 //BA.debugLineNum = 212;BA.debugLine="Dim bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 213;BA.debugLine="bmp.InitializeMutable(32dip, 32dip)";
_bmp.InitializeMutable(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32)));
 //BA.debugLineNum = 214;BA.debugLine="Dim cvs As Canvas";
_cvs = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 215;BA.debugLine="cvs.Initialize2(bmp)";
_cvs.Initialize2((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 216;BA.debugLine="Dim h As Double";
_h = 0;
 //BA.debugLineNum = 217;BA.debugLine="If Not(awesome) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_awesome)) { 
 //BA.debugLineNum = 218;BA.debugLine="h = cvs.MeasureStringHeight(icon, typ, font_size";
_h = _cvs.MeasureStringHeight(_icon,(android.graphics.Typeface)(_typ.getObject()),_font_size)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10));
 }else {
 //BA.debugLineNum = 220;BA.debugLine="h = cvs.MeasureStringHeight(icon, typ, font_size";
_h = _cvs.MeasureStringHeight(_icon,(android.graphics.Typeface)(_typ.getObject()),_font_size);
 };
 //BA.debugLineNum = 222;BA.debugLine="cvs.DrawText(icon, bmp.Width / 2, bmp.Height / 2";
_cvs.DrawText(mostCurrent.activityBA,_icon,(float) (_bmp.getWidth()/(double)2),(float) (_bmp.getHeight()/(double)2+_h/(double)2),(android.graphics.Typeface)(_typ.getObject()),_font_size,_color,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"));
 //BA.debugLineNum = 223;BA.debugLine="Return bmp";
if (true) return _bmp;
 //BA.debugLineNum = 224;BA.debugLine="End Sub";
return null;
}
public static String  _generatebranchlist(int _iareaid) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsbranches = null;
int _i = 0;
 //BA.debugLineNum = 231;BA.debugLine="Private Sub GenerateBranchList(iAreaID As Int)";
 //BA.debugLineNum = 232;BA.debugLine="Dim rsBranches As Cursor";
_rsbranches = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 233;BA.debugLine="lvBranches.Clear";
mostCurrent._lvbranches.Clear();
 //BA.debugLineNum = 235;BA.debugLine="Try";
try { //BA.debugLineNum = 236;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblBranches";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblBranches WHERE AreaID = "+BA.NumberToString(_iareaid);
 //BA.debugLineNum = 237;BA.debugLine="rsBranches = Starter.DBCon.ExecQuery(Starter.str";
_rsbranches = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 239;BA.debugLine="If rsBranches.RowCount > 0 Then";
if (_rsbranches.getRowCount()>0) { 
 //BA.debugLineNum = 240;BA.debugLine="For i = 0 To rsBranches.RowCount - 1";
{
final int step7 = 1;
final int limit7 = (int) (_rsbranches.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit7 ;_i = _i + step7 ) {
 //BA.debugLineNum = 241;BA.debugLine="rsBranches.Position = i";
_rsbranches.setPosition(_i);
 //BA.debugLineNum = 242;BA.debugLine="lvBranches.AddSingleLine(rsBranches.GetString(";
mostCurrent._lvbranches.AddSingleLine(BA.ObjectToCharSequence(_rsbranches.GetString("BranchName")));
 }
};
 };
 } 
       catch (Exception e13) {
			processBA.setLastException(e13); //BA.debugLineNum = 246;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("117235983",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 248;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 29;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 32;BA.debugLine="Dim ActionBarButton As ACActionBar";
mostCurrent._actionbarbutton = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 33;BA.debugLine="Private ToolBar As ACToolBarDark";
mostCurrent._toolbar = new de.amberhome.objects.appcompat.ACToolbarDarkWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private xmlIcon As XmlLayoutBuilder";
mostCurrent._xmlicon = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 36;BA.debugLine="Private PieChart1 As PieChart";
mostCurrent._piechart1 = new mpandroidchartwrapper.pieChartWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private btnStart As ACButton";
mostCurrent._btnstart = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Dim cdReg, cdEmp, cdGuest As ColorDrawable";
mostCurrent._cdreg = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
mostCurrent._cdemp = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
mostCurrent._cdguest = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 41;BA.debugLine="Private lblArea As Label";
mostCurrent._lblarea = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private lblBranches As Label";
mostCurrent._lblbranches = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private lblRegistered As Label";
mostCurrent._lblregistered = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private lblTotal As Label";
mostCurrent._lbltotal = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private lblUnregistered As Label";
mostCurrent._lblunregistered = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private lvBranches As ListView";
mostCurrent._lvbranches = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private btnAddGuest As ACButton";
mostCurrent._btnaddguest = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private btnAddEmployee As ACButton";
mostCurrent._btnaddemployee = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 25;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 26;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbarbackground(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
 //BA.debugLineNum = 189;BA.debugLine="Sub SetSnackBarBackground(pSnack As DSSnackbar, pC";
 //BA.debugLineNum = 190;BA.debugLine="Dim v As View";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 191;BA.debugLine="v = pSnack.View";
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_psnack.getView()));
 //BA.debugLineNum = 192;BA.debugLine="v.Color = pColor";
_v.setColor(_pcolor);
 //BA.debugLineNum = 193;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbartextcolor(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _textv = null;
 //BA.debugLineNum = 195;BA.debugLine="Sub SetSnackBarTextColor(pSnack As DSSnackbar, pCo";
 //BA.debugLineNum = 196;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 197;BA.debugLine="p = pSnack.View";
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_psnack.getView()));
 //BA.debugLineNum = 198;BA.debugLine="For Each v As View In p.GetAllViewsRecursive";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group3 = _p.GetAllViewsRecursive();
final int groupLen3 = group3.getSize()
;int index3 = 0;
;
for (; index3 < groupLen3;index3++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group3.Get(index3)));
 //BA.debugLineNum = 199;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 200;BA.debugLine="Dim textv As Label";
_textv = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 201;BA.debugLine="textv = v";
_textv = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 202;BA.debugLine="textv.TextColor = pColor";
_textv.setTextColor(_pcolor);
 //BA.debugLineNum = 203;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 206;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_menuitemclick(de.amberhome.objects.appcompat.ACMenuItemWrapper _item) throws Exception{
 //BA.debugLineNum = 179;BA.debugLine="Sub ToolBar_MenuItemClick (Item As ACMenuItem)";
 //BA.debugLineNum = 180;BA.debugLine="Select Case Item.Id";
switch (BA.switchObjectToInt(_item.getId(),(int) (1))) {
case 0: {
 //BA.debugLineNum = 182;BA.debugLine="GlobalVar.AreaID = DBFunctions.GetAreaID";
mostCurrent._globalvar._areaid /*int*/  = mostCurrent._dbfunctions._getareaid /*int*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 183;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 184;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 break; }
}
;
 //BA.debugLineNum = 186;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 174;BA.debugLine="Sub ToolBar_NavigationItemClick";
 //BA.debugLineNum = 175;BA.debugLine="GlobalVar.AreaID = DBFunctions.GetAreaID";
mostCurrent._globalvar._areaid /*int*/  = mostCurrent._dbfunctions._getareaid /*int*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 176;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 177;BA.debugLine="End Sub";
return "";
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
