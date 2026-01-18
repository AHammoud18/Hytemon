/*     */ package com.hypixel.hytale.server.core.plugin;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class PluginClassLoader
/*     */   extends URLClassLoader {
/*     */   public static final String THIRD_PARTY_LOADER_NAME = "ThirdPartyPlugin";
/*     */   
/*     */   static {
/*  18 */     registerAsParallelCapable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final PluginManager pluginManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean inServerClassPath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private JavaPlugin plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PluginClassLoader(@Nonnull PluginManager pluginManager, boolean inServerClassPath, @Nonnull URL... urls) {
/*  48 */     super(inServerClassPath ? "BuiltinPlugin" : "ThirdPartyPlugin", urls, (ClassLoader)null);
/*  49 */     this.inServerClassPath = inServerClassPath;
/*  50 */     this.pluginManager = pluginManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInServerClassPath() {
/*  57 */     return this.inServerClassPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setPlugin(@Nonnull JavaPlugin plugin) {
/*  66 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Class<?> loadClass(@Nonnull String name, boolean resolve) throws ClassNotFoundException {
/*  73 */     return loadClass0(name, true);
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
/*     */   @Nonnull
/*     */   private Class<?> loadClass0(@Nonnull String name, boolean useBridge) throws ClassNotFoundException {
/*     */     try {
/*  88 */       Class<?> loadClass = PluginManager.class.getClassLoader().loadClass(name);
/*  89 */       if (loadClass != null) return loadClass; 
/*  90 */     } catch (ClassNotFoundException classNotFoundException) {}
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  95 */       Class<?> loadClass = super.loadClass(name, false);
/*  96 */       if (loadClass != null) return loadClass; 
/*  97 */     } catch (ClassNotFoundException classNotFoundException) {}
/*     */ 
/*     */     
/* 100 */     if (useBridge) {
/* 101 */       if (this.plugin != null) {
/*     */         
/*     */         try {
/* 104 */           Class<?> loadClass = this.pluginManager.getBridgeClassLoader().loadClass0(name, this, this.plugin.getManifest());
/* 105 */           if (loadClass != null) return loadClass; 
/* 106 */         } catch (ClassNotFoundException classNotFoundException) {}
/*     */       } else {
/*     */ 
/*     */         
/*     */         try {
/* 111 */           Class<?> loadClass = this.pluginManager.getBridgeClassLoader().loadClass0(name, this);
/* 112 */           if (loadClass != null) return loadClass; 
/* 113 */         } catch (ClassNotFoundException classNotFoundException) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 118 */     throw new ClassNotFoundException(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Class<?> loadLocalClass(@Nonnull String name) throws ClassNotFoundException {
/* 130 */     synchronized (getClassLoadingLock(name)) {
/*     */       
/* 132 */       Class<?> loadedClass = findLoadedClass(name);
/* 133 */       if (loadedClass == null) {
/*     */         try {
/* 135 */           ClassLoader parent = getParent();
/* 136 */           if (parent != null) {
/* 137 */             loadedClass = parent.loadClass(name);
/*     */           }
/* 139 */         } catch (ClassNotFoundException classNotFoundException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 144 */         if (loadedClass == null) {
/* 145 */           loadedClass = loadClass0(name, false);
/*     */         }
/*     */       } 
/* 148 */       return loadedClass;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public URL getResource(@Nonnull String name) {
/* 156 */     URL resource = PluginManager.class.getClassLoader().getResource(name);
/* 157 */     if (resource != null) return resource;
/*     */ 
/*     */     
/* 160 */     resource = super.getResource(name);
/* 161 */     if (resource != null) return resource;
/*     */ 
/*     */     
/* 164 */     PluginManager.PluginBridgeClassLoader bridge = this.pluginManager.getBridgeClassLoader();
/* 165 */     if (this.plugin != null) {
/* 166 */       resource = bridge.getResource0(name, this, this.plugin.getManifest());
/*     */     } else {
/* 168 */       resource = bridge.getResource0(name, this);
/*     */     } 
/* 170 */     return resource;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Enumeration<URL> getResources(@Nonnull String name) throws IOException {
/*     */     Enumeration<URL> bridgeResources;
/* 176 */     ObjectArrayList<URL> results = new ObjectArrayList();
/*     */ 
/*     */     
/* 179 */     Enumeration<URL> serverResources = PluginManager.class.getClassLoader().getResources(name);
/* 180 */     while (serverResources.hasMoreElements()) {
/* 181 */       results.add(serverResources.nextElement());
/*     */     }
/*     */ 
/*     */     
/* 185 */     Enumeration<URL> pluginResources = super.getResources(name);
/* 186 */     while (pluginResources.hasMoreElements()) {
/* 187 */       results.add(pluginResources.nextElement());
/*     */     }
/*     */ 
/*     */     
/* 191 */     PluginManager.PluginBridgeClassLoader bridge = this.pluginManager.getBridgeClassLoader();
/*     */     
/* 193 */     if (this.plugin != null) {
/* 194 */       bridgeResources = bridge.getResources0(name, this, this.plugin.getManifest());
/*     */     } else {
/* 196 */       bridgeResources = bridge.getResources0(name, this);
/*     */     } 
/* 198 */     while (bridgeResources.hasMoreElements()) {
/* 199 */       results.add(bridgeResources.nextElement());
/*     */     }
/*     */     
/* 202 */     return Collections.enumeration((Collection<URL>)results);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFromThirdPartyPlugin(@Nullable Throwable throwable) {
/* 210 */     while (throwable != null) {
/* 211 */       for (StackTraceElement element : throwable.getStackTrace()) {
/* 212 */         if ("ThirdPartyPlugin".equals(element.getClassLoaderName())) {
/* 213 */           return true;
/*     */         }
/*     */       } 
/* 216 */       if (throwable.getCause() == throwable)
/* 217 */         break;  throwable = throwable.getCause();
/*     */     } 
/* 219 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\PluginClassLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */