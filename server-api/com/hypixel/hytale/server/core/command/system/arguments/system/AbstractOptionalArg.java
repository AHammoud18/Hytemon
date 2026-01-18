/*     */ package com.hypixel.hytale.server.core.command.system.arguments.system;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*     */ import com.hypixel.hytale.server.core.command.system.ParseResult;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
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
/*     */ public abstract class AbstractOptionalArg<Arg extends Argument<Arg, DataType>, DataType>
/*     */   extends Argument<Arg, DataType>
/*     */ {
/*     */   @Nonnull
/*     */   private final Set<String> aliases;
/*     */   @Nullable
/*     */   private String permission;
/*     */   private Set<AbstractOptionalArg<?, ?>> requiredIf;
/*     */   private Set<AbstractOptionalArg<?, ?>> requiredIfAbsent;
/*     */   private Set<AbstractOptionalArg<?, ?>> availableOnlyIfAll;
/*     */   private Set<AbstractOptionalArg<?, ?>> availableOnlyIfAllAbsent;
/*     */   
/*     */   AbstractOptionalArg(@Nonnull AbstractCommand commandRegisteredTo, @Nonnull String name, @Nonnull String description, @Nonnull ArgumentType<DataType> argumentType) {
/*  59 */     super(commandRegisteredTo, name, description, argumentType);
/*  60 */     this.aliases = new HashSet<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Arg addAliases(@Nonnull String... newAliases) {
/*  70 */     if (getCommandRegisteredTo().hasBeenRegistered()) {
/*  71 */       throw new IllegalStateException("Cannot change aliases for an argument after a command has already been registered");
/*     */     }
/*     */     
/*  74 */     for (String newAlias : newAliases) {
/*  75 */       this.aliases.add(newAlias.toLowerCase());
/*     */     }
/*     */     
/*  78 */     return getThis();
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
/*     */   public Arg requiredIf(@Nonnull AbstractOptionalArg<?, ?> dependent, @Nullable AbstractOptionalArg<?, ?>... otherDependents) {
/*  90 */     if (this.requiredIf == null) this.requiredIf = new HashSet<>();
/*     */     
/*  92 */     if (!addDependencyArg(this.requiredIf, this.requiredIfAbsent, dependent, otherDependents)) {
/*  93 */       throw new IllegalStateException("Cannot have one argument in both requiredIf and requiredIfAbsent. Argument: " + dependent.getName());
/*     */     }
/*     */     
/*  96 */     return getThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Arg requiredIf(@Nonnull AbstractOptionalArg<?, ?> dependent) {
/* 106 */     return requiredIf(dependent, (AbstractOptionalArg<?, ?>[])null);
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
/*     */   public Arg requiredIfAbsent(@Nonnull AbstractOptionalArg<?, ?> dependent, @Nullable AbstractOptionalArg<?, ?>... otherDependents) {
/* 118 */     if (this.requiredIfAbsent == null) this.requiredIfAbsent = new HashSet<>();
/*     */     
/* 120 */     if (!addDependencyArg(this.requiredIfAbsent, this.requiredIf, dependent, otherDependents)) {
/* 121 */       throw new IllegalStateException("Cannot have one argument in both requiredIf and requiredIfAbsent. Argument: " + dependent.getName());
/*     */     }
/*     */     
/* 124 */     return getThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Arg requiredIfAbsent(@Nonnull AbstractOptionalArg<?, ?> dependent) {
/* 134 */     return requiredIfAbsent(dependent, (AbstractOptionalArg<?, ?>[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Arg availableOnlyIfAll(@Nonnull AbstractOptionalArg<?, ?> dependent, @Nullable AbstractOptionalArg<?, ?>... otherDependents) {
/* 145 */     if (this.availableOnlyIfAll == null) this.availableOnlyIfAll = new HashSet<>();
/*     */     
/* 147 */     if (!addDependencyArg(this.availableOnlyIfAll, this.availableOnlyIfAllAbsent, dependent, otherDependents)) {
/* 148 */       throw new IllegalStateException("Cannot have one argument in both availableIf and availableIfAbsent. Argument: " + dependent.getName());
/*     */     }
/*     */     
/* 151 */     return getThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Arg availableOnlyIfAll(@Nonnull AbstractOptionalArg<?, ?> dependent) {
/* 161 */     return availableOnlyIfAll(dependent, (AbstractOptionalArg<?, ?>[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Arg availableOnlyIfAllAbsent(@Nonnull AbstractOptionalArg<?, ?> dependent, @Nullable AbstractOptionalArg<?, ?>... otherDependents) {
/* 172 */     if (this.availableOnlyIfAllAbsent == null) this.availableOnlyIfAllAbsent = new HashSet<>();
/*     */     
/* 174 */     if (!addDependencyArg(this.availableOnlyIfAllAbsent, this.availableOnlyIfAll, dependent, otherDependents)) {
/* 175 */       throw new IllegalStateException("Cannot have one argument in both availableIf and availableIfAbsent. Argument: " + dependent.getName());
/*     */     }
/*     */     
/* 178 */     return getThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Arg availableOnlyIfAllAbsent(@Nonnull AbstractOptionalArg<?, ?> dependent) {
/* 188 */     return availableOnlyIfAllAbsent(dependent, (AbstractOptionalArg<?, ?>[])null);
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
/*     */   private boolean addDependencyArg(@Nonnull Set<AbstractOptionalArg<?, ?>> set, @Nullable Set<AbstractOptionalArg<?, ?>> oppositeSet, AbstractOptionalArg<?, ?> dependent, @Nullable AbstractOptionalArg<?, ?>... otherDependents) {
/* 205 */     if (getCommandRegisteredTo().hasBeenRegistered()) {
/* 206 */       throw new IllegalStateException("Cannot change argument dependencies after command has completed registration");
/*     */     }
/*     */     
/* 209 */     if (oppositeSet != null && oppositeSet.contains(dependent)) return false;
/*     */     
/* 211 */     set.add(dependent);
/*     */     
/* 213 */     if (otherDependents != null) {
/* 214 */       if (oppositeSet != null)
/* 215 */         for (AbstractOptionalArg<?, ?> otherDependent : otherDependents) {
/* 216 */           if (oppositeSet.contains(otherDependent)) return false;
/*     */         
/*     */         }  
/* 219 */       Collections.addAll(this.requiredIfAbsent, otherDependents);
/*     */     } 
/* 221 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verifyArgumentDependencies(@Nonnull CommandContext context, @Nonnull ParseResult parseResult) {
/* 232 */     boolean provided = context.provided(this);
/* 233 */     if (!provided) {
/* 234 */       if (this.requiredIf != null) {
/* 235 */         for (AbstractOptionalArg<?, ?> arg : this.requiredIf) {
/* 236 */           if (!arg.provided(context))
/*     */             continue; 
/* 238 */           parseResult.fail(Message.translation("server.commands.parsing.error.optionalArgRequiredIf")
/* 239 */               .param("required", getName()).param("requirer", arg.getName()));
/* 240 */           return false;
/*     */         } 
/*     */       }
/*     */       
/* 244 */       if (this.requiredIfAbsent != null) {
/* 245 */         for (AbstractOptionalArg<?, ?> arg : this.requiredIfAbsent) {
/* 246 */           if (arg.provided(context))
/*     */             continue; 
/* 248 */           parseResult.fail(Message.translation("server.commands.parsing.error.optionalArgRequiredIf")
/* 249 */               .param("required", getName()).param("requirer", arg.getName()));
/* 250 */           return false;
/*     */         } 
/*     */       }
/*     */       
/* 254 */       return true;
/*     */     } 
/*     */     
/* 257 */     if (this.availableOnlyIfAll != null) {
/* 258 */       for (AbstractOptionalArg<?, ?> arg : this.availableOnlyIfAll) {
/* 259 */         if (!arg.provided(context)) {
/* 260 */           parseResult.fail(Message.translation("server.commands.parsing.error.optionalArgAvailableIf")
/* 261 */               .param("available", getName())
/* 262 */               .param("required", this.availableOnlyIfAll.stream().map(Argument::getName).collect(Collectors.joining(", "))));
/*     */           
/* 264 */           return false;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 269 */     if (this.availableOnlyIfAllAbsent != null) {
/* 270 */       for (AbstractOptionalArg<?, ?> arg : this.availableOnlyIfAllAbsent) {
/* 271 */         if (arg.provided(context)) {
/* 272 */           parseResult.fail(Message.translation("server.commands.parsing.error.optionalArgAvailableIfAbsent")
/* 273 */               .param("available", getName())
/* 274 */               .param("required", this.availableOnlyIfAllAbsent.stream().map(Argument::getName).collect(Collectors.joining(", "))));
/*     */           
/* 276 */           return false;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 281 */     return true;
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
/*     */   public Arg setPermission(@Nonnull String permission) {
/* 293 */     if (getCommandRegisteredTo().hasBeenRegistered()) {
/* 294 */       throw new IllegalStateException("Cannot change permissions after a command has already been registered");
/*     */     }
/* 296 */     this.permission = permission;
/* 297 */     return getThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<String> getAliases() {
/* 305 */     return Collections.unmodifiableSet(this.aliases);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getPermission() {
/* 313 */     return this.permission;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPermission(@Nonnull CommandSender sender) {
/* 323 */     return (this.permission == null || sender.hasPermission(this.permission));
/*     */   }
/*     */   
/*     */   public static interface DefaultValueArgument<DataType> {
/*     */     DataType getDefaultValue();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\system\AbstractOptionalArg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */