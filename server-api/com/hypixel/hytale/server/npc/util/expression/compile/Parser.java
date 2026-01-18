/*     */ package com.hypixel.hytale.server.npc.util.expression.compile;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import java.util.EnumSet;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Parser
/*     */ {
/*     */   public static final String MISMATCHED_CLOSING_BRACKET = "Mismatched closing bracket";
/*     */   public static final String TOO_MANY_OPERANDS = "Too many operands";
/*     */   public static final String NOT_ENOUGH_OPERANDS = "Not enough operands";
/*     */   public static final String EXPECTED_UNARY_OPERATOR = "Expected unary operator";
/*     */   public static final String EXPECTED_BINARY_OPERATOR = "Expected binary operator";
/*     */   public static final String MISSING_CLOSING_BRACKET = "Missing closing bracket";
/*     */   public static final String ILLEGAL_USE_OF_ARGUMENT_LIST = "Illegal use of argument list";
/*     */   private Lexer<Token> lexer;
/*     */   private LexerContext<Token> context;
/*     */   
/*     */   public static class ParsedToken
/*     */   {
/*     */     @Nullable
/*     */     public Token token;
/*     */     @Nullable
/*     */     public String tokenString;
/*     */     public double tokenNumber;
/*     */     public int tokenPosition;
/*     */     public int operandCount;
/*     */     public boolean isTuple;
/*     */     public boolean isFunctionCall;
/*     */     public int tupleLength;
/*     */     
/*     */     public ParsedToken(@Nonnull LexerContext<Token> context) {
/*  40 */       this(context.getToken());
/*  41 */       this.tokenString = context.getTokenString();
/*  42 */       this.tokenNumber = context.getTokenNumber();
/*  43 */       this.tokenPosition = context.getTokenPosition();
/*     */     }
/*     */     
/*     */     public ParsedToken(Token token) {
/*  47 */       this.token = token;
/*  48 */       this.tokenString = null;
/*  49 */       this.tokenNumber = 0.0D;
/*  50 */       this.tokenPosition = 0;
/*  51 */       this.operandCount = 0;
/*  52 */       this.isTuple = false;
/*  53 */       this.isFunctionCall = false;
/*  54 */       this.tupleLength = 0;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     static ParsedToken fromLexer(@Nonnull Lexer<Token> lexer, @Nonnull LexerContext<Token> context) throws ParseException {
/*  59 */       lexer.nextToken(context);
/*  60 */       return new ParsedToken(context);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 108 */   private Deque<ParsedToken> operatorStack = new ArrayDeque<>();
/*     */   @Nonnull
/* 110 */   private Deque<ParsedToken> bracketStack = new ArrayDeque<>();
/*     */ 
/*     */   
/*     */   public Parser(Lexer<Token> lexer) {
/* 114 */     this.lexer = lexer;
/* 115 */     this.context = new LexerContext<>();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private ParsedToken nextToken() throws ParseException {
/* 120 */     return ParsedToken.fromLexer(this.lexer, this.context);
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
/*     */   public void parse(@Nonnull String expression, @Nonnull ParsedTokenConsumer tokenConsumer) throws ParseException {
/* 132 */     this.operatorStack.clear();
/* 133 */     this.bracketStack.clear();
/* 134 */     this.bracketStack.push(new ParsedToken(Token.END));
/* 135 */     this.context.init(expression);
/* 136 */     ParsedToken parsedToken = nextToken();
/* 137 */     Token token = parsedToken.token;
/* 138 */     Token lastToken = null;
/* 139 */     ParsedToken bracket = this.bracketStack.peek();
/*     */     
/* 141 */     while (!token.isEndToken()) {
/* 142 */       if (token.isOperand()) {
/* 143 */         tokenConsumer.pushOperand(parsedToken);
/* 144 */         bracket.operandCount++;
/* 145 */       } else if (token.isOpenBracket()) {
/* 146 */         if (token == Token.OPEN_BRACKET) {
/* 147 */           if (lastToken == Token.IDENTIFIER) {
/*     */             
/* 149 */             parsedToken.isTuple = true;
/* 150 */             parsedToken.isFunctionCall = true;
/*     */           } 
/* 152 */         } else if (token.isOpenTuple()) {
/* 153 */           parsedToken.isTuple = true;
/* 154 */           parsedToken.isFunctionCall = false;
/*     */         } 
/* 156 */         this.operatorStack.push(parsedToken);
/* 157 */         this.bracketStack.push(parsedToken);
/* 158 */         bracket = this.bracketStack.peek();
/* 159 */       } else if (token.isCloseBracket()) {
/* 160 */         int deltaArity; Token otherBracket = token.getMatchingBracket();
/* 161 */         if (bracket.token != otherBracket) {
/* 162 */           throw new ParseException("Mismatched closing bracket", parsedToken.tokenPosition);
/*     */         }
/*     */         
/* 165 */         ParsedToken first = this.operatorStack.pop();
/* 166 */         while (!first.token.isOpenBracket()) {
/* 167 */           bracket.operandCount = adjustOperandCount(first, bracket.operandCount);
/* 168 */           tokenConsumer.processOperator(first);
/* 169 */           first = this.operatorStack.pop();
/*     */         } 
/* 171 */         validateOperandCount(bracket);
/*     */         
/* 173 */         if (bracket.isFunctionCall) {
/*     */           
/* 175 */           bracket.tupleLength += bracket.operandCount;
/* 176 */           tokenConsumer.processFunction(bracket.tupleLength);
/* 177 */           deltaArity = 0;
/* 178 */         } else if (bracket.isTuple) {
/* 179 */           bracket.tupleLength += bracket.operandCount;
/* 180 */           tokenConsumer.processTuple(bracket, bracket.tupleLength);
/* 181 */           deltaArity = 1;
/*     */         } else {
/* 183 */           deltaArity = 1;
/*     */         } 
/* 185 */         this.bracketStack.pop();
/* 186 */         bracket = this.bracketStack.peek();
/* 187 */         bracket.operandCount += deltaArity;
/* 188 */       } else if (token.isList()) {
/* 189 */         if (!bracket.isTuple) {
/* 190 */           throw new ParseException("Illegal use of argument list", parsedToken.tokenPosition);
/*     */         }
/*     */         
/* 193 */         ParsedToken first = peekOperator();
/* 194 */         while (!first.token.isOpenBracket()) {
/* 195 */           bracket.operandCount = adjustOperandCount(first, bracket.operandCount);
/* 196 */           tokenConsumer.processOperator(first);
/* 197 */           this.operatorStack.pop();
/* 198 */           first = peekOperator();
/*     */         } 
/* 200 */         validateOperandCount(bracket);
/*     */         
/* 202 */         bracket.tupleLength++;
/* 203 */         bracket.operandCount = 0;
/* 204 */       } else if (token.isOperator()) {
/*     */         
/* 206 */         boolean mustBeUnary = (lastToken == null || lastToken.containsAnyFlag(EnumSet.of(TokenFlags.OPERATOR, TokenFlags.LIST, TokenFlags.OPENING_BRACKET)));
/* 207 */         if (token.canBeUnary() && mustBeUnary)
/* 208 */         { token = token.getUnaryVariant();
/* 209 */           parsedToken.token = token; }
/* 210 */         else { if (mustBeUnary && !token.isUnary())
/* 211 */             throw new ParseException("Expected unary operator", parsedToken.tokenPosition); 
/* 212 */           if (token.isUnary() && !mustBeUnary) {
/* 213 */             throw new ParseException("Expected binary operator", parsedToken.tokenPosition);
/*     */           } }
/*     */         
/* 216 */         ParsedToken stackToken = peekOperator();
/* 217 */         while (hasLowerPrecedence(token, stackToken)) {
/* 218 */           bracket.operandCount = adjustOperandCount(stackToken, bracket.operandCount);
/* 219 */           tokenConsumer.processOperator(stackToken);
/* 220 */           this.operatorStack.pop();
/* 221 */           stackToken = peekOperator();
/*     */         } 
/* 223 */         this.operatorStack.push(parsedToken);
/*     */       } else {
/* 225 */         throw new RuntimeException("Internal parser error: " + String.valueOf(token));
/*     */       } 
/*     */       
/* 228 */       lastToken = token;
/* 229 */       parsedToken = nextToken();
/* 230 */       token = parsedToken.token;
/*     */     } 
/* 232 */     if (bracket.token != Token.END) {
/* 233 */       throw new ParseException("Missing closing bracket", bracket.tokenPosition);
/*     */     }
/*     */     
/* 236 */     while (!this.operatorStack.isEmpty()) {
/* 237 */       parsedToken = this.operatorStack.pop();
/* 238 */       bracket.operandCount = adjustOperandCount(parsedToken, bracket.operandCount);
/* 239 */       tokenConsumer.processOperator(parsedToken);
/*     */     } 
/*     */     
/* 242 */     validateOperandCount(bracket);
/* 243 */     tokenConsumer.done();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ParsedToken peekOperator() {
/* 248 */     return this.operatorStack.isEmpty() ? null : this.operatorStack.peek();
/*     */   }
/*     */   
/*     */   private void validateOperandCount(@Nonnull ParsedToken bracket) throws ParseException {
/* 252 */     if (bracket.isTuple && bracket.tupleLength == 0 && bracket.operandCount == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 258 */     if (bracket.operandCount <= 0) {
/* 259 */       throw new ParseException("Not enough operands", 0);
/*     */     }
/* 261 */     if (bracket.operandCount > 1) {
/* 262 */       throw new ParseException("Too many operands", 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private int adjustOperandCount(@Nonnull ParsedToken parsedToken, int operandCount) throws ParseException {
/* 267 */     int requiredOperands = arity(parsedToken.token);
/*     */     
/* 269 */     if (operandCount < requiredOperands) {
/* 270 */       throw new ParseException("Not enough operands", parsedToken.tokenPosition);
/*     */     }
/* 272 */     return operandCount - requiredOperands + 1;
/*     */   }
/*     */   
/*     */   private boolean hasLowerPrecedence(@Nonnull Token token, @Nullable ParsedToken stackToken) {
/* 276 */     if (stackToken == null || stackToken.token.isList() || stackToken.token.isOpenBracket()) {
/* 277 */       return false;
/*     */     }
/* 279 */     int tokenPrecedence = token.getPrecedence();
/* 280 */     int stackTokenPrecedence = stackToken.token.getPrecedence();
/* 281 */     return (tokenPrecedence == stackTokenPrecedence) ? (!token.isRightToLeft()) : ((tokenPrecedence < stackTokenPrecedence));
/*     */   }
/*     */ 
/*     */   
/*     */   private int arity(@Nonnull Token operator) {
/* 286 */     if (!operator.isOperator()) {
/* 287 */       throw new RuntimeException("Arity only possible with operators");
/*     */     }
/* 289 */     return operator.isUnary() ? 1 : 2;
/*     */   }
/*     */   
/*     */   public static interface ParsedTokenConsumer {
/*     */     void pushOperand(Parser.ParsedToken param1ParsedToken);
/*     */     
/*     */     void processOperator(Parser.ParsedToken param1ParsedToken) throws ParseException;
/*     */     
/*     */     void processFunction(int param1Int) throws ParseException;
/*     */     
/*     */     void processTuple(Parser.ParsedToken param1ParsedToken, int param1Int);
/*     */     
/*     */     void done();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\Parser.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */