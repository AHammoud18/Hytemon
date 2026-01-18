/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.IllegalReferenceCountException;
/*     */ import io.netty.util.ReferenceCounted;
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
/*     */ @Deprecated
/*     */ public abstract class ReferenceCountUpdater<T extends ReferenceCounted>
/*     */ {
/*     */   protected abstract void safeInitializeRawRefCnt(T paramT, int paramInt);
/*     */   
/*     */   protected abstract int getAndAddRawRefCnt(T paramT, int paramInt);
/*     */   
/*     */   protected abstract int getRawRefCnt(T paramT);
/*     */   
/*     */   protected abstract int getAcquireRawRefCnt(T paramT);
/*     */   
/*     */   protected abstract void setReleaseRawRefCnt(T paramT, int paramInt);
/*     */   
/*     */   protected abstract boolean casRawRefCnt(T paramT, int paramInt1, int paramInt2);
/*     */   
/*     */   public final int initialValue() {
/*  54 */     return 2;
/*     */   }
/*     */   
/*     */   public final void setInitialValue(T instance) {
/*  58 */     safeInitializeRawRefCnt(instance, initialValue());
/*     */   }
/*     */   
/*     */   private static int realRefCnt(int rawCnt) {
/*  62 */     return rawCnt >>> 1;
/*     */   }
/*     */   
/*     */   public final int refCnt(T instance) {
/*  66 */     return realRefCnt(getAcquireRawRefCnt(instance));
/*     */   }
/*     */   
/*     */   public final boolean isLiveNonVolatile(T instance) {
/*  70 */     int rawCnt = getRawRefCnt(instance);
/*  71 */     if (rawCnt == 2) {
/*  72 */       return true;
/*     */     }
/*  74 */     return ((rawCnt & 0x1) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setRefCnt(T instance, int refCnt) {
/*  81 */     int rawRefCnt = (refCnt > 0) ? (refCnt << 1) : 1;
/*  82 */     setReleaseRawRefCnt(instance, rawRefCnt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void resetRefCnt(T instance) {
/*  90 */     setReleaseRawRefCnt(instance, initialValue());
/*     */   }
/*     */   
/*     */   public final T retain(T instance) {
/*  94 */     return retain0(instance, 2);
/*     */   }
/*     */   
/*     */   public final T retain(T instance, int increment) {
/*  98 */     return retain0(instance, ObjectUtil.checkPositive(increment, "increment") << 1);
/*     */   }
/*     */   
/*     */   private T retain0(T instance, int increment) {
/* 102 */     int oldRef = getAndAddRawRefCnt(instance, increment);
/*     */ 
/*     */ 
/*     */     
/* 106 */     if ((oldRef & 0x80000001) != 0 || oldRef > Integer.MAX_VALUE - increment) {
/* 107 */       getAndAddRawRefCnt(instance, -increment);
/* 108 */       throw new IllegalReferenceCountException(0, increment >>> 1);
/*     */     } 
/* 110 */     return instance;
/*     */   }
/*     */   
/*     */   public final boolean release(T instance) {
/* 114 */     return release0(instance, 2);
/*     */   }
/*     */   
/*     */   public final boolean release(T instance, int decrement) {
/* 118 */     return release0(instance, ObjectUtil.checkPositive(decrement, "decrement") << 1);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean release0(T instance, int decrement) {
/*     */     while (true) {
/* 124 */       int next, curr = getRawRefCnt(instance);
/* 125 */       if (curr == decrement) {
/* 126 */         next = 1;
/*     */       } else {
/* 128 */         if (curr < decrement || (curr & 0x1) == 1) {
/* 129 */           throwIllegalRefCountOnRelease(decrement, curr);
/*     */         }
/* 131 */         next = curr - decrement;
/*     */       } 
/* 133 */       if (casRawRefCnt(instance, curr, next))
/* 134 */         return ((next & 0x1) == 1); 
/*     */     } 
/*     */   }
/*     */   private static void throwIllegalRefCountOnRelease(int decrement, int curr) {
/* 138 */     throw new IllegalReferenceCountException(curr >>> 1, -(decrement >>> 1));
/*     */   }
/*     */   
/*     */   public enum UpdaterType {
/* 142 */     Unsafe,
/* 143 */     VarHandle,
/* 144 */     Atomic;
/*     */   }
/*     */   
/*     */   public static <T extends ReferenceCounted> UpdaterType updaterTypeOf(Class<T> clz, String fieldName) {
/* 148 */     long fieldOffset = getUnsafeOffset(clz, fieldName);
/* 149 */     if (fieldOffset >= 0L) {
/* 150 */       return UpdaterType.Unsafe;
/*     */     }
/* 152 */     if (PlatformDependent.hasVarHandle()) {
/* 153 */       return UpdaterType.VarHandle;
/*     */     }
/* 155 */     return UpdaterType.Atomic;
/*     */   }
/*     */   
/*     */   public static long getUnsafeOffset(Class<? extends ReferenceCounted> clz, String fieldName) {
/*     */     try {
/* 160 */       if (PlatformDependent.hasUnsafe()) {
/* 161 */         return PlatformDependent.objectFieldOffset(clz.getDeclaredField(fieldName));
/*     */       }
/* 163 */     } catch (Throwable throwable) {}
/*     */ 
/*     */     
/* 166 */     return -1L;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\ReferenceCountUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */