/*     */ package io.netty.bootstrap;
/*     */ 
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.ServiceLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class ChannelInitializerExtensions
/*     */ {
/*  34 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChannelInitializerExtensions.class);
/*     */ 
/*     */   
/*     */   private static volatile ChannelInitializerExtensions implementation;
/*     */ 
/*     */ 
/*     */   
/*     */   private ChannelInitializerExtensions() {}
/*     */ 
/*     */ 
/*     */   
/*     */   static ChannelInitializerExtensions getExtensions() {
/*  46 */     ChannelInitializerExtensions impl = implementation;
/*  47 */     if (impl == null) {
/*  48 */       synchronized (ChannelInitializerExtensions.class) {
/*  49 */         impl = implementation;
/*  50 */         if (impl != null) {
/*  51 */           return impl;
/*     */         }
/*  53 */         String extensionProp = SystemPropertyUtil.get("io.netty.bootstrap.extensions");
/*  54 */         logger.debug("-Dio.netty.bootstrap.extensions: {}", extensionProp);
/*  55 */         if ("serviceload".equalsIgnoreCase(extensionProp)) {
/*  56 */           impl = new ServiceLoadingExtensions(true);
/*  57 */         } else if ("log".equalsIgnoreCase(extensionProp)) {
/*  58 */           impl = new ServiceLoadingExtensions(false);
/*     */         } else {
/*  60 */           impl = new EmptyExtensions();
/*     */         } 
/*  62 */         implementation = impl;
/*     */       } 
/*     */     }
/*  65 */     return impl;
/*     */   }
/*     */   
/*     */   abstract Collection<ChannelInitializerExtension> extensions(ClassLoader paramClassLoader);
/*     */   
/*     */   private static final class EmptyExtensions
/*     */     extends ChannelInitializerExtensions
/*     */   {
/*     */     private EmptyExtensions() {}
/*     */     
/*     */     Collection<ChannelInitializerExtension> extensions(ClassLoader cl) {
/*  76 */       return Collections.emptyList();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ServiceLoadingExtensions
/*     */     extends ChannelInitializerExtensions {
/*     */     private final boolean loadAndCache;
/*     */     private WeakReference<ClassLoader> classLoader;
/*     */     private Collection<ChannelInitializerExtension> extensions;
/*     */     
/*     */     ServiceLoadingExtensions(boolean loadAndCache) {
/*  87 */       this.loadAndCache = loadAndCache;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     synchronized Collection<ChannelInitializerExtension> extensions(ClassLoader cl) {
/*  93 */       ClassLoader configured = (this.classLoader == null) ? null : this.classLoader.get();
/*  94 */       if (configured == null || configured != cl) {
/*  95 */         Collection<ChannelInitializerExtension> loaded = serviceLoadExtensions(this.loadAndCache, cl);
/*  96 */         this.classLoader = new WeakReference<>(cl);
/*  97 */         this.extensions = this.loadAndCache ? loaded : Collections.<ChannelInitializerExtension>emptyList();
/*     */       } 
/*  99 */       return this.extensions;
/*     */     }
/*     */     
/*     */     private static Collection<ChannelInitializerExtension> serviceLoadExtensions(boolean load, ClassLoader cl) {
/* 103 */       List<ChannelInitializerExtension> extensions = new ArrayList<>();
/*     */       
/* 105 */       ServiceLoader<ChannelInitializerExtension> loader = ServiceLoader.load(ChannelInitializerExtension.class, cl);
/*     */       
/* 107 */       for (ChannelInitializerExtension extension : loader) {
/* 108 */         extensions.add(extension);
/*     */       }
/*     */       
/* 111 */       if (!extensions.isEmpty()) {
/* 112 */         Collections.sort(extensions, new Comparator<ChannelInitializerExtension>()
/*     */             {
/*     */               public int compare(ChannelInitializerExtension a, ChannelInitializerExtension b) {
/* 115 */                 return Double.compare(a.priority(), b.priority());
/*     */               }
/*     */             });
/* 118 */         ChannelInitializerExtensions.logger.info("ServiceLoader {}(s) {}: {}", new Object[] { ChannelInitializerExtension.class.getSimpleName(), 
/* 119 */               load ? "registered" : "detected", extensions });
/* 120 */         return Collections.unmodifiableList(extensions);
/*     */       } 
/* 122 */       ChannelInitializerExtensions.logger.debug("ServiceLoader {}(s) {}: []", ChannelInitializerExtension.class.getSimpleName(), 
/* 123 */           load ? "registered" : "detected");
/* 124 */       return Collections.emptyList();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\bootstrap\ChannelInitializerExtensions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */