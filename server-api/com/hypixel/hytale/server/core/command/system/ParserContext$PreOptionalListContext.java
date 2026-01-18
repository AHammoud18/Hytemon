/*     */ package com.hypixel.hytale.server.core.command.system;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
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
/*     */ public class PreOptionalListContext
/*     */ {
/* 377 */   private final List<String> tokens = (List<String>)new ObjectArrayList();
/*     */   private boolean hasReachedFirstMultiArgSeparator = false;
/* 379 */   private int numTokensPerArgument = 0;
/* 380 */   private int numTokensSinceLastSeparator = 0;
/* 381 */   private int numberOfListItems = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public PreOptionalListContext addToken(@Nonnull String token, @Nonnull ParseResult parseResult) {
/* 387 */     if (token.equals(Tokenizer.MULTI_ARG_SEPARATOR)) {
/*     */       
/* 389 */       if (!this.hasReachedFirstMultiArgSeparator) {
/*     */ 
/*     */ 
/*     */         
/* 393 */         this.hasReachedFirstMultiArgSeparator = true;
/* 394 */         this.numTokensSinceLastSeparator = 0;
/* 395 */         this.numberOfListItems++;
/* 396 */         verifyNumberOfListItems(parseResult);
/* 397 */         return this;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 403 */       if (this.numTokensSinceLastSeparator != this.numTokensPerArgument) {
/* 404 */         this.tokens.add(token);
/* 405 */         parseResult.fail(Message.translation("server.commands.parsing.error.allArgumentsInListNeedSameLength").param("error", getStringRepresentation(true)));
/* 406 */         return null;
/*     */       } 
/*     */       
/* 409 */       this.numTokensSinceLastSeparator = 0;
/* 410 */       this.numberOfListItems++;
/* 411 */       verifyNumberOfListItems(parseResult);
/* 412 */       return this;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 417 */     this.numTokensSinceLastSeparator++;
/*     */ 
/*     */     
/* 420 */     if (this.numberOfListItems == 0) {
/* 421 */       this.numTokensPerArgument++;
/*     */     }
/*     */ 
/*     */     
/* 425 */     if (this.hasReachedFirstMultiArgSeparator && this.numTokensSinceLastSeparator > this.numTokensPerArgument) {
/* 426 */       this.tokens.add(token);
/* 427 */       parseResult.fail(Message.translation("server.commands.parsing.error.allArgumentsInListNeedSameLength").param("error", getStringRepresentation(true)));
/* 428 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 432 */     this.tokens.add(token);
/* 433 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private String getStringRepresentation(boolean asTooLongFailure) {
/* 438 */     StringBuilder stringBuilder = new StringBuilder(Tokenizer.MULTI_ARG_BEGIN);
/* 439 */     for (int i = 0; i < this.tokens.size(); i++) {
/* 440 */       if (i != 0 && i % this.numTokensPerArgument == 0 && i != this.tokens.size() - 1) {
/* 441 */         stringBuilder.append(" ").append(Tokenizer.MULTI_ARG_SEPARATOR);
/*     */       }
/* 443 */       stringBuilder.append(" ").append(this.tokens.get(i));
/*     */     } 
/* 445 */     if (asTooLongFailure) {
/* 446 */       stringBuilder.append("<-- *HERE* ... ]");
/*     */     } else {
/* 448 */       stringBuilder.append(" ]");
/*     */     } 
/* 450 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   public void verifyNumberOfListItems(@Nonnull ParseResult parseResult) {
/* 454 */     if (this.numberOfListItems > 10) {
/* 455 */       parseResult.fail(Message.translation("server.commands.parsing.error.tooManyListItems").param("amount", 10));
/*     */     }
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String[] getTokens() {
/* 461 */     return (String[])this.tokens.toArray(x$0 -> new String[x$0]);
/*     */   }
/*     */   
/*     */   public int getNumTokensPerArgument() {
/* 465 */     return this.numTokensPerArgument;
/*     */   }
/*     */   
/*     */   public int getNumberOfListItems() {
/* 469 */     return this.numberOfListItems;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\ParserContext$PreOptionalListContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */