package womp.shellfishmod.client.renderer;

import womp.shellfishmod.client.model.CrayfishModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.CrayfishEntity;
import womp.shellfishmod.entity.CrayfishEntity.Variant;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import womp.shellfishmod.client.ShellfishClient;

public class CrayfishRenderer extends MobRenderer<CrayfishEntity, ShellfishRenderState<Variant>, CrayfishModel> {

   public CrayfishRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CrayfishModel(renderManager.bakeLayer(ShellfishClient.CRAYFISH_MODEL)), 0.4f);
    }

    @Override
    public Identifier getTextureLocation(ShellfishRenderState<Variant> animatable) {
        int variant = animatable.variant.getIndex();
        if (animatable.nameTag != null && "Supercrayfish".equals(animatable.nameTag.getString())) {
            return Identifier.fromNamespaceAndPath("shellfish", "textures/entity/crayfish/supercrayfish.png");
        } else {
            return Identifier.fromNamespaceAndPath("shellfish", "textures/entity/crayfish/crayfish_" + variant + ".png");
        }
    }

    @Override
    public void submit(ShellfishRenderState<Variant> entity, PoseStack poseStack, SubmitNodeCollector orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
        
        int variant = entity.variant.getIndex();                  
        if (variant == 11 || variant == 17 || variant == 25) entity.shellfish.scale(poseStack, 0.9f, 0.65f, 0.4f);
        else if (variant == 18) entity.shellfish.scale(poseStack, 1.4f, 0.9f, 0.4f);
        else if (variant == 10 || variant == 21) entity.shellfish.scale(poseStack, 0.4f, 0.2f);
        else if (variant == 14 || variant == 19) entity.shellfish.scale(poseStack, 0.6f, 0.3f);
        else if (variant == 23) entity.shellfish.scale(poseStack, 0.5f, 0.25f);
        else entity.shellfish.scale(poseStack, 0.75f, 0.4f);

        super.submit(entity, poseStack, orderedRenderCommandQueue, cameraRenderState);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void extractRenderState(CrayfishEntity crayfish, ShellfishRenderState<Variant> crayfishState, float f) {
        super.extractRenderState(crayfish, crayfishState, f);
        crayfishState.variant = crayfish.getVariant();
        crayfishState.shellfish = crayfish;
        crayfishState.idleAnimationState.copyFrom(crayfish.idleAnimationState);
        crayfishState.moveAnimationState.copyFrom(crayfish.moveAnimationState);
    }
}
