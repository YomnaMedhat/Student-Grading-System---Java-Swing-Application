public class Speech {
    public static void speak(String text) {
        new Thread(() -> {
            try {
                String command = "powershell -Command \"Add-Type –AssemblyName System.Speech; " +
                        "$speak = New-Object System.Speech.Synthesis.SpeechSynthesizer; " +
                        "$speak.Speak('" + text.replace("'", "''") + "');\"";

                Runtime.getRuntime().exec(command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}