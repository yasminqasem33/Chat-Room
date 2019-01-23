/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_rrom;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Server {

    ServerSocket srvSkt;
    Socket s;

    Server() {
        try {
            srvSkt = new ServerSocket(5005);
            while (true) {
                s = srvSkt.accept();
                new ChatHandler(s);

            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
      public static void main(String args[])
        {
          new  Server();
        }


   
}
 class ChatHandler extends Thread {

        DataInputStream dis;
        PrintStream ps;
       static  Vector<ChatHandler> clientsVector
                = new Vector<ChatHandler>();

        ChatHandler(Socket s)  {
            try {
                dis = new DataInputStream(s.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                ps = new PrintStream(s.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            clientsVector.add(this);
            start();
        }
      
       
        @Override
        public void run()
        {
            while(true)
            {
                String str=null;
                try {
                   str = dis.readLine();
                   sendToAll(str);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        public void sendToAll(String str)
        {
            for(ChatHandler ch :clientsVector )
            {
                 ch.ps.println(str);
                
            }
           
        }
    }

