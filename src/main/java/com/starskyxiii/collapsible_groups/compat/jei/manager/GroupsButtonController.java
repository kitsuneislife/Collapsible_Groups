package com.starskyxiii.collapsible_groups.compat.jei.manager;

import com.mojang.blaze3d.systems.RenderSystem;
import com.starskyxiii.collapsible_groups.i18n.ModTranslationKeys;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.common.gui.JeiTooltip;
import mezz.jei.common.util.ImmutableRect2i;
import mezz.jei.gui.elements.GuiIconButton;
import mezz.jei.gui.input.IUserInputHandler;
import mezz.jei.gui.input.UserInput;
import mezz.jei.common.input.IInternalKeyMappings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class GroupsButtonController {

    private static final ResourceLocation TEXTURE =
        new ResourceLocation("collapsible_groups", "textures/gui/groups_button.png");

    private static final IDrawable GROUPS_ICON = new IDrawable() {
        private static final float SCALE = 16f / 24f;

        @Override
        public int getWidth() {
            return 16;
        }

        @Override
        public int getHeight() {
            return 16;
        }

        @Override
        public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
            RenderSystem.setShaderColor(0.67f, 0.67f, 0.67f, 1.0f);
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(xOffset, yOffset, 0);
            guiGraphics.pose().scale(SCALE, SCALE, 1f);
            guiGraphics.blit(TEXTURE, 0, 0, 0f, 0f, 24, 24, 24, 24);
            guiGraphics.pose().popPose();
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        }
    };

    private final GuiIconButton button;
    private ImmutableRect2i area = ImmutableRect2i.EMPTY;

    public GroupsButtonController() {
        this.button = new GuiIconButton(GROUPS_ICON, b -> {
            Minecraft mc = Minecraft.getInstance();
            mc.setScreen(new GroupManagerScreen(mc.screen));
        });
    }

    public void updateBounds(ImmutableRect2i area) {
        this.area = area;
        this.button.updateBounds(area);
    }

    public void draw(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (area.isEmpty()) {
            return;
        }
        this.button.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    public void drawTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (area.isEmpty() || !area.contains(mouseX, mouseY)) {
            return;
        }
        JeiTooltip tooltip = new JeiTooltip();
        tooltip.add(Component.translatable(ModTranslationKeys.BUTTON_MANAGE_TOOLTIP));
        tooltip.draw(guiGraphics, mouseX, mouseY);
    }

    public IUserInputHandler createInputHandler() {
        return new GroupsButtonInputHandler(button);
    }

    private static final class GroupsButtonInputHandler implements IUserInputHandler {
        private final IUserInputHandler buttonHandler;
        private final GuiIconButton button;

        private GroupsButtonInputHandler(GuiIconButton button) {
            this.button = button;
            this.buttonHandler = button.createInputHandler();
        }

        @Override
        public Optional<IUserInputHandler> handleUserInput(Screen screen, UserInput input, IInternalKeyMappings keyBindings) {
            return buttonHandler.handleUserInput(screen, input, keyBindings)
                .flatMap(handled -> Optional.of(this));
        }
    }
}
