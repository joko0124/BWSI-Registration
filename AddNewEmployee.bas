B4A=true
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
	Dim Item As ACMenuItem
	Menu.Clear
	Menu.Add2(1, 1, "People",xmlIcon.GetDrawable("ic_group_add_white_36dp")).ShowAsAction = Item.SHOW_AS_ACTION_IF_ROOM
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
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim ActionBarButton As ACActionBar
	Private ToolBar As ACToolBarDark
	Private xmlIcon As XmlLayoutBuilder
	
	Private CDTxtBox As ColorDrawable
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Scale.SetRate(0.5)
	Activity.LoadLayout("Registration")

	GlobalVar.CSTitle.Initialize.Size(17).Bold.Append(GlobalVar.AreaName).PopAll
	GlobalVar.CSSubTitle.Initialize.Size(14).Append(GlobalVar.AreaDesc).PopAll
	
	ToolBar.InitMenuListener
	ToolBar.Title = GlobalVar.CSTitle
	ToolBar.SubTitle = GlobalVar.CSSubTitle
	
	Dim jo As JavaObject
	Dim xl As XmlLayoutBuilder
	jo = ToolBar
	jo.RunMethod("setPopupTheme", Array(xl.GetResourceId("style", "ToolbarMenu")))
	jo.RunMethod("setContentInsetStartWithNavigation", Array(1dip))
	jo.RunMethod("setTitleMarginStart", Array(0dip))
	
	ActionBarButton.Initialize
	ActionBarButton.ShowUpIndicator = True
	
	CDTxtBox.Initialize2(Colors.Transparent, Colors.Transparent,0,0)

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
			.SetMessage($"Do you want to Register"$ & CRLF & sEmpName & $"?"$) _
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


#Region Database
Private Sub GetEmployeeName(iEmpID As Int) As String
	Dim sRetVal As String
	Try
		sRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT FullName FROM tblRegistration WHERE RegID = " & iEmpID)
	Catch
		sRetVal = ""
		Log(LastException)
	End Try
	Return sRetVal
End Sub

Private Sub RegisterEmp(iRegID As Int) As Boolean
	Dim bRetVal As Boolean
	Dim lngDateTime As Long
	Dim TimeReg As String
	Dim RegSeq, NoPrint As Int

	lngDateTime = DateTime.Now
	DateTime.TimeFormat = "HH:mm:ss"
	TimeReg = DateTime.Time(lngDateTime)
	RegSeq = DBFunctions.GetSeqNo

	NoPrint = GetNoOfStubPrint(iRegID)
	NoPrint = NoPrint + 1

	Starter.DBCon.BeginTransaction
	Try
		Starter.strCriteria = "UPDATE tblRegistration " & _
						  "SET WasRegistered = ?, TimeRegistered = ?, " & _
						  "RegSeq = ?, WasStubPrint = ?, NoPrintStub = ? " & _
						  "WHERE RegID = " & iRegID
							  
		Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String($"1"$, TimeReg, _
									RegSeq, $"1"$, NoPrint))

		Starter.DBCon.TransactionSuccessful
		ProgressDialogShow2($"Preparing Stub Printing"$,True)
		bRetVal = True
	Catch
		ProgressDialogHide
		bRetVal = False
		Log(LastException)
	End Try
	Starter.DBCon.EndTransaction
	Return bRetVal
End Sub

Private Sub UpdateReprintStub(iRegID As Int) As Boolean
	Dim bRetVal As Boolean
	Dim NoPrint As Int

	NoPrint = GetNoOfStubPrint(iRegID)
	NoPrint = NoPrint + 1

	Starter.DBCon.BeginTransaction
	Try
		Starter.strCriteria = "UPDATE tblRegistration " & _
						  "SET NoPrintStub = ? " & _
						  "WHERE RegID = " & iRegID
							  
		Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String(NoPrint))

		Starter.DBCon.TransactionSuccessful
		ProgressDialogShow2($"Preparing Stub Printing"$,True)
		bRetVal = True
	Catch
		ProgressDialogHide
		bRetVal = False
		Log(LastException)
	End Try
	Starter.DBCon.EndTransaction
	Return bRetVal
End Sub

Private Sub GetNoOfStubPrint(iRegID As Int) As Int
	Dim iRetVal As Int
	Try
		iRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT NoPrintStub FROM tblRegistration WHERE RegID = " & iRegID)
	Catch
		iRetVal = 0
		Log(LastException)
	End Try
	Return iRetVal
End Sub

Private Sub GetRegStatus(iRegID As Int) As Int
	Dim iRetVal As Int
	Dim rsStatus As Cursor
	Try
		Starter.strCriteria = "SELECT * FROM tblRegistration WHERE RegID = " & iRegID
		rsStatus = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsStatus.RowCount > 0 Then
			rsStatus.Position = 0
			If rsStatus.GetInt("WasRegistered") = 1 Then 'Registered - Reprint Stub
				iRetVal = 1 'Reprint Stub
			Else
				iRetVal = 2 'Do Nothing
			End If
		End If
	Catch
		iRetVal = 0
		Log(LastException)
	End Try
	rsStatus.Close
	Return iRetVal
End Sub

Private Sub UpdateSwappedEmp(iSwapEmp As Int) As Boolean
'	Dim bRetVal As Boolean
	'
'	bRetVal = False
'	Starter.DBCon.BeginTransaction
'	Try
'		'Update will attend to On Duty
'		Starter.strCriteria = "UPDATE tblRegistration " & _
'							  "SET WasSwapped = ?, SwappedTo = ? " & _
'							  "WHERE RegID = " & iSwapEmp
'		Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String($"1"$, sSwapEmp))
'		Starter.DBCon.TransactionSuccessful
'		bRetVal = True
'	Catch
'		bRetVal = False
'		Log(LastException)
'	End Try
'	Starter.DBCon.EndTransaction
'	Return bRetVal
End Sub

#End Region