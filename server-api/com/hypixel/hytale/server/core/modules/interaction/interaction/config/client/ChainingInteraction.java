/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionSyncData;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.Collector;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.CollectorTag;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Label;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Operation;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.OperationsBuilder;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
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
/*     */ public class ChainingInteraction
/*     */   extends Interaction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ChainingInteraction> CODEC;
/*     */   protected String chainId;
/*     */   protected float chainingAllowance;
/*     */   protected String[] next;
/*     */   @Nullable
/*     */   protected Map<String, String> flags;
/*     */   @Nullable
/*     */   protected Object2IntMap<String> flagIndex;
/*     */   private String[] sortedFlagKeys;
/*     */   
/*     */   static {
/*  92 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ChainingInteraction.class, ChainingInteraction::new, Interaction.ABSTRACT_CODEC).documentation("Runs one of the entries in `Next` based on how many times this interaction was run before the `ChainingAllowance` timer was reset.")).appendInherited(new KeyedCodec("ChainingAllowance", (Codec)Codec.DOUBLE), (chainingInteraction, d) -> chainingInteraction.chainingAllowance = d.floatValue(), chainingInteraction -> Double.valueOf(chainingInteraction.chainingAllowance), (chainingInteraction, parent) -> chainingInteraction.chainingAllowance = parent.chainingAllowance).documentation("Time in seconds that the user has to run this interaction again in order to hit the next chain entry.\nResets the timer each time the interaction is reached.").add()).appendInherited(new KeyedCodec("Next", (Codec)new ArrayCodec(Interaction.CHILD_ASSET_CODEC, x$0 -> new String[x$0])), (interaction, s) -> interaction.next = s, interaction -> interaction.next, (interaction, parent) -> interaction.next = parent.next).addValidator(Validators.nonNull()).addValidator((Validator)Validators.nonNullArrayElements()).addValidatorLate(() -> Interaction.VALIDATOR_CACHE.getArrayValidator().late()).add()).appendInherited(new KeyedCodec("ChainId", (Codec)Codec.STRING), (o, i) -> o.chainId = i, o -> o.chainId, (o, p) -> o.chainId = p.chainId).add()).appendInherited(new KeyedCodec("Flags", (Codec)new MapCodec(CHILD_ASSET_CODEC, java.util.HashMap::new)), (o, i) -> o.flags = i, o -> o.flags, (o, p) -> o.flags = p.flags).addValidatorLate(() -> Interaction.VALIDATOR_CACHE.getMapValueValidator().late()).add()).afterDecode(o -> { if (o.flags != null) { String[] sortedFlagKeys = o.sortedFlagKeys = (String[])o.flags.keySet().toArray(()); Arrays.sort((Object[])sortedFlagKeys); o.flagIndex = (Object2IntMap<String>)new Object2IntOpenHashMap(); for (int i = 0; i < sortedFlagKeys.length; i++) o.flagIndex.put(sortedFlagKeys[i], i);  }  })).build();
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
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/* 133 */     return WaitForDataFrom.Client;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/* 138 */     InteractionSyncData clientState = context.getClientState();
/* 139 */     assert clientState != null;
/*     */     
/* 141 */     InteractionSyncData state = context.getState();
/*     */     
/* 143 */     if (clientState.flagIndex != -1) {
/* 144 */       state.state = InteractionState.Finished;
/*     */       
/* 146 */       context.jump(context.getLabel(this.next.length + clientState.flagIndex));
/*     */       
/*     */       return;
/*     */     } 
/* 150 */     if (clientState.chainingIndex == -1) {
/* 151 */       state.state = InteractionState.NotFinished;
/*     */       
/*     */       return;
/*     */     } 
/* 155 */     state.state = InteractionState.Finished;
/*     */     
/* 157 */     context.jump(context.getLabel(clientState.chainingIndex));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void simulateTick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/* 163 */     if (!firstRun) {
/*     */       return;
/*     */     }
/*     */     
/* 167 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 168 */     assert commandBuffer != null;
/*     */     
/* 170 */     Ref<EntityStore> ref = context.getEntity();
/* 171 */     Data dataComponent = (Data)commandBuffer.getComponent(ref, Data.getComponentType());
/* 172 */     if (dataComponent == null)
/*     */       return; 
/* 174 */     InteractionSyncData state = context.getState();
/*     */     
/* 176 */     String id = (this.chainId == null) ? this.id : this.chainId;
/* 177 */     Object2IntMap<String> map = (this.chainId == null) ? dataComponent.map : dataComponent.namedMap;
/*     */     
/* 179 */     int lastSequenceIndex = map.getInt(id);
/*     */     
/* 181 */     lastSequenceIndex++;
/* 182 */     if (lastSequenceIndex >= this.next.length) lastSequenceIndex = 0;
/*     */ 
/*     */     
/* 185 */     if (this.chainingAllowance > 0.0F && dataComponent.getTimeSinceLastAttackInSeconds() > this.chainingAllowance) {
/* 186 */       lastSequenceIndex = 0;
/*     */     }
/*     */     
/* 189 */     map.put(id, lastSequenceIndex);
/* 190 */     state.chainingIndex = lastSequenceIndex;
/* 191 */     state.state = InteractionState.Finished;
/* 192 */     context.jump(context.getLabel(lastSequenceIndex));
/* 193 */     dataComponent.lastAttack = System.nanoTime();
/*     */   }
/*     */ 
/*     */   
/*     */   public void compile(@Nonnull OperationsBuilder builder) {
/* 198 */     int len = this.next.length + ((this.sortedFlagKeys != null) ? this.sortedFlagKeys.length : 0);
/* 199 */     Label[] labels = new Label[len];
/*     */     
/* 201 */     for (int i = 0; i < labels.length; i++) {
/* 202 */       labels[i] = builder.createUnresolvedLabel();
/*     */     }
/*     */     
/* 205 */     builder.addOperation((Operation)this, labels);
/*     */     
/* 207 */     Label end = builder.createUnresolvedLabel();
/*     */     int j;
/* 209 */     for (j = 0; j < this.next.length; j++) {
/* 210 */       builder.resolveLabel(labels[j]);
/* 211 */       Interaction interaction = Interaction.getInteractionOrUnknown(this.next[j]);
/* 212 */       interaction.compile(builder);
/* 213 */       builder.jump(end);
/*     */     } 
/*     */     
/* 216 */     if (this.flags != null) {
/* 217 */       for (j = 0; j < this.sortedFlagKeys.length; j++) {
/* 218 */         String flag = this.sortedFlagKeys[j];
/* 219 */         builder.resolveLabel(labels[this.next.length + j]);
/*     */         
/* 221 */         Interaction interaction = Interaction.getInteractionOrUnknown(this.flags.get(flag));
/* 222 */         interaction.compile(builder);
/* 223 */         builder.jump(end);
/*     */       } 
/*     */     }
/*     */     
/* 227 */     builder.resolveLabel(end);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean walk(@Nonnull Collector collector, @Nonnull InteractionContext context) {
/* 232 */     for (int i = 0; i < this.next.length; i++) {
/* 233 */       if (InteractionManager.walkInteraction(collector, context, ChainingTag.of(i), this.next[i])) return true; 
/*     */     } 
/* 235 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 241 */     return (Interaction)new com.hypixel.hytale.protocol.ChainingInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 246 */     super.configurePacket(packet);
/* 247 */     com.hypixel.hytale.protocol.ChainingInteraction p = (com.hypixel.hytale.protocol.ChainingInteraction)packet;
/* 248 */     p.chainingAllowance = this.chainingAllowance;
/*     */     
/* 250 */     int[] chainingNext = p.chainingNext = new int[this.next.length];
/* 251 */     for (int i = 0; i < this.next.length; i++) {
/* 252 */       chainingNext[i] = Interaction.getInteractionIdOrUnknown(this.next[i]);
/*     */     }
/*     */     
/* 255 */     if (this.flags != null) {
/* 256 */       p.flags = (Map)new Object2IntOpenHashMap();
/* 257 */       for (Map.Entry<String, String> e : this.flags.entrySet()) {
/* 258 */         p.flags.put(e.getKey(), Integer.valueOf(Interaction.getInteractionIdOrUnknown(e.getValue())));
/*     */       }
/*     */     } 
/*     */     
/* 262 */     p.chainId = this.chainId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needsRemoteSync() {
/* 267 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 273 */     return "ChainingInteraction{chainingAllowance=" + this.chainingAllowance + ", next=" + 
/*     */       
/* 275 */       Arrays.toString((Object[])this.next) + "} " + super
/* 276 */       .toString();
/*     */   }
/*     */   
/*     */   public static class Data
/*     */     implements Component<EntityStore>
/*     */   {
/*     */     public static ComponentType<EntityStore, Data> getComponentType() {
/* 283 */       return InteractionModule.get().getChainingDataComponent();
/*     */     }
/*     */     
/* 286 */     private final Object2IntMap<String> map = (Object2IntMap<String>)new Object2IntOpenHashMap();
/* 287 */     private final Object2IntMap<String> namedMap = (Object2IntMap<String>)new Object2IntOpenHashMap();
/*     */     private long lastAttack;
/*     */     
/*     */     public float getTimeSinceLastAttackInSeconds() {
/* 291 */       if (this.lastAttack == 0L) return 0.0F; 
/* 292 */       long diff = System.nanoTime() - this.lastAttack;
/* 293 */       return (float)diff / 1.0E9F;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Object2IntMap<String> getNamedMap() {
/* 298 */       return this.namedMap;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Component<EntityStore> clone() {
/* 304 */       Data c = new Data();
/* 305 */       c.map.putAll((Map)this.map);
/* 306 */       c.lastAttack = this.lastAttack;
/* 307 */       return c;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ChainingTag implements CollectorTag {
/*     */     private final int index;
/*     */     
/*     */     private ChainingTag(int index) {
/* 315 */       this.index = index;
/*     */     }
/*     */     
/*     */     public int getIndex() {
/* 319 */       return this.index;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object o) {
/* 324 */       if (this == o) return true; 
/* 325 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 327 */       ChainingTag that = (ChainingTag)o;
/* 328 */       return (this.index == that.index);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 333 */       return this.index;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 339 */       return "ChainingTag{index=" + this.index + "}";
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public static ChainingTag of(int index) {
/* 346 */       return new ChainingTag(index);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\ChainingInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */