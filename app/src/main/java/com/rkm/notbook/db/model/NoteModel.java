package com.rkm.notbook.db.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "note_details")
public class NoteModel {
    @PrimaryKey
    @NotNull
    private String notId;
    private String noteTitle;
    private String noteDes;
    private long timeForaddNote;
    private long remindertime;
    private String noteTagcolorcode;
    private boolean pinNote=false;

    @NotNull
    public String getNotId() {
        return notId;
    }

    public void setNotId(@NotNull String notId) {
        this.notId = notId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDes() {
        return noteDes;
    }

    public void setNoteDes(String noteDes) {
        this.noteDes = noteDes;
    }

    public long getTimeForaddNote() {
        return timeForaddNote;
    }

    public void setTimeForaddNote(long timeForaddNote) {
        this.timeForaddNote = timeForaddNote;
    }

    public long getRemindertime() {
        return remindertime;
    }

    public void setRemindertime(long remindertime) {
        this.remindertime = remindertime;
    }

    public String getNoteTagcolorcode() {
        return noteTagcolorcode;
    }

    public void setNoteTagcolorcode(String noteTagcolorcode) {
        this.noteTagcolorcode = noteTagcolorcode;
    }

    public boolean isPinNote() {
        return pinNote;
    }

    public void setPinNote(boolean pinNote) {
        this.pinNote = pinNote;
    }
}
