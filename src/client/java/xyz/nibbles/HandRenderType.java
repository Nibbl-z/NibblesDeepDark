package xyz.nibbles;

import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.util.Hand;

public enum HandRenderType {
    RENDER_BOTH_HANDS(true, true),
    RENDER_MAIN_HAND_ONLY(true, false),
    RENDER_OFF_HAND_ONLY(false, true);

    final boolean renderMainHand;
    final boolean renderOffHand;

    private HandRenderType(final boolean renderMainHand, final boolean renderOffHand) {
        this.renderMainHand = renderMainHand;
        this.renderOffHand = renderOffHand;
    }

    public static HandRenderType shouldOnlyRender(Hand hand) {
        return hand == Hand.MAIN_HAND ? RENDER_MAIN_HAND_ONLY : RENDER_OFF_HAND_ONLY;
    }
}
