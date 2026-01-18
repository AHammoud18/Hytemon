/*    */ package io.sentry.config;
/*    */ import io.sentry.ILogger;
/*    */ import io.sentry.SentryLevel;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.InputStream;
/*    */ import java.util.Properties;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ final class FilesystemPropertiesLoader implements PropertiesLoader {
/*    */   @NotNull
/*    */   private final String filePath;
/*    */   @NotNull
/*    */   private final ILogger logger;
/*    */   private boolean logNonExisting;
/*    */   
/*    */   public FilesystemPropertiesLoader(@NotNull String filePath, @NotNull ILogger logger) {
/* 20 */     this(filePath, logger, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public FilesystemPropertiesLoader(@NotNull String filePath, @NotNull ILogger logger, boolean logNonExisting) {
/* 25 */     this.filePath = filePath;
/* 26 */     this.logger = logger;
/* 27 */     this.logNonExisting = logNonExisting;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Properties load() {
/*    */     try {
/* 33 */       File f = new File(this.filePath.trim());
/* 34 */       if (f.isFile() && f.canRead()) {
/* 35 */         InputStream is = new BufferedInputStream(new FileInputStream(f)); 
/* 36 */         try { Properties properties = new Properties();
/* 37 */           properties.load(is);
/* 38 */           Properties properties1 = properties;
/* 39 */           is.close(); return properties1; } catch (Throwable throwable) { try { is.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; } 
/* 40 */       }  if (!f.isFile()) {
/* 41 */         if (this.logNonExisting) {
/* 42 */           this.logger.log(SentryLevel.ERROR, "Failed to load Sentry configuration since it is not a file or does not exist: %s", new Object[] { this.filePath });
/*    */         
/*    */         }
/*    */       
/*    */       }
/* 47 */       else if (!f.canRead()) {
/* 48 */         this.logger.log(SentryLevel.ERROR, "Failed to load Sentry configuration since it is not readable: %s", new Object[] { this.filePath });
/*    */       
/*    */       }
/*    */     
/*    */     }
/* 53 */     catch (Throwable e) {
/* 54 */       this.logger.log(SentryLevel.ERROR, e, "Failed to load Sentry configuration from file: %s", new Object[] { this.filePath });
/*    */       
/* 56 */       return null;
/*    */     } 
/* 58 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\config\FilesystemPropertiesLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */