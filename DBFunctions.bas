B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=StaticCode
Version=10
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	Private rsTemp As Cursor
End Sub

Public Sub GetAreaID() As Int
	Dim iRetVal As Int

	Try
		Starter.strCriteria = "SELECT AreaID FROM tblSysParam"
		LogColor(Starter.strCriteria, Colors.Blue)
		
		iRetVal = Starter.DBCon.ExecQuerySingleResult(Starter.strCriteria)
	Catch
		ToastMessageShow($"Unable to fetch Branch System Mode due to "$ & LastException.Message, False)
		Log(LastException)
		iRetVal = 0
	End Try
	Return iRetVal
End Sub

Public Sub GetAreaName(iAreaID As Int) As String
	Dim sRetVal As String

	Try
		Starter.strCriteria = "SELECT AreaName FROM tblAreas " & _
						  "WHERE AreaID = " & iAreaID
		LogColor(Starter.strCriteria, Colors.Blue)
		
		sRetVal = Starter.DBCon.ExecQuerySingleResult(Starter.strCriteria)
	Catch
		ToastMessageShow($"Unable to fetch Branch System Mode due to "$ & LastException.Message, False)
		Log(LastException)
		sRetVal = ""
	End Try
	Return sRetVal
End Sub

Public Sub GetAreaDesc(iAreaID As Int) As String
	Dim sRetVal As String

	Try
		Starter.strCriteria = "SELECT AreaDesc FROM tblAreas " & _
						  "WHERE AreaID = " & iAreaID
		LogColor(Starter.strCriteria, Colors.Blue)
		
		sRetVal = Starter.DBCon.ExecQuerySingleResult(Starter.strCriteria)
	Catch
		ToastMessageShow($"Unable to fetch Branch System Mode due to "$ & LastException.Message, False)
		Log(LastException)
		sRetVal = ""
	End Try
	Return sRetVal
End Sub

Public Sub GetAreaCode(iAreaID As Int) As String
	Dim sRetVal As String

	Try
		Starter.strCriteria = "SELECT AreaCode FROM tblAreas " & _
						  "WHERE AreaID = " & iAreaID
		LogColor(Starter.strCriteria, Colors.Blue)
		
		sRetVal = Starter.DBCon.ExecQuerySingleResult(Starter.strCriteria)
	Catch
		ToastMessageShow($"Unable to fetch Branch System Mode due to "$ & LastException.Message, False)
		Log(LastException)
		sRetVal = ""
	End Try
	Return sRetVal
End Sub

Public Sub GetTotalAttendees(iAreaID As Int) As Long
	Dim lRetVal As Long

	Try
		Starter.strCriteria = "SELECT Count(Employees.RegID) " & _
						  "FROM tblRegistration AS Employees " & _
						  "INNER JOIN tblBranches AS Branches ON Employees.BranchID = Branches.BranchID " & _
						  "WHERE Employees.WillAttend <> 0 " & _
						  "AND Branches.AreaID = " & iAreaID
		LogColor(Starter.strCriteria, Colors.Blue)
		
		lRetVal = Starter.DBCon.ExecQuerySingleResult(Starter.strCriteria)
	Catch
		ToastMessageShow($"Unable to fetch Branch System Mode due to "$ & LastException.Message, False)
		Log(LastException)
		lRetVal = 0
	End Try
	Return lRetVal
End Sub

Public Sub GetTotRegistered(iAreaID As Int) As Long
	Dim lRetVal As Long

	Try
		Starter.strCriteria = "SELECT Count(Employees.RegID) " & _
						  "FROM tblRegistration AS Employees " & _
						  "INNER JOIN tblBranches AS Branches ON Employees.BranchID = Branches.BranchID " & _
						  "WHERE Employees.WillAttend <> 0 " & _
						  "AND Branches.AreaID = " & iAreaID & " " & _
						  "AND WasRegistered = 1"
		LogColor(Starter.strCriteria, Colors.Blue)
		
		lRetVal = Starter.DBCon.ExecQuerySingleResult(Starter.strCriteria)
	Catch
		ToastMessageShow($"Unable to fetch Branch System Mode due to "$ & LastException.Message, False)
		Log(LastException)
		lRetVal = 0
	End Try
	Return lRetVal
End Sub

Public Sub GetTotUnRegister(iAreaID As Int) As Long
	Dim lRetVal As Long

	Try
		Starter.strCriteria = "SELECT Count(Employees.RegID) " & _
						  "FROM tblRegistration AS Employees " & _
						  "INNER JOIN tblBranches AS Branches ON Employees.BranchID = Branches.BranchID " & _
						  "WHERE Employees.WillAttend <> 0 " & _
						  "AND Branches.AreaID = " & iAreaID & " " & _
						  "AND WasRegistered = 0"
		LogColor(Starter.strCriteria, Colors.Blue)
		
		lRetVal = Starter.DBCon.ExecQuerySingleResult(Starter.strCriteria)
	Catch
		ToastMessageShow($"Unable to fetch Branch System Mode due to "$ & LastException.Message, False)
		Log(LastException)
		lRetVal = 0
	End Try
	Return lRetVal
End Sub

Public Sub GetSeqNo() As Int
	Dim dblNo As Double
	Dim iRetVal As Int
	
	Dim blnSaved As Boolean

	' Initialize blnSaved to False
	blnSaved = False
    
	Do While Not (blnSaved = True)
		blnSaved = SaveSeqNo
		If blnSaved Then
			dblNo = Starter.DBCon.ExecQuerySingleResult("SELECT LastSeqNo FROM tblSysParam")
			Exit
		Else
			' Display a message that the procedure failed but
			' will keep retrying...
		End If
	Loop
	
	iRetVal=dblNo
	Return iRetVal
	
End Sub

Sub GetNewStubNo() As String
	Dim dblNo As Double
	Dim sRetVal As String
	
	Dim blnSaved As Boolean

	' Initialize blnSaved to False
	blnSaved = False
    
	Do While Not (blnSaved = True)
		blnSaved = SaveNewStubNo
		If blnSaved Then
			dblNo = Starter.DBCon.ExecQuerySingleResult("SELECT LastRegNo FROM tblSysParam")
			Exit
		Else
			' Display a message that the procedure failed but
			' will keep retrying...
		End If
	Loop
	
	If GlobalVar.SF.Len(dblNo) = 1 Then
		sRetVal = $"000"$ & dblNo
	Else If GlobalVar.SF.Len(dblNo) = 2 Then
		sRetVal = $"00"$ & dblNo
	Else If GlobalVar.SF.Len(dblNo) = 3 Then
		sRetVal = $"0"$ & dblNo
	Else If GlobalVar.SF.Len(dblNo) >= 4 Then
		sRetVal = dblNo
	End If
	
	Return sRetVal
	
End Sub

Sub SaveSeqNo() As Boolean
	Dim blnRetVal As Boolean
	Dim lngRec As Long
	Try
		Starter.strCriteria = "SELECT * FROM tblSysParam"
		rsTemp = Starter.dbcon.ExecQuery(Starter.strCriteria)
		
		If rsTemp.RowCount =  0 Then
			Starter.dbcon.ExecNonQuery2("INSERT INTO tblSysParam VALUES (?, ?, ?, ?, ?)", Array As Object($"english"$, $"1"$, "", 1, 1))
		Else
			rsTemp.Position = 0
			lngRec = rsTemp.GetLong("LastSeqNo") + 1
			
			Starter.strCriteria="UPDATE tblSysParam SET LastSeqNo = ? "
			Starter.dbcon.ExecNonQuery2(Starter.strCriteria, Array As String(lngRec))
		End If
		blnRetVal=True
	Catch
		blnRetVal = False
		Log(LastException)
	End Try
	Return blnRetVal
End Sub

Sub SaveNewStubNo() As Boolean
	Dim blnRetVal As Boolean
	Dim lngRec As Long
	Try
		Starter.strCriteria = "SELECT * FROM tblSysParam"
		rsTemp = Starter.dbcon.ExecQuery(Starter.strCriteria)
		If rsTemp.RowCount=0 Then
			Starter.dbcon.ExecNonQuery2("INSERT INTO tblSysParam VALUES (?, ?, ?, ?, ?)", Array As Object($"english"$, $"1"$, "", 1, 1))
		Else
			rsTemp.Position = 0
			lngRec = rsTemp.GetLong("LastRegNo") + 1
			Starter.strCriteria="UPDATE tblSysParam SET LastRegNo = ? "
			Starter.dbcon.ExecNonQuery2(Starter.strCriteria, Array As String(lngRec))
		End If
		blnRetVal=True
	Catch
		blnRetVal = False
		Log(LastException)
	End Try
	Return blnRetVal
End Sub

Public Sub GetBranchIDByEmpID(iEmpID As Int) As Int
	Dim iRetVal As Int
	Try
		iRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT BranchID FROM tblRegistration WHERE RegID = " & iEmpID)
	Catch
		ToastMessageShow($"Unable to fetch Branch ID due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	Return iRetVal
End Sub

Public Sub GetIDByCode (sRetField As String, sTableName As String, sFieldToCompare As String, sCodeComparison As String) As Int
	Dim iRetval As Int
	iRetval = 0
	Try
		Starter.strCriteria = "SELECT " & sRetField & " FROM " & sTableName & " WHERE " & sFieldToCompare & " = '" & sCodeComparison & "'"
		LogColor(Starter.strCriteria, Colors.Blue)
		
		iRetval = Starter.DBCon.ExecQuerySingleResult(Starter.strCriteria)
	Catch
		ToastMessageShow($"Unable to fetch ID due to "$ & LastException.Message, False)
		Log(LastException)
		iRetval = 0
	End Try
	LogColor($"Return ID: "$ & iRetval, Colors.Yellow)
	Return iRetval
End Sub

Public Sub IsThereAssignedEmp As Boolean
	Dim bRetVal As Boolean
	
	Try
		Starter.strCriteria = "SELECT * FROM tblSysParam"
		rsTemp = Starter.dbcon.ExecQuery(Starter.strCriteria)

		If rsTemp.RowCount > 0 Then
			rsTemp.Position = 0
			If rsTemp.GetString("AssignedTo") = Null Or GlobalVar.SF.Len(GlobalVar.SF.Trim(rsTemp.GetString("AssignedTo"))) <= 0  Then
				bRetVal = False
			Else
				bRetVal = True
			End If
		Else
			bRetVal = False
		End If

	Catch
		Log(LastException)
		bRetVal = False
	End Try
	Return bRetVal
End Sub

Public Sub GetAssignedEmp As String
	Dim sRetVal As String

	Try
		Starter.strCriteria = "SELECT AssignedTo FROM tblSysParam "
		LogColor(Starter.strCriteria, Colors.Blue)
		
		sRetVal = Starter.DBCon.ExecQuerySingleResult(Starter.strCriteria)
	Catch
		ToastMessageShow($"Unable to fetch Employee Name due to "$ & LastException.Message, False)
		Log(LastException)
		sRetVal = ""
	End Try
	Return sRetVal
End Sub
