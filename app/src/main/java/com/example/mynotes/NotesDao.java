package com.example.mynotes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

//Dao - Database access object (объект доступа к данным), пометим его аннотацией Dao
//Здесь создадим методы для доступа к БД
//После окончания работы в интерфейсе создаем новый класс для создания самой БД

//Метод 1 (назовем его getAllNotes) - который будет получать все данные из БД. Он будет возвращать массив с заметками.
// Он должен вызываться при запросе к базе, поэтому помечаем его аннотацией @Query (запрос)
// для получения Select (выбрать) всех данных (обозначется звездочкой) и можем добавить доп.данные, например отсортировать (ASC - по возрастанию, DESC в обратном порядке)
// Теперь при вызове getAllNotes будет сформирован этот запрос и нам вернется List, добавим LiveData, чтобы возвращался не список записей, а объект LiveData

@Dao
public interface NotesDao {
    @Query("SELECT * FROM notes ORDER BY dayOfWeek ASC")
    LiveData<List<Note>> getAllNotes();

//Метод 2 (назовем его insertNote) - с помощью которого, мы будем вставлять данные в БД.
//В качестве параметра  он принимает заметку
//Этот метод будет вставлять данные, поэтому помечаем его аннотацией @Insert (вставка)

@Insert
void insertNote (Note note);

//Метод 3 (назовем его) - этот метод будет удалять данные. Помечаем аннотацией @Delete (удалить)

@Delete
void deleteNote (Note note);

//Дополнительный метод для удаления всей БД, делаем через запрос к БД

@Query("DELETE FROM notes")
void deleteAllNotes();
}

