/*    */ package com.hypixel.hytale.server.core.util.io;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Options;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.concurrent.locks.ReadWriteLock;
/*    */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class BlockingDiskFile {
/* 19 */   protected final ReadWriteLock fileLock = new ReentrantReadWriteLock();
/*    */   
/*    */   protected final Path path;
/*    */   
/*    */   public BlockingDiskFile(Path path) {
/* 24 */     this.path = path;
/*    */   }
/*    */   
/*    */   protected abstract void read(BufferedReader paramBufferedReader) throws IOException;
/*    */   
/*    */   protected abstract void write(BufferedWriter paramBufferedWriter) throws IOException;
/*    */   
/*    */   protected abstract void create(BufferedWriter paramBufferedWriter) throws IOException;
/*    */   
/*    */   public void syncLoad() {
/* 34 */     this.fileLock.writeLock().lock();
/*    */     try {
/* 36 */       File file = toLocalFile();
/*    */ 
/*    */       
/* 39 */       try { if (!file.exists()) {
/*    */           
/* 41 */           if (Options.getOptionSet().has(Options.BARE)) {
/*    */             byte[] bytes;
/* 43 */             ByteArrayOutputStream out = new ByteArrayOutputStream(); try { BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out)); 
/* 44 */               try { create(bufferedWriter);
/* 45 */                 bytes = out.toByteArray();
/* 46 */                 bufferedWriter.close(); } catch (Throwable throwable) { try { bufferedWriter.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  out.close(); } catch (Throwable throwable) { try { out.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 47 */              BufferedReader buf = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes))); 
/* 48 */             try { read(buf);
/* 49 */               buf.close(); } catch (Throwable throwable) { try { buf.close(); }
/*    */               catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */                throw throwable; }
/*    */              return;
/* 53 */           }  BufferedWriter fileWriter = Files.newBufferedWriter(file.toPath(), new java.nio.file.OpenOption[0]); 
/* 54 */           try { create(fileWriter);
/* 55 */             if (fileWriter != null) fileWriter.close();  } catch (Throwable throwable) { if (fileWriter != null)
/*    */               try { fileWriter.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*    */         
/* 58 */         }  BufferedReader fileReader = Files.newBufferedReader(file.toPath()); 
/* 59 */         try { read(fileReader);
/* 60 */           if (fileReader != null) fileReader.close();  } catch (Throwable throwable) { if (fileReader != null)
/* 61 */             try { fileReader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception ex)
/* 62 */       { byte[] bytes; throw new RuntimeException("Failed to syncLoad() " + file.getAbsolutePath(), bytes); }
/*    */     
/*    */     } finally {
/* 65 */       this.fileLock.writeLock().unlock();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void syncSave() {
/* 70 */     File file = null;
/* 71 */     this.fileLock.readLock().lock();
/*    */     
/* 73 */     try { file = toLocalFile();
/* 74 */       BufferedWriter fileWriter = Files.newBufferedWriter(file.toPath(), new java.nio.file.OpenOption[0]); 
/* 75 */       try { write(fileWriter);
/* 76 */         if (fileWriter != null) fileWriter.close();  } catch (Throwable throwable) { if (fileWriter != null)
/* 77 */           try { fileWriter.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception ex)
/* 78 */     { throw new RuntimeException("Failed to syncSave() " + ((file != null) ? file.getAbsolutePath() : null), ex); }
/*    */     finally
/* 80 */     { this.fileLock.readLock().unlock(); }
/*    */   
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected File toLocalFile() {
/* 86 */     return this.path.toFile();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\io\BlockingDiskFile.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */