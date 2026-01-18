/*     */ package io.sentry.util;
/*     */ 
/*     */ import io.sentry.SentryIntegrationPackageStorage;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.SentryOptions;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class DebugMetaPropertiesApplier {
/*     */   @NotNull
/*  13 */   public static String DEBUG_META_PROPERTIES_FILENAME = "sentry-debug-meta.properties";
/*     */ 
/*     */   
/*     */   public static void apply(@NotNull SentryOptions options, @Nullable List<Properties> debugMetaProperties) {
/*  17 */     if (debugMetaProperties != null) {
/*  18 */       applyToOptions(options, debugMetaProperties);
/*  19 */       applyBuildTool(options, debugMetaProperties);
/*  20 */       applyDistributionOptions(options, debugMetaProperties);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void applyToOptions(@NotNull SentryOptions options, @Nullable List<Properties> debugMetaProperties) {
/*  26 */     if (debugMetaProperties != null) {
/*  27 */       applyBundleIds(options, debugMetaProperties);
/*  28 */       applyProguardUuid(options, debugMetaProperties);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void applyBundleIds(@NotNull SentryOptions options, @NotNull List<Properties> debugMetaProperties) {
/*  34 */     if (options.getBundleIds().isEmpty()) {
/*  35 */       for (Properties properties : debugMetaProperties) {
/*  36 */         String bundleIdStrings = properties.getProperty("io.sentry.bundle-ids");
/*  37 */         options.getLogger().log(SentryLevel.DEBUG, "Bundle IDs found: %s", new Object[] { bundleIdStrings });
/*  38 */         if (bundleIdStrings != null) {
/*  39 */           String[] bundleIds = bundleIdStrings.split(",", -1);
/*  40 */           for (String bundleId : bundleIds) {
/*  41 */             options.addBundleId(bundleId);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void applyProguardUuid(@NotNull SentryOptions options, @NotNull List<Properties> debugMetaProperties) {
/*  50 */     if (options.getProguardUuid() == null) {
/*  51 */       for (Properties properties : debugMetaProperties) {
/*  52 */         String proguardUuid = getProguardUuid(properties);
/*  53 */         if (proguardUuid != null) {
/*  54 */           options.getLogger().log(SentryLevel.DEBUG, "Proguard UUID found: %s", new Object[] { proguardUuid });
/*  55 */           options.setProguardUuid(proguardUuid);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void applyBuildTool(@NotNull SentryOptions options, @NotNull List<Properties> debugMetaProperties) {
/*  64 */     for (Properties properties : debugMetaProperties) {
/*  65 */       String buildTool = getBuildTool(properties);
/*  66 */       if (buildTool != null) {
/*  67 */         String buildToolVersion = getBuildToolVersion(properties);
/*  68 */         if (buildToolVersion == null) {
/*  69 */           buildToolVersion = "unknown";
/*     */         }
/*  71 */         options
/*  72 */           .getLogger()
/*  73 */           .log(SentryLevel.DEBUG, "Build tool found: %s, version %s", new Object[] { buildTool, buildToolVersion });
/*     */         
/*  75 */         SentryIntegrationPackageStorage.getInstance().addPackage(buildTool, buildToolVersion);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   @Nullable
/*     */   public static String getProguardUuid(@NotNull Properties debugMetaProperties) {
/*  82 */     return debugMetaProperties.getProperty("io.sentry.ProguardUuids");
/*     */   }
/*     */   @Nullable
/*     */   public static String getBuildTool(@NotNull Properties debugMetaProperties) {
/*  86 */     return debugMetaProperties.getProperty("io.sentry.build-tool");
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static String getBuildToolVersion(@NotNull Properties debugMetaProperties) {
/*  91 */     return debugMetaProperties.getProperty("io.sentry.build-tool-version");
/*     */   }
/*     */ 
/*     */   
/*     */   private static void applyDistributionOptions(@NotNull SentryOptions options, @NotNull List<Properties> debugMetaProperties) {
/*  96 */     for (Properties properties : debugMetaProperties) {
/*  97 */       String orgSlug = getDistributionOrgSlug(properties);
/*  98 */       String projectSlug = getDistributionProjectSlug(properties);
/*  99 */       String orgAuthToken = getDistributionAuthToken(properties);
/* 100 */       String buildConfiguration = getDistributionBuildConfiguration(properties);
/*     */       
/* 102 */       if (orgSlug != null || projectSlug != null || orgAuthToken != null || buildConfiguration != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 107 */         SentryOptions.DistributionOptions distributionOptions = options.getDistribution();
/*     */         
/* 109 */         if (orgSlug != null && !orgSlug.isEmpty() && distributionOptions.orgSlug.isEmpty()) {
/* 110 */           options.getLogger().log(SentryLevel.DEBUG, "Distribution org slug found: %s", new Object[] { orgSlug });
/* 111 */           distributionOptions.orgSlug = orgSlug;
/*     */         } 
/*     */         
/* 114 */         if (projectSlug != null && 
/* 115 */           !projectSlug.isEmpty() && distributionOptions.projectSlug
/* 116 */           .isEmpty()) {
/* 117 */           options
/* 118 */             .getLogger()
/* 119 */             .log(SentryLevel.DEBUG, "Distribution project slug found: %s", new Object[] { projectSlug });
/* 120 */           distributionOptions.projectSlug = projectSlug;
/*     */         } 
/*     */         
/* 123 */         if (orgAuthToken != null && 
/* 124 */           !orgAuthToken.isEmpty() && distributionOptions.orgAuthToken
/* 125 */           .isEmpty()) {
/* 126 */           options.getLogger().log(SentryLevel.DEBUG, "Distribution org auth token found", new Object[0]);
/* 127 */           distributionOptions.orgAuthToken = orgAuthToken;
/*     */         } 
/*     */         
/* 130 */         if (buildConfiguration != null && 
/* 131 */           !buildConfiguration.isEmpty() && distributionOptions.buildConfiguration == null) {
/*     */           
/* 133 */           options
/* 134 */             .getLogger()
/* 135 */             .log(SentryLevel.DEBUG, "Distribution build configuration found: %s", new Object[] { buildConfiguration });
/*     */ 
/*     */ 
/*     */           
/* 139 */           distributionOptions.buildConfiguration = buildConfiguration;
/*     */         } 
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static String getDistributionOrgSlug(@NotNull Properties debugMetaProperties) {
/* 151 */     return debugMetaProperties.getProperty("io.sentry.distribution.org-slug");
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static String getDistributionProjectSlug(@NotNull Properties debugMetaProperties) {
/* 156 */     return debugMetaProperties.getProperty("io.sentry.distribution.project-slug");
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static String getDistributionAuthToken(@NotNull Properties debugMetaProperties) {
/* 161 */     return debugMetaProperties.getProperty("io.sentry.distribution.auth-token");
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static String getDistributionBuildConfiguration(@NotNull Properties debugMetaProperties) {
/* 166 */     return debugMetaProperties.getProperty("io.sentry.distribution.build-configuration");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\DebugMetaPropertiesApplier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */