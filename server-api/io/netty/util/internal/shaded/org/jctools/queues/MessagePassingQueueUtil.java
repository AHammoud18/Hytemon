/*     */ package io.netty.util.internal.shaded.org.jctools.queues;
/*     */ 
/*     */ import io.netty.util.internal.shaded.org.jctools.util.PortableJvmInfo;
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
/*     */ public final class MessagePassingQueueUtil
/*     */ {
/*     */   public static <E> int drain(MessagePassingQueue<E> queue, MessagePassingQueue.Consumer<E> c, int limit) {
/*  29 */     if (null == c)
/*  30 */       throw new IllegalArgumentException("c is null"); 
/*  31 */     if (limit < 0)
/*  32 */       throw new IllegalArgumentException("limit is negative: " + limit); 
/*  33 */     if (limit == 0) {
/*  34 */       return 0;
/*     */     }
/*  36 */     int i = 0; E e;
/*  37 */     for (; i < limit && (e = queue.relaxedPoll()) != null; i++)
/*     */     {
/*  39 */       c.accept(e);
/*     */     }
/*  41 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static <E> int drain(MessagePassingQueue<E> queue, MessagePassingQueue.Consumer<E> c) {
/*  46 */     if (null == c) {
/*  47 */       throw new IllegalArgumentException("c is null");
/*     */     }
/*  49 */     int i = 0; E e;
/*  50 */     while ((e = queue.relaxedPoll()) != null) {
/*     */       
/*  52 */       i++;
/*  53 */       c.accept(e);
/*     */     } 
/*  55 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static <E> void drain(MessagePassingQueue<E> queue, MessagePassingQueue.Consumer<E> c, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
/*  60 */     if (null == c)
/*  61 */       throw new IllegalArgumentException("c is null"); 
/*  62 */     if (null == wait)
/*  63 */       throw new IllegalArgumentException("wait is null"); 
/*  64 */     if (null == exit) {
/*  65 */       throw new IllegalArgumentException("exit condition is null");
/*     */     }
/*  67 */     int idleCounter = 0;
/*  68 */     while (exit.keepRunning()) {
/*     */       
/*  70 */       E e = queue.relaxedPoll();
/*  71 */       if (e == null) {
/*     */         
/*  73 */         idleCounter = wait.idle(idleCounter);
/*     */         continue;
/*     */       } 
/*  76 */       idleCounter = 0;
/*  77 */       c.accept(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static <E> void fill(MessagePassingQueue<E> q, MessagePassingQueue.Supplier<E> s, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
/*  83 */     if (null == wait)
/*  84 */       throw new IllegalArgumentException("waiter is null"); 
/*  85 */     if (null == exit) {
/*  86 */       throw new IllegalArgumentException("exit condition is null");
/*     */     }
/*  88 */     int idleCounter = 0;
/*  89 */     while (exit.keepRunning()) {
/*     */       
/*  91 */       if (q.fill(s, PortableJvmInfo.RECOMENDED_OFFER_BATCH) == 0) {
/*     */         
/*  93 */         idleCounter = wait.idle(idleCounter);
/*     */         continue;
/*     */       } 
/*  96 */       idleCounter = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static <E> int fillBounded(MessagePassingQueue<E> q, MessagePassingQueue.Supplier<E> s) {
/* 102 */     return fillInBatchesToLimit(q, s, PortableJvmInfo.RECOMENDED_OFFER_BATCH, q.capacity());
/*     */   }
/*     */ 
/*     */   
/*     */   public static <E> int fillInBatchesToLimit(MessagePassingQueue<E> q, MessagePassingQueue.Supplier<E> s, int batch, int limit) {
/* 107 */     long result = 0L;
/*     */     
/*     */     while (true) {
/* 110 */       int filled = q.fill(s, batch);
/* 111 */       if (filled == 0)
/*     */       {
/* 113 */         return (int)result;
/*     */       }
/* 115 */       result += filled;
/*     */       
/* 117 */       if (result > limit)
/* 118 */         return (int)result; 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static <E> int fillUnbounded(MessagePassingQueue<E> q, MessagePassingQueue.Supplier<E> s) {
/* 123 */     return fillInBatchesToLimit(q, s, PortableJvmInfo.RECOMENDED_OFFER_BATCH, 4096);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\MessagePassingQueueUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */