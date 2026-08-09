package womp.shellfishmod.client.renderer;

import womp.shellfishmod.client.model.SeaUrchinModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.SeaUrchinEntity;
import womp.shellfishmod.entity.SeaUrchinEntity.Variant;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import womp.shellfishmod.client.ShellfishClient;

public class SeaUrchinRenderer extends MobRenderer<SeaUrchinEntity, ShellfishRenderState<SeaUrchinEntity.Variant>, SeaUrchinModel> {
    
    public SeaUrchinRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SeaUrchinModel(renderManager.bakeLayer(ShellfishClient.SEA_URCHIN_MODEL)), 0.125f);
    }

    @Override
    public Identifier getTextureLocation(ShellfishRenderState<SeaUrchinEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        return Identifier.fromNamespaceAndPath("shellfish", "textures/entity/sea_urchin/sea_urchin_" + variant + ".png");
    }

    @Override
    public void submit(ShellfishRenderState<SeaUrchinEntity.Variant> entity, PoseStack poseStack,
                       SubmitNodeCollector orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
        int variant = entity.variant.getIndex();                  
        if (variant == 1) entity.shellfish.scale(poseStack, 2f, 1.2f, 0.5f);
        else if (variant == 3) entity.shellfish.scale(poseStack, 0.6f, 0.3f);
        else if (variant == 4) entity.shellfish.scale(poseStack, 1.5f, 1.0f, 0.5f);
        else entity.shellfish.scale(poseStack, 1.0f, 0.5f);

        super.submit(entity, poseStack, orderedRenderCommandQueue, cameraRenderState);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void extractRenderState(SeaUrchinEntity seaUrchin, ShellfishRenderState<Variant> seaUrchinState, float f) {
        super.extractRenderState(seaUrchin, seaUrchinState, f);
        seaUrchinState.variant = seaUrchin.getVariant();
        seaUrchinState.shellfish = seaUrchin;
        seaUrchinState.idleAnimationState.copyFrom(seaUrchin.idleAnimationState);
        seaUrchinState.moveAnimationState.copyFrom(seaUrchin.moveAnimationState);
    }
}
