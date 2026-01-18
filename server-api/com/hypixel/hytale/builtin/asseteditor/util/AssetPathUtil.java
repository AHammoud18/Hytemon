/*     */ package com.hypixel.hytale.builtin.asseteditor.util;
/*     */ 
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AssetPathUtil
/*     */ {
/*     */   public static final String UNIX_FILE_SEPARATOR = "/";
/*     */   public static final String FILE_EXTENSION_JSON = ".json";
/*     */   public static final String DIR_SERVER = "Server";
/*     */   public static final String DIR_COMMON = "Common";
/*  21 */   public static final Path PATH_DIR_COMMON = Paths.get("Common", new String[0]);
/*  22 */   public static final Path PATH_DIR_SERVER = Paths.get("Server", new String[0]);
/*     */   
/*  24 */   public static final Path EMPTY_PATH = Path.of("", new String[0]);
/*     */   
/*  26 */   private static final Pattern INVALID_FILENAME_CHAR_REGEX = Pattern.compile("[<>:\"|?*/\\\\]");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   private static final String[] RESERVED_NAMES = new String[] { "CON", "PRN", "AUX", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9" };
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
/*     */   public static boolean isInvalidFileName(@Nonnull Path path) {
/*  45 */     String fileName = path.getFileName().toString();
/*  46 */     if (fileName.isEmpty()) return true; 
/*  47 */     if (fileName.charAt(fileName.length() - 1) == '.') return true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  52 */     for (int i = 0; i < fileName.length(); i += Character.charCount(codePoint)) {
/*  53 */       int codePoint = fileName.codePointAt(i);
/*  54 */       if (codePoint < 31) return true;
/*     */ 
/*     */       
/*  57 */       switch (codePoint) {
/*     */         case 34:
/*     */         case 42:
/*     */         case 58:
/*     */         case 60:
/*     */         case 62:
/*     */         case 63:
/*     */         case 124:
/*  65 */           return true;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/*  71 */     int pos = fileName.indexOf('.');
/*     */ 
/*     */     
/*  74 */     if (pos == 0) return false;
/*     */ 
/*     */     
/*  77 */     String baseFileName = (pos < 0) ? fileName : fileName.substring(0, pos);
/*     */ 
/*     */     
/*  80 */     for (String str : RESERVED_NAMES) {
/*  81 */       if (str.equals(baseFileName)) return true;
/*     */     
/*     */     } 
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removeInvalidFileNameChars(String name) {
/*  93 */     return INVALID_FILENAME_CHAR_REGEX.matcher(name).replaceAll("");
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static String getIdFromPath(@Nonnull Path path) {
/*  98 */     String fileName = path.getFileName().toString();
/*     */     
/* 100 */     int extensionIndex = fileName.lastIndexOf('.');
/* 101 */     if (extensionIndex == -1) return fileName;
/*     */     
/* 103 */     return fileName.substring(0, extensionIndex);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\assetedito\\util\AssetPathUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */