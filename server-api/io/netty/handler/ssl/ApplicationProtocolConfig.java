/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ public final class ApplicationProtocolConfig
/*     */ {
/*  35 */   public static final ApplicationProtocolConfig DISABLED = new ApplicationProtocolConfig();
/*     */ 
/*     */   
/*     */   private final List<String> supportedProtocols;
/*     */ 
/*     */   
/*     */   private final Protocol protocol;
/*     */ 
/*     */   
/*     */   private final SelectorFailureBehavior selectorBehavior;
/*     */ 
/*     */   
/*     */   private final SelectedListenerFailureBehavior selectedBehavior;
/*     */ 
/*     */   
/*     */   public ApplicationProtocolConfig(Protocol protocol, SelectorFailureBehavior selectorBehavior, SelectedListenerFailureBehavior selectedBehavior, Iterable<String> supportedProtocols) {
/*  51 */     this(protocol, selectorBehavior, selectedBehavior, ApplicationProtocolUtil.toList(supportedProtocols));
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
/*     */   public ApplicationProtocolConfig(Protocol protocol, SelectorFailureBehavior selectorBehavior, SelectedListenerFailureBehavior selectedBehavior, String... supportedProtocols) {
/*  63 */     this(protocol, selectorBehavior, selectedBehavior, ApplicationProtocolUtil.toList(supportedProtocols));
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
/*     */   private ApplicationProtocolConfig(Protocol protocol, SelectorFailureBehavior selectorBehavior, SelectedListenerFailureBehavior selectedBehavior, List<String> supportedProtocols) {
/*  76 */     this.supportedProtocols = Collections.unmodifiableList((List<? extends String>)ObjectUtil.checkNotNull(supportedProtocols, "supportedProtocols"));
/*  77 */     this.protocol = (Protocol)ObjectUtil.checkNotNull(protocol, "protocol");
/*  78 */     this.selectorBehavior = (SelectorFailureBehavior)ObjectUtil.checkNotNull(selectorBehavior, "selectorBehavior");
/*  79 */     this.selectedBehavior = (SelectedListenerFailureBehavior)ObjectUtil.checkNotNull(selectedBehavior, "selectedBehavior");
/*     */     
/*  81 */     if (protocol == Protocol.NONE) {
/*  82 */       throw new IllegalArgumentException("protocol (" + Protocol.NONE + ") must not be " + Protocol.NONE + '.');
/*     */     }
/*  84 */     ObjectUtil.checkNonEmpty(supportedProtocols, "supportedProtocols");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ApplicationProtocolConfig() {
/*  91 */     this.supportedProtocols = Collections.emptyList();
/*  92 */     this.protocol = Protocol.NONE;
/*  93 */     this.selectorBehavior = SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
/*  94 */     this.selectedBehavior = SelectedListenerFailureBehavior.ACCEPT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Protocol
/*     */   {
/* 101 */     NONE, NPN, ALPN, NPN_AND_ALPN;
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
/*     */   public enum SelectorFailureBehavior
/*     */   {
/* 115 */     FATAL_ALERT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     NO_ADVERTISE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     CHOOSE_MY_LAST_PROTOCOL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum SelectedListenerFailureBehavior
/*     */   {
/* 142 */     ACCEPT,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     FATAL_ALERT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 154 */     CHOOSE_MY_LAST_PROTOCOL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> supportedProtocols() {
/* 161 */     return this.supportedProtocols;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Protocol protocol() {
/* 168 */     return this.protocol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SelectorFailureBehavior selectorFailureBehavior() {
/* 175 */     return this.selectorBehavior;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
/* 182 */     return this.selectedBehavior;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\ApplicationProtocolConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */