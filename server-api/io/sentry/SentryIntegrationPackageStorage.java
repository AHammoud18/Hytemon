/*     */ package io.sentry;
/*     */ import io.sentry.protocol.SentryPackage;
/*     */ import io.sentry.util.AutoClosableReentrantLock;
/*     */ import io.sentry.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CopyOnWriteArraySet;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class SentryIntegrationPackageStorage {
/*     */   @Nullable
/*     */   private static volatile SentryIntegrationPackageStorage INSTANCE;
/*     */   @NotNull
/*  16 */   private static final AutoClosableReentrantLock staticLock = new AutoClosableReentrantLock();
/*     */   @Nullable
/*  18 */   private static volatile Boolean mixedVersionsDetected = null; @NotNull
/*  19 */   private static final AutoClosableReentrantLock mixedVersionsLock = new AutoClosableReentrantLock();
/*     */   
/*     */   @NotNull
/*     */   public static SentryIntegrationPackageStorage getInstance() {
/*  23 */     if (INSTANCE == null) {
/*  24 */       ISentryLifecycleToken ignored = staticLock.acquire(); 
/*  25 */       try { if (INSTANCE == null) {
/*  26 */           INSTANCE = new SentryIntegrationPackageStorage();
/*     */         }
/*  28 */         if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*     */           try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*     */     
/*  31 */     }  return INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   private final Set<String> integrations = new CopyOnWriteArraySet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   private final Set<SentryPackage> packages = new CopyOnWriteArraySet<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public void addIntegration(@NotNull String integration) {
/*  56 */     Objects.requireNonNull(integration, "integration is required.");
/*  57 */     this.integrations.add(integration);
/*     */   }
/*     */   @NotNull
/*     */   public Set<String> getIntegrations() {
/*  61 */     return this.integrations;
/*     */   }
/*     */   
/*     */   public void addPackage(@NotNull String name, @NotNull String version) {
/*  65 */     Objects.requireNonNull(name, "name is required.");
/*  66 */     Objects.requireNonNull(version, "version is required.");
/*     */     
/*  68 */     SentryPackage newPackage = new SentryPackage(name, version);
/*  69 */     this.packages.add(newPackage);
/*  70 */     ISentryLifecycleToken ignored = mixedVersionsLock.acquire(); 
/*  71 */     try { mixedVersionsDetected = null;
/*  72 */       if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*  76 */      } @NotNull public Set<SentryPackage> getPackages() { return this.packages; }
/*     */ 
/*     */   
/*     */   public boolean checkForMixedVersions(@NotNull ILogger logger) {
/*  80 */     Boolean mixedVersionsDetectedBefore = mixedVersionsDetected;
/*  81 */     if (mixedVersionsDetectedBefore != null) {
/*  82 */       return mixedVersionsDetectedBefore.booleanValue();
/*     */     }
/*  84 */     ISentryLifecycleToken ignored = mixedVersionsLock.acquire(); 
/*  85 */     try { String sdkVersion = "8.29.0";
/*  86 */       boolean mixedVersionsDetectedThisCheck = false;
/*     */       
/*  88 */       for (SentryPackage pkg : this.packages) {
/*  89 */         if (pkg.getName().startsWith("maven:io.sentry:") && 
/*  90 */           !"8.29.0".equalsIgnoreCase(pkg.getVersion())) {
/*  91 */           logger.log(SentryLevel.ERROR, "The Sentry SDK has been configured with mixed versions. Expected %s to match core SDK version %s but was %s", new Object[] { pkg
/*     */ 
/*     */                 
/*  94 */                 .getName(), "8.29.0", pkg
/*     */                 
/*  96 */                 .getVersion() });
/*  97 */           mixedVersionsDetectedThisCheck = true;
/*     */         } 
/*     */       } 
/*     */       
/* 101 */       if (mixedVersionsDetectedThisCheck) {
/* 102 */         logger.log(SentryLevel.ERROR, "^^^^^^^^^^^^^^^^^^^^^^^^^^^^", new Object[0]);
/* 103 */         logger.log(SentryLevel.ERROR, "^^^^^^^^^^^^^^^^^^^^^^^^^^^^", new Object[0]);
/* 104 */         logger.log(SentryLevel.ERROR, "^^^^^^^^^^^^^^^^^^^^^^^^^^^^", new Object[0]);
/* 105 */         logger.log(SentryLevel.ERROR, "^^^^^^^^^^^^^^^^^^^^^^^^^^^^", new Object[0]);
/*     */       } 
/* 107 */       mixedVersionsDetected = Boolean.valueOf(mixedVersionsDetectedThisCheck);
/* 108 */       boolean bool1 = mixedVersionsDetectedThisCheck;
/* 109 */       if (ignored != null) ignored.close();  return bool1; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 114 */      } @TestOnly public void clearStorage() { this.integrations.clear();
/* 115 */     this.packages.clear(); }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryIntegrationPackageStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */