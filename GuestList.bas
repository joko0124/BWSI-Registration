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
	Private vibration As B4Avibrate
	Private vibratePattern() As Long
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim ActionBarButton As ACActionBar
	Private ToolBar As ACToolBarDark
	Private xmlIcon As XmlLayoutBuilder
	

	Type GuestInfo (GuestID As Int, StubNo As String, GuestName As String, GuestPos As String, Avatar As String)
	Private clvGuests As CustomListView

	Private txtSearch As EditText
	
	Private iGuestID As Int
	Private sGuestName As String
	Private MyList As List

'	Private Arrow As ImageView

	Private CDTxtBox As ColorDrawable

	Private AvatarBG As Panel
	Private lblAvatar As Label
	Private lblGuestName As Label
	Private lblPosition As Label
	
	Private lblRecCount As Label
	Private IMEKeyboard As IME
	
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
	Activity.LoadLayout("GuestListing")

	GlobalVar.CSTitle.Initialize.Size(17).Bold.Append($"Townhall's Guest List"$).PopAll
	GlobalVar.CSSubTitle.Initialize.Size(14).Append($"List of Guest"$).PopAll
	
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
	
	BTAdmin.Initialize("Admin")
	Serial1.Initialize("Printer")
'	AddBranches(GlobalVar.AreaID)

	FillGuests
	CDTxtBox.Initialize2(Colors.Transparent, Colors.Transparent,0,0)
	txtSearch.Background = CDTxtBox

	IMEKeyboard.Initialize("")
	vibratePattern = Array As Long(500, 500, 300, 500)
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
	If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtSearch.Text))<=0 Then
		IMEKeyboard.HideKeyboard
		Activity.Finish
	Else
		txtSearch.Text = ""
		IMEKeyboard.HideKeyboard
	End If
End Sub

Sub ToolBar_MenuItemClick (Item As ACMenuItem)
	Select Case Item.Id
		Case 1
			GuestOption
	End Select
End Sub

#Region Employee Listing
Private Sub FillGuests()
	Dim SenderFilter As Object

	Try
		Starter.strCriteria = "SELECT Guests.GuestID, Guests.StubNo, " & _
						  "Guests.TableNo, Guests.GuestName, " & _
						  "Guests.Remarks_Occupation " & _
						  "FROM tblGuests AS Guests " & _
						  "ORDER BY Guests.GuestID, Guests.RegSeq ASC"
		LogColor(Starter.strCriteria, Colors.Yellow)

		SenderFilter = Starter.DBCon.ExecQueryAsync("SQL", Starter.strCriteria, Null)
		Wait For (SenderFilter) SQL_QueryComplete (Success As Boolean, RS As ResultSet)
		If Success Then
			Dim StartTime As Long = DateTime.Now
			clvGuests.Clear
			Do While RS.NextRow
				Dim GInfo As GuestInfo
				GInfo.Initialize
				GInfo.GuestID = RS.GetInt("GuestID")
				GInfo.StubNo = RS.GetString("StubNo")
				GInfo.GuestName = RS.GetString("GuestName")
				GInfo.GuestPos = RS.GetString("Remarks_Occupation")

				' Split the full name into individual parts
				Dim name_parts() As String
				name_parts = Regex.Split(" ", RS.GetString("GuestName"))
    
				' Extract the initials from each part
				Dim initialsList As List
				initialsList.Initialize
				For Each part As String In name_parts
					If part.Length > 0 Then
						initialsList.Add(part.SubString2(0, 1))
					End If
				Next
    
				' Join the initials into a single string
				GInfo.Avatar = JoinStrings(initialsList, "")
				
				Dim Pnl As B4XView = xui.CreatePanel("")
				Pnl.SetLayoutAnimated(0, 10dip, 20dip, clvGuests.AsView.Width, 55dip) 'Panel height + 4 for drop shadow
				clvGuests.Add(Pnl, GInfo)
			Loop
			If RS.RowCount <= 0 Then
				lblRecCount.Text = $"No Guest Found"$
			Else If RS.RowCount = 1 Then
				lblRecCount.Text = RS.RowCount & $" Guest Found"$
			Else
				lblRecCount.Text = RS.RowCount & $" Guests Found"$
			End If
		Else
			Log(LastException)
		End If

		Log($"List of Employees Records = ${NumberFormat2((DateTime.Now - StartTime) / 1000, 0, 2, 2, False)} seconds to populate ${clvGuests.Size} Employees Records"$)
	Catch
		Log(LastException)
	End Try

End Sub

Sub clvGuests_VisibleRangeChanged (FirstIndex As Int, LastIndex As Int)
	Dim ExtraSize As Int = 15 'List size
	clvGuests.Refresh
	
	For i = Max(0, FirstIndex - ExtraSize) To Min(LastIndex + ExtraSize, clvGuests.Size - 1)
		Dim Pnl As B4XView = clvGuests.GetPanel(i)
		If i > FirstIndex - ExtraSize And i < LastIndex + ExtraSize Then
			If Pnl.NumberOfViews = 0 Then 'Add each item/layout to the list/main layout
				Dim GI As GuestInfo = clvGuests.GetValue(i)
				Pnl.LoadLayout("GuestList")
				lblGuestName.Text = GI.GuestName
				lblPosition.Text = GI.GuestPos
				lblAvatar.Text = GI.Avatar
				AvatarBG.Color = ShadeColor(Rnd(0xFF59C6CC, 0xFFF8D0CD))
				
'				lblBranch.Text = EI.BranchName
			End If
		Else 'Not visible
			If Pnl.NumberOfViews > 0 Then
				Pnl.RemoveAllViews 'Remove none visable item/layouts from the list/main layout
			End If
		End If
	Next

End Sub

Sub clvGuests_ItemClick (Index As Int, Value As Object)
	Dim Guest As GuestInfo = Value
	iGuestID = Guest.GuestID
	sGuestName = Guest.GuestName
'	iGuestID = Value
	Log($"Guest ID: "$ & iGuestID)
	
	GuestOption
End Sub
#End Region

#Region MessageBox
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
	FillGuests

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

Private Sub GuestOption
	Dim Alert As AX_CustomAlertDialog
	
	Dim items As List
	items.Initialize
	items.Add("Reprint Stub")
	items.Add("Edit")
	
	Alert.Initialize.Create _
			.SetDialogStyleName("MyDialog") _	'Manifest style name
			.SetStyle(Alert.STYLE_ACTIONSHEET) _
			.SetTitle("Select an Option") _
			.SetTitleColor(Colors.Black) _
			.SetCancelColor(GlobalVar.NegColor) _
			.SetCancelText("Cancel") _
			.SetNegativeTypeface(GlobalVar.FontBold) _ 'Usable for Cancel Typeface
			.SetOthers(items) _
			.SetActionsheetTypeface(GlobalVar.Font) _
			.SetTitleTypeface(GlobalVar.FontBold) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetOnCancelClicked("GuestOptions") _	'listeners
			.SetOnItemClickListener("GuestOptions") 	'listeners
			
	Alert.SetDialogBackground(GlobalVar.myCD).SetCancelBackground(GlobalVar.myCD)
	Alert.Build.Show
End Sub

'Item Click
Private Sub GuestOptions_OnItemClick (View As View, Selection As String, Position As Int,Id As Long)

	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize.Dismiss2
	
	Select Case Position
		Case 0
			Alert.Dismiss2
			Sleep(100)

			vibration.vibratePattern(vibratePattern,0)
			ConfirmReprint(sGuestName)
		Case 1
	End Select
End Sub
'Actionsheet More Button
Private Sub GuestOptions_OnCancelClicked (View As View, Dialog As Object)
	If View<>Null Then
		ToastMessageShow("Cancelled!",False)
		Dim Alert As AX_CustomAlertDialog
		Alert.Initialize.Dismiss(Dialog)
	End If
End Sub

Private Sub ConfirmReprint (sGuest As String)
	Dim Alert As AX_CustomAlertDialog
	Alert.Dismiss2

	Alert.Initialize.Create _
			.SetDialogStyleName("MyDialogDisableStatus") _	'Manifest style name
			.SetStyle(Alert.STYLE_DIALOGUE) _
			.SetCancelable(False) _
			.SetTitle($"CONFIRM REPRINT STUB"$) _
			.SetMessage($"Do you want to Reprint"$ & CRLF & sGuest & $" stub?"$) _
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
	vibration.vibrateCancel
	ToastMessageShow($"Reprinting Cancelled!"$, True)
End Sub

Private Sub StubReprint_OnPositiveClicked (View As View, Dialog As Object)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize.Dismiss(Dialog)
	vibration.vibrateCancel
	
	If Not(UpdateReprintStub(iGuestID)) Then Return
	PrintStub(iGuestID)
	ToastMessageShow($"Reprinted!"$, True)
End Sub

#End Region

#Region Printing
Private Sub PrintStub(intGuestID As Int)
	Dim rsData As Cursor
	Dim StubNo As String
	Dim RegFullName As String
	Dim RegPosition As String
	Dim RegTableNo As Int
	
	ProgressDialogShow2($"Stub Printing.  Please Wait..."$, False)
	
	Try
		Starter.strCriteria = "SELECT StubNo, TableNo, GuestName, Remarks_Occupation " & _
						  "FROM tblGuests " & _
						  "WHERE GuestID = " & intGuestID
		
		rsData = Starter.DBCon.ExecQuery(Starter.strCriteria)
		LogColor(Starter.strCriteria, Colors.Magenta)
		
		If rsData.RowCount > 0 Then
			rsData.Position = 0
			StubNo = rsData.GetString("StubNo")
			RegFullName = rsData.GetString("GuestName")
			RegPosition = rsData.GetString("Remarks_Occupation")
			RegTableNo = rsData.GetInt("TableNo")
		Else
			Return
		End If

		PrintBuffer =  ESC & "@" _
					& ESC & Chr(97) & Chr(49) _
					& ESC & Chr(97) & Chr(48) _
					& ESC & "!" & Chr(33) & $"STUB NO.: "$ & BOLD & StubNo & CRLF & Chr(10) _
					& ESC & Chr(97) & Chr(49) _
					& ESC & "!" & Chr(8) & RegFullName & Chr(10) _
					& ESC & "!" & Chr(1) & ESC & "t" & Chr(14) & RegPosition & Chr(10) _
					& HIGH  & REVERSE & $"                  "$ & Chr(10) _
					& HIGHWIDE  & UNREVERSE & $"TABLE NO.: "$ & RegTableNo & Chr(10) & Chr(10) _
					& ESC & "!" & Chr(16) & $"Welcome to BWSI Townhall!"$ & Chr(10)  & Chr(10) _
					& ESC & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
					& ESC & Chr(97) & Chr(48) _
					& ESC & "!" & Chr(33) & $"DINNER STUB"$ & Chr(10) _
					& ESC & Chr(97) & Chr(49) _
					& ESC & "!" & Chr(8) & RegFullName & Chr(10) _
					& ESC & "!" & Chr(1) & ESC & "t" & Chr(14) & RegPosition & Chr(10) _
					& HIGH  & REVERSE & $"                  "$ & Chr(10) _
					& HIGHWIDE  & UNREVERSE & $"TABLE NO.: "$ & RegTableNo & Chr(10) & Chr(10) _
					& ESC & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
					& ESC & Chr(97) & Chr(48) _
					& ESC & "!" & Chr(33) & $"LUNCH STUB"$ & Chr(10) _
					& ESC & Chr(97) & Chr(49) _
					& ESC & "!" & Chr(8) & RegFullName & Chr(10) _
					& ESC & "!" & Chr(1) & ESC & "t" & Chr(14) & RegPosition & Chr(10) _
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
Private Sub UpdateReprintStub(iRegID As Int) As Boolean
	Dim bRetVal As Boolean
	Dim NoPrint As Int

	NoPrint = GetNoOfStubPrint(iRegID)
	NoPrint = NoPrint + 1

	Starter.DBCon.BeginTransaction
	Try
		Starter.strCriteria = "UPDATE tblGuests " & _
						  "SET NoOfPrint = ?  " & _
						  "WHERE GuestID = " & iRegID
							  
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
		iRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT NoOfPrint FROM tblGuests WHERE GuestID = " & iRegID)
	Catch
		iRetVal = 0
		Log(LastException)
	End Try
	Return iRetVal
End Sub
#End Region

Sub ShadeColor(clr As Int) As Int
	Dim argb() As Int = GetARGB(clr)
	Dim factor As Float = 3
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

Sub JoinStrings(list As List, separator As String) As String
	' Join the elements of a list into a single string with the specified separator
	Dim result As StringBuilder
	result.Initialize
	For i = 0 To list.Size - 1
		If i > 0 Then result.Append(separator)
		result.Append(list.Get(i))
	Next
	Return result.ToString
End Sub