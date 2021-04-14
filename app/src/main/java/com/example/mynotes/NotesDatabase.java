package com.example.mynotes;
//Комментарий
// Данный класс мы сделали для создания самой БД. Данный класс должен быть абстрактным и должен расширять класс RoomDatabase
// Для того, чтобы быть уверенным, что объект класса существует только один (что мы работаем все с той же базой данных и не путаться), cуществует
// Pattern SINGLETON - шаблон проектирования
// В этом паттерне есть метод getInstance() (получить экземпляр)
// После создания метода и синхронизатора, у всего класса нужно указать аннотацию @Database
// c параметром entities(таблица) и в фигурных скобках таблицу Note.class, версию БД
// После всего, нам нужно добавить второй метод для получения объекта интерфейса NoteDao
// Вся работа с БД должна происходить в отличном потоке от главного.

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {
    //Создаем объект (целиком Базу данных) нашего класса
    private static com.example.mynotes.NotesDatabase database;
    //Создаем метод getInstance (дай БД), который будет возвращать объект нашей БД. Таким образом мы всегда будем получать один и тот же объект
    //Если база еще не создана этот метод ее создаст, а если создана, то вернет текущую базу
    private static final String DB_NAME = "notes2.db";
    private static final Object LOCK = new Object();

    public static com.example.mynotes.NotesDatabase getInstance(Context context) {
        //Важный момент - если из двух разных потоков будет обращение к методу getInstance, то одновременно может создаться 2 таблицы,
        // чтобы такого избежать, нужно добавить блок Синхронизации: создается объект (выше) для синхронизации: LOCK (замок)
        // и метод, который нам надо засинхронизировать мы оборачиваем в фигурные скобки synchronized (LOCK) {}
        synchronized (LOCK) {
            if (database == null) {
                //Для того, чтобы создать объект БД, необходимо использовать Строитель Room.databaseBuilder()
                // в качетсве параметра он принимает контекст, класс, имя БД (для этого выше ее создаем)
                //У полученного строителя вызываем метод build() - построить.
                database = Room.databaseBuilder(context, com.example.mynotes.NotesDatabase.class, DB_NAME).build();

            }
        }
        return database;
    }

    public abstract NotesDao notesDao();

}
