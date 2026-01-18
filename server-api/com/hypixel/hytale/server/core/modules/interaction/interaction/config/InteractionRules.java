/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.InteractionRules;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import java.util.function.Supplier;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InteractionRules
/*     */   implements NetworkSerializable<InteractionRules>
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<InteractionRules> CODEC;
/*     */   
/*     */   static {
/* 109 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(InteractionRules.class, InteractionRules::new).appendInherited(new KeyedCodec("BlockedBy", (Codec)InteractionModule.INTERACTION_TYPE_SET_CODEC), (o, i) -> o.blockedBy = i, o -> o.blockedBy, (o, p) -> o.blockedBy = p.blockedBy).documentation("A collection of interaction types that should block this interaction from starting. If not set then a set of default rules will be applied based on the interaction type that theinteraction is fired with.\nThis is only effective when used on the root interaction of a chain.").add()).appendInherited(new KeyedCodec("Blocking", (Codec)InteractionModule.INTERACTION_TYPE_SET_CODEC), (o, i) -> o.blocking = i, o -> o.blocking, (o, p) -> o.blocking = p.blocking).documentation("A collection of interaction types that this interaction blocks from starting whilst running.\nDefaults to an empty set (blocking nothing).").addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("InterruptedBy", (Codec)InteractionModule.INTERACTION_TYPE_SET_CODEC), (o, i) -> o.interruptedBy = i, o -> o.interruptedBy, (o, p) -> o.interruptedBy = p.interruptedBy).documentation("A collection of interaction types that should stop this interaction while it's running.\nThis is only effective when used on the root interaction of a chain.").add()).appendInherited(new KeyedCodec("Interrupting", (Codec)InteractionModule.INTERACTION_TYPE_SET_CODEC), (o, i) -> o.interrupting = i, o -> o.interrupting, (o, p) -> o.interrupting = p.interrupting).documentation("A collection of interaction types that this interaction should stop when it starts.").add()).appendInherited(new KeyedCodec("BlockedByBypass", (Codec)Codec.STRING), (o, i) -> o.blockedByBypass = i, o -> o.blockedByBypass, (o, p) -> o.blockedByBypass = p.blockedByBypass).documentation("A tag that if matched will bypass the `BlockedBy` rules.").add()).appendInherited(new KeyedCodec("BlockingBypass", (Codec)Codec.STRING), (o, i) -> o.blockingBypass = i, o -> o.blockingBypass, (o, p) -> o.blockingBypass = p.blockingBypass).documentation("A tag that if matched will bypass the `Blocking` rules.").add()).appendInherited(new KeyedCodec("InterruptedByBypass", (Codec)Codec.STRING), (o, i) -> o.interruptedByBypass = i, o -> o.interruptedByBypass, (o, p) -> o.interruptedByBypass = p.interruptedByBypass).documentation("A tag that if matched will bypass the `InterruptedBy` rules.").add()).appendInherited(new KeyedCodec("InterruptingBypass", (Codec)Codec.STRING), (o, i) -> o.interruptingBypass = i, o -> o.interruptingBypass, (o, p) -> o.interruptingBypass = p.interruptingBypass).documentation("A tag that if matched will bypass the `Interrupting` rules.").add()).afterDecode(i -> { if (i.blockedByBypass != null) i.blockedByBypassIndex = AssetRegistry.getOrCreateTagIndex(i.blockedByBypass);  if (i.blockingBypass != null) i.blockingBypassIndex = AssetRegistry.getOrCreateTagIndex(i.blockingBypass);  if (i.interruptedByBypass != null) i.interruptedByBypassIndex = AssetRegistry.getOrCreateTagIndex(i.interruptedByBypass);  if (i.interruptingBypass != null) i.interruptingBypassIndex = AssetRegistry.getOrCreateTagIndex(i.interruptingBypass);  })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 115 */   public static InteractionRules DEFAULT_RULES = new InteractionRules();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Set<InteractionType> blockedBy;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 127 */   protected Set<InteractionType> blocking = Collections.emptySet();
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Set<InteractionType> interruptedBy;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Set<InteractionType> interrupting;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected String blockedByBypass;
/*     */ 
/*     */   
/* 143 */   protected int blockedByBypassIndex = Integer.MIN_VALUE;
/*     */   
/*     */   @Nullable
/*     */   protected String blockingBypass;
/* 147 */   protected int blockingBypassIndex = Integer.MIN_VALUE;
/*     */   
/*     */   @Nullable
/*     */   protected String interruptedByBypass;
/* 151 */   protected int interruptedByBypassIndex = Integer.MIN_VALUE;
/*     */   
/*     */   @Nullable
/*     */   protected String interruptingBypass;
/* 155 */   protected int interruptingBypassIndex = Integer.MIN_VALUE;
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
/*     */   public boolean validateInterrupts(@Nonnull InteractionType type, @Nonnull Int2ObjectMap<IntSet> selfTags, @Nonnull InteractionType otherType, @Nonnull Int2ObjectMap<IntSet> otherTags, @Nonnull InteractionRules otherRules) {
/* 174 */     if (otherRules.interruptedBy != null && otherRules.interruptedBy.contains(type) && (
/* 175 */       otherRules.interruptedByBypassIndex == Integer.MIN_VALUE || !selfTags.containsKey(otherRules.interruptedByBypassIndex))) {
/* 176 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 180 */     if (this.interrupting != null && this.interrupting.contains(otherType) && (
/* 181 */       this.interruptingBypassIndex == Integer.MIN_VALUE || !otherTags.containsKey(this.interruptingBypassIndex))) {
/* 182 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 186 */     return false;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateBlocked(@Nonnull InteractionType type, @Nonnull Int2ObjectMap<IntSet> selfTags, @Nonnull InteractionType otherType, @Nonnull Int2ObjectMap<IntSet> otherTags, @Nonnull InteractionRules otherRules) {
/* 206 */     Set<InteractionType> blockedBy = (this.blockedBy != null) ? this.blockedBy : InteractionTypeUtils.DEFAULT_INTERACTION_BLOCKED_BY.get(type);
/* 207 */     if (blockedBy.contains(otherType) && (
/* 208 */       this.blockedByBypassIndex == Integer.MIN_VALUE || !otherTags.containsKey(this.blockedByBypassIndex))) {
/* 209 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 213 */     if (otherRules.blocking.contains(type) && (
/* 214 */       otherRules.blockingBypassIndex == Integer.MIN_VALUE || !selfTags.containsKey(otherRules.blockingBypassIndex))) {
/* 215 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 219 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionRules toPacket() {
/* 226 */     InteractionRules packet = new InteractionRules();
/* 227 */     packet.blockedBy = (this.blockedBy == null) ? null : (InteractionType[])this.blockedBy.toArray(x$0 -> new InteractionType[x$0]);
/* 228 */     packet.blocking = this.blocking.isEmpty() ? null : (InteractionType[])this.blocking.toArray(x$0 -> new InteractionType[x$0]);
/* 229 */     packet.interruptedBy = (this.interruptedBy == null) ? null : (InteractionType[])this.interruptedBy.toArray(x$0 -> new InteractionType[x$0]);
/* 230 */     packet.interrupting = (this.interrupting == null) ? null : (InteractionType[])this.interrupting.toArray(x$0 -> new InteractionType[x$0]);
/* 231 */     packet.blockedByBypassIndex = this.blockedByBypassIndex;
/* 232 */     packet.blockingBypassIndex = this.blockingBypassIndex;
/* 233 */     packet.interruptedByBypassIndex = this.interruptedByBypassIndex;
/* 234 */     packet.interruptingBypassIndex = this.interruptingBypassIndex;
/* 235 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 241 */     return "InteractionRules{blockedBy=" + String.valueOf(this.blockedBy) + ", blocking=" + String.valueOf(this.blocking) + ", interruptedBy=" + String.valueOf(this.interruptedBy) + ", interrupting=" + String.valueOf(this.interrupting) + ", blockedByBypass='" + this.blockedByBypass + "', blockedByBypassIndex=" + this.blockedByBypassIndex + ", blockingBypass='" + this.blockingBypass + "', blockingBypassIndex=" + this.blockingBypassIndex + ", interruptedByBypass='" + this.interruptedByBypass + "', interruptedByBypassIndex=" + this.interruptedByBypassIndex + ", interruptingBypass='" + this.interruptingBypass + "', interruptingBypassIndex=" + this.interruptedByBypassIndex + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\InteractionRules.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */