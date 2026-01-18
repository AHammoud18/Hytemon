/*     */ package com.hypixel.hytale.plugin.early;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.nio.file.DirectoryStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.ServiceLoader;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EarlyPluginLoader
/*     */ {
/*     */   @Nonnull
/*  28 */   public static final Path EARLY_PLUGINS_PATH = Path.of("earlyplugins", new String[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  34 */   private static final List<ClassTransformer> transformers = (List<ClassTransformer>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static URLClassLoader pluginClassLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loadEarlyPlugins(@Nonnull String[] args) {
/*  57 */     ObjectArrayList<URL> urls = new ObjectArrayList();
/*  58 */     collectPluginJars(EARLY_PLUGINS_PATH, (List<URL>)urls);
/*     */ 
/*     */     
/*  61 */     for (Path path : parseEarlyPluginPaths(args)) {
/*  62 */       collectPluginJars(path, (List<URL>)urls);
/*     */     }
/*     */     
/*  65 */     if (urls.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  69 */     pluginClassLoader = new URLClassLoader((URL[])urls.toArray(x$0 -> new URL[x$0]), EarlyPluginLoader.class.getClassLoader());
/*     */ 
/*     */     
/*  72 */     for (ClassTransformer transformer : ServiceLoader.<ClassTransformer>load(ClassTransformer.class, pluginClassLoader)) {
/*  73 */       System.out.println("[EarlyPlugin] Loading transformer: " + transformer.getClass().getName() + " (priority=" + transformer.priority() + ")");
/*  74 */       transformers.add(transformer);
/*     */     } 
/*     */ 
/*     */     
/*  78 */     transformers.sort(Comparator.<ClassTransformer>comparingInt(ClassTransformer::priority).reversed());
/*     */     
/*  80 */     if (!transformers.isEmpty()) {
/*  81 */       System.err.printf("===============================================================================================\n                              Loaded %d class transformer(s)!!\n===============================================================================================\n                       This is unsupported and may cause stability issues.\n                                     Use at your own risk!!\n===============================================================================================\n", new Object[] {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  88 */             Integer.valueOf(transformers.size())
/*     */           });
/*     */       
/*  91 */       boolean isSingleplayer = hasFlag(args, "--singleplayer");
/*  92 */       boolean acceptEarlyPlugins = hasFlag(args, "--accept-early-plugins");
/*     */       
/*  94 */       if (!isSingleplayer && !acceptEarlyPlugins) {
/*  95 */         if (System.console() == null) {
/*  96 */           System.err.println("ERROR: Early plugins require interactive confirmation, but no console is available.");
/*  97 */           System.err.println("Pass --accept-early-plugins to accept the risk and continue.");
/*  98 */           System.exit(1);
/*     */         } 
/* 100 */         System.err.print("Press ENTER to accept and continue...");
/* 101 */         System.console().readLine();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static List<Path> parseEarlyPluginPaths(@Nonnull String[] args) {
/* 107 */     ObjectArrayList<Path> paths = new ObjectArrayList();
/* 108 */     for (int i = 0; i < args.length; i++) {
/* 109 */       if (args[i].equals("--early-plugins") && i + 1 < args.length) {
/* 110 */         for (String pathStr : args[i + 1].split(",")) {
/* 111 */           paths.add(Path.of(pathStr.trim(), new String[0]));
/*     */         }
/* 113 */       } else if (args[i].startsWith("--early-plugins=")) {
/* 114 */         String value = args[i].substring("--early-plugins=".length());
/* 115 */         for (String pathStr : value.split(",")) {
/* 116 */           paths.add(Path.of(pathStr.trim(), new String[0]));
/*     */         }
/*     */       } 
/*     */     } 
/* 120 */     return (List<Path>)paths;
/*     */   }
/*     */   
/*     */   private static boolean hasFlag(String[] args, String flag) {
/* 124 */     for (String arg : args) {
/* 125 */       if (arg.equals(flag)) return true; 
/*     */     } 
/* 127 */     return false;
/*     */   }
/*     */   
/*     */   private static void collectPluginJars(Path path, List<URL> urls) {
/* 131 */     if (!Files.isDirectory(path, new java.nio.file.LinkOption[0]))
/*     */       return;  
/* 133 */     try { DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*.jar"); 
/* 134 */       try { for (Path file : stream) {
/* 135 */           if (Files.isRegularFile(file, new java.nio.file.LinkOption[0])) {
/* 136 */             urls.add(file.toUri().toURL());
/* 137 */             System.out.println("[EarlyPlugin] Found: " + String.valueOf(file.getFileName()));
/*     */           } 
/*     */         } 
/* 140 */         if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 141 */     { System.err.println("[EarlyPlugin] Failed to scan directory " + String.valueOf(path) + ": " + e.getMessage()); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasTransformers() {
/* 149 */     return !transformers.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<ClassTransformer> getTransformers() {
/* 156 */     return Collections.unmodifiableList(transformers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static URLClassLoader getPluginClassLoader() {
/* 164 */     return pluginClassLoader;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\plugin\early\EarlyPluginLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */