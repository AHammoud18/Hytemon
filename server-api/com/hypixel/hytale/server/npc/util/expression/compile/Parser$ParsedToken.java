/*    */ package com.hypixel.hytale.server.npc.util.expression.compile;
/*    */ 
/*    */ import java.text.ParseException;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class ParsedToken
/*    */ {
/*    */   @Nullable
/*    */   public Token token;
/*    */   @Nullable
/*    */   public String tokenString;
/*    */   public double tokenNumber;
/*    */   public int tokenPosition;
/*    */   public int operandCount;
/*    */   public boolean isTuple;
/*    */   public boolean isFunctionCall;
/*    */   public int tupleLength;
/*    */   
/*    */   public ParsedToken(@Nonnull LexerContext<Token> context) {
/* 40 */     this(context.getToken());
/* 41 */     this.tokenString = context.getTokenString();
/* 42 */     this.tokenNumber = context.getTokenNumber();
/* 43 */     this.tokenPosition = context.getTokenPosition();
/*    */   }
/*    */   
/*    */   public ParsedToken(Token token) {
/* 47 */     this.token = token;
/* 48 */     this.tokenString = null;
/* 49 */     this.tokenNumber = 0.0D;
/* 50 */     this.tokenPosition = 0;
/* 51 */     this.operandCount = 0;
/* 52 */     this.isTuple = false;
/* 53 */     this.isFunctionCall = false;
/* 54 */     this.tupleLength = 0;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   static ParsedToken fromLexer(@Nonnull Lexer<Token> lexer, @Nonnull LexerContext<Token> context) throws ParseException {
/* 59 */     lexer.nextToken(context);
/* 60 */     return new ParsedToken(context);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\Parser$ParsedToken.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */