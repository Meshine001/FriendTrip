package com.xjtu.friendtrip.db;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.xjtu.friendtrip.db.DBTraceFile;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FILES".
*/
public class DBTraceFileDao extends AbstractDao<DBTraceFile, Long> {

    public static final String TABLENAME = "FILES";

    /**
     * Properties of entity DBTraceFile.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Url = new Property(1, String.class, "url", false, "URL");
        public final static Property Type = new Property(2, String.class, "type", false, "TYPE");
        public final static Property Summary = new Property(3, String.class, "summary", false, "SUMMARY");
        public final static Property Position = new Property(4, Integer.class, "position", false, "POSITION");
        public final static Property TraceId = new Property(5, long.class, "traceId", false, "TRACE_ID");
    };

    private DaoSession daoSession;

    private Query<DBTraceFile> dBTrace_FilesQuery;

    public DBTraceFileDao(DaoConfig config) {
        super(config);
    }
    
    public DBTraceFileDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FILES\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"URL\" TEXT," + // 1: url
                "\"TYPE\" TEXT," + // 2: type
                "\"SUMMARY\" TEXT," + // 3: summary
                "\"POSITION\" INTEGER," + // 4: position
                "\"TRACE_ID\" INTEGER NOT NULL );"); // 5: traceId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FILES\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, DBTraceFile entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(2, url);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(3, type);
        }
 
        String summary = entity.getSummary();
        if (summary != null) {
            stmt.bindString(4, summary);
        }
 
        Integer position = entity.getPosition();
        if (position != null) {
            stmt.bindLong(5, position);
        }
        stmt.bindLong(6, entity.getTraceId());
    }

    @Override
    protected void attachEntity(DBTraceFile entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public DBTraceFile readEntity(Cursor cursor, int offset) {
        DBTraceFile entity = new DBTraceFile( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // url
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // type
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // summary
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // position
            cursor.getLong(offset + 5) // traceId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, DBTraceFile entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUrl(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setType(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSummary(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPosition(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setTraceId(cursor.getLong(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(DBTraceFile entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(DBTraceFile entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "files" to-many relationship of DBTrace. */
    public List<DBTraceFile> _queryDBTrace_Files(long traceId) {
        synchronized (this) {
            if (dBTrace_FilesQuery == null) {
                QueryBuilder<DBTraceFile> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.TraceId.eq(null));
                queryBuilder.orderRaw("T.'POSITION' ASC");
                dBTrace_FilesQuery = queryBuilder.build();
            }
        }
        Query<DBTraceFile> query = dBTrace_FilesQuery.forCurrentThread();
        query.setParameter(0, traceId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getDBTraceDao().getAllColumns());
            builder.append(" FROM FILES T");
            builder.append(" LEFT JOIN DBTRACE T0 ON T.\"TRACE_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected DBTraceFile loadCurrentDeep(Cursor cursor, boolean lock) {
        DBTraceFile entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        DBTrace dBTrace = loadCurrentOther(daoSession.getDBTraceDao(), cursor, offset);
         if(dBTrace != null) {
            entity.setDBTrace(dBTrace);
        }

        return entity;    
    }

    public DBTraceFile loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<DBTraceFile> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<DBTraceFile> list = new ArrayList<DBTraceFile>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<DBTraceFile> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<DBTraceFile> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}