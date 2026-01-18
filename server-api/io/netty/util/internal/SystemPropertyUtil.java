/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
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
/*     */ public final class SystemPropertyUtil
/*     */ {
/*  31 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SystemPropertyUtil.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean contains(String key) {
/*  38 */     return (get(key) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String get(String key) {
/*  48 */     return get(key, null);
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
/*     */   public static String get(final String key, String def) {
/*  61 */     ObjectUtil.checkNonEmpty(key, "key");
/*     */     
/*  63 */     String value = null;
/*     */     try {
/*  65 */       if (System.getSecurityManager() == null) {
/*  66 */         value = System.getProperty(key);
/*     */       } else {
/*  68 */         value = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */             {
/*     */               public String run() {
/*  71 */                 return System.getProperty(key);
/*     */               }
/*     */             });
/*     */       } 
/*  75 */     } catch (SecurityException e) {
/*  76 */       logger.warn("Unable to retrieve a system property '{}'; default values will be used.", key, e);
/*     */     } 
/*     */     
/*  79 */     if (value == null) {
/*  80 */       return def;
/*     */     }
/*     */     
/*  83 */     return value;
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
/*     */   public static boolean getBoolean(String key, boolean def) {
/*  96 */     String value = get(key);
/*  97 */     if (value == null) {
/*  98 */       return def;
/*     */     }
/*     */     
/* 101 */     value = value.trim().toLowerCase();
/* 102 */     if (value.isEmpty()) {
/* 103 */       return def;
/*     */     }
/*     */     
/* 106 */     if ("true".equals(value) || "yes".equals(value) || "1".equals(value)) {
/* 107 */       return true;
/*     */     }
/*     */     
/* 110 */     if ("false".equals(value) || "no".equals(value) || "0".equals(value)) {
/* 111 */       return false;
/*     */     }
/*     */     
/* 114 */     logger.warn("Unable to parse the boolean system property '{}':{} - using the default value: {}", new Object[] { key, value, 
/*     */           
/* 116 */           Boolean.valueOf(def) });
/*     */ 
/*     */     
/* 119 */     return def;
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
/*     */   public static int getInt(String key, int def) {
/* 132 */     String value = get(key);
/* 133 */     if (value == null) {
/* 134 */       return def;
/*     */     }
/*     */     
/* 137 */     value = value.trim();
/*     */     try {
/* 139 */       return Integer.parseInt(value);
/* 140 */     } catch (Exception exception) {
/*     */ 
/*     */ 
/*     */       
/* 144 */       logger.warn("Unable to parse the integer system property '{}':{} - using the default value: {}", new Object[] { key, value, 
/*     */             
/* 146 */             Integer.valueOf(def) });
/*     */ 
/*     */       
/* 149 */       return def;
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
/*     */   public static long getLong(String key, long def) {
/* 162 */     String value = get(key);
/* 163 */     if (value == null) {
/* 164 */       return def;
/*     */     }
/*     */     
/* 167 */     value = value.trim();
/*     */     try {
/* 169 */       return Long.parseLong(value);
/* 170 */     } catch (Exception exception) {
/*     */ 
/*     */ 
/*     */       
/* 174 */       logger.warn("Unable to parse the long integer system property '{}':{} - using the default value: {}", new Object[] { key, value, 
/*     */             
/* 176 */             Long.valueOf(def) });
/*     */ 
/*     */       
/* 179 */       return def;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\SystemPropertyUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */