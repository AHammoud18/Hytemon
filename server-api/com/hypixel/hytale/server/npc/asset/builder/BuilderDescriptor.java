/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.providerevaluators.ProviderEvaluator;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.Validator;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuilderDescriptor
/*    */ {
/*    */   private final String name;
/*    */   private final String category;
/*    */   private final BuilderDescriptorState state;
/*    */   private final String shortDescription;
/*    */   private final String longDescription;
/* 19 */   private final List<BuilderAttributeDescriptor> attributes = (List<BuilderAttributeDescriptor>)new ObjectArrayList();
/* 20 */   private final List<Validator> validators = (List<Validator>)new ObjectArrayList();
/* 21 */   private final List<ProviderEvaluator> providerEvaluators = (List<ProviderEvaluator>)new ObjectArrayList();
/*    */   
/*    */   private final Set<String> tags;
/*    */   
/*    */   public BuilderDescriptor(String name, String category, String shortDescription, String longDescription, Set<String> tags, BuilderDescriptorState state) {
/* 26 */     this.name = name;
/* 27 */     this.category = category;
/* 28 */     this.shortDescription = shortDescription;
/* 29 */     this.longDescription = longDescription;
/* 30 */     this.state = state;
/* 31 */     this.tags = tags;
/*    */   }
/*    */   
/*    */   public BuilderAttributeDescriptor addAttribute(BuilderAttributeDescriptor attributeDescriptor) {
/* 35 */     this.attributes.add(attributeDescriptor);
/* 36 */     return attributeDescriptor;
/*    */   }
/*    */   
/*    */   public BuilderAttributeDescriptor addAttribute(String name, String type, BuilderDescriptorState state, String shortDescription, String longDescription) {
/* 40 */     return addAttribute(new BuilderAttributeDescriptor(name, type, state, shortDescription, longDescription));
/*    */   }
/*    */   
/*    */   public void addValidator(Validator validator) {
/* 44 */     this.validators.add(validator);
/*    */   }
/*    */   
/*    */   public void addProviderEvaluator(ProviderEvaluator providerEvaluator) {
/* 48 */     this.providerEvaluators.add(providerEvaluator);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderDescriptor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */