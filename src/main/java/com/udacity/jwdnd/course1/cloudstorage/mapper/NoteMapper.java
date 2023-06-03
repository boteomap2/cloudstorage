package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getAllNotes(Integer userId);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId} and userid = #{userId}")
    Note getNoteById(Integer noteId, Integer userId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    Integer addNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId} and userid = #{userId}")
    Integer deleteNoteById(Integer noteId, Integer userId);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId} and userid = #{userId}")
    Integer editNoteById(Note note);
}
