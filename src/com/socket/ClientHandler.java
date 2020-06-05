package com.socket;

import com.command.IssueCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * <h2>Individual client socket handler for {@link Server com.socket.Server}.</h2>
 *
 * It listens to client socket in a new thread, handles incoming
 * messages,
 */
public class ClientHandler {
    private Socket sock;
    private PrintWriter out;
    private Thread readerThread;

    /**
     * <h2>initializes but does <b>NOT</b> start the {@link ClientHandler ClientHandler}.</h2>
     * {@link ClientHandler#start ClientHandler.start()} needs to be called for handler to actually work.
     *
     * @param sock client socket
     */
    public ClientHandler(Socket sock) {
        this.sock = sock;
    }

    /**
     * Creates and starts the {@link ClientHandler#readerThread readerThread},
     * which runs {@link ClientHandler#runReader ClientHandler.runReader()}.
     * <br/><br/>
     * It also initializes output {@link PrintWriter PrintWriter}, which is
     * necessary for {@link ClientHandler#send ClientHandler.send(...)}
     */
    public synchronized void start() {
        try {
            this.out = new PrintWriter(this.sock.getOutputStream(), true);
            this.readerThread = new Thread(this::runReader);
            this.readerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h2>Closes socket</h2>
     *
     * TODO: it should stop <b>readerThread</b> as well.
     */
    public synchronized void stop() {
        try {
            this.sock.close();
            this.out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.sock = null;
            this.out = null;
        }
    }

    /**
     * Waits and reads input from socket client, parses incoming messages
     * and takes corresponding actions.
     * <br/><br/>
     * <b>Note</b>: each command must be contained in a single line.
     */
    public void runReader() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
            String message;
            while((message = in.readLine()) != null) {
                try {
                    this.send(IssueCommand.fromJson(message).execute().toJsonStr());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            in.close();
            this.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h2>Send message to client socket</h2>
     *
     * ClientHandler <b>NEEDS</b> to be started ({@link ClientHandler#start ClientHandler.start()})
     * for this command to work.
     *
     * @param message
     */
    public synchronized void send(String message) {
        this.out.println(message);
    }
}
