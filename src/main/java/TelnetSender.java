import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TelnetSender {
    public static String outputMessage;

    public static void telnet(String port, String inputMessage, JTextArea textArea) throws IOException {
        Socket socket;
        PrintWriter out;
        BufferedReader in;

        try {
            socket = new Socket("11.123.41.56", Integer.parseInt(port));
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("%");
            textArea.append("Response: ");
            if (in.readLine().isEmpty()) {
                try {
                    in.close();
                    out.close();
                    socket.close();
                    System.out.println("Response = null => " + in.readLine());
                    textArea.append("Response = null => " + in.readLine());
                }catch (Exception e){
                    e.getMessage();
                }
            } else {

                out.println(inputMessage);
                out.println("QUIT|");
                System.out.println("PORT:\nMESSAGE:\n" + inputMessage + "\nRESPONSE:");
                textArea.append("MESSAGE:\n" + inputMessage + "\nRESPONSE:\n");
                while ((outputMessage = in.readLine()) != null) {
                    System.out.println(outputMessage);
                    textArea.append(outputMessage + System.lineSeparator());
                }

                in.close();
                out.close();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
