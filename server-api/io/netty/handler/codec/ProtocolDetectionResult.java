/*    */ package io.netty.handler.codec;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ProtocolDetectionResult<T>
/*    */ {
/* 28 */   private static final ProtocolDetectionResult NEEDS_MORE_DATA = new ProtocolDetectionResult(ProtocolDetectionState.NEEDS_MORE_DATA, null);
/*    */ 
/*    */   
/* 31 */   private static final ProtocolDetectionResult INVALID = new ProtocolDetectionResult(ProtocolDetectionState.INVALID, null);
/*    */ 
/*    */   
/*    */   private final ProtocolDetectionState state;
/*    */ 
/*    */   
/*    */   private final T result;
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> ProtocolDetectionResult<T> needsMoreData() {
/* 42 */     return NEEDS_MORE_DATA;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> ProtocolDetectionResult<T> invalid() {
/* 50 */     return INVALID;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> ProtocolDetectionResult<T> detected(T protocol) {
/* 58 */     return new ProtocolDetectionResult<>(ProtocolDetectionState.DETECTED, (T)ObjectUtil.checkNotNull(protocol, "protocol"));
/*    */   }
/*    */   
/*    */   private ProtocolDetectionResult(ProtocolDetectionState state, T result) {
/* 62 */     this.state = state;
/* 63 */     this.result = result;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ProtocolDetectionState state() {
/* 71 */     return this.state;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public T detectedProtocol() {
/* 78 */     return this.result;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\ProtocolDetectionResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */