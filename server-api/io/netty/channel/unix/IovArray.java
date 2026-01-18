/*     */ package io.netty.channel.unix;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.util.internal.CleanableDirectBuffer;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
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
/*     */ public final class IovArray
/*     */   implements ChannelOutboundBuffer.MessageProcessor
/*     */ {
/*  52 */   private static final int ADDRESS_SIZE = Buffer.addressSize();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public static final int IOV_SIZE = 2 * ADDRESS_SIZE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static final int MAX_CAPACITY = Limits.IOV_MAX * IOV_SIZE;
/*     */   
/*     */   private final long memoryAddress;
/*     */   private final ByteBuf memory;
/*     */   private final CleanableDirectBuffer cleanable;
/*     */   private int count;
/*     */   private long size;
/*  71 */   private long maxBytes = Limits.SSIZE_MAX;
/*     */ 
/*     */   
/*     */   private int maxCount;
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public IovArray() {
/*  79 */     this(Limits.IOV_MAX);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IovArray(int numEntries) {
/*  88 */     int sizeBytes = Math.multiplyExact(ObjectUtil.checkPositive(numEntries, "numEntries"), IOV_SIZE);
/*  89 */     this.cleanable = Buffer.allocateDirectBufferWithNativeOrder(sizeBytes);
/*  90 */     ByteBuf bbuf = Unpooled.wrappedBuffer(this.cleanable.buffer()).setIndex(0, 0);
/*  91 */     this.memory = PlatformDependent.hasUnsafe() ? bbuf : bbuf.order(
/*  92 */         PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
/*  93 */     if (this.memory.hasMemoryAddress()) {
/*  94 */       this.memoryAddress = this.memory.memoryAddress();
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 101 */       ByteBuffer byteBuffer = this.memory.internalNioBuffer(0, this.memory.capacity());
/* 102 */       this.memoryAddress = Buffer.memoryAddress(byteBuffer) + byteBuffer.position();
/*     */     } 
/* 104 */     this.maxCount = Limits.IOV_MAX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public IovArray(ByteBuf memory) {
/* 113 */     assert memory.writerIndex() == 0;
/* 114 */     assert memory.readerIndex() == 0;
/* 115 */     this.memory = PlatformDependent.hasUnsafe() ? memory : memory.order(
/* 116 */         PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
/* 117 */     if (memory.hasMemoryAddress()) {
/* 118 */       this.memoryAddress = memory.memoryAddress();
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 125 */       ByteBuffer byteBuffer = memory.internalNioBuffer(0, memory.capacity());
/* 126 */       this.memoryAddress = Buffer.memoryAddress(byteBuffer) + byteBuffer.position();
/*     */     } 
/* 128 */     this.cleanable = null;
/* 129 */     this.maxCount = Limits.IOV_MAX;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 133 */     this.count = 0;
/* 134 */     this.size = 0L;
/* 135 */     this.maxCount = Limits.IOV_MAX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean add(ByteBuf buf) {
/* 143 */     return add(buf, buf.readerIndex(), buf.readableBytes());
/*     */   }
/*     */   
/*     */   public boolean add(ByteBuf buf, int offset, int len) {
/* 147 */     if (this.count == this.maxCount)
/*     */     {
/* 149 */       return false;
/*     */     }
/* 151 */     if (buf.nioBufferCount() == 1) {
/* 152 */       if (len == 0) {
/* 153 */         return true;
/*     */       }
/* 155 */       if (buf.hasMemoryAddress()) {
/* 156 */         return add(this.memoryAddress, buf.memoryAddress() + offset, len);
/*     */       }
/* 158 */       ByteBuffer nioBuffer = buf.internalNioBuffer(offset, len);
/* 159 */       return add(this.memoryAddress, Buffer.memoryAddress(nioBuffer) + nioBuffer.position(), len);
/*     */     } 
/*     */     
/* 162 */     ByteBuffer[] buffers = buf.nioBuffers(offset, len);
/* 163 */     for (ByteBuffer nioBuffer : buffers) {
/* 164 */       int remaining = nioBuffer.remaining();
/* 165 */       if (remaining != 0 && (
/* 166 */         !add(this.memoryAddress, Buffer.memoryAddress(nioBuffer) + nioBuffer.position(), remaining) || this.count == Limits.IOV_MAX))
/*     */       {
/* 168 */         return false;
/*     */       }
/*     */     } 
/* 171 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFull() {
/* 181 */     return (this.memory.capacity() < (this.count + 1) * IOV_SIZE || this.size >= this.maxBytes);
/*     */   }
/*     */   
/*     */   private boolean add(long memoryAddress, long addr, int len) {
/* 185 */     assert addr != 0L;
/*     */ 
/*     */ 
/*     */     
/* 189 */     if ((this.maxBytes - len < this.size && this.count > 0) || this.memory
/*     */       
/* 191 */       .capacity() < (this.count + 1) * IOV_SIZE)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 198 */       return false;
/*     */     }
/* 200 */     int baseOffset = idx(this.count);
/* 201 */     int lengthOffset = baseOffset + ADDRESS_SIZE;
/*     */     
/* 203 */     this.size += len;
/* 204 */     this.count++;
/*     */     
/* 206 */     if (ADDRESS_SIZE == 8) {
/*     */       
/* 208 */       if (PlatformDependent.hasUnsafe()) {
/* 209 */         PlatformDependent.putLong(baseOffset + memoryAddress, addr);
/* 210 */         PlatformDependent.putLong(lengthOffset + memoryAddress, len);
/*     */       } else {
/* 212 */         this.memory.setLong(baseOffset, addr);
/* 213 */         this.memory.setLong(lengthOffset, len);
/*     */       } 
/*     */     } else {
/* 216 */       assert ADDRESS_SIZE == 4;
/* 217 */       if (PlatformDependent.hasUnsafe()) {
/* 218 */         PlatformDependent.putInt(baseOffset + memoryAddress, (int)addr);
/* 219 */         PlatformDependent.putInt(lengthOffset + memoryAddress, len);
/*     */       } else {
/* 221 */         this.memory.setInt(baseOffset, (int)addr);
/* 222 */         this.memory.setInt(lengthOffset, len);
/*     */       } 
/*     */     } 
/* 225 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int count() {
/* 232 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long size() {
/* 239 */     return this.size;
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
/*     */   public void maxBytes(long maxBytes) {
/* 253 */     this.maxBytes = Math.min(Limits.SSIZE_MAX, ObjectUtil.checkPositive(maxBytes, "maxBytes"));
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
/*     */   public void maxCount(int maxCount) {
/* 265 */     this.maxCount = Math.min(Limits.IOV_MAX, ObjectUtil.checkPositive(maxCount, "maxCount"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long maxBytes() {
/* 273 */     return this.maxBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxCount() {
/* 281 */     return this.maxCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long memoryAddress(int offset) {
/* 288 */     return this.memoryAddress + idx(offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/* 295 */     this.memory.release();
/* 296 */     if (this.cleanable != null)
/*     */     {
/* 298 */       this.cleanable.clean();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processMessage(Object msg) throws Exception {
/* 304 */     if (msg instanceof ByteBuf) {
/* 305 */       ByteBuf buffer = (ByteBuf)msg;
/* 306 */       return add(buffer, buffer.readerIndex(), buffer.readableBytes());
/*     */     } 
/* 308 */     return false;
/*     */   }
/*     */   
/*     */   private static int idx(int index) {
/* 312 */     return IOV_SIZE * index;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\IovArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */