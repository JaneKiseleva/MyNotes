package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNotes;
    private final ArrayList<Note> notes = new ArrayList<>();
    private NotesAdapter adapter;
    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        adapter = new NotesAdapter(notes);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        getData();
        recyclerViewNotes.setAdapter(adapter);
        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener () {
            @Override
            public void onNoteClick(int position) {
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(int position) {
                remove(position);
            }
        });
        //Чтобы движением смахнуть заметку и она при этом удалилась (удаление СВАЙПОМ) создаем объект класса и используем анонимный класс:
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                remove(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
    }

    private void remove (int position) {
        Note note = adapter.getNotes().get(position);
        viewModel.deleteNote(note);
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    //метод, где мы получаем данные из базы данных и присваиваем их списку
    private void getData () {
        //Читаем данные из базы данных, отправили запрос через интерфейс, вызвав метод запроса. (Получили список заметок из БД)
        //Очистили список наших заметок и добавили туда новые
        //Добавив LiveData этот объект становится просматриваемым и если в нем произойдут изменения, БД сообщит об этих изменениях
        //Чтобы использовать полученные изменения используем метод observe. Он принимает два параметра: владелец, объект класса Observer
        LiveData<List<Note>> notesFromDB = viewModel.getNotes();
        notesFromDB.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notesFromLiveData) {
                adapter.setNotes(notesFromLiveData);
                //notes.clear();
                //notes.addAll(notesFromLiveData);
                //adapter.notifyDataSetChanged();
            }
        });
    }
}



 /*       if (notes.isEmpty()) {
            notes.add(new Note("Парикмахер", "Сделать прическу", "Понедельник", 2));
            notes.add(new Note("Баскетбол", "Игра со школьной командой", "Вторник", 3));
            notes.add(new Note("Магазин", "Купить новые джинсы", "Понедельник", 3));
            notes.add(new Note("Стоматолог", "Вылечить зубы", "Понедельник", 2));
            notes.add(new Note("Парикмахер", "Сделать прическу к выпускному", "Среда", 1));
            notes.add(new Note("Баскетбол", "Игра со школьной командой", "Вторник", 3));
            notes.add(new Note("Магазин", "Купить новые джинсы", "Понедельник", 3));
        }

        //Работа с базой данных
        //Вставили значения в объект contentValues
        for (Note note : notes) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLOMN_TITLE, note.getTitle());
            contentValues.put(NotesContract.NotesEntry.COLOMN_DESCRIPTION, note.getDescription());
            contentValues.put(NotesContract.NotesEntry.COLOMN_DAY_OF_WEEK, note.getDayOfWeek());
            contentValues.put(NotesContract.NotesEntry.COLOMN_PRIORITY, note.getPriority());
            //вставили объект contentValues с данными в базу данных
            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
        }
        //Читаем данные из базы данных и сохраняем их в новый массив
        ArrayList<Note> notesFromDB = new ArrayList<>();



    //private NotesDBHelper dbHelper;
    //private SQLiteDatabase database;

    //dbHelper = new NotesDBHelper(this); //получили объект помощника работы с БД
    //database = dbHelper.getWritableDatabase(); //у помощника получили БД и записали в переменную


        String where = NotesContract.NotesEntry._ID + " = ?";//а чему ? оно равно указываем дальше через массив строк
        String[] whereArgs = new String[] {Integer.toString(id)};
        //вторым параметром указываем что конкретно хотим удалить, для этого создали переменную where
        //database.delete(NotesContract.NotesEntry.TABLE_NAME, where, whereArgs);
         getData();

    //метод, где мы получаем данные из базы данных и присваиваем их массиву
    private void getData () {
        notes.clear(); //очищаем массив
        //еще один аргумент в курсоре, используем для отображения только части заметок, например, только самые приоритетные
        //Именно за счет этих строк и selection мы можем ограничивать показ данных
        String selection = NotesContract.NotesEntry.COLOMN_PRIORITY + " <?";
        String [] selectionArgs = new String[] {"3"};


        //Чтобы вытащить все данные из базы данных используем объект курсор с помощью метода query. Курсор указывает на конкретную строку
        //У курсора есть атрибут orderBy, который отвечает за сортировку
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME, null, selection, selectionArgs, null, null, NotesContract.NotesEntry.COLOMN_PRIORITY);
        //Чтобы получить строку вызываем метод moveToNext. Курсор ориентируется по индексу и выдаем нам int, поэтому приводим к нужному типу - String
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry._ID));
            String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLOMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLOMN_DESCRIPTION));
            int dayOfWeek = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLOMN_DAY_OF_WEEK));
            int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLOMN_PRIORITY));

            //Когда все данные получены мы можем cоздать объект типа Note (создаем таким образом новую заметку)
            Note note = new Note(id, title, description, dayOfWeek, priority);
            //Добавляем в только что созданный массив
            notes.add(note);
        }
        //Закрываем обязательно курсор
        cursor.close();
    }
        */