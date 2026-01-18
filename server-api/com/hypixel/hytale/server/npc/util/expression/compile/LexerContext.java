/*     */ package com.hypixel.hytale.server.npc.util.expression.compile;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LexerContext<Token>
/*     */ {
/*     */   private String expression;
/*     */   private int length;
/*     */   private int position;
/*     */   private Token token;
/*     */   private int tokenPosition;
/*     */   @Nonnull
/*  18 */   private StringBuilder tokenString = new StringBuilder(200);
/*     */   
/*     */   private double tokenNumber;
/*     */ 
/*     */   
/*     */   public void init(@Nonnull String expression) {
/*  24 */     this.expression = expression;
/*  25 */     this.length = expression.length();
/*  26 */     this.position = 0;
/*     */   }
/*     */   
/*     */   public void resetToken() {
/*  30 */     this.tokenPosition = this.position;
/*  31 */     this.tokenString.setLength(0);
/*     */   }
/*     */   
/*     */   public Token setToken(Token token) {
/*  35 */     this.token = token;
/*  36 */     return token;
/*     */   }
/*     */   
/*     */   public String getExpression() {
/*  40 */     return this.expression;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Token getToken() {
/*  47 */     return this.token;
/*     */   }
/*     */   
/*     */   public int getTokenPosition() {
/*  51 */     return this.tokenPosition;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getTokenString() {
/*  56 */     return this.tokenString.toString();
/*     */   }
/*     */   
/*     */   public double getTokenNumber() {
/*  60 */     return this.tokenNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected char nextChar(String error) throws ParseException {
/*  67 */     this.position++;
/*  68 */     if (this.position >= this.length) {
/*  69 */       throw new ParseException(error, this.tokenPosition);
/*     */     }
/*  71 */     return this.expression.charAt(this.position);
/*     */   }
/*     */   
/*     */   protected boolean haveChar() {
/*  75 */     return (this.position < this.length);
/*     */   }
/*     */   
/*     */   protected char currentChar() {
/*  79 */     return this.expression.charAt(this.position);
/*     */   }
/*     */   
/*     */   protected char peekChar(char defaultChar) {
/*  83 */     return (this.position < this.length) ? currentChar() : defaultChar;
/*     */   }
/*     */   
/*     */   protected char peekChar() {
/*  87 */     return (this.position < this.length) ? currentChar() : Character.MIN_VALUE;
/*     */   }
/*     */   
/*     */   protected char peekChar(int lookahead, char defaultChar) {
/*  91 */     return (this.position + lookahead < this.length) ? this.expression.charAt(this.position + lookahead) : defaultChar;
/*     */   }
/*     */   
/*     */   protected char peekChar(int lookahead) {
/*  95 */     return (this.position + lookahead < this.length) ? this.expression.charAt(this.position + lookahead) : Character.MIN_VALUE;
/*     */   }
/*     */   
/*     */   protected boolean eatWhiteSpace() {
/*  99 */     while (this.position < this.length && Character.isWhitespace(this.expression.charAt(this.position))) {
/* 100 */       this.position++;
/*     */     }
/* 102 */     return (this.position < this.length);
/*     */   }
/*     */   
/*     */   protected char addTokenCharacter(char ch) {
/* 106 */     this.tokenString.append(ch);
/* 107 */     this.position++;
/* 108 */     return peekChar();
/*     */   }
/*     */   
/*     */   protected int getPosition() {
/* 112 */     return this.position;
/*     */   }
/*     */   
/*     */   protected void setPosition(int position) {
/* 116 */     this.position = position;
/*     */   }
/*     */   
/*     */   protected void adjustPosition(int newPosition) {
/* 120 */     if (newPosition < this.position) {
/* 121 */       this.tokenString.setLength(this.tokenString.length() - this.position - newPosition);
/*     */     }
/* 123 */     this.position = newPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isNumber(char firstLetter) {
/* 130 */     return (Character.isDigit(firstLetter) || (firstLetter == '.' && Character.isDigit(peekChar(1, false))));
/*     */   }
/*     */   
/*     */   protected void parseNumber(char firstChar) throws ParseException {
/* 134 */     char ch = firstChar;
/*     */ 
/*     */ 
/*     */     
/* 138 */     this.tokenNumber = 0.0D;
/* 139 */     ch = copyDigits(ch);
/* 140 */     if (this.position < this.length && ch == '.') {
/* 141 */       this.tokenString.append(ch);
/* 142 */       this.position++;
/* 143 */       if (!Character.isDigit(currentChar())) {
/* 144 */         throw new ParseException("Invalid number format", this.tokenPosition);
/*     */       }
/* 146 */       ch = copyDigits(ch);
/*     */     } 
/* 148 */     if (this.position < this.length && (ch == 'e' || ch == 'E')) {
/* 149 */       this.tokenString.append(ch);
/*     */       
/* 151 */       ch = nextChar("Invalid number format");
/* 152 */       if (ch == '-' || ch == '+') {
/* 153 */         this.tokenString.append(ch);
/* 154 */         ch = nextChar("Invalid number format");
/*     */       } 
/*     */       
/* 157 */       if (!Character.isDigit(ch)) {
/* 158 */         throw new ParseException("Invalid number format", this.tokenPosition);
/*     */       }
/* 160 */       copyDigits(ch);
/*     */     } 
/* 162 */     this.tokenNumber = Double.parseDouble(this.tokenString.toString());
/*     */   }
/*     */   
/*     */   private char copyDigits(char ch) {
/* 166 */     while (this.position < this.length) {
/* 167 */       ch = currentChar();
/* 168 */       if (!Character.isDigit(ch))
/* 169 */         break;  this.tokenString.append(ch);
/* 170 */       this.position++;
/*     */     } 
/* 172 */     return ch;
/*     */   }
/*     */   
/*     */   protected void parseIdent(char firstLetter) {
/* 176 */     this.tokenString.append(firstLetter);
/* 177 */     this.position++;
/*     */     
/* 179 */     while (this.position < this.length && (Character.isLetterOrDigit(currentChar()) || currentChar() == '_')) {
/* 180 */       this.tokenString.append(currentChar());
/* 181 */       this.position++;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void parseString(char delimiter) throws ParseException {
/* 186 */     this.tokenPosition = this.position;
/* 187 */     char ch = nextChar("Unterminated string");
/*     */     
/* 189 */     while (ch != delimiter) {
/* 190 */       this.tokenString.append((ch != '\\') ? ch : nextChar("Unterminated string"));
/* 191 */       ch = nextChar("Unterminated string");
/*     */     } 
/* 193 */     this.position++;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\LexerContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */