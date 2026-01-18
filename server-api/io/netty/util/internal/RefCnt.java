/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.IllegalReferenceCountException;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.VarHandle;
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
/*     */ public final class RefCnt
/*     */ {
/*     */   private static final int UNSAFE = 0;
/*     */   private static final int VAR_HANDLE = 1;
/*     */   private static final int ATOMIC_UPDATER = 2;
/*     */   private static final int REF_CNT_IMPL;
/*     */   volatile int value;
/*     */   
/*     */   static {
/*  40 */     if (PlatformDependent.hasUnsafe()) {
/*  41 */       REF_CNT_IMPL = 0;
/*  42 */     } else if (PlatformDependent.hasVarHandle()) {
/*  43 */       REF_CNT_IMPL = 1;
/*     */     } else {
/*  45 */       REF_CNT_IMPL = 2;
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
/*     */   
/*     */   public RefCnt() {
/*  61 */     switch (REF_CNT_IMPL) {
/*     */       case 0:
/*  63 */         UnsafeRefCnt.init(this);
/*     */         return;
/*     */       case 1:
/*  66 */         VarHandleRefCnt.init(this);
/*     */         return;
/*     */     } 
/*     */     
/*  70 */     AtomicRefCnt.init(this);
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
/*     */   public static int refCnt(RefCnt ref) {
/*  82 */     switch (REF_CNT_IMPL) {
/*     */       case 0:
/*  84 */         return UnsafeRefCnt.refCnt(ref);
/*     */       case 1:
/*  86 */         return VarHandleRefCnt.refCnt(ref);
/*     */     } 
/*     */     
/*  89 */     return AtomicRefCnt.refCnt(ref);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void retain(RefCnt ref) {
/*  99 */     switch (REF_CNT_IMPL) {
/*     */       case 0:
/* 101 */         UnsafeRefCnt.retain(ref);
/*     */         return;
/*     */       case 1:
/* 104 */         VarHandleRefCnt.retain(ref);
/*     */         return;
/*     */     } 
/*     */     
/* 108 */     AtomicRefCnt.retain(ref);
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
/*     */   public static void retain(RefCnt ref, int increment) {
/* 121 */     switch (REF_CNT_IMPL) {
/*     */       case 0:
/* 123 */         UnsafeRefCnt.retain(ref, increment);
/*     */         return;
/*     */       case 1:
/* 126 */         VarHandleRefCnt.retain(ref, increment);
/*     */         return;
/*     */     } 
/*     */     
/* 130 */     AtomicRefCnt.retain(ref, increment);
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
/*     */   public static boolean release(RefCnt ref) {
/* 142 */     switch (REF_CNT_IMPL) {
/*     */       case 0:
/* 144 */         return UnsafeRefCnt.release(ref);
/*     */       case 1:
/* 146 */         return VarHandleRefCnt.release(ref);
/*     */     } 
/*     */     
/* 149 */     return AtomicRefCnt.release(ref);
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
/*     */   public static boolean release(RefCnt ref, int decrement) {
/* 162 */     switch (REF_CNT_IMPL) {
/*     */       case 0:
/* 164 */         return UnsafeRefCnt.release(ref, decrement);
/*     */       case 1:
/* 166 */         return VarHandleRefCnt.release(ref, decrement);
/*     */     } 
/*     */     
/* 169 */     return AtomicRefCnt.release(ref, decrement);
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
/*     */   public static boolean isLiveNonVolatile(RefCnt ref) {
/* 181 */     switch (REF_CNT_IMPL) {
/*     */       case 0:
/* 183 */         return UnsafeRefCnt.isLiveNonVolatile(ref);
/*     */       case 1:
/* 185 */         return VarHandleRefCnt.isLiveNonVolatile(ref);
/*     */     } 
/*     */     
/* 188 */     return AtomicRefCnt.isLiveNonVolatile(ref);
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
/*     */   public static void setRefCnt(RefCnt ref, int refCnt) {
/* 200 */     switch (REF_CNT_IMPL) {
/*     */       case 0:
/* 202 */         UnsafeRefCnt.setRefCnt(ref, refCnt);
/*     */         return;
/*     */       case 1:
/* 205 */         VarHandleRefCnt.setRefCnt(ref, refCnt);
/*     */         return;
/*     */     } 
/*     */     
/* 209 */     AtomicRefCnt.setRefCnt(ref, refCnt);
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
/*     */   
/*     */   public static void resetRefCnt(RefCnt ref) {
/* 224 */     switch (REF_CNT_IMPL) {
/*     */       case 0:
/* 226 */         UnsafeRefCnt.resetRefCnt(ref);
/*     */         return;
/*     */       case 1:
/* 229 */         VarHandleRefCnt.resetRefCnt(ref);
/*     */         return;
/*     */     } 
/*     */     
/* 233 */     AtomicRefCnt.resetRefCnt(ref);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void throwIllegalRefCountOnRelease(int decrement, int curr) {
/* 239 */     throw new IllegalReferenceCountException(curr >>> 1, -(decrement >>> 1));
/*     */   }
/*     */   
/*     */   private static final class AtomicRefCnt
/*     */   {
/* 244 */     private static final AtomicIntegerFieldUpdater<RefCnt> UPDATER = AtomicIntegerFieldUpdater.newUpdater(RefCnt.class, "value");
/*     */     
/*     */     static void init(RefCnt instance) {
/* 247 */       UPDATER.set(instance, 2);
/*     */     }
/*     */     
/*     */     static int refCnt(RefCnt instance) {
/* 251 */       return UPDATER.get(instance) >>> 1;
/*     */     }
/*     */     
/*     */     static void retain(RefCnt instance) {
/* 255 */       retain0(instance, 2);
/*     */     }
/*     */     
/*     */     static void retain(RefCnt instance, int increment) {
/* 259 */       retain0(instance, ObjectUtil.checkPositive(increment, "increment") << 1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static void retain0(RefCnt instance, int increment) {
/* 266 */       int oldRef = UPDATER.getAndAdd(instance, increment);
/* 267 */       if ((oldRef & 0x80000001) != 0 || oldRef > Integer.MAX_VALUE - increment) {
/* 268 */         UPDATER.getAndAdd(instance, -increment);
/* 269 */         throw new IllegalReferenceCountException(0, increment >>> 1);
/*     */       } 
/*     */     }
/*     */     
/*     */     static boolean release(RefCnt instance) {
/* 274 */       return release0(instance, 2);
/*     */     }
/*     */     
/*     */     static boolean release(RefCnt instance, int decrement) {
/* 278 */       return release0(instance, ObjectUtil.checkPositive(decrement, "decrement") << 1);
/*     */     }
/*     */ 
/*     */     
/*     */     private static boolean release0(RefCnt instance, int decrement) {
/*     */       while (true) {
/* 284 */         int next, curr = instance.value;
/* 285 */         if (curr == decrement) {
/* 286 */           next = 1;
/*     */         } else {
/* 288 */           if (curr < decrement || (curr & 0x1) == 1) {
/* 289 */             RefCnt.throwIllegalRefCountOnRelease(decrement, curr);
/*     */           }
/* 291 */           next = curr - decrement;
/*     */         } 
/* 293 */         if (UPDATER.compareAndSet(instance, curr, next))
/* 294 */           return ((next & 0x1) == 1); 
/*     */       } 
/*     */     }
/*     */     static void setRefCnt(RefCnt instance, int refCnt) {
/* 298 */       int rawRefCnt = (refCnt > 0) ? (refCnt << 1) : 1;
/* 299 */       UPDATER.lazySet(instance, rawRefCnt);
/*     */     }
/*     */     
/*     */     static void resetRefCnt(RefCnt instance) {
/* 303 */       UPDATER.lazySet(instance, 2);
/*     */     }
/*     */     
/*     */     static boolean isLiveNonVolatile(RefCnt instance) {
/* 307 */       int rawCnt = instance.value;
/* 308 */       if (rawCnt == 2) {
/* 309 */         return true;
/*     */       }
/* 311 */       return ((rawCnt & 0x1) == 0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class VarHandleRefCnt
/*     */   {
/* 320 */     private static final VarHandle VH = PlatformDependent.findVarHandleOfIntField(MethodHandles.lookup(), RefCnt.class, "value");
/*     */ 
/*     */     
/*     */     static void init(RefCnt instance) {
/* 324 */       VH.set(instance, 2);
/* 325 */       VarHandle.storeStoreFence();
/*     */     }
/*     */     
/*     */     static int refCnt(RefCnt instance) {
/* 329 */       return VH.getAcquire(instance) >>> 1;
/*     */     }
/*     */     
/*     */     static void retain(RefCnt instance) {
/* 333 */       retain0(instance, 2);
/*     */     }
/*     */     
/*     */     static void retain(RefCnt instance, int increment) {
/* 337 */       retain0(instance, ObjectUtil.checkPositive(increment, "increment") << 1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static void retain0(RefCnt instance, int increment) {
/* 344 */       int oldRef = VH.getAndAdd(instance, increment);
/* 345 */       if ((oldRef & 0x80000001) != 0 || oldRef > Integer.MAX_VALUE - increment) {
/* 346 */         VH.getAndAdd(instance, -increment);
/* 347 */         throw new IllegalReferenceCountException(0, increment >>> 1);
/*     */       } 
/*     */     }
/*     */     
/*     */     static boolean release(RefCnt instance) {
/* 352 */       return release0(instance, 2);
/*     */     }
/*     */     
/*     */     static boolean release(RefCnt instance, int decrement) {
/* 356 */       return release0(instance, ObjectUtil.checkPositive(decrement, "decrement") << 1);
/*     */     }
/*     */ 
/*     */     
/*     */     private static boolean release0(RefCnt instance, int decrement) {
/*     */       while (true) {
/* 362 */         int next, curr = VH.get(instance);
/* 363 */         if (curr == decrement) {
/* 364 */           next = 1;
/*     */         } else {
/* 366 */           if (curr < decrement || (curr & 0x1) == 1) {
/* 367 */             RefCnt.throwIllegalRefCountOnRelease(decrement, curr);
/*     */           }
/* 369 */           next = curr - decrement;
/*     */         } 
/* 371 */         if (VH.compareAndSet(instance, curr, next))
/* 372 */           return ((next & 0x1) == 1); 
/*     */       } 
/*     */     }
/*     */     static void setRefCnt(RefCnt instance, int refCnt) {
/* 376 */       int rawRefCnt = (refCnt > 0) ? (refCnt << 1) : 1;
/* 377 */       VH.setRelease(instance, rawRefCnt);
/*     */     }
/*     */     
/*     */     static void resetRefCnt(RefCnt instance) {
/* 381 */       VH.setRelease(instance, 2);
/*     */     }
/*     */     
/*     */     static boolean isLiveNonVolatile(RefCnt instance) {
/* 385 */       int rawCnt = VH.get(instance);
/* 386 */       if (rawCnt == 2) {
/* 387 */         return true;
/*     */       }
/* 389 */       return ((rawCnt & 0x1) == 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class UnsafeRefCnt
/*     */   {
/* 395 */     private static final long VALUE_OFFSET = getUnsafeOffset(RefCnt.class, "value");
/*     */     
/*     */     private static long getUnsafeOffset(Class<?> clz, String fieldName) {
/*     */       try {
/* 399 */         if (PlatformDependent.hasUnsafe()) {
/* 400 */           return PlatformDependent.objectFieldOffset(clz.getDeclaredField(fieldName));
/*     */         }
/* 402 */       } catch (Throwable throwable) {}
/*     */ 
/*     */       
/* 405 */       return -1L;
/*     */     }
/*     */     
/*     */     static void init(RefCnt instance) {
/* 409 */       PlatformDependent.safeConstructPutInt(instance, VALUE_OFFSET, 2);
/*     */     }
/*     */     
/*     */     static int refCnt(RefCnt instance) {
/* 413 */       return PlatformDependent.getVolatileInt(instance, VALUE_OFFSET) >>> 1;
/*     */     }
/*     */     
/*     */     static void retain(RefCnt instance) {
/* 417 */       retain0(instance, 2);
/*     */     }
/*     */     
/*     */     static void retain(RefCnt instance, int increment) {
/* 421 */       retain0(instance, ObjectUtil.checkPositive(increment, "increment") << 1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static void retain0(RefCnt instance, int increment) {
/* 428 */       int oldRef = PlatformDependent.getAndAddInt(instance, VALUE_OFFSET, increment);
/* 429 */       if ((oldRef & 0x80000001) != 0 || oldRef > Integer.MAX_VALUE - increment) {
/* 430 */         PlatformDependent.getAndAddInt(instance, VALUE_OFFSET, -increment);
/* 431 */         throw new IllegalReferenceCountException(0, increment >>> 1);
/*     */       } 
/*     */     }
/*     */     
/*     */     static boolean release(RefCnt instance) {
/* 436 */       return release0(instance, 2);
/*     */     }
/*     */     
/*     */     static boolean release(RefCnt instance, int decrement) {
/* 440 */       return release0(instance, ObjectUtil.checkPositive(decrement, "decrement") << 1);
/*     */     }
/*     */ 
/*     */     
/*     */     private static boolean release0(RefCnt instance, int decrement) {
/*     */       while (true) {
/* 446 */         int next, curr = PlatformDependent.getInt(instance, VALUE_OFFSET);
/* 447 */         if (curr == decrement) {
/* 448 */           next = 1;
/*     */         } else {
/* 450 */           if (curr < decrement || (curr & 0x1) == 1) {
/* 451 */             RefCnt.throwIllegalRefCountOnRelease(decrement, curr);
/*     */           }
/* 453 */           next = curr - decrement;
/*     */         } 
/* 455 */         if (PlatformDependent.compareAndSwapInt(instance, VALUE_OFFSET, curr, next))
/* 456 */           return ((next & 0x1) == 1); 
/*     */       } 
/*     */     }
/*     */     static void setRefCnt(RefCnt instance, int refCnt) {
/* 460 */       int rawRefCnt = (refCnt > 0) ? (refCnt << 1) : 1;
/* 461 */       PlatformDependent.putOrderedInt(instance, VALUE_OFFSET, rawRefCnt);
/*     */     }
/*     */     
/*     */     static void resetRefCnt(RefCnt instance) {
/* 465 */       PlatformDependent.putOrderedInt(instance, VALUE_OFFSET, 2);
/*     */     }
/*     */     
/*     */     static boolean isLiveNonVolatile(RefCnt instance) {
/* 469 */       int rawCnt = PlatformDependent.getInt(instance, VALUE_OFFSET);
/* 470 */       if (rawCnt == 2) {
/* 471 */         return true;
/*     */       }
/* 473 */       return ((rawCnt & 0x1) == 0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\RefCnt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */