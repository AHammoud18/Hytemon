/*     */ package org.jline.builtins;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfigurationPath
/*     */ {
/*     */   private final Path appConfig;
/*     */   private final Path userConfig;
/*     */   
/*     */   public ConfigurationPath(Path appConfig, Path userConfig) {
/*  39 */     this.appConfig = appConfig;
/*  40 */     this.userConfig = userConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConfigurationPath(String classpathResource, Path userConfig) {
/*  49 */     this.appConfig = null;
/*  50 */     this.userConfig = userConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path getConfig(String name) {
/*  61 */     Path out = null;
/*     */     
/*  63 */     if (this.userConfig != null && Files.exists(this.userConfig.resolve(name), new java.nio.file.LinkOption[0])) {
/*  64 */       out = this.userConfig.resolve(name);
/*     */     
/*     */     }
/*  67 */     else if (this.appConfig != null && Files.exists(this.appConfig.resolve(name), new java.nio.file.LinkOption[0])) {
/*  68 */       out = this.appConfig.resolve(name);
/*     */     } 
/*  70 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path getUserConfig(String name) throws IOException {
/*  81 */     return getUserConfig(name, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path getUserConfig(String name, boolean create) throws IOException {
/*  92 */     Path out = null;
/*  93 */     if (this.userConfig != null) {
/*  94 */       if (!Files.exists(this.userConfig.resolve(name), new java.nio.file.LinkOption[0]) && create) {
/*  95 */         Files.createFile(this.userConfig.resolve(name), (FileAttribute<?>[])new FileAttribute[0]);
/*     */       }
/*  97 */       if (Files.exists(this.userConfig.resolve(name), new java.nio.file.LinkOption[0])) {
/*  98 */         out = this.userConfig.resolve(name);
/*     */       }
/*     */     } 
/* 101 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigurationPath fromClasspath(String classpathResource) {
/* 111 */     return new ConfigurationPath(classpathResource, null);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\builtins\ConfigurationPath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */