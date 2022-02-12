package com.wwxiaoqi.get.chexuetang;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public abstract class RootUtils {

	protected abstract ArrayList<String> getCommandsToExecute();

    public static boolean canRunRootCommands() {
        boolean retval = false;
        Process suProcess;
        try {
            suProcess = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());
            DataInputStream osRes = new DataInputStream(suProcess.getInputStream());
            os.writeBytes("id\n");
            os.flush();
            String currUid = osRes.readLine();
            boolean exitSu = false;
            if (null != currUid) {
                if (currUid.contains("uid=0")) {
                    retval = true;
                }
                exitSu = true;
            }
            if (exitSu) {
                os.writeBytes("exit\n");
                os.flush();
            }
        } catch (Exception e) {
            retval = false;
        }
        return retval;
    }

    public final boolean execute() {
        boolean retval = false;
        try {
            ArrayList<String> commands = getCommandsToExecute();
            if (null != commands && commands.size() > 0) {
                Process process = Runtime.getRuntime().exec("su");
                DataOutputStream os = new DataOutputStream(process.getOutputStream());
                for (String currCommand : commands) {
                    os.writeBytes(currCommand + "\n");
                    os.flush();
                }
                os.writeBytes("exit\n");
                os.flush();
				InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
                BufferedReader reader = new BufferedReader(inputStreamReader);
                int read;
                char[] buffer = new char[4096];
                StringBuilder output = new StringBuilder();
                while ((read = reader.read(buffer)) > 0) {
                    output.append(buffer, 0, read);
                }
                reader.close();
                try {
                    int suProcessRetval = process.waitFor();
                    retval = 255 != suProcessRetval;
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ignored) {
        }
        return retval;
    }
}
