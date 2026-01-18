/*    */ package io.sentry.internal.modules;
/*    */ 
/*    */ import io.sentry.ILogger;
/*    */ import io.sentry.ISentryLifecycleToken;
/*    */ import io.sentry.SentryLevel;
/*    */ import io.sentry.util.AutoClosableReentrantLock;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.nio.charset.Charset;
/*    */ import java.util.Map;
/*    */ import java.util.TreeMap;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ @Internal
/*    */ public abstract class ModulesLoader
/*    */   implements IModulesLoader
/*    */ {
/* 22 */   private static final Charset UTF_8 = Charset.forName("UTF-8");
/*    */   public static final String EXTERNAL_MODULES_FILENAME = "sentry-external-modules.txt";
/*    */   @NotNull
/*    */   protected final ILogger logger;
/*    */   @NotNull
/* 27 */   private final AutoClosableReentrantLock modulesLock = new AutoClosableReentrantLock(); @Nullable
/* 28 */   private volatile Map<String, String> cachedModules = null;
/*    */   
/*    */   public ModulesLoader(@NotNull ILogger logger) {
/* 31 */     this.logger = logger;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Map<String, String> getOrLoadModules() {
/* 36 */     if (this.cachedModules == null) {
/* 37 */       ISentryLifecycleToken ignored = this.modulesLock.acquire(); 
/* 38 */       try { if (this.cachedModules == null) {
/* 39 */           this.cachedModules = loadModules();
/*    */         }
/* 41 */         if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*    */           try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/* 43 */     }  return this.cachedModules;
/*    */   }
/*    */   
/*    */   protected abstract Map<String, String> loadModules();
/*    */   
/*    */   protected Map<String, String> parseStream(@NotNull InputStream stream) {
/* 49 */     Map<String, String> modules = new TreeMap<>(); 
/* 50 */     try { BufferedReader reader = new BufferedReader(new InputStreamReader(stream, UTF_8)); 
/* 51 */       try { String module = reader.readLine();
/* 52 */         while (module != null) {
/* 53 */           int sep = module.lastIndexOf(':');
/* 54 */           String group = module.substring(0, sep);
/* 55 */           String version = module.substring(sep + 1);
/* 56 */           modules.put(group, version);
/* 57 */           module = reader.readLine();
/*    */         } 
/* 59 */         this.logger.log(SentryLevel.DEBUG, "Extracted %d modules from resources.", new Object[] { Integer.valueOf(modules.size()) });
/* 60 */         reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/* 61 */     { this.logger.log(SentryLevel.ERROR, "Error extracting modules.", e); }
/* 62 */     catch (RuntimeException e)
/* 63 */     { this.logger.log(SentryLevel.ERROR, e, "%s file is malformed.", new Object[] { "sentry-external-modules.txt" }); }
/*    */     
/* 65 */     return modules;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\internal\modules\ModulesLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */