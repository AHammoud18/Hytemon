/*     */ package io.netty.bootstrap;
/*     */ 
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.ServerChannel;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.Map;
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
/*     */ public final class ServerBootstrapConfig
/*     */   extends AbstractBootstrapConfig<ServerBootstrap, ServerChannel>
/*     */ {
/*     */   ServerBootstrapConfig(ServerBootstrap bootstrap) {
/*  33 */     super(bootstrap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EventLoopGroup childGroup() {
/*  42 */     return this.bootstrap.childGroup();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelHandler childHandler() {
/*  50 */     return this.bootstrap.childHandler();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> childOptions() {
/*  57 */     return this.bootstrap.childOptions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<AttributeKey<?>, Object> childAttrs() {
/*  64 */     return this.bootstrap.childAttrs();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  69 */     StringBuilder buf = new StringBuilder(super.toString());
/*  70 */     buf.setLength(buf.length() - 1);
/*  71 */     buf.append(", ");
/*  72 */     EventLoopGroup childGroup = childGroup();
/*  73 */     if (childGroup != null) {
/*  74 */       buf.append("childGroup: ");
/*  75 */       buf.append(StringUtil.simpleClassName(childGroup));
/*  76 */       buf.append(", ");
/*     */     } 
/*  78 */     Map<ChannelOption<?>, Object> childOptions = childOptions();
/*  79 */     if (!childOptions.isEmpty()) {
/*  80 */       buf.append("childOptions: ");
/*  81 */       buf.append(childOptions);
/*  82 */       buf.append(", ");
/*     */     } 
/*  84 */     Map<AttributeKey<?>, Object> childAttrs = childAttrs();
/*  85 */     if (!childAttrs.isEmpty()) {
/*  86 */       buf.append("childAttrs: ");
/*  87 */       buf.append(childAttrs);
/*  88 */       buf.append(", ");
/*     */     } 
/*  90 */     ChannelHandler childHandler = childHandler();
/*  91 */     if (childHandler != null) {
/*  92 */       buf.append("childHandler: ");
/*  93 */       buf.append(childHandler);
/*  94 */       buf.append(", ");
/*     */     } 
/*  96 */     if (buf.charAt(buf.length() - 1) == '(') {
/*  97 */       buf.append(')');
/*     */     } else {
/*  99 */       buf.setCharAt(buf.length() - 2, ')');
/* 100 */       buf.setLength(buf.length() - 1);
/*     */     } 
/*     */     
/* 103 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\bootstrap\ServerBootstrapConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */