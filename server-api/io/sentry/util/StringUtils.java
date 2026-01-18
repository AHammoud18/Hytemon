/*     */ package io.sentry.util;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.SentryLevel;
/*     */ import java.math.BigInteger;
/*     */ import java.nio.charset.Charset;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.text.CharacterIterator;
/*     */ import java.text.StringCharacterIterator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.regex.Pattern;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class StringUtils
/*     */ {
/*  21 */   private static final Charset UTF_8 = Charset.forName("UTF-8");
/*     */   public static final String PROPER_NIL_UUID = "00000000-0000-0000-0000-000000000000";
/*     */   private static final String CORRUPTED_NIL_UUID = "0000-0000";
/*     */   @NotNull
/*  25 */   private static final Pattern PATTERN_WORD_SNAKE_CASE = Pattern.compile("[\\W_]+");
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static String getStringAfterDot(@Nullable String str) {
/*  30 */     if (str != null) {
/*  31 */       int lastDotIndex = str.lastIndexOf(".");
/*  32 */       if (lastDotIndex >= 0 && str.length() > lastDotIndex + 1) {
/*  33 */         return str.substring(lastDotIndex + 1);
/*     */       }
/*  35 */       return str;
/*     */     } 
/*     */     
/*  38 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static String capitalize(@Nullable String str) {
/*  48 */     if (str == null || str.isEmpty()) {
/*  49 */       return str;
/*     */     }
/*     */     
/*  52 */     return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1).toLowerCase(Locale.ROOT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static String camelCase(@Nullable String str) {
/*  62 */     if (str == null || str.isEmpty()) {
/*  63 */       return str;
/*     */     }
/*     */     
/*  66 */     String[] words = PATTERN_WORD_SNAKE_CASE.split(str, -1);
/*  67 */     StringBuilder builder = new StringBuilder();
/*  68 */     for (String w : words) {
/*  69 */       builder.append(capitalize(w));
/*     */     }
/*  71 */     return builder.toString();
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
/*     */   @Nullable
/*     */   public static String removeSurrounding(@Nullable String str, @Nullable String delimiter) {
/*  84 */     if (str != null && delimiter != null && str.startsWith(delimiter) && str.endsWith(delimiter)) {
/*  85 */       return str.substring(delimiter.length(), str.length() - delimiter.length());
/*     */     }
/*  87 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static String byteCountToString(long bytes) {
/*  98 */     if (-1000L < bytes && bytes < 1000L) {
/*  99 */       return bytes + " B";
/*     */     }
/* 101 */     CharacterIterator ci = new StringCharacterIterator("kMGTPE");
/* 102 */     while (bytes <= -999950L || bytes >= 999950L) {
/* 103 */       bytes /= 1000L;
/* 104 */       ci.next();
/*     */     } 
/* 106 */     return String.format(Locale.ROOT, "%.1f %cB", new Object[] { Double.valueOf(bytes / 1000.0D), Character.valueOf(ci.current()) });
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
/*     */   @Nullable
/*     */   public static String calculateStringHash(@Nullable String str, @NotNull ILogger logger) {
/* 121 */     if (str == null || str.isEmpty()) {
/* 122 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 127 */       MessageDigest md = MessageDigest.getInstance("SHA-1");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 132 */       byte[] messageDigest = md.digest(str.getBytes(UTF_8));
/*     */ 
/*     */       
/* 135 */       BigInteger no = new BigInteger(1, messageDigest);
/*     */ 
/*     */       
/* 138 */       StringBuilder stringBuilder = new StringBuilder(no.toString(16));
/*     */ 
/*     */       
/* 141 */       return stringBuilder.toString();
/*     */ 
/*     */     
/*     */     }
/* 145 */     catch (NoSuchAlgorithmException e) {
/* 146 */       logger.log(SentryLevel.INFO, "SHA-1 isn't available to calculate the hash.", e);
/* 147 */     } catch (Throwable e) {
/* 148 */       logger.log(SentryLevel.INFO, "string: %s could not calculate its hash", new Object[] { e, str });
/*     */     } 
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int countOf(@NotNull String str, char character) {
/* 161 */     int count = 0;
/* 162 */     for (int i = 0; i < str.length(); i++) {
/* 163 */       if (str.charAt(i) == character) {
/* 164 */         count++;
/*     */       }
/*     */     } 
/* 167 */     return count;
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
/*     */   public static String normalizeUUID(@NotNull String uuidString) {
/* 179 */     if (uuidString.equals("0000-0000")) {
/* 180 */       return "00000000-0000-0000-0000-000000000000";
/*     */     }
/* 182 */     return uuidString;
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
/*     */   public static String join(@NotNull CharSequence delimiter, @NotNull Iterable<? extends CharSequence> elements) {
/* 195 */     StringBuilder stringBuilder = new StringBuilder();
/* 196 */     Iterator<? extends CharSequence> iterator = elements.iterator();
/*     */     
/* 198 */     if (iterator.hasNext()) {
/* 199 */       stringBuilder.append(iterator.next());
/* 200 */       while (iterator.hasNext()) {
/* 201 */         stringBuilder.append(delimiter);
/* 202 */         stringBuilder.append(iterator.next());
/*     */       } 
/*     */     } 
/*     */     
/* 206 */     return stringBuilder.toString();
/*     */   }
/*     */   @Nullable
/*     */   public static String toString(@Nullable Object object) {
/* 210 */     if (object == null) {
/* 211 */       return null;
/*     */     }
/*     */     
/* 214 */     return object.toString();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public static String removePrefix(@Nullable String string, @NotNull String prefix) {
/* 219 */     if (string == null) {
/* 220 */       return "";
/*     */     }
/* 222 */     int index = string.indexOf(prefix);
/* 223 */     if (index == 0) {
/* 224 */       return string.substring(prefix.length());
/*     */     }
/* 226 */     return string;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static String substringBefore(@Nullable String string, @NotNull String separator) {
/* 232 */     if (string == null) {
/* 233 */       return "";
/*     */     }
/* 235 */     int index = string.indexOf(separator);
/* 236 */     if (index >= 0) {
/* 237 */       return string.substring(0, index);
/*     */     }
/* 239 */     return string;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\StringUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */