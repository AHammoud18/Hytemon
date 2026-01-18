/*     */ package com.hypixel.hytale.server.npc.util.expression.compile;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.text.ParseException;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Stream;
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
/*     */ public class Lexer<Token extends Supplier<String>>
/*     */ {
/*     */   public static final String UNTERMINATED_STRING = "Unterminated string";
/*     */   public static final String INVALID_NUMBER_FORMAT = "Invalid number format";
/*     */   public static final String INVALID_CHARACTER_IN_EXPRESSION = "Invalid character in expression :";
/*     */   private final Token tokenEnd;
/*     */   private final Token tokenIdent;
/*     */   private final Token tokenString;
/*     */   private final Token tokenNumber;
/*     */   private final CharacterSequenceMatcher<Token> characterSequenceMatcher;
/*     */   
/*     */   protected static class CharacterSequenceMatcher<Token>
/*     */   {
/*     */     @Nullable
/*     */     public Token token;
/*     */     public char letter;
/*     */     @Nullable
/*     */     public List<CharacterSequenceMatcher<Token>> children;
/*     */     
/*     */     public CharacterSequenceMatcher() {
/*  45 */       this.token = null;
/*  46 */       this.letter = Character.MIN_VALUE;
/*  47 */       this.children = null;
/*     */     }
/*     */     
/*     */     public CharacterSequenceMatcher(char letter) {
/*  51 */       this.token = null;
/*  52 */       this.letter = letter;
/*  53 */       this.children = null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void addToken(Token token, int depth, @Nonnull String text, int maxDepth) {
/*  65 */       char ch = text.charAt(depth);
/*  66 */       if (this.children == null) {
/*     */         
/*  68 */         this.children = (List<CharacterSequenceMatcher<Token>>)new ObjectArrayList();
/*  69 */         append(token, depth, text, maxDepth, ch);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  74 */       int index = 0;
/*  75 */       int size = this.children.size();
/*  76 */       while (index < size && ((CharacterSequenceMatcher)this.children.get(index)).letter < ch) {
/*  77 */         index++;
/*     */       }
/*     */       
/*  80 */       if (index == size) {
/*     */         
/*  82 */         append(token, depth, text, maxDepth, ch);
/*     */       } else {
/*  84 */         CharacterSequenceMatcher<Token> child = this.children.get(index);
/*  85 */         if (child.letter == ch) {
/*     */           
/*  87 */           if (depth == maxDepth) {
/*     */             
/*  89 */             if (child.token != null) {
/*  90 */               throw new RuntimeException("Duplicate operator " + text);
/*     */             }
/*  92 */             child.token = token;
/*     */           } else {
/*     */             
/*  95 */             child.addToken(token, depth + 1, text, maxDepth);
/*     */           } 
/*     */         } else {
/*     */           
/*  99 */           CharacterSequenceMatcher<Token> lookup = new CharacterSequenceMatcher(ch);
/* 100 */           this.children.add(index, lookup);
/* 101 */           addTail(token, depth, text, maxDepth, lookup);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void addToken(Token token, @Nonnull String text) {
/* 113 */       addToken(token, 0, text, text.length() - 1);
/*     */     }
/*     */ 
/*     */     
/*     */     private void append(Token token, int depth, @Nonnull String text, int maxDepth, char ch) {
/* 118 */       CharacterSequenceMatcher<Token> lookup = new CharacterSequenceMatcher(ch);
/* 119 */       this.children.add(lookup);
/* 120 */       addTail(token, depth, text, maxDepth, lookup);
/*     */     }
/*     */ 
/*     */     
/*     */     private void addTail(Token token, int depth, @Nonnull String text, int maxDepth, @Nonnull CharacterSequenceMatcher<Token> lookup) {
/* 125 */       if (depth == maxDepth) {
/* 126 */         lookup.token = token;
/*     */       } else {
/* 128 */         lookup.addToken(token, depth + 1, text, maxDepth);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     protected CharacterSequenceMatcher<Token> matchLetter(char ch) {
/* 140 */       if (this.children != null) {
/* 141 */         int index = 0;
/* 142 */         int size = this.children.size();
/* 143 */         while (index < size) {
/* 144 */           CharacterSequenceMatcher<Token> characterSequenceMatcher = this.children.get(index);
/* 145 */           char letter = characterSequenceMatcher.letter;
/* 146 */           if (letter == ch)
/* 147 */             return characterSequenceMatcher; 
/* 148 */           if (letter > ch) {
/* 149 */             return null;
/*     */           }
/* 151 */           index++;
/*     */         } 
/*     */       } 
/* 154 */       return null;
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
/*     */   public Lexer(Token tokenEnd, Token tokenIdent, Token tokenString, Token tokenNumber, @Nonnull Stream<Token> operators) {
/* 168 */     this.tokenEnd = tokenEnd;
/* 169 */     this.tokenIdent = tokenIdent;
/* 170 */     this.tokenString = tokenString;
/* 171 */     this.tokenNumber = tokenNumber;
/* 172 */     this.characterSequenceMatcher = new CharacterSequenceMatcher<>();
/*     */     
/* 174 */     operators.forEach(token -> this.characterSequenceMatcher.addToken((Token)token, token.get()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Token nextToken(@Nonnull LexerContext<Token> context) throws ParseException {
/* 185 */     context.resetToken();
/* 186 */     if (!context.eatWhiteSpace()) {
/* 187 */       return context.setToken(this.tokenEnd);
/*     */     }
/* 189 */     char ch = context.currentChar();
/* 190 */     if (Character.isLetter(ch) || ch == '_') {
/* 191 */       context.parseIdent(ch);
/* 192 */       return context.setToken(this.tokenIdent);
/* 193 */     }  if (context.isNumber(ch)) {
/* 194 */       context.parseNumber(ch);
/* 195 */       return context.setToken(this.tokenNumber);
/* 196 */     }  if (ch == '"' || ch == '\'') {
/* 197 */       context.parseString(ch);
/* 198 */       return context.setToken(this.tokenString);
/*     */     } 
/*     */     
/* 201 */     CharacterSequenceMatcher<Token> lastTerminal = null;
/* 202 */     CharacterSequenceMatcher<Token> matcher = this.characterSequenceMatcher.matchLetter(ch);
/* 203 */     int lastValidPosition = context.getPosition();
/*     */     
/* 205 */     while (matcher != null) {
/* 206 */       if (matcher.token != null) {
/* 207 */         lastValidPosition = context.getPosition();
/* 208 */         lastTerminal = matcher;
/*     */       } 
/* 210 */       ch = context.addTokenCharacter(ch);
/* 211 */       if (!context.haveChar())
/* 212 */         break;  matcher = matcher.matchLetter(ch);
/*     */     } 
/*     */     
/* 215 */     if (lastTerminal != null) {
/* 216 */       context.adjustPosition(lastValidPosition + 1);
/* 217 */       return context.setToken(lastTerminal.token);
/*     */     } 
/*     */     
/* 220 */     throw new ParseException("Invalid character in expression :" + ch, context.getTokenPosition());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\Lexer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */