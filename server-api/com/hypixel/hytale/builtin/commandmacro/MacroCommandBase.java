/*     */ package com.hypixel.hytale.builtin.commandmacro;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandManager;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*     */ import com.hypixel.hytale.server.core.command.system.ParseResult;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.Argument;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*     */ import com.hypixel.hytale.server.core.console.ConsoleSender;
/*     */ import it.unimi.dsi.fastutil.Pair;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.logging.Level;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class MacroCommandBase extends AbstractAsyncCommand {
/*  28 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   private static final Pattern regexBracketPattern = Pattern.compile("\\{(.*?)}");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   private final Map<String, Argument<?, ?>> arguments = (Map<String, Argument<?, ?>>)new Object2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private final List<Pair<String, List<MacroCommandReplacement>>> commandReplacements = (List<Pair<String, List<MacroCommandReplacement>>>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   private final Map<String, String> defaultValueStrings = (Map<String, String>)new Object2ObjectOpenHashMap();
/*     */   
/*     */   public MacroCommandBase(@Nonnull String name, @Nullable String[] aliases, @Nonnull String description, @Nullable MacroCommandParameter[] parameters, @Nonnull String[] commands) {
/*  53 */     super(name, description);
/*     */     
/*  55 */     if (aliases != null) {
/*  56 */       addAliases(aliases);
/*     */     }
/*     */     
/*  59 */     if (parameters != null) {
/*     */       
/*  61 */       ParseResult parseResult = new ParseResult();
/*     */       
/*  63 */       for (MacroCommandParameter parameter : parameters) {
/*     */         RequiredArg requiredArg; OptionalArg optionalArg; FlagArg flagArg; Argument<?, ?> argument;
/*  65 */         switch (parameter.getRequirement()) { case REQUIRED:
/*  66 */             requiredArg = withRequiredArg(parameter.getName(), parameter.getDescription(), parameter.getArgumentType().getArgumentType()); break;
/*  67 */           case OPTIONAL: optionalArg = withOptionalArg(parameter.getName(), parameter.getDescription(), parameter.getArgumentType().getArgumentType()); break;
/*  68 */           case FLAG: flagArg = withFlagArg(parameter.getName(), parameter.getDescription()); break;
/*     */           case DEFAULT:
/*  70 */             argument = withDefaultArg(parameter.getName(), parameter.getDescription(), parameter.getArgumentType().getArgumentType(), parameter.getDefaultValue(), parameter.getDefaultValueDescription(), parseResult); break;
/*  71 */           default: throw new IllegalStateException("Unexpected value for Requirement: " + String.valueOf(parameter.getRequirement())); }
/*     */         
/*  73 */         this.arguments.put(parameter.getName(), argument);
/*     */       } 
/*     */       
/*  76 */       if (parseResult.failed()) {
/*  77 */         parseResult.sendMessages((CommandSender)ConsoleSender.INSTANCE);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     Matcher matcher = regexBracketPattern.matcher("");
/*  84 */     for (int i = 0; i < commands.length; i++) {
/*  85 */       String command = commands[i];
/*  86 */       ObjectArrayList<MacroCommandReplacement> replacements = new ObjectArrayList();
/*     */ 
/*     */ 
/*     */       
/*  90 */       Matcher reset = matcher.reset(command);
/*     */       
/*  92 */       while (reset.find()) {
/*  93 */         MacroCommandReplacement replacement; String result = reset.group(1);
/*  94 */         String[] splitByColons = result.split(":");
/*     */ 
/*     */         
/*  97 */         if (command.charAt(matcher.start(1) - 2) == '\\') {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 102 */         String replacementSubstring = command.substring(matcher.start(1) - 1, matcher.end(1) + 1);
/*     */ 
/*     */ 
/*     */         
/* 106 */         switch (splitByColons.length) { case 1:
/* 107 */             replacement = new MacroCommandReplacement(result, replacementSubstring); break;
/* 108 */           case 2: replacement = new MacroCommandReplacement(splitByColons[1], replacementSubstring, splitByColons[0]); break;
/* 109 */           default: throw new IllegalArgumentException("Cannot have more than one colon in a macro command parameter: '" + result + "'"); }
/*     */ 
/*     */ 
/*     */         
/* 113 */         if (!this.arguments.containsKey(replacement.getNameOfReplacingArg())) {
/* 114 */           throw new IllegalArgumentException("Cannot define command with replacement token that does not refer to an argument: " + replacement.getNameOfReplacingArg());
/*     */         }
/*     */         
/* 117 */         replacements.add(replacement);
/*     */       } 
/*     */ 
/*     */       
/* 121 */       command = command.replaceAll("\\\\\\{", "{");
/* 122 */       commands[i] = command;
/*     */ 
/*     */       
/* 125 */       this.commandReplacements.add(Pair.of(command, replacements));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private <D> Argument<?, ?> withDefaultArg(String name, String description, @Nonnull ArgumentType<D> argumentType, @Nonnull String defaultValue, String defaultValueDescription, @Nonnull ParseResult parseResult) {
/* 134 */     D parsedData = (D)argumentType.parse(defaultValue.split(" "), parseResult);
/*     */     
/* 136 */     if (parseResult.failed()) {
/* 137 */       LOGGER.at(Level.WARNING).log("Could not parse default argument value for argument: '" + name + "' on Macro Command: '" + getName() + "'.");
/* 138 */       parseResult.sendMessages((CommandSender)ConsoleSender.INSTANCE);
/* 139 */       return null;
/*     */     } 
/*     */     
/* 142 */     this.defaultValueStrings.put(name, defaultValue);
/*     */     
/* 144 */     return (Argument<?, ?>)withDefaultArg(name, description, argumentType, parsedData, defaultValueDescription);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 149 */     ObjectArrayList<String> objectArrayList = new ObjectArrayList();
/*     */     
/* 151 */     CommandSender commandSender = context.sender();
/* 152 */     String macro = context.getCalledCommand().getName();
/* 153 */     LOGGER.at(Level.INFO).log("%s expanding command macro: %s", commandSender.getDisplayName(), macro);
/*     */     
/* 155 */     for (Pair<String, List<MacroCommandReplacement>> stringListPair : this.commandReplacements) {
/* 156 */       String command = (String)stringListPair.key();
/* 157 */       List<MacroCommandReplacement> replacements = (List<MacroCommandReplacement>)stringListPair.value();
/* 158 */       for (MacroCommandReplacement replacement : replacements) {
/*     */         
/* 160 */         String stringToInject = "";
/*     */ 
/*     */         
/* 163 */         boolean shouldInject = true;
/*     */         
/* 165 */         Argument<? extends Argument<?, ?>, ?> argument = (Argument<? extends Argument<?, ?>, ?>)this.arguments.get(replacement.getNameOfReplacingArg());
/*     */ 
/*     */         
/* 168 */         if (argument instanceof com.hypixel.hytale.server.core.command.system.arguments.system.AbstractOptionalArg && !context.provided(argument)) {
/*     */ 
/*     */           
/* 171 */           if (argument instanceof com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg) {
/* 172 */             stringToInject = this.defaultValueStrings.get(argument.getName());
/*     */           }
/*     */           else {
/*     */             
/* 176 */             shouldInject = false;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 181 */           stringToInject = String.join(" ", (CharSequence[])context.getInput(this.arguments.get(replacement.getNameOfReplacingArg())));
/*     */         } 
/*     */ 
/*     */         
/* 185 */         if (shouldInject && replacement.getOptionalArgumentKey() != null) {
/* 186 */           stringToInject = replacement.getOptionalArgumentKey() + replacement.getOptionalArgumentKey();
/*     */         }
/*     */ 
/*     */         
/* 190 */         command = command.replace(replacement.getStringToReplaceWithValue(), shouldInject ? stringToInject : "");
/*     */       } 
/* 192 */       objectArrayList.add(command);
/*     */     } 
/*     */     
/* 195 */     CompletableFuture<Void> completableFuture = CompletableFuture.completedFuture(null);
/*     */     
/* 197 */     for (String command : objectArrayList) {
/* 198 */       completableFuture = completableFuture.thenCompose(VOID -> CommandManager.get().handleCommand(commandSender, command));
/*     */     }
/*     */     
/* 201 */     return completableFuture;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\commandmacro\MacroCommandBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */