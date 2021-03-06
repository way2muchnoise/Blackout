package com.hilburn.blackout.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IRenderHandler;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class SkyRenderer extends IRenderHandler
{
	private static final ResourceLocation sunTexture = new ResourceLocation("blackout","textures/environment/sun.png");

    
	public int starGLCallList;
	public int glSkyList;
	public int glSkyList2;

	public SkyRenderer()
	{
		RenderGlobal renderGlobal = Minecraft.getMinecraft().renderGlobal;
		this.glSkyList2 = (this.glSkyList = (this.starGLCallList = ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, renderGlobal, "starGLCallList", "field_72772_v")) + 1) + 1;
	} 
        
	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc)
	{

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Vec3 vec3 = world.getSkyColor(mc.renderViewEntity, partialTicks);
		float f1 = (float)vec3.xCoord;
		float f2 = (float)vec3.yCoord;
		float f3 = (float)vec3.zCoord;
		if (mc.gameSettings.anaglyph)
		{
			f1 = 0;
			f2 = 0;
			f3 = 0;
		}

		GL11.glColor3f(f1, f2, f3);
		Tessellator tessellator = Tessellator.instance;
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glColor3f(f1, f2, f3);
		GL11.glCallList(this.glSkyList);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		RenderHelper.disableStandardItemLighting();
		float f4;
		float f5;
		float f6;
		float f7;

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.glBlendFunc(770, 1, 1, 0);
		GL11.glPushMatrix();
		f4 = 0.0F;
		f5 = 0.0F;
		f6 = 0.0F;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(f4, f5, f6);
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);

	    GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 5F);
	    GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
	    double var8 = 30.0F;
	    FMLClientHandler.instance().getClient().renderEngine.bindTexture(SkyRenderer.sunTexture);
	    tessellator.startDrawingQuads();
	    tessellator.addVertexWithUV(-var8, 150.0D, -var8, 0.0D, 0.0D);
	    tessellator.addVertexWithUV(var8, 150.0D, -var8, 1.0D, 0.0D);
	    tessellator.addVertexWithUV(var8, 150.0D, var8, 1.0D, 1.0D);
	    tessellator.addVertexWithUV(-var8, 150.0D, var8, 0.0D, 1.0D);
	    tessellator.draw();

        GL11.glPopMatrix();

        GL11.glPushMatrix();

        GL11.glDisable(GL11.GL_BLEND);

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        float f18 = world.getStarBrightness(partialTicks);

        if (f18 > 0.0F)
        {
            GL11.glColor4f(f18, f18, f18, f18);
            GL11.glCallList(this.starGLCallList);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor3f(0.0F, 0.0F, 0.0F);
        double d0 = mc.thePlayer.getPosition(partialTicks).yCoord - world.getHorizon();

        if (d0 < 0.0D)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 12.0F, 0.0F);
            GL11.glCallList(this.glSkyList2);
            GL11.glPopMatrix();
            f5 = 1.0F;
            f6 = -((float)(d0 + 65.0D));
            f7 = -f5;
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_I(0, 255);
            tessellator.addVertex((double)(-f5), (double)f6, (double)f5);
            tessellator.addVertex((double)f5, (double)f6, (double)f5);
            tessellator.addVertex((double)f5, (double)f7, (double)f5);
            tessellator.addVertex((double)(-f5), (double)f7, (double)f5);
            tessellator.addVertex((double)(-f5), (double)f7, (double)(-f5));
            tessellator.addVertex((double)f5, (double)f7, (double)(-f5));
            tessellator.addVertex((double)f5, (double)f6, (double)(-f5));
            tessellator.addVertex((double)(-f5), (double)f6, (double)(-f5));
            tessellator.addVertex((double)f5, (double)f7, (double)(-f5));
            tessellator.addVertex((double)f5, (double)f7, (double)f5);
            tessellator.addVertex((double)f5, (double)f6, (double)f5);
            tessellator.addVertex((double)f5, (double)f6, (double)(-f5));
            tessellator.addVertex((double)(-f5), (double)f6, (double)(-f5));
            tessellator.addVertex((double)(-f5), (double)f6, (double)f5);
            tessellator.addVertex((double)(-f5), (double)f7, (double)f5);
            tessellator.addVertex((double)(-f5), (double)f7, (double)(-f5));
            tessellator.addVertex((double)(-f5), (double)f7, (double)(-f5));
            tessellator.addVertex((double)(-f5), (double)f7, (double)f5);
            tessellator.addVertex((double)f5, (double)f7, (double)f5);
            tessellator.addVertex((double)f5, (double)f7, (double)(-f5));
            tessellator.draw();
        }

        if (world.provider.isSkyColored())
        {
            GL11.glColor3f(f1 * 0.2F + 0.04F, f2 * 0.2F + 0.04F, f3 * 0.6F + 0.1F);
        }
        else
        {
            GL11.glColor3f(f1, f2, f3);
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -((float)(d0 - 16.0D)), 0.0F);
        GL11.glCallList(this.glSkyList2);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(true);
    }

    
}
