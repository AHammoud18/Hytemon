/*    */ package org.jline.builtins.ssh;
/*    */ 
/*    */ import java.io.Closeable;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import org.apache.sshd.server.Environment;
/*    */ import org.apache.sshd.server.ExitCallback;
/*    */ import org.apache.sshd.server.channel.ChannelSession;
/*    */ import org.apache.sshd.server.command.Command;
/*    */ import org.apache.sshd.server.session.ServerSession;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShellCommand
/*    */   implements Command
/*    */ {
/* 26 */   private static final Logger LOGGER = Logger.getLogger(ShellCommand.class.getName());
/*    */   
/*    */   private final Consumer<Ssh.ExecuteParams> execute;
/*    */   private final String command;
/*    */   private InputStream in;
/*    */   private OutputStream out;
/*    */   private OutputStream err;
/*    */   private ExitCallback callback;
/*    */   private ServerSession session;
/*    */   private Environment env;
/*    */   
/*    */   public ShellCommand(Consumer<Ssh.ExecuteParams> execute, String command) {
/* 38 */     this.execute = execute;
/* 39 */     this.command = command;
/*    */   }
/*    */   
/*    */   public void setInputStream(InputStream in) {
/* 43 */     this.in = in;
/*    */   }
/*    */   
/*    */   public void setOutputStream(OutputStream out) {
/* 47 */     this.out = out;
/*    */   }
/*    */   
/*    */   public void setErrorStream(OutputStream err) {
/* 51 */     this.err = err;
/*    */   }
/*    */   
/*    */   public void setExitCallback(ExitCallback callback) {
/* 55 */     this.callback = callback;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start(ChannelSession channel, Environment env) throws IOException {
/* 60 */     this.session = channel.getSession();
/* 61 */     this.env = env;
/* 62 */     (new Thread(this::run)).start();
/*    */   }
/*    */   
/*    */   private void run() {
/* 66 */     int exitStatus = 0;
/*    */     try {
/* 68 */       this.execute.accept(new Ssh.ExecuteParams(this.command, this.env.getEnv(), this.session, this.in, this.out, this.err));
/* 69 */     } catch (RuntimeException e) {
/* 70 */       exitStatus = 1;
/* 71 */       LOGGER.log(Level.SEVERE, "Unable to start shell", e);
/*    */       try {
/* 73 */         Throwable t = (e.getCause() != null) ? e.getCause() : e;
/* 74 */         this.err.write(t.toString().getBytes());
/* 75 */         this.err.flush();
/* 76 */       } catch (IOException iOException) {}
/*    */     }
/*    */     finally {
/*    */       
/* 80 */       ShellFactoryImpl.close(new Closeable[] { this.in, this.out, this.err });
/* 81 */       this.callback.onExit(exitStatus);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void destroy(ChannelSession channel) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\builtins\ssh\ShellCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */