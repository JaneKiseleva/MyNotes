package com.example.mynotes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//Для того, чтобы использовать (хранить) объект в базе даных его надо пометить аннотацией @Entity "сущность" и в скобках указываем название таблицы
//Каждая таблица обязана содержать уникальное id. Тут уже указала, поэтому помечаю, что это и есть первичный ключ, генерироваться будет автоматически
//Для того, чтобы этот объект можно было использовать в БД необходимо, чтобы он содержал конструктор (public), а также должен содержать геттеры и сеттеры
//Теория: POJO - Plain Old Java Object Это обычный объект, который содержит поля, конструкторы, геттеры, сеттеры
//После работы в этом классе нам нужно создать Интерфейс
@Entity(tableName = "notes")

public class Note {
    @PrimaryKey (autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private int dayOfWeek;
    private int priority;

    public Note(int id, String title, String description, int dayOfWeek, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    //Если мы захотим вносить заметки самостоятельно, а мы не знаем id, так как он генерируется автоматически,
    // для этого создаем новый конструктор с инициализированными полями, кроме id
    //Но, чтобы использовать этот класс Note в БД он должен содержать только 1 конструктор (выше) со всеми полями, поэтому конструктор ниже будет мешать
    //Чтобы он его игнорировал добавим аннотацию @Ignore
    @Ignore
    public Note(String title, String description, int dayOfWeek, int priority) {
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    public int getId() { return id; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public int getDayOfWeek() { return dayOfWeek; }

    public int getPriority() { return priority; }

    public void setId(int id) { this.id = id; }

    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) { this.description = description; }

    public void setDayOfWeek(int dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public void setPriority(int priority) { this.priority = priority; }

}
