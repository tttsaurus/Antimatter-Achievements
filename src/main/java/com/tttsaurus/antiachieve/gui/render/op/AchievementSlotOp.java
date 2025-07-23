package com.tttsaurus.antiachieve.gui.render.op;

import com.tttsaurus.antiachieve.gui.AchievementGuiConstant;
import com.tttsaurus.ingameinfo.common.core.gui.GuiResources;
import com.tttsaurus.ingameinfo.common.core.gui.layout.Rect;
import com.tttsaurus.ingameinfo.common.core.gui.property.lerp.LerpableProperty;
import com.tttsaurus.ingameinfo.common.core.gui.render.RenderContext;
import com.tttsaurus.ingameinfo.common.core.gui.render.op.IRenderOp;
import com.tttsaurus.ingameinfo.common.core.render.RenderMask;
import com.tttsaurus.ingameinfo.common.core.render.RenderUtils;
import com.tttsaurus.ingameinfo.common.core.render.text.FormattedText;
import com.tttsaurus.ingameinfo.common.core.render.texture.ImagePrefab;
import net.minecraft.util.math.MathHelper;
import java.awt.*;

public class AchievementSlotOp implements IRenderOp
{
    public Rect rect;
    public ImagePrefab image;
    public boolean unlock;
    public LerpableProperty<Float> lerpablePercentage;
    public FormattedText formattedIndexName;
    public FormattedText formattedDesc;
    public FormattedText formattedReward;
    public boolean hasReward;

    public AchievementSlotOp(
            Rect rect,
            ImagePrefab image,
            boolean unlock,
            LerpableProperty<Float> lerpablePercentage,
            FormattedText formattedIndexName,
            FormattedText formattedDesc,
            FormattedText formattedReward,
            boolean hasReward)
    {
        this.rect = rect;
        this.image = image;
        this.unlock = unlock;
        this.lerpablePercentage = lerpablePercentage;
        this.formattedIndexName = formattedIndexName;
        this.formattedDesc = formattedDesc;
        this.formattedReward = formattedReward;
        this.hasReward = hasReward;
    }

    @Override
    public void execute(RenderContext context)
    {
        if (image != null)
        {
            RenderMask mask = new RenderMask(RenderMask.MaskShape.ROUNDED_RECT);
            mask.setRoundedRectMask(rect.x, rect.y, rect.width, rect.height, 3f);
            mask.startMasking();

            RenderUtils.renderImagePrefab(rect.x, rect.y, rect.width, rect.height, image);

            RenderMask.endMasking();
        }

        if (unlock)
        {
            float offset = ((float)AchievementGuiConstant.SLOT_GAP) / 2f;
            int color = (new Color(0x804CD44C, true)).getRGB();
            RenderUtils.renderRect(rect.x - offset, rect.y - offset, rect.width + AchievementGuiConstant.SLOT_GAP, rect.height + AchievementGuiConstant.SLOT_GAP, color);
        }

        RenderUtils.renderFormattedText(formattedIndexName, rect.x, rect.y - 7, 0.6f, -1, true);

        float percentage = lerpablePercentage.lerp(context.lerpAlpha);

        if (percentage != 0f)
        {
            float targetWidth = 10f + formattedDesc.width;
            float targetHeight = 10f + formattedDesc.height;

            if (hasReward)
            {
                targetWidth = Math.max(targetWidth, 10f + formattedReward.width);
                targetHeight = targetHeight + 5f + formattedReward.height;
            }

            float width = MathHelper.clamp(targetWidth * percentage, 10f, 1e5f);
            float height = MathHelper.clamp(targetHeight * percentage, 1f, 1e5f);

            float tooltipX = (rect.x + rect.width / 2f) - width / 2f;
            float tooltipY = rect.y - height - 5f + (1f - percentage) * 4f;

            int color = (new Color(1f, 1f, 1f, percentage)).getRGB();

            RenderUtils.renderImagePrefab(tooltipX, tooltipY, width, MathHelper.clamp(height, 10f, 1e5f), GuiResources.get("vanilla_background"), color);

            RenderMask mask = new RenderMask(RenderMask.MaskShape.RECT);
            mask.setRectMask(
                    MathHelper.clamp(tooltipX + 5, 0, tooltipX + width),
                    MathHelper.clamp(tooltipY + 5, 0, tooltipY + height),
                    MathHelper.clamp(width - 10, 0, 1e5f),
                    MathHelper.clamp(height - 10, 0, 1e5f));
            mask.startMasking();

            float descX = (rect.x + rect.width / 2f) - targetWidth / 2f;
            float descY = rect.y - targetHeight - 5f + (1f - percentage) * 4f;

            RenderUtils.renderFormattedText(formattedDesc, descX + 5, descY + 5, 1f, -1, false);

            if (hasReward)
            {
                RenderUtils.renderRect(descX + 4, descY + 5 + formattedDesc.height + 2, targetWidth - 8, 1, Color.GRAY.getRGB());
                RenderUtils.renderFormattedText(formattedReward, descX + 5, descY + 10 + formattedDesc.height, 1f, -1, false);
            }

            RenderMask.endMasking();
        }
    }
}
