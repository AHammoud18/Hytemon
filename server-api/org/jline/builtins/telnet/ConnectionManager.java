/*     */ package org.jline.builtins.telnet;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
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
/*     */ public abstract class ConnectionManager
/*     */   implements Runnable
/*     */ {
/*  63 */   private static Logger LOG = Logger.getLogger(ConnectionManager.class.getName());
/*     */   
/*     */   private final List<Connection> openConnections;
/*     */   private Thread thread;
/*     */   private ThreadGroup threadGroup;
/*     */   private Stack<Connection> closedConnections;
/*     */   private ConnectionFilter connectionFilter;
/*     */   private int maxConnections;
/*     */   private int warningTimeout;
/*     */   private int disconnectTimeout;
/*     */   private int housekeepingInterval;
/*     */   private String loginShell;
/*     */   private boolean lineMode = false;
/*     */   private boolean stopping = false;
/*     */   
/*     */   public ConnectionManager() {
/*  79 */     this.threadGroup = new ThreadGroup(toString() + "Connections");
/*  80 */     this.closedConnections = new Stack<>();
/*  81 */     this.openConnections = Collections.synchronizedList(new ArrayList<>(100));
/*     */   }
/*     */ 
/*     */   
/*     */   public ConnectionManager(int con, int timew, int timedis, int hoke, ConnectionFilter filter, String lsh, boolean lm) {
/*  86 */     this();
/*  87 */     this.connectionFilter = filter;
/*  88 */     this.loginShell = lsh;
/*  89 */     this.lineMode = lm;
/*  90 */     this.maxConnections = con;
/*  91 */     this.warningTimeout = timew;
/*  92 */     this.disconnectTimeout = timedis;
/*  93 */     this.housekeepingInterval = hoke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConnectionFilter getConnectionFilter() {
/* 103 */     return this.connectionFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConnectionFilter(ConnectionFilter filter) {
/* 114 */     this.connectionFilter = filter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int openConnectionCount() {
/* 122 */     return this.openConnections.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection(int idx) {
/* 131 */     synchronized (this.openConnections) {
/* 132 */       return this.openConnections.get(idx);
/*     */     } 
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
/*     */   public Connection[] getConnectionsByAdddress(InetAddress addr) {
/* 145 */     ArrayList<Connection> l = new ArrayList<>();
/* 146 */     synchronized (this.openConnections) {
/* 147 */       for (Connection connection : this.openConnections) {
/* 148 */         if (connection.getConnectionData().getInetAddress().equals(addr)) {
/* 149 */           l.add(connection);
/*     */         }
/*     */       } 
/*     */     } 
/* 153 */     Connection[] conns = new Connection[l.size()];
/* 154 */     return l.<Connection>toArray(conns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 161 */     this.thread = new Thread(this);
/* 162 */     this.thread.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/* 169 */     LOG.log(Level.FINE, "stop()::" + toString());
/* 170 */     this.stopping = true;
/*     */     
/*     */     try {
/* 173 */       if (this.thread != null) {
/* 174 */         this.thread.join();
/*     */       }
/* 176 */     } catch (InterruptedException iex) {
/* 177 */       LOG.log(Level.SEVERE, "stop()", iex);
/*     */     } 
/* 179 */     synchronized (this.openConnections) {
/* 180 */       for (Connection tc : this.openConnections) {
/*     */         
/*     */         try {
/* 183 */           tc.close();
/* 184 */         } catch (Exception exc) {
/* 185 */           LOG.log(Level.SEVERE, "stop()", exc);
/*     */         } 
/*     */       } 
/* 188 */       this.openConnections.clear();
/*     */     } 
/* 190 */     LOG.log(Level.FINE, "stop():: Stopped " + toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void makeConnection(Socket insock) {
/* 200 */     LOG.log(Level.FINE, "makeConnection()::" + insock.toString());
/* 201 */     if (this.connectionFilter == null || this.connectionFilter.isAllowed(insock.getInetAddress())) {
/*     */ 
/*     */       
/* 204 */       ConnectionData newCD = new ConnectionData(insock, this);
/* 205 */       newCD.setLoginShell(this.loginShell);
/* 206 */       newCD.setLineMode(this.lineMode);
/* 207 */       if (this.openConnections.size() < this.maxConnections) {
/*     */         
/* 209 */         Connection con = createConnection(this.threadGroup, newCD);
/*     */         
/* 211 */         Object[] args = { Integer.valueOf(this.openConnections.size() + 1) };
/* 212 */         LOG.info(MessageFormat.format("connection #{0,number,integer} made.", args));
/*     */         
/* 214 */         synchronized (this.openConnections) {
/* 215 */           this.openConnections.add(con);
/*     */         } 
/*     */         
/* 218 */         con.start();
/*     */       } 
/*     */     } else {
/* 221 */       LOG.info("makeConnection():: Active Filter blocked incoming connection.");
/*     */       try {
/* 223 */         insock.close();
/* 224 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Connection createConnection(ThreadGroup paramThreadGroup, ConnectionData paramConnectionData);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*     */       do {
/* 245 */         cleanupClosed();
/*     */         
/* 247 */         checkOpenConnections();
/*     */         
/* 249 */         Thread.sleep(this.housekeepingInterval);
/* 250 */       } while (!this.stopping);
/*     */     }
/* 252 */     catch (Exception e) {
/* 253 */       LOG.log(Level.SEVERE, "run()", e);
/*     */     } 
/* 255 */     LOG.log(Level.FINE, "run():: Ran out " + toString());
/*     */   }
/*     */   
/*     */   private void cleanupClosed() {
/* 259 */     if (this.stopping) {
/*     */       return;
/*     */     }
/*     */     
/* 263 */     while (!this.closedConnections.isEmpty()) {
/* 264 */       Connection nextOne = this.closedConnections.pop();
/* 265 */       LOG.info("cleanupClosed():: Removing closed connection " + nextOne.toString());
/* 266 */       synchronized (this.openConnections) {
/* 267 */         this.openConnections.remove(nextOne);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkOpenConnections() {
/* 273 */     if (this.stopping) {
/*     */       return;
/*     */     }
/*     */     
/* 277 */     synchronized (this.openConnections) {
/* 278 */       for (Connection conn : this.openConnections) {
/* 279 */         ConnectionData cd = conn.getConnectionData();
/*     */         
/* 281 */         if (!conn.isActive()) {
/* 282 */           registerClosedConnection(conn);
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 287 */         long inactivity = System.currentTimeMillis() - cd.getLastActivity();
/*     */         
/* 289 */         if (inactivity > this.warningTimeout) {
/*     */           
/* 291 */           if (inactivity > (this.disconnectTimeout + this.warningTimeout)) {
/*     */             
/* 293 */             LOG.log(Level.FINE, "checkOpenConnections():" + conn.toString() + " exceeded total timeout.");
/*     */             
/* 295 */             conn.processConnectionEvent(new ConnectionEvent(conn, ConnectionEvent.Type.CONNECTION_TIMEDOUT));
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 300 */           if (!cd.isWarned()) {
/* 301 */             LOG.log(Level.FINE, "checkOpenConnections():" + conn
/*     */                 
/* 303 */                 .toString() + " exceeded warning timeout.");
/* 304 */             cd.setWarned(true);
/*     */             
/* 306 */             conn.processConnectionEvent(new ConnectionEvent(conn, ConnectionEvent.Type.CONNECTION_IDLE));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerClosedConnection(Connection con) {
/* 317 */     if (this.stopping) {
/*     */       return;
/*     */     }
/* 320 */     if (!this.closedConnections.contains(con)) {
/* 321 */       LOG.log(Level.FINE, "registerClosedConnection()::" + con.toString());
/* 322 */       this.closedConnections.push(con);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getDisconnectTimeout() {
/* 327 */     return this.disconnectTimeout;
/*     */   }
/*     */   
/*     */   public void setDisconnectTimeout(int disconnectTimeout) {
/* 331 */     this.disconnectTimeout = disconnectTimeout;
/*     */   }
/*     */   
/*     */   public int getHousekeepingInterval() {
/* 335 */     return this.housekeepingInterval;
/*     */   }
/*     */   
/*     */   public void setHousekeepingInterval(int housekeepingInterval) {
/* 339 */     this.housekeepingInterval = housekeepingInterval;
/*     */   }
/*     */   
/*     */   public boolean isLineMode() {
/* 343 */     return this.lineMode;
/*     */   }
/*     */   
/*     */   public void setLineMode(boolean lineMode) {
/* 347 */     this.lineMode = lineMode;
/*     */   }
/*     */   
/*     */   public String getLoginShell() {
/* 351 */     return this.loginShell;
/*     */   }
/*     */   
/*     */   public void setLoginShell(String loginShell) {
/* 355 */     this.loginShell = loginShell;
/*     */   }
/*     */   
/*     */   public int getMaxConnections() {
/* 359 */     return this.maxConnections;
/*     */   }
/*     */   
/*     */   public void setMaxConnections(int maxConnections) {
/* 363 */     this.maxConnections = maxConnections;
/*     */   }
/*     */   
/*     */   public int getWarningTimeout() {
/* 367 */     return this.warningTimeout;
/*     */   }
/*     */   
/*     */   public void setWarningTimeout(int warningTimeout) {
/* 371 */     this.warningTimeout = warningTimeout;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\builtins\telnet\ConnectionManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */