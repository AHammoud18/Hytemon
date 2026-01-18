/*     */ package io.sentry.internal;
/*     */ import io.sentry.ISentryLifecycleToken;
/*     */ import io.sentry.SentryIntegrationPackageStorage;
/*     */ import io.sentry.util.AutoClosableReentrantLock;
/*     */ import java.net.URL;
/*     */ import java.util.Enumeration;
/*     */ import java.util.jar.Attributes;
/*     */ import java.util.jar.Manifest;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class ManifestVersionReader {
/*     */   @Nullable
/*     */   private static volatile ManifestVersionReader INSTANCE;
/*     */   @NotNull
/*  18 */   private static final AutoClosableReentrantLock staticLock = new AutoClosableReentrantLock();
/*     */   private volatile boolean hasManifestBeenRead = false;
/*     */   @NotNull
/*  21 */   private final VersionInfoHolder versionInfo = new VersionInfoHolder(); @NotNull
/*  22 */   private AutoClosableReentrantLock lock = new AutoClosableReentrantLock();
/*     */   @NotNull
/*     */   public static ManifestVersionReader getInstance() {
/*  25 */     if (INSTANCE == null) {
/*  26 */       ISentryLifecycleToken ignored = staticLock.acquire(); 
/*  27 */       try { if (INSTANCE == null) {
/*  28 */           INSTANCE = new ManifestVersionReader();
/*     */         }
/*  30 */         if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*     */           try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*     */     
/*  33 */     }  return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public VersionInfoHolder readOpenTelemetryVersion() {
/*  39 */     readManifestFiles();
/*  40 */     if (this.versionInfo.sdkVersion == null) {
/*  41 */       return null;
/*     */     }
/*  43 */     return this.versionInfo;
/*     */   }
/*     */   
/*     */   public void readManifestFiles() {
/*  47 */     if (this.hasManifestBeenRead) {
/*     */       return;
/*     */     }
/*     */     
/*  51 */     try { ISentryLifecycleToken ignored = this.lock.acquire(); 
/*  52 */       try { if (this.hasManifestBeenRead)
/*     */         
/*     */         { 
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
/* 107 */           if (ignored != null) ignored.close();  return; }  Enumeration<URL> resources = ClassLoader.getSystemClassLoader().getResources("META-INF/MANIFEST.MF"); while (resources.hasMoreElements()) { try { Manifest manifest = new Manifest(((URL)resources.nextElement()).openStream()); Attributes mainAttributes = manifest.getMainAttributes(); if (mainAttributes != null) { String name = mainAttributes.getValue("Sentry-Opentelemetry-SDK-Name"); String version = mainAttributes.getValue("Implementation-Version"); String sdkName = mainAttributes.getValue("Sentry-SDK-Name"); String packageName = mainAttributes.getValue("Sentry-SDK-Package-Name"); if (name != null && version != null) { this.versionInfo.sdkName = name; this.versionInfo.sdkVersion = version; String otelVersion = mainAttributes.getValue("Sentry-Opentelemetry-Version-Name"); if (otelVersion != null) { SentryIntegrationPackageStorage.getInstance().addPackage("maven:io.opentelemetry:opentelemetry-sdk", otelVersion); SentryIntegrationPackageStorage.getInstance().addIntegration("OpenTelemetry"); }  String otelJavaagentVersion = mainAttributes.getValue("Sentry-Opentelemetry-Javaagent-Version-Name"); if (otelJavaagentVersion != null) { SentryIntegrationPackageStorage.getInstance().addPackage("maven:io.opentelemetry.javaagent:opentelemetry-javaagent", otelJavaagentVersion); SentryIntegrationPackageStorage.getInstance().addIntegration("OpenTelemetry-Agent"); }  if (name.equals("sentry.java.opentelemetry.agentless")) SentryIntegrationPackageStorage.getInstance().addIntegration("OpenTelemetry-Agentless");  if (name.equals("sentry.java.opentelemetry.agentless-spring")) SentryIntegrationPackageStorage.getInstance().addIntegration("OpenTelemetry-Agentless-Spring");  }  if (sdkName != null && version != null && packageName != null && sdkName.startsWith("sentry.java")) SentryIntegrationPackageStorage.getInstance().addPackage(packageName, version);  }  } catch (Exception exception) {} }  if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null) try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException iOException)
/*     */     {  }
/*     */     finally
/* 110 */     { this.hasManifestBeenRead = true; }
/*     */   
/*     */   }
/*     */   public static final class VersionInfoHolder { @Nullable
/*     */     private volatile String sdkName; @Nullable
/*     */     private volatile String sdkVersion;
/*     */     
/*     */     @Nullable
/*     */     public String getSdkName() {
/* 119 */       return this.sdkName;
/*     */     }
/*     */     @Nullable
/*     */     public String getSdkVersion() {
/* 123 */       return this.sdkVersion;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\internal\ManifestVersionReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */