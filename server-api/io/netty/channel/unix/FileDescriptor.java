/*     */ package io.netty.channel.unix;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileDescriptor
/*     */ {
/*  38 */   private static final AtomicIntegerFieldUpdater<FileDescriptor> stateUpdater = AtomicIntegerFieldUpdater.newUpdater(FileDescriptor.class, "state");
/*     */   
/*     */   private static final int STATE_CLOSED_MASK = 1;
/*     */   
/*     */   private static final int STATE_INPUT_SHUTDOWN_MASK = 2;
/*     */   
/*     */   private static final int STATE_OUTPUT_SHUTDOWN_MASK = 4;
/*     */   
/*     */   private static final int STATE_ALL_MASK = 7;
/*     */   
/*     */   volatile int state;
/*     */   
/*     */   final int fd;
/*     */ 
/*     */   
/*     */   public FileDescriptor(int fd) {
/*  54 */     ObjectUtil.checkPositiveOrZero(fd, "fd");
/*  55 */     this.fd = fd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int intValue() {
/*  62 */     return this.fd;
/*     */   }
/*     */   
/*     */   protected boolean markClosed() {
/*     */     while (true) {
/*  67 */       int state = this.state;
/*  68 */       if (isClosed(state)) {
/*  69 */         return false;
/*     */       }
/*     */       
/*  72 */       if (casState(state, state | 0x7)) {
/*  73 */         return true;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  82 */     if (markClosed()) {
/*  83 */       int res = close(this.fd);
/*  84 */       if (res < 0) {
/*  85 */         throw Errors.newIOException("close", res);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/*  94 */     return !isClosed(this.state);
/*     */   }
/*     */   
/*     */   public final int write(ByteBuffer buf, int pos, int limit) throws IOException {
/*  98 */     int res = write(this.fd, buf, pos, limit);
/*  99 */     if (res >= 0) {
/* 100 */       return res;
/*     */     }
/* 102 */     return Errors.ioResult("write", res);
/*     */   }
/*     */   
/*     */   public final int writeAddress(long address, int pos, int limit) throws IOException {
/* 106 */     int res = writeAddress(this.fd, address, pos, limit);
/* 107 */     if (res >= 0) {
/* 108 */       return res;
/*     */     }
/* 110 */     return Errors.ioResult("writeAddress", res);
/*     */   }
/*     */   
/*     */   public final long writev(ByteBuffer[] buffers, int offset, int length, long maxBytesToWrite) throws IOException {
/* 114 */     long res = writev(this.fd, buffers, offset, Math.min(Limits.IOV_MAX, length), maxBytesToWrite);
/* 115 */     if (res >= 0L) {
/* 116 */       return res;
/*     */     }
/* 118 */     return Errors.ioResult("writev", (int)res);
/*     */   }
/*     */   
/*     */   public final long writevAddresses(long memoryAddress, int length) throws IOException {
/* 122 */     long res = writevAddresses(this.fd, memoryAddress, length);
/* 123 */     if (res >= 0L) {
/* 124 */       return res;
/*     */     }
/* 126 */     return Errors.ioResult("writevAddresses", (int)res);
/*     */   }
/*     */   
/*     */   public final int read(ByteBuffer buf, int pos, int limit) throws IOException {
/* 130 */     int res = read(this.fd, buf, pos, limit);
/* 131 */     if (res > 0) {
/* 132 */       return res;
/*     */     }
/* 134 */     if (res == 0) {
/* 135 */       return -1;
/*     */     }
/* 137 */     return Errors.ioResult("read", res);
/*     */   }
/*     */   
/*     */   public final int readAddress(long address, int pos, int limit) throws IOException {
/* 141 */     int res = readAddress(this.fd, address, pos, limit);
/* 142 */     if (res > 0) {
/* 143 */       return res;
/*     */     }
/* 145 */     if (res == 0) {
/* 146 */       return -1;
/*     */     }
/* 148 */     return Errors.ioResult("readAddress", res);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 153 */     return "FileDescriptor{fd=" + this.fd + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 160 */     if (this == o) {
/* 161 */       return true;
/*     */     }
/* 163 */     if (!(o instanceof FileDescriptor)) {
/* 164 */       return false;
/*     */     }
/*     */     
/* 167 */     return (this.fd == ((FileDescriptor)o).fd);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 172 */     return this.fd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FileDescriptor from(String path) throws IOException {
/* 179 */     int res = open((String)ObjectUtil.checkNotNull(path, "path"));
/* 180 */     if (res < 0) {
/* 181 */       throw Errors.newIOException("open", res);
/*     */     }
/* 183 */     return new FileDescriptor(res);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FileDescriptor from(File file) throws IOException {
/* 190 */     return from(((File)ObjectUtil.checkNotNull(file, "file")).getPath());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FileDescriptor[] pipe() throws IOException {
/* 197 */     long res = newPipe();
/* 198 */     if (res < 0L) {
/* 199 */       throw Errors.newIOException("newPipe", (int)res);
/*     */     }
/* 201 */     return new FileDescriptor[] { new FileDescriptor((int)(res >>> 32L)), new FileDescriptor((int)res) };
/*     */   }
/*     */   
/*     */   final boolean casState(int expected, int update) {
/* 205 */     return stateUpdater.compareAndSet(this, expected, update);
/*     */   }
/*     */   
/*     */   static boolean isClosed(int state) {
/* 209 */     return ((state & 0x1) != 0);
/*     */   }
/*     */   
/*     */   static boolean isInputShutdown(int state) {
/* 213 */     return ((state & 0x2) != 0);
/*     */   }
/*     */   
/*     */   static boolean isOutputShutdown(int state) {
/* 217 */     return ((state & 0x4) != 0);
/*     */   }
/*     */   
/*     */   static int inputShutdown(int state) {
/* 221 */     return state | 0x2;
/*     */   }
/*     */   
/*     */   static int outputShutdown(int state) {
/* 225 */     return state | 0x4;
/*     */   }
/*     */   
/*     */   private static native int open(String paramString);
/*     */   
/*     */   private static native int close(int paramInt);
/*     */   
/*     */   private static native int write(int paramInt1, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native int writeAddress(int paramInt1, long paramLong, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native long writev(int paramInt1, ByteBuffer[] paramArrayOfByteBuffer, int paramInt2, int paramInt3, long paramLong);
/*     */   
/*     */   private static native long writevAddresses(int paramInt1, long paramLong, int paramInt2);
/*     */   
/*     */   private static native int read(int paramInt1, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native int readAddress(int paramInt1, long paramLong, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native long newPipe();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\FileDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */