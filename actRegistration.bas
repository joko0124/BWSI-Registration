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
	
	Private rsBranch As Cursor
	Private rsEmployees As Cursor

	Private clvEmployees As CustomListView

	Private txtSearch As EditText
	Type EmpData (RegistrationID As Int, Avatar As String, EmpName As String, EmpDivision As String, RegStatus As Int, WillAttend As Int, WasAwardee As Int, sAwards As String)
	Type BranchData (BranchID As Int, BranchName As String)
	
	Private RegID As Int
	Private sRegName As String
	Private MyList As List

'	Private Arrow As ImageView
	Private btnRegister As ACButton
	Private btnSwap As ACButton

	Private AvatarBG As Panel
	Private lblAvatar As Label
	Private lblBranches As Label
	Private lblDivision As Label
	Private lblEmpName As Label
	Private lblStatus As Label
	Private pnlTitle As B4XView
	Private TitleHeight As Int = 30dip
	Private DividerHeight As Int = 5dip
	
	Private CDTxtBox As ColorDrawable
	
	Private blnNewReg As Boolean
	Private btnCancel As ACButton
	Private lblSearchTitle As Label
	Private pnlSearchEmployees As Panel
	Private pnlSearchMain As Panel
	Dim iSwapBranchID As Int
	Dim iSwapEmpID As Int
	Dim sSwapEmp As String
	Private SV As SearchView

	Private lblWasAwardee As Label
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
	
	GlobalVar.AreaID = DBFunctions.GetAreaID
	GlobalVar.AreaCode = DBFunctions.GetAreaCode(GlobalVar.AreaID)
	GlobalVar.AreaName = DBFunctions.GetAreaName(GlobalVar.AreaID)
	GlobalVar.AreaDesc = DBFunctions.GetAreaDesc(GlobalVar.AreaID)
	
	pnlTitle.Visible = False
	pnlTitle.SetLayoutAnimated(0, 0, 0, clvEmployees.AsView.Width, TitleHeight + DividerHeight)
	pnlTitle.LoadLayout("BranchItems")
	BTAdmin.Initialize("Admin")
	Serial1.Initialize("Printer")
	AddBranches(GlobalVar.AreaID)
	SetButtonColors
	RegID = 0
	CDTxtBox.Initialize2(Colors.Transparent, Colors.Transparent,0,0)
	txtSearch.Background = CDTxtBox
	blnNewReg = True
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
	pnlTitle.Visible = False
	GlobalVar.AreaID = DBFunctions.GetAreaID
	GlobalVar.AreaCode = DBFunctions.GetAreaCode(GlobalVar.AreaID)
	GlobalVar.AreaName = DBFunctions.GetAreaName(GlobalVar.AreaID)
	GlobalVar.AreaDesc = DBFunctions.GetAreaDesc(GlobalVar.AreaID)
	blnNewReg = True
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub ToolBar_NavigationItemClick
	Activity.Finish
End Sub

Sub ToolBar_MenuItemClick (Item As ACMenuItem)
	Select Case Item.Id
		Case 1
			ShowAddPerson
	End Select
End Sub


Sub clvEmployees_ItemClick (Index As Int, Value As Object)
	
End Sub

Sub btnSwap_Click
	Dim index As Int = clvEmployees.GetValue(clvEmployees.GetItemFromView(Sender))

	RegID = index
	Dim btnStatus As Int
	
	LogColor(index, Colors.Red)
'	sRegName = GetSwapEmployeeName(GlobalVar.RegID)
	btnStatus = GetRegStatus(RegID)
	LogColor($"Register Status: "$ & btnStatus, Colors.Red)
	
	Try
		Select Case btnStatus
			Case 1
				sRegName = GetEmployeeName(RegID)
				ConfirmReprint(sRegName)
			Case 2
				If pnlSearchMain.Visible = True Then Return
				pnlSearchMain.Visible = True
				lblSearchTitle.Text = $"Search Employee to Swap Duty"$
				SV.Initialize(Me,"SV")
				SV.AddToParent(pnlSearchEmployees,3,3,pnlSearchEmployees.Width - 5dip, pnlSearchEmployees.Height)
				SV.ClearAll
				SV.lv.Clear
				btnCancel.Enabled = True
				iSwapBranchID = DBFunctions.GetBranchIDByEmpID(RegID)
				
				SearchSwapEmployees(iSwapBranchID)
		End Select
	Catch
		Log(LastException)
	End Try
