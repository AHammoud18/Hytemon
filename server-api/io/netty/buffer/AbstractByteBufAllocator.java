/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.ResourceLeakDetector;
/*     */ import io.netty.util.ResourceLeakTracker;
/*     */ import io.netty.util.internal.MathUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
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
/*     */ public abstract class AbstractByteBufAllocator
/*     */   implements ByteBufAllocator
/*     */ {
/*     */   static final int DEFAULT_INITIAL_CAPACITY = 256;
/*     */   static final int DEFAULT_MAX_CAPACITY = 2147483647;
/*     */   static final int DEFAULT_MAX_COMPONENTS = 16;
/*     */   static final int CALCULATE_THRESHOLD = 4194304;
/*     */   private final boolean directByDefault;
/*     */   private final ByteBuf emptyBuf;
/*     */   
/*     */   static {
/*  37 */     ResourceLeakDetector.addExclusions(AbstractByteBufAllocator.class, new String[] { "toLeakAwareBuffer" });
/*     */   }
/*     */   
/*     */   protected static ByteBuf toLeakAwareBuffer(ByteBuf buf) {
/*  41 */     ResourceLeakTracker<ByteBuf> leak = AbstractByteBuf.leakDetector.track(buf);
/*  42 */     if (leak != null) {
/*  43 */       if (AbstractByteBuf.leakDetector.isRecordEnabled()) {
/*  44 */         buf = new AdvancedLeakAwareByteBuf(buf, leak);
/*     */       } else {
/*  46 */         buf = new SimpleLeakAwareByteBuf(buf, leak);
/*     */       } 
/*     */     }
/*  49 */     return buf;
/*     */   }
/*     */   
/*     */   protected static CompositeByteBuf toLeakAwareBuffer(CompositeByteBuf buf) {
/*  53 */     ResourceLeakTracker<ByteBuf> leak = AbstractByteBuf.leakDetector.track(buf);
/*  54 */     if (leak != null) {
/*  55 */       if (AbstractByteBuf.leakDetector.isRecordEnabled()) {
/*  56 */         buf = new AdvancedLeakAwareCompositeByteBuf(buf, leak);
/*     */       } else {
/*  58 */         buf = new SimpleLeakAwareCompositeByteBuf(buf, leak);
/*     */       } 
/*     */     }
/*  61 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractByteBufAllocator() {
/*  71 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractByteBufAllocator(boolean preferDirect) {
/*  81 */     this.directByDefault = (preferDirect && PlatformDependent.canReliabilyFreeDirectBuffers());
/*  82 */     this.emptyBuf = new EmptyByteBuf(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf buffer() {
/*  87 */     if (this.directByDefault) {
/*  88 */       return directBuffer();
/*     */     }
/*  90 */     return heapBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf buffer(int initialCapacity) {
/*  95 */     if (this.directByDefault) {
/*  96 */       return directBuffer(initialCapacity);
/*     */     }
/*  98 */     return heapBuffer(initialCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf buffer(int initialCapacity, int maxCapacity) {
/* 103 */     if (this.directByDefault) {
/* 104 */       return directBuffer(initialCapacity, maxCapacity);
/*     */     }
/* 106 */     return heapBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf ioBuffer() {
/* 111 */     if (PlatformDependent.canReliabilyFreeDirectBuffers() || isDirectBufferPooled()) {
/* 112 */       return directBuffer(256);
/*     */     }
/* 114 */     return heapBuffer(256);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf ioBuffer(int initialCapacity) {
/* 119 */     if (PlatformDependent.canReliabilyFreeDirectBuffers() || isDirectBufferPooled()) {
/* 120 */       return directBuffer(initialCapacity);
/*     */     }
/* 122 */     return heapBuffer(initialCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf ioBuffer(int initialCapacity, int maxCapacity) {
/* 127 */     if (PlatformDependent.canReliabilyFreeDirectBuffers() || isDirectBufferPooled()) {
/* 128 */       return directBuffer(initialCapacity, maxCapacity);
/*     */     }
/* 130 */     return heapBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf heapBuffer() {
/* 135 */     return heapBuffer(256, 2147483647);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf heapBuffer(int initialCapacity) {
/* 140 */     return heapBuffer(initialCapacity, 2147483647);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf heapBuffer(int initialCapacity, int maxCapacity) {
/* 145 */     if (initialCapacity == 0 && maxCapacity == 0) {
/* 146 */       return this.emptyBuf;
/*     */     }
/* 148 */     validate(initialCapacity, maxCapacity);
/* 149 */     return newHeapBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf directBuffer() {
/* 154 */     return directBuffer(256, 2147483647);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf directBuffer(int initialCapacity) {
/* 159 */     return directBuffer(initialCapacity, 2147483647);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf directBuffer(int initialCapacity, int maxCapacity) {
/* 164 */     if (initialCapacity == 0 && maxCapacity == 0) {
/* 165 */       return this.emptyBuf;
/*     */     }
/* 167 */     validate(initialCapacity, maxCapacity);
/* 168 */     return newDirectBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeBuffer() {
/* 173 */     if (this.directByDefault) {
/* 174 */       return compositeDirectBuffer();
/*     */     }
/* 176 */     return compositeHeapBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeBuffer(int maxNumComponents) {
/* 181 */     if (this.directByDefault) {
/* 182 */       return compositeDirectBuffer(maxNumComponents);
/*     */     }
/* 184 */     return compositeHeapBuffer(maxNumComponents);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeHeapBuffer() {
/* 189 */     return compositeHeapBuffer(16);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeHeapBuffer(int maxNumComponents) {
/* 194 */     return toLeakAwareBuffer(new CompositeByteBuf(this, false, maxNumComponents));
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeDirectBuffer() {
/* 199 */     return compositeDirectBuffer(16);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeDirectBuffer(int maxNumComponents) {
/* 204 */     return toLeakAwareBuffer(new CompositeByteBuf(this, true, maxNumComponents));
/*     */   }
/*     */   
/*     */   private static void validate(int initialCapacity, int maxCapacity) {
/* 208 */     ObjectUtil.checkPositiveOrZero(initialCapacity, "initialCapacity");
/* 209 */     if (initialCapacity > maxCapacity) {
/* 210 */       throw new IllegalArgumentException(String.format("initialCapacity: %d (expected: not greater than maxCapacity(%d)", new Object[] {
/*     */               
/* 212 */               Integer.valueOf(initialCapacity), Integer.valueOf(maxCapacity)
/*     */             }));
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
/*     */   public String toString() {
/* 228 */     return StringUtil.simpleClassName(this) + "(directByDefault: " + this.directByDefault + ')';
/*     */   }
/*     */ 
/*     */   
/*     */   public int calculateNewCapacity(int minNewCapacity, int maxCapacity) {
/* 233 */     ObjectUtil.checkPositiveOrZero(minNewCapacity, "minNewCapacity");
/* 234 */     if (minNewCapacity > maxCapacity)
/* 235 */       throw new IllegalArgumentException(String.format("minNewCapacity: %d (expected: not greater than maxCapacity(%d)", new Object[] {
/*     */               
/* 237 */               Integer.valueOf(minNewCapacity), Integer.valueOf(maxCapacity)
/*     */             })); 
/* 239 */     int threshold = 4194304;
/*     */     
/* 241 */     if (minNewCapacity == 4194304) {
/* 242 */       return 4194304;
/*     */     }
/*     */ 
/*     */     
/* 246 */     if (minNewCapacity > 4194304) {
/* 247 */       int i = minNewCapacity / 4194304 * 4194304;
/* 248 */       if (i > maxCapacity - 4194304) {
/* 249 */         i = maxCapacity;
/*     */       } else {
/* 251 */         i += 4194304;
/*     */       } 
/* 253 */       return i;
/*     */     } 
/*     */ 
/*     */     
/* 257 */     int newCapacity = MathUtil.findNextPositivePowerOfTwo(Math.max(minNewCapacity, 64));
/* 258 */     return Math.min(newCapacity, maxCapacity);
/*     */   }
/*     */   
/*     */   protected abstract ByteBuf newHeapBuffer(int paramInt1, int paramInt2);
/*     */   
/*     */   protected abstract ByteBuf newDirectBuffer(int paramInt1, int paramInt2);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\AbstractByteBufAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */