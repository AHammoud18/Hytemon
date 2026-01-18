/*      */ package com.hypixel.hytale.server.core.command.system;
/*      */ import com.hypixel.hytale.logger.HytaleLogger;
/*      */ import com.hypixel.hytale.protocol.GameMode;
/*      */ import com.hypixel.hytale.server.core.Constants;
/*      */ import com.hypixel.hytale.server.core.Message;
/*      */ import com.hypixel.hytale.server.core.command.system.arguments.system.AbstractOptionalArg;
/*      */ import com.hypixel.hytale.server.core.command.system.arguments.system.ArgWrapper;
/*      */ import com.hypixel.hytale.server.core.command.system.arguments.system.Argument;
/*      */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*      */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*      */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*      */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*      */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*      */ import com.hypixel.hytale.server.core.command.system.arguments.types.ListArgumentType;
/*      */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
/*      */ import com.hypixel.hytale.server.core.modules.i18n.I18nModule;
/*      */ import com.hypixel.hytale.server.core.plugin.PluginBase;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBooleanPair;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ public abstract class AbstractCommand {
/*      */   @Nonnull
/*   37 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   42 */   public static final String[] EMPTY_STRING_ARRAY = new String[0];
/*      */   
/*      */   @Nonnull
/*   45 */   private static final Message MESSAGE_COMMANDS_HELP_NO_PERMISSIBLE_SUB_COMMAND = Message.translation("server.commands.help.noPermissibleSubCommand");
/*      */   @Nonnull
/*   47 */   private static final Message MESSAGE_COMMANDS_PARSING_ERROR_NO_PERMISSION_FOR_COMMAND = Message.translation("server.commands.parsing.error.noPermissionForCommand");
/*      */   @Nonnull
/*   49 */   private static final Message MESSAGE_COMMANDS_PARSING_ERROR_ATTEMPTED_UNSAFE = Message.translation("server.commands.parsing.error.attemptedUnsafe");
/*      */   @Nonnull
/*   51 */   private static final Message MESSAGE_COMMANDS_PARSING_USAGE_REQUIRES_CONFIRMATION = Message.translation("server.commands.parsing.usage.requiresConfirmation");
/*      */   @Nonnull
/*   53 */   private static final Message MESSAGE_COMMAND_SINGLEPLAYER = Message.translation("server.commands.parsing.error.unavailableInSingleplayer");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   static final String CONFIRM_ARG_TAG = "confirm";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private static final String COLOR_STRING_ARG_REQUIRED = "#C1E0FF";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private static final String COLOR_STRING_ARG_OPTIONAL = "#7E9EBC";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AbstractCommand parentCommand;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private final String name;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*   91 */   private final Set<String> aliases = new HashSet<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String description;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  103 */   private final List<RequiredArg<?>> requiredArguments = (List<RequiredArg<?>>)new ObjectArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  110 */   private final Map<String, AbstractOptionalArg<?, ?>> optionalArguments = (Map<String, AbstractOptionalArg<?, ?>>)new Object2ObjectOpenHashMap();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AbbreviationMap<AbstractOptionalArg<?, ?>> argumentAbbreviationMap;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  122 */   private final Map<String, AbstractCommand> subCommands = new LinkedHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  130 */   private final Map<String, String> subCommandsAliases = new LinkedHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  138 */   private final Int2ObjectMap<AbstractCommand> variantCommands = (Int2ObjectMap<AbstractCommand>)new Int2ObjectOpenHashMap();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private CommandOwner owner;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private String permission;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private List<String> permissionGroups;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int totalNumRequiredParameters;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean requiresConfirmation;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean unavailableInSingleplayer;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean allowsExtraArguments;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hasBeenRegistered;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AbstractCommand(@Nullable String name, @Nullable String description, boolean requiresConfirmation) {
/*  194 */     this.name = (name == null) ? null : name.toLowerCase();
/*  195 */     this.description = description;
/*  196 */     this.requiresConfirmation = requiresConfirmation;
/*      */ 
/*      */     
/*  199 */     if (requiresConfirmation) {
/*  200 */       registerOptionalArg(new FlagArg(this, "confirm", ""));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AbstractCommand(@Nullable String name, @Nullable String description) {
/*  211 */     this(name, description, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AbstractCommand(@Nullable String description) {
/*  220 */     this(null, description);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOwner(@Nonnull CommandOwner owner) {
/*  229 */     this.owner = owner;
/*      */ 
/*      */     
/*  232 */     if (this.permission == null && canGeneratePermission()) {
/*  233 */       this.permission = generatePermission();
/*      */     }
/*      */ 
/*      */     
/*  237 */     for (AbstractCommand subCommand : this.subCommands.values()) {
/*  238 */       subCommand.setOwner(owner);
/*      */     }
/*      */ 
/*      */     
/*  242 */     for (ObjectIterator<AbstractCommand> objectIterator = this.variantCommands.values().iterator(); objectIterator.hasNext(); ) { AbstractCommand variantCommand = objectIterator.next();
/*  243 */       variantCommand.setOwner(owner); }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canGeneratePermission() {
/*  251 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected String generatePermissionNode() {
/*  261 */     return (this.name == null) ? null : this.name.toLowerCase();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private String generatePermission() {
/*  271 */     String selfNode = generatePermissionNode();
/*      */     
/*  273 */     if (this.parentCommand != null) {
/*      */ 
/*      */       
/*  276 */       String generatedPermission, parentPermission = (this.parentCommand.permission == null) ? this.parentCommand.generatePermission() : this.parentCommand.permission;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  281 */       if (selfNode == null || selfNode.isEmpty()) {
/*  282 */         generatedPermission = parentPermission;
/*      */       } else {
/*  284 */         generatedPermission = parentPermission + "." + parentPermission;
/*      */       } 
/*      */       
/*  287 */       ((HytaleLogger.Api)LOGGER.atFine()).log("Generated missing permission '" + generatedPermission + "'.");
/*  288 */       return generatedPermission;
/*      */     } 
/*      */     
/*  291 */     CommandOwner commandOwner = this.owner; if (commandOwner instanceof PluginBase) { PluginBase plugin = (PluginBase)commandOwner;
/*  292 */       return plugin.getBasePermission() + ".command." + plugin.getBasePermission(); }
/*  293 */      if (this.owner instanceof CommandManager) {
/*  294 */       return "hytale.system.command." + selfNode;
/*      */     }
/*  296 */     throw new IllegalArgumentException("Unknown owner type, please use PluginBase or CommandManager");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public List<String> getPermissionGroups() {
/*  305 */     return this.permissionGroups;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setPermissionGroups(@Nonnull String... groups) {
/*  317 */     this.permissionGroups = Arrays.asList(groups);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setPermissionGroup(@Nullable GameMode gameMode) {
/*  326 */     setPermissionGroups(new String[] { (gameMode == null) ? null : gameMode.toString() });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Map<String, Set<String>> getPermissionGroupsRecursive() {
/*  334 */     Object2ObjectOpenHashMap object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/*  335 */     putRecursivePermissionGroups((Map<String, Set<String>>)object2ObjectOpenHashMap);
/*  336 */     return (Map<String, Set<String>>)object2ObjectOpenHashMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void putRecursivePermissionGroups(@Nonnull Map<String, Set<String>> permissionsByGroup) {
/*  345 */     List<String> permissionGroups = this.permissionGroups;
/*  346 */     if (permissionGroups == null && this.parentCommand != null) {
/*  347 */       permissionGroups = this.parentCommand.permissionGroups;
/*      */     }
/*      */     
/*  350 */     if (permissionGroups != null && this.permission != null) {
/*  351 */       for (String group : permissionGroups) {
/*  352 */         if (group == null)
/*  353 */           continue;  ((Set<String>)permissionsByGroup.computeIfAbsent(group, k -> new HashSet())).add(this.permission);
/*      */       } 
/*      */     }
/*      */     
/*  357 */     for (AbstractCommand subCommand : this.subCommands.values()) {
/*  358 */       subCommand.putRecursivePermissionGroups(permissionsByGroup);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setUnavailableInSingleplayer(boolean unavailableInSingleplayer) {
/*  368 */     this.unavailableInSingleplayer = unavailableInSingleplayer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllowsExtraArguments(boolean allowsExtraArguments) {
/*  380 */     this.allowsExtraArguments = allowsExtraArguments;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public MatchResult matches(@Nonnull String language, @Nonnull String search, int termDepth) {
/*  393 */     return matches(language, search, termDepth, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private MatchResult matches(@Nonnull String language, @Nonnull String search, int termDepth, int depth) {
/*  407 */     if (this.name != null && this.name.contains(search)) {
/*  408 */       return MatchResult.of(termDepth, depth, 0, this.name, search);
/*      */     }
/*      */     
/*  411 */     for (String alias : this.aliases) {
/*  412 */       if (alias.contains(search)) return MatchResult.of(termDepth, depth, 1, alias, search);
/*      */     
/*      */     } 
/*  415 */     for (AbstractOptionalArg<?, ?> opt : this.optionalArguments.values()) {
/*  416 */       if (opt.getName().contains(search)) {
/*  417 */         return MatchResult.of(termDepth, depth, 3, opt.getName(), search);
/*      */       }
/*  419 */       for (String alias : opt.getAliases()) {
/*  420 */         if (alias.contains(search)) {
/*  421 */           return MatchResult.of(termDepth, depth, 3, alias, search);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  426 */     for (RequiredArg<?> opt : this.requiredArguments) {
/*  427 */       if (opt.getName().contains(search)) {
/*  428 */         return MatchResult.of(termDepth, depth, 3, opt.getName(), search);
/*      */       }
/*      */     } 
/*      */     
/*  432 */     if (this.description != null) {
/*  433 */       String descriptionMessage = I18nModule.get().getMessage(language, this.description);
/*  434 */       if (descriptionMessage != null && descriptionMessage.contains(search)) {
/*  435 */         return MatchResult.of(termDepth, depth, 4, descriptionMessage, search);
/*      */       }
/*      */     } 
/*      */     
/*  439 */     for (AbstractOptionalArg<?, ?> opt : this.optionalArguments.values()) {
/*  440 */       String description = opt.getDescription();
/*  441 */       if (description != null) {
/*  442 */         String usageDescription = I18nModule.get().getMessage(language, description);
/*  443 */         if (usageDescription != null && usageDescription.contains(search)) {
/*  444 */           return MatchResult.of(termDepth, depth, 5, usageDescription, search);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  449 */     for (AbstractCommand subCommand : this.subCommands.values()) {
/*  450 */       MatchResult result = subCommand.matches(language, search, termDepth, depth + 1);
/*  451 */       if (result != MatchResult.NONE) return result;
/*      */     
/*      */     } 
/*  454 */     for (ObjectIterator<AbstractCommand> objectIterator = this.variantCommands.values().iterator(); objectIterator.hasNext(); ) { AbstractCommand variantCommand = objectIterator.next();
/*  455 */       MatchResult result = variantCommand.matches(language, search, termDepth, depth + 1);
/*  456 */       if (result != MatchResult.NONE) return result;
/*      */        }
/*      */     
/*  459 */     return MatchResult.NONE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void completeRegistration() throws GeneralCommandException {
/*  469 */     this.hasBeenRegistered = true;
/*      */     
/*  471 */     for (AbstractCommand command : this.subCommands.values()) {
/*  472 */       command.completeRegistration();
/*      */     }
/*      */     
/*  475 */     for (ObjectIterator<AbstractCommand> objectIterator = this.variantCommands.values().iterator(); objectIterator.hasNext(); ) { AbstractCommand command = objectIterator.next();
/*  476 */       command.completeRegistration(); }
/*      */ 
/*      */     
/*  479 */     validateVariantNumberOfRequiredParameters(new ParseResult(true));
/*  480 */     validateDefaultArguments(new ParseResult(true));
/*  481 */     createOptionalArgumentAbbreviationMap();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createOptionalArgumentAbbreviationMap() {
/*  488 */     AbbreviationMap.AbbreviationMapBuilder<AbstractOptionalArg<?, ?>> abbreviationMapBuilder = AbbreviationMap.create();
/*      */     
/*  490 */     for (AbstractOptionalArg<?, ?> abstractOptionalArg : this.optionalArguments.values()) {
/*  491 */       abbreviationMapBuilder.put(abstractOptionalArg.getName(), abstractOptionalArg);
/*  492 */       for (String alias : abstractOptionalArg.getAliases()) {
/*  493 */         abbreviationMapBuilder.put(alias, abstractOptionalArg);
/*      */       }
/*      */     } 
/*      */     
/*  497 */     this.argumentAbbreviationMap = abbreviationMapBuilder.build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateVariantNumberOfRequiredParameters(@Nonnull ParseResult result) {
/*  506 */     for (ObjectIterator<Int2ObjectMap.Entry<AbstractCommand>> objectIterator = this.variantCommands.int2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<AbstractCommand> entry = objectIterator.next();
/*  507 */       if (this.totalNumRequiredParameters == ((AbstractCommand)entry.getValue()).totalNumRequiredParameters) {
/*      */         
/*  509 */         result.fail(Message.raw("Command '" + getFullyQualifiedName() + "' and its variant '" + ((AbstractCommand)entry.getValue()).toString() + "' both have " + this.totalNumRequiredParameters + " required parameters. Variants must have different numbers of required parameters."));
/*      */         return;
/*      */       }  }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateDefaultArguments(@Nonnull ParseResult parseResult) {
/*  522 */     for (AbstractOptionalArg<?, ?> value : this.optionalArguments.values()) {
/*  523 */       if (value instanceof DefaultArg) { DefaultArg<?> defaultArg = (DefaultArg)value;
/*  524 */         defaultArg.validateDefaultValue(parseResult); }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void requirePermission(@Nonnull String permission) {
/*  535 */     this.permission = permission;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public String getFullyQualifiedName() {
/*  547 */     if (this.parentCommand != null) {
/*  548 */       if (isVariant()) {
/*  549 */         return this.parentCommand.getFullyQualifiedName();
/*      */       }
/*  551 */       return this.parentCommand.getFullyQualifiedName() + " " + this.parentCommand.getFullyQualifiedName();
/*      */     } 
/*      */     
/*  554 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int countParents() {
/*  563 */     if (this.parentCommand == null) return 0; 
/*  564 */     return this.parentCommand.countParents() + 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAliases(@Nonnull String... aliases) {
/*  574 */     if (this.hasBeenRegistered) throw new IllegalStateException("Cannot add aliases when a command has already completed registration"); 
/*  575 */     if (this.name == null) throw new IllegalStateException("Cannot add aliases to a command with no name");
/*      */     
/*  577 */     for (String alias : aliases) {
/*  578 */       this.aliases.add(alias.toLowerCase());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSubCommand(@Nonnull AbstractCommand command) {
/*  590 */     if (this.hasBeenRegistered) throw new IllegalStateException("Cannot add new subcommands when a command has already completed registration"); 
/*  591 */     if (isVariant()) throw new IllegalStateException("Cannot add a subcommand to a variant command, can only add subcommands to named commands"); 
/*  592 */     if (command.name == null) throw new IllegalArgumentException("Cannot add a subcommand with no name"); 
/*  593 */     if (command.parentCommand != null) throw new IllegalArgumentException("Cannot re-use subcommands. Only one parent command allowed for each subcommand");
/*      */     
/*  595 */     command.parentCommand = this;
/*  596 */     if (this.subCommands.containsKey(command.name)) throw new IllegalArgumentException("Cannot have multiple subcommands with the same name"); 
/*  597 */     if (this.subCommandsAliases.containsKey(command.name)) {
/*  598 */       throw new IllegalArgumentException("Command has same name as existing command alias for command: " + command.name);
/*      */     }
/*      */     
/*  601 */     this.subCommands.put(command.name, command);
/*  602 */     for (String alias : command.aliases) {
/*  603 */       if (this.subCommandsAliases.containsKey(alias) || this.subCommands.containsKey(alias)) {
/*  604 */         throw new IllegalArgumentException("Cannot specify a subcommand alias with the same name as an existing command or alias: " + alias);
/*      */       }
/*  606 */       this.subCommandsAliases.put(alias, command.name);
/*      */     } 
/*      */     
/*  609 */     command.hasBeenRegistered = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addUsageVariant(@Nonnull AbstractCommand command) {
/*  621 */     if (this.hasBeenRegistered) throw new IllegalStateException("Cannot add new variants when a command has already completed registration");
/*      */     
/*  623 */     if (isVariant()) throw new IllegalStateException("Cannot add a command variant to a variant command, can only add variants to named commands"); 
/*  624 */     if (command.name != null) throw new IllegalArgumentException("Cannot add a variant command with a name, use the description-only constructor"); 
/*  625 */     if (command.parentCommand != null) {
/*  626 */       throw new IllegalArgumentException("Cannot re-use variant commands. Only one parent command allowed for each variant command");
/*      */     }
/*      */     
/*  629 */     AbstractCommand variantWithSameNumRequiredParameters = (AbstractCommand)this.variantCommands.put(command.totalNumRequiredParameters, command);
/*  630 */     if (variantWithSameNumRequiredParameters != null) {
/*  631 */       throw new IllegalArgumentException("You have already registered a variant command with " + command.totalNumRequiredParameters + " required parameters. Command's class name: " + variantWithSameNumRequiredParameters
/*  632 */           .getClass().getName());
/*      */     }
/*      */ 
/*      */     
/*  636 */     command.parentCommand = this;
/*  637 */     command.hasBeenRegistered = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public CompletableFuture<Void> acceptCall(@Nonnull CommandSender sender, @Nonnull ParserContext parserContext, @Nonnull ParseResult parseResult) {
/*  654 */     parserContext.convertToSubCommand();
/*      */     
/*  656 */     return acceptCall0(sender, parserContext, parseResult);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private CompletableFuture<Void> acceptCall0(@Nonnull CommandSender sender, @Nonnull ParserContext parserContext, @Nonnull ParseResult parseResult) {
/*  674 */     int numberOfPreOptionalTokens = parserContext.getNumPreOptionalTokens();
/*      */ 
/*      */     
/*  677 */     ObjectBooleanPair<CompletableFuture<Void>> completableFutureBooleanPair = checkForExecutingSubcommands(sender, parserContext, parseResult, numberOfPreOptionalTokens);
/*  678 */     if (parseResult.failed()) return null;
/*      */     
/*  680 */     if (completableFutureBooleanPair.rightBoolean()) return (CompletableFuture<Void>)completableFutureBooleanPair.left();
/*      */ 
/*      */     
/*  683 */     if (!hasPermission(sender)) {
/*  684 */       parseResult.fail(MESSAGE_COMMANDS_PARSING_ERROR_NO_PERMISSION_FOR_COMMAND);
/*  685 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  689 */     if (this instanceof com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection && numberOfPreOptionalTokens != 0) {
/*  690 */       HashSet<String> commandNames = new HashSet<>(this.subCommands.keySet());
/*  691 */       commandNames.addAll(this.subCommandsAliases.keySet());
/*      */       
/*  693 */       String firstToken = parserContext.getFirstToken();
/*  694 */       String commandSuggestionPrefix = "\n/" + getFullyQualifiedName() + " ";
/*  695 */       String suggestedCommands = commandSuggestionPrefix + commandSuggestionPrefix;
/*      */       
/*  697 */       parseResult.fail(Message.translation("server.commands.parsing.error.commandCollectionSubcommandNotFound")
/*  698 */           .param("subcommand", firstToken)
/*  699 */           .param("suggestions", suggestedCommands));
/*  700 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  704 */     if (parserContext.isHelpSpecified()) {
/*  705 */       sender.sendMessage(getUsageString(sender));
/*  706 */       return null;
/*      */     } 
/*      */     
/*  709 */     if (this.unavailableInSingleplayer && Constants.SINGLEPLAYER) {
/*  710 */       parseResult.fail(MESSAGE_COMMAND_SINGLEPLAYER);
/*  711 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  715 */     if (this.requiresConfirmation && !parserContext.isConfirmationSpecified()) {
/*  716 */       parseResult.fail(MESSAGE_COMMANDS_PARSING_ERROR_ATTEMPTED_UNSAFE);
/*  717 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  722 */     if (this.allowsExtraArguments) {
/*  723 */       if (numberOfPreOptionalTokens < this.totalNumRequiredParameters) {
/*  724 */         parseResult.fail(Message.translation("server.commands.parsing.error.wrongNumberRequiredParameters")
/*  725 */             .param("expected", this.totalNumRequiredParameters)
/*  726 */             .param("actual", numberOfPreOptionalTokens)
/*  727 */             .insert("\n")
/*  728 */             .insert(Message.translation("server.commands.help.usagecolon").param("usage", getUsageShort(sender, true)))
/*  729 */             .insert("\n")
/*  730 */             .insert(Message.translation("server.commands.help.useHelpToLearnMore").param("command", getFullyQualifiedName())));
/*      */         
/*  732 */         return null;
/*      */       } 
/*  734 */     } else if (this.totalNumRequiredParameters != numberOfPreOptionalTokens) {
/*  735 */       parseResult.fail(Message.translation("server.commands.parsing.error.wrongNumberRequiredParameters")
/*  736 */           .param("expected", this.totalNumRequiredParameters)
/*  737 */           .param("actual", numberOfPreOptionalTokens)
/*  738 */           .insert("\n")
/*  739 */           .insert(Message.translation("server.commands.help.usagecolon").param("usage", getUsageShort(sender, true)))
/*  740 */           .insert("\n")
/*  741 */           .insert(Message.translation("server.commands.help.useHelpToLearnMore").param("command", getFullyQualifiedName())));
/*      */       
/*  743 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  747 */     CommandContext commandContext = new CommandContext(this, sender, parserContext.getInputString());
/*      */ 
/*      */     
/*  750 */     processRequiredArguments(parserContext, parseResult, commandContext);
/*  751 */     if (parseResult.failed()) return null;
/*      */ 
/*      */     
/*  754 */     processOptionalArguments(parserContext, parseResult, commandContext);
/*  755 */     if (parseResult.failed()) return null;
/*      */ 
/*      */     
/*  758 */     return execute(commandContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasPermission(@Nonnull CommandSender sender) {
/*  768 */     String permission = getPermission();
/*  769 */     if (permission == null) return true;
/*      */     
/*  771 */     if (sender.hasPermission(permission)) {
/*  772 */       if (this.parentCommand == null) return true; 
/*  773 */       return this.parentCommand.hasPermission(sender);
/*      */     } 
/*      */     
/*  776 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private ObjectBooleanPair<CompletableFuture<Void>> checkForExecutingSubcommands(@Nonnull CommandSender sender, @Nonnull ParserContext parserContext, @Nonnull ParseResult parseResult, int numberOfPreOptionalTokens) {
/*  791 */     if (parserContext.getNumPreOptSingleValueTokensBeforeListTokens() >= 0) {
/*  792 */       if (!this.subCommands.isEmpty()) {
/*      */         
/*  794 */         String subCommandName = parserContext.getPreOptionalSingleValueToken(0);
/*  795 */         if (subCommandName != null) {
/*  796 */           subCommandName = subCommandName.toLowerCase();
/*      */         }
/*  798 */         AbstractCommand subCommand = this.subCommands.get(subCommandName);
/*  799 */         if (subCommand != null) {
/*      */ 
/*      */           
/*  802 */           parserContext.convertToSubCommand();
/*  803 */           return ObjectBooleanPair.of(subCommand.acceptCall0(sender, parserContext, parseResult), true);
/*      */         } 
/*      */ 
/*      */         
/*  807 */         String alias = this.subCommandsAliases.get(subCommandName);
/*  808 */         if (alias != null) {
/*  809 */           parserContext.convertToSubCommand();
/*  810 */           return ObjectBooleanPair.of(((AbstractCommand)this.subCommands.get(alias)).acceptCall0(sender, parserContext, parseResult), true);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  815 */       AbstractCommand commandVariant = (AbstractCommand)this.variantCommands.get(numberOfPreOptionalTokens);
/*  816 */       if (this.totalNumRequiredParameters != numberOfPreOptionalTokens && commandVariant != null) {
/*  817 */         return ObjectBooleanPair.of(commandVariant.acceptCall0(sender, parserContext, parseResult), true);
/*      */       }
/*      */     } 
/*      */     
/*  821 */     return ObjectBooleanPair.of(null, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processRequiredArguments(@Nonnull ParserContext parserContext, @Nonnull ParseResult parseResult, @Nonnull CommandContext commandContext) {
/*  830 */     int currentReqArgIndex = 0;
/*  831 */     for (RequiredArg<?> requiredArgument : this.requiredArguments) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  838 */       if (requiredArgument.getArgumentType().isListArgument() && parserContext.isListToken(currentReqArgIndex)) {
/*      */         
/*  840 */         ParserContext.PreOptionalListContext preOptionalTokenContext = parserContext.getPreOptionalListToken(currentReqArgIndex);
/*      */ 
/*      */         
/*  843 */         currentReqArgIndex++;
/*      */ 
/*      */         
/*  846 */         commandContext.appendArgumentData((Argument)requiredArgument, preOptionalTokenContext.getTokens(), true, parseResult);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  856 */       String[] argParameters = new String[requiredArgument.getArgumentType().getNumberOfParameters()];
/*  857 */       for (int i = 0; i < requiredArgument.getArgumentType().getNumberOfParameters(); i++) {
/*      */         
/*  859 */         if (parserContext.isListToken(currentReqArgIndex)) {
/*      */           
/*  861 */           parseResult.fail(Message.translation("server.commands.parsing.error.notAList")
/*  862 */               .param("name", requiredArgument.getName()));
/*      */           
/*      */           return;
/*      */         } 
/*  866 */         argParameters[i] = parserContext.getPreOptionalSingleValueToken(currentReqArgIndex);
/*  867 */         currentReqArgIndex++;
/*      */       } 
/*      */       
/*  870 */       commandContext.appendArgumentData((Argument)requiredArgument, argParameters, false, parseResult);
/*  871 */       if (parseResult.failed()) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processOptionalArguments(@Nonnull ParserContext parserContext, @Nonnull ParseResult parseResult, @Nonnull CommandContext commandContext) {
/*  881 */     for (ObjectBidirectionalIterator<Map.Entry<String, List<List<String>>>> objectBidirectionalIterator = parserContext.getOptionalArgs().iterator(); objectBidirectionalIterator.hasNext(); ) { Map.Entry<String, List<List<String>>> optionalArgContext = objectBidirectionalIterator.next();
/*      */       
/*  883 */       AbstractOptionalArg<? extends Argument<?, ?>, ?> optionalArg = (AbstractOptionalArg<? extends Argument<?, ?>, ?>)this.argumentAbbreviationMap.get(optionalArgContext.getKey());
/*  884 */       if (optionalArg == null) {
/*      */ 
/*      */         
/*  887 */         parseResult.fail(Message.translation("server.commands.parsing.error.couldNotFindOptionalArgName")
/*  888 */             .param("input", optionalArgContext.getKey()));
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  893 */       if (optionalArg.getPermission() != null && !commandContext.sender().hasPermission(optionalArg.getPermission())) {
/*  894 */         parseResult.fail(Message.translation("server.commands.parsing.error.noPermissionForOptional")
/*  895 */             .param("argument", optionalArgContext.getKey()));
/*      */         
/*      */         return;
/*      */       } 
/*  899 */       List<List<String>> optionalArgValues = optionalArgContext.getValue();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  904 */       if (optionalArg.getArgumentType().isListArgument() && optionalArgValues.size() > 1) {
/*      */         
/*  906 */         String[] optionalArgParseValues = new String[optionalArgValues.size() * ((List)optionalArgValues.getFirst()).size()];
/*      */ 
/*      */         
/*  909 */         for (int i = 0; i < optionalArgValues.size(); i++) {
/*  910 */           List<String> values = optionalArgValues.get(i);
/*  911 */           for (int k = 0; k < values.size(); k++) {
/*  912 */             optionalArgParseValues[i * values.size() + k] = values.get(k);
/*      */           }
/*      */         } 
/*      */         
/*  916 */         commandContext.appendArgumentData((Argument<?, ?>)optionalArg, optionalArgParseValues, true, parseResult);
/*      */         
/*      */         continue;
/*      */       } 
/*  920 */       commandContext.appendArgumentData((Argument<?, ?>)optionalArg, 
/*      */           
/*  922 */           optionalArgValues.isEmpty() ? EMPTY_STRING_ARRAY : (String[])((List)optionalArgValues.getFirst()).toArray((Object[])EMPTY_STRING_ARRAY), false, parseResult); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  929 */     for (AbstractOptionalArg<?, ?> optionalArg : this.optionalArguments.values()) {
/*  930 */       optionalArg.verifyArgumentDependencies(commandContext, parseResult);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Message getUsageString(@Nonnull CommandSender sender) {
/*      */     // Byte code:
/*      */     //   0: ldc ''
/*      */     //   2: invokestatic raw : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   5: astore_2
/*      */     //   6: aload_0
/*      */     //   7: getfield requiredArguments : Ljava/util/List;
/*      */     //   10: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   15: astore_3
/*      */     //   16: aload_3
/*      */     //   17: invokeinterface hasNext : ()Z
/*      */     //   22: ifeq -> 74
/*      */     //   25: aload_3
/*      */     //   26: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   31: checkcast com/hypixel/hytale/server/core/command/system/arguments/system/RequiredArg
/*      */     //   34: astore #4
/*      */     //   36: aload_2
/*      */     //   37: ldc_w ' '
/*      */     //   40: invokevirtual insert : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   43: aload #4
/*      */     //   45: invokevirtual getUsageMessageWithoutDescription : ()Lcom/hypixel/hytale/server/core/Message;
/*      */     //   48: invokevirtual insert : (Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   51: pop
/*      */     //   52: aload #4
/*      */     //   54: invokevirtual getArgumentType : ()Lcom/hypixel/hytale/server/core/command/system/arguments/types/ArgumentType;
/*      */     //   57: invokevirtual isListArgument : ()Z
/*      */     //   60: ifeq -> 71
/*      */     //   63: aload_2
/*      */     //   64: ldc_w '/...'
/*      */     //   67: invokevirtual insert : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   70: pop
/*      */     //   71: goto -> 16
/*      */     //   74: aload_0
/*      */     //   75: getfield requiresConfirmation : Z
/*      */     //   78: ifeq -> 87
/*      */     //   81: getstatic com/hypixel/hytale/server/core/command/system/AbstractCommand.MESSAGE_COMMANDS_PARSING_USAGE_REQUIRES_CONFIRMATION : Lcom/hypixel/hytale/server/core/Message;
/*      */     //   84: goto -> 92
/*      */     //   87: ldc ''
/*      */     //   89: invokestatic raw : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   92: astore_3
/*      */     //   93: ldc ''
/*      */     //   95: invokestatic raw : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   98: astore #4
/*      */     //   100: iconst_0
/*      */     //   101: istore #5
/*      */     //   103: aload_0
/*      */     //   104: getfield requiredArguments : Ljava/util/List;
/*      */     //   107: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   112: astore #6
/*      */     //   114: aload #6
/*      */     //   116: invokeinterface hasNext : ()Z
/*      */     //   121: ifeq -> 159
/*      */     //   124: aload #6
/*      */     //   126: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   131: checkcast com/hypixel/hytale/server/core/command/system/arguments/system/RequiredArg
/*      */     //   134: astore #7
/*      */     //   136: iconst_1
/*      */     //   137: istore #5
/*      */     //   139: aload #4
/*      */     //   141: ldc_w '\\n    '
/*      */     //   144: invokevirtual insert : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   147: aload #7
/*      */     //   149: invokevirtual getUsageMessage : ()Lcom/hypixel/hytale/server/core/Message;
/*      */     //   152: invokevirtual insert : (Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   155: pop
/*      */     //   156: goto -> 114
/*      */     //   159: ldc ''
/*      */     //   161: invokestatic raw : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   164: astore #6
/*      */     //   166: iconst_0
/*      */     //   167: istore #7
/*      */     //   169: ldc ''
/*      */     //   171: invokestatic raw : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   174: astore #8
/*      */     //   176: iconst_0
/*      */     //   177: istore #9
/*      */     //   179: ldc ''
/*      */     //   181: invokestatic raw : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   184: astore #10
/*      */     //   186: iconst_0
/*      */     //   187: istore #11
/*      */     //   189: aload_0
/*      */     //   190: getfield optionalArguments : Ljava/util/Map;
/*      */     //   193: invokeinterface entrySet : ()Ljava/util/Set;
/*      */     //   198: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   203: astore #12
/*      */     //   205: aload #12
/*      */     //   207: invokeinterface hasNext : ()Z
/*      */     //   212: ifeq -> 405
/*      */     //   215: aload #12
/*      */     //   217: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   222: checkcast java/util/Map$Entry
/*      */     //   225: astore #13
/*      */     //   227: aload #13
/*      */     //   229: invokeinterface getValue : ()Ljava/lang/Object;
/*      */     //   234: checkcast com/hypixel/hytale/server/core/command/system/arguments/system/AbstractOptionalArg
/*      */     //   237: astore #14
/*      */     //   239: aload #14
/*      */     //   241: invokevirtual getPermission : ()Ljava/lang/String;
/*      */     //   244: ifnull -> 264
/*      */     //   247: aload_1
/*      */     //   248: aload #14
/*      */     //   250: invokevirtual getPermission : ()Ljava/lang/String;
/*      */     //   253: invokeinterface hasPermission : (Ljava/lang/String;)Z
/*      */     //   258: ifne -> 264
/*      */     //   261: goto -> 205
/*      */     //   264: aload #14
/*      */     //   266: dup
/*      */     //   267: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   270: pop
/*      */     //   271: astore #15
/*      */     //   273: iconst_0
/*      */     //   274: istore #16
/*      */     //   276: aload #15
/*      */     //   278: iload #16
/*      */     //   280: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*      */     //   285: tableswitch default -> 402, 0 -> 312, 1 -> 342, 2 -> 372
/*      */     //   312: aload #15
/*      */     //   314: checkcast com/hypixel/hytale/server/core/command/system/arguments/system/OptionalArg
/*      */     //   317: astore #17
/*      */     //   319: iconst_1
/*      */     //   320: istore #7
/*      */     //   322: aload #6
/*      */     //   324: ldc_w '\\n    '
/*      */     //   327: invokevirtual insert : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   330: aload #14
/*      */     //   332: invokevirtual getUsageMessage : ()Lcom/hypixel/hytale/server/core/Message;
/*      */     //   335: invokevirtual insert : (Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   338: pop
/*      */     //   339: goto -> 402
/*      */     //   342: aload #15
/*      */     //   344: checkcast com/hypixel/hytale/server/core/command/system/arguments/system/DefaultArg
/*      */     //   347: astore #18
/*      */     //   349: iconst_1
/*      */     //   350: istore #9
/*      */     //   352: aload #8
/*      */     //   354: ldc_w '\\n    '
/*      */     //   357: invokevirtual insert : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   360: aload #14
/*      */     //   362: invokevirtual getUsageMessage : ()Lcom/hypixel/hytale/server/core/Message;
/*      */     //   365: invokevirtual insert : (Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   368: pop
/*      */     //   369: goto -> 402
/*      */     //   372: aload #15
/*      */     //   374: checkcast com/hypixel/hytale/server/core/command/system/arguments/system/FlagArg
/*      */     //   377: astore #19
/*      */     //   379: iconst_1
/*      */     //   380: istore #11
/*      */     //   382: aload #10
/*      */     //   384: ldc_w '\\n    '
/*      */     //   387: invokevirtual insert : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   390: aload #14
/*      */     //   392: invokevirtual getUsageMessage : ()Lcom/hypixel/hytale/server/core/Message;
/*      */     //   395: invokevirtual insert : (Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   398: pop
/*      */     //   399: goto -> 402
/*      */     //   402: goto -> 205
/*      */     //   405: iload #5
/*      */     //   407: ifeq -> 426
/*      */     //   410: ldc_w 'server.commands.parsing.usage.requiredArgs'
/*      */     //   413: invokestatic translation : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   416: ldc_w 'args'
/*      */     //   419: aload #4
/*      */     //   421: invokevirtual param : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   424: astore #4
/*      */     //   426: iload #7
/*      */     //   428: ifeq -> 447
/*      */     //   431: ldc_w 'server.commands.parsing.usage.optionalArgs'
/*      */     //   434: invokestatic translation : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   437: ldc_w 'args'
/*      */     //   440: aload #6
/*      */     //   442: invokevirtual param : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   445: astore #6
/*      */     //   447: iload #11
/*      */     //   449: ifeq -> 468
/*      */     //   452: ldc_w 'server.commands.parsing.usage.flagArgs'
/*      */     //   455: invokestatic translation : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   458: ldc_w 'args'
/*      */     //   461: aload #10
/*      */     //   463: invokevirtual param : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   466: astore #10
/*      */     //   468: iload #9
/*      */     //   470: ifeq -> 489
/*      */     //   473: ldc_w 'server.commands.parsing.usage.defaultArgs'
/*      */     //   476: invokestatic translation : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   479: ldc_w 'args'
/*      */     //   482: aload #8
/*      */     //   484: invokevirtual param : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   487: astore #8
/*      */     //   489: ldc ''
/*      */     //   491: invokestatic raw : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   494: astore #12
/*      */     //   496: aload_0
/*      */     //   497: getfield variantCommands : Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;
/*      */     //   500: invokeinterface values : ()Lit/unimi/dsi/fastutil/objects/ObjectCollection;
/*      */     //   505: invokeinterface iterator : ()Lit/unimi/dsi/fastutil/objects/ObjectIterator;
/*      */     //   510: astore #13
/*      */     //   512: aload #13
/*      */     //   514: invokeinterface hasNext : ()Z
/*      */     //   519: ifeq -> 582
/*      */     //   522: aload #13
/*      */     //   524: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   529: checkcast com/hypixel/hytale/server/core/command/system/AbstractCommand
/*      */     //   532: astore #14
/*      */     //   534: aload #14
/*      */     //   536: aload_1
/*      */     //   537: invokevirtual hasPermission : (Lcom/hypixel/hytale/server/core/command/system/CommandSender;)Z
/*      */     //   540: ifeq -> 579
/*      */     //   543: aload #12
/*      */     //   545: ldc_w 'server.commands.parsing.usage.subcommands'
/*      */     //   548: invokestatic translation : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   551: ldc_w 'separator'
/*      */     //   554: ldc_w 'server.commands.parsing.usage.subcommandSeparator'
/*      */     //   557: invokestatic translation : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   560: invokevirtual param : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   563: ldc_w 'usage'
/*      */     //   566: aload #14
/*      */     //   568: aload_1
/*      */     //   569: invokevirtual getUsageString : (Lcom/hypixel/hytale/server/core/command/system/CommandSender;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   572: invokevirtual param : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   575: invokevirtual insert : (Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   578: pop
/*      */     //   579: goto -> 512
/*      */     //   582: ldc ''
/*      */     //   584: invokestatic raw : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   587: astore #13
/*      */     //   589: aload_0
/*      */     //   590: getfield subCommands : Ljava/util/Map;
/*      */     //   593: invokeinterface values : ()Ljava/util/Collection;
/*      */     //   598: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   603: astore #14
/*      */     //   605: aload #14
/*      */     //   607: invokeinterface hasNext : ()Z
/*      */     //   612: ifeq -> 675
/*      */     //   615: aload #14
/*      */     //   617: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   622: checkcast com/hypixel/hytale/server/core/command/system/AbstractCommand
/*      */     //   625: astore #15
/*      */     //   627: aload #15
/*      */     //   629: aload_1
/*      */     //   630: invokevirtual hasPermission : (Lcom/hypixel/hytale/server/core/command/system/CommandSender;)Z
/*      */     //   633: ifeq -> 672
/*      */     //   636: aload #13
/*      */     //   638: ldc_w 'server.commands.parsing.usage.subcommands'
/*      */     //   641: invokestatic translation : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   644: ldc_w 'separator'
/*      */     //   647: ldc_w 'server.commands.parsing.usage.subcommandSeparator'
/*      */     //   650: invokestatic translation : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   653: invokevirtual param : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   656: ldc_w 'usage'
/*      */     //   659: aload #15
/*      */     //   661: aload_1
/*      */     //   662: invokevirtual getUsageString : (Lcom/hypixel/hytale/server/core/command/system/CommandSender;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   665: invokevirtual param : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   668: invokevirtual insert : (Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   671: pop
/*      */     //   672: goto -> 605
/*      */     //   675: ldc_w '\\nArgument Types:'
/*      */     //   678: invokestatic raw : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   681: astore #14
/*      */     //   683: new java/util/HashSet
/*      */     //   686: dup
/*      */     //   687: invokespecial <init> : ()V
/*      */     //   690: astore #15
/*      */     //   692: aload_0
/*      */     //   693: getfield requiredArguments : Ljava/util/List;
/*      */     //   696: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   701: astore #16
/*      */     //   703: aload #16
/*      */     //   705: invokeinterface hasNext : ()Z
/*      */     //   710: ifeq -> 739
/*      */     //   713: aload #16
/*      */     //   715: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   720: checkcast com/hypixel/hytale/server/core/command/system/arguments/system/RequiredArg
/*      */     //   723: astore #17
/*      */     //   725: aload #15
/*      */     //   727: aload #17
/*      */     //   729: invokevirtual getArgumentType : ()Lcom/hypixel/hytale/server/core/command/system/arguments/types/ArgumentType;
/*      */     //   732: invokevirtual add : (Ljava/lang/Object;)Z
/*      */     //   735: pop
/*      */     //   736: goto -> 703
/*      */     //   739: aload_0
/*      */     //   740: getfield optionalArguments : Ljava/util/Map;
/*      */     //   743: invokeinterface values : ()Ljava/util/Collection;
/*      */     //   748: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   753: astore #16
/*      */     //   755: aload #16
/*      */     //   757: invokeinterface hasNext : ()Z
/*      */     //   762: ifeq -> 791
/*      */     //   765: aload #16
/*      */     //   767: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   772: checkcast com/hypixel/hytale/server/core/command/system/arguments/system/AbstractOptionalArg
/*      */     //   775: astore #17
/*      */     //   777: aload #15
/*      */     //   779: aload #17
/*      */     //   781: invokevirtual getArgumentType : ()Lcom/hypixel/hytale/server/core/command/system/arguments/types/ArgumentType;
/*      */     //   784: invokevirtual add : (Ljava/lang/Object;)Z
/*      */     //   787: pop
/*      */     //   788: goto -> 755
/*      */     //   791: aload #15
/*      */     //   793: invokevirtual iterator : ()Ljava/util/Iterator;
/*      */     //   796: astore #16
/*      */     //   798: aload #16
/*      */     //   800: invokeinterface hasNext : ()Z
/*      */     //   805: ifeq -> 886
/*      */     //   808: aload #16
/*      */     //   810: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   815: checkcast com/hypixel/hytale/server/core/command/system/arguments/types/ArgumentType
/*      */     //   818: astore #17
/*      */     //   820: aload #14
/*      */     //   822: ldc_w '\\n    '
/*      */     //   825: invokevirtual insert : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   828: aload #17
/*      */     //   830: invokevirtual getName : ()Lcom/hypixel/hytale/server/core/Message;
/*      */     //   833: invokevirtual insert : (Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   836: ldc_w ': '
/*      */     //   839: invokevirtual insert : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   842: aload #17
/*      */     //   844: invokevirtual getArgumentUsage : ()Lcom/hypixel/hytale/server/core/Message;
/*      */     //   847: invokevirtual insert : (Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   850: ldc_w '\\n        Examples: '
/*      */     //   853: invokevirtual insert : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   856: ldc_w '''
/*      */     //   859: invokevirtual insert : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   862: ldc_w '', ''
/*      */     //   865: aload #17
/*      */     //   867: invokevirtual getExamples : ()[Ljava/lang/String;
/*      */     //   870: invokestatic join : (Ljava/lang/CharSequence;[Ljava/lang/CharSequence;)Ljava/lang/String;
/*      */     //   873: invokevirtual insert : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   876: ldc_w ''.'
/*      */     //   879: invokevirtual insert : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   882: pop
/*      */     //   883: goto -> 798
/*      */     //   886: ldc_w 'server.commands.parsing.usage.header'
/*      */     //   889: invokestatic translation : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   892: ldc_w 'fullyQualifiedName'
/*      */     //   895: aload_0
/*      */     //   896: invokevirtual getFullyQualifiedName : ()Ljava/lang/String;
/*      */     //   899: invokevirtual param : (Ljava/lang/String;Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   902: ldc_w 'description'
/*      */     //   905: aload_0
/*      */     //   906: getfield description : Ljava/lang/String;
/*      */     //   909: invokestatic translation : (Ljava/lang/String;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   912: invokevirtual param : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   915: ldc_w 'listOfRequiredArgs'
/*      */     //   918: aload_2
/*      */     //   919: invokevirtual param : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   922: ldc_w 'requiresConfirmation'
/*      */     //   925: aload_3
/*      */     //   926: invokevirtual param : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   929: ldc_w 'requiredArgs'
/*      */     //   932: aload #4
/*      */     //   934: invokevirtual param : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   937: ldc_w 'optionalArgs'
/*      */     //   940: aload #6
/*      */     //   942: invokevirtual param : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   945: ldc_w 'defaultArgs'
/*      */     //   948: aload #8
/*      */     //   950: invokevirtual param : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   953: ldc_w 'flagArgs'
/*      */     //   956: aload #10
/*      */     //   958: invokevirtual param : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   961: ldc_w 'argTypes'
/*      */     //   964: aload #14
/*      */     //   966: invokevirtual param : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   969: ldc_w 'variants'
/*      */     //   972: aload #12
/*      */     //   974: invokevirtual param : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   977: ldc_w 'subcommands'
/*      */     //   980: aload #13
/*      */     //   982: invokevirtual param : (Ljava/lang/String;Lcom/hypixel/hytale/server/core/Message;)Lcom/hypixel/hytale/server/core/Message;
/*      */     //   985: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #951	-> 0
/*      */     //   #952	-> 6
/*      */     //   #953	-> 36
/*      */     //   #954	-> 52
/*      */     //   #955	-> 63
/*      */     //   #957	-> 71
/*      */     //   #959	-> 74
/*      */     //   #961	-> 93
/*      */     //   #962	-> 100
/*      */     //   #964	-> 103
/*      */     //   #965	-> 136
/*      */     //   #966	-> 139
/*      */     //   #967	-> 156
/*      */     //   #969	-> 159
/*      */     //   #970	-> 166
/*      */     //   #972	-> 169
/*      */     //   #973	-> 176
/*      */     //   #975	-> 179
/*      */     //   #976	-> 186
/*      */     //   #978	-> 189
/*      */     //   #979	-> 227
/*      */     //   #981	-> 239
/*      */     //   #983	-> 264
/*      */     //   #984	-> 312
/*      */     //   #985	-> 319
/*      */     //   #986	-> 322
/*      */     //   #987	-> 339
/*      */     //   #988	-> 342
/*      */     //   #989	-> 349
/*      */     //   #990	-> 352
/*      */     //   #991	-> 369
/*      */     //   #992	-> 372
/*      */     //   #993	-> 379
/*      */     //   #994	-> 382
/*      */     //   #995	-> 399
/*      */     //   #999	-> 402
/*      */     //   #1001	-> 405
/*      */     //   #1002	-> 410
/*      */     //   #1003	-> 421
/*      */     //   #1006	-> 426
/*      */     //   #1007	-> 431
/*      */     //   #1008	-> 442
/*      */     //   #1011	-> 447
/*      */     //   #1012	-> 452
/*      */     //   #1013	-> 463
/*      */     //   #1016	-> 468
/*      */     //   #1017	-> 473
/*      */     //   #1018	-> 484
/*      */     //   #1021	-> 489
/*      */     //   #1022	-> 496
/*      */     //   #1023	-> 534
/*      */     //   #1024	-> 543
/*      */     //   #1025	-> 557
/*      */     //   #1026	-> 569
/*      */     //   #1024	-> 575
/*      */     //   #1029	-> 579
/*      */     //   #1031	-> 582
/*      */     //   #1032	-> 589
/*      */     //   #1033	-> 627
/*      */     //   #1034	-> 636
/*      */     //   #1035	-> 650
/*      */     //   #1036	-> 662
/*      */     //   #1034	-> 668
/*      */     //   #1039	-> 672
/*      */     //   #1041	-> 675
/*      */     //   #1042	-> 683
/*      */     //   #1043	-> 692
/*      */     //   #1044	-> 725
/*      */     //   #1045	-> 736
/*      */     //   #1047	-> 739
/*      */     //   #1048	-> 777
/*      */     //   #1049	-> 788
/*      */     //   #1051	-> 791
/*      */     //   #1052	-> 820
/*      */     //   #1053	-> 844
/*      */     //   #1054	-> 867
/*      */     //   #1055	-> 883
/*      */     //   #1057	-> 886
/*      */     //   #1058	-> 896
/*      */     //   #1059	-> 909
/*      */     //   #1060	-> 919
/*      */     //   #1061	-> 926
/*      */     //   #1062	-> 934
/*      */     //   #1063	-> 942
/*      */     //   #1064	-> 950
/*      */     //   #1065	-> 958
/*      */     //   #1066	-> 966
/*      */     //   #1067	-> 974
/*      */     //   #1068	-> 982
/*      */     //   #1057	-> 985
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   36	35	4	requiredArgument	Lcom/hypixel/hytale/server/core/command/system/arguments/system/RequiredArg;
/*      */     //   136	20	7	requiredArgument	Lcom/hypixel/hytale/server/core/command/system/arguments/system/RequiredArg;
/*      */     //   319	23	17	ignored	Lcom/hypixel/hytale/server/core/command/system/arguments/system/OptionalArg;
/*      */     //   349	23	18	ignored	Lcom/hypixel/hytale/server/core/command/system/arguments/system/DefaultArg;
/*      */     //   379	23	19	ignored	Lcom/hypixel/hytale/server/core/command/system/arguments/system/FlagArg;
/*      */     //   239	163	14	arg	Lcom/hypixel/hytale/server/core/command/system/arguments/system/AbstractOptionalArg;
/*      */     //   227	175	13	entry	Ljava/util/Map$Entry;
/*      */     //   534	45	14	value	Lcom/hypixel/hytale/server/core/command/system/AbstractCommand;
/*      */     //   627	45	15	value	Lcom/hypixel/hytale/server/core/command/system/AbstractCommand;
/*      */     //   725	11	17	requiredArgument	Lcom/hypixel/hytale/server/core/command/system/arguments/system/RequiredArg;
/*      */     //   777	11	17	optionalArgument	Lcom/hypixel/hytale/server/core/command/system/arguments/system/AbstractOptionalArg;
/*      */     //   820	63	17	argumentType	Lcom/hypixel/hytale/server/core/command/system/arguments/types/ArgumentType;
/*      */     //   0	986	0	this	Lcom/hypixel/hytale/server/core/command/system/AbstractCommand;
/*      */     //   0	986	1	sender	Lcom/hypixel/hytale/server/core/command/system/CommandSender;
/*      */     //   6	980	2	requiredArgsMessage	Lcom/hypixel/hytale/server/core/Message;
/*      */     //   93	893	3	requiresConfirmationMessage	Lcom/hypixel/hytale/server/core/Message;
/*      */     //   100	886	4	requiredArgs	Lcom/hypixel/hytale/server/core/Message;
/*      */     //   103	883	5	requiredArgsShown	Z
/*      */     //   166	820	6	optionalArgs	Lcom/hypixel/hytale/server/core/Message;
/*      */     //   169	817	7	optionalArgsShown	Z
/*      */     //   176	810	8	defaultArgs	Lcom/hypixel/hytale/server/core/Message;
/*      */     //   179	807	9	defaultArgsShown	Z
/*      */     //   186	800	10	flagArgs	Lcom/hypixel/hytale/server/core/Message;
/*      */     //   189	797	11	flagArgsShown	Z
/*      */     //   496	490	12	variantsMessage	Lcom/hypixel/hytale/server/core/Message;
/*      */     //   589	397	13	subcommandsMessage	Lcom/hypixel/hytale/server/core/Message;
/*      */     //   683	303	14	argTypesMessage	Lcom/hypixel/hytale/server/core/Message;
/*      */     //   692	294	15	allArgumentTypes	Ljava/util/HashSet;
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   36	35	4	requiredArgument	Lcom/hypixel/hytale/server/core/command/system/arguments/system/RequiredArg<*>;
/*      */     //   136	20	7	requiredArgument	Lcom/hypixel/hytale/server/core/command/system/arguments/system/RequiredArg<*>;
/*      */     //   239	163	14	arg	Lcom/hypixel/hytale/server/core/command/system/arguments/system/AbstractOptionalArg<+Lcom/hypixel/hytale/server/core/command/system/arguments/system/Argument<**>;*>;
/*      */     //   227	175	13	entry	Ljava/util/Map$Entry<Ljava/lang/String;Lcom/hypixel/hytale/server/core/command/system/arguments/system/AbstractOptionalArg<**>;>;
/*      */     //   725	11	17	requiredArgument	Lcom/hypixel/hytale/server/core/command/system/arguments/system/RequiredArg<*>;
/*      */     //   777	11	17	optionalArgument	Lcom/hypixel/hytale/server/core/command/system/arguments/system/AbstractOptionalArg<**>;
/*      */     //   820	63	17	argumentType	Lcom/hypixel/hytale/server/core/command/system/arguments/types/ArgumentType<*>;
/*      */     //   692	294	15	allArgumentTypes	Ljava/util/HashSet<Lcom/hypixel/hytale/server/core/command/system/arguments/types/ArgumentType<*>;>;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Message getUsageShort(@Nonnull CommandSender sender, boolean fullyQualify) {
/* 1080 */     String indent = " ".repeat(countParents());
/*      */     
/* 1082 */     if (!this.subCommands.isEmpty() || !this.variantCommands.isEmpty()) {
/* 1083 */       String prefix = (this.parentCommand == null) ? "/" : indent;
/* 1084 */       Message message1 = Message.raw(prefix).insert(this.name);
/* 1085 */       boolean anyPermissible = false;
/*      */ 
/*      */       
/* 1088 */       for (ObjectIterator<AbstractCommand> objectIterator = this.variantCommands.values().iterator(); objectIterator.hasNext(); ) { AbstractCommand variantCommand = objectIterator.next();
/* 1089 */         if (variantCommand.hasPermission(sender)) {
/* 1090 */           message1.insert("\n ").insert(indent).insert(variantCommand.getUsageShort(sender, fullyQualify));
/* 1091 */           anyPermissible = true;
/*      */         }  }
/*      */ 
/*      */ 
/*      */       
/* 1096 */       for (AbstractCommand subCommand : this.subCommands.values()) {
/* 1097 */         if (subCommand.hasPermission(sender)) {
/* 1098 */           message1.insert("\n ").insert(indent).insert(subCommand.getUsageShort(sender, fullyQualify));
/* 1099 */           anyPermissible = true;
/*      */         } 
/*      */       } 
/*      */       
/* 1103 */       if (!anyPermissible) {
/* 1104 */         message1.insert("\n ").insert(MESSAGE_COMMANDS_HELP_NO_PERMISSIBLE_SUB_COMMAND);
/*      */       }
/* 1106 */       return message1;
/*      */     } 
/*      */     
/* 1109 */     String fullyQualifiedName = getFullyQualifiedName();
/* 1110 */     Message message = Message.raw(indent).insert(fullyQualify ? ((fullyQualifiedName != null) ? fullyQualifiedName : "???") : ((this.name != null) ? this.name : ""));
/*      */ 
/*      */     
/* 1113 */     for (RequiredArg<?> requiredArgument : this.requiredArguments) {
/* 1114 */       message.insert(" ").insert(requiredArgument.getUsageOneLiner().color("#C1E0FF"));
/*      */     }
/*      */ 
/*      */     
/* 1118 */     for (AbstractOptionalArg<?, ?> optionalArgument : this.optionalArguments.values()) {
/* 1119 */       if (optionalArgument.hasPermission(sender)) {
/* 1120 */         message.insert(" ").insert(optionalArgument.getUsageOneLiner().color("#7E9EBC"));
/*      */       }
/*      */     } 
/*      */     
/* 1124 */     return message;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private <R extends RequiredArg<D>, D> R registerRequiredArg(@Nonnull R requiredArgument) {
/* 1134 */     if (this.hasBeenRegistered) throw new IllegalStateException("Cannot add new arguments when a command has already completed registration"); 
/* 1135 */     if (!requiredArgument.getCommandRegisteredTo().equals(this) || this.requiredArguments.contains(requiredArgument)) {
/* 1136 */       throw new IllegalArgumentException("Cannot re-use arguments");
/*      */     }
/*      */     
/* 1139 */     this.totalNumRequiredParameters += requiredArgument.getArgumentType().getNumberOfParameters();
/* 1140 */     this.requiredArguments.add((RequiredArg<?>)requiredArgument);
/* 1141 */     return requiredArgument;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private <R extends AbstractOptionalArg<?, D>, D> R registerOptionalArg(@Nonnull R optionalArgument) {
/* 1151 */     if (this.hasBeenRegistered) throw new IllegalStateException("Cannot add new arguments when a command has already completed registration"); 
/* 1152 */     if (!optionalArgument.getCommandRegisteredTo().equals(this) || this.optionalArguments.containsKey(optionalArgument.getName().toLowerCase())) {
/* 1153 */       throw new IllegalArgumentException("Cannot re-use arguments");
/*      */     }
/*      */     
/* 1156 */     this.optionalArguments.put(optionalArgument.getName().toLowerCase(), (AbstractOptionalArg<?, ?>)optionalArgument);
/* 1157 */     return optionalArgument;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public <D> RequiredArg<D> withRequiredArg(@Nonnull String name, @Nonnull String description, @Nonnull ArgumentType<D> argType) {
/* 1171 */     return registerRequiredArg(new RequiredArg(this, name, description, argType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <W extends com.hypixel.hytale.server.core.command.system.arguments.system.WrappedArg<D>, D> W withRequiredArg(@Nonnull String name, @Nonnull String description, @Nonnull ArgWrapper<W, D> wrapper) {
/* 1187 */     return (W)wrapper.wrapArg((Argument)registerRequiredArg(new RequiredArg(this, name, description, wrapper.argumentType())));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public <D> RequiredArg<List<D>> withListRequiredArg(@Nonnull String name, @Nonnull String description, @Nonnull ArgumentType<D> argType) {
/* 1202 */     return registerRequiredArg(new RequiredArg(this, name, description, (ArgumentType)new ListArgumentType(argType)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public <D> DefaultArg<D> withDefaultArg(String name, String description, ArgumentType<D> argType, @Nullable D defaultValue, String defaultValueDescription) {
/* 1218 */     return registerOptionalArg(new DefaultArg(this, name, description, argType, defaultValue, defaultValueDescription));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <W extends com.hypixel.hytale.server.core.command.system.arguments.system.WrappedArg<D>, D> W withDefaultArg(@Nonnull String name, @Nonnull String description, @Nonnull ArgWrapper<W, D> wrapper, D defaultValue, @Nonnull String defaultValueDescription) {
/* 1239 */     return (W)wrapper.wrapArg((Argument)registerOptionalArg(new DefaultArg(this, name, description, wrapper.argumentType(), defaultValue, defaultValueDescription)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public <D> DefaultArg<List<D>> withListDefaultArg(@Nonnull String name, @Nonnull String description, @Nonnull ArgumentType<D> argType, List<D> defaultValue, @Nonnull String defaultValueDescription) {
/* 1259 */     return registerOptionalArg(new DefaultArg(this, name, description, (ArgumentType)new ListArgumentType(argType), defaultValue, defaultValueDescription));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public <D> OptionalArg<D> withOptionalArg(@Nonnull String name, @Nonnull String description, @Nonnull ArgumentType<D> argType) {
/* 1274 */     return registerOptionalArg(new OptionalArg(this, name, description, argType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <W extends com.hypixel.hytale.server.core.command.system.arguments.system.WrappedArg<D>, D> W withOptionalArg(@Nonnull String name, @Nonnull String description, @Nonnull ArgWrapper<W, D> wrapper) {
/* 1291 */     return (W)wrapper.wrapArg((Argument)registerOptionalArg(new OptionalArg(this, name, description, wrapper.argumentType())));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public <D> OptionalArg<List<D>> withListOptionalArg(@Nonnull String name, @Nonnull String description, @Nonnull ArgumentType<D> argType) {
/* 1307 */     return registerOptionalArg(new OptionalArg(this, name, description, (ArgumentType)new ListArgumentType(argType)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public FlagArg withFlagArg(@Nonnull String name, @Nonnull String description) {
/* 1320 */     return registerOptionalArg(new FlagArg(this, name, description));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isVariant() {
/* 1327 */     return (this.name == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public String getName() {
/* 1335 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Set<String> getAliases() {
/* 1343 */     return this.aliases;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1350 */     return this.description;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public CommandOwner getOwner() {
/* 1358 */     return this.owner;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public String getPermission() {
/* 1366 */     return this.permission;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Map<String, AbstractCommand> getSubCommands() {
/* 1374 */     return this.subCommands;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public List<RequiredArg<?>> getRequiredArguments() {
/* 1379 */     return this.requiredArguments;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasBeenRegistered() {
/* 1386 */     return this.hasBeenRegistered;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   protected abstract CompletableFuture<Void> execute(@Nonnull CommandContext paramCommandContext);
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\AbstractCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */