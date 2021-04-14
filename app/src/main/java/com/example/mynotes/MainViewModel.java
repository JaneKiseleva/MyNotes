package com.example.mynotes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

//ViewModel - в нем мы будем делать все запросы к БД в другом потоке + у ViewModel есть свой жизненный цикл, на который не влияют методы onStop, onPause
//ViewModel нам нужен, чтобы не смотря на зыкрытие приложения или смену конфигурации наш запрос в БД продолжал выполнятся, а не отправлялся повторно.
//Переопределяем конструктор, и нам нужно будет получить БД
//Добавляем туда список заметок notes
//Создаем объект LiveData в котором будем хранить все заметки
//Создаем геттер на заметки
//Cоздаем метод для вставки элемента insertNote, так как он будет выполняться в другом потоке, создаем класс Задачи AsyncTask
//Создаем метод для удаления элемента
//Cоздаем метод для удаления всех заметок

public class MainViewModel extends AndroidViewModel {
    private static NotesDatabase database;
    private LiveData<List<Note>> notes;


    public MainViewModel(@NonNull Application application) {
        super(application);
        database = NotesDatabase.getInstance(getApplication());
        notes = database.notesDao().getAllNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public void insertNote (Note note) {
        new InsertTask().execute(note);
    }

    public void deleteNote (Note note) {
        new DeleteTask().execute(note);
    }

    public void deleteAllNotes () {
        new DeleteAllTask().execute();
    }

    public static class InsertTask extends AsyncTask <Note, Void, Void> {
        @Override
        protected Void doInBackground(Note... notes) {
            if (notes != null && notes.length > 0) {
            database.notesDao().insertNote(notes[0]);
            }
            return null;
        }
    }

    public static class DeleteTask extends AsyncTask <Note, Void, Void> {
        @Override
        protected Void doInBackground(Note... notes) {
            if (notes != null && notes.length > 0) {
                database.notesDao().deleteNote(notes[0]);
            }
            return null;
        }
    }

    public static class DeleteAllTask extends AsyncTask <Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... notes) {
            database.notesDao().deleteAllNotes();
            return null;
        }
    }
}
