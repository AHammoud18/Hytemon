/*     */ package com.hypixel.hytale.server.core.command.system;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Tokenizer
/*     */ {
/*     */   public static final char MULTI_ARG_SEPARATOR_CHAR = ',';
/*     */   public static final char MULTI_ARG_BEGIN_CHAR = '[';
/*     */   public static final char MULTI_ARG_END_CHAR = ']';
/*  19 */   public static final String MULTI_ARG_SEPARATOR = String.valueOf(',');
/*  20 */   public static final String MULTI_ARG_BEGIN = String.valueOf('[');
/*  21 */   public static final String MULTI_ARG_END = String.valueOf(']');
/*     */   
/*  23 */   private static final Message MESSAGE_COMMANDS_PARSING_ERROR_UNBALANCED_QUOTES = Message.translation("server.commands.parsing.error.unbalancedQuotes");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static List<String> parseArguments(@Nonnull String input, @Nonnull ParseResult parseResult) {
/*  31 */     ObjectArrayList<String> objectArrayList = new ObjectArrayList();
/*     */ 
/*     */     
/*  34 */     String[] firstSplit = input.split(Pattern.quote(" "), 2);
/*  35 */     objectArrayList.add(firstSplit[0]);
/*     */ 
/*     */     
/*  38 */     if (firstSplit.length == 1) {
/*  39 */       return (List<String>)objectArrayList;
/*     */     }
/*     */ 
/*     */     
/*  43 */     String argsStr = firstSplit[1];
/*     */ 
/*     */     
/*  46 */     char quote = Character.MIN_VALUE;
/*     */ 
/*     */     
/*  49 */     int tokenStart = 0;
/*     */ 
/*     */     
/*  52 */     boolean inList = false;
/*     */     
/*  54 */     for (int i = 0; i < argsStr.length(); i++) {
/*  55 */       char nextCharacter; String extraction; char c = argsStr.charAt(i);
/*     */       
/*  57 */       boolean extractToken = false;
/*     */       
/*  59 */       switch (c) {
/*     */ 
/*     */         
/*     */         case '\\':
/*  63 */           if (argsStr.length() <= i + 1) {
/*  64 */             parseResult.fail(Message.translation("server.commands.parsing.error.invalidEscape")
/*  65 */                 .param("index", i + 1)
/*  66 */                 .param("input", input));
/*  67 */             return null;
/*     */           } 
/*     */ 
/*     */           
/*  71 */           nextCharacter = argsStr.charAt(i + 1);
/*  72 */           switch (nextCharacter) { case '"': case '\'': case ',':
/*     */             case '[':
/*     */             case '\\':
/*     */             case ']':
/*  76 */               argsStr = argsStr.substring(0, i) + argsStr.substring(0, i);
/*  77 */               i++;
/*     */               break; }
/*     */ 
/*     */           
/*  81 */           parseResult.fail(Message.translation("server.commands.parsing.error.invalidEscapeForSymbol").param("symbol", nextCharacter)
/*  82 */               .param("index", i + 1).param("input", input).param("command", input));
/*  83 */           return null;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case ' ':
/*  89 */           if (quote != '\000') {
/*     */             break;
/*     */           }
/*     */           
/*  93 */           if (tokenStart < i) objectArrayList.add(argsStr.substring(tokenStart, i));
/*     */ 
/*     */ 
/*     */           
/*  97 */           tokenStart = i + 1;
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case '"':
/* 103 */           if (quote == '\000') {
/* 104 */             quote = '"';
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 110 */           if (quote == '"') {
/* 111 */             quote = Character.MIN_VALUE;
/*     */             
/* 113 */             String str = argsStr.substring(tokenStart, i + 1);
/* 114 */             if (!str.isEmpty()) objectArrayList.add(str); 
/* 115 */             tokenStart = i + 1;
/*     */           } 
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case '\'':
/* 122 */           if (quote == '\000') {
/* 123 */             quote = '\'';
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 129 */           if (quote == '\'') {
/* 130 */             quote = Character.MIN_VALUE;
/*     */             
/* 132 */             String str = argsStr.substring(tokenStart, i + 1);
/* 133 */             if (!str.isEmpty()) objectArrayList.add(str); 
/* 134 */             tokenStart = i + 1;
/*     */           } 
/*     */           break;
/*     */         
/*     */         case '[':
/* 139 */           if (quote != '\000') {
/*     */             break;
/*     */           }
/* 142 */           if (inList) {
/* 143 */             parseResult.fail(Message.translation("server.commands.parsing.error.cannotBeginListInsideList")
/* 144 */                 .param("index", i));
/* 145 */             return null;
/*     */           } 
/* 147 */           inList = true;
/*     */           
/* 149 */           tokenStart = i;
/* 150 */           extractToken = true;
/*     */           break;
/*     */         
/*     */         case ']':
/* 154 */           if (quote != '\000') {
/*     */             break;
/*     */           }
/* 157 */           if (!inList) {
/* 158 */             parseResult.fail(Message.translation("server.commands.parsing.error.cannotEndListWithoutStarting")
/* 159 */                 .param("index", i));
/* 160 */             return null;
/*     */           } 
/*     */ 
/*     */           
/* 164 */           extraction = argsStr.substring(tokenStart, i);
/* 165 */           if (!extraction.isEmpty()) objectArrayList.add(extraction); 
/* 166 */           tokenStart = i;
/*     */           
/* 168 */           inList = false;
/*     */ 
/*     */           
/* 171 */           extractToken = true;
/*     */           break;
/*     */         
/*     */         case ',':
/* 175 */           if (quote != '\000') {
/*     */             break;
/*     */           }
/* 178 */           extraction = argsStr.substring(tokenStart, i);
/* 179 */           if (!extraction.isEmpty()) objectArrayList.add(extraction); 
/* 180 */           tokenStart = i;
/*     */ 
/*     */           
/* 183 */           extractToken = true;
/*     */           break;
/*     */       } 
/*     */       
/* 187 */       if (extractToken) {
/* 188 */         objectArrayList.add(argsStr.substring(tokenStart, i + 1));
/* 189 */         tokenStart = i + 1;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 194 */       if (tokenStart > argsStr.length()) {
/* 195 */         tokenStart = argsStr.length();
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 201 */     if (quote != '\000') {
/* 202 */       parseResult.fail(MESSAGE_COMMANDS_PARSING_ERROR_UNBALANCED_QUOTES);
/* 203 */       return null;
/*     */     } 
/*     */     
/* 206 */     if (tokenStart != argsStr.length()) {
/* 207 */       objectArrayList.add(argsStr.substring(tokenStart));
/*     */     }
/*     */     
/* 210 */     return (List<String>)objectArrayList;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\Tokenizer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */