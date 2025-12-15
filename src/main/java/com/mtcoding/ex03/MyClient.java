package com.mtcoding.ex03;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MyClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",20000);

        OutputStream out = socket.getOutputStream();
        OutputStreamWriter ow = new OutputStreamWriter(out);
        BufferedWriter bw = new BufferedWriter(ow);

        bw.write("Hello World");
        bw.write("\n");
        bw.flush();
    }
}
