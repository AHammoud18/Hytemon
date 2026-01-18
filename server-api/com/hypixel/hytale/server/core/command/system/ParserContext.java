/*     */ package com.hypixel.hytale.server.core.command.system;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParserContext
/*     */ {
/*  24 */   private static final HashSet<String> SPECIAL_TOKENS = new HashSet<>(List.of(Tokenizer.MULTI_ARG_BEGIN, Tokenizer.MULTI_ARG_END, Tokenizer.MULTI_ARG_SEPARATOR));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_LIST_ITEMS = 10;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final String inputString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final BooleanArrayList parameterForwardingMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Int2ObjectMap<String> preOptionalSingleValueTokens;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Int2ObjectMap<PreOptionalListContext> preOptionalListTokens;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Object2ObjectLinkedOpenHashMap<String, List<List<String>>> optionalArgs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String lastInsertedOptionalArgName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int numPreOptSingleValueTokensBeforeListTokens;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int subCommandIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParserContext(@Nonnull List<String> tokens, @Nonnull ParseResult parseResult) {
/* 104 */     this.inputString = String.join(" ", (Iterable)tokens);
/* 105 */     this.parameterForwardingMap = new BooleanArrayList();
/* 106 */     this.preOptionalSingleValueTokens = (Int2ObjectMap<String>)new Int2ObjectOpenHashMap();
/* 107 */     this.preOptionalListTokens = (Int2ObjectMap<PreOptionalListContext>)new Int2ObjectOpenHashMap();
/* 108 */     this.optionalArgs = new Object2ObjectLinkedOpenHashMap();
/* 109 */     contextualizeTokens(tokens, parseResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ParserContext of(@Nonnull List<String> tokens, @Nonnull ParseResult parseResult) {
/* 119 */     return new ParserContext(tokens, parseResult);
/*     */   }
/*     */   
/* 122 */   private static final Pattern ARG_NAME_PATTERN = Pattern.compile("--(\\w*)");
/* 123 */   private static final Matcher ARG_NAME_MATCHER = ARG_NAME_PATTERN.matcher("");
/* 124 */   private static final Pattern ARG_NAME_AND_VALUE_PATTERN = Pattern.compile("--(\\w+)=\"*(.*)\"*");
/* 125 */   private static final Matcher ARG_NAME_AND_VALUE_MATCHER = ARG_NAME_AND_VALUE_PATTERN.matcher("");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void contextualizeTokens(@Nonnull List<String> tokens, @Nonnull ParseResult parseResult) {
/* 134 */     boolean beganParsingOptionals = false;
/*     */     
/* 136 */     boolean inList = false;
/* 137 */     boolean isSingleValueList = false;
/*     */     
/* 139 */     boolean wasLastTokenASpecialValue = false;
/*     */     
/* 141 */     boolean hasEnteredListBefore = false;
/*     */     
/* 143 */     for (int i = 0; i < tokens.size(); i++) {
/* 144 */       String token = tokens.get(i);
/*     */       
/* 146 */       if (inList) {
/* 147 */         hasEnteredListBefore = true;
/*     */       }
/*     */ 
/*     */       
/* 151 */       if (SPECIAL_TOKENS.contains(token)) {
/*     */ 
/*     */         
/* 154 */         boolean isListEndingAndStartingNew = (((String)tokens.get(i - 1)).equals(Tokenizer.MULTI_ARG_END) && !inList && token.equals(Tokenizer.MULTI_ARG_BEGIN));
/* 155 */         if (wasLastTokenASpecialValue && !isListEndingAndStartingNew) {
/* 156 */           StringBuilder stringBuilder = new StringBuilder();
/* 157 */           for (int i1 = 0; i1 < tokens.size(); i1++) {
/* 158 */             stringBuilder.append(tokens.get(i1)).append(" ");
/* 159 */             if (i1 == i) {
/* 160 */               stringBuilder.append(" <--- *HERE!* ");
/*     */             }
/*     */           } 
/* 163 */           parseResult.fail(Message.translation("server.commands.parsing.error.cantDoublePlaceSpecialTokens"), new Message[] { Message.raw(stringBuilder.toString()) });
/*     */           
/*     */           return;
/*     */         } 
/* 167 */         wasLastTokenASpecialValue = true;
/*     */       } else {
/*     */         
/* 170 */         wasLastTokenASpecialValue = false;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 176 */       ARG_NAME_MATCHER.reset(token);
/*     */       
/* 178 */       if (ARG_NAME_MATCHER.lookingAt()) {
/*     */         
/* 180 */         beganParsingOptionals = true;
/*     */ 
/*     */         
/* 183 */         addNewOptionalArg(ARG_NAME_MATCHER.group(1));
/*     */ 
/*     */         
/* 186 */         ARG_NAME_AND_VALUE_MATCHER.reset(token);
/* 187 */         if (ARG_NAME_AND_VALUE_MATCHER.matches()) {
/* 188 */           appendOptionalParameter(ARG_NAME_AND_VALUE_MATCHER.group(2), parseResult);
/*     */           
/* 190 */           if (parseResult.failed())
/*     */           {
/*     */             return;
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 197 */       else if (beganParsingOptionals) {
/* 198 */         appendOptionalParameter(token, parseResult);
/* 199 */         if (parseResult.failed())
/*     */         {
/*     */           return;
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 207 */       else if (token.equals(Tokenizer.MULTI_ARG_BEGIN)) {
/* 208 */         inList = true;
/* 209 */         isSingleValueList = false;
/* 210 */         this.parameterForwardingMap.add(true);
/* 211 */         this.preOptionalListTokens.put(this.parameterForwardingMap.size() - 1, new PreOptionalListContext());
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 216 */       else if (token.equals(Tokenizer.MULTI_ARG_END)) {
/* 217 */         inList = false;
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 222 */       else if (inList) {
/* 223 */         ((PreOptionalListContext)this.preOptionalListTokens.get(this.parameterForwardingMap.size() - 1)).addToken(token, parseResult);
/* 224 */         if (parseResult.failed()) {
/*     */           return;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 230 */         if (isSingleValueList && !wasLastTokenASpecialValue && tokens.size() > i + 1 && !((String)tokens.get(i + 1)).equals(Tokenizer.MULTI_ARG_SEPARATOR)) {
/* 231 */           inList = false;
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 238 */       else if (tokens.size() > i + 1 && ((String)tokens.get(i + 1)).equals(Tokenizer.MULTI_ARG_SEPARATOR)) {
/* 239 */         inList = true;
/* 240 */         isSingleValueList = true;
/* 241 */         this.parameterForwardingMap.add(true);
/* 242 */         this.preOptionalListTokens.put(this.parameterForwardingMap.size() - 1, (new PreOptionalListContext()).addToken(token, parseResult));
/* 243 */         if (parseResult.failed()) {
/*     */           return;
/*     */         }
/*     */       } else {
/*     */         
/* 248 */         if (!hasEnteredListBefore) {
/* 249 */           this.numPreOptSingleValueTokensBeforeListTokens++;
/*     */         }
/*     */         
/* 252 */         this.parameterForwardingMap.add(false);
/* 253 */         this.preOptionalSingleValueTokens.put(this.parameterForwardingMap.size() - 1, token);
/*     */       } 
/*     */     } 
/*     */     
/* 257 */     if (inList && !isSingleValueList) {
/* 258 */       parseResult.fail(Message.translation("server.commands.parsing.error.endCommandWithOpenList").param("listEndToken", Tokenizer.MULTI_ARG_END));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNewOptionalArg(String name) {
/* 268 */     name = name.toLowerCase();
/* 269 */     this.lastInsertedOptionalArgName = name;
/* 270 */     this.optionalArgs.put(name, new ObjectArrayList());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendOptionalParameter(@Nonnull String value, @Nonnull ParseResult parseResult) {
/* 279 */     if (this.optionalArgs.isEmpty() || this.lastInsertedOptionalArgName == null) {
/* 280 */       parseResult.fail(Message.translation("server.commands.parsing.error.noOptionalParameterToAddValueTo"));
/*     */       return;
/*     */     } 
/* 283 */     List<List<String>> args = (List<List<String>>)this.optionalArgs.get(this.lastInsertedOptionalArgName);
/*     */     
/* 285 */     if (value.equals(Tokenizer.MULTI_ARG_BEGIN) || value.equals(Tokenizer.MULTI_ARG_END)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 290 */     if (value.equals(Tokenizer.MULTI_ARG_SEPARATOR)) {
/* 291 */       args.add(new ObjectArrayList());
/* 292 */     } else if (args.isEmpty()) {
/* 293 */       ObjectArrayList<String> values = new ObjectArrayList();
/* 294 */       values.add(value);
/* 295 */       args.add(values);
/*     */     } else {
/* 297 */       ((List<String>)args.getLast()).add(value);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getInputString() {
/* 303 */     return this.inputString;
/*     */   }
/*     */   
/*     */   public boolean isListToken(int index) {
/* 307 */     index += this.subCommandIndex;
/* 308 */     if (this.parameterForwardingMap.size() <= index) {
/* 309 */       return false;
/*     */     }
/* 311 */     return this.parameterForwardingMap.getBoolean(index);
/*     */   }
/*     */   
/*     */   public int getNumPreOptSingleValueTokensBeforeListTokens() {
/* 315 */     return this.numPreOptSingleValueTokensBeforeListTokens - this.subCommandIndex;
/*     */   }
/*     */   
/*     */   public int getNumPreOptionalTokens() {
/* 319 */     int numPreOptionalTokens = 0;
/* 320 */     numPreOptionalTokens += this.preOptionalSingleValueTokens.size();
/* 321 */     for (ObjectIterator<PreOptionalListContext> objectIterator = this.preOptionalListTokens.values().iterator(); objectIterator.hasNext(); ) { PreOptionalListContext value = objectIterator.next();
/* 322 */       numPreOptionalTokens += value.numTokensPerArgument; }
/*     */     
/* 324 */     return numPreOptionalTokens - this.subCommandIndex;
/*     */   }
/*     */   
/*     */   public String getPreOptionalSingleValueToken(int index) {
/* 328 */     index += this.subCommandIndex;
/* 329 */     return (String)this.preOptionalSingleValueTokens.get(index);
/*     */   }
/*     */   
/*     */   public PreOptionalListContext getPreOptionalListToken(int index) {
/* 333 */     index += this.subCommandIndex;
/* 334 */     return (PreOptionalListContext)this.preOptionalListTokens.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getFirstToken() {
/* 345 */     if (this.parameterForwardingMap.size() <= this.subCommandIndex) return null; 
/* 346 */     if (this.parameterForwardingMap.getBoolean(this.subCommandIndex)) {
/* 347 */       PreOptionalListContext preOptionalListContext = (PreOptionalListContext)this.preOptionalListTokens.get(this.subCommandIndex);
/* 348 */       if (preOptionalListContext.tokens.isEmpty()) return null; 
/* 349 */       return preOptionalListContext.tokens.getFirst();
/*     */     } 
/* 351 */     return (String)this.preOptionalSingleValueTokens.get(this.subCommandIndex);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ObjectSortedSet<Map.Entry<String, List<List<String>>>> getOptionalArgs() {
/* 356 */     return this.optionalArgs.entrySet();
/*     */   }
/*     */   
/*     */   public boolean isHelpSpecified() {
/* 360 */     return (this.optionalArgs.containsKey("help") || this.optionalArgs.containsKey("?"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConfirmationSpecified() {
/* 369 */     return this.optionalArgs.containsKey("confirm");
/*     */   }
/*     */   
/*     */   public void convertToSubCommand() {
/* 373 */     this.subCommandIndex++;
/*     */   }
/*     */   
/*     */   public static class PreOptionalListContext {
/* 377 */     private final List<String> tokens = (List<String>)new ObjectArrayList();
/*     */     private boolean hasReachedFirstMultiArgSeparator = false;
/* 379 */     private int numTokensPerArgument = 0;
/* 380 */     private int numTokensSinceLastSeparator = 0;
/* 381 */     private int numberOfListItems = 0;
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public PreOptionalListContext addToken(@Nonnull String token, @Nonnull ParseResult parseResult) {
/* 387 */       if (token.equals(Tokenizer.MULTI_ARG_SEPARATOR)) {
/*     */         
/* 389 */         if (!this.hasReachedFirstMultiArgSeparator) {
/*     */ 
/*     */ 
/*     */           
/* 393 */           this.hasReachedFirstMultiArgSeparator = true;
/* 394 */           this.numTokensSinceLastSeparator = 0;
/* 395 */           this.numberOfListItems++;
/* 396 */           verifyNumberOfListItems(parseResult);
/* 397 */           return this;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 403 */         if (this.numTokensSinceLastSeparator != this.numTokensPerArgument) {
/* 404 */           this.tokens.add(token);
/* 405 */           parseResult.fail(Message.translation("server.commands.parsing.error.allArgumentsInListNeedSameLength").param("error", getStringRepresentation(true)));
/* 406 */           return null;
/*     */         } 
/*     */         
/* 409 */         this.numTokensSinceLastSeparator = 0;
/* 410 */         this.numberOfListItems++;
/* 411 */         verifyNumberOfListItems(parseResult);
/* 412 */         return this;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 417 */       this.numTokensSinceLastSeparator++;
/*     */ 
/*     */       
/* 420 */       if (this.numberOfListItems == 0) {
/* 421 */         this.numTokensPerArgument++;
/*     */       }
/*     */ 
/*     */       
/* 425 */       if (this.hasReachedFirstMultiArgSeparator && this.numTokensSinceLastSeparator > this.numTokensPerArgument) {
/* 426 */         this.tokens.add(token);
/* 427 */         parseResult.fail(Message.translation("server.commands.parsing.error.allArgumentsInListNeedSameLength").param("error", getStringRepresentation(true)));
/* 428 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 432 */       this.tokens.add(token);
/* 433 */       return this;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     private String getStringRepresentation(boolean asTooLongFailure) {
/* 438 */       StringBuilder stringBuilder = new StringBuilder(Tokenizer.MULTI_ARG_BEGIN);
/* 439 */       for (int i = 0; i < this.tokens.size(); i++) {
/* 440 */         if (i != 0 && i % this.numTokensPerArgument == 0 && i != this.tokens.size() - 1) {
/* 441 */           stringBuilder.append(" ").append(Tokenizer.MULTI_ARG_SEPARATOR);
/*     */         }
/* 443 */         stringBuilder.append(" ").append(this.tokens.get(i));
/*     */       } 
/* 445 */       if (asTooLongFailure) {
/* 446 */         stringBuilder.append("<-- *HERE* ... ]");
/*     */       } else {
/* 448 */         stringBuilder.append(" ]");
/*     */       } 
/* 450 */       return stringBuilder.toString();
/*     */     }
/*     */     
/*     */     public void verifyNumberOfListItems(@Nonnull ParseResult parseResult) {
/* 454 */       if (this.numberOfListItems > 10) {
/* 455 */         parseResult.fail(Message.translation("server.commands.parsing.error.tooManyListItems").param("amount", 10));
/*     */       }
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public String[] getTokens() {
/* 461 */       return (String[])this.tokens.toArray(x$0 -> new String[x$0]);
/*     */     }
/*     */     
/*     */     public int getNumTokensPerArgument() {
/* 465 */       return this.numTokensPerArgument;
/*     */     }
/*     */     
/*     */     public int getNumberOfListItems() {
/* 469 */       return this.numberOfListItems;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\ParserContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */