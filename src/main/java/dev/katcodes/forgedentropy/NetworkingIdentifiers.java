package dev.katcodes.forgedentropy;

import net.minecraft.resources.ResourceLocation;

public class NetworkingIdentifiers {
    public static final ResourceLocation JOIN_HANDSHAKE = new ResourceLocation(ForgedEntropyMod.MODID, "join-handshake");
    public static final ResourceLocation JOIN_CONFIRM = new ResourceLocation(ForgedEntropyMod.MODID, "join-confirm");
    public static final ResourceLocation JOIN_SYNC = new ResourceLocation(ForgedEntropyMod.MODID, "join-sync");
    public static final ResourceLocation REMOVE_FIRST = new ResourceLocation(ForgedEntropyMod.MODID, "remove-first");
    public static final ResourceLocation REMOVE_ENDED = new ResourceLocation(ForgedEntropyMod.MODID, "remove-ended");
    public static final ResourceLocation ADD_EVENT = new ResourceLocation(ForgedEntropyMod.MODID, "add-event");
    public static final ResourceLocation END_EVENT = new ResourceLocation(ForgedEntropyMod.MODID, "end-event");
    public static final ResourceLocation TICK = new ResourceLocation(ForgedEntropyMod.MODID, "tick");
    public static final ResourceLocation NEW_POLL = new ResourceLocation(ForgedEntropyMod.MODID, "new-poll");
    public static final ResourceLocation POLL_STATUS = new ResourceLocation(ForgedEntropyMod.MODID, "poll-status");
}
