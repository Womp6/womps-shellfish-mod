package womp.shellfishmod.client.model.snail;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.SeaSnailEntity;
import womp.shellfishmod.entity.animations.ShellfishAnimations;

public abstract class SeaSnailModel extends EntityModel<ShellfishRenderState<SeaSnailEntity.Variant>> {

    public final ModelPart sea_snail;
	protected final KeyframeAnimation moveAnimation;
	protected final KeyframeAnimation idleAnimation;

    protected SeaSnailModel(ModelPart root) {
        super(root);
        this.sea_snail = root.getChild("sea_snail");
		this.moveAnimation = ShellfishAnimations.SNAIL_MOVE.bake(root);
		this.idleAnimation = ShellfishAnimations.SNAIL_HIDE.bake(root);
    }
    
    @Override
	public void setupAnim(ShellfishRenderState<SeaSnailEntity.Variant> entity) {
		super.setupAnim(entity);
		this.moveAnimation.apply(entity.moveAnimationState, entity.ageInTicks, 1f);
		this.idleAnimation.apply(entity.idleAnimationState, entity.ageInTicks, 1f);
	}
}
