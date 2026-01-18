/*     */ package com.hypixel.hytale.server.core.modules.entity.damage;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.SystemGroup;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerInput;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSystems;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Set;
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
/*     */ public class FallDamagePlayers
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   static final float CURVE_MODIFIER = 0.58F;
/*     */   static final float CURVE_MULTIPLIER = 2.0F;
/*     */   public static final double MIN_DAMAGE = 10.0D;
/*     */   @Nonnull
/* 767 */   private static final Query<EntityStore> QUERY = (Query<EntityStore>)Query.and(new Query[] {
/* 768 */         (Query)EntityStatMap.getComponentType(), 
/* 769 */         (Query)MovementStatesComponent.getComponentType(), 
/* 770 */         (Query)EntityModule.get().getPlayerComponentType(), 
/* 771 */         (Query)PlayerInput.getComponentType()
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 778 */   private static final Set<Dependency<EntityStore>> DEPENDENCIES = (Set)Set.of(new SystemDependency(Order.BEFORE, PlayerSystems.ProcessPlayerInput.class));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SystemGroup<EntityStore> getGroup() {
/* 785 */     return DamageModule.get().getGatherDamageGroup();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 791 */     return QUERY;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 797 */     return DEPENDENCIES;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int systemIndex, @NonNullDecl Store<EntityStore> store) {
/* 802 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 803 */     if (!world.getWorldConfig().isFallDamageEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/* 807 */     super.tick(dt, systemIndex, store);
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
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*     */     // Byte code:
/*     */     //   0: aload_3
/*     */     //   1: iload_2
/*     */     //   2: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   5: invokevirtual getComponent : (ILcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   8: checkcast com/hypixel/hytale/server/core/modules/entity/player/PlayerInput
/*     */     //   11: astore #6
/*     */     //   13: getstatic com/hypixel/hytale/server/core/modules/entity/damage/DamageSystems$FallDamagePlayers.$assertionsDisabled : Z
/*     */     //   16: ifne -> 32
/*     */     //   19: aload #6
/*     */     //   21: ifnonnull -> 32
/*     */     //   24: new java/lang/AssertionError
/*     */     //   27: dup
/*     */     //   28: invokespecial <init> : ()V
/*     */     //   31: athrow
/*     */     //   32: aload_3
/*     */     //   33: iload_2
/*     */     //   34: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   37: invokevirtual getComponent : (ILcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   40: checkcast com/hypixel/hytale/server/core/modules/physics/component/Velocity
/*     */     //   43: astore #7
/*     */     //   45: getstatic com/hypixel/hytale/server/core/modules/entity/damage/DamageSystems$FallDamagePlayers.$assertionsDisabled : Z
/*     */     //   48: ifne -> 64
/*     */     //   51: aload #7
/*     */     //   53: ifnonnull -> 64
/*     */     //   56: new java/lang/AssertionError
/*     */     //   59: dup
/*     */     //   60: invokespecial <init> : ()V
/*     */     //   63: athrow
/*     */     //   64: aload #7
/*     */     //   66: invokevirtual getClientVelocity : ()Lcom/hypixel/hytale/math/vector/Vector3d;
/*     */     //   69: invokevirtual getY : ()D
/*     */     //   72: invokestatic abs : (D)D
/*     */     //   75: dstore #8
/*     */     //   77: aload #5
/*     */     //   79: invokevirtual getExternalData : ()Ljava/lang/Object;
/*     */     //   82: checkcast com/hypixel/hytale/server/core/universe/world/storage/EntityStore
/*     */     //   85: invokevirtual getWorld : ()Lcom/hypixel/hytale/server/core/universe/world/World;
/*     */     //   88: astore #10
/*     */     //   90: aload #10
/*     */     //   92: invokevirtual getGameplayConfig : ()Lcom/hypixel/hytale/server/core/asset/type/gameplay/GameplayConfig;
/*     */     //   95: invokevirtual getPlayerConfig : ()Lcom/hypixel/hytale/server/core/asset/type/gameplay/PlayerConfig;
/*     */     //   98: astore #11
/*     */     //   100: aload #6
/*     */     //   102: invokevirtual getMovementUpdateQueue : ()Ljava/util/List;
/*     */     //   105: astore #12
/*     */     //   107: iconst_0
/*     */     //   108: istore #13
/*     */     //   110: iload #13
/*     */     //   112: aload #12
/*     */     //   114: invokeinterface size : ()I
/*     */     //   119: if_icmpge -> 551
/*     */     //   122: aload #12
/*     */     //   124: iload #13
/*     */     //   126: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   131: checkcast com/hypixel/hytale/server/core/modules/entity/player/PlayerInput$InputUpdate
/*     */     //   134: astore #14
/*     */     //   136: aload #14
/*     */     //   138: dup
/*     */     //   139: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   142: pop
/*     */     //   143: astore #15
/*     */     //   145: iconst_0
/*     */     //   146: istore #16
/*     */     //   148: aload #15
/*     */     //   150: iload #16
/*     */     //   152: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   157: lookupswitch default -> 545, 0 -> 184, 1 -> 207
/*     */     //   184: aload #15
/*     */     //   186: checkcast com/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetClientVelocity
/*     */     //   189: astore #17
/*     */     //   191: aload #17
/*     */     //   193: invokevirtual getVelocity : ()Lcom/hypixel/hytale/math/vector/Vector3d;
/*     */     //   196: getfield y : D
/*     */     //   199: invokestatic abs : (D)D
/*     */     //   202: dstore #8
/*     */     //   204: goto -> 545
/*     */     //   207: aload #15
/*     */     //   209: checkcast com/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetMovementStates
/*     */     //   212: astore #18
/*     */     //   214: aload_3
/*     */     //   215: iload_2
/*     */     //   216: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   219: invokevirtual getComponent : (ILcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   222: checkcast com/hypixel/hytale/server/core/entity/entities/Player
/*     */     //   225: astore #19
/*     */     //   227: getstatic com/hypixel/hytale/server/core/modules/entity/damage/DamageSystems$FallDamagePlayers.$assertionsDisabled : Z
/*     */     //   230: ifne -> 246
/*     */     //   233: aload #19
/*     */     //   235: ifnonnull -> 246
/*     */     //   238: new java/lang/AssertionError
/*     */     //   241: dup
/*     */     //   242: invokespecial <init> : ()V
/*     */     //   245: athrow
/*     */     //   246: aload #18
/*     */     //   248: invokevirtual movementStates : ()Lcom/hypixel/hytale/protocol/MovementStates;
/*     */     //   251: getfield onGround : Z
/*     */     //   254: ifeq -> 542
/*     */     //   257: aload #19
/*     */     //   259: invokevirtual getCurrentFallDistance : ()D
/*     */     //   262: dconst_0
/*     */     //   263: dcmpl
/*     */     //   264: ifle -> 542
/*     */     //   267: aload #11
/*     */     //   269: invokevirtual getMovementConfigIndex : ()I
/*     */     //   272: istore #20
/*     */     //   274: invokestatic getAssetMap : ()Lcom/hypixel/hytale/assetstore/map/IndexedLookupTableAssetMap;
/*     */     //   277: iload #20
/*     */     //   279: invokevirtual getAsset : (I)Lcom/hypixel/hytale/assetstore/map/JsonAssetWithMap;
/*     */     //   282: checkcast com/hypixel/hytale/server/core/entity/entities/player/movement/MovementConfig
/*     */     //   285: astore #21
/*     */     //   287: aload #21
/*     */     //   289: invokevirtual getMinFallSpeedToEngageRoll : ()F
/*     */     //   292: fstore #22
/*     */     //   294: dload #8
/*     */     //   296: fload #22
/*     */     //   298: f2d
/*     */     //   299: dcmpl
/*     */     //   300: ifle -> 536
/*     */     //   303: aload #18
/*     */     //   305: invokevirtual movementStates : ()Lcom/hypixel/hytale/protocol/MovementStates;
/*     */     //   308: getfield inFluid : Z
/*     */     //   311: ifne -> 536
/*     */     //   314: aload_3
/*     */     //   315: iload_2
/*     */     //   316: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   319: invokevirtual getComponent : (ILcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   322: checkcast com/hypixel/hytale/server/core/modules/entitystats/EntityStatMap
/*     */     //   325: astore #23
/*     */     //   327: getstatic com/hypixel/hytale/server/core/modules/entity/damage/DamageSystems$FallDamagePlayers.$assertionsDisabled : Z
/*     */     //   330: ifne -> 346
/*     */     //   333: aload #23
/*     */     //   335: ifnonnull -> 346
/*     */     //   338: new java/lang/AssertionError
/*     */     //   341: dup
/*     */     //   342: invokespecial <init> : ()V
/*     */     //   345: athrow
/*     */     //   346: ldc2_w 0.5799999833106995
/*     */     //   349: dload #8
/*     */     //   351: fload #22
/*     */     //   353: f2d
/*     */     //   354: dsub
/*     */     //   355: dmul
/*     */     //   356: ldc2_w 2.0
/*     */     //   359: invokestatic pow : (DD)D
/*     */     //   362: ldc2_w 10.0
/*     */     //   365: dadd
/*     */     //   366: dstore #24
/*     */     //   368: aload #23
/*     */     //   370: invokestatic getHealth : ()I
/*     */     //   373: invokevirtual get : (I)Lcom/hypixel/hytale/server/core/modules/entitystats/EntityStatValue;
/*     */     //   376: astore #26
/*     */     //   378: getstatic com/hypixel/hytale/server/core/modules/entity/damage/DamageSystems$FallDamagePlayers.$assertionsDisabled : Z
/*     */     //   381: ifne -> 397
/*     */     //   384: aload #26
/*     */     //   386: ifnonnull -> 397
/*     */     //   389: new java/lang/AssertionError
/*     */     //   392: dup
/*     */     //   393: invokespecial <init> : ()V
/*     */     //   396: athrow
/*     */     //   397: aload #26
/*     */     //   399: invokevirtual getMax : ()F
/*     */     //   402: fstore #27
/*     */     //   404: fload #27
/*     */     //   406: f2d
/*     */     //   407: ldc2_w 100.0
/*     */     //   410: ddiv
/*     */     //   411: dstore #28
/*     */     //   413: dload #28
/*     */     //   415: dload #24
/*     */     //   417: dmul
/*     */     //   418: invokestatic floor : (D)D
/*     */     //   421: d2i
/*     */     //   422: istore #30
/*     */     //   424: aload #18
/*     */     //   426: invokevirtual movementStates : ()Lcom/hypixel/hytale/protocol/MovementStates;
/*     */     //   429: getfield rolling : Z
/*     */     //   432: ifeq -> 484
/*     */     //   435: dload #8
/*     */     //   437: aload #21
/*     */     //   439: invokevirtual getMaxFallSpeedRollFullMitigation : ()F
/*     */     //   442: f2d
/*     */     //   443: dcmpg
/*     */     //   444: ifgt -> 453
/*     */     //   447: iconst_0
/*     */     //   448: istore #30
/*     */     //   450: goto -> 484
/*     */     //   453: dload #8
/*     */     //   455: aload #21
/*     */     //   457: invokevirtual getMaxFallSpeedToEngageRoll : ()F
/*     */     //   460: f2d
/*     */     //   461: dcmpg
/*     */     //   462: ifgt -> 484
/*     */     //   465: iload #30
/*     */     //   467: i2d
/*     */     //   468: dconst_1
/*     */     //   469: aload #21
/*     */     //   471: invokevirtual getFallDamagePartialMitigationPercent : ()F
/*     */     //   474: f2d
/*     */     //   475: ldc2_w 100.0
/*     */     //   478: ddiv
/*     */     //   479: dsub
/*     */     //   480: dmul
/*     */     //   481: d2i
/*     */     //   482: istore #30
/*     */     //   484: iload #30
/*     */     //   486: ifle -> 536
/*     */     //   489: getstatic com/hypixel/hytale/server/core/modules/entity/damage/DamageSystems$FallDamagePlayers.$assertionsDisabled : Z
/*     */     //   492: ifne -> 509
/*     */     //   495: getstatic com/hypixel/hytale/server/core/modules/entity/damage/DamageCause.FALL : Lcom/hypixel/hytale/server/core/modules/entity/damage/DamageCause;
/*     */     //   498: ifnonnull -> 509
/*     */     //   501: new java/lang/AssertionError
/*     */     //   504: dup
/*     */     //   505: invokespecial <init> : ()V
/*     */     //   508: athrow
/*     */     //   509: new com/hypixel/hytale/server/core/modules/entity/damage/Damage
/*     */     //   512: dup
/*     */     //   513: getstatic com/hypixel/hytale/server/core/modules/entity/damage/Damage.NULL_SOURCE : Lcom/hypixel/hytale/server/core/modules/entity/damage/Damage$Source;
/*     */     //   516: getstatic com/hypixel/hytale/server/core/modules/entity/damage/DamageCause.FALL : Lcom/hypixel/hytale/server/core/modules/entity/damage/DamageCause;
/*     */     //   519: iload #30
/*     */     //   521: i2f
/*     */     //   522: invokespecial <init> : (Lcom/hypixel/hytale/server/core/modules/entity/damage/Damage$Source;Lcom/hypixel/hytale/server/core/modules/entity/damage/DamageCause;F)V
/*     */     //   525: astore #31
/*     */     //   527: iload_2
/*     */     //   528: aload_3
/*     */     //   529: aload #5
/*     */     //   531: aload #31
/*     */     //   533: invokestatic executeDamage : (ILcom/hypixel/hytale/component/ArchetypeChunk;Lcom/hypixel/hytale/component/CommandBuffer;Lcom/hypixel/hytale/server/core/modules/entity/damage/Damage;)V
/*     */     //   536: aload #19
/*     */     //   538: dconst_0
/*     */     //   539: invokevirtual setCurrentFallDistance : (D)V
/*     */     //   542: goto -> 545
/*     */     //   545: iinc #13, 1
/*     */     //   548: goto -> 110
/*     */     //   551: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #812	-> 0
/*     */     //   #813	-> 13
/*     */     //   #815	-> 32
/*     */     //   #816	-> 45
/*     */     //   #817	-> 64
/*     */     //   #819	-> 77
/*     */     //   #820	-> 90
/*     */     //   #822	-> 100
/*     */     //   #823	-> 107
/*     */     //   #824	-> 122
/*     */     //   #826	-> 136
/*     */     //   #827	-> 184
/*     */     //   #828	-> 207
/*     */     //   #829	-> 214
/*     */     //   #830	-> 227
/*     */     //   #832	-> 246
/*     */     //   #833	-> 267
/*     */     //   #834	-> 274
/*     */     //   #835	-> 287
/*     */     //   #837	-> 294
/*     */     //   #838	-> 314
/*     */     //   #839	-> 327
/*     */     //   #841	-> 346
/*     */     //   #842	-> 368
/*     */     //   #843	-> 378
/*     */     //   #845	-> 397
/*     */     //   #846	-> 404
/*     */     //   #848	-> 413
/*     */     //   #851	-> 424
/*     */     //   #852	-> 435
/*     */     //   #853	-> 447
/*     */     //   #854	-> 453
/*     */     //   #855	-> 465
/*     */     //   #859	-> 484
/*     */     //   #860	-> 489
/*     */     //   #861	-> 509
/*     */     //   #862	-> 527
/*     */     //   #866	-> 536
/*     */     //   #868	-> 542
/*     */     //   #823	-> 545
/*     */     //   #873	-> 551
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   191	16	17	velocityEntry	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetClientVelocity;
/*     */     //   527	9	31	damage	Lcom/hypixel/hytale/server/core/modules/entity/damage/Damage;
/*     */     //   327	209	23	entityStatMapComponent	Lcom/hypixel/hytale/server/core/modules/entitystats/EntityStatMap;
/*     */     //   368	168	24	damagePercentage	D
/*     */     //   378	158	26	healthStatValue	Lcom/hypixel/hytale/server/core/modules/entitystats/EntityStatValue;
/*     */     //   404	132	27	maxHealth	F
/*     */     //   413	123	28	healthModifier	D
/*     */     //   424	112	30	damageInt	I
/*     */     //   274	268	20	movementConfigIndex	I
/*     */     //   287	255	21	movementConfig	Lcom/hypixel/hytale/server/core/entity/entities/player/movement/MovementConfig;
/*     */     //   294	248	22	minFallSpeedToEngageRoll	F
/*     */     //   227	315	19	playerComponent	Lcom/hypixel/hytale/server/core/entity/entities/Player;
/*     */     //   214	331	18	movementStatesEntry	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetMovementStates;
/*     */     //   136	409	14	queueEntry	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$InputUpdate;
/*     */     //   110	441	13	i	I
/*     */     //   0	552	0	this	Lcom/hypixel/hytale/server/core/modules/entity/damage/DamageSystems$FallDamagePlayers;
/*     */     //   0	552	1	dt	F
/*     */     //   0	552	2	index	I
/*     */     //   0	552	3	archetypeChunk	Lcom/hypixel/hytale/component/ArchetypeChunk;
/*     */     //   0	552	4	store	Lcom/hypixel/hytale/component/Store;
/*     */     //   0	552	5	commandBuffer	Lcom/hypixel/hytale/component/CommandBuffer;
/*     */     //   13	539	6	playerInputComponent	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput;
/*     */     //   45	507	7	velocityComponent	Lcom/hypixel/hytale/server/core/modules/physics/component/Velocity;
/*     */     //   77	475	8	yVelocity	D
/*     */     //   90	462	10	world	Lcom/hypixel/hytale/server/core/universe/world/World;
/*     */     //   100	452	11	worldPlayerConfig	Lcom/hypixel/hytale/server/core/asset/type/gameplay/PlayerConfig;
/*     */     //   107	445	12	queue	Ljava/util/List;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	552	3	archetypeChunk	Lcom/hypixel/hytale/component/ArchetypeChunk<Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;>;
/*     */     //   0	552	4	store	Lcom/hypixel/hytale/component/Store<Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;>;
/*     */     //   0	552	5	commandBuffer	Lcom/hypixel/hytale/component/CommandBuffer<Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;>;
/*     */     //   107	445	12	queue	Ljava/util/List<Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$InputUpdate;>;
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
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 877 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\damage\DamageSystems$FallDamagePlayers.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */