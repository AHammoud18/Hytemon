/*     */ package org.jline.builtins.telnet;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
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
/*     */ public abstract class Connection
/*     */   extends Thread
/*     */ {
/*  72 */   private static final Logger LOG = Logger.getLogger(Connection.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   private static int number;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean dead;
/*     */ 
/*     */ 
/*     */   
/*     */   private List<ConnectionListener> listeners;
/*     */ 
/*     */   
/*     */   private ConnectionData connectionData;
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection(ThreadGroup tcg, ConnectionData cd) {
/*  92 */     super(tcg, "Connection" + ++number);
/*     */     
/*  94 */     this.connectionData = cd;
/*     */ 
/*     */     
/*  97 */     this.listeners = new CopyOnWriteArrayList<>();
/*  98 */     this.dead = false;
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
/*     */   public void run() {
/*     */     try {
/* 113 */       doRun();
/*     */     }
/* 115 */     catch (Exception ex) {
/* 116 */       LOG.log(Level.SEVERE, "run()", ex);
/*     */     } finally {
/*     */       
/* 119 */       if (!this.dead) {
/* 120 */         close();
/*     */       }
/*     */     } 
/* 123 */     LOG.log(Level.FINE, "run():: Returning from " + toString());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doRun() throws Exception;
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doClose() throws Exception;
/*     */ 
/*     */ 
/*     */   
/*     */   public ConnectionData getConnectionData() {
/* 137 */     return this.connectionData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close() {
/* 145 */     if (this.dead) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 150 */       this.dead = true;
/*     */       
/* 152 */       doClose();
/* 153 */     } catch (Exception ex) {
/* 154 */       LOG.log(Level.SEVERE, "close()", ex);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 159 */       this.connectionData.getSocket().close();
/* 160 */     } catch (Exception ex) {
/* 161 */       LOG.log(Level.SEVERE, "close()", ex);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 166 */       this.connectionData.getManager().registerClosedConnection(this);
/* 167 */     } catch (Exception ex) {
/* 168 */       LOG.log(Level.SEVERE, "close()", ex);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 173 */       interrupt();
/* 174 */     } catch (Exception ex) {
/* 175 */       LOG.log(Level.SEVERE, "close()", ex);
/*     */     } 
/*     */ 
/*     */     
/* 179 */     LOG.log(Level.FINE, "Closed " + toString() + " and inactive.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 189 */     return !this.dead;
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
/*     */   public void addConnectionListener(ConnectionListener cl) {
/* 202 */     this.listeners.add(cl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeConnectionListener(ConnectionListener cl) {
/* 213 */     this.listeners.remove(cl);
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
/*     */   public void processConnectionEvent(ConnectionEvent ce) {
/* 225 */     for (ConnectionListener cl : this.listeners) {
/* 226 */       switch (ce.getType()) {
/*     */         case CONNECTION_IDLE:
/* 228 */           cl.connectionIdle(ce);
/*     */         
/*     */         case CONNECTION_TIMEDOUT:
/* 231 */           cl.connectionTimedOut(ce);
/*     */         
/*     */         case CONNECTION_LOGOUTREQUEST:
/* 234 */           cl.connectionLogoutRequest(ce);
/*     */         
/*     */         case CONNECTION_BREAK:
/* 237 */           cl.connectionSentBreak(ce);
/*     */         
/*     */         case CONNECTION_TERMINAL_GEOMETRY_CHANGED:
/* 240 */           cl.connectionTerminalGeometryChanged(ce);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\builtins\telnet\Connection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */