/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.none;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionChain;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.meta.DynamicMetaStore;
/*     */ import com.hypixel.hytale.server.core.meta.MetaKey;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.Collector;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.CollectorTag;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.StringTag;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class RepeatInteraction
/*     */   extends SimpleInteraction
/*     */ {
/*     */   public static final BuilderCodec<RepeatInteraction> CODEC;
/*     */   
/*     */   static {
/*  54 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RepeatInteraction.class, RepeatInteraction::new, SimpleInteraction.CODEC).documentation("Forks from the current interaction into one or more chains that run the specified interactions.\n\nWhen run this will create a new chain that will run the interactions specified in `ForkInteractions`. This will then wait until that chain completes. If the chain completes successfully it will then check the `Repeat` field to see if it needs to run again, if not then the interactions `Next` are run otherwise this repeats with the next fork. If the chain fails then any repeating is ignored and the interactions `Failed` are run instead.")).appendInherited(new KeyedCodec("ForkInteractions", (Codec)RootInteraction.CHILD_ASSET_CODEC), (i, s) -> i.forkInteractions = s, i -> i.forkInteractions, (i, parent) -> i.forkInteractions = parent.forkInteractions).documentation("The interactions to run in the forks created by this interaction.").addValidator(Validators.nonNull()).addValidatorLate(() -> RootInteraction.VALIDATOR_CACHE.getValidator().late()).add()).appendInherited(new KeyedCodec("Repeat", (Codec)Codec.INTEGER), (i, s) -> i.repeat = s.intValue(), i -> Integer.valueOf(i.repeat), (i, parent) -> i.repeat = parent.repeat).documentation("The number of times to repeat. -1 is considered as infinite, be careful when using this value.").addValidator(Validators.or(new Validator[] { Validators.greaterThanOrEqual(Integer.valueOf(1)), Validators.equal(Integer.valueOf(-1)) })).add()).build();
/*     */   }
/*  56 */   private static final MetaKey<InteractionChain> FORKED_CHAIN = Interaction.META_REGISTRY.registerMetaObject(i -> null);
/*  57 */   private static final MetaKey<Integer> REMAINING_REPEATS = Interaction.META_REGISTRY.registerMetaObject(i -> null);
/*     */   
/*  59 */   private static final StringTag TAG_FORK = StringTag.of("Fork");
/*  60 */   private static final StringTag TAG_NEXT = StringTag.of("Next");
/*  61 */   private static final StringTag TAG_FAILED = StringTag.of("Failed");
/*     */   
/*     */   protected String forkInteractions;
/*  64 */   protected int repeat = 1;
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/*  69 */     return WaitForDataFrom.None;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/*  74 */     DynamicMetaStore<Interaction> instanceStore = context.getInstanceStore();
/*  75 */     if (firstRun && this.repeat != -1) {
/*  76 */       instanceStore.putMetaObject(REMAINING_REPEATS, Integer.valueOf(this.repeat));
/*     */     }
/*     */     
/*  79 */     InteractionChain chain = (InteractionChain)instanceStore.getMetaObject(FORKED_CHAIN);
/*  80 */     if (chain != null) {
/*  81 */       switch (chain.getServerState()) {
/*     */         case NotFinished:
/*  83 */           (context.getState()).state = InteractionState.NotFinished;
/*     */           return;
/*     */         
/*     */         case Finished:
/*  87 */           if (this.repeat != -1 && ((Integer)instanceStore.getMetaObject(REMAINING_REPEATS)).intValue() <= 0) {
/*  88 */             (context.getState()).state = InteractionState.Finished;
/*  89 */             super.tick0(firstRun, time, type, context, cooldownHandler);
/*     */             return;
/*     */           } 
/*  92 */           (context.getState()).state = InteractionState.NotFinished;
/*     */           break;
/*     */         
/*     */         case Failed:
/*  96 */           (context.getState()).state = InteractionState.Failed;
/*  97 */           super.tick0(firstRun, time, type, context, cooldownHandler);
/*     */           return;
/*     */       } 
/*     */ 
/*     */     
/*     */     }
/* 103 */     chain = context.fork(context.duplicate(), RootInteraction.getRootInteractionOrUnknown(this.forkInteractions), true);
/* 104 */     instanceStore.putMetaObject(FORKED_CHAIN, chain);
/* 105 */     (context.getState()).state = InteractionState.NotFinished;
/* 106 */     if (this.repeat != -1) instanceStore.putMetaObject(REMAINING_REPEATS, Integer.valueOf(((Integer)instanceStore.getMetaObject(REMAINING_REPEATS)).intValue() - 1));
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void simulateTick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/* 113 */     InteractionChain chain = (InteractionChain)context.getInstanceStore().getMetaObject(FORKED_CHAIN);
/* 114 */     DynamicMetaStore<Interaction> instanceStore = context.getInstanceStore();
/* 115 */     if (chain != null) {
/* 116 */       switch (chain.getServerState()) {
/*     */         case NotFinished:
/* 118 */           (context.getState()).state = InteractionState.NotFinished;
/*     */           break;
/*     */         case Finished:
/* 121 */           if (this.repeat != -1 && ((Integer)instanceStore.getMetaObject(REMAINING_REPEATS)).intValue() <= 0) {
/* 122 */             (context.getState()).state = InteractionState.Finished;
/* 123 */             super.simulateTick0(firstRun, time, type, context, cooldownHandler); break;
/*     */           } 
/* 125 */           (context.getState()).state = InteractionState.NotFinished;
/*     */           break;
/*     */         
/*     */         case Failed:
/* 129 */           (context.getState()).state = InteractionState.Failed;
/* 130 */           super.simulateTick0(firstRun, time, type, context, cooldownHandler);
/*     */           break;
/*     */       } 
/*     */     } else {
/* 134 */       (context.getState()).state = InteractionState.NotFinished;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean walk(@Nonnull Collector collector, @Nonnull InteractionContext context) {
/* 140 */     if (this.forkInteractions != null && 
/* 141 */       InteractionManager.walkInteractions(collector, context, (CollectorTag)TAG_FORK, RootInteraction.getRootInteractionOrUnknown(this.forkInteractions).getInteractionIds())) {
/* 142 */       return true;
/*     */     }
/*     */     
/* 145 */     if (this.next != null && 
/* 146 */       InteractionManager.walkInteraction(collector, context, (CollectorTag)TAG_NEXT, this.next)) return true;
/*     */     
/* 148 */     if (this.failed != null && 
/* 149 */       InteractionManager.walkInteraction(collector, context, (CollectorTag)TAG_FAILED, this.failed)) return true;
/*     */     
/* 151 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsRemoteSync() {
/* 157 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 163 */     return (Interaction)new com.hypixel.hytale.protocol.RepeatInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 168 */     super.configurePacket(packet);
/* 169 */     com.hypixel.hytale.protocol.RepeatInteraction p = (com.hypixel.hytale.protocol.RepeatInteraction)packet;
/* 170 */     p.forkInteractions = RootInteraction.getRootInteractionIdOrUnknown(this.forkInteractions);
/* 171 */     p.repeat = this.repeat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 178 */     return "RepeatInteraction{forkInteractions='" + this.forkInteractions + "', repeat=" + this.repeat + "} " + super
/*     */ 
/*     */       
/* 181 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\none\RepeatInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */