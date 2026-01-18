/*    */ package com.hypixel.hytale.builtin.npccombatactionevaluator.memory;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.npccombatactionevaluator.NPCCombatActionEvaluatorPlugin;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TargetMemory
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static ComponentType<EntityStore, TargetMemory> getComponentType() {
/* 25 */     return NPCCombatActionEvaluatorPlugin.get().getTargetMemoryComponentType();
/*    */   }
/*    */   
/* 28 */   private final Int2FloatOpenHashMap knownFriendlies = new Int2FloatOpenHashMap();
/* 29 */   private final List<Ref<EntityStore>> knownFriendliesList = (List<Ref<EntityStore>>)new ObjectArrayList();
/*    */   
/* 31 */   private final Int2FloatOpenHashMap knownHostiles = new Int2FloatOpenHashMap();
/* 32 */   private final List<Ref<EntityStore>> knownHostilesList = (List<Ref<EntityStore>>)new ObjectArrayList();
/*    */   
/*    */   private final float rememberFor;
/*    */   
/*    */   private Ref<EntityStore> closestHostile;
/*    */   
/*    */   public TargetMemory(float rememberFor) {
/* 39 */     this.rememberFor = rememberFor;
/* 40 */     this.knownFriendlies.defaultReturnValue(-1.0F);
/* 41 */     this.knownHostiles.defaultReturnValue(-1.0F);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Int2FloatOpenHashMap getKnownFriendlies() {
/* 46 */     return this.knownFriendlies;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public List<Ref<EntityStore>> getKnownFriendliesList() {
/* 51 */     return this.knownFriendliesList;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Int2FloatOpenHashMap getKnownHostiles() {
/* 56 */     return this.knownHostiles;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public List<Ref<EntityStore>> getKnownHostilesList() {
/* 61 */     return this.knownHostilesList;
/*    */   }
/*    */   
/*    */   public float getRememberFor() {
/* 65 */     return this.rememberFor;
/*    */   }
/*    */   
/*    */   public Ref<EntityStore> getClosestHostile() {
/* 69 */     return this.closestHostile;
/*    */   }
/*    */   
/*    */   public void setClosestHostile(Ref<EntityStore> ref) {
/* 73 */     this.closestHostile = ref;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 80 */     return new TargetMemory(this.rememberFor);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npccombatactionevaluator\memory\TargetMemory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */