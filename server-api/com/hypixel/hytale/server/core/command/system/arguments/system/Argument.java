/*     */ package com.hypixel.hytale.server.core.command.system.arguments.system;
/*     */ 
/*     */ import com.hypixel.hytale.codec.EmptyExtraInfo;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandValidationResults;
/*     */ import com.hypixel.hytale.server.core.command.system.ParseResult;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.suggestion.SuggestionProvider;
/*     */ import com.hypixel.hytale.server.core.command.system.suggestion.SuggestionResult;
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
/*     */ public abstract class Argument<Arg extends Argument<Arg, DataType>, DataType>
/*     */ {
/*     */   @Nonnull
/*     */   private final String name;
/*     */   @Nullable
/*     */   private final String description;
/*     */   private final ArgumentType<DataType> argumentType;
/*     */   @Nullable
/*     */   private SuggestionProvider suggestionProvider;
/*     */   @Nullable
/*     */   private List<Validator<DataType>> validators;
/*     */   @Nonnull
/*     */   private final AbstractCommand commandRegisteredTo;
/*     */   
/*     */   Argument(@Nonnull AbstractCommand commandRegisteredTo, @Nonnull String name, @Nullable String description, @Nonnull ArgumentType<DataType> argumentType) {
/*  74 */     this.commandRegisteredTo = commandRegisteredTo;
/*  75 */     this.name = name;
/*  76 */     this.description = description;
/*  77 */     this.argumentType = argumentType;
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
/*     */   public Arg addValidator(@Nonnull Validator<DataType> validator) {
/*  91 */     if (this.commandRegisteredTo.hasBeenRegistered()) throw new IllegalStateException("Cannot add validators after command has already completed registration");
/*     */     
/*  93 */     if (this.validators == null) {
/*  94 */       this.validators = (List<Validator<DataType>>)new ObjectArrayList();
/*     */     }
/*  96 */     this.validators.add(validator);
/*  97 */     return getThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate(@Nonnull DataType data, @Nonnull ParseResult parseResult) {
/* 108 */     if (this.validators == null)
/*     */       return; 
/* 110 */     CommandValidationResults results = new CommandValidationResults((ExtraInfo)EmptyExtraInfo.EMPTY);
/* 111 */     for (Validator<DataType> validator : this.validators) {
/* 112 */       validator.accept(data, (ValidationResults)results);
/*     */     }
/*     */     
/* 115 */     results.processResults(parseResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean provided(@Nonnull CommandContext context) {
/* 125 */     return context.provided(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataType get(@Nonnull CommandContext context) {
/* 135 */     return (DataType)context.get(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected abstract Arg getThis();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public DataType getProcessed(@Nonnull CommandContext context) {
/* 156 */     return (DataType)this.argumentType.processedGet(context.sender(), context, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Arg suggest(@Nonnull SuggestionProvider suggestionProvider) {
/* 167 */     if (this.commandRegisteredTo.hasBeenRegistered()) {
/* 168 */       throw new IllegalStateException("Cannot add a SuggestionProvider after command has already completed registration");
/*     */     }
/*     */     
/* 171 */     this.suggestionProvider = suggestionProvider;
/* 172 */     return getThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<String> getSuggestions(@Nonnull CommandSender sender, @Nonnull String[] textAlreadyEntered) {
/* 184 */     SuggestionResult suggestionResult = new SuggestionResult();
/* 185 */     String textAlreadyEnteredAsSingleString = String.join(" ", (CharSequence[])textAlreadyEntered);
/*     */     
/* 187 */     if (this.suggestionProvider != null) {
/* 188 */       this.suggestionProvider.suggest(sender, textAlreadyEnteredAsSingleString, textAlreadyEntered.length, suggestionResult);
/*     */     }
/*     */     
/* 191 */     this.argumentType.suggest(sender, textAlreadyEnteredAsSingleString, textAlreadyEntered.length, suggestionResult);
/*     */     
/* 193 */     return suggestionResult.getSuggestions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public abstract Message getUsageMessage();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public abstract Message getUsageOneLiner();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public AbstractCommand getCommandRegisteredTo() {
/* 223 */     return this.commandRegisteredTo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getName() {
/* 231 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ArgumentType<DataType> getArgumentType() {
/* 239 */     return this.argumentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getDescription() {
/* 247 */     return this.description;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 253 */     return "Argument{name='" + this.name + "', description='" + this.description + "', argumentType=" + String.valueOf(this.argumentType) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\system\Argument.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */