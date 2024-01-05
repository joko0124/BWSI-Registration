package bwsi.registration.townhall;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class starter extends  android.app.Service{
	public static class starter_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
            BA.LogInfo("** Receiver (starter) OnReceive **");
			android.content.Intent in = new android.content.Intent(context, starter.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
            ServiceHelper.StarterHelper.startServiceFromReceiver (context, in, true, BA.class);
		}

	}
    static starter mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return starter.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "bwsi.registration.townhall", "bwsi.registration.townhall.starter");
            if (BA.isShellModeRuntimeCheck(processBA)) {
                processBA.raiseEvent2(null, true, "SHELL", false);
		    }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "bwsi.registration.townhall.starter", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!true && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("*** Service (starter) Create ***");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (true) {
			ServiceHelper.StarterHelper.runWaitForLayouts();
		}
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		onStartCommand(intent, 0, 0);
    }
    @Override
    public int onStartCommand(final android.content.Intent intent, int flags, int startId) {
    	if (ServiceHelper.StarterHelper.onStartCommand(processBA, new Runnable() {
            public void run() {
                handleStart(intent);
            }}))
			;
		else {
			ServiceHelper.StarterHelper.addWaitForLayout (new Runnable() {
				public void run() {
                    processBA.setActivityPaused(false);
                    BA.LogInfo("** Service (starter) Create **");
                    processBA.raiseEvent(null, "service_create");
					handleStart(intent);
                    ServiceHelper.StarterHelper.removeWaitForLayout();
				}
			});
		}
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    public void onTaskRemoved(android.content.Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if (true)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (starter) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = ServiceHelper.StarterHelper.handleStartIntent(intent, _service, processBA);
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	
	@Override
	public void onDestroy() {
        super.onDestroy();
        if (true) {
            BA.LogInfo("** Service (starter) Destroy (ignored)**");
        }
        else {
            BA.LogInfo("** Service (starter) Destroy **");
		    processBA.raiseEvent(null, "service_destroy");
            processBA.service = null;
		    mostCurrent = null;
		    processBA.setActivityPaused(true);
            processBA.runHook("ondestroy", this, null);
        }
	}

@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.sql.SQL _dbcon = null;
public static String _strcriteria = "";
public static String _dbpath = "";
public static anywheresoftware.b4a.objects.RuntimePermissions _rtp = null;
public static String _safedirectory = "";
public static String _dbname = "";
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
public bwsi.registration.townhall.httputils2service _httputils2service = null;
public bwsi.registration.townhall.b4xcollections _b4xcollections = null;
public static boolean  _application_error(anywheresoftware.b4a.objects.B4AException _error,String _stacktrace) throws Exception{
 //BA.debugLineNum = 31;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 32;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Public DBCon As SQL";
_dbcon = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 8;BA.debugLine="Public strCriteria As String";
_strcriteria = "";
 //BA.debugLineNum = 9;BA.debugLine="Public DBPath As String";
_dbpath = "";
 //BA.debugLineNum = 10;BA.debugLine="Public RTP As RuntimePermissions";
_rtp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 12;BA.debugLine="Public SafeDirectory As String";
_safedirectory = "";
 //BA.debugLineNum = 13;BA.debugLine="Public DBName As String = \"MasterDB.db\"";
_dbname = "MasterDB.db";
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 17;BA.debugLine="Log(RTP.GetSafeDirDefaultExternal(\"\"))";
anywheresoftware.b4a.keywords.Common.LogImpl("826411009",_rtp.GetSafeDirDefaultExternal(""),0);
 //BA.debugLineNum = 18;BA.debugLine="DBPath = DBUtils.CopyDBFromAssets(\"MasterDB.db\")";
_dbpath = mostCurrent._dbutils._copydbfromassets /*String*/ (processBA,"MasterDB.db");
 //BA.debugLineNum = 19;BA.debugLine="DBCon.Initialize(DBPath, \"MasterDB.db\",False)";
_dbcon.Initialize(_dbpath,"MasterDB.db",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 35;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 23;BA.debugLine="Service.StopAutomaticForeground 'Starter service";
mostCurrent._service.StopAutomaticForeground();
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _service_taskremoved() throws Exception{
 //BA.debugLineNum = 26;BA.debugLine="Sub Service_TaskRemoved";
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return "";
}
}
