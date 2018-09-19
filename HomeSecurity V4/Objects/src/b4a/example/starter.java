package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class starter extends  android.app.Service{
	public static class starter_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			android.content.Intent in = new android.content.Intent(context, starter.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
			context.startService(in);
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
		    processBA = new BA(this, null, null, "b4a.example", "b4a.example.starter");
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
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.starter", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
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
    			anywheresoftware.b4a.objects.IntentWrapper iw = new anywheresoftware.b4a.objects.IntentWrapper();
    			if (intent != null) {
    				if (intent.hasExtra("b4a_internal_intent"))
    					iw.setObject((android.content.Intent) intent.getParcelableExtra("b4a_internal_intent"));
    				else
    					iw.setObject(intent);
    			}
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
        BA.LogInfo("** Service (starter) Destroy **");
		processBA.raiseEvent(null, "service_destroy");
        processBA.service = null;
		mostCurrent = null;
		processBA.setActivityPaused(true);
        processBA.runHook("ondestroy", this, null);
	}

@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}public anywheresoftware.b4a.keywords.Common __c = null;
public static boolean _working = false;
public static anywheresoftware.b4j.objects.MqttAsyncClientWrapper _mqtt = null;
public static String _username = "";
public static String _temperature = "";
public static String _motionsensor = "";
public static String _present = "";
public static String _lasttime = "";
public static String _fire = "";
public static String _laser = "";
public static String _windowstat = "";
public static String _alarmsys = "";
public b4a.example.main _main = null;
public b4a.example.roomdetail _roomdetail = null;
public b4a.example.motion _motion = null;
public b4a.example.flame _flame = null;
public b4a.example.wire _wire = null;
public b4a.example.alarm _alarm = null;
public b4a.example.window _window = null;
public static String  _afterconnect() throws Exception{
 //BA.debugLineNum = 70;BA.debugLine="Private Sub AfterConnect";
 //BA.debugLineNum = 71;BA.debugLine="mqtt.Subscribe($\"house/room/#\"$, 0)";
_mqtt.Subscribe(("house/room/#"),(int) (0));
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static boolean  _application_error(anywheresoftware.b4a.objects.B4AException _error,String _stacktrace) throws Exception{
 //BA.debugLineNum = 38;BA.debugLine="Sub Application_Error (Error As Exception, StackTr";
 //BA.debugLineNum = 39;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return false;
}
public static void  _connectandreconnect() throws Exception{
ResumableSub_ConnectAndReconnect rsub = new ResumableSub_ConnectAndReconnect(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_ConnectAndReconnect extends BA.ResumableSub {
public ResumableSub_ConnectAndReconnect(b4a.example.starter parent) {
this.parent = parent;
}
b4a.example.starter parent;
boolean _success = false;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
                return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 47;BA.debugLine="Do While working";
if (true) break;

case 1:
//do while
this.state = 20;
while (parent._working) {
this.state = 3;
if (true) break;
}
if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 48;BA.debugLine="If mqtt.IsInitialized Then mqtt.Close";
if (true) break;

case 4:
//if
this.state = 9;
if (parent._mqtt.IsInitialized()) { 
this.state = 6;
;}if (true) break;

case 6:
//C
this.state = 9;
parent._mqtt.Close();
if (true) break;

case 9:
//C
this.state = 10;
;
 //BA.debugLineNum = 49;BA.debugLine="mqtt.Initialize(\"mqtt\", \"tcp://104.238.164.118:8";
parent._mqtt.Initialize(processBA,"mqtt","tcp://104.238.164.118:8883","");
 //BA.debugLineNum = 51;BA.debugLine="Log(\"Trying to connect\")";
anywheresoftware.b4a.keywords.Common.Log("Trying to connect");
 //BA.debugLineNum = 52;BA.debugLine="mqtt.Connect()";
parent._mqtt.Connect();
 //BA.debugLineNum = 53;BA.debugLine="Wait For Mqtt_Connected (Success As Boolean)";
anywheresoftware.b4a.keywords.Common.WaitFor("mqtt_connected", processBA, this, null);
this.state = 21;
return;
case 21:
//C
this.state = 10;
_success = (Boolean) result[0];
;
 //BA.debugLineNum = 54;BA.debugLine="If Success Then";
if (true) break;

case 10:
//if
this.state = 19;
if (_success) { 
this.state = 12;
}else {
this.state = 18;
}if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 55;BA.debugLine="Log(\"Mqtt connected\")";
anywheresoftware.b4a.keywords.Common.Log("Mqtt connected");
 //BA.debugLineNum = 56;BA.debugLine="AfterConnect";
_afterconnect();
 //BA.debugLineNum = 57;BA.debugLine="Do While working And mqtt.Connected";
if (true) break;

case 13:
//do while
this.state = 16;
while (parent._working && parent._mqtt.getConnected()) {
this.state = 15;
if (true) break;
}
if (true) break;

case 15:
//C
this.state = 13;
 //BA.debugLineNum = 58;BA.debugLine="mqtt.Publish2($\"${username}/f/ping\"$, Array As";
parent._mqtt.Publish2((""+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(parent._username))+"/f/ping"),new byte[]{(byte) (0)},(int) (1),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 59;BA.debugLine="Sleep(5000)";
anywheresoftware.b4a.keywords.Common.Sleep(processBA,this,(int) (5000));
this.state = 22;
return;
case 22:
//C
this.state = 13;
;
 if (true) break;

case 16:
//C
this.state = 19;
;
 //BA.debugLineNum = 61;BA.debugLine="Log(\"Disconnected\")";
anywheresoftware.b4a.keywords.Common.Log("Disconnected");
 if (true) break;

case 18:
//C
this.state = 19;
 //BA.debugLineNum = 63;BA.debugLine="Log(\"Error connecting.\")";
anywheresoftware.b4a.keywords.Common.Log("Error connecting.");
 if (true) break;

case 19:
//C
this.state = 1;
;
 //BA.debugLineNum = 66;BA.debugLine="Sleep(5000)";
anywheresoftware.b4a.keywords.Common.Sleep(processBA,this,(int) (5000));
this.state = 23;
return;
case 23:
//C
this.state = 1;
;
 if (true) break;

case 20:
//C
this.state = -1;
;
 //BA.debugLineNum = 68;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _mqtt_connected(boolean _success) throws Exception{
}
public static String  _mqtt_messagearrived(String _topic,byte[] _payload) throws Exception{
String _temp = "";
String _temp1 = "";
String _temp2 = "";
String _temp3 = "";
String _temp4 = "";
 //BA.debugLineNum = 75;BA.debugLine="Sub mqtt_MessageArrived (Topic As String, Payload(";
 //BA.debugLineNum = 76;BA.debugLine="If Topic.EndsWith(\"/temp\") Then";
if (_topic.endsWith("/temp")) { 
 //BA.debugLineNum = 77;BA.debugLine="Dim temp As String = BytesToString(Payload, 0, P";
_temp = anywheresoftware.b4a.keywords.Common.BytesToString(_payload,(int) (0),_payload.length,"ascii");
 //BA.debugLineNum = 78;BA.debugLine="temperature = temp";
_temperature = _temp;
 };
 //BA.debugLineNum = 82;BA.debugLine="If Topic.EndsWith(\"/motion\") Then";
if (_topic.endsWith("/motion")) { 
 //BA.debugLineNum = 84;BA.debugLine="Dim temp1 As String = BytesToString(Payload, 0,";
_temp1 = anywheresoftware.b4a.keywords.Common.BytesToString(_payload,(int) (0),_payload.length,"ascii");
 //BA.debugLineNum = 85;BA.debugLine="motionsensor = temp1";
_motionsensor = _temp1;
 //BA.debugLineNum = 86;BA.debugLine="If (lasttime == \"\" ) Then";
if (((_lasttime).equals(""))) { 
 //BA.debugLineNum = 87;BA.debugLine="lasttime = DateTime.Now";
_lasttime = BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 88;BA.debugLine="present = DateTime.Now";
_present = BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 }else {
 //BA.debugLineNum = 90;BA.debugLine="present = DateTime.Now";
_present = BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 91;BA.debugLine="lasttime = present";
_lasttime = _present;
 };
 //BA.debugLineNum = 94;BA.debugLine="If temp1 == \"It is gone\" Then";
if ((_temp1).equals("It is gone")) { 
 //BA.debugLineNum = 96;BA.debugLine="alarmsys= \"Off\"";
_alarmsys = "Off";
 }else {
 //BA.debugLineNum = 98;BA.debugLine="alarmsys= \"On\"";
_alarmsys = "On";
 };
 //BA.debugLineNum = 101;BA.debugLine="CallSub(Motion, \"Setstate\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._motion.getObject()),"Setstate");
 //BA.debugLineNum = 102;BA.debugLine="CallSub(Alarm, \"Setstate\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._alarm.getObject()),"Setstate");
 };
 //BA.debugLineNum = 105;BA.debugLine="If Topic.EndsWith(\"/fire\") Then";
if (_topic.endsWith("/fire")) { 
 //BA.debugLineNum = 106;BA.debugLine="Dim temp2 As String = BytesToString(Payload, 0,";
_temp2 = anywheresoftware.b4a.keywords.Common.BytesToString(_payload,(int) (0),_payload.length,"ascii");
 //BA.debugLineNum = 107;BA.debugLine="fire = temp2";
_fire = _temp2;
 //BA.debugLineNum = 108;BA.debugLine="CallSub(Flame, \"Setstate\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._flame.getObject()),"Setstate");
 //BA.debugLineNum = 109;BA.debugLine="CallSub(Main, \"Setstate\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._main.getObject()),"Setstate");
 };
 //BA.debugLineNum = 112;BA.debugLine="If Topic.EndsWith(\"/laser\") Then";
if (_topic.endsWith("/laser")) { 
 //BA.debugLineNum = 113;BA.debugLine="Dim temp3 As String = BytesToString(Payload, 0,";
_temp3 = anywheresoftware.b4a.keywords.Common.BytesToString(_payload,(int) (0),_payload.length,"ascii");
 //BA.debugLineNum = 114;BA.debugLine="laser = temp3";
_laser = _temp3;
 //BA.debugLineNum = 116;BA.debugLine="CallSub(Wire, \"Setstate\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._wire.getObject()),"Setstate");
 };
 //BA.debugLineNum = 118;BA.debugLine="If Topic.EndsWith(\"/window\") Then";
if (_topic.endsWith("/window")) { 
 //BA.debugLineNum = 119;BA.debugLine="Dim temp4 As String = BytesToString(Payload, 0,";
_temp4 = anywheresoftware.b4a.keywords.Common.BytesToString(_payload,(int) (0),_payload.length,"ascii");
 //BA.debugLineNum = 120;BA.debugLine="windowstat = temp4";
_windowstat = _temp4;
 //BA.debugLineNum = 121;BA.debugLine="CallSub(Window, \"Setstate\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._window.getObject()),"Setstate");
 };
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="Private working As Boolean = True";
_working = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 11;BA.debugLine="Public mqtt As MqttClient";
_mqtt = new anywheresoftware.b4j.objects.MqttAsyncClientWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Private username As String = \"Erel\"";
_username = "Erel";
 //BA.debugLineNum = 13;BA.debugLine="Public temperature As String";
_temperature = "";
 //BA.debugLineNum = 14;BA.debugLine="Public motionsensor As String";
_motionsensor = "";
 //BA.debugLineNum = 15;BA.debugLine="Public present As String";
_present = "";
 //BA.debugLineNum = 16;BA.debugLine="Public lasttime As String";
_lasttime = "";
 //BA.debugLineNum = 17;BA.debugLine="Public fire As String";
_fire = "";
 //BA.debugLineNum = 18;BA.debugLine="Public laser As String";
_laser = "";
 //BA.debugLineNum = 19;BA.debugLine="Public windowstat As String";
_windowstat = "";
 //BA.debugLineNum = 20;BA.debugLine="Public alarmsys As String";
_alarmsys = "";
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 23;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 24;BA.debugLine="mqtt.Initialize(\"mqtt\", \"tcp://104.238.164.118:88";
_mqtt.Initialize(processBA,"mqtt","tcp://104.238.164.118:8883","");
 //BA.debugLineNum = 25;BA.debugLine="ConnectAndReconnect";
_connectandreconnect();
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 42;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 29;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public static String  _service_taskremoved() throws Exception{
 //BA.debugLineNum = 33;BA.debugLine="Sub Service_TaskRemoved";
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return "";
}
}
