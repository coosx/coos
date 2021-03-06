package top.coos.tool.local;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

import top.coos.tool.string.StringHelper;

public class MiscUtil {
    private static String DATA = null;

    public static String getMotherboardSN() {
        if (StringHelper.isEmpty(DATA)) {

            try {
                File file = File.createTempFile("realhowto", ".vbs");
                file.deleteOnExit();
                FileWriter fw = new java.io.FileWriter(file);
                String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n" + "Set colItems = objWMIService.ExecQuery _ \n" + "   (\"Select * from Win32_BaseBoard\") \n" + "For Each objItem in colItems \n" + "    Wscript.Echo objItem.SerialNumber \n" + "    exit for  ' do the first cpu only! \n"
                        + "Next \n";
                fw.write(vbs);
                fw.close();
                Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = input.readLine()) != null) {
                    DATA += line;
                }
                input.close();
            } catch (Exception e) {
            }
            if (DATA == null) {
                DATA = "";
            }
        }
        return DATA.trim();
    }

}
