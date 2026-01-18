/*      */ package com.hypixel.hytale.server.core.plugin;
/*      */ 
/*      */ import com.hypixel.hytale.common.plugin.PluginIdentifier;
/*      */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import java.net.URL;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Map;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PluginBridgeClassLoader
/*      */   extends ClassLoader
/*      */ {
/*      */   private final PluginManager pluginManager;
/*      */   
/*      */   static {
/*  830 */     registerAsParallelCapable();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PluginBridgeClassLoader(PluginManager pluginManager, ClassLoader parent) {
/*  836 */     super(parent);
/*  837 */     this.pluginManager = pluginManager;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   protected Class<?> loadClass(@Nonnull String name, boolean resolve) throws ClassNotFoundException {
/*  843 */     return loadClass0(name, null);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public Class<?> loadClass0(@Nonnull String name, PluginClassLoader pluginClassLoader) throws ClassNotFoundException {
/*  848 */     this.pluginManager.lock.readLock().lock();
/*      */     try {
/*  850 */       for (Map.Entry<PluginIdentifier, PluginBase> entry : this.pluginManager.plugins.entrySet()) {
/*  851 */         PluginBase pluginBase = entry.getValue();
/*  852 */         Class<?> loadClass = tryGetClass(name, pluginClassLoader, pluginBase);
/*  853 */         if (loadClass != null) return loadClass; 
/*      */       } 
/*      */     } finally {
/*  856 */       this.pluginManager.lock.readLock().unlock();
/*      */     } 
/*  858 */     throw new ClassNotFoundException();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public Class<?> loadClass0(@Nonnull String name, PluginClassLoader pluginClassLoader, @Nonnull PluginManifest manifest) throws ClassNotFoundException {
/*  863 */     this.pluginManager.lock.readLock().lock();
/*      */     try {
/*  865 */       for (PluginIdentifier pluginIdentifier : manifest.getDependencies().keySet()) {
/*  866 */         PluginBase pluginBase = this.pluginManager.plugins.get(pluginIdentifier);
/*  867 */         Class<?> loadClass = tryGetClass(name, pluginClassLoader, pluginBase);
/*  868 */         if (loadClass != null) return loadClass;
/*      */       
/*      */       } 
/*  871 */       for (PluginIdentifier pluginIdentifier : manifest.getOptionalDependencies().keySet()) {
/*  872 */         if (manifest.getDependencies().containsKey(pluginIdentifier))
/*      */           continue; 
/*  874 */         PluginBase pluginBase = this.pluginManager.plugins.get(pluginIdentifier);
/*  875 */         if (pluginBase == null)
/*      */           continue; 
/*  877 */         Class<?> loadClass = tryGetClass(name, pluginClassLoader, pluginBase);
/*  878 */         if (loadClass != null) return loadClass;
/*      */       
/*      */       } 
/*  881 */       for (Map.Entry<PluginIdentifier, PluginBase> entry : this.pluginManager.plugins.entrySet()) {
/*  882 */         if (manifest.getDependencies().containsKey(entry.getKey()) || 
/*  883 */           manifest.getOptionalDependencies().containsKey(entry.getKey()))
/*      */           continue; 
/*  885 */         PluginBase pluginBase = entry.getValue();
/*  886 */         Class<?> loadClass = tryGetClass(name, pluginClassLoader, pluginBase);
/*  887 */         if (loadClass != null) return loadClass; 
/*      */       } 
/*      */     } finally {
/*  890 */       this.pluginManager.lock.readLock().unlock();
/*      */     } 
/*  892 */     throw new ClassNotFoundException();
/*      */   }
/*      */   
/*      */   public static Class<?> tryGetClass(@Nonnull String name, PluginClassLoader pluginClassLoader, PluginBase pluginBase) {
/*  896 */     if (!(pluginBase instanceof JavaPlugin)) return null;
/*      */     
/*      */     try {
/*  899 */       PluginClassLoader classLoader = ((JavaPlugin)pluginBase).getClassLoader();
/*  900 */       if (classLoader != pluginClassLoader) {
/*  901 */         Class<?> loadClass = classLoader.loadLocalClass(name);
/*  902 */         if (loadClass != null) return loadClass; 
/*      */       } 
/*  904 */     } catch (ClassNotFoundException classNotFoundException) {}
/*      */     
/*  906 */     return null;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public URL getResource0(@Nonnull String name, @Nullable PluginClassLoader pluginClassLoader) {
/*  911 */     this.pluginManager.lock.readLock().lock();
/*      */     try {
/*  913 */       for (Map.Entry<PluginIdentifier, PluginBase> entry : this.pluginManager.plugins.entrySet()) {
/*  914 */         URL resource = tryGetResource(name, pluginClassLoader, entry.getValue());
/*  915 */         if (resource != null) return resource; 
/*      */       } 
/*      */     } finally {
/*  918 */       this.pluginManager.lock.readLock().unlock();
/*      */     } 
/*  920 */     return null;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public URL getResource0(@Nonnull String name, @Nullable PluginClassLoader pluginClassLoader, @Nonnull PluginManifest manifest) {
/*  925 */     this.pluginManager.lock.readLock().lock();
/*      */     try {
/*  927 */       for (PluginIdentifier pluginIdentifier : manifest.getDependencies().keySet()) {
/*  928 */         URL resource = tryGetResource(name, pluginClassLoader, this.pluginManager.plugins.get(pluginIdentifier));
/*  929 */         if (resource != null) return resource;
/*      */       
/*      */       } 
/*  932 */       for (PluginIdentifier pluginIdentifier : manifest.getOptionalDependencies().keySet()) {
/*  933 */         if (manifest.getDependencies().containsKey(pluginIdentifier))
/*      */           continue; 
/*  935 */         PluginBase pluginBase = this.pluginManager.plugins.get(pluginIdentifier);
/*  936 */         if (pluginBase == null)
/*      */           continue; 
/*  938 */         URL resource = tryGetResource(name, pluginClassLoader, pluginBase);
/*  939 */         if (resource != null) return resource;
/*      */       
/*      */       } 
/*  942 */       for (Map.Entry<PluginIdentifier, PluginBase> entry : this.pluginManager.plugins.entrySet()) {
/*  943 */         if (manifest.getDependencies().containsKey(entry.getKey()) || 
/*  944 */           manifest.getOptionalDependencies().containsKey(entry.getKey()))
/*      */           continue; 
/*  946 */         URL resource = tryGetResource(name, pluginClassLoader, entry.getValue());
/*  947 */         if (resource != null) return resource; 
/*      */       } 
/*      */     } finally {
/*  950 */       this.pluginManager.lock.readLock().unlock();
/*      */     } 
/*  952 */     return null;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public Enumeration<URL> getResources0(@Nonnull String name, @Nullable PluginClassLoader pluginClassLoader) {
/*  957 */     ObjectArrayList<URL> results = new ObjectArrayList();
/*  958 */     this.pluginManager.lock.readLock().lock();
/*      */     try {
/*  960 */       for (Map.Entry<PluginIdentifier, PluginBase> entry : this.pluginManager.plugins.entrySet()) {
/*  961 */         URL resource = tryGetResource(name, pluginClassLoader, entry.getValue());
/*  962 */         if (resource != null) results.add(resource); 
/*      */       } 
/*      */     } finally {
/*  965 */       this.pluginManager.lock.readLock().unlock();
/*      */     } 
/*  967 */     return Collections.enumeration((Collection<URL>)results);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public Enumeration<URL> getResources0(@Nonnull String name, @Nullable PluginClassLoader pluginClassLoader, @Nonnull PluginManifest manifest) {
/*  972 */     ObjectArrayList<URL> results = new ObjectArrayList();
/*  973 */     this.pluginManager.lock.readLock().lock();
/*      */     try {
/*  975 */       for (PluginIdentifier pluginIdentifier : manifest.getDependencies().keySet()) {
/*  976 */         URL resource = tryGetResource(name, pluginClassLoader, this.pluginManager.plugins.get(pluginIdentifier));
/*  977 */         if (resource != null) results.add(resource);
/*      */       
/*      */       } 
/*  980 */       for (PluginIdentifier pluginIdentifier : manifest.getOptionalDependencies().keySet()) {
/*  981 */         if (manifest.getDependencies().containsKey(pluginIdentifier))
/*      */           continue; 
/*  983 */         PluginBase pluginBase = this.pluginManager.plugins.get(pluginIdentifier);
/*  984 */         if (pluginBase == null)
/*      */           continue; 
/*  986 */         URL resource = tryGetResource(name, pluginClassLoader, pluginBase);
/*  987 */         if (resource != null) results.add(resource);
/*      */       
/*      */       } 
/*  990 */       for (Map.Entry<PluginIdentifier, PluginBase> entry : this.pluginManager.plugins.entrySet()) {
/*  991 */         if (manifest.getDependencies().containsKey(entry.getKey()) || 
/*  992 */           manifest.getOptionalDependencies().containsKey(entry.getKey()))
/*      */           continue; 
/*  994 */         URL resource = tryGetResource(name, pluginClassLoader, entry.getValue());
/*  995 */         if (resource != null) results.add(resource); 
/*      */       } 
/*      */     } finally {
/*  998 */       this.pluginManager.lock.readLock().unlock();
/*      */     } 
/* 1000 */     return Collections.enumeration((Collection<URL>)results);
/*      */   }
/*      */   @Nullable
/*      */   private static URL tryGetResource(@Nonnull String name, @Nullable PluginClassLoader pluginClassLoader, @Nullable PluginBase pluginBase) {
/*      */     JavaPlugin javaPlugin;
/* 1005 */     if (pluginBase instanceof JavaPlugin) { javaPlugin = (JavaPlugin)pluginBase; } else { return null; }
/*      */     
/* 1007 */     PluginClassLoader classLoader = javaPlugin.getClassLoader();
/* 1008 */     if (classLoader != pluginClassLoader) {
/* 1009 */       return classLoader.findResource(name);
/*      */     }
/* 1011 */     return null;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\PluginManager$PluginBridgeClassLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */