/*     */ package org.jline.builtins.telnet;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PortListener
/*     */   implements Runnable
/*     */ {
/*  62 */   private static final Logger LOG = Logger.getLogger(PortListener.class.getName());
/*     */   
/*     */   private static final String logmsg = "Listening to Port {0,number,integer} with a connectivity queue size of {1,number,integer}.";
/*     */   private String name;
/*     */   private String ip;
/*     */   private int port;
/*     */   private int floodProtection;
/*  69 */   private ServerSocket serverSocket = null;
/*     */ 
/*     */   
/*     */   private Thread thread;
/*     */ 
/*     */   
/*     */   private ConnectionManager connectionManager;
/*     */ 
/*     */   
/*     */   private boolean stopping = false;
/*     */   
/*     */   private boolean available;
/*     */ 
/*     */   
/*     */   public PortListener(String name, String ip, int port, int floodprot) {
/*  84 */     this.name = name;
/*  85 */     this.available = false;
/*  86 */     this.ip = ip;
/*  87 */     this.port = port;
/*  88 */     this.floodProtection = floodprot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  97 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAvailable() {
/* 106 */     return this.available;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAvailable(boolean b) {
/* 115 */     this.available = b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 122 */     LOG.log(Level.FINE, "start()");
/* 123 */     this.thread = new Thread(this);
/* 124 */     this.thread.start();
/* 125 */     this.available = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/* 133 */     LOG.log(Level.FINE, "stop()::" + toString());
/*     */     
/* 135 */     this.stopping = true;
/* 136 */     this.available = false;
/*     */     
/* 138 */     this.connectionManager.stop();
/*     */ 
/*     */     
/*     */     try {
/* 142 */       this.serverSocket.close();
/* 143 */     } catch (IOException ex) {
/* 144 */       LOG.log(Level.SEVERE, "stop()", ex);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 149 */       this.thread.join();
/* 150 */     } catch (InterruptedException iex) {
/* 151 */       LOG.log(Level.SEVERE, "stop()", iex);
/*     */     } 
/*     */     
/* 154 */     LOG.info("stop()::Stopped " + toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/* 171 */       this.serverSocket = new ServerSocket(this.port, this.floodProtection, (this.ip != null) ? InetAddress.getByName(this.ip) : null);
/*     */ 
/*     */       
/* 174 */       LOG.info(MessageFormat.format("Listening to Port {0,number,integer} with a connectivity queue size of {1,number,integer}.", new Object[] { Integer.valueOf(this.port), Integer.valueOf(this.floodProtection) }));
/*     */       
/*     */       do {
/*     */         try {
/* 178 */           Socket s = this.serverSocket.accept();
/* 179 */           if (this.available) {
/* 180 */             this.connectionManager.makeConnection(s);
/*     */           } else {
/*     */             
/* 183 */             s.close();
/*     */           } 
/* 185 */         } catch (SocketException ex) {
/* 186 */           if (this.stopping) {
/*     */             
/* 188 */             LOG.log(Level.FINE, "run(): ServerSocket closed by stop()");
/*     */           } else {
/* 190 */             LOG.log(Level.SEVERE, "run()", ex);
/*     */           } 
/*     */         } 
/* 193 */       } while (!this.stopping);
/*     */     }
/* 195 */     catch (IOException e) {
/* 196 */       LOG.log(Level.SEVERE, "run()", e);
/*     */     } 
/* 198 */     LOG.log(Level.FINE, "run(): returning.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConnectionManager getConnectionManager() {
/* 208 */     return this.connectionManager;
/*     */   }
/*     */   
/*     */   public void setConnectionManager(ConnectionManager connectionManager) {
/* 212 */     this.connectionManager = connectionManager;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\builtins\telnet\PortListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */