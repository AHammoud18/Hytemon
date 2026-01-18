package com.hypixel.hytale.server.npc.asset.builder.validators;

public abstract class IntArrayValidator extends Validator {
  public abstract boolean test(int[] paramArrayOfint);
  
  public abstract String errorMessage(int[] paramArrayOfint, String paramString);
  
  public abstract String errorMessage(int[] paramArrayOfint);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\IntArrayValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */