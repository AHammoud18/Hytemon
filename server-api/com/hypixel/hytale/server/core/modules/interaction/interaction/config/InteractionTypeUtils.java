/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import java.util.Collections;
/*    */ import java.util.EnumMap;
/*    */ import java.util.EnumSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InteractionTypeUtils
/*    */ {
/*    */   @Nonnull
/* 17 */   public static final Set<InteractionType> STANDARD_INPUT = EnumSet.of(InteractionType.Primary, new InteractionType[] { InteractionType.Secondary, InteractionType.Ability1, InteractionType.Ability2, InteractionType.Ability3, InteractionType.Use, InteractionType.Pick, InteractionType.SwapFrom, InteractionType.SwapTo });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 37 */   public static final Map<InteractionType, Set<InteractionType>> DEFAULT_INTERACTION_BLOCKED_BY = Collections.unmodifiableMap(new EnumMap<>(Map.ofEntries((Map.Entry<? extends InteractionType, ? extends Set<InteractionType>>[])new Map.Entry[] { 
/* 38 */             Map.entry(InteractionType.Primary, STANDARD_INPUT), 
/* 39 */             Map.entry(InteractionType.Secondary, STANDARD_INPUT), 
/* 40 */             Map.entry(InteractionType.Ability1, STANDARD_INPUT), 
/* 41 */             Map.entry(InteractionType.Ability2, STANDARD_INPUT), 
/* 42 */             Map.entry(InteractionType.Ability3, STANDARD_INPUT), 
/* 43 */             Map.entry(InteractionType.Use, STANDARD_INPUT), 
/* 44 */             Map.entry(InteractionType.Pick, STANDARD_INPUT), 
/* 45 */             Map.entry(InteractionType.Pickup, Collections.emptySet()), 
/* 46 */             Map.entry(InteractionType.CollisionEnter, Collections.emptySet()), 
/* 47 */             Map.entry(InteractionType.CollisionLeave, Collections.emptySet()), 
/* 48 */             Map.entry(InteractionType.Collision, Collections.emptySet()), 
/* 49 */             Map.entry(InteractionType.EntityStatEffect, Collections.emptySet()), 
/* 50 */             Map.entry(InteractionType.Death, Collections.emptySet()), 
/* 51 */             Map.entry(InteractionType.Wielding, Collections.emptySet()), 
/* 52 */             Map.entry(InteractionType.SwapTo, EnumSet.of(InteractionType.SwapFrom, InteractionType.SwapTo)), 
/* 53 */             Map.entry(InteractionType.SwapFrom, EnumSet.of(InteractionType.SwapFrom, InteractionType.SwapTo)), 
/* 54 */             Map.entry(InteractionType.ProjectileSpawn, Collections.emptySet()), 
/* 55 */             Map.entry(InteractionType.ProjectileBounce, Collections.emptySet()), 
/* 56 */             Map.entry(InteractionType.ProjectileMiss, Collections.emptySet()), 
/* 57 */             Map.entry(InteractionType.ProjectileHit, Collections.emptySet()), 
/* 58 */             Map.entry(InteractionType.Held, Set.of(InteractionType.Held)), 
/* 59 */             Map.entry(InteractionType.HeldOffhand, Set.of(InteractionType.HeldOffhand)), 
/* 60 */             Map.entry(InteractionType.Equipped, Set.of(InteractionType.Equipped)), 
/* 61 */             Map.entry(InteractionType.Dodge, Set.of(InteractionType.Dodge)), 
/* 62 */             Map.entry(InteractionType.GameModeSwap, Set.of(InteractionType.GameModeSwap)) })));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final float DEFAULT_COOLDOWN = 0.35F;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float getDefaultCooldown(@Nonnull InteractionType type) {
/* 80 */     switch (type) { case CollisionEnter: case CollisionLeave: case ProjectileSpawn: case ProjectileHit: case ProjectileMiss: case ProjectileBounce: case GameModeSwap: case EntityStatEffect: case Pickup:  }  return 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 85 */       0.35F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isCollisionType(@Nonnull InteractionType type) {
/* 96 */     return (type == InteractionType.Collision || type == InteractionType.CollisionEnter || type == InteractionType.CollisionLeave);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\InteractionTypeUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */