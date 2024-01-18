﻿B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=10
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region
#Extends: android.support.v7.app.AppCompatActivity
#If Java

public boolean _onCreateOptionsMenu(android.view.Menu menu) {
	if (processBA.subExists("activity_createmenu")) {
		processBA.raiseEvent2(null, true, "activity_createmenu", false, new de.amberhome.objects.appcompat.ACMenuWrapper(menu));
		return true;
	}
	else
		return false;
}
#End If

Sub Activity_CreateMenu(Menu As ACMenu)
'	Dim Item As ACMenuItem
'	Menu.Clear
'	Menu.Add2(1, 1, "People",xmlIcon.GetDrawable("ic_group_add_white_36dp")).ShowAsAction = Item.SHOW_AS_ACTION_IF_ROOM
'	Menu.Add2(2, 2, "Settings",xmlIcon.GetDrawable("ic_settings_white_24dp")).ShowAsAction = Item.SHOW_AS_ACTION_ALWAYS
End Sub

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

	Private xui As XUI
	
	Private BTAdmin As BluetoothAdmin
	Private PairedDevices As Map
	
	Private FoundDevices As List
	Private DeviceName, DeviceMac As String
	
	Private Serial1 As Serial
	Dim TMPrinter As TextWriter

	Private PrintBuffer As String
	Private PrintLogo() As Byte
	
	Private oStream As AsyncStreams
	Private Res As Int

	Private LogoBMP As Bitmap
	Private WoosimCMD As JavaObject
	Private WoosimImage As JavaObject
	
	Private Logo As Bitmap
	
	Private InpTyp As SLInpTypeConst

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Dim ActionBarButton As ACActionBar
	Private ToolBar As ACToolBarDark
	Private xmlIcon As XmlLayoutBuilder
	
	Private cdTxtBox As ColorDrawable
	Private btnCancel As ACButton
	Private btnSave As ACButton
	
	Private cdCancel, cdSave As ColorDrawable
	Private RegistrationNum As String
	
	Private txtFirstName As EditText
	Private txtMI As EditText
	Private txtLastName As EditText
	Private txtSuffixed As EditText
	Private cboBranches As Spinner
	Private cboDivisions As Spinner
	Private txtTableNo As EditText
	Private txtRemarks As EditText
	
	Private IMEKeyboard As IME
	Private EmpFullName As String
	'Printing
	Dim ESC As String = Chr(27)
	Dim FS As String = Chr(28)
	Dim GS As String = Chr(29)
	
	'Bold and underline don't work well in reversed text
	Dim UNREVERSE As String  = GS & "B" & Chr(0)
	Dim REVERSE As String = GS & "B" & Chr(1)
	
	' Character orientation. Print upside down from right margin
	Dim UNINVERT As String = ESC & "{0"
	Dim INVERT As String = ESC & "{1"
	
	' Character rotation clockwise. Not much use without also reversing the printed character sequence
	Dim UNROTATE As String = ESC & "V0"
	Dim ROTATE As String = ESC & "V1"
	
	' Horizontal tab
	Dim HT As String = Chr(9)
	
	' Character underline
	Dim ULINE0 As String = ESC & "-0"
	Dim ULINE1 As String = ESC & "-1"
	Dim ULINE2 As String = ESC & "-2"
	
	' Character emphasis
	Dim BOLD As String = ESC & "E1"
	Dim NOBOLD As String = ESC & "E0"
	
	' Character height and width
	Dim SINGLE As String = GS & "!" & Chr(0x00)
	Dim HIGH As String = GS & "!" & Chr(0x01)
	Dim WIDE As String = GS & "!" & Chr(0x10)
	Dim HIGHWIDE As String = GS & "!" & Chr(0x11)
	
	' Default settings
	Private LEFTJUSTIFY As String = ESC & "a0"
	Private LINEDEFAULT As String = ESC & "2"
	Private LINSET0 As String = ESC & "$" & Chr(0x0) & Chr(0x0)
	Private LMARGIN0 As String = GS & "L" & Chr(0x0) & Chr(0x0)
	Private WIDTH0 As String = GS & "W" & Chr(0xff) & Chr(0xff)
	Private CHARSPACING0 As String = ESC & " " & Chr(0)
	Private CHARFONT0 As String = ESC & "M" & Chr(0)
	Dim DEFAULTS As String =  CHARSPACING0 & CHARFONT0 & LMARGIN0 & WIDTH0 & LINSET0 & LINEDEFAULT & LEFTJUSTIFY _
		& UNINVERT & UNROTATE & UNREVERSE & NOBOLD & ULINE0
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Scale.SetRate(0.5)
	Activity.LoadLayout("AddEmployeeLayout")

	GlobalVar.CSTitle.Initialize.Size(17).Bold.Append($"ADD NEW EMPLOYEE"$).PopAll
	GlobalVar.CSSubTitle.Initialize.Size(14).Append($"Add Unregister Employee"$).PopAll
	
	ToolBar.InitMenuListener
	ToolBar.Title = GlobalVar.CSTitle
	ToolBar.SubTitle = GlobalVar.CSSubTitle
	
	Dim jo As JavaObject
	Dim xl As XmlLayoutBuilder
	jo = ToolBar
	jo.RunMethod("setPopupTheme", Array(xl.GetResourceId("style", "ToolbarMenu")))
	jo.RunMethod("setContentInsetStartWithNavigation", Array(1dip))
	jo.RunMethod("setTitleMarginStart", Array(0dip))
	
	InpTyp.Initialize
	
	InpTyp.SetInputType(txtFirstName,Array As Int(InpTyp.TYPE_CLASS_TEXT, InpTyp.TYPE_TEXT_FLAG_AUTO_CORRECT, InpTyp.TYPE_TEXT_FLAG_CAP_CHARACTERS))
	InpTyp.SetInputType(txtMI,Array As Int(InpTyp.TYPE_CLASS_TEXT, InpTyp.TYPE_TEXT_FLAG_AUTO_CORRECT, InpTyp.TYPE_TEXT_FLAG_CAP_CHARACTERS))
	InpTyp.SetInputType(txtLastName,Array As Int(InpTyp.TYPE_CLASS_TEXT, InpTyp.TYPE_TEXT_FLAG_AUTO_CORRECT, InpTyp.TYPE_TEXT_FLAG_CAP_CHARACTERS))
	InpTyp.SetInputType(txtSuffixed,Array As Int(InpTyp.TYPE_CLASS_TEXT, InpTyp.TYPE_TEXT_FLAG_AUTO_CORRECT, InpTyp.TYPE_TEXT_FLAG_CAP_CHARACTERS))
	InpTyp.SetInputType(txtRemarks,Array As Int(InpTyp.TYPE_CLASS_TEXT, InpTyp.TYPE_TEXT_FLAG_AUTO_CORRECT, InpTyp.TYPE_TEXT_FLAG_CAP_WORDS))
	InpTyp.SetInputType(txtTableNo,Array As Int(InpTyp.TYPE_CLASS_TEXT, InpTyp.TYPE_TEXT_FLAG_AUTO_CORRECT, InpTyp.TYPE_CLASS_NUMBER))

	ActionBarButton.Initialize
	ActionBarButton.ShowUpIndicator = True
	BTAdmin.Initialize("Admin")
	Serial1.Initialize("Printer")
	
	cdTxtBox.Initialize2(Colors.Transparent, Colors.Transparent,0,0)
	txtFirstName.Background = cdTxtBox
	txtMI.Background = cdTxtBox
	txtLastName.Background = cdTxtBox
	txtSuffixed.Background = cdTxtBox
	cboBranches.Background = cdTxtBox
	cboDivisions.Background = cdTxtBox
	txtRemarks.Background = cdTxtBox
	txtTableNo.Background = cdTxtBox
	
	IMEKeyboard.Initialize("IME")
	txtFirstName.RequestFocus
	IMEKeyboard.ShowKeyboard(txtFirstName)
	
	cdSave.Initialize2(0xFF1E4369, 20, 0,0xFF268FC2)
	btnSave.Background = cdSave
	cdCancel.Initialize2(0xFFDC143C, 20, 0,0xFFDC3545)
	btnCancel.Background = cdCancel

	FillBranches(GlobalVar.AreaID)
	FillDivisions
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = 4 Then
		ToolBar_NavigationItemClick
		Return True
	Else
		Return False
	End If
End Sub

Sub Activity_Resume
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub ToolBar_NavigationItemClick
	Activity.Finish
End Sub

Sub ToolBar_MenuItemClick (Item As ACMenuItem)
	Select Case Item.Id
		Case 1
	End Select
End Sub

#Region MessageBox
Private Sub ConfirmRegister (sEmpName As String)
	Dim Alert As AX_CustomAlertDialog

	Alert.Initialize.Create _
			.SetDialogStyleName("MyDialogDisableStatus") _	'Manifest style name
			.SetStyle(Alert.STYLE_DIALOGUE) _
			.SetCancelable(False) _
			.SetTitle($"CONFIRM REGISTRATION"$) _
			.SetMessage($"Do you want to Add and Register "$ & CRLF & sEmpName & $"?"$) _
			.SetPositiveText("YES") _
			.SetPositiveColor(GlobalVar.PosColor) _
			.SetPositiveTypeface(GlobalVar.FontBold) _
			.SetNegativeText("NO") _
			.SetNegativeColor(GlobalVar.NegColor) _
			.SetNegativeTypeface(GlobalVar.Font) _
			.SetTitleTypeface(GlobalVar.Font) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetOnPositiveClicked("RegisterEmp") _	'listeners
			.SetOnNegativeClicked("RegisterEmp") _
			.SetOnViewBinder("RegFontSizeBinder") 'listeners
			
	Alert.SetDialogBackground(GlobalVar.myCD)
	Alert.Build.Show

End Sub
'Listeners
Private Sub RegisterEmp_OnNegativeClicked (View As View, Dialog As Object)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize.Dismiss(Dialog)
	ToastMessageShow($"Canceled!"$, True)
End Sub

Private Sub RegisterEmp_OnPositiveClicked (View As View, Dialog As Object)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize.Dismiss(Dialog)
	
	If Not(SaveEmployeeRegistration) Then
		ShowEntryError($"ERROR"$, $"Unable to Add New Employee data due to "$ & LastException)
	End If
	GlobalVar.NewRegID = DBFunctions.GetIDByCode("RegID", "tblRegistration", "RegNo", GlobalVar.NewRegNo)
	PrintStub(RegistrationNum)

End Sub

Private Sub RegFontSizeBinder_OnBindView (View As View, ViewType As Int)
	Dim alert As AX_CustomAlertDialog
	alert.Initialize
	If ViewType = alert.VIEW_TITLE Then ' Title
		Dim lbl As Label = View
		Dim CS As CSBuilder
		CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Typeface(Typeface.FONTAWESOME).Size(20).Color(Colors.Red).Append(Chr(0xF059) & "  ")
		CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.Text).Pop

		lbl.Text = CS.PopAll
	End If
	
	If ViewType = alert.VIEW_MESSAGE Then
		Dim lbl As Label = View
		lbl.TextSize = 16
		lbl.TextColor = Colors.Gray
	End If
End Sub

Private Sub ShowPrinterError(sTitle As String, sMsg As String)
	Dim Alert As AX_CustomAlertDialog

	Alert.Initialize.Create _
			.SetDialogStyleName("MyDialogDisableStatus") _	'Manifest style name
			.SetStyle(Alert.STYLE_DIALOGUE) _
			.SetCancelable(False) _
			.SetTitle(sTitle) _
			.SetMessage(sMsg) _
			.SetPositiveText("OK") _
			.SetPositiveColor(GlobalVar.PosColor) _
			.SetPositiveTypeface(GlobalVar.FontBold) _
			.SetTitleTypeface(GlobalVar.Font) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetOnPositiveClicked("PrinterError") _	'listeners
			.SetOnViewBinder("PrinterBinder") 'listeners
			
	Alert.SetDialogBackground(GlobalVar.myCD)
	Alert.Build.Show

End Sub
'Listeners
Private Sub PrinterError_OnPositiveClicked (View As View, Dialog As Object)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize.Dismiss(Dialog)
End Sub

Private Sub PrinterBinder_OnBindView (View As View, ViewType As Int)
	Dim alert As AX_CustomAlertDialog
	alert.Initialize
	If ViewType = alert.VIEW_TITLE Then ' Title
		Dim lbl As Label = View
		Dim CS As CSBuilder
		CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Typeface(Typeface.FONTAWESOME).Size(20).Color(Colors.Red).Append(Chr(0xF071) & "  ")
		CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.Text).Pop

		lbl.Text = CS.PopAll
	End If
	
	If ViewType = alert.VIEW_MESSAGE Then
		Dim lbl As Label = View
		lbl.TextSize = 16
		lbl.TextColor = Colors.Gray
	End If
End Sub

Private Sub ShowSuccessMsg(sTitle As String, sMsg As String)
	Dim Alert As AX_CustomAlertDialog

	Alert.Initialize.Create _
			.SetDialogStyleName("MyDialogDisableStatus") _	'Manifest style name
			.SetStyle(Alert.STYLE_DIALOGUE) _
			.SetCancelable(False) _
			.SetTitle(sTitle) _
			.SetMessage(sMsg) _
			.SetPositiveText("OK") _
			.SetPositiveColor(GlobalVar.PosColor) _
			.SetPositiveTypeface(GlobalVar.FontBold) _
			.SetTitleTypeface(GlobalVar.Font) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetOnPositiveClicked("SuccessMsg") _	'listeners
			.SetOnViewBinder("SuccessBinder") 'listeners
			
	Alert.SetDialogBackground(GlobalVar.myCD)
	Alert.Build.Show

End Sub
'Listeners
Private Sub SuccessMsg_OnPositiveClicked (View As View, Dialog As Object)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize.Dismiss(Dialog)
	Activity.Finish
End Sub

Private Sub SuccessBinder_OnBindView (View As View, ViewType As Int)
	Dim alert As AX_CustomAlertDialog
	alert.Initialize
	If ViewType = alert.VIEW_TITLE Then ' Title
		Dim lbl As Label = View
		Dim CS As CSBuilder
		CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Typeface(Typeface.FONTAWESOME).Size(20).Color(GlobalVar.PosColor).Append(Chr(0xF164) & "  ")
		CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.Text).Pop

		lbl.Text = CS.PopAll
	End If
	
	If ViewType = alert.VIEW_MESSAGE Then
		Dim lbl As Label = View
		lbl.TextSize = 16
		lbl.TextColor = Colors.Gray
	End If
End Sub

Private Sub ShowAddPerson
	Dim Alert As AX_CustomAlertDialog
	
	Dim items As List
	items.Initialize
	items.Add("New Employee")
	items.Add("Guest")
	
	Alert.Initialize.Create _
			.SetDialogStyleName("MyDialog") _	'Manifest style name
			.SetStyle(Alert.STYLE_ACTIONSHEET) _
			.SetTitle("Select an Option") _
			.SetTitleColor(Colors.Black) _
			.SetCancelText("Cancel") _
			.SetNegativeTypeface(GlobalVar.FontBold) _ 'Usable for Cancel Typeface
			.SetOthers(items) _
			.SetActionsheetTypeface(GlobalVar.Font) _
			.SetTitleTypeface(GlobalVar.FontBold) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetOnCancelClicked("AddPeople") _	'listeners
			.SetOnItemClickListener("AddPeople") 	'listeners
			
	Alert.SetDialogBackground(GlobalVar.myCD).SetCancelBackground(GlobalVar.myCD)
	Alert.Build.Show
End Sub

'Item Click
Private Sub AddPeople_OnItemClick (View As View, Selection As String, Position As Int,Id As Long)
	ToastMessageShow(Selection&" Selected! (Position : "&Position&")",False)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize.Dismiss2
	
	Select Case Position
		Case 0 'New Employee
		Case 1 'Guest
	End Select
End Sub
'Actionsheet More Button
Private Sub AddPeople_OnCancelClicked (View As View, Dialog As Object)
	If View<>Null Then
		ToastMessageShow("Cancelled!",False)
		Dim Alert As AX_CustomAlertDialog
		Alert.Initialize.Dismiss(Dialog)
	End If
End Sub
#End Region


Sub btnSave_Click
	If Not(ValidEntries) Then Return
'	NewGuestID = GetGuestID(RegistrationNum)

	If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtMI.Text)) <= 0 Then
		If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtSuffixed.Text)) <= 0 Then
			EmpFullName = txtFirstName.Text & " " & txtLastName.Text
		Else
			EmpFullName = txtFirstName.Text & " " & txtLastName.Text & " " & txtSuffixed.Text
		End If
	Else
		If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtSuffixed.Text)) <= 0 Then
			EmpFullName = txtFirstName.Text & " " & txtMI.Text & " " & txtLastName.Text
		Else
			EmpFullName = txtFirstName.Text & " " & txtMI.Text & " " & txtLastName.Text & " " & txtSuffixed.Text
		End If
	End If
	ConfirmRegister(EmpFullName)
	
End Sub

Sub btnCancel_Click
	Activity.Finish
End Sub

Private Sub ValidEntries() As Boolean
	
	Try
		If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtFirstName.Text)) <= 0 Then
			ShowEntryError($"ERROR"$, $"Employee First Name cannot be blank!"$)
			txtFirstName.RequestFocus
			Return False
		End If
		

		If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtLastName.Text)) <= 0 Then
			ShowEntryError($"ERROR"$, $"Employee Last Name cannot be blank!"$)
			txtLastName.RequestFocus
			Return False
		End If

		If GlobalVar.SF.Len(GlobalVar.SF.Trim(cboBranches.SelectedItem)) <= 0 Then
			ShowEntryError($"ERROR"$, $"Employee Name cannot be blank!"$)
			cboBranches.RequestFocus
			Return False
		End If

		If GlobalVar.SF.Len(GlobalVar.SF.Trim(cboDivisions.SelectedItem)) <= 0 Then
			ShowEntryError($"ERROR"$,$"Employee Division cannot be blank!"$)
			cboDivisions.RequestFocus
			Return False
		End If

		If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtRemarks.Text)) <= 0 Then
			ShowEntryError($"ERROR"$, $"Remarks cannot be blank!"$)
			txtRemarks.RequestFocus
			Return False
		End If
		If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtTableNo.Text)) <= 0 Then
			ShowEntryError($"ERROR"$, $"Added By cannot be blank!"$)
			txtTableNo.RequestFocus
			Return False
		End If
		Return True
	Catch
		Return False
		Log(LastException)
	End Try
End Sub

Private Sub ShowEntryError(sTitle As String, sMsg As String)
	Dim Alert As AX_CustomAlertDialog

	Alert.Initialize.Create _
			.SetDialogStyleName("MyDialogDisableStatus") _	'Manifest style name
			.SetStyle(Alert.STYLE_DIALOGUE) _
			.SetCancelable(False) _
			.SetTitle(sTitle) _
			.SetMessage(sMsg) _
			.SetPositiveText("OK") _
			.SetPositiveColor(GlobalVar.PosColor) _
			.SetPositiveTypeface(GlobalVar.FontBold) _
			.SetTitleTypeface(GlobalVar.Font) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetOnPositiveClicked("EntryError") _	'listeners
			.SetOnViewBinder("EntryErrorBinder") 'listeners
			
	Alert.SetDialogBackground(GlobalVar.myCD)
	Alert.Build.Show

End Sub
'Listeners
Private Sub EntryError_OnPositiveClicked (View As View, Dialog As Object)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize.Dismiss(Dialog)
End Sub

Private Sub EntryErrorBinder_OnBindView (View As View, ViewType As Int)
	Dim alert As AX_CustomAlertDialog
	alert.Initialize
	If ViewType = alert.VIEW_TITLE Then ' Title
		Dim lbl As Label = View
		Dim CS As CSBuilder
		CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Typeface(Typeface.FONTAWESOME).Size(20).Color(Colors.Red).Append(Chr(0xF071) & "  ")
		CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.Text).Pop

		lbl.Text = CS.PopAll
	End If
	
	If ViewType = alert.VIEW_MESSAGE Then
		Dim lbl As Label = View
		lbl.TextSize = 16
		lbl.TextColor = Colors.Gray
	End If
End Sub

Private Sub SaveEmployeeRegistration() As Boolean
	Dim bRetVal As Boolean
	Dim lngDateTime As Long
	Dim RegSeq, NoPrint As Int
	
				
	Dim sLastName, sFirstName, sMI, sSuffixed, EmpName As String
	Dim Remarks, DivisionID, TableNo, AddedBy As String
	Dim TimeReg As String
	Dim iBranchID As Int

	ProgressDialogShow2($"Saving Employee Data..."$, False)
	iBranchID = DBFunctions.GetIDByCode("BranchID", "tblBranches","BranchName",cboBranches.SelectedItem)
	
	sFirstName = txtFirstName.Text
	sMI = txtMI.Text
	sLastName = txtLastName.Text
	sSuffixed = txtSuffixed.Text
	EmpName = EmpFullName
	Remarks = txtRemarks.Text
	TableNo = txtTableNo.Text
	DivisionID = DBFunctions.GetIDByCode("DivisionID","tblDivisions", "DivisionName", cboDivisions.SelectedItem)
	
	AddedBy = GlobalVar.AssignedEmp

	lngDateTime = DateTime.Now
	DateTime.TimeFormat = "HH:mm:ss"
	TimeReg = DateTime.Time(lngDateTime)
	
	RegSeq = DBFunctions.GetSeqNo
	
	NoPrint = 1
	
	GlobalVar.NewRegNo = DBFunctions.GetNewStubNo

	Starter.DBCon.BeginTransaction
	Try
		Starter.strCriteria="INSERT INTO tblRegistration VALUES (" & Null & ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
		Starter.DBCon.ExecNonQuery2(Starter.strCriteria , Array As Object(GlobalVar.NewRegNo, iBranchID, DivisionID, TableNo, sLastName, sFirstName, sMI, sSuffixed, EmpName, $"1"$, $"0"$, Null, $"1"$, Remarks, $"1"$, TimeReg, RegSeq, $"1"$, NoPrint, TimeReg, AddedBy))
		Starter.DBCon.TransactionSuccessful
		RegistrationNum = GlobalVar.NewRegNo
		bRetVal = True
	Catch
		bRetVal = False
		Log(LastException)
	End Try
	Starter.DBCon.EndTransaction

	Return bRetVal
End Sub

#Region Printing
Private Sub PrintStub(iRegID As Int)
	Dim rsData As Cursor
	Dim StubNo As String
	Dim RegFullName As String
	Dim RegBranchName As String
	Dim RegDivision As String
	Dim RegTableNo As Int
	Dim AttendStatus As Int
	
	ProgressDialogShow2($"Stub Printing.  Please Wait..."$, False)
	
	Try
		Starter.strCriteria = "SELECT Employees.RegNo, Employees.FullName, " & _
						  "Branches.BranchName, Divisions.DivisionName, Employees.TableNo, " & _
						  "Employees.WillAttend AS AttendStatus " & _
						  "FROM tblRegistration AS Employees " & _
						  "INNER JOIN tblBranches AS Branches ON Employees.BranchID = Branches.BranchID " & _
						  "INNER JOIN tblDivisions AS Divisions ON Employees.DivisionID = Divisions.DivisionID " & _
						  "WHERE Employees.RegID = " & iRegID & " " & _
						  "AND Employees.WasRegistered = 1"
		
		rsData = Starter.DBCon.ExecQuery(Starter.strCriteria)
		LogColor(Starter.strCriteria, Colors.Magenta)
		
		If rsData.RowCount > 0 Then
			rsData.Position = 0
			StubNo = rsData.GetString("RegNo")
			RegFullName = rsData.GetString("FullName")
			RegBranchName = rsData.GetString("BranchName")
			RegDivision = rsData.GetString("DivisionName")
			RegTableNo = rsData.GetInt("TableNo")
			AttendStatus = rsData.GetInt("AttendStatus")
		Else
			Return
		End If

		PrintBuffer =  ESC & "@" _
					& ESC & Chr(97) & Chr(49) _
					& ESC & Chr(97) & Chr(48) _
					& ESC & "!" & Chr(33) & $"STUB NO.: "$ & BOLD & StubNo & CRLF & Chr(10) _
					& ESC & Chr(97) & Chr(49) _
					& ESC & "!" & Chr(8) & RegFullName & Chr(10) _
					& ESC & "!" & Chr(1) & ESC & "t" & Chr(14) & RegBranchName & Chr(10) _
					& ESC & "!" & Chr(1) & ESC & "t" & Chr(14) & RegDivision & Chr(10) _
					& HIGH  & REVERSE & $"                  "$ & Chr(10) _
					& HIGHWIDE  & UNREVERSE & $"TABLE NO.: "$ & RegTableNo & Chr(10) & Chr(10) _
					& ESC & "!" & Chr(16) & $"Sit back, Listen & Learn!"$ & Chr(10)  & Chr(10) _
					& ESC & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
					& ESC & Chr(97) & Chr(48) _
					& ESC & "!" & Chr(33) & $"DINNER STUB"$ & Chr(10) _
					& ESC & Chr(97) & Chr(49) _
					& ESC & "!" & Chr(112) & StubNo & Chr(10) _
					& ESC & "!" & Chr(8) & RegFullName & Chr(10) _
					& ESC & "!" & Chr(1) & ESC & "t" & Chr(14) & RegBranchName & Chr(10) _
					& ESC & "!" & Chr(1) & ESC & "t" & Chr(14) & RegDivision & Chr(10) _
					& HIGH  & REVERSE & $"                  "$ & Chr(10) _
					& HIGHWIDE  & UNREVERSE & $"TABLE NO.: "$ & RegTableNo & Chr(10) & Chr(10) _
					& ESC & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
					& ESC & Chr(97) & Chr(48) _
					& ESC & "!" & Chr(33) & $"LUNCH STUB"$ & Chr(10) _
					& ESC & Chr(97) & Chr(49) _
					& ESC & "!" & Chr(112) & StubNo & Chr(10) _
					& ESC & "!" & Chr(8) & RegFullName & Chr(10) _
					& ESC & "!" & Chr(1) & ESC & "t" & Chr(14) & RegBranchName & Chr(10) _
					& ESC & "!" & Chr(1) & ESC & "t" & Chr(14) & RegDivision & Chr(10) _
					& HIGH  & REVERSE & $"                  "$ & Chr(10) _
					& HIGHWIDE  & UNREVERSE & $"TABLE NO.: "$ & RegTableNo & Chr(10) & Chr(10) _
					& ESC & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
					& ESC & Chr(97) & Chr(48) _
					& ESC & "!" & Chr(33) & $"RAFFLE STUB"$ & Chr(10) _
					& ESC & Chr(97) & Chr(49) _
					& ESC & "!" & Chr(112) & StubNo & Chr(10) _
					& ESC & "!" & Chr(8) & RegFullName & Chr(10) _
					& ESC & "!" & Chr(1) & ESC & "t" & Chr(14) & RegBranchName & Chr(10) _
					& ESC & "!" & Chr(1) & ESC & "t" & Chr(14) & RegDivision & Chr(10) _
					& HIGH  & REVERSE & $"                  "$ & Chr(10) _
					& HIGHWIDE  & UNREVERSE & $"TABLE NO.: "$ & RegTableNo & Chr(10) & Chr(10) _
					& Chr(10) & ESC & Chr(73)
		StartPrinter
	Catch
		ProgressDialogHide
		Log(LastException)
	End Try
End Sub

Sub StartPrinter
	
	PairedDevices.Initialize
	
	Try
		PairedDevices = Serial1.GetPairedDevices
	Catch
		ShowPrinterError($"PRINTER ERROR"$, $"Getting Paired Devices..."$)
		TMPrinter.Close
		Serial1.Disconnect
	End Try

	If PairedDevices.Size = 0 Then
		ShowPrinterError($"PRINTER ERROR"$, $"Error Connecting to Printer - Either No Paired Bluetooth Printer or Printer Not Found!"$)
		Return
	End If
	
	If PairedDevices.Size = 1 Then
		Try
			DeviceName=PairedDevices.Getkeyat(0)
			DeviceMac=PairedDevices.GetValueAt(0)
			Log(DeviceName & " -> " & DeviceMac)
		
			Serial1.ConnectInsecure(BTAdmin, DeviceMac,1)
			ProgressDialogHide
		Catch
			ShowPrinterError($"PRINTER ERROR"$, $"Printer Connection Error..."$)
			TMPrinter.Close
			Serial1.Disconnect
		End Try
	Else
		FoundDevices.Initialize

		For i = 0 To PairedDevices.Size - 1
			FoundDevices.Add(PairedDevices.GetKeyAt(i))
			DeviceName=PairedDevices.Getkeyat(i)
			DeviceMac=PairedDevices.GetValueAt(i)
			Log(DeviceName & " -> " & DeviceMac)
			Serial1.ConnectInsecure(BTAdmin, DeviceMac,1)
			ProgressDialogHide
			Exit
		Next

		Res = InputList(FoundDevices, "Choose Device", -1)

		If Res <> DialogResponse.CANCEL Then
			Serial1.Connect(PairedDevices.Get(FoundDevices.Get(Res)))
		End If
	End If
End Sub

Sub Printer_Connected (Success As Boolean)
	Log("Connected: " & Success)

	If Success = False Then
		ProgressDialogHide
'		If Not(ConfirmWarning($"Unable to Connect to Printer!"$, $"Printer Error"$, $"Reprint"$, $"Cancel"$, "")) Then
'			Return
'		End If
		StartPrinter
	Else
		'Printing here
		Dim initPrinter() As Byte

		ProgressDialogHide
		TMPrinter.Initialize2(Serial1.OutputStream, "windows-1252")
		oStream.Initialize(Serial1.InputStream, Serial1.OutputStream, "LogoPrint")
		Logo.Initialize(File.DirAssets, "Stub-Header-Townhall.png")
		LogoBMP = CreateScaledBitmap(Logo, Logo.Width, Logo.Height)
		Log(DeviceName)

		WoosimCMD.InitializeStatic("com.woosim.printer.WoosimCmd")
		WoosimImage.InitializeStatic("com.woosim.printer.WoosimImage")
		
		initPrinter = WoosimCMD.RunMethod("initPrinter",Null)
		PrintLogo = WoosimImage.RunMethod("printBitmap", Array (0, 0, 420, 220, LogoBMP))
		
		oStream.Write(initPrinter)
		oStream.Write(WoosimCMD.RunMethod("setPageMode",Null))
		oStream.Write(PrintLogo)
		oStream.Write(WoosimCMD.RunMethod("PM_setStdMode",Null))
		oStream.Write(PrintLogo)

		Sleep(500)
		TMPrinter.WriteLine(PrintBuffer)
		Log(PrintBuffer)
		TMPrinter.Flush
		Sleep(600)
		ShowSuccessMsg($"SUCCESS"$, $"Stub was successfully printed."$ & CRLF & $"Tap OK to Continue..."$)
		TMPrinter.Close
		Serial1.Disconnect
	End If
End Sub

Sub CreateScaledBitmap(Original As Bitmap, NewWidth As Int, NewHeight As Int) As Bitmap
	Dim r As Reflector
	Dim b As Bitmap
	b = r.RunStaticMethod("android.graphics.Bitmap", "createScaledBitmap", Array As Object(Original, NewWidth, NewHeight, True), Array As String("android.graphics.Bitmap", "java.lang.int", "java.lang.int", "java.lang.boolean"))
	Return b
End Sub
#End Region

Private Sub FillBranches (iAreaID As Int)
	Dim SenderFilter As Object

	cboBranches.Clear

	Starter.strCriteria = "SELECT * FROM tblBranches " & _
					  "WHERE AreaID = " & iAreaID
	Starter.DBCon.ExecQuery (Starter.strCriteria)
	
	LogColor(Starter.strCriteria, Colors.Yellow)
	Try
		SenderFilter = Starter.DBCon.ExecQueryAsync("SQL", Starter.strCriteria, Null)
		Wait For (SenderFilter) SQL_QueryComplete (Success As Boolean, RS As ResultSet)
		If Success Then
			Dim StartTime As Long = DateTime.Now
			cboBranches.Clear
			Do While RS.NextRow
				cboBranches.Add(RS.GetString("BranchName"))
			Loop
		Else
			Log(LastException)
		End If

		Log($"List of Branch Records = ${NumberFormat2((DateTime.Now - StartTime) / 1000, 0, 2, 2, False)} seconds to populate ${cboBranches.Size} Branches Records"$)
		
	Catch
		Log(LastException)
	End Try
End Sub


Private Sub FillDivisions ()
	Dim SenderFilter As Object

	cboDivisions.Clear

	Starter.strCriteria = "SELECT * FROM tblDivisions"
	Starter.DBCon.ExecQuery (Starter.strCriteria)
	
	LogColor(Starter.strCriteria, Colors.Yellow)
	Try
		SenderFilter = Starter.DBCon.ExecQueryAsync("SQL", Starter.strCriteria, Null)
		Wait For (SenderFilter) SQL_QueryComplete (Success As Boolean, RS As ResultSet)
		If Success Then
			Dim StartTime As Long = DateTime.Now
			cboDivisions.Clear
			Do While RS.NextRow
				cboDivisions.Add(RS.GetString("DivisionName"))
			Loop
		Else
			Log(LastException)
		End If

		Log($"List of Division Records = ${NumberFormat2((DateTime.Now - StartTime) / 1000, 0, 2, 2, False)} seconds to populate ${cboDivisions.Size} Divisions Records"$)
		
	Catch
		Log(LastException)
	End Try
End Sub


Sub txtFirstName_EnterPressed
	txtMI.RequestFocus
End Sub