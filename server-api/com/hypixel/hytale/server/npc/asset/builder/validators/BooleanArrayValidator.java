package com.hypixel.hytale.server.npc.asset.builder.validators;

public abstract class BooleanArrayValidator extends Validator {
  public abstract boolean test(boolean[] paramArrayOfboolean);
  
  public abstract String errorMessage(String paramString, boolean[] paramArrayOfboolean);
  
  public abstract String errorMessage(boolean[] paramArrayOfboolean);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\BooleanArrayValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */