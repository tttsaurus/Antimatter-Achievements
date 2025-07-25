package com.tttsaurus.antiachieve.mixin.early;

import com.tttsaurus.ingameinfo.common.core.IgiRuntimeLocator;
import com.tttsaurus.ingameinfo.common.core.commonutils.MouseUtils;
import com.tttsaurus.ingameinfo.common.core.gui.GuiResources;
import com.tttsaurus.ingameinfo.common.core.gui.layout.Rect;
import com.tttsaurus.ingameinfo.common.core.render.RenderUtils;
import net.minecraft.client.gui.inventory.GuiInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.awt.*;

@Mixin(GuiInventory.class)
public class GuiInventoryMixin
{
    @Inject(method = "drawScreen", at = @At("RETURN"))
    public void drawButton(int mouseX, int mouseY, float partialTicks, CallbackInfo ci)
    {
        GuiInventory this0 = (GuiInventory)((Object)this);

        float x = this0.getGuiLeft() + 104 + 25;
        float y = this0.height / 2f - 22;
        float width = 20;
        float height = 18;

        if ((new Rect(x, y, width, height)).contains(mouseX, mouseY) && !IgiRuntimeLocator.get().livePhase.isGuiOpen("achievement_page"))
        {
            RenderUtils.renderImagePrefab(x, y, width, height, GuiResources.get("vanilla_button"));
            RenderUtils.renderRectBrightnessOverlay(x, y, width, height, 0.07843137f, 0.11372548f, 0.3333333f);
            RenderUtils.renderRectOutline(x, y, width - 1, height - 1, 1f, Color.BLACK.getRGB());
            RenderUtils.renderText("A", x + 7f, y + 4.5f, 1f, 0xffffffa0, true);

            if (MouseUtils.isMouseDownLeft())
                IgiRuntimeLocator.get().livePhase.openGui("achievement_page");
        }
        else
        {
            RenderUtils.renderImagePrefab(x, y, width, height, GuiResources.get("vanilla_button"));
            RenderUtils.renderText("A", x + 7f, y + 4.5f, 1f, -1, true);
        }
    }
}
