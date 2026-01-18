/*     */ package com.hypixel.hytale.server.core.modules.entity.player;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.QuerySystem;
/*     */ import com.hypixel.hytale.component.system.tick.RunWhenPausedSystem;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockPausedMovementSystem
/*     */   implements RunWhenPausedSystem<EntityStore>, QuerySystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/* 317 */   private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] {
/* 318 */         (Query)Player.getComponentType(), 
/* 319 */         (Query)PlayerInput.getComponentType(), 
/* 320 */         (Query)TransformComponent.getComponentType(), 
/* 321 */         (Query)HeadRotation.getComponentType()
/*     */       });
/*     */ 
/*     */   
/*     */   public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 326 */     store.forEachChunk(systemIndex, BlockPausedMovementSystem::onTick);
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
/*     */   private static void onTick(@Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*     */     // Byte code:
/*     */     //   0: iconst_0
/*     */     //   1: istore_2
/*     */     //   2: iload_2
/*     */     //   3: aload_0
/*     */     //   4: invokevirtual size : ()I
/*     */     //   7: if_icmpge -> 427
/*     */     //   10: aload_0
/*     */     //   11: iload_2
/*     */     //   12: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   15: invokevirtual getComponent : (ILcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   18: checkcast com/hypixel/hytale/server/core/modules/entity/player/PlayerInput
/*     */     //   21: astore_3
/*     */     //   22: getstatic com/hypixel/hytale/server/core/modules/entity/player/PlayerSystems$BlockPausedMovementSystem.$assertionsDisabled : Z
/*     */     //   25: ifne -> 40
/*     */     //   28: aload_3
/*     */     //   29: ifnonnull -> 40
/*     */     //   32: new java/lang/AssertionError
/*     */     //   35: dup
/*     */     //   36: invokespecial <init> : ()V
/*     */     //   39: athrow
/*     */     //   40: iconst_0
/*     */     //   41: istore #4
/*     */     //   43: aload_0
/*     */     //   44: iload_2
/*     */     //   45: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   48: invokevirtual getComponent : (ILcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   51: checkcast com/hypixel/hytale/server/core/modules/entity/component/TransformComponent
/*     */     //   54: astore #5
/*     */     //   56: getstatic com/hypixel/hytale/server/core/modules/entity/player/PlayerSystems$BlockPausedMovementSystem.$assertionsDisabled : Z
/*     */     //   59: ifne -> 75
/*     */     //   62: aload #5
/*     */     //   64: ifnonnull -> 75
/*     */     //   67: new java/lang/AssertionError
/*     */     //   70: dup
/*     */     //   71: invokespecial <init> : ()V
/*     */     //   74: athrow
/*     */     //   75: aload_0
/*     */     //   76: iload_2
/*     */     //   77: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   80: invokevirtual getComponent : (ILcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   83: checkcast com/hypixel/hytale/server/core/modules/entity/component/HeadRotation
/*     */     //   86: astore #6
/*     */     //   88: getstatic com/hypixel/hytale/server/core/modules/entity/player/PlayerSystems$BlockPausedMovementSystem.$assertionsDisabled : Z
/*     */     //   91: ifne -> 107
/*     */     //   94: aload #6
/*     */     //   96: ifnonnull -> 107
/*     */     //   99: new java/lang/AssertionError
/*     */     //   102: dup
/*     */     //   103: invokespecial <init> : ()V
/*     */     //   106: athrow
/*     */     //   107: aload_3
/*     */     //   108: invokevirtual getMovementUpdateQueue : ()Ljava/util/List;
/*     */     //   111: astore #7
/*     */     //   113: aload #7
/*     */     //   115: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   120: astore #8
/*     */     //   122: aload #8
/*     */     //   124: invokeinterface hasNext : ()Z
/*     */     //   129: ifeq -> 369
/*     */     //   132: aload #8
/*     */     //   134: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   139: checkcast com/hypixel/hytale/server/core/modules/entity/player/PlayerInput$InputUpdate
/*     */     //   142: astore #9
/*     */     //   144: aload #9
/*     */     //   146: dup
/*     */     //   147: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   150: pop
/*     */     //   151: astore #10
/*     */     //   153: iconst_0
/*     */     //   154: istore #11
/*     */     //   156: aload #10
/*     */     //   158: iload #11
/*     */     //   160: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   165: tableswitch default -> 366, 0 -> 192, 1 -> 239, 2 -> 311
/*     */     //   192: aload #10
/*     */     //   194: checkcast com/hypixel/hytale/server/core/modules/entity/player/PlayerInput$AbsoluteMovement
/*     */     //   197: astore #12
/*     */     //   199: aload #5
/*     */     //   201: invokevirtual getPosition : ()Lcom/hypixel/hytale/math/vector/Vector3d;
/*     */     //   204: aload #12
/*     */     //   206: invokevirtual getX : ()D
/*     */     //   209: aload #12
/*     */     //   211: invokevirtual getY : ()D
/*     */     //   214: aload #12
/*     */     //   216: invokevirtual getZ : ()D
/*     */     //   219: invokevirtual distanceSquaredTo : (DDD)D
/*     */     //   222: ldc2_w 0.009999999776482582
/*     */     //   225: dcmpl
/*     */     //   226: ifle -> 233
/*     */     //   229: iconst_1
/*     */     //   230: goto -> 234
/*     */     //   233: iconst_0
/*     */     //   234: istore #4
/*     */     //   236: goto -> 366
/*     */     //   239: aload #10
/*     */     //   241: checkcast com/hypixel/hytale/server/core/modules/entity/player/PlayerInput$RelativeMovement
/*     */     //   244: astore #13
/*     */     //   246: aload #5
/*     */     //   248: invokevirtual getPosition : ()Lcom/hypixel/hytale/math/vector/Vector3d;
/*     */     //   251: astore #14
/*     */     //   253: aload #5
/*     */     //   255: invokevirtual getPosition : ()Lcom/hypixel/hytale/math/vector/Vector3d;
/*     */     //   258: aload #14
/*     */     //   260: getfield x : D
/*     */     //   263: aload #13
/*     */     //   265: invokevirtual getX : ()D
/*     */     //   268: dadd
/*     */     //   269: aload #14
/*     */     //   271: getfield y : D
/*     */     //   274: aload #13
/*     */     //   276: invokevirtual getY : ()D
/*     */     //   279: dadd
/*     */     //   280: aload #14
/*     */     //   282: getfield z : D
/*     */     //   285: aload #13
/*     */     //   287: invokevirtual getZ : ()D
/*     */     //   290: dadd
/*     */     //   291: invokevirtual distanceSquaredTo : (DDD)D
/*     */     //   294: ldc2_w 0.009999999776482582
/*     */     //   297: dcmpl
/*     */     //   298: ifle -> 305
/*     */     //   301: iconst_1
/*     */     //   302: goto -> 306
/*     */     //   305: iconst_0
/*     */     //   306: istore #4
/*     */     //   308: goto -> 366
/*     */     //   311: aload #10
/*     */     //   313: checkcast com/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetHead
/*     */     //   316: astore #14
/*     */     //   318: aload #6
/*     */     //   320: invokevirtual getRotation : ()Lcom/hypixel/hytale/math/vector/Vector3f;
/*     */     //   323: aload #14
/*     */     //   325: invokevirtual direction : ()Lcom/hypixel/hytale/protocol/Direction;
/*     */     //   328: getfield pitch : F
/*     */     //   331: aload #14
/*     */     //   333: invokevirtual direction : ()Lcom/hypixel/hytale/protocol/Direction;
/*     */     //   336: getfield yaw : F
/*     */     //   339: aload #14
/*     */     //   341: invokevirtual direction : ()Lcom/hypixel/hytale/protocol/Direction;
/*     */     //   344: getfield roll : F
/*     */     //   347: invokevirtual distanceSquaredTo : (FFF)F
/*     */     //   350: ldc 0.01
/*     */     //   352: fcmpl
/*     */     //   353: ifle -> 360
/*     */     //   356: iconst_1
/*     */     //   357: goto -> 361
/*     */     //   360: iconst_0
/*     */     //   361: istore #4
/*     */     //   363: goto -> 366
/*     */     //   366: goto -> 122
/*     */     //   369: aload #7
/*     */     //   371: invokeinterface clear : ()V
/*     */     //   376: iload #4
/*     */     //   378: ifeq -> 421
/*     */     //   381: aload_1
/*     */     //   382: aload_0
/*     */     //   383: iload_2
/*     */     //   384: invokevirtual getReferenceTo : (I)Lcom/hypixel/hytale/component/Ref;
/*     */     //   387: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   390: new com/hypixel/hytale/server/core/modules/entity/teleport/Teleport
/*     */     //   393: dup
/*     */     //   394: aload #5
/*     */     //   396: invokevirtual getPosition : ()Lcom/hypixel/hytale/math/vector/Vector3d;
/*     */     //   399: aload #5
/*     */     //   401: invokevirtual getRotation : ()Lcom/hypixel/hytale/math/vector/Vector3f;
/*     */     //   404: invokespecial <init> : (Lcom/hypixel/hytale/math/vector/Vector3d;Lcom/hypixel/hytale/math/vector/Vector3f;)V
/*     */     //   407: aload #6
/*     */     //   409: invokevirtual getRotation : ()Lcom/hypixel/hytale/math/vector/Vector3f;
/*     */     //   412: invokevirtual withHeadRotation : (Lcom/hypixel/hytale/math/vector/Vector3f;)Lcom/hypixel/hytale/server/core/modules/entity/teleport/Teleport;
/*     */     //   415: invokevirtual withoutVelocityReset : ()Lcom/hypixel/hytale/server/core/modules/entity/teleport/Teleport;
/*     */     //   418: invokevirtual addComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;Lcom/hypixel/hytale/component/Component;)V
/*     */     //   421: iinc #2, 1
/*     */     //   424: goto -> 2
/*     */     //   427: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #336	-> 0
/*     */     //   #337	-> 10
/*     */     //   #338	-> 22
/*     */     //   #340	-> 40
/*     */     //   #341	-> 43
/*     */     //   #342	-> 56
/*     */     //   #344	-> 75
/*     */     //   #345	-> 88
/*     */     //   #347	-> 107
/*     */     //   #348	-> 113
/*     */     //   #349	-> 144
/*     */     //   #350	-> 192
/*     */     //   #351	-> 199
/*     */     //   #352	-> 239
/*     */     //   #353	-> 246
/*     */     //   #354	-> 253
/*     */     //   #355	-> 308
/*     */     //   #357	-> 311
/*     */     //   #358	-> 320
/*     */     //   #357	-> 363
/*     */     //   #362	-> 366
/*     */     //   #364	-> 369
/*     */     //   #366	-> 376
/*     */     //   #367	-> 381
/*     */     //   #368	-> 396
/*     */     //   #369	-> 401
/*     */     //   #370	-> 409
/*     */     //   #367	-> 418
/*     */     //   #336	-> 421
/*     */     //   #373	-> 427
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   199	40	12	abs	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$AbsoluteMovement;
/*     */     //   253	55	14	position	Lcom/hypixel/hytale/math/vector/Vector3d;
/*     */     //   246	65	13	rel	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$RelativeMovement;
/*     */     //   318	48	14	head	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetHead;
/*     */     //   144	222	9	entry	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$InputUpdate;
/*     */     //   22	399	3	playerInputComponent	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput;
/*     */     //   43	378	4	shouldTeleport	Z
/*     */     //   56	365	5	transformComponent	Lcom/hypixel/hytale/server/core/modules/entity/component/TransformComponent;
/*     */     //   88	333	6	headRotationComponent	Lcom/hypixel/hytale/server/core/modules/entity/component/HeadRotation;
/*     */     //   113	308	7	movementUpdateQueue	Ljava/util/List;
/*     */     //   2	425	2	index	I
/*     */     //   0	428	0	archetypeChunk	Lcom/hypixel/hytale/component/ArchetypeChunk;
/*     */     //   0	428	1	commandBuffer	Lcom/hypixel/hytale/component/CommandBuffer;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   113	308	7	movementUpdateQueue	Ljava/util/List<Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$InputUpdate;>;
/*     */     //   0	428	0	archetypeChunk	Lcom/hypixel/hytale/component/ArchetypeChunk<Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;>;
/*     */     //   0	428	1	commandBuffer	Lcom/hypixel/hytale/component/CommandBuffer<Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;>;
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
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 378 */     return this.query;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerSystems$BlockPausedMovementSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */