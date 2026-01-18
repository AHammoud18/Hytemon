/*     */ package com.hypixel.hytale.server.core.command.system;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class ParseResult
/*     */ {
/*     */   private boolean failed;
/*     */   @Nullable
/*     */   private List<Message> reasons;
/*     */   private final boolean throwExceptionWhenFailed;
/*     */   
/*     */   public ParseResult() {
/*  38 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParseResult(boolean throwExceptionWhenFailed) {
/*  47 */     this.throwExceptionWhenFailed = throwExceptionWhenFailed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fail(@Nonnull Message reason, @Nullable Message... otherMessages) {
/*  58 */     this.failed = true;
/*  59 */     if (this.reasons == null) this.reasons = (List<Message>)new ObjectArrayList();
/*     */     
/*  61 */     this.reasons.add(reason);
/*  62 */     if (otherMessages != null) Collections.addAll(this.reasons, otherMessages);
/*     */     
/*  64 */     if (this.throwExceptionWhenFailed) {
/*  65 */       StringBuilder builder = new StringBuilder(reason.getAnsiMessage());
/*  66 */       if (otherMessages != null) {
/*  67 */         for (Message otherMessage : otherMessages) {
/*  68 */           builder.append("\n").append(otherMessage.getAnsiMessage());
/*     */         }
/*     */       }
/*  71 */       throw new GeneralCommandException(Message.raw(builder.toString()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fail(@Nonnull Message reason) {
/*  82 */     fail(reason, (Message[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean failed() {
/*  89 */     return this.failed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessages(@Nonnull CommandSender sender) {
/*  98 */     if (this.reasons == null)
/*     */       return; 
/* 100 */     for (Message reason : this.reasons)
/* 101 */       sender.sendMessage(reason); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\ParseResult.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */