package com.tttsaurus.antiachieve.gui.mvvm;

import com.tttsaurus.antiachieve.gui.AchievementGuiConstant;
import com.tttsaurus.ingameinfo.common.core.gui.layout.ElementGroup;
import com.tttsaurus.ingameinfo.common.core.gui.layout.Padding;
import com.tttsaurus.ingameinfo.common.core.mvvm.compose.ComposeBlock;
import com.tttsaurus.ingameinfo.common.core.mvvm.context.SharedContext;

public class AchievementGridCompose extends ComposeBlock
{
    public AchievementGridCompose(ElementGroup root)
    {
        super(root);
    }

    private int slotCount = 15;

    private void rowOfSlots(int currRow, int currColCount, int colCount)
    {
        ui("HorizontalGroup").wrap(() ->
        {
            for (int i = 0; i < currColCount; i++)
            {
                int slotIndex = currRow * colCount + i;
                String indexName = "@italic[@blue[" + slotIndex + "]]";
                ui("AchievementSlot")
                        .key("slot_" + slotIndex)
                        .prop("padding", new Padding(0, 0, 0, AchievementGuiConstant.SLOT_GAP))
                        .prop("width", AchievementGuiConstant.SLOT_SIZE)
                        .prop("height", AchievementGuiConstant.SLOT_SIZE)
                        .prop("rl", "antiachieve:textures/antimatter.png")
                        .prop("indexName", indexName)
                        .prop("desc", "Achievement " + indexName + "@br[]@item[minecraft:apple]@br[]I'm description!")
                        .prop("hasReward", true)
                        .prop("reward", "I'm the reward! lol @item[minecraft:diamond]");
            }
        });
    }

    @Override
    public void compose(double v, SharedContext sharedContext)
    {
        int column = (AchievementGuiConstant.GUI_WIDTH - AchievementGuiConstant.SECTION_PADDING + AchievementGuiConstant.SLOT_GAP) / (AchievementGuiConstant.SLOT_SIZE + AchievementGuiConstant.SLOT_GAP);
        int row = slotCount / column + (slotCount % column == 0 ? 0 : 1);
        int lastRowSlotCount = slotCount % column;

        ui("VerticalGroup").wrap(() ->
        {
            for (int i = 0; i < row; i++)
            {
                final int index = i;
                ui("SizedGroup")
                        .key("row_" + i)
                        .prop("useMask", false)
                        .prop("padding", new Padding(0, AchievementGuiConstant.SLOT_GAP, (float)AchievementGuiConstant.SECTION_PADDING / 2f, 0))
                        .prop("width", AchievementGuiConstant.GUI_WIDTH - AchievementGuiConstant.SECTION_PADDING)
                        .prop("height", AchievementGuiConstant.SLOT_SIZE)
                        .wrap(() -> { rowOfSlots(index, (index == row - 1 && lastRowSlotCount != 0) ? lastRowSlotCount : column, column); });
            }
        });
    }
}
