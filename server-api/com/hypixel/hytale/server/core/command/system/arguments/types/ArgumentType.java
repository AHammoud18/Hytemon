/*     */ package com.hypixel.hytale.server.core.command.system.arguments.types;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*     */ import com.hypixel.hytale.server.core.command.system.ParseResult;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.Argument;
/*     */ import com.hypixel.hytale.server.core.command.system.suggestion.SuggestionProvider;
/*     */ import com.hypixel.hytale.server.core.command.system.suggestion.SuggestionResult;
/*     */ import java.util.Arrays;
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
/*     */ public abstract class ArgumentType<DataType>
/*     */   implements SuggestionProvider
/*     */ {
/*     */   @Nonnull
/*  29 */   public static final String[] EMPTY_EXAMPLES = new String[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Message name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Message argumentUsage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected final String[] examples;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int numberOfParameters;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ArgumentType(@Nonnull Message name, @Nonnull Message argumentUsage, int numberOfParameters, @Nullable String... examples) {
/*  70 */     this.name = name;
/*  71 */     this.argumentUsage = argumentUsage;
/*  72 */     this.numberOfParameters = numberOfParameters;
/*     */     
/*  74 */     if (numberOfParameters < 0) {
/*  75 */       throw new IllegalArgumentException("You cannot have less than 0 parameters for a argument type");
/*     */     }
/*     */     
/*  78 */     this.examples = (examples == null) ? EMPTY_EXAMPLES : examples;
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
/*     */   protected ArgumentType(@Nonnull String name, @Nonnull Message argumentUsage, int numberOfParameters, @Nullable String... examples) {
/*  90 */     this(Message.translation(name), argumentUsage, numberOfParameters, examples);
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
/*     */   protected ArgumentType(String name, @Nonnull String argumentUsage, int numberOfParameters, @Nullable String... examples) {
/* 102 */     this(Message.translation(name), Message.translation(argumentUsage), numberOfParameters, examples);
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
/*     */   @Nullable
/*     */   public DataType processedGet(CommandSender sender, CommandContext context, Argument<?, DataType> argument) {
/* 117 */     throw new UnsupportedOperationException("This method has not yet been implemented in the subclass, please implement it or do not call it");
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
/*     */   public void suggest(@Nonnull CommandSender sender, @Nonnull String textAlreadyEntered, int numParametersTyped, @Nonnull SuggestionResult result) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public abstract DataType parse(@Nonnull String[] paramArrayOfString, @Nonnull ParseResult paramParseResult);
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
/*     */   public Message getArgumentUsage() {
/* 151 */     return this.argumentUsage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfParameters() {
/* 158 */     return this.numberOfParameters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Message getName() {
/* 166 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String[] getExamples() {
/* 174 */     return this.examples;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isListArgument() {
/* 181 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 187 */     return "ArgumentType{name='" + String.valueOf(this.name) + "', argumentUsage=" + String.valueOf(this.argumentUsage) + ", examples=" + 
/*     */ 
/*     */       
/* 190 */       Arrays.toString((Object[])this.examples) + ", numberOfParameters=" + this.numberOfParameters + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\types\ArgumentType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */