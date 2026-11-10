package com.cardex.micardex.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MateriaDao_Impl implements MateriaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Materia> __insertionAdapterOfMateria;

  private final EntityDeletionOrUpdateAdapter<Materia> __deletionAdapterOfMateria;

  private final EntityDeletionOrUpdateAdapter<Materia> __updateAdapterOfMateria;

  public MateriaDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMateria = new EntityInsertionAdapter<Materia>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `materias` (`id`,`nombre`,`semestre`,`cal_normal`,`cal_repeticion`,`cal_especial`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Materia entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getNombre());
        statement.bindLong(3, entity.getSemestre());
        if (entity.getCalNormal() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getCalNormal());
        }
        if (entity.getCalRepeticion() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getCalRepeticion());
        }
        if (entity.getCalEspecial() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getCalEspecial());
        }
      }
    };
    this.__deletionAdapterOfMateria = new EntityDeletionOrUpdateAdapter<Materia>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `materias` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Materia entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfMateria = new EntityDeletionOrUpdateAdapter<Materia>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `materias` SET `id` = ?,`nombre` = ?,`semestre` = ?,`cal_normal` = ?,`cal_repeticion` = ?,`cal_especial` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Materia entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getNombre());
        statement.bindLong(3, entity.getSemestre());
        if (entity.getCalNormal() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getCalNormal());
        }
        if (entity.getCalRepeticion() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getCalRepeticion());
        }
        if (entity.getCalEspecial() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getCalEspecial());
        }
        statement.bindLong(7, entity.getId());
      }
    };
  }

  @Override
  public Object insertar(final Materia materia, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMateria.insert(materia);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertarTodas(final List<Materia> materias,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMateria.insert(materias);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object eliminar(final Materia materia, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMateria.handle(materia);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object actualizar(final Materia materia, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMateria.handle(materia);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Materia>> obtenerTodas() {
    final String _sql = "SELECT * FROM materias ORDER BY semestre, nombre";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"materias"}, new Callable<List<Materia>>() {
      @Override
      @NonNull
      public List<Materia> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfSemestre = CursorUtil.getColumnIndexOrThrow(_cursor, "semestre");
          final int _cursorIndexOfCalNormal = CursorUtil.getColumnIndexOrThrow(_cursor, "cal_normal");
          final int _cursorIndexOfCalRepeticion = CursorUtil.getColumnIndexOrThrow(_cursor, "cal_repeticion");
          final int _cursorIndexOfCalEspecial = CursorUtil.getColumnIndexOrThrow(_cursor, "cal_especial");
          final List<Materia> _result = new ArrayList<Materia>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Materia _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final int _tmpSemestre;
            _tmpSemestre = _cursor.getInt(_cursorIndexOfSemestre);
            final Integer _tmpCalNormal;
            if (_cursor.isNull(_cursorIndexOfCalNormal)) {
              _tmpCalNormal = null;
            } else {
              _tmpCalNormal = _cursor.getInt(_cursorIndexOfCalNormal);
            }
            final Integer _tmpCalRepeticion;
            if (_cursor.isNull(_cursorIndexOfCalRepeticion)) {
              _tmpCalRepeticion = null;
            } else {
              _tmpCalRepeticion = _cursor.getInt(_cursorIndexOfCalRepeticion);
            }
            final Integer _tmpCalEspecial;
            if (_cursor.isNull(_cursorIndexOfCalEspecial)) {
              _tmpCalEspecial = null;
            } else {
              _tmpCalEspecial = _cursor.getInt(_cursorIndexOfCalEspecial);
            }
            _item = new Materia(_tmpId,_tmpNombre,_tmpSemestre,_tmpCalNormal,_tmpCalRepeticion,_tmpCalEspecial);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object obtenerPorId(final int id, final Continuation<? super Materia> $completion) {
    final String _sql = "SELECT * FROM materias WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Materia>() {
      @Override
      @Nullable
      public Materia call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfSemestre = CursorUtil.getColumnIndexOrThrow(_cursor, "semestre");
          final int _cursorIndexOfCalNormal = CursorUtil.getColumnIndexOrThrow(_cursor, "cal_normal");
          final int _cursorIndexOfCalRepeticion = CursorUtil.getColumnIndexOrThrow(_cursor, "cal_repeticion");
          final int _cursorIndexOfCalEspecial = CursorUtil.getColumnIndexOrThrow(_cursor, "cal_especial");
          final Materia _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final int _tmpSemestre;
            _tmpSemestre = _cursor.getInt(_cursorIndexOfSemestre);
            final Integer _tmpCalNormal;
            if (_cursor.isNull(_cursorIndexOfCalNormal)) {
              _tmpCalNormal = null;
            } else {
              _tmpCalNormal = _cursor.getInt(_cursorIndexOfCalNormal);
            }
            final Integer _tmpCalRepeticion;
            if (_cursor.isNull(_cursorIndexOfCalRepeticion)) {
              _tmpCalRepeticion = null;
            } else {
              _tmpCalRepeticion = _cursor.getInt(_cursorIndexOfCalRepeticion);
            }
            final Integer _tmpCalEspecial;
            if (_cursor.isNull(_cursorIndexOfCalEspecial)) {
              _tmpCalEspecial = null;
            } else {
              _tmpCalEspecial = _cursor.getInt(_cursorIndexOfCalEspecial);
            }
            _result = new Materia(_tmpId,_tmpNombre,_tmpSemestre,_tmpCalNormal,_tmpCalRepeticion,_tmpCalEspecial);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object contar(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM materias";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Double> promedioGeneral() {
    final String _sql = "\n"
            + "        SELECT AVG(COALESCE(cal_especial, cal_repeticion, cal_normal))\n"
            + "        FROM materias\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"materias"}, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<PromedioSemestre>> promedioPorSemestre() {
    final String _sql = "\n"
            + "        SELECT semestre,\n"
            + "               AVG(COALESCE(cal_especial, cal_repeticion, cal_normal)) AS promedio,\n"
            + "               COUNT(*) AS materias\n"
            + "        FROM materias\n"
            + "        GROUP BY semestre\n"
            + "        ORDER BY semestre\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"materias"}, new Callable<List<PromedioSemestre>>() {
      @Override
      @NonNull
      public List<PromedioSemestre> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSemestre = 0;
          final int _cursorIndexOfPromedio = 1;
          final int _cursorIndexOfMaterias = 2;
          final List<PromedioSemestre> _result = new ArrayList<PromedioSemestre>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PromedioSemestre _item;
            final int _tmpSemestre;
            _tmpSemestre = _cursor.getInt(_cursorIndexOfSemestre);
            final Double _tmpPromedio;
            if (_cursor.isNull(_cursorIndexOfPromedio)) {
              _tmpPromedio = null;
            } else {
              _tmpPromedio = _cursor.getDouble(_cursorIndexOfPromedio);
            }
            final int _tmpMaterias;
            _tmpMaterias = _cursor.getInt(_cursorIndexOfMaterias);
            _item = new PromedioSemestre(_tmpSemestre,_tmpPromedio,_tmpMaterias);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
