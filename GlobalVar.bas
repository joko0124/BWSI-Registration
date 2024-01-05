B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=StaticCode
Version=9.3
@EndOfDesignText@
Sub Process_Globals
	Public CSTitle As CSBuilder
	Public CSSubTitle As CSBuilder
	
	Public SF As StringFunctions
	Public AdminPassword As String = "3923bwsi12"
	
	Public WarningIcon = $"ic_warning_black_36dp2"$ As String
	Public PriColor = 0xFF1E4369 As Double
	Public SecColor = 0xFF1976D2 As Double
	
	Public AreaID As Int
	Public AreaName As String
	Public AreaCode As String
	Public AreaDesc As String
	Public TotReg As Int
	Public TotUnreg As Int
	Public TotEmployees As Int
	Public AssignedEmp as String
	
	Public PosColor = 0xFF007BFF As Double
	Public NegColor = 0xFFDC3545 As Double
	Public NeutralColor = 0xFF7FBDFF As Double

	Public Font As Typeface = Typeface.LoadFromAssets("myfont.ttf")
	Public FontBold As Typeface = Typeface.LoadFromAssets("myfont_bold.ttf")
End Sub

Public Sub CreateButtonColor(FocusedColor As Int, EnabledColor As Int, DisabledColor As Int, PressedColor As Int) As StateListDrawable

	Dim RetColor As StateListDrawable
	RetColor.Initialize
	Dim drwFocusedColor, drwEnabledColor, drwDisabledColor, drwPressedColor As ColorDrawable

	'drwFocusedColor.Initialize2(FocusedColor, 5, 0, Colors.LightGray) 'border roundness, thickness, and color on Android TV
	'drwEnabledColor.Initialize2(EnabledColor, 5, 0, Colors.DarkGray)
	'drwDisabledColor.Initialize2(DisabledColor, 5, 0, Colors.White)
	'drwPressedColor.Initialize2(PressedColor, 5, 0, Colors.Black)
'	CD.Initialize(0xFF1976D2, 25)
	
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

Public Sub myCD As ColorDrawable
	Dim mCD As ColorDrawable
	mCD.Initialize(Colors.RGB(240,240,240),0)
	Return mCD
End Sub
