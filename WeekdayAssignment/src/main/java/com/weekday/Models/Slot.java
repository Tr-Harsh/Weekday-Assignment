package com.weekday.Models;

public class Slot {
    private Integer slotID;
    private SlotStatus slotStatus;

    public Slot(Integer slotID, SlotStatus slotStatus) {
        this.slotID = slotID;
        this.slotStatus = slotStatus;
    }

    public Slot() {
    }

    public Integer getSlotID() {
        return slotID;
    }

    public void setSlotID(Integer slotID) {
        this.slotID = slotID;
    }

    public SlotStatus getSlotStatus() {
        return slotStatus;
    }

    public void setSlotStatus(SlotStatus slotStatus) {
        this.slotStatus = slotStatus;
    }
}
