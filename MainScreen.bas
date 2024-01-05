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
'	Menu.Add2(1, 1, "Close App",xmlIcon.GetDrawable("ic_exit_to_app_white_24dp")).ShowAsAction = Item.SHOW_AS_ACTION_IF_ROOM
'	Menu.Add2(2, 2, "Settings",xmlIcon.GetDrawable("ic_settings_white_24dp")).ShowAsAction = Item.SHOW_AS_ACTION_ALWAYS
End Sub

Sub Process_Globals
	Private xui As XUI
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim ActionBarButton As ACActionBar
	Private ToolBar As ACToolBarDark
	Private xmlIcon As XmlLayoutBuilder
	
	Private PieChart1 As PieChart
	Private btnStart As ACButton
	
	Dim CD As ColorDrawable

	Private lblArea As Label
	Private lblBranches As Label
	Private lblRegistered As Label
	Private lblTotal As Label
	Private lblUnregistered As Label
	Private lvBranches As ListView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Scale.SetRate(0.5)
	Activity.LoadLayout("MainScreen")

	GlobalVar.CSTitle.Initialize.Size(17).Bold.Append(Application.LabelName).PopAll
	GlobalVar.CSSubTitle.Initialize.Size(14).Append(Application.VersionName).PopAll
	
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

'	If lvBranches.IsInitialized = False Then lvBranches.Initialize("")
	If FirstTime = True Then
		lvBranches.SingleLineLayout.ItemHeight = 25dip
		lvBranches.SingleLineLayout.Label.TextColor = GlobalVar.SecColor
		lvBranches.SingleLineLayout.Label.Color = Colors.White
		lvBranches.SingleLineLayout.Label.TextSize = 12

		GlobalVar.AreaID = DBFunctions.GetAreaID
		GlobalVar.AreaName = DBFunctions.GetAreaName(GlobalVar.AreaID)
		GlobalVar.AreaDesc = DBFunctions.GetAreaDesc(GlobalVar.AreaID)
		GlobalVar.AreaCode = DBFunctions.GetAreaCode(GlobalVar.AreaID)
		GlobalVar.TotEmployees = DBFunctions.GetTotalAttendees(GlobalVar.AreaID)
		GlobalVar.TotReg = DBFunctions.GetTotRegistered(GlobalVar.AreaID)
		GlobalVar.TotUnreg = DBFunctions.GetTotUnRegister(GlobalVar.AreaID)
	End If
	
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
	
	GlobalVar.AreaID = DBFunctions.GetAreaID
	GlobalVar.AreaName = DBFunctions.GetAreaName(GlobalVar.AreaID)
	GlobalVar.AreaDesc = DBFunctions.GetAreaDesc(GlobalVar.AreaID)
	GlobalVar.AreaCode = DBFunctions.GetAreaCode(GlobalVar.AreaID)
	GlobalVar.TotEmployees = DBFunctions.GetTotalAttendees(GlobalVar.AreaID)
	GlobalVar.TotReg = DBFunctions.GetTotRegistered(GlobalVar.AreaID)
	GlobalVar.TotUnreg = DBFunctions.GetTotUnRegister(GlobalVar.AreaID)

	GenerateBranchList(GlobalVar.AreaID)
	lvBranches.SingleLineLayout.ItemHeight = 25dip
	lvBranches.SingleLineLayout.Label.TextColor = 0xFF01baef
	lvBranches.SingleLineLayout.Label.Color = Colors.White
	lvBranches.SingleLineLayout.Label.TextSize = 15
	
	lblArea.Text = GlobalVar.AreaName
	lblTotal.Text = GlobalVar.TotEmployees
	lblRegistered.Text = GlobalVar.TotReg
	lblUnregistered.Text = GlobalVar.TotUnreg
	
	PieChart1.UsePercentValues = True
	
	PieChart1.DrawHoleEnabled = True
	PieChart1.HoleColorTransparent = True

	PieChart1.TransparentCircleColor = Colors.White
	PieChart1.TransparentCircleAlpha = 110

	PieChart1.HoleRadius = 60.0
	PieChart1.TransparentCircleRadius = 50.0

	PieChart1.DrawCenterText = True

	PieChart1.DrawSliceText = True
	PieChart1.HoleColor = Colors.White
	PieChart1.TransparentCircleColor = Colors.Transparent
	
	'    RIGHT_OF_CHART, RIGHT_OF_CHART_CENTER, RIGHT_OF_CHART_INSIDE,
	'    LEFT_OF_CHART, LEFT_OF_CHART_CENTER, LEFT_OF_CHART_INSIDE,
	'    BELOW_CHART_LEFT, BELOW_CHART_RIGHT, BELOW_CHART_CENTER,
	'    PIECHART_CENTER
	PieChart1.TheLegendPosition = "RIGHT_OF_CHART_CENTER"
'	
	PieChart1.TheLegendColor = 0xFF2A91C3
	PieChart1.TheLegendTextSize = 18.0
'	PieChart1.LegendTitle = "MONTHS"

	PieChart1.ChartDescription = ""
'	PieChart1.ChartDescriptionColor = Colors.ARGB(255,0,255,255)
'	PieChart1.ChartDescriptionTextSize = 14

	PieChart1.ValueTextColor = Colors.LightGray
	PieChart1.ValueTextSize = 7.0

	PieChart1.PieColors = Array As Int(0xFF0b4f6c, 0xFFDC143C)
	PieChart1.LegendText = Array As String("Registered", "Unregistered")
	PieChart1.ChartData = Array As Float(GlobalVar.TotReg, GlobalVar.TotUnreg)    'values - it will be converted to %

	PieChart1.PieData = 2
	
	CD.Initialize2(0xFF1E4369, 25, 0,0xFF268FC2)
	btnStart.Background = CD
	
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	If UserClosed Then ExitApplication
End Sub

Sub ToolBar_NavigationItemClick
	GlobalVar.AreaID = DBFunctions.GetAreaID
	Activity.Finish
End Sub

Sub ToolBar_MenuItemClick (Item As ACMenuItem)
	Select Case Item.Id
		Case 1
			GlobalVar.AreaID = DBFunctions.GetAreaID
			Activity.Finish
			ExitApplication
	End Select
End Sub

#Region Styles
Sub SetSnackBarBackground(pSnack As DSSnackbar, pColor As Int)
	Dim v As View
	v = pSnack.View
	v.Color = pColor
End Sub

Sub SetSnackBarTextColor(pSnack As DSSnackbar, pColor As Int)
	Dim p As Panel
	p = pSnack.View
	For Each v As View In p.GetAllViewsRecursive
		If v Is Label Then
			Dim textv As Label
			textv = v
			textv.TextColor = pColor
			Exit
		End If
	Next
End Sub

Sub FontBit (icon As String, font_size As Float, color As Int, awesome As Boolean) As Bitmap
	If color = 0 Then color = Colors.White
	Dim typ As Typeface = Typeface.MATERIALICONS
	If awesome Then typ = Typeface.FONTAWESOME
	Dim bmp As Bitmap
	bmp.InitializeMutable(32dip, 32dip)
	Dim cvs As Canvas
	cvs.Initialize2(bmp)
	Dim h As Double
	If Not(awesome) Then
		h = cvs.MeasureStringHeight(icon, typ, font_size) + 10dip
	Else
		h = cvs.MeasureStringHeight(icon, typ, font_size)
	End If
	cvs.DrawText(icon, bmp.Width / 2, bmp.Height / 2 + h / 2, typ, font_size, color, "CENTER")
	Return bmp
End Sub
#End Region

Sub btnStart_Click
	StartActivity(Registration)
End Sub

Private Sub GenerateBranchList(iAreaID As Int)
	Dim rsBranches As Cursor
	lvBranches.Clear

	Try
		Starter.strCriteria = "SELECT * FROM tblBranches WHERE AreaID = " & iAreaID
		rsBranches = Starter.DBCon.ExecQuery(Starter.strCriteria)
		
		If rsBranches.RowCount > 0 Then
			For i = 0 To rsBranches.RowCount - 1
				rsBranches.Position = i
				lvBranches.AddSingleLine(rsBranches.GetString("BranchName"))
			Next
		End If
	Catch
		Log(LastException)
	End Try
End Sub