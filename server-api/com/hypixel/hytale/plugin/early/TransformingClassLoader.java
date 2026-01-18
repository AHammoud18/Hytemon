/*     */ package com.hypixel.hytale.plugin.early;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.security.CodeSource;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TransformingClassLoader
/*     */   extends URLClassLoader
/*     */ {
/*  19 */   private static final Set<String> SECURE_PACKAGE_PREFIXES = Set.of(new String[] { "java.", "javax.", "jdk.", "sun.", "com.sun.", "org.bouncycastle.", "server.io.netty.", "org.objectweb.asm.", "com.google.gson.", "org.slf4j.", "org.apache.logging.", "ch.qos.logback.", "com.google.flogger.", "server.io.sentry.", "com.hypixel.protoplus.", "com.hypixel.fastutil.", "com.hypixel.hytale.plugin.early." });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<ClassTransformer> transformers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ClassLoader appClassLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransformingClassLoader(@Nonnull URL[] urls, @Nonnull List<ClassTransformer> transformers, ClassLoader parent, ClassLoader appClassLoader) {
/*  53 */     super(urls, parent);
/*  54 */     this.transformers = transformers;
/*  55 */     this.appClassLoader = appClassLoader;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
/*  60 */     synchronized (getClassLoadingLock(name)) {
/*  61 */       Class<?> loaded = findLoadedClass(name);
/*  62 */       if (loaded != null) {
/*  63 */         if (resolve) resolveClass(loaded); 
/*  64 */         return loaded;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  69 */       if (isPreloadedClass(name)) {
/*  70 */         Class<?> clazz = this.appClassLoader.loadClass(name);
/*  71 */         if (resolve) resolveClass(clazz); 
/*  72 */         return clazz;
/*     */       } 
/*     */ 
/*     */       
/*  76 */       String internalName = name.replace('.', '/');
/*  77 */       URL resource = findResource(internalName + ".class");
/*  78 */       if (resource != null) {
/*  79 */         try { InputStream is = resource.openStream(); 
/*  80 */           try { Class<?> clazz = transformAndDefine(name, internalName, is.readAllBytes(), resource);
/*  81 */             if (resolve) resolveClass(clazz); 
/*  82 */             Class<?> clazz1 = clazz;
/*  83 */             if (is != null) is.close();  return clazz1; } catch (Throwable throwable) { if (is != null) try { is.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException iOException) {}
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  89 */       return super.loadClass(name, resolve);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Class<?> transformAndDefine(String name, String internalName, byte[] classBytes, URL resource) {
/*  94 */     if (!isSecureClass(name)) {
/*  95 */       for (ClassTransformer transformer : this.transformers) {
/*     */         try {
/*  97 */           byte[] transformed = transformer.transform(name, internalName, classBytes);
/*  98 */           if (transformed != null) {
/*  99 */             classBytes = transformed;
/*     */           }
/* 101 */         } catch (Exception e) {
/* 102 */           System.err.println("[EarlyPlugin] Transformer " + transformer.getClass().getName() + " failed on " + name + ": " + e.getMessage());
/* 103 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 109 */     URL codeSourceUrl = getCodeSourceUrl(resource, internalName);
/* 110 */     CodeSource codeSource = new CodeSource(codeSourceUrl, (Certificate[])null);
/* 111 */     ProtectionDomain protectionDomain = new ProtectionDomain(codeSource, null, this, null);
/* 112 */     return defineClass(name, classBytes, 0, classBytes.length, protectionDomain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static URL getCodeSourceUrl(URL resource, String internalName) {
/* 119 */     String urlStr = resource.toString();
/* 120 */     String classPath = internalName + ".class";
/*     */     
/* 122 */     if (urlStr.startsWith("jar:")) {
/*     */       
/* 124 */       int bangIndex = urlStr.indexOf("!/");
/* 125 */       if (bangIndex > 0) {
/*     */         try {
/* 127 */           return new URL(urlStr.substring(4, bangIndex));
/* 128 */         } catch (Exception e) {
/* 129 */           return resource;
/*     */         } 
/*     */       }
/* 132 */     } else if (urlStr.endsWith(classPath)) {
/*     */       
/*     */       try {
/* 135 */         return new URL(urlStr.substring(0, urlStr.length() - classPath.length()));
/* 136 */       } catch (Exception e) {
/* 137 */         return resource;
/*     */       } 
/*     */     } 
/* 140 */     return resource;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isPreloadedClass(@Nonnull String name) {
/* 146 */     return (name.equals("com.hypixel.hytale.Main") || name
/* 147 */       .startsWith("com.hypixel.hytale.plugin.early."));
/*     */   }
/*     */   
/*     */   private static boolean isSecureClass(@Nonnull String name) {
/* 151 */     for (String prefix : SECURE_PACKAGE_PREFIXES) {
/* 152 */       if (name.startsWith(prefix)) {
/* 153 */         return true;
/*     */       }
/*     */     } 
/* 156 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\plugin\early\TransformingClassLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */