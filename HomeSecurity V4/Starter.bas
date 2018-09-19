Type=Service
Version=7.01
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
	#ExcludeFromLibrary: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	'These variables can be accessed from all modules.
	Private working As Boolean = True
	Public mqtt As MqttClient
	Private username As String = "Erel"
	Public temperature As String
	Public motionsensor As String
	Public present As String
	Public lasttime As String
	Public fire As String
	Public laser As String
	Public windowstat As String
	Public alarmsys As String
End Sub

Sub Service_Create
	mqtt.Initialize("mqtt", "tcp://104.238.164.118:8883", "")
	ConnectAndReconnect

End Sub

Sub Service_Start (StartingIntent As Intent)

End Sub

Sub Service_TaskRemoved
	'This event will be raised when the user removes the app from the recent apps list.
End Sub

'Return true to allow the OS default exceptions handler to handle the uncaught exception.
Sub Application_Error (Error As Exception, StackTrace As String) As Boolean
	Return True
End Sub

Sub Service_Destroy

End Sub

Sub ConnectAndReconnect
	Do While working
		If mqtt.IsInitialized Then mqtt.Close
		mqtt.Initialize("mqtt", "tcp://104.238.164.118:8883", "")
		'Dim mo As MqttConnectOptions
		Log("Trying to connect")
		mqtt.Connect()
		Wait For Mqtt_Connected (Success As Boolean)
		If Success Then
			Log("Mqtt connected")
			AfterConnect
			Do While working And mqtt.Connected
				mqtt.Publish2($"${username}/f/ping"$, Array As Byte(0), 1, False)
				Sleep(5000)
			Loop
			Log("Disconnected")
		Else
			Log("Error connecting.")
		End If

		Sleep(5000)
	Loop
End Sub

Private Sub AfterConnect
	mqtt.Subscribe($"house/room/#"$, 0)
	'needs to update state here afterwards
End Sub

Sub mqtt_MessageArrived (Topic As String, Payload() As Byte)
	If Topic.EndsWith("/temp") Then
		Dim temp As String = BytesToString(Payload, 0, Payload.Length, "ascii")
		temperature = temp

	End If
	
	If Topic.EndsWith("/motion") Then

		Dim temp1 As String = BytesToString(Payload, 0, Payload.Length, "ascii")
		motionsensor = temp1
		If (lasttime == "" ) Then
			lasttime = DateTime.Now
			present = DateTime.Now
		Else
			present = DateTime.Now
			lasttime = present
		End If
		
		If temp1 == "It is gone" Then
			
			alarmsys= "Off"
		Else
			alarmsys= "On"
		End If

		CallSub(Motion, "Setstate")
		CallSub(Alarm, "Setstate")
	End If
	
	If Topic.EndsWith("/fire") Then
		Dim temp2 As String = BytesToString(Payload, 0, Payload.Length, "ascii")
		fire = temp2
		CallSub(Flame, "Setstate")
		CallSub(Main, "Setstate")
	End If
	
	If Topic.EndsWith("/laser") Then
		Dim temp3 As String = BytesToString(Payload, 0, Payload.Length, "ascii")
		laser = temp3
		'Log(laser)
		CallSub(Wire, "Setstate")
	End If
	If Topic.EndsWith("/window") Then
		Dim temp4 As String = BytesToString(Payload, 0, Payload.Length, "ascii")
		windowstat = temp4
		CallSub(Window, "Setstate")
	End If
End Sub




