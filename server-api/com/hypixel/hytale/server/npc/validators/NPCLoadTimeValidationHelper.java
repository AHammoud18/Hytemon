/*     */ package com.hypixel.hytale.server.npc.validators;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.valuestore.ValueStoreValidator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class NPCLoadTimeValidationHelper
/*     */ {
/*     */   private final String fileName;
/*     */   private final Model spawnModel;
/*     */   private final boolean isAbstract;
/*  23 */   private final HashSet<String> evaluatedAnimations = new HashSet<>();
/*  24 */   private final Set<Class<? extends MotionController>> providedMotionControllers = new HashSet<>();
/*  25 */   private final Set<Class<? extends MotionController>> requiredMotionControllers = new HashSet<>();
/*  26 */   private final ArrayDeque<HashSet<String>> seenFilterStack = new ArrayDeque<>();
/*     */   
/*  28 */   private final ValueStoreValidator valueStoreValidator = new ValueStoreValidator();
/*     */   
/*     */   @Nullable
/*     */   private Set<String> prioritiserProvidedFilterTypes;
/*     */   
/*     */   private int inventorySize;
/*     */   
/*     */   private int hotbarSize;
/*     */   
/*     */   private int offHandSize;
/*     */   
/*     */   private boolean parentSensorOnce;
/*     */   private boolean isVariant;
/*  41 */   private final ArrayDeque<String> stateStack = new ArrayDeque<>();
/*     */   
/*     */   public NPCLoadTimeValidationHelper(String fileName, Model spawnModel, boolean isAbstract) {
/*  44 */     this.fileName = fileName;
/*  45 */     this.spawnModel = spawnModel;
/*  46 */     this.isAbstract = isAbstract;
/*     */   }
/*     */   
/*     */   public void setInventorySizes(int inventorySize, int hotbarSize, int offHandSize) {
/*  50 */     this.inventorySize = inventorySize;
/*  51 */     this.hotbarSize = hotbarSize;
/*  52 */     this.offHandSize = offHandSize;
/*     */   }
/*     */   
/*     */   public Model getSpawnModel() {
/*  56 */     return this.spawnModel;
/*     */   }
/*     */   
/*     */   public boolean isAbstract() {
/*  60 */     return this.isAbstract;
/*     */   }
/*     */   
/*     */   public boolean isParentSensorOnce() {
/*  64 */     return this.parentSensorOnce;
/*     */   }
/*     */   
/*     */   public void updateParentSensorOnce(boolean parentSensorOnce) {
/*  68 */     this.parentSensorOnce |= parentSensorOnce;
/*     */   }
/*     */   
/*     */   public void clearParentSensorOnce() {
/*  72 */     this.parentSensorOnce = false;
/*     */   }
/*     */   
/*     */   public void setIsVariant() {
/*  76 */     this.isVariant = true;
/*     */   }
/*     */   
/*     */   public boolean isVariant() {
/*  80 */     return this.isVariant;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ValueStoreValidator getValueStoreValidator() {
/*  85 */     return this.valueStoreValidator;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getCurrentStateName() {
/*  90 */     return this.stateStack.peek();
/*     */   }
/*     */   
/*     */   public void pushCurrentStateName(@Nonnull String currentStateName) {
/*  94 */     this.stateStack.push(currentStateName);
/*     */   }
/*     */   
/*     */   public void popCurrentStateName() {
/*  98 */     this.stateStack.pop();
/*     */   }
/*     */   
/*     */   public void validateAnimation(@Nullable String animation) {
/* 102 */     if (animation == null || animation.isEmpty())
/* 103 */       return;  if (!this.evaluatedAnimations.add(animation))
/*     */       return; 
/* 105 */     if (!this.spawnModel.getAnimationSetMap().containsKey(animation)) {
/* 106 */       NPCPlugin.get().getLogger().at(Level.WARNING).log("Animation %s does not exist for model %s!", animation, this.spawnModel.getModelAssetId());
/*     */     }
/*     */   }
/*     */   
/*     */   public void registerMotionControllerType(Class<? extends MotionController> clazz) {
/* 111 */     this.providedMotionControllers.add(clazz);
/*     */   }
/*     */   
/*     */   public void requireMotionControllerType(Class<? extends MotionController> clazz) {
/* 115 */     this.requiredMotionControllers.add(clazz);
/*     */   }
/*     */   
/*     */   public boolean validateMotionControllers(@Nonnull List<String> errors) {
/* 119 */     if (this.requiredMotionControllers.isEmpty()) return true;
/*     */     
/* 121 */     ObjectArrayList<Class<? extends MotionController>> providedMotionControllerList = new ObjectArrayList(this.providedMotionControllers);
/* 122 */     int validCount = 0;
/*     */     
/* 124 */     for (Class<? extends MotionController> requiredMotionController : this.requiredMotionControllers) {
/* 125 */       boolean missing = true;
/* 126 */       for (int i = 0; i < providedMotionControllerList.size(); i++) {
/* 127 */         if (((Class)providedMotionControllerList.get(i)).isAssignableFrom(requiredMotionController)) {
/* 128 */           validCount++;
/* 129 */           missing = false;
/*     */           break;
/*     */         } 
/*     */       } 
/* 133 */       if (missing) {
/* 134 */         errors.add(String.format("%s: Missing required motion controller: %s", new Object[] { this.fileName, requiredMotionController.getSimpleName() }));
/*     */       }
/*     */     } 
/*     */     
/* 138 */     return (this.requiredMotionControllers.size() == validCount);
/*     */   }
/*     */   
/*     */   public boolean validateInventoryHasSlot(int slot, String context, @Nonnull List<String> errors) {
/* 142 */     if (slot < this.inventorySize) return true;
/*     */     
/* 144 */     errors.add(String.format("%s: Inventory too small for slot %d, requested by %s", new Object[] { this.fileName, Integer.valueOf(slot), context }));
/* 145 */     return false;
/*     */   }
/*     */   
/*     */   public boolean validateHotbarHasSlot(int slot, String context, @Nonnull List<String> errors) {
/* 149 */     if (slot < 0) {
/* 150 */       errors.add(String.format("%s: Hotbar slot %s is not valid for parameter %s. Must be >= 0", new Object[] { this.fileName, Integer.valueOf(slot), context }));
/* 151 */       return false;
/*     */     } 
/*     */     
/* 154 */     if (slot < this.hotbarSize) return true;
/*     */     
/* 156 */     errors.add(String.format("%s: Hotbar too small for slot %d, requested by %s. Actual size is %d", new Object[] { this.fileName, Integer.valueOf(slot), context, Integer.valueOf(this.hotbarSize) }));
/* 157 */     return false;
/*     */   }
/*     */   
/*     */   public boolean validateOffHandHasSlot(int slot, String context, @Nonnull List<String> errors) {
/* 161 */     if (slot < -1) {
/* 162 */       errors.add(String.format("%s: Off-hand slot %s is not valid for parameter %s. Must be -1 for empty, or >= 0", new Object[] { this.fileName, Integer.valueOf(slot), context }));
/* 163 */       return false;
/*     */     } 
/*     */     
/* 166 */     if (slot < this.offHandSize) return true;
/*     */     
/* 168 */     errors.add(String.format("%s: Off-hand inventory too small for slot %d, requested by %s. Actual size is %d", new Object[] { this.fileName, Integer.valueOf(slot), context, Integer.valueOf(this.offHandSize) }));
/* 169 */     return false;
/*     */   }
/*     */   
/*     */   public void pushFilterSet() {
/* 173 */     this.seenFilterStack.push(new HashSet<>());
/*     */   }
/*     */   
/*     */   public void popFilterSet() {
/* 177 */     this.seenFilterStack.pop();
/*     */   }
/*     */   
/*     */   public boolean hasSeenFilter(String filter) {
/* 181 */     HashSet<String> set = this.seenFilterStack.peek();
/* 182 */     Objects.requireNonNull(set, "A filter set must have been pushed before checking if a filter has been seen!");
/* 183 */     return !set.add(filter);
/*     */   }
/*     */   
/*     */   public void setPrioritiserProvidedFilterTypes(Set<String> prioritiserProvidedFilterTypes) {
/* 187 */     this.prioritiserProvidedFilterTypes = prioritiserProvidedFilterTypes;
/*     */   }
/*     */   
/*     */   public boolean isFilterExternallyProvided(String filter) {
/* 191 */     if (this.prioritiserProvidedFilterTypes == null) return false;
/*     */     
/* 193 */     return this.prioritiserProvidedFilterTypes.contains(filter);
/*     */   }
/*     */   
/*     */   public void clearPrioritiserProvidedFilterTypes() {
/* 197 */     this.prioritiserProvidedFilterTypes = null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\validators\NPCLoadTimeValidationHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */