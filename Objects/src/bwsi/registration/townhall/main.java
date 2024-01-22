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

public class main extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "bwsi.registration.townhall", "bwsi.registration.townhall.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
		activityBA = new BA(this, layout, processBA, "bwsi.registration.townhall", "bwsi.registration.townhall.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "bwsi.registration.townhall.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
		return main.class;
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
            BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (main) Pause event (activity is not paused). **");
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
            main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
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
public static com.johan.Vibrate.Vibrate _vibration = null;
public static long[] _vibratepattern = null;
public static bwsi.registration.townhall.slinptypeconst _inptyp = null;
public anywheresoftware.b4a.objects.PanelWrapper _btnarea = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblareaicon = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlareas = null;
public static String _strpassword = "";
public de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _matdialogbuilder = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlsettings = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnsave = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdtxtbox = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdsavebutton = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdcancelbutton = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtempname = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btncancel = null;
public bwsi.registration.townhall.actregistration _actregistration = null;
public bwsi.registration.townhall.registration _registration = null;
public bwsi.registration.townhall.addemployee _addemployee = null;
public bwsi.registration.townhall.globalvar _globalvar = null;
public bwsi.registration.townhall.dbfunctions _dbfunctions = null;
public bwsi.registration.townhall.addguest _addguest = null;
public bwsi.registration.townhall.mainscreen _mainscreen = null;
public bwsi.registration.townhall.guestlist _guestlist = null;
public bwsi.registration.townhall.dbutils _dbutils = null;
public bwsi.registration.townhall.scale _scale = null;
public bwsi.registration.townhall.starter _starter = null;
public bwsi.registration.townhall.httputils2service _httputils2service = null;
public bwsi.registration.townhall.b4xcollections _b4xcollections = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (actregistration.mostCurrent != null);
vis = vis | (registration.mostCurrent != null);
vis = vis | (addemployee.mostCurrent != null);
vis = vis | (addguest.mostCurrent != null);
vis = vis | (mainscreen.mostCurrent != null);
vis = vis | (guestlist.mostCurrent != null);
return vis;}
public static String  _activity_click() throws Exception{
 //BA.debugLineNum = 80;BA.debugLine="Sub Activity_Click";
 //BA.debugLineNum = 81;BA.debugLine="StartActivity(MainScreen)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._mainscreen.getObject()));
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 48;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 49;BA.debugLine="Activity.LoadLayout(\"Login\")";
mostCurrent._activity.LoadLayout("Login",mostCurrent.activityBA);
 //BA.debugLineNum = 50;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 51;BA.debugLine="GlobalVar.AreaID = DBFunctions.GetAreaID";
mostCurrent._globalvar._areaid /*int*/  = mostCurrent._dbfunctions._getareaid /*int*/ (mostCurrent.activityBA);
 };
 //BA.debugLineNum = 53;BA.debugLine="InpTyp.Initialize";
_inptyp._initialize /*String*/ (processBA);
 //BA.debugLineNum = 55;BA.debugLine="InpTyp.SetInputType(txtEmpName,Array As Int(InpTy";
_inptyp._setinputtype /*String*/ (mostCurrent._txtempname,new int[]{_inptyp._type_class_text /*int*/ (),_inptyp._type_text_flag_auto_correct /*int*/ (),_inptyp._type_text_flag_cap_words /*int*/ ()});
 //BA.debugLineNum = 57;BA.debugLine="cdTxtBox.Initialize2(Colors.Transparent, 0, 0, Co";
mostCurrent._cdtxtbox.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.Transparent,(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 58;BA.debugLine="cdSaveButton.Initialize2(0xFF1E4369, 20, 0,0xFF26";
mostCurrent._cdsavebutton.Initialize2((int) (0xff1e4369),(int) (20),(int) (0),(int) (0xff268fc2));
 //BA.debugLineNum = 59;BA.debugLine="cdCancelButton.Initialize2(0xFFDC143C, 20, 0,0xFF";
mostCurrent._cdcancelbutton.Initialize2((int) (0xffdc143c),(int) (20),(int) (0),(int) (0xffdc143c));
 //BA.debugLineNum = 60;BA.debugLine="txtEmpName.Background = cdTxtBox";
mostCurrent._txtempname.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdtxtbox.getObject()));
 //BA.debugLineNum = 61;BA.debugLine="btnSave.Background = cdSaveButton";
mostCurrent._btnsave.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdsavebutton.getObject()));
 //BA.debugLineNum = 62;BA.debugLine="btnCancel.Background = cdCancelButton";
mostCurrent._btncancel.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdcancelbutton.getObject()));
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 84;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 85;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 86;BA.debugLine="If pnlSettings.Visible = True Then";
if (mostCurrent._pnlsettings.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 87;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtEmpNam";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtempname.getText()))>0) { 
 //BA.debugLineNum = 88;BA.debugLine="txtEmpName.Text = \"\"";
mostCurrent._txtempname.setText(BA.ObjectToCharSequence(""));
 }else {
 //BA.debugLineNum = 90;BA.debugLine="btnCancel_Click";
_btncancel_click();
 };
 };
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
 //BA.debugLineNum = 76;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 78;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 65;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 66;BA.debugLine="GlobalVar.AreaID = DBFunctions.GetAreaID";
mostCurrent._globalvar._areaid /*int*/  = mostCurrent._dbfunctions._getareaid /*int*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 67;BA.debugLine="If Not(DBFunctions.IsThereAssignedEmp) Then";
if (anywheresoftware.b4a.keywords.Common.Not(mostCurrent._dbfunctions._isthereassignedemp /*boolean*/ (mostCurrent.activityBA))) { 
 //BA.debugLineNum = 68;BA.debugLine="pnlSettings.Visible = True";
mostCurrent._pnlsettings.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 70;BA.debugLine="GlobalVar.AssignedEmp = DBFunctions.GetAssignedE";
mostCurrent._globalvar._assignedemp /*String*/  = mostCurrent._dbfunctions._getassignedemp /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 71;BA.debugLine="pnlSettings.Visible = False";
mostCurrent._pnlsettings.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 73;BA.debugLine="vibratePattern = Array As Long(500, 500, 300, 500";
_vibratepattern = new long[]{(long) (500),(long) (500),(long) (300),(long) (500)};
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
return "";
}
public static String  _adminpassword_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _action) throws Exception{
 //BA.debugLineNum = 240;BA.debugLine="Sub AdminPassword_ButtonPressed (Dialog As Materia";
 //BA.debugLineNum = 241;BA.debugLine="Select Case Action";
switch (BA.switchObjectToInt(_action,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE)) {
case 0: {
 //BA.debugLineNum = 243;BA.debugLine="If strPassword <> GlobalVar.AdminPassword Then";
if ((mostCurrent._strpassword).equals(mostCurrent._globalvar._adminpassword /*String*/ ) == false) { 
 //BA.debugLineNum = 244;BA.debugLine="vibration.vibrateOnce(2000)";
_vibration.vibrateOnce(processBA,(long) (2000));
 //BA.debugLineNum = 245;BA.debugLine="PasswordError";
_passworderror();
 //BA.debugLineNum = 246;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 249;BA.debugLine="If Not(UpdateAreaID(GlobalVar.AreaID)) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_updateareaid(mostCurrent._globalvar._areaid /*int*/ ))) { 
 //BA.debugLineNum = 250;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 251;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 253;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 254;BA.debugLine="ShowSuccessMsg($\"SUCCESSFULLY SAVED\"$, $\"Selec";
_showsuccessmsg(("SUCCESSFULLY SAVED"),("Selected Area has been successfully set!"));
 };
 break; }
case 1: {
 //BA.debugLineNum = 258;BA.debugLine="vibration.vibrateCancel";
_vibration.vibrateCancel(processBA);
 break; }
}
;
 //BA.debugLineNum = 264;BA.debugLine="End Sub";
return "";
}
public static String  _adminpassword_inputchanged(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _spassword) throws Exception{
 //BA.debugLineNum = 231;BA.debugLine="Private Sub AdminPassword_InputChanged (mDialog As";
 //BA.debugLineNum = 232;BA.debugLine="If sPassword.Length = 0 Then";
if (_spassword.length()==0) { 
 //BA.debugLineNum = 233;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSITI";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 235;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSITI";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 237;BA.debugLine="strPassword = sPassword";
mostCurrent._strpassword = _spassword;
 //BA.debugLineNum = 238;BA.debugLine="End Sub";
return "";
}
public static String  _areaselected_oncancelclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,String _selection,int _position,long _id) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 164;BA.debugLine="Private Sub AreaSelected_OnCancelClicked (View As";
 //BA.debugLineNum = 165;BA.debugLine="ToastMessageShow(Selection&\" Selected! (Position";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_selection+" Selected! (Position : "+BA.NumberToString(_position)+")"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 166;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 167;BA.debugLine="vibration.vibrateCancel";
_vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 169;BA.debugLine="Alert.Initialize.Dismiss2";
_alert.Initialize().Dismiss2();
 //BA.debugLineNum = 170;BA.debugLine="End Sub";
return "";
}
public static String  _areaselected_onitemclick(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,String _selection,int _position,long _id) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 155;BA.debugLine="Private Sub AreaSelected_OnItemClick (View As View";
 //BA.debugLineNum = 156;BA.debugLine="ToastMessageShow(Selection&\" Selected! (Position";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_selection+" Selected! (Position : "+BA.NumberToString(_position)+")"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 157;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 158;BA.debugLine="vibration.vibrateCancel";
_vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 159;BA.debugLine="Alert.Initialize.Dismiss2";
_alert.Initialize().Dismiss2();
 //BA.debugLineNum = 160;BA.debugLine="GlobalVar.AreaID = GetAreaIDByAreaName(Selection)";
mostCurrent._globalvar._areaid /*int*/  = _getareaidbyareaname(_selection);
 //BA.debugLineNum = 161;BA.debugLine="ShowAdminPassword";
_showadminpassword();
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
return "";
}
public static String  _btnarea_click() throws Exception{
 //BA.debugLineNum = 113;BA.debugLine="Sub btnArea_Click";
 //BA.debugLineNum = 114;BA.debugLine="pnlAreas_Click";
_pnlareas_click();
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return "";
}
public static String  _btncancel_click() throws Exception{
 //BA.debugLineNum = 580;BA.debugLine="Sub btnCancel_Click";
 //BA.debugLineNum = 581;BA.debugLine="ShowConfirmClose";
_showconfirmclose();
 //BA.debugLineNum = 582;BA.debugLine="End Sub";
return "";
}
public static String  _btnsave_click() throws Exception{
 //BA.debugLineNum = 376;BA.debugLine="Sub btnSave_Click";
 //BA.debugLineNum = 377;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtEmpName.";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtempname.getText()))<=0) { 
 //BA.debugLineNum = 378;BA.debugLine="ShowUserError($\"E R R O R!\"$, $\"Employee Name ca";
_showusererror(("E R R O R!"),("Employee Name cannot be Blank!"));
 //BA.debugLineNum = 379;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 381;BA.debugLine="ConfirmSave(txtEmpName.Text)";
_confirmsave(mostCurrent._txtempname.getText());
 //BA.debugLineNum = 382;BA.debugLine="End Sub";
return "";
}
public static String  _closeapp_onnegativeclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 521;BA.debugLine="Private Sub CloseApp_OnNegativeClicked (View As Vi";
 //BA.debugLineNum = 522;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 523;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 524;BA.debugLine="ToastMessageShow($\"Canceled!\"$, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Canceled!")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 525;BA.debugLine="End Sub";
return "";
}
public static String  _closeapp_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 527;BA.debugLine="Private Sub CloseApp_OnPositiveClicked (View As Vi";
 //BA.debugLineNum = 528;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 529;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 530;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 //BA.debugLineNum = 532;BA.debugLine="End Sub";
return "";
}
public static String  _closeappfontsizebinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 534;BA.debugLine="Private Sub CloseAppFontSizeBinder_OnBindView (Vie";
 //BA.debugLineNum = 535;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 536;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 537;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 538;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 539;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 540;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf059)))+"  "));
 //BA.debugLineNum = 541;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 543;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 546;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 547;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 548;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 549;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 551;BA.debugLine="End Sub";
return "";
}
public static String  _confirmsave(String _sempname) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 389;BA.debugLine="Private Sub ConfirmSave (sEmpName As String)";
 //BA.debugLineNum = 390;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 392;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(("CONFIRM ADD USER")).SetMessage(("Do you want to Save ")+_sempname+anywheresoftware.b4a.keywords.Common.CRLF+(" as the User of this device?")).SetPositiveText("YES").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetNegativeText("NO").SetNegativeColor((int) (mostCurrent._globalvar._negcolor /*double*/ )).SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"SaveUser").SetOnNegativeClicked(mostCurrent.activityBA,"SaveUser").SetOnViewBinder(mostCurrent.activityBA,"SaveUserFontSizeBinder");
 //BA.debugLineNum = 410;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 411;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 413;BA.debugLine="End Sub";
return "";
}
public static int  _getareaidbyareaname(String _sareaname) throws Exception{
int _iretval = 0;
 //BA.debugLineNum = 172;BA.debugLine="Private Sub GetAreaIDByAreaName (sAreaName As Stri";
 //BA.debugLineNum = 173;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 175;BA.debugLine="Try";
try { //BA.debugLineNum = 176;BA.debugLine="Starter.strCriteria = \"SELECT AreaID FROM tblAre";
mostCurrent._starter._strcriteria /*String*/  = "SELECT AreaID FROM tblAreas WHERE AreaName = '"+_sareaname+"'";
 //BA.debugLineNum = 177;BA.debugLine="LogColor(Starter.strCriteria, Colors.Blue)";
anywheresoftware.b4a.keywords.Common.LogImpl("1786437",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 179;BA.debugLine="iRetVal = Starter.DBCon.ExecQuerySingleResult(St";
_iretval = (int)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult(mostCurrent._starter._strcriteria /*String*/ )));
 } 
       catch (Exception e7) {
			processBA.setLastException(e7); //BA.debugLineNum = 181;BA.debugLine="ToastMessageShow($\"Unable to fetch Branch System";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Branch System Mode due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 182;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("1786442",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 183;BA.debugLine="iRetVal = 0";
_iretval = (int) (0);
 };
 //BA.debugLineNum = 185;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 186;BA.debugLine="End Sub";
return 0;
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 31;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 33;BA.debugLine="Private btnArea As Panel";
mostCurrent._btnarea = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private lblAreaIcon As Label";
mostCurrent._lblareaicon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private pnlAreas As Panel";
mostCurrent._pnlareas = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private strPassword As String";
mostCurrent._strpassword = "";
 //BA.debugLineNum = 38;BA.debugLine="Private MatDialogBuilder As MaterialDialogBuilder";
mostCurrent._matdialogbuilder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private pnlSettings As Panel";
mostCurrent._pnlsettings = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private btnSave As ACButton";
mostCurrent._btnsave = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private cdTxtBox As ColorDrawable";
mostCurrent._cdtxtbox = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 43;BA.debugLine="Private cdSaveButton, cdCancelButton As ColorDraw";
mostCurrent._cdsavebutton = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
mostCurrent._cdcancelbutton = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 44;BA.debugLine="Private txtEmpName As EditText";
mostCurrent._txtempname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private btnCancel As ACButton";
mostCurrent._btncancel = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 46;BA.debugLine="End Sub";
return "";
}
public static String  _lblareaicon_click() throws Exception{
 //BA.debugLineNum = 109;BA.debugLine="Sub lblAreaIcon_Click";
 //BA.debugLineNum = 110;BA.debugLine="pnlAreas_Click";
_pnlareas_click();
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
public static String  _loadareas() throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.collections.List _listitems = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsareas = null;
int _jj = 0;
 //BA.debugLineNum = 117;BA.debugLine="Private Sub LoadAreas";
 //BA.debugLineNum = 118;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 119;BA.debugLine="Dim ListItems As List";
_listitems = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 120;BA.debugLine="Dim rsAreas As Cursor";
_rsareas = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 122;BA.debugLine="ListItems.Initialize";
_listitems.Initialize();
 //BA.debugLineNum = 124;BA.debugLine="Try";
try { //BA.debugLineNum = 125;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblAreas WH";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblAreas WHERE AreaID <> 14";
 //BA.debugLineNum = 126;BA.debugLine="LogColor(Starter.strCriteria, Colors.Cyan)";
anywheresoftware.b4a.keywords.Common.LogImpl("1655369",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 127;BA.debugLine="rsAreas = Starter.DBCon.ExecQuery(Starter.strCri";
_rsareas = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 129;BA.debugLine="If rsAreas.RowCount > 0 Then";
if (_rsareas.getRowCount()>0) { 
 //BA.debugLineNum = 130;BA.debugLine="For jj = 0 To rsAreas.RowCount - 1";
{
final int step10 = 1;
final int limit10 = (int) (_rsareas.getRowCount()-1);
_jj = (int) (0) ;
for (;_jj <= limit10 ;_jj = _jj + step10 ) {
 //BA.debugLineNum = 131;BA.debugLine="rsAreas.Position = jj";
_rsareas.setPosition(_jj);
 //BA.debugLineNum = 132;BA.debugLine="ListItems.Add(rsAreas.GetString(\"AreaName\"))";
_listitems.Add((Object)(_rsareas.GetString("AreaName")));
 }
};
 }else {
 //BA.debugLineNum = 135;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("1655378",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 } 
       catch (Exception e18) {
			processBA.setLastException(e18); //BA.debugLineNum = 138;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("1655381",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 142;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialog").SetStyle(_alert.getSTYLE_SELECTOR()).SetOthers((java.util.ArrayList)(_listitems.getObject())).SetActionsheetTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetOnCancelClicked(mostCurrent.activityBA,"AreaSelected").SetOnItemClickListener(mostCurrent.activityBA,"AreaSelected");
 //BA.debugLineNum = 151;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 152;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 153;BA.debugLine="End Sub";
return "";
}
public static String  _passwordbinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 308;BA.debugLine="Private Sub PasswordBinder_OnBindView (View As Vie";
 //BA.debugLineNum = 309;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 310;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 311;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 312;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 313;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 314;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf059)))+"  "));
 //BA.debugLineNum = 315;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 317;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 320;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 321;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 322;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 323;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 325;BA.debugLine="End Sub";
return "";
}
public static String  _passworderror() throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 270;BA.debugLine="Private Sub PasswordError";
 //BA.debugLineNum = 271;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 273;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(("ERROR PASSWORD")).SetMessage(("Password is incorrect!")+anywheresoftware.b4a.keywords.Common.CRLF+("Do you want To Try Again?")).SetPositiveText("YES").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetNegativeText("NO").SetNegativeColor((int) (mostCurrent._globalvar._negcolor /*double*/ )).SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"RetryPassword").SetOnNegativeClicked(mostCurrent.activityBA,"RetryPassword").SetOnViewBinder(mostCurrent.activityBA,"PasswordBinder");
 //BA.debugLineNum = 291;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 292;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 294;BA.debugLine="End Sub";
return "";
}
public static String  _pnlareas_click() throws Exception{
 //BA.debugLineNum = 99;BA.debugLine="Sub pnlAreas_Click";
 //BA.debugLineNum = 104;BA.debugLine="vibration.vibratePattern(vibratePattern,0)";
_vibration.vibratePattern(processBA,_vibratepattern,(int) (0));
 //BA.debugLineNum = 106;BA.debugLine="LoadAreas";
_loadareas();
 //BA.debugLineNum = 107;BA.debugLine="End Sub";
return "";
}
public static String  _pnlsettings_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 384;BA.debugLine="Sub pnlSettings_Touch (Action As Int, X As Float,";
 //BA.debugLineNum = 386;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
actregistration._process_globals();
registration._process_globals();
addemployee._process_globals();
globalvar._process_globals();
dbfunctions._process_globals();
addguest._process_globals();
mainscreen._process_globals();
guestlist._process_globals();
dbutils._process_globals();
scale._process_globals();
starter._process_globals();
httputils2service._process_globals();
b4xcollections._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 23;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 24;BA.debugLine="Private vibration As B4Avibrate";
_vibration = new com.johan.Vibrate.Vibrate();
 //BA.debugLineNum = 25;BA.debugLine="Private vibratePattern() As Long";
_vibratepattern = new long[(int) (0)];
;
 //BA.debugLineNum = 27;BA.debugLine="Private InpTyp As SLInpTypeConst";
_inptyp = new bwsi.registration.townhall.slinptypeconst();
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return "";
}
public static String  _retrybutton_click() throws Exception{
 //BA.debugLineNum = 266;BA.debugLine="Private Sub RetryButton_Click()";
 //BA.debugLineNum = 267;BA.debugLine="ShowAdminPassword";
_showadminpassword();
 //BA.debugLineNum = 268;BA.debugLine="End Sub";
return "";
}
public static String  _retrypassword_onnegativeclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 296;BA.debugLine="Private Sub RetryPassword_OnNegativeClicked (View";
 //BA.debugLineNum = 297;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 298;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 299;BA.debugLine="ToastMessageShow($\"Canceled!\"$, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Canceled!")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 300;BA.debugLine="End Sub";
return "";
}
public static String  _retrypassword_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 302;BA.debugLine="Private Sub RetryPassword_OnPositiveClicked (View";
 //BA.debugLineNum = 303;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 304;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 305;BA.debugLine="ShowAdminPassword";
_showadminpassword();
 //BA.debugLineNum = 306;BA.debugLine="End Sub";
return "";
}
public static boolean  _saveuser(String _sempname) throws Exception{
boolean _bretval = false;
 //BA.debugLineNum = 555;BA.debugLine="Private Sub SaveUser(sEmpName As String) As Boolea";
 //BA.debugLineNum = 556;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 558;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 560;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 561;BA.debugLine="Try";
try { //BA.debugLineNum = 563;BA.debugLine="Starter.strCriteria = \"UPDATE tblSysParam \" & _";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblSysParam "+"SET AssignedTo = ? ";
 //BA.debugLineNum = 565;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{_sempname}));
 //BA.debugLineNum = 567;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 568;BA.debugLine="ShowSuccessMsg($\"SUCCESS\"$, $\"Assigned employee";
_showsuccessmsg(("SUCCESS"),("Assigned employee for this device has been successfully updated!"));
 //BA.debugLineNum = 569;BA.debugLine="GlobalVar.AssignedEmp = sEmpName";
mostCurrent._globalvar._assignedemp /*String*/  = _sempname;
 //BA.debugLineNum = 570;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e12) {
			processBA.setLastException(e12); //BA.debugLineNum = 572;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 573;BA.debugLine="ShowUserError($\"ERROR\"$, $\"Unable to update due";
_showusererror(("ERROR"),("Unable to update due to")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage());
 //BA.debugLineNum = 574;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("12490387",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 576;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 577;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 578;BA.debugLine="End Sub";
return false;
}
public static String  _saveuser_onnegativeclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 415;BA.debugLine="Private Sub SaveUser_OnNegativeClicked (View As Vi";
 //BA.debugLineNum = 416;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 417;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 418;BA.debugLine="ToastMessageShow($\"Canceled!\"$, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Canceled!")),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 419;BA.debugLine="End Sub";
return "";
}
public static String  _saveuser_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 421;BA.debugLine="Private Sub SaveUser_OnPositiveClicked (View As Vi";
 //BA.debugLineNum = 422;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 423;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 425;BA.debugLine="If SaveUser(txtEmpName.Text) = True Then";
