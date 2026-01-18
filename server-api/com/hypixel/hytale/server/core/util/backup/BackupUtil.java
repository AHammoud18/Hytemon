/*     */ package com.hypixel.hytale.server.core.util.backup;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.java.ManifestUtil;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.WorldSavingStatus;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.permissions.PermissionsModule;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.nio.file.attribute.FileTime;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.zip.CRC32;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BackupUtil
/*     */ {
/*     */   static void walkFileTreeAndZip(@Nonnull Path sourceDir, @Nonnull Path zipPath) throws IOException {
/*  31 */     ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipPath, new java.nio.file.OpenOption[0])); try {
/*  32 */       zipOutputStream.setMethod(0);
/*  33 */       zipOutputStream.setLevel(0);
/*     */       
/*  35 */       zipOutputStream.setComment("Automated backup by HytaleServer - Version: " + 
/*  36 */           ManifestUtil.getImplementationVersion() + ", Revision: " + 
/*  37 */           ManifestUtil.getImplementationRevisionId());
/*     */       
/*  39 */       Stream<Path> stream = Files.walk(sourceDir, new java.nio.file.FileVisitOption[0]); 
/*  40 */       try { List<Path> files = stream.filter(x$0 -> Files.isRegularFile(x$0, new java.nio.file.LinkOption[0])).toList();
/*  41 */         for (Path path : files) {
/*  42 */           long size = Files.size(path);
/*  43 */           CRC32 crc = new CRC32();
/*     */           
/*  45 */           InputStream inputStream = Files.newInputStream(path, new java.nio.file.OpenOption[0]); 
/*  46 */           try { byte[] buffer = new byte[16384];
/*     */             int len;
/*  48 */             while ((len = inputStream.read(buffer)) != -1) {
/*  49 */               crc.update(buffer, 0, len);
/*     */             }
/*  51 */             if (inputStream != null) inputStream.close();  } catch (Throwable throwable) { if (inputStream != null)
/*     */               try { inputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*  53 */            ZipEntry zipEntry = new ZipEntry(sourceDir.relativize(path).toString());
/*  54 */           zipEntry.setSize(size);
/*  55 */           zipEntry.setCompressedSize(size);
/*  56 */           zipEntry.setCrc(crc.getValue());
/*     */           
/*  58 */           zipOutputStream.putNextEntry(zipEntry);
/*  59 */           Files.copy(path, zipOutputStream);
/*  60 */           zipOutputStream.closeEntry();
/*     */         } 
/*  62 */         if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null)
/*  63 */           try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  zipOutputStream.close();
/*     */     } catch (Throwable throwable) {
/*     */       try {
/*     */         zipOutputStream.close();
/*     */       } catch (Throwable throwable1) {
/*     */         throwable.addSuppressed(throwable1);
/*     */       } 
/*     */       throw throwable;
/*  71 */     }  } static void broadcastBackupStatus(boolean isWorldSaving) { Universe.get().broadcastPacket((Packet)new WorldSavingStatus(isWorldSaving)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void broadcastBackupError(Throwable cause) {
/*  80 */     Message message = Message.translation("server.universe.backup.error").param("message", cause.getLocalizedMessage());
/*  81 */     Universe.get().getPlayers().forEach(player -> {
/*     */           boolean hasPermission = PermissionsModule.get().hasPermission(player.getUuid(), "hytale.status.backup.error");
/*     */           if (hasPermission) {
/*     */             player.sendMessage(message);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   static List<Path> findOldBackups(@Nonnull Path backupDirectory, int maxBackupCount) throws IOException {
/*  97 */     if (!backupDirectory.toFile().isDirectory()) {
/*  98 */       return null;
/*     */     }
/* 100 */     Stream<Path> files = Files.list(backupDirectory);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     try { List<Path> zipFiles = files.filter(p -> p.getFileName().toString().endsWith(".zip")).sorted(Comparator.comparing(p -> { try { return Files.<BasicFileAttributes>readAttributes(p, BasicFileAttributes.class, new java.nio.file.LinkOption[0]).creationTime(); } catch (IOException e) { return FileTime.fromMillis(0L); }  })).toList();
/* 111 */       if (zipFiles.size() > maxBackupCount)
/* 112 */       { List<Path> list = zipFiles.subList(0, zipFiles.size() - maxBackupCount);
/*     */         
/* 114 */         if (files != null) files.close();  return list; }  if (files != null) files.close();  } catch (Throwable throwable) { if (files != null)
/* 115 */         try { files.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\backup\BackupUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */