/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.logging.Logger;
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
/*     */ public final class RuntimeVersion
/*     */ {
/*     */   public enum RuntimeDomain
/*     */   {
/*  22 */     GOOGLE_INTERNAL,
/*  23 */     PUBLIC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  29 */   public static final RuntimeDomain OSS_DOMAIN = RuntimeDomain.PUBLIC;
/*     */   
/*     */   public static final int OSS_MAJOR = 4;
/*     */   public static final int OSS_MINOR = 33;
/*     */   public static final int OSS_PATCH = 0;
/*     */   public static final String OSS_SUFFIX = "";
/*  35 */   public static final RuntimeDomain DOMAIN = OSS_DOMAIN;
/*     */   
/*     */   public static final int MAJOR = 4;
/*     */   
/*     */   public static final int MINOR = 33;
/*     */   
/*     */   public static final int PATCH = 0;
/*     */   public static final String SUFFIX = "";
/*     */   private static final int MAX_WARNING_COUNT = 20;
/*  44 */   static int majorWarningLoggedCount = 0;
/*     */ 
/*     */   
/*  47 */   static int minorWarningLoggedCount = 0;
/*     */ 
/*     */   
/*     */   static boolean preleaseRuntimeWarningLogged = false;
/*     */   
/*  52 */   private static final String VERSION_STRING = versionString(4, 33, 0, "");
/*  53 */   private static final Logger logger = Logger.getLogger(RuntimeVersion.class.getName());
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
/*     */   public static void validateProtobufGencodeVersion(RuntimeDomain domain, int major, int minor, int patch, String suffix, String location) {
/*  72 */     validateProtobufGencodeVersionImpl(domain, major, minor, patch, suffix, location);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validateProtobufGencodeVersionImpl(RuntimeDomain domain, int major, int minor, int patch, String suffix, String location) {
/*  78 */     if (checkDisabled()) {
/*     */       return;
/*     */     }
/*     */     
/*  82 */     if (major < 0 || minor < 0 || patch < 0) {
/*  83 */       throw new ProtobufRuntimeVersionException("Invalid gencode version: " + 
/*  84 */           versionString(major, minor, patch, suffix));
/*     */     }
/*     */ 
/*     */     
/*  88 */     if (domain != DOMAIN) {
/*  89 */       throw new ProtobufRuntimeVersionException(
/*  90 */           String.format(Locale.US, "Detected mismatched Protobuf Gencode/Runtime domains when loading %s: gencode %s, runtime %s. Cross-domain usage of Protobuf is not supported.", new Object[] { location, domain, DOMAIN }));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     String gencodeVersionString = null;
/*     */     
/* 101 */     if (!"".isEmpty() && !preleaseRuntimeWarningLogged) {
/* 102 */       if (gencodeVersionString == null) {
/* 103 */         gencodeVersionString = versionString(major, minor, patch, suffix);
/*     */       }
/* 105 */       logger.warning(
/* 106 */           String.format(Locale.US, " Protobuf prelease version %s in use. This is not recommended for production use.\n You can ignore this message if you are deliberately testing a prerelease. Otherwise you should switch to a non-prerelease Protobuf version.", new Object[] { VERSION_STRING }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 113 */       preleaseRuntimeWarningLogged = true;
/*     */     } 
/*     */ 
/*     */     
/* 117 */     if (major == 4 && minor == 33 && patch == 0 && suffix.equals("")) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 122 */     if (major != 4) {
/* 123 */       if (major == 3 && majorWarningLoggedCount < 20) {
/* 124 */         gencodeVersionString = versionString(major, minor, patch, suffix);
/* 125 */         logger.warning(
/* 126 */             String.format(Locale.US, " Protobuf gencode version %s is exactly one major version older than the runtime version %s at %s. Please update the gencode to avoid compatibility violations in the next runtime release.", new Object[] { gencodeVersionString, VERSION_STRING, location }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 134 */         majorWarningLoggedCount++;
/*     */       } else {
/* 136 */         throw new ProtobufRuntimeVersionException(
/* 137 */             String.format(Locale.US, "Detected mismatched Protobuf Gencode/Runtime major versions when loading %s: gencode %s, runtime %s. Same major version is required.", new Object[] {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 142 */                 location, versionString(major, minor, patch, suffix), VERSION_STRING
/*     */               }));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 148 */     if (33 < minor || (minor == 33 && 0 < patch)) {
/* 149 */       if (gencodeVersionString == null) {
/* 150 */         gencodeVersionString = versionString(major, minor, patch, suffix);
/*     */       }
/* 152 */       throw new ProtobufRuntimeVersionException(
/* 153 */           String.format(Locale.US, "Detected incompatible Protobuf Gencode/Runtime versions when loading %s: gencode %s, runtime %s. Runtime version cannot be older than the linked gencode version.", new Object[] { location, gencodeVersionString, VERSION_STRING }));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     if (suffix.isEmpty() && "".isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 170 */     if (!suffix.isEmpty()) {
/* 171 */       if (gencodeVersionString == null) {
/* 172 */         gencodeVersionString = versionString(major, minor, patch, suffix);
/*     */       }
/* 174 */       throw new ProtobufRuntimeVersionException(
/* 175 */           String.format(Locale.US, "Detected mismatched Protobuf Gencode/Runtime version suffixes when loading %s: gencode %s, runtime %s. Prerelease gencode must be used with the same runtime.", new Object[] { location, gencodeVersionString, VERSION_STRING }));
/*     */     } 
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
/* 189 */     if (major == 4 && minor == 33 && patch == 0) {
/* 190 */       if (gencodeVersionString == null) {
/* 191 */         gencodeVersionString = versionString(major, minor, patch, suffix);
/*     */       }
/* 193 */       throw new ProtobufRuntimeVersionException(
/* 194 */           String.format(Locale.US, "Detected mismatched Protobuf Gencode/Runtime version suffixes when loading %s: gencode %s, runtime %s. Prelease runtimes must only be used with exact match gencode (including suffix) or non-prerelease gencode versions of a lower version.", new Object[] { location, gencodeVersionString, VERSION_STRING }));
/*     */     } 
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
/*     */   
/*     */   public static final class ProtobufRuntimeVersionException
/*     */     extends RuntimeException
/*     */   {
/*     */     public ProtobufRuntimeVersionException(String message) {
/* 212 */       super(message);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static String versionString(int major, int minor, int patch, String suffix) {
/* 218 */     return String.format(Locale.US, "%d.%d.%d%s", new Object[] { Integer.valueOf(major), Integer.valueOf(minor), Integer.valueOf(patch), suffix });
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean checkDisabled() {
/* 223 */     String disableFlag = System.getenv("TEMPORARILY_DISABLE_PROTOBUF_VERSION_CHECK");
/* 224 */     if (disableFlag != null && disableFlag.equals("true")) {
/* 225 */       return true;
/*     */     }
/*     */     
/* 228 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\RuntimeVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */