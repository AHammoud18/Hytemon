/*     */ package org.jline.builtins.telnet;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
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
/*     */ public class ConnectionData
/*     */ {
/*     */   private ConnectionManager connectionManager;
/*     */   private Socket socket;
/*     */   private InetAddress address;
/*     */   private Map<String, String> environment;
/*     */   private String hostName;
/*     */   private String hostAddress;
/*     */   private int port;
/*     */   private Locale locale;
/*     */   private long lastActivity;
/*     */   private boolean warned;
/*     */   private String negotiatedTerminalType;
/*     */   private int[] terminalGeometry;
/*     */   private boolean terminalGeometryChanged = true;
/*     */   private String loginShell;
/*     */   private boolean lineMode = false;
/*     */   
/*     */   public ConnectionData(Socket sock, ConnectionManager cm) {
/*  86 */     this.socket = sock;
/*  87 */     this.connectionManager = cm;
/*  88 */     this.address = sock.getInetAddress();
/*  89 */     setHostName();
/*  90 */     setHostAddress();
/*  91 */     setLocale();
/*  92 */     this.port = sock.getPort();
/*     */     
/*  94 */     this.terminalGeometry = new int[2];
/*  95 */     this.terminalGeometry[0] = 80;
/*  96 */     this.terminalGeometry[1] = 25;
/*  97 */     this.negotiatedTerminalType = "default";
/*  98 */     this.environment = new HashMap<>(20);
/*     */     
/* 100 */     activity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConnectionManager getManager() {
/* 111 */     return this.connectionManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Socket getSocket() {
/* 122 */     return this.socket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 131 */     return this.port;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHostName() {
/* 142 */     return this.hostName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHostAddress() {
/* 152 */     return this.hostAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InetAddress getInetAddress() {
/* 161 */     return this.address;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Locale getLocale() {
/* 182 */     return this.locale;
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
/*     */   public long getLastActivity() {
/* 194 */     return this.lastActivity;
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
/*     */   public void activity() {
/* 208 */     this.warned = false;
/* 209 */     this.lastActivity = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWarned() {
/* 220 */     return this.warned;
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
/*     */   public void setWarned(boolean bool) {
/* 235 */     this.warned = bool;
/* 236 */     if (!bool) {
/* 237 */       this.lastActivity = System.currentTimeMillis();
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
/*     */   
/*     */   public void setTerminalGeometry(int width, int height) {
/* 251 */     this.terminalGeometry[0] = width;
/* 252 */     this.terminalGeometry[1] = height;
/* 253 */     this.terminalGeometryChanged = true;
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
/*     */   public int[] getTerminalGeometry() {
/* 268 */     if (this.terminalGeometryChanged) this.terminalGeometryChanged = false; 
/* 269 */     return this.terminalGeometry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTerminalColumns() {
/* 278 */     return this.terminalGeometry[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTerminalRows() {
/* 287 */     return this.terminalGeometry[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTerminalGeometryChanged() {
/* 298 */     return this.terminalGeometryChanged;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNegotiatedTerminalType() {
/* 309 */     return this.negotiatedTerminalType;
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
/*     */   public void setNegotiatedTerminalType(String termtype) {
/* 323 */     this.negotiatedTerminalType = termtype;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getEnvironment() {
/* 334 */     return this.environment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLoginShell() {
/* 343 */     return this.loginShell;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoginShell(String s) {
/* 352 */     this.loginShell = s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLineMode() {
/* 361 */     return this.lineMode;
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
/*     */   public void setLineMode(boolean b) {
/* 373 */     this.lineMode = b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setHostName() {
/* 380 */     this.hostName = this.address.getHostName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setHostAddress() {
/* 387 */     this.hostAddress = this.address.getHostAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setLocale() {
/* 398 */     String country = getHostName();
/*     */     try {
/* 400 */       country = country.substring(country.lastIndexOf(".") + 1);
/* 401 */       if (country.equals("at")) {
/* 402 */         this.locale = localeOf("de", "AT");
/* 403 */       } else if (country.equals("de")) {
/* 404 */         this.locale = localeOf("de", "DE");
/* 405 */       } else if (country.equals("mx")) {
/* 406 */         this.locale = localeOf("es", "MX");
/* 407 */       } else if (country.equals("es")) {
/* 408 */         this.locale = localeOf("es", "ES");
/* 409 */       } else if (country.equals("it")) {
/* 410 */         this.locale = Locale.ITALY;
/* 411 */       } else if (country.equals("fr")) {
/* 412 */         this.locale = Locale.FRANCE;
/* 413 */       } else if (country.equals("uk")) {
/* 414 */         this.locale = Locale.UK;
/* 415 */       } else if (country.equals("arpa")) {
/* 416 */         this.locale = Locale.US;
/* 417 */       } else if (country.equals("com")) {
/* 418 */         this.locale = Locale.US;
/* 419 */       } else if (country.equals("edu")) {
/* 420 */         this.locale = Locale.US;
/* 421 */       } else if (country.equals("gov")) {
/* 422 */         this.locale = Locale.US;
/* 423 */       } else if (country.equals("org")) {
/* 424 */         this.locale = Locale.US;
/* 425 */       } else if (country.equals("mil")) {
/* 426 */         this.locale = Locale.US;
/*     */       } else {
/*     */         
/* 429 */         this.locale = Locale.ENGLISH;
/*     */       } 
/* 431 */     } catch (Exception ex) {
/*     */       
/* 433 */       this.locale = Locale.ENGLISH;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Locale localeOf(String language, String country) {
/* 441 */     return new Locale(language, country);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\builtins\telnet\ConnectionData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */