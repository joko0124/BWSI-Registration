package bwsi.registration.townhall;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class dbfunctions {
private static dbfunctions mostCurrent = new dbfunctions();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.sql.SQL.CursorWrapper _rstemp = null;
public bwsi.registration.townhall.main _main = null;
public bwsi.registration.townhall.actregistration _actregistration = null;
public bwsi.registration.townhall.registration _registration = null;
public bwsi.registration.townhall.mainscreen _mainscreen = null;
public bwsi.registration.townhall.scale _scale = null;
public bwsi.registration.townhall.addguest _addguest = null;
public bwsi.registration.townhall.addemployee _addemployee = null;
public bwsi.registration.townhall.globalvar _globalvar = null;
public bwsi.registration.townhall.dbutils _dbutils = null;
public bwsi.registration.townhall.starter _starter = null;
public bwsi.registration.townhall.httputils2service _httputils2service = null;
public bwsi.registration.townhall.b4xcollections _b4xcollections = null;
public static String  _getareacode(anywheresoftware.b4a.BA _ba,int _iareaid) throws Exception{
String _sretval = "";
 //BA.debugLineNum = 57;BA.debugLine="Public Sub GetAreaCode(iAreaID As Int) As String";
 //BA.debugLineNum = 58;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 60;BA.debugLine="Try";
try { //BA.debugLineNum = 61;BA.debugLine="Starter.strCriteria = \"SELECT AreaCode FROM tblA";
mostCurrent._starter._strcriteria /*String*/  = "SELECT AreaCode FROM tblAreas "+"WHERE AreaID = "+BA.NumberToString(_iareaid);
 //BA.debugLineNum = 63;BA.debugLine="LogColor(Starter.strCriteria, Colors.Blue)";
anywheresoftware.b4a.keywords.Common.LogImpl("820971526",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 65;BA.debugLine="sRetVal = Starter.DBCon.ExecQuerySingleResult(St";
_sretval = mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult(mostCurrent._starter._strcriteria /*String*/ );
 } 
       catch (Exception e7) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e7); //BA.debugLineNum = 67;BA.debugLine="ToastMessageShow($\"Unable to fetch Branch System";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Branch System Mode due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 68;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("820971531",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 //BA.debugLineNum = 69;BA.debugLine="sRetVal = \"\"";
_sretval = "";
 };
 //BA.debugLineNum = 71;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static String  _getareadesc(anywheresoftware.b4a.BA _ba,int _iareaid) throws Exception{
String _sretval = "";
 //BA.debugLineNum = 40;BA.debugLine="Public Sub GetAreaDesc(iAreaID As Int) As String";
 //BA.debugLineNum = 41;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 43;BA.debugLine="Try";
try { //BA.debugLineNum = 44;BA.debugLine="Starter.strCriteria = \"SELECT AreaDesc FROM tblA";
mostCurrent._starter._strcriteria /*String*/  = "SELECT AreaDesc FROM tblAreas "+"WHERE AreaID = "+BA.NumberToString(_iareaid);
 //BA.debugLineNum = 46;BA.debugLine="LogColor(Starter.strCriteria, Colors.Blue)";
anywheresoftware.b4a.keywords.Common.LogImpl("820905990",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 48;BA.debugLine="sRetVal = Starter.DBCon.ExecQuerySingleResult(St";
_sretval = mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult(mostCurrent._starter._strcriteria /*String*/ );
 } 
       catch (Exception e7) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e7); //BA.debugLineNum = 50;BA.debugLine="ToastMessageShow($\"Unable to fetch Branch System";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Branch System Mode due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 51;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("820905995",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 //BA.debugLineNum = 52;BA.debugLine="sRetVal = \"\"";
_sretval = "";
 };
 //BA.debugLineNum = 54;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public static int  _getareaid(anywheresoftware.b4a.BA _ba) throws Exception{
int _iretval = 0;
 //BA.debugLineNum = 7;BA.debugLine="Public Sub GetAreaID() As Int";
 //BA.debugLineNum = 8;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 10;BA.debugLine="Try";
try { //BA.debugLineNum = 11;BA.debugLine="Starter.strCriteria = \"SELECT AreaID FROM tblSys";
mostCurrent._starter._strcriteria /*String*/  = "SELECT AreaID FROM tblSysParam";
 //BA.debugLineNum = 12;BA.debugLine="LogColor(Starter.strCriteria, Colors.Blue)";
anywheresoftware.b4a.keywords.Common.LogImpl("820774917",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 14;BA.debugLine="iRetVal = Starter.DBCon.ExecQuerySingleResult(St";
_iretval = (int)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult(mostCurrent._starter._strcriteria /*String*/ )));
 } 
       catch (Exception e7) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e7); //BA.debugLineNum = 16;BA.debugLine="ToastMessageShow($\"Unable to fetch Branch System";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Branch System Mode due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 17;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("820774922",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 //BA.debugLineNum = 18;BA.debugLine="iRetVal = 0";
_iretval = (int) (0);
 };
 //BA.debugLineNum = 20;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return 0;
}
public static String  _getareaname(anywheresoftware.b4a.BA _ba,int _iareaid) throws Exception{
String _sretval = "";
 //BA.debugLineNum = 23;BA.debugLine="Public Sub GetAreaName(iAreaID As Int) As String";
 //BA.debugLineNum = 24;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 26;BA.debugLine="Try";
try { //BA.debugLineNum = 27;BA.debugLine="Starter.strCriteria = \"SELECT AreaName FROM tblA";
mostCurrent._starter._strcriteria /*String*/  = "SELECT AreaName FROM tblAreas "+"WHERE AreaID = "+BA.NumberToString(_iareaid);
 //BA.debugLineNum = 29;BA.debugLine="LogColor(Starter.strCriteria, Colors.Blue)";
anywheresoftware.b4a.keywords.Common.LogImpl("820840454",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 31;BA.debugLine="sRetVal = Starter.DBCon.ExecQuerySingleResult(St";
_sretval = mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult(mostCurrent._starter._strcriteria /*String*/ );
 } 
       catch (Exception e7) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e7); //BA.debugLineNum = 33;BA.debugLine="ToastMessageShow($\"Unable to fetch Branch System";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Branch System Mode due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 34;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("820840459",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 //BA.debugLineNum = 35;BA.debugLine="sRetVal = \"\"";
_sretval = "";
 };
 //BA.debugLineNum = 37;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public static int  _getbranchidbyempid(anywheresoftware.b4a.BA _ba,int _iempid) throws Exception{
int _iretval = 0;
 //BA.debugLineNum = 241;BA.debugLine="Public Sub GetBranchIDByEmpID(iEmpID As Int) As In";
 //BA.debugLineNum = 242;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 243;BA.debugLine="Try";
try { //BA.debugLineNum = 244;BA.debugLine="iRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_iretval = (int)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT BranchID FROM tblRegistration WHERE RegID = "+BA.NumberToString(_iempid))));
 } 
       catch (Exception e5) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e5); //BA.debugLineNum = 246;BA.debugLine="ToastMessageShow($\"Unable to fetch Branch ID due";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Branch ID due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 247;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("821495814",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 249;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 250;BA.debugLine="End Sub";
return 0;
}
public static int  _getidbycode(anywheresoftware.b4a.BA _ba,String _sretfield,String _stablename,String _sfieldtocompare,String _scodecomparison) throws Exception{
int _iretval = 0;
 //BA.debugLineNum = 252;BA.debugLine="Public Sub GetIDByCode (sRetField As String, sTabl";
 //BA.debugLineNum = 253;BA.debugLine="Dim iRetval As Int";
_iretval = 0;
 //BA.debugLineNum = 254;BA.debugLine="iRetval = 0";
_iretval = (int) (0);
 //BA.debugLineNum = 255;BA.debugLine="Try";
try { //BA.debugLineNum = 256;BA.debugLine="Starter.strCriteria = \"SELECT \" & sRetField & \"";
mostCurrent._starter._strcriteria /*String*/  = "SELECT "+_sretfield+" FROM "+_stablename+" WHERE "+_sfieldtocompare+" = '"+_scodecomparison+"'";
 //BA.debugLineNum = 257;BA.debugLine="LogColor(Starter.strCriteria, Colors.Blue)";
anywheresoftware.b4a.keywords.Common.LogImpl("821561349",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 259;BA.debugLine="iRetval = Starter.DBCon.ExecQuerySingleResult(St";
_iretval = (int)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult(mostCurrent._starter._strcriteria /*String*/ )));
 } 
       catch (Exception e8) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e8); //BA.debugLineNum = 261;BA.debugLine="ToastMessageShow($\"Unable to fetch ID due to \"$";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch ID due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 262;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("821561354",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 //BA.debugLineNum = 263;BA.debugLine="iRetval = 0";
_iretval = (int) (0);
 };
 //BA.debugLineNum = 265;BA.debugLine="LogColor($\"Return ID: \"$ & iRetval, Colors.Yellow";
anywheresoftware.b4a.keywords.Common.LogImpl("821561357",("Return ID: ")+BA.NumberToString(_iretval),anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 266;BA.debugLine="Return iRetval";
if (true) return _iretval;
 //BA.debugLineNum = 267;BA.debugLine="End Sub";
return 0;
}
public static String  _getnewstubno(anywheresoftware.b4a.BA _ba) throws Exception{
double _dblno = 0;
String _sretval = "";
boolean _blnsaved = false;
 //BA.debugLineNum = 161;BA.debugLine="Sub GetNewStubNo() As String";
 //BA.debugLineNum = 162;BA.debugLine="Dim dblNo As Double";
_dblno = 0;
 //BA.debugLineNum = 163;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 165;BA.debugLine="Dim blnSaved As Boolean";
_blnsaved = false;
 //BA.debugLineNum = 168;BA.debugLine="blnSaved = False";
_blnsaved = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 170;BA.debugLine="Do While Not (blnSaved = True)";
while (anywheresoftware.b4a.keywords.Common.Not(_blnsaved==anywheresoftware.b4a.keywords.Common.True)) {
 //BA.debugLineNum = 171;BA.debugLine="blnSaved = SaveNewStubNo";
_blnsaved = _savenewstubno(_ba);
 //BA.debugLineNum = 172;BA.debugLine="If blnSaved Then";
if (_blnsaved) { 
 //BA.debugLineNum = 173;BA.debugLine="dblNo = Starter.DBCon.ExecQuerySingleResult(\"SE";
_dblno = (double)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT LastRegNo FROM android_metadata")));
 //BA.debugLineNum = 174;BA.debugLine="Exit";
if (true) break;
 }else {
 };
 }
;
 //BA.debugLineNum = 181;BA.debugLine="If GlobalVar.SF.Len(dblNo) = 1 Then";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(BA.NumberToString(_dblno))==1) { 
 //BA.debugLineNum = 182;BA.debugLine="sRetVal = $\"000\"$ & dblNo";
_sretval = ("000")+BA.NumberToString(_dblno);
 }else if(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(BA.NumberToString(_dblno))==2) { 
 //BA.debugLineNum = 184;BA.debugLine="sRetVal = $\"00\"$ & dblNo";
_sretval = ("00")+BA.NumberToString(_dblno);
 }else if(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(BA.NumberToString(_dblno))==3) { 
 //BA.debugLineNum = 186;BA.debugLine="sRetVal = $\"0\"$ & dblNo";
_sretval = ("0")+BA.NumberToString(_dblno);
 }else if(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(BA.NumberToString(_dblno))>=4) { 
 //BA.debugLineNum = 188;BA.debugLine="sRetVal = dblNo";
_sretval = BA.NumberToString(_dblno);
 };
 //BA.debugLineNum = 191;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 193;BA.debugLine="End Sub";
return "";
}
public static int  _getseqno(anywheresoftware.b4a.BA _ba) throws Exception{
double _dblno = 0;
int _iretval = 0;
boolean _blnsaved = false;
 //BA.debugLineNum = 136;BA.debugLine="Public Sub GetSeqNo() As Int";
 //BA.debugLineNum = 137;BA.debugLine="Dim dblNo As Double";
_dblno = 0;
 //BA.debugLineNum = 138;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 140;BA.debugLine="Dim blnSaved As Boolean";
_blnsaved = false;
 //BA.debugLineNum = 143;BA.debugLine="blnSaved = False";
_blnsaved = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 145;BA.debugLine="Do While Not (blnSaved = True)";
while (anywheresoftware.b4a.keywords.Common.Not(_blnsaved==anywheresoftware.b4a.keywords.Common.True)) {
 //BA.debugLineNum = 146;BA.debugLine="blnSaved = SaveSeqNo";
_blnsaved = _saveseqno(_ba);
 //BA.debugLineNum = 147;BA.debugLine="If blnSaved Then";
if (_blnsaved) { 
 //BA.debugLineNum = 148;BA.debugLine="dblNo = Starter.DBCon.ExecQuerySingleResult(\"SE";
_dblno = (double)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT LastSeqNo FROM android_metadata")));
 //BA.debugLineNum = 149;BA.debugLine="Exit";
if (true) break;
 }else {
 };
 }
;
 //BA.debugLineNum = 156;BA.debugLine="iRetVal=dblNo";
_iretval = (int) (_dblno);
 //BA.debugLineNum = 157;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 159;BA.debugLine="End Sub";
return 0;
}
public static long  _gettotalattendees(anywheresoftware.b4a.BA _ba,int _iareaid) throws Exception{
long _lretval = 0L;
 //BA.debugLineNum = 74;BA.debugLine="Public Sub GetTotalAttendees(iAreaID As Int) As Lo";
 //BA.debugLineNum = 75;BA.debugLine="Dim lRetVal As Long";
_lretval = 0L;
 //BA.debugLineNum = 77;BA.debugLine="Try";
try { //BA.debugLineNum = 78;BA.debugLine="Starter.strCriteria = \"SELECT Count(Employees.Re";
mostCurrent._starter._strcriteria /*String*/  = "SELECT Count(Employees.RegID) "+"FROM tblRegistration AS Employees "+"INNER JOIN tblBranches AS Branches ON Employees.BranchID = Branches.BranchID "+"WHERE Employees.WillAttend <> 0 "+"AND Branches.AreaID = "+BA.NumberToString(_iareaid);
 //BA.debugLineNum = 83;BA.debugLine="LogColor(Starter.strCriteria, Colors.Blue)";
anywheresoftware.b4a.keywords.Common.LogImpl("821037065",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 85;BA.debugLine="lRetVal = Starter.DBCon.ExecQuerySingleResult(St";
_lretval = (long)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult(mostCurrent._starter._strcriteria /*String*/ )));
 } 
       catch (Exception e7) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e7); //BA.debugLineNum = 87;BA.debugLine="ToastMessageShow($\"Unable to fetch Branch System";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Branch System Mode due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 88;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("821037070",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 //BA.debugLineNum = 89;BA.debugLine="lRetVal = 0";
_lretval = (long) (0);
 };
 //BA.debugLineNum = 91;BA.debugLine="Return lRetVal";
if (true) return _lretval;
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return 0L;
}
public static long  _gettotregistered(anywheresoftware.b4a.BA _ba,int _iareaid) throws Exception{
long _lretval = 0L;
 //BA.debugLineNum = 94;BA.debugLine="Public Sub GetTotRegistered(iAreaID As Int) As Lon";
 //BA.debugLineNum = 95;BA.debugLine="Dim lRetVal As Long";
_lretval = 0L;
 //BA.debugLineNum = 97;BA.debugLine="Try";
try { //BA.debugLineNum = 98;BA.debugLine="Starter.strCriteria = \"SELECT Count(Employees.Re";
mostCurrent._starter._strcriteria /*String*/  = "SELECT Count(Employees.RegID) "+"FROM tblRegistration AS Employees "+"INNER JOIN tblBranches AS Branches ON Employees.BranchID = Branches.BranchID "+"WHERE Employees.WillAttend <> 0 "+"AND Branches.AreaID = "+BA.NumberToString(_iareaid)+" "+"AND WasRegistered = 1";
 //BA.debugLineNum = 104;BA.debugLine="LogColor(Starter.strCriteria, Colors.Blue)";
anywheresoftware.b4a.keywords.Common.LogImpl("821102602",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 106;BA.debugLine="lRetVal = Starter.DBCon.ExecQuerySingleResult(St";
_lretval = (long)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult(mostCurrent._starter._strcriteria /*String*/ )));
 } 
       catch (Exception e7) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e7); //BA.debugLineNum = 108;BA.debugLine="ToastMessageShow($\"Unable to fetch Branch System";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Branch System Mode due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 109;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("821102607",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 //BA.debugLineNum = 110;BA.debugLine="lRetVal = 0";
_lretval = (long) (0);
 };
 //BA.debugLineNum = 112;BA.debugLine="Return lRetVal";
if (true) return _lretval;
 //BA.debugLineNum = 113;BA.debugLine="End Sub";
return 0L;
}
public static long  _gettotunregister(anywheresoftware.b4a.BA _ba,int _iareaid) throws Exception{
long _lretval = 0L;
 //BA.debugLineNum = 115;BA.debugLine="Public Sub GetTotUnRegister(iAreaID As Int) As Lon";
 //BA.debugLineNum = 116;BA.debugLine="Dim lRetVal As Long";
_lretval = 0L;
 //BA.debugLineNum = 118;BA.debugLine="Try";
try { //BA.debugLineNum = 119;BA.debugLine="Starter.strCriteria = \"SELECT Count(Employees.Re";
mostCurrent._starter._strcriteria /*String*/  = "SELECT Count(Employees.RegID) "+"FROM tblRegistration AS Employees "+"INNER JOIN tblBranches AS Branches ON Employees.BranchID = Branches.BranchID "+"WHERE Employees.WillAttend <> 0 "+"AND Branches.AreaID = "+BA.NumberToString(_iareaid)+" "+"AND WasRegistered = 0";
 //BA.debugLineNum = 125;BA.debugLine="LogColor(Starter.strCriteria, Colors.Blue)";
anywheresoftware.b4a.keywords.Common.LogImpl("821168138",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 127;BA.debugLine="lRetVal = Starter.DBCon.ExecQuerySingleResult(St";
_lretval = (long)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult(mostCurrent._starter._strcriteria /*String*/ )));
 } 
       catch (Exception e7) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e7); //BA.debugLineNum = 129;BA.debugLine="ToastMessageShow($\"Unable to fetch Branch System";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Branch System Mode due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 130;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("821168143",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 //BA.debugLineNum = 131;BA.debugLine="lRetVal = 0";
_lretval = (long) (0);
 };
 //BA.debugLineNum = 133;BA.debugLine="Return lRetVal";
if (true) return _lretval;
 //BA.debugLineNum = 134;BA.debugLine="End Sub";
return 0L;
}
public static boolean  _isthereassignedemp(anywheresoftware.b4a.BA _ba) throws Exception{
boolean _bretval = false;
 //BA.debugLineNum = 269;BA.debugLine="Public Sub IsThereAssignedEmp As Boolean";
 //BA.debugLineNum = 270;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 272;BA.debugLine="Try";
try { //BA.debugLineNum = 273;BA.debugLine="Starter.strCriteria = \"SELECT * FROM android_met";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM android_metadata";
 //BA.debugLineNum = 274;BA.debugLine="rsTemp = Starter.dbcon.ExecQuery(Starter.strCrit";
_rstemp = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 276;BA.debugLine="If rsTemp.RowCount > 0 Then";
if (_rstemp.getRowCount()>0) { 
 //BA.debugLineNum = 277;BA.debugLine="rsTemp.Position = 0";
_rstemp.setPosition((int) (0));
 //BA.debugLineNum = 278;BA.debugLine="If rsTemp.GetString(\"AssignedTo\") = Null Or Glo";
if (_rstemp.GetString("AssignedTo")== null || mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(_rstemp.GetString("AssignedTo")))<=0) { 
 //BA.debugLineNum = 279;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 281;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 };
 }else {
 //BA.debugLineNum = 284;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e16) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e16); //BA.debugLineNum = 288;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("821626899",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 //BA.debugLineNum = 289;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 291;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 292;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 4;BA.debugLine="Private rsTemp As Cursor";
_rstemp = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 5;BA.debugLine="End Sub";
return "";
}
public static boolean  _savenewstubno(anywheresoftware.b4a.BA _ba) throws Exception{
boolean _blnretval = false;
long _lngrec = 0L;
 //BA.debugLineNum = 219;BA.debugLine="Sub SaveNewStubNo() As Boolean";
 //BA.debugLineNum = 220;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 221;BA.debugLine="Dim lngRec As Long";
_lngrec = 0L;
 //BA.debugLineNum = 222;BA.debugLine="Try";
try { //BA.debugLineNum = 223;BA.debugLine="Starter.strCriteria = \"SELECT * FROM android_met";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM android_metadata";
 //BA.debugLineNum = 224;BA.debugLine="rsTemp = Starter.dbcon.ExecQuery(Starter.strCrit";
_rstemp = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 225;BA.debugLine="If rsTemp.RowCount=0 Then";
if (_rstemp.getRowCount()==0) { 
 //BA.debugLineNum = 226;BA.debugLine="Starter.dbcon.ExecNonQuery2(\"INSERT INTO androi";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2("INSERT INTO android_metadata VALUES (?, ?, ?, ?, ?)",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(("english")),(Object)(("1")),(Object)(""),(Object)(1),(Object)(1)}));
 }else {
 //BA.debugLineNum = 228;BA.debugLine="rsTemp.Position = 0";
_rstemp.setPosition((int) (0));
 //BA.debugLineNum = 229;BA.debugLine="lngRec = rsTemp.GetLong(\"LastRegNo\") + 1";
_lngrec = (long) (_rstemp.GetLong("LastRegNo")+1);
 //BA.debugLineNum = 230;BA.debugLine="Starter.strCriteria=\"UPDATE android_metadata SE";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE android_metadata SET LastRegNo = ? ";
 //BA.debugLineNum = 231;BA.debugLine="Starter.dbcon.ExecNonQuery2(Starter.strCriteria";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{BA.NumberToString(_lngrec)}));
 };
 //BA.debugLineNum = 233;BA.debugLine="blnRetVal=True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e16) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e16); //BA.debugLineNum = 235;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 236;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("821430289",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 238;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 239;BA.debugLine="End Sub";
return false;
}
public static boolean  _saveseqno(anywheresoftware.b4a.BA _ba) throws Exception{
boolean _blnretval = false;
long _lngrec = 0L;
 //BA.debugLineNum = 195;BA.debugLine="Sub SaveSeqNo() As Boolean";
 //BA.debugLineNum = 196;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 197;BA.debugLine="Dim lngRec As Long";
_lngrec = 0L;
 //BA.debugLineNum = 198;BA.debugLine="Try";
try { //BA.debugLineNum = 199;BA.debugLine="Starter.strCriteria = \"SELECT * FROM android_met";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM android_metadata";
 //BA.debugLineNum = 200;BA.debugLine="rsTemp = Starter.dbcon.ExecQuery(Starter.strCrit";
_rstemp = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 202;BA.debugLine="If rsTemp.RowCount =  0 Then";
if (_rstemp.getRowCount()==0) { 
 //BA.debugLineNum = 203;BA.debugLine="Starter.dbcon.ExecNonQuery2(\"INSERT INTO androi";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2("INSERT INTO android_metadata VALUES (?, ?, ?, ?, ?)",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(("english")),(Object)(("1")),(Object)(""),(Object)(1),(Object)(1)}));
 }else {
 //BA.debugLineNum = 205;BA.debugLine="rsTemp.Position = 0";
_rstemp.setPosition((int) (0));
 //BA.debugLineNum = 206;BA.debugLine="lngRec = rsTemp.GetLong(\"LastSeqNo\") + 1";
_lngrec = (long) (_rstemp.GetLong("LastSeqNo")+1);
 //BA.debugLineNum = 208;BA.debugLine="Starter.strCriteria=\"UPDATE android_metadata SE";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE android_metadata SET LastSeqNo = ? ";
 //BA.debugLineNum = 209;BA.debugLine="Starter.dbcon.ExecNonQuery2(Starter.strCriteria";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{BA.NumberToString(_lngrec)}));
 };
 //BA.debugLineNum = 211;BA.debugLine="blnRetVal=True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e16) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e16); //BA.debugLineNum = 213;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 214;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("821364755",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 216;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 217;BA.debugLine="End Sub";
return false;
}
}
