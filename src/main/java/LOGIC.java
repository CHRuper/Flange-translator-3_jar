import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class LOGIC {

    static void TextReBuilder(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            int idIndex;
            int pidIndex;

            String str = "";

            if ((list.get(i).length() == 16) & (list.get(i + 1).length() > 16)) {
                str = (list.get(i + 1).replaceAll("'", ""));
                String flange = list.get(i);
                pidIndex = list.get(i + 1).indexOf("pid=");
                idIndex = list.get(i + 1).indexOf("|id");
                String subStr = str.substring(pidIndex - 1, idIndex - 1);
                str = str.replace(subStr, "pid=" + flange);
                list.remove(list.get(i));
            }

            list.set(i, str);
            //System.out.println(str);
        }
    }

    static void TextReBuilder2(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            int idIndex;
            int pidIndex;

            String str = (list.get(i).replaceAll("'", ""));
            pidIndex = list.get(i).indexOf("pid=");
            idIndex = list.get(i).indexOf("|id");

            if (list.get(i + 1).length() == 16) {
                String flange = list.get(i + 1);
                String subStr = str.substring(pidIndex - 1, idIndex - 1);
                str = str.replace(subStr, "pid=" + flange);
                list.remove(list.get(i + 1));
            }
            list.set(i, str);
            //System.out.println(str);
        }
    }

    static void ListToTextArea(List<String> arrayList, JTextArea textArea) throws IOException {

        for (String str : arrayList) {
            textArea.append(str + System.lineSeparator());
        }

    }

    static void CopyToClipboard(String str) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(str);
        clipboard.setContents(strSel, null);
    }

    static String portFromName(String port) {
        if (port.contains("[") && (port.contains("]"))) {
            String[] ports = port.split(" ");

            Pattern pattern = Pattern.compile("[0-9]{5}");
            if (pattern.matcher(ports[1] = ports[1].replace("[", "").replace("]", "")).matches()) {
                port = (ports[1]);
            }
        }
        return port;
    }
}
