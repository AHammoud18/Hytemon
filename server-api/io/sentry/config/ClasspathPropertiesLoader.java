/*    */ package io.sentry.config;
/*    */ 
/*    */ import io.sentry.ILogger;
/*    */ import io.sentry.SentryLevel;
/*    */ import io.sentry.util.ClassLoaderUtils;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Properties;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ final class ClasspathPropertiesLoader implements PropertiesLoader {
/*    */   @NotNull
/*    */   private final String fileName;
/*    */   @NotNull
/*    */   private final ClassLoader classLoader;
/*    */   @NotNull
/*    */   private final ILogger logger;
/*    */   
/*    */   public ClasspathPropertiesLoader(@NotNull String fileName, @Nullable ClassLoader classLoader, @NotNull ILogger logger) {
/* 22 */     this.fileName = fileName;
/* 23 */     this.classLoader = ClassLoaderUtils.classLoaderOrDefault(classLoader);
/* 24 */     this.logger = logger;
/*    */   }
/*    */   
/*    */   public ClasspathPropertiesLoader(@NotNull ILogger logger) {
/* 28 */     this("sentry.properties", ClasspathPropertiesLoader.class.getClassLoader(), logger);
/*    */   }
/*    */   @Nullable
/*    */   public Properties load() {
/*    */     
/* 33 */     try { InputStream inputStream = this.classLoader.getResourceAsStream(this.fileName); 
/* 34 */       try { if (inputStream != null)
/* 35 */         { BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream); 
/* 36 */           try { Properties properties = new Properties();
/* 37 */             properties.load(bufferedInputStream);
/* 38 */             Properties properties1 = properties;
/* 39 */             bufferedInputStream.close();
/*    */             
/* 41 */             if (inputStream != null) inputStream.close();  return properties1; } catch (Throwable throwable) { try { bufferedInputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  }  if (inputStream != null) inputStream.close();  } catch (Throwable throwable) { if (inputStream != null) try { inputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 42 */     { this.logger.log(SentryLevel.ERROR, e, "Failed to load Sentry configuration from classpath resource: %s", new Object[] { this.fileName });
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 47 */       return null; }
/*    */     
/* 49 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\config\ClasspathPropertiesLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */