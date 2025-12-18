package com.mtcoding.airplane2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AirClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 20000);
            Scanner reader = new Scanner(socket.getInputStream());
            Scanner keyboard = new Scanner(System.in);
            PrintWriter sender = new PrintWriter(socket.getOutputStream(),true);

            String line = "";
            for(int i = 0 ; i < 3 ; i++)
            {
                line = reader.nextLine();
                System.out.println(line);
                sender.println(keyboard.nextLine());
            }
            line = reader.nextLine();
            System.out.println(line);
            line = "";
            while (reader.hasNextLine()) {
                String s = reader.nextLine();
                if (s.equals("END")) break;
                line += s + "\n";
            }
            System.out.println(line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
