package dev.emi.shipit.block.entity;

import java.util.UUID;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;

// Unused
public class MailBoxBlockEntity extends BlockEntity implements Inventory {
	private DefaultedList<ItemStack> stacks = DefaultedList.ofSize(9, ItemStack.EMPTY);
	private UUID owner;
	
	public MailBoxBlockEntity() {
		super(null);
		//super(ShipItBlockEntities.MAIL_BOX);
	}

	public void setOwner(LivingEntity player) {
		if (player instanceof PlayerEntity) {
			//ShipItComponents.MAIL.get(world.getLevelProperties()).setMailBox((PlayerEntity) player, world, pos);
			owner = player.getUuid();
		}
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);
		if (tag.contains("Items", 9)) {
			Inventories.fromTag(tag, stacks);
		}
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		super.toTag(tag);
		Inventories.toTag(tag, stacks, false);
		return tag;
	}

	@Override
	public void clear() {
		for (int i = 0; i < size(); i++) {
			setStack(i, ItemStack.EMPTY);
		}
	}

	@Override
	public int size() {
		return stacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : stacks) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStack(int slot) {
		return stacks.get(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		return Inventories.splitStack(this.stacks, slot, amount);
	}

	@Override
	public ItemStack removeStack(int slot) {
		return Inventories.removeStack(stacks, slot);
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		stacks.set(slot, stack);
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return player.getUuid().equals(owner);
	}
}