if (_saveuser(mostCurrent._txtempname.getText())==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 426;BA.debugLine="pnlSettings.Visible = False";
mostCurrent._pnlsettings.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 428;BA.debugLine="End Sub";
return "";
}
public static String  _saveuserfontsizebinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 430;BA.debugLine="Private Sub SaveUserFontSizeBinder_OnBindView (Vie";
 //BA.debugLineNum = 431;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 432;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 433;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 434;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 435;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 436;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf059)))+"  "));
 //BA.debugLineNum = 437;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 439;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 442;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 443;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 444;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 445;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 447;BA.debugLine="End Sub";
return "";
}
public static String  _showadminpassword() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cscontent = null;
 //BA.debugLineNum = 211;BA.debugLine="Private Sub ShowAdminPassword()";
 //BA.debugLineNum = 212;BA.debugLine="Dim csContent As CSBuilder";
_cscontent = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 214;BA.debugLine="csContent.Initialize.Size(12).Color(Colors.Gray).";
_cscontent.Initialize().Size((int) (12)).Color(anywheresoftware.b4a.keywords.Common.Colors.Gray).Append(BA.ObjectToCharSequence(("Enter Administrative Password to Continue."))).PopAll();
 //BA.debugLineNum = 216;BA.debugLine="MatDialogBuilder.Initialize(\"AdminPassword\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"AdminPassword");
 //BA.debugLineNum = 217;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 218;BA.debugLine="MatDialogBuilder.Title($\"ADMINISTRATIVE PASSWORD\"";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(("ADMINISTRATIVE PASSWORD"))).TitleColor(anywheresoftware.b4a.keywords.Common.Colors.Red).TitleGravity(mostCurrent._matdialogbuilder.GRAVITY_START);
 //BA.debugLineNum = 219;BA.debugLine="MatDialogBuilder.IconRes(GlobalVar.WarningIcon).L";
mostCurrent._matdialogbuilder.IconRes(mostCurrent._globalvar._warningicon /*String*/ ).LimitIconToDefaultSize();
 //BA.debugLineNum = 220;BA.debugLine="MatDialogBuilder.InputType(MatDialogBuilder.TYPE_";
mostCurrent._matdialogbuilder.InputType(mostCurrent._matdialogbuilder.TYPE_TEXT_VARIATION_PASSWORD);
 //BA.debugLineNum = 221;BA.debugLine="MatDialogBuilder.Input2($\"Enter Admin Password he";
mostCurrent._matdialogbuilder.Input2(BA.ObjectToCharSequence(("Enter Admin Password here...")),BA.ObjectToCharSequence(("")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 222;BA.debugLine="MatDialogBuilder.AlwaysCallInputCallback";
mostCurrent._matdialogbuilder.AlwaysCallInputCallback();
 //BA.debugLineNum = 223;BA.debugLine="MatDialogBuilder.Content(csContent)";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(_cscontent.getObject()));
 //BA.debugLineNum = 224;BA.debugLine="MatDialogBuilder.ContentColor(Colors.Black)";
mostCurrent._matdialogbuilder.ContentColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 225;BA.debugLine="MatDialogBuilder.PositiveText($\"OK\"$).PositiveCol";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("OK"))).PositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ ));
 //BA.debugLineNum = 226;BA.debugLine="MatDialogBuilder.NegativeText($\"CANCEL\"$).Negativ";
mostCurrent._matdialogbuilder.NegativeText(BA.ObjectToCharSequence(("CANCEL"))).NegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 227;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 228;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 229;BA.debugLine="End Sub";
return "";
}
public static String  _showconfirmclose() throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 495;BA.debugLine="Private Sub ShowConfirmClose";
 //BA.debugLineNum = 496;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 498;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(("CONFIRM CLOSE APPLICATION")).SetMessage(("Do you want to Close Registration App?")).SetPositiveText("YES").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetNegativeText("NO").SetNegativeColor((int) (mostCurrent._globalvar._negcolor /*double*/ )).SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"CloseApp").SetOnNegativeClicked(mostCurrent.activityBA,"CloseApp").SetOnViewBinder(mostCurrent.activityBA,"CloseAppFontSizeBinder");
 //BA.debugLineNum = 516;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 517;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 519;BA.debugLine="End Sub";
return "";
}
public static String  _showsuccessmsg(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 327;BA.debugLine="Private Sub ShowSuccessMsg(sTitle As String, sMsg";
 //BA.debugLineNum = 328;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 330;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetMessage(_smsg).SetPositiveText("OK").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"SuccessMsg").SetOnViewBinder(mostCurrent.activityBA,"SuccessBinder");
 //BA.debugLineNum = 344;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 345;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 347;BA.debugLine="End Sub";
return "";
}
public static String  _showusererror(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 449;BA.debugLine="Private Sub ShowUserError(sTitle As String, sMsg A";
 //BA.debugLineNum = 450;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 452;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
_alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetMessage(_smsg).SetPositiveText("OK").SetPositiveColor((int) (mostCurrent._globalvar._poscolor /*double*/ )).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"UserError").SetOnViewBinder(mostCurrent.activityBA,"UserErrorBinder");
 //BA.debugLineNum = 466;BA.debugLine="Alert.SetDialogBackground(GlobalVar.myCD)";
_alert.SetDialogBackground((android.graphics.drawable.Drawable)(mostCurrent._globalvar._mycd /*anywheresoftware.b4a.objects.drawable.ColorDrawable*/ (mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 467;BA.debugLine="Alert.Build.Show";
_alert.Build().Show();
 //BA.debugLineNum = 469;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 349;BA.debugLine="Private Sub SuccessMsg_OnPositiveClicked (View As";
 //BA.debugLineNum = 350;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 351;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 352;BA.debugLine="pnlSettings.Visible = False";
mostCurrent._pnlsettings.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 354;BA.debugLine="End Sub";
return "";
}
public static boolean  _updateareaid(int _iareaid) throws Exception{
boolean _bretval = false;
 //BA.debugLineNum = 188;BA.debugLine="Private Sub UpdateAreaID (iAreaID As Int) As Boole";
 //BA.debugLineNum = 189;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 191;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 192;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 193;BA.debugLine="Try";
try { //BA.debugLineNum = 194;BA.debugLine="Starter.strCriteria = \"UPDATE tblSysParam \" & _";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblSysParam "+"SET AreaID = ? ";
 //BA.debugLineNum = 197;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{BA.NumberToString(_iareaid)}));
 //BA.debugLineNum = 199;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 200;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 201;BA.debugLine="ProgressDialogShow2($\"Setting Up Area\"$,False)";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Setting Up Area")),anywheresoftware.b4a.keywords.Common.False);
 } 
       catch (Exception e11) {
			processBA.setLastException(e11); //BA.debugLineNum = 203;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 204;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 205;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("1851985",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 207;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 208;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 209;BA.debugLine="End Sub";
return false;
}
public static String  _usererror_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
 //BA.debugLineNum = 471;BA.debugLine="Private Sub UserError_OnPositiveClicked (View As V";
 //BA.debugLineNum = 472;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 473;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
_alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 474;BA.debugLine="End Sub";
return "";
}
public static String  _usererrorbinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 476;BA.debugLine="Private Sub UserErrorBinder_OnBindView (View As Vi";
 //BA.debugLineNum = 477;BA.debugLine="Dim alert As AX_CustomAlertDialog";
_alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 478;BA.debugLine="alert.Initialize";
_alert.Initialize();
 //BA.debugLineNum = 479;BA.debugLine="If ViewType = alert.VIEW_TITLE Then ' Title";
if (_viewtype==_alert.VIEW_TITLE) { 
 //BA.debugLineNum = 480;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 481;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 482;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf071)))+"  "));
 //BA.debugLineNum = 483;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (18)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 485;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 488;BA.debugLine="If ViewType = alert.VIEW_MESSAGE Then";
if (_viewtype==_alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 489;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 490;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 491;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 493;BA.debugLine="End Sub";
return "";
}
}