End Sub

Sub btnRegister_Click
	Dim index As Int =clvEmployees.GetValue(clvEmployees.GetItemFromView(Sender))
	RegID = index
	LogColor($"Registration ID: "$ & RegID, Colors.Cyan)
	Try
		sRegName = GetEmployeeName(RegID)
	Catch
		Log(LastException)
	End Try

	ConfirmRegister(sRegName)
End Sub

Sub ShadeColor(clr As Int) As Int
	Dim argb() As Int = GetARGB(clr)
	Dim factor As Float = 0.75
	Return xui.Color_RGB(argb(1) * factor, argb(2) * factor, argb(3) * factor)
End Sub

Sub GetARGB(Color As Int) As Int()
	Private Rets(4) As Int
	Rets(0) = Bit.UnsignedShiftRight(Bit.And(Color, 0xff000000), 24)
	Rets(1) = Bit.UnsignedShiftRight(Bit.And(Color, 0xff0000), 16)
	Rets(2) = Bit.UnsignedShiftRight(Bit.And(Color, 0xff00), 8)
	Rets(3) = Bit.And(Color, 0xff)
	Return Rets
End Sub

Private Sub AddBranches(iAreaID As Int)
	Dim iBranchID As Int
	Dim sBranchName As String

	clvEmployees.Clear
	Try
		Starter.strCriteria = "SELECT DISTINCT Employees.BranchID, Branches.BranchName " & _
						  "FROM tblBranches AS Branches " & _
						  "INNER JOIN tblRegistration AS Employees ON Branches.BranchID = Employees.BranchID " & _
						  "WHERE Branches.AreaID = " & iAreaID & " " & _
						  "ORDER BY Branches.BranchID ASC"

		rsBranch = Starter.DBCon.ExecQuery(Starter.strCriteria)
		
		If rsBranch.RowCount > 0 Then
			Dim StartTime As Long = DateTime.Now
			For x = 0 To rsBranch.RowCount - 1
				rsBranch.Position = x
				iBranchID = rsBranch.GetInt("BranchID")
				sBranchName = rsBranch.GetString("BranchName")
				
				AddBranch(sBranchName)
				
				Starter.strCriteria = "SELECT Registration.RegID, Registration.FirstName, Registration.LastName, Registration.FullName, Branches.BranchName, " & _
								  "Registration.Division, Registration.WillAttend, Registration.WasRegistered, Registration.WasAwardee, Registration.Awards " & _
								  "FROM tblRegistration AS Registration " & _
								  "INNER JOIN tblBranches AS Branches ON Registration.BranchID = Branches.BranchID " & _
								  "WHERE Registration.BranchID = " & iBranchID & " " & _
								  "AND Registration.WillAttend <> 0 " & _
								  "ORDER BY Registration.Division, Registration.FullName ASC	"
				
				LogColor(Starter.strCriteria, Colors.Cyan)
				rsEmployees = Starter.DBCon.ExecQuery(Starter.strCriteria)
						
				If rsEmployees.RowCount > 0 Then
					For i = 0 To rsEmployees.RowCount - 1
						rsEmployees.Position = i
						Dim ED As EmpData
						ED.RegistrationID = rsEmployees.GetInt("RegID")
						ED.Avatar = GlobalVar.SF.Upper(GlobalVar.SF.Left(rsEmployees.GetString("FirstName"),1)) & GlobalVar.SF.Upper(GlobalVar.SF.Left(rsEmployees.GetString("LastName"),1))
						ED.EmpName = rsEmployees.GetString("FullName")
						ED.EmpDivision = rsEmployees.GetString("Division")
						ED.WillAttend = rsEmployees.GetInt("WillAttend")
						ED.RegStatus = rsEmployees.GetInt("WasRegistered")
						ED.WasAwardee = rsEmployees.GetInt("WasAwardee")
						ED.sAwards = rsEmployees.GetString("Awards")
						clvEmployees.Add(AddEmployees(clvEmployees.AsView.Width, ED.EmpName, ED.EmpDivision, ED.RegStatus, ED.WillAttend, ED.Avatar, ED.WasAwardee, ED.sAwards),rsEmployees.GetInt("RegID"))
					Next
				Else
					Log(rsEmployees.RowCount)
				End If
				
			Next
		Else
			Log(LastException)
		End If

		Log($"List of Time Records = ${NumberFormat2((DateTime.Now - StartTime) / 1000, 0, 2, 2, False)} seconds to populate ${clvEmployees.Size} Time Records"$)

	Catch
		Log(LastException)
	End Try

'	clvEmployees_ScrollChanged(0)
	

End Sub

Sub AddBranch (Title As String)
	Dim p As B4XView = xui.CreatePanel("")
	p.SetLayoutAnimated(0, 0, 0, clvEmployees.AsView.Width, TitleHeight)
	p.LoadLayout("BranchItems")
	lblBranches.Text = Title
	LogColor(Title, Colors.Red)
	Dim BD As BranchData
	BD.BranchName = Title
	clvEmployees.AnimationDuration = 0
	clvEmployees.Add(p, BD)
End Sub

Sub AddEmployees(iWidth As Int, strEmpName As String, strBranchName As String, iRegStatus As Int, intWillAttend As Int, sAvatar As String, intWasAwardee As Int, strAwards As String) As Panel
	
	Dim p As B4XView = xui.CreatePanel("")
	Dim iHeight As Int = 125dip
	Dim csStatus As CSBuilder

	If GetDeviceLayoutValues.ApproximateScreenSize < 4.5 Then iHeight = 310dip
	p.SetLayoutAnimated(0, 0, 0, iWidth, iHeight)
	p.LoadLayout("EmployeeList")
	clvEmployees.AnimationDuration = 0
	If intWillAttend = 1 Then
		If iRegStatus = 0 Then
			csStatus.Initialize.Bold.Color(Colors.Red).Append("UNREGISTERED").PopAll
			btnSwap.Enabled = False
			btnSwap.Visible = True
			btnSwap.Text = "Reprint Stub"
			btnRegister.Enabled = True
			btnRegister.Text = "Register"
		Else
			csStatus.Initialize.Bold.Color(0xFF1976D2).Append("REGISTERED").PopAll
			btnSwap.Enabled = True
			btnSwap.Visible = True
			btnSwap.Text = "Reprint Stub"
			btnRegister.Enabled = False
			btnRegister.Text = "Register"
		End If
	Else 'Onduty
		If iRegStatus = 0 Then
			csStatus.Initialize.Bold.Color(Colors.Red).Append("UNREGISTERED").PopAll
			btnSwap.Text = $"Swap Duty"$
			btnSwap.Visible = True
			btnSwap.Enabled = True
			btnRegister.Text = "Register"
		Else
			csStatus.Initialize.Bold.Color(0xFF1976D2).Append("REGISTERED").PopAll
			btnSwap.Visible = True
			btnSwap.Enabled = True
			btnSwap.Text = "Reprint Stub"
			btnRegister.Enabled = False
		End If
	End If
	lblAvatar.Text = sAvatar
	lblEmpName.Text = GlobalVar.SF.Upper(strEmpName)
	lblDivision.Text = strBranchName
	lblStatus.Text = csStatus
	If intWasAwardee = 1 Then
		lblWasAwardee.Visible = True
		lblWasAwardee.Text = $"LOYALTY AWARDEE ("$ & strAwards & $")"$
	Else
		lblWasAwardee.Visible = False
	End If
	AvatarBG.Color = ShadeColor(Rnd(0xFF000050, 0xFFFFFFFF))
	SetButtonColors
	Return p
End Sub

Private Sub SetButtonColors()
	btnSwap.Background = CreateButtonColor(0xFF0D47A1, 0xFF0D47A1,0xFF1E88E5, 0xFF0D47A1)
	btnRegister.Background = CreateButtonColor(0xFF0D47A1, 0xFF0D47A1,0xFF1E88E5, 0xFF0D47A1)
End Sub

Private Sub CreateButtonColor(FocusedColor As Int, EnabledColor As Int, DisabledColor As Int, PressedColor As Int) As StateListDrawable

	Dim RetColor As StateListDrawable
	RetColor.Initialize
	Dim drwFocusedColor, drwEnabledColor, drwDisabledColor, drwPressedColor As ColorDrawable
	
	drwFocusedColor.Initialize2(FocusedColor, 25, 0, Colors.Black)
	drwEnabledColor.Initialize2(EnabledColor, 25, 0, Colors.Black)
	drwDisabledColor.Initialize2(DisabledColor, 25, 2, Colors.Black)
	drwPressedColor.Initialize2(PressedColor, 25, 0, Colors.Black)

	RetColor.AddState(RetColor.State_Focused, drwFocusedColor)
	RetColor.AddState(RetColor.State_Pressed, drwPressedColor)
	RetColor.AddState(RetColor.State_Enabled, drwEnabledColor)
	RetColor.AddState(RetColor.State_Disabled, drwDisabledColor)
	RetColor.AddCatchAllState(drwFocusedColor)
	RetColor.AddCatchAllState(drwEnabledColor)
	RetColor.AddCatchAllState(drwDisabledColor)
	RetColor.AddCatchAllState(drwPressedColor)
	Return RetColor

End Sub

#Region Search Employees
Private Sub SearchEmp(iAreaID As Int, sSearch As String)
	Dim iBranchID As Int
	Dim sBranchName As String

	clvEmployees.Clear
	Try
		Starter.strCriteria = "SELECT DISTINCT Employees.BranchID, Branches.BranchName " & _
						  "FROM tblBranches AS Branches " & _
						  "INNER JOIN tblRegistration AS Employees ON Branches.BranchID = Employees.BranchID " & _
						  "WHERE Branches.AreaID = " & iAreaID & " " & _
						  "AND Employees.FullName LIKE '%" & sSearch & "%' " & _
						  "ORDER BY Branches.BranchID ASC"

		LogColor(Starter.strCriteria, Colors.Yellow)
		rsBranch = Starter.DBCon.ExecQuery(Starter.strCriteria)
		
		If rsBranch.RowCount > 0 Then
			Dim StartTime As Long = DateTime.Now
			For x = 0 To rsBranch.RowCount - 1
				rsBranch.Position = x
				iBranchID = rsBranch.GetInt("BranchID")
				sBranchName = rsBranch.GetString("BranchName")
				
				AddBranch(sBranchName)
				
				Starter.strCriteria = "SELECT Registration.RegID, Registration.FirstName, Registration.LastName, Registration.FullName, Branches.BranchName, " & _
								  "Registration.Division, Registration.WillAttend, Registration.WasRegistered, Registration.WasAwardee, Registration.Awards " & _
								  "FROM tblRegistration AS Registration " & _
								  "INNER JOIN tblBranches AS Branches ON Registration.BranchID = Branches.BranchID " & _
								  "WHERE Registration.BranchID = " & iBranchID & " " & _
								  "AND Registration.WillAttend <> 0 " & _
								  "AND Registration.FullName LIKE '%" & sSearch & "%' " & _
								  "ORDER BY Registration.Division, Registration.FullName ASC"
				
				LogColor(Starter.strCriteria, Colors.Cyan)
				rsEmployees = Starter.DBCon.ExecQuery(Starter.strCriteria)
						
				If rsEmployees.RowCount > 0 Then
					For i = 0 To rsEmployees.RowCount - 1
						rsEmployees.Position = i
						Dim ED As EmpData
						ED.RegistrationID = rsEmployees.GetInt("RegID")
						ED.Avatar = GlobalVar.SF.Upper(GlobalVar.SF.Left(rsEmployees.GetString("FirstName"),1)) & GlobalVar.SF.Upper(GlobalVar.SF.Left(rsEmployees.GetString("LastName"),1))
						ED.EmpName = rsEmployees.GetString("FullName")
						ED.EmpDivision = rsEmployees.GetString("Division")
						ED.WillAttend = rsEmployees.GetInt("WillAttend")
						ED.RegStatus = rsEmployees.GetInt("WasRegistered")
						ED.WasAwardee = rsEmployees.GetInt("WasAwardee")
						ED.sAwards = rsEmployees.GetString("Awards")
						clvEmployees.Add(AddEmployees(clvEmployees.AsView.Width, ED.EmpName, ED.EmpDivision, ED.RegStatus, ED.WillAttend, ED.Avatar, ED.WasAwardee, ED.sAwards),rsEmployees.GetInt("RegID"))
					Next
				Else
					Log(rsEmployees.RowCount)
				End If
				
			Next
		Else
			clvEmployees.Clear
		End If

		Log($"List of Time Records = ${NumberFormat2((DateTime.Now - StartTime) / 1000, 0, 2, 2, False)} seconds to populate ${clvEmployees.Size} Time Records"$)

	Catch
		Log(LastException)
	End Try
End Sub
#End Region

Sub txtSearch_TextChanged (Old As String, New As String)
	If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtSearch.Text)) <= 0 Then
		AddBranches(GlobalVar.AreaID)
		Return
	End If
	
	SearchEmp(GlobalVar.AreaID, txtSearch.Text)
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
	blnNewReg = True
	
	If Not(RegisterEmp(RegID)) Then Return
	PrintStub(RegID)
	ToastMessageShow($"Registered!"$, True)
	AddBranches(GlobalVar.AreaID)
End Sub

' //////////////////////// STUB REPRINTING //////////////////////////////////////////////////////////
Private Sub ConfirmReprint (sEmpName As String)
	Dim Alert As AX_CustomAlertDialog

	Alert.Initialize.Create _
			.SetDialogStyleName("MyDialogDisableStatus") _	'Manifest style name
			.SetStyle(Alert.STYLE_DIALOGUE) _
			.SetCancelable(False) _
			.SetTitle($"CONFIRM REPRINT STUB"$) _
			.SetMessage($"Do you want to Reprint"$ & CRLF & sEmpName & $" stub?"$) _
			.SetPositiveText("YES") _
			.SetPositiveColor(GlobalVar.PosColor) _
			.SetPositiveTypeface(GlobalVar.FontBold) _
			.SetNegativeText("NO") _
			.SetNegativeColor(GlobalVar.NegColor) _
			.SetNegativeTypeface(GlobalVar.Font) _
			.SetTitleTypeface(GlobalVar.Font) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetOnPositiveClicked("StubReprint") _	'listeners
			.SetOnNegativeClicked("StubReprint") _
			.SetOnViewBinder("RegFontSizeBinder") 'listeners
			
	Alert.SetDialogBackground(GlobalVar.myCD)
	Alert.Build.Show

End Sub
'Listeners
Private Sub StubReprint_OnNegativeClicked (View As View, Dialog As Object)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize.Dismiss(Dialog)
	ToastMessageShow($"Reprinting Cancelled!"$, True)
End Sub

Private Sub StubReprint_OnPositiveClicked (View As View, Dialog As Object)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize.Dismiss(Dialog)

	blnNewReg = False
	
	If Not(UpdateReprintStub(RegID)) Then Return
	PrintStub(RegID)
	ToastMessageShow($"Reprinted!"$, True)
	AddBranches(GlobalVar.AreaID)
End Sub
' ///////////////////////// Swap Confirmation ///////////////////////////////////////
Private Sub ConfirmSwapEmp (sEmpName As String)
	Dim Alert As AX_CustomAlertDialog

	Alert.Initialize.Create _
			.SetDialogStyleName("MyDialogDisableStatus") _	'Manifest style name
			.SetStyle(Alert.STYLE_DIALOGUE) _
			.SetCancelable(False) _
			.SetTitle($"CONFIRM SWAP DUTY"$) _
			.SetMessage($"Confirm Swap Duty with"$ & CRLF & sEmpName & $"?"$) _
			.SetPositiveText("YES") _
			.SetPositiveColor(GlobalVar.PosColor) _
			.SetPositiveTypeface(GlobalVar.FontBold) _
			.SetNegativeText("NO") _
			.SetNegativeColor(GlobalVar.NegColor) _
			.SetNegativeTypeface(GlobalVar.Font) _
			.SetTitleTypeface(GlobalVar.Font) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetOnPositiveClicked("SwapEmp") _	'listeners
			.SetOnNegativeClicked("SwapEmp") _
			.SetOnViewBinder("RegFontSizeBinder") 'listeners
			
	Alert.SetDialogBackground(GlobalVar.myCD)
	Alert.Build.Show

End Sub
'Listeners
Private Sub SwapEmp_OnNegativeClicked (View As View, Dialog As Object)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize.Dismiss(Dialog)
	ToastMessageShow($"Canceled!"$, True)
End Sub

Private Sub SwapEmp_OnPositiveClicked (View As View, Dialog As Object)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize.Dismiss(Dialog)
	If UpdateSwappedEmp(iSwapEmpID, RegID) = False Then Return
	If RegisterEmp(RegID) = False Then Return
	PrintStub(RegID)
	AddBranches(GlobalVar.AreaID)
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
	If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtSearch.Text))> 0 Then txtSearch.Text = ""

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
		Case 0
			ToastMessageShow($"Sorry, Module not available..."$, True)
		Case 1
			StartActivity(AddGuest)
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

#Region Printing
Private Sub PrintStub(iRegID As Int)
	Dim rsData As Cursor
	Dim StubNo As String
	Dim RegFullName As String
	Dim RegBranchName As String
	Dim RegDivision As String
	Dim AttendStatus As Int
	Dim WasAwardee As Int
	Dim sAwards As String
	Dim sAwardsMsg As String
	
	Dim WasGuest As Int
	
	ProgressDialogShow2($"Stub Printing.  Please Wait..."$, False)
	
	Try
		Starter.strCriteria = "SELECT Employees.RegNo, Employees.FullName, " & _
						  "Branches.BranchName, Employees.Division, " & _
						  "Employees.WillAttend AS AttendStatus, Employees.WasAwardee, Employees.Awards, Employees.WasGuest " & _
						  "FROM tblRegistration AS Employees " & _
						  "INNER JOIN tblBranches AS Branches ON Employees.BranchID = Branches.BranchID " & _
						  "WHERE Employees.RegID = " & iRegID & " " & _
						  "AND Employees.WasRegistered = 1"
		rsData = Starter.DBCon.ExecQuery(Starter.strCriteria)
		LogColor(Starter.strCriteria, Colors.Magenta)
		
		If rsData.RowCount > 0 Then
			rsData.Position = 0
			StubNo = rsData.GetString("RegNo")
			RegFullName = rsData.GetString("FullName")
			RegBranchName = rsData.GetString("BranchName")
			RegDivision = rsData.GetString("Division")
			AttendStatus = rsData.GetInt("AttendStatus")
			WasAwardee = rsData.GetInt("WasAwardee")
			sAwards = rsData.GetString("Awards")
			WasGuest = rsData.GetInt("WasGuest")
		Else
			Return
		End If
		
		If WasAwardee = 1 Then
			If sAwards = "10 YEARS" Then
				sAwardsMsg = "Thank you for Amazing DECADE of service!"
			Else If sAwards = "15 YEARS" Then
				sAwardsMsg = "Thank you for your 15 Years of Service!"
			Else If sAwards = "20 YEARS" Then
				sAwardsMsg = "Thank you for your 20 Years of Service!"
			Else If sAwards = "25 YEARS" Then
				sAwardsMsg = "Thank you for your 25 Years of Service!"
			End If
		End If
		
		If WasGuest = 1 Then 'Guest Only
			PrintBuffer = Chr(27) & "@" _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(8) & $"LAUSGROUP EVENT CENTRE"$ & Chr(10) _
					& Chr(27) & "!" & Chr(8) & $"December 19, 2023"$ & CRLF & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(33) & $"STUB NO.: "$ & Chr(10) _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(112) & StubNo & Chr(10) _
					& Chr(27) & "!" & Chr(8) & RegFullName & Chr(10) & Chr(10) _
					& Chr(27) & "!" & Chr(0) & $"Have a Blessed Holiday Season!"$ & CRLF & Chr(10) _
					& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(33) & $"DINNER STUB"$ & Chr(10) _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(112) & StubNo & Chr(10) _
					& Chr(27) & "!" & Chr(8) & RegFullName & Chr(10) _
					& Chr(10) & Chr(27) & Chr(97) & Chr(10)
		Else If AttendStatus = 1 Then 'Attendees
			If WasAwardee = 1 Then
				PrintBuffer = Chr(27) & "@" _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(8) & $"LAUSGROUP EVENT CENTRE"$ & Chr(10) _
					& Chr(27) & "!" & Chr(8) & $"December 19, 2023"$ & CRLF & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(33) & $"STUB NO.: "$ & Chr(10) _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(112) & StubNo & Chr(10) _
					& Chr(27) & "!" & Chr(8) & RegFullName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegBranchName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegDivision & Chr(10) & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(32) & sAwardsMsg & Chr(10) _
					& Chr(27) & "!" & Chr(0) & $"Have a Blessed Holiday Season!"$ & Chr(10)  & Chr(10) _
					& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(33) & $"DINNER STUB"$ & Chr(10) _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(112) & StubNo & Chr(10) _
					& Chr(27) & "!" & Chr(8) & RegFullName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegBranchName & Chr(10) & Chr(10) _
					& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(33) & $"RAFFLE STUB"$ & Chr(10) _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(112) & StubNo & Chr(10) _
					& Chr(27) & "!" & Chr(8) & RegFullName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegBranchName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegDivision & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & sAwards & $" LOYALTY AWARDEE"$ & Chr(10) & Chr(10) _
					& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(33) & $"TOKEN STUB"$ & Chr(10) _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(112) & StubNo & Chr(10) _
					& Chr(27) & "!" & Chr(8) & RegFullName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegBranchName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegDivision & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & sAwards & $" LOYALTY AWARDEE"$ & Chr(10) & Chr(10) _
					& Chr(10) & Chr(27) & Chr(97) & Chr(10)
			Else
				PrintBuffer = Chr(27) & "@" _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(8) & $"LAUSGROUP EVENT CENTRE"$ & Chr(10) _
					& Chr(27) & "!" & Chr(8) & $"December 19, 2023"$ & CRLF & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(33) & $"STUB NO.: "$ & Chr(10) _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(112) & StubNo & Chr(10) _
					& Chr(27) & "!" & Chr(8) & RegFullName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegBranchName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegDivision & Chr(10) & Chr(10) _
					& Chr(27) & "!" & Chr(0) & $"Have a Blessed Holiday Season!"$ & Chr(10)  & Chr(10) _
					& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(33) & $"DINNER STUB"$ & Chr(10) _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(112) & StubNo & Chr(10) _
					& Chr(27) & "!" & Chr(8) & RegFullName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegBranchName & Chr(10) & Chr(10) _
					& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(33) & $"RAFFLE STUB"$ & Chr(10) _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(112) & StubNo & Chr(10) _
					& Chr(27) & "!" & Chr(8) & RegFullName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegBranchName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegDivision & Chr(10) & Chr(10) _
					& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(33) & $"TOKEN STUB"$ & Chr(10) _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(112) & StubNo & Chr(10) _
					& Chr(27) & "!" & Chr(8) & RegFullName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegBranchName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegDivision & Chr(10) & Chr(10) _
					& Chr(10) & Chr(27) & Chr(97) & Chr(10)
			End If

		Else If AttendStatus = 2 Then 'On Duty
			If WasAwardee = 1 Then
				PrintBuffer = Chr(27) & "@" _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(8) & $"LAUSGROUP EVENT CENTRE"$ & Chr(10) _
					& Chr(27) & "!" & Chr(8) & $"December 19, 2023"$ & CRLF & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(33) & $"STUB NO.: "$ & Chr(10) _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(112) & StubNo & Chr(10) _
					& Chr(27) & "!" & Chr(8) & RegFullName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegBranchName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegDivision & Chr(10) & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(32) & sAwardsMsg & Chr(10) _
					& Chr(27) & "!" & Chr(0) & "Have a Blessed Holiday Season!" & Chr(10)  & Chr(10) _
					& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(33) & $"RAFFLE STUB"$ & Chr(10) _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(112) & StubNo & Chr(10) _
					& Chr(27) & "!" & Chr(8) & RegFullName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegBranchName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegDivision & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & sAwards & " LOYALTY AWARDEE" & Chr(10) & Chr(10) _
					& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(33) & $"TOKEN STUB"$ & Chr(10) _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(112) & StubNo & Chr(10) _
					& Chr(27) & "!" & Chr(8) & RegFullName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegBranchName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegDivision & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & sAwards & " LOYALTY AWARDEE" & Chr(10) & Chr(10) _
					& Chr(10) & Chr(27) & Chr(97) & Chr(10)
			Else
				PrintBuffer = Chr(27) & "@" _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(8) & $"LAUSGROUP EVENT CENTRE"$ & Chr(10) _
					& Chr(27) & "!" & Chr(8) & $"December 19, 2023"$ & CRLF & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(33) & $"STUB NO.: "$ & Chr(10) _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(112) & StubNo & Chr(10) _
					& Chr(27) & "!" & Chr(8) & RegFullName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegBranchName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegDivision & Chr(10) & Chr(10) _
					& Chr(27) & "!" & Chr(0) & $"Have a Blessed Holiday Season!"$ & CRLF & Chr(10) _
					& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(33) & $"RAFFLE STUB"$ & Chr(10) _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(112) & StubNo & Chr(10) _
					& Chr(27) & "!" & Chr(8) & RegFullName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegBranchName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegDivision & Chr(10) & Chr(10) _
					& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(33) & $"TOKEN STUB"$ & Chr(10) _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(112) & StubNo & Chr(10) _
					& Chr(27) & "!" & Chr(8) & RegFullName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegBranchName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & RegDivision & Chr(10) & Chr(10) _
					& Chr(10) & Chr(27) & Chr(97) & Chr(10)
			End If
		End If
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
		Logo.Initialize(File.DirAssets, "Stub-Header.png")
		LogoBMP = CreateScaledBitmap(Logo, Logo.Width, Logo.Height)
		Log(DeviceName)

		WoosimCMD.InitializeStatic("com.woosim.printer.WoosimCmd")
		WoosimImage.InitializeStatic("com.woosim.printer.WoosimImage")
		
		initPrinter = WoosimCMD.RunMethod("initPrinter",Null)
		PrintLogo = WoosimImage.RunMethod("printBitmap", Array (0, 0, 420, 205, LogoBMP))
		
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
		If blnNewReg = True Then
			ShowSuccessMsg($"SUCCESS"$, $"Stub was successfully printed."$ & CRLF & $"Tap OK to Continue..."$)
		Else
			ShowSuccessMsg($"REPRINT SUCCESS"$, $"Stub was successfully reprinted."$ & CRLF & $"Tap OK to Continue..."$)
		End If
'		DispInfoMsg($"Stub was successfully printed."$ & $"Tap OK to Continue..."$, Application.LabelName)
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

Private Sub SearchSwapEmployees(iBranchID As Int)
	Dim rsSwapEmployees As Cursor
	Dim SearchList As List
	
	If SV.IsInitialized=False Then
		SV.Initialize(Me,"SV")
	End If
	
	SV.ClearAll
	SV.ClearSearchBox
	SV.lv.Clear

	SearchList.Initialize
	SearchList.Clear
	Try
		Starter.strCriteria = "SELECT tblRegistration.RegID, tblRegistration.FullName, tblBranches.BranchName " & _
						  "FROM tblRegistration INNER JOIN tblBranches ON tblRegistration.BranchID = tblBranches.BranchID " & _
						  "WHERE tblRegistration.WillAttend = 1 " & _
						  "AND tblRegistration.WasRegistered = 0 " & _
						  "AND tblRegistration.BranchID = " & iBranchID
		
		LogColor(Starter.strCriteria, Colors.Cyan)
		
		rsSwapEmployees = Starter.DBCon.ExecQuery(Starter.strCriteria)

		If rsSwapEmployees.RowCount > 0 Then
			For i = 0 To rsSwapEmployees.RowCount - 1
				rsSwapEmployees.Position = i
				Dim it As Item
				it.Title=rsSwapEmployees.GetString("FullName")
				it.Text=rsSwapEmployees.GetString("BranchName")
				it.SearchText=rsSwapEmployees.GetString("FullName").ToLowerCase
				it.Value=rsSwapEmployees.GetInt("RegID")
				SearchList.Add(it)
			Next
		Else
			Return
		End If
		SV.SetItems(SearchList)
		SearchList.Clear
	Catch
		Log(LastException)
	End Try
End Sub

Private Sub GetSwapEmployeeName(iEmpID As Int) As String
	Dim sRetVal As String
	Try
		sRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT FullName FROM tblRegistration WHERE RegID = " & iEmpID)
	Catch
		sRetVal = ""
		Log(LastException)
	End Try
	Return sRetVal
End Sub



Sub SV_ItemClick(Value As Int)
	Log(Value)
	iSwapEmpID = Value
	SV.ClearAll
	SearchClosed
	sSwapEmp = GetSwapEmployeeName(iSwapEmpID)
	If sSwapEmp = "" Then Return
	ConfirmSwapEmp(sSwapEmp)
End Sub


Private Sub UpdateSwappedEmp(iSwapEmp As Int, iRegID As Int) As Boolean
	Dim bRetVal As Boolean

	bRetVal = False
	Starter.DBCon.BeginTransaction
	Try
		'Update will attend to On Duty
		Starter.strCriteria = "UPDATE tblRegistration " & _
						  "SET WasSwapped = ?, SwappedTo = ?, WillAttend = ?" & _
						  "WHERE RegID = " & iRegID
		Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String($"1"$, sSwapEmp, $"1"$))

		Starter.strCriteria = "UPDATE tblRegistration " & _
						  "SET WillAttend = ?" & _
						  "WHERE RegID = " & iSwapEmp
		Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String($"2"$))
		Starter.DBCon.TransactionSuccessful
		bRetVal = True
	Catch
		bRetVal = False
		Log(LastException)
	End Try
	Starter.DBCon.EndTransaction
	Return bRetVal
End Sub

#End Region

Sub pnlSearchMain_Touch (Action As Int, X As Float, Y As Float)
	
End Sub

Sub btnCancel_Click
	SearchClosed	
	AddBranches(GlobalVar.AreaID)
End Sub
Sub SearchClosed
	SV.ClearSearchBox
	SV.ClearAll
	pnlSearchMain.Visible = False
End Sub
