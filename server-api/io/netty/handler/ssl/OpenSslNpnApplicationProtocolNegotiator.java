/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.util.List;
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
/*    */ @Deprecated
/*    */ public final class OpenSslNpnApplicationProtocolNegotiator
/*    */   implements OpenSslApplicationProtocolNegotiator
/*    */ {
/*    */   private final List<String> protocols;
/*    */   
/*    */   public OpenSslNpnApplicationProtocolNegotiator(Iterable<String> protocols) {
/* 33 */     this.protocols = (List<String>)ObjectUtil.checkNotNull(ApplicationProtocolUtil.toList(protocols), "protocols");
/*    */   }
/*    */   
/*    */   public OpenSslNpnApplicationProtocolNegotiator(String... protocols) {
/* 37 */     this.protocols = (List<String>)ObjectUtil.checkNotNull(ApplicationProtocolUtil.toList(protocols), "protocols");
/*    */   }
/*    */ 
/*    */   
/*    */   public ApplicationProtocolConfig.Protocol protocol() {
/* 42 */     return ApplicationProtocolConfig.Protocol.NPN;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> protocols() {
/* 47 */     return this.protocols;
/*    */   }
/*    */ 
/*    */   
/*    */   public ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior() {
/* 52 */     return ApplicationProtocolConfig.SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
/*    */   }
/*    */ 
/*    */   
/*    */   public ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
/* 57 */     return ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslNpnApplicationProtocolNegotiator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */