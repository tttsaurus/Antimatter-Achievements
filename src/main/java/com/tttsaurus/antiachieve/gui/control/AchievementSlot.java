package com.tttsaurus.antiachieve.gui.control;

import com.tttsaurus.antiachieve.gui.render.op.AchievementSlotOp;
import com.tttsaurus.ingameinfo.common.core.animation.SmoothDamp;
import com.tttsaurus.ingameinfo.common.core.gui.GuiResources;
import com.tttsaurus.ingameinfo.common.core.gui.control.Interactable;
import com.tttsaurus.ingameinfo.common.core.gui.event.IUIEventListener;
import com.tttsaurus.ingameinfo.common.core.gui.event.UIEvent;
import com.tttsaurus.ingameinfo.common.core.gui.event.UIEventListenerType;
import com.tttsaurus.ingameinfo.common.core.gui.property.lerp.LerpCenter;
import com.tttsaurus.ingameinfo.common.core.gui.property.lerp.LerpTarget;
import com.tttsaurus.ingameinfo.common.core.gui.property.lerp.LerpableProperty;
import com.tttsaurus.ingameinfo.common.core.gui.property.style.CallbackInfo;
import com.tttsaurus.ingameinfo.common.core.gui.property.style.StyleProperty;
import com.tttsaurus.ingameinfo.common.core.gui.property.style.StylePropertyCallback;
import com.tttsaurus.ingameinfo.common.core.gui.registry.RegisterElement;
import com.tttsaurus.ingameinfo.common.core.gui.render.RenderOpQueue;
import com.tttsaurus.ingameinfo.common.core.render.RenderUtils;
import com.tttsaurus.ingameinfo.common.core.render.text.FormattedText;
import com.tttsaurus.ingameinfo.common.core.render.texture.*;
import net.minecraft.util.ResourceLocation;

@RegisterElement
public class AchievementSlot extends Interactable
{
    private ImagePrefab image = null;

    @LerpTarget("percentage")
    private LerpableProperty<Float> lerpablePercentage = new LerpableProperty<Float>()
    {
        @Override
        public Float lerp(float v)
        {
            if (currValue == 0f) return 0f;
            return LerpCenter.lerp(prevValue, currValue, v);
        }
    };

    private static final float FADEIN_DURATION = 0.4f;
    private static final float HOVER_DURATION = 0.3f;

    private float timer = 0f;
    private float percentage = 0f;
    private boolean isFadingIn = false;
    private final SmoothDamp smoothDamp = new SmoothDamp(0f, 1f, FADEIN_DURATION);

    //<editor-fold desc="rl">
    @StylePropertyCallback
    public void rlValidation(String value, CallbackInfo callbackInfo)
    {
        if (value == null) callbackInfo.cancel = true;
    }
    @StylePropertyCallback
    public void setRlCallback()
    {
        if (GuiResources.exists(rl))
            image = GuiResources.get(rl);
        else
            image = GuiResources.tryRegisterTexture(new ResourceLocation(rl));
    }
    @StyleProperty(setterCallbackPost = "setRlCallback", setterCallbackPre = "rlValidation")
    public String rl;
    //</editor-fold>

    //<editor-fold desc="indexName">
    private FormattedText formattedIndexName = RenderUtils.bakeFormattedText("");

    @StylePropertyCallback
    public void setIndexNameCallback()
    {
        formattedIndexName = RenderUtils.bakeFormattedText(indexName);
        requestReCalc();
    }
    @StylePropertyCallback
    public void indexNameValidation(String value, CallbackInfo callbackInfo)
    {
        if (value == null) callbackInfo.cancel = true;
    }
    @StyleProperty(setterCallbackPost = "setIndexNameCallback", setterCallbackPre = "indexNameValidation")
    public String indexName = "";
    //</editor-fold>

    //<editor-fold desc="desc">
    private FormattedText formattedDesc = RenderUtils.bakeFormattedText("");

    @StylePropertyCallback
    public void setDescCallback()
    {
        formattedDesc = RenderUtils.bakeFormattedText(desc);
        requestReCalc();
    }
    @StylePropertyCallback
    public void descValidation(String value, CallbackInfo callbackInfo)
    {
        if (value == null) callbackInfo.cancel = true;
    }
    @StyleProperty(setterCallbackPost = "setDescCallback", setterCallbackPre = "descValidation")
    public String desc = "";
    //</editor-fold>

    //<editor-fold desc="reward">
    private FormattedText formattedReward = RenderUtils.bakeFormattedText("");

    @StylePropertyCallback
    public void setRewardCallback()
    {
        formattedReward = RenderUtils.bakeFormattedText(reward);
        requestReCalc();
    }
    @StylePropertyCallback
    public void rewardValidation(String value, CallbackInfo callbackInfo)
    {
        if (value == null) callbackInfo.cancel = true;
    }
    @StyleProperty(setterCallbackPost = "setRewardCallback", setterCallbackPre = "rewardValidation")
    public String reward = "";

    @StyleProperty
    public boolean hasReward = false;
    //</editor-fold>

    public AchievementSlot()
    {
        addEventListener(UIEvent.MouseEnter.class, new IUIEventListener<UIEvent.MouseEnter>()
        {
            @Override
            public void handle(UIEvent.MouseEnter mouseEnter)
            {
                smoothDamp.setTo(1f);
            }

            @Override
            public UIEventListenerType type()
            {
                return UIEventListenerType.TARGET;
            }
        });
        addEventListener(UIEvent.MouseLeave.class, new IUIEventListener<UIEvent.MouseLeave>()
        {
            @Override
            public void handle(UIEvent.MouseLeave mouseEnter)
            {
                smoothDamp.setTo(0f);
                timer = 0f;
            }

            @Override
            public UIEventListenerType type()
            {
                return UIEventListenerType.TARGET;
            }
        });
    }

    @Override
    public void onFixedUpdate(double deltaTime)
    {
        super.onFixedUpdate(deltaTime);

        if (hover && !isFadingIn)
        {
            timer += (float)deltaTime;
            if (timer >= HOVER_DURATION)
            {
                timer = 0f;
                isFadingIn = true;
            }
        }

        if (isFadingIn)
            percentage = smoothDamp.evaluate((float)deltaTime);

        if (isFadingIn && percentage <= 0.05f)
        {
            timer = 0f;
            percentage = 0f;
            isFadingIn = false;
        }
    }

    //
    @Override
    public void onRenderUpdate(RenderOpQueue queue, boolean focused)
    {
        super.onRenderUpdate(queue, focused);

        queue.enqueue(new AchievementSlotOp(
                rect,
                image,
                true,
                lerpablePercentage,
                formattedIndexName,
                formattedDesc,
                formattedReward,
                hasReward));
    }
}
