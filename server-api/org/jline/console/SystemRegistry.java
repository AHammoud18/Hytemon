/*     */ package org.jline.console;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jline.builtins.ConsoleOptionGetter;
/*     */ import org.jline.reader.Completer;
/*     */ import org.jline.reader.ParsedLine;
/*     */ import org.jline.terminal.Terminal;
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
/*     */ public interface SystemRegistry
/*     */   extends CommandRegistry, ConsoleOptionGetter
/*     */ {
/*     */   void setCommandRegistries(CommandRegistry... paramVarArgs);
/*     */   
/*     */   void register(String paramString, CommandRegistry paramCommandRegistry);
/*     */   
/*     */   void initialize(File paramFile);
/*     */   
/*     */   Collection<String> getPipeNames();
/*     */   
/*     */   Completer completer();
/*     */   
/*     */   CmdDesc commandDescription(CmdLine paramCmdLine);
/*     */   
/*     */   Object execute(String paramString) throws Exception;
/*     */   
/*     */   void cleanUp();
/*     */   
/*     */   void trace(Throwable paramThrowable);
/*     */   
/*     */   void trace(boolean paramBoolean, Throwable paramThrowable);
/*     */   
/*     */   Object consoleOption(String paramString);
/*     */   
/*     */   <T> T consoleOption(String paramString, T paramT);
/*     */   
/*     */   void setConsoleOption(String paramString, Object paramObject);
/*     */   
/*     */   Terminal terminal();
/*     */   
/*     */   Object invoke(String paramString, Object... paramVarArgs) throws Exception;
/*     */   
/*     */   boolean isCommandOrScript(ParsedLine paramParsedLine);
/*     */   
/*     */   boolean isCommandOrScript(String paramString);
/*     */   
/*     */   boolean isCommandAlias(String paramString);
/*     */   
/*     */   void close();
/*     */   
/*     */   static SystemRegistry get() {
/* 264 */     return Registeries.getInstance().getSystemRegistry();
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
/*     */   static void add(SystemRegistry systemRegistry) {
/* 277 */     Registeries.getInstance().addRegistry(systemRegistry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void remove() {
/* 288 */     Registeries.getInstance().removeRegistry();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Registeries
/*     */   {
/* 299 */     private static final Registeries instance = new Registeries();
/* 300 */     private final Map<Long, SystemRegistry> systemRegisteries = new HashMap<>();
/*     */ 
/*     */ 
/*     */     
/*     */     protected static Registeries getInstance() {
/* 305 */       return instance;
/*     */     }
/*     */     
/*     */     protected void addRegistry(SystemRegistry systemRegistry) {
/* 309 */       this.systemRegisteries.put(Long.valueOf(getThreadId()), systemRegistry);
/*     */     }
/*     */     
/*     */     protected SystemRegistry getSystemRegistry() {
/* 313 */       return this.systemRegisteries.getOrDefault(Long.valueOf(getThreadId()), null);
/*     */     }
/*     */     
/*     */     protected void removeRegistry() {
/* 317 */       this.systemRegisteries.remove(Long.valueOf(getThreadId()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static long getThreadId() {
/* 323 */       return Thread.currentThread().getId();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\console\SystemRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */