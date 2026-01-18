/*     */ package com.google.protobuf;
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
/*     */ public final class RpcUtil
/*     */ {
/*     */   public static <Type extends Message> RpcCallback<Type> specializeCallback(RpcCallback<Message> originalCallback) {
/*  25 */     return (RpcCallback)originalCallback;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <Type extends Message> RpcCallback<Message> generalizeCallback(final RpcCallback<Type> originalCallback, final Class<Type> originalClass, final Type defaultInstance) {
/*  47 */     return new RpcCallback<Message>()
/*     */       {
/*     */         public void run(Message parameter) {
/*     */           Message message;
/*     */           try {
/*  52 */             message = originalClass.cast(parameter);
/*  53 */           } catch (ClassCastException ignored) {
/*  54 */             message = (Message)RpcUtil.copyAsType((Type)defaultInstance, parameter);
/*     */           } 
/*  56 */           originalCallback.run(message);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <Type extends Message> Type copyAsType(Type typeDefaultInstance, Message source) {
/*  68 */     return (Type)typeDefaultInstance.newBuilderForType().mergeFrom(source).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <ParameterType> RpcCallback<ParameterType> newOneTimeCallback(final RpcCallback<ParameterType> originalCallback) {
/*  78 */     return new RpcCallback<ParameterType>()
/*     */       {
/*     */         private boolean alreadyCalled = false;
/*     */         
/*     */         public void run(ParameterType parameter) {
/*  83 */           synchronized (this) {
/*  84 */             if (this.alreadyCalled) {
/*  85 */               throw new RpcUtil.AlreadyCalledException();
/*     */             }
/*  87 */             this.alreadyCalled = true;
/*     */           } 
/*     */           
/*  90 */           originalCallback.run(parameter);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static final class AlreadyCalledException
/*     */     extends RuntimeException {
/*     */     private static final long serialVersionUID = 5469741279507848266L;
/*     */     
/*     */     public AlreadyCalledException() {
/* 100 */       super("This RpcCallback was already called and cannot be called multiple times.");
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\RpcUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */