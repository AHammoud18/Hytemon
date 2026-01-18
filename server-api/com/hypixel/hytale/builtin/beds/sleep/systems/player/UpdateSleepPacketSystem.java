/*     */ package com.hypixel.hytale.builtin.beds.sleep.systems.player;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.beds.sleep.components.PlayerSomnolence;
/*     */ import com.hypixel.hytale.builtin.beds.sleep.components.SleepTracker;
/*     */ import com.hypixel.hytale.builtin.beds.sleep.systems.world.StartSlumberSystem;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.DelayedEntitySystem;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.world.SleepMultiplayer;
/*     */ import com.hypixel.hytale.protocol.packets.world.UpdateSleepState;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.time.Duration;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UpdateSleepPacketSystem
/*     */   extends DelayedEntitySystem<EntityStore>
/*     */ {
/*  32 */   public static final Query<EntityStore> QUERY = (Query<EntityStore>)Query.and(new Query[] { (Query)PlayerRef.getComponentType(), (Query)PlayerSomnolence.getComponentType(), (Query)SleepTracker.getComponentType() });
/*     */   
/*  34 */   public static final Duration SPAN_BEFORE_BLACK_SCREEN = Duration.ofMillis(1200L);
/*     */   
/*     */   public static final int MAX_SAMPLE_COUNT = 5;
/*  37 */   private static final UUID[] EMPTY_UUIDS = new UUID[0];
/*  38 */   private static final UpdateSleepState PACKET_NO_SLEEP_UI = new UpdateSleepState(false, false, null, null);
/*     */ 
/*     */   
/*     */   public Query<EntityStore> getQuery() {
/*  42 */     return QUERY;
/*     */   }
/*     */   
/*     */   public UpdateSleepPacketSystem() {
/*  46 */     super(0.25F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
/*  51 */     UpdateSleepState packet = createSleepPacket(store, index, archetypeChunk);
/*  52 */     SleepTracker sleepTracker = (SleepTracker)archetypeChunk.getComponent(index, SleepTracker.getComponentType());
/*  53 */     packet = sleepTracker.generatePacketToSend(packet);
/*     */     
/*  55 */     if (packet != null) {
/*  56 */       PlayerRef playerRef = (PlayerRef)archetypeChunk.getComponent(index, PlayerRef.getComponentType());
/*  57 */       playerRef.getPacketHandler().write((Packet)packet);
/*     */     } 
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
/*     */   private UpdateSleepState createSleepPacket(Store<EntityStore> store, int index, ArchetypeChunk<EntityStore> archetypeChunk) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: invokevirtual getExternalData : ()Ljava/lang/Object;
/*     */     //   4: checkcast com/hypixel/hytale/server/core/universe/world/storage/EntityStore
/*     */     //   7: invokevirtual getWorld : ()Lcom/hypixel/hytale/server/core/universe/world/World;
/*     */     //   10: astore #4
/*     */     //   12: aload_1
/*     */     //   13: invokestatic getResourceType : ()Lcom/hypixel/hytale/component/ResourceType;
/*     */     //   16: invokevirtual getResource : (Lcom/hypixel/hytale/component/ResourceType;)Lcom/hypixel/hytale/component/Resource;
/*     */     //   19: checkcast com/hypixel/hytale/builtin/beds/sleep/resources/WorldSomnolence
/*     */     //   22: astore #5
/*     */     //   24: aload #5
/*     */     //   26: invokevirtual getState : ()Lcom/hypixel/hytale/builtin/beds/sleep/resources/WorldSleep;
/*     */     //   29: astore #6
/*     */     //   31: aload_3
/*     */     //   32: iload_2
/*     */     //   33: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */     //   36: invokevirtual getComponent : (ILcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */     //   39: checkcast com/hypixel/hytale/builtin/beds/sleep/components/PlayerSomnolence
/*     */     //   42: astore #7
/*     */     //   44: aload #7
/*     */     //   46: invokevirtual getSleepState : ()Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep;
/*     */     //   49: astore #8
/*     */     //   51: aload #6
/*     */     //   53: instanceof com/hypixel/hytale/builtin/beds/sleep/resources/WorldSlumber
/*     */     //   56: ifeq -> 74
/*     */     //   59: aload #6
/*     */     //   61: checkcast com/hypixel/hytale/builtin/beds/sleep/resources/WorldSlumber
/*     */     //   64: astore #10
/*     */     //   66: aload #10
/*     */     //   68: invokevirtual createSleepClock : ()Lcom/hypixel/hytale/protocol/packets/world/SleepClock;
/*     */     //   71: goto -> 75
/*     */     //   74: aconst_null
/*     */     //   75: astore #9
/*     */     //   77: aload #8
/*     */     //   79: dup
/*     */     //   80: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   83: pop
/*     */     //   84: astore #10
/*     */     //   86: iconst_0
/*     */     //   87: istore #11
/*     */     //   89: aload #10
/*     */     //   91: iload #11
/*     */     //   93: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   98: tableswitch default -> 128, 0 -> 138, 1 -> 151, 2 -> 164, 3 -> 269
/*     */     //   128: new java/lang/MatchException
/*     */     //   131: dup
/*     */     //   132: aconst_null
/*     */     //   133: aconst_null
/*     */     //   134: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
/*     */     //   137: athrow
/*     */     //   138: aload #10
/*     */     //   140: checkcast com/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$FullyAwake
/*     */     //   143: astore #12
/*     */     //   145: getstatic com/hypixel/hytale/builtin/beds/sleep/systems/player/UpdateSleepPacketSystem.PACKET_NO_SLEEP_UI : Lcom/hypixel/hytale/protocol/packets/world/UpdateSleepState;
/*     */     //   148: goto -> 288
/*     */     //   151: aload #10
/*     */     //   153: checkcast com/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$MorningWakeUp
/*     */     //   156: astore #13
/*     */     //   158: getstatic com/hypixel/hytale/builtin/beds/sleep/systems/player/UpdateSleepPacketSystem.PACKET_NO_SLEEP_UI : Lcom/hypixel/hytale/protocol/packets/world/UpdateSleepState;
/*     */     //   161: goto -> 288
/*     */     //   164: aload #10
/*     */     //   166: checkcast com/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$NoddingOff
/*     */     //   169: astore #14
/*     */     //   171: aload #4
/*     */     //   173: invokestatic check : (Lcom/hypixel/hytale/server/core/universe/world/World;)Lcom/hypixel/hytale/builtin/beds/sleep/systems/world/CanSleepInWorld$Result;
/*     */     //   176: invokeinterface isNegative : ()Z
/*     */     //   181: ifeq -> 190
/*     */     //   184: getstatic com/hypixel/hytale/builtin/beds/sleep/systems/player/UpdateSleepPacketSystem.PACKET_NO_SLEEP_UI : Lcom/hypixel/hytale/protocol/packets/world/UpdateSleepState;
/*     */     //   187: goto -> 288
/*     */     //   190: aload #14
/*     */     //   192: invokevirtual realTimeStart : ()Ljava/time/Instant;
/*     */     //   195: invokestatic now : ()Ljava/time/Instant;
/*     */     //   198: invokestatic between : (Ljava/time/temporal/Temporal;Ljava/time/temporal/Temporal;)Ljava/time/Duration;
/*     */     //   201: invokevirtual toMillis : ()J
/*     */     //   204: lstore #15
/*     */     //   206: lload #15
/*     */     //   208: getstatic com/hypixel/hytale/builtin/beds/sleep/systems/player/UpdateSleepPacketSystem.SPAN_BEFORE_BLACK_SCREEN : Ljava/time/Duration;
/*     */     //   211: invokevirtual toMillis : ()J
/*     */     //   214: lcmp
/*     */     //   215: ifle -> 222
/*     */     //   218: iconst_1
/*     */     //   219: goto -> 223
/*     */     //   222: iconst_0
/*     */     //   223: istore #17
/*     */     //   225: aload_3
/*     */     //   226: iload_2
/*     */     //   227: invokevirtual getReferenceTo : (I)Lcom/hypixel/hytale/component/Ref;
/*     */     //   230: astore #18
/*     */     //   232: aload_1
/*     */     //   233: aload #18
/*     */     //   235: invokestatic isReadyToSleep : (Lcom/hypixel/hytale/component/ComponentAccessor;Lcom/hypixel/hytale/component/Ref;)Z
/*     */     //   238: istore #19
/*     */     //   240: new com/hypixel/hytale/protocol/packets/world/UpdateSleepState
/*     */     //   243: dup
/*     */     //   244: iload #17
/*     */     //   246: iconst_0
/*     */     //   247: aload #9
/*     */     //   249: iload #19
/*     */     //   251: ifeq -> 262
/*     */     //   254: aload_0
/*     */     //   255: aload_1
/*     */     //   256: invokevirtual createSleepMultiplayer : (Lcom/hypixel/hytale/component/Store;)Lcom/hypixel/hytale/protocol/packets/world/SleepMultiplayer;
/*     */     //   259: goto -> 263
/*     */     //   262: aconst_null
/*     */     //   263: invokespecial <init> : (ZZLcom/hypixel/hytale/protocol/packets/world/SleepClock;Lcom/hypixel/hytale/protocol/packets/world/SleepMultiplayer;)V
/*     */     //   266: goto -> 288
/*     */     //   269: aload #10
/*     */     //   271: checkcast com/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$Slumber
/*     */     //   274: astore #15
/*     */     //   276: new com/hypixel/hytale/protocol/packets/world/UpdateSleepState
/*     */     //   279: dup
/*     */     //   280: iconst_1
/*     */     //   281: iconst_1
/*     */     //   282: aload #9
/*     */     //   284: aconst_null
/*     */     //   285: invokespecial <init> : (ZZLcom/hypixel/hytale/protocol/packets/world/SleepClock;Lcom/hypixel/hytale/protocol/packets/world/SleepMultiplayer;)V
/*     */     //   288: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #62	-> 0
/*     */     //   #63	-> 12
/*     */     //   #64	-> 24
/*     */     //   #66	-> 31
/*     */     //   #67	-> 44
/*     */     //   #69	-> 51
/*     */     //   #71	-> 77
/*     */     //   #72	-> 138
/*     */     //   #73	-> 151
/*     */     //   #74	-> 164
/*     */     //   #75	-> 171
/*     */     //   #77	-> 190
/*     */     //   #78	-> 206
/*     */     //   #80	-> 225
/*     */     //   #81	-> 232
/*     */     //   #83	-> 240
/*     */     //   #85	-> 269
/*     */     //   #71	-> 288
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   66	8	10	slumber	Lcom/hypixel/hytale/builtin/beds/sleep/resources/WorldSlumber;
/*     */     //   145	6	12	ignored	Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$FullyAwake;
/*     */     //   158	6	13	ignored	Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$MorningWakeUp;
/*     */     //   206	63	15	elapsedMs	J
/*     */     //   225	44	17	grayFade	Z
/*     */     //   232	37	18	ref	Lcom/hypixel/hytale/component/Ref;
/*     */     //   240	29	19	readyToSleep	Z
/*     */     //   171	98	14	noddingOff	Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$NoddingOff;
/*     */     //   276	12	15	ignored	Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$Slumber;
/*     */     //   0	289	0	this	Lcom/hypixel/hytale/builtin/beds/sleep/systems/player/UpdateSleepPacketSystem;
/*     */     //   0	289	1	store	Lcom/hypixel/hytale/component/Store;
/*     */     //   0	289	2	index	I
/*     */     //   0	289	3	archetypeChunk	Lcom/hypixel/hytale/component/ArchetypeChunk;
/*     */     //   12	277	4	world	Lcom/hypixel/hytale/server/core/universe/world/World;
/*     */     //   24	265	5	worldSomnolence	Lcom/hypixel/hytale/builtin/beds/sleep/resources/WorldSomnolence;
/*     */     //   31	258	6	worldSleepState	Lcom/hypixel/hytale/builtin/beds/sleep/resources/WorldSleep;
/*     */     //   44	245	7	playerSomnolence	Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSomnolence;
/*     */     //   51	238	8	playerSleepState	Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep;
/*     */     //   77	212	9	clock	Lcom/hypixel/hytale/protocol/packets/world/SleepClock;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   232	37	18	ref	Lcom/hypixel/hytale/component/Ref<Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;>;
/*     */     //   0	289	1	store	Lcom/hypixel/hytale/component/Store<Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;>;
/*     */     //   0	289	3	archetypeChunk	Lcom/hypixel/hytale/component/ArchetypeChunk<Lcom/hypixel/hytale/server/core/universe/world/storage/EntityStore;>;
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
/*     */   @Nullable
/*     */   private SleepMultiplayer createSleepMultiplayer(Store<EntityStore> store) {
/*  91 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*  92 */     List<PlayerRef> playerRefs = new ArrayList<>(world.getPlayerRefs());
/*  93 */     if (playerRefs.size() <= 1) {
/*  94 */       return null;
/*     */     }
/*     */     
/*  97 */     playerRefs.sort(Comparator.comparingLong(ref -> (ref.getUuid().hashCode() + world.hashCode())));
/*     */     
/*  99 */     int sleepersCount = 0;
/* 100 */     int awakeCount = 0;
/*     */     
/* 102 */     List<UUID> awakeSampleList = new ArrayList<>(playerRefs.size());
/* 103 */     for (PlayerRef playerRef : playerRefs) {
/* 104 */       Ref<EntityStore> ref = playerRef.getReference();
/* 105 */       boolean readyToSleep = StartSlumberSystem.isReadyToSleep((ComponentAccessor)store, ref);
/* 106 */       if (readyToSleep) {
/* 107 */         sleepersCount++; continue;
/*     */       } 
/* 109 */       awakeCount++;
/* 110 */       awakeSampleList.add(playerRef.getUuid());
/*     */     } 
/*     */ 
/*     */     
/* 114 */     UUID[] awakeSample = (awakeSampleList.size() > 5) ? EMPTY_UUIDS : (UUID[])awakeSampleList.toArray(x$0 -> new UUID[x$0]);
/*     */     
/* 116 */     return new SleepMultiplayer(sleepersCount, awakeCount, awakeSample);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\beds\sleep\systems\player\UpdateSleepPacketSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */