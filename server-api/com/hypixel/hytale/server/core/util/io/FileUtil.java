/*    */ package com.hypixel.hytale.server.core.util.io;
/*    */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.nio.file.CopyOption;
/*    */ import java.nio.file.FileVisitOption;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.StandardOpenOption;
/*    */ import java.util.Set;
/*    */ import java.util.regex.Pattern;
/*    */ import java.util.stream.Stream;
/*    */ import java.util.zip.ZipEntry;
/*    */ import java.util.zip.ZipInputStream;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class FileUtil {
/* 18 */   public static final Set<OpenOption> DEFAULT_WRITE_OPTIONS = Set.of(StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
/* 19 */   public static final Set<FileVisitOption> DEFAULT_WALK_TREE_OPTIONS_SET = Set.of();
/* 20 */   public static final FileVisitOption[] DEFAULT_WALK_TREE_OPTIONS_ARRAY = new FileVisitOption[0];
/*    */ 
/*    */   
/* 23 */   public static final Pattern INVALID_FILENAME_CHARACTERS = Pattern.compile("[<>:\"|?*/\\\\]");
/*    */   
/*    */   public static void unzipFile(@Nonnull Path path, @Nonnull byte[] buffer, @Nonnull ZipInputStream zipStream, @Nonnull ZipEntry zipEntry, @Nonnull String name) throws IOException {
/* 26 */     Path filePath = path.resolve(name);
/*    */     
/* 28 */     if (!filePath.toAbsolutePath().startsWith(path)) {
/* 29 */       throw new ZipException("Entry is outside of the target dir: " + zipEntry.getName());
/*    */     }
/*    */     
/* 32 */     if (zipEntry.isDirectory()) {
/* 33 */       Files.createDirectory(filePath, (FileAttribute<?>[])new FileAttribute[0]);
/*    */     } else {
/* 35 */       OutputStream stream = Files.newOutputStream(filePath, new OpenOption[0]); 
/*    */       try { int len;
/* 37 */         while ((len = zipStream.read(buffer)) > 0) {
/* 38 */           stream.write(buffer, 0, len);
/*    */         }
/* 40 */         if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null)
/*    */           try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/* 42 */     }  zipStream.closeEntry();
/*    */   }
/*    */   
/*    */   public static void copyDirectory(@Nonnull Path origin, @Nonnull Path destination) throws IOException {
/* 46 */     Stream<Path> paths = Files.walk(origin, new FileVisitOption[0]); 
/* 47 */     try { paths.forEach(originSubPath -> {
/*    */             try {
/*    */               Path relative = origin.relativize(originSubPath);
/*    */               Path destinationSubPath = destination.resolve(relative);
/*    */               Files.copy(originSubPath, destinationSubPath, new CopyOption[0]);
/* 52 */             } catch (Throwable t) {
/*    */               throw new RuntimeException("Error copying path", t);
/*    */             } 
/*    */           });
/* 56 */       if (paths != null) paths.close();  } catch (Throwable throwable) { if (paths != null)
/*    */         try { paths.close(); }
/*    */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */           throw throwable; }
/* 60 */      } public static void moveDirectoryContents(@Nonnull Path origin, @Nonnull Path destination, CopyOption... options) throws IOException { Stream<Path> paths = Files.walk(origin, new FileVisitOption[0]); 
/* 61 */     try { paths.forEach(originSubPath -> {
/*    */             if (originSubPath.equals(origin))
/*    */               return;  try {
/*    */               Path relative = origin.relativize(originSubPath);
/*    */               Path destinationSubPath = destination.resolve(relative);
/*    */               Files.move(originSubPath, destinationSubPath, options);
/* 67 */             } catch (Throwable t) {
/*    */               throw new RuntimeException("Error moving path", t);
/*    */             } 
/*    */           });
/* 71 */       if (paths != null) paths.close();  } catch (Throwable throwable) { if (paths != null)
/*    */         try { paths.close(); }
/*    */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */           throw throwable; }
/* 75 */      } public static void deleteDirectory(@Nonnull Path path) throws IOException { Stream<Path> stream = Files.walk(path, new FileVisitOption[0]); try {
/* 76 */       stream.sorted(Comparator.reverseOrder())
/* 77 */         .forEach(SneakyThrow.sneakyConsumer(Files::delete));
/* 78 */       if (stream != null) stream.close(); 
/*    */     } catch (Throwable throwable) {
/*    */       if (stream != null)
/*    */         try {
/*    */           stream.close();
/*    */         } catch (Throwable throwable1) {
/*    */           throwable.addSuppressed(throwable1);
/*    */         }  
/*    */       throw throwable;
/*    */     }  }
/*    */ 
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\io\FileUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */