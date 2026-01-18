/*     */ package com.hypixel.hytale.common.util;
/*     */ 
/*     */ import java.net.URL;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class PathUtil
/*     */ {
/*  15 */   private static final Pattern PATH_PATTERN = Pattern.compile("[\\\\/]");
/*     */   
/*     */   @Nonnull
/*     */   public static Path getParent(@Nonnull Path path) {
/*  19 */     if (path.isAbsolute()) return path.getParent().normalize(); 
/*  20 */     Path parentAbsolute = path.toAbsolutePath().getParent();
/*  21 */     Path parent = path.resolve(relativize(path, parentAbsolute));
/*  22 */     return parent.normalize();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Path relativize(@Nonnull Path pathA, @Nonnull Path pathB) {
/*  27 */     Path absolutePathA = pathA.toAbsolutePath();
/*  28 */     Path absolutePathB = pathB.toAbsolutePath();
/*     */     
/*  30 */     if (Objects.equals(absolutePathA.getRoot(), absolutePathB.getRoot())) {
/*  31 */       return absolutePathA.normalize().relativize(absolutePathB.normalize()).normalize();
/*     */     }
/*     */     
/*  34 */     return absolutePathB.normalize();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Path relativizePretty(@Nonnull Path pathA, @Nonnull Path pathB) {
/*  39 */     Path absolutePathA = pathA.toAbsolutePath().normalize();
/*  40 */     Path absolutePathB = pathB.toAbsolutePath().normalize();
/*     */     
/*  42 */     Path absoluteUserHome = getUserHome().toAbsolutePath();
/*  43 */     if (Objects.equals(absoluteUserHome.getRoot(), absolutePathB.getRoot())) {
/*  44 */       Path relativizedHome = absoluteUserHome.relativize(absolutePathB).normalize();
/*  45 */       if (Objects.equals(absolutePathA.getRoot(), absolutePathB.getRoot())) {
/*  46 */         Path relativized = absolutePathA.relativize(absolutePathB).normalize();
/*  47 */         if (relativizedHome.getNameCount() < relativized.getNameCount())
/*  48 */           return Paths.get("~", new String[0]).resolve(relativizedHome); 
/*  49 */         return relativized;
/*     */       } 
/*  51 */       if (relativizedHome.getNameCount() < absolutePathB.getNameCount()) return Paths.get("~", new String[0]).resolve(relativizedHome); 
/*  52 */       return absolutePathB;
/*     */     } 
/*  54 */     if (Objects.equals(absolutePathA.getRoot(), absolutePathB.getRoot())) {
/*  55 */       return absolutePathA.relativize(absolutePathB).normalize();
/*     */     }
/*  57 */     return absolutePathB;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Path get(@Nonnull String path) {
/*  63 */     return get(Paths.get(path, new String[0]));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Path get(@Nonnull Path path) {
/*  68 */     return (path.toString().charAt(0) == '~') ? getUserHome().resolve(path.subpath(1, path.getNameCount())).normalize() : path.normalize();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Path getUserHome() {
/*  73 */     return Paths.get(System.getProperty("user.home"), new String[0]);
/*     */   }
/*     */   
/*     */   public static String getFileName(@Nonnull URL extUrl) {
/*  77 */     String[] pathContents = PATH_PATTERN.split(extUrl.getPath());
/*  78 */     String fileName = pathContents[pathContents.length - 1];
/*  79 */     if (fileName.isEmpty() && pathContents.length > 1) return pathContents[pathContents.length - 2]; 
/*  80 */     return fileName;
/*     */   }
/*     */   
/*     */   public static boolean isChildOf(@Nonnull Path parent, @Nonnull Path child) {
/*  84 */     return child.toAbsolutePath().normalize().startsWith(parent.toAbsolutePath().normalize());
/*     */   }
/*     */   
/*     */   public static void forEachParent(@Nonnull Path path, @Nullable Path limit, @Nonnull Consumer<Path> consumer) {
/*  88 */     Path parent = path.toAbsolutePath();
/*  89 */     if (Files.isRegularFile(parent, new java.nio.file.LinkOption[0])) parent = parent.getParent(); 
/*  90 */     if (parent == null)
/*     */       return;  do {
/*  92 */       consumer.accept(parent);
/*  93 */     } while ((parent = parent.getParent()) != null && (limit == null || isChildOf(limit, parent)));
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
/*     */   public static String getFileExtension(@Nonnull Path path) {
/* 105 */     String fileName = path.getFileName().toString();
/* 106 */     int index = fileName.lastIndexOf('.');
/* 107 */     if (index == -1) return ""; 
/* 108 */     return fileName.substring(index);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static String toUnixPathString(@Nonnull Path path) {
/* 114 */     if ("\\".equals(path.getFileSystem().getSeparator())) {
/* 115 */       return path.toString().replace("\\", "/");
/*     */     }
/* 117 */     return path.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\commo\\util\PathUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */