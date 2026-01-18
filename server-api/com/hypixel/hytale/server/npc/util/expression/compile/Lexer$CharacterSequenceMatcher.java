/*     */ package com.hypixel.hytale.server.npc.util.expression.compile;
/*     */ 
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
/*     */ public class CharacterSequenceMatcher<Token>
/*     */ {
/*     */   @Nullable
/*     */   public Token token;
/*     */   public char letter;
/*     */   @Nullable
/*     */   public List<CharacterSequenceMatcher<Token>> children;
/*     */   
/*     */   public CharacterSequenceMatcher() {
/*  45 */     this.token = null;
/*  46 */     this.letter = Character.MIN_VALUE;
/*  47 */     this.children = null;
/*     */   }
/*     */   
/*     */   public CharacterSequenceMatcher(char letter) {
/*  51 */     this.token = null;
/*  52 */     this.letter = letter;
/*  53 */     this.children = null;
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
/*     */   protected void addToken(Token token, int depth, @Nonnull String text, int maxDepth) {
/*  65 */     char ch = text.charAt(depth);
/*  66 */     if (this.children == null) {
/*     */       
/*  68 */       this.children = (List<CharacterSequenceMatcher<Token>>)new ObjectArrayList();
/*  69 */       append(token, depth, text, maxDepth, ch);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  74 */     int index = 0;
/*  75 */     int size = this.children.size();
/*  76 */     while (index < size && ((CharacterSequenceMatcher)this.children.get(index)).letter < ch) {
/*  77 */       index++;
/*     */     }
/*     */     
/*  80 */     if (index == size) {
/*     */       
/*  82 */       append(token, depth, text, maxDepth, ch);
/*     */     } else {
/*  84 */       CharacterSequenceMatcher<Token> child = this.children.get(index);
/*  85 */       if (child.letter == ch) {
/*     */         
/*  87 */         if (depth == maxDepth) {
/*     */           
/*  89 */           if (child.token != null) {
/*  90 */             throw new RuntimeException("Duplicate operator " + text);
/*     */           }
/*  92 */           child.token = token;
/*     */         } else {
/*     */           
/*  95 */           child.addToken(token, depth + 1, text, maxDepth);
/*     */         } 
/*     */       } else {
/*     */         
/*  99 */         CharacterSequenceMatcher<Token> lookup = new CharacterSequenceMatcher(ch);
/* 100 */         this.children.add(index, lookup);
/* 101 */         addTail(token, depth, text, maxDepth, lookup);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addToken(Token token, @Nonnull String text) {
/* 113 */     addToken(token, 0, text, text.length() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   private void append(Token token, int depth, @Nonnull String text, int maxDepth, char ch) {
/* 118 */     CharacterSequenceMatcher<Token> lookup = new CharacterSequenceMatcher(ch);
/* 119 */     this.children.add(lookup);
/* 120 */     addTail(token, depth, text, maxDepth, lookup);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addTail(Token token, int depth, @Nonnull String text, int maxDepth, @Nonnull CharacterSequenceMatcher<Token> lookup) {
/* 125 */     if (depth == maxDepth) {
/* 126 */       lookup.token = token;
/*     */     } else {
/* 128 */       lookup.addToken(token, depth + 1, text, maxDepth);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected CharacterSequenceMatcher<Token> matchLetter(char ch) {
/* 140 */     if (this.children != null) {
/* 141 */       int index = 0;
/* 142 */       int size = this.children.size();
/* 143 */       while (index < size) {
/* 144 */         CharacterSequenceMatcher<Token> characterSequenceMatcher = this.children.get(index);
/* 145 */         char letter = characterSequenceMatcher.letter;
/* 146 */         if (letter == ch)
/* 147 */           return characterSequenceMatcher; 
/* 148 */         if (letter > ch) {
/* 149 */           return null;
/*     */         }
/* 151 */         index++;
/*     */       } 
/*     */     } 
/* 154 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\Lexer$CharacterSequenceMatcher.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */