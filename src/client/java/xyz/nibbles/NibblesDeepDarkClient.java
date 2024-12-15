package xyz.nibbles;

import net.fabricmc.api.ClientModInitializer;
import xyz.nibbles.models.ModelPredicates;

public class NibblesDeepDarkClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		ModelPredicates.register();
	}
}